package com.xueyiche.zjyk.jiakao.exam.adapter.question;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xueyiche.zjyk.jiakao.exam.entity.dos.QuestionBean;
import com.xueyiche.zjyk.jiakao.exam.fragment.question.QuestionFragment;

import java.util.List;

public class QuestionAdapter extends FragmentPagerAdapter {



    private List<QuestionBean> questionBeanList;

    public QuestionAdapter(@NonNull FragmentManager fm, List<QuestionBean> questionBeanList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.questionBeanList = questionBeanList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return QuestionFragment.newInstance(questionBeanList.size(), position, questionBeanList.get(position));

    }

    @Override
    public int getCount() {
        return questionBeanList.size();
    }
}
