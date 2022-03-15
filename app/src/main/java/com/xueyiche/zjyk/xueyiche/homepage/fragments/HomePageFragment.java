package com.xueyiche.zjyk.xueyiche.homepage.fragments;

import android.content.Intent;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.tencent.bugly.beta.Beta;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.carlife.CarLifeContentActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.constants.bean.OnLineBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaActivity;
import com.xueyiche.zjyk.xueyiche.discover.activity.SheQuContent;
import com.xueyiche.zjyk.xueyiche.discover.activity.TeachVideoContent;
import com.xueyiche.zjyk.xueyiche.discover.activity.WenDaContent;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.discover.fragment.activitys.SheQuListActivity;
import com.xueyiche.zjyk.xueyiche.discover.fragment.activitys.VideoListActivity;
import com.xueyiche.zjyk.xueyiche.discover.fragment.activitys.WenDaListActivity;
import com.xueyiche.zjyk.xueyiche.driverschool.driverschool.DirectDriverSchoolContent;
import com.xueyiche.zjyk.xueyiche.driverschool.driverschool.DriverSchoolContent;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.examtext.Answerwithcash;
import com.xueyiche.zjyk.xueyiche.examtext.TestDriverBookActivity;
import com.xueyiche.zjyk.xueyiche.homepage.activities.KuaiBaoListActivity;
import com.xueyiche.zjyk.xueyiche.homepage.activities.TrainBaoMingActivity;
import com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.CarQueryActivity;
import com.xueyiche.zjyk.xueyiche.homepage.activities.location.LocationActivity;
import com.xueyiche.zjyk.xueyiche.homepage.bean.KuaiBaoListBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ShouYeBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.FlyBanner;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity.TrainingDateManageActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity.TrainingTimeCoachActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student.CoachListActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student.StudentTrainTimeActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.newactivity.PracticeCarMapActivity;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.usedcar.activity.BuyCarActivity;
import com.xueyiche.zjyk.xueyiche.usedcar.activity.UsedCarActivity;
import com.xueyiche.zjyk.xueyiche.usedcar.activity.zhengxin.WriteInfomationActivity;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

//import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitYuYueActivity;

