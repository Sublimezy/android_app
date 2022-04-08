package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderPracticeOkBean;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.submit.bean.DeleteIndentBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.PracticeWeiYueBean;
import com.yinglan.scrolllayout.ScrollLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2018/1/30.
 */
public class IndentDetailsOrderPractice extends BaseActivity implements View.OnClickListener, SensorEventListener {
    private ImageView ll_exam_back, tv_drivers_phone;
    private TextView tv_login_back, tv_date, tv_drivers_name, tv_drivers_type, tv_cartype;
    private LinearLayout ll_toushu, ll_type,ll_bottom;
    private ScrollLayout mScrollLayout;
    BitmapDescriptor mCurrentMarker;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;

    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    // UI相关
    boolean isFirstLoc = true; // 是否首次定位


    //开始时间
    private TextView tv_start_time;
    //共几小时
    private TextView tv_total_time;
    //结束时间
    private TextView tv_end_time;
    //车型
    private TextView tv_car_level;
    private Timer timer1;
    private TimerTask timerTask;
    //单价区间
    private TextView tv_indent_danjia;
    //总价区间
    private TextView tv_indent_total_money;
    //接送地址
    private TextView tv_indent_jie_location;
    private TextView tv_indent_song_location;
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
    //订单状态
    private TextView tv_indent_state;
    private String order_number;
    private String user_id;
    private String order_status;
    private int pay_price;
    private String track_status;
    private String remark;
    private TextView tv_jinji_phone, tv_time_go;
    private CircleImageView iv_drivers_head;
    private RelativeLayout rl_quxiao, rl_finish, rl_time_go;
    private ImageView iv_quxiao, iv_practice_finish, iv_practice_pingjia;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {

            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };
    private String driver_phone;

    @Override
    protected int initContentView() {
        return R.layout.indent_details_order_practice;
    }



    @Override
    protected void initView() {

        ll_exam_back = view.findViewById(R.id.indent_details_include).findViewById(R.id.iv_login_back);
        tv_login_back = (TextView) view.findViewById(R.id.indent_details_include).findViewById(R.id.tv_title);
//        ll_toushu = view.findViewById(R.id.indent_details_include).findViewById(R.id.ll_toushu);
        mScrollLayout = view.findViewById(R.id.scroll_down_layout);
        ll_type = view.findViewById(R.id.ll_type);

        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_total_time = (TextView) view.findViewById(R.id.tv_total_time);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        tv_indent_danjia = (TextView) view.findViewById(R.id.tv_indent_danjia);
        tv_car_level = (TextView) view.findViewById(R.id.tv_car_level);
        tv_indent_total_money = (TextView) view.findViewById(R.id.tv_indent_total_money);
        tv_indent_jie_location = (TextView) view.findViewById(R.id.tv_indent_jie_location);
        tv_indent_song_location = (TextView) view.findViewById(R.id.tv_indent_song_location);
        tv_indent_getcard_time = (TextView) view.findViewById(R.id.tv_indent_getcard_time);
        tv_indent_graduation_level = (TextView) view.findViewById(R.id.tv_indent_graduation_level);
        tv_indent_practice_experience = (TextView) view.findViewById(R.id.tv_indent_practice_experience);
        tv_indent_beizhu = (TextView) view.findViewById(R.id.tv_indent_beizhu);
        tv_indent_number = (TextView) view.findViewById(R.id.tv_indent_number);
        tv_indent_date = (TextView) view.findViewById(R.id.tv_indent_date);
        tv_indent_state = (TextView) view.findViewById(R.id.tv_indent_state);
        tv_jinji_phone = (TextView) view.findViewById(R.id.tv_jinji_phone);
        iv_drivers_head = view.findViewById(R.id.iv_drivers_head);
        tv_drivers_phone = view.findViewById(R.id.tv_drivers_phone);
        tv_drivers_name = view.findViewById(R.id.tv_drivers_name);
        tv_drivers_type = view.findViewById(R.id.tv_drivers_type);
        tv_cartype = view.findViewById(R.id.tv_cartype);
        rl_quxiao = view.findViewById(R.id.rl_quxiao);
        rl_finish = view.findViewById(R.id.rl_finish);
        ll_bottom = view.findViewById(R.id.ll_bottom);
        rl_time_go = view.findViewById(R.id.rl_time_go);
        iv_quxiao = view.findViewById(R.id.iv_quxiao);
        iv_practice_finish = view.findViewById(R.id.iv_practice_finish);
        iv_practice_pingjia = view.findViewById(R.id.iv_practice_pingjia);
        tv_time_go = view.findViewById(R.id.tv_time_go);
        user_id = PrefUtils.getString(App.context, "user_id", "");

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
        ll_exam_back.setOnClickListener(this);
        ll_toushu.setOnClickListener(this);
        tv_drivers_phone.setOnClickListener(this);
        iv_quxiao.setOnClickListener(this);
        iv_practice_finish.setOnClickListener(this);
        iv_practice_pingjia.setOnClickListener(this);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.getBackground().setAlpha(0);
    }

