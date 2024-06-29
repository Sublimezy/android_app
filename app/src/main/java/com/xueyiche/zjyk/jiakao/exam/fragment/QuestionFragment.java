package com.xueyiche.zjyk.jiakao.exam.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.homepage.bean.QuestionBean;
import com.xueyiche.zjyk.jiakao.homepage.db.QuestionDBHelper;
import com.xueyiche.zjyk.jiakao.homepage.view.ReaderViewPager;

import java.util.List;
import java.util.Random;

public class QuestionFragment extends Fragment {
    //顺序练习
    private ReaderViewPager readerViewPager;
    private PagerAdapter adapter;

    private QuestionDBHelper mHelp;

    private TextView mTV_title_mun;

    private CheckBox ck_collection;
    private int[] imgs = {R.mipmap.pic_yi, R.mipmap.pic_er, R.mipmap.pic_san, R.mipmap.pic_si, R.mipmap.pic_wu, R.mipmap.pic_liu,
            R.mipmap.pic_qi, R.mipmap.pic_ba, R.mipmap.pic_jiu, R.mipmap.pic_shi, R.mipmap.pic_shiyi, R.mipmap.pic_shier,
            R.mipmap.pic_shisan};

    public static QuestionFragment newInstance(int size, int position, QuestionBean questionBean) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt("size", size);
        args.putInt("position", position);
        args.putParcelable("questionBean", questionBean);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Context context = getContext();
        Bundle arguments = getArguments();

        int size = arguments.getInt("size", 0);
        int position = arguments.getInt("position", 0);
        QuestionBean questionBean = arguments.getParcelable("questionBean");

        View view = LayoutInflater.from(context).inflate(R.layout.subjecta_question_item, container, false);


        //题目的数量
        mTV_title_mun = (TextView) view.findViewById(R.id.exam_question_include).findViewById(R.id.tv_title_num);
        mTV_title_mun.setText(1 + "/" + size);

        //收藏
        ck_collection = (CheckBox) view.findViewById(R.id.exam_question_include).findViewById(R.id.ck_collection);


        final Long question_type = questionBean.getQuestionType();
        final String question = questionBean.getQuestion();
        final String item1 = questionBean.getItem1();
        final String item2 = questionBean.getItem2();
        final String item3 = questionBean.getItem3();
        final String item4 = questionBean.getItem4();
        final String url = questionBean.getUrl();
        final String answer = questionBean.getAnswer();
        final String explains = questionBean.getExplains();

        String questionType = null;

        if (question_type == 0) {
            questionType = "选择题";
        } else if (question_type == 1) {
            questionType = "判断题";
        } else if (question_type == 2) {
            questionType = "多选题";
        }


        //题目
        TextView mTV_qusetion = (TextView) view.findViewById(R.id.tv_question);

        //题目类型
        TextView mIV_questiontype = view.findViewById(R.id.iv_questiontype);

        //答案的文本内容
        TextView mTV_ansa = (TextView) view.findViewById(R.id.tv_ansa);
        TextView mTV_ansb = (TextView) view.findViewById(R.id.tv_ansb);
        TextView mTV_ansc = (TextView) view.findViewById(R.id.tv_ansc);
        TextView mTV_ansd = (TextView) view.findViewById(R.id.tv_ansd);

        //解析文本内容
        TextView mTV_analysis = (TextView) view.findViewById(R.id.tv_analysis);

        mTV_qusetion.setText(question);
        mIV_questiontype.setText(questionType);
        mTV_ansa.setText(item1);
        mTV_ansb.setText(item2);
        mTV_ansc.setText(item3);
        mTV_ansd.setText(item4);
        mTV_analysis.setText(explains);

        //每一条选项可点击
        final LinearLayout mLL_a = (LinearLayout) view.findViewById(R.id.ll_a);
        final LinearLayout mLL_b = (LinearLayout) view.findViewById(R.id.ll_b);
        final LinearLayout mLL_c = (LinearLayout) view.findViewById(R.id.ll_c);
        final LinearLayout mLL_d = (LinearLayout) view.findViewById(R.id.ll_d);


        // 设置点击事件监听器
        mLL_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinearLayoutClick("1", answer);
            }
        });


        mLL_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinearLayoutClick("2", answer);
            }
        });

        mLL_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinearLayoutClick("3", answer);
            }
        });

        mLL_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinearLayoutClick("4", answer);
            }
        });

        return view;
    }

    void handleLinearLayoutClick(String option, String answer) {

        if (!option.equals(answer)) {

        } else {

        }

    }
}
