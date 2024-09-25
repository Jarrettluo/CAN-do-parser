package com.jiaruiblog.utils;

import org.junit.Test;

import java.util.BitSet;


public class ByteUtilsTest {

    @Test
    public void byteArray2BitSet() {
        BitSet bitSet = ByteUtils.byteArray2BitSet(new byte[]{0b00100111});
        System.out.println(bitSet);

        System.out.println(bitSet.get(7));

        System.out.println(bitSet.length());
        System.out.println(bitSet.size());

    }


    @Test
    public void testByteArray2BitSet() {
        BitSet bitSet = ByteUtils.byteArray2BitSet(new byte[]{0b1});
        System.out.println(bitSet);

        System.out.println(bitSet.get(7));

        System.out.println(bitSet.length());
        System.out.println(bitSet.size());

    }

    @Test
    public void testByteArray2BitSet2() {
//        0 01111111 10000000000000000000000
        BitSet bitSet = ByteUtils.byteArray2BitSet(new byte[]{0b00111111, (byte) 0b11000000, 0b0, 0b0});
        System.out.println(bitSet);

        System.out.println(bitSet.get(7));

        System.out.println(bitSet.get(32));
        // length 是指的最高位
        System.out.println(bitSet.length());
        System.out.println(bitSet.size());

    }


    @Test
    public void testBitSet2ByteArray() {
        // 十进制 39
        byte number = 0b00100111;

        int bit = (number >> 1) & 1; // 取出第2位的bit

        int i = number >> 1; // 右移之后为十进制19

        for (int i1 = 0; i1 < 8; i1++) {
            int a = (number >> i1) & 0b1;
            System.out.println(a);

        }
        System.out.println("---------------");
        System.out.println(i);

        System.out.println(number >> 2);

        System.out.println((number >> 2) & 1);

        System.out.println(bit);
        System.out.println(number);
    }
}