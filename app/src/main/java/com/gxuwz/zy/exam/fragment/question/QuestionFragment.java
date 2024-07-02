package com.gxuwz.zy.exam.fragment.question;

import static com.gxuwz.zy.constants.Constant.PRE_ALL_QUESTION;
import static com.gxuwz.zy.constants.Constant.PRE_MISTAKES;
import static com.gxuwz.zy.constants.Constant.PRE_MISTAKES_QUESTION;
import static com.gxuwz.zy.exam.entity.enums.AnswerEnum.A;
import static com.gxuwz.zy.exam.entity.enums.AnswerEnum.B;
import static com.gxuwz.zy.exam.entity.enums.AnswerEnum.C;
import static com.gxuwz.zy.exam.entity.enums.AnswerEnum.D;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gxuwz.zy.R;
import com.gxuwz.zy.exam.entity.dos.QuestionBean;
import com.gxuwz.zy.utils.PrefUtils;
import com.gxuwz.zy.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionFragment extends Fragment implements View.OnClickListener {
    private FragmentListener fragmentListener;

    private List<String> mistakes;


    public interface FragmentListener {
        void process(int str);
    }

    private int position;
    private TextView mTV_analysis_answer;
    private TextView mTV_analysis;
    private LinearLayout ll_explan;
    private ImageView iv_a;
    private ImageView iv_b;
    private ImageView iv_c;
    private ImageView iv_d;
    private final List<ImageView> answerImageList = new ArrayList<>();
    private final List<ImageView> optionImageList = new ArrayList<>();
    private String answer;
    private LinearLayout mLL_a;
    private LinearLayout mLL_b;
    private LinearLayout mLL_c;
    private LinearLayout mLL_d;
    private String questionType = null;
    private Button answerVerify;
    private QuestionBean questionBean;

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
        questionBean = arguments.getParcelable("questionBean");


        mistakes = PrefUtils.getStrListValue(getContext(), PRE_MISTAKES);


        final Long question_type = questionBean.getQuestionType();
        final String question = questionBean.getQuestion();
        final String item1 = questionBean.getItem1();
        final String item2 = questionBean.getItem2();
        final String item3 = questionBean.getItem3();
        final String item4 = questionBean.getItem4();
        final String url = questionBean.getUrl();
        answer = questionBean.getAnswer();
        final String explains = questionBean.getExplains();


        //题目图片
        ImageView image_url = view.findViewById(R.id.image_url);


        //题目类型
        TextView mIV_questiontype = view.findViewById(R.id.question_type);

        //每一条选项可点击
        mLL_a = view.findViewById(R.id.ll_a);
        mLL_b = view.findViewById(R.id.ll_b);
        mLL_c = view.findViewById(R.id.ll_c);
        mLL_d = view.findViewById(R.id.ll_d);

        // A B C D
        iv_a = view.findViewById(R.id.iv_a);
        iv_b = view.findViewById(R.id.iv_b);
        iv_c = view.findViewById(R.id.iv_c);
        iv_d = view.findViewById(R.id.iv_d);

        //答案的文本内容
        TextView mTV_ansa = view.findViewById(R.id.tv_ansa);
        TextView mTV_ansb = view.findViewById(R.id.tv_ansb);
        TextView mTV_ansc = view.findViewById(R.id.tv_ansc);
        TextView mTV_ansd = view.findViewById(R.id.tv_ansd);

        ll_explan = view.findViewById(R.id.ll_explan);
        //解析文本内容
        mTV_analysis_answer = view.findViewById(R.id.tv_analysis_answer);
        mTV_analysis = view.findViewById(R.id.tv_analysis);

        answerVerify = view.findViewById(R.id.answer_verify);

        String answerTmp = answer;
        answer = "";
        //遍历循环正确答案
        for (char myAnswer : answerTmp.toCharArray()) {
            switch (myAnswer) {
                case '1':
                    answer = answer + A;
                    answerImageList.add(iv_a);
                    break;
                case '2':
                    answer = answer + B;
                    answerImageList.add(iv_b);
                    break;
                case '3':
                    answer = answer + C;
                    answerImageList.add(iv_c);
                    break;
                case '4':
                    answer = answer + D;
                    answerImageList.add(iv_d);
                    break;
            }
        }


        mTV_ansa.setText(item1);
        mTV_ansb.setText(item2);
        mTV_ansc.setText(item3);
        mTV_ansd.setText(item4);
        mTV_analysis.setText(explains);


        //题目
        TextView mTV_qusetion = view.findViewById(R.id.question);

        if (question_type == 0) {
            questionType = "单选题";
        } else if (question_type == 1) {
            questionType = "多选题";
            answerVerify.setVisibility(View.VISIBLE);
        } else if (question_type == 2) {
            mLL_c.setVisibility(View.INVISIBLE);
            mLL_d.setVisibility(View.INVISIBLE);
            questionType = "判断题";
        }
        mTV_qusetion.setText(question);
        mIV_questiontype.setText(questionType);

        if (!url.isEmpty()) {
            Picasso.with(getContext()).load(url).into(image_url);
        }
        answerVerify.setOnClickListener(this);
        // 设置点击事件监听器
        mLL_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinearLayoutClick(A.getDescription(), iv_a);
            }
        });


        mLL_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinearLayoutClick(B.getDescription(), iv_b);
            }
        });

        mLL_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinearLayoutClick(C.getDescription(), iv_c);
            }
        });

        mLL_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLinearLayoutClick(D.getDescription(), iv_d);
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

    private String option = "";

    void handleLinearLayoutClick(char option, ImageView optionImg) {


        if (questionType.equals("多选题")) {

            if (this.option.contains(String.valueOf(option))) {
                optionImageList.remove(optionImg);
                this.option = StringUtils.removeCharacter(this.option, option);

                switch (option) {
                    case 'A':
                        optionImg.setImageResource(R.mipmap.a);
                        break;
                    case 'B':
                        optionImg.setImageResource(R.mipmap.b);
                        break;
                    case 'C':
                        optionImg.setImageResource(R.mipmap.c);
                        break;
                    case 'D':
                        optionImg.setImageResource(R.mipmap.d);
                        break;
                }

            } else {
                optionImageList.add(optionImg);
                this.option = this.option + option;
                //给选中的设置背景
                showOptionImage();
            }
        } else {
            optionImageList.add(optionImg);
            this.option = String.valueOf(option);
            answerVerifyMethod();
        }
    }

    void answerVerifyMethod() {

        String questionId = String.valueOf(questionBean.getId());
        char[] arrOption = option.toCharArray();
        char[] arrAnswer = answer.toCharArray();
        Arrays.sort(arrOption);
        Arrays.sort(arrAnswer);

        //模拟考试 记录做题（哪些没做）
        List<String> mistakesAllList = PrefUtils.getStrListValue(getContext(), PRE_ALL_QUESTION);
        if (!mistakesAllList.contains(questionId)) {
            mistakesAllList.add(questionId);
            PrefUtils.putStrListValue(getContext(), PRE_ALL_QUESTION, mistakesAllList);
        }


        if (Arrays.equals(arrOption, arrAnswer)) {

            if (mistakes.contains(questionId)) {
                //添加我的错题  //移除错题 //不能重复
                mistakes.remove(questionId);
                PrefUtils.putStrListValue(getContext(), PRE_MISTAKES, mistakes);
//                ToastUtils.showToast(getContext(), "移除id为" + questionId + "的错题");
            }

            fragmentListener.process(position);
        } else {
            ll_explan.setVisibility(View.VISIBLE);
            mTV_analysis_answer.setText("答案：" + StringUtils.sortStringByABCD(answer) + "您的选项" + StringUtils.sortStringByABCD(option));
            showCuoImage();

            if (!mistakes.contains(questionId)) {
                //添加我的错题  //移除错题 //不能重复
                mistakes.add(questionId);

                PrefUtils.putStrListValue(getContext(), PRE_MISTAKES, mistakes);

                //模拟考试
                List<String> mistakesQuestionList = PrefUtils.getStrListValue(getContext(), PRE_MISTAKES_QUESTION);
                mistakesQuestionList.add(questionId);
                PrefUtils.putStrListValue(getContext(), PRE_MISTAKES_QUESTION, mistakesQuestionList);

//                ToastUtils.showToast(getContext(), "添加id为" + questionId + "的错题");

            }

        }
        showTrueImage();

        mLL_a.setOnClickListener(null);
        mLL_b.setOnClickListener(null);
        mLL_c.setOnClickListener(null);
        mLL_d.setOnClickListener(null);
    }

    //给正确的选项设置背景
    void showTrueImage() {
        for (ImageView imageView : answerImageList) {
            imageView.setImageResource(R.mipmap.zhengque);
        }
    }

    //给错误的选项设置背景
    void showCuoImage() {
        for (ImageView imageView : optionImageList) {
            imageView.setImageResource(R.mipmap.cuowu);
        }
    }

    //给自己选中的选项设置背景
    void showOptionImage() {
        for (ImageView imageView : optionImageList) {
            if (imageView == iv_a) {
                imageView.setImageResource(R.mipmap.a_true);
            } else if (imageView == iv_b) {
                imageView.setImageResource(R.mipmap.b_true);
            } else if (imageView == iv_c) {
                imageView.setImageResource(R.mipmap.c_true);
            } else if (imageView == iv_d) {
                imageView.setImageResource(R.mipmap.d_true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.answer_verify) {
            answerVerifyMethod();
        }
    }


}
