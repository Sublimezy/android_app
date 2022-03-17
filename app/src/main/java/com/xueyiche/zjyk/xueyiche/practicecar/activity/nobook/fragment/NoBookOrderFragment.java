package com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.CommonWebView;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.homepage.view.LinkagePicker;
import com.xueyiche.zjyk.xueyiche.homepage.view.OptionPicker;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.POIDressActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.activity.NoBookPracticeCarYuYueSubmitIndent;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.bean.WZTCBean;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.view.WZLinkagePicker;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderPostBean;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanglei on 2016/10/31.
 */
public class NoBookOrderFragment extends BaseFragment implements View.OnClickListener {
    //新版
    //开始时间
    private RelativeLayout rl_time_choose;
    private TextView tv_start_time;
    //共几小时
    private TextView tv_total_time;
    //结束时间
    private TextView tv_end_time;
    //接送地址
    private RelativeLayout rl_song_location, rl_jie_location;
    private TextView tv_jie_location, tv_song_location, tv_xieyi, tv_will_practice_content, tv_car_style;
    //意向练车项目
    private RelativeLayout rl_will_practice_content, rl_car_style;
    //和教练说的话
    private EditText ed_practice_talk;
    //发布
    private Button bt_fabu;
    private ArrayList<String> practiceContentList = new ArrayList<>();
    private ArrayList<String> carStyleList = new ArrayList<>();
    private ArrayList<String> practiceContentListMoney = new ArrayList<>();
    //开始时间
    private String start_time;
    //结束时间
    private String end_time;
    //练车时长
    private int totlaTime;
    //接送地
    private String name_jie, name_song;
    private String jie_latitude;
    private String jie_longitude;
    private String song_latitude;
    private String song_longitude;
    private String car_style;
    private String prictice_money = "";
    private String String_practice_content = "";

