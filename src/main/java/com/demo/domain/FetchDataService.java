package com.demo.domain;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigRepository;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FetchDataService {

    private final PLCRepository plcRepository;
    private final PatternConfigRepository patternConfigRepository;
    private final int slaveId;
    private final BaseLocator<Number> dataReadyLocator;

    private ModbusMaster modbusMaster;

    public FetchDataService(PLCRepository plcRepository,
                            PatternConfigRepository patternConfigRepository,
                            @Value("${modbus.slave_id}") int slaveId) {
        this.plcRepository = plcRepository;
        this.patternConfigRepository = patternConfigRepository;
        this.slaveId = slaveId;
        dataReadyLocator = BaseLocator.holdingRegister(slaveId, 7001, DataType.TWO_BYTE_INT_SIGNED);
    }

    public synchronized void process() throws ModbusTransportException, ErrorResponseException {
        if (isDataReady()) {
            return;
        }
        PLCData plcData = getData();
        if (plcData.isValid()) {
            checkDup(plcData);
            plcRepository.save(plcData);
        }
        setShortValue(slaveId, 7001, (short) 0);//mark the data read done
    }

    public PLCData getData() throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, 7000, 125);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] data = response.getShortData();
        boolean valid = false;
        for (int i = 3; i < data.length; i++) {
            if (data[i] > 0) {
                valid = true;
                break;
            }
        }
        PLCData plcData = new PLCData();
        plcData.setDataSource("自动线");
        plcData.setLogTime(LocalDateTime.now());
        plcData.setValid(valid);
        //7000 ON/OFF SIGNAL
        //7001 Data Ready
        plcData.setReady(data[1]);
        //7002 DUP SIGNAL
        plcData.setDuplicate(data[2]);
        //7003 Product Type ID
        short productTypeId = data[3];
        PatternConfig patternConfig = patternConfigRepository.findByProductTypeId(productTypeId);
        int ratio = 2000;
        if (null != patternConfig) {
            ratio = patternConfig.getRatio() == 0 ? 2000 : patternConfig.getRatio();
        }
        plcData.setProductTypeId(productTypeId);
        plcData.setRatio(ratio);
        //7005 RESERVED
        //7006 GENERAL
        plcData.setGeneralFunc(GeneralFunctionEnum.mapDefinition(data[6]));
        //7007 HEIGHT
        plcData.setHeightFunc(GeneralFunctionEnum.mapDefinition(data[7]));
        //7008 FLAG
        plcData.setFlagFunc(GeneralFunctionEnum.mapDefinition(data[8]));
        //7009 SCAN
        plcData.setScanFunc(GeneralFunctionEnum.mapDefinition(data[9]));
        //7010 Typhoon
        plcData.setTyphoonFunc(GeneralFunctionEnum.mapDefinition(data[10]));
        //7011 Slot
        plcData.setSlotDepthFunc(GeneralFunctionEnum.mapDefinition(data[11]));
        //7012 Spin
        plcData.setSpinCheckFunc(GeneralFunctionEnum.mapDefinition(data[12]));
        //7013 Weld
        plcData.setWeldFunc(GeneralFunctionEnum.mapDefinition(data[13]));
        //7014 ~ 7015 Test 1
        plcData.setHeightMeasure1(getFloatValue(data[14], data[15], ratio));
        //7016 ~ 7017 Test 2
        plcData.setHeightMeasure2(getFloatValue(data[16], data[17], ratio));
        //7018 ~ 7019 Test 3
        plcData.setHeightMeasure3(getFloatValue(data[18], data[19], ratio));
        //7020 ~ 7021 Test 4
        plcData.setHeightMeasure4(getFloatValue(data[20], data[21], ratio));
        //7022 ~ 7023 Test 5
        plcData.setHeightMeasure5(getFloatValue(data[22], data[23], ratio));
        //7024 ~ 7025 Test 6
        plcData.setHeightMeasure6(getFloatValue(data[24], data[25], ratio));
        //7026 ~ 7027 Test 7
        plcData.setHeightMeasure7(getFloatValue(data[26], data[27], ratio));
        //7028 ~ 7029 Test 8
        plcData.setHeightMeasure8(getFloatValue(data[28], data[29], ratio));
        //7030 ~ 7031 Test 9
        plcData.setHeightMeasure9(getFloatValue(data[30], data[31], ratio));
        //7032 ~ 7033 Test 10
        plcData.setHeightMeasure10(getFloatValue(data[32], data[33], ratio));
        //7034 ~ 7035 Test 11
        plcData.setHeightMeasure11(getFloatValue(data[34], data[35], ratio));
        //7036 ~ 7037 Test 12
        plcData.setHeightMeasure12(getFloatValue(data[36], data[37], ratio));
        //7038 ~ 7039 Test 13
        plcData.setHeightMeasure13(getFloatValue(data[38], data[39], ratio));
        //7040 ~ 7041 Test 14
        plcData.setHeightMeasure14(getFloatValue(data[40], data[41], ratio));
        //7042 ~ 7043 Test 15
        plcData.setHeightMeasure15(getFloatValue(data[42], data[43], ratio));
        //7044 ~ 7045 Test 16
        plcData.setHeightMeasure16(getFloatValue(data[44], data[45], ratio));
        //7046 ~ 7047 Test 17
        plcData.setHeightMeasure17(getFloatValue(data[46], data[47], ratio));
        //7048 ~ 7049 Test 18
        plcData.setHeightMeasure18(getFloatValue(data[48], data[49], ratio));
        //7050 ~ 7051 Test 19
        plcData.setHeightMeasure19(getFloatValue(data[50], data[51], ratio));
        //7052 ~ 7053 Test 20
        plcData.setHeightMeasure20(getFloatValue(data[52], data[53], ratio));
        //7054 ~ 7154 Barcode Data
        ByteBuffer byteBuffer = ByteBuffer.allocate(200);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        int length = data.length;
        for (int i = 54; i < length; i++) {
            if (data[i] > 0) {
                shortBuffer.put(data[i]);
            }
        }
        if (data[length - 1] > 0) {
            request = new ReadHoldingRegistersRequest(slaveId, 7125, 28);
            response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
            short[] add = response.getShortData();
            if (add[0] > 0) {
                for (int i = 0; i < length; i++) {
                    if (add[i] > 0) {
                        shortBuffer.put(data[i]);
                    }
                }
            }
        }
        String barcodeData = new String(byteBuffer.array(), StandardCharsets.UTF_8);
        plcData.setBarcodeData(barcodeData.trim());
        if (null != patternConfig) {
            plcData.setBarcode(getBarcode(plcData.getBarcodeData(), patternConfig.getStart(), patternConfig.getEnd()));
            plcData.setQualified(getQualify(plcData.getBarcodeData(), patternConfig.getQualifiedStart(), patternConfig.getQualifiedEnd()));
        }
        return plcData;
    }

    private boolean isDataReady() throws ModbusTransportException, ErrorResponseException {
        return modbusMaster.getValue(dataReadyLocator).shortValue() == 1;
    }

    private float getFloatValue(short height, short low, int ratio) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putShort(low);
        buffer.putShort(height);
        int data = buffer.getInt(0);
        return ((float) data) / ratio;
    }

    private void setShortValue(int slaveId, int offset, short value) throws ModbusTransportException {
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
        modbusMaster.send(request);
    }


    private String getBarcode(String barcodeData, int start, int end) {
        if (!StringUtils.hasText(barcodeData) || "error".equalsIgnoreCase(barcodeData)
                || barcodeData.length() < end) {
            return "";
        }
        if (StringUtils.hasText(barcodeData) && barcodeData.length() > end) {
            return barcodeData.substring(start, end);
        }
        return "";
    }

    private String getQualify(String barcodeData, int qualifyStart, int qualifyEnd) {
        if (StringUtils.hasText(barcodeData) && barcodeData.length() >= qualifyEnd) {
            return barcodeData.substring(qualifyStart, qualifyEnd);
        }
        return "";
    }

    private void checkDup(PLCData plcData) throws ModbusTransportException {
        if (!StringUtils.hasText(plcData.getBarcode()) || "error".equalsIgnoreCase(plcData.getBarcode())) {
            return;
        }
        List<PLCData> plcList = plcRepository.getDataByBarcode(plcData.getBarcode());
        if (!CollectionUtils.isEmpty(plcList)) {
            plcList.forEach(v -> v.setDuplicated(BarcodeDuplicateEnum.DUP));
            plcData.setDuplicated(BarcodeDuplicateEnum.DUP);
            plcRepository.saveAll(plcList);
            setShortValue(slaveId, 7002, (short) 1);
        }
    }

    @Autowired(required = false)
    public void setModbusMaster(ModbusMaster modbusMaster) {
        this.modbusMaster = modbusMaster;
    }

    public ModbusMaster getModbusMaster() {
        return modbusMaster;
    }
}
