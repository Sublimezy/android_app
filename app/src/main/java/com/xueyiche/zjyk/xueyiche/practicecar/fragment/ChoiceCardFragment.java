package com.xueyiche.zjyk.xueyiche.practicecar.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
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
import com.xueyiche.zjyk.xueyiche.constants.bean.JiaoLianInfo;
import com.xueyiche.zjyk.xueyiche.constants.bean.ListShaiXuanBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.homepage.activities.location.bean.KaiTongCityBean;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.YiJiCaiDanAdapter;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.ChoiceCardDetails;
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
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Owner on 2016/11/1.
 */
public class ChoiceCardFragment extends BaseFragment implements View.OnClickListener, NetBroadcastReceiver.netEventHandler {
    //选教练列表
    private RadioButton ll_choice_area, ll_choice_carstyle, ll_choice_price, ll_choice_shaixuan;
    private List<String> chexing, city, paixu, price;
    private ListView lv_choice_jiaolian;
    private ListView yijicaidanListView;
    private YiJiCaiDanAdapter yijicaidanAdapter;
    private PopupWindow popupWindow;
    private boolean isOpenPop;
    private TranslateAnimation mShowAction;
    private LinearLayout ll_youzheng_empty;
    private ListShaiXuanBean listShaiXuanBean = new ListShaiXuanBean();
    private int pager = 0;
    private List<JiaoLianInfo.ContentBean> content;
    private List<JiaoLianInfo.ContentBean> list = new ArrayList<>();
    private ChoiceJiaoLianAdapter choiceJiaoLianAdapter;
    private KaiTongCityBean kaiTongCityBean;
    private String area_id;
    private String user_id;
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
//        seriesname = getIntent().getStringExtra("seriesname");
//        series_id = getIntent().getStringExtra("series_id");
//        type = getIntent().getStringExtra("type");
        choiceJiaoLianAdapter = new ChoiceJiaoLianAdapter(list, App.context, R.layout.practice_item);
        rl_title = (RelativeLayout) view.findViewById(R.id.title);
        rl_title.setVisibility(View.GONE);
        ll_choice_price = (RadioButton) view.findViewById(R.id.ll_choice_price);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        ll_choice_area = (RadioButton) view.findViewById(R.id.ll_choice_area);
        ll_choice_shaixuan = (RadioButton) view.findViewById(R.id.ll_choice_shaixuan);
        ll_choice_carstyle = (RadioButton) view.findViewById(R.id.ll_choice_carstyle);
        rg_practice = (RadioGroup) view.findViewById(R.id.rg_practice);
        ll_youzheng_empty = (LinearLayout) view.findViewById(R.id.ll_youzheng_empty);
        lv_choice_jiaolian = (ListView) view.findViewById(R.id.lv_choice_jiaolian);
        ll_choice_price.setOnClickListener(this);
        ll_choice_area.setOnClickListener(this);
        ll_choice_shaixuan.setOnClickListener(this);
        ll_choice_carstyle.setOnClickListener(this);
        EventBus.getDefault().register(this);
        NetBroadcastReceiver.mListeners.add(this);
        listShaiXuanBean.setArea_id(PrefUtils.getParameter("area_id"));
        user_id = PrefUtils.getString(App.context, "user_id", "");
        initData();
        getCityFromNet();
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
            listShaiXuanBean.setName(data);
        }

        if (TextUtils.equals("刷新ChoiceCardFragment", msg)) {
            getCityFromNet();
            getDataFromNet();
        }

    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
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
    private void initData() {
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
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            pager = 0;
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
                        Intent intent = new Intent(App.context, ChoiceCardDetails.class);
                        intent.putExtra("driver_id", driver_id);
                        startActivity(intent);
                    } else {
                        showToastShort(StringConstants.CHECK_NET);
                    }

                }
            }
        });


    }

    private void showBottomMenu(View v, List<String> yiji) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.caidan_listview, null);
        yijicaidanListView = (ListView) view.findViewById(R.id.yijicandan);
        //boolean参数设置PopupWindow中的元素是否可以操作
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        yijicaidanAdapter = new YiJiCaiDanAdapter(getActivity(), yiji);
        yijicaidanListView.setAdapter(yijicaidanAdapter);
        //车型
        if (v == ll_choice_carstyle) {
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismissBottomMenu();
                    ll_choice_carstyle.setText(chexing.get(position));
                    listShaiXuanBean.setCartype((position) + "");
                    getDataFromNet();
                }

            });
        }
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
        //变速箱
        if (v == ll_choice_shaixuan) {
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismissBottomMenu();
                    ll_choice_shaixuan.setText(paixu.get(position));
                    listShaiXuanBean.setBiansutype(position + "");
                    getDataFromNet();

                }
            });
        }
        //价格
        if (v == ll_choice_price) {
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismissBottomMenu();
                    ll_choice_price.setText(price.get(position));
                    listShaiXuanBean.setPrice((position + 1) + "");
                    getDataFromNet();

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_choice_shaixuan:
                changPopState(ll_choice_shaixuan, paixu);
                break;
            case R.id.ll_choice_carstyle:
                changPopState(ll_choice_carstyle, chexing);
                break;
            case R.id.ll_choice_area:
                changPopState(ll_choice_area, city);
                break;
            case R.id.ll_choice_price:
                changPopState(ll_choice_price, price);
                break;
        }
    }


    // 请求网络数据
    public void getDataFromNet() {
        String car_style = listShaiXuanBean.getCartype();
        String car_bian_su_xiang = listShaiXuanBean.getBiansutype();
        String service_search = listShaiXuanBean.getName();
        String car_price = listShaiXuanBean.getPrice();
        String area_id_qu = listShaiXuanBean.getArea_id();
        if (XueYiCheUtils.IsHaveInternet(getActivity())) {
            showProgressDialog(getActivity(), false);
            OkHttpUtils.post().url(AppUrl.Have_Parper_Test)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .addParams("page_index", pager + "")
                    .addParams("page_size", "10")
                    .addParams("car_type", car_style)
                    .addParams("hand_auto", car_bian_su_xiang)
                    .addParams("driver_name", service_search)
                    .addParams("hour_price", car_price)
                    .addParams("area_id", area_id_qu)
                    .addParams("series_id", "")
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
        String car_style = listShaiXuanBean.getCartype();
        String car_bian_su_xiang = listShaiXuanBean.getBiansutype();
        String service_search = listShaiXuanBean.getName();
        String car_price = listShaiXuanBean.getPrice();
        String area_id_qu = listShaiXuanBean.getArea_id();
        if (XueYiCheUtils.IsHaveInternet(getActivity())) {
            showProgressDialog(getActivity(), false);
            OkHttpUtils.post().url(AppUrl.Have_Parper_Test)
                    .addParams("device_id", s)
                    .addParams("page_index", pager + "")
                    .addParams("page_size", "10")
                    .addParams("car_type", car_style)
                    .addParams("hand_auto", car_bian_su_xiang)
                    .addParams("driver_name", service_search)
                    .addParams("hour_price", car_price)
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
        if (XueYiCheUtils.IsHaveInternet(getActivity()) && !TextUtils.isEmpty(area_id)) {
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

            String order_count = contentBean.getOrder_count();
            String seriesname = contentBean.getSeriesname();
            String hand_auto = contentBean.getHand_auto();
            //教练名字
            if (!TextUtils.isEmpty(driver_name)) {
                viewHolder.setText(R.id.tv_drivers_name, driver_name);
            }
            //车辆描述
            if (!TextUtils.isEmpty(seriesname) && !TextUtils.isEmpty(hand_auto) && !TextUtils.isEmpty(driving_year)) {
                viewHolder.setText(R.id.tv_drivers_situation, seriesname + "  " + hand_auto + "  " + driving_year);
            }
            //教练的钱
            if (!TextUtils.isEmpty(hour_price)) {
                viewHolder.setText(R.id.tv_drivers_money, hour_price);
            }
            //头像
            if (!TextUtils.isEmpty(head_img)) {
                viewHolder.setPicHead(R.id.iv_drivers_head, head_img);
            }
            //车
            if (!TextUtils.isEmpty(car_url)) {
                viewHolder.setPic(R.id.iv_drivers_car_photo, car_url);
            }
        }
    }
//
//    public class AreaAdapter extends BaseAdapter {
//        private int selectedPosition = -1;
//
//        @Override
//        public int getCount() {
//            if (contentArea != null) {
//                return contentArea.size();
//            } else {
//                return 0;
//            }
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return contentArea.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//
//            if (view == null) {
//                // 把一个布局xml文件转化成view对象
//                view = LayoutInflater.from(App.context).inflate(R.layout.caidan_shaixuan_item, null);
//            }
//            TextView cheliang_leixing = (TextView) view.findViewById(R.id.cheliang_leixing);
//            cheliang_leixing.setText(contentArea.get(i).getArea_name());
//            LinearLayout item_layout = (LinearLayout) view.findViewById(R.id.root_item);
//            if (selectedPosition == i) {
//                item_layout.setBackgroundColor(Color.WHITE);
//                cheliang_leixing.setTextColor(Color.parseColor("#FF5000"));
//            } else {
//                item_layout.setBackgroundColor(Color.parseColor("#F2F3F5"));
//                cheliang_leixing.setTextColor(Color.parseColor("#666666"));
//            }
//            return null;
//        }
//    }
}
