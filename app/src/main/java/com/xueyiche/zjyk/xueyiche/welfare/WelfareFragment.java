package com.xueyiche.zjyk.xueyiche.welfare;

import android.content.Intent;
import android.os.Bundle;
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
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.CommonWebView;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.HomeListAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ShouYeHotBean;
import com.xueyiche.zjyk.xueyiche.mine.decoration.GridItemDecoration;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 2022/4/6/10:07 上午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage
 * #            xueyiche5.0
 */
public class WelfareFragment extends BaseFragment {
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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private HomeListAdapter homeListAdapter;

    public static WelfareFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        WelfareFragment fragment = new WelfareFragment();
        bundle.putString("welfare", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.fragment_welfare, null);
        ButterKnife.bind(this, view);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("福利");
        initData();
        return view;
    }

    private void initData() {
        llCommonBack.setVisibility(View.GONE);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pager++;
                getHot();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.setEnableLoadMore(true);
                pager = 1;
                getHot();
            }
        });

        GridItemDecoration gridItemDecoration = new GridItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        gridItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(gridItemDecoration);
        homeListAdapter = new HomeListAdapter(R.layout.item_home_5_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(homeListAdapter);
//        homeListAdapter.setNewData(imageurls);
        getHot();
        homeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShouYeHotBean.DataBean.DataBeanX dataBeanX = homeListAdapter.getData().get(position);
                Intent intent = new Intent(getContext(), CommonWebView.class);
                intent.putExtra("weburl", "wangye");
                intent.putExtra("httpUrl", dataBeanX.getNew_url());
                startActivity(intent);
            }
        });
    }

    int pager = 1;

    //首页热门信息
    private void getHot() {
        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pager + "");
        MyHttpUtils.postHttpMessage(AppUrl.articlewelfare, params, ShouYeHotBean.class, new RequestCallBack<ShouYeHotBean>() {
            @Override
            public void requestSuccess(ShouYeHotBean json) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                List<ShouYeHotBean.DataBean.DataBeanX> data = json.getData().getData();
                if (data == null || data.size() == 0) {

                } else {
                    if (pager == 1) {
                        homeListAdapter.setNewData(data);
                    } else {
                        homeListAdapter.addData(data);
                    }

                    if (data.size() < 10) {
                        mRefreshLayout.setEnableLoadMore(false);
                    }

                }

            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    protected Object setLoadDate() {
        return "welfare";
    }
}
