package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.BaseBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.KaoShiDateListBean;
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

public class YuYueKaoShiActivity extends NewBaseActivity implements CalendarView.OnCalendarSelectListener, CalendarView.OnMonthChangeListener {


    @BindView(R.id.title_bar_iv_back)
    ImageView titleBarIvBack;
    @BindView(R.id.title_bar_back)
    RelativeLayout titleBarBack;
    @BindView(R.id.title_bar_title_text)
    TextView titleBarTitleText;
    @BindView(R.id.title_bar_right_text)
    TextView titleBarRightText;
    @BindView(R.id.title_bar_rl)
    RelativeLayout titleBarRl;
    @BindView(R.id.tv_year_month)
    TextView tvYearMonth;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    private String yuyue_type;

    /**
     * 预约考试   11111112222222233333333444444
     *
     * @return
     */

    @Override
    protected int initContentView() {
        return R.layout.activity_yu_yue_kao_shi;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarTitleText.setText("预约考试");
        //学员 预约考试
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        yuyue_type = getIntent().getStringExtra("yuyue_type");// 1234  科目类型
        showProgressDialog(false, "加载中...");
        Map<String, String> params = new HashMap<>();
        params.put("res_subject",yuyue_type);
        MyHttpUtils.postHttpMessage(AppUrl.opendrivingreservation, params, KaoShiDateListBean.class, new RequestCallBack<KaoShiDateListBean>() {
            @Override
            public void requestSuccess(KaoShiDateListBean json) {
                stopProgressDialog();
                if (json.getCode() == 200) {
                    List<KaoShiDateListBean.ContentBean> content = json.getContent();
                    if (content == null || content.size() == 0) {
                        //展示无时间


                        new DialogUtils.Builder(YuYueKaoShiActivity.this, false, false,
                                "当前没有可预约考试的时间",
                                "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                                dialog.dismiss();
                            }
                        }, null, null).create().show();


                    } else {
                        initCalendar(content);

                    }

                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                stopProgressDialog();
                showToastShort("连接服务器失败");
            }
        });
    }


    @OnClick({R.id.title_bar_back, R.id.title_bar_title_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_bar_title_text:
                break;
        }
    }


    private void initCalendar(List<KaoShiDateListBean.ContentBean> content) {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        tvYearMonth.setText(year + "年" + month + "月");
        Map<String, Calendar> map = new HashMap<>();


        for (int i = 0; i < content.size(); i++) {
            KaoShiDateListBean.ContentBean contentBean = content.get(i);
            String reservation_date = contentBean.getReservation_date();
            String[] split_new = reservation_date.split("-");
            String curText = "预约";
            map.put(getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
                    0xFF40db25, curText).toString(),
                    getSchemeCalendar(Integer.parseInt(split_new[0]), Integer.parseInt(split_new[1]), Integer.parseInt(split_new[2]),
                            0xFF40db25, curText));

        }


        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);
        mCalendarView.setOnCalendarInterceptListener(new CalendarView.OnCalendarInterceptListener() {
            @Override
            public boolean onCalendarIntercept(Calendar calendar) {

                String scheme = calendar.getScheme();
                return "".equals(scheme) || TextUtils.isEmpty(scheme) || "".equals(scheme);
            }

            @Override
            public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
                Toast.makeText(YuYueKaoShiActivity.this,
                        /*calendar.toString() + (isClick ? "拦截不可点击" : "拦截滚动到无效日期")*/
                        "当前日期不可选"
                        ,
                        Toast.LENGTH_SHORT).show();
            }
        });
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
//        Toast.makeText(this,
//                String.valueOf(calendar.getYear()) + "年" + calendar.getMonth() + "月" + calendar.getDay() + "日",
//                Toast.LENGTH_SHORT).show();

        new DialogUtils.Builder(this, true, true,
                "您是否要预约(" + String.valueOf(calendar.getYear()) + "-" + calendar.getMonth() + "-" + calendar.getDay() + "" + ")的考试",
                "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String dateFormat = MyTimeUtils.dateFormat(String.valueOf(calendar.getYear()) + "-" + calendar.getMonth() + "-" + calendar.getDay() + "");
                Map<String, String> params = new HashMap<>();

                String entry_project = "科目一";
                switch (yuyue_type) {
                    case "1":
                        entry_project = "科目一";
                        break;
                    case "2":
                        entry_project = "科目二";
                        break;
                    case "3":
                        entry_project = "科目三";
                        break;
                    case "4":
                        entry_project = "科目四";
                        break;


                }
                params.put("stu_user_id", PrefUtils.getParameter("user_id") + "");
                params.put("practice_time", dateFormat);
                params.put("entry_project", entry_project);
                MyHttpUtils.postHttpMessage(AppUrl.addreservationexamination, params, BaseBean.class, new RequestCallBack<BaseBean>() {
                    @Override
                    public void requestSuccess(BaseBean json) {
                        if (json.getCode() == 200) {
                            showToastShort("预约成功");
                            dialog.dismiss();
                            finish();
                        } else {
                            showToastShort(json.getMsg());
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {
                        showToastShort("连接服务器失败");
                        dialog.dismiss();
                    }
                });


            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();


    }

    @Override
    public void onMonthChange(int year, int month) {
        tvYearMonth.setText(year + "年" + month + "月");

    }

}
