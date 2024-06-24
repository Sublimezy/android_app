package com.xueyiche.zjyk.jiakao.examtext.kemud;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.base.module.DBManager;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.constants.AppUrl;

import com.xueyiche.zjyk.jiakao.homepage.adapters.TopicAdapter;
import com.xueyiche.zjyk.jiakao.homepage.bean.OptionBean;
import com.xueyiche.zjyk.jiakao.homepage.bean.Questiona;
import com.xueyiche.zjyk.jiakao.homepage.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.jiakao.homepage.db.KaoJiaZhaoDao;
import com.xueyiche.zjyk.jiakao.homepage.view.Couterdown;
import com.xueyiche.zjyk.jiakao.homepage.view.CustomDialog;
import com.xueyiche.zjyk.jiakao.homepage.view.ReaderViewPager;
import com.xueyiche.zjyk.jiakao.utils.JsonUtil;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;
import com.xueyiche.zjyk.jiakao.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Owner on 2016/10/12.
 */
//模拟考试页面
public class PracticeTestFour extends BaseActivity implements View.OnClickListener {
    private ReaderViewPager readerViewPager;
    private List<Questiona> allQuestionA, allQuestionB;
    private PagerAdapter adapter;
    private List<Questiona> allQuestionC = new ArrayList<>();
    public TextView mTV_title_time, mTV_title_give, mTV_title_mun;
    private CheckBox checkBox;
    private KaoJiaZhaoDao db;
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
    private List<Questiona> allPracticeDuoXuan;
    private TextView mTV_true_num;
    private TextView mTV_false_num;
    private Button btKnow;
    private RelativeLayout rlKnow;
    private long time;
    private boolean once = true;
    private FrameLayout fl_xuzhi;
    private CountDownTimer start;
    private String user_phone;
    private ImageView mIV_topic_jiantou;
    private int okNumber = 45;

