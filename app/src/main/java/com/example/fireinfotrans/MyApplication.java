package com.example.fireinfotrans;

import android.app.Application;
import android.content.Context;

/**
 * Created by charlesyoung on 2016/4/27.
 */
public class MyApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext(){
        return sContext;
    }
}
