package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;

import com.gyf.immersionbar.ImmersionBar;
import com.lihang.ShadowLayout;
import com.luck.picture.lib.utils.ToastUtils;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.SuccessBean;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
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
    @BindView(R.id.llPingJia)
    LinearLayout llPingJia;
    @BindView(R.id.tv_shichang)
    TextView tvShichang;
    @BindView(R.id.tv_shichang_price)
    TextView tvShichangPrice;
    @BindView(R.id.tv_waitfei)
    TextView tv_waitfei;
    @BindView(R.id.tv_licheng)
    TextView tvLicheng;
    @BindView(R.id.tv_licheng_price)
    TextView tvLichengPrice;
    @BindView(R.id.tv_quyunei_licheng)
    TextView tvQuyuneiLicheng;
    @BindView(R.id.tv_quyunei_licheng_price)
    TextView tvQuyuneiLichengPrice;
    @BindView(R.id.tvPingJia)
    TextView tvPingJia;
    @BindView(R.id.rb_star)
    RatingBar rb_star;
    @BindView(R.id.tv_quyuwai_licheng)
    TextView tvQuyuwaiLicheng;
    @BindView(R.id.tv_wait_title)
    TextView tv_wait_title;
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
        getDataFromNet();

    }

    private void getDataFromNet() {
        Map<String, String> params = new HashMap<>();
        params.put("order_sn", order_sn);
        MyHttpUtils.postHttpMessage(AppUrl.orderDetails, params, OrderDetailBean.class, new RequestCallBack<OrderDetailBean>() {
            @Override
            public void requestSuccess(OrderDetailBean json) {
                if (json.getCode() == 1) {
                    OrderDetailBean.DataBean data = json.getData();
                    int order_status = data.getOrder_status();
                    if (5 == order_status) {
                        rb_star.setVisibility(View.GONE);
                        tvPingJia.setVisibility(View.VISIBLE);
                    } else if (6 == order_status) {
                        rb_star.setVisibility(View.VISIBLE);
                        rb_star.setRating(Float.parseFloat(data.getPingjia_rank()));
                        tvPingJia.setVisibility(View.GONE);
                    } else {
                        llPingJia.setVisibility(View.GONE);
                    }
                    user_mobile = data.getUser_mobile();
                    tvOrderNumber.setText("订单号:" + data.getOrder_sn());
                    tvWeihao.setText("代驾编号: " + data.getUser_number());
                    tvOrderTime.setText("下单时间: " + data.getCreate_time());
                    tvStartTime.setText("开始时间: " + data.getStart_time());
                    tvStartLocation.setText("起始位置: " + data.getStart_address());
                    if (TextUtils.isEmpty(data.getEnd_address()) || data.getEnd_address().contains("null")) {
                        tvEndLocation.setText("终点位置: --");
                    } else {
                        tvEndLocation.setText("终点位置: " + data.getEnd_address());
                    }
                    tvEndTime.setText("结束时间: " + data.getEnd_time());

                    tvQibujia.setText("起步价(指定区域内)");
                    tvQibujiaPrice.setText(data.getQibu_price() + "元");

                    tvShichangPrice.setText(data.getShichang_price() + "元");
                    tvShichang.setText("时长费（" + data.getShichang_time() + "）");
                    tv_wait_title.setText("等时费（共" + data.getWait_time() + "）");
                    tv_waitfei.setText("" + data.getWait_price() + "元");
                    tvLicheng.setText("里程费(共" + data.getLicheng_km() + "公里)");
                    tvLichengPrice.setText(data.getLicheng_price() + "元");

                    tvQuyuneiLicheng.setText("区域内里程(共" + data.getNeiquyu_km() + "公里)");
                    tvQuyuneiLichengPrice.setText(data.getNeiquyu_km_price() + "元");

                    tvQuyuwaiLicheng.setText("区域外里程(共" + data.getWaiquyu_km() + "公里)");
                    tvQuyuwaiLichengPrice.setText(data.getWaiquyu_km_price() + "元");

                    tvYouhuiPrice.setText(data.getYouhui_price() + "元");

                    tvTotalPrice.setText(data.getTotal_price() + "元");

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

    @OnClick({R.id.ll_common_back, R.id.tv_weihao, R.id.iv_call_phone, R.id.tv_drive_path, R.id.tvPingJia})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_weihao:
                break;
            case R.id.tvPingJia:
                showPingJia();
                break;
            case R.id.iv_call_phone:
                XueYiCheUtils.CallPhone(OrderDetailActivity.this, "拨打代驾员电话", user_mobile);
                break;
            case R.id.tv_drive_path:
                DriverPathActivity.forward(OrderDetailActivity.this, order_sn);
                break;
        }
    }

    private void showPingJia() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.pingjia_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        final RatingBar rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        rb_star.setRating(5f);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;

        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 400;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rating = (int) rb_star.getRating();
                Map<String, String> map = new HashMap<>();
                map.put("order_sn", "" + order_sn);
                map.put("pingjia_rank", "" + rating);
                MyHttpUtils.postHttpMessage(AppUrl.orderAssess, map, SuccessBean.class, new RequestCallBack<SuccessBean>() {
                    @Override
                    public void requestSuccess(SuccessBean json) {
                        if (1 == json.getCode()) {
                            dialog01.dismiss();
                            getDataFromNet();
                        }
                        ToastUtils.showToast(OrderDetailActivity.this, "" + json.getMsg());
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {

                    }
                });

            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }
}