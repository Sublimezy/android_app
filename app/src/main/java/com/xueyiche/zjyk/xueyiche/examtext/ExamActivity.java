package com.xueyiche.zjyk.xueyiche.examtext;

import android.content.Intent;
import androidx.fragment.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.main.view.NoScrollViewPager;

/**
 * Created by Owner on 2016/9/23.
 */
public class ExamActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {
    //定义禁用滑动的ViewPager
    private static NoScrollViewPager viewPager;
    private LinearLayout llExamBack;

    //4个菜单按钮
    public static RadioGroup mRG_exam_menutab;
    private TextView tvBaoMing,tvTitle;
    @Override
    protected int initContentView() {
        return R.layout.activity_exam;
    }

    @Override
    protected void initView() {
        viewPager = (NoScrollViewPager)view. findViewById(R.id.vp_main);
        mRG_exam_menutab = (RadioGroup)view. findViewById(R.id.rg_exam_menutab);
        llExamBack = (LinearLayout) view.findViewById(R.id.exam_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.exam_include).findViewById(R.id.tv_login_back);
        tvBaoMing = (TextView) view.findViewById(R.id.exam_include).findViewById(R.id.tv_wenxintishi);
        llExamBack.setOnClickListener(this);
        tvBaoMing.setOnClickListener(this);

        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();

        initData();
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyExamAdapter(supportFragmentManager));
        mRG_exam_menutab.setOnCheckedChangeListener(this);
        tvTitle.setText("驾考练习");
        tvBaoMing.setText("报名");
        tvBaoMing.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_subject1:
                viewPager.setCurrentItem(0,false);//禁用动画
                break;
            case R.id.rb_subject2:
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.rb_subject3:
                viewPager.setCurrentItem(2,false);
                break;
            case R.id.rb_subject4:
                viewPager.setCurrentItem(3,false);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:
                Intent intent = new Intent(App.context, MainActivity.class);
                intent.putExtra("position",1);
                startActivity(intent);
                finish();
                break;
        }

    }
}
