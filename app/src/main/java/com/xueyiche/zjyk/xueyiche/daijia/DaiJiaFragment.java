package com.xueyiche.zjyk.xueyiche.daijia;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import android.widget.Toast;

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
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.DaiJiaoActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.MuDiActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.XingChengActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.ConstantsBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.DaiJiaDriverLoactionBean;
import com.xueyiche.zjyk.xueyiche.daijia.view.YuYueLinkagePicker;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitYuYueActivity;

/**
 * Created by Administrator on 2019/9/11.
 */
public class DaiJiaFragment extends BaseFragment implements View.OnClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private String user_id;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
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
    private LinearLayout ll_richang, ll_yuyue,ll_daijiao;
    private TextView tv_qidian_yuyue,tv_choose_people;
    private TextView tv_mudi_yuyue;
    private TextView tv_xiadan_yuyue;
    private TextView tv_qidian_daijiao;
    private TextView tv_mudi_daijiao;
    private LinearLayout ll_yuyue_time;
    private RelativeLayout rl_beizhu;
    private TextView tv_yuyue_time,tv_choose_time;
    private TextView tv_money_yuyue;
    private TextView tv_money;
    private String area_id;
    private String appointed_time = "";
    private String yc_yy = "0";
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    private String dj_xy;
    private View daijiao_line;
    private String daijiao_phone="";



    private void initView(View view) {
        mMapView = (MapView) view.findViewById(R.id.map_car);
        String customStyleFilePath = getCustomStyleFilePath(getActivity(), CUSTOM_FILE_NAME_WHITE);
        mMapView.setMapCustomStylePath(customStyleFilePath);
        mMapView.setMapCustomStyleEnable(true);
        iv_anquan = (ImageView) view.findViewById(R.id.iv_anquan);
        iv_user = (ImageView) view.findViewById(R.id.iv_user);
        rb_richang = view.findViewById(R.id.rb_richang);
        rb_yuyue =view.findViewById(R.id.rb_yuyue);
        daijiao_line =view.findViewById(R.id.daijiao_line);
        tv_choose_time =view.findViewById(R.id.tv_choose_time);
        rb_daijiao =view.findViewById(R.id.rb_daijiao);
        tv_mudi = (TextView) view.findViewById(R.id.tv_mudi);
        tv_qidian = (TextView) view.findViewById(R.id.tv_qidian);
        tv_qidian_daijiao = (TextView) view.findViewById(R.id.tv_qidian_daijiao);
        tv_mudi_daijiao = (TextView) view.findViewById(R.id.tv_mudi_daijiao);
        tv_qidian_yuyue = (TextView) view.findViewById(R.id.tv_qidian_yuyue);
        ll_richang = (LinearLayout) view.findViewById(R.id.ll_richang);
        ll_yuyue = (LinearLayout) view.findViewById(R.id.ll_yuyue);
        ll_daijiao = (LinearLayout) view.findViewById(R.id.ll_daijiao);
        ll_yuyue_time = (LinearLayout) view.findViewById(R.id.ll_yuyue_time);
        tv_mudi_yuyue = (TextView) view.findViewById(R.id.tv_mudi_yuyue);
        tv_qidian_yuyue = (TextView) view.findViewById(R.id.tv_qidian_yuyue);
        tv_xiadan_yuyue = (TextView) view.findViewById(R.id.tv_xiadan_yuyue);
        tv_yuyue_time = (TextView) view.findViewById(R.id.tv_yuyue_time);
        tv_money_yuyue = (TextView) view.findViewById(R.id.tv_money_yuyue);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_choose_people = (TextView) view.findViewById(R.id.tv_choose_people);
        rl_beizhu = (RelativeLayout) view.findViewById(R.id.rl_beizhu);
    }


    private void initListener() {
        tv_choose_time.setOnClickListener(this);
        iv_anquan.setOnClickListener(this);
        tv_choose_people.setOnClickListener(this);
        iv_user.setOnClickListener(this);
        rb_richang.setOnClickListener(this);
        rb_daijiao.setOnClickListener(this);
        rb_yuyue.setOnClickListener(this);
        tv_qidian.setOnClickListener(this);
        tv_qidian_daijiao.setOnClickListener(this);
        tv_mudi_daijiao.setOnClickListener(this);
        tv_money_yuyue.setOnClickListener(this);
        tv_money.setOnClickListener(this);
        tv_qidian.setOnClickListener(this);
        tv_mudi.setOnClickListener(this);
        tv_mudi_yuyue.setOnClickListener(this);
        tv_qidian_yuyue.setOnClickListener(this);
        ll_yuyue_time.setOnClickListener(this);
        tv_xiadan_yuyue.setOnClickListener(this);
        rl_beizhu.setOnClickListener(this);
        tv_money.setOnClickListener(this);
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
                    PoiInfo poiInfo = poiList.get(0);
                    if (poiInfo != null) {
                        if (!"天安门".equals(poiInfo.name)) {
                            jie_address = poiInfo.address;
                            jie_name = poiInfo.name;
                            LatLng location = poiInfo.getLocation();
                            jie_latitude = location.latitude + "";
                            jie_longitude = location.longitude + "";
                            tv_qidian_yuyue.setText(jie_name);
                            tv_qidian.setText(jie_name);
                            tv_qidian_daijiao.setText(jie_name);
                        }
                    }
                }
            }
        });
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng).pageNum(0).pageSize(100));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        //选择地址
        Intent intent1 = new Intent(App.context, MuDiActivity.class);
        Intent intent2 = new Intent(App.context, DaiJiaoActivity.class);
        String mudi = tv_mudi.getText().toString();
        switch (v.getId()) {
            case R.id.iv_login_back:

                break;
            case R.id.tv_choose_people:
                startActivityForResult(intent2, 333);
                break;
            case R.id.tv_top_right_button:
                Intent intent = new Intent(getActivity(), UrlActivity.class);
                intent.putExtra("url", "http://xueyiche.cn/xyc/daijia/index.html");
                intent.putExtra("type", "10");
                startActivity(intent);
                break;
            case R.id.iv_anquan:
                //打开安全中心
                showAnQuan();
                break;
            case R.id.ll_yuyue_time:
                //选择预约时间
                startTimePicker();
                break;
            case R.id.tv_choose_time:
                //选择预约时间
                startTimePicker();
                break;
            case R.id.rl_beizhu:
                showToastShort("请选择目的地");
                break;
            case R.id.tv_daijiao:
                //代叫服务
                showToastShort("请选择目的地");
                break;
            case R.id.tv_xiadan:
                //一键下单
                showToastShort("请选择目的地");
                break;
            case R.id.tv_xiadan_yuyue:
                //预约下单
                showToastShort("请选择目的地");
                break;
            case R.id.rb_richang:
                yc_yy = "0";
                ll_richang.setVisibility(View.VISIBLE);
                ll_yuyue.setVisibility(View.GONE);
                ll_daijiao.setVisibility(View.GONE);
                break;
            case R.id.rb_yuyue:
                yc_yy = "1";
                ll_richang.setVisibility(View.GONE);
                ll_daijiao.setVisibility(View.GONE);
                ll_yuyue.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_daijiao:
                yc_yy = "2";
                ll_richang.setVisibility(View.GONE);
                ll_yuyue.setVisibility(View.GONE);
                ll_daijiao.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_user:
                //回到自己得位置
                LatLng latLng = new LatLng(mCurrentLat, mCurrentLon);
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(mapStatusUpdate, 1000);
                break;
            case R.id.tv_qidian:
                //起点位置
                intent1.putExtra("type", "qi");
                startActivityForResult(intent1, 111);
                break;
            case R.id.tv_qidian_yuyue:
                //起点位置
                intent1.putExtra("type", "qi");
                startActivityForResult(intent1, 111);
                break;
            case R.id.tv_qidian_daijiao:
                //起点位置
                intent1.putExtra("type", "qi");
                startActivityForResult(intent1, 111);
                break;
            case R.id.tv_mudi:
                //目的地位置
                intent1.putExtra("type", "zhong");
                startActivityForResult(intent1, 222);
                break;
            case R.id.tv_mudi_daijiao:
                //目的地位置
                intent1.putExtra("type", "zhong");
                startActivityForResult(intent1, 222);
                break;
            case R.id.tv_mudi_yuyue:
                intent1.putExtra("type", "zhong");
                startActivityForResult(intent1, 222);
                break;
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
        final YuYueLinkagePicker picker = new YuYueLinkagePicker(getActivity(), firstList, secondList, thirdList);
        picker.setTitleText("请选择预约时间");
        picker.setTitleTextSize(20);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));
        picker.setOnLinkageListener(new YuYueLinkagePicker.OnLinkageListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                //时间格式:2016-11-08 08:00:00

                tv_yuyue_time.setText(first + second + ":" + third);
                tv_choose_time.setText(first + second + ":" + third);
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
                case 111:
                    String latitude1 = extras.getString("latitude");
                    String longitude1 = extras.getString("longitude");
                    String address1 = extras.getString("address");
                    String name1 = extras.getString("name");
                    if (jie_name.equals(song_name)) {
                        Toast.makeText(getActivity(), "起点与终点不能相同", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!TextUtils.isEmpty(latitude1)) {
                        jie_latitude = latitude1;
                    }
                    if (!TextUtils.isEmpty(longitude1)) {
                        jie_longitude = longitude1;
                    }
                    if (!TextUtils.isEmpty(address1)) {
                        jie_address = address1;
                    }
                    if (!TextUtils.isEmpty(name1)) {
                        jie_name = name1;
                    }
                    if (!TextUtils.isEmpty(jie_name)) {
                        tv_qidian.setText(jie_name);
                        tv_qidian_yuyue.setText(jie_name);
                        LatLng latLng = new LatLng(Double.parseDouble(jie_latitude), Double.parseDouble(jie_longitude));
                        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                        mBaiduMap.animateMapStatus(mapStatusUpdate, 1000);
                    }

                    break;
                case 222:
                    String latitude = extras.getString("latitude");
                    if (!TextUtils.isEmpty(latitude)) {
                        song_latitude = latitude;
                    }else {
                        song_latitude = "";
                    }
                    String longitude = extras.getString("longitude");
                    if (!TextUtils.isEmpty(longitude)) {
                        song_longitude = longitude;
                    }else {
                        song_longitude = "";

                    }
                    String address = extras.getString("address");
                    if (!TextUtils.isEmpty(address)) {
                        song_address = address;
                    }else {
                        song_address = "";
                    }
                    String name = extras.getString("name");
                    if (!TextUtils.isEmpty(name)) {
                        if (!jie_name.equals(name)) {
                            song_name = name;
                        } else {
                            showToastShort("起点与目的地不可相同");
                        }
                    }
                    if (!TextUtils.isEmpty(jie_name) && !TextUtils.isEmpty(song_name) &&
                            !TextUtils.isEmpty(song_longitude) && !TextUtils.isEmpty(song_latitude) &&
                            !TextUtils.isEmpty(jie_longitude) && !TextUtils.isEmpty(jie_latitude) &&
                            !TextUtils.isEmpty(jie_address) && !TextUtils.isEmpty(song_address)) {
                        Intent intent = new Intent(getActivity(), XingChengActivity.class);
                        intent.putExtra("song_name", song_name);
                        intent.putExtra("song_address", song_address);
                        intent.putExtra("song_longitude", song_longitude);
                        intent.putExtra("song_latitude", song_latitude);
                        intent.putExtra("jie_name", jie_name);
                        intent.putExtra("jie_address", jie_address);
                        intent.putExtra("jie_longitude", jie_longitude);
                        intent.putExtra("jie_latitude", jie_latitude);
                        String trim = tv_yuyue_time.getText().toString().trim();
                        String trim_daijiao = tv_choose_time.getText().toString().trim();
                        intent.putExtra("appointed_time", appointed_time);
                        intent.putExtra("yuyue_time", trim);
                        intent.putExtra("daijiao_time", trim_daijiao);
                        intent.putExtra("daijiao_phone", daijiao_phone);
                        intent.putExtra("yc_yy", yc_yy);
                        startActivity(intent);
                    }
                    break;
                case 444:
                    daijiao_phone = extras.getString("daijiao_phone");
                    String daijiao_name = extras.getString("daijiao_name");
                    if (!TextUtils.isEmpty(daijiao_phone)) {
                        tv_choose_people.setText(daijiao_phone +"  ");
                        daijiao_line.setVisibility(View.VISIBLE);
                        tv_choose_time.setVisibility(View.VISIBLE);
                    }else {
                        tv_choose_people.setText("选择乘车人  ");
                    }
                    break;
            }
        }

    }

    private void initData() {
        area_id = PrefUtils.getString(getActivity(), "area_id", "");
        getPingTaiConstants();
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        //隐藏百度logo
        mMapView.removeViewAt(1);
        user_id = PrefUtils.getString(App.context, "user_id", "");
        dj_xy = PrefUtils.getString(App.context, "dj_xy", "0");
        if ("0".equals(dj_xy)) {
            showXY();
        }
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(14.0f));
        MapStatus.Builder builder1 = new MapStatus.Builder();
        builder1.zoom(14.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
        // 修改为自定义marker
        //    mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.qi_pic);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        addMarker();
        UiSettings settings = mBaiduMap.getUiSettings();
        settings.setOverlookingGesturesEnabled(false);//屏蔽双指下拉时变成3D地图
        settings.setRotateGesturesEnabled(false);   //屏蔽旋转
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            //地图状态开始改变。
            public void onMapStatusChangeStart(MapStatus status) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            //地图状态改变结束
            public void onMapStatusChangeFinish(MapStatus status) {
                //改变结束之后，获取地图可视范围的中心点坐标
                LatLng latLng = status.target;
                //拿到经纬度之后，就可以反地理编码获取地址信息了
                initGeoCoder(latLng);
            }

            //地图状态变化中
            public void onMapStatusChange(MapStatus status) {
            }
        });
    }

    private void showXY() {
        //请您先同意学易车APP平台《法律条款》《平台规则》《用户使用协议》     同意  |  查看
        View view = LayoutInflater.from(App.context).inflate(R.layout.quxiao_indent_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_tishi_content = (TextView) view.findViewById(R.id.tv_tishi_content);
        tv_title.setVisibility(View.INVISIBLE);
        tv_queren.setTextColor(Color.parseColor("#ffb10c"));
        tv_queren.setText("查看");
        tv_quxiao.setText("同意");
        tv_tishi_content.setText("请您先同意学易车APP平台《法律条款》《平台规则》《用户使用协议》");
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
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
                PrefUtils.putString(App.context, "dj_xy", "1");
                dialog01.dismiss();

            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UrlActivity.class);
                intent.putExtra("url", "http://xueyiche.cn/xyc/instructions/index.html");
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    private void addMarker() {
        OkHttpUtils.post().url(AppUrl.DaiJia_location)
                .addParams("device_id", LoginUtils.getId(getActivity()))
                .addParams("user_id", user_id)
                .addParams("area_id", area_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    DaiJiaDriverLoactionBean daiJiaDriverLoactionBean = JsonUtil.parseJsonToBean(string, DaiJiaDriverLoactionBean.class);
                    if (daiJiaDriverLoactionBean != null) {
                        int code = daiJiaDriverLoactionBean.getCode();
                        if (200 == code) {
                            List<DaiJiaDriverLoactionBean.ContentBean> content = daiJiaDriverLoactionBean.getContent();
                            mBaiduMap.clear();//先清除一下图层
                            LatLng latLng = null;
                            Marker marker = null;
                            OverlayOptions options = null;
                            if (content != null & content.size() > 0)
                                for (DaiJiaDriverLoactionBean.ContentBean contentBean : content) {
                                    String latitude = contentBean.getLatitude();
                                    String longitude = contentBean.getLongitude();
                                    if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)) {
                                        double la = Double.parseDouble(latitude);
                                        double lo = Double.parseDouble(longitude);
                                        latLng = new LatLng(la, lo);
                                        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.daijia_pic);
                                        options = new MarkerOptions().position(latLng).icon(bitmap).zIndex(14);
                                        marker = (Marker) mBaiduMap.addOverlay(options);//将覆盖物添加到地图上
                                        Bundle bundle = new Bundle();//创建一个Bundle对象将每个mark具体信息传过去，当点击该覆盖物图标的时候就会显示该覆盖物的详细信息
                                        bundle.putSerializable("baidumapjson", contentBean);
                                        marker.setExtraInfo(bundle);
                                    }
                                }
                            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);//通过这个经纬度对象，地图就可以定位到该点
                            mBaiduMap.animateMapStatus(msu);
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
                Intent intent = new Intent(getActivity(), JinjiPhoneActivity.class);
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
                //web页
                Intent intent = new Intent(getActivity(), UrlActivity.class);
                intent.putExtra("url", "http://xueyiche.cn/xyc/instructions/instructions.html");
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });

    }


    public void getPingTaiConstants() {
        showProgressDialog(getActivity(),false);
        OkHttpUtils.post()
                .url(AppUrl.PingTai_Constants)
                .addParams("area_id", area_id)
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
                        stopProgressDialog();
                    }

                    @Override
                    public void onResponse(Object response) {
                        stopProgressDialog();
                    }
                });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.daijia_activity,null);
        initView(view);
        initListener();
        initData();
        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "daijia";
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        private MyLocationData locData;

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
                builder.target(ll).zoom(15.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }
}
