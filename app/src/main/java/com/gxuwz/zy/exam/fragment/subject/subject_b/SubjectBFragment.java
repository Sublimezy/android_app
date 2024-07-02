package com.gxuwz.zy.exam.fragment.subject.subject_b;

import android.view.LayoutInflater;
import android.view.View;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseFragment;
import com.gxuwz.zy.constants.App;


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
