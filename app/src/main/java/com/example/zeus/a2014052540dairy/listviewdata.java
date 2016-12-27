package com.example.zeus.a2014052540dairy;

import android.icu.util.Calendar;

import java.io.Serializable;

/**
 * Created by zeus on 2016/9/26.
 */
public class listviewdata implements Serializable {
    public String weekday;
    public int date;
    public String dec="";
    //public Calendar Cdate;

    {

    };
    public listviewdata(String  weekday,int date,String dec){
        this.weekday=weekday;
        this.date=date;
        this.dec=dec;

    }
}