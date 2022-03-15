package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.TrainingDateManageBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.LogUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils.MyTimeUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyKeShiDateActivity extends NewBaseActivity implements CalendarView.OnCalendarSelectListener, CalendarView.OnMonthChangeListener, CalendarView.OnCalendarInterceptListener {


    @BindView(R.id.title_bar_iv_back)
    ImageView titleBarIvBack;
    @BindView(R.id.title_bar_back)
    RelativeLayout titleBarBack;
    @BindView(R.id.rb_kemu_2)
    RadioButton rbKemu2;
    @BindView(R.id.rb_kemu_3)
    RadioButton rbKemu3;
    @BindView(R.id.rg_all)
    RadioGroup rgAll;
    @BindView(R.id.title_bar_right_text)
    TextView titleBarRightText;
    @BindView(R.id.title_bar_rl)
    RelativeLayout titleBarRl;
    @BindView(R.id.tv_year_month)
    TextView tvYearMonth;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    private String coach_id;
    private String coach_name;
    private TrainingDateManageBean.ContentBean mContent;
    public static BuyKeShiDateActivity stance;

    /**
     * 购买学时    选择年月日
     *
     * @return
     */

    @Override
    protected int initContentView() {
        return R.layout.activity_buy_ke_shi_date;
    }

    @Override
    protected void initView() {
stance = this;
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {

    }
    private String driving_type = "0";//0:科目二  1：科目三
    @Override
    protected void initData() {
        coach_id = getIntent().getStringExtra("coach_id");
        coach_name = getIntent().getStringExtra("coach_name");




        rgAll.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_kemu_2:
                        driving_type = "0";
//                        showToastShort("切换科目2");
                        getDataFromNet();
                        break;
                    case R.id.rb_kemu_3:
                        driving_type = "1";
//                        showToastShort("切换科目3");
                        getDataFromNet();
                        break;

                    default:

                        break;


                }
            }
        });









    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromNet();

    }
    private void getDataFromNet() {

        showProgressDialog(false, "正在加载...");
        Map<String, String> params = new HashMap<>();
        params.put("driving_type", driving_type);//0:科目二  1：科目三
        params.put("coach_user_id", coach_id);//0:科目二  1：科目三
        MyHttpUtils.postHttpMessage(AppUrl.stuselectcoachdays, params, TrainingDateManageBean.class, new RequestCallBack<TrainingDateManageBean>() {
            @Override
            public void requestSuccess(TrainingDateManageBean json) {
                stopProgressDialog();
                if (json.getCode() == 200) {
                    mContent = json.getContent();
                    String[] split = mContent.getToday().split("-");
                    initCalendar(Integer.parseInt(split[0]),
                            Integer.parseInt(split[1]),
                            Integer.parseInt(split[2]),
                            Integer.parseInt(mContent.getDays()),
                            mContent.getList()


                    );


                } else {
                    showToastShort(json.getMsg());
                }

            }

            @Override
            public void requestError(String errorMsg, int errorType) {

                stopProgressDialog();
            }
        });




    }


    @OnClick({R.id.title_bar_iv_back, R.id.title_bar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_iv_back:
                break;
            case R.id.title_bar_back:

                finish();
                break;
        }
    }

    private void initCalendar(int year,int month,int curDay, int days, List<TrainingDateManageBean.ContentBean.ListBean> list) {

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");



        tvYearMonth.setText( mCalendarView.getCurYear()+"年"+ mCalendarView.getCurMonth()+"月");
//        mCalendarView.scrollToCalendar(year,month,curDay);

        Map<String, Calendar> map = new HashMap<>();

        java.util.Calendar instance1 = java.util.Calendar.getInstance();
        instance1.set(year,month-1,curDay);
        for (int i = 0; i < days; i++) {

//            String curText = "";
//            if (i == 0 || i == 2 || i == 8) {
//                curText = "已满";
//            } else {
//                curText = "可预约";
//
//            }
//
//            if (i == 0) {
//
//                instance1.add(java.util.Calendar.DATE, 0);
//                String days_after1 = sdf2.format(instance1.getTime());
//                String[] split_new = days_after1.split("_");
//
//
//                map.put(getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
//                        0xFF40db25, curText).toString(),
//                        getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
//                                0xFF40db25, curText));
//
//            }
//
//            instance1.add(java.util.Calendar.DATE, 1);
//            String days_after1 = sdf2.format(instance1.getTime());
//            String[] split_new = days_after1.split("_");
//
//            map.put(getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
//                    0xFF40db25, curText).toString(),
//                    getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
//                            0xFF40db25, curText));


            String curText = "已满";
            if (i == 0) {
                instance1.add(java.util.Calendar.DATE, 0);
                String days_after1 = sdf2.format(instance1.getTime());
                String[] split_new = days_after1.split("-");
                //判断是否是已设置
                for (int i1 = 0; i1 < list.size(); i1++) {
                    TrainingDateManageBean.ContentBean.ListBean listBean = list.get(i1);
//                    if (listBean.getCoach_data().equals(days_after1)) {
                    if (MyTimeUtils.isSameDay(listBean.getCoach_data(), days_after1)) {
//                        curText = "已设置";
                        curText = listBean.getCoach_set().equals("1") ? "预约" : "已满";
                        break;
                    }


                }

                map.put(getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
                        0xFF40db25, curText).toString(),
                        getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
                                0xFF40db25, curText));

            }


            curText = "已满";
            instance1.add(java.util.Calendar.DATE, 1);
            String days_after1 = sdf2.format(instance1.getTime());
            String[] split_new = days_after1.split("-");

            //判断是否是已设置
            for (int i1 = 0; i1 < list.size(); i1++) {
                TrainingDateManageBean.ContentBean.ListBean listBean = list.get(i1);
//                if (listBean.getCoach_data().equals(days_after1)) {
                if (MyTimeUtils.isSameDay(listBean.getCoach_data(), days_after1)) {
//                    curText = "已设置";

                    curText = listBean.getCoach_set().equals("1") ? "预约" : "已满";
                    break;
                }

            }

            map.put(getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
                    0xFF40db25, curText).toString(),
                    getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
                            0xFF40db25, curText));

        }


        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);
        mCalendarView.setOnCalendarInterceptListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);



    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }


    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
