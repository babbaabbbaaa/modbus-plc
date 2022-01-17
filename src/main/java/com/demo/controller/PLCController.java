package com.demo.controller;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigService;
import com.demo.domain.FetchDataService;
import com.demo.domain.PLCData;
import com.demo.domain.PLCDataService;
import com.demo.model.PLCConfirmModel;
import com.demo.model.PLCSearchCriteria;
import com.demo.model.Response;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("plc")
public class PLCController {


    private final PLCDataService plcDataService;
    private final PatternConfigService patternConfigService;
    private final FetchDataService fetchDataService;
    private final BaseLocator<Number> signalLocator;

    private ModbusMaster modbusMaster;

    public PLCController(PLCDataService plcDataService,
                         PatternConfigService patternConfigService,
                         FetchDataService fetchDataService,
                         @Value("${modbus.slave_id}") int slaveId) {
        this.plcDataService = plcDataService;
        this.patternConfigService = patternConfigService;
        this.fetchDataService = fetchDataService;
        this.signalLocator = BaseLocator.holdingRegister(slaveId, 7000, DataType.TWO_BYTE_INT_SIGNED);
    }

    @PostMapping("search")
    public Response<Page<PLCData>> search(@RequestBody PLCSearchCriteria criteria) {
        return Response.success(plcDataService.search(criteria));
    }

    @PostMapping("export")
    public ResponseEntity<byte[]> export(@RequestBody PLCSearchCriteria criteria) throws Exception {
        byte[] bytes = plcDataService.export(criteria);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(bytes.length);
        String filename = URLEncoder.encode("PLC追溯文件.xlsx", StandardCharsets.UTF_8.displayName()).replaceAll("\\\\+", "%20");
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename, StandardCharsets.UTF_8).build());
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

    @PostMapping("confirm")
    public Response<Void> confirm(@RequestBody PLCConfirmModel confirmModel) throws ModbusTransportException, ErrorResponseException {
        plcDataService.confirmDuplicate(confirmModel.getBarcode(), confirmModel.getProductTypeId());
        return Response.success();
    }

    @PostMapping("configs")
    public Response<List<PatternConfig>> configs() {
        return Response.success(patternConfigService.findAllConfig());
    }

//    @GetMapping("test")
//    public Response<PLCData> test() throws ModbusTransportException {
//        if (fetchDataService.getModbusMaster() == null) {
//            return Response.success(new PLCData());
//        }
//        return Response.success(fetchDataService.createPLC());
//    }

    @GetMapping("data")
    public Response<PLCData> data() throws ModbusTransportException {
        if (fetchDataService.getModbusMaster() == null) {
            return Response.success(new PLCData());
        }
        return Response.success(fetchDataService.getData());
    }

    @GetMapping("signal")
    public Response<Short> signal() throws ModbusTransportException, ErrorResponseException {
        if (null == modbusMaster) {
            return Response.success((short) 1);
        }
        return Response.success(modbusMaster.getValue(signalLocator).shortValue());
    }

    @GetMapping("clear")
    public Response<Void> clear(int offset, int value) throws ModbusTransportException, ErrorResponseException {
        if (null != modbusMaster) {
            modbusMaster.setValue(BaseLocator.holdingRegister(1, offset, DataType.TWO_BYTE_INT_SIGNED), value);
        }
        return Response.success();
    }

    @GetMapping("show")
    public Response<Integer> show(int offset) throws ModbusTransportException, ErrorResponseException {
        if (null != modbusMaster) {
            return Response.success(modbusMaster.getValue(BaseLocator.holdingRegister(1, offset, DataType.TWO_BYTE_INT_SIGNED)).intValue());
        }
        return Response.success();
    }

    @Autowired(required = false)
    public void setModbusMaster(ModbusMaster modbusMaster) {
        this.modbusMaster = modbusMaster;
    }


}
