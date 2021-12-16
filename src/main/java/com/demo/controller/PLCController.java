package com.demo.controller;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigService;
import com.demo.domain.FetchDataService;
import com.demo.domain.PLCData;
import com.demo.domain.PLCDataService;
import com.demo.model.PLCConfirmModel;
import com.demo.model.PLCSearchCriteria;
import com.demo.model.Response;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("plc")
public class PLCController {


    private final PLCDataService plcDataService;
    private final PatternConfigService patternConfigService;
    private final FetchDataService fetchDataService;

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
        plcDataService.confirmDuplicate(confirmModel.getBarcode());
        return Response.success();
    }

    @PostMapping("configs")
    public Response<List<PatternConfig>> configs() {
        return Response.success(patternConfigService.findAllConfig());
    }

    @GetMapping("test")
    public Response<PLCData> test() throws ModbusTransportException {
        if (fetchDataService.getModbusMaster() == null) {
            return Response.success(new PLCData());
        }
        return Response.success(fetchDataService.getData(1));
    }

}
