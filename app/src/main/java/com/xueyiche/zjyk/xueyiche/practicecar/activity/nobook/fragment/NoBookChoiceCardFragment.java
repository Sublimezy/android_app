package com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.activity.NoBookChoiceCardDetails;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.bean.NoBookChooseCardBean;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Owner on 2016/11/1.
 */
public class NoBookChoiceCardFragment extends BaseFragment implements  NetBroadcastReceiver.netEventHandler {
    //选教练列表
    private ListView lv_choice_jiaolian;
    private LinearLayout ll_youzheng_empty;
    private int pager = 1;
    private List<NoBookChooseCardBean.ContentBean> content;
    private List<NoBookChooseCardBean.ContentBean> list = new ArrayList<>();
    private ChoiceJiaoLianAdapter choiceJiaoLianAdapter;
    private boolean isPrepared = true;
    private RefreshLayout refreshLayout;
    private RelativeLayout rl_title;
    private RadioGroup rg_practice;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        getDataFromNet();
    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.practice_choice_card_activity, null);
        choiceJiaoLianAdapter = new ChoiceJiaoLianAdapter(list, App.context, R.layout.practice_item);
        rl_title = (RelativeLayout) view.findViewById(R.id.title);
        rl_title.setVisibility(View.GONE);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        rg_practice = (RadioGroup) view.findViewById(R.id.rg_practice);
        rg_practice.setVisibility(View.GONE);
        ll_youzheng_empty = (LinearLayout) view.findViewById(R.id.ll_youzheng_empty);
        lv_choice_jiaolian = (ListView) view.findViewById(R.id.lv_choice_jiaolian);
        EventBus.getDefault().register(this);
        NetBroadcastReceiver.mListeners.add(this);
        initData();
        getDataFromNet();


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(MyEvent event) {

        String msg = event.getMsg();
        String data = event.getData();
        if (!TextUtils.isEmpty(data)) {
        }

        if (TextUtils.equals("刷新WZChoiceCardFragment", msg)) {
            getDataFromNet();
        }

    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }
    private void initData() {
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
                                                                    pager = pager + 1;
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
        //进入教练详情页面
        lv_choice_jiaolian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (list != null) {
                    if (XueYiCheUtils.IsHaveInternet(getActivity())) {
                        String driver_id = list.get(position).getDriver_id();
                        Intent intent = new Intent(App.context, NoBookChoiceCardDetails.class);
                        intent.putExtra("driver_id", driver_id);
                        startActivity(intent);
                    } else {
                        showToastShort(StringConstants.CHECK_NET);
                    }

                }
            }
        });


    }


    private void processData(String string, final boolean isMore) {
        final NoBookChooseCardBean jiaoLianInfo = JsonUtil.parseJsonToBean(string, NoBookChooseCardBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (jiaoLianInfo != null) {
                    if (!isMore) {
                        if (list.size() != 0) {
                            list.clear();
                        }
                        List<NoBookChooseCardBean.ContentBean> contentBeen = jiaoLianInfo.getContent();
                        if (contentBeen != null && contentBeen.size() != 0) {
                            list.addAll(contentBeen);
                            ll_youzheng_empty.setVisibility(View.GONE);
                            lv_choice_jiaolian.setAdapter(choiceJiaoLianAdapter);
                            choiceJiaoLianAdapter.notifyDataSetChanged();
                        } else {
                            choiceJiaoLianAdapter.notifyDataSetChanged();
                            ll_youzheng_empty.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //加载更多
                        content = jiaoLianInfo.getContent();
                        if (content != null) {
                            list.addAll(content);//追加更多数据
                            choiceJiaoLianAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }
        });
    }



    // 请求网络数据
    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(getActivity())) {
            showProgressDialog(getActivity(), false);
            OkHttpUtils.post().url(AppUrl.WZ_Practice_List)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .addParams("pager", pager + "")
                    .addParams("search_name", "")
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


    public void getMoreDataFromNet() {
        String s = TextUtils.isEmpty(LoginUtils.getId(getActivity())) ? "" : LoginUtils.getId(getActivity());
        if (XueYiCheUtils.IsHaveInternet(getActivity())) {
            showProgressDialog(getActivity(), false);
            OkHttpUtils.post().url(AppUrl.WZ_Practice_List)
                    .addParams("device_id", s)
                    .addParams("pager", pager + "")
                    .addParams("search_name", "")
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


    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        } else {
            getDataFromNet();
        }
    }



    public class ChoiceJiaoLianAdapter extends BaseCommonAdapter {
        public ChoiceJiaoLianAdapter(List<NoBookChooseCardBean.ContentBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            NoBookChooseCardBean.ContentBean contentBean = (NoBookChooseCardBean.ContentBean) item;
            String driver_name = contentBean.getDriver_name();
            String head_img = contentBean.getHead_img();
            String order_count = contentBean.getOrder_count();
            String driver_school_name = contentBean.getDriver_school_name();
            //教练名字
            if (!TextUtils.isEmpty(driver_name)) {
                viewHolder.setText(R.id.tv_drivers_name, driver_name);
            }
            //车辆描述
            if (!TextUtils.isEmpty(driver_school_name)) {
                viewHolder.setText(R.id.tv_drivers_situation,driver_school_name+ "   科目二   科目三");
            }
            //教练的钱
                viewHolder.setText(R.id.tv_drivers_money, "100");
            //头像
            if (!TextUtils.isEmpty(head_img)) {
                viewHolder.setPicHead(R.id.iv_drivers_head, head_img);
            }
            //车
            if (!TextUtils.isEmpty(head_img)) {
                viewHolder.setPic(R.id.iv_drivers_car_photo, head_img);
            }
        }
    }

}
