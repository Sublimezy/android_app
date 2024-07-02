package com.gxuwz.zy.exam.activity.special;

import static com.gxuwz.zy.constants.Constant.PRE_QUESTION_TYPE;
import static com.gxuwz.zy.constants.Constant.PRE_SUBJECT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseActivity;
import com.gxuwz.zy.exam.activity.question.PracticeNormalActivity;
import com.luck.picture.lib.utils.ToastUtils;


public class SpecialQuestionActivity extends BaseActivity implements View.OnClickListener {
    private TextView single;
    private TextView doubles;
    TextView judge;
    private String model;
    private Bundle args;
    private LinearLayout mLL_questionback;
    private long subject;

    @Override
    protected int initContentView() {
        return R.layout.activity_special_question;
    }

    @Override
    protected void initView() {


        Intent intent = getIntent();
        args = intent.getExtras();
        subject = args.getLong(PRE_SUBJECT);

        single = view.findViewById(R.id.single);
        doubles = view.findViewById(R.id.doubles);
        judge = view.findViewById(R.id.judge);

        single.setOnClickListener(this);
        doubles.setOnClickListener(this);
        judge.setOnClickListener(this);

        //返回
        mLL_questionback = view.findViewById(R.id.my_return).findViewById(R.id.ll_exam_back);

        mLL_questionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                args.putLong(PRE_QUESTION_TYPE, 0L);
                break;
            case R.id.doubles:
                if (subject == 1) {
                    ToastUtils.showToast(this, "科目一无多选题！");
                    return;
                }
                args.putLong(PRE_QUESTION_TYPE, 1L);
                break;
            case R.id.judge:
                args.putLong(PRE_QUESTION_TYPE, 2L);
                break;
        }
        readyGo(PracticeNormalActivity.class, args);
    }
}