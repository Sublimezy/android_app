package com.xueyiche.zjyk.xueyiche.homepage.view;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 */
public class XYCSwipeRefreshLayout extends SwipeRefreshLayout {
    private View view;

    public XYCSwipeRefreshLayout(Context context) {
        super(context);
    }

    public XYCSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewGroup(View view) {
        this.view = view;
    }

    @Override
    public boolean canChildScrollUp() {
        if (view != null && view instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) view;
            return absListView.getChildCount() > 0
                    && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                    .getTop() < absListView.getPaddingTop());
        } else if (view != null && view instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) view;

            return scrollView.getChildCount() > 0
                    && scrollView.getScrollY() != 0;
        } else if (view != null && view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            return recyclerView.getChildCount() > 0
                    && recyclerView.getScrollY() != 0;
        }
        return super.canChildScrollUp();
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(View.VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing",
                    boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
