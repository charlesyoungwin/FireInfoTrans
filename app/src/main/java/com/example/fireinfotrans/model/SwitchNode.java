package com.example.fireinfotrans.model;

/**
 * Created by charlesyoung on 2016/4/26.
 */
public class SwitchNode {

    private int mSwitchId;
    private int mSwitchState;
    private int mSwitchType;
    private String mSwitchDatetime;

    public int getSwitchId() {
        return mSwitchId;
    }

    public void setSwitchId(int switchId) {
        mSwitchId = switchId;
    }

    public int getSwitchState() {
        return mSwitchState;
    }

    public void setSwitchState(int switchState) {
        mSwitchState = switchState;
    }

    public int getSwitchType() {
        return mSwitchType;
    }

    public void setSwitchType(int switchType) {
        mSwitchType = switchType;
    }

    public String getSwitchDatetime() {
        return mSwitchDatetime;
    }

    public void setSwitchDatetime(String switchDatetime) {
        mSwitchDatetime = switchDatetime;
    }
}
