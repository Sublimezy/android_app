package com.xueyiche.zjyk.xueyiche.examtext;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.module.DBManager;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.bean.OptionBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.Questiona;
import com.xueyiche.zjyk.xueyiche.homepage.view.Couterdown;
import com.xueyiche.zjyk.xueyiche.main.view.NoScrollViewPager;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Owner on 2019/2/26.
 */
public class AnswerWithCashQuestion extends BaseActivity implements View.OnClickListener {
    private NoScrollViewPager vp_wincash;
    private PagerAdapter adapter;
    private List<Questiona> allQuestion = new ArrayList<>();
    public TextView  mTV_title_mun;
    private int trueNum = 0;
    private int flaseNum = 0;
    private TextView tv_title_time;
    private long time;
    private CountDownTimer start;
    private ImageView iv_back;
    private String user_id;
    private long used_time;


    @Override
    protected int initContentView() {
        DBManager dbManager = new DBManager(this);
        dbManager.copyDBFile();
        allQuestion = dbManager.answerWithCashQuestion();
        return R.layout.answer_cash_qusetion_activity;
    }

    @Override
    protected void initView() {
        vp_wincash = (NoScrollViewPager) view.findViewById(R.id.vp_wincash);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        tv_title_time = (TextView) view.findViewById(R.id.tv_title_time);
        iv_back.setOnClickListener(this);
        initViewPager();

        user_id = PrefUtils.getString(this, "user_id", "");
    }



    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //获取系统时间
        start = new Couterdown(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_title_time.setText(toClock(millisUntilFinished));
                time = millisUntilFinished;
                used_time = 300000 - time;

            }

            @Override
            public String toClock(long millis) {
                return super.toClock(millis);
            }

            @Override
            public void onFinish() {
                //获取系统时间
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                putVoid(trueNum+"", used_time);

            }
        }.start();

    }

    //上传给后台的用时   分数
    private void putVoid( String number, long used_time) {
        OkHttpUtils.post().
                url(AppUrl.WinCash_Result).addParams("user_id", user_id)
                .addParams("score", number)
                .addParams("ranking", "1")
                .addParams("time_used", used_time + "")
                .addParams("answer_type", "1")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                return null;
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) {
                Intent intent = new Intent(AnswerWithCashQuestion.this,AnswerwithcashFinish.class);
                intent.putExtra("fen",trueNum);
                startActivity(intent);
                finish();
            }
        });
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
                final Questiona questiond = allQuestion.get(position);
                final String answer_1 = questiond.getAnswer_1();
                final String answer_2 = questiond.getAnswer_2();
                final String answer_3 = questiond.getAnswer_3();
                final String answer_4 = questiond.getAnswer_4();
                final String question = questiond.getQuestion();
                final String question_type = questiond.getQuestion_type();
                final String true_answer = questiond.getTrue_answer();
                final String img = questiond.getImg();
                final String video = questiond.getVideo();
                String a_state = questiond.getA_state();
                String b_state = questiond.getB_state();
                String c_state = questiond.getC_state();
                String d_state = questiond.getD_state();
                //多选答案的实体类
                final OptionBean optionBean = new OptionBean();
                optionBean.setA("0");
                optionBean.setB("0");
                optionBean.setC("0");
                optionBean.setD("0");
                View view = View.inflate(container.getContext(), R.layout.wincash_question_item, null);
                TextView mTV_qusetion = (TextView) view.findViewById(R.id.tv_question);
                //错题和对题的数量
                final TextView tv_true_num = (TextView) view.findViewById(R.id.tv_true_num);
                final TextView tv_false_num = (TextView) view.findViewById(R.id.tv_false_num);
                tv_false_num.setText(flaseNum + "");
                tv_true_num.setText(trueNum + "");
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
                                    flaseNum = flaseNum + 1;
                                    tv_false_num.setText(flaseNum + "");
                                } else {
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                    questiond.setA_state("2");
                                    trueNum = trueNum + 1;
                                    tv_true_num.setText(trueNum + "");
                                }
                                int currentItem = vp_wincash.getCurrentItem();
                                vp_wincash.setCurrentItem(currentItem + 1);
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);
                                mLL_d.setClickable(false);
                                finishTest(position);
                            }
                        });
                        mLL_b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!"2".equals(true_answer)) {
                                    mIV_b.setImageResource(R.mipmap.cuowu);
                                    questiond.setB_state("3");
                                    flaseNum = flaseNum + 1;
                                    tv_false_num.setText(flaseNum + "");
                                } else {
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                    questiond.setB_state("2");
                                    trueNum = trueNum + 1;
                                    tv_true_num.setText(trueNum + "");
                                }
                                int currentItem = vp_wincash.getCurrentItem();
                                vp_wincash.setCurrentItem(currentItem + 1);
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);
                                mLL_d.setClickable(false);
                                finishTest(position);
                            }
                        });
                        mLL_c.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!"3".equals(true_answer)) {
                                    mIV_c.setImageResource(R.mipmap.cuowu);
                                    questiond.setC_state("3");
                                    flaseNum = flaseNum + 1;
                                    tv_false_num.setText(flaseNum + "");
                                } else {
                                    mIV_c.setImageResource(R.mipmap.zhengque);
                                    questiond.setC_state("2");
                                    trueNum = trueNum + 1;
                                    tv_true_num.setText(trueNum + "");
                                }
                                int currentItem = vp_wincash.getCurrentItem();
                                vp_wincash.setCurrentItem(currentItem + 1);
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);
                                mLL_d.setClickable(false);
                                finishTest(position);
                            }
                        });
                        mLL_d.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!"4".equals(true_answer)) {
                                    mIV_d.setImageResource(R.mipmap.cuowu);
                                    questiond.setD_state("3");
                                    flaseNum = flaseNum + 1;
                                    tv_false_num.setText(flaseNum + "");
                                } else {
                                    mIV_d.setImageResource(R.mipmap.zhengque);
                                    questiond.setD_state("2");
                                    trueNum = trueNum + 1;
                                    tv_true_num.setText(trueNum + "");
                                }
                                int currentItem = vp_wincash.getCurrentItem();
                                vp_wincash.setCurrentItem(currentItem + 1);
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);
                                mLL_d.setClickable(false);
                                finishTest(position);
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
                                tv_true_num.setText(trueNum + "");
                            } else {
                                flaseNum = flaseNum + 1;
                                tv_false_num.setText(flaseNum + "");
                            }
                            int currentItem = vp_wincash.getCurrentItem();
                            vp_wincash.setCurrentItem(currentItem + 1);
                            mCB_a.setClickable(false);
                            mCB_b.setClickable(false);
                            mCB_a.setClickable(false);
                            mCB_a.setClickable(false);
                            mBT_affirm_answer.setClickable(false);
                            finishTest(position);
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
                //点击事件
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return allQuestion.size();
            }
        };
        vp_wincash.setAdapter(adapter);

    }

    private void finishTest(int position) {
        if (99==position) {
            putVoid(trueNum+"", used_time);
        }
    }


    @Override
    public void onBackPressed() {
        exitExam();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                exitExam();
                break;
        }
    }

    private void exitExam() {
        new AlertDialog.Builder(this)
                .setTitle("正在答题中...")
                .setMessage("是否退出当前答题")
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