package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.CoachInfoBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.TrainingDateManageBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils.MyTimeUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学员预约练车  选择日期
 */
public class YuYueLiancheDateActivity extends NewBaseActivity implements CalendarView.OnCalendarSelectListener, CalendarView.OnMonthChangeListener, CalendarView.OnCalendarInterceptListener {


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
    @BindView(R.id.tv_coach_name)
    TextView tvCoachName;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.tv_coach_name2)
    TextView tvCoachName2;
    @BindView(R.id.tv_coach_phone)
    TextView tvCoachPhone;
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    private TrainingDateManageBean.ContentBean mContent;
    private CoachInfoBean.ContentBean content;
    public static YuYueLiancheDateActivity stance;

    @Override
    protected int initContentView() {
        return R.layout.activity_yu_yue_lianche;
    }

    @Override
    protected void initView() {
        stance = this;
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        //  学员预约练车    选择年月日
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromNet();
    }

    private String driving_type = "0";//0:科目二  1：科目三

    private void getDataFromNet() {
        driving_type = getIntent().getStringExtra("driving_type");
        showProgressDialog(false, "正在加载...");


//        showToastShort(TextUtils.isEmpty("")+"    "+ TextUtils.isEmpty(null));

        Map<String, String> params = new HashMap<>();
        params.put("user_id", PrefUtils.getParameter("user_id"));
        MyHttpUtils.postHttpMessage(AppUrl.coachinfomation, params, CoachInfoBean.class, new RequestCallBack<CoachInfoBean>() {
            @Override
            public void requestSuccess(CoachInfoBean json) {
                if (json.getCode() == 200) {


                    content = json.getContent();
                    tvCoachName.setText(content.getDriving_coach_name());
                    tvCoachName2.setText(content.getDriving_coach_name());
                    tvCoachPhone.setText("联系电话 : " + content.getCoach_phone());


                    Map<String, String> params1 = new HashMap<>();
                    params1.put("driving_type", driving_type);//0:科目二  1：科目三
                    params1.put("coach_user_id", json.getContent().getDriving_coach_id());//0:科目二  1：科目三
                    MyHttpUtils.postHttpMessage(AppUrl.stuselectcoachdays, params1, TrainingDateManageBean.class, new RequestCallBack<TrainingDateManageBean>() {
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
                } else {

                    new DialogUtils.Builder(YuYueLiancheDateActivity.this, false, false,
                            json.getMsg(),
                            "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                            dialog.dismiss();
                        }
                    }, null, null).create().show();
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                stopProgressDialog();
                new DialogUtils.Builder(YuYueLiancheDateActivity.this, false, false,
                        "您当前可能未分配教练或其他未知错误,建议联系客服!",
                        "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        dialog.dismiss();
                    }
                }, null, null).create().show();
            }
        });


    }

    @OnClick({R.id.title_bar_back, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.tv_btn:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + content.getCoach_phone()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }


    private void initCalendar(int year, int month, int curDay, int days, List<TrainingDateManageBean.ContentBean.ListBean> list) {

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");


        tvYearMonth.setText(year + "年" + month + "月");
//        mCalendarView.scrollToCalendar(year,month,curDay);

        Map<String, Calendar> map = new HashMap<>();

        java.util.Calendar instance1 = java.util.Calendar.getInstance();
        instance1.set(year, month - 1, curDay);
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
//        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
//        mTextYear.setText(String.valueOf(calendar.getYear()));


        String selectedDate = MyTimeUtils.dateFormat(String.valueOf(calendar.getYear()) + "-" + calendar.getMonth() + "-" + calendar.getDay());
        List<TrainingDateManageBean.ContentBean.ListBean> list = mContent.getList();
        String selectedid = "";
        for (int i = 0; i < list.size(); i++) {
            if (MyTimeUtils.isSameDay(list.get(i).getCoach_data(), selectedDate)) {
                selectedid = list.get(i).getId() + "";
                break;
            }
        }


        Intent intent = new Intent(this, StudentTrainTimeActivity.class);
        intent.putExtra("driving_type", driving_type);
        intent.putExtra("selected_date_id", selectedid);
        intent.putExtra("selectedDate", selectedDate);
        intent.putExtra("phone", content.getCoach_phone());
        startActivityForResult(intent, 1231);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1231 && resultCode == 1234) {
            setResult(147);//可以拿去刷新
            finish();
        }
    }

    @Override
    public void onMonthChange(int year, int month) {
//        Toast.makeText(this,
//                year + "年" + month + "月",
//                Toast.LENGTH_SHORT).show();
        tvYearMonth.setText(year + "年" + month + "月");

    }


    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        String scheme = calendar.getScheme();
        return "已满".equals(scheme) || TextUtils.isEmpty(scheme) || "".equals(scheme);
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(YuYueLiancheDateActivity.this,
                /*calendar.toString() + (isClick ? "拦截不可点击" : "拦截滚动到无效日期")*/
                "当前日期不可选"
                ,
                Toast.LENGTH_SHORT).show();
    }


}