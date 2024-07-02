package com.gxuwz.zy.exam.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.viewpager.widget.ViewPager;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseFragment;
import com.gxuwz.zy.constants.App;
import com.gxuwz.zy.exam.adapter.subject.MyExamAdapter;
import com.gxuwz.zy.utils.PrefUtils;
import com.gyf.immersionbar.ImmersionBar;


public class ExamFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    //定义禁用滑动的ViewPager
//    private static NoScrollViewPager viewPager;
    private static ViewPager viewPager;

    public static ExamFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        ExamFragment fragment = new ExamFragment();
        bundle.putString("jiakao", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    //4个菜单按钮
    public static RadioGroup mRG_exam_menutab;


    private void initView(View view) {
        viewPager = view.findViewById(R.id.vp_main);
        mRG_exam_menutab = view.findViewById(R.id.rg_exam_menutab);


        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
        viewPager.addOnPageChangeListener(this);
        initData();


    }


    private void initData() {

        viewPager.setAdapter(new MyExamAdapter(getChildFragmentManager()));
        mRG_exam_menutab.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_subject1:

                viewPager.setCurrentItem(0, false);//禁用动画
                break;
            case R.id.rb_subject2:
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.rb_subject3:
                viewPager.setCurrentItem(2, false);
                break;
        }
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.activity_exam, null);
        initView(view);
        initData();
        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "jiakao";
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


        switch (position) {
            case 0:
                mRG_exam_menutab.check(R.id.rb_subject1);
                break;
            case 1:
                mRG_exam_menutab.check(R.id.rb_subject2);
                break;
            case 2:
                mRG_exam_menutab.check(R.id.rb_subject3);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStart() {
        super.onStart();
        String string = PrefUtils.getString(App.context, "model", "c1");

        showToastShort("当前的驾照类型---" + string);
    }
}
