package com.srtianxia.bleattendance.entity;

import java.util.List;

/**
 * Created by 梅梅 on 2017/1/21.
 */
public class NewCourseEntity {
    public int status;                  //状态码
    public String version;              //16.9.4
    public String term;                 //2016-2017学年第一学期
    public int stuNum;                  //学号
    public List<Course> data;

    public static class Course{
        public int hash_day;            //hash_day+1  代表这节课在周几
        public int hash_lesson;         //hash_lesson*2+1   代表这节课开始的小节数
        public int begin_lesson;
        public String day;              //星期一
        public String lesson;           //1、2节
        public String course;           //课程名
        public String teacher;          //老师名
        public String classroom;        //教室号
        public String rawWeek;          //1-18周
        public String weekModel;        //
        public int weekBegin;           //1，第1周开始
        public int weekEnd;             //18，第18周结束
        public String type;             //限选课
        public int period;              //1、2 两节连上
        public String id;               //课程id
        public List<Integer> week;      //包含的周的数组
        public int jxbid;                //教学班id

        public String toString(){
            return course + "@" + classroom;
        }
    }

}
