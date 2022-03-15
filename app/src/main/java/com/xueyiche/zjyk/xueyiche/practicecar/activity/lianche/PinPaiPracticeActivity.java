package com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.CarBean;
import com.xueyiche.zjyk.xueyiche.constants.bean.PinPaiBean;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.CarAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.PinPaiAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.view.QuickIndexBar;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.usedcar.view.MyDrawerLayout;
import com.xueyiche.zjyk.xueyiche.utils.ACache;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2019/9/9.
 */
public class PinPaiPracticeActivity extends BaseActivity implements NetBroadcastReceiver.netEventHandler, View.OnClickListener {
    private TextView tv_login_back,tv_wenxintishi;
    private LinearLayout ll_exam_back;
    //选品牌
    private ListView listview_all_car;
    private ListView right_listview;
    private QuickIndexBar mLetterBar;
    private RelativeLayout right;
    private TextView tv_car_title;
    private List<PinPaiBean.ContentBean> content;
    private PinPaiAdapter pinPaiYiJiAdapter;
    private List<CarBean.ContentBean> content1;
    private MyDrawerLayout drawerlayout;
    private LinearLayout ll_cehua;

    @Override
    protected int initContentView() {
        return R.layout.pinpai_practice_activity;
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_wenxintishi);
        tv_login_back.setText("品牌约车");
        tv_wenxintishi.setVisibility(View.VISIBLE);
        tv_wenxintishi.setText("全部车型");
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        listview_all_car = (ListView) view.findViewById(R.id.listview_all_car);
        right_listview = (ListView) view.findViewById(R.id.right_listview);
        right = (RelativeLayout) view.findViewById(R.id.right);
        TextView overlay = (TextView) view.findViewById(R.id.tv_letter);
        tv_car_title = (TextView) view.findViewById(R.id.tv_car_title);
        mLetterBar = (QuickIndexBar) view.findViewById(R.id.bar_pinpai);
        drawerlayout = (MyDrawerLayout) view.findViewById(R.id.drawerlayout);
        drawerlayout.setScrimColor(Color.TRANSPARENT);
        ll_cehua = (LinearLayout) view.findViewById(R.id.ll_cehua);
        WindowManager wm = this.getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams para = ll_cehua.getLayoutParams();//获取drawerlayout的布局
        para.width = width1 / 3 * 2;//修改宽度
        para.height = height1;//修改高度
        ll_cehua.setLayoutParams(para); //设置修改后的布局。
        mLetterBar.setOverlay(overlay);
        NetBroadcastReceiver.mListeners.add(this);
        right_listview.setDivider(new ColorDrawable(getResources().getColor(R.color.all_line_color)));

    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
        listview_all_car.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerlayout.closeDrawers();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        listview_all_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pinpai_id = content.get(position).getId();
                String name = content.get(position).getName();
                tv_car_title.setText(name);
                String asString = ACache.get(PinPaiPracticeActivity.this).getAsString("CAR");
                if (!TextUtils.isEmpty(asString)) {
                    process(asString);
                }
                if (XueYiCheUtils.IsHaveInternet(App.context)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.CAR)
                            .addParams("equipment_type", LoginUtils.getId(PinPaiPracticeActivity.this))
                            .addParams("brandid", pinpai_id + "")
                            .addParams("area_id", PrefUtils.getParameter("area_id"))
                            .build().execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                ACache.get(PinPaiPracticeActivity.this).put("CAR", string);
                                process(string);
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
        });
        right_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (XueYiCheUtils.IsHaveInternet(PinPaiPracticeActivity.this)) {
                    String seriesname = content1.get(position).getSeriesname();
                    String seriesid = content1.get(position).getSeriesid();
                    String name = content1.get(position).getName();
                    Intent intent = new Intent();
                    intent.putExtra("seriesname", seriesname);
                    intent.putExtra("series_id", seriesid);
                    intent.putExtra("name", name);
                    setResult(333, intent);
                    finish();
                } else {
                    showToastShort(StringConstants.CHECK_NET);
                }

            }
        });
    }

    @Override
    protected void initData() {
        getDataFromNet();

    }

    private void process(String asString) {
        CarBean carBean = JsonUtil.parseJsonToBean(asString, CarBean.class);
        int code = carBean.getCode();
        if (200 == code) {
            content1 = carBean.getContent();
            if (content1.size() != 0 && content1 != null) {
                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        drawerlayout.openDrawer(Gravity.RIGHT);
                        pinPaiYiJiAdapter.notifyDataSetChanged();
                        right_listview.setAdapter(new CarAdapter(content1));
                    }
                });
            }
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_exam_back:
                intent.putExtra("seriesname", "车辆品牌");
                intent.putExtra("series_id", "");
                intent.putExtra("name", "");
                setResult(333, intent);
                finish();
                break;
            case R.id.tv_wenxintishi:
                intent.putExtra("seriesname", "全部车型");
                intent.putExtra("series_id", "");
                intent.putExtra("name", "");
                setResult(333, intent);
                finish();
                break;
        }
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("seriesname", "");
            intent.putExtra("series_id", "");
            intent.putExtra("name", "");
            setResult(333, intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getDataFromNet() {
        String asString = ACache.get(this).getAsString("PPLB");
        if (!TextUtils.isEmpty(asString)) {
            processData(asString);
        }
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.PPLB)
                    .addParams("equipment_type", LoginUtils.getId(this))
                    .addParams("area_id", PrefUtils.getParameter("area_id"))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        ACache.get(PinPaiPracticeActivity.this).put("PPLB", string);
                        processData(string);
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


    private void processData(String string) {
        PinPaiBean pinpaibean = JsonUtil.parseJsonToBean(string, PinPaiBean.class);
        int code = pinpaibean.getCode();
        if (200 == code) {
            content = pinpaibean.getContent();
            if (content.size() != 0) {
                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pinPaiYiJiAdapter = new PinPaiAdapter(content);
                        mLetterBar.setOnLetterChangedListener(new QuickIndexBar.OnLetterChangedListener() {
                            @Override
                            public void onLetterChanged(String letter) {
                                int position = pinPaiYiJiAdapter.getLetterPosition(letter);
                                listview_all_car.setSelection(position + 1);
                            }
                        });

                        listview_all_car.setAdapter(pinPaiYiJiAdapter);
                    }
                });
            }
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
}
