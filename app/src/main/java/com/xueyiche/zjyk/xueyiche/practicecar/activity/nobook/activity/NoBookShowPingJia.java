package com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.activity;

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
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.bean.WZDriverDetailsBean;
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
 * Created by Owner on 2017/7/20.
 */
public class NoBookShowPingJia extends BaseActivity implements View.OnClickListener {
    private int pager = 1;
    private TextView tv_login_back;
    private TextView tv_wenxintishi;
    private String refer_id, evaluate_type;
    private RefreshLayout refreshLayout;
    private ListView lv_pingjia_all;
    private List<WZDriverDetailsBean.ContentBean.PinglunBean> content;
    private List<WZDriverDetailsBean.ContentBean.PinglunBean> list = new ArrayList<>();
    private NoBookPingJiaAdapter pingJiaAdapter;
    private LinearLayout ll_exam_back;

    @Override
    protected int initContentView() {
        return R.layout.pingjia_shop_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.driver_pay_include).findViewById(R.id.ll_exam_back);
        ll_exam_back.setOnClickListener(this);
        tv_login_back = (TextView) view.findViewById(R.id.driver_pay_include).findViewById(R.id.tv_login_back);
        tv_login_back.setText("全部评价");
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        lv_pingjia_all = (ListView) view.findViewById(R.id.lv_pingjia_all);
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
    }

    public void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            String user_id = PrefUtils.getString(this, "user_id", "");
            showProgressDialog(false);
            OkHttpUtils.post()
                    .url(AppUrl.WZDriver_Details)
                    .addParams("driver_id", refer_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("pager", "" + pager)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(string)) {
                                processData(string, true);
                            }
                        }
                    });
                    return null;
                }

                @Override
                public void onError(Request request, Exception e) {
                    showToastShort(StringConstants.CHECK_NET);
                    stopProgressDialog();
                }

                @Override
                public void onResponse(Object response) {
                    stopProgressDialog();
                }
            });
        }
    }


    public void getDataFromNet() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.WZDriver_Details)
                    .url(AppUrl.WZDriver_Details)
                    .addParams("driver_id", refer_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("pager", ""+pager)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(string)) {
                                processData(string, false);
                            }
                        }
                    });
                    return null;
                }

                @Override
                public void onError(Request request, Exception e) {
                    showToastShort(StringConstants.CHECK_NET);
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
        refer_id = getIntent().getStringExtra("refer_id");
        evaluate_type = getIntent().getStringExtra("evaluate_type");
        getDataFromNet();
        pingJiaAdapter = new NoBookPingJiaAdapter();
    }

    private void processData(String string, final boolean isMore) {
        final WZDriverDetailsBean pingJiaBean = JsonUtil.parseJsonToBean(string, WZDriverDetailsBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (pingJiaBean != null) {
                    if (!isMore) {
                        if (list.size() != 0) {
                            list.clear();
                        }
                        List<WZDriverDetailsBean.ContentBean.PinglunBean> content = pingJiaBean.getContent().getPinglun();
                        if (content != null && content.size() != 0) {
                            list.addAll(content);
                            lv_pingjia_all.setAdapter(pingJiaAdapter);
                        }

                    } else {
                        //加载更多
                        content = pingJiaBean.getContent().getPinglun();
                        list.addAll(content);//追加更多数据
                        pingJiaAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }

    public class NoBookPingJiaAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }

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
            WZDriverDetailsBean.ContentBean.PinglunBean contentBean = list.get(i);
            if (contentBean != null) {
                final String all_evaluate = contentBean.getAll_evaluate();
                final String user_name = contentBean.getNickname();
                final String head_img = contentBean.getHead_img();
                final String service_attitude = contentBean.getService_attitude();
                final String technological_level = contentBean.getTechnological_level();
                final String system_time = contentBean.getContent_time();
                final String content = contentBean.getContent();
                final String order_number = contentBean.getOrder_number();
                int praise_count = contentBean.getPraise_count();
                int reply_count = contentBean.getReply_count();

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
                if (!TextUtils.isEmpty("" + reply_count)) {
                    driverSchoolTrainerListViewHolder.tv_pinglun_number.setText("" + reply_count);
                }
                if (!TextUtils.isEmpty("" + praise_count)) {
                    driverSchoolTrainerListViewHolder.tv_dianzan_number.setText("" + praise_count);
                }
                if (!TextUtils.isEmpty(content)) {
                    driverSchoolTrainerListViewHolder.tv_pingjia_content.setText(content);
                }
                if (!TextUtils.isEmpty(system_time)) {
                    driverSchoolTrainerListViewHolder.tv_date.setText(system_time);
                }
                driverSchoolTrainerListViewHolder.ll_number.setVisibility(View.INVISIBLE);
            }
            return view;
        }

        private class DriverSchoolTrainerListViewHolder {
            private CircleImageView iv_head;
            private TextView tv_name, tv_fenshu, tv_date, tv_pingjia_content, tv_pinglun_number, tv_dianzan_number;
            private RatingBar rb_one_now, rb_teach_quality, rb_service_attitude;
            private LinearLayout ll_all,ll_number;

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
                ll_number = (LinearLayout) view.findViewById(R.id.ll_number);

            }
        }
    }
}
