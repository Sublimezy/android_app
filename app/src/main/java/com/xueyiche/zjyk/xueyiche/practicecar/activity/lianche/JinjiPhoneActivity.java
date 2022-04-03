package com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.JinJiPhoneBean;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Administrator on 2019/9/10.
 */
public class JinjiPhoneActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_login_back;
    private LinearLayout ll_exam_back;
    private EditText et_phone;
    private TextView tv_wenxintishi;

    @Override
    protected int initContentView() {
        return R.layout.jinji_phone_activity;
    }
    public static void forward(Context context) {
        Intent intent = new Intent(context, JinjiPhoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_wenxintishi);
        tv_wenxintishi.setVisibility(View.VISIBLE);
        et_phone = (EditText) view.findViewById(R.id.et_phone);

    }

    @Override
    protected void initListener() {
        tv_wenxintishi.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_login_back.setText("");
        tv_wenxintishi.setText("保存");
        getPhoneFromNet();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:
                String s = et_phone.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    showToastShort("请输入电话号码");
                    return;
                }
                if (s.length()!=11) {
                    showToastShort("请输入正确的电话号码");
                    return;
                }
                upDate(s);
                break;
        }
    }
    public void getPhoneFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.JinJi_Phone_Get)
                    .addParams("user_id", user_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        JinJiPhoneBean jinJiPhoneBean = JsonUtil.parseJsonToBean(string, JinJiPhoneBean.class);
                        if (jinJiPhoneBean != null) {
                            JinJiPhoneBean.ContentBean content = jinJiPhoneBean.getContent();
                            if (content!=null) {
                                final String true_phone = content.getTrue_phone();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!TextUtils.isEmpty(true_phone)) {
                                            int length = true_phone.length();
                                            if (11==length) {
                                                et_phone.setText(true_phone);
                                            }else {
                                                et_phone.setText(AES.decrypt(true_phone));
                                            }
                                        }
                                    }
                                });
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
    }
    private void upDate(String true_phone) {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(user_id)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.JinJi_Phone)
                    .addParams("user_id", user_id)
                    .addParams("true_phone", true_phone)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                        int code = successDisCoverBackBean.getCode();
                        final String msg = successDisCoverBackBean.getMsg();
                        if (200 == code) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    showToastShort(msg);
                                    finish();
                                }
                            });
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
    }
}
