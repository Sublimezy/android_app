package com.xueyiche.zjyk.xueyiche.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.permissionx.guolindev.PermissionX;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.ads.splash.SplashADZoomOutListener;
import com.qq.e.comm.util.AdError;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.bugly.Bugly;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.constants.bean.GuangGaoBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.Couterdown;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.splash.bean.CodeBean;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.ProcessResultUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


public class SplashActivity extends AppCompatActivity implements  View.OnClickListener {
    protected static final String TAG = SplashActivity.class.getSimpleName();
    private TextView tv_jump;
    private boolean choice_city;
    private AlphaAnimation alpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  getSupportActionBar().hide();
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
//        XueYiCheUtils.getNowLocation(SplashActivity.this);

        tv_jump = (TextView) findViewById(R.id.tv_jump);
        choice_city = PrefUtils.getBoolean(App.context, "choice_city", false);
        initViews();


    }

    private void showXY() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.yinsi_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        TextView tv_tishi_content = (TextView) view.findViewById(R.id.tv_tishi_content);
        tv_tishi_content.setHighlightColor(getResources().getColor(android.R.color.transparent));
        SpannableString spanableInfo = new SpannableString("请你务必审慎阅读、充分理解“服务协议”和“隐私政策”各条款，包括但不限于：" +
                "为了向你提供内容分享等服务，我们需要收集你的设备信息、操作日志等个人信息。你可阅读《服务协议》和《隐私政策》了解" +
                "详细信息。如你同意，请点击“同意”开始接受我们的服务");
        spanableInfo.setSpan(new Clickable(clickListener1), 78, 83, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new Clickable(clickListener2), 85, 90, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_tishi_content.setText(spanableInfo);
        tv_tishi_content.setMovementMethod(LinkMovementMethod.getInstance());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 200;
        params.height = screenHeight - 400;
        dialog01.getWindow().setAttributes(params);//设置大小
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPreferences.getInstance(SplashActivity.this).setAgreePrivacyAgreement(true);
//                showTime();
//                JPushInterface.setDebugMode(true);
                Bugly.init(getApplicationContext(), "8a3ab79bd2", false);
                JPushInterface.init(SplashActivity.this);
                App.szImei = JPushInterface.getRegistrationID(SplashActivity.this);
                PermissionX.init(SplashActivity.this).permissions(
                                Permission.CAMERA,
                                Permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Permission.READ_PHONE_STATE)
                        .explainReasonBeforeRequest()
                        .request((allGranted, grantedList, deniedList) ->
                                {
                                    MyPreferences.getInstance(SplashActivity.this).setInitSplashPermission(true);
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                        );
                dialog01.dismiss();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    private View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SplashActivity.this, UrlActivity.class);
            intent.putExtra("url", "http://tabankeji.com/djh5/daijiafuwuxieyi.html");
            intent.putExtra("type", "2");
            startActivity(intent);

        }
    };
    private View.OnClickListener clickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SplashActivity.this, UrlActivity.class);
            intent.putExtra("url", "http://tabankeji.com/djh5/gerenxinxibaohuzhengce.html");
            intent.putExtra("type", "2");
            startActivity(intent);

        }
    };


    class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        /**
         * 重写父类点击事件
         */
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        /**
         * 重写父类updateDrawState方法  我们可以给TextView设置字体颜色,背景颜色等等...
         */
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.colorBlue));
        }
    }

    private void initViews() {
        final String area_id = PrefUtils.getString(App.context, "area_id", "qinglei");
        ImageView mRl_splash = (ImageView) findViewById(R.id.image_splash);
//        mRl_splash.setScaleType(ImageView.ScaleType.FIT_XY);
        // 渐变动画
        alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        AnimationSet set = new AnimationSet(false);// 初始化动画集合
        set.addAnimation(alpha);
        //set.addAnimation(alpha);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画结束的回调
            @Override
            public void onAnimationEnd(Animation animation) {
            }

        });
        mRl_splash.startAnimation(set);
        showTime();
    }
    @SuppressLint("WrongConstant")
    private void res() {
        boolean agree = MyPreferences.getInstance(SplashActivity.this).hasInitSplashPermission();
        if (agree) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            PermissionX.init(SplashActivity.this).permissions(
                            Permission.CAMERA,
                            Permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Permission.READ_PHONE_STATE)
                    .explainReasonBeforeRequest()
                    .request((allGranted, grantedList, deniedList) ->
                            {
                                MyPreferences.getInstance(SplashActivity.this).setInitSplashPermission(true);
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                    );
        }
    }

    private void showTime() {
        boolean agree = MyPreferences.getInstance(SplashActivity.this).hasAgreePrivacyAgreement();
        if (!agree) {
            showXY();
        } else {
            res();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_load_ad_close:
                this.finish();
                break;
            case R.id.splash_load_ad_refresh:

                break;
            case R.id.splash_load_ad_display:

                break;
            default:
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        alpha.cancel();
    }




}
