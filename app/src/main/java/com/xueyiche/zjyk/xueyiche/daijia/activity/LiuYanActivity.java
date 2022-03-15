package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;

/**
 * Created by Administrator on 2019/9/19.
 */
public class LiuYanActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private LinearLayout ll_exam_back;
    private TextView tv_wenxintishi;
    private EditText et_liuyan;

    @Override
    protected int initContentView() {
        return R.layout.liuyan_activity;
    }

    @Override
    protected void initView() {
        tv_title = (TextView) view.findViewById(R.id.papertest_include).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.papertest_include).findViewById(R.id.ll_exam_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.papertest_include).findViewById(R.id.tv_wenxintishi);
        et_liuyan = (EditText) view.findViewById(R.id.et_liuyan);
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_title.setText("备注");
        tv_wenxintishi.setText("确认");
        tv_wenxintishi.setVisibility(View.VISIBLE);
        tv_wenxintishi.setTextColor(Color.parseColor("#666666"));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_exam_back:

                intent.putExtra("beizhu", "");
                setResult(333, intent);
                finish();
                break;
            case R.id.tv_wenxintishi:
                String phone = et_liuyan.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(LiuYanActivity.this, "请输入备注", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("beizhu", phone);
                setResult(333, intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("beizhu", "");
            setResult(333, intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }
}
