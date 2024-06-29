package com.xueyiche.zjyk.jiakao.examtext.kemuad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;

import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.constants.AppUrl;

import com.xueyiche.zjyk.jiakao.homepage.adapters.TopicAdapter;
import com.xueyiche.zjyk.jiakao.homepage.bean.QuestionBean;
import com.xueyiche.zjyk.jiakao.homepage.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.jiakao.homepage.db.QuestionDBHelper;
import com.xueyiche.zjyk.jiakao.homepage.view.Couterdown;
import com.xueyiche.zjyk.jiakao.homepage.view.CustomDialog;
import com.xueyiche.zjyk.jiakao.homepage.view.ReaderViewPager;
import com.xueyiche.zjyk.jiakao.utils.JsonUtil;
import com.xueyiche.zjyk.jiakao.utils.LogUtil;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;
import com.xueyiche.zjyk.jiakao.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//模拟考试页面
public class PracticeTestActivity extends BaseActivity implements View.OnClickListener {
    private ReaderViewPager readerViewPager;
    private List<QuestionBean> allQuestionA, allQuestionB;
    private PagerAdapter adapter;
    private List<QuestionBean> allQuestionC = new ArrayList<>();
    public TextView mTV_title_time, mTV_title_give, mTV_title_mun;
    private CheckBox checkBox;
    private QuestionDBHelper db;
    private int prePosition;
    private int curPosition;
    private int prePosition2;
    private int curPosition2;
    private int trueNum = 0;
    private int flaseNum = 0;
    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    private LinearLayout mLL_questionback;
    private int timu_qid;
    private TextView mTV_false_num;
    private TextView mTV_true_num;
    private Button btknow;
    private LinearLayout dragView;
    private RelativeLayout rlKnow;
    private ImageView mIV_topic_jiantou;
    private long time;
    private boolean once = true;

    private GoogleApiClient client;
    private FrameLayout fl_xuzhi;
    private CountDownTimer start;
    private String user_phone;
    private int okNumber = 95;

    @Override
    protected int initContentView() {
        db = new QuestionDBHelper(App.context);
/*        allQuestionA = dbManager.findAllPractice("1", "2", "40");
        allQuestionB = dbManager.findAllPractice("1", "1", "60");*/
        allQuestionC.addAll(allQuestionA);
        allQuestionC.addAll(allQuestionB);

        return R.layout.home_noniexam_subjecta_question;
    }

    @Override
    protected void initView() {
        user_phone = PrefUtils.getString(App.context, "user_phone", "0");
        readerViewPager = (ReaderViewPager) view.findViewById(R.id.vp_subjectA);
        checkBox = (CheckBox) view.findViewById(R.id.moni_test_one_include).findViewById(R.id.ck_moni_collection);
        //时间
        mTV_title_time = (TextView) view.findViewById(R.id.moni_test_one_include).findViewById(R.id.tv_title_time);
        //返回
        mLL_questionback = (LinearLayout) view.findViewById(R.id.moni_test_one_include).findViewById(R.id.ll_moni_back);
        //交卷
        mTV_title_give = (TextView) view.findViewById(R.id.moni_test_one_include).findViewById(R.id.tv_title_give);
        //题目的数量
        mTV_title_mun = (TextView) view.findViewById(R.id.moni_test_one_include).findViewById(R.id.tv_title_num);
        btknow = (Button) view.findViewById(R.id.bt_know_one);
        fl_xuzhi = (FrameLayout) findViewById(R.id.fl_xuzhi);
        rlKnow = (RelativeLayout) view.findViewById(R.id.rl_know_one);
        btknow.setOnClickListener(this);

        mTV_title_give.setOnClickListener(this);
        mLL_questionback.setOnClickListener(this);
        initSlidingUoPanel();
//        initViewPager();
        initList();

        //错题和对题的数量
        mTV_true_num = (TextView) view.findViewById(R.id.tv_true_num);
        mTV_false_num = (TextView) view.findViewById(R.id.tv_false_num);
        mTV_false_num.setText(trueNum + "");
        mTV_true_num.setText(flaseNum + "");
        if (topicAdapter != null) {
            topicAdapter.setDataNum(allQuestionC.size());
            topicAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initListener() {

    }

    private void initList() {
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(App.context, 6);
        topicAdapter = new TopicAdapter(this, allQuestionC);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(topicAdapter);
        topicAdapter.setOnTopicClickListener(new TopicAdapter.OnTopicClickListener() {
            @Override
            public void onClick(TopicAdapter.TopicViewHolder holder, int position) {
                curPosition = position;
                if (mLayout != null &&
                        (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                readerViewPager.setCurrentItem(position);
                topicAdapter.notifyCurPosition(curPosition);
                topicAdapter.notifyPrePosition(prePosition);
                prePosition = curPosition;
            }
        });
    }

    private void initSlidingUoPanel() {
        mLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        mIV_topic_jiantou = (ImageView) view.findViewById(R.id.iv_topic_jiantou);
        final int height = getWindowManager().getDefaultDisplay().getHeight();
        dragView = (LinearLayout) findViewById(R.id.dragView);
        SlidingUpPanelLayout.LayoutParams params = new SlidingUpPanelLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (height * 0.8f));
        dragView.setLayoutParams(params);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            private boolean a = true;

            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    mIV_topic_jiantou.setImageResource(R.mipmap.iv_topic_jiantou_down);
                } else if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {


                    mIV_topic_jiantou.setImageResource(R.mipmap.topic_up);
                } else if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.DRAGGING) {
                    mIV_topic_jiantou.setImageResource(R.mipmap.iv_topic_jiantou_down);
                }
                LogUtil.e("previousState", "newState " + newState);
                LogUtil.e("previousState", "previousState " + previousState);

                //  mIV_topic_jiantou.setImageResource(R.mipmap.iv_topic_jiantou_down);

            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }



    @Override
    protected void initData() {
        start = new Couterdown(2700000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time = millisUntilFinished;
                mTV_title_time.setText(toClock(millisUntilFinished));
            }

            @Override
            public String toClock(long millis) {
                return super.toClock(millis);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(App.context, AchievementActivity.class);
                long time2 = 2700000 - time;
                String number = trueNum + "";
                intent.putExtra("number", number);
                intent.putExtra("time", time2);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                intent.putExtra("presenttimed", str);
                panduanjiaojuan(intent, time2, number);
                Toast.makeText(App.context, "考试时间结束", Toast.LENGTH_SHORT).show();
            }
        }.start();

    }

