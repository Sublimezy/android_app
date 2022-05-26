package com.xueyiche.zjyk.xueyiche.community.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.community.bean.CallSuccessBean;
import com.xueyiche.zjyk.xueyiche.community.bean.TuWenDetailBean;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.AlertPopWindow;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaActivity;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.mine.view.LoadingLayout;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.utils.CommentDialog;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.SharedUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TuWenXiangQingActivity extends BaseActivity {


    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.ll_common_back)
    LinearLayout llCommonBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_btn)
    TextView tvRightBtn;
    @BindView(R.id.iv_caidan)
    ImageView ivCaidan;
    @BindView(R.id.title_view_heng)
    View titleViewHeng;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.cv_head)
    CircleImageView cvHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_release_time)
    TextView tvReleaseTime;
    @BindView(R.id.tv_browse)
    TextView tvBrowse;
    @BindView(R.id.tv_quan_title)
    TextView tvQuanTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.ll_pic)
    LinearLayout llPic;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.banner_container)
    FrameLayout bannerContainer;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_dianzan)
    ImageView tvDianzan;
    @BindView(R.id.tv_sc_number)
    ImageView tvScNumber;
    @BindView(R.id.mineHead)
    CircleImageView mineHead;
    @BindView(R.id.tv_gv_content)
    TextView tvGvContent;
    @BindView(R.id.lv_pl)
    AdListView lvPl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String id;
    private LoadingLayout wrap;
    private QuanPicAdapter quanPicAdapter;
    private CommentAdapter commentAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_tu_wen_xiang_qing;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvTitle.setText("详情");
        id = getIntent().getStringExtra("id");
        ImmersionBar.with(this).statusBarDarkFont(true).titleBar(rlTitle).init();
        wrap = LoadingLayout.wrap(refreshLayout);
        wrap.showLoading();
        wrap.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromNet();
            }
        });

        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        quanPicAdapter = new QuanPicAdapter(R.layout.quan_piclist_item_xiangqing);
        recyclerview.setAdapter(quanPicAdapter);


        getDataFromNet();

    }
    private List<TuWenDetailBean.DataBean.MessagelistBean> list = new ArrayList<>();
    private void getDataFromNet() {
        Map<String, String> params = new HashMap<>();
        params.put("article_id", id);
        MyHttpUtils.postHttpMessage(AppUrl.articleinfo, params, TuWenDetailBean.class, new RequestCallBack<TuWenDetailBean>() {
            @Override
            public void requestSuccess(TuWenDetailBean json) {
                if (json.getCode() == 1) {
                    wrap.showContent();
                    quanPicAdapter.setNewData(json.getData().getNewsinfo().getImages());
                    tvReleaseTime.setText(json.getData().getNewsinfo().getCreatetime());
                    tvQuanTitle.setText(json.getData().getNewsinfo().getContent());
                    Glide.with(TuWenXiangQingActivity.this).load(json.getData().getNewsinfo().getAvatar()).into(cvHead);
                    List<TuWenDetailBean.DataBean.MessagelistBean> messagelist = json.getData().getMessagelist();

                    tvName.setText(json.getData().getNewsinfo().getNickname());
                    commentAdapter = new CommentAdapter(messagelist, App.context, R.layout.shuoche_pl_item);
                    lvPl.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();
                } else {
                    wrap.showError();
                    showToastShort(json.getMsg());
                }


            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                wrap.showError();
            }
        });
    }


    @Override
    protected void initListener() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.ll_common_back, R.id.cv_head, R.id.tv_remark, R.id.tv_dianzan, R.id.tv_sc_number, R.id.mineHead, R.id.tv_gv_content})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.cv_head:
                break;
            case R.id.tv_remark:
                break;
            case R.id.tv_dianzan:
                break;
            case R.id.tv_sc_number:
                break;
            case R.id.mineHead:
                break;
            case R.id.tv_gv_content:
                if (XueYiCheUtils.IsLogin()) {
                    showCommentDialog();
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
        }
    }

    class QuanPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public QuanPicAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imageView = helper.getView(R.id.imageView);
            Glide.with(TuWenXiangQingActivity.this).load(item).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedUtils.ImageShowList(TuWenXiangQingActivity.this, quanPicAdapter.getData(), helper.getLayoutPosition(), "", "2");
                }
            });
        }
    }

    private class CommentAdapter extends BaseCommonAdapter {

        public CommentAdapter(List mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(com.xueyiche.zjyk.xueyiche.utils.BaseViewHolder viewHolder, Object item) {
            TuWenDetailBean.DataBean.MessagelistBean pinglunBean = (TuWenDetailBean.DataBean.MessagelistBean) item;
            String comment = pinglunBean.getText();
            String head_img = pinglunBean.getUser_avatar();
            String comment_nickname = pinglunBean.getUser_nickname();
            String comment_time = pinglunBean.getCreatetime();
            String user_id = pinglunBean.getUser_id();
            viewHolder.setText(R.id.tv_pingjia_name, comment_nickname);
            viewHolder.setText(R.id.tv_pl_content, comment);
            viewHolder.setText(R.id.tv_pingjia_time, comment_time);
            viewHolder.setPic(R.id.cv_pingjia, head_img);

        }


    }

    private void showCommentDialog() {
        new CommentDialog("说点什么...", new CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                if (!TextUtils.isEmpty(inputText)) {
                    Map<String, String> params = new HashMap<>();
                    params.put("article_id", id);
                    params.put("text", inputText);
                    MyHttpUtils.postHttpMessage(AppUrl.message, params, CallSuccessBean.class, new RequestCallBack<CallSuccessBean>() {
                        @Override
                        public void requestSuccess(CallSuccessBean json) {

                            if (json.getCode() == 1) {
                                Map<String, String> params = new HashMap<>();
                                params.put("article_id", id);
                                MyHttpUtils.postHttpMessage(AppUrl.articleinfo, params, TuWenDetailBean.class, new RequestCallBack<TuWenDetailBean>() {
                                    @Override
                                    public void requestSuccess(TuWenDetailBean json) {
                                        if (json.getCode() == 1) {
                                            wrap.showContent();
                                            List<TuWenDetailBean.DataBean.MessagelistBean> messagelist = json.getData().getMessagelist();
                                            commentAdapter = new CommentAdapter(messagelist, App.context, R.layout.shuoche_pl_item);
                                            lvPl.setAdapter(commentAdapter);
                                            commentAdapter.notifyDataSetChanged();
                                        } else {
                                            wrap.showError();
                                            showToastShort(json.getMsg());
                                        }


                                    }

                                    @Override
                                    public void requestError(String errorMsg, int errorType) {
                                        wrap.showError();
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
                    Toast.makeText(TuWenXiangQingActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                }

            }
        }).show(getSupportFragmentManager(), "comment");
    }
}