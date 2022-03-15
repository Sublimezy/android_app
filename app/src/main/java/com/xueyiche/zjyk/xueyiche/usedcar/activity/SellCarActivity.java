package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.view.FlyBanner;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.SellCarBean;
import com.xueyiche.zjyk.xueyiche.usedcar.view.UsedCarFlyBanner;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2018/6/28.
 */
public class SellCarActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_login_back,tv_sell_car_number,tv_yuyue_sell,tv_call;
    private LinearLayout ll_exam_back;
    private EditText et_phone;
    private UsedCarFlyBanner fb_sell_car;

    @Override
    protected int initContentView() {
        return R.layout.sell_car_activity;
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        fb_sell_car = (UsedCarFlyBanner) view.findViewById(R.id.fb_sell_car);
        tv_sell_car_number = (TextView) view.findViewById(R.id.tv_sell_car_number);
        tv_yuyue_sell = (TextView) view.findViewById(R.id.tv_yuyue_sell);
        tv_call = (TextView) view.findViewById(R.id.tv_call);
        tv_login_back.setText("我要卖车");

    }
    @Override
    protected void onResume() {
        super.onResume();
        fb_sell_car.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        fb_sell_car.stopAutoPlay();

    }
    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_call.setOnClickListener(this);
        tv_yuyue_sell.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getdate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_yuyue_sell:
                //预约卖车
                String phone = et_phone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)&&phone.length()==11) {
                    Intent intent = new Intent(this,SellCompleteInforActivity.class);
                    intent.putExtra("tel",phone);
                    startActivity(intent);
                }else {
                    showToastShort("手机号码有误");
                }
                break;
            case R.id.tv_call:
                //打电话
                XueYiCheUtils.CallPhone(this, "拨打客服电话", "0451-51068980");
                break;
        }
    }

    public void getdate() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String id = LoginUtils.getId(this);
            OkHttpUtils.post().url(AppUrl.Sell_Car)
                    .addParams("device_id", id)
                    .addParams("area", PrefUtils.getString(this,"usedcarcity",""))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final SellCarBean sellCarBean = JsonUtil.parseJsonToBean(string, SellCarBean.class);
                        if (sellCarBean != null) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SellCarBean.DataBean data = sellCarBean.getData();
                                    if (data != null) {
                                        List<SellCarBean.DataBean.BroadcastListBean> broadcastList = data.getBroadcastList();
                                        //卖出数量
                                        int sellCount = data.getSellCount();
                                        tv_sell_car_number.setText(sellCount+"");
                                        //轮播图
                                        if (broadcastList != null) {
                                            lunbotu(broadcastList);
                                        }
                                    }
                                }
                            });

                        }
                        return string;
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

    private void lunbotu(List<SellCarBean.DataBean.BroadcastListBean> broadcastList) {
        List<String> imageurls = new ArrayList<String>();
        if (broadcastList != null) {
            for (SellCarBean.DataBean.BroadcastListBean broadcastListBean : broadcastList) {
                String broadcast_pic = broadcastListBean.getBroadcast_pic();
                if (!TextUtils.isEmpty(broadcast_pic)) {
                    imageurls.add(broadcast_pic);
                }
            }
            fb_sell_car.setImagesUrl(imageurls);
        }
    }
}
