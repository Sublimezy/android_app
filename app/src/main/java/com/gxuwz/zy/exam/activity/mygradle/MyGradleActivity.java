package com.gxuwz.zy.exam.activity.mygradle;


import static com.gxuwz.zy.constants.Constant.PRE_MODEL;
import static com.gxuwz.zy.constants.Constant.PRE_SUBJECT;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseActivity;
import com.gxuwz.zy.constants.App;
import com.gxuwz.zy.exam.adapter.myresult.MyResultAdapter;
import com.gxuwz.zy.exam.database.MyGradeDBHelper;
import com.gxuwz.zy.exam.entity.dos.MyGradeBean;

import java.util.List;

public class MyGradleActivity extends BaseActivity {

    private LinearLayout mLL_questionback;

    @Override
    protected int initContentView() {
        return R.layout.activity_my_gradle;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        Long subject = intent.getLongExtra(PRE_SUBJECT, 1);
        String model = intent.getStringExtra(PRE_MODEL);
        MyGradeDBHelper myGradeDBHelper = MyGradeDBHelper.getInstance(App.context);
        MyGradeBean myGradeBean = new MyGradeBean(Math.toIntExact(subject), model);
        List<MyGradeBean> myGradeBeanList = myGradeDBHelper.getAllQuestionByParams(myGradeBean);

        MyResultAdapter planetBaseAdapter = new MyResultAdapter(this, myGradeBeanList);


        ListView listView = view.findViewById(R.id.lv_plant);

        listView.setAdapter(planetBaseAdapter);

        //返回
        mLL_questionback = view.findViewById(R.id.my_return).findViewById(R.id.ll_exam_back);
        mLL_questionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}