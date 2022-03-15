package com.xueyiche.zjyk.xueyiche.mine.activities.my_wallet;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.MyWalletBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by zhanglei on 2016/10/29.
 */
public class MyWalletActivity extends BaseActivity implements View.OnClickListener {
    //我的钱包
    private LinearLayout llBack;
    private TextView tvTitle, tv_wenxintishi, tv_remain_money;
    private Button bt_recharge, bt_cash;
    private String bandKa;
    private Object money;

    @Override
    protected int initContentView() {
        return R.layout.my_wallet_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.chongzhi_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.chongzhi_include).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.chongzhi_include).findViewById(R.id.tv_wenxintishi);
        tv_remain_money = (TextView) view.findViewById(R.id.tv_remain_money);
        bt_recharge = (Button) view.findViewById(R.id.bt_recharge);
        bt_cash = (Button) view.findViewById(R.id.bt_cash);
        llBack.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
        bt_recharge.setOnClickListener(this);
        bt_cash.setOnClickListener(this);
        getMoney();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("我的钱包");
//        tv_wenxintishi.setVisibility(View.VISIBLE);
//        tv_wenxintishi.setTextColor(getResources().getColor(R.color.test_color));
//        tv_wenxintishi.setText("账户明细");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:
                startActivity(new Intent(App.context, ZhangHaoMingXi.class));
                break;
            case R.id.bt_recharge:
//                startActivity(new Intent(App.context, RechargeActivity.class));
//                MobclickAgent.onEvent(MyWalletActivity.this, "wallet_chongzhi");
                break;
            case R.id.bt_cash:
                MobclickAgent.onEvent(MyWalletActivity.this, "wallet_tixian");
                String trim = tv_remain_money.getText().toString().trim();
                if (!"0.00元".equals(trim)) {
                    startActivity(new Intent(App.context, CashActivity.class));
                }else {
                    showToastShort("余额不足，无法提现");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getMoney() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(user_id)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.WDQB)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        MyWalletBean myWalletBean = JsonUtil.parseJsonToBean(string, MyWalletBean.class);
                        if (myWalletBean != null) {
                            final MyWalletBean.ContentBean content = myWalletBean.getContent();
                            if (content!=null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String current_balance = content.getCurrent_balance();
                                        if (!TextUtils.isEmpty(current_balance)) {
                                            tv_remain_money.setText(current_balance+"元");
                                        }else {
                                            tv_remain_money.setText("0.00元");
                                        }
                                    }
                                });
                            }
                        }
                    }
                    return string;
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
