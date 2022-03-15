package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.xueyiche.zjyk.xueyiche.constants.bean.DriverShcoolInfo;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.ImageShower;
import com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview.LoopViewPager;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.ObservableScrollView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.submit.DriverSchoolSubmitIndent;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.DriverSchoolTrainerListActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhanglei on 2016/11/26.
 */
public class DriverSchoolContent extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler, ObservableScrollView.ScrollViewListener {
    //驾校详情
    private ImageView iv_shop_phone, iv_shop_daohang;
    private TextView tv_lunbo_number, tv_shop_name, tv_driverschool_feiyong,
            tv_shop_address, tv_open_time;
    private TextView bt_driver_school_buy, tv_gmxz;
    private LoopViewPager vp_shop_lunbo;
    private String shop_name;
    private ObservableScrollView os_shop;
    private LinearLayout bt_driver_school_train;
    private String longitude, latitude, shop_place, shop_phone, shop_id, user_id;
    private Handler mHandler;
    private TimerTask mTimerTask;
    private Timer mTimer;
    private static final int MSG_NEXT = 1;
    private static final int MSG_NEXT_ONE = 2;
    private DriverShcoolInfo.ContentBean content;
    private AdListView lv_taocan;
    private String practice_place;
    private String practice_latitude;
    private String practice_longitude;
    private String stkm;
    private LinearLayout ll_exam_back;
    private ImageView iv_caidan, iv_shoucang, iv_fenxiang;
    private boolean isCheck;
    private TextView tv_juli;
    private RelativeLayout rl_title;


    //驾校详情
    @Override

