package com.srtianxia.bleattendance.entity;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class StuEntity {
    public String sessionId;
    public Integer status;
    public Student data;
    public String msg;

    public static class Student {
        public Integer sid;
        public String stuAcad;
        public String stuMajor;
        public String stuName;
        public Integer stuNum;
        public String stuPass;
        public String stuSex;
    }
}
