package com.jiaruiblog;

public class DbcSignal {

    // 信号名称
    private String name;

    // 起始位
    private Integer startBit;

    // 字节长度
    private Integer length;

    // 小端数据标志
    private String littleEndian;

    // 有符号的标志
    private String sign;

    // 因数
    private String factor;

    // 偏移量
    private String offset;

    // 信号的最小值
    private String minimum;

    // 信号的最大值
    private String maximum;

    // 信号的单位
    private String unit;

    // 描述信息
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartBit() {
        return startBit;
    }

    public void setStartBit(Integer startBit) {
        this.startBit = startBit;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getLittleEndian() {
        return littleEndian;
    }

    public void setLittleEndian(String littleEndian) {
        this.littleEndian = littleEndian;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitConversion() {
        return comment;
    }

    public void setUnitConversion(String unitConversion) {
        this.comment = unitConversion;
    }

    @Override
    public String toString() {
        return "DbcSignal{" +
                "name='" + name + '\'' +
                ", startBit=" + startBit +
                ", length=" + length +
                ", littleEndian='" + littleEndian + '\'' +
                ", sign='" + sign + '\'' +
                ", factor='" + factor + '\'' +
                ", offset='" + offset + '\'' +
                ", minimum='" + minimum + '\'' +
                ", maximum='" + maximum + '\'' +
                ", unit='" + unit + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
