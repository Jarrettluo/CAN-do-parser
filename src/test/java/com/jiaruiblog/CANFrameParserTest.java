package com.jiaruiblog;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CANFrameParserTest
 * @Description CANFrameParser测试类
 * @Author Jarrett Luo
 * @Date 2024/9/27 13:41
 * @Version 1.0
 */
public class CANFrameParserTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    @Test
    public void testExtractMessage1() {

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("dbcMessageMap is empty");

        // dbc数据; 通过dbc解析器得到
        Map<String, DbcMessage> dbcMessageMap = new HashMap<String, DbcMessage>();

        // 初始化解析器
        CANFrameParser canFrameParser = new CANFrameParser(dbcMessageMap);
    }

    @Test
    public void testExtractMessage2() {

        Map<String, Double> expectValue = new HashMap<>();
        expectValue.put("test", 2.802596928649634E-45D);
        // CAN 帧数据
        CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "275d602702000000");

        // dbc数据; 通过dbc解析器得到
        Map<String, DbcMessage> dbcMessageMap = new HashMap<String, DbcMessage>();

        List<DbcSignal> dbcSignals = new ArrayList<DbcSignal>();
        DbcSignal dbcSignal = new DbcSignal();
        dbcSignal.setName("test");
        dbcSignal.setStartBit(32);
        dbcSignal.setLength(32);
        dbcSignal.setFactor("1");
        dbcSignal.setOffset("0");
        dbcSignal.setSign("true");
        dbcSignal.setLittleEndian("true");
        dbcSignals.add(dbcSignal);

        DbcMessage dbcMessage = new DbcMessage();
        dbcMessage.setIdFormat("00000014");
        dbcMessage.setDbcSignalList(dbcSignals);

        dbcMessageMap.put(dbcMessage.getIdFormat(), dbcMessage);

        // 初始化解析器
        CANFrameParser canFrameParser = new CANFrameParser(dbcMessageMap);
        // 对can数据进行解析
        Map<String, Double> physicalValueMap = canFrameParser.extractMessage(canFrame);
        Assert.assertEquals(expectValue, physicalValueMap);
    }

    @Test
    public void extractSignal() {

    }

    @Test
    public void testExtractSignal() {
        CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "275d602702000000");

        int startBit = 32;
        int length = 32;
        boolean isSigned = true;
        boolean isLittleEndian = true;

        double signal = CANFrameParser.extractSignal(canFrame.getMsgData(), startBit, length, isSigned, isLittleEndian, "1", "0");

        double expectValue = 2.802596928649634E-45D;
        Assert.assertEquals(expectValue, signal, 0.0000001);
    }

    @Test
    public void testExtractSignal2() {
        CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "88d65e2763f784c0");
        // 11000000100001001111011101100011

        int startBit = 32;
        int length = 32;
        boolean isSigned = true;
        boolean isLittleEndian = true;

        double signal = CANFrameParser.extractSignal(canFrame.getMsgData(), startBit, length, isSigned, isLittleEndian,"1", "0");

        double expectValue = -4.155198574066162D;
        Assert.assertEquals(expectValue, signal, 0.0000001);
    }
}