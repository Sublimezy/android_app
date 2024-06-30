package com.xueyiche.zjyk.jiakao.exam.fragment.subject.subject_b;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.constants.App;


public class SubjectBFragment extends BaseFragment {

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.home_exam_subjectb, null);
//        showProgressDialog((Activity) getContext(), false, "你好");
        return view;
    }


    @Override
    protected Object setLoadDate() {
        return "1111";
    }
}
