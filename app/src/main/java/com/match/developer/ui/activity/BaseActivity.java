package com.match.developer.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.match.developer.presenter.BasePresenter;
import com.match.developer.ui.iView.IView;
import com.match.developer.util.ActivityCollector;

import butterknife.ButterKnife;

/**
 * Author:Tanner
 * Time:2018/7/16 15:39
 * Description: This is BaseAcitity
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(getContentViewId(), getViewGroupRoot());
        setContentView(view);
        ActivityCollector.addActivity(this);

        ButterKnife.bind(this);
        initPresenter();
        initSubViews(view);
        initData();
    }

    private void initPresenter() {
        mPresenter = getPresenter();
    }

    protected abstract T getPresenter();

    @Override
    public abstract ViewGroup getViewGroupRoot();

    @Override
    public abstract int getContentViewId();

    @Override
    public abstract void initSubViews(View view);

    @Override
    public abstract void initData();
}