    @Override
    protected int initContentView() {
        DBManager dbManager = new DBManager(this);
        dbManager.copyDBFile();
        db = new KaoJiaZhaoDao(App.context);
        allQuestionB = dbManager.findAllPractice("4", "1", "22");
        allQuestionA = dbManager.findAllPractice("4", "2", "23");
        allPracticeDuoXuan = dbManager.findAllPractice("4", "3", "5");
        allQuestionC.addAll(allQuestionA);
        allQuestionC.addAll(allQuestionB);
        allQuestionC.addAll(allPracticeDuoXuan);

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
        fl_xuzhi = (FrameLayout) view.findViewById(R.id.fl_xuzhi);
        mTV_title_mun.setText("1/50");
        btKnow = (Button) view.findViewById(R.id.bt_know_one);
        rlKnow = (RelativeLayout) view.findViewById(R.id.rl_know_one);
        btKnow.setOnClickListener(this);
        mTV_title_give.setOnClickListener(this);
        mLL_questionback.setOnClickListener(this);
        initSlidingUoPanel();
        initViewPager();
        initList();
        //错题和对题的数量
        mTV_true_num = (TextView) view.findViewById(R.id.tv_true_num);
        mTV_false_num = (TextView) view.findViewById(R.id.tv_false_num);
        mTV_false_num.setText(flaseNum + "");
        mTV_true_num.setText(trueNum + "");
        if (topicAdapter != null) {
            topicAdapter.setDataNum(allQuestionC.size());
        }
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
        int height = getWindowManager().getDefaultDisplay().getHeight();
        LinearLayout dragView = (LinearLayout) findViewById(R.id.dragView);
        SlidingUpPanelLayout.LayoutParams params = new SlidingUpPanelLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (height * 0.8f));
        dragView.setLayoutParams(params);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
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
    protected void initListener() {
        readerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动页面时改变标号
                curPosition2 = position;
                topicAdapter.notifyCurPosition(curPosition2);
                topicAdapter.notifyPrePosition(prePosition2);
                prePosition2 = curPosition2;
                final Questiona questiond = allQuestionC.get(position);
                final String answer_1 = questiond.getAnswer_1();
                final String answer_2 = questiond.getAnswer_2();
                final String answer_3 = questiond.getAnswer_3();
                final String answer_4 = questiond.getAnswer_4();
                final String question = questiond.getQuestion();
                final String question_type = questiond.getQuestion_type();
                final String explain = questiond.getExplain();
                final String true_answer = questiond.getTrue_answer();
                final String img = questiond.getImg();
                final String qid = questiond.getQid();
                final String video = questiond.getVideo();
                if (questiond.getA_state() == "1" && questiond.getB_state() == "1" && questiond.getC_state() == "1" && questiond.getD_state() == "1") {
                    questiond.setPosition(position);
                }
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain, question_type, img, video, "shoucangd");
                            checkBox.setText("已收藏");
                        } else {
                            db.delete(qid);
                            checkBox.setText("收藏");
                        }
                    }
                });
                //判断是否存在
                boolean notes = db.isInShouCangD(qid);
                if (notes == false) {
                    checkBox.setChecked(false);
                    checkBox.setText("收藏");
                } else {
                    checkBox.setChecked(true);
                    checkBox.setText("已收藏");
                }
                timu_qid = position + 1;
                mTV_title_mun.setText(timu_qid + "/" + allQuestionC.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initData() {
        //获取系统时间
        start = new Couterdown(1800000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTV_title_time.setText(toClock(millisUntilFinished));
                time = millisUntilFinished;

            }

            @Override
            public String toClock(long millis) {
                return super.toClock(millis);
            }

            @Override
            public void onFinish() {
                final Intent intent = new Intent(App.context, AchievementFourActivity.class);
                String number = trueNum * 2 + "";
                intent.putExtra("number", number);
                long time2 = 1800000 - time;
                intent.putExtra("time", time2);
                //获取系统时间
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                intent.putExtra("presenttime", str);
                panduanjiaojuan(intent, time2, number);
                Toast.makeText(App.context, "考试时间结束", Toast.LENGTH_SHORT).show();
            }
        }.start();

    }

    private void initViewPager() {
        adapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object obj) {
                container.removeView((View) obj);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                final Questiona questiond = allQuestionC.get(position);
                final String answer_1 = questiond.getAnswer_1();
                final String answer_2 = questiond.getAnswer_2();
                final String answer_3 = questiond.getAnswer_3();
                final String answer_4 = questiond.getAnswer_4();
                final String question = questiond.getQuestion();
                final String question_type = questiond.getQuestion_type();
                final String explain = questiond.getExplain();
                final String true_answer = questiond.getTrue_answer();
                final String img = questiond.getImg();
                final String qid = questiond.getQid();
                final String video = questiond.getVideo();
                String a_state = questiond.getA_state();
                String b_state = questiond.getB_state();
                String c_state = questiond.getC_state();
                String d_state = questiond.getD_state();
                String explain_state = questiond.getExplain_state();
                //多选答案的实体类
                final OptionBean optionBean = new OptionBean();
                optionBean.setA("0");
                optionBean.setB("0");
                optionBean.setC("0");
                optionBean.setD("0");
                View view = View.inflate(container.getContext(), R.layout.subjectd_question_item, null);
                TextView mTV_qusetion = (TextView) view.findViewById(R.id.tv_question);
                //多选答案
                LinearLayout mLL_duoxuan_answer = (LinearLayout) view.findViewById(R.id.ll_duoxuan_answer);
                final CheckBox mCB_a = (CheckBox) view.findViewById(R.id.cb_a);
                final CheckBox mCB_b = (CheckBox) view.findViewById(R.id.cb_b);
                final CheckBox mCB_c = (CheckBox) view.findViewById(R.id.cb_c);
                final CheckBox mCB_d = (CheckBox) view.findViewById(R.id.cb_d);
                //单选答案
                LinearLayout mLL_answer = (LinearLayout) view.findViewById(R.id.ll_answer);
                //确认答案
                final Button mBT_affirm_answer = (Button) view.findViewById(R.id.bt_affirm_answer);
                //问题的视频与图片
                final VideoView mVV_kemu4 = (VideoView) view.findViewById(R.id.vv_kemu4);
                if (video != null && !TextUtils.isEmpty(video)) {
                    mVV_kemu4.setVisibility(View.VISIBLE);
                    mVV_kemu4.setVideoPath("http://jiakao.xueyiche.net/" + video);
                    mVV_kemu4.start();
                    mVV_kemu4.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            mp.setLooping(true);

                        }
                    });

                    mVV_kemu4
                            .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mVV_kemu4.setVideoPath("http://jiakao.xueyiche.net/" + video);
                                    mVV_kemu4.start();

                                }
                            });
                }
                ImageView mIV_kemu4 = (ImageView) view.findViewById(R.id.iv_kemu4);
                if (!TextUtils.isEmpty(img)) {
                    Picasso.with(App.context).load("http://jiakao.xueyiche.net/" + img)
                            .placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(mIV_kemu4);
                } else {
                    mIV_kemu4.setVisibility(View.GONE);
                }
                ImageView xianc = (ImageView) view.findViewById(R.id.xian_c);
                ImageView xiand = (ImageView) view.findViewById(R.id.xian_d);
                //题目类型
                ImageView mIV_questiontype = (ImageView) view.findViewById(R.id.iv_questiontype);
                //答案的文本内容
                TextView mTV_ansa = (TextView) view.findViewById(R.id.tv_ansa);
                TextView mTV_ansb = (TextView) view.findViewById(R.id.tv_ansb);
                TextView mTV_ansc = (TextView) view.findViewById(R.id.tv_ansc);
                TextView mTV_ansd = (TextView) view.findViewById(R.id.tv_ansd);
                //解析文本内容
                final LinearLayout mLL_explan = (LinearLayout) view.findViewById(R.id.ll_explan);
                TextView mTV_analysis = (TextView) view.findViewById(R.id.tv_analysis);
                //四个答案选项
                final LinearLayout ll_answer = (LinearLayout) view.findViewById(R.id.ll_answer);
                //每一条选项可点击
                final LinearLayout mLL_a = (LinearLayout) view.findViewById(R.id.ll_a);
                final LinearLayout mLL_b = (LinearLayout) view.findViewById(R.id.ll_b);
                final LinearLayout mLL_c = (LinearLayout) view.findViewById(R.id.ll_c);
                final LinearLayout mLL_d = (LinearLayout) view.findViewById(R.id.ll_d);
                //选项按钮
                final ImageView mIV_a = (ImageView) view.findViewById(R.id.iv_a);
                final ImageView mIV_b = (ImageView) view.findViewById(R.id.iv_b);
                final ImageView mIV_c = (ImageView) view.findViewById(R.id.iv_c);
                final ImageView mIV_d = (ImageView) view.findViewById(R.id.iv_d);

                int timu_qid = position + 1;
                mTV_qusetion.setText(timu_qid + "/" + allQuestionC.size());
                if ("2".equals(question_type)) {
                    mIV_questiontype.setImageResource(R.mipmap.panduan);
                    xianc.setVisibility(View.GONE);
                    xiand.setVisibility(View.GONE);
                    mLL_c.setVisibility(View.GONE);
                    mLL_d.setVisibility(View.GONE);
                }
                if (a_state != "1" || b_state != "1" || c_state != "1" || d_state != "1") {
                    mBT_affirm_answer.setVisibility(View.GONE);
                    if ("1".equals(question_type)) {
                        mIV_questiontype.setImageResource(R.mipmap.danxuan);
                    } else if ("3".equals(question_type)) {
                        mIV_questiontype.setImageResource(R.mipmap.duoxuan_);
                    }
                    if (a_state.equals("2")) {
                        mIV_a.setImageResource(R.mipmap.zhengque);
                        mCB_a.setButtonDrawable(R.mipmap.zhengque);
                    } else if (a_state.equals("3")) {
                        mIV_a.setImageResource(R.mipmap.cuowu);
                        mCB_a.setButtonDrawable(R.mipmap.cuowu);
                    } else if (a_state.equals("4")) {
                        mIV_a.setImageResource(R.mipmap.a_true);
                        mCB_a.setButtonDrawable(R.mipmap.a_true);
                    }
                    if (b_state.equals("2")) {
                        mIV_b.setImageResource(R.mipmap.zhengque);
                        mCB_b.setButtonDrawable(R.mipmap.zhengque);
                    } else if (b_state.equals("3")) {
                        mIV_b.setImageResource(R.mipmap.cuowu);
                        mCB_b.setButtonDrawable(R.mipmap.cuowu);
                    } else if (b_state.equals("4")) {
                        mIV_b.setImageResource(R.mipmap.b_true);
                        mCB_b.setButtonDrawable(R.mipmap.b_true);
                    }
                    if (c_state.equals("2")) {
                        mIV_c.setImageResource(R.mipmap.zhengque);
                        mCB_c.setButtonDrawable(R.mipmap.zhengque);
                    } else if (c_state.equals("3")) {
                        mIV_c.setImageResource(R.mipmap.cuowu);
                        mCB_c.setButtonDrawable(R.mipmap.cuowu);
                    } else if (c_state.equals("4")) {
                        mIV_c.setImageResource(R.mipmap.c_true);
                        mCB_c.setButtonDrawable(R.mipmap.c_true);
                    }
                    if (d_state.equals("2")) {
                        mIV_d.setImageResource(R.mipmap.zhengque);
                        mCB_d.setButtonDrawable(R.mipmap.zhengque);
                    } else if (d_state.equals("3")) {
                        mIV_d.setImageResource(R.mipmap.cuowu);
                        mCB_d.setButtonDrawable(R.mipmap.cuowu);
                    } else if (d_state.equals("4")) {
                        mIV_d.setImageResource(R.mipmap.d_true);
                        mCB_d.setButtonDrawable(R.mipmap.d_true);
                    }
                    if (explain_state.equals("2")) {
                        mLL_explan.setVisibility(View.VISIBLE);
                    }
                } else {
                    if ("3".equals(question_type)) {
                        //题目是多选
                        mIV_questiontype.setImageResource(R.mipmap.duoxuan_);
                        mCB_a.setText("   " + answer_1);
                        mCB_b.setText("   " + answer_2);
                        mCB_c.setText("   " + answer_3);
                        mCB_d.setText("   " + answer_4);
                        mLL_duoxuan_answer.setVisibility(View.VISIBLE);
                        mLL_answer.setVisibility(View.GONE);
                        mCB_a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if (isChecked) {
                                    mCB_a.setButtonDrawable(R.mipmap.aselect);
                                    optionBean.setA("1");

                                } else {
                                    mCB_a.setButtonDrawable(R.mipmap.a);
                                    optionBean.setA("0");
                                }
                            }
                        });

                        mCB_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    mCB_b.setButtonDrawable(R.mipmap.bselect);
                                    optionBean.setB("2");
                                } else {
                                    mCB_b.setButtonDrawable(R.mipmap.b);
                                    optionBean.setB("0");
                                }
                            }
                        });

                        mCB_c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    mCB_c.setButtonDrawable(R.mipmap.cselect);
                                    optionBean.setC("3");
                                } else {
                                    mCB_c.setButtonDrawable(R.mipmap.c);
                                    optionBean.setC("0");
                                }
                            }
                        });

                        mCB_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    mCB_d.setButtonDrawable(R.mipmap.dselect);
                                    optionBean.setD("4");
                                } else {
                                    mCB_d.setButtonDrawable(R.mipmap.d);
                                    optionBean.setD("0");
                                }
                            }
                        });
                    } else {
                        //题目是单选
                        mLL_a.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!"1".equals(true_answer)) {
                                    mIV_a.setImageResource(R.mipmap.cuowu);
                                    questiond.setA_state("3");
                                    db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain
                                            , question_type, img, video, "cuotia");
                                    flaseNum = flaseNum + 1;
                                    mTV_false_num.setText(flaseNum + "");
                                } else {
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                    questiond.setA_state("2");
                                    trueNum = trueNum + 1;
                                    mTV_true_num.setText(trueNum + "");
                                }
                                int currentItem = readerViewPager.getCurrentItem();
                                readerViewPager.setCurrentItem(currentItem + 1);
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);
                                mLL_d.setClickable(false);
                            }
                        });
                        mLL_b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!"2".equals(true_answer)) {
                                    mIV_b.setImageResource(R.mipmap.cuowu);
                                    questiond.setB_state("3");
                                    db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain
                                            , question_type, img, video, "cuotia");
                                    flaseNum = flaseNum + 1;
                                    mTV_false_num.setText(flaseNum + "");
                                } else {
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                    questiond.setB_state("2");
                                    trueNum = trueNum + 1;
                                    mTV_true_num.setText(trueNum + "");
                                }
                                int currentItem = readerViewPager.getCurrentItem();
                                readerViewPager.setCurrentItem(currentItem + 1);
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);
                                mLL_d.setClickable(false);
                            }
                        });
                        mLL_c.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!"3".equals(true_answer)) {
                                    mIV_c.setImageResource(R.mipmap.cuowu);
                                    questiond.setC_state("3");
                                    db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain
                                            , question_type, img, video, "cuotia");
                                    flaseNum = flaseNum + 1;
                                    mTV_false_num.setText(flaseNum + "");
                                } else {
                                    mIV_c.setImageResource(R.mipmap.zhengque);
                                    questiond.setC_state("2");
                                    trueNum = trueNum + 1;
                                    mTV_true_num.setText(trueNum + "");
                                }
                                int currentItem = readerViewPager.getCurrentItem();
                                readerViewPager.setCurrentItem(currentItem + 1);
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);
                                mLL_d.setClickable(false);
                            }
                        });
                        mLL_d.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!"4".equals(true_answer)) {
                                    mIV_d.setImageResource(R.mipmap.cuowu);
                                    db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain
                                            , question_type, img, video, "cuotia");
                                    questiond.setD_state("3");
                                    flaseNum = flaseNum + 1;
                                    mTV_false_num.setText(flaseNum + "");
                                } else {
                                    mIV_d.setImageResource(R.mipmap.zhengque);
                                    questiond.setD_state("2");
                                    trueNum = trueNum + 1;
                                    mTV_true_num.setText(trueNum + "");
                                }
                                int currentItem = readerViewPager.getCurrentItem();
                                readerViewPager.setCurrentItem(currentItem + 1);
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);
                                mLL_d.setClickable(false);
                            }

                        });
                    }
                    //点击查看多选题的答案
                    mBT_affirm_answer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean a = true;
                            boolean b = true;
                            boolean c = true;
                            boolean d = true;
                            if (true_answer.contains(optionBean.getA())) {
                                mCB_a.setButtonDrawable(R.mipmap.zhengque);
                                questiond.setA_state("2");
                            } else if (true_answer.contains("1")) {
                                mCB_a.setButtonDrawable(R.mipmap.a_true);
                                questiond.setA_state("4");
                                a = false;
                            } else if (optionBean.getA() != "0") {
                                mCB_a.setButtonDrawable(R.mipmap.cuowu);
                                questiond.setA_state("3");
                                a = false;
                            }

                            if (true_answer.contains(optionBean.getB())) {
                                mCB_b.setButtonDrawable(R.mipmap.zhengque);
                                questiond.setB_state("2");
                            } else if (true_answer.contains("2")) {
                                mCB_b.setButtonDrawable(R.mipmap.b_true);
                                questiond.setB_state("4");
                                b = false;
                            } else if (optionBean.getB() != "0") {
                                mCB_b.setButtonDrawable(R.mipmap.cuowu);
                                questiond.setB_state("3");
                                b = false;
                            }

                            if (true_answer.contains(optionBean.getC())) {
                                mCB_c.setButtonDrawable(R.mipmap.zhengque);
                                questiond.setC_state("2");
                            } else if (true_answer.contains("3")) {
                                mCB_c.setButtonDrawable(R.mipmap.c_true);
                                questiond.setC_state("4");
                                c = false;
                            } else if (optionBean.getC() != "0") {
                                mCB_c.setButtonDrawable(R.mipmap.cuowu);
                                questiond.setC_state("3");
                                c = false;
                            }


                            if (true_answer.contains(optionBean.getD())) {
                                mCB_d.setButtonDrawable(R.mipmap.zhengque);
                                questiond.setD_state("2");
                            } else if (true_answer.contains("4")) {
                                mCB_d.setButtonDrawable(R.mipmap.d_true);
                                questiond.setD_state("4");
                                d = false;
                            } else if (optionBean.getD() != "0") {
                                mCB_d.setButtonDrawable(R.mipmap.cuowu);
                                questiond.setD_state("3");
                                d = false;
                            }

                            if (a && b && c && d) {
                                trueNum = trueNum + 1;
                                mTV_true_num.setText(trueNum + "");
                            } else {
                                flaseNum = flaseNum + 1;
                                mTV_false_num.setText(flaseNum + "");
                            }
                            int currentItem = readerViewPager.getCurrentItem();
                            readerViewPager.setCurrentItem(currentItem + 1);
                            mCB_a.setClickable(false);
                            mCB_b.setClickable(false);
                            mCB_a.setClickable(false);
                            mCB_a.setClickable(false);
                            mBT_affirm_answer.setClickable(false);
                        }
                    });
                }


                mTV_qusetion.setText("       " + timu_qid + "." + question);
                mTV_ansa.setText(answer_1 == null ? "正确" : answer_1);
                mTV_ansb.setText(answer_2 == null ? "错误" : answer_2);
                if (answer_3 == null) {
                    mLL_c.setVisibility(View.INVISIBLE);
                } else {
                    mTV_ansc.setText(answer_3);
                }
                if (answer_4 == null) {
                    mLL_d.setVisibility(View.INVISIBLE);
                } else {
                    mTV_ansd.setText(answer_4);
                }
                mTV_analysis.setText("    " + explain);
                //错题到6题的提示
                if (flaseNum == 6 & once) {
                    showAlertDialogD();
                    once = false;
                }
                //点击事件
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return allQuestionC.size();
            }
        };
        readerViewPager.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        exitExam();

    }

    //交卷
    private void panduanjiaojuan(Intent intent, long time2, String number) {
//        阿达阿达
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            int kemuyi_num = PrefUtils.getInt(App.context, "kemusi_num", 0);
            kemuyi_num++;
            PrefUtils.putInt(App.context, "kemusi_num", kemuyi_num);
            if (trueNum > okNumber) {
                OkHttpUtils.post().url(AppUrl.addlearningrecord)
                        .addParams("stu_user_id", PrefUtils.getParameter("user_id"))
                        .addParams("subject_backup", "科目四模拟考试" + number + "分")
                        .addParams("subject_name", "科目四")
                        .addParams("subject_id", "4")
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (200 == successDisCoverBackBean.getCode()) {
                                            startActivity(intent);
                                            finish();
//                                            OkHttpUtils.post().url(AppUrl.DT).addParams("user_phone", user_phone)
//                                                    .addParams("score", number)
//                                                    .addParams("ranking", "1")
//                                                    .addParams("time_used", time2 + "")
//                                                    .addParams("answer_type", "4")
//                                                    .build().execute(new Callback() {
//                                                @Override
//                                                public Object parseNetworkResponse(Response response) throws IOException {
//                                                    return null;
//                                                }
//
//                                                @Override
//                                                public void onError(Request request, Exception e) {
//
//                                                }
//
//                                                @Override
//                                                public void onResponse(Object response) {
//
//                                                }
//                                            });

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
//                OkHttpUtils.post().url(AppUrl.DT).addParams("user_phone", user_phone)
//                        .addParams("score", number)
//                        .addParams("ranking", "1")
//                        .addParams("time_used", time2 + "")
//                        .addParams("answer_type", "4")
//                        .build().execute(new Callback() {
//                    @Override
//                    public Object parseNetworkResponse(Response response) throws IOException {
//                        return null;
//                    }
//
//                    @Override
//                    public void onError(Request request, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Object response) {
//
//                    }
//                });

            }
        } else {
            showToastShort("请检查网络连接");
        }

    }

    //错题到6的提示
    public void showAlertDialogD() {
        final CustomDialog.Builder builder = new CustomDialog.Builder(this);
        String tNumber = flaseNum + "";
        builder.setMessage("错题已到" + tNumber + "题，是否交卷？");
        builder.setTitle("错了好多哟！");
        builder.setPositiveButton("继续答题", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(App.context, AchievementFourActivity.class);
                        String number = trueNum * 2 + "";
                        intent.putExtra("number", number);
                        long time2 = 1800000 - time;
                        intent.putExtra("time", time2);
                        //获取系统时间
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String str = formatter.format(curDate);
                        intent.putExtra("presenttime", str);
                        startActivity(intent);
                        finish();
//                        OkHttpUtils.post().url(AppUrl.DT).addParams("user_phone", user_phone)
//                                .addParams("score", number)
//                                .addParams("ranking", "1")
//                                .addParams("time_used", time2 + "")
//                                .addParams("answer_type", "4")
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

    //交卷后的提示
    public void showAlertDialog() {
        final CustomDialog.Builder builder = new CustomDialog.Builder(this);
        String tNumber = (50 - trueNum - flaseNum) + "";
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
                        start.cancel();
                        final Intent intent = new Intent(App.context, AchievementFourActivity.class);
                        String number = trueNum * 2 + "";
                        intent.putExtra("number", number);
                        long time2 = 1800000 - time;
                        intent.putExtra("time", time2);
                        //获取系统时间
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String str = formatter.format(curDate);
                        intent.putExtra("presenttime", str);
                        panduanjiaojuan(intent, time2, number);
                    }
                });

        builder.create().show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_moni_back:
                exitExam();
                break;
            case R.id.tv_title_give:
                showAlertDialog();
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
                        //  score=num-content;
//                       SharedPreferences.Editor editor=getSharedPreferences("score",MODE_PRIVATE).edit();
//                       editor.putString("score", String.valueOf(score));
//                       editor.commit();
                        //  receiveScore(score);
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
