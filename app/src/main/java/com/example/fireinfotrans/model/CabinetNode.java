package com.example.fireinfotrans.model;

/**
 * Created by charlesyoung on 2016/4/26.
 */
public class CabinetNode {

    private int mCabinetId;
    private String mCabinetType;
    private String mCabinetBusType;
    private String mCabinetDatetime;

    public int getCabinetId() {
        return mCabinetId;
    }

    public void setCabinetId(int cabinetId) {
        mCabinetId = cabinetId;
    }

    public String getCabinetType() {
        return mCabinetType;
    }

    public void setCabinetType(String cabinetType) {
        mCabinetType = cabinetType;
    }

    public String getCabinetBusType() {
        return mCabinetBusType;
    }

    public void setCabinetBusType(String cabinetBusType) {
        mCabinetBusType = cabinetBusType;
    }

    public String getCabinetDatetime() {
        return mCabinetDatetime;
    }

    public void setCabinetDatetime(String cabinetDatetime) {
        mCabinetDatetime = cabinetDatetime;
    }
}
