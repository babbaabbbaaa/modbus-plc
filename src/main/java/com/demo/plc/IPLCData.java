package com.demo.plc;

import com.demo.enums.BarcodeDuplicateEnum;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public interface IPLCData {

    default void setIndex(int index) {}

    BarcodeDuplicateEnum getDuplicated();

    default float getFloatValue(short height, short low) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putShort(low);
        buffer.putShort(height);
        return buffer.getFloat(0);
    }

    default int getIntValue(short height, short low) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putShort(low);
        buffer.putShort(height);
        return buffer.getInt(0);
    }

    default String getBarcodeData(short[] data, int offset, int length) {
        //calculate the byte length according to the string length, one character persists four bytes.
        ByteBuffer byteBuffer = ByteBuffer.allocate(length * 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(Arrays.copyOfRange(data, offset, offset + length));
        return new String(byteBuffer.array(), StandardCharsets.UTF_8).trim();
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

    default String getBarcodeGrade(String barcodeData, int qualifyStart, int qualifyEnd) {
        if ("ERROR".equalsIgnoreCase(barcodeData)) {
            return "";
        }
        if (StringUtils.hasText(barcodeData) && barcodeData.length() >= qualifyEnd) {
            return barcodeData.substring(qualifyStart, qualifyEnd).toUpperCase();
        }
        return "";
    }


}
