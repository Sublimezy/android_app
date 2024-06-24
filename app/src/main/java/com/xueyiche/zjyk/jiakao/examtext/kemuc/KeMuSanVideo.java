package com.xueyiche.zjyk.jiakao.examtext.kemuc;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.utils.NetUtil;


public class KeMuSanVideo extends BaseActivity implements View.OnClickListener{
    private VideoView video_view;
    private ImageView iv_kesan_back;
    @Override
    protected int initContentView() {
        return R.layout.kemusan_viideo;
    }

    @Override
    protected void initView() {
        video_view = (VideoView) view.findViewById(R.id.video_view);
        iv_kesan_back = (ImageView) view.findViewById(R.id.iv_kesan_back);
        iv_kesan_back.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String subjects_name = intent.getStringExtra("subjects_name");
        String video_url = intent.getStringExtra("video_url");
        Uri uri = Uri.parse( video_url );
        //设置视频控制器
        video_view.setMediaController(new MediaController(this));
        video_view.setOnCompletionListener(new MyPlayerOnCompletionListener());
        //播放完成回调
        int netWorkState = NetUtil.getNetWorkState(App.context);

        if (netWorkState==2) {
            showToastShort("建议Wifi环境下观看视频");
            video_view.setVideoURI(uri);
            video_view.start();
        }else if(netWorkState==1){
            video_view.setVideoURI(uri);
            video_view.start();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_kesan_back:
                finish();
                break;
        }
    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            finish();

        }
    }

}
