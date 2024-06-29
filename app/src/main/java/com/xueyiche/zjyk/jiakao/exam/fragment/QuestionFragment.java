package com.xueyiche.zjyk.jiakao.exam.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.homepage.bean.QuestionBean;

public class QuestionFragment extends Fragment {
    private FragmentListener fragmentListener;

    public interface FragmentListener {
        void process(int str);
    }

    private int position;
    private TextView mTV_analysis_answer;
    private TextView mTV_analysis;
    private LinearLayout ll_explan;
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

        View view = LayoutInflater.from(context).inflate(R.layout.subjecta_question_item, container, false);

        Bundle arguments = getArguments();

        int size = arguments.getInt("size", 0);
        position = arguments.getInt("position", 0);
        QuestionBean questionBean = arguments.getParcelable("questionBean");


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
            questionType = "单选题";
        } else if (question_type == 1) {
            questionType = "判断题";
        } else if (question_type == 2) {
            questionType = "多选题";
        }


        //题目
        TextView mTV_qusetion = (TextView) view.findViewById(R.id.question);

        //题目类型
        TextView mIV_questiontype = view.findViewById(R.id.question_type);

        //答案的文本内容
        TextView mTV_ansa = (TextView) view.findViewById(R.id.tv_ansa);
        TextView mTV_ansb = (TextView) view.findViewById(R.id.tv_ansb);
        TextView mTV_ansc = (TextView) view.findViewById(R.id.tv_ansc);
        TextView mTV_ansd = (TextView) view.findViewById(R.id.tv_ansd);


        ll_explan = view.findViewById(R.id.ll_explan);
        //解析文本内容
        mTV_analysis_answer = (TextView) view.findViewById(R.id.tv_analysis_answer);
        mTV_analysis = (TextView) view.findViewById(R.id.tv_analysis);


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

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentListener) {
            fragmentListener = (FragmentListener) activity;
        }
    }

    void handleLinearLayoutClick(String option, String answer) {
//设置正确选项的背景
        //如果选错，
        if (!option.equals(answer)) {
            ll_explan.setVisibility(View.VISIBLE);
            mTV_analysis_answer.setText("答案：" + answer + "您的选项" + option);
//给option设置红色,显示 explain,
        } else {
//延迟1秒后翻页
            fragmentListener.process(position);
        }


    }
}
