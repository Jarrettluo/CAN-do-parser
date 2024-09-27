package com.jiaruiblog;

import java.util.List;

/**
 * @ClassName DbcMessage
 * @Description DBC数据帧
 * @Author Jarrett Luo
 * @Date 2024/9/27 13:41
 * @Version 1.0
 */
public class DbcMessage {

    private String name;

    private String idFormat;

    private Integer dlc;

    private String txMethod;

    private String cycleTime;

    private String transmitter;

    private String comment;

    private List<DbcSignal> dbcSignalList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdFormat() {
        return idFormat;
    }

    public void setIdFormat(String idFormat) {
        this.idFormat = idFormat;
    }

    public Integer getDlc() {
        return dlc;
    }

    public void setDlc(Integer dlc) {
        this.dlc = dlc;
    }

    public String getTxMethod() {
        return txMethod;
    }

    public void setTxMethod(String txMethod) {
        this.txMethod = txMethod;
    }

    public String getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(String cycleTime) {
        this.cycleTime = cycleTime;
    }

    public String getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(String transmitter) {
        this.transmitter = transmitter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDbcSignalList(List<DbcSignal> dbcSignalList) {
        this.dbcSignalList = dbcSignalList;
    }

    public List<DbcSignal> getDbcSignalList() {
        return dbcSignalList;
    }

    @Override
    public String toString() {
        return "DbcMessage{" +
                "name='" + name + '\'' +
                ", idFormat='" + idFormat + '\'' +
                ", dlc=" + dlc +
                ", txMethod='" + txMethod + '\'' +
                ", cycleTime='" + cycleTime + '\'' +
                ", transmitter='" + transmitter + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
