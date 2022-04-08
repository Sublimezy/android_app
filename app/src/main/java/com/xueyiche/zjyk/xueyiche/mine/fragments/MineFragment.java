package com.xueyiche.zjyk.xueyiche.mine.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * Created by Owner on 2016/9/21.
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.mine_head)
    CircleImageView mineHead;
    @BindView(R.id.tv_mine_title)
    TextView tvMineTitle;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.iv_mine_setting)
    ImageView ivMineSetting;
    @BindView(R.id.ll_my_order)
    LinearLayout llMyOrder;
    @BindView(R.id.ll_changyong_dizhi)
    LinearLayout llChangyongDizhi;
    @BindView(R.id.ll_siji_baoming)
    LinearLayout llSijiBaoming;
    @BindView(R.id.ll_shared_app)
    LinearLayout llSharedApp;
    @BindView(R.id.ll_kefu)
    LinearLayout llKefu;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;

    public static MineFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        MineFragment fragment = new MineFragment();
        bundle.putString("mine", tag);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        EventBus.getDefault().register(this);
        View viewHead = LayoutInflater.from(App.context).inflate(R.layout.mine_fragment_two_new, null);
        ButterKnife.bind(viewHead);
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
        if (DialogUtils.IsLogin()) {

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.mine_head, R.id.tv_mine_title, R.id.tv_mine_name, R.id.iv_mine_setting, R.id.ll_my_order, R.id.ll_changyong_dizhi, R.id.ll_siji_baoming, R.id.ll_shared_app, R.id.ll_kefu, R.id.ll_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_head:
                break;
            case R.id.tv_mine_title:
                break;
            case R.id.tv_mine_name:
                break;
            case R.id.iv_mine_setting:
                break;
            case R.id.ll_my_order:
                break;
            case R.id.ll_changyong_dizhi:
                break;
            case R.id.ll_siji_baoming:
                break;
            case R.id.ll_shared_app:
                break;
            case R.id.ll_kefu:
                break;
            case R.id.ll_about:
                break;
        }
    }
}
