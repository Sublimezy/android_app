package com.xueyiche.zjyk.xueyiche.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.drawerlayout.widget.DrawerLayout;

/**
 */
public class MyDrawerLayout extends DrawerLayout {

    public MyDrawerLayout(Context context){
        this(context, null);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle);
    }




    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        return false;
    }

}


