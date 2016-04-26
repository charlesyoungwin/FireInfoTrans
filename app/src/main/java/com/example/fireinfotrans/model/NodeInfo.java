package com.example.fireinfotrans.model;

/**
 * Created by charlesyoung on 2016/4/26.
 */
public class NodeInfo {

    private int mNodeInfoId;
    private String mNodeLocation;
    private String mNodeSystem;
    private String mNodeStandard;

    public int getNodeInfoId() {
        return mNodeInfoId;
    }

    public void setNodeInfoId(int nodeInfoId) {
        mNodeInfoId = nodeInfoId;
    }

    public String getNodeLocation() {
        return mNodeLocation;
    }

    public void setNodeLocation(String nodeLocation) {
        mNodeLocation = nodeLocation;
    }

    public String getNodeSystem() {
        return mNodeSystem;
    }

    public void setNodeSystem(String nodeSystem) {
        mNodeSystem = nodeSystem;
    }

    public String getNodeStandard() {
        return mNodeStandard;
    }

    public void setNodeStandard(String nodeStandard) {
        mNodeStandard = nodeStandard;
    }
}
