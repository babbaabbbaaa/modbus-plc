package com.demo.domain;


import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigRepository;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.WriteRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.TableRowHeightRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        plcRepository.save(plcData);
        setShortValue(slaveId, 7001, (short) 0);//mark the data read done
    }

    public PLCData createPLC() {
        PLCData plcData = new PLCData();
        plcData.setDataSource("自动线");
        plcData.setLogTime(LocalDateTime.now());
        try {
            int divides = getShortValue(slaveId, 7004);
            //measurement data
            plcData.setHeightMeasure1(getFloatValue(slaveId, 7014, divides));
            plcData.setHeightMeasure2(getFloatValue(slaveId, 7016, divides));
            plcData.setHeightMeasure3(getFloatValue(slaveId, 7018, divides));
            plcData.setHeightMeasure4(getFloatValue(slaveId, 7020, divides));
            plcData.setHeightMeasure5(getFloatValue(slaveId, 7022, divides));
            plcData.setHeightMeasure6(getFloatValue(slaveId, 7024, divides));
            plcData.setHeightMeasure7(getFloatValue(slaveId, 7026, divides));
            plcData.setHeightMeasure8(getFloatValue(slaveId, 7028, divides));
            plcData.setHeightMeasure9(getFloatValue(slaveId, 7030, divides));
            plcData.setHeightMeasure10(getFloatValue(slaveId, 7032, divides));
            plcData.setHeightMeasure11(getFloatValue(slaveId, 7034, divides));
            plcData.setHeightMeasure12(getFloatValue(slaveId, 7036, divides));
            plcData.setHeightMeasure13(getFloatValue(slaveId, 7038, divides));
            plcData.setHeightMeasure14(getFloatValue(slaveId, 7040, divides));
            plcData.setHeightMeasure15(getFloatValue(slaveId, 7042, divides));
            plcData.setHeightMeasure16(getFloatValue(slaveId, 7044, divides));
            plcData.setHeightMeasure17(getFloatValue(slaveId, 7046, divides));
            plcData.setHeightMeasure18(getFloatValue(slaveId, 7048, divides));
            plcData.setHeightMeasure19(getFloatValue(slaveId, 7050, divides));
            plcData.setHeightMeasure20(getFloatValue(slaveId, 7052, divides));

            //product type
            short productTypeId = getShortValue(slaveId, 7003);
            PatternConfig patternConfig = patternConfigRepository.findByProductTypeId(productTypeId);
            plcData.setProductTypeId(productTypeId);


            //functional check
            plcData.setGeneralFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7006)));
            plcData.setHeightFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7007)));
            plcData.setFlagFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7008)));
            plcData.setScanFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7009)));
            plcData.setTyphoonFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7010)));
            plcData.setSlotDepthFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7011)));
            plcData.setSpinCheckFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7012)));
            plcData.setWeldFunc(GeneralFunctionEnum.mapDefinition(getShortValue(slaveId, 7013)));

            plcData.setBarcodeData(getBarcodeData(slaveId, 7054));
            plcData.setBarcode(getBarcode(plcData.getBarcodeData(), patternConfig.getStart(), patternConfig.getEnd()));
            checkDup(plcData);
        } catch (ModbusTransportException e) {
            log.error("cannot communicate with plc: ", e);
        }
        return plcData;
    }

    private float getFloatValue(int slaveId, int offset, int divides) throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, offset, 2);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] data = response.getShortData();
        int d = Integer.parseInt(fillBinary(data[1]) + fillBinary(data[0]), 2);
        return ((float) d) / divides;
    }

    private short getShortValue(int slaveId, int offset) throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, offset, 1);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        short[] data = response.getShortData();
        return data[0];
    }

    private void getData(int slaveId) throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, 0, 156);
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
            //7002 DUP SIGNAL
            //7003 Product Type ID
            short productTypeId = data[3];
            PatternConfig patternConfig = patternConfigRepository.findByProductTypeId(productTypeId);
            plcData.setProductTypeId(productTypeId);
            //7004 Float Ratio
            short ratio = data[4];
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
            int size = 0;
            for (int i = 54; i < 155; i ++) {
                if (data[i] > 0) {
                    size ++;
                }
            }
            byte[] barcodeBytes = new byte[size * 2];
            for (int i = 54, j = 0; i < 155; i ++) {
                if (data[i] > 0) {
                    short s = data[i];

                }
            }
        }
    }

    private float getFloatValue(short height, short low, int ratio) {
        int d = Integer.parseInt(fillBinary(height) + fillBinary(low), 2);
        return ((float) d) / ratio;
    }

    private void setShortValue(int slaveId, int offset, short value) throws ModbusTransportException {
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
        modbusMaster.send(request);
    }

    private String getBarcodeData(int slaveId, int offset) throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, offset, 100);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        byte[] data = response.getData();

        int size = 0;
        for (byte d : data) {
            if (d > 0) {
                size++;
            }
        }
        byte[] ready2Use = new byte[size];
        for (int i = 0; i < size; ) {
            ready2Use[i] = data[i + 1];
            ready2Use[i + 1] = data[i];
            i += 2;
        }
        return new String(ready2Use, StandardCharsets.UTF_8);
    }

    private String getBarcode(String barcodeData, int start, int end) {
        if (Objects.equals("error", barcodeData)) {
            return barcodeData;
        }
        if (StringUtils.hasText(barcodeData) && barcodeData.length() > end) {
            return barcodeData.substring(start, end);
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
