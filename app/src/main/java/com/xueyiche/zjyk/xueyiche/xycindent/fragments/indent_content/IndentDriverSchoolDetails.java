package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.NewPlayActivity;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.DriverSchoolIndentDetailsBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by zhanglei on 2016/11/8.
 */
public class IndentDriverSchoolDetails extends BaseActivity implements NetBroadcastReceiver.netEventHandler, View.OnClickListener {

    private ImageView iv_caidan, iv_indent_content_phone, iv_indent_content_daohang, iv_school_head, iv_ew_caode;
    private TextView tv_login_back, tv_caidan_background, tv_fanxian_states, tv_shop_name, tv_shop_place;
    private TextView tv_indent_number, tv_indent_content_style_fuwu, tv_indent_content_money, tv_indent_content_money_shifu;
    private TextView tv_indent_time, tv_indent_content_style_zhifu,tv_erweima;
    private PopupWindow pop = new PopupWindow();
    private int height;
    private boolean isClosePup = true;
    private String order_number;
    private String driver_school_name;
    private String driver_school_phone;
    private String driver_school_place;
    private String latitude;
    private String longitude;
    private LinearLayout ll_exam_back;
    private ListView lv_shangpin;


    @Override
    protected int initContentView() {
        return R.layout.indent_driver_school_details;
    }

