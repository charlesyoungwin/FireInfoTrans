package com.example.fireinfotrans.model;

/**
 * Created by charlesyoung on 2016/4/26.
 */
public class PowerNode {

    private int mPowerId;
    private int mPowerA;
    private int mPowerB;
    private int mPowerC;
    private String mPowerDateTime;
    private int mPowerState;

    public int getPowerId() {
        return mPowerId;
    }

    public void setPowerId(int powerId) {
        mPowerId = powerId;
    }

    public int getPowerA() {
        return mPowerA;
    }

    public void setPowerA(int powerA) {
        mPowerA = powerA;
    }

    public int getPowerB() {
        return mPowerB;
    }

    public void setPowerB(int powerB) {
        mPowerB = powerB;
    }

    public int getPowerC() {
        return mPowerC;
    }

    public void setPowerC(int powerC) {
        mPowerC = powerC;
    }

    public String getPowerDateTime() {
        return mPowerDateTime;
    }

    public void setPowerDateTime(String powerDateTime) {
        mPowerDateTime = powerDateTime;
    }

    public int getPowerState() {
        return mPowerState;
    }

    public void setPowerState(int powerState) {
        mPowerState = powerState;
    }
}
