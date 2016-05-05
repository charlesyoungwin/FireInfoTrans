package com.example.fireinfotrans;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.fireinfotrans.db.FireInfoTransDB;
import com.example.fireinfotrans.model.RealPower;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by charlesyoung on 2016/5/4.
 */
public class PowerActivity extends AppCompatActivity {


    private FireInfoTransDB mFireInfoTransDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);
        mFireInfoTransDB = FireInfoTransDB.getInstance(this);
        initView();
    }

    public void initView(){

        TextView textPowerA1 = (TextView) findViewById(R.id.powerA1);
        TextView textPowerA2 = (TextView) findViewById(R.id.powerA2);
        TextView textPowerA3 = (TextView) findViewById(R.id.powerA3);
        TextView textPowerA4 = (TextView) findViewById(R.id.powerA4);
        TextView textPowerA5 = (TextView) findViewById(R.id.powerA5);
        TextView textPowerA6 = (TextView) findViewById(R.id.powerA6);
        TextView textPowerA7 = (TextView) findViewById(R.id.powerA7);
        TextView textPowerA8 = (TextView) findViewById(R.id.powerA8);
        TextView textPowerA9 = (TextView) findViewById(R.id.powerA9);

        TextView textPowerB1 = (TextView) findViewById(R.id.powerB1);
        TextView textPowerB2 = (TextView) findViewById(R.id.powerB2);
        TextView textPowerB3 = (TextView) findViewById(R.id.powerB3);
        TextView textPowerB4 = (TextView) findViewById(R.id.powerB4);
        TextView textPowerB5 = (TextView) findViewById(R.id.powerB5);
        TextView textPowerB6 = (TextView) findViewById(R.id.powerB6);
        TextView textPowerB7 = (TextView) findViewById(R.id.powerB7);
        TextView textPowerB8 = (TextView) findViewById(R.id.powerB8);
        TextView textPowerB9 = (TextView) findViewById(R.id.powerB9);

        TextView textPowerC1 = (TextView) findViewById(R.id.powerC1);
        TextView textPowerC2 = (TextView) findViewById(R.id.powerC2);
        TextView textPowerC3 = (TextView) findViewById(R.id.powerC3);
        TextView textPowerC4 = (TextView) findViewById(R.id.powerC4);
        TextView textPowerC5 = (TextView) findViewById(R.id.powerC5);
        TextView textPowerC6 = (TextView) findViewById(R.id.powerC6);
        TextView textPowerC7 = (TextView) findViewById(R.id.powerC7);
        TextView textPowerC8 = (TextView) findViewById(R.id.powerC8);
        TextView textPowerC9 = (TextView) findViewById(R.id.powerC9);

        int powerId1 = Integer.parseInt(this.getString(R.string.power_id_1));
        int powerId2 = Integer.parseInt(this.getString(R.string.power_id_2));
        int powerId3 = Integer.parseInt(this.getString(R.string.power_id_3));
        int powerId4 = Integer.parseInt(this.getString(R.string.power_id_4));
        int powerId5 = Integer.parseInt(this.getString(R.string.power_id_5));
        int powerId6 = Integer.parseInt(this.getString(R.string.power_id_6));
        int powerId7 = Integer.parseInt(this.getString(R.string.power_id_7));
        int powerId8 = Integer.parseInt(this.getString(R.string.power_id_8));
        int powerId9 = Integer.parseInt(this.getString(R.string.power_id_9));

        displayData(powerId1, textPowerA1, textPowerB1, textPowerC1);
        displayData(powerId2, textPowerA2, textPowerB2, textPowerC2);
        displayData(powerId3, textPowerA3, textPowerB3, textPowerC3);
        displayData(powerId4, textPowerA4, textPowerB4, textPowerC4);
        displayData(powerId5, textPowerA5, textPowerB5, textPowerC5);
        displayData(powerId6, textPowerA6, textPowerB6, textPowerC6);
        displayData(powerId7, textPowerA7, textPowerB7, textPowerC7);
        displayData(powerId8, textPowerA8, textPowerB8, textPowerC8);
        displayData(powerId9, textPowerA9, textPowerB9, textPowerC9);

    }

    public void displayData(int powerId, TextView textPower1, TextView textPower2,
                            TextView textPower3){

        RealPower realPower = mFireInfoTransDB.queryPowerNode(powerId);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = sDateFormat.format(new java.util.Date());
        try {
            String datetime;
            if(realPower != null){
                datetime = realPower.getPowerDateTime();
            } else{
                datetime = "2015-05-04 00:00:00";
            }
            Date currentTime2 = sDateFormat.parse(currentTime);
            Date dateTime2 = sDateFormat.parse(datetime);
            long diffTime = currentTime2.getTime() - dateTime2.getTime();
            long diffSeconds = diffTime / 1000;
            if(diffSeconds < 60*60*24){
                textPower1.setText(String.valueOf(realPower.getPowerA()));
                textPower2.setText(String.valueOf(realPower.getPowerB()));
                textPower3.setText(String.valueOf(realPower.getPowerC()));
                if(realPower.getPowerState() == 1){
                    textPower1.setTextColor(Color.parseColor("#808080"));
                    textPower2.setTextColor(Color.parseColor("#808080"));
                    textPower3.setTextColor(Color.parseColor("#808080"));
                } else {
                    textPower1.setTextColor(Color.parseColor("#FF0000"));
                    textPower2.setTextColor(Color.parseColor("#FF0000"));
                    textPower3.setTextColor(Color.parseColor("#FF0000"));
                }
            } else {
                textPower1.setText("--");
                textPower2.setText("--");
                textPower3.setText("--");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
