package com.demo.domain.casting;

import com.demo.config.PatternConfigRepository;
import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.plc.IDataFetchService;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.WriteRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
@Profile("casting")
public class DieCastingFetchService implements IDataFetchService {

    private final DieCastingRepository dieCastingRepository;
    private final SubDieCastingRepository subDieCastingRepository;
    private final PatternConfigRepository patternConfigRepository;
    private final ModbusMaster modbusMaster;
    private final BaseLocator<Number> dataReadyLocator;

    public DieCastingFetchService(DieCastingRepository dieCastingRepository,
                                  SubDieCastingRepository subDieCastingRepository, PatternConfigRepository patternConfigRepository, @Autowired(required = false) ModbusMaster modbusMaster) {
        this.dieCastingRepository = dieCastingRepository;
        this.subDieCastingRepository = subDieCastingRepository;
        this.patternConfigRepository = patternConfigRepository;
        this.modbusMaster = modbusMaster;
        this.dataReadyLocator = BaseLocator.holdingRegister(1, 7001, DataType.TWO_BYTE_INT_SIGNED);
    }

    @Override
    public void process() throws ModbusTransportException, ErrorResponseException {
        if (isDataNotReady()) {
            return;
        }
        DieCasting dieCasting = getData();
        if (dieCasting.getSubDieCastings().stream().anyMatch(v -> v.getDuplicated() == BarcodeDuplicateEnum.DUP)) {
            modbusMaster.setValue(BaseLocator.holdingRegister(1, 7002, DataType.TWO_BYTE_INT_SIGNED), 1);
        }
        dieCastingRepository.save(getData());
        setDataReadDone();//mark the data read done
    }

    @Override
    public DieCasting getData() throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(1, 7000, 53);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] dataArrays = response.getShortData();
        request = new ReadHoldingRegistersRequest(1, 7054, 100);
        response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] barcodeArrays = response.getShortData();
        return new DieCasting(dataArrays, barcodeArrays, patternConfigRepository, subDieCastingRepository);
    }

    @Override
    public ModbusMaster getModbusMaster() {
        return this.modbusMaster;
    }

    private boolean isDataNotReady() throws ModbusTransportException, ErrorResponseException {
        return modbusMaster.getValue(dataReadyLocator).shortValue() != 1;
    }

    private void setDataReadDone() throws ModbusTransportException {
        WriteRegisterRequest request = new WriteRegisterRequest(1, 7001, 0);
        modbusMaster.send(request);
    }

}
