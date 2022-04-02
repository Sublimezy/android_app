package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DatePanDuanUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/19.
 */
public class JieDanActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_name,tv_quxiao_rule,tv_quxiao,tv_date;
    private TextView tv_age;
    private TextView tv_gonghao;
    private CircleImageView ci_head;
    private TextView tv_tishi_quxiao;
    private ImageView iv_call;
    private String order_number;
    private String user_id;
    private String driver_phone;
    private String default_time;
    private LinearLayout ll_bottom_money, ll_top, ll_yuyue_time;
    private TextView tv_pay, tv_yuyue_shuoming;
    private TextView tv_juli;
    private double user_amount2;
    private String order_number2;
    private String on_latitude;
    private String on_longitude;
    public static JieDanActivity instance;
    private TextView tv_time_yuyue;
    private String order_status;
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    private int duration;
    private String head_img;
    private static Bitmap bitmap;
    private String latitude;
    private String longitude;
    private String cancle_remark;

    @Override
    protected int initContentView() {
        return R.layout.jiedan_activity;
    }

    @Override
    protected void initView() {

        instance = this;
        AppUtils.addActivity(this);
        iv_back = view.findViewById(R.id.iv_back);
        ll_top = view.findViewById(R.id.ll_top);
        ll_yuyue_time = view.findViewById(R.id.ll_yuyue_time);
        //姓名
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        //驾龄
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        tv_yuyue_shuoming = (TextView) view.findViewById(R.id.tv_yuyue_shuoming);
        //工号
        tv_gonghao = (TextView) view.findViewById(R.id.tv_gonghao);
        //头像
        ci_head = (CircleImageView) view.findViewById(R.id.ci_head);
        //取消时间提示  司机正在火速赶来，12：10后取消订单需要费用
        tv_tishi_quxiao = (TextView) view.findViewById(R.id.tv_tishi_quxiao);
        iv_call = (ImageView) view.findViewById(R.id.iv_call);
        tv_pay = (TextView) view.findViewById(R.id.tv_pay);
        tv_juli = (TextView) view.findViewById(R.id.tv_juli);
        tv_quxiao_rule = (TextView) view.findViewById(R.id.tv_quxiao_rule);
        tv_time_yuyue = (TextView) view.findViewById(R.id.tv_time_yuyue);
        tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        ll_bottom_money = (LinearLayout) view.findViewById(R.id.ll_bottom_money);

    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新代驾", msg)) {
            getOrder();
        } else if (TextUtils.equals("开始行程", msg)) {
            Intent intent = new Intent(this, JinXingActivity.class);
            intent.putExtra("order_number", order_number);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        iv_call.setOnClickListener(this);
        tv_quxiao.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        user_id = PrefUtils.getString(App.context, "user_id", "");
        default_time = PrefUtils.getString(App.context, "default_time", "15");
        order_number = getIntent().getStringExtra("order_number");
        getOrder();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void getOrder() {
        OkHttpUtils.post().url(AppUrl.Get_Order)
                .addParams("order_number", order_number)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    OrderInfoBean orderInfoBean = JsonUtil.parseJsonToBean(string, OrderInfoBean.class);
                    if (orderInfoBean != null) {
                        final int code = orderInfoBean.getCode();
                        if (200 == code) {
                            final OrderInfoBean.ContentBean content = orderInfoBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String job_number = content.getJob_number();
                                        tv_gonghao.setText("工号：" + job_number);
                                        String driving_year = content.getDriving_year();
                                        tv_age.setText(driving_year + "年驾龄");
                                        String driver_name = content.getDriver_name();
                                        tv_name.setText(driver_name);
                                        driver_phone = content.getDriver_phone();
                                        head_img = content.getHead_img();
                                        order_status = content.getOrder_status();
                                        cancle_remark = content.getCancle_remark();
                                        tv_tishi_quxiao.setText(cancle_remark);

                                        String appointed_time = content.getAppointed_time();
                                        if (TextUtils.isEmpty(appointed_time)) {
                                            ll_top.setVisibility(View.VISIBLE);
                                            tv_time_yuyue.setVisibility(View.GONE);
                                            ll_yuyue_time.setVisibility(View.GONE);
                                            tv_yuyue_shuoming.setVisibility(View.GONE);
                                        } else {
                                            tv_time_yuyue.setVisibility(View.VISIBLE);
                                            ll_top.setVisibility(View.GONE);
                                            ll_yuyue_time.setVisibility(View.VISIBLE);
                                            tv_yuyue_shuoming.setVisibility(View.VISIBLE);
                                        }
                                        //是否需要收费  1 需要  2 不需要
                                        String driver_need2 = content.getDriver_need2();
                                        if ("1".equals(driver_need2)) {
                                            ll_bottom_money.setVisibility(View.VISIBLE);
                                            ll_top.setVisibility(View.GONE);
                                            tv_quxiao.setVisibility(View.GONE);
                                        } else {
                                            ll_bottom_money.setVisibility(View.GONE);
                                            ll_top.setVisibility(View.VISIBLE);
                                            tv_quxiao.setVisibility(View.VISIBLE);
                                        }
                                        String waitminutes = content.getWaitminutes();
                                        String over_time = content.getOver_time();
                                        user_amount2 = content.getUser_amount2();
                                        if (!TextUtils.isEmpty(appointed_time)) {

                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                            Date date;
                                            try {
                                                date = format.parse(appointed_time);
                                                SimpleDateFormat sdf =   new SimpleDateFormat( "MM月dd日" );
                                                SimpleDateFormat sdf_time =   new SimpleDateFormat("HH:mm" );
                                                String sDate = sdf.format(date);
                                                String sTime = sdf_time.format(date);
                                                Long timestamp = DatePanDuanUtils.getTimestamp(appointed_time);
                                                String today = DatePanDuanUtils.getToday(""+timestamp);
                                                if (!TextUtils.isEmpty(sDate)) {
                                                    tv_date.setText(sDate+"  "+today);
                                                }


                                                tv_time_yuyue.setText(sTime);

                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        tv_juli.setText("共" + waitminutes + "分钟，超时" + over_time + "分钟");
                                        tv_pay.setText("等候费" + user_amount2 + "元 去支付");
                                        Picasso.with(App.context).load(head_img).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(ci_head);
                                        on_latitude = content.getOn_latitude();
                                        on_longitude = content.getOn_longitude();
                                        String down_latitude = content.getDown_latitude();
                                        String down_longitude = content.getDown_longitude();
                                        latitude = content.getLatitude();
                                        order_number2 = content.getOrder_number2();
                                        longitude = content.getLongitude();
                                        tv_quxiao_rule.setText(cancle_remark);
                                        tv_yuyue_shuoming.setText(cancle_remark);

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



        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pay:
                //支付等候费
                Intent intent = new Intent(App.context, AppPay.class);
                intent.putExtra("pay_style", "daijia2");
                intent.putExtra("order_number", order_number2);
                intent.putExtra("subscription", user_amount2 + "");
                intent.putExtra("jifen", "0");
                startActivity(intent);
                break;
            case R.id.iv_call:
                //打电话
                if (!TextUtils.isEmpty(driver_phone)) {
                    if (driver_phone.length() != 11) {
                        String decrypt = AES.decrypt(driver_phone);
                        XueYiCheUtils.CallPhone(this, "拨打代驾电话", decrypt);
                    } else {
                        XueYiCheUtils.CallPhone(this, "拨打代驾电话", driver_phone);
                    }
                } else {
                    Toast.makeText(JieDanActivity.this, "电话号码为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_quxiao:
                //取消订单
                Intent intent1 = new Intent(JieDanActivity.this, LiYouActivity.class);
                intent1.putExtra("order_number", order_number);
                intent1.putExtra("cancle_remark", cancle_remark);
                intent1.putExtra("type", "JieDan");
                startActivity(intent1);
                break;
        }
    }
}