    protected int initContentView() {

        return R.layout.driver_school_content;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        shop_id = intent.getStringExtra("driver_school_id");
        //SHOP_TOP
        ll_exam_back = (LinearLayout) view.findViewById(R.id.ll_exam_back);
        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        iv_caidan = (ImageView) view.findViewById(R.id.iv_caidan);
        //驾校套餐
        lv_taocan = (AdListView) view.findViewById(R.id.lv_taocan);
        iv_shop_phone = (ImageView) view.findViewById(R.id.iv_shop_phone);
        iv_shop_daohang = (ImageView) view.findViewById(R.id.iv_shop_daohang);
        iv_shoucang = (ImageView) view.findViewById(R.id.iv_shoucang);
        iv_fenxiang = (ImageView) view.findViewById(R.id.iv_fenxiang);
        tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
        tv_open_time = (TextView) view.findViewById(R.id.tv_open_time);
        tv_lunbo_number = (TextView) view.findViewById(R.id.tv_lunbo_number);
        tv_juli = (TextView) view.findViewById(R.id.tv_juli);
        tv_gmxz = (TextView) view.findViewById(R.id.tv_gmxz);
        tv_shop_address = (TextView) view.findViewById(R.id.tv_shop_address);
        vp_shop_lunbo = (LoopViewPager) view.findViewById(R.id.vp_shop_lunbo);
        os_shop = (ObservableScrollView) view.findViewById(R.id.os_shop);
        os_shop.setScrollViewListener(this);
        tv_driverschool_feiyong = (TextView) view.findViewById(R.id.tv_driverschool_feiyong);
        bt_driver_school_buy = (TextView) view.findViewById(R.id.bt_driver_school_buy);
        bt_driver_school_train = (LinearLayout) view.findViewById(R.id.bt_driver_school_train);
        NetBroadcastReceiver.mListeners.add(this);
        user_id = PrefUtils.getString(App.context, "user_id", "");
        lv_taocan.setVerticalScrollBarEnabled(false);
        lv_taocan.setFastScrollEnabled(false);
        lv_taocan.setFocusable(false);
        os_shop.smoothScrollBy(0, 0);
        os_shop.smoothScrollTo(0, 0);
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        iv_caidan.setOnClickListener(this);
        iv_shoucang.setOnClickListener(this);
        iv_fenxiang.setOnClickListener(this);
        iv_shop_phone.setOnClickListener(this);
        iv_shop_daohang.setOnClickListener(this);
        bt_driver_school_buy.setOnClickListener(this);
        bt_driver_school_train.setOnClickListener(this);

        vp_shop_lunbo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (content != null) {
                    List<DriverShcoolInfo.ContentBean.VolutionBean> volution = content.getVolution();
                    if (volution != null && volution.size() > 0) {
                        tv_lunbo_number.setText(position + 1 + "/" + volution.size());

                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //选择下一个卡片
    public void next() {
        if (content != null) {
            List<DriverShcoolInfo.ContentBean.VolutionBean> volution = content.getVolution();
            if (volution != null && volution.size() > 0) {
                int pos = vp_shop_lunbo.getCurrentItem();
                pos += 1;
                vp_shop_lunbo.setCurrentItem(pos);

            }
        }
    }
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//标题栏颜色的变化
        int height = vp_shop_lunbo.getHeight();
        if (y <= 0 || y < height ) {
            rl_title.setBackgroundColor(Color.argb(0, 0, 0, 0));//AGB由相关工具获得，或者美工提供
        }else if (y > height) {
            rl_title.setBackgroundColor(Color.argb(255, 255, 255, 255));
        }
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

    private void lunbo() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_NEXT:
                        List<DriverShcoolInfo.ContentBean.VolutionBean> volution = content.getVolution();
                        if (volution.size() != 1) {
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

    @Override
    protected void initData() {
        getDataFromNet();
    }

    @Override
    protected void onResume() {
//        getDataFromNet();
        super.onResume();
    }

    private void getDataFromNet() {
        String x = PrefUtils.getString(App.context, "x", "");
        String y = PrefUtils.getString(App.context, "y", "");
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(shop_id)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Driver_Shcool_Content)
                    .addParams("driver_school_id", shop_id)
                    .addParams("latitude_user", y)
                    .addParams("longitude_user", x)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
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
                    return null;
                }

                @Override
                public void onError(Request request, Exception e) {
                    os_shop.smoothScrollBy(0, 0);
                    stopProgressDialog();
                }

                @Override
                public void onResponse(Object response) {
                    stopProgressDialog();
                    os_shop.smoothScrollBy(0, 0);
                }
            });
        }
    }

    private void processData(String string) {
        DriverShcoolInfo driverShcoolInfo = JsonUtil.parseJsonToBean(string, DriverShcoolInfo.class);
        if (driverShcoolInfo != null) {
            content = driverShcoolInfo.getContent();
            if (content != null) {
                DriverShcoolInfo.ContentBean.SchoolInfoBean school_info = content.getSchool_info();
                String collect_status = content.getCollect_status();
                if ("1".equals(collect_status)) {
                    //未收藏
                    isCheck = false;
                    iv_shoucang.setImageResource(R.mipmap.jx_title_shoucang);
                } else if ("2".equals(collect_status)) {
                    isCheck = true;
                    iv_shoucang.setImageResource(R.mipmap.jx_title_shoucang_select);
                }
                if (school_info != null) {
                    shop_name = school_info.getDriver_school_name();
                    latitude = school_info.getLatitude();
                    String service_detail_text = school_info.getService_detail_text();
                    longitude = school_info.getLongitude();
                    String label_one_name = school_info.getLabel_one_name();
                    stkm = school_info.getStkm();
                    String distance = school_info.getDistance();
                    shop_place = school_info.getDriver_school_place();
                    shop_phone = school_info.getDriver_school_phone();
                    practice_place = school_info.getPractice_place();
                    practice_latitude = school_info.getPractice_latitude();
                    practice_longitude = school_info.getPractice_longitude();
                    String gmxz = school_info.getGmxz();
                    if (!TextUtils.isEmpty(shop_name)) {
                        tv_shop_name.setText(shop_name);
                    }
                    if (!TextUtils.isEmpty(distance)) {
                        tv_juli.setText("距您:"+distance);
                    }
                    if (!TextUtils.isEmpty(label_one_name)) {
                        tv_open_time.setText(label_one_name);
                    }
                    if (!TextUtils.isEmpty(gmxz)) {
                        tv_gmxz.setText(gmxz);
                    }
                    if (!TextUtils.isEmpty(shop_place)) {
                        tv_shop_address.setText(shop_place);
                    }
                    if (!TextUtils.isEmpty(service_detail_text)) {
                        String br = service_detail_text.replaceAll("<br>", "\n");
                        tv_driverschool_feiyong.setText(br);
                    }
                }
                List<DriverShcoolInfo.ContentBean.VolutionBean> volution = content.getVolution();
                if (volution != null && volution.size() > 0) {
                    vp_shop_lunbo.setAdapter(new DriverPagAdapter(volution));
                    //轮播
                    lunbo();
                }

                List<DriverShcoolInfo.ContentBean.PackageTaocanBean> package_taocan = content.getPackage_taocan();
                if (package_taocan != null) {
                    lv_taocan.setAdapter(new TaoCanAdapter(package_taocan, App.context, R.layout.driver_school_taocan_item));
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.bt_driver_school_train:
                Intent intent3 = new Intent();
                intent3.setClass(App.context, DriverSchoolTrainerListActivity.class);
                intent3.putExtra("id", shop_id);
                intent3.putExtra("order_number", "");
                intent3.putExtra("style", "2");
                startActivity(intent3);
                break;
            case R.id.iv_shop_phone:
                callshop();
                break;
            case R.id.iv_caidan:
                openActivity(KeFuActivity.class);
                break;
            case R.id.iv_shoucang:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);

                } else {
                    if (!TextUtils.isEmpty(shop_id) && !TextUtils.isEmpty(shop_name)) {
                        //收藏驾校
                        collction();
                    }
                }
                break;
            case R.id.iv_fenxiang:
                //分享
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    //分享
                    List<DriverShcoolInfo.ContentBean.VolutionBean> volution = content.getVolution();
                    if (volution != null && volution.size() > 0) {
                        DriverShcoolInfo.ContentBean.VolutionBean volutionBean = volution.get(0);
                        if (volutionBean != null) {
                            String address = volutionBean.getAddress();
                            if (!TextUtils.isEmpty(shop_id) && !TextUtils.isEmpty(shop_name) && !TextUtils.isEmpty(address)) {
                                XueYiCheUtils.showShareAppCommon(App.context, DriverSchoolContent.this,
                                        StringConstants.SHARED_TITLE + shop_name,
                                        "http://xueyiche.cn/share/jiaxiao.html?driver_school_id=" + shop_id,
                                        StringConstants.SHARED_TEXT,
                                        address,
                                        "http://xueyiche.cn/share/jiaxiao.html?driver_school_id=" + shop_id);
                            }
                        }
                    }
                }
                break;
            case R.id.iv_shop_kefu:
                openActivity(KeFuActivity.class);
                break;
            case R.id.iv_shop_daohang:
                XueYiCheUtils.getDiaLocation(DriverSchoolContent.this, latitude, longitude, shop_name, shop_place);
                break;
            case R.id.iv_label_three:
                XueYiCheUtils.getDiaLocation(DriverSchoolContent.this, practice_latitude, practice_longitude, shop_name, practice_place);
                break;
            case R.id.bt_driver_school_buy:
                //进入支付页
                if (DialogUtils.IsLogin()) {
                    if (!TextUtils.isEmpty(shop_id)) {
                        Intent intent1 = new Intent(App.context, DriverSchoolSubmitIndent.class);
                        intent1.putExtra("driver_school_id", shop_id);
                        intent1.putExtra("tuan", "zhengchang");
                        intent1.putExtra("stkm", stkm);
                        startActivity(intent1);
                    }
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
        }
    }

    private void collction() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            if (!isCheck) {
                OkHttpUtils.post().url(AppUrl.Collection_Add)
                        .addParams("user_id", user_id)
                        .addParams("device_id", LoginUtils.getId(this))
                        .addParams("refer_id", shop_id)
                        .addParams("kind_type", "1")
                        .addParams("mark_type", "2")
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
                                            iv_shoucang.setImageResource(R.mipmap.jx_title_shoucang_select);
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
                        .addParams("refer_id", shop_id)
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
                                            iv_shoucang.setImageResource(R.mipmap.jx_title_shoucang);
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

    private void callshop() {
        if (!TextUtils.isEmpty(shop_phone)) {
            XueYiCheUtils.CallPhone(DriverSchoolContent.this, "驾校电话", shop_phone);
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


    public class DriverPagAdapter extends PagerAdapter {
        private List<DriverShcoolInfo.ContentBean.VolutionBean> volution;

        public DriverPagAdapter(List<DriverShcoolInfo.ContentBean.VolutionBean> volution) {
            this.volution = volution;
        }

        @Override
        public int getCount() {
            if (volution != null) {
                return volution.size();
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
            DriverShcoolInfo.ContentBean.VolutionBean volutionBean = volution.get(position);
            if (volutionBean != null) {
                final String address = volutionBean.getAddress();
                Picasso.with(App.context).load(address).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(App.context, ImageShower.class);
                        intent.putExtra("head_img", address);
                        startActivity(intent);
                    }
                });
            }

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    private class TaoCanAdapter extends BaseCommonAdapter {

        public TaoCanAdapter(List<DriverShcoolInfo.ContentBean.PackageTaocanBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            DriverShcoolInfo.ContentBean.PackageTaocanBean packageTaocanBean = (DriverShcoolInfo.ContentBean.PackageTaocanBean) item;
            String driver_school_money = packageTaocanBean.getDriver_school_money();
            String driver_school_service = packageTaocanBean.getDriver_school_service();
            String return_money = packageTaocanBean.getReturn_money();
            viewHolder.setText(R.id.tv_driverschool_money, "¥ " + driver_school_money);
            viewHolder.setText(R.id.tv_driverschool_service, driver_school_service);
            viewHolder.setText(R.id.tv_fan_money, "返" + return_money + "元");
        }
    }

}
