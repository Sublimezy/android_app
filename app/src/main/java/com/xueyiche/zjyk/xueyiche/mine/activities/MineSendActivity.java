package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.gyf.immersionbar.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.community.activity.ShiPinPlayActivity;
import com.xueyiche.zjyk.xueyiche.community.activity.TuWenXiangQingActivity;
import com.xueyiche.zjyk.xueyiche.community.bean.CallSuccessBean;
import com.xueyiche.zjyk.xueyiche.community.bean.CommunityListBean;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.main.activities.main.BaseBean;
import com.xueyiche.zjyk.xueyiche.mine.decoration.GridItemDecoration;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;
import com.xueyiche.zjyk.xueyiche.utils.CommentDialog;
import com.xueyiche.zjyk.xueyiche.utils.MyDialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineSendActivity extends BaseActivity {

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
    @BindView(R.id.rl_empty_view)
    RelativeLayout rl_empty_view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tvRemark)
    TextView tvRemark;
    private CommunityAdapter communityAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_mine_send;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, view);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("我的发布");
    }

    @Override
    protected void initListener() {

    }

    List<CommunityListBean.DataBean.DataBeanX> mData = new ArrayList<>();

    @Override
    protected void initData() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pager++;
                getDataFromNet();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.setEnableLoadMore(true);
                pager = 1;
                getDataFromNet();
            }
        });


        GridItemDecoration gridItemDecoration = new GridItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        gridItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(gridItemDecoration);

        communityAdapter = new CommunityAdapter(mData);
