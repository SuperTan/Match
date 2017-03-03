package com.match.developer.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.match.developer.R;
import com.match.developer.model.User;
import com.match.developer.util.MyUtils;

import static com.match.developer.ui.MainActivity.user;

public class SplashActivity extends AppCompatActivity {

    public static String PREF_USER = "PREF_USER";
    public static String PREF_USER_KEY = "PREF_USER_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences = getSharedPreferences(PREF_USER, MODE_PRIVATE);
        String string = preferences.getString(PREF_USER_KEY, "");
        user = MyUtils.sGson.fromJson(string, User.class);
        Intent intent = new Intent();
        if (user == null) {
            intent.setClass(SplashActivity.this, LoginActivity.class);
        } else {
            intent.setClass(SplashActivity.this, MainActivity.class);
        }
        startActivity(intent);
        SplashActivity.this.finish();
    }
}
