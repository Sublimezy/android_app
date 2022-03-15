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
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.UsedCarCollectBean;
import com.xueyiche.zjyk.xueyiche.usedcar.activity.UsedCarContentActivity;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarSuccessBean;
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
public class UsedCarCollection extends BaseFragment {
    private ListView lv_collection;
    private LinearLayout ll_noIndent;
    private boolean isPrepared;
    private List<UsedCarCollectBean.ContentBean> content;
    private List<UsedCarCollectBean.ContentBean> list = new ArrayList<>();
    private UsedCarCollectionAdapter shopCollectionAdapter;
    private String user_id;
    private String kind_type;
    private TextView tv_collection;
    private ImageView iv_collection_logo;
    private int pager = 1;
    private String x;
    private String y;
    private RefreshLayout refreshLayout;

    public UsedCarCollection(String kind_type) {
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
        iv_collection_logo.setImageResource(R.mipmap.collection_shop);
        if ("1".equals(kind_type)) {
            tv_collection.setText("暂无二手车收藏哦~");
        } else {
            tv_collection.setText("暂无二手车足迹哦~");
        }
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        shopCollectionAdapter = new UsedCarCollectionAdapter(list, App.context, R.layout.used_car_list_item);
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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(App.context, UsedCarContentActivity.class);
                UsedCarCollectBean.ContentBean contentBean = list.get(i);
                if (contentBean != null) {
                    String id = contentBean.getId();
                    String htmlUrl = contentBean.getHtmlUrl();
                    intent1.putExtra("carsource_id", id);
                    intent1.putExtra("htmlUrl",htmlUrl);
                    intent1.putExtra("cartype", "buy");
                    intent1.putExtra("qu_time_date", "");
                    intent1.putExtra("huan_time_date", "");
                    intent1.putExtra("store_id_usedcar", "");
                    intent1.putExtra("duration", "");
                    intent1.putExtra("qu_city_usedcar", "");
                    intent1.putExtra("huan_city_usedcar", "");
                    intent1.putExtra("shangMenQu", "");
                    intent1.putExtra("shangMenHuan", "");
                    intent1.putExtra("qu_latitude_usedcar", "");
                    intent1.putExtra("qu_longitude_usedcar", "");
                    intent1.putExtra("qu_name_usedcar", "");
                    intent1.putExtra("huan_latitude_usedcar", "");
                    intent1.putExtra("huan_longitude_usedcar", "");
                    intent1.putExtra("huan_name_usedcar", "");
                    startActivity(intent1);
                }
            }
        });
        lv_collection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final UsedCarCollectBean.ContentBean contentBean = list.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.mipmap.logo);
                builder.setTitle("温馨提示！");
                builder.setMessage("是否取消收藏？");
                //点击空白处弹框不消失
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (contentBean != null) {
                            String id = contentBean.getId();
                            OkHttpUtils.post().url(AppUrl.Used_Car_Collect)
                                    .addParams("device_id", LoginUtils.getId(getActivity()))
                                    .addParams("carsource_id", id)
                                    .addParams("user_id", user_id)
                                    .build().execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    UsedCarSuccessBean usedCarSuccessBean = JsonUtil.parseJsonToBean(string, UsedCarSuccessBean.class);
                                    final String message = usedCarSuccessBean.getMessage();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            getDataFromNet();
                                            showToastShort(message);
                                        }
                                    });

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
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
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
                    .addParams("mark_type", "3")
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
                    .addParams("mark_type", "3")
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
        final UsedCarCollectBean usedCarCollectBean = JsonUtil.parseJsonToBean(string, UsedCarCollectBean.class);

        if (!isMore) {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    if (list.size() != 0) {
                        list.clear();
                    }
                    if (usedCarCollectBean != null) {
                        List<UsedCarCollectBean.ContentBean> content = usedCarCollectBean.getContent();
                        if (content != null && content.size() != 0) {
                            list.addAll(content);
                            lv_collection.setAdapter(shopCollectionAdapter);
                            ll_noIndent.setVisibility(View.GONE);
                        } else {
                            ll_noIndent.setVisibility(View.VISIBLE);
                        }
                        shopCollectionAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            //刷新listview
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    //加载更多
                    content = usedCarCollectBean.getContent();
                    if (content != null) {
                        list.addAll(content);//追加更多数据
                        shopCollectionAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }

    private class UsedCarCollectionAdapter extends BaseCommonAdapter {
        public UsedCarCollectionAdapter(List<UsedCarCollectBean.ContentBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            UsedCarCollectBean.ContentBean similarBean = (UsedCarCollectBean.ContentBean) item;
            String just_side_img = similarBean.getJust_side_img();
            String status = similarBean.getStatus();
            String car_allname = similarBean.getCar_allname();
            //月供
            String loan_month = similarBean.getLoan_month();
            //首付
            String loan_first = similarBean.getLoan_first();
            //上牌时间
            String last_time = similarBean.getLast_time();
            //公里数
            String mileage = similarBean.getMileage();
            //售价
            String car_price = similarBean.getCar_price();
            //新车售价
            String new_car_price = similarBean.getNew_car_price();
            //平台租车价
            String rent_price = similarBean.getRent_price();
            //新车租车价
            String new_rent_price = similarBean.getNew_rent_price();
            //车辆是否租
            String rent_status = similarBean.getRent_status();
            viewHolder.setPic(R.id.iv_used_car_photo, just_side_img);
            if ("1".equals(status)) {
                //出租中
                viewHolder.changeImageVisible(R.id.iv_used_car_state);
            } else {
                viewHolder.changeImageGone(R.id.iv_used_car_state);
            }
            viewHolder.setText(R.id.tv_usedcar_title, car_allname);
            viewHolder.setText(R.id.tv_money_buy_loan, "首付" + loan_first + "万  月供" + loan_month + "元");
            viewHolder.setText(R.id.tv_shangpan_gongli, last_time + "/" + mileage + "公里");
            viewHolder.setText(R.id.tv_car_price, car_price + "万");
            viewHolder.setText(R.id.tv_new_car_price, "新车价" + new_car_price + "万");
            viewHolder.setText(R.id.tv_rent_price, rent_price + "/天");
            viewHolder.setText(R.id.tv_new_rent_price, "新车价" + new_rent_price + "/天");
            if ("0".equals(rent_status)) {
                viewHolder.changeLinearLayoutInVisible(R.id.ll_rent_content);
            } else if ("1".equals(rent_status)) {
                viewHolder.changeLinearLayoutVisible(R.id.ll_rent_content);
            }
        }
    }
}
