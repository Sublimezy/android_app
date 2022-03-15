package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.BanCheContentBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZL on 2019/4/24.
 */
public class BanCheContentActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back, ll_bottom;
    private TextView tv_title;
    private ListView lv_lucheng;
    private TextView tv_name, tv_lucheng_end, tv_lucheng_start, tv_driver_name, tv_driver_phone, tv_car_number;
    private LuChengAdapter luChengAdapter;
    private RadioButton rb_start, rb_end;
    private RefreshLayout refreshLayout;
    private String id;
    private String start_end = "1";
    private List<BanCheContentBean.ContentBean.ListDataBean> listData;
    private BanCheContentBean.ContentBean content;
    public static BanCheContentActivity instance;
    private String driver_school_id;


    @Override
    protected int initContentView() {
        return R.layout.banche_content_activity;
    }

    @Override
    protected void initView() {
        ll_back = (LinearLayout) findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tv_title = (TextView) findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        ll_bottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
        lv_lucheng = (ListView) view.findViewById(R.id.lv_lucheng);
        View viewHead = LayoutInflater.from(App.context).inflate(R.layout.head_view_banche, null);
        lv_lucheng.addHeaderView(viewHead);
        tv_name = (TextView) viewHead.findViewById(R.id.tv_name);
        tv_lucheng_start = (TextView) viewHead.findViewById(R.id.tv_lucheng_start);
        tv_lucheng_end = (TextView) viewHead.findViewById(R.id.tv_lucheng_end);
        tv_driver_name = (TextView) viewHead.findViewById(R.id.tv_driver_name);
        tv_driver_phone = (TextView) viewHead.findViewById(R.id.tv_driver_phone);
        tv_car_number = (TextView) viewHead.findViewById(R.id.tv_car_number);
        rb_start = (RadioButton) viewHead.findViewById(R.id.rb_start);
        rb_end = (RadioButton) viewHead.findViewById(R.id.rb_end);
        lv_lucheng.setFocusable(false);
        luChengAdapter = new LuChengAdapter();

        instance = this;
    }

    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        rb_start.setOnClickListener(this);
        rb_end.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
//                            pager = 1;
//                            content = null;
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
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.BanChe_Content)
                    .addParams("driver_school_id", driver_school_id)
                    .addParams("id", id)
                    .addParams("start_end", start_end)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final BanCheContentBean studentListBean = JsonUtil.parseJsonToBean(string, BanCheContentBean.class);
                        if (studentListBean != null) {
                            final int code = studentListBean.getCode();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (200 == code) {
                                        content = studentListBean.getContent();
                                        String bus_name = content.getBus_name();
                                        String bus_number = content.getBus_number();
                                        String driver_name = content.getDriver_name();
                                        String driver_phone = content.getDriver_phone();
                                        String shifa = content.getShifa();
                                        String zhongdian = content.getZhongdian();
                                        String zhongdian_time = content.getZhongdian_time();
                                        String shifa_time = content.getShifa_time();

                                        listData = content.getListData();
                                        if (!TextUtils.isEmpty(bus_number)) {
                                            tv_name.setText("车牌号：" + bus_number);
                                        }
                                        if (!TextUtils.isEmpty(bus_name)) {
                                            tv_title.setText(bus_name);
                                        }
                                        if (!TextUtils.isEmpty(driver_name)) {
                                            tv_driver_name.setText("司机：" + driver_name);
                                        }
                                        if (!TextUtils.isEmpty(driver_phone)) {
                                            tv_driver_phone.setText(driver_phone);
                                        }
                                        if (!TextUtils.isEmpty(shifa)&&!TextUtils.isEmpty(shifa_time)) {
                                            tv_lucheng_start.setText("始发："+shifa+"   "+shifa_time);
                                        }
                                        if (!TextUtils.isEmpty(zhongdian)&&!TextUtils.isEmpty(zhongdian_time)) {
                                            tv_lucheng_end.setText("终点："+zhongdian+"   "+zhongdian_time);
                                        }
//                                        processData(string, false);
                                        lv_lucheng.setAdapter(luChengAdapter);

                                    }
                                }
                            });
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

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        driver_school_id = intent.getStringExtra("driver_school_id");
        getDataFromNet();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rb_start:
                start_end = "1";
                listData=null;
                getDataFromNet();
                luChengAdapter.notifyDataSetChanged();
                break;
            case R.id.rb_end:
                start_end = "2";
                listData=null;
                getDataFromNet();
                luChengAdapter.notifyDataSetChanged();
                break;
        }
    }

    private class LuChengAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (listData != null) {
                return listData.size();
            } else {
                return 0;
            }

        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            XueYuanViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.lucheng_item, null);
                holder = new XueYuanViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (XueYuanViewHolder) convertView.getTag();
            }
            holder.dot_iv.setText((position + 1) + "");
            if (position == 0) {
                holder.dot_iv.setTextColor(getResources().getColor(R.color.green));
                holder.dot_iv.setBackgroundResource(R.drawable.kongxin_yuan_one);
                holder.time_line_view.setVisibility(View.VISIBLE);
            } else if (position == listData.size() - 1) {
                //最后一条数据，隐藏时间轴的竖线和水平的分割线
                holder.time_line_view.setVisibility(View.INVISIBLE);
                holder.dot_iv.setTextColor(getResources().getColor(R.color.red));
                holder.dot_iv.setBackgroundResource(R.drawable.kongxin_yuan_end);
            } else {
                holder.dot_iv.setTextColor(getResources().getColor(R.color.test_color));
                holder.dot_iv.setBackgroundResource(R.drawable.kongxin_yuan_zhongjian);
                holder.time_line_view.setVisibility(View.VISIBLE);
            }

            BanCheContentBean.ContentBean.ListDataBean listDataBean = listData.get(position);
            String station = listDataBean.getStation();
            String station_address = listDataBean.getStation_address();
            String station_time = listDataBean.getStation_time();
            if (!TextUtils.isEmpty(station)) {
                holder.tv_name.setText(station);
            }
            if (!TextUtils.isEmpty(station_address)) {
                holder.tv_location.setText(station_address);
            }
            if (!TextUtils.isEmpty(station_time)) {
                holder.tv_time.setText(station_time);
            }
            return convertView;
        }

        class XueYuanViewHolder {
            private TextView tv_name, tv_time, tv_location, dot_iv;
            private View time_line_view, line_view;

            public XueYuanViewHolder(View view) {
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_time = (TextView) view.findViewById(R.id.tv_time);
                tv_location = (TextView) view.findViewById(R.id.tv_location);
                dot_iv = (TextView) view.findViewById(R.id.dot_iv);
                time_line_view = view.findViewById(R.id.time_line_view);
                line_view = view.findViewById(R.id.line_view);

            }
        }
    }
}
