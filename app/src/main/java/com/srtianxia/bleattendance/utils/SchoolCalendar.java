package com.srtianxia.bleattendance.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by srtianxia on 2016/4/3.
 * 根据周数得到真实日期
 */
public class SchoolCalendar {
    //本学期开开学第一天 Calender的月份需要真实月份 -1
    private Calendar firstDay = new GregorianCalendar(2017, 2 - 1, 27);
    private Calendar currentDay;

    /**
     * @param week 第几周
     * @param weekDay 周几
     */
    public SchoolCalendar(int week, int weekDay) {
        currentDay = firstDay;
        currentDay.add(Calendar.DAY_OF_MONTH, 7 * (week - 1));
        if (weekDay == 0) {
            weekDay = 7;
        }
        currentDay.add(Calendar.DAY_OF_MONTH, weekDay - getDayOfWeek());
    }


    public int getCurrentDay() {
        return currentDay.get(Calendar.DATE);
    }

    public int getCurrentMonth(){
        return currentDay.get(Calendar.MONTH)+1;
    }


    public int getDayOfWeek() {
        int i = currentDay.get(Calendar.DAY_OF_WEEK);
        if (i == 1) {
            return 7;
        }
        return (i - 1);
    }
}
