package com.xueyiche.zjyk.xueyiche.mine.activities.my_wallet;

import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;

/**
 * Created by zhanglei on 2016/12/5.
 */
public class CashContent extends BaseActivity implements View.OnClickListener{
    private LinearLayout llBack;
    private TextView tvTitle,tvCashOk;
    private Button btOk;
    @Override
    protected int initContentView() {
        return R.layout.cash_content;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.cash_content_include).findViewById(R.id.ll_bianji_back);
        tvTitle = (TextView) view.findViewById(R.id.cash_content_include).findViewById(R.id.mine_tv_title);
        tvCashOk = (TextView) view.findViewById(R.id.tv_tixian_ok);
        btOk = (Button) view.findViewById(R.id.bt_cash_ok);
        TextPaint tp = tvCashOk.getPaint();
        tp.setFakeBoldText(true);
        llBack.setOnClickListener(this);
        btOk.setOnClickListener(this);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("提现详情");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_bianji_back:
                finish();
                break;
            case R.id.bt_cash_ok:
                finish();
                break;
        }
    }
}