//        if("已满".equals(calendar.getScheme())){
//            showToastShort("当前日期已经约满,请选择其他日期!");
//            return;
//        }
        String[] weeks = getResources().getStringArray(com.haibin.calendarview.R.array.week_string_array);
        int week = calendar.getWeek();
//        Toast.makeText(this,
//                String.valueOf(calendar.getYear()) + "年" + calendar.getMonth() + "月" + calendar.getDay() + "日"+"   星期"+weeks[week],
//                Toast.LENGTH_SHORT).show();

        String selectedDate = MyTimeUtils.dateFormat(String.valueOf(calendar.getYear()) + "-" + calendar.getMonth() + "-" + calendar.getDay());
        List<TrainingDateManageBean.ContentBean.ListBean> list = mContent.getList();
        String selectedid = "";
        for (int i = 0; i < list.size(); i++) {
            if (MyTimeUtils.isSameDay(list.get(i).getCoach_data(), selectedDate)) {
                selectedid = list.get(i).getId() + "";
                break;
            }
        }


        Intent intent = new Intent(this,BuyTrainTimeStudentActivity.class);
        intent.putExtra("coach_id", coach_id);
        intent.putExtra("coach_name", coach_name);
        intent.putExtra("driving_type", driving_type);
        intent.putExtra("selected_date_id", selectedid);
        intent.putExtra("selected_date",String.valueOf(calendar.getYear()) + "年" + calendar.getMonth() + "月" + calendar.getDay() + "日"+"   星期"+weeks[week]);
        startActivityForResult(intent,11223);


    }

    @Override
    public void onMonthChange(int year, int month) {
        tvYearMonth.setText(year+"年"+month+"月");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11223&& resultCode == 123){
            setResult(1231);
            finish();
        }
    }

    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        String scheme = calendar.getScheme();
        return "已满".equals(scheme) || TextUtils.isEmpty(scheme)||"".equals(scheme);
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(BuyKeShiDateActivity.this,
                /*calendar.toString() + (isClick ? "拦截不可点击" : "拦截滚动到无效日期")*/
              "当前日期不可选"
                ,
                Toast.LENGTH_SHORT).show();
    }
}
