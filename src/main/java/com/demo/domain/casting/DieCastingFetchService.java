package com.demo.domain.casting;

import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigRepository;
import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.plc.IDataFetchService;
import com.demo.utility.JsonUtil;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.WriteRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;


@Slf4j
@Service
@Profile("casting")
public class DieCastingFetchService implements IDataFetchService {

    private final DieCastingRepository dieCastingRepository;
    private final SubDieCastingRepository subDieCastingRepository;
    private final PatternConfigRepository patternConfigRepository;
    private final ModbusMaster modbusMaster;
    private final BaseLocator<Number> dataReadyLocator;

    public DieCastingFetchService(DieCastingRepository dieCastingRepository,
                                  SubDieCastingRepository subDieCastingRepository,
                                  PatternConfigRepository patternConfigRepository,
                                  @Autowired(required = false) ModbusMaster modbusMaster) {
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
        log.info(JsonUtil.writeAsString(dieCasting));
        checkDup(dieCasting);
        dieCastingRepository.save(dieCasting);
        setDataReadDone();//mark the data read done
    }

    @Override
    public DieCasting getData() throws ModbusTransportException {
        if (null == modbusMaster) {
            return DieCasting.EMPTY_DIE_CASTING;
        }
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(1, 7000, 53);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] dataArrays = response.getShortData();
        request = new ReadHoldingRegistersRequest(1, 7054, 100);
        response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] barcodeArrays = response.getShortData();
        DieCasting dieCasting = new DieCasting(dataArrays);

        PatternConfig patternConfig = patternConfigRepository.findByProductTypeId(dieCasting.getProductTypeId());
        dieCasting.initSubDieCasting(dataArrays, barcodeArrays, "A", patternConfig);
        dieCasting.initSubDieCasting(dataArrays, barcodeArrays, "B", patternConfig);
        return dieCasting;
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

    public void checkDup(DieCasting dieCasting) throws ModbusTransportException, ErrorResponseException {
        for (SubDieCasting subDieCasting : dieCasting.getSubDieCastings()) {
            if (!StringUtils.hasText(subDieCasting.getBarcodeData()) || "ERROR".equalsIgnoreCase(subDieCasting.getBarcodeData())) {
                continue;
            }
            log.info("checking duplicate code");
            List<SubDieCasting> subDieCastings = subDieCastingRepository
                    .findSubDieCastingByBarcodeAndProduct(subDieCasting.getBarcodeData(), dieCasting.getProductTypeId());
            if (!CollectionUtils.isEmpty(subDieCastings)) {
                for (SubDieCasting sub : subDieCastings) {
                    sub.setDuplicated(BarcodeDuplicateEnum.DUP);
                    sub.autoInspectionResult();
                }
                subDieCastingRepository.saveAll(subDieCastings);
                modbusMaster.setValue(BaseLocator.holdingRegister(1, 7002, DataType.TWO_BYTE_INT_SIGNED), 1);
                subDieCasting.setDuplicated(BarcodeDuplicateEnum.DUP);
                subDieCasting.autoInspectionResult();
            }
        }

    }
}
