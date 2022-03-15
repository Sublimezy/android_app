package com.xueyiche.zjyk.xueyiche.main.activities.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.MyBasePagerAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.constants.bean.GuangGaoBean;
import com.xueyiche.zjyk.xueyiche.constants.bean.GuangGaoHomeBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.homepage.db.MyGuangGaoDB;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.main.view.NoScrollViewPager;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.service.UpLocationService;
import com.xueyiche.zjyk.xueyiche.receive.LocationService;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.BaiduLocation;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.ToastUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import me.jessyan.autosize.internal.CancelAdapt;

/**
 * Created by Owner on 2016/9/13.
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private long exitTime;
    private NoScrollViewPager mVP_main;
    private RadioGroup mRG_menutab;

    private Intent homeIntent;
    private GuangGaoHomeBean guangGaoHomeBean;
    private RadioButton rb_car, rb_home, rb_discover,rb_my;
    private ToastUtil toastUtils;
    //    private CountDownTimer cdt;


    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void initView() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mVP_main = (NoScrollViewPager) view.findViewById(R.id.vp_main);
        mRG_menutab = (RadioGroup) view.findViewById(R.id.rg_menutab);
        rb_car = (RadioButton) view.findViewById(R.id.rb_car);
        rb_home = (RadioButton) view.findViewById(R.id.rb_home);
        rb_discover = (RadioButton) view.findViewById(R.id.rb_discover);
        rb_my = (RadioButton) view.findViewById(R.id.rb_my);
        mVP_main.setCurrentItem(0);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metric);
        int[] location = new int[2];
        mRG_menutab.getLocationOnScreen(location);

        BaiduLocation baiduLocation = new BaiduLocation();
        baiduLocation.baiduLocation();
        initData();
        getGuangGao();
        String szImei = App.szImei;
        if (TextUtils.isEmpty(szImei)) {
            Intent bindintent = new Intent(this, LocationService.class);
            startService(bindintent);
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getGuangGao();
    }


    private void getGuangGao() {
        final String area_id = PrefUtils.getString(App.context, "area_id", "");
        final String area_id_version = PrefUtils.getString(App.context, area_id + "version", "1.0.0");
        OkHttpUtils.post().url(AppUrl.GGY)
                .addParams("version", area_id_version)
                .addParams("city_id", area_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                final String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    guangGaoHomeBean = JsonUtil.parseJsonToBean(string, GuangGaoHomeBean.class);
                    if (guangGaoHomeBean != null) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String time = formatter.format(curDate);
                        String start_date = time.substring(0, 10);
                        String start_time = time.substring(11, time.length());
                        final MyGuangGaoDB db = new MyGuangGaoDB(MainActivity.this);
                        final GuangGaoBean gg = db.findGg(start_date, start_time, area_id);
                        String tupian = gg.getTupian();
                        final String tupian_url = gg.getTupian_url();
                        if (!TextUtils.isEmpty(tupian_url) && TextUtils.isEmpty(tupian)) {
                            Bitmap imageBit = XueYiCheUtils.getImageBit(tupian_url);
                            String tupianload = XueYiCheUtils.bitmaptoString(imageBit);
                            if (!TextUtils.isEmpty(tupianload)) {
                                db.addTuPian(tupian_url, tupianload);
                            }

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
                if (guangGaoHomeBean != null) {
                    String version = guangGaoHomeBean.getVersion();
                    if (!TextUtils.isEmpty(version)) {
                        if (!area_id_version.equals(version)) {
                            PrefUtils.putString(App.context, area_id + "version", version);
                            List<GuangGaoHomeBean.ContentBean> content = guangGaoHomeBean.getContent();
                            addGuangGAo(content);
                        }
                    }
                }
            }
        });

    }

    private void addGuangGAo(List<GuangGaoHomeBean.ContentBean> content) {
        App.myGuangGaoDB.deleAll();
        for (GuangGaoHomeBean.ContentBean contentBean : content) {
            String end_date = contentBean.getEnd_date();
            String end_time = contentBean.getEnd_time();
            String start_date = contentBean.getStart_date();
            String start_time = contentBean.getStart_time();
            String web_content_url = contentBean.getWeb_content_url();
            String picture_url = contentBean.getPicture_url();
            String picture_refer_id = contentBean.getPicture_refer_id();
            String city_id = contentBean.getCity_id();
            App.myGuangGaoDB.add(picture_refer_id, "", start_date, end_date, start_time, end_time, web_content_url, picture_url, city_id);
        }
        for (GuangGaoHomeBean.ContentBean contentBean : content) {
            String tupian_url = contentBean.getPicture_url();
            Bitmap imageBit = XueYiCheUtils.getImageBit(tupian_url);
            String tupian = XueYiCheUtils.bitmaptoString(imageBit);
            App.myGuangGaoDB.addTuPian(tupian_url, tupian);
        }
    }

    public void mine() {
        if (DialogUtils.IsLogin()) {
            XueYiCheUtils.showShareAppCommon(this, this, "学易车-易动华夏", "http://xueyiche.cn/", StringConstants.SHARED_TEXT + "http://www.xueyiche.cn/", "http://xychead.xueyiche.vip/share.jpg", "http://xueyiche.cn/");
        } else {
            openActivity(LoginFirstStepActivity.class);
        }
    }

    @Override
    protected void initListener() {
    }

    protected void onNewIntent(Intent intent) {

        if (intent != null) {
            homeIntent = intent;
        }
        super.onNewIntent(intent);
        if (homeIntent != null) {
            int id = homeIntent.getIntExtra("position", 0);
            if (id == 0) {
                mVP_main.setCurrentItem(0);
                rb_home.setChecked(true);
            } else if (id == 1) {
                setFragment(1);
            } else if (id == 2) {
                mVP_main.setCurrentItem(2);
            }
        }
    }



    @Override
    protected void initData() {
        MyBasePagerAdapter myBasePagerAdapter = new MyBasePagerAdapter(getSupportFragmentManager());
        mVP_main.setAdapter(myBasePagerAdapter);
        mRG_menutab.setOnCheckedChangeListener(this);
        Intent intent = getIntent();
        String device_id = LoginUtils.getId(MainActivity.this);
        if (!TextUtils.isEmpty(device_id)) {
            PrefUtils.putString(App.context, "device_id", device_id);
        } else {
            PrefUtils.putString(App.context, "device_id", "123456789");
        }
        String baoming = intent.getStringExtra("baoming");
        int position = intent.getIntExtra("position", 0);
        if (!TextUtils.isEmpty("" + position)) {
            setFragment(position);
        }
        if (!TextUtils.isEmpty(baoming)) {
            if ("1".equals(baoming)) {
                setFragment(1);
            }
        }
        xianxing();

    }

    private void xianxing() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.XianXing)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                        if (successDisCoverBackBean != null) {
                            final int code = successDisCoverBackBean.getCode();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (231 == code) {
                                        toastUtils = new ToastUtil(MainActivity.this);
                                        toastUtils.showToastTop("您绑定的车辆今日限行，请注意出行。");
                                    }
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
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再点击一次退出学易车", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            Intent ser = new Intent(this, UpLocationService.class);
            stopService(ser);
            AppUtils.AppExit(MainActivity.this);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        getGuangGao();

        updateLonLatUser();
    }

    private void updateLonLatUser() {
        BaiduLocation baiduLocation = new BaiduLocation();
        baiduLocation.baiduLocation();
        String latitude = PrefUtils.getParameter("y");
        String longitude = PrefUtils.getParameter("x");
        Map<String, String> params = new HashMap<>();
        params.put("user_id", PrefUtils.getParameter("user_id"));
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        MyHttpUtils.postHttpMessage(AppUrl.updatelonlatuser, params, BaseBean.class, new RequestCallBack<BaseBean>() {
            @Override
            public void requestSuccess(BaseBean json) {

            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                mVP_main.setCurrentItem(0, false);//禁用动画
                EventBus.getDefault().post(new MyEvent("首页"));
//                AppUtils.dissList(MainActivity.this);
//                if (cdt != null) {
//                    cdt.cancel();
//                }
                break;
            case R.id.rb_car:
                mVP_main.setCurrentItem(1, false);
                EventBus.getDefault().post(new MyEvent("车生活"));
//                AppUtils.dissList(MainActivity.this);
//                if (cdt != null) {
//                    cdt.cancel();
//                }

                break;
            case R.id.rb_discover:
                mVP_main.setCurrentItem(2, false);
                EventBus.getDefault().post(new MyEvent("车知乎"));
//                AppUtils.dissList(MainActivity.this);
//                if (cdt != null) {
//                    cdt.cancel();
//                }
                break;
            case R.id.rb_my:
                mVP_main.setCurrentItem(3, false);
                EventBus.getDefault().post(new MyEvent("我的"));
//                AppUtils.dissList(MainActivity.this);
//                if (cdt != null) {
//                    cdt.cancel();
//                }
                break;
        }

    }


    @Override
    protected void onDestroy() {
        AppUtils.dissList(MainActivity.this);
        Intent bindintent = new Intent(this, LocationService.class);
        stopService(bindintent);
        PrefUtils.putInt(App.context,"click_paixu",0);
        PrefUtils.putInt(App.context,"click_type",0);
        PrefUtils.putInt(App.context,"click_street",0);
        PrefUtils.putInt(App.context,"click_area",0);
        super.onDestroy();


//        if (cdt != null) {
//            cdt.cancel();
//        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        AppUtils.dissList(MainActivity.this);
        PrefUtils.putInt(App.context,"click_paixu",0);
        PrefUtils.putInt(App.context,"click_type",0);
        PrefUtils.putInt(App.context,"click_street",0);
        PrefUtils.putInt(App.context,"click_area",0);
//        if (cdt != null) {
//            cdt.cancel();
//        }
    }

    //Fragment调用
    public void setFragment(int position) {
        mVP_main.setCurrentItem(position, false);
        if (position == 1) {
            rb_car.setChecked(true);
        }else if (position == 0) {
            rb_home.setChecked(true);
        } else if (position == 2) {
            rb_discover.setChecked(true);
        } else if (position == 3) {
            rb_my.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    //在Fragment里的软件盘消失
    public interface MyTouchListener {
        void onTouchEvent(MotionEvent event);
    }

    // 保存MyTouchListener接口的列表
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {

        myTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
