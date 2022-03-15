package com.xueyiche.zjyk.xueyiche.mine.fragments.collection;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.carlife.CarLifeContentActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.bean.CarLifeGoodsBean;
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
public class ShopCollection extends BaseFragment {
    private GridView lv_collection;
    private LinearLayout ll_noIndent;
    private boolean isPrepared;
    private List<CarLifeGoodsBean.ContentBean> content;
    private List<CarLifeGoodsBean.ContentBean> list = new ArrayList<>();
    private ShopCollectionAdapter shopCollectionAdapter;
    private String user_id;
    private String kind_type;
    private TextView tv_collection;
    private ImageView iv_collection_logo;
    private int pager = 1;
    private String x;
    private String y;
    private RefreshLayout refreshLayout;

    public ShopCollection(String kind_type) {
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
        View view = LayoutInflater.from(App.context).inflate(R.layout.collection_carlive_fragment, null);
        lv_collection = (GridView) view.findViewById(R.id.lv_collection);
        ll_noIndent = (LinearLayout) view.findViewById(R.id.ll_not_indent);
        tv_collection = (TextView) view.findViewById(R.id.tv_collection);
        iv_collection_logo = (ImageView) view.findViewById(R.id.iv_collection_logo);
        iv_collection_logo.setImageResource(R.mipmap.collection_shop);
        if ("1".equals(kind_type)) {
            tv_collection.setText("暂无商铺收藏哦~");
        } else {
            tv_collection.setText("暂无商铺足迹哦~");
        }

        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        shopCollectionAdapter = new ShopCollectionAdapter();
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
                    .addParams("mark_type", "1")
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
                    .addParams("mark_type", "1")
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
        final CarLifeGoodsBean dingDan = JsonUtil.parseJsonToBean(string, CarLifeGoodsBean.class);
        if (!isMore) {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    if (list.size() != 0) {
                        list.clear();
                    }
                    if (dingDan != null) {
                        List<CarLifeGoodsBean.ContentBean> contentBeen = dingDan.getContent();
                        if (contentBeen != null && contentBeen.size() != 0) {
                            list.addAll(contentBeen);
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
                    content = dingDan.getContent();
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


    public class ShopCollectionAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            if (list != null) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.shop_list_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //01洗车 02加油 03维修 04 4S店 05餐饮 06KTV 07汽配 08轮胎 09 驾校
            if (list != null) {
                CarLifeGoodsBean.ContentBean contentBean = list.get(position);
                if (contentBean != null) {
                    String shop_img = contentBean.getShop_img();
                    double price = contentBean.getPrice();
                    String sold_number = contentBean.getSold_number();
                    String distance = contentBean.getDistance();
                    final String shop_id = contentBean.getShop_id();
                    final int goods_id = contentBean.getGoods_id();
                    final int service_id = contentBean.getService_id();
                    String goods_name = contentBean.getGoods_name();
                    if (!TextUtils.isEmpty(shop_img)) {
                        Picasso.with(App.context).load(shop_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_goods);
                    }
                    if (!TextUtils.isEmpty(price + "")) {
                        viewHolder.tv_goods_money.setText(price + "");
                    }
                    if (!TextUtils.isEmpty(sold_number)) {
                        viewHolder.tv_goods_munber.setText(sold_number);
                    }
                    if (!TextUtils.isEmpty(distance)) {
                        viewHolder.tv_goods_distance.setText(distance);
                    }
                    if (!TextUtils.isEmpty(goods_name)) {
                        viewHolder.tv_goods_name.setText(goods_name);
                    }
                    viewHolder.ll_all_content.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(App.context, CarLifeContentActivity.class);
                            if (!TextUtils.isEmpty("" + goods_id) && !TextUtils.isEmpty("" + service_id) && !TextUtils.isEmpty(shop_id)) {
                                intent.putExtra("goods_id", "" + goods_id);
                                intent.putExtra("shop_id", shop_id);
                                intent.putExtra("service_id", "" + service_id);
                                startActivity(intent);
                            }

                        }
                    });
                    viewHolder.ll_all_content.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
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
                                        OkHttpUtils.post().url(AppUrl.Collection_Delete)
                                                .addParams("user_id", user_id)
                                                .addParams("refer_id", shop_id)
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
            }
            return convertView;
        }

        class ViewHolder {
            //商品照片
            public RoundImageView iv_goods;
            //服务的名字
            public TextView tv_goods_name;
            //价格
            public TextView tv_goods_money;
            //购买人数
            public TextView tv_goods_munber;
            //距离
            public TextView tv_goods_distance;
            //  点击事件
            public LinearLayout ll_all_content;

            public ViewHolder(View v) {
                ll_all_content = (LinearLayout) v.findViewById(R.id.ll_all_content);
                iv_goods = (RoundImageView) v.findViewById(R.id.iv_goods);
                tv_goods_name = (TextView) v.findViewById(R.id.tv_goods_name);
                tv_goods_money = (TextView) v.findViewById(R.id.tv_goods_money);
                tv_goods_munber = (TextView) v.findViewById(R.id.tv_goods_munber);
                tv_goods_distance = (TextView) v.findViewById(R.id.tv_goods_distance);
            }
        }
    }
}
