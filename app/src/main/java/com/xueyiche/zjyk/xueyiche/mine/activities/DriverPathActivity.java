package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.query.entity.HistoryTrack;
import com.amap.api.track.query.entity.Point;
import com.amap.api.track.query.entity.TrackPoint;
import com.amap.api.track.query.model.AddTerminalResponse;
import com.amap.api.track.query.model.AddTrackResponse;
import com.amap.api.track.query.model.DistanceResponse;
import com.amap.api.track.query.model.HistoryTrackRequest;
import com.amap.api.track.query.model.HistoryTrackResponse;
import com.amap.api.track.query.model.LatestPointResponse;
import com.amap.api.track.query.model.OnTrackListener;
import com.amap.api.track.query.model.ParamErrorResponse;
import com.amap.api.track.query.model.QueryTerminalResponse;
import com.amap.api.track.query.model.QueryTrackResponse;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.activity.ArrivedActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.CancelOrderActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.IndentContentBean;
import com.xueyiche.zjyk.xueyiche.mine.bean.OrderDetailBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/19.
 */
public class DriverPathActivity extends BaseMapActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private AMapTrackClient aMapTrackClient;
    String order_sn = "";
    private List<Polyline> polylines = new LinkedList<>();
    private List<Marker> endMarkers = new LinkedList<>();

    @Override
    protected int initContentView() {
        return R.layout.driver_path_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).keyboardEnable(true).init();
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        aMapTrackClient = new AMapTrackClient(getApplicationContext());
    }

    public static void forward(Context context, String order_sn) {
        Intent intent = new Intent(context, DriverPathActivity.class);
        intent.putExtra("order_sn", order_sn);
        context.startActivity(intent);
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新代驾", msg)) {
//            getOrder();
        } else if (TextUtils.equals("开始行程", msg)) {
            Intent intent = new Intent(this, JinXingActivity.class);
            intent.putExtra("order_number", "order_number");
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        tvTitle.setText("行驶路线");
        order_sn = getIntent().getStringExtra("order_sn");
        Map<String, String> params = new HashMap<>();
        params.put("order_sn", order_sn);
        MyHttpUtils.postHttpMessage(AppUrl.orderDetails, params, OrderDetailBean.class, new RequestCallBack<OrderDetailBean>() {
            @Override
            public void requestSuccess(OrderDetailBean json) {
                if (json.getCode() == 1) {
                    OrderDetailBean.DataBean data = json.getData();
                    String service_id = data.getService_id();
                    String t_id = data.getT_id();
                    String tr_id = data.getTr_id();
                    String start_time = data.getStart_time();
                    String end_time = data.getEnd_time();
                    try {
                        String s = AppUtils.dateToStamp(start_time);
                        String s1 = AppUtils.dateToStamp(end_time);
                        long l = Long.parseLong(s);
                        long l1 = Long.parseLong(s1);
                        Log.e("dadad", "" + AppUtils.dateToStamp(start_time) + "~~~~~~~" + end_time);

                        clearTracksOnMap();
                        // 搜索最近12小时以内上报的轨迹
                        HistoryTrackRequest historyTrackRequest = new HistoryTrackRequest(
                                Long.parseLong(service_id),
                                Long.parseLong(t_id),
                                l,
                                l1,
                                Integer.parseInt(tr_id),      // 不绑路
                                1,      // 不做距离补偿
                                3000,   // 距离补偿阈值，只有超过5km的点才启用距离补偿
                                0,  // 由旧到新排序
                                1,  // 返回第1页数据
                                999,    // 一页不超过1000条
                                ""  // 暂未实现，该参数无意义，请留空
                        );
                        aMapTrackClient.queryHistoryTrack(historyTrackRequest, new OnTrackListener() {

                            @Override
                            public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {

                            }

                            @Override
                            public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {

                            }

                            @Override
                            public void onDistanceCallback(DistanceResponse distanceResponse) {

                            }

                            @Override
                            public void onLatestPointCallback(LatestPointResponse latestPointResponse) {

                            }

                            @Override
                            public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {
                                if (historyTrackResponse.isSuccess()) {
                                    HistoryTrack historyTrack = historyTrackResponse.getHistoryTrack();
                                    ArrayList<Point> points = historyTrack.getPoints();
                                    drawTrackOnMap(points, historyTrack.getStartPoint(), historyTrack.getEndPoint());
                                    Log.e("historyTrack", "" + points.size());
                                    // historyTrack中包含终端轨迹信息
                                } else {
                                    // 查询失败
                                }
                            }

                            @Override
                            public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {

                            }

                            @Override
                            public void onAddTrackCallback(AddTrackResponse addTrackResponse) {

                            }

                            @Override
                            public void onParamErrorCallback(ParamErrorResponse paramErrorResponse) {

                            }
                        });
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    //如果它本来就是long类型的,则不用写这一步
//                    long lt = new Long(start_time);
//                    Date date = new Date(lt);
//                 String   res = simpleDateFormat.format(date);
//                    String s = AppUtils.stampToDate(start_time);
//                    String s1 = AppUtils.stampToDate(end_time);



                } else {
                    showToastShort(json.getMsg());

                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

                showToastShort("服务器内部错误,稍后再试!");
            }
        });

    }

    private void clearTracksOnMap() {
        for (Polyline polyline : polylines) {
            polyline.remove();
        }
        for (Marker marker : endMarkers) {
            marker.remove();
        }
        endMarkers.clear();
        polylines.clear();
    }

    private void drawTrackOnMap(List<Point> points, TrackPoint startPoint, TrackPoint endPoint) {
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE).width(20);

        if (startPoint != null && startPoint.getLocation() != null) {
            LatLng latLng = new LatLng(startPoint.getLocation().getLat(), startPoint.getLocation().getLng());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.qi_pic));
            endMarkers.add(mapView.getMap().addMarker(markerOptions));
        }

        if (endPoint != null && endPoint.getLocation() != null) {
            LatLng latLng = new LatLng(endPoint.getLocation().getLat(), endPoint.getLocation().getLng());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.zhong_pic));
            endMarkers.add(mapView.getMap().addMarker(markerOptions));
        }

        for (Point p : points) {
            LatLng latLng = new LatLng(p.getLat(), p.getLng());
            polylineOptions.add(latLng);
            boundsBuilder.include(latLng);
        }
        Polyline polyline = mapView.getMap().addPolyline(polylineOptions);
        polylines.add(polyline);
        mapView.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 10));
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
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {

            }
        }
    }


    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @OnClick({R.id.ll_common_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
        }
    }
}
