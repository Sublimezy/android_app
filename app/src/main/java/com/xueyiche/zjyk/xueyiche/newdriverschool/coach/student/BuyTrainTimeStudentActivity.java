package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.CoachShowTimeRangeBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsOrderBuyActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils.BigDecimalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyTrainTimeStudentActivity extends NewBaseActivity {


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
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    @BindView(R.id.tv_cost)
    TextView tvCost;
    private TimeQuickAdapter timeQuickAdapter;
    private String selected_date_id;
    private String coach_id;

    private String coach_name;
    private String selected_date;
    private String driving_type;
    private String section_price;
    public static BuyTrainTimeStudentActivity stance;

    @Override
    protected int initContentView() {
        return R.layout.activity_training_time_student;
    }

    @Override
    protected void initView() {
        stance = this;
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarTitleText.setText("购买学时");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        coach_id = getIntent().getStringExtra("coach_id");
        coach_name = getIntent().getStringExtra("coach_name");
        selected_date = getIntent().getStringExtra("selected_date");
        driving_type = getIntent().getStringExtra("driving_type");
        selected_date_id = getIntent().getStringExtra("selected_date_id");


        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        timeQuickAdapter = new TimeQuickAdapter(R.layout.item_lianxi_time);
//        List<TimeBean> list = new ArrayList<>();
//        list.add(new TimeBean("00:00-01:00", "(4/4)", false, true));  //被选中的  不可以取消
//        list.add(new TimeBean("01:00-02:00", "(4/4)", false, true));
//        list.add(new TimeBean("02:00-03:00", "(0/4)", false, true));
//        list.add(new TimeBean("03:00-04:00", "(0/4)", false, true));
//        list.add(new TimeBean("04:00-05:00", "(0/4)", false, true));
//        list.add(new TimeBean("05:00-06:00", "(0/4)", false, true));
//        list.add(new TimeBean("06:00-07:00", "(0/4)", false, true));
//        list.add(new TimeBean("07:00-08:00", "(0/4)", false, true));
//        list.add(new TimeBean("08:00-09:00", "(0/4)", false, true));
//        list.add(new TimeBean("09:00-10:00", "(0/4)", false, true));
//        list.add(new TimeBean("10:00-11:00", "(0/4)", false, true));
//        list.add(new TimeBean("11:00-12:00", "(0/4)", false, true));
//        list.add(new TimeBean("12:00-13:00", "(0/4)", false, true));
//        list.add(new TimeBean("13:00-14:00", "(0/4)", false, true));
//        list.add(new TimeBean("14:00-15:00", "(0/4)", false, true));
//        list.add(new TimeBean("15:00-16:00", "(0/4)", false, true));
//        list.add(new TimeBean("16:00-17:00", "(0/4)", false, true));
//        list.add(new TimeBean("17:00-18:00", "(0/4)", false, true));
//        list.add(new TimeBean("18:00-19:00", "(0/4)", false, true));
//        list.add(new TimeBean("19:00-20:00", "(4/4)", false, true));
//        list.add(new TimeBean("20:00-21:00", "(4/4)", false, true));
//        list.add(new TimeBean("21:00-22:00", "(0/4)", false, true));
//        list.add(new TimeBean("22:00-23:00", "(0/4)", false, true));
//        list.add(new TimeBean("23:00-24:00", "(0/4)", false, true));
//        timeQuickAdapter.setNewData(list);
        recyclerView.setAdapter(timeQuickAdapter);

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
                    list.add(new TimeBean("00:00-01:00", "(4/4)", false, false));  //被选中的  不可以取消
                    list.add(new TimeBean("01:00-02:00", "(4/4)", false, false));
                    list.add(new TimeBean("02:00-03:00", "(4/4)", false, false));
                    list.add(new TimeBean("03:00-04:00", "(4/4)", false, false));
                    list.add(new TimeBean("04:00-05:00", "(4/4)", false, false));
                    list.add(new TimeBean("05:00-06:00", "(4/4)", false, false));
                    list.add(new TimeBean("06:00-07:00", "(4/4)", false, false));
                    list.add(new TimeBean("07:00-08:00", "(4/4)", false, false));
                    list.add(new TimeBean("08:00-09:00", "(4/4)", false, false));
                    list.add(new TimeBean("09:00-10:00", "(4/4)", false, false));
                    list.add(new TimeBean("10:00-11:00", "(4/4)", false, false));
                    list.add(new TimeBean("11:00-12:00", "(4/4)", false, false));
                    list.add(new TimeBean("12:00-13:00", "(4/4)", false, false));
                    list.add(new TimeBean("13:00-14:00", "(4/4)", false, false));
                    list.add(new TimeBean("14:00-15:00", "(4/4)", false, false));
                    list.add(new TimeBean("15:00-16:00", "(4/4)", false, false));
                    list.add(new TimeBean("16:00-17:00", "(4/4)", false, false));
                    list.add(new TimeBean("17:00-18:00", "(4/4)", false, false));
                    list.add(new TimeBean("18:00-19:00", "(4/4)", false, false));
                    list.add(new TimeBean("19:00-20:00", "(4/4)", false, false));
                    list.add(new TimeBean("20:00-21:00", "(4/4)", false, false));
                    list.add(new TimeBean("21:00-22:00", "(4/4)", false, false));
                    list.add(new TimeBean("22:00-23:00", "(4/4)", false, false));
                    list.add(new TimeBean("23:00-24:00", "(4/4)", false, false));

                    section_price = json.getContent().getSection_price();
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
                    updateTotalMoney();

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


    @OnClick({R.id.title_bar_back, R.id.title_bar_title_text, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_bar_title_text:
                break;
            case R.id.tv_btn:

                if ("".equals(TimeRange)) {
                    showToastShort("请先选择时间段");
                    return;
                }
                if (TotalNum == 0) {
                    showToastShort("请先选择时间段");
                    return;
                }
                String substring = TimeRange.substring(0, TimeRange.length() - 1);

                Intent intent = new Intent(this, StudentsOrderBuyActivity.class);//跳预约详情购买支付
                intent.putExtra("coach_id", coach_id);//教练id
                intent.putExtra("coach_name", coach_name);//教练名字
                intent.putExtra("driving_type", driving_type);// 	0:科目二 1:科目三
                intent.putExtra("selected_date_id", selected_date_id);
                intent.putExtra("selected_period_id", selected_period_id.substring(0, selected_period_id.length() - 1));
                intent.putExtra("selected_date", selected_date);//xxxx年xx月  星期x
                intent.putExtra("total_num", "共" + TotalNum + "学时");//共xx学时
                intent.putExtra("time_range", substring);//9:00-10:00、10:00-11:00
                intent.putExtra("TotalMoney", TotalMoney);//9:00-10:00、10:00-11:00
                intent.putExtra("id", id.substring(0, id.length() - 1));//9:00-10:00、10:00-11:00
                startActivityForResult(intent, 11223);


                break;
        }
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
            num.setVisibility(View.GONE);
            if (item.getNum().equals("(4/4)")) {

                time.setTextColor(Color.parseColor("#CCCCCC"));
                num.setTextColor(Color.parseColor("#CCCCCC"));
                ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToastShort("当前时间段不可选");
                        updateTotalMoney();
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
                                updateTotalMoney();
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
                            updateTotalMoney();
                        }


                    }
                });
            }


        }
    }


    String TotalMoney = "0";
    String TimeRange = "";
    String id = "";
    String selected_period_id = "";
    int TotalNum = 0;

    public void updateTotalMoney() {
        TotalMoney = "0";
        TimeRange = "";
        TotalNum = 0;
        List<TimeBean> data = timeQuickAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            TimeBean timeBean = data.get(i);
            if (timeBean.selected && timeBean.canCancel) {
                TotalNum++;
                TotalMoney = BigDecimalUtils.add(TotalMoney, section_price);
                TimeRange += timeBean.getTime() + ",";
                selected_period_id += (i + ",");
                id += (timeBean.getId() + ",");
            }

        }
        tvCost.setText("共" + TotalNum + "学时 合计：¥" + TotalMoney);

    }

    class TimeBean {
        private String time;
        private String num;
        private boolean selected;
        private boolean canCancel;
        private String id;


        public TimeBean(String time, String num, boolean selected, boolean canCancel) {
            this.time = time;
            this.num = num;
            this.selected = selected;
            this.canCancel = canCancel;
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
