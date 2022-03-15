package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.BaseBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.CoachShowTimeRangeBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.TrainingDataCoachSelectBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.TrainingTimeCoachBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 练车时间  教练端
 */
public class TrainingTimeCoachActivity extends NewBaseActivity {


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
    private String driving_type;
    private String coach_user_id;
    private String coach_data;
    private String selected_date_id;
    private TimeQuickAdapter timeQuickAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_training_time_coach;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarTitleText.setText("练车时间");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        driving_type = getIntent().getStringExtra("driving_type");
        coach_user_id = getIntent().getStringExtra("coach_user_id");
        coach_data = getIntent().getStringExtra("coach_data");
        selected_date_id = getIntent().getStringExtra("selected_date_id");


        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        timeQuickAdapter = new TimeQuickAdapter(R.layout.item_lianxi_time);
//        List<TimeBean> list = new ArrayList<>();
//        list.add(new TimeBean("00:00-01:00", "(0/4)", true, false));  //被选中的  不可以取消
//        list.add(new TimeBean("01:00-02:00", "(0/4)", true, false));
//        list.add(new TimeBean("02:00-03:00", "(0/4)", true, false));
//        list.add(new TimeBean("03:00-04:00", "(0/4)", true, false));
//        list.add(new TimeBean("04:00-05:00", "(0/4)", true, false));
//        list.add(new TimeBean("05:00-06:00", "(0/4)", true, false));
//        list.add(new TimeBean("06:00-07:00", "(0/4)", true, false));
//        list.add(new TimeBean("07:00-08:00", "(0/4)", true, false));
//        list.add(new TimeBean("08:00-09:00", "(0/4)", true, false));
//        list.add(new TimeBean("09:00-10:00", "(0/4)", true, false));
//        list.add(new TimeBean("10:00-11:00", "(0/4)", true, false));
//        list.add(new TimeBean("11:00-12:00", "(0/4)", false, true));
//        list.add(new TimeBean("12:00-13:00", "(0/4)", false, true));
//        list.add(new TimeBean("13:00-14:00", "(0/4)", false, true));
//        list.add(new TimeBean("14:00-15:00", "(0/4)", false, true));
//        list.add(new TimeBean("15:00-16:00", "(0/4)", false, true));
//        list.add(new TimeBean("16:00-17:00", "(0/4)", false, true));
//        list.add(new TimeBean("17:00-18:00", "(0/4)", false, true));
//        list.add(new TimeBean("18:00-19:00", "(0/4)", false, true));
//        list.add(new TimeBean("19:00-20:00", "(0/4)", false, true));
//        list.add(new TimeBean("20:00-21:00", "(0/4)", false, true));
//        list.add(new TimeBean("21:00-22:00", "(0/4)", false, true));
//        list.add(new TimeBean("22:00-23:00", "(0/4)", false, true));
//        list.add(new TimeBean("23:00-24:00", "(0/4)", false, true));
//        timeQuickAdapter.setNewData(list);
        recyclerView.setAdapter(timeQuickAdapter);

