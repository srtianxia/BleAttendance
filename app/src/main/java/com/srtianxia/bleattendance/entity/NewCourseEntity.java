package com.srtianxia.bleattendance.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梅梅 on 2017/1/21.
 */
public class NewCourseEntity {

    public static final String TAG = "NewCourseEntity";

    public int status;                  //状态码
    public String version;              //16.9.4    ,   1.0.0
    public String term;                 //2016-2017学年第一学期
    public int stuNum;                  //学号
    public String message;                 //"success"
    public List<Course> data;

    public static class Course{
        public int hash_day;            //hash_day+1  代表这节课在周几
        public int hash_lesson;         //hash_lesson*2+1   代表这节课开始的小节数
        public int begin_lesson;        //这节课开始的节数,从第1节开始
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
        public String trid;             //教师id
        public String scNum;            //课程id
        public String jxbID;               //教学班id


        public String toString(){
            return course + "@" + classroom;
        }

    }

    public static NewCourseEntity Tea2NewCourse(TeaCourseEntity courseList){

        NewCourseEntity newCourseEntity = null;

        if (newCourseEntity == null){
            newCourseEntity = new NewCourseEntity();
        }
        newCourseEntity.status = courseList.status;
        newCourseEntity.message = courseList.message;
        newCourseEntity.version = courseList.version;

        if (courseList != null && courseList.data != null && courseList.data.size()>0)
        {
            for (int i=0; i<courseList.data.size(); i++){
                NewCourseEntity.Course aCourse = new NewCourseEntity.Course();
                TeaCourseEntity.TeaCourse teaCourse = courseList.data.get(i);
                aCourse.trid = teaCourse.trid;
                aCourse.scNum = teaCourse.scNum;
                aCourse.jxbID = teaCourse.jxbID;
                aCourse.hash_day = Integer.parseInt(teaCourse.hash_day);
                aCourse.hash_lesson = Integer.parseInt(teaCourse.hash_lesson);
                aCourse.begin_lesson  = Integer.parseInt(teaCourse.begin_lesson);
                aCourse.day = teaCourse.day;
                aCourse.lesson = teaCourse.lesson;
                aCourse.course = teaCourse.course;
                aCourse.teacher = teaCourse.teacher;
                aCourse.type = teaCourse.type;
                aCourse.classroom = teaCourse.classroom;
                aCourse.rawWeek = teaCourse.rawWeek;
                aCourse.period = Integer.parseInt(teaCourse.period);
                aCourse.week = teaCourse.week;

                if (newCourseEntity.data == null){
                    newCourseEntity.data = new ArrayList<>();
                }
                newCourseEntity.data.add(aCourse);
            }
        }

        return newCourseEntity;
    }

}
