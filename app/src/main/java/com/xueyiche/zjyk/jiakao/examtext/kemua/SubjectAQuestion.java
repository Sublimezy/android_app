package com.xueyiche.zjyk.jiakao.examtext.kemua;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.base.module.DBManager;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.homepage.bean.Questiona;
import com.xueyiche.zjyk.jiakao.homepage.db.KaoJiaZhaoDao;
import com.xueyiche.zjyk.jiakao.homepage.view.ReaderViewPager;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;

import java.util.List;
import java.util.Random;


public class SubjectAQuestion extends BaseActivity implements View.OnClickListener {
    //顺序练习
    private ReaderViewPager readerViewPager;
    private List<Questiona> allQuestionA;
    private PagerAdapter adapter;
    private KaoJiaZhaoDao db;
    private KaoJiaZhaoDao kjz;
    private TextView mTV_title_mun;
    private LinearLayout mLL_questionback;
    private CheckBox ck_collection;
    private int[] imgs = {R.mipmap.pic_yi,R.mipmap.pic_er,R.mipmap.pic_san,R.mipmap.pic_si,R.mipmap.pic_wu,R.mipmap.pic_liu,
            R.mipmap.pic_qi,R.mipmap.pic_ba,R.mipmap.pic_jiu,R.mipmap.pic_shi,R.mipmap.pic_shiyi,R.mipmap.pic_shier,
            R.mipmap.pic_shisan};


    @Override
    protected int initContentView() {
        DBManager dbManager = new DBManager(this);
        dbManager.copyDBFile();
        allQuestionA = dbManager.getAllQuestionA();
        db = new KaoJiaZhaoDao(App.context);
        kjz = new KaoJiaZhaoDao(App.context);
        return R.layout.home_exam_subjecta_question;
    }

    @Override
    protected void initView() {
        readerViewPager = (ReaderViewPager) view.findViewById(R.id.vp_subjectA);
        //题目的数量
        mTV_title_mun = (TextView) view.findViewById(R.id.exam_question_include).findViewById(R.id.tv_title_num);
        //返回
        mLL_questionback = (LinearLayout) view.findViewById(R.id.exam_question_include).findViewById(R.id.ll_question_back);
        mLL_questionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //收藏
        ck_collection = (CheckBox) view.findViewById(R.id.exam_question_include).findViewById(R.id.ck_collection);
        mTV_title_mun.setText(1 + "/" + allQuestionA.size());
        initViewPager();
    }

