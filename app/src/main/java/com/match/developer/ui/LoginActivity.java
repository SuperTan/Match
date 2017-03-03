package com.match.developer.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.match.developer.R;
import com.match.developer.model.User;
import com.match.developer.util.EmptyUtils;
import com.match.developer.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.id_login_username)
    EditText mUserName;
    @BindView(R.id.id_login_password)
    EditText mPassWord;
    @BindView(R.id.id_login_login)
    TextView mLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.id_login_login)
    public void toLogin() {
        String name = mUserName.getText().toString();
        final String pwd = mPassWord.getText().toString();
        if (EmptyUtils.isEmpty(name)) {
            MyUtils.showToast(getResources().getString(R.string.name_null));
            return;
        }
        if (EmptyUtils.isEmpty(pwd)) {
            MyUtils.showToast(getResources().getString(R.string.pwd_null));
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
                boolean flag = false;
                if (e == null && list.size() > 0) {
                    for (User user : list) {
                        if (user.getPwd().equals(pwd)) {
                            flag = true;
                            MainActivity.user = user;
                            //登录成功
                            //保存用户数据到本地
                            SharedPreferences preferences = getSharedPreferences(SplashActivity.PREF_USER, MODE_PRIVATE);
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putString(SplashActivity.PREF_USER_KEY, new Gson().toJson(user));
                            edit.commit();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                            break;
                        }
                    }
                    if (!flag) {
                        MyUtils.showToast(getResources().getString(R.string.pwd_err));
                    }
                } else {
                    MyUtils.showToast(getResources().getString(R.string.name_err));
                }
            }
        });
    }
}
