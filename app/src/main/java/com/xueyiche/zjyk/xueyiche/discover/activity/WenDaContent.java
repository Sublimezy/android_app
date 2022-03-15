package com.xueyiche.zjyk.xueyiche.discover.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.discover.bean.WenDaContentBean;
import com.xueyiche.zjyk.xueyiche.discover.view.CommentDialog;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
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

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2018/3/9.
 */
public class WenDaContent extends BaseActivity implements View.OnClickListener {
    private TextView tv_login_back, tv_qusetion, tv_content;
    private String wenda_id;
    private RefreshLayout refreshLayout;
    private AdListView lv_huida;
    private CommentAdapter commentAdapter;
    private int pager = 1;
    private List<WenDaContentBean.ContentBean.PinglunBean> list = new ArrayList<>();
    private List<WenDaContentBean.ContentBean.PinglunBean> pinglun;
    private TextView tv_gv_content;
    private String user_id;
    private WenDaContentBean.ContentBean content;
    private TextView tv_dz_number, tv_sc_number, tv_fx_number;
    private ImageView iv_caidan;
    private LinearLayout ll_pic;
    private RoundImageView iv_one, iv_two, iv_three;
    private TextView tv_pinglun_number, tv_name, tv_time;
    private CircleImageView cv_wd_content;
    private LinearLayout ll_exam_back;

    @Override
    protected int initContentView() {
        return R.layout.wenda_content;
    }

