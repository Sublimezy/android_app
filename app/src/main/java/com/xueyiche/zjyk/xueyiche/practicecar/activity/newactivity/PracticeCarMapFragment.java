package com.xueyiche.zjyk.xueyiche.practicecar.activity.newactivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.daijia.RegistSiJiActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.DriverPracticeActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.NowPracticeActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.YuYuePracticeActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.DriverLocationBean;
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
import java.util.List;

/**
 * Created by ZL on 2020/2/4.
 */
public class PracticeCarMapFragment extends BaseFragment implements SensorEventListener, View.OnClickListener {

    // 定位相关
    BitmapDescriptor mCurrentMarker;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    // UI相关
    boolean isFirstLoc = true; // 是否首次定位
    TextureMapView mMapView;
    private TextView tv_anquan, tv_content;
    private RadioButton rb_now,rb_yuyue,rb_driverbaoming;
    private ImageView iv_user,iv_login_back;
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    private LinearLayout ll_choose_type;
    protected int initContentView() {
        return R.layout.practice_car_map_activity;
    }

    private void initView(View view) {
        mMapView = (TextureMapView) view.findViewById(R.id.map_car);

        tv_anquan = (TextView) view.findViewById(R.id.tv_anquan);
        rb_now = (RadioButton) view.findViewById(R.id.rb_now);
        rb_yuyue = (RadioButton) view.findViewById(R.id.rb_yuyue);
        rb_driverbaoming = (RadioButton) view.findViewById(R.id.rb_driverbaoming);
        ll_choose_type = (LinearLayout) view.findViewById(R.id.ll_choose_type);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        iv_user = (ImageView) view.findViewById(R.id.iv_user);
        iv_login_back = (ImageView) view.findViewById(R.id.iv_login_back);
        iv_login_back.setVisibility(View.INVISIBLE);
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
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.practice_car_map_activity,null);
        initView(view);
        initListener();
        initData();
        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "lianche";
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        mMapView.onResume();
        mMapView.setVisibility(View.VISIBLE);
        super.onResume();
        mapVoid();
        LatLng latLng = new LatLng(mCurrentLat, mCurrentLon);
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mMapView.onDestroy();
        super.onDestroy();
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
    public void showAnQuan() {
        final Dialog dialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_anqun_layout, null);
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
//       将属性设置给窗体
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
                Intent intent = new Intent(App.context, JinjiPhoneActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ll_baojing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XueYiCheUtils.CallPhone(getActivity(), "拨打报警电话", "110");
            }
        });
        rl_xuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(App.context, UrlActivity.class);
                intent1.putExtra("url", "http://xueyiche.cn/xyc/instructions/safe.html");
                intent1.putExtra("type", "9");
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_driverbaoming:
                Intent intent = new Intent(App.context, RegistSiJiActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_user:
                //回到自己得位置
                break;
            case R.id.tv_anquan:
                //打开安全中心
                showAnQuan();
                break;
            case R.id.iv_login_back:
                break;
            case R.id.rb_now:
                //打开现在练车车
                Intent intent1 = new Intent(App.context, NowPracticeActivity.class);
                startActivity(intent1);
                break;
            case R.id.rb_yuyue:
                //打开预约练车
                Intent intent2 = new Intent(App.context, YuYuePracticeActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_choose_type:
                //打开教练列表
                Intent intent3 = new Intent(App.context, DriverPracticeActivity.class);
                startActivity(intent3);
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void mapVoid() {
    }
    private void showPopTop(String size) {
        if ("0".equals(size)) {
            tv_content.setText("附近暂无教练");
        } else {
            tv_content.setText("附近有" + size + "位教练");
        }
    }
    private void addMarker(String longitude_user,String latitude_user) {
        OkHttpUtils.post().url(AppUrl.Driver_Map)
                .addParams("device_id", LoginUtils.getId(getActivity()))
                .addParams("user_id", PrefUtils.getParameter("user_id"))
                .addParams("longitude_user", longitude_user)
                .addParams("latitude_user", latitude_user)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    DriverLocationBean driverLocationBean = JsonUtil.parseJsonToBean(string, DriverLocationBean.class);
                    if (driverLocationBean != null) {
                        int code = driverLocationBean.getCode();
                        if (200 == code) {
                            final List<DriverLocationBean.ContentBean> content = driverLocationBean.getContent();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (content.size() == 0) {
                                        showPopTop("0");
                                    } else {
                                        showPopTop("" + content.size());
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

            }

            @Override
            public void onResponse(Object response) {

            }
        });

    }
    private void initListener() {
        tv_anquan.setOnClickListener(this);
        rb_now.setOnClickListener(this);
        rb_yuyue.setOnClickListener(this);
        ll_choose_type.setOnClickListener(this);
        rb_driverbaoming.setOnClickListener(this);
        iv_user.setOnClickListener(this);
        iv_login_back.setOnClickListener(this);
    }


    private void initData() {
        ll_choose_type.setVisibility(View.VISIBLE);
        String customStyleFilePath = getCustomStyleFilePath(getActivity(), CUSTOM_FILE_NAME_WHITE);
    }
}
