package com.example.fireinfotrans.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by charlesyoung on 2016/4/26.
 */
public class FireInfoTransOpenHelper extends SQLiteOpenHelper {

    /**
     * node_info建表语句
     */
    public static final String CREATE_NODE_INFO = "create table node_info ("
            + "id integer primary key,"
            + "location text,"
            + "system text,"
            + "standard text)";

    /**
     * pressure_info建表语句
     */
    public static final String CREATE_PRESSURE_INFO = "create table pressure_info ("
            + "id integer primary key,"
            + "data real,"
            + "type integer,"
            + "datetime text,"
            + "state integer default(0))";

    /**
     * real_p_info建表语句
     */
    public static final String CREATE_REAL_P_INFO = "create table real_p_info ("
            + "id integer primary key,"
            + "data real,"
            + "type integer,"
            + "datetime text,"
            + "state integer default(0))";

    /**
     * power_info建表语句
     */
    public static final String CREATE_POWER_INFO = "create table power_info ("
            + "id integer primary key,"
            + "a integer,"
            + "b integer,"
            + "c integer,"
            + "datetime text,"
            + "state integer default(0))";

    /**
     * real_w_info建表语句
     */
    public static final String CREATE_REAL_W_INFO = "create table real_w_info ("
            + "id integer primary key,"
            + "a integer,"
            + "b integer,"
            + "c integer,"
            + "datetime text,"
            + "state integer default(0))";

    /**
     * switch_info建表语句
     */
    public static final String CREATE_SWITCH_INFO = "create table switch_info ("
            + "id integer primary key,"
            + "state integer,"
            + "type integer,"
            + "datetime text)";

    /**
     * real_s_info建表语句
     */
    public static final String CREATE_REAL_S_INFO = "create table real_s_info ("
            + "id integer primary key,"
            + "state integer,"
            + "type integer,"
            + "datetime text)";

    /**
     * cabinet_info建表语句
     */
    public static final String CREATE_CABINET_INFO = "create table cabinet_info ("
            + "id integer primary key,"
            + "type text,"
            + "bus_type text,"
            + "datetime text)";

    /**
     * real_c_info建表语句
     */
    public static final String CREATE_REAL_C_INFO = "create table real_c_info ("
            + "id integer primary key,"
            + "type text,"
            + "bus_type text,"
            + "datetime text)";



    public FireInfoTransOpenHelper(Context context, String name,
                                   SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    //建表
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NODE_INFO);
        db.execSQL(CREATE_PRESSURE_INFO);
        db.execSQL(CREATE_REAL_P_INFO);
        db.execSQL(CREATE_POWER_INFO);
        db.execSQL(CREATE_REAL_W_INFO);
        db.execSQL(CREATE_SWITCH_INFO);
        db.execSQL(CREATE_REAL_S_INFO);
        db.execSQL(CREATE_CABINET_INFO);
        db.execSQL(CREATE_REAL_C_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
