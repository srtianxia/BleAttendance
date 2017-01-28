package com.srtianxia.bleattendance.entity;

import java.util.List;

/**
 * Created by srtianxia on 2017/1/28.
 */

public class AttInfoEntity {
    public String status;
    public String message;
    public List<AttInfo> data;


    static class AttInfo {
        String stuNum;
        String stuName;
        String status;
    }
}
