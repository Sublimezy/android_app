package com.xueyiche.zjyk.xueyiche.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
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
import com.xueyiche.zjyk.xueyiche.discover.view.CommentDialog;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.ObservableScrollView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2018/3/6.
 */
public class SheQuContent extends BaseActivity implements View.OnClickListener {
    private ScrollView os_shequ;
    private WebView wv_shequ_content;
    private AdListView lv_pinglun;
    private CommentAdapter commentAdapter;
    private TextView tv_gv_content, tv_pinglun_number;
    private String shequ_id;
    private int pager = 1;
    private List<SheQuContentBean.ContentBean.CorrelationCommentBean> list = new ArrayList<>();
    private List<SheQuContentBean.ContentBean.CorrelationCommentBean> correlationComment;
    private RefreshLayout refreshLayout;
    private String user_id;
    private SheQuContentBean.ContentBean.ArticleBean article;
    private ImageView iv_caidan;
    private ImageView iv_login_back;
    private TextView tv_dz_number,tv_sc_number,tv_fx_number;

    @Override
    protected int initContentView() {
        return R.layout.shequ_content;
    }

    @Override
    protected void initView() {
        iv_login_back = (ImageView) view.findViewById(R.id.title_include).findViewById(R.id.iv_login_back);
        iv_caidan = (ImageView) view.findViewById(R.id.title_include).findViewById(R.id.iv_caidan);
        iv_caidan.setVisibility(View.VISIBLE);
        wv_shequ_content = (WebView) view.findViewById(R.id.wv_shequ_content);
        tv_pinglun_number = (TextView) view.findViewById(R.id.tv_pinglun_number);
        lv_pinglun = (AdListView) view.findViewById(R.id.lv_pinglun);
        os_shequ = (ScrollView) view.findViewById(R.id.os_shequ);
        os_shequ.smoothScrollBy(0, 0);
        tv_gv_content = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_gv_content);
        tv_dz_number = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_dz_number);
        tv_sc_number = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_sc_number);
        tv_fx_number = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_fx_number);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        commentAdapter = new CommentAdapter(list, App.context, R.layout.shequ_comment_item);
        //列表id
        shequ_id = getIntent().getStringExtra("shequ_id");
        user_id = PrefUtils.getString(this, "user_id", "");
        getDataFromNet();

    }

    @Override
    protected void initListener() {
        iv_login_back.setOnClickListener(this);
        tv_gv_content.setOnClickListener(this);
        iv_caidan.setOnClickListener(this);
        iv_caidan.setVisibility(View.INVISIBLE);
        tv_fx_number.setOnClickListener(this);
        tv_sc_number.setOnClickListener(this);
        tv_dz_number.setOnClickListener(this);
        refreshLayout.finishRefresh();
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

    private void contentDianZan() {
        if (article != null) {
            String main_praise = article.getMain_praise();
            if ("0".equals(main_praise)) {
                if (XueYiCheUtils.IsHaveInternet(this)) {
                    showProgressDialog(false);
                    user_id = PrefUtils.getString(App.context, "user_id", "");
                    OkHttpUtils.post().url(AppUrl.FaXian_DianZan_Content)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("user_id", user_id)
                            .addParams("like_type", "1")
                            .addParams("id", shequ_id)
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
                                                    Drawable drawable = getResources().getDrawable(R.mipmap.dz_bt_select);
                                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                                    tv_dz_number.setCompoundDrawables(drawable, null, null, null);
                                                    getDataFromNet();
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

    public void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.Discover_SheQu_Content)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("pager", pager + "")
                    .addParams("id", shequ_id)
                    .addParams("user_id", user_id)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                processData(string, true);
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

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            user_id = PrefUtils.getString(App.context, "user_id", "");
            String id = LoginUtils.getId(this);
            OkHttpUtils.post().url(AppUrl.Discover_SheQu_Content)
                    .addParams("device_id", id)
                    .addParams("pager", pager + "")
                    .addParams("id", shequ_id)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                processData(string, false);
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

    private void processData(String string, final boolean isMore) {
        final SheQuContentBean sheQuContentBean = JsonUtil.parseJsonToBean(string, SheQuContentBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (sheQuContentBean != null) {
                    int code = sheQuContentBean.getCode();
                    if (200 == code) {
                        SheQuContentBean.ContentBean content = sheQuContentBean.getContent();
                        if (content != null) {
                            article = content.getArticle();
                            if (article != null) {
                                //是否点赞该资讯，0：未点赞，1：已点赞
                                String main_praise = article.getMain_praise();
                                //是否收藏该资讯，0：未收藏，1：已收藏
                                String main_collect = article.getMain_collect();
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
                                } else if ("1".equals(main_collect)) {
                                    Drawable drawable = getResources().getDrawable(R.mipmap.sc_bt_select);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    tv_sc_number.setCompoundDrawables(drawable, null, null, null);
                                }
                                String article_url = article.getArticle_url();
                                wv_shequ_content.loadUrl(article_url);
                                String praise_count = article.getPraise_count();
                                String collect_count = article.getCollect_count();
                                String share_count = article.getShare_count();
                                tv_dz_number.setText(praise_count);
                                tv_sc_number.setText(collect_count);
                                tv_fx_number.setText(share_count);
                                String comment_count = article.getComment_count();
                                tv_pinglun_number.setText("共" + comment_count + "条评论");
                            }
                            correlationComment = content.getCorrelationComment();
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

                }
            }
        });
    }

    @Override
    protected void initData() {

        WebSettings webSettings = wv_shequ_content.getSettings();
        webSettings.setJavaScriptEnabled(true);


    }

    private void showCommentDialog() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        new CommentDialog("说点什么...", new CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                if (!TextUtils.isEmpty(inputText)) {
                    String user_id = PrefUtils.getString(App.context, "user_id", "");
                    OkHttpUtils.post().url(AppUrl.Discover_PingLun)
                            .addParams("user_id", user_id)
                            .addParams("device_id", LoginUtils.getId(SheQuContent.this))
                            .addParams("comment_type", "1")
                            .addParams("comment", inputText)
                            .addParams("id", shequ_id)
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
                                                    getDataFromNet();
                                                    EventBus.getDefault().post(new MyEvent("社区"));

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
                    Toast.makeText(SheQuContent.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                }

            }
        }).show(getSupportFragmentManager(), "comment");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.tv_gv_content:
                if (DialogUtils.IsLogin()) {
                    showCommentDialog();
                } else {
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
            case R.id.iv_caidan:
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
                    SheQuContent.this, "学易车-易动华夏",
                    "http://xueyiche.cn/article/index.php?device_id=" + LoginUtils.getId(this) + "&id=" + shequ_id,
                    StringConstants.SHARED_TEXT + "http://www.xueyiche.cn/",
                    "http://ojljpzjpm.bkt.clouddn.com/shar.jpg",
                    "http://xueyiche.cn/article/index.php?device_id=" + LoginUtils.getId(this) + "&id=" + shequ_id,"1",shequ_id);
        } else {
            openActivity(LoginFirstStepActivity.class);
        }

    }

    private void contentShouCang() {
        if (article != null) {
            //是否收藏，0：未收藏，1：已收藏
            String main_collect = article.getMain_collect();
            if ("0".equals(main_collect)) {
                if (XueYiCheUtils.IsHaveInternet(SheQuContent.this)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.Czh_Collect)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("like_type", "1")
                            .addParams("kind_type", "1")
                            .addParams("refer_id", shequ_id)
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
                                                    getDataFromNet();
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
                if (XueYiCheUtils.IsHaveInternet(SheQuContent.this)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.Czh_Collect)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("like_type", "1")
                            .addParams("kind_type", "2")
                            .addParams("refer_id", shequ_id)
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
                                                    getDataFromNet();
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
            SheQuContentBean.ContentBean.CorrelationCommentBean correlationCommentBean = (SheQuContentBean.ContentBean.CorrelationCommentBean) item;
            String comment = correlationCommentBean.getComment();
            String comment_nickname = correlationCommentBean.getNickname();
            String head_img = correlationCommentBean.getHead_img();
            String comment_time = correlationCommentBean.getComment_time();
            final String if_praise = correlationCommentBean.getIf_praise();
            final String article_comment_id = correlationCommentBean.getArticle_comment_id();
            String comment_praise_count = correlationCommentBean.getComment_praise_count();
            viewHolder.setText(R.id.tv_pl_content, comment);
            viewHolder.setText(R.id.tv_pingjia_time, comment_time);
            viewHolder.setText(R.id.tv_pingjia_name, comment_nickname);
            viewHolder.setText(R.id.tv_pingjia_good_number, comment_praise_count);
            viewHolder.setPic(R.id.cv_pingjia, head_img);
            View view = viewHolder.getmConvertView();
            final ImageView iv_select_zan = (ImageView) view.findViewById(R.id.iv_select_zan);
            if ("0".equals(if_praise)) {
                //未点击
                iv_select_zan.setImageResource(R.mipmap.viewpager_dianzan);
            } else if ("1".equals(if_praise)) {
                iv_select_zan.setImageResource(R.mipmap.viewpager_dianzan_select);
            }
            iv_select_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(if_praise)) {
                        pingLunDianZan(iv_select_zan, article_comment_id);
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
                    .addParams("article_comment_id", article_comment_id)
                    .addParams("like_type", "1")
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
                                            iv_select_zan.setImageResource(R.mipmap.viewpager_dianzan_select);
                                            getDataFromNet();
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
