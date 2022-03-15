package com.xueyiche.zjyk.xueyiche.mine.activities.bianji;

import android.content.Intent;
import android.text.SpannableString;
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
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by zhanglei on 2016/10/28.
 */
public class InformationChangeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private MClearEditText ed_information;
    private TextView tv_ok;
    private TextView tvTitle;
    private AES mAes;
    private String change_information, user_name, user_phone, user_cards, id_card;


    @Override
    protected int initContentView() {
        return R.layout.name_activity;
    }

    @Override
    protected void initView() {
        mAes = new AES();
        llBack = (LinearLayout) view.findViewById(R.id.bianji_title_include).findViewById(R.id.ll_exam_back);
        tv_ok = (TextView) view.findViewById(R.id.bianji_title_include).findViewById(R.id.tv_wenxintishi);
        tvTitle = (TextView) view.findViewById(R.id.bianji_title_include).findViewById(R.id.tv_login_back);
        ed_information = (MClearEditText) view.findViewById(R.id.ed_name);
        llBack.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        tv_ok.setVisibility(View.VISIBLE);
        tv_ok.setTextColor(getResources().getColor(R.color.test_color));

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        change_information = intent.getStringExtra("change_information");
        if (!TextUtils.isEmpty(change_information)) {
            if ("name".equals(change_information)) {
                String nickname = PrefUtils.getString(App.context, "user_name", "");
                if (!TextUtils.isEmpty(nickname)) {
                    ed_information.setText(nickname);
                    ed_information.setSelection(nickname.length());
                } else {
                    ed_information.setText("");
                }
                SpannableString name = new SpannableString("请输入姓名");//定义hint的值
                ed_information.setHint(name);
                tvTitle.setText("姓名");
            } else if ("id_card".equals(change_information)) {
                tvTitle.setText("身份证");
                String user_cards = PrefUtils.getString(App.context, "user_cards", "");
                String decrypt_user_cards = mAes.decrypt(user_cards);
                if (!TextUtils.isEmpty(user_cards)) {
                    ed_information.setText(decrypt_user_cards);
                    ed_information.setSelection(decrypt_user_cards.length());

                } else {
                    ed_information.setText("");
                }
                SpannableString name = new SpannableString("请输入身份证号");//定义hint的值
                ed_information.setHint(name);
            } else if ("phone".equals(change_information)) {
                tvTitle.setText("手机号");
                String user_phone = PrefUtils.getString(App.context, "user_phone", "");
                String decrypt_user_cards = mAes.decrypt(user_phone);
                if (!TextUtils.isEmpty(user_phone)) {
                    ed_information.setText(decrypt_user_cards);
                    ed_information.setSelection(decrypt_user_cards.length());
                } else {
                    ed_information.setText("");
                }
                SpannableString name = new SpannableString("请输入手机号");//定义hint的值
                ed_information.setHint(name);
            }
        }

        tv_ok.setText("保存");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:

                String content = ed_information.getText().toString().trim();
                if (!TextUtils.isEmpty(change_information)) {
                    if ("name".equals(change_information)) {
                        user_name = content;
                        user_phone = "";
                        user_cards = "";
                    } else if ("id_card".equals(change_information)) {
                        user_name = "";
                        user_phone = "";
                        String encrypt = AES.encrypt(content);
                        user_cards = encrypt;
                        id_card = content;

                    } else if ("phone".equals(change_information)) {
                        user_name = "";
                        String encrypt = AES.encrypt(content);
                        user_phone = encrypt;
                        user_cards = "";
                    }
                }

                if ("id_card".equals(change_information)) {
                    if (StringUtils.isIdCard(id_card)) {
                        sendMess();
                    }else {
                        Toast.makeText(InformationChangeActivity.this, "身份证格式不正确", Toast.LENGTH_SHORT).show();
                    }
                }else if ("name".equals(change_information)){
                    int length = user_name.length();
                    if (length>5) {
                        Toast.makeText(InformationChangeActivity.this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
                    }else {
                        sendMess();
                    }
                }else {
                    sendMess();
                }

                break;
        }
    }

    private void sendMess() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        OkHttpUtils.post().url(AppUrl.Setting_Change)
                .addParams("user_id", user_id)
                .addParams("user_name", user_name)
                .addParams("user_phone", user_phone)
                .addParams("user_cards", user_cards)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean != null) {
                                int code = successDisCoverBackBean.getCode();
                                final String msg = successDisCoverBackBean.getMsg();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(InformationChangeActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                finish();
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

                    }

                    @Override
                    public void onResponse(Object response) {

                    }
                });
    }
}