    private CheckBox cb_xieyi;
    private String isCheck = "1";
    private String type = "0";


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.nobok_order_practice_activity, null);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_total_time = (TextView) view.findViewById(R.id.tv_total_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        tv_jie_location = (TextView) view.findViewById(R.id.tv_jie_location);
        tv_car_style = (TextView) view.findViewById(R.id.tv_car_style);
        tv_will_practice_content = (TextView) view.findViewById(R.id.tv_will_practice_content);
        tv_song_location = (TextView) view.findViewById(R.id.tv_song_location);
        rl_time_choose = (RelativeLayout) view.findViewById(R.id.rl_time_choose);
        rl_jie_location = (RelativeLayout) view.findViewById(R.id.rl_jie_location);
        rl_song_location = (RelativeLayout) view.findViewById(R.id.rl_song_location);
        rl_will_practice_content = (RelativeLayout) view.findViewById(R.id.rl_will_practice_content);
        rl_car_style = (RelativeLayout) view.findViewById(R.id.rl_car_style);
        ed_practice_talk = (EditText) view.findViewById(R.id.ed_practice_talk);
        ed_practice_talk.setFocusable(false);
        cb_xieyi = (CheckBox) view.findViewById(R.id.cb_xieyi);
        tv_xieyi = (TextView) view.findViewById(R.id.tv_xieyi);
        bt_fabu = (Button) view.findViewById(R.id.bt_fabu);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        rl_time_choose.setOnClickListener(this);
        rl_jie_location.setOnClickListener(this);
        rl_song_location.setOnClickListener(this);
        rl_will_practice_content.setOnClickListener(this);
        rl_car_style.setOnClickListener(this);
        bt_fabu.setOnClickListener(this);
        tv_xieyi.setOnClickListener(this);
        getDataFromNet();


        initData();
        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            OkHttpUtils.post()
                    .url(AppUrl.WZ_TC_Practice_List)
                    .addParams("device_id", "123456789")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        WZTCBean wztcBean = JsonUtil.parseJsonToBean(string, WZTCBean.class);
                        if (wztcBean != null) {
                            int code = wztcBean.getCode();
                            final List<WZTCBean.ContentBean> content_taocan = wztcBean.getContent();
                            if (200 == code) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (content_taocan.size() > 0) {
                                            for (int i = 0; i < content_taocan.size(); i++) {
                                                String hour_price = content_taocan.get(i).getHour_price();
                                                String remark = content_taocan.get(i).getRemark();
                                                practiceContentList.add(remark + "(" + hour_price + ")");
                                                practiceContentListMoney.add(hour_price);
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
                }

                @Override
                public void onResponse(Object response) {
                }
            });
        } else {
            stopProgressDialog();
            showToastLong(StringConstants.CHECK_NET);
        }
    }

    private void initData() {
        cb_xieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isCheck = "1";
                } else {
                    isCheck = "0";
                }

            }
        });
        carStyleList.add("轿车");
        carStyleList.add("皮卡");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 111:
                jie_latitude = extras.getString("latitude");
                jie_longitude = extras.getString("longitude");
                name_jie = extras.getString("name");
                tv_jie_location.setText(name_jie);
                break;
            case 333:
                song_latitude = extras.getString("latitude");
                song_longitude = extras.getString("longitude");
                name_song = extras.getString("name");
                tv_song_location.setText(name_song);
                break;
            default:
                break;
        }
    }

    public void practiceContentPicker() {
        final OptionPicker picker = new OptionPicker(getActivity(), practiceContentList);
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
                tv_will_practice_content.setText(option);
                String_practice_content = practiceContentList.get(position);
                if (0 == position || 1 == position) {
                    type = "0";
                    prictice_money = practiceContentListMoney.get(position);
                } else {
                    type = "1";
                    String s = practiceContentListMoney.get(position);
                    BigDecimal bd_money = new BigDecimal(s);
                    BigDecimal bd_hour = new BigDecimal("3");
                    BigDecimal divide = bd_money.divide(bd_hour);
                    prictice_money =""+divide ;
                }

            }

        });
        picker.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_time_choose:
                //开始时间
                startTimePicker();
                break;
            case R.id.rl_jie_location:
                //接送地址
                Intent intent2 = new Intent(App.context, POIDressActivity.class);
                intent2.putExtra("jiesong_type", "1");
                intent2.putExtra("cartype", "1");
                startActivityForResult(intent2, 1);
                break;
            case R.id.rl_song_location:
                //接送地址
                Intent intent3 = new Intent(App.context, POIDressActivity.class);
                intent3.putExtra("jiesong_type", "2");
                intent3.putExtra("cartype", "1");
                startActivityForResult(intent3, 3);
                break;
            case R.id.rl_will_practice_content:
                //意向练车内容
                practiceContentPicker();
                break;
            case R.id.rl_car_style:
                //车辆类型
                carStylePicker();
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
            case R.id.tv_xieyi:
                Intent intent4 = new Intent(App.context, CommonWebView.class);
                intent4.putExtra("baoxianurl", "http://xueyiche.cn/shouquan/shouquan.html");
                intent4.putExtra("weburl", "zhengxinxieyi");
                startActivity(intent4);
                break;
        }
    }

    private void fabu() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        String bz = ed_practice_talk.getText().toString().trim();
        if (TextUtils.isEmpty(start_time) || TextUtils.isEmpty(end_time)) {
            Toast.makeText(getActivity(), "请选择开始时间和结束时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty("" + totlaTime)) {
            Toast.makeText(getActivity(), "请选择时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name_jie)) {
            Toast.makeText(getActivity(), "请选择上车地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name_song)) {
            Toast.makeText(getActivity(), "请选择下车地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(String_practice_content)) {
            Toast.makeText(getActivity(), "请选择车意向练车项目", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(car_style)) {
            Toast.makeText(getActivity(), "请选择车辆类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals("0", isCheck)) {
            showToastShort("请阅读并同意协议");
            return;
        }

        if ("1".equals(type) && totlaTime != 3) {
            Toast.makeText(getContext(), "请重新选择时间", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.post()
                .url(AppUrl.WZ_Order_Indent_Post)
                .addParams("device_id", LoginUtils.getId(getActivity()))
                .addParams("user_id", user_id)
                .addParams("start_time", start_time)
                .addParams("end_time", end_time)
                .addParams("driver_id", "")
                .addParams("practice_hours", totlaTime + "")
                .addParams("hour_price", prictice_money)
                .addParams("get_on_address", name_jie)
                .addParams("get_down_address", name_song)
                .addParams("on_longitude", jie_longitude)
                .addParams("on_latitude", jie_latitude)
                .addParams("down_longitude", song_longitude)
                .addParams("down_latitude", song_latitude)
                .addParams("car_style", car_style)
                .addParams("purpose_item", String_practice_content)
                .addParams("bz", TextUtils.isEmpty(bz) ? "" : bz)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        LogUtil.e("Order", string);
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
                                                    Intent intent = new Intent(App.context, NoBookPracticeCarYuYueSubmitIndent.class);
                                                    intent.putExtra("order_number", order_number);
                                                    intent.putExtra("type", "1");
                                                    intent.putExtra("tc_type", type);
                                                    startActivity(intent);
                                                    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
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

    //车辆类型选择
    public void carStylePicker() {
        if (carStyleList.size() != 0) {
            final OptionPicker picker = new OptionPicker(getActivity(), carStyleList);
            picker.setOffset(1);
            picker.setSelectedIndex(0);
            picker.setTextSize(15);
            picker.setTitleTextColor(getResources().getColor(R.color._3232));
            picker.setTitleText("请选择车辆类型");
            picker.setTitleTextSize(20);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int position, String option) {
                    picker.setSelectedIndex(position);
                    int i = position + 1;
                    car_style = "" + i;
                    tv_car_style.setText(option);
                }

            });
            picker.show();
        }

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
        for (int i = dangqian_shi_int + 4 >= 21 ? 21 : dangqian_shi_int + 4; i < 22; i++) {
            thirdListItem_jintian.add(DateUtils.fillZero(i));//对应0-23点
        }
        thirdList.add(thirdListItem_jintian);//对应今天
        thirdList.add(thirdListItem);//对应明天
        thirdList.add(thirdListItem);//对应后天
        if ("0".equals(type)) {
            startEveryHour(firstList, secondList, dangqian_shi_int, thirdList);
        } else {
            startThreeHour(firstList, secondList, dangqian_shi_int, thirdList);
        }

    }

    private void startEveryHour(ArrayList<String> firstList, ArrayList<ArrayList<String>> secondList, final int dangqian_shi_int, ArrayList<ArrayList<String>> thirdList) {
        final LinkagePicker picker = new LinkagePicker(getActivity(), firstList, secondList, thirdList);
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

    private void startThreeHour(ArrayList<String> firstList, ArrayList<ArrayList<String>> secondList, final int dangqian_shi_int, ArrayList<ArrayList<String>> thirdList) {
        final WZLinkagePicker picker = new WZLinkagePicker(getActivity(), firstList, secondList, thirdList);
        picker.setTitleText("请选择练车时间");
        picker.setTitleTextSize(20);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));

        picker.setOnLinkageListener(new WZLinkagePicker.OnLinkageListener() {
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


}
