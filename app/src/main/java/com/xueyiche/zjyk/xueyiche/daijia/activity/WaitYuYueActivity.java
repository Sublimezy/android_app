package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DatePanDuanUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/29.
 */
public class WaitYuYueActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_quxia, tv_date,tv_time_yuyue,tv_money,tv_qi,tv_zhong,tv_siji_number,tv_jishi,tv_isToday;
    private String user_id;
    private String order_number;
    private long tiam_hm_c;
    private Timer mTimer2;
    private TimerTask mTask2;
    public static WaitYuYueActivity instance;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    BitmapDescriptor mCurrentMarker;
    private ImageView iv_back;
    private String order_time1;
    // UI相关
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    @Override
    protected int initContentView() {
        return R.layout.wait_yuyue_activity;
    }

    @Override
    protected void initView() {
        instance = this;
        AppUtils.addActivity(this);
        iv_back = view.findViewById(R.id.iv_back);
        tv_quxia = view.findViewById(R.id.tv_quxia);
        tv_date = view.findViewById(R.id.tv_date);
        tv_time_yuyue = view.findViewById(R.id.tv_time_yuyue);
        tv_isToday = view.findViewById(R.id.tv_isToday);




    }


    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新代驾", msg)) {
            Intent intent = new Intent(this,JieDanActivity.class);
            intent.putExtra("order_number",order_number);
            startActivity(intent);
            finish();
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
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }
    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_quxia.setOnClickListener(this);
    }
    @Override
    protected void initData() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        order_number = getIntent().getStringExtra("order_number");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_quxia:
                //取消订单
                Intent intent = new Intent(this,LiYouActivity.class);
                intent.putExtra("order_number",order_number);
                intent.putExtra("type","WaitYuYue");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    private Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
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
                        int code = orderInfoBean.getCode();
                        if (200 == code) {
                            final OrderInfoBean.ContentBean content = orderInfoBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                String down_longitude = content.getDown_longitude();
                                                String down_latitude = content.getDown_latitude();
                                                String on_latitude = content.getOn_latitude();
                                                String on_longitude = content.getOn_longitude();
                                                order_time1 = content.getOrder_time1();
                                                String appointed_time = content.getAppointed_time();
                                                if (!TextUtils.isEmpty(appointed_time)) {

                                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                                    Date date;
                                                    try {
                                                        date = format.parse(appointed_time);
                                                        SimpleDateFormat sdf =   new SimpleDateFormat( "MM月dd日" );
                                                        SimpleDateFormat sdf_time =   new SimpleDateFormat("HH:mm" );
                                                        String sDate = sdf.format(date);
                                                        String sTime = sdf_time.format(date);
                                                        if (!TextUtils.isEmpty(sDate)) {
                                                            tv_date.setText(sDate);
                                                        }
                                                        if (!TextUtils.isEmpty(sTime)) {
                                                            tv_time_yuyue.setText(sTime);
                                                        }
                                                        Long timestamp = DatePanDuanUtils.getTimestamp(appointed_time);
                                                        String today = DatePanDuanUtils.getToday(""+timestamp);
                                                        tv_isToday.setText(today);

                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                                String x = PrefUtils.getString(App.context, "x", "");
                                                String y = PrefUtils.getString(App.context, "y", "");
                                                double la = Double.parseDouble(y);
                                                double lo = Double.parseDouble(x);
                                                View inflate = LayoutInflater.from(App.context).inflate(R.layout.mark_view_layout, null);
                                                Bitmap bitmapFromView = getBitmapFromView(inflate);
                                                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmapFromView);
                                                View viewTop = LayoutInflater.from(App.context).inflate(R.layout.map_top_bg, null);
                                                // 显示 InfoWindow, 该接口会先隐藏其他已添加的InfoWindow, 再添加新的InfoWindow

                                                final TextView tv_time = (TextView) viewTop.findViewById(R.id.tv_time);
                                                if (mTimer2 == null && mTask2 == null) {
                                                    mTimer2 = new Timer();
                                                    mTask2 = new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if (!TextUtils.isEmpty(order_time1)) {
                                                                        //下单时间
                                                                        long stringToHm = DateUtils.getStringToHm(order_time1);
                                                                        //当前时间
                                                                        long time = System.currentTimeMillis();
                                                                        tiam_hm_c = time - stringToHm;
                                                                        long hours = tiam_hm_c / (1000 * 60 * 60);
                                                                        long minutes = (tiam_hm_c - hours * (1000 * 60 * 60)) / (1000 * 60);
                                                                        long miao = (tiam_hm_c - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
                                                                        String diffTime = "";
                                                                        if (miao < 10 && minutes < 10) {
                                                                            diffTime = "0" + minutes + ":0" + miao;
                                                                        } else if (miao > 10 && minutes < 10) {
                                                                            diffTime = "0" + minutes + ":" + miao;
                                                                        } else if (miao < 10 && minutes > 10) {
                                                                            diffTime = minutes + ":0" + miao;
                                                                        } else if (miao > 10 && minutes > 10) {
                                                                            diffTime = minutes + ":" + miao;
                                                                        }
                                                                        tv_time.setText(diffTime);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    };
                                                    mTimer2.schedule(mTask2, 0, 1000);
                                                }

                                            }
                                        });
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
    protected void onResume() {
        super.onResume();
//        String hongbao_show = PrefUtils.getString(App.context, "hongbao_show", "2");
//        if (!TextUtils.isEmpty(hongbao_show)) {
//            if ("1".equals(hongbao_show)) {
//                showDia();
//            }
//        }
    }

    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public String formatTime(long millisecond) {
        int minute;//分钟
        int second;//秒数
        String time="";
        long hours = millisecond / (1000 * 60 * 60);
        minute = (int) ((millisecond / 1000) / 60);
        second = (int) ((millisecond / 1000) % 60);
        if (minute < 10) {
            if (second < 10) {
                time= "0" + minute + "分0" + second+"秒";
            } else {
                time= "0" + minute + "分" + second+"秒";
            }
        }else {
            if (second < 10) {
                time= minute + "分:" + "0" + second+"秒";
            } else {
                time= minute + "分" + second+"秒";
            }
        }
        if (hours!=0) {
            if (second < 10) {
                time=  "0"+hours+"时"+time;
            } else {
                time=  hours+"时"+time;
            }
        }
        return time;
    }

}
