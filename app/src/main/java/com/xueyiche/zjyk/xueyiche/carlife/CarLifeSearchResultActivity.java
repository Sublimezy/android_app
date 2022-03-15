package com.xueyiche.zjyk.xueyiche.carlife;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.carlife.bean.CarLifeSearchResultBean;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.ListShaiXuanBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.activities.location.bean.KaiTongCityBean;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.YiJiCaiDanAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.bean.CarLifeGoodsBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.CommonSearchBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ResultBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.MyGridView;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by YJF on 2020/2/25.
 */
public class CarLifeSearchResultActivity extends BaseActivity implements NetBroadcastReceiver.netEventHandler, View.OnClickListener {
    private HomeYouHuiAdapter homeYouHuiAdapter;
    private List<CarLifeSearchResultBean.ContentBean> content;
    private List<CarLifeSearchResultBean.ContentBean> list = new ArrayList<>();
    private int pager = 1;
    private String longitude;
    private String latitude;
    private String area_id;
    private String service_id, service_type;
    private RadioButton ll_choice_paixu;
    private RadioButton ll_choice_diqu;
    private List<String> city, paixu;
    private boolean isOpenPop;
    private KaiTongCityBean kaiTongCityBean;
    private ListView yijicaidanListView;
    private PopupWindow popupWindow;
    private YiJiCaiDanAdapter yijicaidanAdapter;
    private ListShaiXuanBean listShaiXuanBean = new ListShaiXuanBean();
    private RadioGroup ll_shaixuantiaojian;
    private TextView tv_search_title;
    private ImageView iv_login_back, iv_search_yuyin, iv_kefu;
    private RelativeLayout rl_title_search_jump;
    private String search_type, coupon_id, search_name;
    private RefreshLayout refreshLayout;
    public static CarLifeSearchResultActivity instance;
    private ListView lv_result;


    @Override
    protected int initContentView() {
        return R.layout.carlife_searchresult_activity;
    }

    @Override
    protected void initView() {
        instance = this;
        iv_kefu = (ImageView) view.findViewById(R.id.iv_kefu);
        tv_search_title = (TextView) view.findViewById(R.id.tv_search_title);
        iv_login_back = (ImageView) view.findViewById(R.id.iv_login_back);
        rl_title_search_jump = (RelativeLayout) view.findViewById(R.id.rl_title_search_jump);
        ll_choice_paixu = (RadioButton) view.findViewById(R.id.ll_choice_paixu);
        ll_choice_diqu = (RadioButton) view.findViewById(R.id.ll_choice_diqu);
        ll_shaixuantiaojian = (RadioGroup) view.findViewById(R.id.ll_shaixuantiaojian);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        iv_kefu.setOnClickListener(this);
        ll_choice_diqu.setOnClickListener(this);
        rl_title_search_jump.setOnClickListener(this);
        ll_choice_paixu.setOnClickListener(this);
        iv_login_back.setOnClickListener(this);
        lv_result = (ListView) view.findViewById(R.id.lv_result);
        String area_id = PrefUtils.getString(App.context, "area_id", "");
        listShaiXuanBean.setArea_id(area_id);
    }

