package com.xueyiche.zjyk.xueyiche.community;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.community.activity.ShiPinFaBuActivity;
import com.xueyiche.zjyk.xueyiche.community.activity.ShiPinPlayActivity;
import com.xueyiche.zjyk.xueyiche.community.activity.TuWenFabuActivity;
import com.xueyiche.zjyk.xueyiche.community.activity.TuWenXiangQingActivity;
import com.xueyiche.zjyk.xueyiche.community.bean.CallSuccessBean;
import com.xueyiche.zjyk.xueyiche.community.bean.CommunityListBean;
import com.xueyiche.zjyk.xueyiche.community.bean.TuWenDetailBean;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.activity.DaShangActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.decoration.GridItemDecoration;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.CommentDialog;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 2022/4/6/10:07 上午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage
 * #            xueyiche5.0
 */
public class CommunityFragment extends BaseFragment {
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.iv_send)
    ImageView iv_send;
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private CommunityAdapter communityAdapter;

    public static CommunityFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        CommunityFragment fragment = new CommunityFragment();
        bundle.putString("community", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected Object setLoadDate() {
        return "community";
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.fragment_community, null);

        ButterKnife.bind(this, view);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("社区");
        initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("图文发布成功", msg)) {
            mRefreshLayout.setEnableLoadMore(true);
            pager = 1;
            getDataFromNet();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    List<CommunityListBean.DataBean.DataBeanX> data = new ArrayList<>();

    private void initData() {
        llCommonBack.setVisibility(View.GONE);
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

        communityAdapter = new CommunityAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(communityAdapter);
        getDataFromNet();
        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFaBuDialog(getContext(), getActivity());
            }
        });
    }

    private void showFaBuDialog(Context context, Activity activity) {
        RelativeLayout ll_popup;
        PopupWindow pop;
        pop = new PopupWindow(context);
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_send_circle,
                null);
        ll_popup = view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = view.findViewById(R.id.parent);
        ImageView bt1 = view.findViewById(R.id.iv_tuwen);
        ImageView bt2 = view.findViewById(R.id.iv_shipin);
        ImageView bt3 = view.findViewById(R.id.iv_exit);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (XueYiCheUtils.IsLogin()) {
                    Intent intent1 = new Intent(context, TuWenFabuActivity.class);
//                    intent1.putExtra("circle_id", circle_id);
//                    intent1.putExtra("type", type);
//                    intent1.putExtra("spot_id", spot_id);
//                    intent1.putExtra("have_been_type", have_been_type);
                    context.startActivity(intent1);

                } else {
                    showToastShort("请先登录");
                    context.startActivity(new Intent(activity, LoginFirstStepActivity.class));
//                    openActivity(LoginFirstStepActivity.class);
                }
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (XueYiCheUtils.IsLogin()) {
//                    if (ConstantsFiled.can_upload) {
                    Intent intent = new Intent(context, ShiPinFaBuActivity.class);
//                        intent.putExtra("type", type);
//                        intent.putExtra("circle_id", circle_id);
//                        intent.putExtra("have_been_type", have_been_type);
                    context.startActivity(intent);
//                    } else {
//                        showToastShort("上一段视频还未完成,请稍后再试!");
//                    }

                } else {
                    showToastShort("请先登录");
                    context.startActivity(new Intent(activity, LoginFirstStepActivity.class));

                }
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                context, R.anim.activity_translate_in));
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    int pager = 1;

    private void getDataFromNet() {
        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pager + "");
        MyHttpUtils.postHttpMessage(AppUrl.articlecommunity, params, CommunityListBean.class, new RequestCallBack<CommunityListBean>() {
            @Override
            public void requestSuccess(CommunityListBean json) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                List<CommunityListBean.DataBean.DataBeanX> data = json.getData().getData();
                if (data == null || data.size() == 0) {

                } else {
                    if (pager == 1) {
                        communityAdapter.setNewData(data);
                    } else {
                        communityAdapter.addData(data);
                    }

                    if (data.size() < 10) {
                        mRefreshLayout.setEnableLoadMore(false);
                    }

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
//            if (position == 1 || position == 7) {
//                return CommunityListBean.DataBean.DataBeanX.TEXT;
//            } else if (position == 4 || position == 5 || position == 6) {
//                return CommunityListBean.DataBean.DataBeanX.TEXT_ONE_PIC;
//            } else if (position == 2) {
//                return CommunityListBean.DataBean.DataBeanX.TEXT_PICS;
//            } else if (position == 0) {
//                return CommunityListBean.DataBean.DataBeanX.TEXT_VIDEO;
//            } else if (position == 3) {
//                return CommunityListBean.DataBean.DataBeanX.TEXT_TWO_PIC;
//            }
            if (TextUtils.isEmpty(dataBeanX.getVideo_file())) {
                return CommunityListBean.DataBean.DataBeanX.TEXT_PICS;
            } else {
                return CommunityListBean.DataBean.DataBeanX.TEXT_VIDEO;
            }
//            return CommunityListBean.DataBean.DataBeanX.TEXT;
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
        }).show(getChildFragmentManager(), "comment");
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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quan_piclist_item, parent, false);
            return new ViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            String str = strs.get(position);
            Glide.with(App.context).load(str)/*.addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(App.context).load(R.mipmap.icon_image_error).into(holder.imageView);
                            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                    });

                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            })*/.into(holder.imageView);
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