    @Override
    protected void initView() {
        iv_caidan = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_caidan);
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        iv_indent_content_daohang = (ImageView) view.findViewById(R.id.iv_indent_content_daohang);
        tv_caidan_background = (TextView) view.findViewById(R.id.tv_caidan_background);
        tv_fanxian_states = (TextView) view.findViewById(R.id.tv_fanxian_states);
        tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
        tv_erweima = (TextView) view.findViewById(R.id.tv_erweima);
        lv_shangpin = (ListView) view.findViewById(R.id.lv_shangpin);
        lv_shangpin.setVisibility(View.GONE);
        tv_shop_place = (TextView) view.findViewById(R.id.tv_shop_place);
        tv_indent_number = (TextView) view.findViewById(R.id.tv_indent_number);
        tv_indent_content_style_fuwu = (TextView) view.findViewById(R.id.tv_indent_content_style_fuwu);
        tv_indent_content_money_shifu = (TextView) view.findViewById(R.id.tv_indent_content_money_shifu);
        tv_indent_content_money = (TextView) view.findViewById(R.id.tv_indent_content_money);
        tv_indent_content_style_zhifu = (TextView) view.findViewById(R.id.tv_indent_content_style_zhifu);
        tv_indent_time = (TextView) view.findViewById(R.id.tv_indent_time);
        iv_school_head = (ImageView) view.findViewById(R.id.iv_school_head);
        iv_ew_caode = (ImageView) view.findViewById(R.id.iv_ew_caode);
        tv_caidan_background.setFocusable(true);
        iv_indent_content_phone = (ImageView) view.findViewById(R.id.iv_indent_content_phone);
        iv_caidan.setVisibility(View.GONE);
        iv_caidan.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        tv_login_back.setText("订单详情");
        iv_indent_content_daohang.setOnClickListener(this);
        iv_indent_content_phone.setOnClickListener(this);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        height = dm.heightPixels;
    }

    @Override
    protected void initListener() {
        final View inflate = LayoutInflater.from(IndentDriverSchoolDetails.this).inflate(R.layout.pop_indent_content_caidan, null);
        final TextView tv_caidan_message = (TextView) inflate.findViewById(R.id.tv_caidan_message);
        final TextView tv_caidan_shouye = (TextView) inflate.findViewById(R.id.tv_caidan_shouye);
        final TextView tv_caidan_kefu = (TextView) inflate.findViewById(R.id.tv_caidan_kefu);
        iv_caidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (isClosePup) {

                    tv_caidan_background.setVisibility(View.VISIBLE);
                    tv_caidan_message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (pop != null && pop.isShowing()) {
                                Intent intent = new Intent(App.context, NewPlayActivity.class);
                                startActivity(intent);
                                finish();
                                pop.dismiss();
                                isClosePup = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        tv_caidan_background.setVisibility(View.GONE);
                                    }
                                }, 200);
                            }
                        }
                    });
                    tv_caidan_shouye.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (pop != null && pop.isShowing()) {
                                pop.dismiss();
                                Intent intent = new Intent(App.context, MainActivity.class);
                                intent.putExtra("position", 0);
                                startActivity(intent);
                                finish();
                                isClosePup = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_caidan_background.setVisibility(View.GONE);
                                    }
                                }, 300);
                            }
                        }
                    });
                    tv_caidan_kefu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (pop != null && pop.isShowing()) {
                                pop.dismiss();
                                startActivity(new Intent(App.context, KeFuActivity.class));
                                isClosePup = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        tv_caidan_background.setVisibility(View.GONE);
                                    }
                                }, 300);
                            }
                        }
                    });
                    pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    pop.setHeight(height - 240);
                    pop.setBackgroundDrawable(new BitmapDrawable());
                    //添加弹出、弹入的动画
                    pop.setAnimationStyle(R.style.Popupwindow_indent_caidan);
                    pop.setOutsideTouchable(false);
                    pop.setContentView(inflate);
                    pop.showAsDropDown(view, 0, 0);
                    isClosePup = false;
                } else {
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                        isClosePup = true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tv_caidan_background.setVisibility(View.GONE);
                            }
                        }, 300);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        getDataFromNet();

    }

    private void getDataFromNet() {
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.Driver_Shcool_Indent_Details)
                .addParams("device_id", LoginUtils.getId(IndentDriverSchoolDetails.this))
                .addParams("order_number", order_number)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            process(string);
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

    private void process(String string) {
        DriverSchoolIndentDetailsBean driverSchoolIndentDetailsBean = JsonUtil.parseJsonToBean(string, DriverSchoolIndentDetailsBean.class);
        if (driverSchoolIndentDetailsBean != null) {
            int code = driverSchoolIndentDetailsBean.getCode();
            if (200 == code) {
                DriverSchoolIndentDetailsBean.ContentBean content = driverSchoolIndentDetailsBean.getContent();
                if (content != null) {
                    driver_school_name = content.getDriver_school_name();
                    latitude = content.getLatitude();
                    longitude = content.getLongitude();
                    final String driver_school_money = content.getDriver_school_money();
                    driver_school_phone = content.getDriver_school_phone();
                    driver_school_place = content.getDriver_school_place();
                    final String driver_school_service = content.getDriver_school_service();
                    final String order_number = content.getOrder_number();
                    final String driver_school_url = content.getDriver_school_url();
                    final String subscription = content.getSubscription();
                    final String order_system_time = content.getOrder_system_time();
                    final String ew_code = content.getEw_code();
                    final String order_status = content.getOrder_status();
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_erweima.setText("券码："+ew_code);
                            if (!TextUtils.isEmpty(order_status)) {
                                if ("1".equals(order_status)) {
                                    tv_fanxian_states.setText("进行中");
                                } else if ("2".equals(order_status)) {
                                    tv_fanxian_states.setText("已完成");
                                } else if ("5".equals(order_status)) {
                                    tv_fanxian_states.setText("待返现");
                                } else if ("8".equals(order_status)) {
                                    tv_fanxian_states.setText("返现中");
                                }
                            }
                            if (!TextUtils.isEmpty(driver_school_url)) {
                                Picasso.with(App.context).load(driver_school_url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_school_head);
                            }
                            if (!TextUtils.isEmpty(driver_school_name)) {
                                tv_shop_name.setText(driver_school_name);
                            }
                            if (!TextUtils.isEmpty(driver_school_place)) {
                                tv_shop_place.setText(driver_school_place);
                            }
                            if (!TextUtils.isEmpty(order_number)) {
                                tv_indent_number.setText(order_number);
                            }
                            if (!TextUtils.isEmpty(driver_school_service)) {
                                tv_indent_content_style_fuwu.setText(driver_school_service);
                            }
                            if (!TextUtils.isEmpty(driver_school_money)) {
                                tv_indent_content_money.setText("¥" + driver_school_money);
                            }
                            if (!TextUtils.isEmpty(subscription)) {
                                tv_indent_content_money_shifu.setText("¥" + subscription);
                            }
                            if (!TextUtils.isEmpty(order_system_time)) {
                                tv_indent_time.setText(order_system_time);
                            }
                            Bitmap qrCode = XueYiCheUtils.createQRCodeDriver(ew_code, 260, 260, null);
                            iv_ew_caode.setImageBitmap(qrCode);
                        }
                    });
                }
            }
        }

    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_indent_content_daohang:
                //导航
                if (TextUtils.isEmpty(longitude)) {
                    showToastShort("位置异常");
                    return;
                }
                if (TextUtils.isEmpty(latitude)) {
                    showToastShort("位置异常");
                    return;
                }
                if (TextUtils.isEmpty(driver_school_name)) {
                    showToastShort("驾校名称为空");
                    return;
                }
                if (TextUtils.isEmpty(driver_school_place)) {
                    showToastShort("驾校地址为空");
                    return;
                }
                XueYiCheUtils.getDiaLocation(IndentDriverSchoolDetails.this, latitude, longitude, driver_school_name, driver_school_place);
                break;
            case R.id.iv_indent_content_phone:
                if (TextUtils.isEmpty(driver_school_phone)) {
                    showToastShort("驾校电话为空");
                    return;
                }
                if (TextUtils.isEmpty(driver_school_name)) {
                    showToastShort("驾校名称为空");
                    return;
                }
                XueYiCheUtils.CallPhone(IndentDriverSchoolDetails.this, driver_school_name, driver_school_phone);
                break;
        }
    }
}
