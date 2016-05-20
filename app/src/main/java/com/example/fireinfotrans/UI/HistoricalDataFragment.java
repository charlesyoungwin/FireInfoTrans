package com.example.fireinfotrans.UI;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fireinfotrans.R;
import com.example.fireinfotrans.db.FireInfoTransDB;
import com.example.fireinfotrans.db.FireInfoTransOpenHelper;
import com.example.fireinfotrans.model.RealPower;
import com.example.fireinfotrans.model.RealPressure;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by charlesyoung on 2016/4/22.
 */
public class HistoricalDataFragment extends Fragment {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String node;
    private String nodeNumber;

    private TextView textHistoric;

    private FireInfoTransDB mFireInfoTransDB;
    private SQLiteDatabase db;
    public static final String DB_NAME = "fire_info_trans";
    public static final int VERSION = 1;

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
        View v = inflater.inflate(R.layout.tab02,container,false);
        //初始化控件
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_node);
        final EditText textNodeNumber = (EditText) v.findViewById(R.id.text_node_number);
        Button buttonQuery = (Button) v.findViewById(R.id.button_query);
        textHistoric = (TextView) v.findViewById(R.id.text_historic);

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
        month = c.get(Calendar.MONTH);
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
                HistoricalDataFragment.this.year = year;
                HistoricalDataFragment.this.month = month;
                HistoricalDataFragment.this.day = day;
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
                HistoricalDataFragment.this.hour = hourOfDay;
                HistoricalDataFragment.this.minute = minute;
                // 显示当前日期、时间
            }
        });
        Log.w("TAG","test");
        //选择节点的节点号
        nodeNumber = textNodeNumber.getText().toString();
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"click",Toast.LENGTH_SHORT).show();
                Log.w("TAG","click");
                RealPressure realPressure = new RealPressure();
                realPressure = (RealPressure) queryDatabase(node,nodeNumber,year,month,day,hour,minute);
                if(realPressure != null) {
                    textHistoric.setText(String.valueOf(realPressure.getPressureId()));
                }
            }
        });

        return v;
    }


    public Object queryDatabase(String node,String num,int yearNode,
                              int monthNode, int dayNode, int hourNode, int minuteNode) {
        Log.w("TAG","before switch");
        switch (node) {
            case "消防压力":
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
                Log.w("TAG",timeInput);
                if (num == null) {
                    Cursor cursor1 = db.rawQuery("select * from pressure_info where DATETIME(datetime) > ? and" +
                            " DATETIME(datetime) < ?", new String[]{timeInput, timeEnd});
                    while (cursor1.moveToNext()) {
                        RealPressure realPressure1 = new RealPressure(cursor1.getInt(0),cursor1.getDouble(1),cursor1.getInt(2),
                                cursor1.getString(3),cursor1.getInt(4));
                        Log.w("TAG",realPressure1.getPressureDatetime());
                        return realPressure1;

                    }
                } else {
                    Cursor cursor2 = db.rawQuery("select * from pressure_info where DATETIME(datetime) > ? and " +
                            "DATETIME(datetime) < ? and id = ?", new String[]{timeInput, timeEnd, num});
                    while(cursor2.moveToNext()){
                        RealPressure realPressure2 = new RealPressure(cursor2.getInt(0),cursor2.getDouble(1),cursor2.getInt(2),
                                cursor2.getString(3),cursor2.getInt(4));
                        return realPressure2;
                    }

                }
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
            Toast.makeText(getActivity(),node,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
