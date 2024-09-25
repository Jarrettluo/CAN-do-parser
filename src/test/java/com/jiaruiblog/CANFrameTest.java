package com.jiaruiblog;

import org.junit.Before;
import org.junit.Test;


public class CANFrameTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testToString() {
        CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "275d602702000000");
        System.out.println(canFrame.toString());
    }
}