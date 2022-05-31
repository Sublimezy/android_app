package com.xueyiche.zjyk.xueyiche.splash;

import android.Manifest;
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

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.ads.splash.SplashADZoomOutListener;
import com.qq.e.comm.util.AdError;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.bugly.Bugly;
import com.umeng.analytics.MobclickAgent;
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


public class SplashActivity extends AppCompatActivity implements SplashADZoomOutListener, View.OnClickListener {
    protected static final String TAG = SplashActivity.class.getSimpleName();
    public static final String PREF_IS_USER_GUIDE_SHOWED = "is_user_guide_showed";//标记新手引导是否已经展示过
    private TextView tv_jump;
    private CountDownTimer start;
    private boolean showed;
    private int randNum;
    private boolean choice_city;
    private boolean mFristLoad = true;
    private AlphaAnimation alpha;
    private ProcessResultUtil mProcessResultUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  getSupportActionBar().hide();
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
//        XueYiCheUtils.getNowLocation(SplashActivity.this);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);// 设置统计场景类型为普通统计
        tv_jump = (TextView) findViewById(R.id.tv_jump);
        choice_city = PrefUtils.getBoolean(App.context, "choice_city", false);
        mProcessResultUtil = new ProcessResultUtil(this);
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
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 350;
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
                PrefUtils.putString(App.context, "app_xy", "1");
                showTime();
                if (App.splash_init) {
                    JPushInterface.setDebugMode(true);
                    Bugly.init(getApplicationContext(), "8a3ab79bd2", false);
                    JPushInterface.init(SplashActivity.this);
                    App.szImei = JPushInterface.getRegistrationID(SplashActivity.this);
                }
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        String start_date = time.substring(0, 10);
        String start_time = time.substring(11, time.length());
        ImageView mRl_splash = (ImageView) findViewById(R.id.image_splash);

