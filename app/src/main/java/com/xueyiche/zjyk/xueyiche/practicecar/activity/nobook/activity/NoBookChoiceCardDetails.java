package com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview.LoopViewPager;
import com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview.ProgressPageIndicator;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.ObservableScrollView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.bean.WZDriverDetailsBean;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.bean.WZTCBean;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhanglei on 2016/11/2.
 */
public class NoBookChoiceCardDetails extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler, ObservableScrollView.ScrollViewListener {
    //教练详情
    private ImageView iv_shop_back_all, iv_shop_shoucang_all;
    private TextView tv_pingjia_more, tv_shop_content_money, tv_driver_school_briefing;
    private RelativeLayout rl_shop_top_backg;
    private ObservableScrollView os_shop;
    private ImageView iv_shop_share;
    private int width;
    private Handler mHandler;
    private TimerTask mTimerTask;
    private Timer mTimer;
    private static final int MSG_NEXT = 1;
    private static final int MSG_NEXT_ONE = 2;
    private ProgressPageIndicator progressPageIndicator;
    private TextView tv_pingjia_number, tv_shop_type;
    private Button bt_carlive_shop_buy;
    private LinearLayout shop_content_include;
    private ImageView iv_shop_phone, iv_shop_daohang, iv_label_one, iv_label_two, iv_label_three, iv_label_four;
    private TextView tv_shop_name, tv_label_one_name, tv_label_two_name, tv_label_three_name, tv_label_four_name;
    private LoopViewPager vp_shop_lunbo;
    private WZDriverDetailsBean driverDetails;
    private WZDriverDetailsBean.ContentBean content;
    private String driver_id;
    private boolean isCheck;
    private TextView tv_driver_seriesname;
    private TextView tv_hour_price;
    private String collect_status;
    private TextView tv_hour_price_bottom;
    private String user_id;

    private LinearLayout ll_youzheng_visible, ll_wuzheng_visible;
    private ImageView iv_shop_kefu;
    private String type;
    private AdListView lv_taocan,lv_pingjia;
    private TaoCanAdapter taoCanAdapter;
    private List<WZTCBean.ContentBean> content_taocan;
    private ListAdapter listAdapter;

