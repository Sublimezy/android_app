package com.xueyiche.zjyk.xueyiche.practicecar.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.xycindent.activities.IndentActivity;

/**
 * Created by Owner on 2018/4/20.
 */
public class OrderFaBuOkActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_login_back,tv_succeed_chakan;
    private LinearLayout llBack;
    @Override
    protected int initContentView() {
        return R.layout.fabu_ok_activity;
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tv_succeed_chakan = (TextView) view.findViewById(R.id.tv_succeed_chakan);
        tv_login_back.setText("发布成功");
        tv_succeed_chakan.setOnClickListener(this);
        llBack.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_succeed_chakan:
                Intent intent = new Intent(this,IndentActivity.class);
                intent.putExtra("position",0);
                startActivity(intent);
                finish();
                break;
        }
    }
}
