package com.xueyiche.zjyk.xueyiche.community.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.community.BaseBottomSheetDialog;
import com.xueyiche.zjyk.xueyiche.community.OnVideoControllerListener;
import com.xueyiche.zjyk.xueyiche.community.bean.CallSuccessBean;
import com.xueyiche.zjyk.xueyiche.community.bean.TuWenDetailBean;
import com.xueyiche.zjyk.xueyiche.community.cache.PreloadManager;
import com.xueyiche.zjyk.xueyiche.community.video.TikTokController;
import com.xueyiche.zjyk.xueyiche.community.video.TikTokRenderViewFactory;
import com.xueyiche.zjyk.xueyiche.community.video.Tiktok3Adapter;
import com.xueyiche.zjyk.xueyiche.community.video.TiktokBean;
import com.xueyiche.zjyk.xueyiche.community.video.Utils;
import com.xueyiche.zjyk.xueyiche.community.video.VerticalViewPager;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.CommentDialog;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

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
    private CommentAdapter commentAdapter;
    private List<TuWenDetailBean.DataBean.MessagelistBean> messagelist;

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

        mTiktok2Adapter.setListener(new OnVideoControllerListener() {
            @Override
            public void onHeadClick() {

            }

            @Override
            public void onLikeClick() {

            }

            @Override
            public void onCommentClick() {
                //展示评论
//                showCommentDialog();
                showPLDialog(ShiPinPlayActivity.this, ShiPinPlayActivity.this);

            }

            @Override
            public void onShareClick() {

            }
        });
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
                    data.authorImgUrl = json.getData().getNewsinfo().getAvatar();
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
                    messagelist = json.getData().getMessagelist();

                } else {

                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }


    class CommentDialog extends BaseBottomSheetDialog {
        @BindView(R.id.dialog_bottomsheet_rv_lists)
        RecyclerView recyclerView;
        @BindView(R.id.tv_pinglunshu)
        TextView tv_pinglunshu;
        @BindView(R.id.tvZanWu)
        TextView tvZanWu;
        @BindView(R.id.rl_comment)
        RelativeLayout rl_comment;

        private View view;
        List<TuWenDetailBean.DataBean.MessagelistBean> data;

        public CommentDialog(List<TuWenDetailBean.DataBean.MessagelistBean> data) {
            this.data = data;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.dialog_bottomsheet, container);
            ButterKnife.bind(this, view);

            init();

            return view;
        }

        private void init() {


        }


        @Override
        protected int getHeight() {
            return getResources().getDisplayMetrics().heightPixels - 600;
        }
    }

    private void showPLDialog(Context context, Activity activity) {
        RelativeLayout ll_popup;
        PopupWindow pop;
        pop = new PopupWindow(context);
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_pl,
                null);
        ll_popup = view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = view.findViewById(R.id.parent);
        RecyclerView recyclerView = view.findViewById(R.id.dialog_bottomsheet_rv_lists);
        RelativeLayout rl_comment = view.findViewById(R.id.rl_comment);
        TextView tv_pinglunshu = view.findViewById(R.id.tv_pinglunshu);
        tv_pinglunshu.setText("评论" + messagelist.size() + "条");
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        commentAdapter = new CommentAdapter(R.layout.shuoche_pl_item);
        recyclerView.setAdapter(commentAdapter);
        commentAdapter.setNewData(messagelist);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        rl_comment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCommentDialog();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                context, R.anim.activity_translate_in));
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    class CommentAdapter extends BaseQuickAdapter<TuWenDetailBean.DataBean.MessagelistBean, BaseViewHolder> {

        public CommentAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, TuWenDetailBean.DataBean.MessagelistBean item) {
            helper.setText(R.id.tv_pingjia_name, item.getUser_nickname());
            helper.setText(R.id.tv_pl_content, item.getText());
            helper.setText(R.id.tv_pingjia_time, item.getCreatetime());
            CircleImageView civ = helper.getView(R.id.cv_pingjia);
            Glide.with(ShiPinPlayActivity.this).load(item.getUser_avatar()).into(civ);
        }
    }


    private void showCommentDialog() {
        new com.xueyiche.zjyk.xueyiche.utils.CommentDialog("说点什么...", new com.xueyiche.zjyk.xueyiche.utils.CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                if (!TextUtils.isEmpty(inputText)) {
                    Map<String, String> params = new HashMap<>();
                    params.put("article_id", getIntent().getStringExtra("id"));
                    params.put("text", inputText);
                    MyHttpUtils.postHttpMessage(AppUrl.message, params, CallSuccessBean.class, new RequestCallBack<CallSuccessBean>() {
                        @Override
                        public void requestSuccess(CallSuccessBean json) {

                            if (json.getCode() == 1) {
                                Map<String, String> params = new HashMap<>();
                                params.put("article_id", getIntent().getStringExtra("id"));
                                MyHttpUtils.postHttpMessage(AppUrl.articleinfo, params, TuWenDetailBean.class, new RequestCallBack<TuWenDetailBean>() {
                                    @Override
                                    public void requestSuccess(TuWenDetailBean json) {
                                        if (json.getCode() == 1) {
//                                            wrap.showContent();
                                            messagelist = json.getData().getMessagelist();

                                            commentAdapter.setNewData(messagelist);
                                        } else {
//                                            wrap.showError();
                                            showToastShort(json.getMsg());
                                        }


                                    }

                                    @Override
                                    public void requestError(String errorMsg, int errorType) {
//                                        wrap.showError();
                                    }
                                });
                            } else {
                            }
                            showToastShort(json.getMsg());
                        }

                        @Override
                        public void requestError(String errorMsg, int errorType) {
                            showToastShort("与服务器连接失败");

                        }
                    });

                } else {
                    Toast.makeText(ShiPinPlayActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                }

            }
        }).show(getSupportFragmentManager(), "comment");
    }
}