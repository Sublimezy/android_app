package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
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
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.daijia.view.DrivingRouteOverlay;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DatePanDuanUtils;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/19.
 */
public class JieDanActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private TextView tv_name,tv_quxiao_rule,tv_quxiao,tv_date;
    private TextView tv_age;
    private TextView tv_gonghao;
    private CircleImageView ci_head;
    private TextView tv_tishi_quxiao;
    private ImageView iv_call;
    private String order_number;
    private String user_id;
    private String driver_phone;
    private RoutePlanSearch mSearch;
    private String default_time;
    private LinearLayout ll_bottom_money, ll_top, ll_yuyue_time;
    private TextView tv_pay, tv_yuyue_shuoming;
    private TextView tv_juli;
    private double user_amount2;
    private String order_number2;
    private String on_latitude;
    private String on_longitude;
    public static JieDanActivity instance;
    private TextView tv_time_yuyue;
    private String order_status;
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    private int duration;
    private String head_img;
    LatLng latLng = null;
    Marker marker = null;
    OverlayOptions options = null;
    private InfoWindow mInfoWindow;
    private static Bitmap bitmap;
    private String latitude;
    private String longitude;
    private String cancle_remark;

    @Override
    protected int initContentView() {
        return R.layout.jiedan_activity;
    }

    @Override
    protected void initView() {

        instance = this;
        AppUtils.addActivity(this);
        iv_back = view.findViewById(R.id.iv_back);
        ll_top = view.findViewById(R.id.ll_top);
        ll_yuyue_time = view.findViewById(R.id.ll_yuyue_time);
        mMapView = (MapView) view.findViewById(R.id.map_wait);
        //姓名
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        //驾龄
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        tv_yuyue_shuoming = (TextView) view.findViewById(R.id.tv_yuyue_shuoming);
        //工号
        tv_gonghao = (TextView) view.findViewById(R.id.tv_gonghao);
        //头像
        ci_head = (CircleImageView) view.findViewById(R.id.ci_head);
        //取消时间提示  司机正在火速赶来，12：10后取消订单需要费用
        tv_tishi_quxiao = (TextView) view.findViewById(R.id.tv_tishi_quxiao);
        iv_call = (ImageView) view.findViewById(R.id.iv_call);
        tv_pay = (TextView) view.findViewById(R.id.tv_pay);
        tv_juli = (TextView) view.findViewById(R.id.tv_juli);
        tv_quxiao_rule = (TextView) view.findViewById(R.id.tv_quxiao_rule);
        tv_time_yuyue = (TextView) view.findViewById(R.id.tv_time_yuyue);
        tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        ll_bottom_money = (LinearLayout) view.findViewById(R.id.ll_bottom_money);

    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新代驾", msg)) {
            getOrder();
        } else if (TextUtils.equals("开始行程", msg)) {
            Intent intent = new Intent(this, JinXingActivity.class);
            intent.putExtra("order_number", order_number);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        iv_call.setOnClickListener(this);
        tv_quxiao.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        user_id = PrefUtils.getString(App.context, "user_id", "");
        default_time = PrefUtils.getString(App.context, "default_time", "15");
        order_number = getIntent().getStringExtra("order_number");
        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_WHITE);
        mMapView.setMapCustomStylePath(customStyleFilePath);
        mMapView.setMapCustomStyleEnable(true);
        getOrder();
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        MapStatus.Builder builder1 = new MapStatus.Builder();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
        UiSettings settings = mBaiduMap.getUiSettings();
        settings.setOverlookingGesturesEnabled(false);//屏蔽双指下拉时变成3D地图
        settings.setRotateGesturesEnabled(false);   //屏蔽旋转
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        //隐藏百度logo
        mMapView.removeViewAt(1);

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
                        final int code = orderInfoBean.getCode();
                        if (200 == code) {
                            final OrderInfoBean.ContentBean content = orderInfoBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String job_number = content.getJob_number();
                                        tv_gonghao.setText("工号：" + job_number);
                                        String driving_year = content.getDriving_year();
                                        tv_age.setText(driving_year + "年驾龄");
                                        String driver_name = content.getDriver_name();
                                        tv_name.setText(driver_name);
                                        driver_phone = content.getDriver_phone();
                                        head_img = content.getHead_img();
                                        order_status = content.getOrder_status();
                                        cancle_remark = content.getCancle_remark();
                                        tv_tishi_quxiao.setText(cancle_remark);

                                        String appointed_time = content.getAppointed_time();
                                        if (TextUtils.isEmpty(appointed_time)) {
                                            ll_top.setVisibility(View.VISIBLE);
                                            tv_time_yuyue.setVisibility(View.GONE);
                                            ll_yuyue_time.setVisibility(View.GONE);
                                            tv_yuyue_shuoming.setVisibility(View.GONE);
                                        } else {
                                            tv_time_yuyue.setVisibility(View.VISIBLE);
                                            ll_top.setVisibility(View.GONE);
                                            ll_yuyue_time.setVisibility(View.VISIBLE);
                                            tv_yuyue_shuoming.setVisibility(View.VISIBLE);
                                        }
                                        //是否需要收费  1 需要  2 不需要
                                        String driver_need2 = content.getDriver_need2();
                                        if ("1".equals(driver_need2)) {
                                            ll_bottom_money.setVisibility(View.VISIBLE);
                                            ll_top.setVisibility(View.GONE);
                                            tv_quxiao.setVisibility(View.GONE);
                                        } else {
                                            ll_bottom_money.setVisibility(View.GONE);
                                            ll_top.setVisibility(View.VISIBLE);
                                            tv_quxiao.setVisibility(View.VISIBLE);
                                        }
                                        String waitminutes = content.getWaitminutes();
                                        String over_time = content.getOver_time();
                                        user_amount2 = content.getUser_amount2();
                                        if (!TextUtils.isEmpty(appointed_time)) {

                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                            Date date;
                                            try {
                                                date = format.parse(appointed_time);
                                                SimpleDateFormat sdf =   new SimpleDateFormat( "MM月dd日" );
                                                SimpleDateFormat sdf_time =   new SimpleDateFormat("HH:mm" );
                                                String sDate = sdf.format(date);
                                                String sTime = sdf_time.format(date);
                                                Long timestamp = DatePanDuanUtils.getTimestamp(appointed_time);
                                                String today = DatePanDuanUtils.getToday(""+timestamp);
                                                if (!TextUtils.isEmpty(sDate)) {
                                                    tv_date.setText(sDate+"  "+today);
                                                }


                                                tv_time_yuyue.setText(sTime);

                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        tv_juli.setText("共" + waitminutes + "分钟，超时" + over_time + "分钟");
                                        tv_pay.setText("等候费" + user_amount2 + "元 去支付");
                                        Picasso.with(App.context).load(head_img).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(ci_head);
                                        on_latitude = content.getOn_latitude();
                                        on_longitude = content.getOn_longitude();
                                        String down_latitude = content.getDown_latitude();
                                        String down_longitude = content.getDown_longitude();
                                        latitude = content.getLatitude();
                                        order_number2 = content.getOrder_number2();
                                        longitude = content.getLongitude();
                                        tv_quxiao_rule.setText(cancle_remark);
                                        tv_yuyue_shuoming.setText(cancle_remark);
                                        mSearch = RoutePlanSearch.newInstance();
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
                                                        DrivingRouteLine drivingRouteLine = drivingRouteResult.getRouteLines().get(0);
                                                        overlay.setData(drivingRouteLine);
                                                        //在地图上绘制DrivingRouteOverlay
                                                        //两点之间的公里数 单位 米
                                                        String distance = drivingRouteLine.getDistance() + "";
                                                        if (!TextUtils.isEmpty(distance)) {
                                                            BigDecimal bigDecimal1 = new BigDecimal(distance);
                                                            BigDecimal bigDecimal2 = new BigDecimal("" + 1000);
                                                            final String   rder_distance = bigDecimal1.divide(bigDecimal2).setScale(1, BigDecimal.ROUND_HALF_UP) + "";
                                                            //时间
                                                            duration = drivingRouteLine.getDuration();
//                                                            int[] aa = new int[]{10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
//                                                            int[] bb = new int[]{20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3};
//                                                            int level = 5;
//                                                            for (int i = 0; i < aa.length; i++) {
//                                                                if (Double.parseDouble(rder_distance) > aa[i] && aa[i + 1] > Double.parseDouble(rder_distance)) {
//                                                                    level = i-9;
//                                                                }
//                                                            }
                                                            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(14).build()));
                                                            returnBitMap(head_img, new Handler(Looper.getMainLooper()){

                                                                @Override
                                                                public void handleMessage(Message msg) {
                                                                    super.handleMessage(msg);
                                                                    Bitmap mBitmap = (Bitmap) msg.obj;
                                                                    int width = mBitmap.getWidth();
                                                                    int height = mBitmap.getHeight();
                                                                    int newWidth=80;
                                                                    int newHeight=80;
                                                                    float scaleWidth=((float)newWidth)/width;
                                                                    float scaleHeight=((float)newHeight)/height;
                                                                    Matrix matrix = new Matrix();
                                                                    matrix.postScale(scaleWidth,scaleHeight);
                                                                    bitmap=Bitmap.createBitmap(mBitmap,0,0,width,height,matrix,true);
                                                                    bitmap.getWidth();
                                                                    bitmap.getHeight();
                                                                    latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                                                    if (!TextUtils.isEmpty(head_img)) {
                                                                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                                                                        options = new MarkerOptions().position(latLng).icon(bitmapDescriptor);
                                                                    }else {
                                                                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.daijia_small_car);
                                                                        options = new MarkerOptions().position(latLng).icon(bitmapDescriptor);
                                                                    }
                                                                    marker = (Marker) mBaiduMap.addOverlay(options);//将覆盖物添加到地图上
                                                                    LatLng latLng1 = marker.getPosition();
                                                                    View viewTop = LayoutInflater.from(App.context).inflate(R.layout.jiedan_top_layout, null);
                                                                    TextView tv_content = viewTop.findViewById(R.id.tv_content);
                                                                    if ("2".equals(order_status)) {
                                                                        tv_content.setText("代驾员已到达");
//                                            tv_title.setText("已到达");
                                                                    } else {
                                                                        tv_content.setText("距离您" + rder_distance + "公里 预计" + duration / 60 + "分钟到达");
                                                                    }
                                                                    mInfoWindow = new InfoWindow(viewTop, latLng1, -50);
                                                                    mBaiduMap.showInfoWindow(mInfoWindow);

                                                                }
                                                            });

                                                            overlay.addToMap();
                                                        }
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
                                        if (!TextUtils.isEmpty(down_latitude) && !TextUtils.isEmpty(down_longitude) && !TextUtils.isEmpty(on_latitude)
                                                && !TextUtils.isEmpty(on_longitude)) {
                                            setMap(latitude, longitude);
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

    private void setMap(final String latitude, final String longitude) {

        LatLng qi_latLng = new LatLng(Double.parseDouble(on_latitude), Double.parseDouble(on_longitude));
        LatLng zhong_latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        LatLng driver = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        Overlay overlay1 = mBaiduMap.addOverlay(new MarkerOptions().position(qi_latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.qi_pic)));
        Overlay overlay2 = mBaiduMap.addOverlay(new MarkerOptions().position(zhong_latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.zhong_pic)));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(((Marker) overlay1).getPosition());
        builder.include(((Marker) overlay2).getPosition());
        PlanNode stNode = PlanNode.withLocation(qi_latLng);
        PlanNode dNode = PlanNode.withLocation(driver);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(dNode)
                .to(stNode));
    }


    public static Bitmap returnBitMap(final String url, final android.os.Handler handler){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                    Message message = handler.obtainMessage();
                    message.obj = bitmap;
                    message.arg1 = 1;
                    handler.sendMessage(message);

                    is.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }
        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pay:
                //支付等候费
                Intent intent = new Intent(App.context, AppPay.class);
                intent.putExtra("pay_style", "daijia2");
                intent.putExtra("order_number", order_number2);
                intent.putExtra("subscription", user_amount2 + "");
                intent.putExtra("jifen", "0");
                startActivity(intent);
                break;
            case R.id.iv_call:
                //打电话
                if (!TextUtils.isEmpty(driver_phone)) {
                    if (driver_phone.length() != 11) {
                        String decrypt = AES.decrypt(driver_phone);
                        XueYiCheUtils.CallPhone(this, "拨打代驾电话", decrypt);
                    } else {
                        XueYiCheUtils.CallPhone(this, "拨打代驾电话", driver_phone);
                    }
                } else {
                    Toast.makeText(JieDanActivity.this, "电话号码为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_quxiao:
                //取消订单
                Intent intent1 = new Intent(JieDanActivity.this, LiYouActivity.class);
                intent1.putExtra("order_number", order_number);
                intent1.putExtra("cancle_remark", cancle_remark);
                intent1.putExtra("type", "JieDan");
                startActivity(intent1);
                break;
        }
    }
}
