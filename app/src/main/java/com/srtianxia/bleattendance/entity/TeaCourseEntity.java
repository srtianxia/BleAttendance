package com.srtianxia.bleattendance.entity;

import java.util.List;

/**
 * Created by 梅梅 on 2017/1/27.
 */
public class TeaCourseEntity {
    public int status;
    public String message;
    public String version;
    public List<TeaCourse> data;

    public static class TeaCourse{
        public String trid;
        public String scNum;
        public String jxbID;
        public String hash_day;
        public String hash_lesson;
        public String begin_lesson;
        public String day;
        public String lesson;
        public String course;
        public String teacher;
        public String type;
        public String classroom;
        public String rawWeek;
        public String period;
        public List<Integer> week;

        public String toString(){
            return course + "@" + classroom;
        }

    }
}
