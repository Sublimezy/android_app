package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.baidu.mapapi.map.InfoWindow;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.DriverShcoolBean;
import com.xueyiche.zjyk.xueyiche.constants.bean.ListShaiXuanBean;
import com.xueyiche.zjyk.xueyiche.homepage.activities.location.bean.KaiTongCityBean;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.YiJiCaiDanAdapter;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2019/7/24.
 */
public class BaoMingActivity extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler {
    private LinearLayout llBack;
    private TextView tvTitle;
    private ImageView iv_caidan;
    private ListView lv_driving_school;
    private String x;
    private String y;
    private RefreshLayout refreshLayout;
    private List<DriverShcoolBean.ContentBean> content;

    @Override
    protected int initContentView() {
        return R.layout.baoming_activity;
    }

    @Override
    protected void initView() {

        llBack = (LinearLayout) view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        iv_caidan = (ImageView) view.findViewById(R.id.title_include).findViewById(R.id.iv_caidan);
        iv_caidan.setVisibility(View.VISIBLE);
        iv_caidan.setOnClickListener(this);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        tvTitle.setText("驾校");
        lv_driving_school = (ListView) view.findViewById(R.id.lv_driving_school);
        NetBroadcastReceiver.mListeners.add(this);
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        x = PrefUtils.getString(App.context, "x", "");
        y = PrefUtils.getString(App.context, "y", "");
        iv_caidan.setImageResource(R.mipmap.sy_kefu);
        lv_driving_school.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (XueYiCheUtils.IsHaveInternet(App.context)) {
                    DriverShcoolBean.ContentBean contentBean = content.get(position);
                    String driver_school_id = contentBean.getDriver_school_id();
                    Intent intent = new Intent(App.context, DriverSchoolContent.class);
                    intent.putExtra("driver_school_id", driver_school_id);
                    startActivity(intent);
                } else {
                    showToastShort(StringConstants.CHECK_NET);
                }
            }
        });
        getDataFromNet();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            getDataFromNet();
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                            Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 1500);
            }
        });
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        } else {
            getDataFromNet();
        }
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.DRVIER_SCHOOL)
                    .addParams("pager", "1")
                    .addParams("sort_method", "0")
                    .addParams("latitude_user", y)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("longitude_user", x)
                    .addParams("user_id", user_id)
                    .addParams("area_id", PrefUtils.getString(App.context, "area_id", ""))
                    .addParams("practice_area_id", PrefUtils.getString(App.context, "area_id", ""))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        DriverShcoolBean driverLocationBean = JsonUtil.parseJsonToBean(string, DriverShcoolBean.class);
                        if (driverLocationBean != null) {
                            int code = driverLocationBean.getCode();
                            if (200 == code) {
                                content = driverLocationBean.getContent();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        lv_driving_school.setAdapter(new DrivingSchoolAdapter(content, App.context, R.layout.exam_driving_school_list_item));
                                    }
                                });

//                            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);//通过这个经纬度对象，地图就可以定位到该点
//                            mBaiduMap.animateMapStatus(msu);
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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_caidan:
                //客服
                openActivity(KeFuActivity.class);
                break;
        }
    }

    public class DrivingSchoolAdapter extends BaseCommonAdapter {

        public DrivingSchoolAdapter(List<DriverShcoolBean.ContentBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            DriverShcoolBean.ContentBean contentBean = (DriverShcoolBean.ContentBean) item;
            String driver_school_name = contentBean.getDriver_school_name();
            //        String driver_school_money = contentBean.getDriver_school_money();
            String driver_school_place = contentBean.getDriver_school_place();
            String driver_school_url = contentBean.getDriver_school_url();
            String min_school_price = contentBean.getMin_school_price();
            String wecsf = contentBean.getWecsf();
            String return_money = contentBean.getReturn_money();
            String distance = contentBean.getDistance();
            if (!TextUtils.isEmpty(driver_school_name)) {
                viewHolder.setText(R.id.tv_school_name, driver_school_name);
            }
            if (!TextUtils.isEmpty(return_money)) {
                viewHolder.setText(R.id.tv_bq_one, return_money);
            }
            if (!TextUtils.isEmpty(driver_school_name)) {
                viewHolder.setText(R.id.tv_bq_two, wecsf);
            }
            if (!TextUtils.isEmpty(distance)) {
                viewHolder.setText(R.id.tv_shop_address_item, distance);
            }
            if (!TextUtils.isEmpty(driver_school_place)) {
                viewHolder.setText(R.id.tv_school_address, driver_school_place);
            }
            if (!TextUtils.isEmpty(min_school_price)) {
                viewHolder.setText(R.id.tv_school_money, "¥ " + min_school_price);
            }
            if (!TextUtils.isEmpty(driver_school_url)) {
                viewHolder.setPic(R.id.iv_school_head, driver_school_url);
            }

        }
    }

}
