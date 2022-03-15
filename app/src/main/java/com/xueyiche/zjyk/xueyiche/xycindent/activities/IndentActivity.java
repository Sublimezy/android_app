package com.xueyiche.zjyk.xueyiche.xycindent.activities;

import android.content.Intent;
import androidx.fragment.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.IndentViewAdapter;
import com.xueyiche.zjyk.xueyiche.main.view.NoScrollViewPager;

import de.greenrobot.event.EventBus;

/**
 * Created by zhanglei on 2016/10/25.
 */
public class IndentActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private  static RadioGroup radioGroup;
    private NoScrollViewPager viewPager;
    private TextView tv_login_back;
    private ImageView iv_login_back;


    @Override
    protected int initContentView() {
        return R.layout.indent_activity;
    }

    @Override
    protected void initView() {
        //标题栏的一些内容
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        iv_login_back = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_login_back);
        tv_login_back.setText("我的订单");
        viewPager = (NoScrollViewPager) view.findViewById(R.id.indent_view_pager);
        viewPager.setOffscreenPageLimit(0);
        radioGroup = (RadioGroup) view.findViewById(R.id.rg_indent);
        radioGroup.setOnCheckedChangeListener(this);
        iv_login_back.setOnClickListener(this);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new IndentViewAdapter(supportFragmentManager));
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        if (position==0){
            viewPager.setCurrentItem(0);
            radioGroup.check(R.id.rb_indent1);
        }else if (position==1){
            viewPager.setCurrentItem(1);
            radioGroup.check(R.id.rb_indent2);
        }else if (position==2){
            viewPager.setCurrentItem(2);
            radioGroup.check(R.id.rb_indent3);
        }
        else if (position==3){
            viewPager.setCurrentItem(3);
            radioGroup.check(R.id.rb_indent4);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_login_back:
                finish();
                break;

        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_indent1:
                viewPager.setCurrentItem(0,false);//禁用动画
                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                break;
            case R.id.rb_indent2:
                viewPager.setCurrentItem(1,false);
                EventBus.getDefault().post(new MyEvent("刷新代付款订单"));
                break;
            case R.id.rb_indent3:
                viewPager.setCurrentItem(2,false);
                EventBus.getDefault().post(new MyEvent("刷新进行中订单"));
                break;
            case R.id.rb_indent4:
                viewPager.setCurrentItem(3,false);
                EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                break;
        }
    }

}
