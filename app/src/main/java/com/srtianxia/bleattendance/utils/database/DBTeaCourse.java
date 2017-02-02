package com.srtianxia.bleattendance.utils.database;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by 梅梅 on 2017/1/30.
 */
public class DBTeaCourse extends RealmObject{

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
    public RealmList<RealmInteger> week;

    public String toString(){
        return course + "@" + classroom;
    }

}
