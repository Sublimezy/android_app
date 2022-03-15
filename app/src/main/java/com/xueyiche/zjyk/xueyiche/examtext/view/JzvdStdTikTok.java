//package com.xueyiche.zjyk.xueyiche.examtext.view;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//
//
//import com.xueyiche.zjyk.xueyiche.R;
//
//import cn.jzvd.JzvdStd;
//import tv.danmaku.ijk.media.player.IMediaPlayer;
//
//
//public class JzvdStdTikTok extends JzvdStd {
//    public JzvdStdTikTok(Context context) {
//        super(context);
//    }
//
//    public JzvdStdTikTok(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public void init(Context context) {
//        super.init(context);
//        SAVE_PROGRESS = false;
//        bottomContainer.setVisibility(GONE);
//        topContainer.setVisibility(GONE);
//        bottomProgressBar.setVisibility(GONE);
//        posterImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        fullscreenButton.setVisibility(GONE);
//
//    }
//
//
//    //changeUiTo 真能能修改ui的方法
//    @Override
//    public void changeUiToNormal() {
//        super.changeUiToNormal();
//        bottomContainer.setVisibility(GONE);
//
//        bottomProgressBar.setVisibility(GONE);
//        topContainer.setVisibility(GONE);
//        fullscreenButton.setVisibility(GONE);
//    }
//
//    @Override
//    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro,
//                                        int posterImg, int bottomPro, int retryLayout) {
//        topContainer.setVisibility(topCon);
//        bottomContainer.setVisibility(GONE);
//        startButton.setVisibility(startBtn);
//        loadingProgressBar.setVisibility(loadingPro);
//        posterImageView.setVisibility(posterImg);
//
//        bottomProgressBar.setVisibility(GONE);
//        bottomProgressBar.setVisibility(GONE);
//        fullscreenButton.setVisibility(GONE);
//        mRetryLayout.setVisibility(retryLayout);
//    }
//
//    @Override
//    public void dissmissControlView() {
//        if (state != STATE_NORMAL
//                && state != STATE_ERROR
//                && state != STATE_AUTO_COMPLETE) {
//            post(() -> {
//
//                bottomProgressBar.setVisibility(GONE);
//                bottomContainer.setVisibility(View.GONE);
//                topContainer.setVisibility(View.INVISIBLE);
//                startButton.setVisibility(View.INVISIBLE);
//                if (clarityPopWindow != null) {
//                    clarityPopWindow.dismiss();
//                }
//                if (screen != SCREEN_TINY) {
//                    bottomProgressBar.setVisibility(View.GONE);
//                }
//
//                bottomProgressBar.setVisibility(GONE);
//            });
//        }
//    }
//
//
//    @Override
//    public void onClickUiToggle() {
//        super.onClickUiToggle();
//        Log.i(TAG, "click blank");
//        startButton.performClick();
//        bottomContainer.setVisibility(GONE);
//        topContainer.setVisibility(GONE);
//
//        bottomProgressBar.setVisibility(GONE);
//    }
//
//    public void updateStartImage() {
//        if (state == STATE_PLAYING) {
//            startButton.setVisibility(VISIBLE);
//            startButton.setImageResource(R.mipmap.tiktok_play_tiktok);
//            replayTextView.setVisibility(GONE);
//        } else if (state == STATE_ERROR) {
//            startButton.setVisibility(INVISIBLE);
//            replayTextView.setVisibility(GONE);
//        } else if (state == STATE_AUTO_COMPLETE) {
//            startButton.setVisibility(VISIBLE);
//            startButton.setImageResource(R.mipmap.tiktok_play_tiktok);
//            replayTextView.setVisibility(VISIBLE);
//        } else {
//            startButton.setImageResource(R.mipmap.tiktok_play_tiktok);
//            replayTextView.setVisibility(GONE);
//        }
//
//        bottomProgressBar.setVisibility(GONE);
//    }
//    @Override
//    public void onInfo(int what, int extra) {
//        super.onInfo(what, extra);
//        if(what== IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED){
//            //这里返回了视频的旋转角度，根据角度旋转视频到正确角度
//            textureView.setRotation(extra);
//        }
//    }
//
//    /**
//     * 重复播放
//     */
//    @Override
//    public void onStateAutoComplete() {
//        super.onStateAutoComplete();
//        Log.i(TAG, "onStateAutoComplete: " + "播放完了");
//        startVideo();
//    }
//
//    @Override
//    public void onCompletion() {
//        super.onCompletion();
//        Log.i(TAG, "onCompletion: " + "播放完了");
//    }
//}
