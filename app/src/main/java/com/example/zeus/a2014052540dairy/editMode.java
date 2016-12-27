package com.example.zeus.a2014052540dairy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zeus on 2016/9/25.
 */
@TargetApi(Build.VERSION_CODES.N)
public class editMode extends Activity {
    private Calendar date=Calendar.getInstance();
    private ArrayList<HashMap<String, Object>> myobject;
    private ImageView done;
    private TextView Diary_Date;
    private EditText mEditText;
    private MyApp myApp;
    private HashMap<String,String> Daily;
    public String getWeekDay(java.util.Calendar c)
    {
        if (c == null)
        {
            return "Mon";
        }

        if (java.util.Calendar.MONDAY == c.get(java.util.Calendar.DAY_OF_WEEK))
        {
            return "Mon";
        }
        if (java.util.Calendar.TUESDAY == c.get(java.util.Calendar.DAY_OF_WEEK))
        {
            return "Tue";
        }
        if (java.util.Calendar.WEDNESDAY == c.get(java.util.Calendar.DAY_OF_WEEK))
        {
            return "Wed";
        }
        if (java.util.Calendar.THURSDAY == c.get(java.util.Calendar.DAY_OF_WEEK))
        {
            return "Thu";
        }
        if (java.util.Calendar.FRIDAY == c.get(java.util.Calendar.DAY_OF_WEEK))
        {
            return "Fri";
        }
        if (java.util.Calendar.SATURDAY == c.get(java.util.Calendar.DAY_OF_WEEK))
        {
            return "Sat";
        }
        if (java.util.Calendar.SUNDAY == c.get(java.util.Calendar.DAY_OF_WEEK))
        {
            return "Sun";
        }

        return "Mon";
    }
    void updatelistview(java.util.Calendar Cdate) {
        int maxday = Cdate.getActualMaximum(java.util.Calendar.DATE);

        final ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/

        for (int i = 1; i <= maxday; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Cdate.set(java.util.Calendar.DATE, i);
            map.put("weekday", getWeekDay(Cdate));//加入图片            e", "第"+i+"行");
            Date Ddate = Cdate.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            map.put("dec", format.format(Ddate));
            map.put("date", Cdate.get(java.util.Calendar.DATE));
            map.put("Thedate", format.format(Ddate));
            map.put("Cday", Cdate);
            listItem.add(map);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_mode_layout);
        myApp= (MyApp) getApplication();
        Daily=myApp.getLabel();//获得hashmap
        if(Daily==null)
            Daily=new HashMap<String,String>();
        final SimpleDateFormat format1 = new SimpleDateFormat("E dd/MM/YYYY");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
        final Date date= (Date) getIntent().getSerializableExtra("passDate");
        mEditText= (EditText) findViewById(R.id.Edit);
        //设置内容
        if(Daily.containsKey(format2.format(date)))
            mEditText.setText(Daily.get(format2.format(date)));

        //Cdate=Calendar.getInstance();
        Diary_Date= (TextView) findViewById(R.id.diary_Date);
        Diary_Date.setText(format1.format(date));


        done= (ImageView) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(editMode.this,MainActivity.class);
                String a=mEditText.getText().toString();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
                Daily.put(format1.format(date),a);
                myApp.setLabel(Daily);
                setResult(RESULT_OK, i);
                finish();
            }
        });




    }
}
