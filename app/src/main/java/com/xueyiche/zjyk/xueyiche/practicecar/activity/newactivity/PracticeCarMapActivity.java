package com.xueyiche.zjyk.xueyiche.practicecar.activity.newactivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.ChoiceCardDetails;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.DriverPracticeActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.NowPracticeActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.YuYuePracticeActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.DriverLocationBean;
import com.xueyiche.zjyk.xueyiche.utils.BaiduLocation;
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

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * Created by ZL on 2020/2/4.
 */
public class PracticeCarMapActivity extends BaseActivity implements SensorEventListener, View.OnClickListener {

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    // UI相关
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    TextureMapView mMapView;
    BaiduMap mBaiduMap;
    private TextView tv_anquan, tv_content;
    private RadioButton rb_now,rb_yuyue,rb_driverbaoming;
    private ImageView iv_user,iv_login_back;
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    private LinearLayout ll_choose_type;

    @Override
    protected int initContentView() {
        return R.layout.practice_car_map_activity;
    }

    @Override
    protected void initView() {
        mMapView = (TextureMapView) view.findViewById(R.id.map_car);

        tv_anquan = (TextView) view.findViewById(R.id.tv_anquan);
        rb_now = (RadioButton) view.findViewById(R.id.rb_now);
        rb_yuyue = (RadioButton) view.findViewById(R.id.rb_yuyue);
        rb_driverbaoming = (RadioButton) view.findViewById(R.id.rb_driverbaoming);
        ll_choose_type = (LinearLayout) view.findViewById(R.id.ll_choose_type);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        iv_user = (ImageView) view.findViewById(R.id.iv_user);
        iv_login_back = (ImageView) view.findViewById(R.id.iv_login_back);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(0)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
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
        mMapView.onPause();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mMapView.setVisibility(View.GONE);
        MapView.setMapCustomEnable(false);
        mBaiduMap.setMyLocationEnabled(false);
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        mMapView.setVisibility(View.VISIBLE);
        super.onResume();
        mapVoid();
        LatLng latLng = new LatLng(mCurrentLat, mCurrentLon);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(mapStatusUpdate, 1000);
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        MapView.setMapCustomEnable(false);
        mBaiduMap.setMyLocationEnabled(false);
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
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            locData = new MyLocationData.Builder()
                    .accuracy(0)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(12.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }
    public void showAnQuan() {
        final Dialog dialog = new Dialog(PracticeCarMapActivity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(PracticeCarMapActivity.this).inflate(R.layout.dialog_anqun_layout, null);
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
                XueYiCheUtils.CallPhone(PracticeCarMapActivity.this, "拨打报警电话", "110");
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
                Intent intent = new Intent(App.context, UrlActivity.class);
                intent.putExtra("url", "http://www.xueyiche.vip:89//wap/Driver/youzheng");
                intent.putExtra("type", "11");
                startActivity(intent);
                break;
            case R.id.iv_user:
                //回到自己得位置
                LatLng latLng = new LatLng(mCurrentLat, mCurrentLon);
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(mapStatusUpdate, 1000);
                break;
            case R.id.tv_anquan:
                //打开安全中心
                showAnQuan();
                break;
            case R.id.iv_login_back:
              finish();
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
    private void mapVoid() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(12.0f));
        MapStatus.Builder builder1 = new MapStatus.Builder();
        builder1.zoom(12.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        BaiduLocation baidu = new BaiduLocation();
        baidu.baiduLocation();
        String lon = PrefUtils.getString(App.context, "x", "");
        String lat = PrefUtils.getString(App.context, "y", "");
        addMarker(lon,lat);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 获得marker中的数据
                Bundle extraInfo = marker.getExtraInfo();
                if (extraInfo != null) {
                    if (marker != null) {
                        DriverLocationBean.ContentBean bikeInfo = (DriverLocationBean.ContentBean) extraInfo.get("baidumapjson");
                        if (bikeInfo != null) {
                            Intent intent = new Intent(App.context, ChoiceCardDetails.class);
                            String driver_id = bikeInfo.getDriver_id();
                            intent.putExtra("driver_id", driver_id);
                            startActivity(intent);
                        }
                    }
                }
                return false;
            }
        });
        tv_content.setVisibility(View.VISIBLE);
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            //地图状态开始改变。
            public void onMapStatusChangeStart(MapStatus status) {
                tv_content.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
                tv_content.setVisibility(View.VISIBLE);
            }

