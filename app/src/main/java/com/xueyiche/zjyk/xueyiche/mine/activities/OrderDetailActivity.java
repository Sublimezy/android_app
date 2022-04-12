package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.gyf.immersionbar.ImmersionBar;
import com.lihang.ShadowLayout;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.mine.bean.OrderDetailBean;
import com.xueyiche.zjyk.xueyiche.mine.view.LoadingLayout;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {


    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.ll_common_back)
    LinearLayout llCommonBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_btn)
    TextView tvRightBtn;
    @BindView(R.id.iv_caidan)
    ImageView ivCaidan;
    @BindView(R.id.title_view_heng)
    View titleViewHeng;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_weihao)
    TextView tvWeihao;
    @BindView(R.id.iv_call_phone)
    ImageView ivCallPhone;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_start_location)
    TextView tvStartLocation;
    @BindView(R.id.tv_end_location)
    TextView tvEndLocation;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_drive_path)
    TextView tvDrivePath;
    @BindView(R.id.tv_qibujia)
    TextView tvQibujia;
    @BindView(R.id.tv_qibujia_price)
    TextView tvQibujiaPrice;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.tv_youhui_price)
    TextView tvYouhuiPrice;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.mShadowLayout)
    ShadowLayout mShadowLayout;
    @BindView(R.id.content)
    NestedScrollView content;
    @BindView(R.id.tv_shichang)
    TextView tvShichang;
    @BindView(R.id.tv_shichang_price)
    TextView tvShichangPrice;
    @BindView(R.id.tv_licheng)
    TextView tvLicheng;
    @BindView(R.id.tv_licheng_price)
    TextView tvLichengPrice;
    @BindView(R.id.tv_quyunei_licheng)
    TextView tvQuyuneiLicheng;
    @BindView(R.id.tv_quyunei_licheng_price)
    TextView tvQuyuneiLichengPrice;
    @BindView(R.id.tv_quyuwai_licheng)
    TextView tvQuyuwaiLicheng;
    @BindView(R.id.tv_quyuwai_licheng_price)
    TextView tvQuyuwaiLichengPrice;
    private LoadingLayout loading;
    private String order_sn;
    private String user_mobile;


    @Override
    protected int initContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        loading = LoadingLayout.wrap(content);
        loading.showLoading();
        tvTitle.setText("订单详情");
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();

    }

    @Override
    protected void initListener() {
        loading.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    protected void initData() {
        order_sn = getIntent().getStringExtra("order_sn");
        Map<String, String> params = new HashMap<>();
        params.put("order_sn", order_sn);
        MyHttpUtils.postHttpMessage(AppUrl.orderDetails, params, OrderDetailBean.class, new RequestCallBack<OrderDetailBean>() {
            @Override
            public void requestSuccess(OrderDetailBean json) {
                if (json.getCode() == 1) {
                    OrderDetailBean.DataBean data = json.getData();
                    user_mobile = data.getUser_mobile();
                    tvOrderNumber.setText("订单号:"+data.getOrder_sn());
                    tvWeihao.setText("代驾编号: "+data.getUser_number());
                    tvOrderTime.setText("下单时间: "+data.getCreatetime());
                    tvStartTime.setText("开始时间: "+data.getStart_time());
                    tvStartLocation.setText("起始位置: "+data.getStart_address());
                    tvEndLocation.setText("终点位置: "+data.getEnd_address());
                    tvEndTime.setText("结束时间: "+data.getEnd_time());

                    tvQibujia.setText("起步价(指定区域内)");
                    tvQibujiaPrice.setText(data.getQibu_price()+"元");

                    tvShichang.setText("时长费(共"+data.getShichang_time()+"分钟)");
                    tvShichangPrice.setText(data.getShichang_price()+"元");

                    tvLicheng.setText("里程费(共"+data.getLicheng_km()+"公里)");
                    tvLichengPrice.setText(data.getLicheng_price()+"元");

                    tvQuyuneiLicheng.setText("区域内里程(共"+data.getNeiquyu_km()+"公里)");
                    tvQuyuneiLichengPrice.setText(data.getNeiquyu_km_price()+"元");

                    tvQuyuwaiLicheng.setText("区域外里程(共"+data.getWaiquyu_km()+"公里)");
                    tvQuyuwaiLichengPrice.setText(data.getWaiquyu_km_price()+"元");

                    tvYouhuiPrice.setText(data.getYouhui_price()+"元");

                    tvTotalPrice.setText(data.getTotal_price()+"元");

                    loading.showContent();
                } else {
                    loading.showError();
                    showToastShort(json.getMsg());

                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

                loading.showError();
                showToastShort("服务器内部错误,稍后再试!");
            }
        });
    }


    @OnClick({R.id.ll_common_back, R.id.tv_weihao, R.id.iv_call_phone, R.id.tv_drive_path})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_weihao:
                break;
            case R.id.iv_call_phone:
                XueYiCheUtils.CallPhone(OrderDetailActivity.this, "拨打代驾员电话", user_mobile);
                break;
            case R.id.tv_drive_path:
                break;
        }
    }

}