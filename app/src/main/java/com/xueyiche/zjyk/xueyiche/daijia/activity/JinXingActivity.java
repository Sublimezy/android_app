package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.IndentContentBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.SjLoactionBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.route.DrivingRouteOverLay;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.AMapUtil;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/23.
 */
public class JinXingActivity extends BaseMapActivity {
    private MarkerOptions markerOption;
    private String order_sn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.ci_head)
    CircleImageView ciHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_gonghao)
    TextView tvGonghao;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    private String user_mobile;
    public static JinXingActivity instance;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getDataFromNetNext();
                    break;
                default:
                    break;
            }
        }


    };

    public static void forward(Context context, String order_sn) {
        Intent intent = new Intent(context, JinXingActivity.class);
        intent.putExtra("order_sn", order_sn);
        context.startActivity(intent);
    }

    @Override
    protected int initContentView() {
        return R.layout.jinxin_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        ImmersionBar.with(this).titleBar(rlTitle).keyboardEnable(true).init();
        instance = this;
    }

    private void getDataFromNetNext() {
        Map<String, String> params = new HashMap<>();
        params.put("order_sn", "" + order_sn);
        MyHttpUtils.postHttpMessage(AppUrl.orderDetails, params, IndentContentBean.class, new RequestCallBack<IndentContentBean>() {
            @Override
            public void requestSuccess(IndentContentBean json) {
                if (1 == json.getCode()) {
                    IndentContentBean.DataBean data = json.getData();
                    int order_status = data.getOrder_status();
                    tvMoney.setText("¥" + data.getTotal_price());
                    new GDLocation().startLocation();
                    String lat = PrefUtils.getParameter("lat");
                    String lon = PrefUtils.getParameter("lon");
                    String end_address_lat = data.getEnd_address_lat();
                    String end_address_lng = data.getEnd_address_lng();
                    LatLonPoint mStartPoint = new LatLonPoint(Double.parseDouble(lat), Double.parseDouble(lon));
                    LatLonPoint mEndPoint = new LatLonPoint(Double.parseDouble(end_address_lat), Double.parseDouble(end_address_lng));
                    setfromandtoMarker(aMap, mStartPoint, mEndPoint);
                    searchRouteResult(2, RouteSearch.DrivingDefault, mStartPoint, mEndPoint);
                    switch (order_status) {
                        case 4:
                            timer.cancel();
                            EndActivity.forward(JinXingActivity.this, order_sn);
                            finish();
                            break;
                        case 5:
                            timer.cancel();
                            EndActivity.forward(JinXingActivity.this, order_sn);
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

    private void getDataFromNet() {
        showProgressDialog(false);
        Map<String, String> params = new HashMap<>();
        params.put("order_sn", "" + order_sn);
        MyHttpUtils.postHttpMessage(AppUrl.orderDetails, params, IndentContentBean.class, new RequestCallBack<IndentContentBean>() {
            @Override
            public void requestSuccess(IndentContentBean json) {
                if (1 == json.getCode()) {
                    IndentContentBean.DataBean data = json.getData();
                    Glide.with(App.context).load(data.getHead_img()).error(R.mipmap.mine_head).placeholder(R.mipmap.mine_head).into(ciHead);
                    tvName.setText("" + data.getUser_name());
                    user_mobile = data.getUser_mobile();
                    tvGonghao.setText("工号：" + data.getUser_number());
                    tvMoney.setText("¥" + data.getTotal_price());
                    new GDLocation().startLocation();
                    String lat = PrefUtils.getParameter("lat");
                    String lon = PrefUtils.getParameter("lon");
                    String end_address_lat = data.getEnd_address_lat();
                    String end_address_lng = data.getEnd_address_lng();
                    LatLonPoint mStartPoint = new LatLonPoint(Double.parseDouble(lat), Double.parseDouble(lon));
                    LatLonPoint mEndPoint = new LatLonPoint(Double.parseDouble(end_address_lat), Double.parseDouble(end_address_lng));
                    setfromandtoMarker(aMap, mStartPoint, mEndPoint);
                    searchRouteResult(2, RouteSearch.DrivingDefault, mStartPoint, mEndPoint);
                }
                stopProgressDialog();
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                stopProgressDialog();
            }
        });


    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("结束行程", msg)) {
            EndActivity.forward(JinXingActivity.this, order_sn);
            if (timer != null) {
                timer.cancel();
            }
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

    Timer timer;

    @Override
    protected void initData() {
        tvTitle.setText("服务中");
        order_sn = getIntent().getStringExtra("order_sn");
        getDataFromNet();
        getDataFromNetNext();
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
        if (timer != null) {
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
                    tvDistance.setText(AMapUtil.getFriendlyTime(dur) + "  " + AMapUtil.getFriendlyLength(dis));
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                }
            }
        }
    }


    @OnClick({R.id.ll_common_back, R.id.now_location, R.id.tv_right_btn, R.id.tvCallPhone, R.id.iv_anquan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.iv_anquan:
                AppUtils.showAnQuan(JinXingActivity.this);
                break;
            case R.id.now_location:
                userLocation();
                break;
            case R.id.tvCallPhone:
                XueYiCheUtils.CallPhone(JinXingActivity.this, "拨打电话？", "" + user_mobile);
                break;
        }
    }

}
