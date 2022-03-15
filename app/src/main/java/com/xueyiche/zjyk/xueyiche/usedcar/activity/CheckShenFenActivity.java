package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.view.BasicPopup;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.submit.UsedCarSubmitIndent;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarSuccessBean;
import com.xueyiche.zjyk.xueyiche.utils.GregorianLunarCalendarView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by ZL on 2018/6/29.
 */
public class CheckShenFenActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle, tv_tijiao, ed_idcard_date;
    private MClearEditText ed_name, ed_idcard;
    private String qu_time_date, huan_city, huan_time_date, qu_city, store_id, shangMenQu, shangMenHuan, duration;
    private String basic_service_charge, shop_service_charge, violated_deposit, car_deposit, shop_name, qu_latitude_usedcar, qu_longitude_usedcar, qu_name_usedcar, huan_latitude_usedcar,
            huan_longitude_usedcar, huan_name_usedcar, rent_price;
    private String img;
    private String car_allname;
    private String carsource_id;

    @Override
    protected int initContentView() {
        return R.layout.check_shenfen_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.checkshenfen_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.checkshenfen_include).findViewById(R.id.tv_login_back);
        ed_name = (MClearEditText) view.findViewById(R.id.ed_name);
        ed_idcard = (MClearEditText) view.findViewById(R.id.ed_idcard);
        ed_idcard_date = (TextView) view.findViewById(R.id.ed_idcard_date);
        tv_tijiao = (TextView) view.findViewById(R.id.tv_tijiao);

    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_tijiao.setOnClickListener(this);
        ed_idcard_date.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        tvTitle.setText("核对身份信息");
        Intent intent = getIntent();
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
        //基本保障服务费
        basic_service_charge = intent.getStringExtra("basic_service_charge");
        //租车单价
        rent_price = intent.getStringExtra("rent_price");
        //门店服务费
        shop_service_charge = intent.getStringExtra("shop_service_charge");
        //违章押金
        violated_deposit = intent.getStringExtra("violated_deposit");
        //车辆押金
        car_deposit = intent.getStringExtra("car_deposit");
        //门店名称
        shop_name = intent.getStringExtra("shop_name");
        img = intent.getStringExtra("img");
        car_allname = intent.getStringExtra("car_allname");
        carsource_id = intent.getStringExtra("carsource_id");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_tijiao:
                submitDate();
                break;
            case R.id.ed_idcard_date:
                getCardTimePicker();
                break;
            default:
                break;
        }
    }

    private void submitDate() {
        String name = ed_name.getText().toString();
        String id_card = ed_idcard.getText().toString();
        String idcard_date = ed_idcard_date.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(CheckShenFenActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(id_card)) {
            Toast.makeText(CheckShenFenActivity.this, "请输入身份证号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtils.isIdCard(id_card)) {
           Toast.makeText(CheckShenFenActivity.this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(idcard_date)) {
            Toast.makeText(CheckShenFenActivity.this, "请输入证件有效期", Toast.LENGTH_SHORT).show();
            return;
        }
        String user_id = PrefUtils.getString(this, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Used_Car_IdData)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("name", name)
                    .addParams("id_number", id_card)
                    .addParams("id_effective_time", idcard_date)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        UsedCarSuccessBean usedCarSuccessBean = JsonUtil.parseJsonToBean(string, UsedCarSuccessBean.class);
                        int code = usedCarSuccessBean.getCode();
                        final String message = usedCarSuccessBean.getMessage();
                        if (200 == code) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ShenFenQueRen.instance.finish();
                                    Intent intent = new Intent(App.context, UsedCarSubmitIndent.class);
                                    commonIntent(intent);
                                    startActivity(intent);
                                    showToastShort(message);
                                }
                            });
                        }
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

    private void commonIntent(Intent intent1) {
        intent1.putExtra("carsource_id", carsource_id);
        intent1.putExtra("qu_time_date", qu_time_date);
        intent1.putExtra("huan_time_date", huan_time_date);
        intent1.putExtra("store_id_usedcar", store_id);
        intent1.putExtra("duration", duration);
        intent1.putExtra("qu_city_usedcar", qu_city);
        intent1.putExtra("huan_city_usedcar", huan_city);
        intent1.putExtra("shangMenQu", shangMenQu);
        intent1.putExtra("shangMenHuan", shangMenHuan);
        intent1.putExtra("qu_latitude_usedcar", qu_latitude_usedcar);
        intent1.putExtra("qu_longitude_usedcar", qu_longitude_usedcar);
        intent1.putExtra("qu_name_usedcar", qu_name_usedcar);
        intent1.putExtra("huan_latitude_usedcar", huan_latitude_usedcar);
        intent1.putExtra("huan_longitude_usedcar", huan_longitude_usedcar);
        intent1.putExtra("huan_name_usedcar", huan_name_usedcar);
        intent1.putExtra("car_allname", car_allname);
        intent1.putExtra("shop_service_charge", shop_service_charge);
        intent1.putExtra("basic_service_charge", basic_service_charge);
        intent1.putExtra("violated_deposit", violated_deposit);
        intent1.putExtra("car_deposit", car_deposit);
        intent1.putExtra("rent_price", rent_price);
        intent1.putExtra("shop_name", shop_name);
        intent1.putExtra("img", img);
    }

    public void getCardTimePicker() {
        BasicPopup basicPopup = new BasicPopup(CheckShenFenActivity.this) {
            @Override
            protected View makeContentView() {
                View view = LayoutInflater.from(App.context).inflate(R.layout.practice_pop, null);
                final GregorianLunarCalendarView mGLCView = (GregorianLunarCalendarView) view.findViewById(R.id.calendar_view);
                mGLCView.init();
                TextView tv_exit = (TextView) view.findViewById(R.id.tv_exit);
                TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
                TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                tv_title.setText("请选择证件有效期");
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
                        String showToast = calendar.get(Calendar.YEAR) + "-" + month + "-" + day;
                        ed_idcard_date.setText(showToast);
                        dismiss();

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
