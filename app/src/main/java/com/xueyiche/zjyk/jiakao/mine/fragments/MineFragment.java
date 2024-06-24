package com.xueyiche.zjyk.jiakao.mine.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.constants.AppUrl;
import com.xueyiche.zjyk.jiakao.constants.StringConstants;
import com.xueyiche.zjyk.jiakao.constants.event.MyEvent;
import com.xueyiche.zjyk.jiakao.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.jiakao.mine.activities.AboutXueYiCheActivity;
import com.xueyiche.zjyk.jiakao.mine.activities.bean.MineCenterBean;
import com.xueyiche.zjyk.jiakao.mine.activities.bianji.EditMySelfInfoActivity;
import com.xueyiche.zjyk.jiakao.mine.activities.bianji.SetActivity;
import com.xueyiche.zjyk.jiakao.mine.view.CircleImageView;
import com.xueyiche.zjyk.jiakao.myhttp.LogUtils;
import com.xueyiche.zjyk.jiakao.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.jiakao.myhttp.RequestCallBack;
import com.xueyiche.zjyk.jiakao.utils.DialogUtils;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;
import com.xueyiche.zjyk.jiakao.utils.XueYiCheUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;



public class MineFragment extends BaseFragment {

    @BindView(R.id.mine_head)
    CircleImageView mineHead;
    @BindView(R.id.tv_mine_title)
    TextView tvMineTitle;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.iv_mine_setting)
    ImageView ivMineSetting;
    @BindView(R.id.ll_shared_app)
    LinearLayout llSharedApp;
    @BindView(R.id.ll_kefu)
    LinearLayout llKefu;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
     String avatar11;

    public static MineFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        MineFragment fragment = new MineFragment();
        bundle.putString("mine", tag);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
        showToastShort("我的");
    }

    @Override
    protected View setInitView() {
        EventBus.getDefault().register(this);
        View viewHead = LayoutInflater.from(App.context).inflate(R.layout.mine_fragment_two_new, null);
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
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.mine_head, R.id.tv_mine_title, R.id.tv_mine_name, R.id.iv_mine_setting
            , R.id.ll_shared_app, R.id.ll_kefu, R.id.ll_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_head:
//                if (!DialogUtils.IsLogin()) {
//                    openActivity(LoginFirstStepActivity.class);
//                } else {
//                   Intent intent = new Intent(getContext(), EditMySelfInfoActivity.class);
//                    LogUtils.i("张斯佳naem1", nickname11);
//                    LogUtils.i("张斯佳head1", avatar11);
//                   intent.putExtra("name", nickname11);
//                   intent.putExtra("head", avatar11);
//                   startActivity(intent);
//                }
                break;
            case R.id.tv_mine_title:
                break;
            case R.id.tv_mine_name:
                break;
            case R.id.iv_mine_setting:
//                if (DialogUtils.IsLogin()) {
//                    openActivity(SetActivity.class);
//                } else {
//                    openActivity(LoginFirstStepActivity.class);
//                }
                break;
            case R.id.ll_shared_app:

                    XueYiCheUtils.showShareAppCommon(getContext(), getActivity(), "驾考APP", "http://xueyiche.cn/", StringConstants.SHARED_TEXT + "http://www.xueyiche.cn/", "http://xychead.xueyiche.vip/share.jpg", "http://xueyiche.cn/");

                break;
            case R.id.ll_kefu:
                XueYiCheUtils.CallPhone(getActivity(), "拨打客服电话", "1111-11111111");
                break;
            case R.id.ll_about:
                openActivity(AboutXueYiCheActivity.class);
                break;
        }
    }
}
