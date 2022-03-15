package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
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
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.discover.view.CommentDialog;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.JiaoLianPJListBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
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

/**
 * Created by ZL on 2018/10/23.
 */
public class JiaoLianPJActivity extends BaseActivity implements View.OnClickListener {
    private CircleImageView iv_head;
    private TextView tv_name, tv_fenshu, tv_date, tv_pingjia_content, tv_pinglun_number, tv_main_praise;
    private RatingBar rb_one_now, rb_teach_quality, rb_service_attitude;
    private LinearLayout llback;
    private TextView tvTitle;
    private String driver_id;
    private String order_number;
    private TextView tv_gv_content;
    private int pager = 1;
    private RefreshLayout refreshLayout;
    private AdListView lv_pinglun;
    private CommentAdapter commentAdapter;
    private List<JiaoLianPJListBean.ContentBean.ReplyListBean> list = new ArrayList<>();
    private List<JiaoLianPJListBean.ContentBean.ReplyListBean> pinglun;
    private JiaoLianPJListBean.ContentBean content;
    private ScrollView scroll_view;
    private CheckBox rb_niming;
    private String style = "1";
    private LinearLayout ll_dianzan_content;
    private ImageView iv_main_praise;


    @Override
    protected int initContentView() {
        return R.layout.jiaolian_pingjia_activity;
    }

