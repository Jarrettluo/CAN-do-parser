package com.jiaruiblog;

import com.jiaruiblog.utils.ByteUtils;
import com.jiaruiblog.utils.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public class CANFrameParser {

    private static final int BIT_LENGTH = 8;

    private final Map<String, DbcMessage> dbcMessageMap;

    public CANFrameParser(Map<String, DbcMessage> dbcMessageMap) {
        if (Objects.isNull(dbcMessageMap) || dbcMessageMap.isEmpty()) {
            throw new IllegalArgumentException("dbcMessageMap is empty");
        }
        this.dbcMessageMap = dbcMessageMap;
    }

    /*
     * 提取帧数据
     */
    public Map<String, Double> extractMessage(CANFrame canFrame) {
        String hexMsgId = canFrame.getHexMsgId();
        DbcMessage dbcMessage = dbcMessageMap.get(hexMsgId);
        if (Objects.isNull(dbcMessage)) {
            return Collections.emptyMap();
        }
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
        if (Objects.isNull(data) || data.length == 0) {
            throw new IllegalArgumentException("data is null");
        }
        // 1. 根据字节序调整起始位和字节顺序
        if (isLittleEndian) {
            // 小端处理，需要重新计算起始位在小端下的位置
            data = reverseArray4(data);
        }
        // 2.将byte数组转换为BitSet数据类型
        BitSet bitSet = ByteUtils.byteArray2BitSet(data);
        BitSet newBitSet = new BitSet(length);
        // 计算出一共有多少位数据
        int bitLength = BIT_LENGTH * data.length;

        // 3. 如果是小端数据，那么则将数据进行重新换算
        if (isLittleEndian) {
            for (int i = bitLength - startBit - length; i < bitLength - startBit; i++) {
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
        // 从二进制转换后的十进制数据
        double doubleValue = bitSet2Integer(newBitSet, length);
        if (isSigned) {
            // 有符号的计算方式
            boolean integer = isInteger(factor);
            if (integer) {
                // factor是整数则按照ieee754进行解析
                if (length == 32 || length == 64) {
                    doubleValue = parseIEEE754(newBitSet, length);
                } else {
                    // 计算有符号的二进制数据
                    if (bitSet.get(0)) {
                        doubleValue = signBinaryValue(newBitSet, length);
                    }
                }
            } else {
                doubleValue = Integer.parseUnsignedInt(ByteUtils.byteSet2String(newBitSet, length), 2);
            }
        }

        // 5. 将十进制数据转换为物理量
        return calcPhysicalValue(doubleValue, factor, offset);
    }

    /*
     * 计算有符号的二进制数据
     */
    private static double signBinaryValue(BitSet newBitSet, int length) {
        double doubleValue;
        BitSet bitSet1 = new BitSet();
        for (int i = 0; i < length; i++) {
            if (!newBitSet.get(i)) {
                bitSet1.set(i);
            }
        }
        doubleValue = -(Long.parseLong(ByteUtils.byteSet2String(bitSet1, length), 2) + 1);
        return doubleValue;
    }

    /*
     * bit数组转换为整数
     */
    private static double bitSet2Integer(BitSet bits, Integer length) {
        // 将8位BitSet转换为整数
        int value = 0;
        for (int i = 0; i < length; i++) {
            if (bits.get(i)) {
                // 从高位到低位设置值
                value |= (1 << (length - 1 - i));
            }
        }
        return value;
    }

    // 解析32位或者64位的数据
    private static double parseIEEE754(BitSet bits, int length) {
        if (length == 32) {
            return parse32BitIeee754(bits);
        } else if (length == 64) {
            return parse64BitIeee754(bits);
        } else {
            throw new IllegalArgumentException("BitSet must contain at least 64 bits.");
        }

    }

    // 32位
    private static double parse32BitIeee754(BitSet bits) {
        // 提取符号位、指数位和尾数位
        int sign = bits.get(0) ? -1 : 1;

        // 32位指数为8位长度
        int exponent = 0;
        for (int i = 1; i < 9; i++) {
            exponent = (exponent << 1) | (bits.get(i) ? 1 : 0);
        }

        // 计算尾数，长度为23位
        double mantissa = 0;
        for (int i = 9; i < 32; i++) {
            if (bits.get(i)) {
                mantissa += 1.0 / (1 << (i - 8));
            }
        }

        // 计算非规格化的值
        if (exponent == 0) {
            return mantissa == 0 ? 0.0 : sign * mantissa * Math.pow(2, -126);
        }
        // 计算特殊化的值
        if (exponent == 0xFF) {
            return mantissa == 0 ? (sign == 1 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY) : Double.NaN;
        }

        exponent -= 127;
        return sign * (1 + mantissa) * Math.pow(2, exponent);
    }

    // 64位
    public static double parse64BitIeee754(BitSet bits) {
        // 提取符号位、指数位和尾数位
        int sign = bits.get(0) ? 1 : 0; // 符号位
        long exponent = 0;
        for (int i = 1; i < 12; i++) {
            exponent = (exponent << 1) | (bits.get(i) ? 1 : 0);
        }

        long mantissa = 0;
        for (int i = 12; i < 64; i++) {
            if (bits.get(i)) {
                mantissa |= (1L << (63 - i));
            }
        }

        // 计算实际值
        double result;
        if (exponent == 0 && mantissa != 0) {
            result = (sign == 0 ? 1 : -1) * (mantissa) * Math.pow(2, -1022);
        } else if (exponent == 0 && mantissa == 0) {
            // 处理零
            result = 0.0;
        } else if (exponent == 0x7FF) {
            // 处理无穷大或NaN
            result = (mantissa == 0) ? (sign == 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY) : Double.NaN;
        } else {
            exponent -= 1023; // 调整指数
            result = ((sign == 0 ? 1 : -1) * (1 + mantissa) * Math.pow(2, exponent));
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

    /*
     * 计算总线数据的物理值
     */
    public static double calcPhysicalValue(double originValue, String factor, String offset) {
        // 避免空指针
        if (StringUtils.isEmpty(factor) || StringUtils.isEmpty(offset)) {
            throw new IllegalArgumentException("factor or offset is empty");
        }
        try {
            BigDecimal originData = new BigDecimal(String.valueOf(originValue));
            BigDecimal factorData = new BigDecimal(factor);
            BigDecimal offsetData = new BigDecimal(offset);
            return originData.multiply(factorData).add(offsetData).doubleValue();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("calculate physical value failed");
        }
    }


}
