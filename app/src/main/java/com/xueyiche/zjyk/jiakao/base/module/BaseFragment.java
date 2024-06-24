package com.xueyiche.zjyk.jiakao.base.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.xueyiche.zjyk.jiakao.base.view.BaseProgressDialog;
import com.xueyiche.zjyk.jiakao.base.view.LoadingPager;
import com.xueyiche.zjyk.jiakao.constants.App;

/**
 * Fragment的基类
 */
public abstract class BaseFragment extends Fragment {
    protected LoadingPager loadingPager;
    protected Context mContext;
    protected boolean isVisible;
    private InputMethodManager manager;
    private BaseProgressDialog mProgressDialog = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (loadingPager == null) {
            loadingPager = new LoadingPager(mContext) {
                @Override
                public Object loadDate() {
                    return setLoadDate();
                }

                @Override
                public View initView() {
                    return setInitView();
                }
            };
        }
        //在Fragment里点击软键盘的空白处消失
        BaseActivity.MyTouchListener myTouchListener = new BaseActivity.MyTouchListener() {
            @Override
            public void onTouchEvent(MotionEvent event) {
                // 处理手势事件
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        };

        // 将myTouchListener注册到分发列表
        ((BaseActivity) this.getActivity()).registerMyTouchListener(myTouchListener);
        return loadingPager;
    }

        @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }



    /**
     * @author wulee
     */

    public void showProgressDialog(Activity activity, BaseProgressDialog.OnCancelListener cancelListener, boolean cancelable, String msg) {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = new BaseProgressDialog(activity, msg);
        if (cancelListener != null) {
            mProgressDialog.setOnCancelListener(cancelListener);
        }
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    public void showProgressDialog(Activity activity, boolean cancelable, String msg) {
        showProgressDialog(activity, null, cancelable, msg);
    }

    public void showProgressDialog(Activity activity, boolean cancelable) {
        showProgressDialog(activity, cancelable, "");
    }

    public void stopProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.stop();
        }
        mProgressDialog = null;
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract View setInitView();

    protected abstract Object setLoadDate();

    @Override
    public void onPause() {
        super.onPause();
    }

    //短Toast
    protected void showToastShort(String Msg) {
        Toast.makeText(App.context, Msg, Toast.LENGTH_SHORT).show();
    }

    //长Toast
    protected void showToastLong(String Msg) {
        Toast.makeText(App.context, Msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 界面跳转
     *
     * @param clazz 目标Activity
     */
    protected void openActivity(Class<?> clazz) {
        readyGo(clazz, null);
    }

    /**
     * 跳转界面，  传参
     *
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }


}
