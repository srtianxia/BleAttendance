
package com.srtianxia.bleattendance.entity;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StuInfoEntity {

    @SerializedName("academy")
    private String mAcademy;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("grade")
    private String mGrade;
    @SerializedName("major")
    private String mMajor;
    @SerializedName("majorName")
    private String mMajorName;
    @SerializedName("name")
    private String mName;
    @SerializedName("stuNum")
    private String mStuNum;
    @SerializedName("type")
    private String mType;

    public String getAcademy() {
        return mAcademy;
    }

    public void setAcademy(String academy) {
        mAcademy = academy;
    }


    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getGrade() {
        return mGrade;
    }

    public void setGrade(String grade) {
        mGrade = grade;
    }

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String major) {
        mMajor = major;
    }

    public String getMajorName() {
        return mMajorName;
    }

    public void setMajorName(String majorName) {
        mMajorName = majorName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getStuNum() {
        return mStuNum;
    }

    public void setStuNum(String stuNum) {
        mStuNum = stuNum;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
