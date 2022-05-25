package com.xueyiche.zjyk.xueyiche.practicecar;

import android.content.Context;
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
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.tencent.bugly.proguard.A;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.activity.LocationSearchActivity;
import com.xueyiche.zjyk.xueyiche.homepage.activities.DaifBaoYangListActivity;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.ShouYeBannerAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.bean.MaintenanceBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.OptionPicker;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.TrainWithInfoBean;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
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
public class ContentTrainNewActivity extends BaseActivity {
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
    @BindView(R.id.tvChooseLocation)
    TextView tvChooseLocation;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.tvDriverYear)
    TextView tvDriverYear;
    @BindView(R.id.tvOrder)
    TextView tvOrder;
    @BindView(R.id.tvChoodeTime)
    TextView tvChoodeTime;
    private TrainWithInfoBean.DataBean data;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;

    @Override
    protected int initContentView() {
        return R.layout.activity_train_new;
    }

    public static void forward(Context context, String id) {
        Intent intent = new Intent(context, ContentTrainNewActivity.class);
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
        String id = getIntent().getStringExtra("id");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        MyHttpUtils.postHttpMessage(AppUrl.trainwith_info, map, TrainWithInfoBean.class, new RequestCallBack<TrainWithInfoBean>() {
            @Override
            public void requestSuccess(TrainWithInfoBean json) {
                if (1 == json.getCode()) {
                    data = json.getData();
                    if (data != null) {
                        tvName.setText(data.getTitle());
                        tvLocation.setText("所用车辆：" + data.getCat_brand());
                        if (!TextUtils.isEmpty(data.getMoney())) {
                            tvMoney.setText("¥" + data.getMoney());
                        } else {
                            tvMoney.setText("¥0");
                        }
                        tvDriverYear.setText("驾龄"+data.getDriving_age()+"年");
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


    @OnClick({R.id.ll_common_back, R.id.tvChooseLocation, R.id.tvOrder, R.id.tvChoodeTime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tvChooseLocation:
                LocationSearchActivity.forward(ContentTrainNewActivity.this, 222, "end");
                break;
            case R.id.tvChoodeTime:
                chooseTime();
                break;
            case R.id.tvOrder:
                ToastUtils.showToast(ContentTrainNewActivity.this, "下单成功");
                finish();
                break;
        }
    }

    private void chooseTime() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 12; i++) {
            list.add("" + i + "小时");
        }
        final OptionPicker picker = new OptionPicker(ContentTrainNewActivity.this, list);
        picker.setOffset(1);
        picker.setSelectedIndex(0);
        picker.setTextSize(15);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));
        picker.setTitleText("请选择练车时长");
        picker.setTitleTextSize(20);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                picker.setSelectedIndex(position);
                tvChoodeTime.setText(option);

            }

        });
        picker.show();
    }
}
