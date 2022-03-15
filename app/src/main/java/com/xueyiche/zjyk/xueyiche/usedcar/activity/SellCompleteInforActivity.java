package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.view.BasicPopup;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.AllCityBean;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarSuccessBean;
import com.xueyiche.zjyk.xueyiche.usedcar.view.OptionsPickerView;
import com.xueyiche.zjyk.xueyiche.usedcar.view.WheelView;
import com.xueyiche.zjyk.xueyiche.utils.GregorianLunarCalendarView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Owner on 2018/7/2.
 */
public class SellCompleteInforActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_login_back, tv_tijiao;
    private LinearLayout ll_exam_back;
    private TextView et_kil;
    private RelativeLayout rl_brand, rl_car_location, rl_is_bendi, rl_guohu_number, rl_shangpan_time, rl_car_kuang, rl_follow_up;
    private ArrayList<String> localCarCardList = new ArrayList<>();
    private ArrayList<String> guohuNumberList = new ArrayList<>();
    private ArrayList<String> vehicleConditionList = new ArrayList<>();
    private ArrayList<String> planList = new ArrayList<>();
    private ArrayList<AllCityBean> proviceList = new ArrayList<>();
    private ArrayList<ArrayList<String>> cityList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> quList = new ArrayList<>();
    //选择后的显示
    private TextView tv_choose_car_style, tv_choose_car_location, tv_local_car_card, tv_choose_transfer_number, tv_choose_first_onTheCards_time, tv_choose_vehicle_condition, tv_choose_followUp_plan;
    private String system_name;
    private String brand_id;
    private String carSystem_id;
    private String tel;
    private String brandname;


    @Override
    protected int initContentView() {
        return R.layout.sell_complete_infor_activity;
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        et_kil = (TextView) view.findViewById(R.id.et_kil);
        tv_choose_car_style = (TextView) view.findViewById(R.id.tv_choose_car_style);
        tv_choose_car_location = (TextView) view.findViewById(R.id.tv_choose_car_location);
        tv_choose_transfer_number = (TextView) view.findViewById(R.id.tv_choose_transfer_number);
        tv_choose_first_onTheCards_time = (TextView) view.findViewById(R.id.tv_choose_first_onTheCards_time);
        tv_choose_vehicle_condition = (TextView) view.findViewById(R.id.tv_choose_vehicle_condition);
        tv_choose_followUp_plan = (TextView) view.findViewById(R.id.tv_choose_followUp_plan);
        tv_local_car_card = (TextView) view.findViewById(R.id.tv_local_car_card);
        tv_tijiao = (TextView) view.findViewById(R.id.tv_tijiao);
        //品牌车系
        rl_brand = (RelativeLayout) view.findViewById(R.id.rl_brand);
        //车辆所在地
        rl_car_location = (RelativeLayout) view.findViewById(R.id.rl_car_location);
        //是否是本地
        rl_is_bendi = (RelativeLayout) view.findViewById(R.id.rl_is_bendi);
        //过户次数
        rl_guohu_number = (RelativeLayout) view.findViewById(R.id.rl_guohu_number);
        //上牌时间
        rl_shangpan_time = (RelativeLayout) view.findViewById(R.id.rl_shangpan_time);
        //车况
        rl_car_kuang = (RelativeLayout) view.findViewById(R.id.rl_car_kuang);
        //后续计划
        rl_follow_up = (RelativeLayout) view.findViewById(R.id.rl_follow_up);
        tv_login_back.setText("补充信息");
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        rl_brand.setOnClickListener(this);
        rl_car_location.setOnClickListener(this);
        rl_is_bendi.setOnClickListener(this);
        rl_guohu_number.setOnClickListener(this);
        rl_shangpan_time.setOnClickListener(this);
        rl_car_kuang.setOnClickListener(this);
        rl_follow_up.setOnClickListener(this);
        tv_tijiao.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tel = getIntent().getStringExtra("tel");
        String city = PrefUtils.getString(App.context, "city", "");
        String province = PrefUtils.getString(App.context, "province", "");
        String district = PrefUtils.getString(App.context, "district", "");
        if (!TextUtils.isEmpty(city) && !TextUtils.isEmpty(province) && !TextUtils.isEmpty(district)) {
            tv_choose_car_location.setText(city + province + district);
        }
        //是否是当地车牌
        localCarCardList.add("是");
        localCarCardList.add("否");
        //过户次数
        guohuNumberList.add("0次");
        guohuNumberList.add("1次");
        guohuNumberList.add("2次");
        guohuNumberList.add("3次");
        guohuNumberList.add("4次");
        guohuNumberList.add("4次以上");
        guohuNumberList.add("不清楚");
        //车况信息
        vehicleConditionList.add("车况好，没有任何事故");
        vehicleConditionList.add("有过少量剐蹭和钣金");
        vehicleConditionList.add("有发生过伤及汽车主体框架的碰撞事故");
        vehicleConditionList.add("有水泡或者火烧历史");
        //后续计划
        planList.add("团购新车");
        planList.add("买二手车");
        planList.add("不买车");
        initJsonData();
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String json = getJson(App.context, "province.json");

        ArrayList<AllCityBean> jsonBean = parseData(json);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        proviceList = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            cityList.add(CityList);
            /**
             * 添加地区数据
             */
            quList.add(Province_AreaList);
        }
    }

    public ArrayList<AllCityBean> parseData(String result) {//Gson 解析
        ArrayList<AllCityBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AllCityBean entity = gson.fromJson(data.optJSONObject(i).toString(), AllCityBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    public static String getJson(Context mContext, String fileName) {
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rl_brand:
                Intent intent = new Intent(App.context, PinPaiActivity.class);
                startActivityForResult(intent, 11);
                break;
            case R.id.rl_car_location:
                //车辆所在地
                ShowCarLocationPickerView();
                break;
            case R.id.rl_is_bendi:
                //是否是本地
                localCarCardPicker();
                break;
            case R.id.rl_guohu_number:
                //过户次数
                guohuNumberPicker();
                break;
            case R.id.rl_shangpan_time:
                //上牌时间
                getCardTimePicker();
                break;
            case R.id.rl_car_kuang:
                //车况
                vehicleConditionPicker();
                break;
            case R.id.rl_follow_up:
                //后续计划
                planPicker();
                break;
            case R.id.tv_tijiao:
                String choose_car_style = tv_choose_car_style.getText().toString();
                String choose_car_location = tv_choose_car_location.getText().toString();
                String local_car_card = tv_local_car_card.getText().toString();
                String choose_transfer_number = tv_choose_transfer_number.getText().toString();
                String choose_first_onTheCards_time = tv_choose_first_onTheCards_time.getText().toString();
                String choose_vehicle_condition = tv_choose_vehicle_condition.getText().toString();
                String choose_followUp_plan = tv_choose_followUp_plan.getText().toString();
                String kil = et_kil.getText().toString().trim();
                if (TextUtils.isEmpty(choose_car_style)) {
                    Toast.makeText(SellCompleteInforActivity.this, "请选择品牌车系！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(kil)) {
                    Toast.makeText(SellCompleteInforActivity.this, "请填写当前里程！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(choose_car_location)) {
                    Toast.makeText(SellCompleteInforActivity.this, "请选择车辆所在地！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(local_car_card)) {
                    Toast.makeText(SellCompleteInforActivity.this, "请选择是否是当地车牌！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(choose_transfer_number)) {
                    Toast.makeText(SellCompleteInforActivity.this, "请选择过户次数！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(choose_first_onTheCards_time)) {
                    Toast.makeText(SellCompleteInforActivity.this, "请选择初次上牌时间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(choose_vehicle_condition)) {
                    Toast.makeText(SellCompleteInforActivity.this, "请选择车况！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(choose_followUp_plan)) {
                    Toast.makeText(SellCompleteInforActivity.this, "请选择后续计划", Toast.LENGTH_SHORT).show();
                    return;
                }
                BigDecimal bd_kill = new BigDecimal(kil);
                BigDecimal bigDecimal = bd_kill.setScale(2, RoundingMode.CEILING);
                sendData(bigDecimal,choose_car_location,local_car_card,choose_transfer_number
                        ,choose_first_onTheCards_time,choose_vehicle_condition,choose_followUp_plan);
                break;
        }
    }

    private void sendData(BigDecimal bigDecimal, String chooseCarLocation, String local_car_card,
                          String choose_transfer_number, String choose_first_onTheCards_time,
                          String choose_vehicle_condition, String choose_car_location) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String id = LoginUtils.getId(this);
            String substring = choose_first_onTheCards_time.substring(0, 8);
            OkHttpUtils.post().url(AppUrl.Sell_Car_Submit)
                    .addParams("device_id", id)
                    .addParams("tel",tel )
                    .addParams("brand_id", brand_id)
                    .addParams("brand_name", brandname)
                    .addParams("carsystem_id", carSystem_id)
                    .addParams("system_name",system_name )
                    .addParams("mileage",bigDecimal+"")
                    .addParams("city", chooseCarLocation)
                    .addParams("license_plate","是".equals(local_car_card)?"1":"0")
                    .addParams("transfer",choose_transfer_number)
                    .addParams("first_last_time",substring)
                    .addParams("car_condition",choose_vehicle_condition )
                    .addParams("plan",choose_car_location)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final UsedCarSuccessBean usedCarSuccessBean = JsonUtil.parseJsonToBean(string, UsedCarSuccessBean.class);
                        if (usedCarSuccessBean != null) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int code = usedCarSuccessBean.getCode();
                                    String message = usedCarSuccessBean.getMessage();
                                    showToastShort(message);
                                    if (200==code) {
                                        finish();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        switch (resultCode) {
            case 123:
                brand_id = extras.getString("brand_id");
                carSystem_id = extras.getString("carSystem_id");
                system_name = extras.getString("system_name");
                brandname = extras.getString("brandname");
                tv_choose_car_style.setText(system_name);
                break;
        }
    }

    //车辆所在地
    private void ShowCarLocationPickerView() {
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = proviceList.get(options1).getPickerViewText() +
                        cityList.get(options1).get(options2) +
                        quList.get(options1).get(options2).get(options3);
                tv_choose_car_location.setText(tx);
            }
        })
                .setTitleText("请选择车辆所在地")
                .setDividerColor(getResources().getColor(R.color._cccccc))
                .setCancelColor(getResources().getColor(R.color._3232))
                .setSubmitColor(getResources().getColor(R.color._3232))
                .setTextColorCenter(getResources().getColor(R.color.colorOrange)) //设置选中项文字颜色
                .setContentTextSize(20)
                .setTitleSize(20)
                .build();
        pvOptions.setPicker(proviceList, cityList, quList);//三级选择器
        pvOptions.setSelectOptions();
        pvOptions.show();
    }

    //当地车牌
    public void localCarCardPicker() {
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = localCarCardList.get(options1);
                tv_local_car_card.setText(tx);
            }
        })
                .setTitleText("是否是当地车牌")
                .setDividerColor(getResources().getColor(R.color._cccccc))
                .setCancelColor(getResources().getColor(R.color._3232))
                .setSubmitColor(getResources().getColor(R.color._3232))
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(getResources().getColor(R.color.colorOrange)) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(localCarCardList);//一级选择器
        pvOptions.setSelectOptions();
        pvOptions.show();
    }

    //过户次数
    public void guohuNumberPicker() {
        if (guohuNumberList.size() != 0) {
            final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = guohuNumberList.get(options1);
                    tv_choose_transfer_number.setText(tx);
                }
            })
                    .setTitleText("请选择过户次数")
                    .setDividerColor(getResources().getColor(R.color._cccccc))
                    .setCancelColor(getResources().getColor(R.color._3232))
                    .setSubmitColor(getResources().getColor(R.color._3232))
                    .setDividerType(WheelView.DividerType.WRAP)
                    .setTextColorCenter(getResources().getColor(R.color.colorOrange)) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(guohuNumberList);//一级选择器
            pvOptions.setSelectOptions();
            pvOptions.show();

        }
    }

    //车况信息
    public void vehicleConditionPicker() {
        if (vehicleConditionList.size() != 0) {
            final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = vehicleConditionList.get(options1);
                    tv_choose_vehicle_condition.setText(tx);
                }
            })
                    .setTitleText("请选择车况信息")
                    .setDividerColor(getResources().getColor(R.color._cccccc))
                    .setCancelColor(getResources().getColor(R.color._3232))
                    .setSubmitColor(getResources().getColor(R.color._3232))
                    .setDividerType(WheelView.DividerType.WRAP)
                    .setTextColorCenter(getResources().getColor(R.color.colorOrange)) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(vehicleConditionList);//一级选择器
            pvOptions.setSelectOptions();
            pvOptions.show();
        }

    }

    //后续计划
    public void planPicker() {
        if (planList.size() != 0) {
            final OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = planList.get(options1);
                    tv_choose_followUp_plan.setText(tx);
                }
            })
                    .setTitleText("请选择车况信息")
                    .setDividerColor(getResources().getColor(R.color._cccccc))
                    .setCancelColor(getResources().getColor(R.color._3232))
                    .setSubmitColor(getResources().getColor(R.color._3232))
                    .setDividerType(WheelView.DividerType.WRAP)
                    .setTextColorCenter(getResources().getColor(R.color.colorOrange)) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(planList);//一级选择器
            pvOptions.setSelectOptions();
            pvOptions.show();
        }

    }

    /**
     * BigDecimal shouxu = new BigDecimal(zhekou);
     * BigDecimal xianshimoney = new BigDecimal(zhe + "");
     * shouxuqian = xianshimoney.multiply(shouxu);
     * finishshouxu = shouxuqian.setScale(2, RoundingMode.CEILING);
     */
    //初次上牌时间
    public void getCardTimePicker() {
        BasicPopup basicPopup = new BasicPopup(SellCompleteInforActivity.this) {
            @Override
            protected View makeContentView() {
                View view = LayoutInflater.from(App.context).inflate(R.layout.practice_pop, null);
                final GregorianLunarCalendarView mGLCView = (GregorianLunarCalendarView) view.findViewById(R.id.calendar_view);
                mGLCView.init();
                TextView tv_exit = (TextView) view.findViewById(R.id.tv_exit);
                TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
                TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                tv_title.setText("请选择初次上牌时间");
                RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
                tv_ok.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String month = "";
                        String day = "";
                        GregorianLunarCalendarView.CalendarData calendarData = mGLCView.getCalendarData();
                        Calendar calendar = calendarData.getCalendar();
                        int iDay = calendar.get(Calendar.DAY_OF_MONTH);
                        int iMonth = calendar.get(Calendar.MONTH) + 1;
                        if (iMonth > 0 && iMonth < 10) {
                            month = "0" + iMonth;
                        } else {
                            month = "" + iMonth;
                        }
                        if (iDay > 0 && iDay < 10) {
                            day = "0" + iDay;
                        } else {
                            day = "" + iDay;
                        }
                        String showToast = calendar.get(Calendar.YEAR) + "年" + month + "月" + day;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd");
                        try {
                            Long second = simpleDateFormat.parse(showToast).getTime();
                            Date date = new Date(System.currentTimeMillis());
                            long time = date.getTime();
                            if (time >= second) {
                                String down_card_time = showToast;
                                tv_choose_first_onTheCards_time.setText(down_card_time);
                                dismiss();
                            } else {
                                showToastShort("时间选择有误");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });
                parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();

                    }
                });
                tv_exit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        dismiss();
                    }
                });
                return view;
            }
        };
        basicPopup.show();
    }

}
