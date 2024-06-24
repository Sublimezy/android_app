package com.xueyiche.zjyk.jiakao.examtext.kemua;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.homepage.bean.OptionBean;
import com.xueyiche.zjyk.jiakao.homepage.bean.Questiona;
import com.xueyiche.zjyk.jiakao.homepage.db.KaoJiaZhaoDao;
import com.xueyiche.zjyk.jiakao.homepage.view.ReaderViewPager;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;

import java.util.List;


public class CollectionA extends BaseActivity{
    private ReaderViewPager readerViewPager;
    private List<Questiona> allCollectQuestion;
    private PagerAdapter adapter;
    private KaoJiaZhaoDao db;
    private TextView mTV_title_mun;
    private LinearLayout mLL_questionback;
    private TextView mTV_move;
    private int timu_qid;

    @Override
    protected int initContentView() {
        db = new KaoJiaZhaoDao(App.context);
        allCollectQuestion = db.findAllShouCangA();
        return R.layout.home_exam_subjecta_collection;
    }

    @Override
    protected void initView() {
            readerViewPager = (ReaderViewPager) view.findViewById(R.id.vp_subjectA);
            mTV_title_mun = (TextView) view.findViewById(R.id.exam_question_collect).findViewById(R.id.tv_title_num);
        mTV_title_mun.setText(1 +"/"+allCollectQuestion.size());
            mLL_questionback = (LinearLayout) view.findViewById(R.id.exam_question_collect).findViewById(R.id.ll_question_back);
            //移除
            mTV_move = (TextView) view.findViewById(R.id.exam_question_collect).findViewById(R.id.tv_move);
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
                //    final   Questiona questiond = allCollectQuestion.get(position);

                    timu_qid = position+1;
                    mTV_title_mun.setText(timu_qid +"/"+allCollectQuestion.size());
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
            public int getItemPosition(Object object)   {
                if ( mChildCount > 0) {
                    mChildCount --;
                    return POSITION_NONE;
                }
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                {
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
                    String a_state = questiond.getA_state();
                    String b_state = questiond.getB_state();
                    String c_state = questiond.getC_state();
                    String d_state = questiond.getD_state();
                    String explain_state = questiond.getExplain_state();
                    mTV_move.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String qid1 = questiond.getQid();
                        db.delete(qid1);
                        allCollectQuestion = db.findAllShouCangA();
                        adapter.notifyDataSetChanged();
                        mTV_title_mun.setText(timu_qid+"/"+allCollectQuestion.size());
                        if (allCollectQuestion.size()==0) {
                            finish();
                        }
                    }
                });
                    //多选答案的实体类
                    final OptionBean optionBean = new OptionBean();
                    optionBean.setA("0");
                    optionBean.setB("0");
                    optionBean.setC("0");
                    optionBean.setD("0");
                    View view = View.inflate(container.getContext(), R.layout.subjecta_question_item, null);
                    TextView mTV_qusetion = (TextView) view.findViewById(R.id.tv_question);
                    //问题的图片
                    ImageView mIV_kemu1 = (ImageView) view.findViewById(R.id.iv_kemu1);
                    if (!TextUtils.isEmpty(img)) {
                        Picasso.with(App.context).load("http://jiakao.xueyiche.net/" + img)
                                .placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(mIV_kemu1);
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

                    int timu_qid = position+1;
                    mTV_qusetion.setText(timu_qid+"/"+allCollectQuestion.size());
                    if ("2".equals(question_type)) {
                        mIV_questiontype.setImageResource(R.mipmap.panduan);
                        xianc.setVisibility(View.GONE);
                        xiand.setVisibility(View.GONE);
                        mLL_c.setVisibility(View.GONE);
                        mLL_d.setVisibility(View.GONE);
                    }
                    if (a_state!="1"||b_state!="1"||c_state!="1"||d_state!="1"){
                        if (a_state.equals("2")) {
                            mIV_a.setImageResource(R.mipmap.zhengque);
                        }else if (a_state.equals("3")){
                            mIV_a.setImageResource(R.mipmap.cuowu);
                        }
                        if (b_state.equals("2")) {
                            mIV_b.setImageResource(R.mipmap.zhengque);
                        }else if (b_state.equals("3")){
                            mIV_b.setImageResource(R.mipmap.cuowu);
                        }
                        if (c_state.equals("2")) {
                            mIV_c.setImageResource(R.mipmap.zhengque);
                        }else if (c_state.equals("3")){
                            mIV_c.setImageResource(R.mipmap.cuowu);
                        }
                        if (d_state.equals("2")) {
                            mIV_d.setImageResource(R.mipmap.zhengque);
                        }else if (d_state.equals("3")){
                            mIV_d.setImageResource(R.mipmap.cuowu);
                        }
                        if (explain_state.equals("2")) {
                            mLL_explan.setVisibility(View.VISIBLE);
                        }
                    }else {
                        //题目是单选
                        mLL_a.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("1".equals(trueanswer)) {
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                }else if ("2".equals(trueanswer)){
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                    mIV_a.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }else if ("3".equals(trueanswer)){
                                    mIV_c.setImageResource(R.mipmap.zhengque);
                                    mIV_a.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }else if ("4".equals(trueanswer)){
                                    mIV_d.setImageResource(R.mipmap.zhengque);
                                    mIV_a.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }
                                mLL_d.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);

                            }
                        });
                        mLL_b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("2".equals(true_answer)) {
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                }else if ("1".equals(trueanswer)){
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                    mIV_b.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }else if ("3".equals(trueanswer)){
                                    mIV_c.setImageResource(R.mipmap.zhengque);
                                    mIV_b.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }else if ("4".equals(trueanswer)){
                                    mIV_d.setImageResource(R.mipmap.zhengque);
                                    mIV_b.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }
                                mLL_a.setClickable(false);
                                mLL_c.setClickable(false);
                                mLL_d.setClickable(false);
                            }
                        });
                        mLL_c.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("3".equals(true_answer)) {
                                    mIV_c.setImageResource(R.mipmap.zhengque);
                                }else if ("1".equals(trueanswer)){
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                    mIV_c.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }else if ("2".equals(trueanswer)){
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                    mIV_c.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }else if ("4".equals(trueanswer)){
                                    mIV_d.setImageResource(R.mipmap.zhengque);
                                    mIV_c.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_d.setClickable(false);
                            }
                        });
                        mLL_d.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("4".equals(true_answer)) {
                                    mIV_d.setImageResource(R.mipmap.zhengque);
                                }else if ("2".equals(trueanswer)){
                                    mIV_b.setImageResource(R.mipmap.zhengque);
                                    mIV_d.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }else if ("3".equals(trueanswer)){
                                    mIV_c.setImageResource(R.mipmap.zhengque);
                                    mIV_d.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }else if ("1".equals(trueanswer)){
                                    mIV_a.setImageResource(R.mipmap.zhengque);
                                    mIV_d.setImageResource(R.mipmap.cuowu);
                                    mLL_explan.setVisibility(View.VISIBLE);
                                }
                                mLL_a.setClickable(false);
                                mLL_b.setClickable(false);
                                mLL_c.setClickable(false);
                            }
                        });
                    }
                    mTV_qusetion.setText("       "+timu_qid+"."+ question);
                    mTV_ansa.setText(answer_1==null? "正确": answer_1);
                    mTV_ansb.setText(answer_2==null? "错误": answer_2);
                    if (answer_3==null) {
                        mLL_c.setVisibility(View.INVISIBLE);
                    }else {
                        mTV_ansc.setText(answer_3);
                    }
                    if (answer_4==null) {
                        mLL_d.setVisibility(View.INVISIBLE);
                    }else {
                        mTV_ansd.setText(answer_4);
                    }
                    mTV_analysis.setText(explain);
                    //点击事件
                    container.addView(view);
                    return view;
                }
            }

            @Override
            public int getCount() {
                return allCollectQuestion.size();
            }
        };
        if (readerViewPager!=null) {
            readerViewPager.setAdapter(adapter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (readerViewPager!=null) {
            int currentItem = readerViewPager.getCurrentItem();
            PrefUtils.putInt(App.context,"COLLECT",currentItem);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        int currentitem = PrefUtils.getInt(App.context, "COLLECT", 0);
        if (readerViewPager!=null) {
            readerViewPager.setCurrentItem(currentitem);
        }

    }
}
