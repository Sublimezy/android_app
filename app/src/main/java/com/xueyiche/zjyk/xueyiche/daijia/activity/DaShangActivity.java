package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.utils.EditInputFilter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 打赏
 */
public class DaShangActivity extends BaseActivity {


    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.ll_common_back)
    LinearLayout llCommonBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_btn)
    TextView tvRightBtn;
    @BindView(R.id.iv_caidan)
    ImageView ivCaidan;
    @BindView(R.id.title_view_heng)
    View titleViewHeng;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.btn_dashang)
    TextView btnDashang;

    @Override
    protected int initContentView() {
        return R.layout.activity_da_shang;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        EditInputFilter editInputFilter = new EditInputFilter();
        InputFilter[] filters = {editInputFilter};
        etMoney.setFilters(filters);
    }


    @OnClick({R.id.ll_common_back, R.id.btn_dashang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.btn_dashang:
                break;
        }
    }
}