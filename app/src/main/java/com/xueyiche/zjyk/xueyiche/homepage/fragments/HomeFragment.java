package com.xueyiche.zjyk.xueyiche.homepage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.umeng.analytics.MobclickAgent;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.CommonWebView;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.ArrivedActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.homepage.activities.DaiJianCheListActivity;
import com.xueyiche.zjyk.xueyiche.homepage.activities.DaifBaoYangListActivity;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.HomeListAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.ShouYeBannerAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ShouYeBannerBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ShouYeHotBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.UserOrderDetailsBean;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.decoration.GridItemDecoration;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.PracticeCarActivity;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
public class HomeFragment extends BaseFragment {
    @BindView(R.id.banner_view)
    BannerViewPager mViewPager;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.rl_home_title)
    RelativeLayout rlHomeTitle;
    private HomeListAdapter homeListAdapter;
    private List<ShouYeBannerBean.DataBean> data;

    public static HomeFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        bundle.putString("home", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.home_page_fragment, null);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {

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
                getBanner();
            }
        });
//        List<String> imageurls = new ArrayList<String>();
//        imageurls.add("https://img0.baidu.com/it/u=3821463873,4211379788&fm=253&fmt=auto&app=120&f=JPEG?w=889&h=500");
//        imageurls.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1115%2F0ZR1095111%2F210ZP95111-10-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651820583&t=30c6005ca94db6092b4151c884736e46");
        mViewPager.setIndicatorSliderGap(BannerUtils.dp2px(6));
        mViewPager.setScrollDuration(1500);
        mViewPager.setIndicatorStyle(IndicatorStyle.ROUND_RECT);
        mViewPager.setIndicatorSliderGap(BannerUtils.dp2px(4));
        mViewPager.setIndicatorSlideMode(IndicatorSlideMode.SCALE);
        mViewPager.setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.seven));
        mViewPager.setIndicatorSliderColor(getResources().getColor(R.color.colorLine), getResources().getColor(R.color.colorOrange));
        mViewPager.setIndicatorSliderWidth(getResources().getDimensionPixelOffset(R.dimen.seven), getResources().getDimensionPixelOffset(R.dimen.dp_15));
        mViewPager.setIndicatorMargin(0, 0, 0, BannerUtils.dp2px(15));
        mViewPager.setLifecycleRegistry(getLifecycle());
        mViewPager.setIndicatorGravity(IndicatorGravity.CENTER);
        mViewPager.setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(View clickedView, int position) {
//                List<String> data = mViewPager.getData();
//                showToastShort(data.get(position)+"");
                ShouYeBannerBean.DataBean dataBean = data.get(position);
                if ("1".equals(dataBean.getUrl_type())) {
                    Intent intent = new Intent(getContext(), CommonWebView.class);
                    intent.putExtra("weburl", "wangye");
                    intent.putExtra("httpUrl", dataBean.getUrl());
                    startActivity(intent);
                }
            }
        });
        mViewPager.setAdapter(new ShouYeBannerAdapter());

        GridItemDecoration gridItemDecoration = new GridItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        gridItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(gridItemDecoration);
        homeListAdapter = new HomeListAdapter(R.layout.item_home_5_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(homeListAdapter);
//        homeListAdapter.setNewData(imageurls);
        getBanner();
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
//        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////                LogUtil.i("首页滑动", "scrollX==" + scrollX + "  scrollY == " + scrollY + "   oldScrollY==" + oldScrollY);
//                if (scrollY > 100) {
////                    changeTop(true);
////                    rlHomeTitle.setBackgroundColor(getResources().getColor(R.color.white));
//                } else {
////                    rlHomeTitle.setBackgroundColor(getResources().getColor(R.color.transparent));
////                    changeTop(false);
//                }
//            }
//        });
    }

    int pager = 1;

    //首页热门信息
    private void getHot() {
        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pager + "");
        MyHttpUtils.postHttpMessage(AppUrl.topswitch, params, ShouYeHotBean.class, new RequestCallBack<ShouYeHotBean>() {
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

    //首页轮播图
    public void getBanner() {
        Map<String, String> params = new HashMap<>();

        MyHttpUtils.postHttpMessage(AppUrl.banner, params, ShouYeBannerBean.class, new RequestCallBack<ShouYeBannerBean>() {
            @Override
            public void requestSuccess(ShouYeBannerBean json) {
                data = json.getData();

                List<String> imageurls = new ArrayList<String>();
                if (data != null) {
                    for (ShouYeBannerBean.DataBean dataBean : data) {
                        String address = dataBean.getMini_image();
                        if (!TextUtils.isEmpty(address)) {
                            imageurls.add(address);
                        }
                    }
                    mViewPager.create(imageurls);
                }

            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    @Override
    protected Object setLoadDate() {
        return "home";
    }

    @OnClick({R.id.ll_one, R.id.ll_two, R.id.ll_three, R.id.ll_four, R.id.iv_kefu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_one:
                boolean b = XueYiCheUtils.IsLogin();
                Log.e("IsLogin", "" + b);
                if (XueYiCheUtils.IsLogin()) {
                    goDaiJiao();
                } else {
                    LoginFirstStepActivity.forward(getActivity());
                }
                break;
            case R.id.ll_two:
                PracticeCarActivity.forward(getActivity());
                break;
            case R.id.ll_three:
                DaiJianCheListActivity.forward(getActivity());
                break;
            case R.id.ll_four:
                DaifBaoYangListActivity.forward(getActivity());
                break;
            case R.id.iv_kefu:
                XueYiCheUtils.CallPhone(getActivity(), "拨打客服电话", "0451-58627471");
                MobclickAgent.onEvent(getContext(), "kefu_phone");
                break;
        }
    }

    private void goDaiJiao() {
        showProgressDialog(getActivity(), false);
        Map<String, String> map = new HashMap<>();
        MyHttpUtils.postHttpMessage(AppUrl.userOrderDetails, map, UserOrderDetailsBean.class, new RequestCallBack<UserOrderDetailsBean>() {
            @Override
            public void requestSuccess(UserOrderDetailsBean json) {
                if (1 == json.getCode()) {
                    UserOrderDetailsBean.DataBean data = json.getData();
                    if (data != null) {
                        int order_status = data.getOrder_status();
                        String order_sn = data.getOrder_sn();
                        switch (order_status) {
                            case 0:
                                WaitActivity.forward(getActivity(), order_sn);
                                break;
                            case 1:
                                JieDanActivity.forward(getActivity(), order_sn);
                                break;
                            case 2:
                                ArrivedActivity.forward(getActivity(), order_sn);
                                break;
                            case 3:
                                JinXingActivity.forward(getActivity(), order_sn);
                                break;
                            case 4:
                                EndActivity.forward(getActivity(), order_sn);
                                break;
                            default:
                                DaiJiaActivity.forward(getActivity());
                                break;
                        }
                    } else {
                        DaiJiaActivity.forward(getActivity());
                    }


                }
                stopProgressDialog();
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                DaiJiaActivity.forward(getActivity());
                stopProgressDialog();
            }
        });
    }
}
