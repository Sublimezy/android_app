package com.xueyiche.zjyk.jiakao.exam.activity.practice;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.exam.activity.question.PracticeNormalActivity;

public class PracticeEndActivity extends BaseActivity {

    private LinearLayout mLL_questionback;

    @Override
    protected int initContentView() {
        return R.layout.activity_practice_end;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();

        int unAnsweredNum = intent.getIntExtra("unAnsweredNum", 0);
        int takeTime = intent.getIntExtra("takeTime", 0);
        int score = intent.getIntExtra("score", 0);
        int mistakesNum = intent.getIntExtra("mistakesNum", 0);

        //返回
        mLL_questionback = view.findViewById(R.id.my_return).findViewById(R.id.ll_exam_back);
        mLL_questionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PracticeNormalActivity.instance.finish();
                finish();
            }
        });

        TextView unAnsweredNumTV = view.findViewById(R.id.tv_no_question);
        TextView takeTimeTV = view.findViewById(R.id.tv_mistakes_question);
        TextView scoreTV = view.findViewById(R.id.tv_score);
        TextView mistakesNumTV = view.findViewById(R.id.tv_taketime);

        unAnsweredNumTV.setText("未答题数：" + unAnsweredNum);
        takeTimeTV.setText("错题数：" + takeTime);
        scoreTV.setText("考试得分：" + score);
        mistakesNumTV.setText("总用时：" + mistakesNum);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}