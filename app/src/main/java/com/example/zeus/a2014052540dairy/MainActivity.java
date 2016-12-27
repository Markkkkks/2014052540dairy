package com.example.zeus.a2014052540dairy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
public class MainActivity extends Activity {
    private Spinner monthchose,yearchose;
    private ImageView adddairy,show_month_dairy;
    private Calendar Cdate;
    private ListView lv;
    private ArrayList<HashMap<String, Object>> myobject;
    private ArrayList<listviewdata> textdata;
    private int year;
    private int month;
    private int day;
    private MyApp myApp;
    private HashMap<String,String>Daily;
    private Object getObject(String name){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }
    private void saveObject(String name){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Daily);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }
public String getWeekDay(Calendar c)
{
    if (c == null)
    {
        return "Mon";
    }

    if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK))
    {
        return "Mon";
    }
    if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK))
    {
        return "Tue";
    }
    if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK))
    {
        return "Wed";
    }
    if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK))
    {
        return "Thu";
    }
    if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK))
    {
        return "Fri";
    }
    if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK))
    {
        return "Sat";
    }
    if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK))
    {
        return "Sun";
    }

    return "Mon";
}
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("weekday", "sun");
        map.put("date", (int)1);

        list.add(map);

        map = new HashMap<String, Object>();
        map.put("weekday", "mon");
        map.put("date", (int)2);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("weekday", "sun");
        map.put("date", (int)1);
        list.add(map);

        return list;
    }
    @TargetApi(Build.VERSION_CODES.N)
    public final class ViewHolder{
        public TextView date;
        public TextView weekday;
        public TextView dec;
    }
    @TargetApi(Build.VERSION_CODES.N)
    void updatelistview(Calendar Cdate){
        int maxday=Cdate.getActualMaximum(Calendar.DATE);
        HashMap<String,String>Daily=myApp.getLabel();
        final ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/
        Toast.makeText(MainActivity.this, getWeekDay(Cdate)+"///"+Cdate.get(Calendar.YEAR)+"年"
                +(Cdate.get(Calendar.MONTH)+1)+"月"
                +Cdate.get(Calendar.DATE), Toast.LENGTH_SHORT).show();
        Calendar tempDate=Cdate;
        String dec;
        Daily=myApp.getLabel();
        if (Daily==null)
            Daily=new HashMap<String,String>();
        for(int i=1;i<=maxday;i++)
        {

            HashMap<String, Object> map = new HashMap<String, Object>();
            Cdate.set(Calendar.DATE,i);
            Date Ddate=Cdate.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
            String date=format1.format(Ddate);
            if(Daily.containsKey(date))
                dec=Daily.get(date);
            else
                dec=null;
            map.put("weekday",  getWeekDay(Cdate));//加入图片            map.put("ItemTitle", "第"+i+"行");
            SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
            map.put("dec", dec);
            map.put("date", Cdate.get(Calendar.DATE));
            map.put("Thedate",format.format(Ddate));
            map.put("Cday",Cdate);
            listItem.add(map);
            Cdate=tempDate;
        }
        saveObject("data");
        SimpleAdapter mSimpleAdapter=new SimpleAdapter(this,listItem,R.layout.customlist,
                new String[]{"weekday","dec","date"},
                new int[]{R.id.list2_weekday,R.id.list2_dec,R.id.list2_date});
        lv.setAdapter(mSimpleAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Calendar daydata= (Calendar) listItem.get(position+1).get("Cday");
                daydata.set(Calendar.DATE,position+1);
                Intent i=new Intent(MainActivity.this,editMode.class);
                Date date=daydata.getTime();
                i.putExtra("passDate",date);
                startActivityForResult(i,0);
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwindow);
        myApp = (MyApp) getApplication();
        if(getObject("data")!=null)
            Daily= (HashMap<String, String>) getObject("data");
        myApp.setLabel(Daily);
        Cdate=Calendar.getInstance();
        year = Cdate.get(Calendar.YEAR);
        month = Cdate.get(Calendar.MONTH);
        day=Cdate.get(Calendar.DAY_OF_MONTH);
        lv= (ListView) findViewById(R.id.lv);
        monthchose=(Spinner)findViewById(R.id.monthchose);
        monthchose.setSelection(Cdate.get(Calendar.MONTH),true);
        yearchose=(Spinner)findViewById(R.id.yearchose);
        yearchose.setSelection(2016-Cdate.get(Calendar.YEAR),true);
        adddairy= (ImageView) findViewById(R.id.add_dairy);
        show_month_dairy= (ImageView) findViewById(R.id.showmonthdairy);
        updatelistview(Cdate);




//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "太难了"+position, Toast.LENGTH_SHORT).show();
//            }
//        });
        // set month and year

        monthchose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Cdate=Calendar.getInstance();
                month=position;
                //Cdate.clear();
                Cdate.set(Calendar.YEAR,year);
                Cdate.set(Calendar.MONTH,month);
                Cdate.set(Calendar.DAY_OF_MONTH,day);
                //Date Ddate=Cdate.getTime();
                //SimpleDateFormat format1 = new SimpleDateFormat("E dd/MM/YYYY");

                updatelistview(Cdate);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothingtodo
            }


        });

        yearchose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] year1 = getResources().getStringArray(R.array.Year);
                //tempyear=(2016-position);
                year=2016-position;
                //Cdate=Calendar.getInstance();
                //Cdate.clear();
                Cdate.set(Calendar.YEAR,year);
                Cdate.set(Calendar.MONTH,month);
                Cdate.set(Calendar.DAY_OF_MONTH,day);

                //Cdate.set(Calendar.MONTH,month);
//                Cdate=Calendar.getInstance();
                Date date=Cdate.getTime();
                updatelistview(Cdate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothingtodo
            }


        });

        adddairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "点我干嘛！", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,editMode.class);
                Cdate=Calendar.getInstance();
                Date date=Cdate.getTime();
                i.putExtra("passDate",date);
                startActivityForResult(i,0);
            }
        });

        show_month_dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "太难了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            updatelistview(Cdate);
    }


}

