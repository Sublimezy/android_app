package com.xueyiche.zjyk.xueyiche.community.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.community.bean.TuWenDetailBean;
import com.xueyiche.zjyk.xueyiche.community.cache.PreloadManager;
import com.xueyiche.zjyk.xueyiche.community.video.TikTokController;
import com.xueyiche.zjyk.xueyiche.community.video.TikTokRenderViewFactory;
import com.xueyiche.zjyk.xueyiche.community.video.Tiktok3Adapter;
import com.xueyiche.zjyk.xueyiche.community.video.TiktokBean;
import com.xueyiche.zjyk.xueyiche.community.video.Utils;
import com.xueyiche.zjyk.xueyiche.community.video.VerticalViewPager;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShiPinPlayActivity extends BaseActivity {
    @BindView(R.id.vp2)
    ViewPager2 mViewPager;

    /**
     * 当前播放位置
     */
    private int mCurPos;
    private PreloadManager mPreloadManager;
    private Tiktok3Adapter mTiktok2Adapter;
    private TikTokController mController;
    private List<TiktokBean> mVideoList = new ArrayList<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_shi_pin_play;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initViewPager();
        initVideoView();
        mPreloadManager = PreloadManager.getInstance(this);
        addData(null);


//        refreshLayout.setEnableAutoLoadMore(true);
//        refreshLayout.setEnableFooterTranslationContent(false);
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                addData(null);
//                refreshLayout.finishLoadMore();
//                refreshLayout.closeHeaderOrFooter();
////                mViewPager.setCurrentItem(mCurPos, false);
//            }
//        });

        mVideoView.addOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {

            @Override
            public void onPlayStateChanged(int playState) {
                Log.i("播放完成", "onPlayStateChanged: " + playState);
                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
                    Log.i("播放完成", "onPlayStateChanged: ");
                    //播放完成  自动播放下一个
                    mViewPager.setCurrentItem(mCurPos, true);
//                    mViewPager.setCurrentItem(mCurPos + 1, true);
                }
            }
        });
    }

    private void initVideoView() {
        mVideoView = new VideoView(this);
        mVideoView.setLooping(true);

        //以下只能二选一，看你的需求
        mVideoView.setRenderViewFactory(TikTokRenderViewFactory.create());
//        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);

        mController = new TikTokController(this);
        mVideoView.setVideoController(mController);
    }

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(4);
        mTiktok2Adapter = new Tiktok3Adapter(mVideoList);
        mViewPager.setAdapter(mTiktok2Adapter);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            private int mCurItem;

            /**
             * VerticalViewPager是否反向滑动
             */
            private boolean mIsReverseScroll;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == mCurItem) {
                    return;
                }
                mIsReverseScroll = position < mCurItem;
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == mCurPos) return;
                mViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        startPlay(position);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                    mCurItem = mViewPager.getCurrentItem();
                }

                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    mPreloadManager.resumePreload(mCurPos, mIsReverseScroll);
                } else {
                    mPreloadManager.pausePreload(mCurPos, mIsReverseScroll);
                }
            }
        });

        //ViewPage2内部是通过RecyclerView去实现的，它位于ViewPager2的第0个位置
        mViewPagerImpl = (RecyclerView) mViewPager.getChildAt(0);


    }

    private RecyclerView mViewPagerImpl;

    private void startPlay(int position) {
        int count = mViewPagerImpl.getChildCount();
        for (int i = 0; i < count; i++) {
            View itemView = mViewPagerImpl.getChildAt(i);
            Tiktok3Adapter.ViewHolder viewHolder = (Tiktok3Adapter.ViewHolder) itemView.getTag();
            if (viewHolder.mPosition == position) {
                mVideoView.release();
                Utils.removeViewFormParent(mVideoView);

                TiktokBean tiktokBean = mVideoList.get(position);
                String playUrl = mPreloadManager.getPlayUrl(tiktokBean.videoDownloadUrl);
                Log.i("播放", "startPlay: " + "position: " + position + "  url: " + playUrl);
                mVideoView.setUrl(playUrl);
                mController.addControlComponent(viewHolder.mTikTokView, true);
                viewHolder.mPlayerContainer.addView(mVideoView, 0);
                mVideoView.start();
                mCurPos = position;

                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }
    }

    protected VideoView mVideoView;

    @Override
    public void onBackPressed() {
        if (mVideoView == null || !mVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void addData(View view) {

        getDataFromNet();
    }

    private void getDataFromNet() {
        String id = getIntent().getStringExtra("id");
        Map<String, String> params = new HashMap<>();
        params.put("article_id", id);
        MyHttpUtils.postHttpMessage(AppUrl.articleinfo, params, TuWenDetailBean.class, new RequestCallBack<TuWenDetailBean>() {
            @Override
            public void requestSuccess(TuWenDetailBean json) {
                if (json.getCode() == 1) {
                    int size = mVideoList.size();
//        mVideoList.addAll(DataUtil.getTiktokDataFromAssets(this));
                    TiktokBean data = new TiktokBean();
                    data.videoDownloadUrl = json.getData().getNewsinfo().getVideo_file();
                    data.title = json.getData().getNewsinfo().getTitle();
                    mVideoList.add(data);
                    //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
                    mTiktok2Adapter.notifyItemRangeChanged(size, mVideoList.size());

                    mViewPager.setCurrentItem(0);

                    mViewPager.post(new Runnable() {
                        @Override
                        public void run() {
                            startPlay(0);
                        }
                    });
                } else {

                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }
}