package com.xueyiche.zjyk.xueyiche.submit;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.driverschool.driverschool.DirectDriverSchoolContent;
import com.xueyiche.zjyk.xueyiche.homepage.bean.DriverSchoolTuiJianBean;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.submit.bean.DirectCarBean;
import com.xueyiche.zjyk.xueyiche.submit.bean.ZTCOrderBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhanglei on 2016/11/28.
 */
public class DirectDriverSchoolSubmitIndent extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler {
    //驾校支付页
    private TextView tv_pay_money, bt_indent_content_ok;
    private TextView tv_driver_content_phone;
    private MClearEditText tv_driver_content_name, tv_driver_content_card;
    private TextView tv_service_money;
    private TextView tv_login_back,tv_wenxintishi;
    private String user_id, user_phone, driver_user_name, driver_user_card;
    private RadioButton rb_ok, rb_no;
    private MClearEditText tv_tuijian_phone;
    private boolean isTuiJian = false;
    private TextView tv_tuijian_name;
    private String recommend_id = "";
    private LinearLayout ll_tuijianren_phone;
    private LinearLayout ll_exam_back;
    private TextView tv_taocan;
    public static DirectDriverSchoolSubmitIndent instance;
    private int driver_school_money;
    private String service_price;
    private int id;


    @Override
    protected int initContentView() {
        return R.layout.direct_driverschool_submit;
    }

