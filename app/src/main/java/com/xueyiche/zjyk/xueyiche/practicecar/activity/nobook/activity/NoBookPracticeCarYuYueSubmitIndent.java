package com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.OrderFaBuOkActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderPracticeOkBean;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by ZL on 2018/3/14.
 */
public class NoBookPracticeCarYuYueSubmitIndent extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private TextView tv_start_time;
    private TextView tv_total_time;
    private TextView tv_end_time;
    private TextView tv_indent_danjia;
    private TextView tv_indent_total_money;
    private TextView tv_indent_jie_location, tv_indent_song_location;
    private TextView tv_indent_will_content;
    private TextView tv_indent_beizhu;
    private TextView tv_indent_number;
    private TextView tv_indent_date;
    private TextView tv_indent_carstyle;
    private Button bt_fabu;
    private String order_number_now;
    private String user_id;
    private String type;
    private String start_time;
    private String end_time;
    private String order_number,tc_type;
    private int pay_price;
    public static NoBookPracticeCarYuYueSubmitIndent instance;
    private LinearLayout ll_danjia;
    private int hour_price;

    @Override
    protected int initContentView() {
        return R.layout.nobook_order_practice_ok_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.indent_details_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.indent_details_include).findViewById(R.id.tv_login_back);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_total_time = (TextView) view.findViewById(R.id.tv_total_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        tv_indent_danjia = (TextView) view.findViewById(R.id.tv_indent_danjia);
        tv_indent_carstyle = (TextView) view.findViewById(R.id.tv_indent_carstyle);
        ll_danjia = (LinearLayout) view.findViewById(R.id.ll_danjia);
        tv_indent_total_money = (TextView) view.findViewById(R.id.tv_indent_total_money);
        tv_indent_jie_location = (TextView) view.findViewById(R.id.tv_indent_jie_location);
        tv_indent_song_location = (TextView) view.findViewById(R.id.tv_indent_song_location);
        tv_indent_will_content = (TextView) view.findViewById(R.id.tv_indent_will_content);
        tv_indent_beizhu = (TextView) view.findViewById(R.id.tv_indent_beizhu);
        tv_indent_number = (TextView) view.findViewById(R.id.tv_indent_number);
        tv_indent_date = (TextView) view.findViewById(R.id.tv_indent_date);
        bt_fabu = (Button) view.findViewById(R.id.bt_fabu);
        instance = this;

    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        bt_fabu.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        tc_type = intent.getStringExtra("tc_type");
        type = intent.getStringExtra("type");
        if ("1".equals(type)) {
            tv_login_back.setText("预约订单");
            bt_fabu.setText("确认发布");
        } else if ("2".equals(type)) {
            tv_login_back.setText("确认订单");
            bt_fabu.setText("立即支付");
        }
        getDataFromNet();

    }

    private void getDataFromNet() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Order_Post_Ok_WZ)
                    .addParams("device_id", LoginUtils.getId(NoBookPracticeCarYuYueSubmitIndent.this))
                    .addParams("user_id", TextUtils.isEmpty(user_id) ? "" : user_id)
                    .addParams("order_number", TextUtils.isEmpty(order_number) ? "" : order_number)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                OrderPracticeOkBean orderPracticeOkBean = JsonUtil.parseJsonToBean(string, OrderPracticeOkBean.class);
                                if (orderPracticeOkBean != null) {
                                    int code = orderPracticeOkBean.getCode();
                                    if (!TextUtils.isEmpty("" + code)) {
                                        if (200 == code) {
                                            OrderPracticeOkBean.ContentBean content = orderPracticeOkBean.getContent();
                                            if (content != null) {
                                                final String bz = content.getBz();
                                                end_time = content.getEnd_time();
                                                pay_price = content.getPay_price();
                                                hour_price = content.getHour_price();
                                                order_number_now = content.getOrder_number();
                                                final String order_system_time = content.getOrder_system_time();
                                                final String practice_hours = content.getPractice_hours();
                                                final String purpose_item = content.getPurpose_item();
                                                start_time = content.getStart_time();
                                                final String traincar_experience = content.getTraincar_experience();
                                                final String get_on_address = content.getGet_on_address();
                                                final String get_down_address = content.getGet_down_address();
                                                App.handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        //开始时间
                                                        if (!TextUtils.isEmpty(start_time)) {
                                                            tv_start_time.setText(start_time);
                                                        }
                                                        //结束时间
                                                        if (!TextUtils.isEmpty(end_time)) {
                                                            tv_end_time.setText(end_time);
                                                        }

                                                        //共计小时
                                                        if (!TextUtils.isEmpty(practice_hours)) {
                                                            tv_total_time.setText("共" + practice_hours + "小时");
                                                        }
                                                        //单价
                                                        if (!TextUtils.isEmpty("" + hour_price)) {
                                                            tv_indent_danjia.setText("" + hour_price);
                                                        }
                                                        if ("1".equals(tc_type)) {
                                                            ll_danjia.setVisibility(View.GONE);
                                                            //共计
                                                            if (!TextUtils.isEmpty("" + pay_price)) {
                                                                tv_indent_total_money.setText("" + pay_price);
                                                            }
                                                        }else {
                                                            ll_danjia.setVisibility(View.VISIBLE);
                                                            //共计
                                                            if (!TextUtils.isEmpty("" + pay_price)) {
                                                                tv_indent_total_money.setText("" + pay_price);
                                                            }
                                                        }

                                                        //接送地址
                                                        if (!TextUtils.isEmpty(get_on_address)) {
                                                            tv_indent_jie_location.setText(get_on_address);
                                                        }
                                                        if (!TextUtils.isEmpty(get_down_address)) {
                                                            tv_indent_song_location.setText(get_down_address);
                                                        }
                                                        //意向练车项目
                                                        if (!TextUtils.isEmpty(purpose_item)) {
                                                            tv_indent_will_content.setText(purpose_item);
                                                        }
                                                        //备注
                                                        if (!TextUtils.isEmpty(bz)) {
                                                            tv_indent_beizhu.setText("备注：" + bz);
                                                        } else {
                                                            tv_indent_beizhu.setText("备注：无");
                                                        }
                                                        //订单编号
                                                        if (!TextUtils.isEmpty(order_number_now)) {
                                                            tv_indent_number.setText(order_number_now);
                                                        }
                                                        //下单时间
                                                        if (!TextUtils.isEmpty(order_system_time)) {
                                                            tv_indent_date.setText(order_system_time);
                                                        }
                                                    }
                                                });
                                            }
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

    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number_now)) {
                AppUtils.deleteIndent(NoBookPracticeCarYuYueSubmitIndent.this, AppUrl.Delete_Indent_Practice, user_id, order_number_now);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number_now)) {
                    AppUtils.deleteIndent(NoBookPracticeCarYuYueSubmitIndent.this, AppUrl.Delete_Indent_Practice, user_id, order_number_now);
                }
                break;
            case R.id.bt_fabu:
                if (DialogUtils.IsLogin()) {
                    if ("1".equals(type)) {
                        queRenFaBu();
                    } else if ("2".equals(type)) {
                        Intent intent = new Intent(App.context, AppPay.class);
                        intent.putExtra("pay_style","practice_wz");
                        intent.putExtra("order_number",order_number);
                        if ("1".equals(tc_type)) {
                            intent.putExtra("subscription",hour_price+"");
                        }else {
                            intent.putExtra("subscription",pay_price+"");
                        }
//                        intent.putExtra("subscription","0.01");
                        intent.putExtra("jifen", "0");
                        startActivity(intent);
                    }
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            default:
                break;
        }
    }

    private void queRenFaBu() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.FaBu_QueRen_WZ)
                    .addParams("device_id", LoginUtils.getId(NoBookPracticeCarYuYueSubmitIndent.this))
                    .addParams("user_id", TextUtils.isEmpty(user_id) ? "" : user_id)
                    .addParams("order_number", TextUtils.isEmpty(order_number_now) ? "" : order_number_now)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                SuccessDisCoverBackBean su = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                final int code = su.getCode();
                                final String msg = su.getMsg();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (200 == code) {
                                            //发布成功
                                            Intent intent = new Intent(NoBookPracticeCarYuYueSubmitIndent.this, OrderFaBuOkActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        Toast.makeText(NoBookPracticeCarYuYueSubmitIndent.this, msg, Toast.LENGTH_SHORT).show();
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
