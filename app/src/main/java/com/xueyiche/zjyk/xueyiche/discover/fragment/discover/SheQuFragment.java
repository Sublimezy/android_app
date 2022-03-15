package com.xueyiche.zjyk.xueyiche.discover.fragment.discover;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.xueyiche.zjyk.xueyiche.discover.activity.SheQuContent;
import com.xueyiche.zjyk.xueyiche.discover.bean.SheQuBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class SheQuFragment extends BaseFragment {
    private ListView lv_shequ;
    private RefreshLayout refreshLayout;
    private int pager = 1;
    private List<SheQuBean.ContentBean> list = new ArrayList<>();
    private List<SheQuBean.ContentBean> content;
    private SheQuAdapter sheQuAdapter;
    private boolean isPrepared;


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        getDataFromNet();
    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.shequ_fragment, null);
        lv_shequ = (ListView) view.findViewById(R.id.lv_shequ);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        EventBus.getDefault().register(this);
        initData();
        getDataFromNet();
        isPrepared = true;
        lazyLoad();
        return view;
    }
    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("社区", msg)) {
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
        lv_shequ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SheQuBean.ContentBean contentBean = list.get(i);
                if (contentBean!=null) {
                    int id = contentBean.getId();
                    Intent intent = new Intent(App.context, SheQuContent.class);
                    intent.putExtra("shequ_id",id+"");
                    startActivity(intent);
                }
            }
        });
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
        });
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

    private void getDataFromNet() {
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
        final SheQuBean sheQuBean = JsonUtil.parseJsonToBean(string, SheQuBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (sheQuBean != null) {
                    if (sheQuBean.getContent() != null && sheQuBean.getContent().size() != 0) {
                        if (!isMore) {
                            if (list.size() != 0) {
                                list.clear();
                            }
                            List<SheQuBean.ContentBean> content = sheQuBean.getContent();
                            if (content != null) {
                                list.addAll(content);
                            }
                            sheQuAdapter = new SheQuAdapter();
                            lv_shequ.setAdapter(sheQuAdapter);
                        } else {
                            content = sheQuBean.getContent();
                            list.addAll(content);
                            sheQuAdapter.notifyDataSetChanged();
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

    public class SheQuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (list != null) {
                return list.size();
            } else {
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolerSheQu viewHolerSheQu = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.shequ_list_item, null);
                viewHolerSheQu = new ViewHolerSheQu(view);
                view.setTag(viewHolerSheQu);
            } else {
                viewHolerSheQu = (ViewHolerSheQu) view.getTag();
            }
            if (list != null) {
                SheQuBean.ContentBean contentBean = list.get(i);
                if (contentBean != null) {
                    //评论数
                    int comment_count = contentBean.getComment_count();
                    int praise_count = contentBean.getPraise_count();
                    viewHolerSheQu.tvSheQuNumber.setText(comment_count + "评论");
                    viewHolerSheQu.tvZanNumber.setText(praise_count + "点赞");
                    //图片
                    String image = contentBean.getImage();
                    if (TextUtils.isEmpty(image)) {
                        viewHolerSheQu.rivSheQuPicture.setVisibility(View.GONE);
                    } else {
                        Picasso.with(App.context).load(image).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolerSheQu.rivSheQuPicture);
                        viewHolerSheQu.rivSheQuPicture.setVisibility(View.VISIBLE);
                    }
                    //昵称
                    String nickname = contentBean.getInformation_from();
                    viewHolerSheQu.tvSheQuName.setText(nickname);
                    //新闻标题
                    String title = contentBean.getTitle();
                    viewHolerSheQu.tvSheQuTitle.setText(title);
                }
            }
            return view;
        }

        class ViewHolerSheQu {
            private TextView tvSheQuTitle, tvSheQuName, tvSheQuNumber,tvZanNumber;
            private RoundImageView rivSheQuPicture;

            public ViewHolerSheQu(View view) {
                tvSheQuTitle = (TextView) view.findViewById(R.id.tvSheQuTitle);
                tvSheQuName = (TextView) view.findViewById(R.id.tvSheQuName);
                tvSheQuNumber = (TextView) view.findViewById(R.id.tvSheQuNumber);
                tvZanNumber = (TextView) view.findViewById(R.id.tvZanNumber);
                rivSheQuPicture = (RoundImageView) view.findViewById(R.id.rivSheQuPicture);
            }
        }
    }
}
