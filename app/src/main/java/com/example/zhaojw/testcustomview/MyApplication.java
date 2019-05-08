package com.example.zhaojw.testcustomview;

import android.app.Application;
import android.util.Log;

/**
 * Created by zhaojianwu on 2018/8/29.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("zhaojianwu", "MyApplication onCreate()");
    }
}
