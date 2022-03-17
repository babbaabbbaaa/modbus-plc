package com.demo.plc;

import java.nio.ByteBuffer;

import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;

public interface IDataFetchService {

    void process() throws ModbusTransportException, ErrorResponseException;
    IPLCData getData() throws ModbusTransportException;
    ModbusMaster getModbusMaster();

    default float getFloatValue(short height, short low, int ratio) {
        int data = getIntValue(height, low);
        return ((float) data) / ratio;
    }

    default int getIntValue(short height, short low) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putShort(low);
        buffer.putShort(height);
        return buffer.getInt(0);
    }

}
