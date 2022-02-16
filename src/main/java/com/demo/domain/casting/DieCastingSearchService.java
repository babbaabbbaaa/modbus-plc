package com.demo.domain.casting;

import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.exceptions.ServiceException;
import com.demo.model.PLCQualifiedProductCountModel;
import com.demo.model.PLCSearchCriteria;
import com.demo.plc.IDataSearchService;
import com.demo.plc.IPLCData;
import com.demo.utility.ExcelHeaderConstants;
import com.demo.utility.ExcelHelper;
import com.demo.utility.SecurityUtil;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Profile("casting")
public class DieCastingSearchService implements IDataSearchService {

    private final DieCastingRepository dieCastingRepository;
    private final SubDieCastingRepository subDieCastingRepository;
    private final ModbusMaster modbusMaster;
    private final EntityManager entityManager;

    private final BaseLocator<Number> duplicateLocator;

    public DieCastingSearchService(DieCastingRepository dieCastingRepository,
                                   SubDieCastingRepository subDieCastingRepository,
                                   @Autowired(required = false) ModbusMaster modbusMaster, EntityManager entityManager) {
        this.dieCastingRepository = dieCastingRepository;
        this.subDieCastingRepository = subDieCastingRepository;
        this.modbusMaster = modbusMaster;
        this.entityManager = entityManager;
        this.duplicateLocator = BaseLocator.holdingRegister(1, 7002, DataType.TWO_BYTE_INT_SIGNED);
    }

    @Override
    public Page<? extends IPLCData> search(PLCSearchCriteria criteria) {
        Page<DieCasting> dieCastings = dieCastingRepository.findAll(dieCastingRepository.buildSpecification(criteria), criteria.createPageRequest());
        if (StringUtils.hasText(criteria.getBarcodeData()) && dieCastings.isEmpty()) {
            throw new ServiceException("二维码有误！");
        }
        List<DieCasting> dieCastingList = dieCastings.stream().distinct().collect(Collectors.toList());
        if (StringUtils.hasText(criteria.getBarcodeData())) {
            for (DieCasting dieCasting : dieCastingList) {
                dieCasting.setSubDieCastings(dieCasting.getSubDieCastings().stream().filter(sub -> Objects.equals(sub.getBarcodeData(), criteria.getBarcodeData())).collect(Collectors.toList()));
            }
        }
        return new PageImpl<>(dieCastingList, dieCastings.getPageable(), dieCastings.getTotalElements());
    }

    @Override
    public PLCQualifiedProductCountModel countQualifiedProducts(PLCSearchCriteria criteria) {
        List<Object[]> results = entityManager.createQuery(dieCastingRepository.buildCountQualifiedProducts(criteria, entityManager.getCriteriaBuilder())).getResultList();
        PLCQualifiedProductCountModel model = new PLCQualifiedProductCountModel();
        if (!CollectionUtils.isEmpty(results)) {
            for (Object[] result : results) {
                if (Objects.equals("合格", result[0])) {
                    model.setQualifiedCount(result[1]);
                } else if (Objects.equals("不合格", result[0])) {
                    model.setNotQualifiedCount(result[1]);
                }
            }
        }
        return model;
    }

    @Override
    public void confirmDuplicate(String barcode, Integer productTypeId) throws ModbusTransportException, ErrorResponseException {
        List<SubDieCasting> plcData = subDieCastingRepository.findSubDieCastingByBarcodeAndProduct(barcode, productTypeId);
        if (!CollectionUtils.isEmpty(plcData)) {
            for (SubDieCasting plc : plcData) {
                plc.setDuplicated(BarcodeDuplicateEnum.CONFIRMED);
            }
            subDieCastingRepository.saveAll(plcData);
            short dup = modbusMaster.getValue(duplicateLocator).shortValue();
            if (dup == 1) {
                modbusMaster.setValue(duplicateLocator, 0);
            }
        }
    }

    @Override
    public byte[] export(PLCSearchCriteria criteria) throws Exception {
        List<DieCasting> plcData = dieCastingRepository.findAll(dieCastingRepository.buildSpecification(criteria));
        return ExcelHelper.writeBean(plcData.stream().distinct().collect(Collectors.toList()), ExcelHeaderConstants.DIE_CASTING_EXCEL_HEADERS);
    }

    @Override
    public int reinspect(int id, String status) {
        return subDieCastingRepository.saveManualReinspectResult(status, SecurityUtil.getCurrentUser(), id);
    }

    @Override
    public int clear(short productTypeId) {
        return dieCastingRepository.deleteAllByProductTypeId(productTypeId);
    }
}
