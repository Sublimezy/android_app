package com.xueyiche.zjyk.xueyiche.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Owner on 2017/8/1.
 */
public class AdListView extends ListView{
    public AdListView(Context context) {
        super(context);
    }

    public AdListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
