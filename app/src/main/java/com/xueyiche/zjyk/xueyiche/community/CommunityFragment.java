package com.xueyiche.zjyk.xueyiche.community;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.community.activity.ShiPinPlayActivity;
import com.xueyiche.zjyk.xueyiche.community.activity.TuWenXiangQingActivity;
import com.xueyiche.zjyk.xueyiche.community.bean.CommunityListBean;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.activity.DaShangActivity;
import com.xueyiche.zjyk.xueyiche.mine.decoration.GridItemDecoration;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        return view;
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
            expandableTextView.setContent(item.getContent());
            Glide.with(App.context).load(item.getVideo_file())
                    .into(image);
            helper.setText(R.id.tv_fabu_time,item.getCreatetime());
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ShiPinPlayActivity.class);
                    intent.putExtra("id",item.getId()+"");
                    startActivity(intent);
                }
            });
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
            helper.setText(R.id.tv_fabu_time,item.getCreatetime());
            recycler.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            QuanPicAdapter quanPicAdapter = new QuanPicAdapter(item.getImages(), "" + item.getId());
            recycler.setAdapter(quanPicAdapter);
        }


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
            Glide.with(App.context).load(str).addListener(new RequestListener<Drawable>() {
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
            }).into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //图文
                    Intent intent = new Intent(getContext(), TuWenXiangQingActivity.class);
                    intent.putExtra("id",content_id);
                    startActivity(intent);
                }
            });
        }
    }

}
