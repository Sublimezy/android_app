package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.icbc.paysdk.services.FileUtil;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;


import org.song.videoplayer.DemoQSVideoView;
import org.song.videoplayer.IVideoPlayer;
import org.song.videoplayer.PlayListener;
import org.song.videoplayer.QSVideo;
import org.song.videoplayer.Util;
import org.song.videoplayer.media.AndroidMedia;
import org.song.videoplayer.media.BaseMedia;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 6/21/21/3:05 PM .
 * #            com.xueyiche.zjyk.xueyiche.driverschool.driverschool
 * #            xueyiche3.0
 */
public class MiJiContentActivityTwo extends AppCompatActivity {
    //    @BindView(R.id.mVideoPlayer)
//    JzvdStd videoplayer;
    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.ll_exam_back)
    LinearLayout llExamBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.qs)
    DemoQSVideoView demoVideoView;
    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miji_content);
        ButterKnife.bind(this);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        tvTitle.setText("" + getIntent().getStringExtra("title"));
        url = getIntent().getStringExtra("url");

        Glide.with(App.context).load("" + getIntent().getStringExtra("img")).into(demoVideoView.getCoverImageView());
        demoVideoView.setAspectRatio(3);
        //进入全屏的模式 0横屏 1竖屏 2传感器自动横竖屏 3根据视频比例自动确定横竖屏      -1什么都不做
        demoVideoView.enterFullMode = 3;
        Util.CLEAR_FULL(MiJiContentActivityTwo.this);
        //是否非全屏下也可以手势调节进度

        demoVideoView.isWindowGesture = false;
        demoVideoView.setPlayListener(new PlayListener() {

            int mode;

            @Override
            public void onStatus(int status) {//播放状态
                if (status == IVideoPlayer.STATE_AUTO_COMPLETE)
                    demoVideoView.quitWindowFullscreen();//播放完成退出全屏
            }

            @Override//全屏/普通/浮窗
            public void onMode(int mode) {
                this.mode = mode;
            }

            @Override
            public void onEvent(int what, Integer... extra) {
//                if (what == DemoQSVideoView.EVENT_CONTROL_VIEW && mode == IVideoPlayer.MODE_WINDOW_NORMAL) {
//                    if (Build.VERSION.SDK_INT >= 19) {
////                        if (extra[0] == 0)//状态栏隐藏/显示
////                            Util.CLEAR_FULL(MainActivity.this);
////                        else
//                            Util.SET_FULL(MainActivity.this);
//                    }
//                }
                //系统浮窗点击退出退出activity
                if (what == DemoQSVideoView.EVENT_CLICK_VIEW
                        && extra[0] == R.id.help_float_close)
                    if (demoVideoView.isSystemFloatMode())
                        finish();
            }

        });
        play(url, AndroidMedia.class);

    }

    Class<? extends BaseMedia> decodeMedia;

    private void play(String url, Class<? extends BaseMedia> decodeMedia) {
        demoVideoView.release();
        demoVideoView.setDecodeMedia(decodeMedia);

        demoVideoView.setUp(
                QSVideo.Build(url).title("" + getIntent().getStringExtra("title")).definition("标清").resolution("标清 720P").build(),
                QSVideo.Build(url).title("" + getIntent().getStringExtra("title")).definition("高清").resolution("高清 1080P").build(),
                QSVideo.Build(url).title("" + getIntent().getStringExtra("title")).definition("2K").resolution("超高清 2K").build(),
                QSVideo.Build(url).title("" + getIntent().getStringExtra("title")).definition("4K").resolution("极致 4K").build()

        );
//        demoVideoView.setUp(url, "这是一个标题");
        //demoVideoView.seekTo(12000);
        demoVideoView.openCache();//缓存配置见最后,缓存框架可能会出错,
        demoVideoView.play();
        this.url = url;
        this.decodeMedia = decodeMedia;

    }

    private boolean playFlag;//记录退出时播放状态 回来的时候继续播放
    private int position;//记录销毁时的进度 回来继续该进度播放
    private Handler handler = new Handler();

    @Override
    public void onResume() {
        super.onResume();
        if (playFlag)
            demoVideoView.play();
        handler.removeCallbacks(runnable);
        if (position > 0) {
            demoVideoView.seekTo(position);
            position = 0;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (demoVideoView.isSystemFloatMode())
            return;
        //暂停
        playFlag = demoVideoView.isPlaying();
        demoVideoView.pause();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (demoVideoView.isSystemFloatMode())
            return;
        //进入后台不马上销毁,延时15秒
        handler.postDelayed(runnable, 1000 * 15);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();//销毁
        if (demoVideoView.isSystemFloatMode())
            demoVideoView.quitWindowFloat();
        demoVideoView.release();
        handler.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (demoVideoView.getCurrentState() != IVideoPlayer.STATE_AUTO_COMPLETE)
                position = demoVideoView.getPosition();
            demoVideoView.release();
        }
    };

    //返回键
    @Override
    public void onBackPressed() {
        //全屏和系统浮窗不finish
        if (demoVideoView.onBackPressed()) {
            if (demoVideoView.isSystemFloatMode())
                //系统浮窗返回上一界面 android:launchMode="singleTask"
                moveTaskToBack(true);
            return;
        }
        super.onBackPressed();
    }


    @OnClick({R.id.iv_login_back, R.id.ll_exam_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }
}
