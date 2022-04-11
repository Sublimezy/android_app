package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.YuSuanBean;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/9/26.
 */
public class YuGuFeiActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right_btn)
    TextView tv_wenxintishi;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_qibu)
    TextView tvQibu;


    public static void forward(Context context, String start_lng, String start_lat, String end_lng, String end_lat) {
        Intent intent = new Intent(context, YuGuFeiActivity.class);
        intent.putExtra("start_lng", start_lng);
        intent.putExtra("start_lat", start_lat);
        intent.putExtra("end_lng", end_lng);
        intent.putExtra("end_lat", end_lat);
        context.startActivity(intent);
    }

    @Override
    protected int initContentView() {
        return R.layout.yugu_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, view);
        ImmersionBar.with(this).titleBar(rlTitle).init();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        tv_title.setText("预估费");
        tv_wenxintishi.setText("计费规则");
        getYuSuan();
    }

    private void getYuSuan() {
        Intent intent = getIntent();
        String start_lng = intent.getStringExtra("start_lng");
        String start_lat = intent.getStringExtra("start_lat");
        String end_lng = intent.getStringExtra("end_lng");
        String end_lat = intent.getStringExtra("end_lat");
        Map<String, String> map = new HashMap<>();
        map.put("start_lng", "" + start_lng);
        map.put("start_lat", "" + start_lat);
        map.put("end_lng", "" + end_lng);
        map.put("end_lat", "" + end_lat);
        MyHttpUtils.postHttpMessage(AppUrl.orderBudgetPrice, map, YuSuanBean.class, new RequestCallBack<YuSuanBean>() {
            @Override
            public void requestSuccess(YuSuanBean json) {
                if (1 == json.getCode()) {
                    YuSuanBean.DataBean data = json.getData();

//                    tv_money.setText("" + json.getData().getPrices());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    @OnClick({R.id.ll_common_back, R.id.tv_right_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_right_btn:
                UrlActivity.forward(YuGuFeiActivity.this, "http://xueyiche.cn/xyc/rule/index.html", "2");
                break;
        }
    }
}
