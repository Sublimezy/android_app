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
import com.bigkoo.pickerview.TimePickerView;
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
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderNumberBean;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.TrainWithInfoBean;
import com.xueyiche.zjyk.xueyiche.utils.PayUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    private TrainWithInfoBean.DataBean data;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private TimePickerView pvTime;
    private String hour_num = "";
    private String sLocation = "";
    private String choosedate = "";
    private String lat = "";
    private String lon = "";
    private String id;

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
        startTIme();
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

    //谷歌的时间选择器
    private void startTIme() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        int nian = selectedDate.get(Calendar.YEAR);
        int yue = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        int hour = selectedDate.get(Calendar.HOUR_OF_DAY);
        int min = selectedDate.get(Calendar.MINUTE);
        startDate.set(nian, yue, day, hour, min);
        Calendar endDate = Calendar.getInstance();
        endDate.set(nian + 10, 11, 31, 23, 59);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                String times = getTimes(date);
                if (!TextUtils.isEmpty(times)) {
                    tvStartTime.setText(times);
                    choosedate = times;
                }

            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(true)
                .setDividerColor(getResources().getColor(R.color._f2f3f4f5))
                .setContentSize(16)
                .setCancelColor(getResources().getColor(R.color.colorOrange))
                .setSubmitColor(getResources().getColor(R.color.colorOrange))
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .isDialog(true)
                .build();

    }

    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    private void getDataFromNet() {
        id = getIntent().getStringExtra("id");
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
                        tvDriverYear.setText("驾龄" + data.getDriving_age() + "年");
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
                    lat = extras.getString("lat");
                    lon = extras.getString("lon");
                    sLocation = extras.getString("title");
                    if (!TextUtils.isEmpty(sLocation)) {
                        tvChooseLocation.setText(sLocation);
                    }
                    break;

            }
        }

    }


    @OnClick({R.id.ll_common_back, R.id.tvChooseLocation, R.id.tvOrder, R.id.tvChoodeTime, R.id.tvStartTime})
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
                xiadan();
                break;
            case R.id.tvStartTime:
                pvTime.show(tvStartTime);
                break;
        }
    }

    private void xiadan() {
        if (XueYiCheUtils.IsLogin()) {
            if (TextUtils.isEmpty(hour_num)) {
                ToastUtils.showToast(ContentTrainNewActivity.this, "选择练车时长！");
                return;
            }
            if (TextUtils.isEmpty(sLocation)) {
                ToastUtils.showToast(ContentTrainNewActivity.this, "选择接送地点！");
                return;
            }
            if (TextUtils.isEmpty(choosedate)) {
                ToastUtils.showToast(ContentTrainNewActivity.this, "请选择开始时间！");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("order_type", "3");
            map.put("start_address", "" + sLocation);
            map.put("start_address_lng", "" + lon);
            map.put("start_address_lat", "" + lat);
            map.put("practice_id", "" + id);
            map.put("hour_num", "" + hour_num);
            map.put("fixed_time", "" + choosedate);
            MyHttpUtils.postHttpMessage(AppUrl.orderSavePractice, map, OrderNumberBean.class, new RequestCallBack<OrderNumberBean>() {
                @Override
                public void requestSuccess(OrderNumberBean json) {
                    if (1 == json.getCode()) {
                        OrderNumberBean.DataDTO data = json.getData();
                        if (data != null) {
                            String order_sn = data.getOrder_sn();
                            if (!TextUtils.isEmpty(order_sn)) {
                                PayUtils.showPopupWindow(AppUrl.Pay_Order_One, ContentTrainNewActivity.this, order_sn, "daijia");
                            }
                        }
                    }
                    ToastUtils.showToast(ContentTrainNewActivity.this, json.getMsg());
                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
        } else {
            LoginFirstStepActivity.forward(ContentTrainNewActivity.this);
        }
    }
    private void chooseTime() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 2; i < 13; i++) {
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
                int total = (position + 2)*(Integer.parseInt(data.getMoney()));
                tvMoney.setText("¥" + total);
                hour_num = "" + (position + 2);
            }

        });
        picker.show();
    }
}
