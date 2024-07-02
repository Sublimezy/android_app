package com.gxuwz.zy.exam.activity.practice;


import static com.gxuwz.zy.constants.Constant.MISTAKES_NUM;
import static com.gxuwz.zy.constants.Constant.SCORE;
import static com.gxuwz.zy.constants.Constant.TAKE_TIME;
import static com.gxuwz.zy.constants.Constant.UN_ANSWERED_NUM;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseActivity;
import com.gxuwz.zy.exam.activity.question.PracticeNormalActivity;

public class PracticeEndActivity extends BaseActivity {

    private LinearLayout mLL_questionback;

    @Override
    protected int initContentView() {
        return R.layout.activity_practice_end;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();

        int unAnsweredNum = intent.getIntExtra(UN_ANSWERED_NUM, 0);
        int takeTime = intent.getIntExtra(TAKE_TIME, 0);
        int score = intent.getIntExtra(SCORE, 0);
        int mistakesNum = intent.getIntExtra(MISTAKES_NUM, 0);

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