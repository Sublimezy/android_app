package com.xueyiche.zjyk.xueyiche.mine.fragments.collection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.JiaoLianInfo;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.ChoiceCardDetails;
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
public class DriverCollection extends BaseFragment {
    private ListView lv_collection;
    private LinearLayout ll_noIndent;
    private boolean isPrepared;
    private List<JiaoLianInfo.ContentBean> content;
    private List<JiaoLianInfo.ContentBean> list = new ArrayList<>();
    private DriverCollectionAdapter shopCollectionAdapter;
    private String user_id;
    private String kind_type;
    private TextView tv_collection;
    private ImageView iv_collection_logo;
    private RefreshLayout refreshLayout;
    private int pager = 1;
    private String x;
    private String y;

    public DriverCollection(String kind_type) {
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
        iv_collection_logo = (ImageView) view.findViewById(R.id.iv_collection_logo);
        iv_collection_logo.setImageResource(R.mipmap.collection_driver);
        if ("1".equals(kind_type)) {
            tv_collection.setText("暂无教练收藏哦~");
        } else {
            tv_collection.setText("暂无教练足迹哦~");
        }
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        shopCollectionAdapter = new DriverCollectionAdapter(list, getContext(), R.layout.practice_item);
        user_id = PrefUtils.getString(App.context, "user_id", "0");
        x = PrefUtils.getString(App.context, "x", "");
        y = PrefUtils.getString(App.context, "y", "");
        getDataFromNet();
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


        lv_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String driver_id = list.get(position).getDriver_id();
                Intent intent = new Intent(App.context, ChoiceCardDetails.class);
                intent.putExtra("driver_id", driver_id);
                startActivity(intent);
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
                            final String driver_id = list.get(i).getDriver_id();

                            OkHttpUtils.post().url(AppUrl.Collection_Delete)
                                    .addParams("user_id", user_id)
                                    .addParams("refer_id", driver_id)
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
                    .addParams("mark_type", "0")
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
                    .addParams("mark_type", "0")
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
        final JiaoLianInfo jiaoLianInfo = JsonUtil.parseJsonToBean(string, JiaoLianInfo.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (jiaoLianInfo != null) {
                    if (!isMore) {
                        if (list.size() != 0) {
                            list.clear();
                        }
                        List<JiaoLianInfo.ContentBean> contentBeen = jiaoLianInfo.getContent();
                        if (contentBeen != null && contentBeen.size() != 0) {
                            list.addAll(contentBeen);
                            lv_collection.setAdapter(shopCollectionAdapter);
                            ll_noIndent.setVisibility(View.GONE);
                        } else {
                            ll_noIndent.setVisibility(View.VISIBLE);
                        }
                        shopCollectionAdapter.notifyDataSetChanged();
                    } else {
                        content = jiaoLianInfo.getContent();
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


    public class DriverCollectionAdapter extends BaseCommonAdapter {
        public DriverCollectionAdapter(List<JiaoLianInfo.ContentBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            JiaoLianInfo.ContentBean contentBean = (JiaoLianInfo.ContentBean) item;
            String driver_name = contentBean.getDriver_name();
            if (!TextUtils.isEmpty(driver_name)) {
                viewHolder.setText(R.id.tv_drivers_name, driver_name);
            }
            if (!TextUtils.isEmpty(contentBean.getHead_img())) {
                viewHolder.setPicHead(R.id.iv_drivers_head, contentBean.getHead_img());
            }
            //车辆
            if (!TextUtils.isEmpty(contentBean.getHead_img())) {
                viewHolder.setPic(R.id.iv_drivers_car_photo, contentBean.getHead_img());
            }
        }
    }
}
