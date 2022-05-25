package com.xueyiche.zjyk.xueyiche.homepage.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.example.qrcode.Constant;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.CommonWebView;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.LocationSearchActivity;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.ShouYeBannerAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.bean.MaintenanceBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ShouYeBannerBean;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.ToastUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * #            Created by 張某人 on 2022/5/25/3:40 下午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage.activities
 * #            xueyiche5.0
 */
public class ContentActivity extends BaseActivity implements INaviInfoCallback{
    @BindView(R.id.ll_common_back)
    LinearLayout llCommonBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.banner_view)
    BannerViewPager mViewPager;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.ivDaoHang)
    ImageView ivDaoHang;
    @BindView(R.id.tvChooseLocation)
    TextView tvChooseLocation;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvOrder)
    TextView tvOrder;
    private MaintenanceBean.DataBean data;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;

    @Override
    protected int initContentView() {
        return R.layout.activity_daijianche_content;
    }

    public static void forward(Context context, String id) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvTitle.setText("详情");
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        mViewPager.setIndicatorSliderGap(BannerUtils.dp2px(6));
        mViewPager.setScrollDuration(1500);
        mViewPager.setIndicatorStyle(IndicatorStyle.ROUND_RECT);
        mViewPager.setIndicatorSliderGap(BannerUtils.dp2px(4));
        mViewPager.setIndicatorSlideMode(IndicatorSlideMode.SCALE);
        mViewPager.setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.seven));
        mViewPager.setIndicatorSliderColor(getResources().getColor(R.color.colorLine), getResources().getColor(R.color.colorOrange));
        mViewPager.setIndicatorSliderWidth(getResources().getDimensionPixelOffset(R.dimen.seven), getResources().getDimensionPixelOffset(R.dimen.dp_15));
        mViewPager.setIndicatorMargin(0, 0, 0, BannerUtils.dp2px(15));
        mViewPager.setLifecycleRegistry(getLifecycle());
        mViewPager.setIndicatorGravity(IndicatorGravity.CENTER);
        mViewPager.setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(View clickedView, int position) {
            }
        });
        mViewPager.setAdapter(new ShouYeBannerAdapter());
    }

    @Override
    protected void initListener() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        new GDLocation().startLocation();
        String lat = PrefUtils.getParameter("lat");
        String lon = PrefUtils.getParameter("lon");
        String id = getIntent().getStringExtra("id");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("coordinate", lon + "," + lat);
        MyHttpUtils.postHttpMessage(AppUrl.maintenance_info, map, MaintenanceBean.class, new RequestCallBack<MaintenanceBean>() {
            @Override
            public void requestSuccess(MaintenanceBean json) {
                if (1 == json.getCode()) {
                    data = json.getData();
                    if (data != null) {
                        tvName.setText(data.getTitle());
                        tvLocation.setText(data.getAddress());
                        tvDistance.setText("距您" + data.getDistance() + "公里");
                        if (!TextUtils.isEmpty(data.getMoney())) {
                            tvMoney.setText("¥" + data.getMoney());
                        } else {
                            tvMoney.setText("¥0");
                        }

                        if (data.getImages().size() > 0) {
                            mViewPager.create(data.getImages());
                        }
                    }
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle extras = data.getExtras();
            switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
                case 222:
                    String latitude1 = extras.getString("lat");
                    String longitude1 = extras.getString("lon");
                    String address1 = extras.getString("title");
                    if (!TextUtils.isEmpty(address1)) {
                        tvChooseLocation.setText(address1);
                    }
                    break;

            }
        }

    }


    private void daohang() {
        if (TextUtils.isEmpty(data.getLat()) || data.getLat().contains("null")) {
            com.hjq.toast.ToastUtils.show("终点信息不全");
            return;
        }
        new GDLocation().startLocation();
        String lat = PrefUtils.getParameter("lat");
        String lon = PrefUtils.getParameter("lon");
        String address = PrefUtils.getParameter("address");
        LatLng p1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        LatLng  p2 = new LatLng(Double.parseDouble(data.getLat()), Double.parseDouble(data.getLng()));
        Poi start = new Poi(address, p1, "");
        //途经点
        List<Poi> poiList = new ArrayList();
        poiList.add(new Poi(data.getAddress(), p2, ""));
        //终点
        Poi end = new Poi(data.getAddress(), p2, "");
        AmapNaviParams amapNaviParams = new AmapNaviParams(start, poiList, end, AmapNaviType.DRIVER, AmapPageType.NAVI);
        amapNaviParams.setUseInnerVoice(true);
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), amapNaviParams, ContentActivity.this);

    }

    @OnClick({R.id.ll_common_back, R.id.ivDaoHang, R.id.tvChooseLocation, R.id.tvPhone, R.id.tvOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.ivDaoHang:
                daohang();
                break;
            case R.id.tvChooseLocation:
                LocationSearchActivity.forward(ContentActivity.this, 222, "end");
                break;
            case R.id.tvPhone:
                XueYiCheUtils.CallPhone(ContentActivity.this, "是否拨打电话？", "" + data.getTel());
                break;
            case R.id.tvOrder:
                ToastUtils.showToast(ContentActivity.this, "下单成功");
                finish();
                break;
        }
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviDirectionChanged(int i) {

    }

    @Override
    public void onDayAndNightModeChanged(int i) {

    }

    @Override
    public void onBroadcastModeChanged(int i) {

    }

    @Override
    public void onScaleAutoChanged(boolean b) {

    }

    @Override
    public View getCustomMiddleView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }
}
