package com.xueyiche.zjyk.xueyiche.carlife.carbaoxian;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
import com.xueyiche.zjyk.xueyiche.constants.bean.CarBean;
import com.xueyiche.zjyk.xueyiche.constants.bean.PinPaiBean;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.CarAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.PinPaiAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.view.QuickIndexBar;
import com.xueyiche.zjyk.xueyiche.usedcar.view.MyDrawerLayout;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2016/11/23.
 */
public class CarStyle extends BaseActivity implements View.OnClickListener {
    //选品牌
    private TextView tv_title;
    private LinearLayout ll_exam_back;
    private ListView listview_all_car;
    private ListView right_listview;
    private QuickIndexBar mLetterBar;
    private RelativeLayout right;
    private TextView tv_car_title;
    private TranslateAnimation mShowAction;
    private boolean isopen = false;
    private List<PinPaiBean.ContentBean> content;
    private static final int PINPAI_RESULT_REQUEST = 001;
    private List<CarBean.ContentBean> content1;
    private PinPaiAdapter pinPaiYiJiAdapter;
    private MyDrawerLayout drawerlayout;



    @Override
    protected int initContentView() {
        return R.layout.practice_pinpai_card_two;
    }

    @Override
    protected void initView() {
        tv_title = (TextView) view.findViewById(R.id.pinpai_include).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.pinpai_include).findViewById(R.id.ll_exam_back);
        listview_all_car = (ListView) view.findViewById(R.id.listview_all_car);
        right_listview = (ListView) view.findViewById(R.id.right_listview);
        right = (RelativeLayout) view.findViewById(R.id.right);
        drawerlayout = (MyDrawerLayout) view.findViewById(R.id.drawerlayout);
        TextView overlay = (TextView) findViewById(R.id.tv_letter);
        tv_car_title = (TextView) findViewById(R.id.tv_car_title);
        ll_exam_back.setOnClickListener(this);
        mLetterBar = (QuickIndexBar) findViewById(R.id.bar_pinpai);
        mLetterBar.setOverlay(overlay);
        drawerlayout.setScrimColor(Color.TRANSPARENT);
        listview_all_car.setDivider(new ColorDrawable(getResources().getColor(R.color.all_line_color)));
        listview_all_car.setDividerHeight(1);
        right_listview.setDivider(new ColorDrawable(getResources().getColor(R.color.all_line_color)));
        right_listview.setDividerHeight(1);
    }

    @Override
    protected void initListener() {
        listview_all_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mShowAction == null) {
                    animShow();
                }
                if (!isopen) {
                    right.startAnimation(mShowAction);
                    right.setVisibility(View.VISIBLE);
                    isopen = true;
                }
                int pinpai_id = content.get(position).getId();
                String logo = content.get(position).getLogo();
                String name = content.get(position).getName();
                tv_car_title.setText(name);
                if (XueYiCheUtils.IsHaveInternet(App.context)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.PinPaiCheXi)
                            .addParams("equipment_type", LoginUtils.getId(CarStyle.this))
                            .addParams("brandid", pinpai_id + "")
                            .build().execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
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
                if (XueYiCheUtils.IsHaveInternet(CarStyle.this)) {
                    CarBean.ContentBean contentBean = content1.get(position);
                    if (contentBean != null) {
                        String seriesname = contentBean.getSeriesname();
                        String seriesid = contentBean.getSeriesid();
                        int brandid = contentBean.getBrandid();
                        String name = contentBean.getName();
                        Intent intent = new Intent();
                        intent.putExtra("seriesname", seriesname);
                        intent.putExtra("brandid", brandid);
                        intent.putExtra("name", name);
                        setResult(PINPAI_RESULT_REQUEST,intent);
                        finish();
                    }
                }
            }
        });
        listview_all_car.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (drawerlayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerlayout.closeDrawers();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    private void process(String asString) {
        CarBean carBean = JsonUtil.parseJsonToBean(asString, CarBean.class);
        content1 = carBean.getContent();
        if (content1.size() != 0 && content1 != null) {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    drawerlayout.openDrawer(Gravity.RIGHT);
                    right_listview.setAdapter(new CarAdapter(content1));
                }
            });
        }

    }

    private void animShow() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(300);
    }

    @Override
    protected void initData() {
        tv_title.setText("品牌选择");
        getDataFromNet();

    }
    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("seriesname", "");
            intent.putExtra("brandid", 0);
            intent.putExtra("name", "");
            setResult(PINPAI_RESULT_REQUEST,intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                Intent intent = new Intent();
                intent.putExtra("seriesname", "");
                intent.putExtra("brandid", 0);
                intent.putExtra("name", "");
                setResult(PINPAI_RESULT_REQUEST,intent);
                finish();
                break;

        }
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.PJPPLB)
                    .addParams("equipment_type", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
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
                            listview_all_car.setSelection(position);

                        }
                    });

                    listview_all_car.setAdapter(pinPaiYiJiAdapter);
                }
            });
        }
    }

}
