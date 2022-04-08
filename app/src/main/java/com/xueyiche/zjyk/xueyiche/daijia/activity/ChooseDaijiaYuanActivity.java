package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.bean.DrivingListBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseDaijiaYuanActivity extends BaseActivity {


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
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ItemAdapter itemAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_choose_daijia_yuan;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        itemAdapter = new ItemAdapter(R.layout.item_daijia_yuan);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pager = 1;
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
        getDataNet();
    }

    int pager = 1;

    public void getDataNet() {
        Map<String, String> params = new HashMap<>();
        params.put("user_lng", PrefUtils.getParameter("lon"));
        params.put("user_lat", PrefUtils.getParameter("lat"));
        params.put("search", etSearch.getText().toString().trim());
        params.put("pageNumber", pager + "");
        MyHttpUtils.postHttpMessage(AppUrl.drivingList, params, DrivingListBean.class, new RequestCallBack<DrivingListBean>() {
            @Override
            public void requestSuccess(DrivingListBean json) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (json.getCode() == 1) {
                    if (pager == 1) {
                        itemAdapter.setNewData(json.getData().getRows());
                    } else {
                        itemAdapter.addData(json.getData().getRows());
                    }

                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }


    @OnClick({R.id.ll_common_back, R.id.iv_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.iv_scan:
                // 扫一扫
                break;
        }
    }

    class ItemAdapter extends BaseQuickAdapter<DrivingListBean.DataBean.RowsBean, BaseViewHolder> {


        public ItemAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, DrivingListBean.DataBean.RowsBean item) {
            helper.setText(R.id.tv_name, ""+item.getName());
            helper.setText(R.id.tv_bianhao, ""+item.getUser_number());
            helper.setText(R.id.tv_location, "距您"+item.getJuli()+"km");
            CircleImageView view = helper.getView(R.id.civ_head);
            Glide.with(ChooseDaijiaYuanActivity.this).load(item.getHead_img()).into(view);
            CheckBox checkBox = helper.getView(R.id.check_box);
            String driving_id = item.getDriving_id();//司机id


        }
    }
}