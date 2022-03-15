package com.xueyiche.zjyk.xueyiche.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.discover.bean.SheQuContentBean;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.discover.bean.VideoContentBean;
import com.xueyiche.zjyk.xueyiche.discover.view.CommentDialog;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.ObservableScrollView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2018/3/7.
 */
public class TeachVideoContent extends BaseActivity implements View.OnClickListener {
    private ImageView   iv_shop_share,iv_shop_back_all;
    private TextView tv_gv_content;
    private CommentAdapter commentAdapter;
    private String video_id,screenshot_image_url;
    private RefreshLayout refreshLayout;
    private AdListView lv_pinglun;
    private List<VideoContentBean.ContentBean.PinglunBean> list = new ArrayList<>();
    private List<VideoContentBean.ContentBean.PinglunBean> correlationComment;
    private int pager = 1;
    private TextView tv_pinglun_number;
    private String screenshot_video_url;
    private String user_id;
    private VideoContentBean.ContentBean content;
    private TextView tv_dz_number,tv_sc_number,tv_fx_number,tv_video_name,tv_video_title;
    private CircleImageView cv_head;
    private VideoView video_view;

    @Override
    protected int initContentView() {
        return R.layout.teach_video_content;
    }

    @Override
    protected void initView() {
        tv_gv_content = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_gv_content);
        tv_dz_number = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_dz_number);
        tv_sc_number = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_sc_number);
        tv_fx_number = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_fx_number);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        iv_shop_back_all = (ImageView) view.findViewById(R.id.iv_shop_back_all);
        cv_head = (CircleImageView) view.findViewById(R.id.cv_head);
        iv_shop_share = (ImageView) view.findViewById(R.id.iv_shop_share);
        lv_pinglun = (AdListView) view.findViewById(R.id.lv_pinglun);
        tv_video_title = (TextView) view.findViewById(R.id.tv_video_title);
        tv_video_name = (TextView) view.findViewById(R.id.tv_video_name);
        tv_pinglun_number = (TextView) view.findViewById(R.id.tv_pinglun_number);
        commentAdapter = new CommentAdapter(list, App.context, R.layout.shequ_comment_item);

        video_view =  findViewById(R.id.video_view);

        lv_pinglun.setFocusable(false);
    }

    @Override
    protected void initListener() {
        tv_gv_content.setOnClickListener(this);
        tv_dz_number.setOnClickListener(this);
        tv_sc_number.setOnClickListener(this);
        tv_fx_number.setOnClickListener(this);
        iv_shop_share.setOnClickListener(this);
        iv_shop_share.setVisibility(View.GONE);
        iv_shop_back_all.setOnClickListener(this);

    }
    private void contentDianZan() {
        if (content!=null) {
            String main_praise = content.getMain_praise();
            if ("0".equals(main_praise)) {
                if (XueYiCheUtils.IsHaveInternet(this)) {
                    showProgressDialog(false);
                    user_id = PrefUtils.getString(App.context, "user_id", "");
                    OkHttpUtils.post().url(AppUrl.FaXian_DianZan_Content)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("user_id", user_id)
                            .addParams("like_type", "3")
                            .addParams("video_id", video_id)
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                        int code = successDisCoverBackBean.getCode();
                                        if (200 == code) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    pager = 1;
                                                    getDataFromNet(true);
                                                }
                                            });
                                        }
                                    }
                                    return string;
                                }

                                @Override
                                public void onError(Request request, Exception e) {
                                    stopProgressDialog();
                                }

                                @Override
                                public void onResponse(Object response) {
                                    stopProgressDialog();
                                }
                            });
                }
            }
        }
    }
    private void showCommentDialog() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        new CommentDialog("说点什么...", new CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                if (!TextUtils.isEmpty(inputText)) {
                    OkHttpUtils.post().url(AppUrl.Discover_PingLun)
                            .addParams("user_id", user_id)
                            .addParams("device_id",LoginUtils.getId(TeachVideoContent.this))
                            .addParams("comment_type", "3")
                            .addParams("comment", inputText)
                            .addParams("video_id", video_id)
                            .build().execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                if (successDisCoverBackBean != null) {
                                    int code = successDisCoverBackBean.getCode();
                                    final String msg = successDisCoverBackBean.getMsg();
                                    if (!TextUtils.isEmpty("" + code)) {
                                        if (200 == code) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {

                                                    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                                    pager = 1;
                                                    getDataFromNet(true);
                                                    EventBus.getDefault().post(new MyEvent("视频"));
                                                    EventBus.getDefault().post(new MyEvent("更新首页"));
                                                }
                                            });
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

                        }
                    });
                } else {
                    Toast.makeText(TeachVideoContent.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                }

            }
        }).show(getSupportFragmentManager(), "comment");
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        user_id = PrefUtils.getString(App.context, "user_id", "");
        video_id = intent.getStringExtra("video_id");

        getDataFromNet(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            if (correlationComment != null && correlationComment.size() == 0) {
                                showToastShort(StringConstants.MEIYOUSHUJU);
                                refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                            } else {
                                pager += 1;
                                getMoreDataFromNet();
                                refreshLayout.finishLoadMore();
                            }
                        } else {
                            refreshLayout.finishLoadMore();
                            Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 1500);
            }
        });

    }

    private void getDataFromNet(final boolean isPingLun) {
        if (!TextUtils.isEmpty(video_id) && XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Discover_Video_Content)
                    .addParams("device_id", LoginUtils.getId(TeachVideoContent.this))
                    .addParams("pager", "" + pager)
                    .addParams("video_id", video_id)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                processData(string, false,isPingLun);
                            }
                            return string;
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            stopProgressDialog();
                        }

                        @Override
                        public void onResponse(Object response) {
                            stopProgressDialog();
                        }
                    });
        }
    }

    private void getMoreDataFromNet() {
        if (!TextUtils.isEmpty(video_id) && XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Discover_Video_Content)
                    .addParams("device_id", LoginUtils.getId(TeachVideoContent.this))
                    .addParams("pager", "" + pager)
                    .addParams("video_id", video_id)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                processData(string, true,false);
                            }
                            return string;
                        }

                        @Override
                        public void onError(Request request, Exception e) {

                            stopProgressDialog();
                        }

                        @Override
                        public void onResponse(Object response) {
                            stopProgressDialog();
                        }
                    });

        }
    }

    private void processData(String string, final boolean isMore, final boolean isPingLun) {
        final VideoContentBean sheQuContentBean = JsonUtil.parseJsonToBean(string, VideoContentBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (sheQuContentBean != null) {
                    int code = sheQuContentBean.getCode();
                    String msg = sheQuContentBean.getMsg();
                    if (!TextUtils.isEmpty(""+code)) {
                        if (200==code) {
                            content = sheQuContentBean.getContent();
                            correlationComment = sheQuContentBean.getContent().getPinglun();
                            if (content != null) {
                                if (content != null) {
                                    //是否收藏，0：未收藏，1：已收藏
                                    String main_collect = content.getMain_collect();
                                    //头像
                                    String head_img = content.getHead_img();
                                    Picasso.with(App.context).load(head_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(cv_head);
                                    String nickname = content.getNickname();
                                    tv_video_name.setText(nickname);
                                    String title = content.getTitle();
                                    tv_video_title.setText(title);
                                    screenshot_image_url = content.getScreenshot_image_url();
                                     screenshot_video_url = content.getScreenshot_video_url();
                                    if (!isPingLun) {
                                        video_view.setVideoPath(screenshot_video_url);
                                        video_view.start();
//                                        bofang(screenshot_video_url,screenshot_image_url);
                                    }
                                    //当前用户对该问题没点赞0，1：已点赞
                                    String main_praise = content.getMain_praise();
                                    if ("0".equals(main_praise)) {
                                        Drawable drawable = getResources().getDrawable(R.mipmap.dz_bt);
                                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                        tv_dz_number.setCompoundDrawables(drawable, null, null, null);
                                    } else if ("1".equals(main_praise)) {
                                        Drawable drawable = getResources().getDrawable(R.mipmap.dz_bt_select);
                                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                        tv_dz_number.setCompoundDrawables(drawable, null, null, null);
                                    }
                                    if ("0".equals(main_collect)) {
                                        Drawable drawable = getResources().getDrawable(R.mipmap.sc_bt);
                                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                        tv_sc_number.setCompoundDrawables(drawable, null, null, null);
                                    } else if ("1".equals(main_praise)) {
                                        Drawable drawable = getResources().getDrawable(R.mipmap.sc_bt_select);
                                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                        tv_sc_number.setCompoundDrawables(drawable, null, null, null);
                                    }
                                    String praise_count = content.getPraise_count();
                                    String collect_count = content.getCollect_count();
                                    String share_count = content.getShare_count();
                                    tv_dz_number.setText(praise_count);
                                    tv_sc_number.setText(collect_count);
                                    tv_fx_number.setText(share_count);
                                    String comment_count = content.getComment_count();
                                    tv_pinglun_number.setText("共" + comment_count + "条评论");
                                }
                                if (correlationComment != null) {
                                    if (!isMore) {
                                        if (list.size() != 0) {
                                            list.clear();
                                        }
                                        list.addAll(correlationComment);
                                        lv_pinglun.setAdapter(commentAdapter);
                                        XueYiCheUtils.setListViewHeightBasedOnChildren(lv_pinglun);
                                    } else {
                                        list.addAll(correlationComment);
                                        commentAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }else {
                        LogUtil.e("log",msg);
                    }

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shop_back_all:
                finish();
                break;
            case R.id.tv_gv_content:
                if (DialogUtils.IsLogin()) {
                    showCommentDialog();
                }else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.tv_dz_number:
                //点赞
                contentDianZan();
                break;
            case R.id.tv_sc_number:
                //收藏
                contentShouCang();
                break;
            case R.id.iv_shop_share:
                fenXiang();
                break;
            case R.id.tv_fx_number:
                fenXiang();
                break;


        }
    }
    private void fenXiang() {
        if (DialogUtils.IsLogin()) {
            XueYiCheUtils.czhFenXiang(App.context,
                    TeachVideoContent.this, "学易车-易动华夏",
                    "http://xueyiche.cn/article/index.php?device_id=" + LoginUtils.getId(this) + "&id=" + video_id,
                    StringConstants.SHARED_TEXT + "http://www.xueyiche.cn/",
                    "http://ojljpzjpm.bkt.clouddn.com/shar.jpg",
                    "http://xueyiche.cn/article/index.php?device_id=" + LoginUtils.getId(this) + "&id=" + video_id,"1",video_id);
        } else {
            openActivity(LoginFirstStepActivity.class);
        }

    }
    private void contentShouCang() {
        if (content != null) {
            //是否收藏，0：未收藏，1：已收藏
            String main_collect = content.getMain_collect();
            if ("0".equals(main_collect)) {
                if (XueYiCheUtils.IsHaveInternet(TeachVideoContent.this)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.Czh_Collect)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("like_type", "3")
                            .addParams("kind_type", "1")
                            .addParams("refer_id", video_id)
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                        int code = successDisCoverBackBean.getCode();
                                        if (200 == code) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    pager = 1;
                                                    getDataFromNet(true);
                                                }
                                            });
                                        }
                                    }
                                    return string;
                                }

                                @Override
                                public void onError(Request request, Exception e) {
                                    stopProgressDialog();
                                }

                                @Override
                                public void onResponse(Object response) {
                                    stopProgressDialog();
                                }
                            });
                }
            }else {
                if (XueYiCheUtils.IsHaveInternet(TeachVideoContent.this)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.Czh_Collect)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("like_type", "3")
                            .addParams("kind_type", "2")
                            .addParams("refer_id", video_id)
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                        int code = successDisCoverBackBean.getCode();
                                        if (200 == code) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    pager = 1;
                                                    getDataFromNet(true);
                                                }
                                            });
                                        }
                                    }
                                    return string;
                                }

                                @Override
                                public void onError(Request request, Exception e) {
                                    stopProgressDialog();
                                }

                                @Override
                                public void onResponse(Object response) {
                                    stopProgressDialog();
                                }
                            });
                }
            }
        }
    }

    private class CommentAdapter extends BaseCommonAdapter {

        public CommentAdapter(List mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            VideoContentBean.ContentBean.PinglunBean pinglunBean = (VideoContentBean.ContentBean.PinglunBean) item;
            String comment = pinglunBean.getComment();
            String comment_nickname = pinglunBean.getNickname();
            String head_img = pinglunBean.getHead_img();
            //点赞数
            String comment_praise_count = pinglunBean.getComment_praise_count();
            //评论时间
            String comment_time = pinglunBean.getComment_time();
            //是否给该评论点赞，0：未点赞，1：已点赞
            final String if_praise = pinglunBean.getIf_praise();
            final int comment_id = pinglunBean.getId();
            viewHolder.setText(R.id.tv_pl_content, comment);
            viewHolder.setText(R.id.tv_pingjia_time, comment_time);
            viewHolder.setText(R.id.tv_pingjia_name, comment_nickname);
            viewHolder.setText(R.id.tv_pingjia_good_number, comment_praise_count);
            viewHolder.setPic(R.id.cv_pingjia, head_img);
            View view = viewHolder.getmConvertView();
            final ImageView iv_select_zan = (ImageView) view.findViewById(R.id.iv_select_zan);
            if ("0".equals(if_praise)) {
                //未点击
                iv_select_zan.setImageResource(R.mipmap.dz_bt);
            } else if ("1".equals(if_praise)) {
                iv_select_zan.setImageResource(R.mipmap.dz_bt_select);
            }
            iv_select_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(if_praise)) {
                        pingLunDianZan(iv_select_zan, comment_id + "");
                    }
                }
            });
        }
    }



    private void pingLunDianZan(final ImageView iv_select_zan, String article_comment_id) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.FaXian_DianZan_PingLun)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("id", article_comment_id)
                    .addParams("like_type", "3")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                int code = successDisCoverBackBean.getCode();
                                if (200 == code) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            iv_select_zan.setImageResource(R.mipmap.dz_bt_select);
                                            getDataFromNet(true);
                                        }
                                    });
                                }
                            }
                            return string;
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            stopProgressDialog();
                        }

                        @Override
                        public void onResponse(Object response) {
                            stopProgressDialog();
                        }
                    });
        }
    }
}
