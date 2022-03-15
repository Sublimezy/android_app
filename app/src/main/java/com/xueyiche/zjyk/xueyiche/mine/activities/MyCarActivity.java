package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.GetMyCarBean;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.utils.CarKeyboardUtil;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Owner on 2019/6/3.
 */
public class MyCarActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private TextView tv_query;
    private MClearEditText ed_chejia, ed_fadongji;
    private EditText ed_chepai;
    private CarKeyboardUtil keyboardUtil;

    @Override
    protected int initContentView() {
        return R.layout.my_car_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        tv_query = (TextView) view.findViewById(R.id.tv_query);
        ed_chejia = (MClearEditText) view.findViewById(R.id.ed_chejia);
        ed_fadongji = (MClearEditText) view.findViewById(R.id.ed_fadongji);
        ed_chepai = (EditText) view.findViewById(R.id.ed_chepai);
        ed_chejia.setGravity(Gravity.RIGHT);
        keyboardUtil = new CarKeyboardUtil(this, ed_chepai);
        ed_chepai.setOnTouchListener(this);
        ed_fadongji.setGravity(Gravity.RIGHT);
        ed_chepai.setGravity(Gravity.RIGHT);

    }


    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_query.setOnClickListener(this);
        ed_chepai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.contains("港") || text.contains("澳") || text.contains("学")) {
                    ed_chepai.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
                } else {
                    ed_chepai.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void initData() {
        tv_login_back.setText("我的爱车");
        Intent intent = getIntent();
        String mylovercar = intent.getStringExtra("mylovercar");
        if ("1".equals(mylovercar)) {
            getDataFromNet();
        }

    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Get_BangDing_Car)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final GetMyCarBean weiZhangPostBean = JsonUtil.parseJsonToBean(string, GetMyCarBean.class);
                                if (weiZhangPostBean != null) {
                                    final int success = weiZhangPostBean.getCode();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200 == success) {
                                                GetMyCarBean.ContentBean content = weiZhangPostBean.getContent();
                                                String enginenumber = content.getEnginenumber();
                                                String licenseno = content.getLicenseno();
                                                String vin = content.getVin();
                                                if (!TextUtils.isEmpty(vin)) {
                                                    ed_chejia.setText(vin);
                                                    ed_chejia.setSelection(vin.length());
                                                }
                                                if (!TextUtils.isEmpty(enginenumber)) {
                                                    ed_fadongji.setText(enginenumber);
                                                    ed_fadongji.setSelection(enginenumber.length());
                                                }
                                                if (!TextUtils.isEmpty(licenseno)) {
                                                    ed_chepai.setText(licenseno);
                                                    ed_chepai.setSelection(licenseno.length());
                                                }
                                            }
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
        } else {
            Toast.makeText(MyCarActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_query:
                //立即查询
                String chejia = ed_chejia.getText().toString().trim();
                String fadongji = ed_fadongji.getText().toString().trim();
                String chepai = ed_chepai.getText().toString().trim();
                if (TextUtils.isEmpty(chejia)) {
                    showToastShort("请输入车架号");
                    return;
                }
                if (TextUtils.isEmpty(chepai)) {
                    showToastShort("请输入车牌号");
                    return;
                }
                if (TextUtils.isEmpty(fadongji)) {
                    showToastShort("请输入发动机号");
                    return;
                }

                post(chejia, fadongji, chepai);
                break;
        }
    }

    private void post(final String chejia, final String fadongji, final String chepai) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.BangDing_Car)
                    .addParams("vin", chejia)
                    .addParams("enginenumber", fadongji)
                    .addParams("licenseno", chepai)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final SuccessDisCoverBackBean weiZhangPostBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                if (weiZhangPostBean != null) {
                                    final int success = weiZhangPostBean.getCode();
                                    final String msg = weiZhangPostBean.getMsg();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200 == success) {
                                                finish();
                                            }
                                            Toast.makeText(MyCarActivity.this, msg, Toast.LENGTH_SHORT).show();
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
        } else {
            Toast.makeText(MyCarActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.ed_chepai:
                keyboardUtil.hideSystemKeyBroad();
                keyboardUtil.hideSoftInputMethod();
                if (!keyboardUtil.isShow())
                    keyboardUtil.showKeyboard();
                break;
            case R.id.ed_sheng:
                if (keyboardUtil.isShow())
                    keyboardUtil.hideKeyboard();
                break;
            default:
                if (keyboardUtil.isShow())
                    keyboardUtil.hideKeyboard();
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (keyboardUtil.isShow()) {
            keyboardUtil.hideKeyboard();
        }
        return super.onTouchEvent(event);
    }
}
