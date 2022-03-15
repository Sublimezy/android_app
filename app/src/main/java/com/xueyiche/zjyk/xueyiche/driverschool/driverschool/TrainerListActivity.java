package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.RemarkTrainerListBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.ImageShower;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2018/7/16.
 */
public class TrainerListActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private ListView lv_list;
    private TextView tv_name, tv_login_back;
    private String driver_id;
    private RefreshLayout refreshLayout;
    private CircleImageView iv_head;
    private TextView tv_years_old, tv_sex, tv_driver_school, tv_car_style;
    private String head_img;
    private int pager = 1;
    List<RemarkTrainerListBean.ContentBean.EvaluateBean> content;
    private List<RemarkTrainerListBean.ContentBean.EvaluateBean> list = new ArrayList<>();
    private ListAdapter listAdapter;
    private LinearLayout ll_driver_content;
    private String user_id;

    @Override
    protected int initContentView() {
        return R.layout.driver_school_trainer_list_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.driver_school_trainer_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.driver_school_trainer_include).findViewById(R.id.tv_login_back);
        lv_list = (ListView) view.findViewById(R.id.lv_list);
        ll_driver_content = (LinearLayout) view.findViewById(R.id.ll_driver_content);
        ll_driver_content.setVisibility(View.VISIBLE);
        iv_head = (CircleImageView) view.findViewById(R.id.iv_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_years_old = (TextView) view.findViewById(R.id.tv_years_old);
        tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        tv_driver_school = (TextView) view.findViewById(R.id.tv_driver_school);
        tv_car_style = (TextView) view.findViewById(R.id.tv_car_style);
        user_id = PrefUtils.getString(App.context, "user_id", "");
        listAdapter = new ListAdapter(list);
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        iv_head.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            pager = 1;
                            content = null;
                            getDataFromNet();
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                            Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 1500);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                                                @Override
                                                public void onLoadMore(final RefreshLayout refreshLayout) {
                                                    refreshLayout.getLayout().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (XueYiCheUtils.IsHaveInternet(App.context)) {
                                                                if (content != null && content.size() == 0) {
                                                                    showToastShort(StringConstants.MEIYOUSHUJU);
                                                                    refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                                                } else {
                                                                    pager += 1;
                                                                    getMoreDataFromNet();
                                                                    refreshLayout.finishLoadMore();
                                                                }
                                                            } else {
                                                                refreshLayout.finishLoadMore();
                                                                Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    }, 1500);
                                                }
                                            }

        );
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        driver_id = intent.getStringExtra("driver_id");
        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.DriverSchool_Trainer_Content)
                    .addParams("driver_id", driver_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("pager", pager + "")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        processData(string, false);
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
        } else {
            Toast.makeText(TrainerListActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String user_phone = PrefUtils.getString(App.context, "user_phone", "");
            if (!"".equals(user_phone)) {
                OkHttpUtils.post().url(AppUrl.DriverSchool_Trainer_Content)
                        .addParams("driver_id", driver_id)
                        .addParams("device_id", LoginUtils.getId(this))
                        .addParams("pager", pager + "")
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            processData(string, true);
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

    private void processData(String string, final boolean isMore) {
        final RemarkTrainerListBean remarkTrainerListBean = JsonUtil.parseJsonToBean(string, RemarkTrainerListBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (remarkTrainerListBean != null) {
                    RemarkTrainerListBean.ContentBean allcontent = remarkTrainerListBean.getContent();
                    if (allcontent != null) {
                        if (!isMore) {
                            String age = allcontent.getAge();
                            String driver_name = allcontent.getDriver_name();
                            head_img = allcontent.getHead_img();
                            String sex = allcontent.getSex();
                            String driver_school_name = allcontent.getDriver_school_name();
                            String car_type = allcontent.getCar_type();
                            String driver_id_back = allcontent.getDriver_id();
                            tv_login_back.setText(driver_name);
                            tv_name.setText(driver_name);
                            tv_sex.setText(sex);
                            tv_years_old.setText(age);
                            tv_car_style.setText(car_type);
                            tv_driver_school.setText(driver_school_name);
                            Picasso.with(App.context).load(head_img).placeholder(R.mipmap.hot_train_head).error(R.mipmap.hot_train_head).into(iv_head);
                            if (list.size() != 0) {
                                list.clear();
                            }
                            List<RemarkTrainerListBean.ContentBean.EvaluateBean> content = allcontent.getEvaluate();
                            if (content != null && content.size() != 0) {
                                list.addAll(content);
                                lv_list.setAdapter(listAdapter);
                            }
                            listAdapter.notifyDataSetChanged();
                        } else {
                            content = allcontent.getEvaluate();
                            if (content != null) {
                                list.addAll(content);//追加更多数据
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_head:
                Intent intent = new Intent(App.context, ImageShower.class);
                intent.putExtra("head_img", head_img);
                startActivity(intent);
                break;
        }
    }

    private class ListAdapter extends BaseAdapter {
        private List<RemarkTrainerListBean.ContentBean.EvaluateBean> list;

        public ListAdapter(List<RemarkTrainerListBean.ContentBean.EvaluateBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DriverSchoolTrainerListViewHolder driverSchoolTrainerListViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.trainer_list_item, null);
                driverSchoolTrainerListViewHolder = new DriverSchoolTrainerListViewHolder(view);
                view.setTag(driverSchoolTrainerListViewHolder);
            } else {
                driverSchoolTrainerListViewHolder = (DriverSchoolTrainerListViewHolder) view.getTag();
            }

            driverSchoolTrainerListViewHolder.rb_one_now.setStepSize(0.5f);
            driverSchoolTrainerListViewHolder.rb_teach_quality.setStepSize(0.5f);
            driverSchoolTrainerListViewHolder.rb_service_attitude.setStepSize(0.5f);
            RemarkTrainerListBean.ContentBean.EvaluateBean contentBean = list.get(i);
            if (contentBean != null) {
                final String all_evaluate = contentBean.getAll_evaluate();
                final String user_name = contentBean.getNickname();
                final String head_img = contentBean.getHead_img();
                final String service_attitude = contentBean.getService_attitude();
                final String technological_level = contentBean.getTechnological_level();
                final String system_time = contentBean.getContent_time();
                final String content = contentBean.getContent();
                final String order_number = contentBean.getOrder_number();
                String praise_count = contentBean.getPraise_count();
                String reply_count = contentBean.getReply_count();
                String reply = contentBean.getReply();
                if (!TextUtils.isEmpty(all_evaluate)) {
                    float star = Float.parseFloat(all_evaluate);
                    driverSchoolTrainerListViewHolder.rb_one_now.setRating(star);
                    driverSchoolTrainerListViewHolder.tv_fenshu.setText(all_evaluate);
                }
                if (!TextUtils.isEmpty(technological_level)) {
                    float star = Float.parseFloat(technological_level);
                    driverSchoolTrainerListViewHolder.rb_teach_quality.setRating(star);
                }
                if (!TextUtils.isEmpty(service_attitude)) {
                    float star = Float.parseFloat(service_attitude);
                    driverSchoolTrainerListViewHolder.rb_service_attitude.setRating(star);
                }
                if (!TextUtils.isEmpty(head_img)) {
                    Picasso.with(App.context).load(head_img).placeholder(R.mipmap.hot_train_head).error(R.mipmap.hot_train_head).into(driverSchoolTrainerListViewHolder.iv_head);
                }
                if (!TextUtils.isEmpty(user_name)) {
                    driverSchoolTrainerListViewHolder.tv_name.setText(user_name);
                }
                if (!TextUtils.isEmpty(reply_count)) {
                    driverSchoolTrainerListViewHolder.tv_pinglun_number.setText(reply_count);
                }
                if (!TextUtils.isEmpty(praise_count)) {
                    driverSchoolTrainerListViewHolder.tv_dianzan_number.setText(praise_count);
                }
                if (!TextUtils.isEmpty(content)) {
                    driverSchoolTrainerListViewHolder.tv_pingjia_content.setText(content);
                }
                if (!TextUtils.isEmpty(system_time)) {
                    driverSchoolTrainerListViewHolder.tv_date.setText(system_time);
                }
                driverSchoolTrainerListViewHolder.ll_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(App.context, JiaoLianPJActivity.class);
                        intent.putExtra("head_img", head_img);
                        intent.putExtra("user_name", user_name);
                        intent.putExtra("all_evaluate", all_evaluate);
                        intent.putExtra("service_attitude", service_attitude);
                        intent.putExtra("technological_level", technological_level);
                        intent.putExtra("content", content);
                        intent.putExtra("system_time", system_time);
                        intent.putExtra("driver_id", driver_id);
                        intent.putExtra("order_number", order_number);
                        startActivity(intent);
                    }
                });

            }
            return view;
        }

        private class DriverSchoolTrainerListViewHolder {
            private CircleImageView iv_head;
            private TextView tv_name, tv_fenshu, tv_date, tv_pingjia_content, tv_pinglun_number, tv_dianzan_number;
            private RatingBar rb_one_now, rb_teach_quality, rb_service_attitude;
            private LinearLayout ll_all;

            public DriverSchoolTrainerListViewHolder(View view) {
                iv_head = (CircleImageView) view.findViewById(R.id.iv_head);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                rb_one_now = (RatingBar) view.findViewById(R.id.rb_one_now);
                rb_teach_quality = (RatingBar) view.findViewById(R.id.rb_teach_quality);
                rb_service_attitude = (RatingBar) view.findViewById(R.id.rb_service_attitude);
                tv_fenshu = (TextView) view.findViewById(R.id.tv_fenshu);
                tv_pingjia_content = (TextView) view.findViewById(R.id.tv_pingjia_content);
                tv_pinglun_number = (TextView) view.findViewById(R.id.tv_pinglun_number);
                tv_dianzan_number = (TextView) view.findViewById(R.id.tv_dianzan_number);
                tv_date = (TextView) view.findViewById(R.id.tv_date);
                ll_all = (LinearLayout) view.findViewById(R.id.ll_all);

            }
        }
    }
}
