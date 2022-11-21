package com.xueyiche.zjyk.xueyiche.practicecar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.example.qrcode.Constant;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.activity.ChooseDaijiaYuanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.DaiJiaoActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.LocationSearchActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.YuGuFeiActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.BuyOrderBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.NearDrivingBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.YuSuanBean;
import com.xueyiche.zjyk.xueyiche.daijia.view.YuYueLinkagePicker;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.homepage.view.OptionPicker;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.DanJiaBean;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderNumberBean;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;
import com.xueyiche.zjyk.xueyiche.route.DrivingRouteOverLay;
import com.xueyiche.zjyk.xueyiche.utils.AMapUtil;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.PayUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * * #
 * #            Created by 張某人 on 2022/4/2/11:14 下午 .
 * #            com.xueyiche.zjyk.xueyiche
 * #            xueyiche5.0
 */
public class PracticeCarFirstStepActivity extends BaseMapActivity {

    @BindView(R.id.tv_qidian)
    TextView tvQidian;

    @BindView(R.id.tvChoodeTime)
    TextView tvChoodeTime;
    @BindView(R.id.tv_yuyue_time)
    TextView tvYuyueTime;
    @BindView(R.id.tv_choose_people)
    TextView tvChoosePeople;
    @BindView(R.id.tv_xiadan)
    TextView tv_xiadan;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_choose_daijia)
    TextView tv_choose_daijia;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    private int unit_price = 1;
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
    private String hour_num = "";

    @Override
    protected int initContentView() {
        return R.layout.practicecar_first_step_activity;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(context, PracticeCarFirstStepActivity.class);
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

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("支付成功", msg)) {
            ToastUtils.showToast(PracticeCarFirstStepActivity.this, "支付成功！");
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
    protected void initData() {
        tv_title.setText("有证练车");
        getDanJia();
        getDataDriverList();
    }

    private void getDanJia() {
        Map<String, String> map = new HashMap<>();
        MyHttpUtils.postHttpMessage(AppUrl.unit_price, map, DanJiaBean.class, new RequestCallBack<DanJiaBean>() {
            @Override
            public void requestSuccess(DanJiaBean json) {
                if (1 == json.getCode()) {
                    DanJiaBean.DataDTO data = json.getData();
                    if (data != null) {
                        unit_price = data.getUnit_price();

                    }
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    private MarkerOptions markerOption;
    private Marker marker;

    private void getDataDriverList() {
        new GDLocation().startLocation();
        String lat = PrefUtils.getParameter("lat");
        String lon = PrefUtils.getParameter("lon");
        Map<String, String> map = new HashMap<>();
        map.put("address_lng", "" + lon);
        map.put("address_lat", "" + lat);
        MyHttpUtils.postHttpMessage(AppUrl.near_practice, map, NearDrivingBean.class, new RequestCallBack<NearDrivingBean>() {
            @Override
            public void requestSuccess(NearDrivingBean json) {
                if (1 == json.getCode()) {
                    NearDrivingBean.DataBean data = json.getData();
                    if (data != null) {
                        List<NearDrivingBean.DataBean.UserListBean> user_list = data.getUser_list();
                        aMap.moveCamera(CameraUpdateFactory.zoomTo(15.5f));
                        if (user_list.size() > 0) {
                            for (int i = 0; i < user_list.size(); i++) {
                                LatLng latlng = new LatLng(Double.parseDouble(user_list.get(i).getUser_lat()), Double.parseDouble(user_list.get(i).getUser_lng()));
                                View viewCat = LayoutInflater.from(PracticeCarFirstStepActivity.this).inflate(R.layout.item_daijia_fujin, null);
                                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
                                Bitmap bitmap = convertViewToBitmap(viewCat);
                                markerOption = new MarkerOptions()
                                        .position(latlng)
                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                        .draggable(false);
                                marker = aMap.addMarker(markerOption);
                                marker.setObject(user_list.get(i));
                            }
                        }
                    }
                }

            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    //view 转bitmap
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
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
                    ToastUtils.showToast(PracticeCarFirstStepActivity.this, "" + result);

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
        if (!TextUtils.isEmpty(sLat) && !TextUtils.isEmpty(sLon)) {
            tv_xiadan.setVisibility(View.VISIBLE);
            tv_choose_daijia.setVisibility(View.GONE);

//            LatLonPoint mStartPoint = new LatLonPoint(Double.parseDouble(sLat), Double.parseDouble(sLon));//起点，116.335891,39.942295
//            LatLonPoint mEndPoint = new LatLonPoint(Double.parseDouble(eLat), Double.parseDouble(eLon));//终点，116.481288,39.995576
//            setfromandtoMarker(aMap, mStartPoint, mEndPoint);
//            searchRouteResult(2, RouteSearch.DrivingDefault, mStartPoint, mEndPoint);
        }
    }


    @OnClick({R.id.iv_user, R.id.tv_choose_daijia, R.id.tv_xiadan, R.id.tv_choose_people, R.id.tvChoodeTime, R.id.tv_yuyue_time, R.id.ll_common_back, R.id.rb_richang, R.id.rb_yuyue, R.id.tv_qidian})
    public void onClick(View view) {
        switch (view.getId()) {

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
                PracticeCarSecondStepActivity.forward(PracticeCarFirstStepActivity.this);
                break;

            case R.id.tv_qidian:
                LocationSearchActivity.forward(PracticeCarFirstStepActivity.this, 111, "start");
                break;
            case R.id.tvChoodeTime:
                chooseTime();
                break;

            case R.id.tv_yuyue_time:
                startTimePicker();
                break;
            case R.id.tv_choose_people:
                DaiJiaoActivity.forward(PracticeCarFirstStepActivity.this, 333);
                break;
            case R.id.tv_choose_daijia:
                ChooseDaijiaYuanActivity.forward(PracticeCarFirstStepActivity.this, 444);
                break;
            case R.id.tv_xiadan:
                //下单
                xiadan();
                break;
        }
    }

    private void xiadan() {
        if (XueYiCheUtils.IsLogin()) {
            Calendar selectedDate = Calendar.getInstance();
            int nian = selectedDate.get(Calendar.YEAR);
            int yue = selectedDate.get(Calendar.MONTH);
            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
            int hour = selectedDate.get(Calendar.HOUR_OF_DAY);
            int min = selectedDate.get(Calendar.MINUTE);
            if (TextUtils.isEmpty(hour_num)) {
                ToastUtils.showToast(PracticeCarFirstStepActivity.this, "选择练车时长！");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("order_type", "3");
            map.put("start_address", "" + sLocation);
            map.put("start_address_lng", "" + sLon);
            map.put("start_address_lat", "" + sLat);
            map.put("practice_id", "");
            map.put("hour_num", "" + hour_num);
            map.put("fixed_time", nian + "年" + yue + "月" + day + "日" + hour + ":" + min);
            MyHttpUtils.postHttpMessage(AppUrl.orderSavePractice, map, OrderNumberBean.class, new RequestCallBack<OrderNumberBean>() {
                @Override
                public void requestSuccess(OrderNumberBean json) {
                    if (1 == json.getCode()) {
                        OrderNumberBean.DataDTO data = json.getData();
                        if (data != null) {
                            String order_sn = data.getOrder_sn();
                            if (!TextUtils.isEmpty(order_sn)) {
                                PayUtils.showPopupWindow(AppUrl.Pay_Order_One, PracticeCarFirstStepActivity.this, order_sn, "daijia");
                            }
                        }
                    }
                    ToastUtils.showToast(PracticeCarFirstStepActivity.this, json.getMsg());
                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
        } else {
            LoginFirstStepActivity.forward(PracticeCarFirstStepActivity.this);
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


    private void chooseTime() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 2; i < 13; i++) {
            list.add("" + i + "小时");
        }
        final OptionPicker picker = new OptionPicker(PracticeCarFirstStepActivity.this, list);
        picker.setOffset(1);
        picker.setSelectedIndex(0);
        picker.setTextSize(15);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));
        picker.setTitleText("请选择练车时长");
        picker.setTitleTextSize(20);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                picker.setSelectedIndex(position);
                int total = (position + 2) * unit_price;
                tvChoodeTime.setText(option+"（预估费用约"+total+"元）");
                hour_num = "" + (position + 2);

                next();
            }

        });
        picker.show();
    }
}
