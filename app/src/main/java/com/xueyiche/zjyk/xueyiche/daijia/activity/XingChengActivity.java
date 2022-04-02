package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.GoDaiJiaBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.LiJIBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.YuGuBean;
import com.xueyiche.zjyk.xueyiche.daijia.view.YuYueLinkagePicker;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.pay.bean.ZhiFuBaoBean;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PayUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/10/15.
 */
public class XingChengActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_login_back;
    private ImageView ll_exam_back;
    private String user_id;
    PopupWindow pop;
    LinearLayout ll_popup;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    // UI相关
    boolean isFirstLoc = true; // 是否首次定位
    private ImageView iv_anquan;
    private TextView tv_qidian, tv_mudi;
    private RadioButton rb_richang, rb_yuyue, rb_daijiao;
    private String jie_latitude;
    private String jie_longitude;
    private String jie_name;
    private String song_latitude;
    private String song_longitude;
    private String song_name;
    private ImageView iv_user;
    private String song_address;
    private String jie_address;
    private TextView tv_xiadan;
    private LinearLayout ll_richang, ll_yuyue, ll_daijiao;
    private TextView tv_qidian_yuyue, tv_qidian_daijiao, tv_choose_people;
    private TextView tv_mudi_yuyue, tv_mudi_daijiao;
    private TextView tv_xiadan_yuyue, tv_xiadan_daijiao;
    private LinearLayout ll_yuyue_time;
    private RelativeLayout rl_beizhu, rl_money, rl_money_yuyue;
    private TextView tv_yuyue_time, tv_choose_time;
    private TextView tv_daijiao;
    private TextView tv_money_yuyue;
    private TextView tv_money, tv_money_daijiao;
    private String area_id;
    private String order_distance = "";
    private String appointed_time = "";
    private String over_distance = "";
    private String amount = "";
    private String beizhu = "";
    private String youhui_id = "";
    private String user_phone;
    private TextView tv_beizhu;
    private String daijiao_phone = "";
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    public static XingChengActivity instance;
    private TextView tv_yh, tv_yh_yuyue, tv_yh_daijiao, tv_daijiao_shuoming,tv_choose_liji_daijia,tv_choose_daijiao_daijia;
    private String coupon_num;
    private String user_amount;
    private String user_amount3;
    private String pay_style;
    private String order_number;
    private boolean isYuyue;
    private String yc_yy;
    private String daijia_number;
    private String daijia_name;

    @Override
    protected int initContentView() {
        return R.layout.xingcheng_activity;
    }

    @Override
    protected void initView() {
        instance = this;
        EventBus.getDefault().register(this);
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_title);
        tv_login_back.setText("代驾");
        ll_exam_back = view.findViewById(R.id.title).findViewById(R.id.iv_login_back);
        iv_anquan = (ImageView) view.findViewById(R.id.iv_anquan);
        iv_user = (ImageView) view.findViewById(R.id.iv_user);
        rb_richang = view.findViewById(R.id.rb_richang);
        rb_yuyue = view.findViewById(R.id.rb_yuyue);
        rb_daijiao = view.findViewById(R.id.rb_daijiao);
        tv_daijiao_shuoming = view.findViewById(R.id.tv_daijiao_shuoming);
        tv_mudi = (TextView) view.findViewById(R.id.tv_mudi);
        tv_choose_people = (TextView) view.findViewById(R.id.tv_choose_people);
        tv_qidian = (TextView) view.findViewById(R.id.tv_qidian);
        tv_choose_liji_daijia = (TextView) view.findViewById(R.id.tv_choose_liji_daijia);
        tv_choose_daijiao_daijia = (TextView) view.findViewById(R.id.tv_choose_daijiao_daijia);
        tv_xiadan = (TextView) view.findViewById(R.id.tv_xiadan);
        tv_yh = (TextView) view.findViewById(R.id.tv_yh);
        tv_yh_yuyue = (TextView) view.findViewById(R.id.tv_yh_yuyue);
        tv_yh_daijiao = (TextView) view.findViewById(R.id.tv_yh_daijiao);
        tv_qidian_yuyue = (TextView) view.findViewById(R.id.tv_qidian_yuyue);
        tv_beizhu = (TextView) view.findViewById(R.id.tv_beizhu);
        tv_qidian_daijiao = (TextView) view.findViewById(R.id.tv_qidian_daijiao);
        ll_richang = (LinearLayout) view.findViewById(R.id.ll_richang);
        ll_yuyue = (LinearLayout) view.findViewById(R.id.ll_yuyue);
        ll_daijiao = (LinearLayout) view.findViewById(R.id.ll_daijiao);
        ll_yuyue_time = (LinearLayout) view.findViewById(R.id.ll_yuyue_time);
        tv_mudi_yuyue = (TextView) view.findViewById(R.id.tv_mudi_yuyue);
        tv_mudi_daijiao = (TextView) view.findViewById(R.id.tv_mudi_daijiao);
        tv_xiadan_yuyue = (TextView) view.findViewById(R.id.tv_xiadan_yuyue);
        tv_xiadan_daijiao = (TextView) view.findViewById(R.id.tv_xiadan_daijiao);
        tv_yuyue_time = (TextView) view.findViewById(R.id.tv_yuyue_time);
        tv_choose_time = (TextView) view.findViewById(R.id.tv_choose_time);
        tv_daijiao = (TextView) view.findViewById(R.id.tv_daijiao);
        tv_money_yuyue = (TextView) view.findViewById(R.id.tv_money_yuyue);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_money_daijiao = (TextView) view.findViewById(R.id.tv_money_daijiao);
        rl_money = (RelativeLayout) view.findViewById(R.id.rl_money);
        rl_money_yuyue = (RelativeLayout) view.findViewById(R.id.rl_money_yuyue);
        rl_beizhu = (RelativeLayout) view.findViewById(R.id.rl_beizhu);
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_yh.setOnClickListener(this);
        tv_yh_yuyue.setOnClickListener(this);
        tv_yh_daijiao.setOnClickListener(this);
        iv_anquan.setOnClickListener(this);
        tv_daijiao.setOnClickListener(this);
        iv_user.setOnClickListener(this);
        rb_richang.setOnClickListener(this);
        rb_yuyue.setOnClickListener(this);
        rb_daijiao.setOnClickListener(this);
        tv_choose_time.setOnClickListener(this);
        tv_qidian.setOnClickListener(this);
        tv_money_yuyue.setOnClickListener(this);
        tv_choose_liji_daijia.setOnClickListener(this);
        tv_choose_daijiao_daijia.setOnClickListener(this);
        tv_qidian.setOnClickListener(this);
        tv_xiadan.setOnClickListener(this);
        tv_mudi.setOnClickListener(this);
        tv_mudi_yuyue.setOnClickListener(this);
        tv_qidian_yuyue.setOnClickListener(this);
        tv_choose_people.setOnClickListener(this);
        ll_yuyue_time.setOnClickListener(this);
        tv_xiadan_yuyue.setOnClickListener(this);
        tv_xiadan_daijiao.setOnClickListener(this);
        rl_beizhu.setOnClickListener(this);
        tv_money.setOnClickListener(this);
        tv_money_daijiao.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("支付成功", msg)) {
            if ("daijia_liji".equals(pay_style)) {
                PrefUtils.putString(App.context, "hongbao_show", "1");
                Intent intent = new Intent(App.context, WaitActivity.class);
                intent.putExtra("order_number", order_number);
                startActivity(intent);
                finish();
                return;
            } else if ("daijia_yuyue".equals(pay_style)) {
                PrefUtils.putString(App.context, "hongbao_show", "1");
                Intent intent = new Intent(App.context, WaitYuYueActivity.class);
                intent.putExtra("order_number", order_number);
                startActivity(intent);
                finish();
                return;
            }
        }
    }

    public void goDaiJia() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        OkHttpUtils.post().url(AppUrl.Get_Number_UserId)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    GoDaiJiaBean goDaiJiaBean = JsonUtil.parseJsonToBean(string, GoDaiJiaBean.class);
                    if (goDaiJiaBean != null) {
                        final int code = goDaiJiaBean.getCode();
                        if (200 == code) {
                            final GoDaiJiaBean.ContentBean content = goDaiJiaBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
                                        String order_number = content.getOrder_number();
                                        String status = content.getStatus();
                                        String order_type = content.getOrder_type();
                                        if ("0".equals(status)) {
                                            if ("0".equals(order_type)) {
                                                Intent intent = new Intent(App.context, WaitActivity.class);
                                                intent.putExtra("order_number", order_number);
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(App.context, WaitYuYueActivity.class);
                                                intent.putExtra("order_number", order_number);
                                                startActivity(intent);
                                            }
                                        } else if ("1".equals(status) || "2".equals(status)) {
                                            Intent intent = new Intent(App.context, JieDanActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            startActivity(intent);
                                        } else if ("3".equals(status)) {
                                            Intent intent = new Intent(App.context, JinXingActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            startActivity(intent);
                                        } else if ("4".equals(status)) {
                                            Intent intent = new Intent(App.context, JieDanActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            startActivity(intent);
                                        } else {
                                            if ("1".equals(yc_yy)) {
                                                sendYuYueOrder();
                                            } else if ("0".equals(yc_yy)) {
                                                sendOrder();
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

            }

            @Override
            public void onResponse(Object response) {

            }
        });
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        //预估费
        Intent intent = new Intent(this, YuGuFeiActivity.class);
        //优惠券
        Intent intent5 = new Intent(XingChengActivity.this, YouHuiQuanActivity.class);
        Intent intent6 = new Intent(XingChengActivity.this, ChooseDaiJiaoActivity.class);
        switch (v.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.iv_anquan:
                //打开安全中心
                showAnQuan();
                break;
            case R.id.tv_choose_liji_daijia:
                startActivityForResult(intent6, 555);
                break;
            case R.id.tv_choose_daijiao_daijia:
                startActivityForResult(intent6, 555);
                break;
            case R.id.tv_yh:
                //优惠券
                if (!"0".equals(coupon_num)) {
                    startActivityForResult(intent5, 222);
                }
                break;
            case R.id.tv_yh_yuyue:
                //优惠券
                if (!"0".equals(coupon_num)) {
                    startActivityForResult(intent5, 222);
                }
                break;
            case R.id.tv_yh_daijiao:
                //优惠券
                if (!"0".equals(coupon_num)) {
                    startActivityForResult(intent5, 222);
                }
                break;
            case R.id.ll_yuyue_time:
                //选择预约时间
                startTimePicker();
                break;
            case R.id.tv_choose_people:
                Intent intent9 = new Intent(App.context, DaiJiaoActivity.class);
                startActivityForResult(intent9, 333);
                break;
            case R.id.tv_choose_time:
                //选择预约时间
                startTimePicker();
                break;
            case R.id.rl_beizhu:
                //填写备注
                Intent intent3 = new Intent(XingChengActivity.this, LiuYanActivity.class);
                startActivityForResult(intent3, 333);
                break;
            case R.id.tv_money:
                //价格明细232236 236967
                intent.putExtra("money", amount);
                intent.putExtra("distance", order_distance);
                intent.putExtra("over_distance", over_distance);
                intent.putExtra("user_amount3", user_amount3);
                intent.putExtra("user_amount", user_amount);
                startActivity(intent);
                break;
            case R.id.tv_money_yuyue:
                //价格明细
                intent.putExtra("money", amount);
                intent.putExtra("distance", order_distance);
                intent.putExtra("over_distance", over_distance);
                intent.putExtra("user_amount3", user_amount3);
                intent.putExtra("user_amount", user_amount);
                startActivity(intent);
                break;
            case R.id.tv_money_daijiao:
                //价格明细
                intent.putExtra("money", amount);
                intent.putExtra("distance", order_distance);
                intent.putExtra("over_distance", over_distance);
                intent.putExtra("user_amount3", user_amount3);
                intent.putExtra("user_amount", user_amount);
                startActivity(intent);
                break;
            case R.id.tv_daijiao:
                //代叫服务
                Intent intent4 = new Intent(this, DaiJiaoActivity.class);
                startActivityForResult(intent4, 444);
                break;
            case R.id.tv_xiadan:
                //一键下单
                //支付
                goDaiJia();
                break;
            case R.id.tv_xiadan_yuyue:
                //支付
                if (TextUtils.isEmpty(appointed_time)) {
                    showToastShort("请选择预约时间");
                    return;
                }
                goDaiJia();
                break;
            case R.id.tv_xiadan_daijiao:
                //支付
                String s = tv_choose_time.getText().toString();
                String sPeople = tv_choose_people.getText().toString();
                isYuyue = s.contains("预约时间");
                boolean isPeople = sPeople.contains("选择乘车人");
                if (isPeople) {
                    Toast.makeText(XingChengActivity.this, "请选择乘车人", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendOrderDaijiao();
                break;
            case R.id.rb_richang:
                ll_richang.setVisibility(View.VISIBLE);
                ll_yuyue.setVisibility(View.GONE);
                ll_daijiao.setVisibility(View.GONE);
                break;
            case R.id.rb_yuyue:
                ll_richang.setVisibility(View.GONE);
                ll_yuyue.setVisibility(View.VISIBLE);
                ll_daijiao.setVisibility(View.GONE);
                break;
            case R.id.rb_daijiao:
                ll_daijiao.setVisibility(View.VISIBLE);
                ll_yuyue.setVisibility(View.GONE);
                ll_richang.setVisibility(View.GONE);
                break;
            case R.id.iv_user:
                break;
        }
    }

    private void sendOrderDaijiao() {
        OkHttpUtils.post().url(AppUrl.insertSubstituteOrderDJ)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .addParams("true_phone", TextUtils.isEmpty(daijiao_phone) ? AES.decrypt(user_phone) : daijiao_phone)
                .addParams("appointed_time", !TextUtils.isEmpty(appointed_time) ? appointed_time : "")
                .addParams("get_on_name", jie_name)
                .addParams("get_on_address", jie_address)
                .addParams("on_longitude", jie_longitude)
                .addParams("on_latitude", jie_latitude)
                .addParams("get_down_name", song_name)
                .addParams("get_down_address", song_address)
                .addParams("down_longitude", song_longitude)
                .addParams("down_latitude", song_latitude)
                .addParams("order_distance", order_distance)
                .addParams("talk_to_driver", beizhu)
                .addParams("area_id", area_id)
                .addParams("coupon_id", youhui_id)
                .addParams("job_number", !TextUtils.isEmpty(daijia_number)?daijia_number:"")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    LiJIBean liJIBean = JsonUtil.parseJsonToBean(string, LiJIBean.class);
                    if (liJIBean != null) {
                        final int code = liJIBean.getCode();
                        final String msg = liJIBean.getMsg();

                        final LiJIBean.ContentBean content = liJIBean.getContent();
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showToastShort(msg);
                                if (200 == code) {
                                    order_number = content.getOrder_number();
                                    String user_amount = content.getUser_amount();
                                    double v = Double.parseDouble(user_amount);
                                    if (isYuyue) {
                                        if (v > 0) {
                                            showPopupWindow(order_number, user_amount + "", "daijia_liji");
                                            ll_popup.startAnimation(AnimationUtils.loadAnimation(
                                                    XingChengActivity.this, R.anim.activity_translate_in));
                                            pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                                        } else {
                                            Intent intent = new Intent(App.context, WaitActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {

                                        if (v > 0) {
                                            showPopupWindow(order_number, user_amount + "", "daijia_yuyue");
                                            ll_popup.startAnimation(AnimationUtils.loadAnimation(
                                                    XingChengActivity.this, R.anim.activity_translate_in));
                                            pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                                        } else {
                                            Intent intent = new Intent(App.context, WaitYuYueActivity.class);
                                            intent.putExtra("order_number", order_number);
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

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }

    private void sendYuYueOrder() {
        OkHttpUtils.post().url(AppUrl.YuYue_Order)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .addParams("true_phone", TextUtils.isEmpty(daijiao_phone) ? AES.decrypt(user_phone) : daijiao_phone)
                .addParams("appointed_time", appointed_time)
                .addParams("get_on_name", jie_name)
                .addParams("get_on_address", jie_address)
                .addParams("on_longitude", jie_longitude)
                .addParams("on_latitude", jie_latitude)
                .addParams("get_down_name", song_name)
                .addParams("get_down_address", song_address)
                .addParams("down_longitude", song_longitude)
                .addParams("down_latitude", song_latitude)
                .addParams("order_distance", order_distance)
                .addParams("talk_to_driver", beizhu)
                .addParams("area_id", area_id)
                .addParams("coupon_id", youhui_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    LiJIBean liJIBean = JsonUtil.parseJsonToBean(string, LiJIBean.class);
                    if (liJIBean != null) {
                        final int code = liJIBean.getCode();
                        final String msg = liJIBean.getMsg();

                        final LiJIBean.ContentBean content = liJIBean.getContent();
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showToastShort(msg);
                                if (200 == code) {
                                    order_number = content.getOrder_number();
                                    String user_amount = content.getUser_amount();
                                    double v = Double.parseDouble(user_amount);

                                    if (v > 0) {
                                        showPopupWindow(order_number, user_amount + "", "daijia_yuyue");
                                        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                                                XingChengActivity.this, R.anim.activity_translate_in));
                                        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                                    } else {
                                        Intent intent = new Intent(App.context, WaitYuYueActivity.class);
                                        intent.putExtra("order_number", order_number);
                                        startActivity(intent);
                                        finish();
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

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    private void sendOrder() {
        OkHttpUtils.post().url(AppUrl.Now_Order)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .addParams("true_phone", TextUtils.isEmpty(daijiao_phone) ? AES.decrypt(user_phone) : daijiao_phone)
                .addParams("get_on_name", jie_name)
                .addParams("get_on_address", jie_address)
                .addParams("on_longitude", jie_longitude)
                .addParams("on_latitude", jie_latitude)
                .addParams("get_down_name", song_name)
                .addParams("get_down_address", song_address)
                .addParams("down_longitude", song_longitude)
                .addParams("down_latitude", song_latitude)
                .addParams("order_distance", order_distance)
                .addParams("area_id", area_id)
                .addParams("coupon_id", youhui_id)
                .addParams("job_number", !TextUtils.isEmpty(daijia_number)?daijia_number:"")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    LiJIBean liJIBean = JsonUtil.parseJsonToBean(string, LiJIBean.class);
                    if (liJIBean != null) {
                        final int code = liJIBean.getCode();
                        final String msg = liJIBean.getMsg();

                        final LiJIBean.ContentBean content = liJIBean.getContent();
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (200 == code) {
                                    order_number = content.getOrder_number();
                                    String user_amount = content.getUser_amount();
                                    double v = Double.parseDouble(user_amount);
                                    if (v > 0) {
                                        showPopupWindow(order_number, user_amount + "", "daijia_liji");
                                        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                                                XingChengActivity.this, R.anim.activity_translate_in));
                                        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                                    } else {
                                        Intent intent = new Intent(App.context, WaitActivity.class);
                                        intent.putExtra("order_number", order_number);
                                        startActivity(intent);
                                        finish();
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

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    public void showPopupWindow(final String order_number, final String subscription, final String pay_style) {
        pop = new PopupWindow(XingChengActivity.this);
        View view = getLayoutInflater().inflate(R.layout.pop_pay_void_layout, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(false);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        RelativeLayout rl_pay_zhifubao = (RelativeLayout) view.findViewById(R.id.rl_pay_zhifubao);
        RelativeLayout rl_pay_wechat = (RelativeLayout) view.findViewById(R.id.rl_pay_wechat);
        ImageView iv_pay_exit = view.findViewById(R.id.iv_pay_exit);
        final ImageView iv_wecaht_select = view.findViewById(R.id.iv_wecaht_select);
        final ImageView iv_zhifubao_select = view.findViewById(R.id.iv_zhifubao_select);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        iv_pay_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        rl_pay_zhifubao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (AppUtils.isFastClick()) {
                    iv_zhifubao_select.setImageResource(R.mipmap.daijia_pay_ok);
                    iv_wecaht_select.setImageResource(R.mipmap.daijia_pay_no);
                    zfb(order_number, subscription, pay_style);
                    pop.dismiss();
                    ll_popup.clearAnimation();
                }

            }
        });
        rl_pay_wechat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //微信支付
                if (AppUtils.isFastClick()) {
                    iv_zhifubao_select.setImageResource(R.mipmap.daijia_pay_no);
                    iv_wecaht_select.setImageResource(R.mipmap.daijia_pay_ok);
                    if (XueYiCheUtils.isWeixinAvilible(XingChengActivity.this)) {
                        PayUtils.wx(XingChengActivity.this, AppUrl.Pay_Order_One, order_number, user_id);
                    } else {
                        showToastShort("目前您的微信版本过低或未安装微信，需要安装微信才能使用");
                    }
                    pop.dismiss();
                    ll_popup.clearAnimation();
                }

            }
        });
    }

    private void zfb(final String order_number, final String subscription, final String pay_style) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.Pay_Order_One)
                    .addParams("order_number", order_number)
                    .addParams("device_id", LoginUtils.getId(XingChengActivity.this))
                    .addParams("user_id", user_id)
                    .addParams("pay_type_id", "1")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        ZhiFuBaoBean zhiFuBaoBean = JsonUtil.parseJsonToBean(string, ZhiFuBaoBean.class);
                        if (zhiFuBaoBean != null) {
                            int code = zhiFuBaoBean.getCode();
                            if (200 == code) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        PayUtils.zfb(string, XingChengActivity.this, XingChengActivity.this, "0", subscription, order_number, pay_style);
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

    public void startTimePicker() {
        ArrayList<String> firstList = new ArrayList<String>();
        ArrayList<ArrayList<String>> secondList = new ArrayList<ArrayList<String>>();
        ArrayList<String> secondListItem = new ArrayList<String>();
        ArrayList<String> thirdListItem = new ArrayList<String>();
        ArrayList<String> secondListItem_jintian = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        final int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        c.add(Calendar.DAY_OF_MONTH, 1);
        int month1 = c.get(Calendar.MONTH) + 1;
        int day1 = c.get(Calendar.DATE);
        String mday = "";
        String jday = "";
        if ((day1 < 9)) {
            mday = "0" + day1;
        } else {
            mday = "" + day1;
        }
        if ((day < 9)) {
            jday = "0" + day;
        } else {
            jday = "" + day;
        }
        firstList.add(month + "月" + jday + "日");
        firstList.add(month1 + "月" + mday + "日");
        int i1 = minute + 30;
        if (i1 > 50) {
            for (int i = hour + 1; i < 24; i++) {
                secondListItem_jintian.add(DateUtils.fillZero(i));
            }
        } else {
            for (int i = hour; i < 24; i++) {
                secondListItem_jintian.add(DateUtils.fillZero(i));
            }
        }
        for (int i = 0; i < 24; i++) {
            secondListItem.add(DateUtils.fillZero(i));
        }
        secondList.add(secondListItem_jintian);//对应今天
        secondList.add(secondListItem);//对应明天
        ArrayList<ArrayList<String>> thirdList = new ArrayList<ArrayList<String>>();
        ArrayList<String> thirdListItem_jintian = new ArrayList<>();
        int fen = 0;
        if (minute > 0 && minute <= 10) {
            fen = 4;
        }
        if (minute > 10 && minute <= 20) {
            fen = 5;
        }
        if (minute > 20 && minute <= 30) {
            fen = 0;
        }
        if (minute > 30 && minute <= 40) {
            fen = 1;
        }
        if (minute > 40 && minute <= 50) {
            fen = 2;
        }
        if (minute > 50 && minute <= 60) {
            fen = 3;
        }
        for (int i = fen; i < 6; i++) {
            thirdListItem_jintian.add(i + "0");
        }
        thirdListItem.add("00");
        thirdListItem.add("10");
        thirdListItem.add("20");
        thirdListItem.add("30");
        thirdListItem.add("40");
        thirdListItem.add("50");
        thirdList.add(thirdListItem_jintian);//对应今天
        thirdList.add(thirdListItem);//对应明天
        final YuYueLinkagePicker picker = new YuYueLinkagePicker(this, firstList, secondList, thirdList);
        picker.setTitleText("请选择预约时间");
        picker.setTitleTextSize(20);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));
        picker.setOnLinkageListener(new YuYueLinkagePicker.OnLinkageListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                //时间格式:2016-11-08 08:00:00

                tv_yuyue_time.setText(first + second + ":" + third);
                tv_choose_time.setText(first + second + ":" + third + "  ");
                tv_choose_daijiao_daijia.setVisibility(View.GONE);
                tv_xiadan_daijiao.setText("预约下单");
                picker.setSelectedItem(first, second, third);
                String date;
                if (first.contains("今天")) {
                    date = first.replace("今天", "");
                } else {
                    date = first;
                }
                String substring = date.substring(0, 1);
                if (!"1".equals(substring)) {
                    date = "0" + date;
                }
                String s = year + "年" + date + second + ":" + third + ":00";
                String s1 = s.replace("年", "-");
                String s2 = s1.replace("月", "-");
                appointed_time = s2.replace("日", " ");
                getMoney();
                picker.dismiss();
            }
        });
        picker.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null) {
            Bundle extras = data.getExtras();
            switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
                case 222:
                    String coupon_id = extras.getString("coupon_id");
                    if (!TextUtils.isEmpty(coupon_id)) {
                        youhui_id = coupon_id;
                        getMoney();
                    }
                    break;
                case 333:
                    String bz = extras.getString("beizhu");
                    if (!TextUtils.isEmpty(bz)) {
                        beizhu = bz;
                        tv_beizhu.setText(bz);
                    }
                    break;
                case 444:
                    daijiao_phone = extras.getString("daijiao_phone");
                    String daijiao_name = extras.getString("daijiao_name");
                    if (!TextUtils.isEmpty(daijiao_phone)) {
                        tv_choose_people.setText(daijiao_phone + "  ");
                    } else {
                        tv_choose_people.setText("选择乘车人  ");
                    }
                    break;
                case 555:
                    daijia_number = extras.getString("daijia_number");
                    daijia_name = extras.getString("daijia_name");
                    if (!TextUtils.isEmpty(daijia_number)&&!TextUtils.isEmpty(daijia_name)) {
                        tv_choose_liji_daijia.setText(daijia_name + "  "+daijia_number);
                        tv_choose_daijiao_daijia.setText(daijia_name + "  "+daijia_number);
                        tv_choose_liji_daijia.setTextColor(getResources().getColor(R.color._4A4A4A));
                        tv_choose_daijiao_daijia.setTextColor(getResources().getColor(R.color._4A4A4A));
                    } else {
                        tv_choose_liji_daijia.setText("选择代驾员   >");
                        tv_choose_daijiao_daijia.setText("选择代驾员   >");
                    }
                    break;
            }
        }


    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        user_id = PrefUtils.getString(App.context, "user_id", "");
        user_phone = PrefUtils.getString(App.context, "user_phone", "");
        area_id = PrefUtils.getString(this, "area_id", "");
        jie_address = intent.getStringExtra("jie_address");
        jie_name = intent.getStringExtra("jie_name");
        jie_latitude = intent.getStringExtra("jie_latitude");
        jie_longitude = intent.getStringExtra("jie_longitude");
        song_address = intent.getStringExtra("song_address");
        song_name = intent.getStringExtra("song_name");
        song_latitude = intent.getStringExtra("song_latitude");
        song_longitude = intent.getStringExtra("song_longitude");
        appointed_time = intent.getStringExtra("appointed_time");
        daijiao_phone = intent.getStringExtra("daijiao_phone");
        String yuyue_time = intent.getStringExtra("yuyue_time");
        String daijiao_time = intent.getStringExtra("daijiao_time");
        if (!TextUtils.isEmpty(daijiao_phone)) {
            tv_choose_people.setText(daijiao_phone + "  ");
        } else {
            tv_choose_people.setText("选择乘车人  ");
        }
        if (!TextUtils.isEmpty(yuyue_time)) {
            tv_yuyue_time.setText(yuyue_time);
        } else {
            tv_yuyue_time.setText("预约出发时间");
        }
        if (!daijiao_time.contains("预约")) {
            tv_choose_time.setText("  " + daijiao_time);
            tv_xiadan_daijiao.setText("预约下单");
        } else {
            tv_xiadan_daijiao.setText("立即下单");

        }
        //rb_richang, rb_yuyue, rb_daijiao
        yc_yy = intent.getStringExtra("yc_yy");
        if ("1".equals(yc_yy)) {

            ll_richang.setVisibility(View.GONE);
            ll_yuyue.setVisibility(View.VISIBLE);
            ll_daijiao.setVisibility(View.GONE);
            pay_style = "daijia_yuyue";
            rb_yuyue.setChecked(true);
        } else if ("0".equals(yc_yy)) {
            ll_richang.setVisibility(View.VISIBLE);
            ll_yuyue.setVisibility(View.GONE);
            ll_daijiao.setVisibility(View.GONE);
            pay_style = "daijia_liji";
            rb_richang.setChecked(true);
        } else if ("2".equals(yc_yy)) {
            ll_richang.setVisibility(View.GONE);
            ll_yuyue.setVisibility(View.GONE);
            ll_daijiao.setVisibility(View.VISIBLE);
            if (!daijiao_time.contains("预约")) {
                tv_choose_time.setText(daijiao_time + "  ");
                pay_style = "daijia_yuyue";
                tv_xiadan_daijiao.setText("预约下单");
            } else {
                tv_xiadan_daijiao.setText("立即下单");
                pay_style = "daijia_liji";
            }
            rb_daijiao.setChecked(true);
        }
        tv_qidian.setText(jie_name);
        tv_qidian_yuyue.setText(jie_name);
        tv_qidian_daijiao.setText(jie_name);
        tv_mudi.setText(song_name);
        tv_mudi_yuyue.setText(song_name);
        tv_mudi_daijiao.setText(song_name);

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
                Intent intent = new Intent(XingChengActivity.this, JinjiPhoneActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ll_baojing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XueYiCheUtils.CallPhone(XingChengActivity.this, "拨打报警电话", "110");
            }
        });
        rl_xuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //web页
                Intent intent = new Intent(XingChengActivity.this, UrlActivity.class);
                intent.putExtra("url", "http://xueyiche.cn/xyc/instructions/safe.html");
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });

    }

    public void getMoney() {
        showProgressDialog(false);
        OkHttpUtils.post()
                .url(AppUrl.YuGuFei)
                .addParams("order_distance", order_distance)
                //2019-09-12 22:10:10
                .addParams("appointed_time", appointed_time)
                .addParams("user_id", user_id)
                .addParams("area_id", area_id)
                .addParams("coupon_id", youhui_id)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            final YuGuBean yuGuBean = JsonUtil.parseJsonToBean(string, YuGuBean.class);
                            if (yuGuBean != null) {
                                int code = yuGuBean.getCode();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        final YuGuBean.ContentBean content = yuGuBean.getContent();
                                        if (content != null) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    amount = content.getAmount();
                                                    String coupon_offset_amount = content.getCoupon_offset_amount();
                                                    over_distance = content.getOver_distance();
                                                    order_distance = content.getOrder_distance();
                                                    coupon_num = content.getCoupon_num();
                                                    user_amount = content.getUser_amount();
                                                    user_amount3 = content.getUser_amount3();

                                                    if (!TextUtils.isEmpty(amount)) {
                                                        rl_money_yuyue.setVisibility(View.VISIBLE);
                                                        rl_money.setVisibility(View.VISIBLE);
                                                        double v = Double.parseDouble(coupon_offset_amount);
                                                        if (v > 0) {
                                                            tv_money_yuyue.setText(amount + "(已抵扣" + coupon_offset_amount + "元)");
                                                            tv_money.setText(amount + "(已抵扣" + coupon_offset_amount + "元)");
                                                            tv_money_daijiao.setText(amount + "(已抵扣" + coupon_offset_amount + "元)");
                                                        } else {
                                                            tv_money_yuyue.setText(amount);
                                                            tv_money.setText(amount);
                                                            tv_money_daijiao.setText(amount);
                                                        }

                                                    }
                                                    if (!TextUtils.isEmpty(coupon_num)) {
                                                        if ("0".equals(coupon_num)) {
                                                            tv_yh.setText("无可用优惠券");
                                                            tv_yh_yuyue.setText("无可用优惠券");
                                                            tv_yh_daijiao.setText("无可用优惠券");
                                                        } else {
                                                            Drawable drawable = getResources().getDrawable(R.mipmap.dj_yhjy_pc);// 找到资源图片
                                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
                                                            tv_yh.setText("有" + coupon_num + "张优惠券可用 >");
                                                            tv_yh_daijiao.setText("有" + coupon_num + "张优惠券可用 >");
                                                            tv_yh.setTextColor(getResources().getColor(R.color.colorOrange));
                                                            tv_yh_yuyue.setTextColor(getResources().getColor(R.color.colorOrange));
                                                            tv_yh_daijiao.setTextColor(getResources().getColor(R.color.colorOrange));
                                                            tv_yh_yuyue.setText("有" + coupon_num + "张优惠券可用 >");
                                                            tv_yh.setCompoundDrawables(drawable, null, null, null);
                                                            tv_yh_yuyue.setCompoundDrawables(drawable, null, null, null);
                                                            tv_yh_daijiao.setCompoundDrawables(drawable, null, null, null);
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
