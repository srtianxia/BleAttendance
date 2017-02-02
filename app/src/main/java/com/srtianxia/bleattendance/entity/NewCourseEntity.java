package com.srtianxia.bleattendance.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梅梅 on 2017/1/21.
 */
public class NewCourseEntity{

    public static final String TAG = "NewCourseEntity";


    public int status;                  //状态码
    public String version;              //16.9.4    ,   1.0.0
    public String term;                 //2016-2017学年第一学期
    public int stuNum;                  //学号
    public String message;                 //"success"
    public List<Course> data;

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
                Course aCourse = new Course();
                TeaCourse teaCourse = courseList.data.get(i);
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
