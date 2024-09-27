package com.jiaruiblog;

import com.jiaruiblog.utils.ByteUtils;

import java.util.StringJoiner;

/**
 * @ClassName CANFrame
 * @Description CAN数据帧
 * @Author Jarrett Luo
 * @Date 2024/9/27 13:41
 * @Version 1.0
 */
public class CANFrame {

    // CAN帧数据的时间戳；时间精度为us
    private long timestamp;

    // 通道编号；一般从0开始
    private int channel;

    // 数据场长度，一般CAN是8byte, CAN FD是64byte
    private int dlc;

    // 数据帧的帧ID，这里是十进制转换后的数据
    private long msgId;

    // 数据帧的ID，十六进制的原始数据
    private String hexMsgId;

    // 数据场字节
    private byte[] msgData;

    // 数据场转换为中文可阅读的十六进制字符串
    private String msgDataHexString;

    // 补充字段，用来标明协议类型
    private int protocol;

    public CANFrame(long timestamp, int channel, int dlc, long msgId, byte[] msgData) {
        this.timestamp = timestamp;
        this.channel = channel;
        this.dlc = dlc;
        this.msgId = msgId;
        this.msgData = msgData;
    }

    public CANFrame(long timestamp, int channel, int dlc, long msgId, String hexString) {
        this.timestamp = timestamp;
        this.channel = channel;
        this.dlc = dlc;
        this.msgId = msgId;
        this.msgData = ByteUtils.hexStringToByteArray(hexString);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getDlc() {
        return dlc;
    }

    public void setDlc(int dlc) {
        this.dlc = dlc;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    /**
     * @author Jarrett Luo
     * @desc    // 将msgID格式化为16进制字符串
     *          // 保持8位长度，不足的补0
     * @createTime  2024/9/27 15:25
     *
     * @return java.lang.String
     **/
    public String getHexMsgId() {
        return String.format("%08x", msgId);
    }

    public void setHexMsgId(String hexMsgId) {
        this.hexMsgId = hexMsgId;
    }

    public byte[] getMsgData() {
        return msgData;
    }

    public void setMsgData(byte[] msgData) {
        this.msgData = msgData;
    }

    /**
     * @author Jarrett Luo
     * @desc 获取字节数组的16进制编码形式
     * @createTime  2024/9/27 15:25
     *
     * @return java.lang.String
     **/
    public String getMsgDataHexString() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (byte msgDatum : msgData) {
            stringJoiner.add(String.format("%02X", msgDatum));
        }
        return stringJoiner.toString();
    }

    /**
     * @author Jarrett Luo
     * @desc 获取字节数组的二进制
     * @createTime  2024/9/27 15:24
     *
     * @return java.lang.String
     **/
    public String getMsgDataBinaryString() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (byte msgDatum : msgData) {
            stringJoiner.add(ByteUtils.getBinaryStrFromByte(msgDatum));
        }
        return stringJoiner.toString();
    }


    @Override
    public String toString() {
        return "CANFrame{" +
                "timestamp=" + timestamp +
                ", channel=" + channel +
                ", dlc=" + dlc +
                ", msgId=" + msgId +
                ", hexMsgId='" + getHexMsgId() + '\'' +
                ", msgHex='" + getMsgDataHexString() + '\'' +
                ", msgBin=" + getMsgDataBinaryString() + '\'' +
                '}';
    }
}