    private void getDataFromNet() {
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
                                            final OrderPracticeOkBean.ContentBean content = orderPracticeOkBean.getContent();
                                            if (content != null) {
                                                final String bz = content.getBz();
                                                final String down_card_time = content.getDown_card_time();
                                                final String end_school_level = content.getEnd_school_level();
                                                pay_price = content.getPay_price();
                                                final int hour_price = content.getHour_price();
                                                order_number = content.getOrder_number();
                                                final String order_status = content.getOrder_status();
                                                final String order_system_time = content.getOrder_system_time();
                                                final String practice_hours = content.getPractice_hours();
                                                final String car_level_detail = content.getCar_level_detail();
                                                final String traincar_experience = content.getTraincar_experience();
                                                final String get_on_address = content.getGet_on_address();
                                                final String get_down_address = content.getGet_down_address();
                                                IndentDetailsOrderPractice.this.order_status = content.getOrder_status();
                                                final String order_number = content.getOrder_number();
                                                final String true_phone = content.getTrue_phone();
                                                final String order_sort = content.getOrder_sort();
                                                final String start_time = content.getStart_time();
                                                final String end_time = content.getEnd_time();
                                                final String head_img = content.getHead_img();
                                                final String driver_name = content.getDriver_name();
                                                final String driving_year = content.getDriving_year();
                                                final String brand_name = content.getBrand_name();
                                                final String series_name = content.getSeries_name();
                                                final String hand_auto = content.getHand_auto();
                                                String latitude_driver = content.getLatitude_driver();
                                                String longitude_driver = content.getLongitude_driver();
                                                driver_phone = content.getDriver_phone();
                                                //1：教练点击开始；2：结束，默认：0
                                                track_status = content.getTrack_status();
                                                App.handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (!TextUtils.isEmpty(head_img)) {
                                                            Picasso.with(App.context).load(head_img).into(iv_drivers_head);
                                                        }
                                                        if (!TextUtils.isEmpty(driver_name)) {
                                                            tv_drivers_name.setText(driver_name);
                                                        }
                                                        if (!TextUtils.isEmpty(driving_year)) {
                                                            tv_drivers_type.setText(driving_year + "年驾龄");
                                                        }
                                                        if (!TextUtils.isEmpty(driver_name)) {
                                                            if ("0".equals(hand_auto)) {
                                                                tv_cartype.setText(brand_name + series_name + "   手动挡");
                                                            } else {
                                                                tv_cartype.setText(brand_name + series_name + "   自动挡");
                                                            }
                                                        }
                                                        /**
                                                         *   rl_quxiao = view.findViewById(R.id.rl_quxiao);
                                                         rl_finish = view.findViewById(R.id.rl_finish);
                                                         rl_time_go = view.findViewById(R.id.rl_time_go);
                                                         */
                                                        //0:待付款  1：进行中  2：已完成  4：取消订单 6:待接单 9：已接单
                                                        if (!TextUtils.isEmpty(order_status)) {
                                                            //支付1.进行中2.已完成3.待确认4.已取消6.待接单7.退款中9.已接单
                                                            if ("0".equals(order_status)) {
                                                                tv_indent_state.setText("待付款");
                                                            } else if ("1".equals(order_status)) {
                                                                tv_indent_state.setText("进行中");
                                                                ll_bottom.setVisibility(View.GONE);
                                                            }else if ("6".equals(order_status)) {
                                                                tv_indent_state.setText("进行中");
                                                                rl_quxiao.setVisibility(View.VISIBLE);
                                                                ll_bottom.setVisibility(View.VISIBLE);
                                                                rl_finish.setVisibility(View.GONE);
                                                            } else if ("9".equals(order_status)) {
                                                                tv_indent_state.setText("已接单");
                                                                rl_quxiao.setVisibility(View.VISIBLE);
                                                                ll_bottom.setVisibility(View.VISIBLE);
                                                                rl_finish.setVisibility(View.GONE);
                                                            } else if ("3".equals(order_status)) {
                                                                tv_indent_state.setText("待确认");
                                                                ll_bottom.setVisibility(View.VISIBLE);
                                                                rl_finish.setVisibility(View.VISIBLE);
                                                                rl_quxiao.setVisibility(View.GONE);

                                                            }
                                                        }

                                                        if (!TextUtils.isEmpty(start_time) && !TextUtils.isEmpty(end_time)) {
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

                                                        if (!TextUtils.isEmpty(order_sort)) {
                                                            if ("2".equals(order_sort)) {
                                                                ll_type.setVisibility(View.VISIBLE);
                                                            } else {
                                                                ll_type.setVisibility(View.GONE);
                                                            }
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
                                                            tv_indent_danjia.setText("" + hour_price+"/小时"+"  共" + practice_hours + "小时");
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
                                                        //意向练车项目
                                                        if (!TextUtils.isEmpty(true_phone)) {
                                                            tv_jinji_phone.setText(true_phone);
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
                        }

                        @Override
                        public void onResponse(Object response) {
                        }
                    });


            OkHttpUtils.post().url(AppUrl.WeiYue_GuiZe)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        PracticeWeiYueBean practiceWeiYueBean = JsonUtil.parseJsonToBean(string, PracticeWeiYueBean.class);
                        if (practiceWeiYueBean != null) {
                            PracticeWeiYueBean.ContentBean content = practiceWeiYueBean.getContent();
                            if (content != null) {
                                remark = content.getRemark();
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

    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("刷新张磊", msg)) {
            getDataFromNet();
        }

    }

    public void startClick() {
        timerTask = new TimerTask() {
            int cnt = 0;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_time_go.setText(getStringTime(cnt++));
                    }
                });
            }
        };
        timer1.schedule(timerTask, 0, 1000);
    }

    public void stopClick1(View view) {
        if (!timerTask.cancel()) {
            timerTask.cancel();
            timer1.cancel();
        }
    }

    private String getStringTime(int cnt) {
        int hour = cnt / 3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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
    protected void initData() {
        tv_login_back.setText("订单详情");
        ll_toushu.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        timer1 = new Timer();
        getDataFromNet();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
//            case R.id.ll_toushu:
//                XueYiCheUtils.CallPhone(IndentDetailsOrderPractice.this, "是否拨打投诉电话？", "0451-51068980");
//                break;
            case R.id.tv_drivers_phone:
                if (!TextUtils.isEmpty(driver_phone)) {
                    int length = driver_phone.length();
                    if (11 == length) {
                        XueYiCheUtils.CallPhone(IndentDetailsOrderPractice.this, "是否拨打教练电话？", driver_phone);
                    } else {
                        XueYiCheUtils.CallPhone(IndentDetailsOrderPractice.this, "是否拨打教练电话？", AES.decrypt(driver_phone));
                    }
                }

                break;
            case R.id.iv_quxiao:
                if (!TextUtils.isEmpty(order_status) && !TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number)) {
                    if ("0".equals(order_status) || "6".equals(order_status)) {
                        //代付款取消
                        cancelIndentFree(this, AppUrl.Delete_Indent_Practice, user_id, order_number);
                    } else {
                        //取消订单赔付违约金
                        cancelIndentMoney(order_number);
                    }
                }
                break;
            case R.id.iv_practice_finish:
                userEndPracticing();
                break;
        }
    }
    private void userEndPracticing() {
        String x = PrefUtils.getString(App.context, "x", "");
        String y = PrefUtils.getString(App.context, "y", "");
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.User_End_Practicing)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("last_user_longitude",x )
                    .addParams("last_user_latitude",y )
                    .addParams("order_number", TextUtils.isEmpty(order_number) ? "" : order_number)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                if (successDisCoverBackBean != null) {
                                    final int code = successDisCoverBackBean.getCode();
                                    if (!TextUtils.isEmpty("" + code)) {

                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (200 == code) {
                                                    finish();
                                                }else {
                                                    showToastShort("操作失败");
                                                }
                                            }
                                        });
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

    private void cancelIndentMoney(final String order_number) {
        View viewDia = LayoutInflater.from(App.context).inflate(R.layout.get_jifen_dialog, null);
        TextView tv_quxiao = (TextView) viewDia.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) viewDia.findViewById(R.id.tv_queren);
        TextView tv_content = (TextView) viewDia.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(remark)) {
            String br = remark.replaceAll("br", "\n");
            tv_content.setText(br);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(viewDia);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
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
                OkHttpUtils.post().url(AppUrl.Delete_Indent_Practice)
                        .addParams("device_id", LoginUtils.getId(IndentDetailsOrderPractice.this))
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
                                        final int code = deleteIndentBean.getCode();
                                        final String msg = deleteIndentBean.getMsg();
                                        if (!TextUtils.isEmpty("" + code)) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (200 == code) {
                                                        getDataFromNet();
                                                        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                                        dialog01.dismiss();
                                                        finish();
                                                    }

                                                }
                                            });
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
