package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.homepage.view.FlyBanner;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.POIDressActivity;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.RentCarBean;
import com.xueyiche.zjyk.xueyiche.usedcar.view.OptionsPickerView;
import com.xueyiche.zjyk.xueyiche.usedcar.view.UsedCarFlyBanner;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Owner on 2018/6/28.
 */
public class RentCarActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_login_back;
    private LinearLayout ll_exam_back;
    private AdListView lv_car_tuijian;
    private ScrollView scroll_view;
    private Button vt_now_chose;
    private UsedCarFlyBanner fb_rent_car;
    private boolean isShangMenQu = false;
    private boolean isShangMenHuan = false;
    private ImageView iv_qu_sd, iv_huan_sd, iv_jiantou;
    private TextView tv_qu_address, tv_huan_address, tv_qu_city, tv_huan_city, tv_end_time, tv_start_time;
    private String store_id = "", store_name;
    private String qu_city = "哈尔滨", huan_city = "哈尔滨";
    private RelativeLayout rl_time;
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private List<List<String>> options3Items = new ArrayList<>();
    private TextView tv_total_time;
    private String qu_time_date;
    private long duration;
    private String mYear;
    private String huan_time_date;
    private List<RentCarBean.DataBean.CarSourceHotBean> carSourceHot;
    private String qu_latitude_usedcar;
    private String qu_longitude_usedcar;
    private String qu_name_usedcar;
    private String huan_latitude_usedcar;
    private String huan_longitude_usedcar;
    private String huan_name_usedcar;


    @Override
    protected int initContentView() {
        return R.layout.rent_car_activity;
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        lv_car_tuijian = (AdListView) view.findViewById(R.id.lv_car_tuijian);
        rl_time = (RelativeLayout) view.findViewById(R.id.rl_time);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        fb_rent_car = (UsedCarFlyBanner) view.findViewById(R.id.fb_rent_car);
        vt_now_chose = (Button) view.findViewById(R.id.vt_now_chose);
        iv_qu_sd = (ImageView) view.findViewById(R.id.iv_qu_sd);
        tv_total_time = (TextView) view.findViewById(R.id.tv_total_time);
        iv_jiantou = (ImageView) view.findViewById(R.id.iv_jiantou);
        iv_huan_sd = (ImageView) view.findViewById(R.id.iv_huan_sd);
        tv_qu_address = (TextView) view.findViewById(R.id.tv_qu_address);
        tv_huan_address = (TextView) view.findViewById(R.id.tv_huan_address);
        tv_qu_city = (TextView) view.findViewById(R.id.tv_qu_city);
        tv_huan_city = (TextView) view.findViewById(R.id.tv_huan_city);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        scroll_view.smoothScrollBy(0, 0);
        tv_login_back.setText("我要试车");
        PrefUtils.putString(App.context, "shangMenHuan", "1");
        PrefUtils.putString(App.context, "shangMenqu", "1");

    }

    @Override
    protected void onResume() {
        super.onResume();
        fb_rent_car.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        fb_rent_car.stopAutoPlay();

    }

    @Override
    protected void initListener() {
        rl_time.setOnClickListener(this);
        tv_qu_city.setOnClickListener(this);
        tv_huan_city.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        vt_now_chose.setOnClickListener(this);
        iv_qu_sd.setOnClickListener(this);
        iv_huan_sd.setOnClickListener(this);
        tv_huan_address.setOnClickListener(this);
        tv_qu_address.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 获取当前年份
        mYear = String.valueOf(c.get(Calendar.YEAR));
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前日份的日期号码
        List<String> date1 = DateUtils.getMonthdate(mYear, mMonth, mDay);
        ArrayList<String> date2 = DateUtils.getHh();
        ArrayList<String> date3 = new ArrayList<>();
        date3.add("00:30");
        for (int i = 1; i < 24; i++) {
            date3.add(DateUtils.fillZero(i) + ":00");
            date3.add(DateUtils.fillZero(i) + ":30");
        }
        date3.add("24:00");
        options1Items.addAll(date1);
        options2Items.add(date2);
        List<String> strings = date2.subList(1, date2.size());
        options3Items.add(strings);
        for (int i = 1; i < 100; i++) {
            options2Items.add(date3);
            options3Items.add(date3);
        }
        getdate();
        lv_car_tuijian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RentCarBean.DataBean.CarSourceHotBean carSourceHotBean = carSourceHot.get(i);
                if (carSourceHotBean != null) {
                    int id = carSourceHotBean.getId();
                    String htmlUrl = carSourceHotBean.getHtmlUrl();
                    if (!TextUtils.isEmpty("" + id)) {
                        Intent intent1 = new Intent(App.context, UsedCarContentActivity.class);
                        intent1.putExtra("carsource_id", id + "");
                        intent1.putExtra("htmlUrl",htmlUrl);
                        intent1.putExtra("cartype", "buy");
                        intent1.putExtra("qu_time_date","");
                        intent1.putExtra("huan_time_date","");
                        intent1.putExtra("store_id_usedcar","");
                        intent1.putExtra("duration","");
                        intent1.putExtra("qu_city_usedcar","");
                        intent1.putExtra("huan_city_usedcar","");
                        intent1.putExtra("shangMenQu","");
                        intent1.putExtra("shangMenHuan","");
                        intent1.putExtra("qu_latitude_usedcar","");
                        intent1.putExtra("qu_longitude_usedcar","");
                        intent1.putExtra("qu_name_usedcar","");
                        intent1.putExtra("huan_latitude_usedcar","");
                        intent1.putExtra("huan_longitude_usedcar","");
                        intent1.putExtra("huan_name_usedcar","");
                        startActivity(intent1);
                    }
                }
            }
        });
    }

    private void lunbotu(List<RentCarBean.DataBean.BroadcastListBean> broadcastList) {
        List<String> imageurls = new ArrayList<String>();
        if (broadcastList != null) {
            for (RentCarBean.DataBean.BroadcastListBean broadcastListBean : broadcastList) {
                String broadcast_pic = broadcastListBean.getBroadcast_pic();
                if (!TextUtils.isEmpty(broadcast_pic)) {
                    imageurls.add(broadcast_pic);
                }
            }
            fb_rent_car.setImagesUrl(imageurls);
        }
    }

    private void getdate() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String id = LoginUtils.getId(this);
            OkHttpUtils.post().url(AppUrl.Rent_Car)
                    .addParams("device_id", id)
                    .addParams("area", PrefUtils.getString(this, "usedcarcity", ""))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final RentCarBean rentCarBean = JsonUtil.parseJsonToBean(string, RentCarBean.class);
                        if (rentCarBean != null) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    RentCarBean.DataBean data = rentCarBean.getData();
                                    if (data != null) {
                                        List<RentCarBean.DataBean.BroadcastListBean> broadcastList = data.getBroadcastList();
                                        //热门推荐车辆
                                        carSourceHot = data.getCarSourceHot();
                                        //轮播图
                                        broadcastList = data.getBroadcastList();
                                        if (carSourceHot != null) {
                                            lv_car_tuijian.setAdapter(new RentCarAdapter(carSourceHot));
                                        }
                                        if (broadcastList != null) {
                                            lunbotu(broadcastList);
                                        }
                                    }
                                }
                            });

                        }
                        return string;
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
    public void onClick(View view) {
        String qu = tv_qu_address.getText().toString();
        String huan = tv_huan_address.getText().toString();
        String start_time = tv_start_time.getText().toString();
        String end_time = tv_end_time.getText().toString();
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rl_time:
                startTimePicker();
                break;
            case R.id.tv_qu_city:
                Intent intent4 = new Intent(this, RentCarCityActivity.class);
                intent4.putExtra("jiesong_type", "1");
                startActivityForResult(intent4, 4);
                break;
            case R.id.tv_huan_city:
                if (qu.contains("请选择")) {
                    showToastShort("请选择取车方式和地址");
                    return;
                }
                if (isShangMenQu) {
                    Intent intent5 = new Intent(this, RentCarCityActivity.class);
                    intent5.putExtra("jiesong_type", "2");
                    startActivityForResult(intent5, 5);
                }
                break;
            case R.id.vt_now_chose:
                if (TextUtils.isEmpty(qu_city)) {
                    qu_city = "哈尔滨市";
                }
                if (TextUtils.isEmpty(huan_city)) {
                    huan_city = "哈尔滨市";
                }

                if (qu.contains("请选择")) {
                    showToastShort("请选择取还方式和地址");
                    return;
                }
                if (huan.contains("请选择")) {
                    showToastShort("请选择取还方式和地址");
                    return;
                }
                if (start_time.contains("请选择")) {
                    showToastShort("请选择开始时间");
                    return;
                }
                if (end_time.contains("请选择")) {
                    showToastShort("请选择结束时间");
                    return;
                }
                Intent intent = new Intent(this, ChooseCarActivity.class);
                //开始时间
                intent.putExtra("qu_time_date",qu_time_date);
                //结束时间
                intent.putExtra("huan_time_date",huan_time_date);
                //门店id
                intent.putExtra("store_id_usedcar",store_id);
                //间隔天数
                intent.putExtra("duration",duration+ "");
                //取车城市
                intent.putExtra("qu_city_usedcar",qu_city);
                //还车城市
                intent.putExtra("huan_city_usedcar",huan_city);

                intent.putExtra("qu_latitude_usedcar",qu_latitude_usedcar);
                intent.putExtra("qu_longitude_usedcar",qu_longitude_usedcar);
                intent.putExtra("qu_name_usedcar",qu_name_usedcar);
                intent.putExtra("huan_latitude_usedcar",huan_latitude_usedcar);
                intent.putExtra("huan_longitude_usedcar",huan_longitude_usedcar);
                intent.putExtra("huan_name_usedcar",huan_name_usedcar);
                //是否上门
                if (isShangMenQu) {
                    intent.putExtra("shangMenQu","2");
                } else {
                    intent.putExtra("shangMenQu","1");
                }
                if (isShangMenHuan) {
                    intent.putExtra("shangMenHuan","2");
                } else {
                    intent.putExtra("shangMenHuan","1");
                }



                startActivity(intent);
                break;
            case R.id.tv_qu_address:
                if (isShangMenQu) {
                    //选择地址  接  取
                    Intent intent2 = new Intent(App.context, POIDressActivity.class);
                    intent2.putExtra("jiesong_type", "1");
                    intent2.putExtra("cartype", "2");
                    startActivityForResult(intent2, 1);
                } else {
                    //选择门店
                    Intent intent1 = new Intent(this, ChooseStoreActivity.class);
                    intent1.putExtra("city", qu_city);
                    startActivityForResult(intent1, 2);
                }
                break;
            case R.id.tv_huan_address:
                if (qu.contains("请选择")) {
                    showToastShort("请选择取车方式和地址");
                    return;
                }
                if (isShangMenHuan) {
                    //选择地址  送  还
                    Intent intent3 = new Intent(App.context, POIDressActivity.class);
                    intent3.putExtra("jiesong_type", "2");
                    intent3.putExtra("cartype", "2");
                    startActivityForResult(intent3, 3);
                } else {
                    if (isShangMenQu) {
                        //选择门店
                        Intent intent1 = new Intent(this, ChooseStoreActivity.class);
                        intent1.putExtra("city", huan_city);
                        startActivityForResult(intent1, 2);
                    }
                }
                break;
            case R.id.iv_qu_sd:

                if (isShangMenQu) {
                    iv_qu_sd.setImageResource(R.mipmap.used_car_rent_daodian);
                    tv_qu_address.setText("请选择门店");
                    isShangMenQu = false;
                } else {
                    iv_qu_sd.setImageResource(R.mipmap.used_car_rent_shangmen);
                    tv_qu_address.setText("请选择地址");
                    isShangMenQu = true;
                }
                break;
            case R.id.iv_huan_sd:
                if (qu.contains("请选择")) {
                    showToastShort("请选择取车方式和地址");
                    return;
                }
                if (isShangMenHuan) {
                    iv_huan_sd.setImageResource(R.mipmap.used_car_rent_daodian);
                    if (isShangMenQu) {
                        tv_huan_address.setText("请选择门店");
                    } else {
                        tv_huan_address.setText(qu);
                    }
                    isShangMenHuan = false;
                } else {
                    iv_huan_sd.setImageResource(R.mipmap.used_car_rent_shangmen);
                    tv_huan_address.setText("请选择地址");
                    isShangMenHuan = true;
                }
                break;
        }
    }


    //开始时间
    public void startTimePicker() {
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                qu_time_date = options1Items.get(options1) +" "+
                        options2Items.get(options1).get(options2);
                tv_start_time.setText(qu_time_date);

                endTimePicker(options1);
            }
        })

                .setTitleText("取车时间")
                .setDividerColor(getResources().getColor(R.color._cccccc))
                .setCancelColor(getResources().getColor(R.color._3232))
                .setSubmitColor(getResources().getColor(R.color._3232))
                .setTextColorCenter(getResources().getColor(R.color.colorOrange)) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setSelectOptions();
        pvOptions.show();
    }

    //结束时间
    public void endTimePicker(int options) {
        String kais = options1Items.get(0);
        String s = options1Items.get(options);
        String substring1 = kais.substring(0, 2);
        String substring2 = s.substring(0, 2);
        String day = s.substring(3, 5);
        int a = Integer.parseInt(substring1);
        int b = Integer.parseInt(substring2);
        int year = Integer.parseInt(mYear);
        if (b < a) {
            year += 1;
        }
        final List<String> monthdate = DateUtils.getMonthdate(year + "", substring2, day);
        final List<List<String>> lists = options3Items.subList(options, options3Items.size());
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                huan_time_date = monthdate.get(options1)+" "+
                        lists.get(options1).get(options2);
                tv_end_time.setText(huan_time_date);
                iv_jiantou.setVisibility(View.INVISIBLE);
                tv_total_time.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(qu_time_date) && !TextUtils.isEmpty(huan_time_date)) {
                    String qu_date = qu_time_date.substring(0, 6);
                    String qu_time = qu_time_date.substring(10, 16);
                    String huan_date = huan_time_date.substring(0, 6);
                    String huan_time = huan_time_date.substring(10, 16);
                    duration = DateUtils.dateDiff(qu_date + qu_time, huan_date + huan_time);
                    tv_total_time.setText(duration + "天");

                }

            }
        })
                .setTitleText("还车时间")
                .setDividerColor(getResources().getColor(R.color._cccccc))
                .setCancelColor(getResources().getColor(R.color._3232))
                .setSubmitColor(getResources().getColor(R.color._3232))
                .setTextColorCenter(getResources().getColor(R.color.colorOrange)) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(monthdate, lists);//二级选择器
        pvOptions.setSelectOptions();
        pvOptions.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 111:
                qu_latitude_usedcar = extras.getString("latitude");
                qu_longitude_usedcar =extras.getString("longitude");
                qu_name_usedcar = extras.getString("name");
                if (TextUtils.isEmpty(qu_name_usedcar)) {
                    tv_qu_address.setText("请选择地址");
                } else {
                    tv_qu_address.setText(qu_name_usedcar);
                }
                PrefUtils.putString(this, "qu_latitude_usedcar", qu_latitude_usedcar);
                PrefUtils.putString(this, "qu_longitude_usedcar", qu_longitude_usedcar);
                PrefUtils.putString(this, "qu_name_usedcar", qu_name_usedcar);
                break;
            case 222:
                store_id = extras.getString("store_id");
                store_name = extras.getString("store_name");
                if (!TextUtils.isEmpty(store_name)) {
                    tv_qu_address.setText(store_name);
                    tv_huan_address.setText(store_name);
                    iv_huan_sd.setImageResource(R.mipmap.used_car_rent_daodian);
                    iv_qu_sd.setImageResource(R.mipmap.used_car_rent_daodian);
                    isShangMenHuan = false;
                    isShangMenQu = false;
                } else {
                    tv_qu_address.setText("请选择门店");
                    tv_huan_address.setText("请选择门店");
                }
                break;
            case 333:
                huan_latitude_usedcar = extras.getString("latitude");
                huan_longitude_usedcar = extras.getString("longitude");
                huan_name_usedcar = extras.getString("name");
                if (TextUtils.isEmpty(huan_name_usedcar)) {
                    tv_huan_address.setText("请选择地址");
                } else {
                    tv_huan_address.setText(huan_name_usedcar);
                }
                PrefUtils.putString(this, "qu_latitude_usedcar", huan_latitude_usedcar);
                PrefUtils.putString(this, "qu_longitude_usedcar", huan_longitude_usedcar);
                PrefUtils.putString(this, "qu_name_usedcar", huan_name_usedcar);
                break;
            case 444:
                qu_city = extras.getString("city");
                tv_qu_city.setText(qu_city);
                PrefUtils.putString(this, "qu_city_usedcar", qu_city);
                if (isShangMenQu) {
                    tv_qu_address.setText("请选择地址");
                } else {
                    tv_qu_address.setText("请选择门店");
                }
                break;
            case 555:
                huan_city = extras.getString("city");
                tv_huan_city.setText(huan_city);
                PrefUtils.putString(this, "huan_city_usedcar", huan_city);
                if (isShangMenHuan) {
                    tv_huan_address.setText("请选择地址");
                } else {
                    tv_huan_address.setText("请选择门店");
                }
                break;


        }
    }

    private class RentCarAdapter extends BaseAdapter {
        private List<RentCarBean.DataBean.CarSourceHotBean> carSourceHot;

        public RentCarAdapter(List<RentCarBean.DataBean.CarSourceHotBean> carSourceHot) {
            this.carSourceHot = carSourceHot;
        }

        @Override
        public int getCount() {
            return carSourceHot.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HotViewHolder hotViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.used_car_list_item, null);
                hotViewHolder = new HotViewHolder(view);
                view.setTag(hotViewHolder);
            } else {
                hotViewHolder = (HotViewHolder) view.getTag();
            }
            if (hotViewHolder != null) {
                RentCarBean.DataBean.CarSourceHotBean carSourceHotBean = carSourceHot.get(i);
                if (carSourceHotBean != null) {
                    String just_side_img = carSourceHotBean.getJust_side_img();
                    if (!TextUtils.isEmpty(just_side_img)) {
                        Picasso.with(App.context).load(just_side_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(hotViewHolder.iv_used_car_photo);
                    }
                    int status = carSourceHotBean.getStatus();
                    if (1 == status) {
                        //出租中
                        hotViewHolder.iv_used_car_state.setVisibility(View.VISIBLE);
                    } else {
                        hotViewHolder.iv_used_car_state.setVisibility(View.INVISIBLE);
                    }
                    String car_allname = carSourceHotBean.getCar_allname();
                    hotViewHolder.tv_usedcar_title.setText(car_allname);
                    //月供
                    String loan_month = carSourceHotBean.getLoan_month();
                    //首付
                    String loan_first = carSourceHotBean.getLoan_first();
                    hotViewHolder.tv_money_buy_loan.setText(loan_first + "  " + loan_month);
                    //上牌时间公里数
                    String last_mileage = carSourceHotBean.getLast_mileage();
                    hotViewHolder.tv_shangpan_gongli.setText(last_mileage);
                    //售价
                    String car_price = carSourceHotBean.getCar_price();
                    //新车售价
                    String new_car_price = carSourceHotBean.getNew_car_price();
                    hotViewHolder.tv_car_price.setText(car_price );
                    hotViewHolder.tv_new_car_price.setText(new_car_price);
                    //平台租车价
                    String rent_price = carSourceHotBean.getRent_price();
                    //新车租车价
                    String new_rent_price = carSourceHotBean.getNew_rent_price();
                    hotViewHolder.tv_rent_price.setText(rent_price);
                    hotViewHolder.tv_new_rent_price.setText(new_rent_price );
                    //车辆是否租
                    int rent_status = carSourceHotBean.getRent_status();
                    if (0 == rent_status) {
                        hotViewHolder.ll_rent_content.setVisibility(View.INVISIBLE);
                    } else if (1 == rent_status) {
                        hotViewHolder.ll_rent_content.setVisibility(View.VISIBLE);
                    }
                }

            }
            return view;
        }

        class HotViewHolder {
            private LinearLayout ll_rent_content;
            private TextView tv_usedcar_title, tv_money_buy_loan, tv_shangpan_gongli, tv_car_price, tv_new_car_price, tv_new_rent_price, tv_rent_price;
            private ImageView iv_used_car_photo, iv_used_car_state;

            public HotViewHolder(View view) {
                iv_used_car_photo = (ImageView) view.findViewById(R.id.iv_used_car_photo);
                iv_used_car_state = (ImageView) view.findViewById(R.id.iv_used_car_state);
                tv_usedcar_title = (TextView) view.findViewById(R.id.tv_usedcar_title);
                tv_money_buy_loan = (TextView) view.findViewById(R.id.tv_money_buy_loan);
                tv_shangpan_gongli = (TextView) view.findViewById(R.id.tv_shangpan_gongli);
                tv_car_price = (TextView) view.findViewById(R.id.tv_car_price);
                tv_new_car_price = (TextView) view.findViewById(R.id.tv_new_car_price);
                tv_new_rent_price = (TextView) view.findViewById(R.id.tv_new_rent_price);
                tv_rent_price = (TextView) view.findViewById(R.id.tv_rent_price);
                ll_rent_content = (LinearLayout) view.findViewById(R.id.ll_rent_content);
            }
        }
    }
}
