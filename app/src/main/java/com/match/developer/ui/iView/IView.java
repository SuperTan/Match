package com.match.developer.ui.iView;

import android.view.View;
import android.view.ViewGroup;

/**
 * Author:Tanner
 * Time:2018/7/16 15:37
 * Description: This is IView
 */
public interface IView {

    ViewGroup getViewGroupRoot();

    int getContentViewId();

    void initSubViews(View view);

    void initData();
}
