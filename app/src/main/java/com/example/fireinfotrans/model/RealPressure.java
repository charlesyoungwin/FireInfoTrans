package com.example.fireinfotrans.model;

/**
 * Created by charlesyoung on 2016/4/26.
 */
public class RealPressure {

    private int mPressureId;
    private double mPressureData;
    private int mPressureType;
    private String mPressureDatetime;
    private int mPressureState;

    public RealPressure(){
        super();
    }

    public RealPressure(int pressureId, double pressureData, int pressureType, String pressureDatetime, int pressureState) {
        mPressureId = pressureId;
        mPressureData = pressureData;
        mPressureType = pressureType;
        mPressureDatetime = pressureDatetime;
        mPressureState = pressureState;
    }

    public int getPressureId() {
        return mPressureId;
    }

    public void setPressureId(int pressureId) {
        mPressureId = pressureId;
    }

    public double getPressureData() {
        return mPressureData;
    }

    public void setPressureData(double pressureData) {
        mPressureData = pressureData;
    }

    public int getPressureType() {
        return mPressureType;
    }

    public void setPressureType(int pressureType) {
        mPressureType = pressureType;
    }

    public String getPressureDatetime() {
        return mPressureDatetime;
    }

    public void setPressureDatetime(String pressureDatetime) {
        mPressureDatetime = pressureDatetime;
    }

    public int getPressureState() {
        return mPressureState;
    }

    public void setPressureState(int pressureState) {
        mPressureState = pressureState;
    }

}
