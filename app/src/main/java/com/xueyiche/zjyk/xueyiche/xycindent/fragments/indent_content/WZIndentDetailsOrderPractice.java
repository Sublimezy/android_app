package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderPracticeOkBean;
import com.xueyiche.zjyk.xueyiche.submit.bean.DeleteIndentBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by ZL on 2018/1/30.
 */
public class WZIndentDetailsOrderPractice extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    //菜单
    private ImageView iv_caidan;
    //最上面的支付状态
    private TextView tv_indent_pay_state;
    //开始时间
    private TextView tv_start_time;
    //共几小时
    private TextView tv_total_time;
    //结束时间
    private TextView tv_end_time;
    //车型
    private TextView tv_car_level;
    //车辆品牌
    private TextView tv_indent_carstyle;
    //单价区间
    private TextView tv_indent_danjia;
    //总价区间
    private TextView tv_indent_total_money, tv_pay_money;
    //接送地址
    private TextView tv_indent_jie_location;
    private TextView tv_indent_song_location;
    //意向练车项目
    private TextView tv_indent_will_content;
    //意向练车区域
//    private TextView tv_indent_will_area;
    //下证时间
    private TextView tv_indent_getcard_time;
    //毕业水平
    private TextView tv_indent_graduation_level;
    //练车经验
    private TextView tv_indent_practice_experience;
    //备注
    private TextView tv_indent_beizhu;
    //订单编号
    private TextView tv_indent_number;
    //订单时间
    private TextView tv_indent_date;
    //取消预约
    private TextView bt_indent_content_quxiao;
    //订单状态
    private TextView tv_indent_state;
    //下面是否隐藏
    private LinearLayout isGone;
    private LinearLayout rl_bottom;
    private View view_line;
    //立即支付
    private Button bt_indent_content_pay;
    private String order_number;
    private String user_id;
    private String order_status;
    private int pay_price;
    private String track_status;

    @Override
    protected int initContentView() {
        return R.layout.wz_indent_details_order_practice;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.indent_details_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.indent_details_include).findViewById(R.id.tv_login_back);
        iv_caidan = (ImageView) view.findViewById(R.id.indent_details_include).findViewById(R.id.iv_caidan);
        tv_indent_pay_state = (TextView) view.findViewById(R.id.tv_indent_pay_state);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_total_time = (TextView) view.findViewById(R.id.tv_total_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        bt_indent_content_quxiao = (TextView) view.findViewById(R.id.bt_indent_content_quxiao);
        tv_indent_carstyle = (TextView) view.findViewById(R.id.tv_indent_carstyle);
        tv_indent_danjia = (TextView) view.findViewById(R.id.tv_indent_danjia);
        tv_car_level = (TextView) view.findViewById(R.id.tv_car_level);
        tv_indent_total_money = (TextView) view.findViewById(R.id.tv_indent_total_money);
        tv_indent_jie_location = (TextView) view.findViewById(R.id.tv_indent_jie_location);
        tv_indent_song_location = (TextView) view.findViewById(R.id.tv_indent_song_location);
        tv_indent_will_content = (TextView) view.findViewById(R.id.tv_indent_will_content);
//        tv_indent_will_area = (TextView) view.findViewById(R.id.tv_indent_will_area);
        tv_indent_getcard_time = (TextView) view.findViewById(R.id.tv_indent_getcard_time);
        tv_indent_graduation_level = (TextView) view.findViewById(R.id.tv_indent_graduation_level);
        tv_indent_practice_experience = (TextView) view.findViewById(R.id.tv_indent_practice_experience);
        tv_indent_beizhu = (TextView) view.findViewById(R.id.tv_indent_beizhu);
        tv_indent_number = (TextView) view.findViewById(R.id.tv_indent_number);
        tv_indent_date = (TextView) view.findViewById(R.id.tv_indent_date);
        tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        tv_indent_state = (TextView) view.findViewById(R.id.tv_indent_state);
        isGone = (LinearLayout) view.findViewById(R.id.isGone);
        view_line = view.findViewById(R.id.view_line);
        rl_bottom = (LinearLayout) view.findViewById(R.id.rl_bottom);
        bt_indent_content_pay = (Button) view.findViewById(R.id.bt_indent_content_pay);
        ll_exam_back.setOnClickListener(this);
        iv_caidan.setVisibility(View.GONE);
        iv_caidan.setOnClickListener(this);
        bt_indent_content_pay.setOnClickListener(this);
        bt_indent_content_quxiao.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    private void getDataFromNet() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Order_Post_Ok)
                    .addParams("device_id", LoginUtils.getId(this))
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
                                                final String down_card_time = content.getDown_card_time();
                                                final String end_school_level = content.getEnd_school_level();
                                                final String end_time = content.getEnd_time();
                                                pay_price = content.getPay_price();
                                                final int hour_price = content.getHour_price();
                                                order_number = content.getOrder_number();
                                                final String order_system_time = content.getOrder_system_time();
                                                final String practice_hours = content.getPractice_hours();
                                                final String purpose_item = content.getPurpose_item();
                                                final String start_time = content.getStart_time();
                                                final String traincar_experience = content.getTraincar_experience();
                                                final String get_on_address = content.getGet_on_address();
                                                final String get_down_address = content.getGet_down_address();
                                                final  String car_level_detail = content.getCar_level_detail();
                                                order_status = content.getOrder_status();
                                                final String order_number = content.getOrder_number();
                                                //1：教练点击开始；2：结束，默认：0
                                                track_status = content.getTrack_status();
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
                                                        //车辆级别
                                                        if (!TextUtils.isEmpty(car_level_detail)) {
                                                            tv_car_level.setText(car_level_detail);
                                                        }
                                                        //单价
                                                        if (!TextUtils.isEmpty("" + hour_price)) {
                                                            tv_indent_danjia.setText("" + hour_price);
                                                        }
                                                        //共计
                                                        if (!TextUtils.isEmpty("" + pay_price)) {
                                                            tv_indent_total_money.setText("" + pay_price);
                                                        }
                                                        //共计
                                                        if (!TextUtils.isEmpty("" + pay_price)) {
                                                            tv_pay_money.setText("" + pay_price);
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
//                                                        //意向练车区域
//                                                        if (!TextUtils.isEmpty(area_name)) {
//                                                            tv_indent_will_area.setText(area_name);
//                                                        }
                                                        //下证时间
                                                        if (!TextUtils.isEmpty(down_card_time)) {
                                                            tv_indent_getcard_time.setText(down_card_time);
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

                                                        }
                                                        //练车经验
                                                        if (!TextUtils.isEmpty(traincar_experience)) {
                                                            if ("1".equals(traincar_experience)) {
                                                                tv_indent_practice_experience.setText("有");
                                                            } else if ("2".equals(traincar_experience)) {
                                                                tv_indent_practice_experience.setText("无");
                                                            }
                                                        }
                                                        //备注
                                                        if (!TextUtils.isEmpty(bz)) {
                                                            tv_indent_beizhu.setText("备注：" + bz);
                                                        } else {
                                                            tv_indent_beizhu.setText("备注：无");
                                                        }
                                                        //下单时间
                                                        if (!TextUtils.isEmpty(order_system_time)) {
                                                            tv_indent_date.setText(order_system_time);
                                                        } //订单编号
                                                        if (!TextUtils.isEmpty(order_number)) {
                                                            tv_indent_number.setText(order_number);
                                                        }
                                                        if (!TextUtils.isEmpty(order_status)) {
                                                            if ("6".equals(order_status)) {
                                                                //待接单
                                                                tv_indent_pay_state.setText("待接单");
                                                                view_line.setVisibility(View.GONE);
                                                                isGone.setVisibility(View.GONE);
                                                                bt_indent_content_pay.setVisibility(View.GONE);
                                                                bt_indent_content_quxiao.setText("取消预约");
                                                            } else if ("0".equals(order_status)) {
                                                                //待支付
                                                                tv_indent_pay_state.setText("待支付");
                                                                bt_indent_content_pay.setText("立即支付");
                                                                bt_indent_content_quxiao.setText("取消订单");
                                                            } else if ("1".equals(order_status)) {
                                                                //已支付练车教练未开始
                                                                tv_indent_pay_state.setText("已支付");
                                                                isGone.setVisibility(View.GONE);
                                                                bt_indent_content_quxiao.setText("取消订单");
                                                                bt_indent_content_pay.setText("进入练车");
                                                            } else if ("2".equals(order_status)) {
                                                                //已支付练车教练未开始
                                                                tv_indent_pay_state.setText("已完成");
                                                                isGone.setVisibility(View.GONE);
                                                                bt_indent_content_quxiao.setVisibility(View.GONE);
                                                                bt_indent_content_pay.setVisibility(View.GONE);
                                                            }
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

    @Override
    protected void initData() {
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        getDataFromNet();
        tv_login_back.setText("订单详情");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_caidan:

                //菜单
                break;
            case R.id.bt_indent_content_quxiao:
                if (!TextUtils.isEmpty(order_status) && !TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number)) {
                    if ("0".equals(order_status) || "6".equals(order_status)) {
                        //代付款取消
                        cancelIndentFree(this, AppUrl.Delete_Indent_Practice, user_id, order_number);
                    } else {
                        //取消订单赔付违约金

                    }
                }
                break;
            case R.id.bt_indent_content_pay:
                if (!TextUtils.isEmpty(order_status)) {
                    if ("0".equals(order_status)) {
                        //待支付
                        Intent intent = new Intent(App.context, AppPay.class);
                        intent.putExtra("pay_style", "practice");
                        intent.putExtra("order_number", order_number);
                        intent.putExtra("subscription", pay_price + "");
                        intent.putExtra("jifen","0");
                        startActivity(intent);
                    } else {
                        //已支付练车
                        if (!"1".equals(track_status)) {
                            showToastShort("等待教练确认开始练车");
                            return;
                        }
                        Intent intent = new Intent(App.context, PracticeIndentContent.class);
                        intent.putExtra("order_number", order_number);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    public void cancelIndentFree(final Activity activity, final String Url, final String user_id, final String order_number) {
        //免费取消订单
        View viewDia = LayoutInflater.from(App.context).inflate(R.layout.get_jifen_dialog, null);
        TextView tv_quxiao = (TextView) viewDia.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) viewDia.findViewById(R.id.tv_queren);
        TextView tv_content = (TextView) viewDia.findViewById(R.id.tv_content);
        tv_content.setText("是否取消订单？");
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog).setView(viewDia);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth * 2 / 3;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils.post().url(Url)
                        .addParams("device_id", TextUtils.isEmpty(LoginUtils.getId(activity)) ? "" : LoginUtils.getId(activity))
                        .addParams("user_id", user_id)
                        .addParams("order_number", order_number)
                        .build()
                        .execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws IOException {
                                String string = response.body().string();
                                if (!TextUtils.isEmpty(string)) {
                                    DeleteIndentBean deleteIndentBean = JsonUtil.parseJsonToBean(string, DeleteIndentBean.class);
                                    if (deleteIndentBean != null) {
                                        int code = deleteIndentBean.getCode();
                                        final String msg = deleteIndentBean.getMsg();
                                        if (!TextUtils.isEmpty("" + code)) {
                                            if (200 == code) {
                                                App.handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                                dialog01.dismiss();
                                            }
                                        }
                                    }
                                }

                                return null;
                            }

                            @Override
                            public void onError(Request request, Exception e) {

                            }

                            @Override
                            public void onResponse(Object response) {

                            }
                        });
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }
}
