package com.demo.plc;

import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;

public interface IDataFetchService {

    void process() throws ModbusTransportException, ErrorResponseException;
    IPLCData getData() throws ModbusTransportException;
    ModbusMaster getModbusMaster();

    default float getFloatValue(short height, short low, int ratio) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putShort(low);
        buffer.putShort(height);
        int data = buffer.getInt(0);
        return ((float) data) / ratio;
    }

    default String getBarcode(String barcodeData, int start, int end) {
        if (!StringUtils.hasText(barcodeData) || "error".equalsIgnoreCase(barcodeData)
                || barcodeData.length() < end) {
            return "";
        }
        if (StringUtils.hasText(barcodeData) && barcodeData.length() > end) {
            return barcodeData.substring(start, end);
        }
        return "";
    }

}
