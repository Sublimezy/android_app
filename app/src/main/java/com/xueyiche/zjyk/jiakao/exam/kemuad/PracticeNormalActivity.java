package com.xueyiche.zjyk.jiakao.exam.kemuad;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.exam.adapter.QuestionAdapter;
import com.xueyiche.zjyk.jiakao.exam.fragment.QuestionFragment;
import com.xueyiche.zjyk.jiakao.homepage.bean.QuestionBean;
import com.xueyiche.zjyk.jiakao.homepage.db.QuestionDBHelper;
import com.xueyiche.zjyk.jiakao.homepage.view.ReaderViewPager;

import java.util.List;


public class PracticeNormalActivity extends BaseActivity implements QuestionFragment.FragmentListener {

    private LinearLayout mLL_questionback;
    private ReaderViewPager readerViewPager;
    private QuestionDBHelper mHelper;
    private List<QuestionBean> questionBeanList;
    private QuestionBean queryQuestionParams;

    Long subject = null;
    String model = null;

    @Override
    protected int initContentView() {
        return R.layout.home_exam_subjecta_question;
    }

    @Override
    protected void initView() {

        mHelper = QuestionDBHelper.getInstance(App.context);

        Intent intent = getIntent();

        subject = intent.getLongExtra("subject", 1);
        model = intent.getStringExtra("model");


        //返回
        mLL_questionback = (LinearLayout) view.findViewById(R.id.exam_question_include).findViewById(R.id.ll_question_back);
        mLL_questionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        queryQuestionParams = new QuestionBean(null, subject, model, null);


        questionBeanList = mHelper.getAllQuestionByParams(queryQuestionParams);


        readerViewPager = (ReaderViewPager) view.findViewById(R.id.vp_subjectA);

        QuestionAdapter adapter = new QuestionAdapter(getSupportFragmentManager(), questionBeanList);

        readerViewPager.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @Override
    public void process(int position) {
        readerViewPager.setCurrentItem(position + 1);
    }
}
