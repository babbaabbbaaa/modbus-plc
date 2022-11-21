package com.demo.domain.stamping;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.demo.config.PatternConfig;
import com.demo.config.PatternConfigRepository;
import com.demo.enums.BarcodeDuplicateEnum;
import com.demo.enums.GeneralFunctionEnum;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Profile("stamping")
public class StampingFetchService implements IDataFetchService {

    private final StampingRepository stampingRepository;
    private final PatternConfigRepository patternConfigRepository;
    private final int slaveId;
    private final BaseLocator<Number> dataReadyLocator;

    private ModbusMaster modbusMaster;

    public StampingFetchService(StampingRepository stampingRepository,
                                PatternConfigRepository patternConfigRepository,
                                @Value("${modbus.slave_id}") int slaveId) {
        this.stampingRepository = stampingRepository;
        this.patternConfigRepository = patternConfigRepository;
        this.slaveId = slaveId;
        dataReadyLocator = BaseLocator.holdingRegister(slaveId, 7001, DataType.TWO_BYTE_INT_SIGNED);
    }

    @Override
    public synchronized void process() throws ModbusTransportException, ErrorResponseException {
        if (isDataNotReady()) {
            return;
        }
        Stamping stamping = getData();
        if (stamping.isValid()) {
            checkDup(stamping, stamping.getProductTypeId());
            stampingRepository.save(stamping);
        }
        setShortValue(slaveId, 7001, (short) 0);//mark the data read done
    }

    public Stamping getData() throws ModbusTransportException {
        if (null == modbusMaster) {
            return Stamping.EMPTY_PLC_DATA;
        }
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
        Stamping stamping = new Stamping();
        stamping.setDataSource("自动线");
        stamping.setLogTime(LocalDateTime.now());
        stamping.setValid(valid);
        //7000 ON/OFF SIGNAL
        //7001 Data Ready
        stamping.setReady(data[1]);
        //7002 DUP SIGNAL
        stamping.setDuplicate(data[2]);
        //7003 Product Type ID
        short productTypeId = data[3];
        PatternConfig patternConfig = patternConfigRepository.findByProductTypeId(productTypeId);
        int ratio = 2000;
        if (null != patternConfig) {
            ratio = patternConfig.getRatio() == 0 ? 2000 : patternConfig.getRatio();
        }
        stamping.setProductTypeId(productTypeId);
        stamping.setRatio(ratio);
        //7005 RESERVED
        //7006 GENERAL
        stamping.setGeneralFunc(GeneralFunctionEnum.mapLongDefinition(data[6]));
        //7007 HEIGHT
        stamping.setHeightFunc(GeneralFunctionEnum.mapLongDefinition(data[7]));
        //7008 FLAG
        stamping.setFlagFunc(GeneralFunctionEnum.mapLongDefinition(data[8]));
        //7009 SCAN
        stamping.setScanFunc(GeneralFunctionEnum.mapLongDefinition(data[9]));
        //7010 Typhoon
        stamping.setTyphoonFunc(GeneralFunctionEnum.mapLongDefinition(data[10]));
        //7011 Slot
        stamping.setSlotDepthFunc(GeneralFunctionEnum.mapLongDefinition(data[11]));
        //7012 Spin
        stamping.setSpinCheckFunc(GeneralFunctionEnum.mapLongDefinition(data[12]));
        //7013 Weld
        stamping.setWeldFunc(GeneralFunctionEnum.mapLongDefinition(data[13]));
        //7014 ~ 7015 Test 1
        stamping.setHeightMeasure1(getFloatValue(data[14], data[15], ratio));
        //7016 ~ 7017 Test 2
        stamping.setHeightMeasure2(getFloatValue(data[16], data[17], ratio));
        //7018 ~ 7019 Test 3
        stamping.setHeightMeasure3(getFloatValue(data[18], data[19], ratio));
        //7020 ~ 7021 Test 4
        stamping.setHeightMeasure4(getFloatValue(data[20], data[21], ratio));
        //7022 ~ 7023 Test 5
        stamping.setHeightMeasure5(getFloatValue(data[22], data[23], ratio));
        //7024 ~ 7025 Test 6
        stamping.setHeightMeasure6(getFloatValue(data[24], data[25], ratio));
        //7026 ~ 7027 Test 7
        stamping.setHeightMeasure7(getFloatValue(data[26], data[27], ratio));
        //7028 ~ 7029 Test 8
        stamping.setHeightMeasure8(getFloatValue(data[28], data[29], ratio));
        //7030 ~ 7031 Test 9
        stamping.setHeightMeasure9(getFloatValue(data[30], data[31], ratio));
        //7032 ~ 7033 Test 10
        stamping.setHeightMeasure10(getFloatValue(data[32], data[33], ratio));
        //7034 ~ 7035 Test 11
        stamping.setHeightMeasure11(getFloatValue(data[34], data[35], ratio));
        //7036 ~ 7037 Test 12
        stamping.setHeightMeasure12(getFloatValue(data[36], data[37], ratio));
        //7038 ~ 7039 Test 13
        stamping.setHeightMeasure13(getFloatValue(data[38], data[39], ratio));
        //7040 ~ 7041 Test 14
        stamping.setHeightMeasure14(getFloatValue(data[40], data[41], ratio));
        //7042 ~ 7043 Test 15
        stamping.setHeightMeasure15(getFloatValue(data[42], data[43], ratio));
        //7044 ~ 7045 Test 16
        stamping.setHeightMeasure16(getFloatValue(data[44], data[45], ratio));
        //7046 ~ 7047 Test 17
        stamping.setHeightMeasure17(getFloatValue(data[46], data[47], ratio));
        //7048 ~ 7049 Test 18
        stamping.setHeightMeasure18(getFloatValue(data[48], data[49], ratio));
        //7050 ~ 7051 Test 19
        stamping.setHeightMeasure19(getFloatValue(data[50], data[51], ratio));
        //7052 ~ 7053 Test 20
        stamping.setHeightMeasure20(getFloatValue(data[52], data[53], ratio));
        //7054 ~ 7153 Barcode Data
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

        //7154 ~ 7157 Tray Identity Code
        request = new ReadHoldingRegistersRequest(slaveId, 7154, 4);
        response = (ReadHoldingRegistersResponse) modbusMaster.send(request);
        stamping.setTrayIdentityCode(getStringValue(response.getShortData(), 8));

        String barcodeData = new String(byteBuffer.array(), StandardCharsets.UTF_8);
        stamping.setBarcodeData(barcodeData.trim());
        if (null != patternConfig) {
            stamping.setBarcode(stamping.getBarcode(stamping.getBarcodeData(), patternConfig.getStart(), patternConfig.getEnd()));
            stamping.setBarcodeGrade(stamping.getBarcodeGrade(stamping.getBarcodeData(), patternConfig.getQualifiedStart(), patternConfig.getQualifiedEnd()));
        }
        stamping.setQualified(isQualified(Arrays.copyOfRange(data, 6, 13)));
        stamping.autoInspect();
        return stamping;
    }

