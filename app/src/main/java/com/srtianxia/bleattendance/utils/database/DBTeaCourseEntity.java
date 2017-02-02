package com.srtianxia.bleattendance.utils.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 梅梅 on 2017/1/27.
 */
public class DBTeaCourseEntity extends RealmObject{

    @PrimaryKey
    public int id;
    public int status;
    public String message;
    public String version;
    public RealmList<DBTeaCourse> data;

}
