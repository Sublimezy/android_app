package com.xueyiche.zjyk.xueyiche.discover.fragment.discover;

import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.discover.activity.TeachVideoContent;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.discover.bean.VideoBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
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

public class VideoFragment extends BaseFragment {
    private List<VideoBean.ContentBean> list = new ArrayList<>();
    private List<VideoBean.ContentBean> content;
    private int pager = 1;
    private RefreshLayout refreshLayout;
    private VideoAdapter videoAdapter;
    private RecyclerView rv_shipin;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.video_fragment, null);
        rv_shipin = (RecyclerView) view.findViewById(R.id.rv_shipin);
        rv_shipin.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        EventBus.getDefault().register(this);
        getDataFromNet();
        initData();
        videoAdapter = new VideoAdapter(list);
        rv_shipin.setAdapter(videoAdapter);
        return view;
    }

    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("视频", msg)) {
            getDataFromNet();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    private void initData() {

        lazyLoad();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            pager = 1;
                            content = null;
                            getDataFromNet();
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                            Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 1500);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                                                @Override
                                                public void onLoadMore(final RefreshLayout refreshLayout) {
                                                    refreshLayout.getLayout().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (XueYiCheUtils.IsHaveInternet(App.context)) {
                                                                if (content != null && content.size() == 0) {
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
                                            }

        );
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            showProgressDialog(getActivity(), false);
            OkHttpUtils.post().url(AppUrl.Discover_Video_List)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .addParams("pager", pager + "")
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

    private void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            OkHttpUtils.post().url(AppUrl.Discover_Video_List)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .addParams("pager", pager + "")
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

    private void processData(String string, final boolean isMore) {
        final VideoBean wenDaBean = JsonUtil.parseJsonToBean(string, VideoBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (wenDaBean != null) {
                    if (wenDaBean.getContent() != null && wenDaBean.getContent().size() != 0) {
                        if (!isMore) {
                            if (list.size() != 0) {
                                list.clear();
                            }
                            List<VideoBean.ContentBean> content = wenDaBean.getContent();
                            if (content != null) {
                                list.addAll(content);
                            }
                        } else {
                            content = wenDaBean.getContent();
                            list.addAll(content);
                        }
                        videoAdapter.notifyDataSetChanged();
                    } else {
                        if (!isMore) {
                            if (videoAdapter != null) {
                                list.clear();
                                videoAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getContext(), "无更多数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }


    @Override
    protected Object setLoadDate() {
        return "xyc";
    }

    private void dianZan(int article_comment_id) {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            showProgressDialog(getActivity(), false);
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.FaXian_DianZan_Content)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .addParams("user_id", user_id)
                    .addParams("like_type", "3")
                    .addParams("video_id", article_comment_id + "")
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

    public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
        private List<VideoBean.ContentBean> list;

        public VideoAdapter(List<VideoBean.ContentBean> list) {
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView video_teach_title, tv_video_teach_comment_number, tv_video_teach_dianzan_number;
            RoundImageView video_teach_bg;
            LinearLayout ll_video_item;
            ImageView iv_video_zan;

            public ViewHolder(View v) {
                super(v);
            }

        }

        @Override
        public int getItemCount() {
            if (list != null) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_teach_list_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.video_teach_title = (TextView) v.findViewById(R.id.video_teach_title);
            holder.tv_video_teach_comment_number = (TextView) v.findViewById(R.id.tv_video_teach_comment_number);
            holder.tv_video_teach_dianzan_number = (TextView) v.findViewById(R.id.tv_video_teach_dianzan_number);
            holder.video_teach_bg = (RoundImageView) v.findViewById(R.id.video_teach_bg);
            holder.ll_video_item = (LinearLayout) v.findViewById(R.id.ll_video_item);
            holder.iv_video_zan = (ImageView) v.findViewById(R.id.iv_video_zan);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (list != null) {
                VideoBean.ContentBean contentBean = list.get(position);
                if (contentBean != null) {
                    int comment_count = contentBean.getComment_count();
                    final String is_praise = contentBean.getIs_praise();
                    int praise_count = contentBean.getPraise_count();
                    final int video_id = contentBean.getVideo_id();
                    String screenshot_image_url = contentBean.getScreenshot_image_url();
                    if (!TextUtils.isEmpty(screenshot_image_url)) {
                        Picasso.with(App.context).load(screenshot_image_url).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(holder.video_teach_bg);
                    }
                    String title = contentBean.getTitle();
                    if (!TextUtils.isEmpty(title)) {
                        holder.video_teach_title.setText(title);
                    }
//                    if (position == 1) {
//                        holder.video_teach_bg.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 500));
//                    } else {
//                        holder.video_teach_bg.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 700));
//                    }

                    if ("" + comment_count != null) {
                        holder.tv_video_teach_comment_number.setText("" + comment_count);
                    }
                    if ("" + praise_count != null) {
                        holder.tv_video_teach_dianzan_number.setText("" + praise_count);
                    }
                    if ("0".equals(is_praise)) {
                        holder.iv_video_zan.setImageDrawable(ContextCompat.getDrawable(App.context, R.mipmap.czh_video_zan));
                    } else {
                        holder.iv_video_zan.setImageDrawable(ContextCompat.getDrawable(App.context, R.mipmap.czh_video_zan_se));
                    }
                    holder.iv_video_zan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("0".equals(is_praise)) {
                                dianZan(video_id);
                            }
                        }
                    });

                    holder.ll_video_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (list != null) {
                                VideoBean.ContentBean contentBean = list.get(position);
                                if (contentBean != null) {
                                    int video_id = contentBean.getVideo_id();
                                    if (!TextUtils.isEmpty("" + video_id)) {
                                        Intent intent = new Intent(App.context, TeachVideoContent.class);
                                        intent.putExtra("video_id", "" + video_id);
                                        startActivity(intent);
                                    }
                                }

                            }

                        }
                    });
                }
            }
        }


    }
}