    @Override
    protected int initContentView() {
        return R.layout.nobook_choice_card_details;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        driver_id = intent.getStringExtra("driver_id");
        type = intent.getStringExtra("type");
        user_id = PrefUtils.getString(App.context, "user_id", "0");
        //大家怎么说 评价
        tv_pingjia_number = (TextView) view.findViewById(R.id.tv_pingjia_number);
        tv_pingjia_number.setText("评价（）");
        //SHOP_TOP
        iv_shop_back_all = (ImageView) view.findViewById(R.id.shop_top_include).findViewById(R.id.iv_shop_back_all);
        //收藏
        iv_shop_shoucang_all = (ImageView) view.findViewById(R.id.shop_top_include).findViewById(R.id.iv_shop_shoucang_all);
        iv_shop_kefu = (ImageView) view.findViewById(R.id.shop_top_include).findViewById(R.id.iv_shop_kefu);
        tv_shop_type = (TextView) view.findViewById(R.id.tv_shop_type);
        tv_shop_type.setText("教练");
        iv_shop_shoucang_all.setOnClickListener(this);
        //分享
        iv_shop_share = (ImageView) view.findViewById(R.id.shop_top_include).findViewById(R.id.iv_shop_share);
        iv_shop_share.setOnClickListener(this);
        //轮播图
        shop_content_include = (LinearLayout) view.findViewById(R.id.shop_content_include);
        iv_shop_phone = (ImageView) view.findViewById(R.id.iv_shop_phone);
        iv_shop_phone.setVisibility(View.GONE);
        iv_shop_daohang = (ImageView) view.findViewById(R.id.iv_shop_daohang);
        iv_shop_daohang.setVisibility(View.GONE);
        //
        iv_label_one = (ImageView) view.findViewById(R.id.iv_label_one);
        tv_label_one_name = (TextView) view.findViewById(R.id.tv_label_one_name);
        iv_label_one.setImageResource(R.mipmap.driver_car_type);
        iv_label_two = (ImageView) view.findViewById(R.id.iv_label_two);
        tv_label_two_name = (TextView) view.findViewById(R.id.tv_label_two_name);
        iv_label_two.setImageResource(R.mipmap.driver_year);
        iv_label_three = (ImageView) view.findViewById(R.id.iv_label_three);
        tv_label_three_name = (TextView) view.findViewById(R.id.tv_shop_people_number);
        iv_label_three.setImageResource(R.mipmap.driver_on_of);
        iv_label_four = (ImageView) view.findViewById(R.id.iv_label_four);
        tv_label_four_name = (TextView) view.findViewById(R.id.tv_shop_address);
        iv_label_four.setImageResource(R.mipmap.carlive_buynumber);
        //
        tv_driver_seriesname = (TextView) view.findViewById(R.id.tv_driver_seriesname);
        tv_shop_content_money = (TextView) view.findViewById(R.id.tv_shop_content_money);
        tv_hour_price_bottom = (TextView) view.findViewById(R.id.tv_hour_price_bottom);

        tv_shop_content_money.setText("/小时起");
        tv_hour_price = (TextView) view.findViewById(R.id.tv_hour_price);
        ll_youzheng_visible = (LinearLayout) view.findViewById(R.id.ll_youzheng_visible);
        ll_wuzheng_visible = (LinearLayout) view.findViewById(R.id.ll_wuzheng_visible);
        ll_wuzheng_visible.setVisibility(View.VISIBLE);
        tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
        vp_shop_lunbo = (LoopViewPager) view.findViewById(R.id.vp_shop_lunbo);
        os_shop = (ObservableScrollView) view.findViewById(R.id.os_shop);
        rl_shop_top_backg = (RelativeLayout) view.findViewById(R.id.rl_shop_top_backg);
        bt_carlive_shop_buy = (Button) view.findViewById(R.id.bt_carlive_shop_buy);
        bt_carlive_shop_buy.setOnClickListener(this);
        progressPageIndicator = (ProgressPageIndicator) findViewById(R.id.dotsL);
        tv_pingjia_more = (TextView) view.findViewById(R.id.tv_pingjia_more);
        lv_taocan = (AdListView) view.findViewById(R.id.lv_taocan);
        lv_pingjia = (AdListView) view.findViewById(R.id.lv_pingjia);
        lv_pingjia.setFocusable(false);
        lv_taocan.setFocusable(false);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        iv_shop_back_all.setOnClickListener(this);
        tv_pingjia_more.setOnClickListener(this);
        iv_shop_phone.setOnClickListener(this);
        iv_shop_kefu.setOnClickListener(this);
        NetBroadcastReceiver.mListeners.add(this);
        os_shop.setScrollViewListener(NoBookChoiceCardDetails.this);
        os_shop.smoothScrollBy(0,0);
        os_shop.smoothScrollTo(0,0);


    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void onResume() {
        super.onResume();
//        getDataFromNet();
    }

//    //选择下一个卡片
//    public void next() {
//        if (content != null) {
//            List<DriverDetails.ContentBean.FengcaiBean> fengcai = content.getFengcai();
//            if (fengcai != null && fengcai.size() > 0) {
//                int pos = vp_shop_lunbo.getCurrentItem();
//                pos += 1;
//                vp_shop_lunbo.setCurrentItem(pos);
//            }
//        }
//    }
//
//    //启动动画
//    public void startTimer() {
//        stopTimer();
//        mTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                mHandler.sendEmptyMessage(MSG_NEXT);
//            }
//        };
//        mTimer = new Timer(true);
//        mTimer.schedule(mTimerTask, 1000, 3000);
//    }

    //停止动画
    public void stopTimer() {
        mHandler.removeMessages(MSG_NEXT);
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

//    private void lunbo() {
//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case MSG_NEXT:
//                        next();
//                        break;
//                    case MSG_NEXT_ONE:
//
//                        break;
//                }
//                super.handleMessage(msg);
//            }
//        };
//        startTimer();
//        vp_shop_lunbo.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                    case MotionEvent.ACTION_MOVE:
//                        stopTimer();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        startTimer();
//                        break;
//                }
//                return false;
//            }
//        });
//    }


    @Override
    protected void initData() {
        getDataFromNet();
    }

    public void getDataFromNet() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        showProgressDialog(false);
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post()
                    .url(AppUrl.WZDriver_Details)
                    .addParams("driver_id", driver_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("pager", "1")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                processData(string);
                            }
                        });
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
            OkHttpUtils.post()
                    .url(AppUrl.WZ_TC_Practice_List)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        WZTCBean wztcBean = JsonUtil.parseJsonToBean(string, WZTCBean.class);
                        if (wztcBean != null) {
                            int code = wztcBean.getCode();
                            content_taocan = wztcBean.getContent();
                            if (200 == code) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (content_taocan.size()>0) {
                                            tv_hour_price_bottom.setText(content_taocan.get(0).getHour_price());
                                        }
                                        taoCanAdapter = new TaoCanAdapter(content_taocan,App.context,R.layout.wz_taocan_list_iyem);
                                        lv_taocan.setAdapter(taoCanAdapter);

                                    }
                                });
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
        } else {
            stopProgressDialog();
            showToastLong(StringConstants.CHECK_NET);
        }
    }

    private void processData(String string) {
        driverDetails = JsonUtil.parseJsonToBean(string, WZDriverDetailsBean.class);
        if (driverDetails != null) {
            content = driverDetails.getContent();
            if (content != null) {
                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        //轮播
//                        List<DriverDetails.ContentBean.FengcaiBean> fengcai = content.getFengcai();
//                        if (fengcai != null && fengcai.size() > 0) {
//                            vp_shop_lunbo.setAdapter(new DriverCardPagAdapter(fengcai));
//                            progressPageIndicator.setViewPager(vp_shop_lunbo);
//                            progressPageIndicator.setRadius(width / fengcai.size());
//                            lunbo();
//                        }

                        daJiaZenMeShuo();
                        collect_status = content.getCollect_status();
                        String driver_name = content.getDriver_name();

                        tv_shop_name.setText(driver_name);
                        if ("1".equals(collect_status)) {
                            //未收藏
                            isCheck = false;
                            iv_shop_shoucang_all.setImageResource(R.mipmap.shoucang_shop);
                        } else if ("2".equals(collect_status)) {
                            isCheck = true;
                            iv_shop_shoucang_all.setImageResource(R.mipmap.quxiaoshouchang_driver);
                        }
                        tv_label_one_name.setText("轿车 皮卡");
                        String driving_year = content.getDriving_year();
                        tv_label_two_name.setText(driving_year);
                        String on_off = content.getOn_off();
                        tv_label_three_name.setText(on_off);
                        String order_count = content.getOrder_count();
                        tv_label_four_name.setText(order_count);
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_shop_shoucang_all:
                //收藏
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    collction();
                }
                break;
            case R.id.tv_pingjia_more:
                Intent intent2 = new Intent(App.context, NoBookShowPingJia.class);
                if (!TextUtils.isEmpty(driver_id)) {
                    intent2.putExtra("refer_id", driver_id);
                    startActivity(intent2);
                }
                break;
            case R.id.iv_shop_back_all:
                finish();
                break;
            case R.id.iv_shop_kefu:
                openActivity(KeFuActivity.class);
                break;
            case R.id.iv_shop_share:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
//                    if (content != null) {
//                        String driver_name = content.getDriver_name();
//                        String car_url = content.getFengcai().get(0).getCar_url();
//                        if (!TextUtils.isEmpty(driver_name) && !TextUtils.isEmpty(car_url)) {
//                            XueYiCheUtils.showShareAppCommon(App.context, NoBookChoiceCardDetails.this, StringConstants.SHARED_TITLE + driver_name,
//                                    "http://xueyiche.cn/share/youzhenglianche.php?driver_id=" + driver_id,
//                                    StringConstants.SHARED_TEXT, car_url, "http://xueyiche.cn/share/youzhenglianche.php?driver_id=" + driver_id);
//                        }
//                    }
                }
                break;
            case R.id.bt_carlive_shop_buy:
                Intent intent = new Intent(this, NoBookSubmitZhiJiePractice.class);
                intent.putExtra("driver_id", driver_id);
                startActivity(intent);
                break;
        }
    }

    private void collction() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            final int height = shop_content_include.getHeight();
            final int measuredHeight = os_shop.getScrollY() + 200;
            if (!isCheck) {
                OkHttpUtils.post().url(AppUrl.Collection_Add)
                        .addParams("user_id", user_id)
                        .addParams("device_id", LoginUtils.getId(this))
                        .addParams("refer_id", driver_id)
                        .addParams("kind_type", "1")
                        .addParams("mark_type", "0")
                        .addParams("service_id", "")
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean != null) {
                                int code = successDisCoverBackBean.getCode();
                                if (200 == code) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (measuredHeight < height) {
                                                iv_shop_shoucang_all.setImageResource(R.mipmap.quxiaoshouchang_driver);
                                            } else {
                                                iv_shop_shoucang_all.setImageResource(R.mipmap.quxiaoshouchang_driver_hei);
                                            }
                                            showToastShort("已收藏");
                                            isCheck = true;
                                        }
                                    });
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
            } else {
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
                            if (successDisCoverBackBean != null) {
                                int code = successDisCoverBackBean.getCode();
                                if (200 == code) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (measuredHeight < height) {
                                                iv_shop_shoucang_all.setImageResource(R.mipmap.shoucang_shop);
                                            } else {
                                                iv_shop_shoucang_all.setImageResource(R.mipmap.shoucang_shop_hei);
                                            }
                                            showToastShort("取消收藏");
                                            isCheck = false;
                                        }
                                    });
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
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        } else {
            initData();
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        //标题栏颜色的变化
        int height = shop_content_include.getHeight();
        if (y <= 0 || y < height * 9 / 10) {
            rl_shop_top_backg.setBackgroundColor(Color.argb(0, 0, 0, 0));//AGB由相关工具获得，或者美工提供
            iv_shop_back_all.setImageResource(R.mipmap.car_meirong_back);
            iv_shop_share.setImageResource(R.mipmap.share_shop);
            iv_shop_shoucang_all.setImageResource(R.mipmap.shoucang_shop);
            if (!TextUtils.isEmpty(collect_status)) {
                if (!isCheck) {
                    iv_shop_shoucang_all.setImageResource(R.mipmap.shoucang_shop);
                } else {
                    iv_shop_shoucang_all.setImageResource(R.mipmap.quxiaoshouchang_driver);
                }
            }
        } else if (y > height * 9 / 10 && y < height) {
            float scale = (float) y / height * 9 / 10;
            float alpha = (255 * scale);
            // 只是layout背景透明
            rl_shop_top_backg.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            if (!isCheck) {
                iv_shop_shoucang_all.setImageResource(R.mipmap.shoucang_shop_hei);
            } else {
                iv_shop_shoucang_all.setImageResource(R.mipmap.quxiaoshouchang_driver_hei);
            }
            iv_shop_back_all.setImageResource(R.mipmap.driver_school_back_hei);
            iv_shop_share.setImageResource(R.mipmap.share_shop_hei);
        } else if (y > height) {
            rl_shop_top_backg.setBackgroundColor(Color.argb(255, 255, 255, 255));
            if (!TextUtils.isEmpty(collect_status)) {
                if (!isCheck) {
                    iv_shop_shoucang_all.setImageResource(R.mipmap.shoucang_shop_hei);
                } else {
                    iv_shop_shoucang_all.setImageResource(R.mipmap.quxiaoshouchang_driver_hei);
                }
            }

        }

    }

    private void daJiaZenMeShuo() {
        if (content != null) {
            List<WZDriverDetailsBean.ContentBean.PinglunBean> pinglun = content.getPinglun();
            int comment_count = pinglun.size();
            if (pinglun != null) {
                tv_pingjia_number.setText("评价（" + comment_count + "）");
            } else {
                tv_pingjia_number.setText("评价（" + 0 + "）");
            }

            listAdapter = new ListAdapter(pinglun);
            lv_pingjia.setAdapter(listAdapter);
            if (pinglun != null && pinglun.size() > 0) {
                tv_pingjia_more.setVisibility(View.VISIBLE);
            }else {
                tv_pingjia_more.setVisibility(View.INVISIBLE);
            }
        }

    }

