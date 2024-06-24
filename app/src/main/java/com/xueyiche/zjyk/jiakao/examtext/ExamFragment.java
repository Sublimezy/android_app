package com.xueyiche.zjyk.jiakao.examtext;

import android.content.Intent;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.main.activities.main.MainActivity;
import com.xueyiche.zjyk.jiakao.main.view.NoScrollViewPager;
import com.xueyiche.zjyk.jiakao.mine.fragments.MineFragment;

/**
 * Created by Owner on 2016/9/23.
 */
public class ExamFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    //定义禁用滑动的ViewPager
    private static NoScrollViewPager viewPager;
    public static ExamFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        ExamFragment fragment = new ExamFragment();
        bundle.putString("jiakao", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    //4个菜单按钮
    public static RadioGroup mRG_exam_menutab;
    private TextView tvBaoMing,tvTitle;

    private void initView(View view) {
        viewPager = (NoScrollViewPager)view. findViewById(R.id.vp_main);
        mRG_exam_menutab = (RadioGroup)view. findViewById(R.id.rg_exam_menutab);
        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();

        initData();
    }


    private void initData() {
        FragmentManager supportFragmentManager = getChildFragmentManager();
        viewPager.setAdapter(new MyExamAdapter(supportFragmentManager));
        mRG_exam_menutab.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_subject1:
                viewPager.setCurrentItem(0,false);//禁用动画
                break;
//            case R.id.rb_subject2:
//                viewPager.setCurrentItem(1,false);
//                break;
//            case R.id.rb_subject3:
//                viewPager.setCurrentItem(2,false);
//                break;
            case R.id.rb_subject4:
                viewPager.setCurrentItem(1,false);
                break;
        }
    }



    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.activity_exam,null);
        initView(view);
        initData();
        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "jiakao";
    }
}