    @Override
    protected void initView() {
        //标题栏的一些内容
        tv_login_back = (TextView) view.findViewById(R.id.driver_pay_include).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.driver_pay_include).findViewById(R.id.tv_wenxintishi);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.driver_pay_include).findViewById(R.id.ll_exam_back);
        tv_login_back.setText("提交订单");
        ll_exam_back.setOnClickListener(this);
        //姓名的输入框
        tv_driver_content_name = (MClearEditText) view.findViewById(R.id.tv_driver_content_name);
        //身份证的输入框
        tv_driver_content_card = (MClearEditText) view.findViewById(R.id.tv_driver_content_card);
        textChange tc1 = new textChange();
        tv_driver_content_card.addTextChangedListener(tc1);
        //手机号码
        tv_driver_content_phone = (TextView) view.findViewById(R.id.tv_driver_content_phone);
        tv_taocan = (TextView) view.findViewById(R.id.tv_taocan);
        //服务价格
        tv_service_money = (TextView) view.findViewById(R.id.tv_service_money);
        //提交订单左边显示的金额
        tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        rb_ok = (RadioButton) view.findViewById(R.id.rb_ok);
        rb_no = (RadioButton) view.findViewById(R.id.rb_no);
        //推荐人电话
        tv_tuijian_phone = (MClearEditText) view.findViewById(R.id.tv_tuijian_phone);
        //推荐人姓名
        tv_tuijian_name = (TextView) view.findViewById(R.id.tv_tuijian_name);
        ll_tuijianren_phone = (LinearLayout) view.findViewById(R.id.ll_tuijianren_phone);
        textChange2 tc2 = new textChange2();
        instance = this;
        tv_tuijian_phone.addTextChangedListener(tc2);
        rb_ok.setOnClickListener(this);
        rb_no.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
        tv_wenxintishi.setVisibility(View.VISIBLE);
        tv_wenxintishi.setText("须知");
        tv_wenxintishi.setTextColor(Color.parseColor("#666666"));
        //提交订单
        bt_indent_content_ok = (TextView) view.findViewById(R.id.bt_indent_content_ok);
        bt_indent_content_ok.setOnClickListener(this);
        bt_indent_content_ok.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                driverPay();
            }
        });
    }

    private void driverPay() {
        driver_user_name = tv_driver_content_name.getText().toString().trim();
        driver_user_card = tv_driver_content_card.getText().toString().trim();
        if (TextUtils.isEmpty(driver_user_name)) {
            showToastShort("请填写真实姓名");
            return;
        }
        if (TextUtils.isEmpty(driver_user_card)) {
            showToastShort("请填写身份证号");
            return;
        }

        if (!StringUtils.isIdCard(driver_user_card)) {
            showToastShort("请填写正确的身份证号码");
            return;
        }
        String string = tv_tuijian_phone.getText().toString();
        if (rb_ok.isChecked() && TextUtils.isEmpty(string)) {
            showToastShort("请填写推荐人");
            return;
        }
        if (rb_ok.isChecked() && !isTuiJian) {
            showToastShort("无效的推荐人");
            return;
        }
        if (TextUtils.isEmpty(user_phone)) {
            showToastShort("数据异常！");
            return;
        }
        String encrypt = AES.encrypt(driver_user_card);
        String user_phone_encrypt = AES.encrypt(user_phone);
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.ZTC_Driver_Shcool_Post)
                .addParams("user_id", user_id)
                .addParams("user_phone", user_phone_encrypt)
                .addParams("user_name", driver_user_name)
                .addParams("user_cards", encrypt)
                .addParams("recommend_id", recommend_id)
                .addParams("id", ""+id)
                .addParams("device_id", LoginUtils.getId(DirectDriverSchoolSubmitIndent.this))
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    ZTCOrderBean submitBean = JsonUtil.parseJsonToBean(string, ZTCOrderBean.class);
                    if (submitBean != null) {
                        final int code = submitBean.getCode();
                        final String msg = submitBean.getMsg();
                        final ZTCOrderBean.ContentBean content = submitBean.getContent();
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (content != null) {
                                    showToastShort(msg);
                                    String order_number = content.getOrder_number();
                                    if (!TextUtils.isEmpty(order_number) && !TextUtils.isEmpty(service_price)) {
                                        if (code == 200) {
                                            //支付页
                                            Intent intent = new Intent(App.context, AppPay.class);
                                            intent.putExtra("order_number", order_number);
                                            intent.putExtra("subscription", service_price);
                                            intent.putExtra("jifen", driver_school_money + "00");
//                                            intent.putExtra("jifen",  "200");
                                            intent.putExtra("pay_style", "zhitongche");
                                            startActivity(intent);

                                            finish();
                                        }
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

    }


    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        } else {
            getOrder();
        }
    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initData() {
        user_id = PrefUtils.getString(this, "user_id", "");
        String user_name = PrefUtils.getString(this, "user_name", "");
        if (!TextUtils.isEmpty(user_name)) {
            tv_driver_content_name.setText(user_name);
            tv_driver_content_name.setSelection(user_name.length());
        }
        getOrder();
    }

    public void getTuiJianName(String tuijian_phone) {
        String encrypt = AES.encrypt(tuijian_phone);
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.TuiJianMoney)
                .addParams("recommender_phone", encrypt)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                final String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    final DriverSchoolTuiJianBean driverSchoolTuiJianBean = JsonUtil.parseJsonToBean(string, DriverSchoolTuiJianBean.class);
                    if (driverSchoolTuiJianBean != null) {
                        final int code = driverSchoolTuiJianBean.getCode();
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (200 == code) {
                                    DriverSchoolTuiJianBean.ContentBean content = driverSchoolTuiJianBean.getContent();
                                    if (content != null) {
                                        String recommender_name = content.getRecommender_name();
                                        recommend_id = content.getRecommend_id();
                                        tv_tuijian_name.setText(recommender_name);
                                        tv_tuijian_name.setVisibility(View.VISIBLE);
                                        isTuiJian = true;
                                    }
                                } else {
                                    isTuiJian = false;
                                    tv_tuijian_name.setVisibility(View.GONE);
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogUtils.showNoTuijian(DirectDriverSchoolSubmitIndent.this, "无效的推荐人", tv_tuijian_phone);
                                        }
                                    });
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

    }

    private void getOrder() {
        if (!TextUtils.isEmpty(user_id)) {
            if (XueYiCheUtils.IsHaveInternet(this)) {
                showProgressDialog(false);
                OkHttpUtils.post().url(AppUrl.Direct_Driver_Shcool_Order)
                        .addParams("user_id", user_id)
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        final String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    processData(string);
                                }
                            });
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

    private void processData(String string) {
        DirectCarBean directCarBean = JsonUtil.parseJsonToBean(string, DirectCarBean.class);
        if (directCarBean != null) {
            DirectCarBean.ContentBean content = directCarBean.getContent();
            if (content != null) {
                user_phone = content.getUser_phone();
                String service = content.getService();
                service_price = content.getService_price();
                String effective = content.getEffective();
                id = content.getId();

                if (!TextUtils.isEmpty(user_phone)) {
                    tv_driver_content_phone.setText(user_phone);
                }
                if (!TextUtils.isEmpty(service)) {
                    tv_taocan.setText(service);
                }
                if (!TextUtils.isEmpty(service_price)) {
                    tv_service_money.setText("¥"+service_price);
                    tv_pay_money.setText(service_price);
                    driver_school_money = Integer.parseInt(service_price);
                }
                if (!TextUtils.isEmpty(effective)) {
                    if ("0".equals(effective)) {
                        bt_indent_content_ok.setEnabled(false);
                        bt_indent_content_ok.setOnClickListener(null);
                        bt_indent_content_ok.setBackgroundColor(getResources().getColor(R.color._cccccc));

                    } else if ("1".equals(effective)) {
                        bt_indent_content_ok.setEnabled(true);
                        bt_indent_content_ok.setBackgroundColor(getResources().getColor(R.color.colorOrange));


                    }
                }

            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                //删除订单
                finish();
                break;
            case R.id.tv_wenxintishi:
                openActivity(DirectDriverSchoolContent.class);
                break;
            case R.id.rb_ok:
                rb_no.setChecked(false);
                ll_tuijianren_phone.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_no:
                rb_ok.setChecked(false);
                ll_tuijianren_phone.setVisibility(View.GONE);
                break;
        }
    }


    public abstract class NoDoubleClickListener implements View.OnClickListener {
        public static final int MIN_CLICK_DELAY_TIME = 2000;
        private long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        }

        protected abstract void onNoDoubleClick(View v);
    }


    class textChange2 implements TextWatcher {
        @Override

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (text.length() == 11) {
                getTuiJianName(text);
            }
        }

        @Override

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count) {

        }
    }

    class textChange implements TextWatcher {
        @Override

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            int length = text.length();
            if (18 == length) {
                if (!StringUtils.isIdCard(text)) {
                    showToastShort("请输入正确的身份证号");
                }
            }

        }

        @Override

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count) {
            String txt = tv_driver_content_card.getText().toString();
            Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(txt);
            if (m.matches()) {
                showToastShort("请输入正确的身份证号");
            }


        }
    }
}