        if (App.showAD) {
            //展示广告
            initAD();
        }
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
    }


    private void showTime() {


        if (App.showAD) {
            //展示广告
            findViewById(R.id.splash_main).setVisibility(View.GONE);
            res();

        } else {
            findViewById(R.id.splash_main).setVisibility(View.GONE);
            tv_jump.setVisibility(View.GONE);
//            start = new Couterdown(3000, 1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    tv_jump.setText("" + ((millisUntilFinished / 1000) + 1));
//                }
//
//                @Override
//                public String toClock(long millis) {
//                    return super.toClock(millis);
//                }
//
//                @Override
//                public void onFinish() {
//                    res();
//                }
//
//
//            }.start();
                    res();
        }

    }

    private void goMainActivity() {
        if (choice_city) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                                            startActivity(new Intent(SplashActivity.this, LocationActivity.class));
            finish();
        }

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    private void res() {
        AndPermission.with(SplashActivity.this)
                .permission( Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Permission.CAMERA, Permission.ACCESS_FINE_LOCATION,Permission.ACCESS_COARSE_LOCATION,Permission.READ_PHONE_STATE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                    gonext();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
//                        Uri packageURI = Uri.parse("package:" + getPackageName());
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                        gonext();
                    }
                }).start();
    }
    public static final String SPLASH_POS_ID = "2001447730515391";

    private void gonext() {
        if (App.showAD) {

            fetchSplashAD(SplashActivity.this, container, SPLASH_POS_ID, SplashActivity.this);

        } else {
            goMainActivity();

        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    //-------------------------- 腾讯广告----------------------------------------

    private SplashAD splashAD;
    private ViewGroup container;
    private ViewGroup zoomOutView;
    private ImageView splashHolder;

    public boolean canJump = false;
    private boolean needStartDemoList = true;

    private boolean loadAdOnly = false;
    private boolean showingAd = false;
    private boolean isFullScreen = false;
    private Integer fetchDelay;
    private LinearLayout loadAdOnlyView;
    private Button loadAdOnlyCloseButton;
    private Button loadAdOnlyDisplayButton;
    private Button loadAdOnlyRefreshButton;
    private TextView loadAdOnlyStatusTextView;

    /**
     * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
     * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
     * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值（单位ms）
     **/
    private int minSplashTimeWhenNoAD = 2000;
    /**
     * 记录拉取广告的时间
     */
    private long fetchSplashADTime = 0;
    private Handler handler = new Handler(Looper.getMainLooper());

    private boolean isZoomOut = false;
    private boolean isSupportZoomOut = true;
    private boolean isZoomOutInAnother = false;

    private void initAD() {
        Intent intent = getIntent();

        splashHolder = (ImageView) findViewById(R.id.splash_holder);
        boolean needLogo = intent.getBooleanExtra("need_logo", true);
        needStartDemoList = intent.getBooleanExtra("need_start_demo_list", true);
        loadAdOnly = intent.getBooleanExtra("load_ad_only", false);
        isSupportZoomOut = intent.getBooleanExtra("support_zoom_out", false);
        isZoomOutInAnother = intent.getBooleanExtra("zoom_out_in_another", false);
        isFullScreen = intent.getBooleanExtra("is_full_screen", false);
        fetchDelay = (Integer) intent.getSerializableExtra("fetch_delay");

        loadAdOnlyView = findViewById(R.id.splash_load_ad_only);
        loadAdOnlyCloseButton = findViewById(R.id.splash_load_ad_close);
        loadAdOnlyCloseButton.setOnClickListener(this);
        loadAdOnlyDisplayButton = findViewById(R.id.splash_load_ad_display);
        loadAdOnlyDisplayButton.setOnClickListener(this);
        loadAdOnlyRefreshButton = findViewById(R.id.splash_load_ad_refresh);
        loadAdOnlyRefreshButton.setOnClickListener(this);
        loadAdOnlyStatusTextView = findViewById(R.id.splash_load_ad_status);

        if (loadAdOnly) {
            loadAdOnlyView.setVisibility(View.VISIBLE);
            loadAdOnlyStatusTextView.setText("广告加载中...");
            loadAdOnlyDisplayButton.setEnabled(false);
        }
        if (!needLogo) {
            findViewById(R.id.app_logo).setVisibility(View.GONE);
        }
    }


    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity    展示广告的activity
     * @param adContainer 展示广告的大容器
     * @param posId       广告位ID
     * @param adListener  广告状态监听器
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer,
                               String posId, SplashADListener adListener) {
        fetchSplashADTime = System.currentTimeMillis();
        splashAD = getSplashAd(activity, posId, adListener, fetchDelay);
        if (loadAdOnly) {
            if (isFullScreen) {
                splashAD.fetchFullScreenAdOnly();
            } else {
                splashAD.fetchAdOnly();
            }
        } else {
            if (isFullScreen) {
                splashAD.fetchFullScreenAndShowIn(adContainer);
            } else {
                splashAD.fetchAndShowIn(adContainer);
            }
        }
    }

    protected SplashAD getSplashAd(Activity activity, String posId,
                                   SplashADListener adListener, Integer fetchDelay) {
        SplashAD splashAD = new SplashAD(activity, posId, adListener, fetchDelay == null ? 0 : fetchDelay);
        if (isFullScreen) {
            splashAD.setDeveloperLogo(getIntent().getIntExtra("developer_logo", 0));
        }
        return splashAD;
    }

    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            if (keyCode == KeyEvent.KEYCODE_BACK && loadAdOnlyView.getVisibility() == View.VISIBLE) {
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_load_ad_close:
                this.finish();
                break;
            case R.id.splash_load_ad_refresh:
                showingAd = false;
                if (isFullScreen) {
                    splashAD.fetchFullScreenAdOnly();
                } else {
                    splashAD.fetchAdOnly();
                }
                this.loadAdOnlyStatusTextView.setText(R.string.splash_loading);
                loadAdOnlyDisplayButton.setEnabled(false);
                break;
            case R.id.splash_load_ad_display:
                loadAdOnlyView.setVisibility(View.GONE);
                showingAd = true;
                if (isFullScreen) {
                    splashAD.showFullScreenAd(container);
                } else {
                    splashAD.showAd(container);
                }
                break;
            default:
        }
    }

    @Override
    public void onZoomOut() {
        isZoomOut = true;
        Log.d("AD_DEMO", "onZoomOut");
        if (isZoomOutInAnother) {
            next();
        } else {
            SplashZoomOutManager splashZoomOutManager = SplashZoomOutManager.getInstance();
            ViewGroup content = findViewById(android.R.id.content);
            zoomOutView = splashZoomOutManager.startZoomOut(container.getChildAt(0), content, content,
                    new SplashZoomOutManager.AnimationCallBack() {
                        @Override
                        public void animationStart(int animationTime) {
                            Log.d("AD_DEMO", "animationStart:" + animationTime);
                        }

                        @Override
                        public void animationEnd() {
                            Log.d("AD_DEMO", "animationEnd");
                            splashAD.zoomOutAnimationFinish();
                        }
                    });
            findViewById(R.id.splash_main).setVisibility(View.GONE);
        }
    }

    @Override
    public void onZoomOutPlayFinish() {
        Log.d("AD_DEMO", "onZoomOutPlayFinish");
    }

    @Override
    public boolean isSupportZoomOut() {
        return isSupportZoomOut;
    }

    /**
     * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。当从广告落地页返回以后，
     * 才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
     */
    private void next() {
        if (canJump) {
            if (needStartDemoList) {
//                this.startActivity(new Intent(this, DemoListActivity.class));
                goMainActivity();
            }
            if (isZoomOut && isZoomOutInAnother) {
                //防止移除view后显示底图导致屏幕闪烁
                Bitmap b = splashAD.getZoomOutBitmap();
                if (b != null) {
                    splashHolder.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    splashHolder.setImageBitmap(b);
                }
                SplashZoomOutManager zoomOutManager = SplashZoomOutManager.getInstance();
                zoomOutManager.setSplashInfo(splashAD, container.getChildAt(0),
                        getWindow().getDecorView());
                this.setResult(RESULT_OK);
            }
            this.finish();
        } else {
            canJump = true;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
        MobclickAgent.onPageEnd("SplashActivity"); // [(仅有Activity的应用中SDK自动调用,不需要单独写)保证onPageEnd在onPause之前调用,因为onPause中会保存信息。参数页面名称,可自定义]
        MobclickAgent.onPause(this);// 友盟统计，所有Activity中添加，父类添加后子类不用重复添加
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            next();
        }
        canJump = true;

        if (mFristLoad) {
            mFristLoad = false;
            String app_xy = PrefUtils.getString(App.context, "app_xy", "0");
            if ("0".equals(app_xy)) {
                showXY();
            } else {
                if (App.splash_init) {
                    JPushInterface.setDebugMode(true);
                    JPushInterface.init(this);
                    Bugly.init(getApplicationContext(), "8a3ab79bd2", false);
                    App.szImei = JPushInterface.getRegistrationID(this);
                }
                showTime();
            }

        }
        MobclickAgent.onPageStart("SplashActivity"); // [统计页面(仅有Activity的应用中SDK自动调用,不需要单独写。参数为页面名称,可自定义)]
        MobclickAgent.onResume(this);// 友盟统计，所有Activity中添加，父类添加后子类不用重复添加

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

        if (mProcessResultUtil != null) {
            mProcessResultUtil.release();
        }
        alpha.cancel();
    }

    @Override
    public void onADPresent() {
        Log.i("AD_DEMO", "SplashADPresent");
    }

    @Override
    public void onADClicked() {
        Log.i("AD_DEMO", "SplashADClicked clickUrl: "
                + (splashAD.getExt() != null ? splashAD.getExt().get("clickUrl") : ""));
    }

    /**
     * 倒计时回调，返回广告还将被展示的剩余时间。
     * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
     *
     * @param millisUntilFinished 剩余毫秒数
     */
    @Override
    public void onADTick(long millisUntilFinished) {
        Log.i("AD_DEMO", "SplashADTick " + millisUntilFinished + "ms");
    }

    @Override
    public void onADExposure() {
        Log.i("AD_DEMO", "SplashADExposure");
    }

    @Override
    public void onADLoaded(long expireTimestamp) {
        Log.i("AD_DEMO", "SplashADFetch expireTimestamp: " + expireTimestamp
                + ", eCPMLevel = " + splashAD.getECPMLevel() + ", ECPM: " + splashAD.getECPM());
        if (DownloadConfirmHelper.USE_CUSTOM_DIALOG) {
            splashAD.setDownloadConfirmListener(DownloadConfirmHelper.DOWNLOAD_CONFIRM_LISTENER);
        }
        if (loadAdOnly) {
            loadAdOnlyDisplayButton.setEnabled(true);
            long timeIntervalSec = (expireTimestamp - SystemClock.elapsedRealtime()) / 1000;
            long min = timeIntervalSec / 60;
            long second = timeIntervalSec - (min * 60);
            loadAdOnlyStatusTextView.setText("加载成功,广告将在:" + min + "分" + second + "秒后过期，请在此之前展示(showAd)");
        }
    }

    @Override
    public void onADDismissed() {
        Log.i("AD_DEMO", "SplashADDismissed");
        if (zoomOutView != null) {
            ViewUtils.removeFromParent(zoomOutView);
        }
        next();
    }

    @Override
    public void onNoAD(AdError error) {
        String str = String.format("LoadSplashADFail, eCode=%d, errorMsg=%s", error.getErrorCode(),
                error.getErrorMsg());
        Log.i("AD_DEMO", str);
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SplashActivity.this.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });
        if (loadAdOnly && !showingAd) {
            loadAdOnlyStatusTextView.setText(str);
            return;//只拉取广告时，不终止activity
        }    /**
         * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
         * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
         * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值
         **/
        long alreadyDelayMills = System.currentTimeMillis() - fetchSplashADTime;//从拉广告开始到onNoAD已经消耗了多少时间
        long shouldDelayMills = alreadyDelayMills > minSplashTimeWhenNoAD ? 0 : minSplashTimeWhenNoAD
                - alreadyDelayMills;//为防止加载广告失败后立刻跳离开屏可能造成的视觉上类似于"闪退"的情况，根据设置的minSplashTimeWhenNoAD
        // 计算出还需要延时多久
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (needStartDemoList) {
//                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, DemoListActivity.class));
                    goMainActivity();
                }
//                SplashActivity.this.finish();
            }
        }, shouldDelayMills);
    }
}
