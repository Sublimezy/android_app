package com.xueyiche.zjyk.xueyiche.mine.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.RegistSiJiActivity;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.AboutXueYiCheActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.ConstantlyAddressActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.MineSendActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.MyOrderListActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.MineCenterBean;
import com.xueyiche.zjyk.xueyiche.mine.activities.bianji.EditMySelfInfoActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bianji.SetActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bianji.SettingActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.myhttp.LogUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.ll_wode_fabu)
    LinearLayout ll_wode_fabu;
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
        if (DialogUtils.IsLogin()) {
            Map<String, String> params = new HashMap<>();
            MyHttpUtils.postHttpMessage(AppUrl.mine_center, params, MineCenterBean.class, new RequestCallBack<MineCenterBean>() {
                @Override
                public void requestSuccess(MineCenterBean json) {
                    if (json.getCode() == 1) {
                        tvMineName.setVisibility(View.VISIBLE);
                        Glide.with(getContext()).load(json.getData().getAvatar()).into(mineHead);
                        tvMineTitle.setText(json.getData().getNickname());
                        tvMineName.setText(json.getData().getMobile());
                        avatar11 = json.getData().getAvatar();
                        nickname11 = json.getData().getNickname();
                    } else {
                        showToastShort(json.getMsg());
                    }
                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
        } else {
            mineHead.setImageResource(R.mipmap.mine_head);
            tvMineName.setVisibility(View.GONE);
            tvMineTitle.setText("点击头像登录");
        }
    }
     String nickname11;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //true  表示不可见

        } else {
            //为false  表示可见

            if (DialogUtils.IsLogin()) {

//                tvMineTitle.setText("Hi~，学易车用户");
                tvMineName.setVisibility(View.VISIBLE);
                Map<String, String> params = new HashMap<>();
                MyHttpUtils.postHttpMessage(AppUrl.mine_center, params, MineCenterBean.class, new RequestCallBack<MineCenterBean>() {



                    @Override
                    public void requestSuccess(MineCenterBean json) {
                        if (json.getCode() == 1) {
                            Glide.with(getContext()).load(json.getData().getAvatar()).into(mineHead);
                            avatar11 = json.getData().getAvatar();
                            nickname11 = json.getData().getNickname();
                            tvMineTitle.setText(json.getData().getNickname());
                            tvMineName.setText(json.getData().getMobile());
                            PrefUtils.putParameter("name",json.getData().getName());
                            PrefUtils.putParameter("card_num",json.getData().getCard_num());
                        } else {
                            showToastShort(json.getMsg());
                        }
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {

                    }
                });
            } else {
                mineHead.setImageResource(R.mipmap.mine_head);
                tvMineName.setVisibility(View.GONE);
                tvMineTitle.setText("点击头像登录");
            }

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.mine_head, R.id.tv_mine_title, R.id.tv_mine_name, R.id.iv_mine_setting, R.id.ll_my_order
            , R.id.ll_changyong_dizhi, R.id.ll_siji_baoming, R.id.ll_shared_app, R.id.ll_kefu, R.id.ll_about, R.id.ll_wode_fabu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_head:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                   Intent intent = new Intent(getContext(), EditMySelfInfoActivity.class);
                    LogUtils.i("张斯佳naem1", nickname11);
                    LogUtils.i("张斯佳head1", avatar11);
                   intent.putExtra("name", nickname11);
                   intent.putExtra("head", avatar11);
                   startActivity(intent);
                }
                break;
            case R.id.tv_mine_title:
                break;
            case R.id.tv_mine_name:
                break;
            case R.id.iv_mine_setting:
                if (DialogUtils.IsLogin()) {
                    openActivity(SetActivity.class);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.ll_my_order:

                if (DialogUtils.IsLogin()) {
                    openActivity(MyOrderListActivity.class);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.ll_changyong_dizhi:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    openActivity(ConstantlyAddressActivity.class);
                }
            case R.id.ll_wode_fabu:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    openActivity(MineSendActivity.class);
                }
                break;
            case R.id.ll_siji_baoming:
                Intent intent = new Intent(App.context, RegistSiJiActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_shared_app:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    XueYiCheUtils.showShareAppCommon(getContext(), getActivity(), "学易车-易动华夏", "http://xueyiche.cn/", StringConstants.SHARED_TEXT + "http://www.xueyiche.cn/", "http://xychead.xueyiche.vip/share.jpg", "http://xueyiche.cn/");

                }
                break;
            case R.id.ll_kefu:
                XueYiCheUtils.CallPhone(getActivity(), "拨打客服电话", "0451-58627471");
                break;
            case R.id.ll_about:
                openActivity(AboutXueYiCheActivity.class);
                break;
        }
    }
}