    @Override
    protected void initListener() {
        readerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final Questiona questiond = allQuestionA.get(position);

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


                //判断是否存在
                boolean notes = kjz.isInShouCangA(qid);
                if (notes == false) {
                    //收藏
                    ck_collection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain, question_type, img, null, "shoucanga");
                                ck_collection.setText("已收藏");
                            } else {
                                db.delete(qid);
                                ck_collection.setText("收藏");
                            }
                        }
                    });
                    ck_collection.setChecked(false);
                    ck_collection.setText("收藏");
                } else {
                    ck_collection.setChecked(true);
                    ck_collection.setText("已收藏");
                }
                int timu_qid = position + 1;
                mTV_title_mun.setText(timu_qid + "/" + allQuestionA.size());


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                final Questiona questiond = allQuestionA.get(position);
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
                String a_state = questiond.getA_state();
                String b_state = questiond.getB_state();
                String c_state = questiond.getC_state();
                String d_state = questiond.getD_state();
                String explain_state = questiond.getExplain_state();
                View view = View.inflate(container.getContext(), R.layout.subjecta_question_item, null);
                TextView mTV_qusetion = (TextView) view.findViewById(R.id.tv_question);
                //问题的图片
                ImageView mIV_kemu1 = (ImageView) view.findViewById(R.id.iv_kemu1);
                if (!TextUtils.isEmpty(img)) {
//                    Picasso.with(App.context).load("http://xychead.xueyiche.vip/" + img)
//                            .placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(mIV_kemu1);
                    // 创建随机数生成器
                    Random random = new Random();
                    // 获取随机数索引
                    int index = random.nextInt(imgs.length);
                    mIV_kemu1.setImageResource(imgs[index]);
                }else {
                    mIV_kemu1.setVisibility(View.GONE);
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
                mTV_qusetion.setText(timu_qid + "/" + allQuestionA.size());
                if ("2".equals(question_type)) {
                    mIV_questiontype.setImageResource(R.mipmap.panduan);
                    xianc.setVisibility(View.GONE);
                    xiand.setVisibility(View.GONE);
                    mLL_c.setVisibility(View.GONE);
                    mLL_d.setVisibility(View.GONE);
                }
                if (a_state != "1" || b_state != "1" || c_state != "1" || d_state != "1") {
                    mLL_a.setClickable(false);
                    mLL_b.setClickable(false);
                    mLL_c.setClickable(false);
                    mLL_d.setClickable(false);
                    if (a_state.equals("2")) {
                        mIV_a.setImageResource(R.mipmap.zhengque);
                    } else if (a_state.equals("3")) {
                        mIV_a.setImageResource(R.mipmap.cuowu);
                    }
                    if (b_state.equals("2")) {
                        mIV_b.setImageResource(R.mipmap.zhengque);
                    } else if (b_state.equals("3")) {
                        mIV_b.setImageResource(R.mipmap.cuowu);
                    }
                    if (c_state.equals("2")) {
                        mIV_c.setImageResource(R.mipmap.zhengque);
                    } else if (c_state.equals("3")) {
                        mIV_c.setImageResource(R.mipmap.cuowu);
                    }
                    if (d_state.equals("2")) {
                        mIV_d.setImageResource(R.mipmap.zhengque);
                    } else if (d_state.equals("3")) {
                        mIV_d.setImageResource(R.mipmap.cuowu);
                    }
                    if (explain_state.equals("2")) {
                        mLL_explan.setVisibility(View.VISIBLE);
                    }

                } else {
                    //题目是单选
                    mLL_a.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!"1".equals(true_answer)) {
                                db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain
                                        , question_type, img, null, "cuotia");
                                if ("2".equals(true_answer)) {
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                    mIV_a.setImageResource(R.mipmap.cuowu);
                                    questiond.setB_state("2");
                                } else if ("3".equals(true_answer)) {
                                    mIV_c.setImageResource(R.mipmap.zhengque);
                                    mIV_a.setImageResource(R.mipmap.cuowu);
                                    questiond.setC_state("2");
                                } else if ("4".equals(true_answer)) {
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
                                db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain
                                        , question_type, img, null, "cuotia");
                                if ("1".equals(true_answer)) {
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                    mIV_b.setImageResource(R.mipmap.cuowu);
                                    questiond.setA_state("2");
                                } else if ("3".equals(true_answer)) {
                                    mIV_c.setImageResource(R.mipmap.zhengque);
                                    mIV_b.setImageResource(R.mipmap.cuowu);
                                    questiond.setC_state("2");
                                } else if ("4".equals(true_answer)) {
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
                                db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain
                                        , question_type, img, null, "cuotia");
                                if ("1".equals(true_answer)) {
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                    mIV_c.setImageResource(R.mipmap.cuowu);
                                    questiond.setA_state("2");
                                } else if ("2".equals(true_answer)) {
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                    mIV_c.setImageResource(R.mipmap.cuowu);
                                    questiond.setB_state("2");
                                } else if ("4".equals(true_answer)) {
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
                                db.add(qid, question, answer_1, answer_2, answer_3, answer_4, true_answer, explain
                                        , question_type, img, null, "cuotia");
                                if ("1".equals(true_answer)) {
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                    mIV_d.setImageResource(R.mipmap.cuowu);
                                    questiond.setA_state("2");
                                } else if ("2".equals(true_answer)) {
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                    mIV_d.setImageResource(R.mipmap.cuowu);
                                    questiond.setB_state("2");
                                } else if ("3".equals(true_answer)) {
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
                int timu_qid1 = position + 1;
                mTV_qusetion.setText("       " + timu_qid1 + "." + question);
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
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return allQuestionA.size();
            }
        };
        readerViewPager.setAdapter(adapter);
    }

    @Override
    protected void onPause() {

        super.onPause();
        int currentItem = readerViewPager.getCurrentItem();
        PrefUtils.putInt(App.context, "CURRENTITEM1", currentItem);
        int daTJL = currentItem + 1;
        PrefUtils.putString(App.context,"one_number",daTJL+"");
        kjz.daTJLA(daTJL + "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        int currentitem = PrefUtils.getInt(App.context, "CURRENTITEM1", 0);
        readerViewPager.setCurrentItem(currentitem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_question_back:
                finish();
                break;


        }
    }
}