//        communityAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(communityAdapter);
        getDataFromNet();
    }


    @OnClick({R.id.ll_common_back, R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_title:
                break;
        }
    }


    int pager = 1;

    private void getDataFromNet() {
        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pager + "");
        MyHttpUtils.postHttpMessage(AppUrl.article_user, params, CommunityListBean.class, new RequestCallBack<CommunityListBean>() {
            @Override
            public void requestSuccess(CommunityListBean json) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                List<CommunityListBean.DataBean.DataBeanX> data = json.getData().getData();
                if (data == null || data.size() == 0) {


                } else {
                    if (pager == 1) {
                        mData = data;
                        communityAdapter.setNewData(data);
                    } else {
                        mData.addAll(data);
                        communityAdapter.addData(data);
                    }

                    if (data.size() < 10) {
                        mRefreshLayout.setEnableLoadMore(false);
                    }

                }
                if (mData.size() == 0) {
                    rl_empty_view.setVisibility(View.VISIBLE);
                } else {
                    rl_empty_view.setVisibility(View.GONE);

                }

            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    class CommunityAdapter extends BaseMultiItemQuickAdapter<CommunityListBean.DataBean.DataBeanX, BaseViewHolder> {


        public CommunityAdapter(List<CommunityListBean.DataBean.DataBeanX> data) {
            super(data);
            addItemType(CommunityListBean.DataBean.DataBeanX.TEXT, R.layout.item_only_text);
            addItemType(CommunityListBean.DataBean.DataBeanX.TEXT_ONE_PIC, R.layout.item_text_one_pic);
            addItemType(CommunityListBean.DataBean.DataBeanX.TEXT_TWO_PIC, R.layout.item_text_two_pic);
            addItemType(CommunityListBean.DataBean.DataBeanX.TEXT_PICS, R.layout.item_text_pics);
            addItemType(CommunityListBean.DataBean.DataBeanX.TEXT_VIDEO, R.layout.item_text_video);
        }

        @Override
        public int getItemViewType(int position) {
            CommunityListBean.DataBean.DataBeanX dataBeanX = mData.get(position);

            if (TextUtils.isEmpty(dataBeanX.getVideo_file())) {
                return CommunityListBean.DataBean.DataBeanX.TEXT_PICS;
            } else {
                return CommunityListBean.DataBean.DataBeanX.TEXT_VIDEO;
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, CommunityListBean.DataBean.DataBeanX item) {

            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(item.getVideo_file())) {
                        //图文
                        Intent intent = new Intent(getContext(), TuWenXiangQingActivity.class);
                        intent.putExtra("id", item.getId());
                        startActivity(intent);
                    } else {
                        //视频
                        Intent intent = new Intent(getContext(), ShiPinPlayActivity.class);
                        intent.putExtra("id", item.getId() + "");
                        startActivity(intent);
                    }
                }
            });
            switch (helper.getItemViewType()) {
                case CommunityListBean.DataBean.DataBeanX.TEXT:

                    setOnlyText(helper, item);
                    break;
                case CommunityListBean.DataBean.DataBeanX.TEXT_ONE_PIC:
                    setOnlyText(helper, item);
                    break;
                case CommunityListBean.DataBean.DataBeanX.TEXT_TWO_PIC:
                    setOnlyText(helper, item);
                    break;
                case CommunityListBean.DataBean.DataBeanX.TEXT_PICS:
                    setTextPics(helper, item);
                    break;
                case CommunityListBean.DataBean.DataBeanX.TEXT_VIDEO:
                    setVideo(helper, item);
                    break;

                default:

                    break;


            }
        }

        private void setVideo(BaseViewHolder helper, CommunityListBean.DataBean.DataBeanX item) {
            ExpandableTextView expandableTextView = helper.getView(R.id.tv_quan_title);
            CustomShapeImageView image = helper.getView(R.id.imageView_one);
            expandableTextView.bind(item);
            if (TextUtils.isEmpty(item.getContent())) {
                expandableTextView.setVisibility(View.GONE);
            } else {
                expandableTextView.setVisibility(View.VISIBLE);

            }
            expandableTextView.setContent(item.getContent());
            Glide.with(App.context).load(item.getVideo_file())
                    .into(image);
            helper.setText(R.id.tv_fabu_time, item.getCreatetime());
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ShiPinPlayActivity.class);
                    intent.putExtra("id", item.getId() + "");
                    startActivity(intent);
                }
            });
            try {
                RoundedImageView riv_head = helper.getView(R.id.riv_head);
//                if (!TextUtils.isEmpty(item.getAvatar())) {
                Glide.with(App.context).load(item.getAvatar()).into(riv_head);
//                } else {
//                    Glide.with(App.context).load(R.mipmap.logo).into(riv_head);
//
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(item.getNickname())) {
                helper.setText(R.id.tvNickName, item.getNickname());
            }
            LinearLayout ll_pinglun = helper.getView(R.id.ll_pinglun);
            ll_pinglun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCommentDialog(item.getId());
                }
            });
            LinearLayout ll_delete = helper.getView(R.id.ll_delete);

            ll_delete.setVisibility(View.VISIBLE);
            ll_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //删除
                    //删除


                    new MyDialogUtils.Builder(MineSendActivity.this, true, true, "确定要删除此条内容吗?"
                            , "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Map<String, String> params = new HashMap<>();
                            params.put("id", item.getId() + "");
                            MyHttpUtils.postHttpMessage(AppUrl.article_user_del, params, BaseBean.class, new RequestCallBack<BaseBean>() {
                                @Override
                                public void requestSuccess(BaseBean json) {
                                    if (json.getCode() == 1) {
                                        communityAdapter.remove(helper.getLayoutPosition());
                                    } else {

                                    }
                                    showToastShort(json.getMsg());

                                }

                                @Override
                                public void requestError(String errorMsg, int errorType) {

                                }
                            });
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    }
                    ).create().show();
                }
            });
            RoundedImageView riv_head = helper.getView(R.id.riv_head);
