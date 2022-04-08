package com.xueyiche.zjyk.xueyiche.daijia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RouteSearch;
import com.example.qrcode.Constant;
import com.example.qrcode.ScannerActivity;
import com.google.android.exoplayer2.upstream.HttpUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.daijia.activity.ChooseDaijiaYuanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.DaiJiaoActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.LocationSearchActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.XingChengActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.BuyOrderBean;
import com.xueyiche.zjyk.xueyiche.daijia.view.YuYueLinkagePicker;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;
import com.xueyiche.zjyk.xueyiche.route.DrivingRouteOverLay;
import com.xueyiche.zjyk.xueyiche.utils.AMapUtil;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DensityUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 2022/4/2/11:14 下午 .
 * #            com.xueyiche.zjyk.xueyiche
 * #            xueyiche5.0
 */
public class DaiJiaActivity extends BaseMapActivity {
    @BindView(R.id.iv_daijia_banner)
    CustomShapeImageView ivDaijiaBanner;
    @BindView(R.id.tv_qidian)
    TextView tvQidian;
    @BindView(R.id.tv_mudi)
    TextView tvMudi;
    @BindView(R.id.tv_yuyue_time)
    TextView tvYuyueTime;
    @BindView(R.id.tv_choose_people)
    TextView tvChoosePeople;
    @BindView(R.id.tv_xiadan)
    TextView tv_xiadan;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_yh)
    TextView tv_yh;
    @BindView(R.id.tv_choose_daijia)
    TextView tv_choose_daijia;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.rl_money)
    RelativeLayout rl_money;
    private String sLocation = "";
    private String sLat = "";
    private String sLon = "";
    private String eLocation = "";
    private String eLat = "";
    private String eLon = "";
    private String order_type = "0";
    private String choose_time = "";
    private String driving_id = "";
    private String daijiao_phone = "";
    private String daijiao_name = "";

    @Override
    protected int initContentView() {
        return R.layout.daijia_activity;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(context, DaiJiaActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        ImmersionBar.with(this).titleBar(rl_title).statusBarDarkFont(true).init();
        initData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tv_title.setText("代驾");
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
                if (!TextUtils.isEmpty("" + latitude)) {
                    sLat = "" + latitude;
                }
                if (!TextUtils.isEmpty("" + longitude)) {
                    sLon = "" + longitude;
                }
                if (!TextUtils.isEmpty(aMapLocation.getAddress())) {
                    sLocation = aMapLocation.getAddress();
                }
                tvQidian.setText(sLocation);
                Log.e("onLocationChanged", "" + city);
                Log.e("onLocationChanged", "" + cityCode);

            }
        }
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
                }
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle extras = data.getExtras();
            switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
                case 111:
                    String latitude = extras.getString("lat");
                    String longitude = extras.getString("lon");
                    String address = extras.getString("title");
                    if (!TextUtils.isEmpty(latitude)) {
                        sLat = latitude;
                    }
                    if (!TextUtils.isEmpty(longitude)) {
                        sLon = longitude;
                    }
                    if (!TextUtils.isEmpty(address)) {
                        sLocation = address;
                    }
                    if (!TextUtils.isEmpty(sLocation)) {
                        tvQidian.setText(sLocation);
                    }
                    next();
                    break;
                case 222:
                    String latitude1 = extras.getString("lat");
                    String longitude1 = extras.getString("lon");
                    String address1 = extras.getString("title");
                    if (!TextUtils.isEmpty(latitude1)) {
                        eLat = latitude1;
                    } else {
                        eLat = "";
                    }
                    if (!TextUtils.isEmpty(longitude1)) {
                        eLon = longitude1;
                    } else {
                        eLon = "";

                    }
                    if (!TextUtils.isEmpty(address1)) {
                        eLocation = address1;
                    } else {
                        eLocation = "";
                    }
                    if (!TextUtils.isEmpty(eLocation)) {
                        tvMudi.setText(eLocation);
                    }
                    next();
                    break;
                case 333:
                    daijiao_phone = extras.getString("daijiao_phone");
                    daijiao_name = extras.getString("daijiao_name");
                    if (!TextUtils.isEmpty(daijiao_name)) {
                        tvChoosePeople.setText("" + daijiao_name + "  " + daijiao_phone);
                    }
                    break;
                case -1:
                    String result = data.getExtras().getString(Constant.EXTRA_RESULT_CONTENT);
                    if (TextUtils.isEmpty(result)) {
                        return;
                    }
                    ToastUtils.showToast(DaiJiaActivity.this, "" + result);

                    break;
                case 444:
                    driving_id = extras.getString("driving_id");
                    String name = extras.getString("name");
                    String user_number = extras.getString("user_number");
                    if (!TextUtils.isEmpty(name)) {
                        tv_choose_daijia.setText(name + " " + user_number);
                    }
                    break;

            }
        }

    }

    private void next() {
        //订单类型 0日常 1预约 2代叫
        if (!TextUtils.isEmpty(sLat) && !TextUtils.isEmpty(eLat)) {
            tv_xiadan.setVisibility(View.VISIBLE);
            tv_choose_daijia.setVisibility(View.VISIBLE);
            rl_money.setVisibility(View.VISIBLE);

            LatLonPoint mStartPoint = new LatLonPoint(Double.parseDouble(sLat), Double.parseDouble(sLon));//起点，116.335891,39.942295
            LatLonPoint mEndPoint = new LatLonPoint(Double.parseDouble(eLat), Double.parseDouble(eLon));//终点，116.481288,39.995576
            setfromandtoMarker(aMap, mStartPoint, mEndPoint);
            searchRouteResult(2, RouteSearch.DrivingDefault, mStartPoint, mEndPoint);
        }
    }

    @OnClick({R.id.iv_anquan, R.id.iv_user, R.id.tv_choose_daijia, R.id.tv_xiadan, R.id.tv_choose_people, R.id.tv_mudi, R.id.tv_yuyue_time, R.id.ll_common_back, R.id.rb_richang, R.id.rb_yuyue, R.id.rb_daijiao, R.id.tv_qidian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_anquan:
                AppUtils.showAnQuan(DaiJiaActivity.this);
                break;
            case R.id.iv_user:
                userLocation();
                break;
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.rb_richang:
                order_type = "0";
                tvYuyueTime.setVisibility(View.GONE);
                tvChoosePeople.setVisibility(View.GONE);
                tv_xiadan.setText("立即下单");
                break;
            case R.id.rb_yuyue:
                order_type = "1";
                tvYuyueTime.setVisibility(View.VISIBLE);
                tvChoosePeople.setVisibility(View.GONE);
                tv_xiadan.setText("预约下单");
                break;
            case R.id.rb_daijiao:
                order_type = "2";
                tvChoosePeople.setVisibility(View.VISIBLE);
                tvYuyueTime.setVisibility(View.GONE);
                tv_xiadan.setText("立即下单");
                break;
            case R.id.tv_qidian:
                LocationSearchActivity.forward(DaiJiaActivity.this, 111, "start");
                break;
            case R.id.tv_mudi:
                LocationSearchActivity.forward(DaiJiaActivity.this, 222, "end");
                break;
            case R.id.tv_yuyue_time:
                startTimePicker();
                break;
            case R.id.tv_choose_people:
                DaiJiaoActivity.forward(DaiJiaActivity.this, 333);
                break;
            case R.id.tv_choose_daijia:
                ChooseDaijiaYuanActivity.forward(DaiJiaActivity.this, 444);
                break;
            case R.id.tv_xiadan:
                //下单
                buy();
                break;
        }
    }

    private void buy() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            if ("2".equals(order_type)) {
                if (TextUtils.isEmpty(daijiao_phone)) {
                    ToastUtils.showToast(DaiJiaActivity.this,"请选择乘车人！");
                    return;
                }

            }
            if ("1".equals(order_type)) {
                if (TextUtils.isEmpty(choose_time)) {
                    ToastUtils.showToast(DaiJiaActivity.this,"请选择预约时间！");
                    return;
                }
            }
            showProgressDialog(false);
            Map<String, String> map = new HashMap<>();
            map.put("order_type", "" + order_type);
            map.put("start_address", "" + sLocation);
            map.put("start_address_lng", "" + sLon);
            map.put("start_address_lat", "" + sLat);
            map.put("end_address", "" + eLocation);
            map.put("end_address_lng", "" + eLon);
            map.put("end_address_lat", "" + eLat);
            map.put("driving_id", "" + driving_id);
            if ("2".equals(order_type)) {
                map.put("call_name", "" + daijiao_name);
                map.put("call_mobile", "" + daijiao_phone);
            }
            if ("1".equals(order_type)) {
                map.put("fixed_time", "" + choose_time);
            }
            MyHttpUtils.postHttpMessage(AppUrl.orderSave, map, BuyOrderBean.class, new RequestCallBack<BuyOrderBean>() {
                @Override
                public void requestSuccess(BuyOrderBean json) {
                    if (1 == json.getCode()) {

                    }
                    ToastUtils.showToast(DaiJiaActivity.this, "" + json.getMsg());
                    stopProgressDialog();
                }

                @Override
                public void requestError(String errorMsg, int errorType) {
                    stopProgressDialog();
                }
            });
        } else {
            ToastUtils.showToast(DaiJiaActivity.this, "请检查网络连接！");
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
        firstList.add(month + "月" + day + "日");
        firstList.add(month1 + "月" + day1 + "日");
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
                choose_time = "" + year + "年" + first + second + ":" + third;
                Log.e("choose_time", "" + choose_time);
                tvYuyueTime.setText(first + second + ":" + third);
                picker.setSelectedItem(first, second, third);
                picker.dismiss();
            }
        });
        picker.show();
    }
}
