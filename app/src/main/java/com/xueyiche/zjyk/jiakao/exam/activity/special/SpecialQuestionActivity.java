package com.xueyiche.zjyk.jiakao.exam.activity.special;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.exam.activity.question.PracticeNormalActivity;

public class SpecialQuestionActivity extends BaseActivity implements View.OnClickListener {
    private TextView single;
    private TextView doubles;
    TextView judge;
    private String model;
    private Bundle args;

    @Override
    protected int initContentView() {
        return R.layout.activity_special_question;
    }

    @Override
    protected void initView() {


        Intent intent = getIntent();
        args = intent.getExtras();


        single = view.findViewById(R.id.single);
        doubles = view.findViewById(R.id.doubles);
        judge = view.findViewById(R.id.judge);

        single.setOnClickListener(this);
        doubles.setOnClickListener(this);
        judge.setOnClickListener(this);
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
            case R.id.single:
                args.putLong("questionType", 0L);
                break;
            case R.id.doubles:
                args.putLong("questionType", 1L);
                break;
            case R.id.judge:
                args.putLong("questionType", 2L);
                break;
        }
        readyGo(PracticeNormalActivity.class, args);
    }
}