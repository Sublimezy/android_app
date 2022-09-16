package com.xueyiche.zjyk.xueyiche.mine.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.main.activities.main.BaseBean;
import com.xueyiche.zjyk.xueyiche.mine.activities.YouZhengLianCheDetailActivity;
import com.xueyiche.zjyk.xueyiche.mine.bean.OrderListBean;
import com.xueyiche.zjyk.xueyiche.mine.bean.YouZhengLianCheBean;
import com.xueyiche.zjyk.xueyiche.mine.decoration.GridItemDecoration;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.ToastUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YouZhengLianCheOrderListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YouZhengLianCheOrderListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Myadapter orderAdapter;

    public YouZhengLianCheOrderListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YouZhengLianCheOrderListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YouZhengLianCheOrderListFragment newInstance(String param1, String param2) {
        YouZhengLianCheOrderListFragment fragment = new YouZhengLianCheOrderListFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_you_zheng_lian_che_order_list, null);
        ButterKnife.bind(YouZhengLianCheOrderListFragment.this, inflate);

        orderAdapter = new Myadapter(R.layout.item_youzheng_lianche_list);
//        GridItemDecoration gridItemDecoration = new GridItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//        gridItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
//        recyclerView.addItemDecoration(gridItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(orderAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pager = 1;
                getDataFromNet();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pager++;
                getDataFromNet();
            }
        });
        orderAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_view, null));
        getDataFromNet();
        orderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_view:
//                        ToastUtils.show("去详情" + position);
                        Intent intent = new Intent(getContext(), YouZhengLianCheDetailActivity.class);
                        intent.putExtra("order_sn",orderAdapter.getData().get(position).getOrder_sn());
                        startActivity(intent);
                        break;

                    case R.id.btn_cancel:

//                        ToastUtils.show("取消");
                        Map<String, String> params = new HashMap<>();
                        MyHttpUtils.postHttpMessage(AppUrl.cancelOrder_youzheng, params, BaseBean.class, new RequestCallBack<BaseBean>() {
                            @Override
                            public void requestSuccess(BaseBean json) {
                                ToastUtils.show(json.getMsg());
                                if(json.getCode() == 1){
                                    orderAdapter.getData().get(position).setOrder_status("已取消");
                                    orderAdapter.notifyItemChanged(position);
                                }
                            }

                            @Override
                            public void requestError(String errorMsg, int errorType) {
                                ToastUtils.show("服务器内部错误");
                            }
                        });
                        break;

                    case R.id.btn_contact:
//                        ToastUtils.show("联系");

                        XueYiCheUtils.CallPhone(getActivity(),"联系教练?",orderAdapter.getData().get(position).getMobile());
                        break;


                }
            }
        });
        return inflate;
    }

    int pager = 1;

    private void getDataFromNet() {

        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pager + "");
        params.put("pageSize", 10 + "");
        MyHttpUtils.postHttpMessage(AppUrl.orderList, params, YouZhengLianCheBean.class, new RequestCallBack<YouZhengLianCheBean>() {
            @Override
            public void requestSuccess(YouZhengLianCheBean json) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (json.getCode() == 1) {
                    if (pager == 1) {
                        List<YouZhengLianCheBean.DataBean.DataBeanX> data = json.getData().getData();
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
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });

    }

    class Myadapter extends BaseQuickAdapter<YouZhengLianCheBean.DataBean.DataBeanX, BaseViewHolder> {

        public Myadapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, YouZhengLianCheBean.DataBean.DataBeanX item) {

            helper.setText(R.id.tv_order_sn, "订单号: " + item.getOrder_sn());
            helper.setText(R.id.state, "" + item.getOrder_status());
            helper.setText(R.id.tv_start, "接送地点: " + item.getStart_address());
//            helper.setText(R.id.tv_end, "结束地点: " + item.getEnd_address());
            helper.setText(R.id.tv_time, "" + item.getCreatetime());

            if ("已完成".equals(item.getOrder_status())) {
                helper.setVisible(R.id.btn_cancel, false);
                helper.setVisible(R.id.view, true);
                helper.setVisible(R.id.btn_contact, true);
            } else if ("已取消".equals(item.getOrder_status())) {
                helper.setVisible(R.id.btn_cancel, false);
                helper.setVisible(R.id.btn_contact, true);
                helper.setVisible(R.id.view, true);
            } else {
                helper.setVisible(R.id.btn_cancel, true);
                helper.setVisible(R.id.btn_contact, true);
                helper.setVisible(R.id.view, true);
            }
            helper.addOnClickListener(R.id.item_view, R.id.btn_cancel, R.id.btn_contact);
        }
    }
}