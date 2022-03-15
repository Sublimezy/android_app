package com.xueyiche.zjyk.xueyiche.mine.activities.message;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.KeFuContentBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Owner on 2018/3/19.
 */
public class KeFuQuesContentActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_exam_back;
    private TextView tv_login_back,tv_question,tv_content;

    @Override
    protected int initContentView() {
        return R.layout.kefu_faq_content;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.message_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.message_include).findViewById(R.id.tv_login_back);
        tv_question = (TextView) view.findViewById(R.id.tv_question);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_login_back.setText("问题详情");
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            if (!TextUtils.isEmpty(id)) {
                OkHttpUtils.post().url(AppUrl.KeFu_Content)
                        .addParams("device_id", LoginUtils.getId(KeFuQuesContentActivity.this))
                        .addParams("id", id)
                        .build()
                        .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            KeFuContentBean keFuContentBean = JsonUtil.parseJsonToBean(string, KeFuContentBean.class);
                            if (keFuContentBean!=null) {
                                int code = keFuContentBean.getCode();
                                if (!TextUtils.isEmpty(""+code)) {
                                    if (200==code) {
                                        KeFuContentBean.ContentBean content = keFuContentBean.getContent();
                                        final String quest_detail = content.getQuest_detail();
                                        final String quest_name = content.getQuest_name();
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (!TextUtils.isEmpty(quest_name)) {
                                                    tv_question.setText(quest_name);
                                                }
                                                if (!TextUtils.isEmpty(quest_detail)) {
                                                    tv_content.setText(quest_detail);
                                                }
                                            }
                                        });

                                    }
                                }
                            }
                        }
                        return string;
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
            }
        }else {
            Toast.makeText(KeFuQuesContentActivity.this, StringConstants.CHECK_NET, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }
}
