package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.WaveMapUtils;

import java.util.Locale;
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
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;
                default:
                    break;
            }
        }


    };
private String order_sn;
    @Override
    protected int initContentView() {
        return R.layout.wait_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        ImmersionBar.with(this).titleBar(rlTitle).init();
    }

    @Override
    protected void initListener() {

    }


    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新代驾", msg)) {
//            JieDanActivity.instance.finish();
            Intent intent = new Intent(this, JinXingActivity.class);
            intent.putExtra("order_number", "order_number");
            startActivity(intent);
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
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }, 1000, 5000);
        startTime();
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
                CancelOrderActivity.forward(WaitActivity.this,order_sn,"wait");
                break;
            case R.id.iv_user:
                userLocation();
                break;
        }
    }

    private void startTime() {
        timer1 = new Timer();
        int start_time = PrefUtils.getInt(App.context, "start_time", 0);
        timerTask = new TimerTask() {
            int cnt = start_time;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("已等待" + getStringTime(cnt++));
                        PrefUtils.putInt(App.context, "start_time", cnt);
                    }
                });
            }
        };
        timer1.schedule(timerTask, 0, 1000);
    }

    private String getStringTime(int cnt) {
        int hour = cnt / 3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
    }
}