    @Override
    protected void initView() {
        tv_gv_content = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_gv_content);
        tv_dz_number = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_dz_number);
        tv_sc_number = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_sc_number);
        tv_fx_number = (TextView) view.findViewById(R.id.content_bottom_include).findViewById(R.id.tv_fx_number);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        iv_caidan = (ImageView) view.findViewById(R.id.title_include).findViewById(R.id.iv_caidan);
        tv_login_back = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        tv_login_back.setVisibility(View.VISIBLE);
        iv_caidan.setVisibility(View.GONE);
        tv_login_back.setText("问答详情");
        tv_qusetion = (TextView) view.findViewById(R.id.tv_qusetion);
        tv_pinglun_number = (TextView) view.findViewById(R.id.tv_pinglun_number);
        ll_pic = (LinearLayout) view.findViewById(R.id.ll_pic);
        cv_wd_content = (CircleImageView) view.findViewById(R.id.cv_wd_content);
        iv_one = (RoundImageView) view.findViewById(R.id.iv_one);
        iv_two = (RoundImageView) view.findViewById(R.id.iv_two);
        iv_three = (RoundImageView) view.findViewById(R.id.iv_three);
        lv_huida = (AdListView) view.findViewById(R.id.lv_huida);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        wenda_id = getIntent().getStringExtra("wenda_id");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        commentAdapter = new CommentAdapter(list, this, R.layout.wenda_huida_item);

    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_sc_number.setOnClickListener(this);
        tv_dz_number.setOnClickListener(this);
        iv_caidan.setOnClickListener(this);
        tv_fx_number.setOnClickListener(this);
        tv_gv_content.setOnClickListener(this);
        refreshLayout.finishRefresh();
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            if (pinglun != null && pinglun.size() == 0) {
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

    @Override
    protected void initData() {
        getDataFromNet();
    }

    private void showCommentDialog() {
        new CommentDialog("说点什么...", new CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                if (!TextUtils.isEmpty(inputText)) {
                    if (!TextUtils.isEmpty(inputText)) {
                        OkHttpUtils.post().url(AppUrl.Discover_PingLun)
                                .addParams("user_id", user_id)
                                .addParams("device_id", LoginUtils.getId(WenDaContent.this))
                                .addParams("comment_type", "2")
                                .addParams("comment", inputText)
                                .addParams("id", wenda_id)
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
                                                        EventBus.getDefault().post(new MyEvent("问答"));
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
                        Toast.makeText(WenDaContent.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).show(getSupportFragmentManager(), "comment");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                try {
                    if (AppUtils.isSoftShowing(WenDaContent.this)) {
                        AppUtils.hideSoftKeyBoard(WenDaContent.this);
                    }
                } finally {

                    finish();
                }
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
                    WenDaContent.this, "学易车-易动华夏",
                    "http://xueyiche.cn/article/index.php?device_id=" + LoginUtils.getId(this) + "&id=" + wenda_id,
                    StringConstants.SHARED_TEXT + "http://www.xueyiche.cn/",
                    "http://ojljpzjpm.bkt.clouddn.com/shar.jpg",
                    "http://xueyiche.cn/article/index.php?device_id=" + LoginUtils.getId(this) + "&id=" + wenda_id, "1", wenda_id);
        } else {
            openActivity(LoginFirstStepActivity.class);
        }

    }

    private void contentShouCang() {
        if (content != null) {
            //是否收藏，0：未收藏，1：已收藏
            String main_collect = content.getMain_collect();
            if ("0".equals(main_collect)) {
                if (XueYiCheUtils.IsHaveInternet(WenDaContent.this)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.Czh_Collect)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("like_type", "2")
                            .addParams("kind_type", "1")
                            .addParams("refer_id", wenda_id)
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
            } else {
                if (XueYiCheUtils.IsHaveInternet(WenDaContent.this)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.Czh_Collect)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("like_type", "2")
                            .addParams("kind_type", "2")
                            .addParams("refer_id", wenda_id)
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

    private void contentDianZan() {
        if (content != null) {
            String main_praise = content.getMain_praise();
            if ("0".equals(main_praise)) {
                if (XueYiCheUtils.IsHaveInternet(WenDaContent.this)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.FaXian_DianZan_Content)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("like_type", "2")
                            .addParams("id", wenda_id)
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

    public void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(WenDaContent.this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Discover_WenDa_Content)
                    .addParams("device_id", LoginUtils.getId(WenDaContent.this))
                    .addParams("pager", pager + "")
                    .addParams("id", wenda_id)
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
        if (XueYiCheUtils.IsHaveInternet(WenDaContent.this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Discover_WenDa_Content)
                    .addParams("device_id", LoginUtils.getId(WenDaContent.this))
                    .addParams("pager", pager + "")
                    .addParams("id", wenda_id)
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
        final WenDaContentBean wenDaContent = JsonUtil.parseJsonToBean(string, WenDaContentBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (wenDaContent != null) {
                    int code = wenDaContent.getCode();
                    if (200 == code) {
                        content = wenDaContent.getContent();
                        if (content != null) {
                            //头像
                            String head_img = content.getHead_img();
                            String image1 = content.getImage1();
                            String image2 = content.getImage2();
                            String image3 = content.getImage3();
                            Picasso.with(App.context).load(head_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(cv_wd_content);
                            if (TextUtils.isEmpty(image1) && TextUtils.isEmpty(image2) && TextUtils.isEmpty(image3)) {
                                ll_pic.setVisibility(View.GONE);
                            } else {
                                if (TextUtils.isEmpty(image1)) {
                                    iv_one.setVisibility(View.INVISIBLE);
                                } else {
                                    Picasso.with(App.context).load(image1).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_one);
                                    iv_one.setVisibility(View.VISIBLE);
                                }
                                if (TextUtils.isEmpty(image2)) {
                                    iv_two.setVisibility(View.INVISIBLE);
                                } else {
                                    Picasso.with(App.context).load(image2).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_two);
                                    iv_two.setVisibility(View.VISIBLE);
                                }
                                if (TextUtils.isEmpty(image3)) {
                                    iv_three.setVisibility(View.INVISIBLE);
                                } else {
                                    Picasso.with(App.context).load(image3).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_three);
                                    iv_three.setVisibility(View.VISIBLE);
                                }

                            }

                            //是否点赞该资讯，0：未点赞，1：已点赞
                            String main_praise = content.getMain_praise();
                            //是否收藏该资讯，0：未收藏，1：已收藏
                            String main_collect = content.getMain_praise();
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
                            String praise_count = content.getPraise_count();
                            String collect_count = content.getCollect_count();
                            String share_count = content.getShare_count();
                            tv_dz_number.setText(praise_count);
                            tv_sc_number.setText(collect_count);
                            tv_fx_number.setText(share_count);
                            String comment_count = content.getComment_count();
                            tv_pinglun_number.setText("共" + comment_count + "条评论");
                            String title = content.getTitle();
                            tv_qusetion.setText(title);
                            String represent = content.getRepresent();
                            tv_content.setText(represent);
                            String quest_nickname = content.getQuest_nickname();
                            tv_name.setText(quest_nickname);
                            String release_time = content.getRelease_time();
                            tv_time.setText(release_time);
                            pinglun = content.getPinglun();
                            if (pinglun != null) {
                                if (!isMore) {
                                    if (list.size() != 0) {
                                        list.clear();
                                    }
                                    list.addAll(pinglun);
                                    lv_huida.setAdapter(commentAdapter);
                                    XueYiCheUtils.setListViewHeightBasedOnChildren(lv_huida);
                                } else {
                                    list.addAll(pinglun);
                                    commentAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                }
            }
        });
    }

    private class CommentAdapter extends BaseCommonAdapter {

        public CommentAdapter(List mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            WenDaContentBean.ContentBean.PinglunBean pinglunBean = (WenDaContentBean.ContentBean.PinglunBean) item;
            String comment = pinglunBean.getComment();
            String comment_nickname = pinglunBean.getComment_nickname();
            String head_img = pinglunBean.getHead_img();
            String comment_time = pinglunBean.getComment_time();
            final int comment_id = pinglunBean.getComment_id();
            final String if_praise = pinglunBean.getIf_praise();
            int comment_praise_count = pinglunBean.getComment_praise_count();
            viewHolder.setText(R.id.tv_huida_content, comment);
            viewHolder.setText(R.id.tv_huida_time, comment_time);
            viewHolder.setText(R.id.tv_huida_name, comment_nickname);
            viewHolder.setText(R.id.tv_dz_number_pl, comment_praise_count + "");
            viewHolder.setPic(R.id.cv_head, head_img);
            View view = viewHolder.getmConvertView();
            final TextView tv_dz_number_pl = (TextView) view.findViewById(R.id.tv_dz_number_pl);
            if ("0".equals(if_praise)) {
                Drawable drawable = getResources().getDrawable(R.mipmap.wd_dz);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_dz_number_pl.setCompoundDrawables(drawable, null, null, null);
            } else if ("1".equals(if_praise)) {
                Drawable drawable = getResources().getDrawable(R.mipmap.wd_dz_select);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_dz_number_pl.setCompoundDrawables(drawable, null, null, null);
            }
            tv_dz_number_pl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("0".equals(if_praise)) {
                        pingLunDianZan(tv_dz_number_pl, comment_id + "");
                    }
                }
            });
        }
    }


    private void pingLunDianZan(final TextView tv_dz_number_pl, String article_comment_id) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.FaXian_DianZan_PingLun)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("comment_id", article_comment_id)
                    .addParams("like_type", "2")
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