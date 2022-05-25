package com.xueyiche.zjyk.xueyiche.homepage.activities;

import android.content.Context;
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
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.activity.ArrivedActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.IndentContentBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.DaiJianCheBean;
import com.xueyiche.zjyk.xueyiche.mine.activities.OrderDetailActivity;
import com.xueyiche.zjyk.xueyiche.mine.bean.OrderListBean;
import com.xueyiche.zjyk.xueyiche.mine.decoration.GridItemDecoration;
import com.xueyiche.zjyk.xueyiche.mine.view.LoadingLayout;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaiJianCheListActivity extends BaseActivity {


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
        tvTitle.setText("代检车");
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        loading = LoadingLayout.wrap(refreshLayout);
        loading.showLoading();
    }
    public static void forward(Context context) {
        Intent intent = new Intent(context, DaiJianCheListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        orderAdapter = new OrderAdapter(R.layout.item_daijianche_layout);
        GridItemDecoration gridItemDecoration = new GridItemDecoration(this, DividerItemDecoration.VERTICAL);
        gridItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(gridItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DaiJianCheBean.DataBean.DataBean1 dataBean = (DaiJianCheBean.DataBean.DataBean1) adapter.getItem(position);
                if (dataBean != null) {
                    int id = dataBean.getId();
                    ContentActivity.forward(DaiJianCheListActivity.this, "" + id);
                }
            }
        });
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
        new GDLocation().startLocation();
        String lat = PrefUtils.getParameter("lat");
        String lon = PrefUtils.getParameter("lon");
        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pager + "");
        params.put("pageSize", 10 + "");
        params.put("coordinate", lon+","+lat);
        MyHttpUtils.postHttpMessage(AppUrl.validate_car, params, DaiJianCheBean.class, new RequestCallBack<DaiJianCheBean>() {
            @Override
            public void requestSuccess(DaiJianCheBean json) {
                loading.showContent();
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (json.getCode() == 1) {
                    if (pager == 1) {

                        List<DaiJianCheBean.DataBean.DataBean1> data = json.getData().getData();
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

    class OrderAdapter extends BaseQuickAdapter<DaiJianCheBean.DataBean.DataBean1, BaseViewHolder> {


        public OrderAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, DaiJianCheBean.DataBean.DataBean1 item) {
            CustomShapeImageView iv_head = helper.getView(R.id.iv_head);
            TextView tv_name = helper.getView(R.id.tv_name);
            TextView tvLocation = helper.getView(R.id.tvLocation);
            TextView tvDiatance = helper.getView(R.id.tvDiatance);
            Picasso.with(App.context).load(""+item.getImage()).into(iv_head);
            tv_name.setText(item.getTitle());
            tvLocation.setText("地址："+item.getAddress());
            tvDiatance.setText(item.getDistance()+"km");
        }
    }
}