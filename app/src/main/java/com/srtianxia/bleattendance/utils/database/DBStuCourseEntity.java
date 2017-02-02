package com.srtianxia.bleattendance.utils.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 梅梅 on 2017/1/21.
 */
public class DBStuCourseEntity extends RealmObject{

    public static final String TAG = "NewCourseEntity";

    @PrimaryKey
    public int id;                      //现在的周数
    public int status;                  //状态码
    public String version;              //16.9.4    ,   1.0.0
    public String term;                 //2016-2017学年第一学期
    public int stuNum;                  //学号
    public String message;                 //"success"
    public RealmList<DBStuCourse> data;

}