    @Override
    protected void initListener() {
        //刷新
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
        //加载
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
        });

    }

    @Override
    protected void initData() {
        homeYouHuiAdapter = new HomeYouHuiAdapter();
        paixu = new ArrayList<String>();
        city = new ArrayList<String>();
        city.add("不限");
        paixu.add("默认排序");
        paixu.add("距离最近");
        paixu.add("人气优先");
        paixu.add("好评优先");
        longitude = PrefUtils.getString(App.context, "x", "0");
        latitude = PrefUtils.getString(App.context, "y", "0");
        area_id = PrefUtils.getString(App.context, "area_id", "");
        Intent intent = getIntent();
        service_id = intent.getStringExtra("service_id");
        service_type = intent.getStringExtra("service_type");
        search_name = intent.getStringExtra("search_name");
        search_type = intent.getStringExtra("search_type");
        coupon_id = intent.getStringExtra("coupon_id");
        tv_search_title.setText(search_name);
        getCityFromNet();
        getDataFromNet();
    }

    public void getCityFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(area_id)) {
            OkHttpUtils.post().url(AppUrl.Have_Parper_Test_Area)
                    .addParams("area_id", area_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {

                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        kaiTongCityBean = JsonUtil.parseJsonToBean(string, KaiTongCityBean.class);
                        List<KaiTongCityBean.ContentBean> content_city = kaiTongCityBean.getContent();
                        for (int i = 0; i < content_city.size(); i++) {
                            KaiTongCityBean.ContentBean contentBean = content_city.get(i);
                            String area_name = contentBean.getArea_name();
                            city.add(area_name);
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

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Carlife_Goods)
                    .addParams("pager", pager + "")
                    .addParams("longitude_user", longitude)
                    .addParams("latitude_user", latitude)
                    .addParams("service_id", service_id)
                    .addParams("area_id", listShaiXuanBean.getArea_id())
                    .addParams("sort_method", listShaiXuanBean.getSort_method())
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("search_name", search_name)
                    .addParams("search_type", search_type)
                    .addParams("coupon_id", coupon_id)
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
        } else {
            stopProgressDialog();
            showToastShort(StringConstants.CHECK_NET);
        }


    }

    private void processData(String string, final boolean isMore) {
        final CarLifeSearchResultBean carLifeSearchResultBean = JsonUtil.parseJsonToBean(string, CarLifeSearchResultBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (carLifeSearchResultBean != null) {
                    if (!isMore) {
                        if (list.size() != 0) {
                            list.clear();
                        }
                        List<CarLifeSearchResultBean.ContentBean> contentBeen = carLifeSearchResultBean.getContent();
                        if (contentBeen != null && contentBeen.size() != 0) {
                            list.addAll(contentBeen);
                            lv_result.setAdapter(homeYouHuiAdapter);
                        }
                        homeYouHuiAdapter.notifyDataSetChanged();
                    } else {
                        //加载更多
                        content = carLifeSearchResultBean.getContent();
                        if (content != null) {
                            list.addAll(content);//追加更多数据
                            homeYouHuiAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    public void getMoreDataFromNet() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Carlife_Goods)
                    .addParams("pager", pager + "")
                    .addParams("longitude_user", longitude)
                    .addParams("latitude_user", latitude)
                    .addParams("service_id", service_id)
                    .addParams("service_type", service_type)
                    .addParams("area_id", listShaiXuanBean.getArea_id())
                    .addParams("sort_method", listShaiXuanBean.getSort_method())
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("search_name", search_name)
                    .addParams("search_type", search_type)
                    .addParams("coupon_id", coupon_id)
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.iv_kefu:
                openActivity(KeFuActivity.class);
                break;
            case R.id.rl_title_search_jump:
                Intent intent = new Intent(App.context, CarLifeSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_choice_diqu:
                changPopState(ll_choice_diqu, city);
                break;
            case R.id.ll_choice_paixu:
                changPopState(ll_choice_paixu, paixu);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 111:
                Bundle extras = data.getExtras();
                search_name = extras.getString("search_name");
                search_type = extras.getString("search_type");
                service_id = extras.getString("service_id");
                service_type = extras.getString("service_type");
                coupon_id = extras.getString("coupon_id");
                tv_search_title.setText(search_name);
                getDataFromNet();
                break;
        }
    }

    public void changPopState(View v, List<String> yiji) {
        isOpenPop = !isOpenPop;
        if (isOpenPop) {
            showBottomMenu(v, yiji);
        } else {
            dismissBottomMenu();
        }
    }

    private void dismissBottomMenu() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();

        }
    }

    private void showBottomMenu(View v, List<String> yiji) {
        View view = LayoutInflater.from(this).inflate(R.layout.caidan_listview, null);
        yijicaidanListView = (ListView) view.findViewById(R.id.yijicandan);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        yijicaidanAdapter = new YiJiCaiDanAdapter(this, yiji);
        yijicaidanListView.setAdapter(yijicaidanAdapter);
        if (v == ll_choice_diqu) {
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismissBottomMenu();
                    if (position == 0) {
                        //不限
                        ll_choice_diqu.setText("不限");
                        listShaiXuanBean.setArea_id(area_id);
                    } else {
                        KaiTongCityBean.ContentBean contentBean = kaiTongCityBean.getContent().get(position - 1);
                        String area_name = contentBean.getArea_name();
                        String area_id = contentBean.getArea_id();

                        if (!TextUtils.isEmpty(area_id)) {
                            listShaiXuanBean.setArea_id(area_id);
                        }
                        if (!TextUtils.isEmpty(area_name)) {
                            ll_choice_diqu.setText(area_name);
                        }
                    }
                    getDataFromNet();
                    ll_shaixuantiaojian.clearCheck();
                }
            });
        }
        if (v == ll_choice_paixu) {
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ll_choice_paixu.setText(paixu.get(position));
                    TextView cheliang_leixing = (TextView) view.findViewById(R.id.cheliang_leixing);
                    cheliang_leixing.setTextColor(Color.parseColor("#ff5000"));
                    dismissBottomMenu();
                    listShaiXuanBean.setSort_method(position + "");
                    getDataFromNet();
                    ll_shaixuantiaojian.clearCheck();
                }
            });
        }

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(v, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ll_shaixuantiaojian.clearCheck();
                isOpenPop = false;
            }
        });
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismissBottomMenu();
                    return true;
                }
                return false;
            }
        });
    }

    public class HomeYouHuiAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.search_result_list_iten, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //01洗车 02加油 03维修 04 4S店 05餐饮 06KTV 07汽配 08轮胎 09 驾校
            if (list != null && list.size() != 0) {
                CarLifeSearchResultBean.ContentBean contentBean = list.get(position);
                if (contentBean != null) {
                    String shop_img = contentBean.getShop_img();
                    int service_id = contentBean.getService_id();
                    String price = contentBean.getPrice();
                    final String latitude_shop = contentBean.getLatitude_shop();
                    final String longitude_shop = contentBean.getLongitude_shop();
                    final String shop_place_name = contentBean.getShop_place_name();
                    String distance = contentBean.getDistance();
                    final String shop_id = contentBean.getShop_id();
                    final String name = contentBean.getName();
                    final int goods_id = contentBean.getGoods_id();
                    if (31 == service_id) {
                        viewHolder.tv_goods_money.setVisibility(View.GONE);
                        viewHolder.iv_daohang.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.tv_goods_money.setVisibility(View.VISIBLE);
                        viewHolder.iv_daohang.setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(shop_img)) {
                        Picasso.with(App.context).load(shop_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_goods);
                    }
                    if (!TextUtils.isEmpty(price)) {
                        viewHolder.tv_goods_money.setText(price);
                    }
                    if (!TextUtils.isEmpty(distance)) {
                        viewHolder.tv_goods_distance.setText(distance);
                    }
                    if (!TextUtils.isEmpty(name)) {
                        viewHolder.tv_goods_name.setText(name);
                    }
                    if (!TextUtils.isEmpty(shop_place_name)) {
                        viewHolder.tv_shop_place_name.setText(shop_place_name);
                    }
                    viewHolder.ll_all_content.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(App.context, CarLifeContentActivity.class);
                            intent.putExtra("goods_id", goods_id + "");
                            intent.putExtra("shop_id", shop_id);
                            intent.putExtra("service_id", CarLifeSearchResultActivity.this.service_id);
                            startActivity(intent);
                        }
                    });
                    viewHolder.iv_daohang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            XueYiCheUtils.getDiaLocation(CarLifeSearchResultActivity.this, latitude_shop, longitude_shop, name, shop_place_name);
                        }
                    });
                }
            }
            return convertView;
        }

        class ViewHolder {
            private final ImageView iv_daohang;
            //商品照片
            public RoundImageView iv_goods;
            //服务的名字
            public TextView tv_goods_name, tv_shop_place_name;
            //价格
            public TextView tv_goods_money;
            //距离
            public TextView tv_goods_distance;
            //  点击事件
            public LinearLayout ll_all_content;

            public ViewHolder(View v) {
                ll_all_content = (LinearLayout) v.findViewById(R.id.ll_all_content);
                iv_goods = (RoundImageView) v.findViewById(R.id.iv_goods);
                iv_daohang = (ImageView) v.findViewById(R.id.iv_daohang);
                tv_goods_name = (TextView) v.findViewById(R.id.tv_goods_name);
                tv_goods_money = (TextView) v.findViewById(R.id.tv_goods_money);
                tv_shop_place_name = (TextView) v.findViewById(R.id.tv_shop_place_name);
                tv_goods_distance = (TextView) v.findViewById(R.id.tv_goods_distance);
            }
        }
    }
}