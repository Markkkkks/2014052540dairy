package com.example.zeus.a2014052540dairy;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zeus on 2016/12/23.
 */
public class MyApp extends Application{

    private HashMap<String,String> Daily;
    public HashMap getLabel(){
        return Daily;
    }
    public void setLabel(HashMap<String,String> Daily){
        this.Daily = Daily;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }
}
