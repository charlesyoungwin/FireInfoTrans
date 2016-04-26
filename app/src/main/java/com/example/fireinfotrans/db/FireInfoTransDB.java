package com.example.fireinfotrans.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fireinfotrans.model.CabinetNode;
import com.example.fireinfotrans.model.PowerNode;
import com.example.fireinfotrans.model.PressureNode;
import com.example.fireinfotrans.model.RealCabinet;
import com.example.fireinfotrans.model.RealPower;
import com.example.fireinfotrans.model.RealPressure;
import com.example.fireinfotrans.model.RealSwitch;
import com.example.fireinfotrans.model.SwitchNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by charlesyoung on 2016/4/26.
 */
public class FireInfoTransDB {

    /**
     * 数据库名
     */
    public static final String DB_NAME = "fire_info_trans";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static FireInfoTransDB sFireInfoTransDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private FireInfoTransDB(Context context){
        FireInfoTransOpenHelper dbHelper = new FireInfoTransOpenHelper(context,
                DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取FireInfoTransDB的实例
     */
    public synchronized static FireInfoTransDB getInstance(Context context){
        if(sFireInfoTransDB == null) {
            sFireInfoTransDB = new FireInfoTransDB(context);
        }
        return sFireInfoTransDB;
    }




    /**
     * 存储PressureNode实例到数据库
     */
    public void savePressureNode(PressureNode pressureNode){
        if(pressureNode != null){
            ContentValues values = new ContentValues();
            values.put("id",pressureNode.getPressureId());
            values.put("data",pressureNode.getPressureData());
            values.put("type",pressureNode.getPressureDatetime());
            values.put("datetime",pressureNode.getPressureDatetime());
            values.put("state",pressureNode.getPressureState());
            db.insert("pressure_info",null,values);
        }
    }

    /**
     * 更新PressureNode实例到数据库
     */
    public void updatePressureNode(RealPressure realPressure){
        if(realPressure != null){
            ContentValues values = new ContentValues();
            values.put("id",realPressure.getPressureId());
            values.put("data",realPressure.getPressureData());
            values.put("type",realPressure.getPressureDatetime());
            values.put("datetime",realPressure.getPressureDatetime());
            values.put("state",realPressure.getPressureState());
            db.update("real_p_info",values,null,null);
        }
    }

    /**
     * 存储PowerNode实例到数据库
     */
    public void savePowerNode(PowerNode powerNode){
        if(powerNode != null){
            ContentValues values = new ContentValues();
            values.put("id",powerNode.getPowerId());
            values.put("a",powerNode.getPowerA());
            values.put("b",powerNode.getPowerB());
            values.put("c",powerNode.getPowerC());
            values.put("datetime",powerNode.getPowerDateTime());
            values.put("state",powerNode.getPowerState());
            db.insert("power_info",null,values);
        }

    }

    /**
     * 更新PowerNode实例到数据库
     */
    public void updatePowerNode(RealPower realPower){
        if(realPower != null){
            ContentValues values = new ContentValues();
            values.put("id",realPower.getPowerId());
            values.put("a",realPower.getPowerA());
            values.put("b",realPower.getPowerB());
            values.put("c",realPower.getPowerC());
            values.put("datetime",realPower.getPowerDateTime());
            values.put("state",realPower.getPowerState());
            db.update("real_w_info",values,null,null);
        }
    }

    /**
     *  存储SwitchNode实例到数据库
     */
    public void saveSwitchNode(SwitchNode switchNode){
        if(switchNode != null){
            ContentValues values = new ContentValues();
            values.put("id",switchNode.getSwitchId());
            values.put("state",switchNode.getSwitchState());
            values.put("type",switchNode.getSwitchType());
            values.put("datetime",switchNode.getSwitchDatetime());
            db.insert("switch_info",null,values);
        }
    }

    /**
     * 更新SwitchNode实例到数据库
     */
    public void updateSwitchNode(RealSwitch realSwitch){
        if(realSwitch != null){
            ContentValues values = new ContentValues();
            values.put("id",realSwitch.getSwitchId());
            values.put("state",realSwitch.getSwitchState());
            values.put("type",realSwitch.getSwitchType());
            values.put("datetime",realSwitch.getSwitchDatetime());
            db.update("real_s_info",values,null,null);
        }
    }

    /**
     * 存储CabinetNode实例到数据库
     */
    public void saveCabinetNode(CabinetNode cabinetNode){
        if(cabinetNode != null){
            ContentValues values = new ContentValues();
            values.put("id",cabinetNode.getCabinetId());
            values.put("type",cabinetNode.getCabinetBusType());
            values.put("bus_type",cabinetNode.getCabinetBusType());
            values.put("datetime",cabinetNode.getCabinetDatetime());
            db.insert("cabinet_info",null,values);
        }
    }

    /**
     * 更新CabinetNode实例到数据库
     */
    public void updateCabinetNode(RealCabinet realCabinet){
        if(realCabinet != null){
            ContentValues values = new ContentValues();
            values.put("id",realCabinet.getCabinetId());
            values.put("type",realCabinet.getCabinetBusType());
            values.put("bus_type",realCabinet.getCabinetBusType());
            values.put("datetime",realCabinet.getCabinetDatetime());
            db.update("real_c_info",values,null,null);
        }
    }


    /**
     * 从FIRE_NODE.csv中读取数据，并存到数据库
     * @param context
     */
    public void saveNodeInfo(Context context){

        String mCSVFile = "FIRE_NODE.csv";
        AssetManager manager = context.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length != 4) {
                    Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }
                ContentValues values = new ContentValues(3);
                values.put("id", columns[0].trim());
                values.put("location", columns[1].trim());
                values.put("system", columns[2].trim());
                values.put("standard",columns[3].trim());
                db.insert("node_info", null, values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
