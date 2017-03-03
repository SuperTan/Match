package com.match.developer.base;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Tanner on 2017/3/1.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        instance=this;
        Bmob.initialize(this, "e6f9a937e89463205c0c79d41856e677");
        super.onCreate();
    }
    public static MyApplication getInstance(){
        return instance;
    }
}
