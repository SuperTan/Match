package com.match.developer.presenter.impl;

import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.match.developer.R;
import com.match.developer.model.User;
import com.match.developer.presenter.BasePresenter;
import com.match.developer.ui.activity.LoginActivity;
import com.match.developer.ui.activity.MainActivity;
import com.match.developer.ui.activity.SplashActivity;
import com.match.developer.ui.iView.ILoginView;
import com.match.developer.util.EmptyUtils;
import com.match.developer.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Author:Tanner
 * Time:2018/7/16 15:52
 * Description: This is LoginPresenter
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    public LoginPresenter(ILoginView view) {
        super(view);
    }

    public void login() {
        String name = getmViewRef().getName();


        final String pwd = getmViewRef().getPwd();
        if (getmViewRef().emptyName() || getmViewRef().emptyPwd()) {
            return;
        }
        BmobQuery<User> query1 = new BmobQuery<User>();
        query1.addWhereEqualTo("name", name);
        BmobQuery<User> query2 = new BmobQuery<User>();
        query2.addWhereEqualTo("phone", name);

        List<BmobQuery<User>> queries = new ArrayList<>();
        queries.add(query1);
        queries.add(query2);

        BmobQuery<User> mainQuery = new BmobQuery<>();
        mainQuery.or(queries);
        mainQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    boolean flag = true;
                    for (User user : list) {
                        if (user.getPwd().equals(pwd)) {
                            flag = false;
                            MainActivity.user = user;
                            //登录成功
                            getmViewRef().saveUserInfo(user);
                            getmViewRef().loginSuccess();
                            break;
                        }
                    }
                    if (flag) {
                        getmViewRef().loginFail(false);
                    }
                } else {
                    getmViewRef().loginFail(true);

                }
            }
        });
    }
}
