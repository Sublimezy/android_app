package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.SjLoactionBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.route.DrivingRouteOverLay;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.AMapUtil;
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
import java.math.BigDecimal;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/23.
 */
public class JinXingActivity extends BaseMapActivity {

    public static JinXingActivity instance;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getSj();
            handler.postDelayed(this, 5000);
        }
    };


    public static void forward(Context context) {
        Intent intent = new Intent(context, JinXingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int initContentView() {
        return R.layout.jinxin_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        instance = this;
    }


    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新代驾", msg)) {

        } else if (TextUtils.equals("代驾结束", msg)) {
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra("order_number", "order_number");
            startActivity(intent);
            finish();
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
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {


    }


    public void getSj() { handler.postDelayed(runnable, 5000);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                LatLonPoint mStartPoint = new LatLonPoint(latitude, longitude);//起点，116.335891,39.942295
                LatLonPoint mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，116.481288,39.995576
                setfromandtoMarker(aMap,mStartPoint, mEndPoint);
                searchRouteResult(2, RouteSearch.DrivingDefault, mStartPoint, mEndPoint);
//


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
                    List<LatLonPoint> polyline = drivePath.getPolyline();
                    int dur = (int) drivePath.getDuration();
                    //35分钟（19公里）
                    String lenthGL = AMapUtil.getFriendlyLength(dis);
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                }
            }
        }
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//            case R.id.now_location:
//                break;
//            case R.id.tv_pay:
//                //支付
//                break;
//            case R.id.iv_anquan:
//                showAnQuan();
//                break;
//            case R.id.tv_share:
//                XueYiCheUtils.showShareAppCommon(App.context,JinXingActivity.this,"分享该行程","","","","");
//                break;
//            case R.id.iv_call:
//                //打电话
//                if (!TextUtils.isEmpty(driver_phone)) {
//                    if (driver_phone.length() != 11) {
//                        String decrypt = AES.decrypt(driver_phone);
//                        XueYiCheUtils.CallPhone(this, "拨打代驾电话", decrypt);
//                    } else {
//                        XueYiCheUtils.CallPhone(this, "拨打代驾电话", driver_phone);
//                    }
//                }
//                break;
//        }
//    }

}
