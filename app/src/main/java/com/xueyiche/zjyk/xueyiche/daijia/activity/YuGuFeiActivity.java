package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.LiJIBean;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Administrator on 2019/9/26.
 */
public class YuGuFeiActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_wenxintishi;
    private LinearLayout ll_exam_back;
    private TextView tv_money;
    private TextView tv_qibu;
    private TextView tv_distance,tv_licheng;

    @Override
    protected int initContentView() {
        return R.layout.yugu_activity;
    }

    @Override
    protected void initView() {
        tv_title = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_wenxintishi);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_qibu = (TextView) view.findViewById(R.id.tv_qibu);
        tv_distance = (TextView) view.findViewById(R.id.tv_distance);
        tv_licheng = (TextView) view.findViewById(R.id.tv_licheng);
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_title.setText("预估费");
        tv_wenxintishi.setText("计费规则");
        tv_wenxintishi.setTextColor(Color.parseColor("#666666"));
        tv_wenxintishi.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        String distance = intent.getStringExtra("distance");
        String money = intent.getStringExtra("money");
        String over_distance = intent.getStringExtra("over_distance");
        String user_amount3 = intent.getStringExtra("user_amount3");
        String user_amount = intent.getStringExtra("user_amount");
        tv_money.setText("约"+money+"元");
        tv_qibu.setText(user_amount+"元");
        tv_licheng.setText(user_amount3+"元");
        tv_distance.setText("里程费（共"+distance+"公里，区域外"+over_distance+"公里）");

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:
                Intent intent = new Intent(this, UrlActivity.class);
                intent.putExtra("url", "http://xueyiche.cn/xyc/rule/index.html");
                intent.putExtra("type", "2");
                startActivity(intent);
                break;

        }
    }
}
