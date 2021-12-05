package com.edikorce.cigarettereminder.dateTimeUtilities;

import java.util.Calendar;
import java.util.Date;


public class DateTimeUtilities {


    public String hourNow(){
        Calendar now = Calendar.getInstance();
        int hourNow = now.get(Calendar.HOUR_OF_DAY);
        int minuteNow = now.get(Calendar.MINUTE);

        return String.valueOf(hourNow) + ":" + String.valueOf(minuteNow);
    }


    public int getHourInt(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR_OF_DAY);
    }


    public int getMinuteInt(){
        Calendar now = Calendar.getInstance();

        return now.get(Calendar.MINUTE);
    }


    public int numberOfMonthInt() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.MONTH);
    }


    public int dateTodayInt(){
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int nrOfDaysInAMonthInt(){
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public int currentYear(){
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);

    }
}
