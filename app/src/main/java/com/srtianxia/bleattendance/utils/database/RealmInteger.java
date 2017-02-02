package com.srtianxia.bleattendance.utils.database;

import io.realm.RealmObject;

/**
 * Created by 梅梅 on 2017/1/30.
 */
public class RealmInteger extends RealmObject{
    public int wek;

    public RealmInteger() {
    }

    public RealmInteger(int wek) {
        this.wek = wek;
    }
}
