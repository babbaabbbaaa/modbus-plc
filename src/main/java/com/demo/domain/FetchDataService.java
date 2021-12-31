package com.demo.domain;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigRepository;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusTransportException;
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
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class FetchDataService {

    private final PLCRepository plcRepository;
    private final PatternConfigRepository patternConfigRepository;
    private final int slaveId;

    private ModbusMaster modbusMaster;

    public FetchDataService(PLCRepository plcRepository,
                            PatternConfigRepository patternConfigRepository,
                            @Value("${modbus.slave_id}") int slaveId) {
        this.plcRepository = plcRepository;
        this.patternConfigRepository = patternConfigRepository;
        this.slaveId = slaveId;
    }

    public synchronized void process() throws ModbusTransportException {
        if (getShortValue(slaveId, 7001) != 1) {
            return;
        }
        PLCData plcData = createPLC();
        checkDup(plcData);
        plcRepository.save(plcData);
        setShortValue(slaveId, 7001, (short) 0);//mark the data read done
    }

    public PLCData createPLC() {
        PLCData plcData = new PLCData();
        plcData.setDataSource("自动线");
        plcData.setLogTime(LocalDateTime.now());
        try {
            //product type
            short productTypeId = getShortValue(slaveId, 7003);
            PatternConfig patternConfig = patternConfigRepository.findByProductTypeId(productTypeId);
            plcData.setProductTypeId(productTypeId);

            plcData.setBarcodeData(getBarcodeData(slaveId));
            int ratio = 2000;
            if (null != patternConfig) {
                ratio = patternConfig.getRatio() == 0 ? (2000) : patternConfig.getRatio();
                plcData.setRatio(patternConfig.getRatio());
                plcData.setBarcode(getBarcode(plcData.getBarcodeData(), patternConfig.getStart(), patternConfig.getEnd()));
                plcData.setQualify(getQualify(plcData.getBarcodeData(), patternConfig.getQualifiedStart(), patternConfig.getQualifiedEnd()));
            }
            //measurement data
            plcData.setHeightMeasure1(getFloatValue(slaveId, 7014, ratio));
            plcData.setHeightMeasure2(getFloatValue(slaveId, 7016, ratio));
            plcData.setHeightMeasure3(getFloatValue(slaveId, 7018, ratio));
            plcData.setHeightMeasure4(getFloatValue(slaveId, 7020, ratio));
            plcData.setHeightMeasure5(getFloatValue(slaveId, 7022, ratio));
            plcData.setHeightMeasure6(getFloatValue(slaveId, 7024, ratio));
            plcData.setHeightMeasure7(getFloatValue(slaveId, 7026, ratio));
            plcData.setHeightMeasure8(getFloatValue(slaveId, 7028, ratio));
            plcData.setHeightMeasure9(getFloatValue(slaveId, 7030, ratio));
            plcData.setHeightMeasure10(getFloatValue(slaveId, 7032, ratio));
            plcData.setHeightMeasure11(getFloatValue(slaveId, 7034, ratio));
            plcData.setHeightMeasure12(getFloatValue(slaveId, 7036, ratio));
            plcData.setHeightMeasure13(getFloatValue(slaveId, 7038, ratio));
            plcData.setHeightMeasure14(getFloatValue(slaveId, 7040, ratio));
            plcData.setHeightMeasure15(getFloatValue(slaveId, 7042, ratio));
            plcData.setHeightMeasure16(getFloatValue(slaveId, 7044, ratio));
            plcData.setHeightMeasure17(getFloatValue(slaveId, 7046, ratio));
            plcData.setHeightMeasure18(getFloatValue(slaveId, 7048, ratio));
            plcData.setHeightMeasure19(getFloatValue(slaveId, 7050, ratio));
            plcData.setHeightMeasure20(getFloatValue(slaveId, 7052, ratio));

            plcData.setDuplicate(getShortValue(slaveId, 7002));
            plcData.setReady(getShortValue(slaveId, 7001));

            //functional check
            plcData.setGeneralFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7006)));
            plcData.setHeightFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7007)));
            plcData.setFlagFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7008)));
            plcData.setScanFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7009)));
            plcData.setTyphoonFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7010)));
            plcData.setSlotDepthFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7011)));
            plcData.setSpinCheckFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7012)));
            plcData.setWeldFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7013)));


        } catch (ModbusTransportException e) {
            log.error("cannot communicate with plc: ", e);
        }
        return plcData;
    }

    private float getFloatValue(int slaveId, int offset, int ratio) throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, offset, 2);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] data = response.getShortData();
        int d = Integer.parseInt(fillBinary(data[1]) + fillBinary(data[0]), 2);
        return ((float) d) / ratio;
    }

    private short getShortValue(int slaveId, int offset) throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, offset, 1);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] data = response.getShortData();
        return data[0];
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
        if (valid) {
            //7000 ON/OFF SIGNAL
            //7001 Data Ready
            plcData.setReady(data[1]);
            //7002 DUP SIGNAL
            plcData.setDuplicate(data[2]);
            //7003 Product Type ID
            short productTypeId = data[3];
            PatternConfig patternConfig = patternConfigRepository.findByProductTypeId(productTypeId);
            plcData.setProductTypeId(productTypeId);
            //7004 Float Ratio
            short ratio = data[4];
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
            for (int i = 54; i < length; i ++) {
                if (data[i] > 0) {
                    shortBuffer.put(data[i]);
                }
            }
            if (data[length - 1] > 0) {
                request = new ReadHoldingRegistersRequest(slaveId, 7125, 28);
                response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
                short[] add = response.getShortData();
                if (add[0] > 0) {
                    for (int i = 0; i < length; i ++) {
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
            }
        }
        return plcData;
    }

    private float getFloatValue(short height, short low, int ratio) {
        int d = Integer.parseInt(fillBinary(height) + fillBinary(low), 2);
        return ((float) d) / ratio;
    }

    private void setShortValue(int slaveId, int offset, short value) throws ModbusTransportException {
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
        modbusMaster.send(request);
    }

    private String getBarcodeData(int slaveId) throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, 7054, 100);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] data = response.getShortData();
        ByteBuffer byteBuffer = ByteBuffer.allocate(200);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        for (short d : data) {
            if (d > 0) {
                shortBuffer.put(d);
            }
        }
        String barcode = new String(Arrays.copyOfRange(byteBuffer.array(), 0, shortBuffer.position() * 2), StandardCharsets.UTF_8);
        return barcode.trim();
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
        if (StringUtils.hasText(barcodeData) && barcodeData.length() > qualifyEnd) {
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

    private String fillBinary(short value) {
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(value & 0xFFFF));
        while (binary.length() < 16) {
            binary.insert(0, "0");
        }
        return binary.toString();
    }

    @Autowired(required = false)
    public void setModbusMaster(ModbusMaster modbusMaster) {
        this.modbusMaster = modbusMaster;
    }

    public ModbusMaster getModbusMaster() {
        return modbusMaster;
    }
}
