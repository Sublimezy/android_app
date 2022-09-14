package com.xueyiche.zjyk.xueyiche.examtext.kemud;

import android.media.MediaPlayer;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.homepage.bean.OptionBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.Questiona;
import com.xueyiche.zjyk.xueyiche.homepage.db.KaoJiaZhaoDao;
import com.xueyiche.zjyk.xueyiche.homepage.view.ReaderViewPager;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.List;

/**
 * Created by Owner on 2016/10/9.
 */
public class CuoTiD extends BaseActivity {
    private ReaderViewPager readerViewPager;
    private List<Questiona> allCollectQuestion;
    private PagerAdapter adapter;
    private KaoJiaZhaoDao db;
    private TextView mTV_title_mun;
    private LinearLayout mLL_questionback;

    @Override
    protected int initContentView() {
        db = new KaoJiaZhaoDao(App.context);
        allCollectQuestion = db.findAllCuoTiD();
        return R.layout.home_exam_subjecta_cuoti;
    }

    @Override
    protected void initView() {
        readerViewPager = (ReaderViewPager) view.findViewById(R.id.vp_subjectA);
        mTV_title_mun = (TextView) view.findViewById(R.id.tv_title_num);
        mLL_questionback = (LinearLayout) view.findViewById(R.id.exam_question_collect).findViewById(R.id.ll_question_back);
        mTV_title_mun.setText(1+"/"+allCollectQuestion.size());
        mLL_questionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        readerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                final Questiona questiond = allCollectQuestion.get(position);
                //正确答案


                int timu_qid = position + 1;
                mTV_title_mun.setText(timu_qid + "/" + allCollectQuestion.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        initViewPager();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

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

            private int mChildCount = 0;

            @Override
            public void notifyDataSetChanged() {
                mChildCount = getCount();
                super.notifyDataSetChanged();
            }

            @Override
            public int getItemPosition(Object object) {
                if (mChildCount > 0) {
                    mChildCount--;
                    return POSITION_NONE;
                }
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {

                final Questiona questiond = allCollectQuestion.get(position);
                //正确答案
                final String trueanswer = questiond.getTrue_answer();
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
                if (video != null&& !TextUtils.isEmpty(video)) {
                    mVV_kemu4.setVisibility(View.VISIBLE);
                    mVV_kemu4.setVideoPath("http://jiakao.xueyiche.net/"+video);
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
                                    mVV_kemu4.setVideoPath("http://jiakao.xueyiche.net/"+video);
                                    mVV_kemu4.start();

                                }
                            });
                }
                ImageView mIV_kemu4 = (ImageView) view.findViewById(R.id.iv_kemu4);
                if (!TextUtils.isEmpty(img)) {
                    Picasso.with(App.context).load("http://jiakao.xueyiche.net/" + img)
                            .placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(mIV_kemu4);
                }else {
                    mIV_kemu4.setVisibility(View.GONE);
                }
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
                ImageView xianc = (ImageView) view.findViewById(R.id.xian_c);
                ImageView xiand = (ImageView) view.findViewById(R.id.xian_d);
                int timu_qid = position + 1;
                mTV_qusetion.setText(timu_qid + "/" + allCollectQuestion.size());
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
                    }else if ("3".equals(question_type)){
                        mIV_questiontype.setImageResource(R.mipmap.duoxuan_);
                    }
                    if (a_state.equals("2")) {
                        mIV_a.setImageResource(R.mipmap.zhengque);
                        mCB_a.setButtonDrawable(R.mipmap.zhengque);
                    }else if (a_state.equals("3")){
                        mIV_a.setImageResource(R.mipmap.cuowu);
                        mCB_a.setButtonDrawable(R.mipmap.cuowu);
                    }else if (a_state.equals("4")){
                        mIV_a.setImageResource(R.mipmap.a_true);
                        mCB_a.setButtonDrawable(R.mipmap.a_true);
                    }
                    if (b_state.equals("2")) {
                        mIV_b.setImageResource(R.mipmap.zhengque);
                        mCB_b.setButtonDrawable(R.mipmap.zhengque);
                    }else if (b_state.equals("3")){
                        mIV_b.setImageResource(R.mipmap.cuowu);
                        mCB_b.setButtonDrawable(R.mipmap.cuowu);
                    }else if (b_state.equals("4")){
                        mIV_b.setImageResource(R.mipmap.b_true);
                        mCB_b.setButtonDrawable(R.mipmap.b_true);
                    }
                    if (c_state.equals("2")) {
                        mIV_c.setImageResource(R.mipmap.zhengque);
                        mCB_c.setButtonDrawable(R.mipmap.zhengque);
                    }else if (c_state.equals("3")){
                        mIV_c.setImageResource(R.mipmap.cuowu);
                        mCB_c.setButtonDrawable(R.mipmap.cuowu);
                    }else if (c_state.equals("4")){
                        mIV_c.setImageResource(R.mipmap.c_true);
                        mCB_c.setButtonDrawable(R.mipmap.c_true);
                    }
                    if (d_state.equals("2")) {
                        mIV_d.setImageResource(R.mipmap.zhengque);
                        mCB_d.setButtonDrawable(R.mipmap.zhengque);
                    }else if (d_state.equals("3")){
                        mIV_d.setImageResource(R.mipmap.cuowu);
                        mCB_d.setButtonDrawable(R.mipmap.cuowu);
                    }else if (d_state.equals("4")){
                        mIV_d.setImageResource(R.mipmap.d_true);
                        mCB_d.setButtonDrawable(R.mipmap.d_true);
                    }
                    if (explain_state.equals("2")) {
                        mLL_explan.setVisibility(View.VISIBLE);
                    }
                }else {
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
                                    if ("2".equals(trueanswer)) {
                                        mIV_b.setImageResource(R.mipmap.zhengque);
                                        mIV_a.setImageResource(R.mipmap.cuowu);
                                        questiond.setB_state("2");
                                    } else if ("3".equals(trueanswer)) {
                                        mIV_c.setImageResource(R.mipmap.zhengque);
                                        mIV_a.setImageResource(R.mipmap.cuowu);
                                        questiond.setC_state("2");
                                    } else if ("4".equals(trueanswer)) {
                                        mIV_d.setImageResource(R.mipmap.zhengque);
                                        mIV_a.setImageResource(R.mipmap.cuowu);
                                        questiond.setD_state("2");
                                    }
                                    questiond.setA_state("3");
                                } else {
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                    int currentItem = readerViewPager.getCurrentItem();
                                    readerViewPager.setCurrentItem(currentItem + 1);
                                    questiond.setA_state("2");
                                    db.delete(qid);
                                    allCollectQuestion = db.findAllCuoTiA();
                                    adapter.notifyDataSetChanged();
                                    if (allCollectQuestion.size() == 0) {
                                        finish();
                                    }
                                }
                                questiond.setExplain_state("2");
                                mLL_explan.setVisibility(View.VISIBLE);
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
                                    if ("1".equals(trueanswer)) {
                                        mIV_a.setImageResource(R.mipmap.zhengque);
                                        mIV_b.setImageResource(R.mipmap.cuowu);
                                        questiond.setA_state("2");
                                    } else if ("3".equals(trueanswer)) {
                                        mIV_c.setImageResource(R.mipmap.zhengque);
                                        mIV_b.setImageResource(R.mipmap.cuowu);
                                        questiond.setC_state("2");
                                    } else if ("4".equals(trueanswer)) {
                                        mIV_d.setImageResource(R.mipmap.zhengque);
                                        mIV_b.setImageResource(R.mipmap.cuowu);
                                        questiond.setD_state("2");
                                    }
                                    questiond.setB_state("3");
                                } else {
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                    int currentItem = readerViewPager.getCurrentItem();
                                    readerViewPager.setCurrentItem(currentItem + 1);
                                    questiond.setB_state("2");
                                    db.delete(qid);
                                    allCollectQuestion = db.findAllCuoTiA();
                                    adapter.notifyDataSetChanged();
                                    if (allCollectQuestion.size() == 0) {
                                        finish();
                                    }
                                }
                                mLL_explan.setVisibility(View.VISIBLE);
                                questiond.setExplain_state("2");
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
                                    if ("1".equals(trueanswer)) {
                                        mIV_a.setImageResource(R.mipmap.zhengque);
                                        mIV_c.setImageResource(R.mipmap.cuowu);
                                        questiond.setA_state("2");
                                    } else if ("2".equals(trueanswer)) {
                                        mIV_b.setImageResource(R.mipmap.zhengque);
                                        mIV_c.setImageResource(R.mipmap.cuowu);
                                        questiond.setB_state("2");
                                    } else if ("4".equals(trueanswer)) {
                                        mIV_d.setImageResource(R.mipmap.zhengque);
                                        mIV_c.setImageResource(R.mipmap.cuowu);
                                        questiond.setD_state("2");
                                    }
                                    questiond.setC_state("3");
                                } else {
                                    mIV_c.setImageResource(R.mipmap.zhengque);
                                    int currentItem = readerViewPager.getCurrentItem();
                                    readerViewPager.setCurrentItem(currentItem + 1);
                                    questiond.setC_state("2");
                                    db.delete(qid);
                                    allCollectQuestion = db.findAllCuoTiA();
                                    adapter.notifyDataSetChanged();
                                    if (allCollectQuestion.size() == 0) {
                                        finish();
                                    }
                                }
                                mLL_explan.setVisibility(View.VISIBLE);
                                questiond.setExplain_state("2");
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
                                    if ("1".equals(trueanswer)) {
                                        mIV_a.setImageResource(R.mipmap.zhengque);
                                        mIV_d.setImageResource(R.mipmap.cuowu);
                                        questiond.setA_state("2");
                                    } else if ("2".equals(trueanswer)) {
                                        mIV_b.setImageResource(R.mipmap.zhengque);
                                        mIV_d.setImageResource(R.mipmap.cuowu);
                                        questiond.setB_state("2");
                                    } else if ("3".equals(trueanswer)) {
                                        mIV_c.setImageResource(R.mipmap.zhengque);
                                        mIV_d.setImageResource(R.mipmap.cuowu);
                                        questiond.setC_state("2");
                                    }
                                    questiond.setD_state("3");
                                } else {
                                    mIV_d.setImageResource(R.mipmap.zhengque);
                                    int currentItem = readerViewPager.getCurrentItem();
                                    readerViewPager.setCurrentItem(currentItem + 1);
                                    questiond.setD_state("2");
                                    db.delete(qid);
                                    allCollectQuestion = db.findAllCuoTiA();
                                    adapter.notifyDataSetChanged();
                                    if (allCollectQuestion.size() == 0) {
                                        finish();
                                    }
                                }
                                mLL_explan.setVisibility(View.VISIBLE);
                                questiond.setExplain_state("2");
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
                            }else if(true_answer.contains("1")){
                                mCB_a.setButtonDrawable(R.mipmap.a_true);
                                questiond.setA_state("4");
                                a = false;
                            }else if(optionBean.getA()!="0") {
                                mCB_a.setButtonDrawable(R.mipmap.cuowu);
                                questiond.setA_state("3");
                                a = false;
                            }

                            if (true_answer.contains(optionBean.getB())) {
                                mCB_b.setButtonDrawable(R.mipmap.zhengque);
                                questiond.setB_state("2");
                            }else if(true_answer.contains("2")){
                                mCB_b.setButtonDrawable(R.mipmap.b_true);
                                questiond.setB_state("4");
                                b = false;
                            }else if(optionBean.getB()!="0") {
                                mCB_b.setButtonDrawable(R.mipmap.cuowu);
                                questiond.setB_state("3");
                                b = false;
                            }

                            if (true_answer.contains(optionBean.getC())) {
                                mCB_c.setButtonDrawable(R.mipmap.zhengque);
                                questiond.setC_state("2");
                            }else if(true_answer.contains("3")){
                                mCB_c.setButtonDrawable(R.mipmap.c_true);
                                questiond.setC_state("4");
                                c = false;
                            }else if(optionBean.getC()!="0") {
                                mCB_c.setButtonDrawable(R.mipmap.cuowu);
                                questiond.setC_state("3");
                                c = false;
                            }


                            if (true_answer.contains(optionBean.getD())) {
                                mCB_d.setButtonDrawable(R.mipmap.zhengque);
                                questiond.setD_state("2");
                            }else if(true_answer.contains("4")){
                                mCB_d.setButtonDrawable(R.mipmap.d_true);
                                questiond.setD_state("4");
                                d = false;
                            }else if(optionBean.getD()!="0"){
                                mCB_d.setButtonDrawable(R.mipmap.cuowu);
                                questiond.setD_state("3");
                                d = false;
                            }

                            if (a&&b&&c&&d) {
                                int currentItem = readerViewPager.getCurrentItem();
                                readerViewPager.setCurrentItem(currentItem+1);
                            }else {
                                db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain
                                        , question_type, img, video, "cuotid");
                            }

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
                mTV_analysis.setText(explain);
                //点击事件
                //返回
                mLL_questionback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                container.addView(view);
                return view;

            }

            @Override
            public int getCount() {
                return allCollectQuestion.size();
            }
        };
        if (readerViewPager != null) {
            readerViewPager.setAdapter(adapter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (readerViewPager != null) {
            int currentItem = readerViewPager.getCurrentItem();
            PrefUtils.putInt(App.context, "COLLECT", currentItem);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        int currentitem = PrefUtils.getInt(App.context, "COLLECT", 0);
        if (readerViewPager != null) {
            readerViewPager.setCurrentItem(currentitem);
        }

    }
}
