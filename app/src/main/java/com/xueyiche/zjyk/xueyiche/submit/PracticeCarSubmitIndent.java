package com.xueyiche.zjyk.xueyiche.submit;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.newactivity.PracticeCarMapFragment;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderPracticeOkBean;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.IndentDetailsOrderPractice;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.PingJia;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2018/3/14.
 */
public class PracticeCarSubmitIndent extends BaseActivity implements View.OnClickListener {
    private ImageView ll_exam_back,bt_fabu,bt_quxiao;
    private TextView tv_login_back,tv_start_time,tv_end_time,tv_date;
    private TextView tv_total_time;
    private TextView tv_car_level;
    private TextView tv_indent_danjia;
    private TextView tv_indent_total_money;
    private TextView tv_indent_jie_location, tv_indent_song_location;
    private TextView tv_indent_getcard_time;
    private TextView tv_indent_graduation_level;
    private TextView tv_indent_practice_experience;
    private TextView tv_indent_beizhu;
    private TextView tv_indent_number,tv_indent_state,tv_indent_date,tv_jinji_phone;
    private String order_number_now,user_id,order_number, driver_id;
    private int pay_price;
    public static PracticeCarSubmitIndent instance;
    private LinearLayout ll_type;
    private String order_status,type;
    private String order_receiving;