    private boolean isDataNotReady() throws ModbusTransportException, ErrorResponseException {
        return modbusMaster.getValue(dataReadyLocator).shortValue() != 1;
    }


    private void setShortValue(int slaveId, int offset, short value) throws ModbusTransportException {
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
        modbusMaster.send(request);
    }

    private String getStringValue(short[] data, int capacity) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        for (short datum : data) {
            if (datum > 0) {
                shortBuffer.put(datum);
            }
        }
        return new String(byteBuffer.array(), StandardCharsets.UTF_8).trim();
    }

    /**
     * 通规等功能结果
     * @param func PLC寄存器的值，参考 {@link com.demo.enums.GeneralFunctionEnum}
     */
    private boolean isQualified(short... func) {
        for (short f : func) {
            if (f == 2) {
                return false;
            }
        }
        return true;
    }

    private void checkDup(Stamping stamping, short productTypeId) throws ModbusTransportException {
        if (!StringUtils.hasText(stamping.getBarcode()) || "error".equalsIgnoreCase(stamping.getBarcode())) {
            return;
        }
        List<Stamping> plcList = stampingRepository.getDataByBarcode(stamping.getBarcode(), productTypeId);
        if (!CollectionUtils.isEmpty(plcList)) {
            plcList.forEach(v -> v.setDuplicated(BarcodeDuplicateEnum.DUP));
            stamping.setDuplicated(BarcodeDuplicateEnum.DUP);
            stamping.setQualified(false);
            stamping.setAutoInspectResult("设备NG");
            stampingRepository.saveAll(plcList);
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
