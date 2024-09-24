package com.jiaruiblog;

public class DbcMessage {

    private String name;

    private String idFormat;

    private Integer dlc;

    private String txMethod;

    private String cycleTime;

    private String transmitter;

    private String comment;


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
}
