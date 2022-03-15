package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.driverschool.view.ImageShower;
import com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview.LoopViewPager;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.MyGridView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.submit.UsedCarSubmitIndent;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarInfoBean;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarSuccessBean;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZL on 2018/8/2.
 */
public class UsedCarContentActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack, ll_rent, ll_peizhi;
    private TextView tvTitle;
    private MyGridView gv_used_car_miaoshu, gv_used_car_liangdian;
    private ScrollView scroll_view;
    private AdListView lv_used_car_picture, alv_hot_used_car;
    //名字
    private TextView tv_name;
    //出售的现价
    private TextView tv_sell_now_price;
    //出售的原价
    private TextView tv_sell_price_old;
    //租的现价
    private TextView tv_rent_now_price, tv_lunbo_number;
    //租的原价
    private TextView tv_rent_price_old;
    private TextView tv_rent_car;
    private TextView tv_order_look_car;
    private TextView tv_loan_jieshsao;
    private CheckBox cb_collection;
    private TextView tv_collection;
    private ImageView iv_caidan;
    private String carsource_id;
    private int rent_status;
    private List<UsedCarInfoBean.DataBean.SimilarBean> similar;
    private String id_status;
    private String detection_address;
    private String qu_time_date, huan_city, huan_time_date, qu_city, store_id, shangMenQu, shangMenHuan, duration;
    private String qu_latitude_usedcar;
    private String qu_longitude_usedcar;
    private String qu_name_usedcar;
    private String huan_latitude_usedcar;
    private String huan_longitude_usedcar;
    private String huan_name_usedcar;
    private String car_allname, violated_deposit, car_deposit, shop_name;
    private List<UsedCarInfoBean.DataBean.ContentImgBean> content_img;
    private String about_status;
    private String rent_whether;
    private String rent_price;
    private RelativeLayout rl_look_baogao;
    private String header_img;
    private View xian;
    private String htmlUrl;
    private LoopViewPager vp_shop_lunbo;
    private Handler mHandler;
    private TimerTask mTimerTask;
    private Timer mTimer;
    private static final int MSG_NEXT = 1;
    private static final int MSG_NEXT_ONE = 2;
    private List<String> twocar_img;

    @Override
    protected int initContentView() {
        return R.layout.used_car_content_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        iv_caidan = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_caidan);
        iv_caidan.setVisibility(View.VISIBLE);
        iv_caidan.setImageResource(R.mipmap.share_shop_hei);
        gv_used_car_miaoshu = (MyGridView) view.findViewById(R.id.gv_used_car_miaoshu);
        gv_used_car_liangdian = (MyGridView) view.findViewById(R.id.gv_used_car_liangdian);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        rl_look_baogao = (RelativeLayout) view.findViewById(R.id.rl_look_baogao);
        ll_rent = (LinearLayout) view.findViewById(R.id.ll_rent);
        ll_peizhi = (LinearLayout) view.findViewById(R.id.ll_peizhi);
        tv_rent_price_old = (TextView) view.findViewById(R.id.tv_rent_price_old);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_loan_jieshsao = (TextView) view.findViewById(R.id.tv_loan_jieshsao);
        vp_shop_lunbo = (LoopViewPager) view.findViewById(R.id.vp_shop_lunbo);
        tv_rent_now_price = (TextView) view.findViewById(R.id.tv_rent_now_price);
        tv_sell_price_old = (TextView) view.findViewById(R.id.tv_sell_price_old);
        tv_sell_now_price = (TextView) view.findViewById(R.id.tv_sell_now_price);
        tv_lunbo_number = (TextView) view.findViewById(R.id.tv_lunbo_number);
        tv_rent_car = (TextView) view.findViewById(R.id.tv_rent_car);
        xian = view.findViewById(R.id.xian);
        tv_collection = (TextView) view.findViewById(R.id.tv_collection);
        tv_order_look_car = (TextView) view.findViewById(R.id.tv_order_look_car);
        cb_collection = (CheckBox) view.findViewById(R.id.cb_collection);
        lv_used_car_picture = (AdListView) view.findViewById(R.id.lv_used_car_picture);
        alv_hot_used_car = (AdListView) view.findViewById(R.id.alv_hot_used_car);
        scroll_view.smoothScrollBy(0, 0);
        scroll_view.smoothScrollTo(0, 0);
        alv_hot_used_car.setVerticalScrollBarEnabled(false);
        alv_hot_used_car.setFastScrollEnabled(false);
        alv_hot_used_car.setFocusable(false);
        lv_used_car_picture.setVerticalScrollBarEnabled(false);
        lv_used_car_picture.setFastScrollEnabled(false);
        lv_used_car_picture.setFocusable(false);

    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        rl_look_baogao.setOnClickListener(this);
        tv_loan_jieshsao.setOnClickListener(this);
        tv_order_look_car.setOnClickListener(this);
        iv_caidan.setOnClickListener(this);
        tv_rent_car.setOnClickListener(this);
        vp_shop_lunbo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (twocar_img != null && twocar_img.size() > 0) {
                    tv_lunbo_number.setText(position + 1 + "/" + twocar_img.size());

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        cb_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shouCang();
            }
        });

        alv_hot_used_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UsedCarInfoBean.DataBean.SimilarBean carSourceHotBean = similar.get(i);
                if (carSourceHotBean != null) {
                    int id = carSourceHotBean.getId();
                    String htmlUrl = carSourceHotBean.getHtmlUrl();
                    if (!TextUtils.isEmpty("" + id)) {
                        Intent intent1 = new Intent(App.context, UsedCarContentActivity.class);
                        intent1.putExtra("carsource_id", id + "");
                        intent1.putExtra("htmlUrl", htmlUrl);
                        intent1.putExtra("cartype", "buy");
                        intent1.putExtra("qu_time_date", "");
                        intent1.putExtra("huan_time_date", "");
                        intent1.putExtra("store_id_usedcar", "");
                        intent1.putExtra("duration", "");
                        intent1.putExtra("qu_city_usedcar", "");
                        intent1.putExtra("huan_city_usedcar", "");
                        intent1.putExtra("shangMenQu", "");
                        intent1.putExtra("shangMenHuan", "");
                        startActivity(intent1);
                    }
                }
            }
        });
    }

    //启动动画
    public void startTimer() {
        stopTimer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_NEXT);
            }
        };
        mTimer = new Timer(true);
        mTimer.schedule(mTimerTask, 1000, 5000);
    }

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

    //选择下一个卡片
    public void next() {
        if (twocar_img != null && twocar_img.size() > 0) {
            int pos = vp_shop_lunbo.getCurrentItem();
            pos += 1;
            vp_shop_lunbo.setCurrentItem(pos);

        }
    }

    private void shouCang() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Used_Car_Collect)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("carsource_id", carsource_id)
                    .addParams("user_id", user_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    UsedCarSuccessBean usedCarSuccessBean = JsonUtil.parseJsonToBean(string, UsedCarSuccessBean.class);
                    final int code = usedCarSuccessBean.getCode();
                    final String message = usedCarSuccessBean.getMessage();
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (200 == code) {
                                tv_collection.setText("已收藏");
                            }
                            if (203 == code) {
                                tv_collection.setText("收藏");
                            }
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

    @Override
    protected void initData() {
        tvTitle.setText("详情");
        Intent intent = getIntent();
        carsource_id = intent.getStringExtra("carsource_id");
        htmlUrl = intent.getStringExtra("htmlUrl");
        shangMenQu = intent.getStringExtra("shangMenQu");
        shangMenHuan = intent.getStringExtra("shangMenHuan");
        qu_time_date = intent.getStringExtra("qu_time_date");
        huan_time_date = intent.getStringExtra("huan_time_date");
        qu_city = intent.getStringExtra("qu_city_usedcar");
        huan_city = intent.getStringExtra("huan_city_usedcar");
        store_id = intent.getStringExtra("store_id_usedcar");
        duration = intent.getStringExtra("duration");
        qu_latitude_usedcar = intent.getStringExtra("qu_latitude_usedcar");
        qu_longitude_usedcar = intent.getStringExtra("qu_longitude_usedcar");
        qu_name_usedcar = intent.getStringExtra("qu_name_usedcar");
        huan_latitude_usedcar = intent.getStringExtra("huan_latitude_usedcar");
        huan_longitude_usedcar = intent.getStringExtra("huan_longitude_usedcar");
        huan_name_usedcar = intent.getStringExtra("huan_name_usedcar");
        getDataFromNet();
    }

    private void getDataFromNet() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        String usedcarcity = PrefUtils.getString(this, "usedcarcity", "");
        String id = LoginUtils.getId(this);
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Used_Car_Info)
                    .addParams("device_id", id)
                    .addParams("area", usedcarcity)
                    .addParams("carsource_id", carsource_id)
                    .addParams("user_id", user_id)
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

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        carsource_id = intent.getStringExtra("carsource_id");
        shangMenQu = intent.getStringExtra("shangMenQu");
        shangMenHuan = intent.getStringExtra("shangMenHuan");
        qu_time_date = intent.getStringExtra("qu_time_date");
        huan_time_date = intent.getStringExtra("huan_time_date");
        qu_city = intent.getStringExtra("qu_city_usedcar");
        huan_city = intent.getStringExtra("huan_city_usedcar");
        store_id = intent.getStringExtra("store_id_usedcar");
        duration = intent.getStringExtra("duration");
        qu_latitude_usedcar = intent.getStringExtra("qu_latitude_usedcar");
        qu_longitude_usedcar = intent.getStringExtra("qu_longitude_usedcar");
        qu_name_usedcar = intent.getStringExtra("qu_name_usedcar");
        huan_latitude_usedcar = intent.getStringExtra("huan_latitude_usedcar");
        huan_longitude_usedcar = intent.getStringExtra("huan_longitude_usedcar");
        huan_name_usedcar = intent.getStringExtra("huan_name_usedcar");
        getDataFromNet();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void processData(String string) {
        UsedCarInfoBean usedCarInfoBean = JsonUtil.parseJsonToBean(string, UsedCarInfoBean.class);
        if (usedCarInfoBean != null) {
            final UsedCarInfoBean.DataBean data = usedCarInfoBean.getData();
            if (data != null) {
                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //车源亮点
                        List<UsedCarInfoBean.DataBean.BrightBean> bright = data.getBright();
                        if (bright != null && bright.size() != 0) {
                            ll_peizhi.setVisibility(View.VISIBLE);
                            gv_used_car_liangdian.setAdapter(new LiangDianAdapter(bright, App.context, R.layout.used_car_liangdian_item));
                        } else {
                            ll_peizhi.setVisibility(View.GONE);
                        }
                        //车源配置参数
                        List<UsedCarInfoBean.DataBean.CanshuBean> canshu = data.getCanshu();
                        if (canshu != null && canshu.size() != 0) {
                            gv_used_car_miaoshu.setVisibility(View.VISIBLE);
                            gv_used_car_miaoshu.setAdapter(new UsedCarInformationAdapter(canshu, App.context, R.layout.used_car_information_item));
                        } else {
                            gv_used_car_miaoshu.setVisibility(View.GONE);
                        }
                        if ((canshu != null && canshu.size() == 0) && (bright != null && bright.size() == 0)) {
                            xian.setVisibility(View.GONE);
                        }
                        //车源数据
                        UsedCarInfoBean.DataBean.CarsourceBean carsource = data.getCarsource();
                        String car_price = carsource.getCar_price();
                        String new_car_price = carsource.getNew_car_price();
                        String new_rent_price = carsource.getNew_rent_price();

                        String collect_status = carsource.getCollect_status();
                        //租车状态：1出租中2未出租
                        rent_whether = carsource.getRent_whether();
                        about_status = carsource.getAbout_status();
                        rent_status = carsource.getRent_status();
                        rent_price = carsource.getRent_price();
                        header_img = carsource.getHeader_img();
                        if ("1".equals(rent_whether)) {
                            tv_rent_car.setText("试车中");
                        } else if ("2".equals(rent_whether)) {
                            tv_order_look_car.setText("试车");
                        }
                        if (0 == rent_status) {
                            tv_order_look_car.setText("试车");
                            ll_rent.setVisibility(View.GONE);
                            tv_rent_car.setBackgroundResource(R.drawable.used_bt_bg_gray);
                            tv_rent_car.setClickable(false);
                        } else if (1 == rent_status) {
                            tv_order_look_car.setText("试车");
                            ll_rent.setVisibility(View.VISIBLE);
                            tv_rent_car.setBackgroundResource(R.drawable.usedcar_bt_bg);
                            tv_rent_car.setClickable(true);
                        }

                        if ("1".equals(about_status)) {
                            tv_order_look_car.setText("已预约");
                        } else if ("2".equals(about_status)) {
                            tv_order_look_car.setText("预约看车");
                        }

                        car_allname = carsource.getCar_allname();
                        //违章押金
                        violated_deposit = carsource.getViolated_deposit();
                        //车辆押金
                        car_deposit = carsource.getCar_deposit();
                        //门店名称
                        shop_name = carsource.getShop_name();
                        detection_address = carsource.getDetection_address();
                        if (!TextUtils.isEmpty(detection_address)) {
                            rl_look_baogao.setVisibility(View.VISIBLE);
                        } else {
                            rl_look_baogao.setVisibility(View.GONE);
                        }
                        id_status = carsource.getId_status();

                        if ("1".equals(collect_status)) {
                            //已收藏
                            cb_collection.setChecked(true);
                            tv_collection.setText("已收藏");
                        } else {
                            cb_collection.setChecked(false);
                            tv_collection.setText("收藏");
                        }

                        if (!TextUtils.isEmpty(car_allname)) {
                            tv_name.setText(car_allname);
                        }
                        if (!TextUtils.isEmpty(new_car_price)) {
                            tv_sell_price_old.setText(new_car_price);
                        }
                        if (!TextUtils.isEmpty(car_price)) {
                            tv_sell_now_price.setText(car_price);
                        }
                        if (!TextUtils.isEmpty(rent_price)) {
                            tv_rent_price_old.setText(rent_price);
                        }
                        if (!TextUtils.isEmpty(new_rent_price)) {
                            tv_rent_now_price.setText(new_rent_price);
                        }
                        //车源轮播图片
                        twocar_img = data.getTwocar_img();
                        vp_shop_lunbo.setAdapter(new DriverPagAdapter(twocar_img));
                        if (twocar_img != null && twocar_img.size() > 0) {
                            mHandler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    switch (msg.what) {
                                        case MSG_NEXT:
                                            if (twocar_img.size() > 0) {
                                                next();
                                            }
                                            break;
                                        case MSG_NEXT_ONE:

                                            break;
                                    }
                                    super.handleMessage(msg);
                                }
                            };
                            startTimer();
                            vp_shop_lunbo.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    switch (event.getAction()) {
                                        case MotionEvent.ACTION_DOWN:
                                        case MotionEvent.ACTION_MOVE:
                                            stopTimer();
                                            break;
                                        case MotionEvent.ACTION_UP:
                                            startTimer();
                                            break;
                                    }
                                    return false;
                                }
                            });
                        }
                        //相关推荐
                        similar = data.getSimilar();
                        if (similar != null && similar.size() != 0) {
                            alv_hot_used_car.setAdapter(new HotAdapter(similar, App.context, R.layout.used_car_list_item));
                        }
                        //车辆信息照片
                        content_img = data.getContent_img();
                        if (content_img != null && content_img.size() != 0) {
                            lv_used_car_picture.setAdapter(new UsedCarPictureAdapter(content_img, App.context, R.layout.used_car_picture_item));
                        }
                    }
                });
            }
        }
    }

    public class DriverPagAdapter extends PagerAdapter {
        private List<String> twocar_img;

        public DriverPagAdapter(List<String> twocar_img) {
            this.twocar_img = twocar_img;
        }

        @Override
        public int getCount() {
            if (twocar_img != null) {
                return twocar_img.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(App.context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final String address = twocar_img.get(position);
            if (!TextUtils.isEmpty(address)) {
                Picasso.with(App.context).load(address).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(App.context, ImageShower.class);
                        intent.putExtra("head_img", address);
                        startActivity(intent);
                    }
                });

                container.addView(imageView);
            }

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_loan_jieshsao:
                openActivity(LoanStagingFirstActivity.class);
                break;
            case R.id.tv_rent_car:
                if (DialogUtils.IsLogin()) {
                    if ("2".equals(rent_whether)) {
                        if (!TextUtils.isEmpty(id_status)) {
                            if ("1".equals(id_status)) {
                                //订单页
                                Intent intent1 = new Intent(this, UsedCarSubmitIndent.class);
                                commonIntent(intent1);
                                startActivity(intent1);
                            } else {
                                //上传身份信息
                                Intent intent2 = new Intent(this, ShenFenQueRen.class);
                                commonIntent(intent2);
                                startActivity(intent2);
                            }
                        }
                    }
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.iv_caidan:
                if (DialogUtils.IsLogin()) {
                    //分享
                    if (!TextUtils.isEmpty(header_img) && !TextUtils.isEmpty(htmlUrl)) {
                        XueYiCheUtils.showShareAppCommon(App.context, UsedCarContentActivity.this,
                                StringConstants.SHARED_TITLE + "二手车", htmlUrl
                                ,
                                StringConstants.SHARED_TEXT, header_img,
                                htmlUrl);
                    }
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.tv_order_look_car:
                boolean b = DialogUtils.IsLogin();
                if (b) {
                    if ("2".equals(about_status)) {
                        yuYueKanChe();
                    }
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.rl_look_baogao:
                if (!TextUtils.isEmpty(detection_address)) {
                    rl_look_baogao.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(App.context, UrlActivity.class);
                    intent.putExtra("url", detection_address);
                    intent.putExtra("type", "6");
                    startActivity(intent);
                }
                break;
        }
    }

    private void commonIntent(Intent intent1) {
        intent1.putExtra("carsource_id", carsource_id);
        intent1.putExtra("qu_time_date", qu_time_date);
        intent1.putExtra("huan_time_date", huan_time_date);
        intent1.putExtra("store_id_usedcar", store_id);
        intent1.putExtra("duration", duration);
        intent1.putExtra("qu_city_usedcar", qu_city);
        intent1.putExtra("huan_city_usedcar", huan_city);
        intent1.putExtra("shangMenQu", shangMenQu);
        intent1.putExtra("shangMenHuan", shangMenHuan);
        intent1.putExtra("qu_latitude_usedcar", qu_latitude_usedcar);
        intent1.putExtra("qu_longitude_usedcar", qu_longitude_usedcar);
        intent1.putExtra("qu_name_usedcar", qu_name_usedcar);
        intent1.putExtra("huan_latitude_usedcar", huan_latitude_usedcar);
        intent1.putExtra("huan_longitude_usedcar", huan_longitude_usedcar);
        intent1.putExtra("huan_name_usedcar", huan_name_usedcar);
        intent1.putExtra("car_allname", car_allname);
        intent1.putExtra("violated_deposit", violated_deposit);
        intent1.putExtra("car_deposit", car_deposit);
        intent1.putExtra("rent_price", rent_price);
        intent1.putExtra("shop_name", shop_name);
        intent1.putExtra("img", header_img);
    }

    private void yuYueKanChe() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Used_Car_BuyOrder)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("carsource_id", carsource_id)
                    .addParams("user_id", user_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        UsedCarSuccessBean usedCarSuccessBean = JsonUtil.parseJsonToBean(string, UsedCarSuccessBean.class);
                        final int code = usedCarSuccessBean.getCode();
                        final String message = usedCarSuccessBean.getMessage();

                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showToastShort(message);
                                if (200 == code) {
                                    tv_order_look_car.setText("已预约");
                                }
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
        }
    }

    private class UsedCarInformationAdapter extends BaseCommonAdapter {
        public UsedCarInformationAdapter(List<UsedCarInfoBean.DataBean.CanshuBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            UsedCarInfoBean.DataBean.CanshuBean canshuBean = (UsedCarInfoBean.DataBean.CanshuBean) item;
            String canshu_name = canshuBean.getCanshu_name();
            String canshu_value = canshuBean.getCanshu_value();
            viewHolder.setText(R.id.tv_title, canshu_value);
            viewHolder.setText(R.id.tv_content, canshu_name);
        }
    }

    private class LiangDianAdapter extends BaseCommonAdapter {

        public LiangDianAdapter(List<UsedCarInfoBean.DataBean.BrightBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            UsedCarInfoBean.DataBean.BrightBean brightBean = (UsedCarInfoBean.DataBean.BrightBean) item;
            String bright_information = brightBean.getBright_information();
            viewHolder.setText(R.id.tv_title, bright_information);
        }
    }

    private class UsedCarPictureAdapter extends BaseCommonAdapter {
        public UsedCarPictureAdapter(List<UsedCarInfoBean.DataBean.ContentImgBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            UsedCarInfoBean.DataBean.ContentImgBean contentImgBean = (UsedCarInfoBean.DataBean.ContentImgBean) item;
            String content = contentImgBean.getContent();
            String img = contentImgBean.getImg();
            String title = contentImgBean.getTitle();
            viewHolder.setText(R.id.tv_title, title);
            if (!TextUtils.isEmpty(content)) {
                viewHolder.setText(R.id.tv_content, content);
            } else {
                viewHolder.changeTextGone(R.id.tv_content);
            }
            viewHolder.setPicCommon(R.id.iv_picture, img, R.mipmap.usedcar_zanwu, R.mipmap.usedcar_zanwu);

        }
    }

    private class HotAdapter extends BaseCommonAdapter {
        public HotAdapter(List<UsedCarInfoBean.DataBean.SimilarBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            UsedCarInfoBean.DataBean.SimilarBean similarBean = (UsedCarInfoBean.DataBean.SimilarBean) item;
            String just_side_img = similarBean.getJust_side_img();
            int status = similarBean.getStatus();
            String car_allname = similarBean.getCar_allname();
            //月供
            String loan_month = similarBean.getLoan_month();
            //首付
            String loan_first = similarBean.getLoan_first();
            //售价
            String car_price = similarBean.getCar_price();
            //上牌时间公里数
            String last_mileage = similarBean.getLast_mileage();
            //新车售价
            String new_car_price = similarBean.getNew_car_price();
            //平台租车价
            String rent_price = similarBean.getRent_price();
            //新车租车价
            String new_rent_price = similarBean.getNew_rent_price();
            //车辆是否租
            int rent_status = similarBean.getRent_status();
            viewHolder.setPic(R.id.iv_used_car_photo, just_side_img);
            if (1 == status) {
                //出租中
                viewHolder.changeImageVisible(R.id.iv_used_car_state);
            } else {
                viewHolder.changeImageGone(R.id.iv_used_car_state);
            }
            viewHolder.setText(R.id.tv_usedcar_title, car_allname);
            viewHolder.setText(R.id.tv_money_buy_loan, loan_first + "  " + loan_month);
            viewHolder.setText(R.id.tv_shangpan_gongli, "行驶里程："+last_mileage);
            viewHolder.setText(R.id.tv_car_price, car_price);
            viewHolder.setText(R.id.tv_new_car_price, new_car_price);
            viewHolder.setText(R.id.tv_rent_price, rent_price);
            viewHolder.setText(R.id.tv_new_rent_price, new_rent_price);
            if (0 == rent_status) {
                viewHolder.changeLinearLayoutInVisible(R.id.ll_rent_content);
            } else if (1 == rent_status) {
                viewHolder.changeLinearLayoutVisible(R.id.ll_rent_content);
            }
        }
    }
}
