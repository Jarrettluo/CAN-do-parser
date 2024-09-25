package com.jiaruiblog;

import org.junit.Test;

public class CANFrameParserTest {

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