    @Override
    protected void initView() {
        llback = (LinearLayout) view.findViewById(R.id.driver_school_trainer_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.driver_school_trainer_include).findViewById(R.id.tv_login_back);
        ll_dianzan_content = (LinearLayout) view.findViewById(R.id.shequ_content_dianzan).findViewById(R.id.ll_dianzan_content);
        tv_main_praise = (TextView) view.findViewById(R.id.shequ_content_dianzan).findViewById(R.id.tv_main_praise);
        iv_main_praise = (ImageView) view.findViewById(R.id.shequ_content_dianzan).findViewById(R.id.iv_main_praise);
        iv_head = (CircleImageView) view.findViewById(R.id.iv_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rb_one_now = (RatingBar) view.findViewById(R.id.rb_one_now);
        rb_teach_quality = (RatingBar) view.findViewById(R.id.rb_teach_quality);
        rb_service_attitude = (RatingBar) view.findViewById(R.id.rb_service_attitude);
        tv_fenshu = (TextView) view.findViewById(R.id.tv_fenshu);
        tv_pingjia_content = (TextView) view.findViewById(R.id.tv_pingjia_content);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_pinglun_number = (TextView) view.findViewById(R.id.tv_pinglun_number);
        tv_gv_content = (TextView) view.findViewById(R.id.tv_gv_content);
        lv_pinglun = (AdListView) view.findViewById(R.id.lv_pinglun);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        commentAdapter = new CommentAdapter(list, this, R.layout.shequ_comment_item);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        rb_niming = (CheckBox) view.findViewById(R.id.rb_niming);
        scroll_view.smoothScrollTo(0, 0);
        scroll_view.smoothScrollBy(0, 0);
        lv_pinglun.setFocusable(false);
    }

    @Override
    protected void initListener() {
        llback.setOnClickListener(this);
        tv_gv_content.setOnClickListener(this);
        ll_dianzan_content.setOnClickListener(this);
        rb_niming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    style = "1";
                } else {
                    style = "2";
                }
            }
        });
    }


    @Override
    protected void initData() {
        tvTitle.setText("详情");
        Intent intent = getIntent();

        String head_img = intent.getStringExtra("head_img");
        String user_name = intent.getStringExtra("user_name");
        String all_evaluate = intent.getStringExtra("all_evaluate");
        String service_attitude = intent.getStringExtra("service_attitude");
        String technological_level = intent.getStringExtra("technological_level");
        String content = intent.getStringExtra("content");
        driver_id = intent.getStringExtra("driver_id");
        String system_time = intent.getStringExtra("system_time");
        order_number = intent.getStringExtra("order_number");
        if (!TextUtils.isEmpty(order_number)) {
            getDataFromNet();
        }
        if (!TextUtils.isEmpty(head_img)) {
            Picasso.with(App.context).load(head_img).placeholder(R.mipmap.hot_train_head).error(R.mipmap.hot_train_head).into(iv_head);
        }
        if (!TextUtils.isEmpty(user_name)) {
            tv_name.setText(user_name);
        }
        if (!TextUtils.isEmpty(all_evaluate)) {
            float star = Float.parseFloat(all_evaluate);
            rb_one_now.setRating(star);
            tv_fenshu.setText(all_evaluate);
        }
        if (!TextUtils.isEmpty(technological_level)) {
            float star = Float.parseFloat(technological_level);
            rb_teach_quality.setRating(star);
        }
        if (!TextUtils.isEmpty(service_attitude)) {
            float star = Float.parseFloat(service_attitude);
            rb_service_attitude.setRating(star);
        }
        if (!TextUtils.isEmpty(content)) {
            tv_pingjia_content.setText(content);
        }
        if (!TextUtils.isEmpty(system_time)) {
            tv_date.setText(system_time);
        }
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

    public void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(JiaoLianPJActivity.this)) {
          String  user_id = PrefUtils.getString(App.context, "user_id", "");
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.JiaoLian_Remark_content)
                    .addParams("device_id", LoginUtils.getId(JiaoLianPJActivity.this))
                    .addParams("pager", pager + "")
                    .addParams("order_number", order_number)
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
        if (XueYiCheUtils.IsHaveInternet(JiaoLianPJActivity.this)) {
          String  user_id = PrefUtils.getString(App.context, "user_id", "");
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.JiaoLian_Remark_content)
                    .addParams("device_id", LoginUtils.getId(JiaoLianPJActivity.this))
                    .addParams("pager", pager + "")
                    .addParams("order_number", order_number)
                    .addParams("user_id", user_id)
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
        final JiaoLianPJListBean wenDaContent = JsonUtil.parseJsonToBean(string, JiaoLianPJListBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (wenDaContent != null) {
                    int code = wenDaContent.getCode();
                    if (200 == code) {
                        content = wenDaContent.getContent();
                        if (content != null) {
                            String comment_count = content.getReply_count();
                            String if_praise = content.getIf_praise();
                            String praise_count = content.getPraise_count();
                            if (!TextUtils.isEmpty("" + comment_count)) {
                                tv_pinglun_number.setText("回答（" + comment_count + "）");
                            }
                            if (!TextUtils.isEmpty(praise_count)) {
                                tv_main_praise.setText(praise_count);
                            }
                            if (!TextUtils.isEmpty(if_praise)) {
                                if ("0".equals(if_praise)) {
                                    //未点击
                                    iv_main_praise.setImageResource(R.mipmap.viewpager_dianzan);
                                } else if ("1".equals(if_praise)) {
                                    iv_main_praise.setImageResource(R.mipmap.viewpager_dianzan_select);
                                }
                            }

                            pinglun = content.getReply_list();
                            if (pinglun != null) {
                                if (!isMore) {
                                    if (list.size() != 0) {
                                        list.clear();
                                    }
                                    list.addAll(pinglun);
                                    lv_pinglun.setAdapter(commentAdapter);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.ll_dianzan_content:
                if (DialogUtils.IsLogin()) {
                    contentDianZan();
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.tv_gv_content:
                if (DialogUtils.IsLogin()) {
                    showCommentDialog();
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            default:

                break;
        }
    }

    private void contentDianZan() {
        if (content != null) {
            String main_praise = content.getIf_praise();
            if ("0".equals(main_praise)) {
                String user_id = PrefUtils.getString(App.context, "user_id", "");
                if (XueYiCheUtils.IsHaveInternet(JiaoLianPJActivity.this)) {
                    OkHttpUtils.post().url(AppUrl.JiaoLian_PingJia_Trainer_DianZan)
                            .addParams("user_id", user_id)
                            .addParams("order_number", order_number)
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
                                                    iv_main_praise.setImageResource(R.mipmap.viewpager_dianzan_select);
                                                    getDataFromNet();
                                                }
                                            });
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
                }
            }
        }

    }

    private void showCommentDialog() {
        new CommentDialog("我也说两句", new CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                if (!TextUtils.isEmpty(inputText)) {
                    if (!TextUtils.isEmpty(inputText)) {
                        String user_id = PrefUtils.getString(App.context, "user_id", "");
                        OkHttpUtils.post().url(AppUrl.JiaoLian_PingJia_Trainer)
                                .addParams("reply_user_id", user_id)
                                .addParams("order_number", order_number)
                                .addParams("reply", inputText)
                                .addParams("name_type", style)
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
                        Toast.makeText(JiaoLianPJActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).show(getSupportFragmentManager(), "comment");
    }

    private class CommentAdapter extends BaseCommonAdapter {

        public CommentAdapter(List<JiaoLianPJListBean.ContentBean.ReplyListBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            JiaoLianPJListBean.ContentBean.ReplyListBean pinglunBean = (JiaoLianPJListBean.ContentBean.ReplyListBean) item;
            String comment = pinglunBean.getReply();
            String comment_nickname = pinglunBean.getNickname();
            String head_img = pinglunBean.getHead_img();
            final String if_praise = pinglunBean.getIf_praise();
            String comment_time = pinglunBean.getReply_time();
            String reply_praise_count = pinglunBean.getReply_praise_count();

            final String eva_detail_id = pinglunBean.getEva_detail_id();
            viewHolder.setText(R.id.tv_pingjia_content, comment);
            viewHolder.setText(R.id.tv_pingjia_time, comment_time);
            viewHolder.setText(R.id.tv_pingjia_name, comment_nickname);
            viewHolder.setText(R.id.tv_pingjia_good_number, reply_praise_count);
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
                        if (DialogUtils.IsLogin()) {
                            pingLunDianZan(iv_select_zan, eva_detail_id + "");
                        } else {
                            openActivity(LoginFirstStepActivity.class);
                        }
                    }
                }
            });
        }
    }

    private void pingLunDianZan(final ImageView iv_select_zan, String eva_detail_id) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.JiaoLian_PingJia_Trainer_List_DianZan)
                    .addParams("user_id", user_id)
                    .addParams("eva_detail_id", eva_detail_id)
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
