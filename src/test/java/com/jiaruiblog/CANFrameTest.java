package com.jiaruiblog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CANFrameTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testToString() {
        String expectValue = "CANFrame{timestamp=1727277759911, channel=1, dlc=8, msgId=20, hexMsgId='00000014'," +
                " msgHex='27 5D 60 27 02 00 00 00'," +
                " msgBin=00100111 01011101 01100000 00100111 00000010 00000000 00000000 00000000'}";
        CANFrame canFrame = new CANFrame(1727277759911L, 1, 8, 20, "275d602702000000");
        Assert.assertEquals(expectValue, canFrame.toString());
    }
}