package com.demo.domain.stamping;


import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;

import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.exceptions.ServiceException;
import com.demo.model.PLCQualifiedProductCountModel;
import com.demo.model.PLCSearchCriteria;
import com.demo.plc.IDataSearchService;
import com.demo.utility.ExcelHeaderConstants;
import com.demo.utility.ExcelHelper;
import com.demo.utility.SecurityUtil;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Profile("stamping")
public class StampingSearchService implements IDataSearchService {

    private final StampingRepository stampingRepository;
    private final BaseLocator<Number> duplicateLocator;
    private final EntityManager entityManager;

    private ModbusMaster modbusMaster;

    public StampingSearchService(StampingRepository stampingRepository,
                                 @Value("${modbus.slave_id}") int slaveId, EntityManager entityManager) {
        this.stampingRepository = stampingRepository;
        this.duplicateLocator = BaseLocator.holdingRegister(slaveId, 7002, DataType.TWO_BYTE_INT_SIGNED);
        this.entityManager = entityManager;
    }

    @Override
    public Page<Stamping> search(PLCSearchCriteria criteria) {
        Page<Stamping> stampings = stampingRepository.findAll(stampingRepository.buildSpecification(criteria), criteria.createPageRequest());
        if (StringUtils.hasText(criteria.getBarcodeData()) && stampings.isEmpty()) {
            throw new ServiceException("二维码有误！");
        }
        return stampings;
    }

    @Override
    public PLCQualifiedProductCountModel countQualifiedProducts(PLCSearchCriteria criteria) {
        List<Object[]> results = entityManager.createQuery(stampingRepository.buildCountQualifiedProducts(criteria, entityManager.getCriteriaBuilder())).getResultList();
        PLCQualifiedProductCountModel model = new PLCQualifiedProductCountModel();
        if (!CollectionUtils.isEmpty(results)) {
            for (Object[] result : results) {
                if (result.length == 2) {
                    if (Objects.equals(result[0], "设备OK")) {
                        model.setQualifiedCount(result[1]);
                    } else if (Objects.equals(result[0], "设备NG")) {
                        model.setNotQualifiedCount(result[1]);
                    }
                }
            }
        }
        return model;
    }

    @Override
    public void confirmDuplicate(String barcode, Integer productTypeId) throws ModbusTransportException, ErrorResponseException {
        List<Stamping> plcData = stampingRepository.getDataByBarcode(barcode, productTypeId.shortValue());
        if (!CollectionUtils.isEmpty(plcData)) {
            for (Stamping plc : plcData) {
                plc.setDuplicated(BarcodeDuplicateEnum.CONFIRMED);
            }
            stampingRepository.saveAll(plcData);
            short dup = modbusMaster.getValue(duplicateLocator).shortValue();
            if (dup == 1) {
                modbusMaster.setValue(duplicateLocator, 0);
            }
        }
    }

    @Override
    public byte[] export(PLCSearchCriteria criteria) throws Exception {
        List<Stamping> plcData = stampingRepository.findAll(stampingRepository.buildSpecification(criteria));
        return ExcelHelper.writeBean(plcData, ExcelHeaderConstants.EXCEL_HEADERS);
    }

    @Override
    public int reinspect(long id, String status) {
        return stampingRepository.updateStampingById(id, status, SecurityUtil.getCurrentUser());
    }

    @Override
    public int clear(short productTypeId) {
        return stampingRepository.deleteAllByProductTypeId(productTypeId);
    }

    @Autowired(required = false)
    public void setModbusMaster(ModbusMaster modbusMaster) {
        this.modbusMaster = modbusMaster;
    }
}
