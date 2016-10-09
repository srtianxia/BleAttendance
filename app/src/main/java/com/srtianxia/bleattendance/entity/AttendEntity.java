package com.srtianxia.bleattendance.entity;

import java.util.List;

/**
 * Created by 梅梅 on 2016/9/13.
 */
public class AttendEntity {
    public Integer status;
    public List<Data> data;
    public String msg;

    public static class Data{
        public Integer absenceNum;
        public List<Absences> absences;
        public Integer actual;
        public Integer all;
        public String course;
        public String lesson;
        public Integer nowWeek;
        public String type;
    }

    public static class Absences{
        public String stuName;
        public Integer stuNum;
    }
}
