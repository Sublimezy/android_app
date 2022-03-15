package com.xueyiche.zjyk.xueyiche.submit;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.usedcar.activity.FeiYongMingXiActivity;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.SubmitUsedCarBean;
import com.xueyiche.zjyk.xueyiche.usedcar.view.OptionsPickerView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ZL on 2018/6/29.
 */
public class UsedCarSubmitIndent extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle, tv_pay, tv_mingxi, tv_name, change_getcar_date, change_returncar_date;
    private TextView tv_getcar_address, tv_getcar_date, tv_returncar_address, tv_returncar_date, tv_total_date, tv_total_price, tv_pay_price;
    private ImageView iv_head;
    private String mYear;
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private List<List<String>> options3Items = new ArrayList<>();
    private int choose_positon;
    public static UsedCarSubmitIndent instance;
    private String qu_time_date, huan_city, huan_time_date, qu_city, store_id, shangMenQu, shangMenHuan, duration;
    private String violated_deposit, car_deposit, shop_name, qu_latitude_usedcar, qu_longitude_usedcar, qu_name_usedcar, huan_latitude_usedcar,
            huan_longitude_usedcar, huan_name_usedcar, rent_price, carsource_id;
    @Override
    protected int initContentView() {
        return R.layout.submit_usedcar_indent_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.submit_usedcar_indent_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.submit_usedcar_indent_include).findViewById(R.id.tv_login_back);
        tv_pay = (TextView) view.findViewById(R.id.tv_pay);
        //名字
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        //车辆照片
        iv_head = (ImageView) view.findViewById(R.id.iv_head);
        //取车地址
        tv_getcar_address = (TextView) view.findViewById(R.id.tv_getcar_address);
        //取车日期
        tv_getcar_date = (TextView) view.findViewById(R.id.tv_getcar_date);
        //还车地址
        tv_returncar_address = (TextView) view.findViewById(R.id.tv_returncar_address);
        //还车日期
        tv_returncar_date = (TextView) view.findViewById(R.id.tv_returncar_date);
        //总共的时间
        tv_total_date = (TextView) view.findViewById(R.id.tv_total_date);
        //总共的价格
        tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
        //支付时显示的价格
        tv_pay_price = (TextView) view.findViewById(R.id.tv_pay_price);
        tv_mingxi = (TextView) view.findViewById(R.id.tv_mingxi);
        change_getcar_date = (TextView) view.findViewById(R.id.change_getcar_date);
        change_returncar_date = (TextView) view.findViewById(R.id.change_returncar_date);
        instance = this;
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        tv_mingxi.setOnClickListener(this);
        change_getcar_date.setOnClickListener(this);
        change_returncar_date.setOnClickListener(this);
        tv_pay.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String total_price = tv_pay_price.getText().toString();
                if ("0".equals(total_price)) {
                    showToastShort("请选择时间");
                    return;
                }
                sendData(total_price);
            }
        });

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        carsource_id = intent.getStringExtra("carsource_id");
        shangMenQu = intent.getStringExtra("shangMenQu");
        shangMenHuan = intent.getStringExtra("shangMenHuan");
        qu_time_date = intent.getStringExtra("qu_time_date");
        huan_time_date = intent.getStringExtra("huan_time_date");
        qu_city = intent.getStringExtra("qu_city_usedcar");
        huan_city = intent.getStringExtra("huan_city_usedcar");
        store_id = intent.getStringExtra("store_id_usedcar");
        duration = intent.getStringExtra("duration");
        //取车地址名称和经纬度上门
        qu_latitude_usedcar = intent.getStringExtra("qu_latitude_usedcar");
        qu_longitude_usedcar = intent.getStringExtra("qu_longitude_usedcar");
        qu_name_usedcar = intent.getStringExtra("qu_name_usedcar");
        //还车地址名称和经纬度上门
        huan_latitude_usedcar = intent.getStringExtra("huan_latitude_usedcar");
        huan_longitude_usedcar = intent.getStringExtra("huan_longitude_usedcar");
        huan_name_usedcar = intent.getStringExtra("huan_name_usedcar");
        //租车单价
        rent_price = intent.getStringExtra("rent_price");
        //违章押金
        violated_deposit = intent.getStringExtra("violated_deposit");
        //车辆押金
        car_deposit = intent.getStringExtra("car_deposit");
        //门店名称
        shop_name = intent.getStringExtra("shop_name");
        String img = intent.getStringExtra("img");
        String car_allname = intent.getStringExtra("car_allname");

        if (!TextUtils.isEmpty(qu_time_date)) {
            tv_getcar_date.setText(qu_time_date);
        }
        if (!TextUtils.isEmpty(qu_name_usedcar)) {
            tv_getcar_address.setText(qu_name_usedcar);
        } else {
            tv_getcar_address.setText(shop_name);
        }

        if (!TextUtils.isEmpty(huan_name_usedcar)) {
            tv_returncar_address.setText(huan_name_usedcar);
        } else {
            tv_returncar_address.setText(shop_name);
        }

        if (!TextUtils.isEmpty(huan_time_date)) {
            tv_returncar_date.setText(huan_time_date);
        }
        if (!TextUtils.isEmpty(duration)) {
            tv_total_date.setText("共" + duration + "天");
            BigDecimal tian = new BigDecimal(duration);
            BigDecimal danjia = new BigDecimal(rent_price);
            BigDecimal multiply = tian.multiply(danjia);
            tv_total_price.setText("¥" + multiply);
            tv_pay_price.setText("" + multiply);
        }
        if (!TextUtils.isEmpty(car_allname)) {
            tv_name.setText(car_allname);
        }
        if (!TextUtils.isEmpty(img)) {
            Picasso.with(App.context).load(img).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(iv_head);
        }
        tvTitle.setText("提交订单");
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.change_getcar_date:
                startTimePicker();
                break;
            case R.id.change_returncar_date:
                endTimePicker(choose_positon);
                break;
            case R.id.tv_mingxi:
                if (TextUtils.isEmpty(duration)) {
                    showToastShort("请选择时间");
                    return;
                }
                Intent intent = new Intent(App.context, FeiYongMingXiActivity.class);
                intent.putExtra("rent_price", rent_price);
                intent.putExtra("duration", duration);
                intent.putExtra("violated_deposit", violated_deposit);
                intent.putExtra("car_deposit", car_deposit);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    public abstract class NoDoubleClickListener implements View.OnClickListener {
        public static final int MIN_CLICK_DELAY_TIME = 2000;
        private long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        }

        protected abstract void onNoDoubleClick(View v);
    }
    private void sendData(final String total_price) {
        String user_id = PrefUtils.getString(this, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.Used_Car_RntOrder)
                    .addParams("carsource_id", carsource_id)
                    .addParams("take_car_address", qu_name_usedcar)
                    .addParams("still_car_address", huan_name_usedcar)
                    .addParams("rent_start_time", qu_time_date)
                    .addParams("rent_end_time", huan_time_date)
                    .addParams("least_rent_time", duration)
                    .addParams("take_longitude", qu_longitude_usedcar)
                    .addParams("take_latitude", qu_latitude_usedcar)
                    .addParams("still_longitude", huan_longitude_usedcar)
                    .addParams("still_latitude", huan_latitude_usedcar)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("pay_type_id", "1")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final SubmitUsedCarBean submitUsedCarBean = JsonUtil.parseJsonToBean(string, SubmitUsedCarBean.class);
                        if (submitUsedCarBean != null) {
                            final int code = submitUsedCarBean.getCode();
                            final String message = submitUsedCarBean.getMessage();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    showToastShort(message);
                                    if (200 == code) {
                                        String order_number = submitUsedCarBean.getOrder_number();
                                        Intent intent1 = new Intent(App.context, AppPay.class);
                                        intent1.putExtra("pay_style", "usedcar");
                                        intent1.putExtra("order_number", order_number);
                                        intent1.putExtra("subscription", total_price + "");
                                        intent1.putExtra("jifen","0");
                                        startActivity(intent1);
                                    }
                                }
                            });
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


    //开始时间
    public void startTimePicker() {
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                qu_time_date = options1Items.get(options1) +" "+
                        options2Items.get(options1).get(options2);
                tv_getcar_date.setText(qu_time_date);
                choose_positon = options1;
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
                tv_returncar_date.setText(huan_time_date);
                if (!TextUtils.isEmpty(qu_time_date) && !TextUtils.isEmpty(huan_time_date)) {
                    String qu_date = qu_time_date.substring(0, 6);
                    String qu_time = qu_time_date.substring(10, 16);
                    String huan_date = huan_time_date.substring(0, 6);
                    String huan_time = huan_time_date.substring(10, 16);
                    duration = DateUtils.dateDiff(qu_date + qu_time, huan_date + huan_time) + "";
                    tv_total_date.setText("共" + duration + "天");
                    //计算总价
                    BigDecimal tian = new BigDecimal(duration);
                    BigDecimal danjia = new BigDecimal(rent_price);
                    BigDecimal multiply = tian.multiply(danjia);
                    tv_total_price.setText("¥" + multiply);
                    tv_pay_price.setText(multiply + "");
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
}