/**
 * Created by Owner on 2016/9/21.
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener, NetBroadcastReceiver.netEventHandler {
    private TextView tvLocation, tv_gengduo;
    private String area_name;
    //三个主按键
    private LinearLayout ll_one, ll_three, ll_four, ll_five;
    private LinearLayout ll_address;
    private boolean isPrepared = true;
    //新版轮播图
    private FlyBanner fb_shouye;
    //轮播图
    private List<ShouYeBean.ContentBean.VolutionContentBean> volution_content;
    private RefreshLayout refreshLayout;
    private GridLayoutManager mLayoutManage;
    private ImageView iv_weizhang, iv_baoxian, iv_weibao, iv_kefu, iv_zhengxin, iv_more;
    private ViewFlipper marqueeView;
    private RadioButton rb_shequ, rb_video, rb_wenda;
    private RecyclerView rv_shipin;
    private WenDaAdapter wenDaAdapter;
    private VideoAdapter videoAdapter;
    private SySheQuAdapter sySheQuAdapter;
    private List<ShouYeBean.ContentBean.InformationContentBean> information_content;
    private List<ShouYeBean.ContentBean.QuestionsContentBean> questions_content;
    private List<ShouYeBean.ContentBean.VideoContentBean> video_content;
    private String type = "0";
    private LinearLayout llBaoxian,ll_search;

    private ImageView iv_zixun,iv_shipin,iv_wenda;

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home_page_fragment, null);
        ll_address = (LinearLayout) view.findViewById(R.id.ll_address);
        iv_kefu = (ImageView) view.findViewById(R.id.iv_kefu);
        tvLocation = (TextView) view.findViewById(R.id.text_include_location);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        rb_shequ = view.findViewById(R.id.rb_shequ);
        rb_video = view.findViewById(R.id.rb_video);
        rb_wenda = view.findViewById(R.id.rb_wenda);
        ll_search = view.findViewById(R.id.ll_search);

        iv_zixun = view.findViewById(R.id.iv_zixun);
        iv_shipin = view.findViewById(R.id.iv_shipin);
        iv_wenda = view.findViewById(R.id.iv_wenda);
        rv_shipin = view.findViewById(R.id.rv_shipin);
        tv_gengduo = view.findViewById(R.id.tv_gengduo);
        llBaoxian = view.findViewById(R.id.llBaoxian);
        rb_shequ.setChecked(true);
        //四大模块
        ll_one = (LinearLayout) view.findViewById(R.id.ll_one);
        ll_three = (LinearLayout) view.findViewById(R.id.ll_three);
        iv_more = (ImageView) view.findViewById(R.id.iv_more);
        ll_four = (LinearLayout) view.findViewById(R.id.ll_four);
        ll_five = (LinearLayout) view.findViewById(R.id.ll_five);
        marqueeView = (ViewFlipper) view.findViewById(R.id.marqueeView);
        ll_one.setOnClickListener(this);
        ll_three.setOnClickListener(this);
        ll_four.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        ll_five.setOnClickListener(this);
        iv_more.setOnClickListener(this);
        rb_shequ.setOnClickListener(this);
        rb_video.setOnClickListener(this);
        rb_wenda.setOnClickListener(this);
        //优惠活动
        iv_weizhang = (ImageView) view.findViewById(R.id.iv_weizhang);
        iv_baoxian = (ImageView) view.findViewById(R.id.iv_baoxian);
        iv_weibao = (ImageView) view.findViewById(R.id.iv_weibao);
        iv_weibao.setOnClickListener(this);
        iv_baoxian.setOnClickListener(this);
        iv_weizhang.setOnClickListener(this);
        Beta.checkUpgrade(false, false);
        iv_zhengxin = (ImageView) view.findViewById(R.id.iv_zhengxin);
        mLayoutManage = new GridLayoutManager(getActivity(), 4);
        mLayoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        //新版轮播图
        fb_shouye = (FlyBanner) view.findViewById(R.id.fb_shouye);
        tvLocation.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        tv_gengduo.setOnClickListener(this);
        String user_phone = PrefUtils.getString(App.context, "user_phone", "");
        if (!TextUtils.isEmpty(user_phone)) {
            LogUtil.e("user_phone", user_phone);
            LogUtil.e("user_phone", AES.decrypt(user_phone));
        }

        iv_zhengxin.setOnClickListener(this);
        NetBroadcastReceiver.mListeners.add(this);
        iv_kefu.setOnClickListener(this);
        EventBus.getDefault().register(this);
        lazyLoad();
        initData();
        getdate("0");
        iv_zixun.setVisibility(View.VISIBLE);
        iv_shipin.setVisibility(View.GONE);
        iv_wenda.setVisibility(View.GONE);
        rb_shequ.setTypeface(null, Typeface.BOLD);
        rb_video.setTypeface(null, Typeface.NORMAL);
        rb_wenda.setTypeface(null, Typeface.NORMAL);
        getOnLine();
        rv_shipin.setHasFixedSize(true);
        rv_shipin.setNestedScrollingEnabled(false);
        return view;
    }

    private void getOnLine() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            MyHttpUtils.getHttpMessage(AppUrl.onlineshowinfo, OnLineBean.class, new RequestCallBack<OnLineBean>() {
                @Override
                public void requestSuccess(OnLineBean json) {
                    if (200==json.getCode()) {
                        if ("0".equals(json.getContent().getVar_value())) {
                            llBaoxian.setVisibility(View.GONE);
                        }else {
                            llBaoxian.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }

    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("更新首页", msg)) {
            getdate("0");
            rb_shequ.setChecked(true);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent3 = new Intent(App.context, CarQueryActivity.class);
        Intent intent = new Intent(App.context, CarLifeContentActivity.class);
        intent.putExtra("search_type", "");
        intent.putExtra("coupon_id", "");
        intent.putExtra("service_id", "");
        intent.putExtra("search_name", "");
        switch (v.getId()) {
            case R.id.iv_kefu:
                //客服
                openActivity(KeFuActivity.class);
                break;
            case R.id.rb_shequ:
                getdate("0");
                type = "0";

                iv_zixun.setVisibility(View.VISIBLE);
                iv_shipin.setVisibility(View.GONE);
                iv_wenda.setVisibility(View.GONE);

                rb_shequ.setTypeface(null, Typeface.BOLD);
                rb_video.setTypeface(null, Typeface.NORMAL);
                rb_wenda.setTypeface(null, Typeface.NORMAL);
                break;
            case R.id.rb_video:
                getdate("1");
                type = "1";
                iv_zixun.setVisibility(View.GONE);
                iv_shipin.setVisibility(View.VISIBLE);
                iv_wenda.setVisibility(View.GONE);
                rb_shequ.setTypeface(null, Typeface.NORMAL);
                rb_video.setTypeface(null, Typeface.BOLD);
                rb_wenda.setTypeface(null, Typeface.NORMAL);
                break;
            case R.id.rb_wenda:
                getdate("2");
                type = "2";
                iv_zixun.setVisibility(View.GONE);
                iv_shipin.setVisibility(View.GONE);
                iv_wenda.setVisibility(View.VISIBLE);
                rb_shequ.setTypeface(null, Typeface.NORMAL);
                rb_video.setTypeface(null, Typeface.NORMAL);
                rb_wenda.setTypeface(null, Typeface.BOLD);
                break;
            case R.id.tv_gengduo:
                if ("0".equals(type)) {
                    openActivity(SheQuListActivity.class);
                } else if ("1".equals(type)) {
                    openActivity(VideoListActivity.class);
                } else if ("2".equals(type)) {
                    openActivity(WenDaListActivity.class);
                }
                break;
            case R.id.ll_five:
//                //代驾
                if (DialogUtils.IsLogin()) {
                    openActivity(DaiJiaActivity.class);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.ll_one:
                //考驾照

                if (DialogUtils.IsLogin()) {
                    startActivity(new Intent(App.context, TestDriverBookActivity.class));


                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.ll_three:
                startActivity(new Intent(getActivity(), PracticeCarMapActivity.class));
                break;
            case R.id.ll_four:
                openActivity(UsedCarActivity.class);
                break;
            case R.id.iv_zhengxin:
                if (DialogUtils.IsLogin()) {
                    Intent intentZheng = new Intent(getActivity(), WriteInfomationActivity.class);
                    startActivity(intentZheng);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.ll_address:
                startActivity(new Intent(App.context, LocationActivity.class));
                getActivity().finish();
                break;
            case R.id.iv_more:
                openActivity(KuaiBaoListActivity.class);
                break;
            case R.id.iv_weizhang:
                if (DialogUtils.IsLogin()) {
                    intent3.putExtra("type", "2");
                    startActivity(intent3);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.iv_baoxian:
                if (DialogUtils.IsLogin()) {
                    intent3.putExtra("type", "3");
                    startActivity(intent3);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.iv_weibao:
                if (DialogUtils.IsLogin()) {
                    intent3.putExtra("type", "1");
                    startActivity(intent3);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.ll_search:
                Intent intent31 = new Intent(getActivity(), BuyCarActivity.class);
                intent31.putExtra("carSourceSearch", "");
                intent31.putExtra("rbhot_type", "");
                intent31.putExtra("rbhot_id", "");
                intent31.putExtra("level_id", "");
                startActivity(intent31);
                break;

        }
    }

    public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
        List<ShouYeBean.ContentBean.VideoContentBean> list;

        public VideoAdapter(List<ShouYeBean.ContentBean.VideoContentBean> video_content) {
            this.list = video_content;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView video_teach_title, tv_video_teach_comment_number, tv_video_teach_dianzan_number;
            RoundImageView video_teach_bg;
            LinearLayout ll_video_item,ll_dianzan;
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
            holder.ll_dianzan =  v.findViewById(R.id.ll_dianzan);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (list != null) {
                ShouYeBean.ContentBean.VideoContentBean contentBean = list.get(position);
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
                    holder.ll_dianzan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("0".equals(is_praise)) {
                                if (DialogUtils.IsLogin()) {
                                    dianZan(video_id, "3", "video_id");
                                } else {
                                    openActivity(LoginFirstStepActivity.class);
                                }
                            }
                        }
                    });

                    holder.ll_video_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (list != null) {
                                ShouYeBean.ContentBean.VideoContentBean contentBean = list.get(position);
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

    public class SySheQuAdapter extends RecyclerView.Adapter<SySheQuAdapter.ViewHolder> {
        List<ShouYeBean.ContentBean.InformationContentBean> list;

        public SySheQuAdapter(List<ShouYeBean.ContentBean.InformationContentBean> video_content) {
            this.list = video_content;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvSheQuTitle, tvSheQuName, tvSheQuNumber, tvZanNumber;
            private RoundImageView rivSheQuPicture;
            private LinearLayout ll_video_item;

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shequ_list_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            holder.tvSheQuTitle = (TextView) view.findViewById(R.id.tvSheQuTitle);
            holder.tvSheQuName = (TextView) view.findViewById(R.id.tvSheQuName);
            holder.tvSheQuNumber = (TextView) view.findViewById(R.id.tvSheQuNumber);
            holder.tvZanNumber = (TextView) view.findViewById(R.id.tvZanNumber);
            holder.rivSheQuPicture = (RoundImageView) view.findViewById(R.id.rivSheQuPicture);
            holder.ll_video_item = (LinearLayout) view.findViewById(R.id.ll_video_item);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (list != null) {
                final ShouYeBean.ContentBean.InformationContentBean contentBean = list.get(position);
                if (contentBean != null) {
                    //评论数
                    int comment_count = contentBean.getComment_count();
                    int praise_count = contentBean.getPraise_count();
                    holder.tvSheQuNumber.setText(comment_count + "评论");
                    holder.tvZanNumber.setText(praise_count + "点赞");
                    //图片
                    String image = contentBean.getImage();
                    if (TextUtils.isEmpty(image)) {
                        holder.rivSheQuPicture.setVisibility(View.GONE);
                    } else {
                        Picasso.with(App.context).load(image).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.rivSheQuPicture);
                        holder.rivSheQuPicture.setVisibility(View.VISIBLE);
                    }
                    //昵称
                    String nickname = contentBean.getInformation_from();
                    holder.tvSheQuName.setText(nickname);
                    //新闻标题
                    String title = contentBean.getTitle();
                    holder.tvSheQuTitle.setText(title);
                    holder.ll_video_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (list != null) {
                                int id = contentBean.getId();
                                Intent intent = new Intent(App.context, SheQuContent.class);
                                intent.putExtra("shequ_id", id + "");
                                startActivity(intent);
                            }

                        }
                    });
                }
            }
        }


    }

    public class WenDaAdapter extends RecyclerView.Adapter<WenDaAdapter.ViewHolder> {
        List<ShouYeBean.ContentBean.QuestionsContentBean> list;

        public WenDaAdapter(List<ShouYeBean.ContentBean.QuestionsContentBean> questions_content) {
            this.list = questions_content;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_wenda_title, tv_wenda_pinglun_number, tv_wenda_dianzan_number,
                    tv_name, tv_release_time;
            private ImageView iv_one, /*iv_two, iv_three,*/ iv_wd_zan;
            private CircleImageView wd_head;
            private LinearLayout ll_pic, ll_video_item;
            private TextView tv_dianzan,tv_pinglun;
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wenda_list_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            holder.ll_pic = (LinearLayout) view.findViewById(R.id.ll_pic);
            holder.ll_video_item = (LinearLayout) view.findViewById(R.id.ll_video_item);
            holder.tv_wenda_title = (TextView) view.findViewById(R.id.tv_wenda_title);
            holder.tv_release_time = (TextView) view.findViewById(R.id.tv_release_time);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.iv_wd_zan = (ImageView) view.findViewById(R.id.iv_wd_zan);
            holder.iv_one = (ImageView) view.findViewById(R.id.iv_one);
