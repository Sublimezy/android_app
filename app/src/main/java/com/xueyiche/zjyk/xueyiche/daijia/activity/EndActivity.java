package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
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
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.daijia.view.DrivingRouteOverlay;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.BaiduLocation;
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

/**
 * Created by Administrator on 2019/9/23.
 */
public class EndActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back, iv_anquan, iv_call, now_location;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private TextView tv_name;
    private TextView tv_age;
    private TextView tv_gonghao;
    private CircleImageView ci_head;
    private TextView tv_pingjia;
    private RatingBar rb_star;
    private RoutePlanSearch mSearch;
    private String user_id;
    private String order_number;
    private TextView tv_act_distance;
    private TextView tv_user_amount;
    private TextView tv_wait_time;
    private TextView tv_all_money;
    private TextView tv_user_amount2;
    private TextView tv_user_amount3;
    private double user_amount3;
    private String order_status;
    private String driver_phone;
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";

    @Override
    protected int initContentView() {
        return R.layout.end_activity;
    }

    @Override
    protected void initView() {

        iv_back = view.findViewById(R.id.iv_back);
        mMapView = (MapView) view.findViewById(R.id.map_wait);
        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_WHITE);
        mMapView.setMapCustomStylePath(customStyleFilePath);
        mMapView.setMapCustomStyleEnable(true);
        //合计
        tv_all_money = (TextView) view.findViewById(R.id.tv_all_money);
        //超出等候费
        tv_user_amount2 = (TextView) view.findViewById(R.id.tv_user_amount2);
        //超出公里费
        tv_user_amount3 = (TextView) view.findViewById(R.id.tv_user_amount3);
        //姓名
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        //驾龄
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        //工号
        tv_gonghao = (TextView) view.findViewById(R.id.tv_gonghao);
        tv_pingjia = (TextView) view.findViewById(R.id.tv_pingjia);
        tv_act_distance = (TextView) view.findViewById(R.id.tv_act_distance);
        tv_user_amount = (TextView) view.findViewById(R.id.tv_user_amount);
        tv_wait_time = (TextView) view.findViewById(R.id.tv_wait_time);
        iv_anquan = view.findViewById(R.id.iv_anquan);
        iv_call = view.findViewById(R.id.iv_call);
        //头像
        ci_head = (CircleImageView) view.findViewById(R.id.ci_head);
        now_location = view.findViewById(R.id.now_location);

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
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_pingjia.setOnClickListener(this);
        iv_anquan.setOnClickListener(this);
        iv_call.setOnClickListener(this);
        now_location.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        order_number = getIntent().getStringExtra("order_number");
        getOrder();
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        //隐藏百度logo
        mMapView.removeViewAt(1);
        UiSettings settings = mBaiduMap.getUiSettings();
        settings.setOverlookingGesturesEnabled(false);//屏蔽双指下拉时变成3D地图
        settings.setRotateGesturesEnabled(false);   //屏蔽旋转
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
                if (drivingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条路线为例）
                    //为DrivingRouteOverlay实例设置数据
                    DrivingRouteLine drivingRouteLine = drivingRouteResult.getRouteLines().get(0);
                    overlay.setData(drivingRouteLine);
                    //在地图上绘制DrivingRouteOverlay
                    //两点之间的公里数 单位 米
                    int distance = drivingRouteLine.getDistance();
                    //时间
                    int[] aa = new int[]{10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
                    int[] bb = new int[]{20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3};
                    int level = 5;
                    for (int i = 0; i < aa.length; i++) {
                        if (distance > aa[i] && aa[i + 1] > distance) {
                            level = i - 2;
                        }
                    }
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(bb[level > 0 ? level : 0]).build()));
                    overlay.addToMap();
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

    private InfoWindow mInfoWindow;

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
                                        //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
                                        String job_number = content.getJob_number();
                                        order_status = content.getOrder_status();
                                        driver_phone = content.getDriver_phone();
                                        String driving_year = content.getDriving_year();
                                        String driver_name = content.getDriver_name();
                                        String head_img = content.getHead_img();
                                        String on_latitude = content.getOn_latitude();
                                        String on_longitude = content.getOn_longitude();
                                        String down_latitude = content.getDown_latitude();
                                        String down_longitude = content.getDown_longitude();
                                        String order_time1 = content.getOrder_time1();
                                        String act_distance = content.getAct_distance();
                                        double user_amount = content.getUser_amount();
                                        String over_distance = content.getOver_distance();
                                        String waitminutes = content.getWaitminutes();
                                        String latitude = content.getLatitude();
                                        String longitude = content.getLongitude();
                                        //等候费金额
                                        double user_amount2 = content.getUser_amount2();
                                        user_amount3 = content.getUser_amount3();
                                        if ("4".equals(order_status)) {
                                            tv_pingjia.setText("评 价");
                                        } else {
                                            tv_pingjia.setText("已评价");
                                        }
                                        if (!TextUtils.isEmpty(job_number)) {
                                            tv_gonghao.setText("工号：" + job_number);
                                        }
                                        if (!TextUtils.isEmpty(driving_year)) {
                                            tv_age.setText(driving_year + "年驾龄");
                                        }
                                        if (!TextUtils.isEmpty(driver_name)) {
                                            tv_name.setText(driver_name);
                                        }
                                        if (!TextUtils.isEmpty(head_img)) {
                                            Picasso.with(App.context).load(head_img).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(ci_head);
                                        }
                                        if (!TextUtils.isEmpty(act_distance)) {
                                            tv_act_distance.setText("里程费（共" + act_distance + "公里，区域外" + over_distance + "公里）");
                                        }
                                        tv_user_amount.setText(user_amount + "元");
                                        tv_user_amount2.setText(user_amount2 + "元");
                                        tv_user_amount3.setText(user_amount3 + "元");
                                        if (!TextUtils.isEmpty(waitminutes)) {

                                            tv_wait_time.setText("等候费（共" + waitminutes + "分）");
                                        }
                                        tv_all_money.setText((user_amount + user_amount2 + user_amount3) + "元");

                                        if (!TextUtils.isEmpty(down_latitude) && !TextUtils.isEmpty(down_longitude) && !TextUtils.isEmpty(on_latitude)
                                                && !TextUtils.isEmpty(on_longitude)) {
                                            LatLng qi_latLng = new LatLng(Double.parseDouble(on_latitude), Double.parseDouble(on_longitude));
                                            LatLng zhong_latLng = new LatLng(Double.parseDouble(down_latitude), Double.parseDouble(down_longitude));
                                            LatLng driver_latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                            Overlay overlay1 = mBaiduMap.addOverlay(new MarkerOptions().position(qi_latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.qi_pic)));
                                            Overlay overlay2 = mBaiduMap.addOverlay(new MarkerOptions().position(zhong_latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.zhong_pic)));
                                            Overlay overlay3 = mBaiduMap.addOverlay(new MarkerOptions().position(driver_latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.daijia_small_car)));
                                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                            builder.include(((Marker) overlay1).getPosition());
                                            builder.include(((Marker) overlay2).getPosition());
                                            builder.include(((Marker) overlay3).getPosition());
                                            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
                                            PlanNode stNode = PlanNode.withLocation(qi_latLng);
                                            PlanNode enNode = PlanNode.withLocation(zhong_latLng);
                                            View viewTop = LayoutInflater.from(App.context).inflate(R.layout.jiedan_top_layout, null);
                                            TextView tv_content = viewTop.findViewById(R.id.tv_content);
                                            tv_content.setText("已到达目的地");
                                            mInfoWindow = new InfoWindow(viewTop, driver_latLng, -50);
                                            mBaiduMap.showInfoWindow(mInfoWindow);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.now_location:
                 BaiduLocation baiduLocation = new BaiduLocation();
                baiduLocation.baiduLocation();
                String x = PrefUtils.getParameter("x");
                String y = PrefUtils.getParameter("y");
                LatLng latLng = new LatLng(Double.valueOf(y), Double.valueOf(x));
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(mapStatusUpdate, 1000);
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
                }
                break;
            case R.id.iv_anquan:
                showAnQuan();
                break;
            case R.id.tv_pingjia:
                //去评价
                if ("4".equals(order_status)) {
                    showPingJia();
                }
                break;
        }
    }

    public void showAnQuan() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_anqun_layout, null);
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
                Intent intent = new Intent(EndActivity.this, JinjiPhoneActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ll_baojing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XueYiCheUtils.CallPhone(EndActivity.this, "拨打报警电话", "110");
            }
        });
        rl_xuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //web页
                Intent intent = new Intent(EndActivity.this, UrlActivity.class);
                intent.putExtra("url", "http://xueyiche.cn/xyc/instructions/safe.html");
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });

    }

    private void showPingJia() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.pingjia_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        final RatingBar rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        rb_star.setRating(5f);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);

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
                int rating = (int) rb_star.getRating();
                pingJia(rating);
                dialog01.dismiss();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    public void pingJia(int rating) {
        OkHttpUtils.post().url(AppUrl.PingJia_DaiJia)
                .addParams("order_number", order_number)
                .addParams("user_id", user_id)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("score_num", rating + "")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    final SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                    if (successDisCoverBackBean != null) {
                        final int code = successDisCoverBackBean.getCode();
                        if (200 == code) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String msg = successDisCoverBackBean.getMsg();
                                    Toast.makeText(EndActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
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
}
