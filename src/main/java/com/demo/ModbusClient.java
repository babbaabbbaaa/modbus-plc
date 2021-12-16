package com.demo;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class ModbusClient {

    @Value("${modbus.host}")
    private String host;
    @Value("${modbus.port}")
    private int port;

    @Bean
    public ModbusMaster modbusMaster() throws ModbusInitException {
        IpParameters params = new IpParameters();
        params.setHost(host);
        params.setPort(port);
        ModbusMaster master = new ModbusFactory().createTcpMaster(params, true);
        master.init();
        return master;
    }
}
