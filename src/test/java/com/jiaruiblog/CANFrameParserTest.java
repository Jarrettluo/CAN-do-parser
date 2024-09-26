package com.jiaruiblog;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CANFrameParserTest {

    @Test
    public void testExtractMessage() {
        // CAN 帧数据
        CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "275d602702000000");

        // dbc数据; 通过dbc解析器得到
        Map<String, DbcMessage> dbcMessageMap = new HashMap<String, DbcMessage>();

        // 初始化解析器
        CANFrameParser canFrameParser = new CANFrameParser(dbcMessageMap);
        // 对can数据进行解析
        Map<String, Double> physicalValueMap = canFrameParser.extractMessage(canFrame);
        System.out.println(physicalValueMap);
    }

    @Test
    public void extractSignal() {

    }

    @Test
    public void testExtractSignal() {
        CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "275d602702000000");

//        CanFrameParser parser = new CanFrameParser();
        int startBit = 32;
        int length = 32;
        boolean isSigned = true;
        boolean isLittleEndian = true;

        double signal = CANFrameParser.extractSignal(canFrame.getMsgData(), startBit, length, isSigned, isLittleEndian, "1", "0");
        System.out.println(signal);


    }

    @Test
    public void testExtractSignal2() {
        CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "88d65e2763f784c0");
        // 11000000100001001111011101100011
//        CanFrameParser parser = new CanFrameParser();
        int startBit = 32;
        int length = 32;
        boolean isSigned = true;
        boolean isLittleEndian = true;
        System.out.println(canFrame);
        double signal = CANFrameParser.extractSignal(canFrame.getMsgData(), startBit, length, isSigned, isLittleEndian,"1", "0");
        System.out.println(signal);
    }
}