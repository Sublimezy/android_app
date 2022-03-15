package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.daijia.view.DrivingRouteOverlay;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.utils.BaiduLocation;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/19.
 */
public class WaitActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_quxia;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private RoutePlanSearch mSearch;
    private String user_id;
    private String order_number;
    private long tiam_hm_c;
    private Timer mTimer2;
    private TimerTask mTask2;
    public static WaitActivity instance;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private ImageView iv_back;
    // UI相关
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    private String order_time1;

    @Override
    protected int initContentView() {
        return R.layout.wait_activity;
    }

    @Override
    protected void initView() {
        instance = this;
        mMapView =view.findViewById(R.id.map_wait);
        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_WHITE);
        mMapView.setCustomMapStylePath(customStyleFilePath);
        mMapView.setMapCustomEnable(true);
        tv_quxia = (TextView) view.findViewById(R.id.tv_quxia);
        iv_back = view.findViewById(R.id.iv_back);
    }

    @Override
    protected void initListener() {
        tv_quxia.setOnClickListener(this);
        iv_back.setOnClickListener(this);
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
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                return null;
            }
        }

        return parentPath + "/" + customStyleFileName;
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新代驾", msg)) {
            JieDanActivity.instance.finish();
            Intent intent = new Intent(this, JinXingActivity.class);
            intent.putExtra("order_number", order_number);
            startActivity(intent);
            finish();
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
        mMapView.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    private InfoWindow mInfoWindow;

    @Override
    protected void initData() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        order_number = getIntent().getStringExtra("order_number");

        // 地图初始化
        mBaiduMap = mMapView.getMap();
        UiSettings settings = mBaiduMap.getUiSettings();
        settings.setOverlookingGesturesEnabled(false);//屏蔽双指下拉时变成3D地图
        settings.setRotateGesturesEnabled(false);   //屏蔽旋转
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);

        //隐藏百度logo
        mMapView.removeViewAt(1);
        mSearch = RoutePlanSearch.newInstance();
        MapStatus.Builder builder1 = new MapStatus.Builder();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));


        getOrder();
        OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                //创建DrivingRouteOverlay实例
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                List<DrivingRouteLine> routeLines = drivingRouteResult.getRouteLines();
                if (routeLines != null) {
                    if (routeLines.size() > 0) {
                        //获取路径规划数据,(以返回的第一条路线为例）
                        //为DrivingRouteOverlay实例设置数据
                        overlay.setData(drivingRouteResult.getRouteLines().get(0));
                        //在地图上绘制DrivingRouteOverlay
                        //两点之间的公里数 单位 米
                        int distance = drivingRouteResult.getRouteLines().get(0).getDistance();
                        int[] aa = new int[]{10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
                        int[] bb = new int[]{20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3};
                        int level = 5;
                        for (int i = 0; i < aa.length; i++) {
                            if (distance > aa[i] && aa[i + 1] > distance) {
                                level = i-1;
                            }
                        }
                        if (level>0) {
                            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(bb[level > 0 ? level : 0]).build()));
                        }
                        overlay.addToMap();
                    }
                }
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }

        };
        mSearch.setOnGetRoutePlanResultListener(listener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quxia:
                //取消订单  弹窗确认
                showQuXiaoIndent();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        // 退出时销毁定位
        // 关闭定位图层
        MapView.setMapCustomEnable(false);
        mBaiduMap.setMyLocationEnabled(false);
        super.onPause();
    }


    public void getOrder() {
        OkHttpUtils.post().url(AppUrl.Get_Order)
                .addParams("order_number", order_number)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    OrderInfoBean orderInfoBean = JsonUtil.parseJsonToBean(string, OrderInfoBean.class);
                    if (orderInfoBean != null) {
                        int code = orderInfoBean.getCode();
                        if (200 == code) {
                            final OrderInfoBean.ContentBean content = orderInfoBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String down_longitude = content.getDown_longitude();
                                        String down_latitude = content.getDown_latitude();
                                        String on_latitude = content.getOn_latitude();
                                        String on_longitude = content.getOn_longitude();
                                        order_time1 = content.getOrder_time1();
                                        LatLng latLng = null;
                                        Marker marker = null;
                                        OverlayOptions options = null;
                                        BaiduLocation baidu = new BaiduLocation();
                                        baidu.baiduLocation();
                                        String x = PrefUtils.getString(App.context, "x", "");
                                        String y = PrefUtils.getString(App.context, "y", "");
                                        double la = Double.parseDouble(y);
                                        double lo = Double.parseDouble(x);
                                        latLng = new LatLng(la, lo);
                                        View inflate = LayoutInflater.from(App.context).inflate(R.layout.mark_view_layout, null);
                                        Bitmap bitmapFromView = getBitmapFromView(inflate);
                                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmapFromView);
                                        options = new MarkerOptions().position(latLng).icon(bitmapDescriptor);
                                        marker = (Marker) mBaiduMap.addOverlay(options);//将覆盖物添加到地图上
                                        LatLng latLng1 = marker.getPosition();
                                        View viewTop = LayoutInflater.from(App.context).inflate(R.layout.map_top_bg, null);
                                        mInfoWindow = new InfoWindow(viewTop, latLng1, -200);
                                        // 显示 InfoWindow, 该接口会先隐藏其他已添加的InfoWindow, 再添加新的InfoWindow

                                        final TextView tv_time = (TextView) viewTop.findViewById(R.id.tv_time);
                                        if (mTimer2 == null && mTask2 == null) {
                                            mTimer2 = new Timer();
                                            mTask2 = new TimerTask() {
                                                @Override
                                                public void run() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (!TextUtils.isEmpty(order_time1)) {
                                                                //下单时间
                                                                long stringToHm = DateUtils.getStringToHm(order_time1);
                                                                //当前时间
                                                                long time = System.currentTimeMillis();
                                                                tiam_hm_c = time - stringToHm;
                                                                long hours = tiam_hm_c / (1000 * 60 * 60);
                                                                long minutes = (tiam_hm_c - hours * (1000 * 60 * 60)) / (1000 * 60);
                                                                long miao = (tiam_hm_c - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
                                                                String diffTime = "";
                                                                if (miao < 10 && minutes < 10) {
                                                                    diffTime = "0" + minutes + ":0" + miao;
                                                                } else if (miao > 10 && minutes < 10) {
                                                                    diffTime = "0" + minutes + ":" + miao;
                                                                } else if (miao < 10 && minutes > 10) {
                                                                    diffTime = minutes + ":0" + miao;
                                                                } else if (miao > 10 && minutes > 10) {
                                                                    diffTime = minutes + ":" + miao;
                                                                }
                                                                tv_time.setText(diffTime);
                                                            }
                                                        }
                                                    });
                                                }
                                            };
                                            mTimer2.schedule(mTask2, 0, 1000);
                                        }
                                        mBaiduMap.showInfoWindow(mInfoWindow);

                                        if (!TextUtils.isEmpty(down_longitude) && !TextUtils.isEmpty(down_latitude) && !TextUtils.isEmpty(on_latitude)
                                                && !TextUtils.isEmpty(on_longitude)) {
                                            LatLng qi_latLng = new LatLng(Double.parseDouble(on_latitude), Double.parseDouble(on_longitude));
                                            LatLng zhong_latLng = new LatLng(Double.parseDouble(down_latitude), Double.parseDouble(down_longitude));
                                            Overlay overlay1 = mBaiduMap.addOverlay(new MarkerOptions().position(qi_latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.qi_pic)));
                                            Overlay overlay2 = mBaiduMap.addOverlay(new MarkerOptions().position(zhong_latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.zhong_pic)));
                                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                            builder.include(((Marker) overlay1).getPosition());
                                            builder.include(((Marker) overlay2).getPosition());
                                            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
                                            PlanNode stNode = PlanNode.withLocation(qi_latLng);
                                            PlanNode enNode = PlanNode.withLocation(zhong_latLng);
                                            mSearch.drivingSearch((new DrivingRoutePlanOption())
                                                    .from(stNode)
                                                    .to(enNode));
                                        }
                                    }
                                });
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

    private Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        LatLng latLng = new LatLng(mCurrentLat, mCurrentLon);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(mapStatusUpdate, 1000);


    }


    //取消订单
    public void showQuXiaoIndent() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.quxiao_indent_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
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
                dialog01.dismiss();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消接口
                quxiaoOrder(dialog01);
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    public void quxiaoOrder(final AlertDialog dialog01) {
        OkHttpUtils.post().url(AppUrl.Quxiao_Indent)
                .addParams("device_id", LoginUtils.getId(WaitActivity.this))
                .addParams("user_id", user_id)
                .addParams("order_number", order_number)
                .addParams("cancle_reason", "")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                LogUtil.e("22222222222222", string);
                if (!TextUtils.isEmpty(string)) {
                    SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                    int code = successDisCoverBackBean.getCode();
                    final String msg = successDisCoverBackBean.getMsg();
                    if (code == 200) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                dialog01.dismiss();
                                Toast.makeText(WaitActivity.this, msg, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
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
}
