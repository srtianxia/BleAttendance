package com.srtianxia.bleattendance.entity;

import java.util.List;

/**
 * Created by srtianxia on 2017/1/28.
 */

public class AttInfoEntity {
    public String status;
    public String message;
    public List<AttInfo> data;


    public static class AttInfo {
        public String stuNum;
        public String stuName;
        public String status;
    }
}
