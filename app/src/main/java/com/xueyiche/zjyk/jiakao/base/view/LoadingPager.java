package com.xueyiche.zjyk.jiakao.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.constants.App;

public abstract class LoadingPager extends FrameLayout {

    public static final int PAGER_LOADING = 0;
    public static final int PAGER_FAILED = 1;
    public static final int PAGER_SUCCESS = 2;

    private int currentState;

    private View loadingView;
    private View failedView;
    private View successView;

    private Button btn_reload;

    public LoadingPager(Context context) {
        this(context, null);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        currentState = PAGER_LOADING;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        loadingView = View.inflate(getContext(), R.layout.page_loading, null);
        failedView = View.inflate(getContext(), R.layout.page_error, null);
        btn_reload = (Button) failedView.findViewById(R.id.btn_reload);
        reload();
        successView = initView();
        if (successView == null) {
            successView = new View(App.context);
        }
        addView(loadingView, params);
        addView(failedView, params);
        addView(successView, params);
        showPager();
        updatePager();
    }

    private void reload() {
        btn_reload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState = PAGER_LOADING;
                showPager();
                updatePager();
            }
        });
    }

    public void updatePager() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               // SystemClock.sleep(800);
                Object obj = loadDate();

                currentState = obj == null ? PAGER_FAILED : PAGER_SUCCESS;

              App. handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showPager();
                    }
                });
            }
        }).start();
    }


    private void showPager() {
        loadingView.setVisibility(currentState == PAGER_LOADING ? VISIBLE : INVISIBLE);
        failedView.setVisibility(currentState == PAGER_FAILED ? VISIBLE : INVISIBLE);
        successView.setVisibility(currentState == PAGER_SUCCESS ? VISIBLE : INVISIBLE);
    }

    public abstract Object loadDate();

    public abstract View initView();


}
