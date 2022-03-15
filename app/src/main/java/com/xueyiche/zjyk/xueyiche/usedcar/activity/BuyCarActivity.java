package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.xueyiche.zjyk.xueyiche.homepage.adapters.YiJiCaiDanAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.view.MyGridView;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.BuyUsedCarShaiXuanBean;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.BuyUsedCarlistBean;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarShaiXuanBean;
import com.xueyiche.zjyk.xueyiche.usedcar.db.DBUsedCarManager;
import com.xueyiche.zjyk.xueyiche.usedcar.view.RangeSeekbar;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2018/6/28.
 */
public class BuyCarActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_exam_back;
    private TextView tv_search_title;
    private ListView lv_list;
    private RadioButton ll_choice_carstyle;
    private RadioButton ll_choice_shaixuan;
    private RadioButton ll_choice_price;
    private RadioButton ll_choice_zhineng;
    private PopupWindow popupWindow;
    private boolean isOpenPop;
    private BuyCarAdapter buyCarAdapter;
    private List<BuyUsedCarlistBean.DataBean> data;
    private List<BuyUsedCarlistBean.DataBean> list = new ArrayList<>();
    private LinearLayout ll_usedcar_empty;
    private int pager = 1;
    private RefreshLayout refreshLayout;
    private List<String> zhineng = new ArrayList<>();
    private List<String> priceList = new ArrayList<>();
    private BuyUsedCarShaiXuanBean buyUsedCarShaiXuanBean = new BuyUsedCarShaiXuanBean();
    private DBUsedCarManager dbUsedCarManager;
    private String cheling = "", jiage = "", licheng = "", pailiang = "", chexing = "", chandi = "", zuowei = "", guobie = "", biansu = "", paifang = "", yanse = "", ranliao = "";
    private String brand_id = "", carSystem_id = "";
    private YiJiCaiDanAdapter yijicaidanAdapter;
    private String level_id = "", rbhot_id = "", rbhot_type = "", carSourceSearch = "";
    private int width;
    private RadioGroup rg_practice;

    @Override
    protected int initContentView() {
        return R.layout.buy_car_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        ll_usedcar_empty = (LinearLayout) view.findViewById(R.id.ll_usedcar_empty);
        tv_search_title = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_search_title);
        lv_list = (ListView) view.findViewById(R.id.lv_list);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        ll_choice_carstyle = (RadioButton) view.findViewById(R.id.ll_choice_carstyle);
        ll_choice_shaixuan = (RadioButton) view.findViewById(R.id.ll_choice_shaixuan);
        ll_choice_zhineng = (RadioButton) view.findViewById(R.id.ll_choice_zhineng);
        ll_choice_price = (RadioButton) view.findViewById(R.id.ll_choice_price);
        rg_practice = (RadioGroup) view.findViewById(R.id.rg_practice);
        buyCarAdapter = new BuyCarAdapter(list,App.context,R.layout.used_car_list_item);
        getDataFromNet();
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        ll_choice_carstyle.setOnClickListener(this);
        ll_choice_shaixuan.setOnClickListener(this);
        ll_choice_zhineng.setOnClickListener(this);
        ll_choice_price.setOnClickListener(this);
        tv_search_title.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            pager = 1;
                            data = null;
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
                                                                if (data != null && data.size() == 0) {
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
    protected void initData() {
        Intent intent = getIntent();
        rbhot_type = intent.getStringExtra("rbhot_type");
        rbhot_id = intent.getStringExtra("rbhot_id");
        level_id = intent.getStringExtra("level_id");
        carSourceSearch = intent.getStringExtra("carSourceSearch");
        SpannableString name = new SpannableString("搜索您想要的车");//定义hint的值
        tv_search_title.setHint(name);
        zhineng.add("综合排序");
        zhineng.add("价格最低");
        zhineng.add("价格最高");
        zhineng.add("车龄最短");
        zhineng.add("里程最少");
        zhineng.add("最新发布");
        priceList.add("不限");
        priceList.add("3万以下");
        priceList.add("3-5万");
        priceList.add("5-7万");
        priceList.add("7-9万");
        priceList.add("9-12万");
        priceList.add("12-16万");
        priceList.add("16-20万");
        priceList.add("20万以上");
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        yijicaidanAdapter = new YiJiCaiDanAdapter(BuyCarActivity.this, zhineng);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BuyUsedCarlistBean.DataBean dataBean = list.get(i);
                if (dataBean != null) {
                    int id = dataBean.getId();
                    String htmlUrl = dataBean.getHtmlUrl();
                    Intent intent1 = new Intent(App.context, UsedCarContentActivity.class);
                    intent1.putExtra("carsource_id", id+"");
                    intent1.putExtra("cartype", "buy");
                    intent1.putExtra("htmlUrl",htmlUrl);
                    intent1.putExtra("qu_time_date","");
                    intent1.putExtra("huan_time_date","");
                    intent1.putExtra("store_id_usedcar","");
                    intent1.putExtra("duration","");
                    intent1.putExtra("qu_city_usedcar","");
                    intent1.putExtra("huan_city_usedcar","");
                    intent1.putExtra("shangMenQu","");
                    intent1.putExtra("shangMenHuan","");
                    intent1.putExtra("qu_latitude_usedcar","");
                    intent1.putExtra("qu_longitude_usedcar","");
                    intent1.putExtra("qu_name_usedcar","");
                    intent1.putExtra("huan_latitude_usedcar","");
                    intent1.putExtra("huan_longitude_usedcar","");
                    intent1.putExtra("huan_name_usedcar","");
                    startActivity(intent1);
                }
            }
        });
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dbUsedCarManager.cleanAllState();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        dbUsedCarManager = new DBUsedCarManager(this);
        dbUsedCarManager.copyDBFile();
        getDataFromNet();
        super.onResume();
    }

    private void getDataFromNet() {
        if (dbUsedCarManager != null) {
            String[] titile = {"价格", "车龄", "里程", "排量", "车型", "变速箱", "排放标准", "颜色", "燃料类型", "产地", "座位数", "国别"};
            List<String> shaixuanList = new ArrayList<>();
            for (int i = 0; i < titile.length; i++) {
                List<UsedCarShaiXuanBean> typeList = dbUsedCarManager.findTypeList(titile[i]);
                String s = "";
                if (typeList != null && typeList.size() > 0) {
                    if (i > 3) {
                        for (int j = 0; j < typeList.size(); j++) {
                            UsedCarShaiXuanBean usedCarShaiXuanBean = typeList.get(j);
                            String state = usedCarShaiXuanBean.getState();
                            String name = usedCarShaiXuanBean.getName();
                            if ("1".equals(state)) {
                                s += name + ",";
                            }
                        }
                        if (s.length() > 0) {
                            s = s.substring(0, s.length() - 1);
                        }
                        shaixuanList.add(s);
                    } else {
                        UsedCarShaiXuanBean usedCarShaiXuanBean = typeList.get(0);
                        String state = usedCarShaiXuanBean.getState();
                        String name = usedCarShaiXuanBean.getName();
                        s = name + "-" + state;
                        shaixuanList.add(s);
                    }
                }
            }
            if (shaixuanList.size() == 12) {
                jiage = shaixuanList.get(0);
                cheling = shaixuanList.get(1);
                licheng = shaixuanList.get(2);
                pailiang = shaixuanList.get(3);
                chexing = shaixuanList.get(4);
                biansu = shaixuanList.get(5);
                paifang = shaixuanList.get(6);
                yanse = shaixuanList.get(7);
                ranliao = shaixuanList.get(8);
                chandi = shaixuanList.get(9);
                zuowei = shaixuanList.get(10);
                guobie = shaixuanList.get(11);
                if (XueYiCheUtils.IsHaveInternet(this)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.Used_Car_Buy)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("area", PrefUtils.getString(this, "usedcarcity", ""))
                            .addParams("page", pager + "")
                            .addParams("submit", "1")
                            .addParams("rbhot_type", rbhot_type)
                            .addParams("rbhot_id", rbhot_id)
                            .addParams("level_id", level_id)
                            .addParams("carSourceSearch", carSourceSearch)
                            .addParams("sort_id", buyUsedCarShaiXuanBean.getZhineng())
                            .addParams("brand_id", brand_id)
                            .addParams("carSystem_id", carSystem_id)
                            .addParams("level_name", chexing)
                            .addParams("transmission_case", biansu)
                            .addParams("car_age", cheling)
                            .addParams("mileage", licheng)
                            .addParams("range", jiage)
                            .addParams("displacement", pailiang)
                            .addParams("emission_standard", paifang)
                            .addParams("color", yanse)
                            .addParams("fuel_type", ranliao)
                            .addParams("production_address", chandi)
                            .addParams("pedestal", zuowei)
                            .addParams("different_countries", guobie)
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
        View view = LayoutInflater.from(App.context).inflate(R.layout.caidan_listview, null);
        ListView yijicaidanListView = (ListView) view.findViewById(R.id.yijicandan);
        //boolean参数设置PopupWindow中的元素是否可以操作
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        yijicaidanListView.setAdapter(yijicaidanAdapter);
        //智能
        if (v == ll_choice_zhineng) {
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismissBottomMenu();
                    buyUsedCarShaiXuanBean.setZhineng("" + position);
                    yijicaidanAdapter.setSelectedPosition(position);
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
                    return true;
                }
                return false;
            }
        });
    }

    private void showBottomMenuGrid(View v) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.buycar_price_gridview, null);
        String[] price = {"0", "5", "10", "15", "20", "不限"};
        final String[] postShuJu = new String[1];

        MyGridView yijicaidanListView = (MyGridView) view.findViewById(R.id.gv_shaixuan);
        final RangeSeekbar seekbar = (RangeSeekbar) view.findViewById(R.id.seekbar);
        seekbar.setTextMarks("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "不限");
        final LinearLayout ll_heng = (LinearLayout) view.findViewById(R.id.ll_heng);
        final TextView tv_value = (TextView) view.findViewById(R.id.tv_value);
        final TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        final List<UsedCarShaiXuanBean> cheling_list = dbUsedCarManager.findTypeList("价格");
        String name = cheling_list.get(0).getName();
        final String state = cheling_list.get(0).getState();
        postShuJu[0] = name + " - " + state;
        if (!TextUtils.isEmpty(name)) {
            seekbar.setLeftSelection(Integer.valueOf(name));
        }
        if (!TextUtils.isEmpty(state)) {
            if ("A".equals(state)) {
                seekbar.setRightSelection(25);
                tv_value.setText(name + " - " + "不限");
            } else {
                tv_value.setText(name + " - " + state);
                if ("不限".equals(state)) {
                    seekbar.setRightSelection(25);
                } else {
                    seekbar.setRightSelection(Integer.valueOf(state));
                }
            }
        }
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        PriceAdapter yijicaidanAdapter = new PriceAdapter(priceList, App.context,R.layout.gv_search_item);
        yijicaidanListView.setAdapter(yijicaidanAdapter);
//        价格
        for (int i = 0; i < price.length; i++) {
            TextView imageView = new TextView(BuyCarActivity.this);
            imageView.setTextColor(getResources().getColor(R.color.test_color));
            imageView.setTextSize(15.0f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width / price.length, 50);
            imageView.setLayoutParams(params);
            imageView.setText(price[i] + "");
            ll_heng.addView(imageView);
        }

        final String[] left = new String[1];
        final String[] right = new String[1];
        seekbar.setOnCursorChangeListener(new RangeSeekbar.OnCursorChangeListener() {
            @Override
            public void onLeftCursorChanged(int location, String textMark) {
                LogUtil.e("leftCursorIndex", textMark);
                String name = cheling_list.get(0).getName();
                String state = cheling_list.get(0).getState();
                left[0] = textMark;
                dbUsedCarManager.changeFanWei("价格", TextUtils.isEmpty(left[0]) ? name : left[0], TextUtils.isEmpty(right[0]) ? state : right[0]);
                postShuJu[0] = (TextUtils.isEmpty(left[0]) ? name : left[0]) + " - " + (TextUtils.isEmpty(right[0]) ? state : right[0]);
                if (postShuJu[0].contains("A")) {
                    String replace = postShuJu[0].replace("A", "不限");
                    tv_value.setText(replace);
                } else {
                    tv_value.setText(postShuJu[0]);
                }

            }

            @Override
            public void onRightCursorChanged(int location, String textMark) {
                String name = cheling_list.get(0).getName();
                right[0] = textMark;
                dbUsedCarManager.changeFanWei("价格", TextUtils.isEmpty(left[0]) ? name : left[0], textMark.equals("不限") ? "A" : right[0]);
                postShuJu[0] = (TextUtils.isEmpty(left[0]) ? name : left[0]) + " - " + (textMark.equals("不限") ? "A" : right[0]);
                if (postShuJu[0].contains("A")) {
                    String replace = postShuJu[0].replace("A", "不限");
                    tv_value.setText(replace);
                } else {
                    tv_value.setText(postShuJu[0]);
                }


            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromNet();
                dismissBottomMenu();
            }
        });
        if (v == ll_choice_price) {
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismissBottomMenu();
                    switch (position) {
                        case 0:
                            dbUsedCarManager.changeFanWei("价格", "0", "A");
                            break;
                        case 1:
                            dbUsedCarManager.changeFanWei("价格", "0", "3");
                            break;
                        case 2:
                            dbUsedCarManager.changeFanWei("价格", "3", "5");
                            break;
                        case 3:
                            dbUsedCarManager.changeFanWei("价格", "5", "7");
                            break;
                        case 4:
                            dbUsedCarManager.changeFanWei("价格", "7", "9");
                            break;
                        case 5:
                            dbUsedCarManager.changeFanWei("价格", "9", "12");
                            break;
                        case 6:
                            dbUsedCarManager.changeFanWei("价格", "12", "16");
                            break;
                        case 7:
                            dbUsedCarManager.changeFanWei("价格", "16", "20");
                            break;
                        case 8:
                            dbUsedCarManager.changeFanWei("价格", "20", "A");
                            break;
                    }

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
                    return true;
                }
                return false;
            }
        });
    }

    private void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Used_Car_Buy)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("area", PrefUtils.getString(this, "usedcarcity", ""))
                    .addParams("page", pager + "")
                    .addParams("submit", "1")
                    .addParams("carSourceSearch", carSourceSearch)
                    .addParams("sort_id", buyUsedCarShaiXuanBean.getZhineng())
                    .addParams("brand_id", brand_id)
                    .addParams("carSystem_id", carSystem_id)
                    .addParams("range", jiage)
                    .addParams("level_name", chexing)
                    .addParams("transmission_case", biansu)
                    .addParams("car_age", cheling)
                    .addParams("mileage", licheng)
                    .addParams("displacement", pailiang)
                    .addParams("emission_standard", paifang)
                    .addParams("color", yanse)
                    .addParams("fuel_type", ranliao)
                    .addParams("production_address", chandi)
                    .addParams("pedestal", zuowei)
                    .addParams("different_countries", guobie)
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 123:
                brand_id = data.getStringExtra("brand_id");
                carSystem_id = data.getStringExtra("carSystem_id");
                getDataFromNet();
                break;
            case 111:
                carSourceSearch = data.getStringExtra("carSourceSearch");
                getDataFromNet();
                break;
        }

    }

    private void processData(String string, final boolean isMore) {
        final BuyUsedCarlistBean buyUsedCarlistBean = JsonUtil.parseJsonToBean(string, BuyUsedCarlistBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (buyUsedCarlistBean != null) {
                    if (!isMore) {
                        if (list.size() != 0) {
                            list.clear();
                        }
                        List<BuyUsedCarlistBean.DataBean> data = buyUsedCarlistBean.getData();
                        if (data != null && data.size() != 0) {
                            list.addAll(data);
                            lv_list.setAdapter(buyCarAdapter);
                            ll_usedcar_empty.setVisibility(View.GONE);
                        } else {
                            ll_usedcar_empty.setVisibility(View.VISIBLE);
                        }
                        buyCarAdapter.notifyDataSetChanged();
                    } else {
                        data = buyUsedCarlistBean.getData();
                        if (data != null) {
                            list.addAll(data);//追加更多数据
                            buyCarAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                dbUsedCarManager.cleanAllState();
                finish();
                break;
            case R.id.tv_search_title:
                Intent intent1 = new Intent(App.context, UsedCarSearchActivity.class);
                startActivityForResult(intent1, 111);
                break;
            case R.id.ll_choice_carstyle:
                Intent intent = new Intent(App.context, PinPaiActivity.class);
                rg_practice.clearCheck();
                startActivityForResult(intent, 123);
                break;
            case R.id.ll_choice_shaixuan:
                Intent intent2 = new Intent(App.context, GaoJiShaiXuanActivity.class);
                intent2.putExtra("cartype", "buy");
                intent2.putExtra("qu_time_date","");
                intent2.putExtra("huan_time_date","");
                intent2.putExtra("store_id_usedcar","");
                intent2.putExtra("duration","");
                intent2.putExtra("qu_city_usedcar","");
                intent2.putExtra("huan_city_usedcar","");
                intent2.putExtra("shangMenQu","");
                intent2.putExtra("shangMenHuan","");
                startActivity(intent2);
                rg_practice.clearCheck();
                break;
            case R.id.ll_choice_zhineng:
                changPopState(ll_choice_zhineng, zhineng);
                break;
            case R.id.ll_choice_price:
                showBottomMenuGrid(ll_choice_price);
                break;
        }
    }
    private class BuyCarAdapter extends BaseCommonAdapter {
        public BuyCarAdapter(List<BuyUsedCarlistBean.DataBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }
        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            BuyUsedCarlistBean.DataBean similarBean = (BuyUsedCarlistBean.DataBean) item;
            String just_side_img = similarBean.getJust_side_img();
            String status = similarBean.getStatus();
            String car_allname = similarBean.getCar_allname();
            //月供
            String loan_month = similarBean.getLoan_month();
            //首付
            String loan_first = similarBean.getLoan_first();
            //上牌时间公里数
            String last_mileage = similarBean.getLast_mileage();
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
            viewHolder.setText(R.id.tv_money_buy_loan, loan_first + "  " + loan_month);
            viewHolder.setText(R.id.tv_shangpan_gongli, "行驶里程："+last_mileage);
            viewHolder.setText(R.id.tv_car_price, car_price );
            viewHolder.setText(R.id.tv_new_car_price,new_car_price);
            viewHolder.setText(R.id.tv_rent_price,rent_price);
            viewHolder.setText(R.id.tv_new_rent_price, new_rent_price);
            if ("0".equals(rent_status)) {
                viewHolder.changeLinearLayoutInVisible(R.id.ll_rent_content);
            } else if ("1".equals(rent_status) ) {
                viewHolder.changeLinearLayoutVisible(R.id.ll_rent_content);
            }
        }
    }
    private class PriceAdapter extends BaseCommonAdapter {

        public PriceAdapter(List<String> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            viewHolder.setText(R.id.tv_gv_content, (String) item);

        }
    }
}