    private int[] imgs = {R.mipmap.pic_yi, R.mipmap.pic_er, R.mipmap.pic_san, R.mipmap.pic_si, R.mipmap.pic_wu, R.mipmap.pic_liu,
            R.mipmap.pic_qi, R.mipmap.pic_ba, R.mipmap.pic_jiu, R.mipmap.pic_shi, R.mipmap.pic_shiyi, R.mipmap.pic_shier,
            R.mipmap.pic_shisan};



    @Override
    public void onBackPressed() {
        exitExam();

    }

    //错题到11的提示
    public void showAlertDialogA() {
        final CustomDialog.Builder builder = new CustomDialog.Builder(this);
        String tNumber = flaseNum + "";
        builder.setTitle("错了好多哟！");
        builder.setMessage("错题已到" + tNumber + "题，是否交卷?");
        builder.setPositiveButton("继续答题", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final Intent intent = new Intent(App.context, AchievementActivity.class);
                        long time2 = 2700000 - time;
                        String number = trueNum + "";
                        intent.putExtra("number", number);
                        intent.putExtra("time", time2);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String str = formatter.format(curDate);
                        intent.putExtra("presenttimed", str);
                        startActivity(intent);
                        finish();
//                        OkHttpUtils.post().url(AppUrl.DT).addParams("user_phone", user_phone)
//                                .addParams("score", number)
//                                .addParams("ranking", "1")
//                                .addParams("time_used", time2 + "")
//                                .addParams("answer_type", "1")
//                                .build().execute(new Callback() {
//                            @Override
//                            public Object parseNetworkResponse(Response response) throws IOException {
//                                return null;
//                            }
//
//                            @Override
//                            public void onError(Request request, Exception e) {
//
//                            }
//
//                            @Override
//                            public void onResponse(Object response) {
//
//                            }
//                        });
                    }
                });
        builder.create().show();
    }


/*    //交卷后的提示
    public void showAlertDialog() {
        final CustomDialog.Builder builder = new CustomDialog.Builder(this);
        String tNumber = (100 - trueNum - flaseNum) + "";
        builder.setMessage("还有" + tNumber + "题没做，确认交卷？");
        builder.setTitle("确认交卷");
        builder.setPositiveButton("查看未做", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                for (int i = 0; i < allQuestionC.size(); i++) {
                    if (allQuestionC.get(i).getA_state() == "1" && allQuestionC.get(i).getB_state() == "1"
                            && allQuestionC.get(i).getC_state() == "1" && allQuestionC.get(i).getD_state() == "1") {
                        int position = allQuestionC.get(i).getPosition();
                        readerViewPager.setCurrentItem(position);
                        break;
                    }
                }

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final Intent intent = new Intent(App.context, AchievementActivity.class);
                        long time2 = 2700000 - time;
                        String number = trueNum + "";
                        intent.putExtra("number", number);
                        intent.putExtra("time", time2);
                        start.cancel();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String str = formatter.format(curDate);
                        intent.putExtra("presenttimed", str);
                        panduanjiaojuan(intent, time2, number);
                        dialog.dismiss();

                    }
                });
        builder.create().show();
    }*/

    //交卷
    private void panduanjiaojuan(Intent intent, long time2, String number) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            int kemuyi_num = PrefUtils.getInt(App.context, "kemuyi_num", 0);
            kemuyi_num++;
            PrefUtils.putInt(App.context, "kemuyi_num", kemuyi_num);
            if (trueNum > okNumber) {
                OkHttpUtils.post().url(AppUrl.addlearningrecord)
                        .addParams("stu_user_id", PrefUtils.getParameter("user_id"))
                        .addParams("subject_backup", "科目一模拟考试" + number + "分")
                        .addParams("subject_name", "科目一")
                        .addParams("subject_id", "1")
                        .build().execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws IOException {
                                String string = response.body().string();
                                if (!TextUtils.isEmpty(string)) {
                                    Log.e("addlearningrecord", string);
                                    SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                    if (successDisCoverBackBean != null) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (200 == successDisCoverBackBean.getCode()) {
                                                    startActivity(intent);
                                                    finish();

                                                }
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

            } else {
                startActivity(intent);
                finish();

            }
        } else {
            showToastShort("请检查网络连接");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_moni_back:
                exitExam();
                break;
            case R.id.tv_title_give:
//                showAlertDialog();
                break;
            case R.id.bt_know_one:
                fl_xuzhi.setVisibility(View.GONE);
                break;

        }
    }

    private void exitExam() {
        new AlertDialog.Builder(this)
                .setTitle("正在考试中...")
                .setMessage("是否退出当前考试")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(App.context, "请您继续作答", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        start.cancel();
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        start.cancel();
        super.onDestroy();
    }
}
