
package com.srtianxia.bleattendance.entity;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StuListEntity {

    @SerializedName("data")
    private List<StuInfoEntity> mData;
    @SerializedName("data_num")
    private Long mDataNum;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("trid")
    private String mTrid;

    public List<StuInfoEntity> getData() {
        return mData;
    }

    public void setData(List<StuInfoEntity> data) {
        mData = data;
    }

    public Long getDataNum() {
        return mDataNum;
    }

    public void setDataNum(Long dataNum) {
        mDataNum = dataNum;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public String getTrid() {
        return mTrid;
    }

    public void setTrid(String trid) {
        mTrid = trid;
    }

}
