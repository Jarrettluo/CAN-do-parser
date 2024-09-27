package com.jiaruiblog.utils;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteOrder;
import java.util.BitSet;

/**
 * @ClassName ByteUtilsTest
 * @Description ByteUtils测试类
 * @Author Jarrett Luo
 * @Date 2024/9/27 13:41
 * @Version 1.0
 */
public class ByteUtilsTest {

    @Test
    public void byteArray2BitSet() {
        BitSet expectBitSet = new BitSet(8);
        expectBitSet.set(2);
        expectBitSet.set(5);
        expectBitSet.set(6);
        expectBitSet.set(7);
        BitSet bitSet = ByteUtils.byteArray2BitSet(new byte[]{0b00100111});

        ByteOrder byteOrder = ByteOrder.nativeOrder();

        Assert.assertEquals(expectBitSet, bitSet);
    }


    @Test
    public void testByteArray2BitSet() {
        BitSet expectBitSet = new BitSet(8);
        expectBitSet.set(7);
        BitSet bitSet = ByteUtils.byteArray2BitSet(new byte[]{0b1});
        Assert.assertEquals(expectBitSet, bitSet);
    }

    @Test
    public void testByteArray2BitSet2() {
        // 0 01111111 10000000000000000000000
        BitSet expectBitSet = new BitSet(8);
        expectBitSet.set(2);
        expectBitSet.set(3);
        expectBitSet.set(4);
        expectBitSet.set(5);
        expectBitSet.set(6);
        expectBitSet.set(7);
        expectBitSet.set(8);
        expectBitSet.set(9);
        BitSet bitSet = ByteUtils.byteArray2BitSet(new byte[]{0b00111111, (byte) 0b11000000, 0b0, 0b0});
        Assert.assertEquals(expectBitSet, bitSet);
    }

}