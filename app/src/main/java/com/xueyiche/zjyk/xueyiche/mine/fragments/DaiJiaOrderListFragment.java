package com.xueyiche.zjyk.xueyiche.mine.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.utils.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.activity.ArrivedActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.IndentContentBean;
import com.xueyiche.zjyk.xueyiche.mine.activities.OrderDetailActivity;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DaiJiaOrderListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaiJiaOrderListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DaiJiaOrderListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DaiJiaOrderListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DaiJiaOrderListFragment newInstance(String param1, String param2) {
        DaiJiaOrderListFragment fragment = new DaiJiaOrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    private OrderAdapter orderAdapter;
    private LoadingLayout loading;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dai_jia_order_list, null);
        ButterKnife.bind(DaiJiaOrderListFragment.this,inflate);
        loading = LoadingLayout.wrap(refreshLayout);
        loading.showLoading();

        orderAdapter = new OrderAdapter(R.layout.item_oeder_list);
        GridItemDecoration gridItemDecoration = new GridItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        gridItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(gridItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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

        return inflate;
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
                                        WaitActivity.forward(getContext(), order_sn);
                                    }
                                } else if ("1".equals(order_status)) {
                                    if (!TextUtils.isEmpty(order_sn)) {
                                        JieDanActivity.forward(getContext(), order_sn);
                                    }
                                } else if ("2".equals(order_status)) {
                                    if (!TextUtils.isEmpty(order_sn)) {
                                        ArrivedActivity.forward(getContext(), order_sn);
                                    }

                                } else if ("2".equals(order_status)) {
                                    if (!TextUtils.isEmpty(order_sn)) {
                                        ArrivedActivity.forward(getContext(), order_sn);
                                    }

                                } else if ("3".equals(order_status)) {
                                    if (!TextUtils.isEmpty(order_sn)) {
                                        JinXingActivity.forward(getContext(), order_sn);
                                    }

                                }
                            } else {
                                com.hjq.toast.ToastUtils.show(json.getMsg());
                            }
                        }

                        @Override
                        public void requestError(String errorMsg, int errorType) {

                        }
                    });

                } else if ("待付款".equals(orderAdapter.getData().get(position).getOrder_status())) {
                    String bd_type = orderAdapter.getData().get(position).getBd_type();
                    if (!TextUtils.isEmpty(bd_type)) {
                        if ("1".equals(bd_type)) {
                            Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                            intent.putExtra("order_sn", orderAdapter.getData().get(position).getOrder_sn());
                            startActivity(intent);
                        } else {
                            EndActivity.forward(getContext(), orderAdapter.getData().get(position).getOrder_sn());
                        }
                    }
                } else if ("已取消".equals(orderAdapter.getData().get(position).getOrder_status())) {
                    ToastUtils.showToast(getContext(), "无订单详情！");
                } else {
                    Intent intent = new Intent(getContext(), OrderDetailActivity.class);
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
                    com.hjq.toast.ToastUtils.show(json.getMsg());
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


    class OrderAdapter extends BaseQuickAdapter<OrderListBean.DataBean.DataBeanX, BaseViewHolder> {


        public OrderAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderListBean.DataBean.DataBeanX item) {
            helper.setText(R.id.tv_last_number, "尾号" + item.getMobile());
            helper.setText(R.id.tv_order_sn, "" + item.getCreatetime());
            helper.setText(R.id.tv_start, "始：" + item.getStart_address());
            if (TextUtils.isEmpty(item.getEnd_address()) || item.getEnd_address().contains("null")) {
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