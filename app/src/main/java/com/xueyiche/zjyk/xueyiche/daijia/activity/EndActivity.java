package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Administrator on 2019/9/23.
 */
public class EndActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back, iv_anquan, iv_call, now_location;
    private TextView tv_name;
    private TextView tv_age;
    private TextView tv_gonghao;
    private CircleImageView ci_head;
    private TextView tv_pingjia;
    private RatingBar rb_star;
    private String user_id;
    private String order_number;
    private TextView tv_act_distance;
    private TextView tv_user_amount;
    private TextView tv_wait_time;
    private TextView tv_all_money;
    private TextView tv_user_amount2;
    private TextView tv_user_amount3;
    private double user_amount3;
    private String order_status;
    private String driver_phone;
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";

    @Override
    protected int initContentView() {
        return R.layout.end_activity;
    }

    @Override
    protected void initView() {

        iv_back = view.findViewById(R.id.iv_back);
        //合计
        tv_all_money = (TextView) view.findViewById(R.id.tv_all_money);
        //超出等候费
        tv_user_amount2 = (TextView) view.findViewById(R.id.tv_user_amount2);
        //超出公里费
        tv_user_amount3 = (TextView) view.findViewById(R.id.tv_user_amount3);
        //姓名
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        //驾龄
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        //工号
        tv_gonghao = (TextView) view.findViewById(R.id.tv_gonghao);
        tv_pingjia = (TextView) view.findViewById(R.id.tv_pingjia);
        tv_act_distance = (TextView) view.findViewById(R.id.tv_act_distance);
        tv_user_amount = (TextView) view.findViewById(R.id.tv_user_amount);
        tv_wait_time = (TextView) view.findViewById(R.id.tv_wait_time);
        iv_anquan = view.findViewById(R.id.iv_anquan);
        iv_call = view.findViewById(R.id.iv_call);
        //头像
        ci_head = (CircleImageView) view.findViewById(R.id.ci_head);
        now_location = view.findViewById(R.id.now_location);

    }


    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_pingjia.setOnClickListener(this);
        iv_anquan.setOnClickListener(this);
        iv_call.setOnClickListener(this);
        now_location.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        order_number = getIntent().getStringExtra("order_number");
        getOrder();
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
                                        //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
                                        String job_number = content.getJob_number();
                                        order_status = content.getOrder_status();
                                        driver_phone = content.getDriver_phone();
                                        String driving_year = content.getDriving_year();
                                        String driver_name = content.getDriver_name();
                                        String head_img = content.getHead_img();
                                        String on_latitude = content.getOn_latitude();
                                        String on_longitude = content.getOn_longitude();
                                        String down_latitude = content.getDown_latitude();
                                        String down_longitude = content.getDown_longitude();
                                        String order_time1 = content.getOrder_time1();
                                        String act_distance = content.getAct_distance();
                                        double user_amount = content.getUser_amount();
                                        String over_distance = content.getOver_distance();
                                        String waitminutes = content.getWaitminutes();
                                        String latitude = content.getLatitude();
                                        String longitude = content.getLongitude();
                                        //等候费金额
                                        double user_amount2 = content.getUser_amount2();
                                        user_amount3 = content.getUser_amount3();
                                        if ("4".equals(order_status)) {
                                            tv_pingjia.setText("评 价");
                                        } else {
                                            tv_pingjia.setText("已评价");
                                        }
                                        if (!TextUtils.isEmpty(job_number)) {
                                            tv_gonghao.setText("工号：" + job_number);
                                        }
                                        if (!TextUtils.isEmpty(driving_year)) {
                                            tv_age.setText(driving_year + "年驾龄");
                                        }
                                        if (!TextUtils.isEmpty(driver_name)) {
                                            tv_name.setText(driver_name);
                                        }
                                        if (!TextUtils.isEmpty(head_img)) {
                                            Picasso.with(App.context).load(head_img).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(ci_head);
                                        }
                                        if (!TextUtils.isEmpty(act_distance)) {
                                            tv_act_distance.setText("里程费（共" + act_distance + "公里，区域外" + over_distance + "公里）");
                                        }
                                        tv_user_amount.setText(user_amount + "元");
                                        tv_user_amount2.setText(user_amount2 + "元");
                                        tv_user_amount3.setText(user_amount3 + "元");
                                        if (!TextUtils.isEmpty(waitminutes)) {

                                            tv_wait_time.setText("等候费（共" + waitminutes + "分）");
                                        }
                                        tv_all_money.setText((user_amount + user_amount2 + user_amount3) + "元");

                                        if (!TextUtils.isEmpty(down_latitude) && !TextUtils.isEmpty(down_longitude) && !TextUtils.isEmpty(on_latitude)
                                                && !TextUtils.isEmpty(on_longitude)) {
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
            case R.id.now_location:

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
                }
                break;
            case R.id.iv_anquan:
                showAnQuan();
                break;
            case R.id.tv_pingjia:
                //去评价
                if ("4".equals(order_status)) {
                    showPingJia();
                }
                break;
        }
    }

    public void showAnQuan() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_anqun_layout, null);
        //初始化控件
        LinearLayout ll_jinji = (LinearLayout) inflate.findViewById(R.id.ll_jinji);
        LinearLayout ll_baojing = (LinearLayout) inflate.findViewById(R.id.ll_baojing);
        RelativeLayout rl_xuzhi = (RelativeLayout) inflate.findViewById(R.id.rl_xuzhi);
        ImageView iv_close_anquan = (ImageView) inflate.findViewById(R.id.iv_close_anquan);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
        iv_close_anquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll_jinji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, JinjiPhoneActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ll_baojing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XueYiCheUtils.CallPhone(EndActivity.this, "拨打报警电话", "110");
            }
        });
        rl_xuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //web页
                Intent intent = new Intent(EndActivity.this, UrlActivity.class);
                intent.putExtra("url", "http://xueyiche.cn/xyc/instructions/safe.html");
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });

    }

    private void showPingJia() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.pingjia_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        final RatingBar rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        rb_star.setRating(5f);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;

        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 400;
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
                int rating = (int) rb_star.getRating();
                pingJia(rating);
                dialog01.dismiss();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    public void pingJia(int rating) {
        OkHttpUtils.post().url(AppUrl.PingJia_DaiJia)
                .addParams("order_number", order_number)
                .addParams("user_id", user_id)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("score_num", rating + "")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    final SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                    if (successDisCoverBackBean != null) {
                        final int code = successDisCoverBackBean.getCode();
                        if (200 == code) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String msg = successDisCoverBackBean.getMsg();
                                    Toast.makeText(EndActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    finish();
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
