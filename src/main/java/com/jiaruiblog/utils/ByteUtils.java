package com.jiaruiblog.utils;

import java.util.BitSet;

public class ByteUtils {

    private ByteUtils() {
        throw new IllegalArgumentException("Utility class");
    }

    /**
     * 从十六进制字符串转换为字节数组
     *
     * @param hexString 输入的十六进制字符串
     * @return 转换后的字节数组
     */
    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * 把byte转化成2进制字符串
     * 1byte = 8bit
     * << 左移 二进制位左移若干位，高位丢弃，低位补0
     * >> 右移 右移若干位，高位补0或补符号位，右边丢弃
     *
     * @param b byte
     * @return String
     */
    public static String getBinaryStrFromByte(byte b) {
        StringBuilder result = new StringBuilder();
        byte a = b;

        for (int i = 0; i < 8; i++) {
            byte c = a;
            //每移一位如同将10进制数除以2并去掉余数。 例如：39 经过处理后变成了38
            a = (byte) (a >> 1);
            a = (byte) (a << 1);
            if (a == c) {
                result.insert(0, "0");
            } else {
                result.insert(0, "1");
            }
            a = (byte) (a >> 1);
        }
        return result.toString();
    }

    /*
     * 二进制转byte
     */
    public static byte bit2byte(String bString) {
        byte result = 0;
        for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
            result += (byte) (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
        }
        return result;
    }

    /**
     * 将ByteArray对象转化为BitSet
     *
     * @param bytes byte arrays
     * @return
     */
    public static BitSet byteArray2BitSet(byte[] bytes) {
        BitSet bitSet = new BitSet(bytes.length * 8);
        int index = 0;
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; j--) {
                bitSet.set(index++, (aByte & (1 << j)) >> j == 1);
            }
        }
        return bitSet;
    }

    public static String byteSet2String(BitSet bitSet, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(bitSet.get(i) ? "1" : "0");
        }
        return stringBuilder.toString();
    }
}
