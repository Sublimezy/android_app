package com.xueyiche.zjyk.xueyiche.mine.fragments.collection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.DriverShcoolBean;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.driverschool.driverschool.DriverSchoolContent;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2017/7/21.
 */
public class DriverSchoolCollection extends BaseFragment {
    private ListView lv_collection;
    private LinearLayout ll_noIndent;
    private boolean isPrepared;
    private List<DriverShcoolBean.ContentBean> content;
    private List<DriverShcoolBean.ContentBean> list = new ArrayList<>();
    private DriverSchoolCollectionAdapter shopCollectionAdapter;
    private String user_id;
    private String kind_type;
    private int pager = 1;
    private TextView tv_collection;
    private String x;
    private String y;
    private RefreshLayout refreshLayout;

    public DriverSchoolCollection(String kind_type) {
        this.kind_type = kind_type;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        getDataFromNet();
    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.collection_fragment, null);
        lv_collection = (ListView) view.findViewById(R.id.lv_collection);
        ll_noIndent = (LinearLayout) view.findViewById(R.id.ll_not_indent);
        tv_collection = (TextView) view.findViewById(R.id.tv_collection);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        if ("1".equals(kind_type)) {
            tv_collection.setText("暂无驾校收藏哦~");
        } else {
            tv_collection.setText("暂无驾校足迹哦~");
        }
        shopCollectionAdapter = new DriverSchoolCollectionAdapter(list, getContext(), R.layout.exam_driving_school_list_item);
        user_id = PrefUtils.getString(App.context, "user_id", "0");
        x = PrefUtils.getString(App.context, "x", "");
        y = PrefUtils.getString(App.context, "y", "");
        initData();
        isPrepared = true;
        lazyLoad();
        return view;
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
        //item的点击和长按点击
        onItemClick();

    }

    private void onItemClick() {
        lv_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DriverShcoolBean.ContentBean contentBean = list.get(i);
                String driver_school_id = contentBean.getDriver_school_id();
                Intent intent1 = new Intent(App.context, DriverSchoolContent.class);
                intent1.putExtra("driver_school_id", driver_school_id);
                startActivity(intent1);
            }
        });
        lv_collection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if ("1".equals(kind_type)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.mipmap.logo);
                    builder.setTitle("温馨提示！");
                    builder.setMessage("是否取消收藏？");
                    //点击空白处弹框不消失
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DriverShcoolBean.ContentBean contentBean = list.get(i);
                            String driver_school_id = contentBean.getDriver_school_id();
                            OkHttpUtils.post().url(AppUrl.Collection_Delete)
                                    .addParams("user_id", user_id)
                                    .addParams("refer_id", driver_school_id)
                                    .addParams("kind_type", "1")
                                    .build().execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                        int code = successDisCoverBackBean.getCode();
                                        if (200 == code) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    getDataFromNet();
                                                    showToastShort("取消收藏");
                                                }
                                            });
                                        }
                                    }
                                    return string;
                                }

                                @Override
                                public void onError(Request request, Exception e) {

                                }

                                @Override
                                public void onResponse(Object response) {

                                }
                            });
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromNet();
    }

    public void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            showProgressDialog(getActivity(), false);
            OkHttpUtils.post().url(AppUrl.Collection_List_All)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .addParams("kind_type", kind_type)
                    .addParams("mark_type", "2")
                    .addParams("pager", pager + "")
                    .addParams("longitude_user", x)
                    .addParams("latitude_user", y)
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

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            showProgressDialog(getActivity(), false);
            OkHttpUtils.post().url(AppUrl.Collection_List_All)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .addParams("kind_type", kind_type)
                    .addParams("mark_type", "2")
                    .addParams("pager", pager + "")
                    .addParams("longitude_user", x)
                    .addParams("latitude_user", y)
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

    private void processData(String string, final boolean isMore) {
        final DriverShcoolBean driverShcoolBean = JsonUtil.parseJsonToBean(string, DriverShcoolBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (driverShcoolBean != null) {
                    if (!isMore) {
                        if (list.size() != 0) {
                            list.clear();
                        }
                        List<DriverShcoolBean.ContentBean> contentBeen = driverShcoolBean.getContent();
                        if (contentBeen != null && contentBeen.size() != 0) {
                            list.addAll(contentBeen);
                            lv_collection.setAdapter(shopCollectionAdapter);
                            ll_noIndent.setVisibility(View.GONE);
                        } else {
                            ll_noIndent.setVisibility(View.VISIBLE);
                        }
                        shopCollectionAdapter.notifyDataSetChanged();
                    } else {
                        content = driverShcoolBean.getContent();
                        if (content != null) {
                            list.addAll(content);//追加更多数据
                            shopCollectionAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }


    public class DriverSchoolCollectionAdapter extends BaseCommonAdapter {
        public DriverSchoolCollectionAdapter(List<DriverShcoolBean.ContentBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            DriverShcoolBean.ContentBean contentBean = (DriverShcoolBean.ContentBean) item;
            String driver_school_name = contentBean.getDriver_school_name();
            String driver_school_money = contentBean.getDriver_school_money();
            String min_school_price = contentBean.getMin_school_price();
            String driver_school_place = contentBean.getDriver_school_place();
            String return_money = contentBean.getReturn_money();
            String wecsf = contentBean.getWecsf();
            String driver_school_url = contentBean.getDriver_school_url();
            String distance = contentBean.getDistance();
            if (!TextUtils.isEmpty(driver_school_name)) {
                viewHolder.setText(R.id.tv_school_name, driver_school_name);
            }
            if (!TextUtils.isEmpty(return_money)) {
                viewHolder.setText(R.id.tv_bq_one, return_money);
            }
            if (!TextUtils.isEmpty(driver_school_name)) {
                viewHolder.setText(R.id.tv_bq_two, wecsf);
            }
            if (!TextUtils.isEmpty(distance)) {
                viewHolder.setText(R.id.tv_shop_address_item, distance);
            }
            if (!TextUtils.isEmpty(driver_school_place)) {
                viewHolder.setText(R.id.tv_school_address, driver_school_place);
            }
            if (!TextUtils.isEmpty(min_school_price)) {
                viewHolder.setText(R.id.tv_school_money,"¥"+ min_school_price);
            }
            if (!TextUtils.isEmpty(driver_school_url)) {
                viewHolder.setPic(R.id.iv_school_head, driver_school_url);
            }
        }
    }
}
