package com.xueyiche.zjyk.jiakao.exam;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.mine.view.CircleImageView;
import com.xueyiche.zjyk.jiakao.utils.DialogUtils;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;


public class MoNiTestPage extends BaseActivity implements View.OnClickListener {
    private CircleImageView mo_ni_head;
    private TextView tv_achievement_title, tv_time;
    private String moni_style;

    @Override
    protected int initContentView() {
        return R.layout.mo_ni_test_page_activity;
    }

    @Override
    protected void initView() {
        Button mBtStart = (Button) view.findViewById(R.id.start_test);
        ImageView ivBack = (ImageView) view.findViewById(R.id.test_moni_activity_include).findViewById(R.id.ll_test_license_back);
        tv_achievement_title = (TextView) view.findViewById(R.id.test_moni_activity_include).findViewById(R.id.tv_achievement_title);
        mo_ni_head = (CircleImageView) view.findViewById(R.id.mo_ni_head);
        tv_time = (TextView) view.findViewById(R.id.tv_order_sn);
        if (!DialogUtils.IsLogin()) {
            mo_ni_head.setImageResource(R.mipmap.mo_ni_head);
        } else {
            String head_img = PrefUtils.getString(App.context, "head_img", "");
            if (!TextUtils.isEmpty(head_img)) {
                Picasso.with(this).load(head_img).into(mo_ni_head);
            }
        }
        tv_achievement_title.setText("模拟考试");
        mBtStart.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        moni_style = intent.getStringExtra("moni_style");
        if ("1".equals(moni_style)) {
            tv_time.setText("45分钟");
        } else if ("2".equals(moni_style)) {
            tv_time.setText("30分钟");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_test:
/*                if ("1".equals(moni_style)) {
                    Intent intent = new Intent(App.context, PracticeTestActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }*/
                break;
            case R.id.ll_test_license_back:
                finish();
                break;
        }
    }
}
