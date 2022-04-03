package com.xueyiche.zjyk.xueyiche.main.activities.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.MyBasePagerAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.GuangGaoBean;
import com.xueyiche.zjyk.xueyiche.constants.bean.GuangGaoHomeBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.main.view.NoScrollViewPager;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.receive.LocationService;
import com.xueyiche.zjyk.xueyiche.receive.UpLocationService;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
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

/**
 * Created by Owner on 2016/9/13.
 */
public class MainActivity extends BaseActivity {
    private long exitTime;
    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void initView() {
        initData();
        String szImei = App.szImei;
        if (TextUtils.isEmpty(szImei)) {
            Intent bindintent = new Intent(this, LocationService.class);
            startService(bindintent);
        }
    }
    @Override
    protected void initListener() {
    }
    @Override
    protected void initData() {

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
        updateLonLatUser();
    }
    private void updateLonLatUser() {
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
    protected void onDestroy() {
        Intent bindintent = new Intent(this, LocationService.class);
        stopService(bindintent);
        super.onDestroy();
    }
}
