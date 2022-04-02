package com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.homepage.view.LinkagePicker;
import com.xueyiche.zjyk.xueyiche.homepage.view.OptionPicker;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.CarlevelBean;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.JinJiPhoneBean;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderPostBean;
import com.xueyiche.zjyk.xueyiche.submit.PracticeCarSubmitIndent;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.GregorianLunarCalendarView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/9/9.
 */
public class YuYuePracticeActivity extends BaseActivity implements View.OnClickListener {
    //新版
    //开始时间
    private RelativeLayout rl_time_choose;
    private TextView tv_start_time;
    //共几小时
    private TextView tv_total_time;
    //结束时间
    private TextView tv_end_time;
    //车辆类别
    private RelativeLayout rl_carstyle_jibie;
    private TextView tv_carstyle_jibie;
    //接送地址
    private TextView tv_jie_location, tv_song_location;
    //下证时间
    private RelativeLayout rl_getcard_time;
    private TextView tv_getcard_time;
    //毕业水平
    private RelativeLayout rl_graduation_level;
    private TextView tv_graduation_level;
    //练车经验
    private RelativeLayout rl_practice_experience;
    private TextView tv_practice_experience;
    //和教练说的话
    private EditText ed_practice_talk;
    //发布
    private Button bt_fabu;
    private AES aes;
    //练车经验List
    private ArrayList<String> practiceExperienceList;
    //毕业水平List
    private ArrayList<String> graduationLevelList;
    //车辆级别List
    private ArrayList<String> carLevelList;
    //车型
    private ArrayList<String> firstList = new ArrayList<String>();
    private ArrayList<ArrayList<String>> secondList = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> thirdList = new ArrayList<ArrayList<String>>();
    private String car_level;
    //开始时间
    private String start_time;
    //结束时间
    private String end_time;
    //练车时长
    private int totlaTime;
    //接送地
    private String name_jie, name_song;
    //下证时间
    private String down_card_time = "";
    //毕业水平
    private String end_school_level = "";
    //练车经验
    private String traincar_experience = "";
    private String jie_latitude;
    private String jie_longitude;
    private String song_latitude;
    private String song_longitude;
    private MClearEditText tv_urgency_phone;
    private TextView tv_login_back;
    private ImageView iv_login_back;
    private String user_id;

