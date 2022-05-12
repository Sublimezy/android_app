package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.activity.ArrivedActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.IndentContentBean;
import com.xueyiche.zjyk.xueyiche.mine.bean.OrderListBean;
import com.xueyiche.zjyk.xueyiche.mine.decoration.GridItemDecoration;
import com.xueyiche.zjyk.xueyiche.mine.view.LoadingLayout;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderListActivity extends BaseActivity {


    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.ll_common_back)
    LinearLayout llCommonBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_btn)
    TextView tvRightBtn;
    @BindView(R.id.iv_caidan)
    ImageView ivCaidan;
    @BindView(R.id.title_view_heng)
    View titleViewHeng;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private OrderAdapter orderAdapter;
    private LoadingLayout loading;

    @Override
    protected int initContentView() {
        return R.layout.activity_my_order_list;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvTitle.setText("我的订单");
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        loading = LoadingLayout.wrap(refreshLayout);
        loading.showLoading();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("我的订单");
        orderAdapter = new OrderAdapter(R.layout.item_oeder_list);
        GridItemDecoration gridItemDecoration = new GridItemDecoration(this, DividerItemDecoration.VERTICAL);
        gridItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(gridItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(orderAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pager = 0;
                getData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pager++;
                getData();
            }
        });
        getData();
    }

    int pager = 1;

    public void getData() {
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if ("进行中".equals(orderAdapter.getData().get(position).getOrder_status())) {
                    Map<String, String> map = new HashMap<>();
                    map.put("order_sn", orderAdapter.getData().get(position).getOrder_sn());
                    MyHttpUtils.postHttpMessage(AppUrl.orderDetails, map, IndentContentBean.class, new RequestCallBack<IndentContentBean>() {
                        @Override
                        public void requestSuccess(IndentContentBean json) {
                            if (json.getCode() == 1) {
                                String order_status = json.getData().getOrder_status() + "";
                                String order_sn = json.getData().getOrder_sn() + "";
                                //0待接单 1已接单 2已到达 3开始服务 4结束服务 5已完成 -1取消
                                if ("0".equals(order_status)) {
                                    if (!TextUtils.isEmpty(order_sn)) {
                                        WaitActivity.forward(MyOrderListActivity.this, order_sn);
                                    }
                                } else if ("1".equals(order_status)) {
                                    if (!TextUtils.isEmpty(order_sn)) {
                                        JieDanActivity.forward(MyOrderListActivity.this, order_sn);
                                    }
                                } else if ("2".equals(order_status)) {
                                    if (!TextUtils.isEmpty(order_sn)) {
                                        ArrivedActivity.forward(MyOrderListActivity.this, order_sn);
                                    }

                                }else if ("2".equals(order_status)) {
                                    if (!TextUtils.isEmpty(order_sn)) {
                                        ArrivedActivity.forward(MyOrderListActivity.this, order_sn);
                                    }

                                }else if ("3".equals(order_status)) {
                                    if (!TextUtils.isEmpty(order_sn)) {
                                        JinXingActivity.forward(MyOrderListActivity.this, order_sn);
                                    }

                                }
                            } else {
                                showToastShort(json.getMsg());
                            }
                        }
                        @Override
                        public void requestError(String errorMsg, int errorType) {

                        }
                    });

                } else if("待付款".equals(orderAdapter.getData().get(position).getOrder_status())){
                    EndActivity.forward(MyOrderListActivity.this,orderAdapter.getData().get(position).getOrder_sn());
                } else if("已取消".equals(orderAdapter.getData().get(position).getOrder_status())){
                    com.luck.picture.lib.utils.ToastUtils.showToast(MyOrderListActivity.this,"无订单详情！");
                }else {
                    Intent intent = new Intent(MyOrderListActivity.this, OrderDetailActivity.class);
                    intent.putExtra("order_sn", orderAdapter.getData().get(position).getOrder_sn());
                    startActivity(intent);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pager = 1;
                refreshLayout.setEnableLoadMore(true);
                getDataNet();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pager++;
                getDataNet();
            }
        });
        orderAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_view, null));
        getDataNet();
        loading.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataNet();
            }
        });
    }

    private void getDataNet() {
        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pager + "");
        params.put("pageSize", 10 + "");
        MyHttpUtils.postHttpMessage(AppUrl.orderList, params, OrderListBean.class, new RequestCallBack<OrderListBean>() {
            @Override
            public void requestSuccess(OrderListBean json) {
                loading.showContent();
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (json.getCode() == 1) {
                    if (pager == 1) {

                        List<OrderListBean.DataBean.DataBeanX> data = json.getData().getData();
                        orderAdapter.setNewData(data);
                    } else {
                        orderAdapter.addData(json.getData().getData());
                    }
                    if (json.getData().getData() == null || json.getData().getData().size() < 10) {
                        refreshLayout.setEnableLoadMore(false);
                    }
                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                loading.showError();
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }


    @OnClick({R.id.ll_common_back, R.id.tv_right_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_right_btn:
                break;
        }
    }

    class OrderAdapter extends BaseQuickAdapter<OrderListBean.DataBean.DataBeanX, BaseViewHolder> {


        public OrderAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderListBean.DataBean.DataBeanX item) {
            helper.setText(R.id.tv_last_number, "尾号" + item.getMobile());
            helper.setText(R.id.tv_time, "" + item.getCreatetime());
            helper.setText(R.id.tv_start, "始：" + item.getStart_address());
            if (TextUtils.isEmpty(item.getEnd_address())||item.getEnd_address().contains("null")) {
                helper.setText(R.id.tv_end, "终：--");
            } else {
                helper.setText(R.id.tv_end, "终：" + item.getEnd_address());
            }
            helper.setText(R.id.tv_state, "" + item.getOrder_status());
            if ("已完成".equals(item.getOrder_status())) {
                helper.setTextColor(R.id.tv_state, getResources().getColor(R.color.order_finish));
            }
            if ("已评价".equals(item.getOrder_status())) {
                helper.setTextColor(R.id.tv_state, getResources().getColor(R.color.colorBlue));
            }
            if ("进行中".equals(item.getOrder_status())) {

                helper.setTextColor(R.id.tv_state, getResources().getColor(R.color.order_ing));
            }
            if ("已取消".equals(item.getOrder_status())) {
                helper.setTextColor(R.id.tv_state, getResources().getColor(R.color.order_cancel));

            }
            if ("待付款".equals(item.getOrder_status())) {
                helper.setTextColor(R.id.tv_state, getResources().getColor(R.color.order_cancel));

            }
        }
    }
}