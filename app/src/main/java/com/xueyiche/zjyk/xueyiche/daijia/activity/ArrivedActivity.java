package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DriveRouteResult;
import com.google.gson.Gson;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/19.
 */
public class ArrivedActivity extends BaseMapActivity {
    private MarkerOptions markerOption;

    @Override
    protected int initContentView() {
        return R.layout.jiedan_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
    }

    public static void forward(Context context,String order_sn) {
        Intent intent = new Intent(context, ArrivedActivity.class);
        intent.putExtra("order_sn",order_sn);
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
                getDataFromNet("" + latitude, "" + longitude);

            }
        }
    }

    private void getDataFromNet(String latitude, String longitude) {
        Map<String, String> params = new HashMap<>();
        params.put("user_lng", "" + latitude);
        params.put("user_lat", "" + longitude);
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
            View viewCat = LayoutInflater.from(ArrivedActivity.this).inflate(R.layout.item_map_nearby_layout, null);
            TextView tvName = viewCat.findViewById(R.id.tvName);
            LinearLayout llTop = viewCat.findViewById(R.id.llTop);
            ImageView ivLogoType = viewCat.findViewById(R.id.ivLogoType);

            TextView tvDistance = viewCat.findViewById(R.id.tvDistance);
            tvDistance.setVisibility(View.GONE);
            tvName.setText("代驾员已到达");
            if (i == 0) {
                llTop.setVisibility(View.GONE);
                ivLogoType.setImageResource(R.mipmap.dingwei);
            } else {
                llTop.setVisibility(View.VISIBLE);
                ivLogoType.setImageResource(R.mipmap.logo);
            }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
