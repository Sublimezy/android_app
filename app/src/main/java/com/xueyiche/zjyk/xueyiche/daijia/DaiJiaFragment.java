package com.xueyiche.zjyk.xueyiche.daijia;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.ConstantsBean;
import com.xueyiche.zjyk.xueyiche.daijia.view.YuYueLinkagePicker;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;
import com.xueyiche.zjyk.xueyiche.route.DrivingRouteOverLay;
import com.xueyiche.zjyk.xueyiche.utils.AMapUtil;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaiJiaFragment extends Fragment implements RouteSearch.OnRouteSearchListener, AMapLocationListener, LocationSource {
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.rb_richang)
    RadioButton rbRichang;
    @BindView(R.id.rb_yuyue)
    RadioButton rbYuyue;
    @BindView(R.id.rb_daijiao)
    RadioButton rbDaijiao;
    @BindView(R.id.iv_daijia_banner)
    ImageView ivDaijiaBanner;
    @BindView(R.id.tv_qidian)
    TextView tvQidian;
    @BindView(R.id.tv_mudi)
    TextView tvMudi;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_richang)
    LinearLayout llRichang;
    @BindView(R.id.tv_yuyue_time)
    TextView tvYuyueTime;
    @BindView(R.id.ll_yuyue_time)
    LinearLayout llYuyueTime;
    @BindView(R.id.iv_daijia_banner_yuyue)
    CustomShapeImageView ivDaijiaBannerYuyue;
    @BindView(R.id.tv_qidian_yuyue)
    TextView tvQidianYuyue;
    @BindView(R.id.tv_mudi_yuyue)
    TextView tvMudiYuyue;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.rl_beizhu)
    RelativeLayout rlBeizhu;
    @BindView(R.id.tv_money_yuyue)
    TextView tvMoneyYuyue;
    @BindView(R.id.tv_xiadan_yuyue)
    TextView tvXiadanYuyue;
    @BindView(R.id.ll_yuyue)
    LinearLayout llYuyue;
    @BindView(R.id.iv_daijia_banner_daijiao)
    ImageView ivDaijiaBannerDaijiao;
    @BindView(R.id.tv_choose_time)
    TextView tvChooseTime;
    @BindView(R.id.daijiao_line)
    View daijiaoLine;
    @BindView(R.id.tv_choose_people)
    TextView tvChoosePeople;
    @BindView(R.id.tv_qidian_daijiao)
    TextView tvQidianDaijiao;
    @BindView(R.id.tv_mudi_daijiao)
    TextView tvMudiDaijiao;
    @BindView(R.id.ll_daijiao)
    LinearLayout llDaijiao;
    private UiSettings mUiSettings;
    private AMap aMap;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private AMapLocationClient mlocationClient;
    private final int ROUTE_TYPE_DRIVE = 2;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public void onClick(View v) {
//        //选择地址
//        Intent intent2 = new Intent(App.context, DaiJiaoActivity.class);
//        String mudi = tv_mudi.getText().toString();
//        switch (v.getId()) {
//            case R.id.iv_login_back:
//
//                break;
//            case R.id.tv_choose_people:
//                startActivityForResult(intent2, 333);
//                break;
//            case R.id.tv_top_right_button:
//                Intent intent = new Intent(getActivity(), UrlActivity.class);
//                intent.putExtra("url", "http://xueyiche.cn/xyc/daijia/index.html");
//                intent.putExtra("type", "10");
//                startActivity(intent);
//                break;
//            case R.id.iv_anquan:
//                //打开安全中心
//                showAnQuan();
//                break;
//            case R.id.ll_yuyue_time:
//                //选择预约时间
//                startTimePicker();
//                break;
//            case R.id.tv_choose_time:
//                //选择预约时间
//                startTimePicker();
//                break;
//            case R.id.rb_richang:
//                yc_yy = "0";
//                ll_richang.setVisibility(View.VISIBLE);
//                ll_yuyue.setVisibility(View.GONE);
//                ll_daijiao.setVisibility(View.GONE);
//                break;
//            case R.id.rb_yuyue:
//                yc_yy = "1";
//                ll_richang.setVisibility(View.GONE);
//                ll_daijiao.setVisibility(View.GONE);
//                ll_yuyue.setVisibility(View.VISIBLE);
//                break;
//            case R.id.rb_daijiao:
//                yc_yy = "2";
//                ll_richang.setVisibility(View.GONE);
//                ll_yuyue.setVisibility(View.GONE);
//                ll_daijiao.setVisibility(View.VISIBLE);
//                break;
//            case R.id.iv_user:
//                break;
//            case R.id.tv_qidian:
//                //起点位置
////                intent1.putExtra("type", "qi");
////                startActivityForResult(intent1, 111);
//                break;
//            case R.id.tv_qidian_yuyue:
//                //起点位置
////                intent1.putExtra("type", "qi");
////                startActivityForResult(intent1, 111);
//                break;
//            case R.id.tv_qidian_daijiao:
//                //起点位置
////                intent1.putExtra("type", "qi");
////                startActivityForResult(intent1, 111);
//                break;
//            case R.id.tv_mudi:
//                //目的地位置
////                intent1.putExtra("type", "zhong");
////                startActivityForResult(intent1, 222);
//                break;
//            case R.id.tv_mudi_daijiao:
//                //目的地位置
////                intent1.putExtra("type", "zhong");
////                startActivityForResult(intent1, 222);
//                break;
//            case R.id.tv_mudi_yuyue:
////                intent1.putExtra("type", "zhong");
////                startActivityForResult(intent1, 222);
//                break;
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle extras = data.getExtras();
            switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
                case 111:
                    String latitude1 = extras.getString("latitude");
                    String longitude1 = extras.getString("longitude");
                    String address1 = extras.getString("address");
                    String name1 = extras.getString("name");
//                    if (jie_name.equals(song_name)) {
//                        Toast.makeText(getActivity(), "起点与终点不能相同", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    if (!TextUtils.isEmpty(latitude1)) {
//                        jie_latitude = latitude1;
//                    }
//                    if (!TextUtils.isEmpty(longitude1)) {
//                        jie_longitude = longitude1;
//                    }
//                    if (!TextUtils.isEmpty(address1)) {
//                        jie_address = address1;
//                    }
//                    if (!TextUtils.isEmpty(name1)) {
//                        jie_name = name1;
//                    }
//                    if (!TextUtils.isEmpty(jie_name)) {
//                        tv_qidian.setText(jie_name);
//                        tv_qidian_yuyue.setText(jie_name);
//                        LatLng latLng = new LatLng(Double.parseDouble(jie_latitude), Double.parseDouble(jie_longitude));
//                    }

                    break;
                case 222:
//                    String latitude = extras.getString("latitude");
//                    if (!TextUtils.isEmpty(latitude)) {
//                        song_latitude = latitude;
//                    }else {
//                        song_latitude = "";
//                    }
//                    String longitude = extras.getString("longitude");
//                    if (!TextUtils.isEmpty(longitude)) {
//                        song_longitude = longitude;
//                    }else {
//                        song_longitude = "";
//
//                    }
//                    String address = extras.getString("address");
//                    if (!TextUtils.isEmpty(address)) {
//                        song_address = address;
//                    }else {
//                        song_address = "";
//                    }
//                    String name = extras.getString("name");
//                    if (!TextUtils.isEmpty(name)) {
//                        if (!jie_name.equals(name)) {
//                            song_name = name;
//                        } else {
//                            showToastShort("起点与目的地不可相同");
//                        }
//                    }
//                    if (!TextUtils.isEmpty(jie_name) && !TextUtils.isEmpty(song_name) &&
//                            !TextUtils.isEmpty(song_longitude) && !TextUtils.isEmpty(song_latitude) &&
//                            !TextUtils.isEmpty(jie_longitude) && !TextUtils.isEmpty(jie_latitude) &&
//                            !TextUtils.isEmpty(jie_address) && !TextUtils.isEmpty(song_address)) {
//                        Intent intent = new Intent(getActivity(), XingChengActivity.class);
//                        intent.putExtra("song_name", song_name);
//                        intent.putExtra("song_address", song_address);
//                        intent.putExtra("song_longitude", song_longitude);
//                        intent.putExtra("song_latitude", song_latitude);
//                        intent.putExtra("jie_name", jie_name);
//                        intent.putExtra("jie_address", jie_address);
//                        intent.putExtra("jie_longitude", jie_longitude);
//                        intent.putExtra("jie_latitude", jie_latitude);
//                        String trim = tv_yuyue_time.getText().toString().trim();
//                        String trim_daijiao = tv_choose_time.getText().toString().trim();
//                        intent.putExtra("appointed_time", appointed_time);
//                        intent.putExtra("yuyue_time", trim);
//                        intent.putExtra("daijiao_time", trim_daijiao);
//                        intent.putExtra("daijiao_phone", daijiao_phone);
//                        intent.putExtra("yc_yy", yc_yy);
//                        startActivity(intent);
//                    }
                    break;
                case 444:
//                    daijiao_phone = extras.getString("daijiao_phone");
//                    String daijiao_name = extras.getString("daijiao_name");
//                    if (!TextUtils.isEmpty(daijiao_phone)) {
//                        tv_choose_people.setText(daijiao_phone +"  ");
//                        daijiao_line.setVisibility(View.VISIBLE);
//                        tv_choose_time.setVisibility(View.VISIBLE);
//                    }else {
//                        tv_choose_people.setText("选择乘车人  ");
//                    }
                    break;
            }
        }

    }

    private void initData() {
        try {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationOption.setOnceLocation(true);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        try {
            mRouteSearch = new RouteSearch(getActivity());
            aMap.setLocationSource(this);// 设置定位监听
            aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            mRouteSearch.setRouteSearchListener(this);
        } catch (AMapException e) {
            e.printStackTrace();
        }


//        area_id = PrefUtils.getString(getActivity(), "area_id", "");
//        getPingTaiConstants();
//        dj_xy = PrefUtils.getString(App.context, "dj_xy", "0");
//        if ("0".equals(dj_xy)) {
//            showXY();
//        }
    }







    public void getPingTaiConstants() {

        OkHttpUtils.post()
                .url(AppUrl.PingTai_Constants)
//                .addParams("area_id", area_id)
                .addParams("device_id", LoginUtils.getId(getActivity()))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            final ConstantsBean constantsBean = JsonUtil.parseJsonToBean(string, ConstantsBean.class);
                            if (constantsBean != null) {
                                int code = constantsBean.getCode();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        final ConstantsBean.ContentBean content = constantsBean.getContent();
                                        if (content != null) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ////免费可取消最大分钟数
                                                    String default_time = content.getDefault_time();
                                                    PrefUtils.putString(getActivity(), "default_time", default_time);
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
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.daijia_activity, null);
        ButterKnife.bind(this, view);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);
            setUpMap();
        } else {
            aMap.clear();
            aMap.setLocationSource(this);
            aMap.setMyLocationEnabled(true);
            aMap = mapView.getMap();
            setUpMap();
        }
        initData();
        LatLonPoint mStartPoint = new LatLonPoint(39.942295, 116.335891);//起点，116.335891,39.942295
        LatLonPoint mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，116.481288,39.995576
        setfromandtoMarker(mStartPoint, mEndPoint);
        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault, mStartPoint, mEndPoint);
        return view;
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW));
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }

    private void setfromandtoMarker(LatLonPoint mStartPoint, LatLonPoint mEndPoint) {
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.qi_pic)));
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.zhong_pic)));
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode, LatLonPoint mStartPoint, LatLonPoint mEndPoint) {
        if (mStartPoint == null) {
            return;
        }
        if (mEndPoint == null) {
        }
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }
    @OnClick({R.id.iv_anquan, R.id.iv_daijia_banner, R.id.tv_qidian, R.id.tv_mudi, R.id.iv_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_anquan:
                AppUtils.showAnQuan(getActivity());
                break;
            case R.id.iv_daijia_banner:
                break;
            case R.id.tv_qidian:
                break;
            case R.id.tv_mudi:
                break;
            case R.id.iv_user:
                mlocationClient.startLocation();
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                String city = aMapLocation.getCity();
                String cityCode = aMapLocation.getCityCode();
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                tvQidian.setText(aMapLocation.getAddress());
                Log.e("onLocationChanged", "" + city);
                Log.e("onLocationChanged", "" + cityCode);

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            try {
                mlocationClient = new AMapLocationClient(getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationOption.setOnceLocation(true);
            mLocationOption.setInterval(Long.valueOf("10000"));
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        aMap.clear();// 清理地图上的所有覆盖物
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
                            App.context, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    //35分钟（19公里）
                    String lenthGL = AMapUtil.getFriendlyLength(dis);
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
                }

            } else {
            }
        } else {
        }

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mapView.onDestroy();
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
        final YuYueLinkagePicker picker = new YuYueLinkagePicker(getActivity(), firstList, secondList, thirdList);
        picker.setTitleText("请选择预约时间");
        picker.setTitleTextSize(20);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));
        picker.setOnLinkageListener(new YuYueLinkagePicker.OnLinkageListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                //时间格式:2016-11-08 08:00:00

//                tv_yuyue_time.setText(first + second + ":" + third);
//                tv_choose_time.setText(first + second + ":" + third);
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
//                appointed_time = s2.replace("日", " ");
                picker.dismiss();
            }
        });
        picker.show();
    }
}
