package com.xueyiche.zjyk.xueyiche.practicecar.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderRecordBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.PingJiaOrderDriver;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2019/3/11.
 */
public class OrderRecordActivity extends BaseActivity {
    private LinearLayout llBack;
    private TextView tvTitle;
    private ListView lv_list;
    private OrderRecordAdapter orderRecordAdapter;
    private List<OrderRecordBean.ContentBean> content;
    private int pager = 1;
    private List<OrderRecordBean.ContentBean> list = new ArrayList<>();
    private RefreshLayout refreshLayout;


    @Override
    protected int initContentView() {
        return R.layout.driver_school_trainer_list_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.driver_school_trainer_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.driver_school_trainer_include).findViewById(R.id.tv_login_back);
        lv_list = (ListView) view.findViewById(R.id.lv_list);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        orderRecordAdapter = new OrderRecordAdapter();
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        tvTitle.setText("预约记录");
        getDataFromNet();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            String user_id = PrefUtils.getParameter("user_id");
            OkHttpUtils.post().url(AppUrl.Order_Record)
                    .addParams("user_id",user_id)
                    .addParams("pager","1")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            final String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                OrderRecordBean orderRecordBean = JsonUtil.parseJsonToBean(string, OrderRecordBean.class);
                                if (orderRecordBean != null) {
                                    int code = orderRecordBean.getCode();
                                    if (200 == code) {
                                        content = orderRecordBean.getContent();
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                             processData(string,false);
                                            }
                                        });

                                    }
                                }
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
        }else {
            Toast.makeText(OrderRecordActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }
    private void processData(String string, final boolean isMore) {
        final OrderRecordBean jiaoLianInfo = JsonUtil.parseJsonToBean(string, OrderRecordBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (jiaoLianInfo != null) {
                    if (!isMore) {
                        if (list.size() != 0) {
                            list.clear();
                        }
                        List<OrderRecordBean.ContentBean> contentBeen = jiaoLianInfo.getContent();
                        if (contentBeen != null && contentBeen.size() != 0) {
                            list.addAll(contentBeen);
                            lv_list.setAdapter(orderRecordAdapter);
                            orderRecordAdapter.notifyDataSetChanged();
                        } else {
                            orderRecordAdapter.notifyDataSetChanged();
                        }
                    } else {
                        //加载更多
                        content = jiaoLianInfo.getContent();
                        if (content != null) {
                            list.addAll(content);//追加更多数据
                            orderRecordAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
    private void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            String user_id = PrefUtils.getParameter("user_id");
            OkHttpUtils.post().url(AppUrl.Order_Record)
                    .addParams("user_id",user_id)
                    .addParams("pager",""+pager)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            final String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                OrderRecordBean orderRecordBean = JsonUtil.parseJsonToBean(string, OrderRecordBean.class);
                                if (orderRecordBean != null) {
                                    int code = orderRecordBean.getCode();
                                    if (200 == code) {
                                        content = orderRecordBean.getContent();
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                processData(string,true);
                                            }
                                        });

                                    }
                                }
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
        }else {
            Toast.makeText(OrderRecordActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }
    private class OrderRecordAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (list != null) {
                return list.size();
            } else {
                return 0;
            }

        }

        @Override
        public Object getItem(int position) {
            return list.get(position);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            XueYuanViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.order_record_list_item, null);
                holder = new XueYuanViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (XueYuanViewHolder) convertView.getTag();
            }
            OrderRecordBean.ContentBean contentBean = list.get(position);
            String yuyue_date = contentBean.getYuyue_date();
            final String driver_name = contentBean.getDriver_name();
            String driver_school_name = contentBean.getDriver_school_name();
            String km = contentBean.getKm();
            String xlxm = contentBean.getXlxm();
            String xunlian_status = contentBean.getXunlian_status();
            final String head_img = contentBean.getHead_img();
            String pingjia = contentBean.getPingjia();
            final String driver_id = contentBean.getDriver_id();
            final String order_number = contentBean.getOrder_number();
            if (!TextUtils.isEmpty(yuyue_date)) {
                holder.tv_time.setText(yuyue_date);
            }
            if (!TextUtils.isEmpty(driver_school_name) && !TextUtils.isEmpty(driver_name)) {
                holder.tv_content.setText( driver_school_name + driver_name + "教练");
            }
            if (!TextUtils.isEmpty(km) && !TextUtils.isEmpty(xlxm)) {
                String kemu = null;
                if ("1".equals(km)) {
                    kemu = "科目一：";
                } else if ("2".equals(km)) {
                    kemu = "科目二：";
                } else if ("3".equals(km)) {
                    kemu = "科目三：";
                } else if ("4".equals(km)) {
                    kemu = "科目四：";
                }
                holder.tv_xiangmu.setText(kemu + xlxm);
            }
            if (!TextUtils.isEmpty(xunlian_status)) {
                holder.tv_state.setText(xunlian_status);
                if ("已训练".equals(xunlian_status)) {
                    holder.tv_state.setTextColor(getResources().getColor(R.color.colorOrange));
                    if (!TextUtils.isEmpty(pingjia)) {
                        if ("0".equals(pingjia)) {
                            holder.tv_state.setText("去评价");
                            holder.tv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(App.context, PingJiaOrderDriver.class);
                                    intent.putExtra("head_img",head_img);
                                    intent.putExtra("driver_name",driver_name);
                                    intent.putExtra("order_number",order_number);
                                    intent.putExtra("driver_id",driver_id);
                                    startActivity(intent);
                                }
                            });

                        }else if ("1".equals(pingjia)){
                            holder.tv_state.setText("已评价");
                        }
                    }


                }
            }

            return convertView;
        }

        class XueYuanViewHolder {
            private TextView tv_xiangmu, tv_state, tv_time, tv_content, tv_kemu, tv_driver_name;

            public XueYuanViewHolder(View view) {
                tv_xiangmu = (TextView) view.findViewById(R.id.tv_xiangmu);
                tv_state = (TextView) view.findViewById(R.id.tv_state);
                tv_time = (TextView) view.findViewById(R.id.tv_time);
                tv_content = (TextView) view.findViewById(R.id.tv_content);
            }
        }
    }


}