            //地图状态改变结束
            public void onMapStatusChangeFinish(MapStatus status) {
                //改变结束之后，获取地图可视范围的中心点坐标
                tv_content.setVisibility(View.VISIBLE);
                LatLng latLng = status.target;
                //拿到经纬度之后，就可以反地理编码获取地址信息了
                initGeoCoder(latLng);
            }

            //地图状态变化中
            public void onMapStatusChange(MapStatus status) {
                tv_content.setVisibility(View.INVISIBLE);
            }
        });
//        addMarker();
    }
    private void initGeoCoder(final LatLng latLng) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                List<PoiInfo> poiList = reverseGeoCodeResult.getPoiList();
                if (poiList != null && poiList.size() > 0) {
                    LatLng location = poiList.get(0).location;
                    String jie_latitude = location.latitude + "";
                    String jie_longitude = location.longitude + "";
                    addMarker(jie_longitude,jie_latitude);
                }
            }
        });
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
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
                .addParams("device_id", LoginUtils.getId(this))
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

                            mBaiduMap.clear();//先清除一下图层
                            LatLng latLng = null;
                            Marker marker = null;
                            OverlayOptions options = null;
                            if (content != null & content.size() > 0)
                                for (DriverLocationBean.ContentBean contentBean : content) {
                                    int on_off = contentBean.getOn_off();
                                    String latitude_driver = contentBean.getLatitude_driver();
                                    String longitude_driver = contentBean.getLongitude_driver();
                                    if (!TextUtils.isEmpty(latitude_driver) && !TextUtils.isEmpty(longitude_driver)) {
                                        double la = Double.parseDouble(latitude_driver);
                                        double lo = Double.parseDouble(longitude_driver);
                                        latLng = new LatLng(la, lo);
                                        // 0:等待接单 1: 关闭 2：进行中
                                        if (0 == on_off) {
                                            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.kongxian);
                                            options = new MarkerOptions().position(latLng).icon(bitmap).zIndex(14);
                                        } else if (2 == on_off) {
                                            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.manglu);
                                            options = new MarkerOptions().position(latLng).icon(bitmap).zIndex(14);
                                        }
                                        marker = (Marker) mBaiduMap.addOverlay(options);//将覆盖物添加到地图上
                                        Bundle bundle = new Bundle();//创建一个Bundle对象将每个mark具体信息传过去，当点击该覆盖物图标的时候就会显示该覆盖物的详细信息
                                        bundle.putSerializable("baidumapjson", contentBean);
                                        marker.setExtraInfo(bundle);
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

    }
    @Override
    protected void initListener() {
        tv_anquan.setOnClickListener(this);
        rb_now.setOnClickListener(this);
        rb_yuyue.setOnClickListener(this);
        ll_choose_type.setOnClickListener(this);
        rb_driverbaoming.setOnClickListener(this);
        iv_user.setOnClickListener(this);
        iv_login_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        ll_choose_type.setVisibility(View.VISIBLE);
        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_WHITE);
        mMapView.setMapCustomStylePath(customStyleFilePath);
        mMapView.setMapCustomStyleEnable(true);
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        //隐藏百度logo
        mMapView.removeViewAt(1);
        UiSettings settings=mBaiduMap.getUiSettings();
        settings.setOverlookingGesturesEnabled(false);//屏蔽双指下拉时变成3D地图
        settings.setRotateGesturesEnabled(false);   //屏蔽旋转
    }
}