    @Override
    protected int initContentView() {
        return R.layout.yuyue_practice_activity;
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_title);
        iv_login_back = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_login_back);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_total_time = (TextView) view.findViewById(R.id.tv_total_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        tv_jie_location = (TextView) view.findViewById(R.id.tv_jie_location);
        tv_urgency_phone = (MClearEditText) view.findViewById(R.id.tv_urgency_phone);
        tv_song_location = (TextView) view.findViewById(R.id.tv_song_location);
        tv_practice_experience = (TextView) view.findViewById(R.id.tv_practice_experience);
        tv_graduation_level = (TextView) view.findViewById(R.id.tv_graduation_level);
        tv_getcard_time = (TextView) view.findViewById(R.id.tv_getcard_time);
        tv_carstyle_jibie = (TextView) view.findViewById(R.id.tv_carstyle_jibie);
        rl_time_choose = (RelativeLayout) view.findViewById(R.id.rl_time_choose);
        rl_getcard_time = (RelativeLayout) view.findViewById(R.id.rl_getcard_time);
        rl_carstyle_jibie = (RelativeLayout) view.findViewById(R.id.rl_carstyle_jibie);
        rl_graduation_level = (RelativeLayout) view.findViewById(R.id.rl_graduation_level);
        rl_practice_experience = (RelativeLayout) view.findViewById(R.id.rl_practice_experience);
        ed_practice_talk = (EditText) view.findViewById(R.id.ed_practice_talk);
        bt_fabu = (Button) view.findViewById(R.id.bt_fabu);

    }

    @Override
    protected void initListener() {
        rl_time_choose.setOnClickListener(this);
        rl_getcard_time.setOnClickListener(this);
        tv_jie_location.setOnClickListener(this);
        tv_song_location.setOnClickListener(this);
        rl_graduation_level.setOnClickListener(this);
        rl_carstyle_jibie.setOnClickListener(this);
        iv_login_back.setOnClickListener(this);
        rl_practice_experience.setOnClickListener(this);
        bt_fabu.setOnClickListener(this);
    }
    public void getPriceFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.get().url(AppUrl.SelectCarTypeAndPrice)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        CarlevelBean carlevelBean = JsonUtil.parseJsonToBean(string, CarlevelBean.class);
                        if (carlevelBean!=null) {
                            int code = carlevelBean.getCode();
                            if (200==code) {
                                List<CarlevelBean.ContentBean> content = carlevelBean.getContent();
                                if (content!=null&&content.size()>0) {
                                    for (CarlevelBean.ContentBean contentBean : content) {
                                        String hour_price = contentBean.getHour_price();
                                        firstList.add(hour_price);
                                    }
                                }
                            }
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

    @Override
    protected void initData() {
        tv_login_back.setText("有证练车");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        practiceExperienceList = new ArrayList<>();
        graduationLevelList = new ArrayList<>();
        carLevelList = new ArrayList<>();
        //练车经验
        practiceExperienceList.add("有");
        practiceExperienceList.add("无");
        //毕业水平
        graduationLevelList.add("勉强");
        graduationLevelList.add("一般");
        graduationLevelList.add("优秀");
        graduationLevelList.add("熟练");
        //车辆级别 0:buxian1：微型；2：紧凑；3：中型；4：大型；5：豪华；6：SUV
        carLevelList.add("不限");
        carLevelList.add("微型");
        carLevelList.add("紧凑");
        carLevelList.add("中型");
        carLevelList.add("大型");
        carLevelList.add("豪华");
        carLevelList.add("SUV");
        getPriceFromNet();
        getPhoneFromNet();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_time_choose:
                //开始时间
                startTimePicker();
                break;
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.rl_carstyle_jibie:
                //车辆级别
            //    carStyle();
                carOneStyle();
                break;
            case R.id.tv_jie_location:
                //接送地址
//                Intent intent2 = new Intent(App.context, POIDressActivity.class);
//                intent2.putExtra("jiesong_type", "1");
//                intent2.putExtra("cartype", "1");
//                startActivityForResult(intent2, 1);
                break;
            case R.id.tv_song_location:
                //接送地址
//                Intent intent3 = new Intent(App.context, POIDressActivity.class);
//                intent3.putExtra("jiesong_type", "2");
//                intent3.putExtra("cartype", "1");
//                startActivityForResult(intent3, 3);
                break;
            case R.id.rl_getcard_time:
                //下证日期
                getCardTimePicker();
                break;
            case R.id.rl_graduation_level:
                //毕业水平
                graduationLevelPicker();
                break;
            case R.id.rl_practice_experience:
                //练车经验
                practiceExperiencePicker();
                break;
            case R.id.bt_fabu:
                if (AppUtils.isFastDoubleClick()) {
                    return;
                }
                if (DialogUtils.IsLogin()) {
                    fabu();
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
        }
    }

    private void carOneStyle() {
        if (firstList.size()!=0) {
            final OptionPicker picker = new OptionPicker(this, firstList);
            picker.setOffset(1);
            picker.setSelectedIndex(0);
            picker.setTextSize(15);
            picker.setTitleTextColor(getResources().getColor(R.color._3232));
            picker.setTitleText("请选择车型价格");
            picker.setTitleTextSize(20);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int position, String option) {
                    picker.setSelectedIndex(position);
                    car_level = position+"";
                    tv_carstyle_jibie.setText(option);
                    picker.dismiss();
                }

            });
            picker.show();
        }else {
            Toast.makeText(YuYuePracticeActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
        }

    }

    private void fabu() {
        String true_phone = tv_urgency_phone.getText().toString().trim();
        String bz = ed_practice_talk.getText().toString().trim();
        if (TextUtils.isEmpty(car_level)) {
            Toast.makeText(this, "请选择车型价格", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(true_phone) && true_phone.length() != 11) {
            Toast.makeText(this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(start_time) || TextUtils.isEmpty(end_time)) {
            Toast.makeText(this, "请选择开始时间和结束时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty("" + totlaTime)) {
            Toast.makeText(this, "请选择时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name_jie)) {
            Toast.makeText(this, "请选择上车地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name_song)) {
            Toast.makeText(this, "请选择下车地址", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.post()
                .url(AppUrl.Order_Indent_Post)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .addParams("start_time", start_time)
                .addParams("end_time", end_time)
                .addParams("driver_id", "")
                .addParams("order_sort", "2")
                .addParams("car_level", car_level)
                .addParams("true_phone", true_phone)
                .addParams("practice_hours", totlaTime + "")
                .addParams("get_on_address", name_jie)
                .addParams("get_down_address", name_song)
                .addParams("on_longitude", jie_longitude)
                .addParams("on_latitude", jie_latitude)
                .addParams("down_longitude", song_longitude)
                .addParams("down_latitude", song_latitude)
                .addParams("down_card_time", !TextUtils.isEmpty(down_card_time) ? down_card_time : "")
                .addParams("end_school_level", !TextUtils.isEmpty(end_school_level) ? end_school_level : "")
                .addParams("bz", TextUtils.isEmpty(bz) ? "" : bz)
                .addParams("traincar_experience", !TextUtils.isEmpty(traincar_experience) ? traincar_experience : "")
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            OrderPostBean orderPostBean = JsonUtil.parseJsonToBean(string, OrderPostBean.class);
                            if (orderPostBean != null) {
                                int code = orderPostBean.getCode();
                                final String msg = orderPostBean.getMsg();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        OrderPostBean.ContentBean content = orderPostBean.getContent();
                                        if (content != null) {
                                            final String order_number = content.getOrder_number();
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(App.context, PracticeCarSubmitIndent.class);
                                                    intent.putExtra("order_number", order_number);
                                                    intent.putExtra("driver_id", "");
                                                    intent.putExtra("type", "liucheng");
                                                    startActivity(intent);
                                                    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                                    clear();
                                                }
                                            });
                                        }
                                    }
                                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 111:
                jie_latitude = extras.getString("latitude");
                jie_longitude = extras.getString("longitude");
                name_jie = extras.getString("name");
                tv_jie_location.setText(name_jie);
                tv_jie_location.setTextColor(getResources().getColor(R.color._3333));
                break;
            case 333:
                song_latitude = extras.getString("latitude");
                song_longitude = extras.getString("longitude");
                name_song = extras.getString("name");
                tv_song_location.setText(name_song);
                tv_song_location.setTextColor(getResources().getColor(R.color._3333));
                break;
            default:
                break;
        }
    }

    private void clear() {
        tv_start_time.setText("请选择开始时间");
        tv_end_time.setText("请选择结束时间");
        tv_total_time.setText("共0小时");
        tv_carstyle_jibie.setText("");
        tv_jie_location.setText("选择您的上车地点");
        tv_song_location.setText("选择您的下车地点");
        tv_graduation_level.setText("");
        tv_practice_experience.setText("");
        ed_practice_talk.getText().clear();
        car_level = "";
        start_time = "";
        end_time = "";
        name_jie = "";
        name_song = "";
        down_card_time = "";
        end_school_level = "";
        traincar_experience = "";

    }


    //请选择毕业水平
    public void graduationLevelPicker() {
        final OptionPicker picker = new OptionPicker(this, graduationLevelList);
        picker.setOffset(1);
        picker.setSelectedIndex(0);
        picker.setTextSize(15);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));
        picker.setTitleText("请选择毕业水平");
        picker.setTitleTextSize(20);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                picker.setSelectedIndex(position);
                end_school_level = (position + 1) + "";
                tv_graduation_level.setText(option);
            }

        });
        picker.show();

    }

    //练车经验
    public void practiceExperiencePicker() {
        final OptionPicker picker = new OptionPicker(this, practiceExperienceList);
        picker.setOffset(1);
        picker.setSelectedIndex(0);
        picker.setTextSize(15);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));
        picker.setTitleText("请选择练车经验");
        picker.setTitleTextSize(20);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                picker.setSelectedIndex(position);
                traincar_experience = (position + 1) + "";
                tv_practice_experience.setText(option);
            }

        });
        picker.show();

    }

    //下证日期
    public void getCardTimePicker() {
        BasicPopup basicPopup = new BasicPopup(this) {
            @Override
            protected View makeContentView() {
                View view = LayoutInflater.from(App.context).inflate(R.layout.practice_pop, null);
                final GregorianLunarCalendarView mGLCView = (GregorianLunarCalendarView) view.findViewById(R.id.calendar_view);
                mGLCView.init();
                TextView tv_exit = (TextView) view.findViewById(R.id.tv_exit);
                TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
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
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Long second = simpleDateFormat.parse(showToast).getTime();
                            Date date = new Date(System.currentTimeMillis());
                            long time = date.getTime();
                            if (time >= second) {
                                down_card_time = showToast;
                                tv_getcard_time.setText(showToast);
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

    //开始时间和结束时间
    public void startTimePicker() {
        ArrayList<String> firstList = new ArrayList<String>();
        firstList.add("今天");
        firstList.add("明天");
        firstList.add("后天");
        ArrayList<ArrayList<String>> secondList = new ArrayList<ArrayList<String>>();
        ArrayList<String> secondListItem = new ArrayList<String>();
        ArrayList<String> thirdListItem = new ArrayList<String>();
        ArrayList<String> secondListItem_jintian = new ArrayList<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String dangqian_time = formatter.format(curDate);
        String dangqian_shi = dangqian_time.substring(11, 13);
        final int dangqian_shi_int = Integer.parseInt(dangqian_shi);
        for (int i = 5; i < 20; i++) {
            secondListItem.add(DateUtils.fillZero(i));
        }
        for (int i = 5; i < 20; i++) {
            thirdListItem.add(DateUtils.fillZero(i));
        }
        for (int i = dangqian_shi_int + 1 >= 19 ? 19 : dangqian_shi_int + 1; i < 20; i++) {
            secondListItem_jintian.add(DateUtils.fillZero(i));
        }
        secondList.add(secondListItem_jintian);//对应今天
        secondList.add(secondListItem);//对应明天
        secondList.add(secondListItem);//对应后天
        ArrayList<ArrayList<String>> thirdList = new ArrayList<ArrayList<String>>();
        ArrayList<String> thirdListItem_jintian = new ArrayList<>();
        for (int i = dangqian_shi_int + 3 >= 21 ? 21 : dangqian_shi_int + 3; i < 22; i++) {
            thirdListItem_jintian.add(DateUtils.fillZero(i));//对应0-23点
        }
        thirdList.add(thirdListItem_jintian);//对应今天
        thirdList.add(thirdListItem);//对应明天
        thirdList.add(thirdListItem);//对应后天
        final LinkagePicker picker = new LinkagePicker(this, firstList, secondList, thirdList);
        picker.setTitleText("请选择练车时间");
        picker.setTitleTextSize(20);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));
        picker.setOnLinkageListener(new LinkagePicker.OnLinkageListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                //时间格式:2016-11-08 08:00:00
                String star_shi = second.substring(0, 1);
                String star = "";
                if ("0".equals(star_shi)) {
                    star = second.substring(1, 2);
                } else {
                    star = second.substring(0, 2);
                }
                String end = "";
                String end_shi = third.substring(0, 1);
                if ("0".equals(end_shi)) {
                    end = third.substring(1, 2);
                } else {
                    end = third.substring(0, 2);
                }
                if (end != "" && star != "") {
                    if (first.equals("今天")) {
                        int startime = Integer.parseInt(star);
                        if (dangqian_shi_int + 1 > startime) {
                            showToastShort("都这么晚了，休息休息吧");
                            return;
                        }
                        SimpleDateFormat formatter_dangqian = new SimpleDateFormat("yyyy-MM-dd-HH");
                        Date date = new Date(System.currentTimeMillis());
                        String dangqian = formatter_dangqian.format(date);
                        //2016-11-14-02
                        String dangqian_shi = "";
                        int shiwei = Integer.parseInt(dangqian.substring(11, 12));
                        if (shiwei < 1) {
                            //10点之前
                            dangqian_shi = dangqian.substring(12, 13);
                        } else {
                            dangqian_shi = dangqian.substring(11, 13);
                        }
                        if (startime > Integer.parseInt(dangqian_shi)) {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date curDate = new Date();
                            String str = formatter.format(curDate);
                            tv_start_time.setText(str + " " + second + ":00:00");
                            tv_end_time.setText(str + " " + third + ":00:00");
                            start_time = str + " " + second + ":00:00";
                            end_time = str + " " + third + ":00:00";
                        }
                    } else if (first.equals("明天")) {
                        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                        Date curDate1 = new Date();
                        String str1 = formatter1.format(getNextDay(curDate1));
                        tv_start_time.setText(str1 + " " + second + ":00:00");
                        tv_end_time.setText(str1 + " " + third + ":00:00");
                        start_time = str1 + " " + second + ":00:00";
                        end_time = str1 + " " + third + ":00:00";
                    } else if (first.equals("后天")) {
                        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                        Date curDate2 = new Date();
                        String str2 = formatter1.format(getThirdDay(curDate2));
                        tv_start_time.setText(str2 + " " + second + ":00:00");
                        tv_end_time.setText(str2 + " " + third + ":00:00");
                        start_time = str2 + " " + second + ":00:00";
                        end_time = str2 + " " + third + ":00:00";
                    }
                    picker.setSelectedItem(first, second, third);
                    int IntstartTime = Integer.parseInt(second);
                    int IntendTime = Integer.parseInt(third);
                    totlaTime = IntendTime - IntstartTime;
                    tv_total_time.setText("共" + totlaTime + "小时");
                    picker.dismiss();
                }
            }
        });
        picker.show();
    }

    //明天的方法
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    //后天的方法
    public static Date getThirdDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +2);//+2今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    public void getPhoneFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.JinJi_Phone_Get)
                    .addParams("user_id", user_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        JinJiPhoneBean jinJiPhoneBean = JsonUtil.parseJsonToBean(string, JinJiPhoneBean.class);
                        if (jinJiPhoneBean != null) {
                            JinJiPhoneBean.ContentBean content = jinJiPhoneBean.getContent();
                            if (content != null) {
                                final String true_phone = content.getTrue_phone();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!TextUtils.isEmpty(true_phone)) {
                                            int length = true_phone.length();
                                            if (11==length) {
                                                tv_urgency_phone.setText(true_phone);
                                            }else {
                                                tv_urgency_phone.setText(AES.decrypt(true_phone));
                                            }
                                        }
                                    }
                                });
                            }

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
}
