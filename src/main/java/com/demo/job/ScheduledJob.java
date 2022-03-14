package com.demo.job;


import com.demo.plc.IDataFetchService;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
@Slf4j
@Profile("default")
public class ScheduledJob {


    private final IDataFetchService fetchDataService;
    private final BaseLocator<Number> locator;

    private ModbusMaster modbusMaster;

    public ScheduledJob(IDataFetchService fetchDataService,
                        @Value("${modbus.slave_id}") int slaveId) {
        this.fetchDataService = fetchDataService;
        locator = BaseLocator.holdingRegister(slaveId, 7000, DataType.TWO_BYTE_INT_SIGNED);
    }

    @Scheduled(fixedRate = 500L)
    public void signal() {
        try {
            short value = modbusMaster.getValue(locator).shortValue();
            modbusMaster.setValue(locator, value == 1 ? 0 : 1);
        } catch (ModbusTransportException | ErrorResponseException e) {
            log.error("", e);
        }
    }

    @Scheduled(fixedRate = 500L)
    public void read() {
        try {
            fetchDataService.process();
        } catch (ModbusTransportException | ErrorResponseException e) {
            log.error("read data failed: ", e);
        }
    }

    @Autowired(required = false)
    public void setModbusMaster(ModbusMaster modbusMaster) {
        this.modbusMaster = modbusMaster;
    }
}
