package com.srtianxia.bleattendance.utils;

import java.util.Calendar;

/**
 * Created by 梅梅 on 2017/2/18.
 */
public class TimeUtil {

    public static int CONTINUE_TIME = 40 * 60;

    public static String timeTransform(int time){

        int hours = time/3600 ;
        int minutes = (time - hours*3600)/60;
        int seconds = time - hours*3600 - minutes*60;
        String str = "";

        if (hours < 10 ){
            str = "0" + hours + ":";
        }else
            str = "" + hours + ":";

        if (minutes < 10){
            str = str + "0" + minutes + ":";
        }else
            str = str + minutes + ":";

        if (seconds < 10){
            str = str + "0" + seconds ;
        }else
            str = str + seconds ;

        return str;
    }

    public static String getNotificationTime(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int current_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int current_minutes = calendar.get(Calendar.MINUTE);

        int hours = CONTINUE_TIME/3600 + current_hour;
        int minutes = (CONTINUE_TIME - (CONTINUE_TIME/3600)*3600)/60 + current_minutes;

        while (minutes >= 60){
            minutes -= 60;
            hours ++;
        }
        while (hours >= 24){
            hours -= 24;
        }

        String str = "";

        if (hours < 10 ){
            str = "0" + hours + ":";
        }else
            str = "" + hours + ":";

        if (minutes < 10){
            str = str + "0" + minutes + ":";
        }else
            str = str + minutes + ":";

        str = str + "结束";

        return str;
    }
}
