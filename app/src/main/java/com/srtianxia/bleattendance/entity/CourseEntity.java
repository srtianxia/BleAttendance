package com.srtianxia.bleattendance.entity;

import java.util.List;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class CourseEntity {
    public List<Course> data;
    public String nowWeek;
    public String info;
    public int status;
    public String version;

    public static class Course {
        public String stuNum;
        public String term;
        public String cid;              //课程id
        public int begin_lesson;        //每一节课开始的节数
        public String classroom;        //教室
        public String course;           //课程名称
        public String day;              //周几的字符串表示法，显示在控件上
        public int hash_day;            //周几的表示法，从0开始，0为周一，代表这个课程在周几
        public int hash_lesson;         //课节数的表示法，从0开始，0为一二节课，1为三四节课，以此类推
        public String lesson;           //课节数的字符串表示法，例如一二节
        public int period;              //课程维持的节数 2节或者3节
        public String rawWeek;          //课程的维持周数，例如 1-18周
        public String status;           //课程的状态，例如：正常
        public String teacher;          //课程的老师名称
        public String type;             //课程的类型：必修，限选，任选
        public List<Integer> week;      //课程的具体周数
        public int weekBegin;           //课程的开始周数
        public int weekEnd;             //课程的结束周数
        public String weekModel;        //课程的类型：单周/双周/所有

        @Override
        public String toString() {
            return "课程号 " + cid + " 教室 " + classroom + " 课程名 " + course + " 老师 " + teacher;
        }
    }

}
