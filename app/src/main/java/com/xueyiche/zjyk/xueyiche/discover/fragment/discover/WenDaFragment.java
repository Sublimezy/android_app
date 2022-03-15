package com.xueyiche.zjyk.xueyiche.discover.fragment.discover;

import android.content.Intent;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.xueyiche.zjyk.xueyiche.discover.activity.FaBuQuestionActivity;
import com.xueyiche.zjyk.xueyiche.discover.activity.WenDaContent;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.discover.bean.WenDaBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
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

import de.greenrobot.event.EventBus;

public class WenDaFragment extends BaseFragment implements View.OnClickListener{
    private ListView lv_wenda;
    private  List<WenDaBean.ContentBean> list = new ArrayList<>();
    private  List<WenDaBean.ContentBean> content;
    private int pager = 1;
    private RefreshLayout refreshLayout;
    private boolean isPrepared;
    private WenDaAdapter wenDaAdapter;
    private ImageView iv_fabu;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        getDataFromNet();
    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.wenda_fragment,null);
        lv_wenda = (ListView) view.findViewById(R.id.lv_wenda);
        iv_fabu = (ImageView) view.findViewById(R.id.iv_fabu);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        EventBus.getDefault().register(this);
        lv_wenda.setAdapter(new WenDaAdapter());
        isPrepared = true;
        iv_fabu.setOnClickListener(this);
        initData();
        lazyLoad();
        return view;
    }
    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("问答", msg)) {
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
        lv_wenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list!=null&&list.size()!=0) {
                    WenDaBean.ContentBean contentBean = list.get(i);
                    if (contentBean!=null) {
                        int id = contentBean.getId();
                        Intent intent = new Intent(App.context, WenDaContent.class);
                        intent.putExtra("wenda_id",id+"");
                        startActivity(intent);
                    }
                }
            }
        });

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


    @Override
    protected Object setLoadDate() {
        return "xyc";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fabu:
                if (DialogUtils.IsLogin()) {
                    startActivity(new Intent(getContext(),FaBuQuestionActivity.class));
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
        }
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            OkHttpUtils.post().url(AppUrl.Discover_WenDa_List)
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
            OkHttpUtils.post().url(AppUrl.Discover_SheQu_List)
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
        final WenDaBean wenDaBean = JsonUtil.parseJsonToBean(string, WenDaBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (wenDaBean != null) {
                    if (wenDaBean.getContent() != null && wenDaBean.getContent().size() != 0) {
                        if (!isMore) {
                            if (list.size() != 0) {
                                list.clear();
                            }
                            List<WenDaBean.ContentBean> content = wenDaBean.getContent();
                            if (content != null) {
                                list.addAll(content);
                            }
                            wenDaAdapter = new WenDaAdapter();
                            lv_wenda.setAdapter(wenDaAdapter);
                        } else {
                            content = wenDaBean.getContent();
                            list.addAll(content);
                            wenDaAdapter.notifyDataSetChanged();
                        }
                    }else {
                        if (!isMore) {
                            if (wenDaAdapter!=null) {
                                list.clear();
                                wenDaAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(getContext(), "无更多数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    public class WenDaAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (list!=null) {
                return list.size();
            }else {
                return 0;
            }
        }

        @Override
        public Object getItem(int i) {
            return getItem(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolerVideo viewHolerSheQu = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.wenda_list_item, null);
                viewHolerSheQu = new ViewHolerVideo(view);
                view.setTag(viewHolerSheQu);
            } else {
                viewHolerSheQu = (ViewHolerVideo) view.getTag();
            }
            if (list != null && list.size() != 0) {
                WenDaBean.ContentBean contentBean = list.get(i);
                if (contentBean != null) {
                    //评论数
                    int comment_count = contentBean.getComment_count();
                    final int id = contentBean.getId();
                    viewHolerSheQu.tv_wenda_pinglun_number.setText(comment_count+"");
                    String head_img = contentBean.getHead_img();
                    Picasso.with(App.context).load(head_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolerSheQu.wd_head);
                    String nickname = contentBean.getNickname();
                    viewHolerSheQu.tv_name.setText(nickname);
                    String release_time = contentBean.getRelease_time();
                    viewHolerSheQu.tv_release_time.setText(release_time);
                    //点赞数
                    int praise_count = contentBean.getPraise_count();
                    viewHolerSheQu.tv_wenda_dianzan_number.setText(praise_count+"");
                    String image1 = contentBean.getImage1();
                    String image2 = contentBean.getImage2();
                    String image3 = contentBean.getImage3();
                    String title = contentBean.getTitle();
                    final String is_praise = contentBean.getIs_praise();
                    viewHolerSheQu.tv_wenda_title.setText(title);
                    if ("1".equals(is_praise)) {
                        viewHolerSheQu.iv_wd_zan.setImageDrawable(ContextCompat.getDrawable(App.context, R.mipmap.czh_video_zan_se));
                    } else {
                        viewHolerSheQu.iv_wd_zan.setImageDrawable(ContextCompat.getDrawable(App.context, R.mipmap.czh_video_zan));
                    }
                    if (TextUtils.isEmpty(image1)&&TextUtils.isEmpty(image2)&&TextUtils.isEmpty(image3)) {
                        viewHolerSheQu.ll_pic.setVisibility(View.GONE);
                    }else {
                        if (TextUtils.isEmpty(image1)) {
                            viewHolerSheQu.iv_one.setVisibility(View.INVISIBLE);
                        } else {
                            Picasso.with(App.context).load(image1).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolerSheQu.iv_one);
                            viewHolerSheQu.iv_one.setVisibility(View.VISIBLE);
                        }
//                        if (TextUtils.isEmpty(image2)) {
//                            viewHolerSheQu.iv_two.setVisibility(View.INVISIBLE);
//                        } else {
//                            Picasso.with(App.context).load(image2).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolerSheQu.iv_two);
//                            viewHolerSheQu.iv_two.setVisibility(View.VISIBLE);
//                        }
//                        if (TextUtils.isEmpty(image3)) {
//                            viewHolerSheQu.iv_three.setVisibility(View.INVISIBLE);
//                        } else {
//                            Picasso.with(App.context).load(image3).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolerSheQu.iv_three);
//                            viewHolerSheQu.iv_three.setVisibility(View.VISIBLE);
//                        }

                    }

                    viewHolerSheQu.iv_wd_zan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("0".equals(is_praise)) {
                                dianZan(id);
                            }
                        }
                    });
                    viewHolerSheQu.tv_dianzan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("0".equals(is_praise)) {
                                dianZan(id);
                            }
                        }
                    });
                }
            }

            return view;
        }

        class ViewHolerVideo {
            private TextView tv_wenda_title, tv_wenda_pinglun_number, tv_wenda_dianzan_number,
                    tv_name,tv_release_time;
            private ImageView  iv_wd_zan;
            private CircleImageView wd_head;
            private LinearLayout ll_pic;
            private RoundImageView  iv_one /*,iv_two, iv_three*/;
            private TextView tv_dianzan,tv_pinglun;
            public ViewHolerVideo(View view) {
                ll_pic = (LinearLayout) view.findViewById(R.id.ll_pic);
                tv_wenda_title = (TextView) view.findViewById(R.id.tv_wenda_title);
                tv_release_time = (TextView) view.findViewById(R.id.tv_release_time);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                iv_wd_zan = (ImageView) view.findViewById(R.id.iv_wd_zan);
                iv_one = (RoundImageView) view.findViewById(R.id.iv_one);
//                iv_two = (RoundImageView) view.findViewById(R.id.iv_two);
//                iv_three = (RoundImageView) view.findViewById(R.id.iv_three);
                wd_head = (CircleImageView) view.findViewById(R.id.wd_head);
                tv_wenda_pinglun_number = (TextView) view.findViewById(R.id.tv_wenda_pinglun_number);
                tv_wenda_dianzan_number = (TextView) view.findViewById(R.id.tv_wenda_dianzan_number);
                tv_dianzan = (TextView) view.findViewById(R.id.tv_dianzan);
                tv_pinglun = (TextView) view.findViewById(R.id.tv_pinglun);
            }
        }
    }

    private void dianZan(int article_comment_id) {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            showProgressDialog(getActivity(),false);
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.FaXian_DianZan_Content)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .addParams("user_id", user_id)
                    .addParams("like_type", "2")
                    .addParams("id", article_comment_id+"")
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
