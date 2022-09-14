package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.examtext.bean.UserTypeBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.BaseBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.CoachShowTimeRangeBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.JsonUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsBaoMingActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsSelfTestActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils.BigDecimalUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学员  预约  练车时间
 */
public class StudentTrainTimeActivity extends NewBaseActivity {


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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_contack_coach)
    TextView tvContackCoach;
    @BindView(R.id.tv_yuyue)
    TextView tvYuyue;
    private String selected_date_id;
    private String selectedDate;
    private String driving_type;
    private TimeQuickAdapter timeQuickAdapter;
    private String phone;

    @Override
    protected int initContentView() {
        return R.layout.activity_student_train_time;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarTitleText.setText("预约练车时间");
        //学员预约 练车时间
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        timeQuickAdapter = new TimeQuickAdapter(R.layout.item_lianxi_time);

        recyclerView.setAdapter(timeQuickAdapter);
        driving_type = getIntent().getStringExtra("driving_type");
        selected_date_id = getIntent().getStringExtra("selected_date_id");
        selectedDate = getIntent().getStringExtra("selectedDate");
        phone = getIntent().getStringExtra("phone");
//        String s = JsonUtils.objectToJson(list);
//        Log.i("zhangsijia_练车", s);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromNet();
    }

    private void getDataFromNet() {
        Map<String, String> params = new HashMap<>();
        params.put("selected_date_id", selected_date_id);
        MyHttpUtils.postHttpMessage(AppUrl.coachfreedperiodlist, params, CoachShowTimeRangeBean.class, new RequestCallBack<CoachShowTimeRangeBean>() {
            @Override
            public void requestSuccess(CoachShowTimeRangeBean json) {
                if (json.getCode() == 200) {
                    List<TimeBean> list = new ArrayList<>();
                    list.add(new TimeBean("00:00-01:00", "(4/4)", false, false, ""));  //被选中的  不可以取消
                    list.add(new TimeBean("01:00-02:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("02:00-03:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("03:00-04:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("04:00-05:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("05:00-06:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("06:00-07:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("07:00-08:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("08:00-09:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("09:00-10:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("10:00-11:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("11:00-12:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("12:00-13:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("13:00-14:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("14:00-15:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("15:00-16:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("16:00-17:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("17:00-18:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("18:00-19:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("19:00-20:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("20:00-21:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("21:00-22:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("22:00-23:00", "(4/4)", false, false, ""));
                    list.add(new TimeBean("23:00-24:00", "(4/4)", false, false, ""));


                    List<CoachShowTimeRangeBean.ContentBean.ListBean> content = json.getContent().getList();
                    for (int i = 0; i < content.size(); i++) {
                        int index = Integer.parseInt(content.get(i).getCoach_peirod());
                        TimeBean timeBean = list.get(index);
                        timeBean.setSelected(false);
                        timeBean.setNum("(" + content.get(i).getShow_person_num() + "/4)");
                        timeBean.setCanCancel(true);
                        timeBean.setId(content.get(i).getId());
                        list.set(index, timeBean);

                    }


                    timeQuickAdapter.setNewData(list);


                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });

    }

    @OnClick({R.id.title_bar_back, R.id.title_bar_title_text, R.id.tv_contack_coach, R.id.tv_yuyue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_bar_title_text:
                break;
            case R.id.tv_contack_coach:

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.tv_yuyue:
                Map<String, String> map = new HashMap<>();
                map.put("user_id", "" + PrefUtils.getParameter("user_id"));
                MyHttpUtils.postHttpMessage(AppUrl.usertype, map, UserTypeBean.class, new RequestCallBack<UserTypeBean>() {
                    @Override
                    public void requestSuccess(UserTypeBean json) {
                        if (200 == json.getCode()) {
                            UserTypeBean.ContentBean content = json.getContent();
                            if ("0".equals("" + content.getStu_sign_up())) {
                                new DialogUtils.Builder(StudentTrainTimeActivity.this, false, false, "您还未报名，请先完成报名!", "报名", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        openActivity(StudentsBaoMingActivity.class);
                                        YuYueLiancheDateActivity.stance.finish();
                                        StudentsSelfTestActivity.stance.finish();
                                        finish();
                                        dialog.dismiss();
                                    }
                                }, "取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                            } else {
                                yuyue();
                            }
                        }
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {

                    }
                });


                break;
        }
    }

    private void yuyue() {
        String TimeRange = "";
        String TimeRangeid = "";
        String ids = "";
        int TotalNum = 0;
        List<TimeBean> data = timeQuickAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            TimeBean timeBean = data.get(i);
            if (timeBean.selected && timeBean.canCancel) {
                TotalNum++;
                TimeRange += timeBean.getTime() + ",";
                TimeRangeid += i + ",";
                ids += timeBean.getId() + ",";
            }

        }


        if ("".equals(TimeRange)) {
            showToastShort("请先选择时间段");
            return;
        }
        if (TotalNum == 0) {
            showToastShort("请先选择时间段");
            return;
        }


        Map<String, String> params = new HashMap<>();
        params.put("stu_user_id", PrefUtils.getParameter("user_id"));
        params.put("entry_project", driving_type);
        params.put("driving_practice", "驾驶练习");
        params.put("selected_period_id", TimeRangeid.substring(0, TimeRangeid.length() - 1));
        params.put("selected_period", TimeRange.substring(0, TimeRange.length() - 1));
        params.put("id", ids.substring(0, ids.length() - 1));
        params.put("practice_time", selectedDate);
        params.put("num_class", TotalNum + "");
        MyHttpUtils.postHttpMessage(AppUrl.addpracticedriving, params, BaseBean.class, new RequestCallBack<BaseBean>() {
            @Override
            public void requestSuccess(BaseBean json) {
                if (json.getCode() == 200) {
                    setResult(1234);
                    showToastShort("预约成功");
                    finish();
                } else {
                    showToastShort(json.getMsg());

                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                showToastShort("连接是服务器失败");

            }
        });
    }

    class TimeQuickAdapter extends BaseQuickAdapter<TimeBean, BaseViewHolder> {

        public TimeQuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, TimeBean item) {


            LinearLayout ll_item = helper.getView(R.id.ll_item);
            TextView time = helper.getView(R.id.time);
            TextView num = helper.getView(R.id.num);

            boolean selected = item.isSelected();
            boolean canCancel = item.isCanCancel();

            time.setText(item.getTime());
            num.setText(item.getNum());
            if (item.getNum().equals("(4/4)")) {

                time.setTextColor(Color.parseColor("#CCCCCC"));
                num.setTextColor(Color.parseColor("#CCCCCC"));
                ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToastShort("当前时间段不可选");
                    }
                });
            } else {

                if (selected) {
                    ll_item.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_orange));
                    time.setTextColor(Color.parseColor("#ffffff"));
                    num.setTextColor(Color.parseColor("#ffffff"));

                } else {
                    ll_item.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_gray));
                    time.setTextColor(Color.parseColor("#ff333333"));
                    num.setTextColor(Color.parseColor("#ffff5000"));
                }
                ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (item.isSelected()) {
                            //已经被选择的   点击后查看 是不是能取消
                            if (item.isCanCancel()) {
                                //  true  能取消
                                ll_item.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_gray));
                                time.setTextColor(Color.parseColor("#ff333333"));
                                num.setTextColor(Color.parseColor("#ffff5000"));
                                item.setSelected(false);

                            } else {


                                showToastShort("当前时间段不可选");

                            }

                        } else {
                            //未被选择的 点击后被选上
                            ll_item.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_orange));
                            time.setTextColor(Color.parseColor("#ffffff"));
                            num.setTextColor(Color.parseColor("#ffffff"));

                            item.setSelected(true);
                            item.setCanCancel(true);

                        }


                    }
                });
            }


        }
    }

    class TimeBean {
        private String time;
        private String num;
        private boolean selected;
        private boolean canCancel;
        private String id;


        public TimeBean(String time, String num, boolean selected, boolean canCancel, String id) {
            this.time = time;
            this.num = num;
            this.selected = selected;
            this.canCancel = canCancel;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isCanCancel() {
            return canCancel;
        }

        public void setCanCancel(boolean canCancel) {
            this.canCancel = canCancel;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }


        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
