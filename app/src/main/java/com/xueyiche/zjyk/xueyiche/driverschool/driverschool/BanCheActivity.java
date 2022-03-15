package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.xueyiche.zjyk.xueyiche.constants.bean.ListShaiXuanBean;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.BanCheListBean;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2019/3/13.
 */
public class BanCheActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_search;
    private ListView lv_banche;
    private RefreshLayout refreshLayout;
    private BanCheAdapter banCheAdapter;
    private List<BanCheListBean.ContentBean> content;
    private String driver_school_id;
    private MClearEditText tv_search_title;
    private ListShaiXuanBean listShaiXuanBean = new ListShaiXuanBean();

    @Override
    protected int initContentView() {
        return R.layout.banche_activity;
    }

    @Override
    protected void initView() {
        ll_back = (LinearLayout) findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tv_title = (TextView) findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        lv_banche = (ListView) view.findViewById(R.id.lv_banche);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_search_title = (MClearEditText) view.findViewById(R.id.tv_search_title);
        Intent intent = getIntent();
        driver_school_id = intent.getStringExtra("driver_school_id");
        listShaiXuanBean.setName("");
        getDataFromNet();
        banCheAdapter = new BanCheAdapter();

        tv_title.setText("班车查询");
    }

    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        lv_banche.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(App.context, BanCheContentActivity.class);
                int id = content.get(i).getId();
                intent.putExtra("id",""+id);
                intent.putExtra("driver_school_id",driver_school_id);
                startActivity(intent);
            }
        });
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

    // 请求网络数据
    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.BanChe_List)
                    .addParams("driver_school_id", driver_school_id)
                    .addParams("bus_name", listShaiXuanBean.getName())
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final BanCheListBean studentListBean = JsonUtil.parseJsonToBean(string, BanCheListBean.class);
                        if (studentListBean != null) {
                            final int code = studentListBean.getCode();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (200 == code) {
//                                        processData(string, false);
                                        content = studentListBean.getContent();
                                        lv_banche.setAdapter(banCheAdapter);

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

    private void processData(String string, final boolean isMore) {
//        final StudentListBean jiaoLianInfo = JsonUtil.parseJsonToBean(string, StudentListBean.class);
//        App.handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (jiaoLianInfo != null) {
//                    if (!isMore) {
//                        if (list.size() != 0) {
//                            list.clear();
//                        }
//                        List<StudentListBean.ContentBean> contentBeen = jiaoLianInfo.getContent();
//                        if (contentBeen != null && contentBeen.size() != 0) {
//                            list.addAll(contentBeen);
//                            ll_empty.setVisibility(View.GONE);
//                            lv_xueyuan.setAdapter(xueYuanAdapter);
//                            xueYuanAdapter.notifyDataSetChanged();
//                        } else {
//                            xueYuanAdapter.notifyDataSetChanged();
//                            ll_empty.setVisibility(View.VISIBLE);
//                        }
//                    } else {
//                        //加载更多
//                        content = jiaoLianInfo.getContent();
//                        if (content != null) {
//                            list.addAll(content);//追加更多数据
//                            xueYuanAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//            }
//        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_search:
                String trim = tv_search_title.getText().toString().trim();
                listShaiXuanBean.setName(!TextUtils.isEmpty(trim)?trim:"");
                getDataFromNet();
                break;
        }
    }

    private class BanCheAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (content != null) {
                return content.size();
            } else {
                return 0;
            }

        }

        @Override
        public Object getItem(int position) {
            return content.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            XueYuanViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.banche_list_item, null);
                holder = new XueYuanViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (XueYuanViewHolder) convertView.getTag();
            }
            BanCheListBean.ContentBean contentBean = content.get(position);
            String bus_name = contentBean.getBus_name();
            String bus_number = contentBean.getBus_number();
            final String driver_name = contentBean.getDriver_name();
            final String driver_phone = contentBean.getDriver_phone();
            String shifa = contentBean.getShifa();
            String shifa_time = contentBean.getShifa_time();
            String zhongdian = contentBean.getZhongdian();
            String zhongdian_time = contentBean.getZhongdian_time();
            if (!TextUtils.isEmpty(bus_name)) {
                holder.tv_name.setText(bus_name);
            }
            if (!TextUtils.isEmpty(bus_number)) {
                holder.tv_car_number.setText(bus_number);
            }
            if (!TextUtils.isEmpty(driver_name)) {
                holder.tv_driver_name.setText("司机：" + driver_name);
            }
            if (!TextUtils.isEmpty(shifa)&&!TextUtils.isEmpty(shifa_time)) {
                holder.tv_lucheng_start.setText("始发：" + shifa+"   "+shifa_time);
            } if (!TextUtils.isEmpty(zhongdian)&&!TextUtils.isEmpty(zhongdian_time)) {
                holder.tv_lucheng_end.setText("终点：" + zhongdian+"   "+zhongdian_time);
            }
            if (!TextUtils.isEmpty(driver_phone)) {
                holder.tv_driver_phone.setText(driver_phone);
                holder.tv_driver_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        XueYiCheUtils.CallPhone(BanCheActivity.this,driver_name,driver_phone);
                    }
                });
            }
            return convertView;
        }

        class XueYuanViewHolder {
            private TextView tv_name, tv_lucheng_start, tv_driver_name, tv_driver_phone, tv_car_number, tv_lucheng_end;

            public XueYuanViewHolder(View view) {
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_lucheng_start = (TextView) view.findViewById(R.id.tv_lucheng_start);
                tv_lucheng_end = (TextView) view.findViewById(R.id.tv_lucheng_end);
                tv_driver_name = (TextView) view.findViewById(R.id.tv_driver_name);
                tv_driver_phone = (TextView) view.findViewById(R.id.tv_driver_phone);
                tv_car_number = (TextView) view.findViewById(R.id.tv_car_number);

            }
        }
    }
}