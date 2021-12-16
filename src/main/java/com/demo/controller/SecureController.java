package com.demo.controller;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigService;
import com.demo.domain.PLCDataService;
import com.demo.model.DataClearModel;
import com.demo.model.Response;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("secure")
public class SecureController {

    private final PatternConfigService patternConfigService;
    private final PLCDataService plcDataService;
    private final BaseLocator<Number> signalLocator;
    private final String configPwd;

    private ModbusMaster modbusMaster;

    public SecureController(PatternConfigService patternConfigService,
                            PLCDataService plcDataService, @Value("${modbus.slave_id}") int slaveId,
                            @Value("${config.password}") String configPwd) throws ModbusInitException {
        this.patternConfigService = patternConfigService;
        this.plcDataService = plcDataService;
        this.signalLocator = BaseLocator.holdingRegister(slaveId, 7000, DataType.TWO_BYTE_INT_SIGNED);
        this.configPwd = configPwd;
    }

    @GetMapping("configs")
    public Response<List<PatternConfig>> findAllConfig() {
        return Response.success(patternConfigService.findAllConfig());
    }

    @GetMapping("signal")
    public Response<Short> signal() throws ModbusTransportException, ErrorResponseException {
//        return Response.success((short) (new SecureRandom().nextBoolean() ? 1 : 0));
        return Response.success(modbusMaster.getValue(signalLocator).shortValue());
    }

    @PostMapping("config")
    public Response<PatternConfig> config(@RequestBody PatternConfig config) {
        return Response.success(patternConfigService.save(config));
    }

    @PostMapping("clear")
    public Response<Integer> clear(@RequestBody DataClearModel dataClearModel) {
        return Response.success(plcDataService.clear(dataClearModel.getProductTypeId()));
    }

    @GetMapping("login")
    public Response<String> login(@RequestParam("x-api-access-key") String apiKey, HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        if (StringUtils.hasText(apiKey)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(this.configPwd.getBytes(StandardCharsets.UTF_8));
            String hashedPwd = DatatypeConverter.printHexBinary(md.digest()).toLowerCase(Locale.ROOT);
            if (Objects.equals(hashedPwd, apiKey.toLowerCase(Locale.ROOT))) {
                session.setAttribute("username", hashedPwd);
                return Response.success();
            }
        }
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return Response.forbidden();
    }

    @Autowired(required = false)
    public void setModbusMaster(ModbusMaster modbusMaster) {
        this.modbusMaster = modbusMaster;
    }


}
