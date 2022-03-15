package com.xueyiche.zjyk.xueyiche.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

/**
 * Created by Zhanglei on 2017/8/1.
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction()==MotionEvent.ACTION_MOVE) {
            return true;
        }return super.dispatchTouchEvent(ev);
    }

    /**
     * int型的MeasureSpec来表示一个组件的大小
     * 在系统中组件的大小模式有三种:
     * 精确模式（MeasureSpec.EXACTLY）
     * 最大模式（MeasureSpec.AT_MOST）
     * 未指定模式（MeasureSpec.UNSPECIFIED）
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }


}