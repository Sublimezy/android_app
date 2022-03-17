package com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.JiaoLianInfo;
import com.xueyiche.zjyk.xueyiche.constants.bean.ListShaiXuanBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.location.bean.KaiTongCityBean;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.YiJiCaiDanAdapter;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.ChoiceCardDetails;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.newactivity.PracticeCarMapFragment;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.utils.BaiduLocation;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/9.
 */
public class DriverPracticeActivity extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler {

    private TextView tv_login_back;
    private ImageView iv_login_back, tv_top_right_button_map;

    //选教练列表
    private RadioButton ll_choice_area, ll_choice_carstyle, ll_choice_shaixuan;
    private List<String> chexing, city, paixu, price;
    private ListView lv_choice_jiaolian;
    private ListView yijicaidanListView;
    private YiJiCaiDanAdapter yijicaidanAdapter;
    private PopupWindow popupWindow;
    private boolean isOpenPop;
    private LinearLayout ll_youzheng_empty;
    private ListShaiXuanBean listShaiXuanBean = new ListShaiXuanBean();
    private int pager = 1;
    private List<JiaoLianInfo.ContentBean> content;
    private List<JiaoLianInfo.ContentBean> list = new ArrayList<>();
    private ChoiceJiaoLianAdapter choiceJiaoLianAdapter;
    private KaiTongCityBean kaiTongCityBean;
    private String area_id;
    private RefreshLayout refreshLayout;
    private RadioGroup rg_practice;
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private double mCurrentLat;
    private double mCurrentLon;

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.driver_practice_activity;
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_title);
        iv_login_back = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_login_back);
        tv_top_right_button_map = view.findViewById(R.id.title).findViewById(R.id.tv_top_right_button_map);
        choiceJiaoLianAdapter = new ChoiceJiaoLianAdapter(list, App.context, R.layout.practice_item);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        ll_choice_area = (RadioButton) view.findViewById(R.id.ll_choice_area);
        ll_choice_shaixuan = (RadioButton) view.findViewById(R.id.ll_choice_shaixuan);
        ll_choice_carstyle = (RadioButton) view.findViewById(R.id.ll_choice_carstyle);
        rg_practice = (RadioGroup) view.findViewById(R.id.rg_practice);
        ll_youzheng_empty = (LinearLayout) view.findViewById(R.id.ll_youzheng_empty);
        lv_choice_jiaolian = (ListView) view.findViewById(R.id.lv_choice_jiaolian);
        listShaiXuanBean.setArea_id(PrefUtils.getParameter("area_id"));

    }

    @Override
    protected void initListener() {
        ll_choice_area.setOnClickListener(this);
        ll_choice_shaixuan.setOnClickListener(this);
        ll_choice_carstyle.setOnClickListener(this);
        iv_login_back.setOnClickListener(this);
        tv_top_right_button_map.setOnClickListener(this);
        EventBus.getDefault().register(this);
        NetBroadcastReceiver.mListeners.add(this);
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
                    if (XueYiCheUtils.IsHaveInternet(DriverPracticeActivity.this)) {
                        String driver_id = list.get(position).getDriver_id();
                        String from_user = list.get(position).getFrom_user();
                        Intent intent = new Intent(App.context, ChoiceCardDetails.class);
                        intent.putExtra("driver_id", driver_id);
                        intent.putExtra("from_user", from_user);
                        startActivity(intent);
                    } else {
                        showToastShort(StringConstants.CHECK_NET);
                    }

                }
            }
        });
    }

    @Override
    protected void initData() {
        tv_top_right_button_map.setVisibility(View.VISIBLE);
        tv_login_back.setText("有证练车");
        chexing = new ArrayList<String>();
        paixu = new ArrayList<String>();
        city = new ArrayList<String>();
        price = new ArrayList<String>();
        //车型
        chexing.add("全部车型");
        chexing.add("微型");
        chexing.add("紧凑");
        chexing.add("中型");
        chexing.add("大型");
        chexing.add("豪华");
        chexing.add("SUV");
        //变速箱
        paixu.add("不限");
        paixu.add("自动挡");
        paixu.add("手动挡");
        //区域
        city.add("不限");
        //价格
        price.add("由低到高");
        price.add("由高到低");
        listShaiXuanBean.setCartype("0");
        listShaiXuanBean.setBiansutype("0");
        listShaiXuanBean.setPrice("0");
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        getCityFromNet();
        getDataFromNet();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        mLocClient.stop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocClient.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocClient.stop();
    }

    public void onEvent(MyEvent event) {

        String msg = event.getMsg();
        String data = event.getData();
        if (!TextUtils.isEmpty(data)) {
            listShaiXuanBean.setName(data);
        }

        if (TextUtils.equals("刷新ChoiceCardFragment", msg)) {
            getCityFromNet();
            getDataFromNet();
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

    public void changPopStateShaiXuan(View v) {
        isOpenPop = !isOpenPop;
        if (isOpenPop) {
            showBottomMenuShaiXuan(v);
        } else {
            dismissBottomMenu();
        }
    }

    private void dismissBottomMenu() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();

        }
    }

    private void showBottomMenuShaiXuan(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.practice_list_shaixuan_layout, null);
        RadioButton rb_all = view.findViewById(R.id.rb_all);
        RadioButton rb_free = view.findViewById(R.id.rb_free);
        RadioButton rb_all_car = view.findViewById(R.id.rb_all_car);
        RadioButton rb_hand = view.findViewById(R.id.rb_hand);
        RadioButton rb_auto = view.findViewById(R.id.rb_auto);
        ImageView iv_ok = view.findViewById(R.id.iv_ok);
        String isFree = listShaiXuanBean.getIsFree();
        String handOrauto = listShaiXuanBean.getHandOrauto();
        if (!TextUtils.isEmpty(isFree)) {
            rb_free.setChecked(true);
        } else {
            rb_all.setChecked(true);
        }
        if (!TextUtils.isEmpty(handOrauto)) {
            if ("0".equals(handOrauto)) {
                rb_hand.setChecked(true);
            } else if ("1".equals(handOrauto)) {
                rb_auto.setChecked(true);
            }
        } else {
            rb_all_car.setChecked(true);
        }
        //boolean参数设置PopupWindow中的元素是否可以操作
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        //变速箱
        if (v == ll_choice_shaixuan) {
            rb_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listShaiXuanBean.setIsFree("");
                }
            });
            rb_free.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listShaiXuanBean.setIsFree("0");
                }
            });
            rb_all_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listShaiXuanBean.setHandOrauto("");
                }
            });
            rb_hand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listShaiXuanBean.setHandOrauto("0");
                }
            });
            rb_auto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listShaiXuanBean.setHandOrauto("1");
                }
            });
            iv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDataFromNet();
                    dismissBottomMenu();
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
                rg_practice.clearCheck();
                isOpenPop = false;
            }
        });
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismissBottomMenu();
                    rg_practice.clearCheck();
                    return true;
                }
                return false;
            }
        });
    }

    private void showBottomMenu(View v, List<String> yiji) {
        View view = LayoutInflater.from(this).inflate(R.layout.caidan_listview, null);
        yijicaidanListView = (ListView) view.findViewById(R.id.yijicandan);
        //boolean参数设置PopupWindow中的元素是否可以操作
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        yijicaidanAdapter = new YiJiCaiDanAdapter(this, yiji);
        yijicaidanListView.setAdapter(yijicaidanAdapter);
        //地区
        if (v == ll_choice_area) {
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismissBottomMenu();
                    if (position == 0) {
                        //不限
                        ll_choice_area.setText("不限");
                        listShaiXuanBean.setArea_id(PrefUtils.getParameter("area_id"));
                        getDataFromNet();
                    } else {
                        KaiTongCityBean.ContentBean contentBean = kaiTongCityBean.getContent().get(position - 1);
                        String area_name = contentBean.getArea_name();
                        String area_id = contentBean.getArea_id();
                        listShaiXuanBean.setArea_id(area_id);
                        ll_choice_area.setText(area_name);
                        getDataFromNet();
                    }
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
                rg_practice.clearCheck();
                isOpenPop = false;
            }
        });
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismissBottomMenu();
                    rg_practice.clearCheck();
                    return true;
                }
                return false;
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 333:
                String seriesname = extras.getString("seriesname");
                String series_id = extras.getString("series_id");
                String name = extras.getString("name");
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(seriesname)) {
                    ll_choice_carstyle.setText(name+seriesname);
                    ll_choice_carstyle.setChecked(true);
                }else {
                    ll_choice_carstyle.setText("车辆品牌");
                    ll_choice_carstyle.setChecked(false);
                }
                listShaiXuanBean.setSeries_id(series_id);
                getDataFromNet();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.tv_top_right_button_map:
                finish();
                break;
            case R.id.ll_choice_shaixuan:
                changPopStateShaiXuan(ll_choice_shaixuan);
                break;
            case R.id.ll_choice_carstyle:
                Intent intent2 = new Intent(App.context, PinPaiPracticeActivity.class);
                intent2.putExtra("jiesong_type", "1");
                intent2.putExtra("cartype", "1");
                startActivityForResult(intent2, 1);
                break;
            case R.id.ll_choice_area:
                changPopState(ll_choice_area, city);
                break;
        }
    }


    // 请求网络数据
    public void getDataFromNet() {
        String area_id_qu = listShaiXuanBean.getArea_id();
        String isFree = listShaiXuanBean.getIsFree();
        String handOrauto = listShaiXuanBean.getHandOrauto();
        String series_id = listShaiXuanBean.getSeries_id();
        BaiduLocation baidu = new BaiduLocation();
        baidu.baiduLocation();
        String x = PrefUtils.getString(App.context, "x", "");
        String y = PrefUtils.getString(App.context, "y", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.getDriverListYZ)
                    .addParams("series_id",series_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("longitude_user", x)
                    .addParams("latitude_user",y)
                    .addParams("driver_name", "")
                    .addParams("pager", pager + "")
                    .addParams("on_off", TextUtils.isEmpty(isFree) ? "" : isFree)
                    .addParams("car_type", "0")
                    .addParams("hand_auto", TextUtils.isEmpty(handOrauto) ? "" : handOrauto)
                    .addParams("hour_price", "1")
                    .addParams("area_id", area_id_qu)
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
        String area_id_qu = listShaiXuanBean.getArea_id();
        String isFree = listShaiXuanBean.getIsFree();
        String handOrauto = listShaiXuanBean.getHandOrauto();
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.getDriverListYZ)
                    .addParams("series_id", "")
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("longitude_user", "" + mCurrentLon)
                    .addParams("latitude_user", "" + mCurrentLat)
                    .addParams("driver_name", "")
                    .addParams("pager", pager + "")
                    .addParams("on_off", TextUtils.isEmpty(isFree) ? "" : isFree)
                    .addParams("car_type", "0")
                    .addParams("hand_auto", TextUtils.isEmpty(handOrauto) ? "" : handOrauto)
                    .addParams("hour_price", "1")
                    .addParams("area_id", area_id_qu)
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


    public void getCityFromNet() {
        area_id = PrefUtils.getString(App.context, "area_id", "");
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
                            if (!TextUtils.isEmpty(area_name)) {
                                city.add(area_name);
                            }
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


    public class ChoiceJiaoLianAdapter extends BaseCommonAdapter {
        public ChoiceJiaoLianAdapter(List<JiaoLianInfo.ContentBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            JiaoLianInfo.ContentBean contentBean = (JiaoLianInfo.ContentBean) item;
            String driver_name = contentBean.getDriver_name();
            String hour_price = contentBean.getHour_price();
            String driving_year = contentBean.getDriving_year();
            String car_url = contentBean.getCar_url();
            String head_img = contentBean.getHead_img();
            String seriesname = contentBean.getSeriesname();
            String hand_auto = contentBean.getHand_auto();
            String from_user = contentBean.getFrom_user();
            String brand_name = contentBean.getBrand_name();
            //教练名字
            if (!TextUtils.isEmpty(driver_name)) {
                viewHolder.setText(R.id.tv_drivers_name, driver_name);
            }
            //车辆描述
            if (!TextUtils.isEmpty(brand_name) && !TextUtils.isEmpty(seriesname) && !TextUtils.isEmpty(hand_auto) && !TextUtils.isEmpty(driving_year)) {
                viewHolder.setText(R.id.tv_drivers_situation, brand_name + seriesname + "  " + hand_auto + "  " + driving_year);
            }
            //教练的钱
            if (!TextUtils.isEmpty(hour_price)) {
                viewHolder.setText(R.id.tv_drivers_money, hour_price + "/小时");
            }
            //头像
            if (!TextUtils.isEmpty(head_img)) {
                viewHolder.setPicHead(R.id.iv_drivers_head, head_img);
            }
            //车
            if (!TextUtils.isEmpty(car_url)) {
                viewHolder.setPic(R.id.iv_drivers_car_photo, car_url);
            }
            if (!TextUtils.isEmpty(from_user)) {
                viewHolder.setText(R.id.tv_drivers_distance, from_user);
            }
        }
    }
}
