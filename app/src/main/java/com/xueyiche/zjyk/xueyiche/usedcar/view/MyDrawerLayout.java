package com.xueyiche.zjyk.xueyiche.usedcar.view;

import android.content.Context;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

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


