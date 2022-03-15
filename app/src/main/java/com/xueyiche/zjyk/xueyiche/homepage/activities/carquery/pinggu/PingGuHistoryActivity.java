package com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.pinggu;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.bean.HistoryBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2019/5/31.
 */
public class PingGuHistoryActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private ListView lv_order_gujia;
    private LinearLayout ll_order_empty;
    private OrderAdapter orderAdapter;
    private List<HistoryBean.MessageBean> message;

    @Override
    protected int initContentView() {
        return R.layout.pinggu_order_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        lv_order_gujia = (ListView) view.findViewById(R.id.lv_order_gujia);
        ll_order_empty = (LinearLayout) view.findViewById(R.id.ll_order_empty);
        tv_login_back.setText("历史记录");

    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        lv_order_gujia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (message!=null) {
                    HistoryBean.MessageBean messageBean = message.get(i);
                    Intent intent1 = new Intent(PingGuHistoryActivity.this,OrderContentNoActivity.class);
                    Intent intent2 = new Intent(PingGuHistoryActivity.this,OrderContentYesActivity.class);
                    if (messageBean!=null) {
                        int id = messageBean.getId();
                        int istate = messageBean.getIstate();
                        LogUtil.e("istate",istate+"");

                        if (6<=istate&&istate<=11) {
                            //已完成
                            intent2.putExtra("tid",id+"");
                            startActivity(intent2);
                        }
                        if (-1==istate) {
                            //驳回
                            intent1.putExtra("tid",id+"");
                            startActivity(intent1);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromNet();
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String user_phone = PrefUtils.getString(App.context, "user_phone", "");
            showProgressDialog(false);
            OkHttpUtils.post().
//                    url("http://172.16.51.61:8082/secondhandcarevaluation/querylist.do")
                    url(AppUrl.PingGu_History_List)
                    .addParams("user_tel", AES.decrypt(user_phone))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    LogUtil.e("232323",string);
                    if (!TextUtils.isEmpty(string)) {
                        processData(string);
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

    private void processData(String string) {
        final HistoryBean historyBean = JsonUtil.parseJsonToBean(string, HistoryBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (historyBean != null) {
                    message = historyBean.getMessage();
                    if (message != null) {
                        orderAdapter = new OrderAdapter(message, App.context, R.layout.gujia_order_list_item);
                        lv_order_gujia.setAdapter(orderAdapter);
                    } else {
                        ll_order_empty.setVisibility(View.VISIBLE);
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
        }
    }


    public class OrderAdapter extends BaseCommonAdapter {

        public OrderAdapter(List<HistoryBean.MessageBean> message, Context context, int layoutId) {
            super(message, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            HistoryBean.MessageBean messageBean = (HistoryBean.MessageBean) item;
            int id = messageBean.getId();
            int tradeprice = messageBean.getTradeprice();
            int istate = messageBean.getIstate();
            String url = messageBean.getUrl();
            String accessprice = messageBean.getAccessprice();
            String expect_time = messageBean.getExpect_time();
            String apply_time = messageBean.getApply_time();
            String reject_time = messageBean.getReject_time();
            String reject_msg = messageBean.getReject_msg();
            String reportdate = messageBean.getReportdate();
            viewHolder.setText(R.id.tv_one, "单号: "+id);
            viewHolder.setText(R.id.tv_two, "成交价: " + tradeprice );
            viewHolder.setPic(R.id.iv_car,url);
            if (istate<=5) {
                //进行中
                viewHolder.setLocalPic(R.id.iv_type, R.mipmap.pinggu_order_wait);

                viewHolder.setText(R.id.tv_three, "提交时间: "+apply_time);
                viewHolder.setText(R.id.tv_four, "预计完成时间: "+expect_time);
            }
            if (6<=istate&&istate<=11) {
                //已完成

                viewHolder.setLocalPic(R.id.iv_type, R.mipmap.pinggu_order_yes);
                viewHolder.setText(R.id.tv_three, "评估价: "+accessprice);
                viewHolder.setText(R.id.tv_four, "完成时间: "+reportdate);
            }
            if (-1==istate) {
                //驳回
                viewHolder.setLocalPic(R.id.iv_type, R.mipmap.pinggu_order_no);
                viewHolder.setText(R.id.tv_three, "驳回时间: "+reject_time);
                viewHolder.changeTextColor(R.id.tv_four,"#ff0000");
                viewHolder.setText(R.id.tv_four, "驳回原因: "+reject_msg);
            }
            if (-2==istate) {
                //不予评估
                viewHolder.setLocalPic(R.id.iv_type, R.mipmap.pinggu_order_out);
                viewHolder.setText(R.id.tv_three, "驳回时间:"+reject_time);
                viewHolder.changeTextColor(R.id.tv_four,"#ff0000");
                viewHolder.setText(R.id.tv_four, "驳回原因: "+reject_msg);
            }

        }
    }
}
