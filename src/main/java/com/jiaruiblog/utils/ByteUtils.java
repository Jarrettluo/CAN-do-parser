package com.jiaruiblog.utils;

import java.util.BitSet;

/**
 * 用于对byte类型进行处理的工具类
 *
 * @author Jarrett Luo
 * @version 1.0
 */
public class ByteUtils {

    private ByteUtils() {
        throw new IllegalArgumentException("Utility class");
    }

    /**
     * <p>从十六进制字符串转换为字节数组</p>
     * @param hexString 转换前十六进制字符串
     * @return byte[] 转换后的字节数组
     * @author Jarrett Luo
     **/
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
     * <p>把byte转化成2进制字符串</p>
     *          1byte = 8bit
     *          <p>&lt; &lt; 左移 二进制位左移若干位，高位丢弃，低位补0</p>
     *          <p>&gt; &gt; 右移 右移若干位，高位补0或补符号位，右边丢弃</p>
     * @param b byte
     * @return java.lang.String
     * @author Jarrett Luo
     **/
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

    /**
     * <p>二进制转byte</p>
     * @param bString 二进制的字符串
     * @return byte
     * @author Jarrett Luo
     **/
    public static byte bit2byte(String bString) {
        byte result = 0;
        for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
            result += (byte) (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
        }
        return result;
    }

    /**
     * <p>将ByteArray对象转化为BitSet</p>
     * @param bytes 2024/9/27 15:24
     *      * @param bytes byte arrays
     * @return java.util.BitSet
     * @author Jarrett Luo
     **/
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
