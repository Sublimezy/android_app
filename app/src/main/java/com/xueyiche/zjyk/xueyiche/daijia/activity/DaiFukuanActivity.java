package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.PlanNode;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Administrator on 2019/10/17.
 */
public class DaiFukuanActivity extends BaseActivity{

    private TextView tv_login_back;
    private TextView tv_qi,tv_zhong,tv_name,tv_age,tv_gonghao,tv_user_amount,
            tv_act_distance,tv_all_money,tv_user_amount3,tv_wait_time,tv_user_amount2
            ,tv_pay,tv_dai_money,tv_time;
    private ImageView iv_login_back;
    private CircleImageView ci_head;
    private String user_id;
    private String order_number;
    private double user_amount2;
    private double user_amount3;
    public static DaiFukuanActivity instance;
    @Override
    protected int initContentView() {
        return R.layout.daifukuan_activity;
    }

    @Override
    protected void initView() {
        instance = this;
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        iv_login_back = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_login_back);
        tv_qi = (TextView) view.findViewById(R.id.tv_qi);
        tv_zhong = (TextView) view.findViewById(R.id.tv_zhong);
        tv_user_amount = (TextView) view.findViewById(R.id.tv_user_amount);
        tv_act_distance = (TextView) view.findViewById(R.id.tv_act_distance);
        tv_user_amount3 = (TextView) view.findViewById(R.id.tv_user_amount3);
        tv_wait_time = (TextView) view.findViewById(R.id.tv_wait_time);
        tv_all_money = (TextView) view.findViewById(R.id.tv_all_money);
        tv_pay = (TextView) view.findViewById(R.id.tv_pay);
        tv_dai_money = (TextView) view.findViewById(R.id.tv_dai_money);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_user_amount2 = (TextView) view.findViewById(R.id.tv_user_amount2);

        //姓名
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        //驾龄
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        //工号
        tv_gonghao = (TextView) view.findViewById(R.id.tv_gonghao);
        //头像
        ci_head = (CircleImageView) view.findViewById(R.id.ci_head);

    }

    @Override
    protected void initListener() {
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //支付
                Intent intent = new Intent(App.context, AppPay.class);
                intent.putExtra("pay_style", "daijia_daifu");
                intent.putExtra("order_number", order_number);
                intent.putExtra("subscription", (user_amount2+user_amount3)+ "");
                intent.putExtra("jifen", "0");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        user_id = PrefUtils.getString(this, "user_id", "");
        order_number = getIntent().getStringExtra("order_number");
        iv_login_back.setVisibility(View.INVISIBLE);
        tv_login_back.setText("待支付");
        getOrder();
    }

    public void getOrder() {
        OkHttpUtils.post().url(AppUrl.Get_Order)
                .addParams("order_number", order_number)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    OrderInfoBean orderInfoBean = JsonUtil.parseJsonToBean(string, OrderInfoBean.class);
                    if (orderInfoBean != null) {
                        final int code = orderInfoBean.getCode();
                        if (200 == code) {
                            final OrderInfoBean.ContentBean content = orderInfoBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        String job_number = content.getJob_number();
                                        tv_gonghao.setText("工号："+job_number);
                                        String driving_year = content.getDriving_year();
                                        tv_age.setText(driving_year+"年驾龄");
                                        String driver_name = content.getDriver_name();
                                        tv_name.setText(driver_name);
                                        String head_img = content.getHead_img();
                                        Picasso.with(App.context).load(head_img).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(ci_head);
                                        String order_time1 = content.getOrder_time1();
                                        String get_on_name = content.getGet_on_name();
                                        tv_qi.setText(get_on_name);
                                        String get_down_name = content.getGet_down_name();
                                        tv_zhong.setText(get_down_name);
                                        String over_time = content.getOver_time();
                                        String act_distance = content.getAct_distance();
                                        double user_amount = content.getUser_amount();
                                        String over_distance = content.getOver_distance();
                                        String waitminutes = content.getWaitminutes();
                                        //等候费金额
                                        user_amount2 = content.getUser_amount2();
                                        user_amount3 = content.getUser_amount3();
                                        //0未支付 1 已支付
                                        String pay_status2 = content.getPay_status2();
                                        String pay_status3 = content.getPay_status3();
                                        //，1:需要，2:不需要
                                        String driver_need2 = content.getDriver_need2();
                                        String driver_need3 = content.getDriver_need3();

                                        if ("1".equals(driver_need2)) {
                                            if ("0".equals(pay_status2)) {
                                                tv_user_amount2.setTextColor(Color.parseColor("#ffb10c"));
                                                tv_user_amount2.setText(user_amount2 +"元");
                                            }else {
                                                tv_user_amount2.setTextColor(Color.parseColor("#666666"));
                                                tv_user_amount2.setText(user_amount2 +"元已支付");
                                            }
                                        }
                                        if ("1".equals(driver_need3)) {
                                            if ("0".equals(pay_status3)) {
                                                tv_user_amount3.setTextColor(Color.parseColor("#ffb10c"));
                                                tv_user_amount3.setText(user_amount3 +"元");
                                            }else {
                                                tv_user_amount3.setTextColor(Color.parseColor("#666666"));
                                                tv_user_amount3.setText(user_amount3 +"元已支付");
                                            }
                                        }
                                        tv_dai_money.setText("还需支付"+ (user_amount2 + user_amount3) +"元");
                                        tv_time.setText("下单时间："+order_time1);
                                        tv_act_distance.setText("里程费（共"+act_distance+"公里，区域外"+over_distance+"公里）");
                                        tv_user_amount.setText(user_amount+"元已支付");
                                        tv_all_money.setText(user_amount+ (user_amount2 + user_amount3)  +"元");
                                        tv_wait_time.setText("等候费（共"+waitminutes+"分，超时"+over_time+"分）");
                                    }
                                });
                            }
                        }
                    }
                }
                return string;
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }
}
