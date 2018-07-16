package com.match.developer.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * Author:Tanner
 * Time:2018/7/16 17:07
 * Description: This is IconfontTextView
 */
public class IconfontTextView extends AppCompatTextView {
    public IconfontTextView(Context context) {
        this(context, null);
    }

    public IconfontTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconfontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Typeface fromAsset = IconfontFace.getTypeFace(context);
        setTypeface(fromAsset);
    }

    public static class IconfontFace {
        private static Typeface typeface = null;

        public static synchronized Typeface getTypeFace(Context context) {
            if (typeface == null) {
                try {
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iconfont.ttf");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return typeface;
        }
    }


}