        getDataFromNet();
    }

    private void getDataFromNet() {


        if (TextUtils.isEmpty(selected_date_id) || "".equals(selected_date_id)) {
            List<TimeBean> list = new ArrayList<>();
            list.add(new TimeBean("00:00-01:00", "(0/4)", false, true));  //被选中的  不可以取消
            list.add(new TimeBean("01:00-02:00", "(0/4)", false, true));
            list.add(new TimeBean("02:00-03:00", "(0/4)", false, true));
            list.add(new TimeBean("03:00-04:00", "(0/4)", false, true));
            list.add(new TimeBean("04:00-05:00", "(0/4)", false, true));
            list.add(new TimeBean("05:00-06:00", "(0/4)", false, true));
            list.add(new TimeBean("06:00-07:00", "(0/4)", false, true));
            list.add(new TimeBean("07:00-08:00", "(0/4)", false, true));
            list.add(new TimeBean("08:00-09:00", "(0/4)", false, true));
            list.add(new TimeBean("09:00-10:00", "(0/4)", false, true));
            list.add(new TimeBean("10:00-11:00", "(0/4)", false, true));
            list.add(new TimeBean("11:00-12:00", "(0/4)", false, true));
            list.add(new TimeBean("12:00-13:00", "(0/4)", false, true));
            list.add(new TimeBean("13:00-14:00", "(0/4)", false, true));
            list.add(new TimeBean("14:00-15:00", "(0/4)", false, true));
            list.add(new TimeBean("15:00-16:00", "(0/4)", false, true));
            list.add(new TimeBean("16:00-17:00", "(0/4)", false, true));
            list.add(new TimeBean("17:00-18:00", "(0/4)", false, true));
            list.add(new TimeBean("18:00-19:00", "(0/4)", false, true));
            list.add(new TimeBean("19:00-20:00", "(0/4)", false, true));
            list.add(new TimeBean("20:00-21:00", "(0/4)", false, true));
            list.add(new TimeBean("21:00-22:00", "(0/4)", false, true));
            list.add(new TimeBean("22:00-23:00", "(0/4)", false, true));
            list.add(new TimeBean("23:00-24:00", "(0/4)", false, true));

            timeQuickAdapter.setNewData(list);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("selected_date_id", selected_date_id);
            MyHttpUtils.postHttpMessage(AppUrl.coachfreedperiodlist, params, CoachShowTimeRangeBean.class, new RequestCallBack<CoachShowTimeRangeBean>() {
                @Override
                public void requestSuccess(CoachShowTimeRangeBean json) {
                    if (json.getCode() == 200) {
                        List<TimeBean> list = new ArrayList<>();
                        list.add(new TimeBean("00:00-01:00", "(0/4)", false, true));  //被选中的  不可以取消
                        list.add(new TimeBean("01:00-02:00", "(0/4)", false, true));
                        list.add(new TimeBean("02:00-03:00", "(0/4)", false, true));
                        list.add(new TimeBean("03:00-04:00", "(0/4)", false, true));
                        list.add(new TimeBean("04:00-05:00", "(0/4)", false, true));
                        list.add(new TimeBean("05:00-06:00", "(0/4)", false, true));
                        list.add(new TimeBean("06:00-07:00", "(0/4)", false, true));
                        list.add(new TimeBean("07:00-08:00", "(0/4)", false, true));
                        list.add(new TimeBean("08:00-09:00", "(0/4)", false, true));
                        list.add(new TimeBean("09:00-10:00", "(0/4)", false, true));
                        list.add(new TimeBean("10:00-11:00", "(0/4)", false, true));
                        list.add(new TimeBean("11:00-12:00", "(0/4)", false, true));
                        list.add(new TimeBean("12:00-13:00", "(0/4)", false, true));
                        list.add(new TimeBean("13:00-14:00", "(0/4)", false, true));
                        list.add(new TimeBean("14:00-15:00", "(0/4)", false, true));
                        list.add(new TimeBean("15:00-16:00", "(0/4)", false, true));
                        list.add(new TimeBean("16:00-17:00", "(0/4)", false, true));
                        list.add(new TimeBean("17:00-18:00", "(0/4)", false, true));
                        list.add(new TimeBean("18:00-19:00", "(0/4)", false, true));
                        list.add(new TimeBean("19:00-20:00", "(0/4)", false, true));
                        list.add(new TimeBean("20:00-21:00", "(0/4)", false, true));
                        list.add(new TimeBean("21:00-22:00", "(0/4)", false, true));
                        list.add(new TimeBean("22:00-23:00", "(0/4)", false, true));
                        list.add(new TimeBean("23:00-24:00", "(0/4)", false, true));


                        List<CoachShowTimeRangeBean.ContentBean.ListBean> content = json.getContent().getList();
                        for (int i = 0; i < content.size(); i++) {
                            int index = Integer.parseInt(content.get(i).getCoach_peirod());
                            TimeBean timeBean = list.get(index);
                            timeBean.setSelected(true);
                            timeBean.setCanCancel(false);
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
                //点击保存
                List<TimeBean> data = timeQuickAdapter.getData();
                String coach_peirod = "";
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).selected && data.get(i).canCancel) {
                        coach_peirod += (i + ",");

                    }
                }
                if (coach_peirod.length() == 0) {
                    showToastShort("未选择时间段");
                    return;
                }
                String substring = coach_peirod.substring(0, coach_peirod.length() - 1);

                showProgressDialog(false, "正在保存...");
                Map<String, String> params = new HashMap<>();

                params.put("coach_user_id", coach_user_id);
                params.put("coach_peirod", substring);
                params.put("coach_data", coach_data);
                params.put("driving_type", driving_type);
                MyHttpUtils.postHttpMessage(AppUrl.addcoachperiod, params, BaseBean.class, new RequestCallBack<BaseBean>() {
                    @Override
                    public void requestSuccess(BaseBean json) {
                        stopProgressDialog();
                        if (json.getCode() == 200) {

                            showToastShort("保存成功");
                            setResult(456);
                            finish();
                        } else {
                            showToastShort(json.getMsg());
                        }
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {
                        stopProgressDialog();

                    }
                });

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
            num.setVisibility(View.GONE);
            boolean selected = item.isSelected();
            boolean canCancel = item.isCanCancel();

            time.setText(item.getTime());
            num.setText(item.getNum());

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


                            showToastShort("当前时间段不可取消");

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

    class TimeBean {
        private String time;
        private String num;
        private boolean selected;
        private boolean canCancel;


        public TimeBean(String time, String num, boolean selected, boolean canCancel) {
            this.time = time;
            this.num = num;
            this.selected = selected;
            this.canCancel = canCancel;
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
