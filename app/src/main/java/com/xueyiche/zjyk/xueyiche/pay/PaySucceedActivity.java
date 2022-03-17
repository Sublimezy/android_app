package com.xueyiche.zjyk.xueyiche.pay;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.pay.bean.PinNumberBean;
import com.xueyiche.zjyk.xueyiche.submit.PracticeCarSubmitIndent;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.activities.IndentActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Owner on 2018/1/5.
 */
public class PaySucceedActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_login_back, tv_pay_money,tv_pintuan_number, tv_succeed_chakan,tv_tishi_content,tv_get_jifen;
    private ImageView iv_login_back;
    private String order_number;
    private String pay_style;
    private LinearLayout ll_jifen;
    private Button bt_fenxiang;
    private String pin_number;

    @Override
    protected int initContentView() {
        return R.layout.activity_pay_succeed;
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        iv_login_back = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_login_back);
        tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        ll_jifen = (LinearLayout) view.findViewById(R.id.ll_jifen);
        tv_get_jifen = (TextView) view.findViewById(R.id.tv_get_jifen);
        tv_pintuan_number = (TextView) view.findViewById(R.id.tv_pintuan_number);
        tv_tishi_content = (TextView) view.findViewById(R.id.tv_tishi_content);
        tv_succeed_chakan = (TextView) view.findViewById(R.id.tv_succeed_chakan);
        bt_fenxiang = (Button) view.findViewById(R.id.bt_fenxiang);
        tv_login_back.setText("支付完成");
        iv_login_back.setOnClickListener(this);
        bt_fenxiang.setOnClickListener(this);
        tv_succeed_chakan.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String subscription = intent.getStringExtra("subscription");
        String huodejifen = intent.getStringExtra("huodejifen");
        pay_style = intent.getStringExtra("pay_style");
        order_number = intent.getStringExtra("order_number");
        if (!TextUtils.isEmpty(subscription)) {
            tv_pay_money.setText(subscription+"元");
        }
        if ("0".equals(huodejifen)) {
            ll_jifen.setVisibility(View.GONE);
        }else {
            ll_jifen.setVisibility(View.VISIBLE);
            tv_get_jifen.setText(huodejifen);
        }
        if ("driver_school".equals(pay_style)||"kaituan".equals(pay_style)) {
            tv_tishi_content.setText("请您到驾校完成线下采集，补交尾款后，提交申请返现，经平台确认后3-7个工作日完成返现。");
        }
        if ("kaituan".equals(pay_style)) {
            getTuanNumber();
        }else {
            tv_pintuan_number.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.bt_fenxiang:
                XueYiCheUtils.showShareAppCommon(App.context, this,
                        "学易车拼团考驾照",
                        "http://xueyiche.cn/xyc/pin/pin.php?pin="+pin_number,
                        "拼！拼！拼！两人成团，再减300", "http://xychead.xueyiche.vip/pin.png",
                        "http://xueyiche.cn/xyc/pin/pin.php?pin="+pin_number);
                break;
            case R.id.tv_succeed_chakan:
                if (!TextUtils.isEmpty(pay_style)) {
                  if ("practice".equals(pay_style)) {
                        Intent intent = new Intent(this, PracticeCarSubmitIndent.class);
                        intent.putExtra("order_number", order_number);
                        intent.putExtra("type", "indent");
                        startActivity(intent);
                    }else if ("train".equals(pay_style)) {
                        Intent intent = new Intent(this, IndentActivity.class);
                        intent.putExtra("position", 3);
                        startActivity(intent);
                    }else if ("zhitongche".equals(pay_style)) {
                        Intent intent = new Intent(this, IndentActivity.class);
                        intent.putExtra("position", 0);
                        startActivity(intent);
                    }
                    finish();
                }
                break;
        }
    }

    public void getTuanNumber() {
        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(order_number)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Pin_By_OrderNumber)
                    .addParams("order_number", order_number)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                PinNumberBean pinNumberBean = JsonUtil.parseJsonToBean(string, PinNumberBean.class);
                                if (pinNumberBean != null) {
                                    int code = pinNumberBean.getCode();
                                    if (200==code) {
                                        PinNumberBean.ContentBean content = pinNumberBean.getContent();
                                        if (content != null) {
                                            pin_number = content.getPin();
                                            tv_pintuan_number.setVisibility(View.VISIBLE);
                                            bt_fenxiang.setVisibility(View.VISIBLE);
                                            if (!TextUtils.isEmpty(pin_number)) {
                                                tv_pintuan_number.setText("拼团码："+pin_number);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                    return null;
                }

                @Override
                public void onError(Request request, Exception e) {
                    stopProgressDialog();
                }

                @Override
                public void onResponse(Object response) {
                    stopProgressDialog();
                }
            });
        }
    }
}
