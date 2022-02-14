package com.demo.domain.stamping;


import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.model.PLCQualifiedProductCountModel;
import com.demo.model.PLCSearchCriteria;
import com.demo.plc.IDataSearchService;
import com.demo.utility.ExcelHeaderConstants;
import com.demo.utility.ExcelHelper;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@Profile("stamping")
public class PLCDataService implements IDataSearchService {

    private final PLCRepository plcRepository;
    private final BaseLocator<Number> duplicateLocator;
    private final EntityManager entityManager;

    private ModbusMaster modbusMaster;

    public PLCDataService(PLCRepository plcRepository,
                          @Value("${modbus.slave_id}") int slaveId, EntityManager entityManager) {
        this.plcRepository = plcRepository;
        this.duplicateLocator = BaseLocator.holdingRegister(slaveId, 7002, DataType.TWO_BYTE_INT_SIGNED);
        this.entityManager = entityManager;
    }

    @Override
    public Page<PLCData> search(PLCSearchCriteria criteria) {
        return plcRepository.findAll(plcRepository.buildSpecification(criteria), criteria.createPageRequest());
    }

    @Override
    public PLCQualifiedProductCountModel countQualifiedProducts(PLCSearchCriteria criteria) {
        List<Object[]> results = entityManager.createQuery(plcRepository.buildCountQualifiedProducts(criteria, entityManager.getCriteriaBuilder())).getResultList();
        PLCQualifiedProductCountModel model = new PLCQualifiedProductCountModel();
        if (!CollectionUtils.isEmpty(results)) {
            for (Object[] result : results) {
                if (result.length == 2) {
                    if (Objects.equals(result[0], true)) {
                        model.setQualifiedCount(result[1]);
                    }
                    if (Objects.equals(result[0], false)) {
                        model.setNotQualifiedCount(result[1]);
                    }
                }
            }
        }
        return model;
    }

    @Override
    public void confirmDuplicate(String barcode, Integer productTypeId) throws ModbusTransportException, ErrorResponseException {
        List<PLCData> plcData = plcRepository.getDataByBarcode(barcode, productTypeId);
        if (!CollectionUtils.isEmpty(plcData)) {
            for (PLCData plc : plcData) {
                plc.setDuplicated(BarcodeDuplicateEnum.CONFIRMED);
            }
            plcRepository.saveAll(plcData);
            short dup = modbusMaster.getValue(duplicateLocator).shortValue();
            if (dup == 1) {
                modbusMaster.setValue(duplicateLocator, 0);
            }
        }
    }

    @Override
    public byte[] export(PLCSearchCriteria criteria) throws Exception {
        List<PLCData> plcData = plcRepository.findAll(plcRepository.buildSpecification(criteria));
        return ExcelHelper.writeBean(plcData, ExcelHeaderConstants.EXCEL_HEADERS);
    }

    @Override
    public int reinspect(int id, String status) {
        return 0;
    }

    @Override
    public int clear(short productTypeId) {
        return plcRepository.deleteAllByProductTypeId(productTypeId);
    }

    @Autowired(required = false)
    public void setModbusMaster(ModbusMaster modbusMaster) {
        this.modbusMaster = modbusMaster;
    }
}
