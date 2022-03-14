package com.demo.controller;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigService;
import com.demo.model.PLCQualifiedProductCountModel;
import com.demo.model.PLCSearchCriteria;
import com.demo.model.Response;
import com.demo.plc.IDataFetchService;
import com.demo.plc.IDataSearchService;
import com.demo.plc.IPLCData;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("plc")
public class PLCController {


    private final IDataSearchService dataSearchService;
    private final PatternConfigService patternConfigService;
    private final IDataFetchService dataFetchService;
    private final BaseLocator<Number> signalLocator;

    private ModbusMaster modbusMaster;

    public PLCController(IDataSearchService dataSearchService,
                         PatternConfigService patternConfigService,
                         IDataFetchService dataFetchService,
                         @Value("${modbus.slave_id}") int slaveId) {
        this.dataSearchService = dataSearchService;
        this.patternConfigService = patternConfigService;
        this.dataFetchService = dataFetchService;
        this.signalLocator = BaseLocator.holdingRegister(slaveId, 7000, DataType.TWO_BYTE_INT_SIGNED);
    }

    @PostMapping("search")
    public Response<Page<? extends IPLCData>> search(@RequestBody PLCSearchCriteria criteria) {
        return Response.success(dataSearchService.search(criteria));
    }

    @PostMapping("export")
    @PreAuthorize("hasRole('EXPORT')")
    public ResponseEntity<byte[]> export(@RequestBody PLCSearchCriteria criteria) throws Exception {
        byte[] bytes = dataSearchService.export(criteria);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(bytes.length);
        String filename = URLEncoder.encode("PLC追溯文件.xlsx", StandardCharsets.UTF_8.displayName()).replaceAll("\\\\+", "%20");
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename, StandardCharsets.UTF_8).build());
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

    @PostMapping("configs")
    public Response<List<PatternConfig>> configs() {
        return Response.success(patternConfigService.findAllConfig());
    }

    @PostMapping("count")
    public Response<PLCQualifiedProductCountModel> count(@RequestBody PLCSearchCriteria criteria) throws ModbusTransportException {
        return Response.success(dataSearchService.countQualifiedProducts(criteria));
    }

    @GetMapping("data")
    public Response<IPLCData> data() throws ModbusTransportException {
        return Response.success(dataFetchService.getData());
    }

    @GetMapping("signal")
    public Response<Short> signal() throws ModbusTransportException, ErrorResponseException {
        if (null == modbusMaster) {
            return Response.success((short) 1);
        }
        return Response.success(modbusMaster.getValue(signalLocator).shortValue());
    }

    /**
     * Set the value in PLC according to the register
     * @param offset register position
     * @param value the value to set
     */
    @GetMapping("clear")
    public Response<Void> clear(int offset, int value) throws ModbusTransportException, ErrorResponseException {
        if (null != modbusMaster) {
            modbusMaster.setValue(BaseLocator.holdingRegister(1, offset, DataType.TWO_BYTE_INT_SIGNED), value);
        }
        return Response.success();
    }

    /**
     * get the value of the register
     * @param offset register
     * @return value of the register
     */
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
