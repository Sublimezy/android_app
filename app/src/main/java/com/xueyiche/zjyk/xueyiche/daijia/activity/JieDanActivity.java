package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DriveRouteResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.IndentContentBean;
import com.xueyiche.zjyk.xueyiche.mine.bean.OrderDetailBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

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
 * Created by Administrator on 2019/9/19.
 */
public class JieDanActivity extends BaseMapActivity {
    @BindView(R.id.tv_right_btn)
    TextView cancelOrder;
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
    @BindView(R.id.tv_quxiao_rule)
    TextView tvQuxiaoRule;
    private MarkerOptions markerOption;
    String order_sn = "";
    String user_mobile = "";
    public static JieDanActivity instance;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getDataFromNet();
                    break;
                default:
                    break;
            }
        }


    };

    @Override
    protected int initContentView() {
        return R.layout.jiedan_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).keyboardEnable(true).init();
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        instance = this;
    }

    public static void forward(Context context, String order_sn) {
        Intent intent = new Intent(context, JieDanActivity.class);
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
        cancelOrder.setVisibility(View.VISIBLE);
        tvTitle.setText("司机正在赶来");
        cancelOrder.setText("取消订单");
        order_sn = getIntent().getStringExtra("order_sn");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }, 1000, 5000);
        getDataFromNet();
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
//                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
//                LatLng latLng = new LatLng(latitude, longitude);

                LatLonPoint mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，116.481288,39.995576
//


            }
        }
    }

    private void getDataFromNet() {
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
                    tvQuxiaoRule.setText("" + data.getQuxiao_content());
                    int order_status = data.getOrder_status();
                    switch (order_status) {
                        case 2:
                            ArrivedActivity.forward(JieDanActivity.this, order_sn);
                            finish();
                            break;
                        case 3:
                            JinXingActivity.forward(JieDanActivity.this, order_sn);
                            finish();
                            break;
                        case 4:
                            EndActivity.forward(JieDanActivity.this, order_sn);
                            finish();
                            break;
                    }
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
        String s = new Gson().toJson(params);
        Log.e("nearbg_list", s);
        new GDLocation().startLocation();
        String lat = PrefUtils.getParameter("lat");
        String lon = PrefUtils.getParameter("lon");
        List<LatLng> list = new ArrayList<>();
        LatLng latlng1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        LatLng latlng = new LatLng(45.773342, 126.670695);
        list.add(latlng1);
        list.add(latlng);
        for (int i = 0; i < list.size(); i++) {
            LatLonPoint mStartPoint = new LatLonPoint(39.942295, 116.335891);//起点，116.335891,39.942295
            View viewCat = LayoutInflater.from(JieDanActivity.this).inflate(R.layout.item_map_nearby_layout, null);
            TextView tvName = viewCat.findViewById(R.id.tvName);
            LinearLayout llTop = viewCat.findViewById(R.id.llTop);
            ImageView ivLogoType = viewCat.findViewById(R.id.ivLogoType);
            if (i == 0) {
                llTop.setVisibility(View.GONE);
                ivLogoType.setImageResource(R.mipmap.dingwei);
            } else {
                llTop.setVisibility(View.VISIBLE);
                ivLogoType.setImageResource(R.mipmap.logo);
            }
            TextView tvDistance = viewCat.findViewById(R.id.tvDistance);
//                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AppUtils.createBounds(Double.parseDouble(lat), Double.parseDouble(lon), 45.773342, 126.670695), 200));

            Bitmap bitmap = convertViewToBitmap(viewCat);
            markerOption = new MarkerOptions()
                    .position(list.get(i))
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                    .draggable(false);
            Marker marker = aMap.addMarker(markerOption);
//                marker.setObject(user_list.get(i));
        }

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
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }


    @OnClick({R.id.ll_common_back, R.id.tv_right_btn, R.id.tvCallPhone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tvCallPhone:
                XueYiCheUtils.CallPhone(JieDanActivity.this, "拨打电话？", "" + user_mobile);
                break;
            case R.id.tv_right_btn:
                CancelOrderActivity.forward(JieDanActivity.this, order_sn, "jiedan");
                break;
        }
    }


//        @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//            case R.id.tv_pay:
//                //支付等候费
//                Intent intent = new Intent(App.context, AppPay.class);
//                intent.putExtra("pay_style", "daijia2");
//                intent.putExtra("order_number", order_number2);
//                intent.putExtra("subscription", user_amount2 + "");
//                intent.putExtra("jifen", "0");
//                startActivity(intent);
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
//                } else {
//                    Toast.makeText(JieDanActivity.this, "电话号码为空", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.tv_quxiao:
//                //取消订单
//                Intent intent1 = new Intent(JieDanActivity.this, LiYouActivity.class);
//                intent1.putExtra("order_number", order_number);
//                intent1.putExtra("cancle_remark", cancle_remark);
//                intent1.putExtra("type", "JieDan");
//                startActivity(intent1);
//                break;
//        }
//    }
}
