package com.example.fireinfotrans;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.fireinfotrans.db.FireInfoTransDB;
import com.example.fireinfotrans.db.FireInfoTransOpenHelper;
import com.example.fireinfotrans.model.CabinetNode;
import com.example.fireinfotrans.model.PowerNode;
import com.example.fireinfotrans.model.PressureNode;
import com.example.fireinfotrans.model.RealCabinet;
import com.example.fireinfotrans.model.RealPower;
import com.example.fireinfotrans.model.RealPressure;
import com.example.fireinfotrans.model.RealSwitch;
import com.example.fireinfotrans.model.SwitchNode;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by charlesyoung on 2016/4/23.
 */
public class PressureActivity extends AppCompatActivity{

    private TextView textData1;
    private TextView textData2;
    private TextView textData3;
    private TextView textData4;
    private TextView textData5;
    private TextView textData6;
    private TextView textData7;
    private TextView textData8;
    private TextView textData9;
    private TextView textData10;
    private TextView textData11;
    private TextView textData12;
    private TextView textData13;
    private TextView textData14;
    private TextView textData15;
    private TextView textData16;

    private FireInfoTransDB mFireInfoTransDB;

    private final String TAG = "pressureActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure);
        mFireInfoTransDB = FireInfoTransDB.getInstance(this);
        initView();

    }

    public void initView(){

        textData1 = (TextView) findViewById(R.id.data_1);
        textData2 = (TextView) findViewById(R.id.data_2);
        textData3 = (TextView) findViewById(R.id.data_3);
        textData4 = (TextView) findViewById(R.id.data_4);
        textData5 = (TextView) findViewById(R.id.data_5);
        textData6 = (TextView) findViewById(R.id.data_6);
        textData7 = (TextView) findViewById(R.id.data_7);
        textData8 = (TextView) findViewById(R.id.data_8);
        textData9 = (TextView) findViewById(R.id.data_9);
        textData10 = (TextView) findViewById(R.id.data_10);
        textData11 = (TextView) findViewById(R.id.data_11);
        textData12 = (TextView) findViewById(R.id.data_12);
        textData13 = (TextView) findViewById(R.id.data_13);
        textData14 = (TextView) findViewById(R.id.data_14);
        textData15 = (TextView) findViewById(R.id.data_15);
        textData16 = (TextView) findViewById(R.id.data_16);

        int pressureId1 = Integer.parseInt(this.getString(R.string.pressure_id_1));
        int pressureId2 = Integer.parseInt(this.getString(R.string.pressure_id_2));
        int pressureId3 = Integer.parseInt(this.getString(R.string.pressure_id_3));
        int pressureId4 = Integer.parseInt(this.getString(R.string.pressure_id_4));
        int pressureId5 = Integer.parseInt(this.getString(R.string.pressure_id_5));
        int pressureId6 = Integer.parseInt(this.getString(R.string.pressure_id_6));
        int pressureId7 = Integer.parseInt(this.getString(R.string.pressure_id_7));
        int pressureId8 = Integer.parseInt(this.getString(R.string.pressure_id_8));
        int pressureId9 = Integer.parseInt(this.getString(R.string.pressure_id_9));
        int pressureId10 = Integer.parseInt(this.getString(R.string.pressure_id_10));
        int pressureId11 = Integer.parseInt(this.getString(R.string.pressure_id_11));
        int pressureId12 = Integer.parseInt(this.getString(R.string.pressure_id_12));
        int pressureId13 = Integer.parseInt(this.getString(R.string.pressure_id_13));
        int pressureId14 = Integer.parseInt(this.getString(R.string.pressure_id_14));
        int pressureId15 = Integer.parseInt(this.getString(R.string.pressure_id_15));
        int pressureId16 = Integer.parseInt(this.getString(R.string.pressure_id_16));


        displayData(pressureId1,textData1);
        displayData(pressureId2,textData2);
        displayData(pressureId3,textData3);
        displayData(pressureId4,textData4);
        displayData(pressureId5,textData5);
        displayData(pressureId6,textData6);
        displayData(pressureId7,textData7);
        displayData(pressureId8,textData8);
        displayData(pressureId9,textData9);
        displayData(pressureId10,textData10);
        displayData(pressureId11,textData11);
        displayData(pressureId12,textData12);
        displayData(pressureId13,textData13);
        displayData(pressureId14,textData14);
        displayData(pressureId15,textData15);
        displayData(pressureId16,textData16);



    }

    public void displayData(int pressureId, TextView textData){

        RealPressure realPressure = mFireInfoTransDB.queryPressureNode(pressureId);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = sDateFormat.format(new java.util.Date());

        //Log.d(TAG,currentTime);
        try {
            String datetime;
            if(realPressure != null){
                datetime = realPressure.getPressureDatetime();
            }else{
                datetime = "2015-05-04 00:00:00";
            }
            Date currentTime2 = sDateFormat.parse(currentTime);
            Date dateTime2 = sDateFormat.parse(datetime);
            long diffTime = currentTime2.getTime() - dateTime2.getTime();
            long diffSeconds = diffTime / 1000;

            Log.d(TAG,String.valueOf(diffSeconds));
            if(diffSeconds < 60*60*24){
                DecimalFormat df = new DecimalFormat("#.###");
                String text = String.valueOf(df.format
                        (realPressure.getPressureData())) + "MPa";
                textData.setText(text);
                if(realPressure.getPressureState() == 1){
                    textData.setTextColor(Color.parseColor("#808080"));
                } else {
                    textData.setTextColor(Color.parseColor("#FF0000"));
                }
            } else {
                String text = " -- " + "MPa";
                textData.setText(text);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
