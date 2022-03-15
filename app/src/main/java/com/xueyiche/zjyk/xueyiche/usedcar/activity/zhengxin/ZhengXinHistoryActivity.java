package com.xueyiche.zjyk.xueyiche.usedcar.activity.zhengxin;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.examtext.CommonWebView;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.ZhengXinHistoryBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2018/10/10.
 */
public class ZhengXinHistoryActivity extends BaseActivity {
    private LinearLayout llBack;
    private TextView tvTitle;
    private ListView lv_history;
    private RefreshLayout refreshLayout;
    private List<ZhengXinHistoryBean.ContentBean> content;
    private List<ZhengXinHistoryBean.ContentBean> list = new ArrayList<>();
    private LinearLayout ll_not_indent;
    private int pager = 1;
    private HistoryAdapter historyAdapter;

    @Override
    protected int initContentView() {
        return R.layout.zhengxin_history_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        lv_history = (ListView) view.findViewById(R.id.lv_history);
        ll_not_indent = (LinearLayout) view.findViewById(R.id.ll_not_indent);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        historyAdapter = new HistoryAdapter();

    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        tvTitle.setText("历史记录");
        getDataFromNet();
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

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context) ){
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            if (!TextUtils.isEmpty(user_id)) {
                showProgressDialog(false);
                OkHttpUtils.post().url(AppUrl.ZhengXin_History)
                        .addParams("user_id", user_id)
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
    }
    private void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog( false);
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            if (!TextUtils.isEmpty(user_id)) {
                OkHttpUtils.post().url(AppUrl.ZhengXin_History)
                        .addParams("user_id", user_id)
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
        final ZhengXinHistoryBean dingDanBean = JsonUtil.parseJsonToBean(string, ZhengXinHistoryBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (dingDanBean != null) {
                    int code = dingDanBean.getCode();
                    if (200==code) {
                        if (!isMore) {
                            if (list.size() != 0) {
                                list.clear();
                            }
                            List<ZhengXinHistoryBean.ContentBean> content = dingDanBean.getContent();
                            if (content != null && content.size() != 0) {
                                list.addAll(content);
                                lv_history.setAdapter(historyAdapter);
                                ll_not_indent.setVisibility(View.GONE);
                            } else {
                                ll_not_indent.setVisibility(View.VISIBLE);
                            }
                            historyAdapter.notifyDataSetChanged();
                        } else {
                            content = dingDanBean.getContent();
                            if (content != null) {
                                list.addAll(content);//追加更多数据
                                historyAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });
    }
    private class HistoryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HistoryViewHolder historyViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.zhengxin_history_item, null);
                historyViewHolder = new HistoryViewHolder(view);
                view.setTag(historyViewHolder);
            } else {
                historyViewHolder = (HistoryViewHolder) view.getTag();
            }

            String credit_num = list.get(i).getCredit_num();
            final String credit_url = list.get(i).getCredit_url();
            String find_system = list.get(i).getFind_system();
            if (!TextUtils.isEmpty(credit_num)) {
                historyViewHolder.tv_number.setText("编号："+credit_num);
            }
            if (!TextUtils.isEmpty(find_system)) {
                historyViewHolder.tv_date.setText("查询日期："+find_system);
            }
            historyViewHolder.iv_look.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(credit_url)) {
                        Intent intent2 = new Intent(App.context, CommonWebView.class);
                        intent2.putExtra("baoxianurl",credit_url);
                        intent2.putExtra("weburl","zhengxinbaogao");
                        startActivity(intent2);
                    }
                }
            });

            return view;
        }

        private class HistoryViewHolder {
            private TextView  tv_number,tv_date;
            private ImageView iv_look;
            public HistoryViewHolder(View view) {
                tv_date = (TextView) view.findViewById(R.id.tv_date);
                tv_number = (TextView) view.findViewById(R.id.tv_number);
                iv_look = (ImageView) view.findViewById(R.id.iv_look);
            }
        }
    }
}
