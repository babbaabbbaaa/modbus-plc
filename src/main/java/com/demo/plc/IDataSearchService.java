package com.demo.plc;

import com.demo.model.PLCQualifiedProductCountModel;
import com.demo.model.PLCSearchCriteria;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


public interface IDataSearchService {

    Page<? extends IPLCData> search(PLCSearchCriteria criteria);
    PLCQualifiedProductCountModel countQualifiedProducts(PLCSearchCriteria criteria);
    void confirmDuplicate(String barcode, Integer productTypeId) throws ModbusTransportException, ErrorResponseException;
    byte[] export(PLCSearchCriteria criteria) throws Exception;

    @Transactional
    int reinspect(int id, String status);

    @Transactional
    int clear(short productTypeId);
}
