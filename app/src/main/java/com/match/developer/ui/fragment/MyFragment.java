package com.match.developer.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.match.developer.R;
import com.match.developer.ui.LoginActivity;
import com.match.developer.ui.SplashActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tanner on 2017/3/1.
 * 我
 */

public class MyFragment extends Fragment{
    FragmentActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container,false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        return view;
    }
    
    @OnClick(R.id.id_my_exit)
    public void toExit(){
        SharedPreferences preferences = activity.getSharedPreferences(SplashActivity.PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(SplashActivity.PREF_USER_KEY, "");
        edit.commit();
        startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }
}
