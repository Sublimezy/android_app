package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.SjLoactionBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/23.
 */
public class JinXingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back,iv_anquan,iv_call,now_location;
    private TextView tv_time,tv_total_money,tv_share;
    private String user_id;
    private String order_number;
    private int duration;
    private String down_latitude;
    private String down_longitude;
    public static JinXingActivity instance;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getSj();
            handler.postDelayed(this, 5000);
        }
    };
    private LinearLayout ll_bottom_money,ll_feiyong;
    private TextView tv_pay;
    private TextView tv_gonghao, tv_age, tv_name, tv_juli,tv_act_distance,tv_user_amount,tv_user_amount2,tv_user_amount3,tv_wait_time;
    private CircleImageView ci_head;
    private double user_amount3;
    private String order_number3;
    private String driver_phone;
    private CheckBox cb_jifei;
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    private String on_latitude;
    private String on_longitude;
    private int distance;

    @Override
    protected int initContentView() {
        return R.layout.jinxin_activity;
    }

    @Override
    protected void initView() {
        instance = this;
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_anquan = (ImageView) view.findViewById(R.id.iv_anquan);
        iv_call = (ImageView) view.findViewById(R.id.iv_call);
        //预计到达时间
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_act_distance = (TextView) view.findViewById(R.id.tv_act_distance);
        tv_total_money = (TextView) view.findViewById(R.id.tv_total_money);
        //预计费用
        ll_bottom_money = (LinearLayout) view.findViewById(R.id.ll_bottom_money);
        tv_pay = (TextView) view.findViewById(R.id.tv_pay);
        tv_gonghao = (TextView) view.findViewById(R.id.tv_gonghao);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_juli = (TextView) view.findViewById(R.id.tv_juli);
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        tv_share= (TextView) view.findViewById(R.id.tv_share);
        ci_head = (CircleImageView) view.findViewById(R.id.ci_head);
        cb_jifei =  view.findViewById(R.id.cb_jifei);
        ll_feiyong =  view.findViewById(R.id.ll_feiyong);
        now_location =  view.findViewById(R.id.now_location);
        tv_user_amount =  view.findViewById(R.id.tv_user_amount);
        tv_user_amount2 =  view.findViewById(R.id.tv_user_amount2);
        tv_user_amount3 =  view.findViewById(R.id.tv_user_amount3);
        tv_wait_time =  view.findViewById(R.id.tv_wait_time);

    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新代驾", msg)) {
            getOrder();
        } else if (TextUtils.equals("代驾结束", msg)) {
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra("order_number", order_number);
            startActivity(intent);
            finish();
        }
    }
    private String getCustomStyleFilePath(Context context, String customStyleFileName) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String parentPath = null;

        try {
            inputStream = context.getAssets().open("customConfigdir/" + customStyleFileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            parentPath = context.getFilesDir().getAbsolutePath();
            File customStyleFile = new File(parentPath + "/" + customStyleFileName);
            if (customStyleFile.exists()) {
                customStyleFile.delete();
            }
            customStyleFile.createNewFile();

            outputStream = new FileOutputStream(customStyleFile);
            outputStream.write(buffer);
        } catch (IOException e) {
            Log.e("CustomMapDemo", "Copy custom style file failed", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e("CustomMapDemo", "Close stream failed", e);
                return null;
            }
        }

        return parentPath + "/" + customStyleFileName;
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

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        iv_anquan.setOnClickListener(this);
        iv_call.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        now_location.setOnClickListener(this);
        cb_jifei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_feiyong.setVisibility(View.VISIBLE);
                }else {
                    ll_feiyong.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        order_number = getIntent().getStringExtra("order_number");

        // 地图初始化
        getOrder();
        LatLng latLng1 = new LatLng(Double.valueOf(down_latitude),Double.valueOf(down_longitude));
        View viewTop = LayoutInflater.from(App.context).inflate(R.layout.jiedan_top_layout, null);
        TextView tv_content = viewTop.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(""+distance)) {
            BigDecimal bigDecimal1 = new BigDecimal(distance);
            BigDecimal bigDecimal2 = new BigDecimal("" + 1000);
            final String   rder_distance = bigDecimal1.divide(bigDecimal2).setScale(1, BigDecimal.ROUND_HALF_UP) + "";
            tv_content.setText("距离" + rder_distance + "公里 预计" + duration / 60 + "分钟到达");
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
                                        String head_img = content.getHead_img();
                                        Picasso.with(App.context).load(head_img).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(ci_head);
                                        down_latitude = content.getDown_latitude();
                                        down_longitude = content.getDown_longitude();
                                        on_latitude = content.getOn_latitude();
                                        on_longitude = content.getOn_longitude();
                                        String latitude = content.getLatitude();
                                        String longitude = content.getLongitude();
                                        user_amount3 = content.getUser_amount3();
                                        String over_distance = content.getOver_distance();
                                        order_number3 = content.getOrder_number3();
                                        String act_distance = content.getAct_distance();
                                        driver_phone = content.getDriver_phone();
                                        double user_amount = content.getUser_amount();
                                        double user_amount2 = content.getUser_amount2();
                                        String waitminutes = content.getWaitminutes();
                                        //三次超里程司机是否需要收费  1 需要  2 不需要
                                        String driver_need3 = content.getDriver_need3();
//                                        if ("1".equals(driver_need3)) {
//                                            ll_bottom_money.setVisibility(View.VISIBLE);
//                                        } else {
//                                            ll_bottom_money.setVisibility(View.GONE);
//                                        }
                                        if (!TextUtils.isEmpty(act_distance)) {
                                            tv_act_distance.setText("里程费（共" + act_distance + "公里，区域外" + over_distance + "公里）");
                                        }
                                        tv_user_amount.setText(user_amount + "元");
                                        tv_user_amount2.setText(user_amount2 + "元");
                                        tv_user_amount3.setText(user_amount3 + "元");
                                        if (!TextUtils.isEmpty(waitminutes)) {

                                            tv_wait_time.setText("等候费（共" + waitminutes + "分）");
                                        }
                                        tv_total_money.setText((user_amount + user_amount2 + user_amount3) + "元");
                                        tv_pay.setText("里程费" + user_amount3 + "元 去支付");
                                        tv_juli.setText("共" + act_distance + "公里，区域外" + over_distance + "公里）");
                                        if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(down_latitude)
                                                && !TextUtils.isEmpty(down_longitude)) {
                                            setMap(latitude, longitude);
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

    public void getSj() {
        OkHttpUtils.post().url(AppUrl.SJ)
                .addParams("order_number", order_number)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    SjLoactionBean sjLoactionBean = JsonUtil.parseJsonToBean(string, SjLoactionBean.class);
                    if (sjLoactionBean != null) {
                        final int code = sjLoactionBean.getCode();
                        if (200 == code) {
                            final SjLoactionBean.ContentBean content = sjLoactionBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        String latitude = content.getLatitude();
                                        String longitude = content.getLongitude();
                                        if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(down_latitude)
                                                && !TextUtils.isEmpty(down_longitude)) {
                                            setMap(latitude, longitude);
                                        }
                                        handler.postDelayed(runnable, 5000);
                                        //handler.removeCallbacks(runnable);
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

    private void setMap(String latitude, String longitude) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.now_location:
                break;
            case R.id.tv_pay:
                //支付
                Intent intent = new Intent(App.context, AppPay.class);
                intent.putExtra("pay_style", "daijia3");
                intent.putExtra("order_number", order_number3);
                intent.putExtra("subscription", user_amount3 + "");
                intent.putExtra("jifen", "0");
                startActivity(intent);
                break;
            case R.id.iv_anquan:
                showAnQuan();
                break;
            case R.id.tv_share:
                XueYiCheUtils.showShareAppCommon(App.context,JinXingActivity.this,"分享该行程","","","","");
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
                Intent intent = new Intent(JinXingActivity.this, JinjiPhoneActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ll_baojing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XueYiCheUtils.CallPhone(JinXingActivity.this, "拨打报警电话", "110");
            }
        });
        rl_xuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //web页
                Intent intent = new Intent(JinXingActivity.this, UrlActivity.class);
                intent.putExtra("url", "http://xueyiche.cn/xyc/instructions/safe.html");
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });

    }
}
