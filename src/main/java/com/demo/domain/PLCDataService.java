package com.demo.domain;


import com.demo.model.PLCSearchCriteria;
import com.demo.utility.ExcelHeaderConstants;
import com.demo.utility.ExcelHelper;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class PLCDataService {

    private final PLCRepository plcRepository;
    private final BaseLocator<Number> duplicateLocator;

    private ModbusMaster modbusMaster;

    public PLCDataService(PLCRepository plcRepository,
                          @Value("${modbus.slave_id}") int slaveId) {
        this.plcRepository = plcRepository;
        this.duplicateLocator = BaseLocator.holdingRegister(slaveId, 7002, DataType.TWO_BYTE_INT_SIGNED);
    }

    public Page<PLCData> search(PLCSearchCriteria criteria) {
        return plcRepository.findAll(plcRepository.buildSpecification(criteria), criteria.createPageRequest());
    }

    public void confirmDuplicate(String barcode) throws ModbusTransportException, ErrorResponseException {
        List<PLCData> plcData = plcRepository.getDataByBarcode(barcode);
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

    public byte[] export(PLCSearchCriteria criteria) throws Exception {
        List<PLCData> plcData = plcRepository.findAll(plcRepository.buildSpecification(criteria));
        return ExcelHelper.writeBean(plcData, ExcelHeaderConstants.EXCEL_HEADERS);

    }

    public List<PLCData> findByBarcode(String barcode) {
        return plcRepository.getDataByBarcode(barcode);
    }

    @Transactional
    public int clear(short productTypeId) {
        return plcRepository.deleteAllByProductTypeId(productTypeId);
    }

    @Autowired(required = false)
    public void setModbusMaster(ModbusMaster modbusMaster) {
        this.modbusMaster = modbusMaster;
    }
}
