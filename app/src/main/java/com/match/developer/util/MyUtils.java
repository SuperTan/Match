package com.match.developer.util;

import android.widget.Toast;

import com.google.gson.Gson;
import com.match.developer.base.MyApplication;

/**
 * Created by Tanner on 2017/3/3.
 */

public class MyUtils {

    public static Gson sGson = new Gson();

    public static String COST_TYPE_IN="in";
    public static String COST_TYPE_OUT="out";
    public static String COST_TYPE_OUT_FOOD="FOOD";
    public static String COST_TYPE_OUT_WEN="WEN";

    public static void showToast(String msg) {
        Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
