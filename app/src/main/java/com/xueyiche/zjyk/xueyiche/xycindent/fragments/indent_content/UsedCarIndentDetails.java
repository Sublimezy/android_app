package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.UsedCarIndentContentBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by zhanglei on 2016/11/8.
 */
public class UsedCarIndentDetails extends BaseActivity implements NetBroadcastReceiver.netEventHandler, View.OnClickListener {

    private ImageView iv_caidan, iv_indent_content_phone, iv_school_head;
    private TextView tv_login_back, tv_caidan_background, tv_fanxian_states, tv_name, tv_rent_day, tv_get_location, tv_huan_location, tv_indent_system_time;
    private TextView tv_indent_number, tv_pay_money, tv_start_time, tv_end_time;
    private PopupWindow pop = new PopupWindow();
    private int height;
    private boolean isClosePup = true;
    private LinearLayout ll_exam_back;
    private String shop_phone, car_allname, still_latitude, still_longitude, take_latitude, take_longitude, take_car_address, still_car_address;

    @Override
    protected int initContentView() {
        return R.layout.indent_used_car_details;
    }

    @Override
    protected void initView() {
        iv_caidan = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_caidan);
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tv_caidan_background = (TextView) view.findViewById(R.id.tv_caidan_background);
        tv_fanxian_states = (TextView) view.findViewById(R.id.tv_fanxian_states);
        tv_rent_day = (TextView) view.findViewById(R.id.tv_rent_day);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_get_location = (TextView) view.findViewById(R.id.tv_get_location);
        tv_huan_location = (TextView) view.findViewById(R.id.tv_huan_location);
        tv_indent_number = (TextView) view.findViewById(R.id.tv_indent_number);
        tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        tv_indent_system_time = (TextView) view.findViewById(R.id.tv_indent_system_time);
        iv_school_head = (ImageView) view.findViewById(R.id.iv_school_head);
        tv_caidan_background.setFocusable(true);
        iv_indent_content_phone = (ImageView) view.findViewById(R.id.iv_indent_content_phone);
        iv_caidan.setVisibility(View.GONE);
        iv_caidan.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        tv_login_back.setText("订单详情");
        iv_indent_content_phone.setOnClickListener(this);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        height = dm.heightPixels;
    }

    @Override
    protected void initListener() {
        final View inflate = LayoutInflater.from(UsedCarIndentDetails.this).inflate(R.layout.pop_indent_content_caidan, null);
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
        tv_get_location.setOnClickListener(this);
        tv_huan_location.setOnClickListener(this);
    }

    @Override
    protected void initData() {


        getDataFromNet();

    }

    private void getDataFromNet() {
        showProgressDialog(false);
        Intent intent = getIntent();

        String user_id = PrefUtils.getString(App.context, "user_id", "");
        String order_number = intent.getStringExtra("order_number");
        OkHttpUtils.post().url(AppUrl.UsedCar_Indent_Details)
                .addParams("device_id", LoginUtils.getId(UsedCarIndentDetails.this))
                .addParams("order_number", order_number)
                .addParams("user_id", user_id)
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
        UsedCarIndentContentBean usedCarIndentDetails = JsonUtil.parseJsonToBean(string, UsedCarIndentContentBean.class);
        if (usedCarIndentDetails != null) {
            int code = usedCarIndentDetails.getCode();
            if (200 == code) {
                final UsedCarIndentContentBean.ContentBean content = usedCarIndentDetails.getContent();
                if (content != null) {
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //名字
                            car_allname = content.getCar_allname();
                            //天数
                            int least_rent_time = content.getLeast_rent_time();
                            //开始时间
                            String rent_start_time = content.getRent_start_time();
                            //结束时间
                            String rent_end_time = content.getRent_end_time();
                            //订单编号
                            String order_number = content.getOrder_number();
                            //订单状态
                            String order_status = content.getOrder_status();
                            //二手车头像
                            String twocar_img = content.getHeader_img();
                            //电话
                            shop_phone = content.getShop_phone();
                            //取车地点
                            take_car_address = content.getTake_car_address();
                            //还车地点
                            still_car_address = content.getStill_car_address();
                            //还车纬度
                            still_latitude = content.getStill_latitude();
                            //还车经度
                            still_longitude = content.getStill_longitude();
                            //取车纬度
                            take_latitude = content.getTake_latitude();
                            //取车经度
                            take_longitude = content.getTake_longitude();
                            //支付的金额
                            int rent_total = content.getRent_total();
                            //订单时间
                            String order_system_time = content.getOrder_system_time();

                            if (!TextUtils.isEmpty(order_status)) {
                                if ("0".equals(order_status)) {
                                    tv_fanxian_states.setText("待付款");
                                } else if ("1".equals(order_status)) {
                                    tv_fanxian_states.setText("进行中");
                                } else if ("2".equals(order_status)) {
                                    tv_fanxian_states.setText("已完成");
                                }
                            }
                            if (!TextUtils.isEmpty(twocar_img)) {
                                Picasso.with(App.context).load(twocar_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_school_head);
                            }
                            if (!TextUtils.isEmpty(car_allname)) {
                                tv_name.setText(car_allname);
                            }
                            if (!TextUtils.isEmpty(order_number)) {
                                tv_indent_number.setText(order_number);
                            }
                            if (!TextUtils.isEmpty("" + least_rent_time)) {
                                tv_rent_day.setText("" + least_rent_time);
                            }
                            if (!TextUtils.isEmpty(rent_start_time)) {
                                tv_start_time.setText(rent_start_time);
                            }
                            if (!TextUtils.isEmpty(rent_end_time)) {
                                tv_end_time.setText(rent_end_time);
                            }
                            if (!TextUtils.isEmpty(take_car_address)) {
                                tv_get_location.setText(take_car_address + "  ");
                            }
                            if (!TextUtils.isEmpty(still_car_address)) {
                                tv_huan_location.setText(still_car_address + "  ");
                            }
                            if (!TextUtils.isEmpty("" + rent_total)) {
                                tv_pay_money.setText("¥" + rent_total);
                            }
                            if (!TextUtils.isEmpty("" + order_system_time)) {
                                tv_indent_system_time.setText(""+order_system_time);
                            }
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
            case R.id.tv_get_location:
                //导航
                if (TextUtils.isEmpty(take_longitude)) {
                    showToastShort("位置异常");
                    return;
                }
                if (TextUtils.isEmpty(take_latitude)) {
                    showToastShort("位置异常");
                    return;
                }
                if (TextUtils.isEmpty(car_allname)) {
                    showToastShort("名称为空");
                    return;
                }
                if (TextUtils.isEmpty(take_car_address)) {
                    showToastShort("地址为空");
                    return;
                }
                XueYiCheUtils.getDiaLocation(UsedCarIndentDetails.this, take_latitude, take_longitude, car_allname, take_car_address);
                break;
            case R.id.tv_huan_location:
                //导航
                if (TextUtils.isEmpty(still_longitude)) {
                    showToastShort("位置异常");
                    return;
                }
                if (TextUtils.isEmpty(still_latitude)) {
                    showToastShort("位置异常");
                    return;
                }
                if (TextUtils.isEmpty(car_allname)) {
                    showToastShort("名称为空");
                    return;
                }
                if (TextUtils.isEmpty(still_car_address)) {
                    showToastShort("地址为空");
                    return;
                }
                XueYiCheUtils.getDiaLocation(UsedCarIndentDetails.this, still_latitude, still_longitude, car_allname, still_car_address);
                break;
            case R.id.iv_indent_content_phone:
                if (TextUtils.isEmpty(shop_phone)) {
                    showToastShort("店铺电话为空");
                    return;
                }
                XueYiCheUtils.CallPhone(UsedCarIndentDetails.this, "联系电话", shop_phone);
                break;
        }
    }

}
