package com.xueyiche.zjyk.xueyiche.mine.activities.collection;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.main.view.NoScrollViewPager;
import com.xueyiche.zjyk.xueyiche.mine.activities.adapter.CollectionFragmentAdapter;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

/**
 * Created by zhanglei on 2016/10/29.
 */
public class MyCollection extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    //3个菜单按钮
    public static RadioGroup rg_collection;
    private NoScrollViewPager vp_collection;
    private TextView tv_login_back;
    private LinearLayout llBack;
    private String kind_type;
    private View view_line;
    private RadioButton rb_usdecar;

    @Override
    protected int initContentView() {
        return R.layout.my_collection_activity;
    }

    @Override
    protected void initView() {

        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);

        rg_collection = (RadioGroup) view.findViewById(R.id.rg_collection);
        llBack.setOnClickListener(this);
        vp_collection = (NoScrollViewPager) view.findViewById(R.id.vp_collection);
        view_line = view.findViewById(R.id.view_line);
        rb_usdecar = (RadioButton) view.findViewById(R.id.rb_usdecar);
        String x = PrefUtils.getString(App.context, "x", "");
        String y = PrefUtils.getString(App.context, "y", "");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        kind_type = getIntent().getStringExtra("kind_type");
        if ("1".equals(kind_type)) {
            tv_login_back.setText("我的收藏");
        }else {
            tv_login_back.setText("我的足迹");
            rb_usdecar.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);

        }
        vp_collection.setAdapter(new CollectionFragmentAdapter(getSupportFragmentManager(),kind_type));
        rg_collection.setOnCheckedChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;

        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_driver_school:
                vp_collection.setCurrentItem(0, false);//禁用动画
                break;
            case R.id.rb_driver:
                vp_collection.setCurrentItem(1, false);
                break;
            case R.id.rb_shop:
                vp_collection.setCurrentItem(2, false);
                break;
            case R.id.rb_usdecar:
                vp_collection.setCurrentItem(3, false);
                break;
        }
    }
    }