//    public class DriverCardPagAdapter extends PagerAdapter {
//        private List<DriverDetails.ContentBean.FengcaiBean> fengcai;
//
//        public DriverCardPagAdapter(List<DriverDetails.ContentBean.FengcaiBean> fengcai) {
//            this.fengcai = fengcai;
//        }
//
//        @Override
//        public int getCount() {
//            if (fengcai != null) {
//                return fengcai.size();
//            } else {
//                return 0;
//            }
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//
//        @Override
//        public Object instantiateItem(ViewGroup container, final int position) {
//            ImageView imageView = new ImageView(App.context);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            DriverDetails.ContentBean.FengcaiBean fengcaiBean = fengcai.get(position);
//            if (fengcaiBean != null) {
//                String car_url = fengcaiBean.getCar_url();
//                Picasso.with(App.context).load(car_url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(imageView);
//            }
//            container.addView(imageView);
//            return imageView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//    }

    private class TaoCanAdapter extends BaseCommonAdapter {

        public TaoCanAdapter(List<WZTCBean.ContentBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            WZTCBean.ContentBean packageTaocanBean = (WZTCBean.ContentBean) item;
            String content = packageTaocanBean.getHour_price();
            String title = packageTaocanBean.getRemark();
            viewHolder.setText(R.id.tv_title, title);
            viewHolder.setText(R.id.tv_content,"¥"+ content);
        }
    }
    private class ListAdapter extends BaseAdapter {
     private   List<WZDriverDetailsBean.ContentBean.PinglunBean> list;
//
        public ListAdapter(List<WZDriverDetailsBean.ContentBean.PinglunBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size()>2?2:list.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DriverSchoolTrainerListViewHolder driverSchoolTrainerListViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.trainer_list_item, null);
                driverSchoolTrainerListViewHolder = new DriverSchoolTrainerListViewHolder(view);
                view.setTag(driverSchoolTrainerListViewHolder);
            } else {
                driverSchoolTrainerListViewHolder = (DriverSchoolTrainerListViewHolder) view.getTag();
            }

            driverSchoolTrainerListViewHolder.rb_one_now.setStepSize(0.5f);
            driverSchoolTrainerListViewHolder.rb_teach_quality.setStepSize(0.5f);
            driverSchoolTrainerListViewHolder.rb_service_attitude.setStepSize(0.5f);
            WZDriverDetailsBean.ContentBean.PinglunBean contentBean = list.get(i);
            if (contentBean != null) {
                final String all_evaluate = contentBean.getAll_evaluate();
                final String user_name = contentBean.getNickname();
                final String head_img = contentBean.getHead_img();
                final String service_attitude = contentBean.getService_attitude();
                final String technological_level = contentBean.getTechnological_level();
                final String system_time = contentBean.getContent_time();
                final String content = contentBean.getContent();
                final String order_number = contentBean.getOrder_number();
                int praise_count = contentBean.getPraise_count();
                int reply_count = contentBean.getReply_count();

                if (!TextUtils.isEmpty(all_evaluate)) {
                    float star = Float.parseFloat(all_evaluate);
                    driverSchoolTrainerListViewHolder.rb_one_now.setRating(star);
                    driverSchoolTrainerListViewHolder.tv_fenshu.setText(all_evaluate);
                }
                if (!TextUtils.isEmpty(technological_level)) {
                    float star = Float.parseFloat(technological_level);
                    driverSchoolTrainerListViewHolder.rb_teach_quality.setRating(star);
                }
                if (!TextUtils.isEmpty(service_attitude)) {
                    float star = Float.parseFloat(service_attitude);
                    driverSchoolTrainerListViewHolder.rb_service_attitude.setRating(star);
                }
                if (!TextUtils.isEmpty(head_img)) {
                    Picasso.with(App.context).load(head_img).placeholder(R.mipmap.hot_train_head).error(R.mipmap.hot_train_head).into(driverSchoolTrainerListViewHolder.iv_head);
                }
                if (!TextUtils.isEmpty(user_name)) {
                    driverSchoolTrainerListViewHolder.tv_name.setText(user_name);
                }
                if (!TextUtils.isEmpty(""+reply_count)) {
                    driverSchoolTrainerListViewHolder.tv_pinglun_number.setText(""+reply_count);
                }
                if (!TextUtils.isEmpty(""+praise_count)) {
                    driverSchoolTrainerListViewHolder.tv_dianzan_number.setText(""+praise_count);
                }
                if (!TextUtils.isEmpty(content)) {
                    driverSchoolTrainerListViewHolder.tv_pingjia_content.setText(content);
                }
                if (!TextUtils.isEmpty(system_time)) {
                    driverSchoolTrainerListViewHolder.tv_date.setText(system_time);
                }
                driverSchoolTrainerListViewHolder.ll_number.setVisibility(View.INVISIBLE);
//                driverSchoolTrainerListViewHolder.ll_all.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(App.context, JiaoLianPJActivity.class);
//                        intent.putExtra("head_img", head_img);
//                        intent.putExtra("user_name", user_name);
//                        intent.putExtra("all_evaluate", all_evaluate);
//                        intent.putExtra("service_attitude", service_attitude);
//                        intent.putExtra("technological_level", technological_level);
//                        intent.putExtra("content", content);
//                        intent.putExtra("system_time", system_time);
//                        intent.putExtra("driver_id", driver_id);
//                        intent.putExtra("order_number", order_number);
//                        startActivity(intent);
//                    }
//                });

            }
            return view;
        }

        private class DriverSchoolTrainerListViewHolder {
            private CircleImageView iv_head;
            private TextView tv_name, tv_fenshu, tv_date, tv_pingjia_content, tv_pinglun_number, tv_dianzan_number;
            private RatingBar rb_one_now, rb_teach_quality, rb_service_attitude;
            private LinearLayout ll_all,ll_number;

            public DriverSchoolTrainerListViewHolder(View view) {
                iv_head = (CircleImageView) view.findViewById(R.id.iv_head);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                rb_one_now = (RatingBar) view.findViewById(R.id.rb_one_now);
                rb_teach_quality = (RatingBar) view.findViewById(R.id.rb_teach_quality);
                rb_service_attitude = (RatingBar) view.findViewById(R.id.rb_service_attitude);
                tv_fenshu = (TextView) view.findViewById(R.id.tv_fenshu);
                tv_pingjia_content = (TextView) view.findViewById(R.id.tv_pingjia_content);
                tv_pinglun_number = (TextView) view.findViewById(R.id.tv_pinglun_number);
                tv_dianzan_number = (TextView) view.findViewById(R.id.tv_dianzan_number);
                tv_date = (TextView) view.findViewById(R.id.tv_date);
                ll_all = (LinearLayout) view.findViewById(R.id.ll_all);
                ll_number = (LinearLayout) view.findViewById(R.id.ll_number);

            }
        }
    }
}
