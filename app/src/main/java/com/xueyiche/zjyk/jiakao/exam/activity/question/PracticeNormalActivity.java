package com.xueyiche.zjyk.jiakao.exam.activity.question;

import static com.xueyiche.zjyk.jiakao.constants.Constant.ALL_TIME;
import static com.xueyiche.zjyk.jiakao.constants.Constant.ALL_TIME_MIN;
import static com.xueyiche.zjyk.jiakao.constants.Constant.DEF_QUESTION_TYPE;
import static com.xueyiche.zjyk.jiakao.constants.Constant.DEF_SUBJECT;
import static com.xueyiche.zjyk.jiakao.constants.Constant.ONCE_TIME;
import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_ALL_QUESTION;
import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_COLLECT;
import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_MISTAKES;
import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_MISTAKES_QUESTION;
import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_MODEL;
import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_PAGE;
import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_QUESTION_TYPE;
import static com.xueyiche.zjyk.jiakao.constants.Constant.PRE_SUBJECT;
import static com.xueyiche.zjyk.jiakao.constants.Constant.SUBJECT1_NUM;
import static com.xueyiche.zjyk.jiakao.constants.Constant.SUBJECT4_NUM;
import static com.xueyiche.zjyk.jiakao.constants.Constant.TAKE_START_TIME;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.COLLECTION;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.MISTAKES;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.PRACTICE;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.SEQUENCE;
import static com.xueyiche.zjyk.jiakao.exam.entity.enums.PageEnum.SPECIALS;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.xueyiche.zjyk.jiakao.exam.activity.practice.PracticeEndActivity;
import com.xueyiche.zjyk.jiakao.exam.adapter.question.QuestionAdapter;
import com.xueyiche.zjyk.jiakao.exam.database.MyGradeDBHelper;
import com.xueyiche.zjyk.jiakao.exam.database.QuestionDBHelper;
import com.xueyiche.zjyk.jiakao.exam.entity.dos.MyGradeBean;
import com.xueyiche.zjyk.jiakao.exam.entity.dos.QuestionBean;
import com.xueyiche.zjyk.jiakao.exam.fragment.question.QuestionFragment;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;
import com.xueyiche.zjyk.jiakao.view.ReaderViewPager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PracticeNormalActivity extends BaseActivity implements QuestionFragment.FragmentListener, ViewPager.OnPageChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    public static PracticeNormalActivity instance;


    private QuestionDBHelper mHelper;
    private List<QuestionBean> questionBeanList = new ArrayList<>();
    private List<String> collect;
    private Integer questionBeanListSize;
    private QuestionBean queryQuestionParams;
    private QuestionBean questionBean;
    private Long subject = null;
    private String model = null;
    private String page = null;
    private final Long id = null;
    private Long questionType = null;

    private LinearLayout mLL_questionback;
    private TextView questionNum;
    private ReaderViewPager readerViewPager;
    private CheckBox ckCollection;
    private TextView countDownTimeTxt;
    private TextView tvTitleSum;
    private List<String> busList;
    private List<String> mistakes;

    //实现倒计时
    private final CountDownTimer countDownTime = new CountDownTimer(ALL_TIME, ONCE_TIME) {

        //还剩多长时间
        @Override
        public void onTick(long millisUntilFinished) {
            // 将毫秒转换成分钟和秒钟
            long seconds = millisUntilFinished / 1000;
            long minutes = seconds / 60;
            seconds = seconds % 60;

            // 构造显示的字符串
            String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

            // 将格式化后的时间显示在界面上
            countDownTimeTxt.setText(timeLeftFormatted);
        }

        //完成后
        @Override
        public void onFinish() {

        }
    };


    @Override
    protected int initContentView() {
        return R.layout.home_exam_subjecta_question;
    }

    @Override
    protected void initView() {

        instance = this;
        mHelper = QuestionDBHelper.getInstance(App.context);

        Intent intent = getIntent();
        subject = intent.getLongExtra(PRE_SUBJECT, DEF_SUBJECT);
        model = intent.getStringExtra(PRE_MODEL);
        page = intent.getStringExtra(PRE_PAGE);
        questionType = intent.getLongExtra(PRE_QUESTION_TYPE, DEF_QUESTION_TYPE);

        collect = PrefUtils.getStrListValue(this, PRE_COLLECT);

        mistakes = PrefUtils.getStrListValue(this, PRE_MISTAKES);

        countDownTimeTxt = view.findViewById(R.id.exam_question_include).findViewById(R.id.count_down_time);
        tvTitleSum = view.findViewById(R.id.exam_question_include).findViewById(R.id.tv_title_sum);


        if (page.equals(MISTAKES.name()) || page.equals(COLLECTION.name())) {
            if (page.equals(MISTAKES.name())) {
                busList = mistakes;
            } else if (page.equals(COLLECTION.name())) {
                busList = collect;
            }
            for (String id : busList) {
                queryQuestionParams = new QuestionBean(Long.parseLong(id), subject, model, null);
                questionBeanList.addAll(mHelper.getAllQuestionByParams(queryQuestionParams));
            }
        } else if (page.equals(SPECIALS.name())) {
            queryQuestionParams = new QuestionBean(id, subject, model, questionType);
            questionBeanList = mHelper.getAllQuestionByParams(queryQuestionParams);
        } else if (page.equals(PRACTICE.name()) || page.equals(SEQUENCE.name())) {

            queryQuestionParams = new QuestionBean(id, subject, model, null);

            questionBeanList = mHelper.getAllQuestionByParams(queryQuestionParams);

            if (page.equals(PRACTICE.name())) {

                countDownTimeTxt.setVisibility(View.VISIBLE);

                tvTitleSum.setVisibility(View.VISIBLE);

                if (subject == 1) {
                    // 调用 getRandomQuestions 方法获取随机的50条记录
                    questionBeanList = getRandomQuestions(questionBeanList, SUBJECT1_NUM);
                } else if (subject == 4) {
                    // 调用 getRandomQuestions 方法获取随机的100条记录
                    questionBeanList = getRandomQuestions(questionBeanList, SUBJECT4_NUM);
                }

                countDownTime.start();
            }


        }

        questionBeanListSize = questionBeanList.size();


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
        tvTitleSum.setOnClickListener(this);
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
            if (ckCollection.isChecked()) {
//第一次进入

                collect.add(String.valueOf(questionBean.getId()));

//                ToastUtils.showToast(this, "收藏成功");
                ckCollection.setText("取消收藏");
                ToastUtils.showToast(this, "收藏成功id为" + questionBean.getId());
            } else {

                collect.remove(String.valueOf(questionBean.getId()));

                Iterator<String> iterator = collect.iterator();
                while (iterator.hasNext()) {
                    String value = iterator.next();
                    if (value.equals(String.valueOf(questionBean.getId()))) {
                        iterator.remove();
                    }
                }

//                ToastUtils.showToast(this, "取消收藏成功");
                ckCollection.setText("收藏");
                ToastUtils.showToast(this, "移除收藏id为" + questionBean.getId());

            }
            PrefUtils.putStrListValue(this, "collect", collect);

        } else if (v_id == R.id.tv_title_sum) {

            List<String> allList = PrefUtils.getStrListValue(this, PRE_ALL_QUESTION);
            List<String> mistakeList = PrefUtils.getStrListValue(this, PRE_MISTAKES_QUESTION);
            String startTime = PrefUtils.getString(this, TAKE_START_TIME, null);

            //保存记录到数据库
            MyGradeDBHelper mHelper = MyGradeDBHelper.getInstance(App.context);

            MyGradeBean myGradeBean = new MyGradeBean();

            Date current = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            Date startDate = null;
            try {
                startDate = dateFormat.parse(startTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            long diffInMillis = current.getTime() - startDate.getTime();
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);


            myGradeBean.setExamDate(startTime);
            myGradeBean.setTakeTime((int) diffInMinutes);
            myGradeBean.setTotalQuestions(subject == 1 ? SUBJECT1_NUM : SUBJECT4_NUM);
            myGradeBean.setTrueNum(allList.size() - mistakeList.size());
            myGradeBean.setMistakesNum(mistakeList.size());
            myGradeBean.setAllTime(ALL_TIME_MIN);
            myGradeBean.setScore(subject == 1 ? myGradeBean.getTrueNum() : myGradeBean.getTrueNum() * 2);
            myGradeBean.setUnAnsweredNum(myGradeBean.getTotalQuestions() - allList.size());
            String mistakesQuestion = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                mistakesQuestion = mistakeList.stream()
                        .collect(Collectors.joining(","));
            }
            myGradeBean.setMistakesQuestion(mistakesQuestion);
            myGradeBean.setSubject(Math.toIntExact(subject));
            myGradeBean.setModel(model);

            mHelper.insert(myGradeBean);
            //跳转
            Bundle args = new Bundle();
            args.putInt("unAnsweredNum", myGradeBean.getUnAnsweredNum());
            args.putInt("takeTime", myGradeBean.getTakeTime());
            args.putInt("score", myGradeBean.getScore());
            args.putInt("mistakesNum", myGradeBean.getMistakesNum());
            readyGo(PracticeEndActivity.class, args);

        }
    }


    void isCollect(int position) {
        ToastUtils.showToast(this, "收藏id为" + collect);

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

    public List<QuestionBean> getRandomQuestions(List<QuestionBean> questionBeanList, int count) {
        List<QuestionBean> randomQuestionList = new ArrayList<>();

        // 如果原列表 questionBeanList 的大小小于等于 count，直接将其全部添加到新列表中
        if (questionBeanList.size() <= count) {
            randomQuestionList.addAll(questionBeanList);
        } else {

            // 创建一个包含所有索引的列表
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < questionBeanList.size(); i++) {
                indexes.add(i);
            }

            // 打乱索引顺序
            Collections.shuffle(indexes);

            // 取前 count 个索引对应的元素
            for (int i = 0; i < count; i++) {
                int index = indexes.get(i);
                randomQuestionList.add(questionBeanList.get(index));
            }

        }

        return randomQuestionList;
    }
}
