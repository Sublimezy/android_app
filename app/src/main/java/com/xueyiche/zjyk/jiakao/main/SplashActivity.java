package com.xueyiche.zjyk.jiakao.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mob.MobSDK;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.utils.AppUtils;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;
import com.xueyiche.zjyk.jiakao.utils.XieYiDialogUtils;

//程序入口
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String TAG = SplashActivity.class.getSimpleName();
    private TextView tv_jump;
    private boolean choice_city;
    private AlphaAnimation alpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        tv_jump = (TextView) findViewById(R.id.tv_jump);
        choice_city = PrefUtils.getBoolean(App.context, "choice_city", false);
        initViews();


    }

    /**
     * 腾讯要求 必须有协议提示
     */
    private void showXY() {
        XieYiDialogUtils.Builder builder = new XieYiDialogUtils.Builder(SplashActivity.this, false, false, "", "同意",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MobSDK.submitPolicyGrantResult(true);
                        MyPreferences.getInstance(SplashActivity.this).setAgreePrivacyAgreement(true);
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }, "拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                AppUtils.AppExit(SplashActivity.this);
            }
        });
        builder.create().show();

    }


    private void initViews() {
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

    //抑制特定Lint检查的警告或错误
    @SuppressLint("WrongConstant")
    private void res() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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
