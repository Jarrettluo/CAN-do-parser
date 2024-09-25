package com.jiaruiblog;

import com.jiaruiblog.utils.ByteUtils;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanFrameParser {

    private final Map<String, DbcMessage> dbcMessageMap;

    public CanFrameParser(Map<String, DbcMessage> dbcMessageMap) {
        this.dbcMessageMap = dbcMessageMap;
    }

    /*
     * 提取帧数据
     */
    public Map<String, Double> extractMessage(CANFrame canFrame) {
        String hexMsgId = canFrame.getHexMsgId();
        DbcMessage dbcMessage = dbcMessageMap.get(hexMsgId);
        List<DbcSignal> dbcSignalList = dbcMessage.getDbcSignalList();
        Map<String, Double> result = new HashMap<>();
        for (DbcSignal dbcSignal : dbcSignalList) {
            double signal = extractSignal(canFrame.getMsgData(), dbcSignal);
            result.put(dbcSignal.getName(), signal);
        }
        return result;
    }

    /*
     * 提取一帧信号数据
     */
    public static double extractSignal(byte[] data, DbcSignal dbcSignal) {
        return extractSignal(data, dbcSignal.getStartBit(), dbcSignal.getLength(),
                dbcSignal.getSign().equals("true"), dbcSignal.getLittleEndian().equals("true"),
                dbcSignal.getFactor(), dbcSignal.getOffset());
    }


    /**
     * 从 CAN 数据中提取信号值
     *
     * @param data           CAN 数据字节数组
     * @param startBit       信号在数据中的起始位（DBC 中定义）
     * @param length         信号的位长度（DBC 中定义）
     * @param isSigned       是否为有符号信号
     * @param isLittleEndian 是否为小端字节序
     * @return 提取到的信号值
     */
    public static double extractSignal(byte[] data, int startBit, int length,
                                     boolean isSigned, boolean isLittleEndian,
                                     String factor, String offset) {
        // 1. 根据字节序调整起始位和字节顺序
        if (isLittleEndian) {
            // 小端处理，需要重新计算起始位在小端下的位置
            data = reverseArray4(data);
        }
        // 2.将byte数组转换为BitSet数据类型
        BitSet bitSet = ByteUtils.byteArray2BitSet(data);
        BitSet newBitSet = new BitSet(length);

        // 开始进行转数据
        if (isLittleEndian) {
            for (int i = 64 - startBit - length; i < 64 - startBit; i++) {
                if (bitSet.get(i)) {
                    newBitSet.set(i);
                }
            }
        } else {
            for (int i = startBit; i < length; i++) {
                if (bitSet.get(i)) {
                    newBitSet.set(i);
                }
            }
        }

        double secondData = bitSet2Integer(newBitSet, length);
        if (isSigned) {
            // 有符号的计算方式
            boolean integer = isInteger(factor);
            if (integer) {
                // factor是整数则按照ieee754进行解析
                secondData = bitSetToFloat(newBitSet, length);
            } else {
                secondData = Integer.parseUnsignedInt(ByteUtils.byteSet2String(newBitSet, length), 2);
            }
        }

        return secondData;
    }

    /*
     * bit数组转换为整数
     */
    private static double bitSet2Integer(BitSet bits, Integer length) {
        // 将8位BitSet转换为整数
        int value = 0;
        for (int i = 0; i < length; i++) {
            if (bits.get(i)) {
                value |= (1 << (length - 1 - i)); // 从高位到低位设置值
            }
        }
        return value;
    }

    // 32位
    public static float bitSetToFloat(BitSet bits, int length) {
        if (length < 32) {
            throw new IllegalArgumentException("BitSet must contain at least 32 bits.");
        }

        // 提取符号位、指数位和尾数位
        int sign = bits.get(0) ? -1 : 1; // 符号位

        int exponent = 0;
        // 解析1 - 9
        for (int i = 1; i < 9; i++) {
            exponent = (exponent << 1) | (bits.get(i) ? 1 : 0);
        }

        float mantissa = 0;
        for (int i = 9; i < length; i++) {
            if (bits.get(i)) {
                mantissa += (float) Math.pow(2, -(i - 9 + 1));
            }
        }

        // 计算实际值
        float result;
        if (exponent == 0 && mantissa != 0) {
            // 非规格数, 非全零
            result = (float) ((sign) * (mantissa) * Math.pow(2, -126));
        } else if (exponent == 0 && mantissa == 0F) {
            result = 0.0f; // 处理零
        } else if (exponent == 0xFF) {
            result = (mantissa == 0) ? (sign == 1 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY) : Float.NaN; // 处理无穷大或NaN
        } else {
            exponent -= 127; // 调整指数
            result = (float) ((sign) * (1 + mantissa) * Math.pow(2, exponent));
        }
        return result;
    }

    // 64位
    public static double bitSetToDouble(BitSet bits) {
        if (bits.length() < 64) {
            throw new IllegalArgumentException("BitSet must contain at least 64 bits.");
        }

        // 提取符号位、指数位和尾数位
        int sign = bits.get(63) ? 1 : 0; // 符号位
        long exponent = 0;
        for (int i = 62; i >= 52; i--) {
            exponent = (exponent << 1) | (bits.get(i) ? 1 : 0);
        }
        long mantissa = 0;
        for (int i = 51; i >= 0; i--) {
            mantissa = (mantissa << 1) | (bits.get(i) ? 1 : 0);
        }

        // 计算实际值
        double result;
        if (exponent == 0 && mantissa == 0) {
            result = 0.0; // 处理零
        } else if (exponent == 0x7FF) {
            result = (mantissa == 0) ? (sign == 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY) : Double.NaN; // 处理无穷大或NaN
        } else {
            exponent -= 1023; // 调整指数
            result = (double) ((sign == 0 ? 1 : -1) * (1 + (mantissa / (double) (1L << 52))) * Math.pow(2, exponent));
        }
        return result;
    }

    /**
     * 倒置字符串数组优化,将除法替换成移位操作
     *
     * @param strArray byte数组
     * @return 翻转后的数组
     */
    public static byte[] reverseArray4(byte[] strArray) {
        int len = strArray.length;
        int mid = len >> 1;
        //定义一个新的数组
        byte[] newArray = new byte[len];
        for (int i = 0; i <= mid; i++) {
            newArray[i] = strArray[len - 1 - i];
            newArray[len - 1 - i] = strArray[i];
        }
        return newArray;
    }

    /*
     * 用于对整数的字符串进行判断
     * 例如 1.0 等于 1
     * 例如 0.001 不等于 0
     */
    private static boolean isInteger(String input) {
        try {
            return Float.parseFloat(input) == (float) Float.valueOf(input).intValue();
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