    @Override
    protected int initContentView() {
        return R.layout.order_practice_ok_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = view.findViewById(R.id.indent_details_include).findViewById(R.id.iv_login_back);
        tv_login_back = (TextView) view.findViewById(R.id.indent_details_include).findViewById(R.id.tv_title);
        tv_total_time = (TextView) view.findViewById(R.id.tv_total_time);
        tv_car_level = (TextView) view.findViewById(R.id.tv_car_level);
        tv_indent_state = (TextView) view.findViewById(R.id.tv_indent_state);
        tv_indent_danjia = (TextView) view.findViewById(R.id.tv_indent_danjia);
        tv_indent_total_money = (TextView) view.findViewById(R.id.tv_indent_total_money);
        tv_indent_jie_location = (TextView) view.findViewById(R.id.tv_indent_jie_location);
        tv_indent_song_location = (TextView) view.findViewById(R.id.tv_indent_song_location);
        tv_indent_getcard_time = (TextView) view.findViewById(R.id.tv_indent_getcard_time);
        tv_indent_graduation_level = (TextView) view.findViewById(R.id.tv_indent_graduation_level);
        tv_indent_practice_experience = (TextView) view.findViewById(R.id.tv_indent_practice_experience);
        tv_indent_beizhu = (TextView) view.findViewById(R.id.tv_indent_beizhu);
        tv_indent_number = (TextView) view.findViewById(R.id.tv_indent_number);
        tv_indent_date = (TextView) view.findViewById(R.id.tv_indent_date);
        tv_jinji_phone = (TextView) view.findViewById(R.id.tv_jinji_phone);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        ll_type = view.findViewById(R.id.ll_type);
        bt_fabu = view.findViewById(R.id.bt_fabu);
        bt_quxiao = view.findViewById(R.id.bt_quxiao);
        instance = this;

    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        bt_fabu.setOnClickListener(this);
        bt_quxiao.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        driver_id = intent.getStringExtra("driver_id");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        type = intent.getStringExtra("type");
        if ("indent".equals(type)) {
            order_receiving = intent.getStringExtra("order_receiving");
        }
        tv_login_back.setText("确认订单");
        getDataFromNet();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }
    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("刷新待接单", msg)) {
           Intent intent = new Intent(App.context, IndentDetailsOrderPractice.class);
            intent.putExtra("order_number",order_number);
            startActivity(intent);
            finish();
        }

    }
    private void getDataFromNet() {

        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Order_Post_Ok)
                    .addParams("device_id", LoginUtils.getId(PracticeCarSubmitIndent.this))
                    .addParams("user_id", TextUtils.isEmpty(user_id) ? "" : user_id)
                    .addParams("order_number", TextUtils.isEmpty(order_number) ? "" : order_number)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final OrderPracticeOkBean orderPracticeOkBean = JsonUtil.parseJsonToBean(string, OrderPracticeOkBean.class);
                                if (orderPracticeOkBean != null) {
                                    int code = orderPracticeOkBean.getCode();
                                    if (!TextUtils.isEmpty("" + code)) {
                                        if (200 == code) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    OrderPracticeOkBean.ContentBean content = orderPracticeOkBean.getContent();
                                                    if (content != null) {
                                                        String bz = content.getBz();
                                                        String down_card_time = content.getDown_card_time();
                                                        String end_school_level = content.getEnd_school_level();
                                                        pay_price = content.getPay_price();
                                                        int hour_price = content.getHour_price();
                                                        order_number_now = content.getOrder_number();
                                                        String order_system_time = content.getOrder_system_time();
                                                        String practice_hours = content.getPractice_hours();
                                                        String traincar_experience = content.getTraincar_experience();
                                                        String car_level_detail = content.getCar_level_detail();
                                                        String get_on_address = content.getGet_on_address();
                                                        String get_down_address = content.getGet_down_address();
                                                        String true_phone = content.getTrue_phone();
                                                        String order_sort = content.getOrder_sort();
                                                        order_status = content.getOrder_status();
                                                        String start_time = content.getStart_time();
                                                        String end_time = content.getEnd_time();
                                                        if (!TextUtils.isEmpty(start_time)&&!TextUtils.isEmpty(end_time)) {
                                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                                            Date dateStart;
                                                            Date dateEnd;
                                                            try {
                                                                dateStart = formatter.parse(start_time);
                                                                dateEnd = formatter.parse(end_time);
                                                                SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
                                                                SimpleDateFormat formatDate = new SimpleDateFormat("MM月dd日");
                                                                String sStart = formatTime.format(dateStart);
                                                                String sEnd = formatTime.format(dateEnd);
                                                                String SDate = formatDate.format(dateStart);
                                                                tv_start_time.setText(sStart);
                                                                tv_end_time.setText(sEnd);
                                                                tv_date.setText(SDate);
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        if (!TextUtils.isEmpty(order_status)) {
                                                            //0:待付款  1：进行中  2：已完成  4：取消订单 6:待接单 9：已接单
                                                            if ("0".equals(order_status)) {
                                                                tv_indent_state.setText("待付款");
                                                                bt_fabu.setImageResource(R.mipmap.iv_practice_gopay);
                                                            }else if ("1".equals(order_status)){
                                                                tv_indent_state.setText("进行中");
                                                            }else if ("2".equals(order_status)){
                                                                tv_indent_state.setText("已完成");
                                                                if (order_receiving.equals("1")) {
                                                                    bt_quxiao.setVisibility(View.INVISIBLE);
                                                                }else {
                                                                    bt_quxiao.setVisibility(View.VISIBLE);
                                                                }
                                                                bt_quxiao.setImageResource(R.mipmap.iv_practice_pingjia);
                                                                bt_fabu.setImageResource(R.mipmap.iv_practice_once);
                                                            }else if ("6".equals(order_status)){
                                                                tv_indent_state.setText("待接单");
                                                                bt_quxiao.setVisibility(View.INVISIBLE);
                                                                bt_fabu.setImageResource(R.mipmap.iv_practice_quxiao);
                                                            }else if ("9".equals(order_status)){
                                                                tv_indent_state.setText("已接单");
                                                            }
                                                        }

                                                        if (!TextUtils.isEmpty(order_sort)) {
                                                            if ("2".equals(order_sort)) {
                                                                ll_type.setVisibility(View.VISIBLE);
                                                            }else {
                                                                ll_type.setVisibility(View.GONE);
                                                            }
                                                        }
                                                        if (!TextUtils.isEmpty(true_phone)) {
                                                            tv_jinji_phone.setText(true_phone);
                                                        }
                                                        //共计小时
                                                        if (!TextUtils.isEmpty(practice_hours)) {
                                                            tv_total_time.setText(practice_hours + "个小时");
                                                        }
                                                        //车辆级别
                                                        if (!TextUtils.isEmpty(car_level_detail)) {
                                                            tv_car_level.setText(car_level_detail);
                                                        }
                                                        //单价
                                                        if (!TextUtils.isEmpty("" + hour_price)) {
                                                            tv_indent_danjia.setText(hour_price+"/小时  " +"共"+practice_hours+"个小时");
                                                        }
                                                        //共计
                                                        if (!TextUtils.isEmpty("" + pay_price)) {
                                                            tv_indent_total_money.setText("" + pay_price);
                                                        }
                                                        //接送地址
                                                        if (!TextUtils.isEmpty(get_on_address)) {
                                                            tv_indent_jie_location.setText(get_on_address);
                                                        }
                                                        if (!TextUtils.isEmpty(get_down_address)) {
                                                            tv_indent_song_location.setText(get_down_address);
                                                        }
                                                        //下证时间
                                                        if (!TextUtils.isEmpty(down_card_time)) {
                                                            tv_indent_getcard_time.setText(down_card_time);
                                                        } else {
                                                            tv_indent_getcard_time.setText("无");
                                                        }
                                                        //毕业水平
                                                        if (!TextUtils.isEmpty(end_school_level)) {
                                                            if ("1".equals(end_school_level)) {
                                                                tv_indent_graduation_level.setText("勉强");
                                                            } else if ("2".equals(end_school_level)) {
                                                                tv_indent_graduation_level.setText("一般");
                                                            } else if ("3".equals(end_school_level)) {
                                                                tv_indent_graduation_level.setText("优秀");
                                                            } else if ("4".equals(end_school_level)) {
                                                                tv_indent_graduation_level.setText("熟练");
                                                            }

                                                        } else {
                                                            tv_indent_graduation_level.setText("无");
                                                        }
                                                        //练车经验
                                                        if (!TextUtils.isEmpty(traincar_experience)) {
                                                            if ("1".equals(traincar_experience)) {
                                                                tv_indent_practice_experience.setText("有");
                                                            } else if ("2".equals(traincar_experience)) {
                                                                tv_indent_practice_experience.setText("无");
                                                            }
                                                        } else {
                                                            tv_indent_practice_experience.setText("无");
                                                        }
                                                        //备注
                                                        if (!TextUtils.isEmpty(bz)) {
                                                            tv_indent_beizhu.setText(bz);
                                                        } else {
                                                            tv_indent_beizhu.setText("无");
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

    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ("indent".equals(type)) {
                finish();
            }else if ("liucheng".equals(type)){
                if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number_now)) {
                    AppUtils.deleteIndent(PracticeCarSubmitIndent.this, AppUrl.Delete_Indent_Practice, user_id, order_number_now);
                }
            }else {
                finish();
            }

            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                if ("indent".equals(type)) {
                    finish();
                }else if ("liucheng".equals(type)){
                    if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number_now)) {
                        AppUtils.deleteIndent(PracticeCarSubmitIndent.this, AppUrl.Delete_Indent_Practice, user_id, order_number_now);
                    }
                }else {
                    finish();
                }

                break;
            case R.id.bt_quxiao:
                if (!TextUtils.isEmpty(order_status)) {
                    //0:待付款  1：进行中  2：已完成  4：取消订单
                    if ("2".equals(order_status)) {
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(App.context, PingJia.class);
                        intent.putExtra("order_number", order_number);
                        App.context.startActivity(intent);
                        finish();
                    }else {
                        if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number_now)) {
                            AppUtils.deleteIndent(PracticeCarSubmitIndent.this, AppUrl.Delete_Indent_Practice, user_id, order_number_now);
                        }
                    }
                }
                break;
            case R.id.bt_fabu:
                if (DialogUtils.IsLogin()) {
                    if (!TextUtils.isEmpty(order_status)) {
                        //0:待付款  1：进行中  2：已完成  4：取消订单
                        if ("0".equals(order_status)) {
                            Intent intent = new Intent(App.context, AppPay.class);
                            intent.putExtra("pay_style", "practice");
                            intent.putExtra("order_number", order_number);
                            intent.putExtra("driver_id", driver_id);
                            intent.putExtra("subscription", pay_price + "");
                            intent.putExtra("jifen", "0");
                            startActivity(intent);
                            finish();
                        }else if ("6".equals(order_status)){
                            if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number_now)) {
                                AppUtils.deleteIndent(PracticeCarSubmitIndent.this, AppUrl.Delete_Indent_Practice, user_id, order_number_now);
                            }
                        }else if ("2".equals(order_status)){
                            Intent intent = new Intent(App.context, PracticeCarMapFragment.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
        }
    }

}
