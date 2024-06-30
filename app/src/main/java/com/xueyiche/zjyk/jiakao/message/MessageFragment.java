package com.xueyiche.zjyk.jiakao.message;

import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.constants.event.MyEvent;


import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class MessageFragment extends BaseFragment {

    public static MessageFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        MessageFragment fragment = new MessageFragment();
        bundle.putString("message", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        showToastShort("消息");
    }

    @Override
    protected View setInitView() {
        EventBus.getDefault().register(this);
        View viewHead = LayoutInflater.from(App.context).inflate(R.layout.fragment_message, null);
        ButterKnife.bind(this, viewHead);
        return viewHead;
    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if ("刷新FragmentLogin".equals(msg)) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    String nickname11;

    //Fragment 类中的一个回调方法，用于通知 Fragment 当前是否被隐藏或显示。
// 当 Fragment 被添加到 Activity 中并且其可见性发生变化时，系统会调用这个方法。
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}