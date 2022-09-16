package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.YouZhengLianCheDetailBean;
import com.xueyiche.zjyk.xueyiche.mine.view.LoadingLayout;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YouZhengLianCheDetailActivity extends BaseActivity {


    @BindView(R.id.title_bar_iv_back)
    ImageView titleBarIvBack;
    @BindView(R.id.title_bar_back)
    RelativeLayout titleBarBack;
    @BindView(R.id.title_bar_title_text)
    TextView titleBarTitleText;
    @BindView(R.id.title_bar_right_text)
    TextView titleBarRightText;
    @BindView(R.id.title_bar_rl)
    RelativeLayout titleBarRl;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_xiadan_time)
    TextView tvXiadanTime;
    @BindView(R.id.tv_yuyue_time)
    TextView tvYuyueTime;
    @BindView(R.id.tv_coash_name)
    TextView tvCoashName;
    @BindView(R.id.tv_coash_phone)
    TextView tvCoashPhone;
    @BindView(R.id.tv_danjian)
    TextView tvDanjian;
    @BindView(R.id.tv_lianche_shichang)
    TextView tvLiancheShichang;
    @BindView(R.id.tv_feiyong_jine)
    TextView tvFeiyongJine;
    @BindView(R.id.nes_scrollView)
    NestedScrollView nesScrollView;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    private String order_sn;

    @Override
    protected int initContentView() {
        return R.layout.activity_you_zheng_lian_che_detail;
    }

    @Override
    protected void initView() {
        order_sn = getIntent().getStringExtra("order_sn");

        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {

    }

    private LoadingLayout loading;

    @Override
    protected void initData() {
        loading = LoadingLayout.wrap(ll_content);
        loading.showLoading();
        titleBarTitleText.setText("订单详情");
        loading.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataNet();
            }
        });
        getDataNet();
    }

    private void getDataNet() {
        Map<String, String> params = new HashMap<>();
        params.put("order_sn", order_sn);
        MyHttpUtils.postHttpMessage(AppUrl.userOrderDetails_youzheng, params, YouZhengLianCheDetailBean.class, new RequestCallBack<YouZhengLianCheDetailBean>() {
            @Override
            public void requestSuccess(YouZhengLianCheDetailBean json) {
                if (json.getCode() == 1) {
                    loading.showContent();
                    tvOrderSn.setText("订单号: " + formatString(json.getData().getOrder_sn()));
                    state.setText(json.getData().getOrder_status());
                    tvStart.setText("接送地点: " + formatString(json.getData().getStart_address()));
                    tvXiadanTime.setText("下单时间: " + json.getData().getCreatetime());
                    tvYuyueTime.setText("预约时间: " + (TextUtils.isEmpty(json.getData().getFixed_time()) ? "----" : json.getData().getFixed_time()));
                    tvCoashName.setText("教练姓名: " + (TextUtils.isEmpty(json.getData().getPractice_nickname()) ? "----" : json.getData().getPractice_nickname()));
                    tvCoashPhone.setText("教练联系方式: " + (TextUtils.isEmpty(json.getData().getPractice_username()) ? "----" : json.getData().getPractice_username()));
                    tvDanjian.setText("单价: " + formatString(json.getData().getH_money()) + "元/小时");
                    tvLiancheShichang.setText("练车时长: " + formatString(json.getData().getHour_num()) + "小时");
                    tvFeiyongJine.setText("费用金额: " + formatString(json.getData().getTotal_price()) + "元");


                } else {
                    showToastShort(json.getMsg());
                    loading.showError();
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                loading.showError();
            }
        });
    }


    @OnClick({R.id.title_bar_back, R.id.tv_yuyue_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.tv_yuyue_time:
                break;
        }
    }

    private String formatString(String input) {
        return TextUtils.isEmpty(input) ? "----" : input;
    }
}