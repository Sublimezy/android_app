package com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.StudentsOrderConentBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.StudentsStudyRecordContentBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 张磊 on 2021/1/18.                                       #
 */
//学习详情
public class StudentsStudyContentActivity extends NewBaseActivity {
    @BindView(R.id.ll_exam_back)
    LinearLayout llExamBack;
    @BindView(R.id.tv_login_back)
    TextView tvLoginBack;
    @BindView(R.id.tvStudyContent)
    TextView tvStudyContent;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvCoachRemark)
    TextView tvCoachRemark;
    @BindView(R.id.tvCoachRemarkContent)
    TextView tvCoachRemarkContent;
    @BindView(R.id.tvStudentRemark)
    TextView tvStudentRemark;
    @BindView(R.id.tvStudentRemarkContent)
    TextView tvStudentRemarkContent;
    private StudentsOrderConentBean.ContentBean content;

    @Override
    protected int initContentView() {
        return R.layout.students_study_record_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvLoginBack.setText("学习记录");
        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.selectdrivingtraining)
                    .addParams("training_id", "" + (getIntent().getStringExtra("training_id")))
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                StudentsOrderConentBean studentsStudyRecordContentBean = JsonUtil.parseJsonToBean(string, StudentsOrderConentBean.class);
                                if (studentsStudyRecordContentBean != null) {
                                    if (200 == studentsStudyRecordContentBean.getCode()) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                content = studentsStudyRecordContentBean.getContent();
                                                tvStudyContent.setText("" + content.getEntry_project() + "：" + content.getDriving_practice());
                                                tvStartTime.setText("开始时间：" + content.getBoarding_time());
                                                tvEndTime.setText("结束时间：" + content.getGet_off_time());
                                                tvName.setText("姓名：" + content.getCoach_name());
                                                tvPhone.setText("电话：" + content.getCoach_phone());
                                                tvCoachRemark.setText("" + content.getCoach_to_student());
                                                tvCoachRemarkContent.setText("" + content.getCoach_to_student_detail());
                                                tvStudentRemark.setText("" + content.getStudent_to_coach());
                                                tvStudentRemarkContent.setText("" + content.getStudent_to_coach_detail());
                                            }
                                        });
                                    }

                                }
                            }
                            return null;
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            stopProgressDialog();
                        }

                        @Override
                        public void onResponse(Object response) {
                            stopProgressDialog();
                        }
                    });
        } else {
            showToastShort("请检查网络连接！");
        }

    }

    @OnClick({R.id.ll_exam_back, R.id.tvPhone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tvPhone:
                XueYiCheUtils.CallPhone(StudentsStudyContentActivity.this, "是否拨打"+content.getCoach_name()+"的电话？", ""+content.getCoach_phone());
                break;
        }
    }
}
