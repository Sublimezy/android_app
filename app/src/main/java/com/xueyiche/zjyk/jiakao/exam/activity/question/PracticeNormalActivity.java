package com.xueyiche.zjyk.jiakao.exam.activity.question;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.luck.picture.lib.utils.ToastUtils;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.exam.adapter.question.QuestionAdapter;
import com.xueyiche.zjyk.jiakao.exam.entity.dos.QuestionBean;
import com.xueyiche.zjyk.jiakao.exam.fragment.question.QuestionFragment;
import com.xueyiche.zjyk.jiakao.exam.database.QuestionDBHelper;
import com.xueyiche.zjyk.jiakao.view.ReaderViewPager;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;

import java.util.Iterator;
import java.util.List;


public class PracticeNormalActivity extends BaseActivity implements QuestionFragment.FragmentListener, ViewPager.OnPageChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    private QuestionDBHelper mHelper;
    private List<QuestionBean> questionBeanList;
    private List<String> collect;
    private Integer questionBeanListSize;
    private QuestionBean queryQuestionParams;
    private QuestionBean questionBean;
    private Long subject = null;
    private String model = null;


    private LinearLayout mLL_questionback;
    private TextView questionNum;
    private ReaderViewPager readerViewPager;
    private CheckBox ckCollection;


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


        queryQuestionParams = new QuestionBean(null, subject, model, null);
        questionBeanList = mHelper.getAllQuestionByParams(queryQuestionParams);
        questionBeanListSize = questionBeanList.size();

        collect = PrefUtils.getStrListValue(this, "collect");

        //返回
        mLL_questionback = view.findViewById(R.id.exam_question_include).findViewById(R.id.ll_question_back);
        mLL_questionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //题目顺序
        questionNum = view.findViewById(R.id.exam_question_include).findViewById(R.id.tv_title_num);
        questionNum.setText(1 + "/" + questionBeanListSize);

        //收藏
        ckCollection = view.findViewById(R.id.exam_question_include).findViewById(R.id.ck_collection);

        readerViewPager = view.findViewById(R.id.vp_subjectA);
        QuestionAdapter adapter = new QuestionAdapter(getSupportFragmentManager(), questionBeanList);
        readerViewPager.setAdapter(adapter);
        readerViewPager.addOnPageChangeListener(this);
        ckCollection.setOnClickListener(this);

        if (readerViewPager.getCurrentItem() == 0) {
            questionBean = questionBeanList.get(readerViewPager.getCurrentItem());
            isCollect(readerViewPager.getCurrentItem());

        }


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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        isCollect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


    }


    @Override
    public void onClick(View view) {
        int v_id = view.getId();
        if (v_id == R.id.ck_collection) {
            ToastUtils.showToast(this, ckCollection.isChecked() + "状态");

            if (ckCollection.isChecked()) {
//第一次进入

                collect.add(String.valueOf(questionBean.getId()));
                PrefUtils.putStrListValue(this, "collect", collect);
                ToastUtils.showToast(this, "收藏成功");
                ckCollection.setChecked(true);
                ckCollection.setText("取消收藏");
            } else {

                Iterator<String> iterator = collect.iterator();
                while (iterator.hasNext()) {
                    String value = iterator.next();
                    if (value.equals(String.valueOf(questionBean.getId()))) {
                        iterator.remove();
                    }
                }
                ToastUtils.showToast(this, "取消收藏成功");
                ckCollection.setChecked(false);
                ckCollection.setText("收藏");
            }

        }
    }


    void isCollect(int position) {
        questionNum.setText((position + 1) + "/" + questionBeanListSize);
        questionBean = questionBeanList.get(position);
        if (collect.contains(String.valueOf(questionBean.getId()))) {
            ckCollection.setChecked(true);
            ckCollection.setText("取消收藏");
        } else {
            ckCollection.setChecked(false);
            ckCollection.setText("收藏");
        }
    }
}
