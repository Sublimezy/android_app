package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.bean.ChooseDaiJiaBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;


/**
 * Created by Administrator on 2019/9/18.
 */
public class ChooseDaiJiaoActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title, tv_name;
    private ImageView ll_exam_back;
    private EditText et_number;
    private Button iv_daijiao;


    @Override
    protected int initContentView() {
        return R.layout.choose_daijiao_activity;
    }

    @Override
    protected void initView() {
        tv_title = findViewById(R.id.title).findViewById(R.id.tv_title);
        ll_exam_back = findViewById(R.id.title).findViewById(R.id.iv_login_back);
        et_number = (EditText) findViewById(R.id.et_number);
        tv_name = (TextView) findViewById(R.id.tv_name);
        iv_daijiao = (Button) findViewById(R.id.iv_daijiao);
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        iv_daijiao.setOnClickListener(this);
        et_number.addTextChangedListener(textWatch);
    }

    @Override
    protected void initData() {
        tv_title.setText("选择代驾");
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_login_back:
                intent.putExtra("daijia_number", "");
                intent.putExtra("daijia_name", "");
                setResult(555, intent);
                finish();
                break;
            case R.id.iv_daijiao:
                //保存电话号码
                String number = et_number.getText().toString().trim();
                String name = tv_name.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(ChooseDaiJiaoActivity.this, "请输入代驾员号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("daijia_number", number);
                intent.putExtra("daijia_name", name);
                setResult(555, intent);
                finish();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("daijia_number", "");
            intent.putExtra("daijia_name", "");
            setResult(555, intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }


    private TextWatcher textWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (XueYiCheUtils.IsHaveInternet(App.context)) {
                OkHttpUtils.post().url(AppUrl.getSubstituteDriverInfoByJobNumber)
                        .addParams("job_number", s.toString())
                        .build()
                        .execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws IOException {
                                String string = response.body().string();
                                if (!TextUtils.isEmpty(string)) {
                                    final ChooseDaiJiaBean chooseDaiJiaBean = JsonUtil.parseJsonToBean(string, ChooseDaiJiaBean.class);
                                    if (chooseDaiJiaBean != null) {
                                        int code = chooseDaiJiaBean.getCode();
                                        final String msg = chooseDaiJiaBean.getMsg();
                                        if (200 == code) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ChooseDaiJiaBean.ContentBean content = chooseDaiJiaBean.getContent();
                                                    if (content != null) {
                                                        String user_name = content.getUser_name();
                                                        String driver_status = content.getDriver_status();
                                                        String on_off = content.getOn_off();
                                                        if (!TextUtils.isEmpty(user_name)) {
                                                            tv_name.setText(user_name);
                                                        }
                                                        if (!TextUtils.isEmpty(driver_status) && !TextUtils.isEmpty(on_off)) {
                                                            if ("0".equals(driver_status) && "1".equals(on_off)) {
                                                                iv_daijiao.setClickable(true);
                                                                iv_daijiao.setPressed(true);
                                                                return;
                                                            } else {
                                                                iv_daijiao.setClickable(false);
                                                                iv_daijiao.setPressed(false);
                                                                if ("0".equals(on_off)) {
                                                                    Toast.makeText(ChooseDaiJiaoActivity.this, "该代驾员正在关闭中。。。", Toast.LENGTH_SHORT).show();
                                                                    return;
                                                                }
                                                                if ("1".equals(driver_status)) {
                                                                    Toast.makeText(ChooseDaiJiaoActivity.this, "该代驾员正在忙碌中。。。", Toast.LENGTH_SHORT).show();
                                                                    return;
                                                                }
                                                            }

                                                        }


                                                    }
                                                    Toast.makeText(ChooseDaiJiaoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                }
                                            });
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
    };


}
