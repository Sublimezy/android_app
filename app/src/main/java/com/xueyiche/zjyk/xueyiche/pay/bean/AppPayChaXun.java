package com.xueyiche.zjyk.xueyiche.pay.bean;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PayUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by Owner on 2018/1/4.
 */
public class AppPayChaXun extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlZhifubao, rlWechat, rl_car_live_pos, rl_indent_yinlian;
    private ImageView iv_login_back;
    private TextView tv_login_back;
    private String order_number;
    private String subscription, jifen;
    private String url;
    private String user_id;
    private LinearLayout ll_exam_back;
    private String pay_style, type;


    @Override
    protected int initContentView() {
        return R.layout.activity_app_pay;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        iv_login_back = (ImageView) view.findViewById(R.id.driver_pay_include).findViewById(R.id.iv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.ll_exam_back).findViewById(R.id.ll_exam_back);
        iv_login_back.setOnClickListener(this);
        tv_login_back = (TextView) view.findViewById(R.id.driver_pay_include).findViewById(R.id.tv_login_back);
        tv_login_back.setText("支付方式");
        rlZhifubao = (RelativeLayout) view.findViewById(R.id.rl_zhifubao);
        rlWechat = (RelativeLayout) view.findViewById(R.id.rl_wechat);
        rl_car_live_pos = (RelativeLayout) view.findViewById(R.id.rl_pos);
        rl_indent_yinlian = (RelativeLayout) view.findViewById(R.id.rl_yinlian);
        rlZhifubao.setOnClickListener(this);
        rl_indent_yinlian.setOnClickListener(this);
        rlWechat.setOnClickListener(this);
        rl_car_live_pos.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        user_id = PrefUtils.getString(App.context, "user_id", "");


    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("支付成功", msg)) {
            EventBus.getDefault().post(new MyEvent("查询支付成功"));
            finish();
        }

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        //订单支付金额
        subscription = intent.getStringExtra("subscription");
        jifen = intent.getStringExtra("jifen");
        pay_style = intent.getStringExtra("pay_style");
        type = intent.getStringExtra("type");
        url = AppUrl.ChaXun_Pay;
//        url = "http://172.16.51.61:8082/secondhandcarevaluation/pay_insurance_notify.do";
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();

            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rl_zhifubao:
                zfb();
                break;
            case R.id.rl_wechat:
                if (XueYiCheUtils.isWeixinAvilible(this)) {
                    PayUtils.wx(AppPayChaXun.this, url, order_number, user_id);
                } else {
                    showToastShort("目前您的微信版本过低或未安装微信，需要安装微信才能使用");
                }
                break;
            case R.id.rl_pos:
                showToastShort("暂未开通");
                break;
            case R.id.rl_yinlian:
                showToastShort("暂未开通");
                break;
        }
    }

    private void zfb() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post()
                    .url(url)
                    .addParams("order_number", order_number)
                    .addParams("violation_type", type)
                    .addParams("pay_money", subscription)
                    .addParams("pay_type_id", "1")
                    .addParams("user_id", user_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        ZhiFuBaoBean zhiFuBaoBean = JsonUtil.parseJsonToBean(string, ZhiFuBaoBean.class);
                        if (zhiFuBaoBean != null) {
                            int code = zhiFuBaoBean.getCode();
                            if (200 == code) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        PayUtils.zfb(string, AppPayChaXun.this, AppPayChaXun.this, jifen, subscription, order_number, pay_style);
                                    }
                                });
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
}
