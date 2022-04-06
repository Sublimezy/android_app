package com.xueyiche.zjyk.xueyiche.daijia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RouteSearch;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;
import com.xueyiche.zjyk.xueyiche.route.DrivingRouteOverLay;
import com.xueyiche.zjyk.xueyiche.utils.AMapUtil;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 2022/4/2/11:14 下午 .
 * #            com.xueyiche.zjyk.xueyiche
 * #            xueyiche5.0
 */
public class DaiJiaActivity extends BaseMapActivity {
    @BindView(R.id.iv_anquan)
    ImageView ivAnquan;
    @BindView(R.id.rb_richang)
    RadioButton rbRichang;
    @BindView(R.id.rb_yuyue)
    RadioButton rbYuyue;
    @BindView(R.id.rb_daijiao)
    RadioButton rbDaijiao;
    @BindView(R.id.iv_daijia_banner)
    ImageView ivDaijiaBanner;
    @BindView(R.id.tv_qidian)
    TextView tvQidian;
    @BindView(R.id.tv_mudi)
    TextView tvMudi;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_richang)
    LinearLayout llRichang;
    @BindView(R.id.tv_yuyue_time)
    TextView tvYuyueTime;
    @BindView(R.id.ll_yuyue_time)
    LinearLayout llYuyueTime;
    @BindView(R.id.iv_daijia_banner_yuyue)
    CustomShapeImageView ivDaijiaBannerYuyue;
    @BindView(R.id.tv_qidian_yuyue)
    TextView tvQidianYuyue;
    @BindView(R.id.tv_mudi_yuyue)
    TextView tvMudiYuyue;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.rl_beizhu)
    RelativeLayout rlBeizhu;
    @BindView(R.id.tv_money_yuyue)
    TextView tvMoneyYuyue;
    @BindView(R.id.tv_xiadan_yuyue)
    TextView tvXiadanYuyue;
    @BindView(R.id.ll_yuyue)
    LinearLayout llYuyue;
    @BindView(R.id.iv_daijia_banner_daijiao)
    ImageView ivDaijiaBannerDaijiao;
    @BindView(R.id.tv_choose_time)
    TextView tvChooseTime;
    @BindView(R.id.daijiao_line)
    View daijiaoLine;
    @BindView(R.id.tv_choose_people)
    TextView tvChoosePeople;
    @BindView(R.id.tv_qidian_daijiao)
    TextView tvQidianDaijiao;
    @BindView(R.id.tv_mudi_daijiao)
    TextView tvMudiDaijiao;
    @BindView(R.id.ll_daijiao)
    LinearLayout llDaijiao;


    @Override
    protected int initContentView() {
        return R.layout.daijia_activity;
    }
    public static void forward(Context context) {
        Intent intent = new Intent(context, DaiJiaActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

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
//                tvQidian.setText(aMapLocation.getAddress());
                Log.e("onLocationChanged", "" + city);
                Log.e("onLocationChanged", "" + cityCode);
                LatLonPoint mStartPoint = new LatLonPoint(39.942295, 116.335891);//起点，116.335891,39.942295
                LatLonPoint mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，116.481288,39.995576
                setfromandtoMarker(aMap,mStartPoint, mEndPoint);
                searchRouteResult(2, RouteSearch.DrivingDefault, mStartPoint, mEndPoint);
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

    @OnClick({R.id.iv_anquan, R.id.iv_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_anquan:
//                AppUtils.showAnQuan(getActivity());
//                WaitActivity.forward(getActivity());
//                JieDanActivity.forward(getActivity());
                JinXingActivity.forward(this);
                break;
            case R.id.iv_user:
                userLocation();
                break;
        }
    }
}
