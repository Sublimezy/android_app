package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.route.DriveRouteResult;
import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.IndentContentBean;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.WaveMapUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/19.
 */
public class WaitActivity extends BaseMapActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private TimerTask timerTask;
    private Timer timer1;
    public static WaitActivity instance;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getDataFromNet();
                    break;
                default:
                    break;
            }
        }


    };
    private String order_sn;
   private Timer timer;
    @Override
    protected int initContentView() {
        return R.layout.wait_activity;
    }

    private void getDataFromNet() {
        Map<String, String> params = new HashMap<>();
        params.put("order_sn", "" + order_sn);
        MyHttpUtils.postHttpMessage(AppUrl.orderDetails, params, IndentContentBean.class, new RequestCallBack<IndentContentBean>() {
            @Override
            public void requestSuccess(IndentContentBean json) {
                if (1 == json.getCode()) {
                    IndentContentBean.DataBean data = json.getData();
                    int order_status = data.getOrder_status();

                    long timecurrentTimeMillis = System.currentTimeMillis();
                    String da =""+ timecurrentTimeMillis;
                    String substring = da.substring(0, 10);
                    String createtime = data.getCreatetime();
                    Log.e("kongxinrui",""+createtime);
                    Log.e("kongxinrui",""+substring);
                    Log.e("timecurrentTimeMillis",""+createtime);
                    long l = Long.parseLong(substring);
                    long l1 = Long.parseLong(createtime);
                    timer1 = new Timer();
                    timerTask = new TimerTask() {
                        int zuizhong = (int)(l-l1);
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvTime.setText("已等待" + getStringTime(zuizhong++));
                                }
                            });
                        }
                    };
                    timer1.schedule(timerTask, 0, 1000);

                    switch (order_status) {
                        case 1:
                            timer.cancel();
                            JieDanActivity.forward(WaitActivity.this, order_sn);
                            finish();
                            break;
                        case 2:
                            timer.cancel();
                            ArrivedActivity.forward(WaitActivity.this, order_sn);

                            finish();
                            break;
                        case 3:
                            timer.cancel();
                            JinXingActivity.forward(WaitActivity.this, order_sn);
                            finish();
                            break;
                        case 4:
                            timer.cancel();
                            EndActivity.forward(WaitActivity.this, order_sn);
                            finish();
                            break;
                    }

                }

            }

            @Override
            public void requestError(String errorMsg, int errorType) {
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        ImmersionBar.with(this).titleBar(rlTitle).init();
        instance = this;
    }

    @Override
    protected void initListener() {

    }


    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("抢单成功", msg)) {
            JieDanActivity.forward(WaitActivity.this, order_sn);
            if (timer!=null) {
                timer.cancel();
            }
            finish();
        }
    }

    public static void forward(Context context, String order_sn) {
        Intent intent = new Intent(context, WaitActivity.class);
        intent.putExtra("order_sn", "" + order_sn);
        context.startActivity(intent);
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
    protected void initData() {
        tvTitle.setText("等待应答");
        order_sn = getIntent().getStringExtra("order_sn");
      getDataFromNet();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }, 1000, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer!=null) {
            timer.cancel();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                aMap.moveCamera(CameraUpdateFactory.zoomTo(16.5f));
                new WaveMapUtils().addWaveAnimation(latLng, aMap);
            }
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @OnClick({R.id.ll_common_back, R.id.tv_quxia, R.id.iv_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_quxia:
                CancelOrderActivity.forward(WaitActivity.this, order_sn, "wait");
                break;
            case R.id.iv_user:
                userLocation();
                break;
        }
    }


    private String getStringTime(int cnt) {
        int hour = cnt / 3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
    }
}