//            holder.iv_two = (ImageView) view.findViewById(R.id.iv_two);
//            holder.iv_three = (ImageView) view.findViewById(R.id.iv_three);
            holder.wd_head = (CircleImageView) view.findViewById(R.id.wd_head);
            holder.tv_wenda_pinglun_number = (TextView) view.findViewById(R.id.tv_wenda_pinglun_number);
            holder.tv_wenda_dianzan_number = (TextView) view.findViewById(R.id.tv_wenda_dianzan_number);
            holder.tv_dianzan = (TextView) view.findViewById(R.id.tv_dianzan);
            holder.tv_pinglun = (TextView) view.findViewById(R.id.tv_pinglun);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (list != null) {
                final ShouYeBean.ContentBean.QuestionsContentBean contentBean = list.get(position);
                if (contentBean != null) {
                    //评论数
                    int comment_count = contentBean.getComment_count();
                    final int id = contentBean.getId();
                    holder.tv_wenda_pinglun_number.setText(comment_count + "");
                    String head_img = contentBean.getHead_img();
                    Picasso.with(App.context).load(head_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.wd_head);
                    String nickname = contentBean.getNickname();
                    holder.tv_name.setText(nickname);
                    String release_time = contentBean.getRelease_time();
                    holder.tv_release_time.setText(release_time);
                    //点赞数
                    int praise_count = contentBean.getPraise_count();
                    holder.tv_wenda_dianzan_number.setText(praise_count + "");
                    String image1 = contentBean.getImage1();
                    String image2 = contentBean.getImage2();
                    String image3 = contentBean.getImage3();
                    String title = contentBean.getTitle();
                    final String is_praise = contentBean.getIs_praise();
                    holder.tv_wenda_title.setText(title);
                    if ("1".equals(is_praise)) {
                        holder.iv_wd_zan.setImageDrawable(ContextCompat.getDrawable(App.context, R.mipmap.czh_video_zan_se));
                    } else {
                        holder.iv_wd_zan.setImageDrawable(ContextCompat.getDrawable(App.context, R.mipmap.czh_video_zan));
                    }
                    if (TextUtils.isEmpty(image1) && TextUtils.isEmpty(image2) && TextUtils.isEmpty(image3)) {
                        holder.ll_pic.setVisibility(View.GONE);
                    } else {
                        if (TextUtils.isEmpty(image1)) {
                            holder.iv_one.setVisibility(View.INVISIBLE);
                        } else {
                            Picasso.with(App.context).load(image1).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.iv_one);
                            holder.iv_one.setVisibility(View.VISIBLE);
                        }
//                        if (TextUtils.isEmpty(image2)) {
//                            holder.iv_two.setVisibility(View.INVISIBLE);
//                        } else {
//                            Picasso.with(App.context).load(image2).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.iv_two);
//                            holder.iv_two.setVisibility(View.VISIBLE);
//                        }
//                        if (TextUtils.isEmpty(image3)) {
//                            holder.iv_three.setVisibility(View.INVISIBLE);
//                        } else {
//                            Picasso.with(App.context).load(image3).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.iv_three);
//                            holder.iv_three.setVisibility(View.VISIBLE);
//                        }

                    }
                    holder.iv_wd_zan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("0".equals(is_praise)) {
                                if (DialogUtils.IsLogin()) {
                                    dianZan(id, "2", "id");
                                } else {
                                    openActivity(LoginFirstStepActivity.class);
                                }
                            }
                        }
                    });
                    holder.tv_dianzan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("0".equals(is_praise)) {
                                if (DialogUtils.IsLogin()) {
                                    dianZan(id, "2", "id");
                                } else {
                                    openActivity(LoginFirstStepActivity.class);
                                }
                            }
                        }
                    });
                }

                holder.ll_video_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list != null) {
                            int id = contentBean.getId();
                            Intent intent = new Intent(App.context, WenDaContent.class);
                            intent.putExtra("wenda_id", id + "");
                            startActivity(intent);
                        }

                    }
                });
            }
        }


    }

    //like_type:视频：3  ；问答：2；资讯：1；
    private void dianZan(int article_comment_id, final String like_type, String id_key) {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            showProgressDialog(getActivity(), false);
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.FaXian_DianZan_Content)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .addParams("user_id", user_id)
                    .addParams("like_type", like_type)
                    .addParams(id_key, article_comment_id + "")
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
                                            if ("2".equals(like_type)) {
                                                getdate("2");
                                            } else if ("3".equals(like_type)) {
                                                getdate("1");
                                            }

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


    private void initData() {
        area_name = PrefUtils.getString(App.context, "area_name", "");
        if (!TextUtils.isEmpty(area_name)) {
            tvLocation.setText(area_name);
        } else {
            tvLocation.setText("选择城市");
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            getdate("0");
                            rb_shequ.setChecked(true);
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
        setLunbotu();
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.dissList(getActivity());
        fb_shouye.stopAutoPlay();
    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }

    @Override
    public void onPause() {
        super.onPause();
        fb_shouye.startAutoPlay();
    }

    private void getdate(final String type) {
        if (XueYiCheUtils.IsHaveInternet(getActivity())) {
            showProgressDialog(getActivity(), false);
            String id = LoginUtils.getId(getActivity());
            OkHttpUtils.post().url(AppUrl.All_ShouYe)
                    .addParams("device_id", id)
                    .addParams("area_id", PrefUtils.getParameter("area_id"))
                    .addParams("version", "3.0.0")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final ShouYeBean shouYeBean = JsonUtil.parseJsonToBean(string, ShouYeBean.class);
                        if (shouYeBean != null) {
                            final ShouYeBean.ContentBean content = shouYeBean.getContent();
                            if (content != null) {
                                volution_content = content.getVolution_content();

                            }
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (volution_content != null) {
                                        //轮播
                                        lunbotu(volution_content);

                                        information_content = content.getInformation_content();
                                        questions_content = content.getQuestions_content();
                                        video_content = content.getVideo_content();
                                        if ("0".equals(type)) {
                                            rv_shipin.setLayoutManager(new LinearLayoutManager(getActivity()));
                                            sySheQuAdapter = new SySheQuAdapter(information_content);
                                            rv_shipin.setAdapter(sySheQuAdapter);
                                        } else if ("1".equals(type)) {
                                            rv_shipin.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                                            videoAdapter = new VideoAdapter(video_content);
                                            rv_shipin.setAdapter(videoAdapter);
                                        } else if ("2".equals(type)) {
                                            rv_shipin.setLayoutManager(new LinearLayoutManager(getActivity()));
                                            wenDaAdapter = new WenDaAdapter(questions_content);
                                            rv_shipin.setAdapter(wenDaAdapter);
                                        }
                                    }
                                }
                            });
                        }
                        return string;
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
//快报
        OkHttpUtils.post()
                .url(AppUrl.ShouYe_KuaiBao)
                .addParams("device_id", LoginUtils.getId(getActivity()))
                .addParams("pager", "1")
                .addParams("page_size", "5")
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            KuaiBaoListBean kuaiBaoListBean = JsonUtil.parseJsonToBean(string, KuaiBaoListBean.class);
                            if (kuaiBaoListBean != null) {
                                int code = kuaiBaoListBean.getCode();
                                if (200 == code) {
                                    final List<KuaiBaoListBean.ContentBean> content = kuaiBaoListBean.getContent();
                                    if (content != null) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                for (int i = 0; i < content.size(); i++) {
                                                    View view = LayoutInflater.from(getContext()).inflate(R.layout.marquee_scroll_content, null);
                                                    TextView text = (TextView) view.findViewById(R.id.tv_text);
                                                    final int finalI = i;
                                                    text.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            int id = content.get(finalI).getId();
                                                            Intent intent = new Intent(App.context, SheQuContent.class);
                                                            intent.putExtra("shequ_id", id + "");
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    text.setText(content.get(i).getTitle());
                                                    marqueeView.addView(view);
                                                }
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
    }

    private void lunbotu(List<ShouYeBean.ContentBean.VolutionContentBean> volution_content) {
        List<String> imageurls = new ArrayList<String>();
        if (volution_content != null) {
            for (ShouYeBean.ContentBean.VolutionContentBean volutionContentBean : volution_content) {
                String address = volutionContentBean.getAddress();
                if (!TextUtils.isEmpty(address)) {
                    imageurls.add(address);
                }
            }
            fb_shouye.setImagesUrl(imageurls);
        }
    }


    private void setLunbotu() {
        fb_shouye.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (XueYiCheUtils.IsHaveInternet(getContext())) {
                    Intent intent1 = new Intent(App.context, CarLifeContentActivity.class);
                    Intent intent2 = new Intent(App.context, DriverSchoolContent.class);
                    Intent intent3 = new Intent(App.context, UrlActivity.class);
                    Intent intent4 = new Intent(App.context, TrainBaoMingActivity.class);
                    Intent intent5 = new Intent(App.context, Answerwithcash.class);
                    //1:标准洗车  2:保养维修  3:内饰清洗  4:钣金喷漆  5:全车打蜡  6:美容养护  7:优惠保险 8:4s店 9:美食 10:KTV
                    // 11:酒店  12:加油站  13:监测站  14:停车场  15:车管所  16:交警队  17:驾校 18:web_url 19:答题赢现金
                    ShouYeBean.ContentBean.VolutionContentBean contentBean = volution_content.get(position);
                    int carlife_type = contentBean.getCarlife_type();
                    String refer_id = contentBean.getRefer_id();
                    String web_url1 = contentBean.getWeb_url();
                    String address = contentBean.getAddress();
                    String main_title = contentBean.getMain_title();
                    String sub_title = contentBean.getSub_title();
                    if (1 == carlife_type || 2 == carlife_type || 3 == carlife_type || 4 == carlife_type || 5 == carlife_type || 6 == carlife_type
                            || 8 == carlife_type || 9 == carlife_type || 10 == carlife_type || 11 == carlife_type) {
                        intent1.putExtra("goods_id", "");
                        intent1.putExtra("shop_id", refer_id);
                        intent1.putExtra("service_id", carlife_type + "");
                        startActivity(intent1);
                    } else if (17 == carlife_type) {
                        intent2.putExtra("driver_school_id", refer_id);
                        startActivity(intent2);
                    } else if (18 == carlife_type) {
//                        if (DialogUtils.IsLogin()) {
                        intent3.putExtra("url", web_url1);
                        intent3.putExtra("type", "12");
                        startActivity(intent3);
//                        } else {
//                            openActivity(LoginFirstStepActivity.class);
//                        }
                    } else if (19 == carlife_type) {
                        startActivity(intent5);
                    } else if (2980 == carlife_type) {
                        openActivity(DirectDriverSchoolContent.class);
                    } else if (99 == carlife_type) {
                        intent3.putExtra("url", web_url1);
                        intent3.putExtra("type", "99");
                        intent3.putExtra("address", address);
                        intent3.putExtra("main_title", main_title);
                        intent3.putExtra("sub_title", sub_title);
                        startActivity(intent3);
                    }
                } else {
                    showToastShort(StringConstants.CHECK_NET);
                }
            }
        });
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
        } else {
            getdate("0");
            rb_shequ.setChecked(true);
        }
    }
}
