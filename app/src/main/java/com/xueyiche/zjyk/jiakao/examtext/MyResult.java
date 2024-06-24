package com.xueyiche.zjyk.jiakao.examtext;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.homepage.adapters.MyResultAdapterA;
import com.xueyiche.zjyk.jiakao.homepage.adapters.MyResultAdapterD;


public class MyResult extends BaseActivity implements View.OnClickListener{
    private LinearLayout llBack;
    private ListView listView;
    private MyResultAdapterA adapterA;
    private TextView tvTitle;
    private MyResultAdapterD myResultAdapterD;
    @Override
    protected int initContentView() {
        return R.layout.my_result_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.my_result_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.my_result_include).findViewById(R.id.tv_login_back);
        listView = (ListView) view.findViewById(R.id.my_result_list_view);
        llBack.setOnClickListener(this);
        adapterA = new MyResultAdapterA();
        myResultAdapterD = new MyResultAdapterD();

        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
        tvTitle.setText("我的成绩");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String myresult = intent.getStringExtra("myresult");
        if ("1".equals(myresult)) {
            listView.setAdapter(adapterA);
            adapterA.notifyDataSetChanged();
        }else if ("2".equals(myresult)){
            listView.setAdapter(myResultAdapterD);
            adapterA.notifyDataSetChanged();
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_exam_back:
                finish();
                break;

        }
    }
}