//            if (!TextUtils.isEmpty(item.getAvatar())) {
            Glide.with(App.context).load(item.getAvatar()).into(riv_head);
        }

        public void setOnlyText(BaseViewHolder helper, CommunityListBean.DataBean.DataBeanX item) {
            ExpandableTextView expandableTextView = helper.getView(R.id.tv_quan_title);
            expandableTextView.bind(item);
            expandableTextView.setContent("这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");

        }

        /**
         * '
         * 多图
         *
         * @param helper
         * @param item
         */
        public void setTextPics(BaseViewHolder helper, CommunityListBean.DataBean.DataBeanX item) {
            ExpandableTextView expandableTextView = helper.getView(R.id.tv_quan_title);

            RecyclerView recycler = helper.getView(R.id.recycler);
            expandableTextView.bind(item);
            expandableTextView.setContent(item.getContent());
            if (TextUtils.isEmpty(item.getContent())) {
                expandableTextView.setVisibility(View.GONE);
            } else {
                expandableTextView.setVisibility(View.VISIBLE);

            }
            helper.setText(R.id.tv_fabu_time, item.getCreatetime());
            recycler.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            QuanPicAdapter quanPicAdapter = new QuanPicAdapter(item.getImages(), "" + item.getId());
            recycler.setAdapter(quanPicAdapter);
            RoundedImageView riv_head = helper.getView(R.id.riv_head);
//            if (!TextUtils.isEmpty(item.getAvatar())) {
            Glide.with(App.context).load(item.getAvatar()).into(riv_head);
//            } else {
//                Glide.with(App.context).load(R.mipmap.logo).into(riv_head);
//            }
            if (!TextUtils.isEmpty(item.getNickname())) {
                helper.setText(R.id.tvNickName, item.getNickname());
            }
            LinearLayout ll_pinglun = helper.getView(R.id.ll_pinglun);
            ll_pinglun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCommentDialog(item.getId());
                }
            });
            LinearLayout ll_delete = helper.getView(R.id.ll_delete);
            ll_delete.setVisibility(View.VISIBLE);
            ll_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //删除


                    new MyDialogUtils.Builder(MineSendActivity.this, true, true, "确定要删除此条内容吗?"
                            , "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Map<String, String> params = new HashMap<>();
                            params.put("id", item.getId() + "");
                            MyHttpUtils.postHttpMessage(AppUrl.article_user_del, params, BaseBean.class, new RequestCallBack<BaseBean>() {
                                @Override
                                public void requestSuccess(BaseBean json) {
                                    if (json.getCode() == 1) {
                                        communityAdapter.remove(helper.getLayoutPosition());
                                    } else {

                                    }
                                    showToastShort(json.getMsg());

                                }

                                @Override
                                public void requestError(String errorMsg, int errorType) {

                                }
                            });
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    }
                    ).create().show();
                }
            });

        }


    }

    private void showCommentDialog(String id) {
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
                    Toast.makeText(getContext(), "请输入评论内容", Toast.LENGTH_SHORT).show();
                }

            }
        }).show(getSupportFragmentManager(), "comment");
    }

    private Context getContext() {
        return MineSendActivity.this;
    }

    private final Handler handler = new Handler();

    public class QuanPicAdapter extends RecyclerView.Adapter<QuanPicAdapter.ViewHolder> {
        private List<String> strs;
        private String content_id;

        public QuanPicAdapter(List<String> strs, String content_id) {
            this.strs = strs;
            this.content_id = content_id;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            CustomShapeImageView imageView;

            public ViewHolder(View v) {
                super(v);
                imageView = v.findViewById(R.id.imageView);
            }

        }

        @Override
        public int getItemCount() {
            return strs.size();
        }

        @Override
        public QuanPicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quan_piclist_item, parent, false);
            return new QuanPicAdapter.ViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(QuanPicAdapter.ViewHolder holder, final int position) {
            String str = strs.get(position);
            Glide.with(App.context).load(str).into(holder.imageView);
//            Glide.with(App.context).load("http://xychead.xueyiche.vip/fx_tuwen1653620811900.jpg").into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //图文
                    Intent intent = new Intent(getContext(), TuWenXiangQingActivity.class);
                    intent.putExtra("id", content_id);
                    startActivity(intent);
                }
            });
        }
    }
}