package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.xueyiche.zjyk.xueyiche.driverschool.driverschool.TrainerListActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.DriverSchoolCoachPingJiaBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2018/7/16.
 */
public class DriverSchoolTrainerListActivity extends BaseActivity {
    private LinearLayout llBack;
    private ListView lv_list;
    private TextView tv_login_back;
    private int pager = 1;
    private String driver_school_id;
    private List<DriverSchoolCoachPingJiaBean.ContentBean> content;
    private List<DriverSchoolCoachPingJiaBean.ContentBean> list = new ArrayList<>();
    public static DriverSchoolTrainerListActivity instance;
    private String style;
    private RefreshLayout refreshLayout;
    private DriverSchoolTrainerListAdapter driverSchoolTrainerListAdapter;
    private String order_number;

    @Override
    protected int initContentView() {
        return R.layout.driver_school_trainer_list_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.driver_school_trainer_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.driver_school_trainer_include).findViewById(R.id.tv_login_back);
        lv_list = (ListView) view.findViewById(R.id.lv_list);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        instance = this;
        driverSchoolTrainerListAdapter = new DriverSchoolTrainerListAdapter(list);
    }

    @Override
    protected void initListener() {
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
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        tv_login_back.setText("教练");
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        driver_school_id = intent.getStringExtra("id");
        style = intent.getStringExtra("style");
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(App.context, PingJiaDriverSchool.class);
                Intent intent2 = new Intent(App.context, TrainerListActivity.class);
                if (list.size()>0) {
                    DriverSchoolCoachPingJiaBean.ContentBean contentBean = list.get(i);
                    if (contentBean != null) {
                        String driver_id = contentBean.getDriver_id();
                        String head_img = contentBean.getHead_img();
                        String driver_name = contentBean.getDriver_name();
                        if ("1".equals(style)) {
                            intent1.putExtra("driver_school_id", driver_school_id);
                            intent1.putExtra("order_number", order_number);
                            intent1.putExtra("head_img", head_img);
                            intent1.putExtra("driver_id", driver_id);
                            intent1.putExtra("driver_name", driver_name);
                            intent1.putExtra("evaluate_type", "0");
                            startActivity(intent1);
                        } else if ("2".equals(style)) {
                            intent2.putExtra("driver_id", driver_id);
                            startActivity(intent2);
                        }
                    }
                }
            }
        });
        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
                showProgressDialog(false);
                OkHttpUtils.post().url(AppUrl.Driver_School_Coach_List)
                        .addParams("driver_school_id", driver_school_id)
                        .addParams("order_number", order_number)
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
        }
    }

    private void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
                OkHttpUtils.post().url(AppUrl.Driver_School_Coach_List)
                        .addParams("driver_school_id", driver_school_id)
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

    private void processData(String string, final boolean isMore) {
        final DriverSchoolCoachPingJiaBean driverSchoolCoachPingJiaBean = JsonUtil.parseJsonToBean(string, DriverSchoolCoachPingJiaBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (driverSchoolCoachPingJiaBean != null) {
                    if (!isMore) {
                        if (list.size() != 0) {
                            list.clear();
                        }
                        List<DriverSchoolCoachPingJiaBean.ContentBean> content = driverSchoolCoachPingJiaBean.getContent();
                        if (content != null && content.size() != 0) {
                            list.addAll(content);
                            lv_list.setAdapter(driverSchoolTrainerListAdapter);
                        }
                        driverSchoolTrainerListAdapter.notifyDataSetChanged();
                    } else {
                        content = driverSchoolCoachPingJiaBean.getContent();
                        if (content != null) {
                            list.addAll(content);//追加更多数据
                            driverSchoolTrainerListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    private class DriverSchoolTrainerListAdapter extends BaseAdapter {
        private List<DriverSchoolCoachPingJiaBean.ContentBean> list;

        public DriverSchoolTrainerListAdapter(List<DriverSchoolCoachPingJiaBean.ContentBean> list) {
            this.list =list ;
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
                view = LayoutInflater.from(App.context).inflate(R.layout.driver_school_trainer_list_item, null);
                driverSchoolTrainerListViewHolder = new DriverSchoolTrainerListViewHolder(view);
                view.setTag(driverSchoolTrainerListViewHolder);
            } else {
                driverSchoolTrainerListViewHolder = (DriverSchoolTrainerListViewHolder) view.getTag();
            }
            driverSchoolTrainerListViewHolder.rb_one_now.setStepSize(0.5f);
            DriverSchoolCoachPingJiaBean.ContentBean contentBean = list.get(i);
            if (contentBean != null) {

                String all_evaluate = contentBean.getAll_evaluate();
                String driver_name = contentBean.getDriver_name();
                String head_img = contentBean.getHead_img();
                String allow_driving_type = contentBean.getCar_type();
                String sex = contentBean.getSex();
                if (!TextUtils.isEmpty(all_evaluate)) {
                    if ("暂无评价".equals(all_evaluate)) {
                        driverSchoolTrainerListViewHolder.tv_fenshu.setText("暂无评价");
                        driverSchoolTrainerListViewHolder.rb_one_now.setVisibility(View.INVISIBLE);
                    }else {
                        float star = Float.parseFloat(all_evaluate);
                        driverSchoolTrainerListViewHolder.rb_one_now.setRating(star);
                        driverSchoolTrainerListViewHolder.tv_fenshu.setText(all_evaluate);
                    }
                } else {
                    driverSchoolTrainerListViewHolder.tv_fenshu.setText("暂无评价");
                    driverSchoolTrainerListViewHolder.rb_one_now.setVisibility(View.INVISIBLE);
                }
                if (!TextUtils.isEmpty(head_img)) {
                    Picasso.with(App.context).load(head_img).placeholder(R.mipmap.hot_train_head).error(R.mipmap.hot_train_head).into(driverSchoolTrainerListViewHolder.iv_head);
                }
                if (!TextUtils.isEmpty(driver_name)) {
                    driverSchoolTrainerListViewHolder.tv_name.setText(driver_name);
                }
                if (!TextUtils.isEmpty(allow_driving_type)) {
                    driverSchoolTrainerListViewHolder.tv_carstyle.setText(allow_driving_type);
                }
                if (!TextUtils.isEmpty(sex)) {
                    driverSchoolTrainerListViewHolder.tv_sex.setText(sex);
                }

            }
            return view;
        }

        private class DriverSchoolTrainerListViewHolder {
            private CircleImageView iv_head;
            private TextView tv_name, tv_fenshu, tv_carstyle,tv_sex;
            private RatingBar rb_one_now;

            public DriverSchoolTrainerListViewHolder(View view) {
                iv_head = (CircleImageView) view.findViewById(R.id.iv_head);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_sex = (TextView) view.findViewById(R.id.tv_sex);
                rb_one_now = (RatingBar) view.findViewById(R.id.rb_one_now);
                tv_fenshu = (TextView) view.findViewById(R.id.tv_fenshu);
                tv_carstyle = (TextView) view.findViewById(R.id.tv_carstyle);
            }
        }
    }
}
