package com.match.developer.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Author:Tanner
 * Time:2018/7/12 17:17
 * Description: This is BasePresenter
 */
public class BasePresenter<T> {
    protected Reference<T> mViewRef;

    public BasePresenter(T view) {
        this.mViewRef = new WeakReference<>(view);

    }

    public T getmViewRef(){
        if (null != mViewRef) {
            return mViewRef.get();
        }
        return null;
    }
}
