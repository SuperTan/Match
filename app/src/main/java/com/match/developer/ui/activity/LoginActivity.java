package com.match.developer.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.match.developer.R;
import com.match.developer.model.User;
import com.match.developer.presenter.impl.LoginPresenter;
import com.match.developer.ui.iView.ILoginView;
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

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {

    @BindView(R.id.id_login_username)
    EditText mUserName;
    @BindView(R.id.id_login_password)
    EditText mPassWord;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.id_login_login)
    public void toLogin() {
        mPresenter.login();
    }

    @Override
    public String getName() {
        return mUserName.getText().toString();
    }

    @Override
    public String getPwd() {
        return mPassWord.getText().toString();
    }

    @Override
    public boolean emptyName() {
        if (EmptyUtils.isEmpty(getName())) {
            MyUtils.showToast(getResources().getString(R.string.name_null));
            return true;
        }
        return false;
    }

    @Override
    public boolean emptyPwd() {
        if (EmptyUtils.isEmpty(getPwd())) {
            MyUtils.showToast(getResources().getString(R.string.pwd_null));
            return true;
        }
        return false;
    }

    @Override
    public void saveUserInfo(User user) {
        //保存用户数据到本地
        SharedPreferences preferences = getSharedPreferences(SplashActivity.PREF_USER, MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(SplashActivity.PREF_USER_KEY, new Gson().toJson(user));
        edit.commit();
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        LoginActivity.this.finish();
    }

    @Override
    public void loginFail(boolean flag) {
        if (flag) {
            MyUtils.showToast(getResources().getString(R.string.name_err));
        } else {
            MyUtils.showToast(getResources().getString(R.string.pwd_err));
        }
    }
}
