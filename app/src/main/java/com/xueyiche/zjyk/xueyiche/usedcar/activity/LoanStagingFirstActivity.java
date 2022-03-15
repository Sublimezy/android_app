package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;

/**
 * Created by ZL on 2018/6/28.
 */
public class LoanStagingFirstActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle, tv_zixun, tv_shenqing,tv_title,tv_first_money;

    @Override
    protected int initContentView() {
        return R.layout.loanstaging_first_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.fenjidaikuan_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.fenjidaikuan_include).findViewById(R.id.tv_login_back);
        tv_zixun = (TextView) view.findViewById(R.id.tv_shenqing);
        tv_shenqing = (TextView) view.findViewById(R.id.tv_zixun);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_zixun.setOnClickListener(this);
        tv_shenqing.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        tvTitle.setText("贷款分期");
        tv_title.setText("大众速腾 2017款 180TSI 自动尊享版显示该车简介");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_zixun:
                //TODO:咨询
                break;
            case R.id.tv_shenqing:
                startActivity(new Intent(App.context,LoanStagingSecondActivity.class));
                break;

        }
    }
}
