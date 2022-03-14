package com.demo.controller;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigService;
import com.demo.model.DataClearModel;
import com.demo.model.PLCConfirmModel;
import com.demo.model.Response;
import com.demo.plc.IDataSearchService;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("secure")
public class SecureController {

    private final PatternConfigService patternConfigService;
    private final IDataSearchService dataSearchService;


    public SecureController(PatternConfigService patternConfigService,
                            IDataSearchService dataSearchService) throws ModbusInitException {
        this.patternConfigService = patternConfigService;
        this.dataSearchService = dataSearchService;
    }

    @GetMapping("configs")
    public Response<List<PatternConfig>> findAllConfig() {
        return Response.success(patternConfigService.findAllConfig());
    }

    @PostMapping("config")
    public Response<PatternConfig> config(@RequestBody PatternConfig config) {
        return Response.success(patternConfigService.save(config));
    }

    @PostMapping("clear")
    public Response<Integer> clear(@RequestBody DataClearModel dataClearModel) {
        return Response.success(dataSearchService.clear(dataClearModel.getProductTypeId()));
    }

    @GetMapping("reinspect")
    public Response<Void> reinspect(long id, String status) {
        dataSearchService.reinspect(id, status);
        return Response.success();
    }

    /**
     * confirm the duplicated barcode with specified product type
     * @param confirmModel the data model for the confirmation request
     */
    @PostMapping("confirm")
    public Response<Void> confirm(@RequestBody PLCConfirmModel confirmModel) throws ModbusTransportException, ErrorResponseException {
        dataSearchService.confirmDuplicate(confirmModel.getBarcode(), confirmModel.getProductTypeId());
        return Response.success();
    }


}
