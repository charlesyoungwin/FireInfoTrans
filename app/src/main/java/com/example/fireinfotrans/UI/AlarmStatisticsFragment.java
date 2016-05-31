package com.example.fireinfotrans.UI;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.fireinfotrans.R;
import com.example.fireinfotrans.db.FireInfoTransDB;
import com.example.fireinfotrans.db.FireInfoTransOpenHelper;
import com.example.fireinfotrans.model.PowerNode;
import com.example.fireinfotrans.model.PressureNode;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by charlesyoung on 2016/4/22.
 */
public class AlarmStatisticsFragment extends Fragment {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String node;
    private String nodeNumber;
    private int count = 0;

    private TextView textHistoric;
    private TextView textCount;

    private FireInfoTransDB mFireInfoTransDB;
    private SQLiteDatabase db;
    public static final String DB_NAME = "fire_info_trans";
    public static final int VERSION = 1;
    public EditText textNodeNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFireInfoTransDB = FireInfoTransDB.getInstance(getActivity());
        mFireInfoTransDB.saveNodeInfo(getActivity());
        FireInfoTransOpenHelper dbHelper = new FireInfoTransOpenHelper(getActivity(),
                DB_NAME,null,VERSION);
        this.db = dbHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab03,container,false);
        //初始化控件
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_node);
        textNodeNumber = (EditText) v.findViewById(R.id.text_node_number);
        Button buttonQuery = (Button) v.findViewById(R.id.button_query);
        textHistoric = (TextView) v.findViewById(R.id.text_historic);
        textCount = (TextView) v.findViewById(R.id.text_count);

        String[] mItems = getResources().getStringArray(R.array.node);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item ,mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        SpinnerListener spinnerListener = new SpinnerListener();
        spinner.setOnItemSelectedListener(spinnerListener);


        DatePicker datePicker = (DatePicker)
                v.findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker)
                v.findViewById(R.id.timePicker);
        // 获取当前的年、月、日、小时、分钟
        timePicker.setIs24HourView(true);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) ;
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener()
        {

            @Override
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day)
            {
                AlarmStatisticsFragment.this.year = year;
                AlarmStatisticsFragment.this.month = month + 1;
                AlarmStatisticsFragment.this.day = day;
                // 显示当前日期、时间
            }
        });
        // 为TimePicker指定监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {

            @Override
            public void onTimeChanged(TimePicker view
                    , int hourOfDay, int minute)
            {
                AlarmStatisticsFragment.this.hour = hourOfDay;
                AlarmStatisticsFragment.this.minute = minute;
                // 显示当前日期、时间
            }
        });
        Log.w("TAG","test");
        //选择节点的节点号

        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("TAG","click");
                count = 0;
                nodeNumber = textNodeNumber.getText().toString();
                textHistoric.setText("");
                textCount.setText("报警信息统计： ");
                switch (node){
                    case "消防压力":
                        ArrayList<PressureNode> pressureNodeArrayList = new ArrayList<PressureNode>();
                        pressureNodeArrayList = (ArrayList<PressureNode>) queryDatabase(node,nodeNumber,year,month,day,hour,minute);
                        if(pressureNodeArrayList != null) {
                            for(PressureNode pressureNode : pressureNodeArrayList){
                                count++;
                                textHistoric.append(String.valueOf(pressureNode.getPressureId()) + "    " +
                                        String.valueOf(pressureNode.getPressureData()) + "   " +
                                        String.valueOf(pressureNode.getPressureType()) + "   " +
                                        String.valueOf(pressureNode.getPressureDatetime()) + "   " +
                                        String.valueOf(pressureNode.getPressureState())
                                );
                                textHistoric.append("\n");
                                Log.w("TAG",String.valueOf(pressureNode.getPressureData()));
                            }
                        }
                        break;
                    case "消防电源":
                        ArrayList<PowerNode> powerNodeArrayList = new ArrayList<PowerNode>();
                        powerNodeArrayList = (ArrayList<PowerNode>) queryDatabase(node,nodeNumber,year,month,day,hour,minute);
                        if(powerNodeArrayList != null){
                            for(PowerNode powerNode : powerNodeArrayList){
                                count++;
                                textHistoric.append(String.valueOf(powerNode.getPowerId())+"    "+
                                        String.valueOf(powerNode.getPowerA())+  "   " +
                                        String.valueOf(powerNode.getPowerB())+ "   "  +
                                        String.valueOf(powerNode.getPowerC()) + "   " +
                                        String.valueOf(powerNode.getPowerDateTime()) + "    " +
                                        String.valueOf(powerNode.getPowerState())
                                );
                                textHistoric.append("\n");
                            }

                        }
                        break;
                    case "消防开关量":

                        break;
                    case "消防控制柜":

                        break;

                    default:
                        break;
                }
                textCount.append(String.valueOf(count));
            }
        });

        return v;
    }

    public Object queryDatabase(String node,String num,int yearNode,
                                int monthNode, int dayNode, int hourNode, int minuteNode) {

        String yearNodeString, monthNodeString, dayNodeString, hourNodeString, minuteNodeString;
        String hourNodeString2;
        int hourNode2 = hourNode + 1;
        yearNodeString = String.valueOf(yearNode);
        if (monthNode < 10) {
            monthNodeString = "0" + String.valueOf(monthNode);
        } else {
            monthNodeString = String.valueOf(monthNode);
        }
        if (dayNode < 10) {
            dayNodeString = "0" + String.valueOf(dayNode);
        } else {
            dayNodeString = String.valueOf(dayNode);
        }
        if (hourNode < 10) {
            hourNodeString = "0" + String.valueOf(hourNode);
        } else {
            hourNodeString = String.valueOf(hourNode);
        }
        if (hourNode2 < 10) {
            hourNodeString2 = "0" + String.valueOf(hourNode2);
        } else {
            hourNodeString2 = String.valueOf(hourNode2);
        }
        if (minuteNode < 10) {
            minuteNodeString = "0" + String.valueOf(minuteNode);
        } else {
            minuteNodeString = String.valueOf(minuteNode);
        }
        String timeInput = yearNodeString + "-" + monthNodeString + "-" + dayNodeString + " "
                + hourNodeString + ":" + minuteNodeString + ":" + "00";
        String timeEnd = yearNodeString + "-" + monthNodeString + "-" + dayNodeString + " "
                + hourNodeString2 + ":" + minuteNodeString + ":" + "00";
        Log.w("TAG","timeInput"+timeInput);
        Log.w("TAG","timeEnd"+timeEnd);
        switch (node) {
            case "消防压力":
                ArrayList<PressureNode> pressureList = new ArrayList<PressureNode>();
                if (TextUtils.isEmpty(num)) {
                    Log.w("TAG","cursor begin1");
                    Cursor cursor1 = db.rawQuery("select * from pressure_info where datetime between ? and ? and state = ?",
                            new String[]{timeInput, timeEnd, "0"});
                    Log.w("TAG","cursor succeed1");
                    if(cursor1.moveToFirst()){
                        do{
                            PressureNode pressureNode = new PressureNode( cursor1.getInt(0), cursor1.getDouble(1),
                                    cursor1.getInt(2), cursor1.getString(3),cursor1.getInt(4));
                            pressureList.add(pressureNode);
                        }
                        while(cursor1.moveToNext());
                        return pressureList;
                    }
                    cursor1.close();
                } else {
                    Log.w("TAG","cursor begin2");
                    //Cursor cursor2 = db.rawQuery("select * from pressure_info where datetime > ? and " +
                    //      "datetime < ? and id = ?", new String[]{timeInput, timeEnd, num});
                    Log.w("TAG","cursor succeed2");
                    Cursor cursor2 = db.rawQuery("select * from pressure_info where datetime between ? and ?"+
                            "and id = ? and state = ?", new String[]{timeInput, timeEnd, num, "0"});
                    if(cursor2.moveToFirst()){
                        do{
                            PressureNode pressureNode = new PressureNode( cursor2.getInt(0), cursor2.getDouble(1),
                                    cursor2.getInt(2), cursor2.getString(3),cursor2.getInt(4));
                            pressureList.add(pressureNode);
                        }
                        while(cursor2.moveToNext());
                        return pressureList;
                    }
                    cursor2.close();
                }
                break;

            case "消防电源":
                ArrayList<PowerNode> powerList = new ArrayList<PowerNode>();
                if(TextUtils.isEmpty(num)){

                    Cursor cursor1 = db.rawQuery("select * from power_info where datetime between ? and ? and " +
                            "state = ?", new String[]{timeInput, timeEnd, "0"});
                    if(cursor1.moveToFirst()){
                        do{
                            PowerNode powerNode = new PowerNode(cursor1.getInt(0),cursor1.getInt(1),cursor1.getInt(2),
                                    cursor1.getInt(3),cursor1.getString(4),cursor1.getInt(5));
                            powerList.add(powerNode);
                        }
                        while (cursor1.moveToNext());
                        return powerList;
                    }
                    cursor1.close();
                } else{

                    Cursor cursor2 = db.rawQuery("select * from power_info where datetime between ? and ? "+
                            "and id = ? and state = ?", new String[]{timeInput, timeEnd, num, "0"});
                    if(cursor2.moveToFirst()){
                        do{
                            PowerNode powerNode = new PowerNode(cursor2.getInt(0),cursor2.getInt(1),cursor2.getInt(2),
                                    cursor2.getInt(3),cursor2.getString(4),cursor2.getInt(5));
                            powerList.add(powerNode);
                        }
                        while(cursor2.moveToNext());
                        return powerList;
                    }
                    cursor2.close();
                }
                break;

            case "消防开关量":

                break;

            case "消防控制柜":

                break;

            default:
                break;
        }
        return null;
    }

    public class SpinnerListener implements AdapterView.OnItemSelectedListener{


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String[] nodeList = getResources().getStringArray(R.array.node);
            node = nodeList[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
