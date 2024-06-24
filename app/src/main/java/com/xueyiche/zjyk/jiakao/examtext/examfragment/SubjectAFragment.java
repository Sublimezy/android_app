package com.xueyiche.zjyk.jiakao.examtext.examfragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.CommonWebView;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.base.module.DBManager;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.examtext.Answerwithcash;
import com.xueyiche.zjyk.jiakao.examtext.MoNiTestPage;
import com.xueyiche.zjyk.jiakao.examtext.MyJiangJin;
import com.xueyiche.zjyk.jiakao.examtext.MyResult;
import com.xueyiche.zjyk.jiakao.examtext.PaiHangBang;
import com.xueyiche.zjyk.jiakao.examtext.kemua.CollectionA;
import com.xueyiche.zjyk.jiakao.examtext.kemua.CuoTiA;
import com.xueyiche.zjyk.jiakao.examtext.kemua.RandomTestA;
import com.xueyiche.zjyk.jiakao.examtext.kemua.SubjectAQuestion;
import com.xueyiche.zjyk.jiakao.examtext.kemua.ZhuanXiangA;
import com.xueyiche.zjyk.jiakao.homepage.bean.MyResultBean;
import com.xueyiche.zjyk.jiakao.homepage.bean.Questiona;
import com.xueyiche.zjyk.jiakao.homepage.db.KaoJiaZhaoDao;
import com.xueyiche.zjyk.jiakao.homepage.db.MyResultDB;
import com.xueyiche.zjyk.jiakao.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.jiakao.utils.DialogUtils;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;

import java.util.List;

/**
 * Created by Owner on 2016/9/23.
 */
//科目一的fragment
public class SubjectAFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_test_practice;
    private TextView mTV_shunxuid,tv_practice_test;
    private TextView mTV_one, mTV_four, mTV_three, mTV_two,mTV_five,tv_eight,tv_six,tv_seven;
    private KaoJiaZhaoDao db;
    private boolean cuotia;
    private boolean shoucanga;
    private boolean chengji;
    private MyResultDB dbResult;
    private TextView tv_biguojiqiao,tv_exam_shuoming;
    private String user_phone;
    private DBManager dbManager;
    private Button bt_wincash;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        db = new KaoJiaZhaoDao(App.context);
        dbResult = new MyResultDB(App.context);
        View view = View.inflate(App.context, R.layout.home_exam_subjecta, null);
        bt_wincash = (Button) view.findViewById(R.id.bt_wincash);
        ll_test_practice = (LinearLayout) view.findViewById(R.id.ll_test_practice);
        //考试重点
        tv_eight = (TextView) view.findViewById(R.id.tv_eight);
        //顺序练习
        mTV_shunxuid = (TextView) view.findViewById(R.id.tv_shunxuid);
        //随机练习
        mTV_one = (TextView) view.findViewById(R.id.tv_one);
        //必过技巧
        tv_biguojiqiao  = (TextView) view.findViewById(R.id.tv_biguojiqiao);
        tv_exam_shuoming  = (TextView) view.findViewById(R.id.tv_exam_shuoming);
        //专项
        mTV_two = (TextView) view.findViewById(R.id.tv_two);
        //错题
        mTV_three = (TextView) view.findViewById(R.id.tv_three);
        //收藏
        mTV_four = (TextView) view.findViewById(R.id.tv_four);
        //我的成绩
        mTV_five = (TextView) view.findViewById(R.id.tv_five);
        mTV_five.setOnClickListener(this);
        //排行榜
        tv_six = (TextView) view.findViewById(R.id.tv_six);
        bt_wincash.setOnClickListener(this);
        tv_six.setOnClickListener(this);
        //我的奖金
        tv_seven = (TextView) view.findViewById(R.id.tv_seven);
        tv_seven.setOnClickListener(this);
        tv_practice_test = (TextView) view.findViewById(R.id.tv_practice_test);
        tv_practice_test.setOnClickListener(this);
        ll_test_practice.setOnClickListener(this);
        mTV_one.setOnClickListener(this);
        tv_biguojiqiao.setOnClickListener(this);
        mTV_two.setOnClickListener(this);
        mTV_three.setOnClickListener(this);
        mTV_four.setOnClickListener(this);
        tv_exam_shuoming.setOnClickListener(this);
        tv_eight.setOnClickListener(this);
        dbManager = new DBManager(getContext());
        dbManager.copyDBFile();
        String one_number = PrefUtils.getString(App.context, "one_number", "");
        if (!TextUtils.isEmpty(one_number)) {
            mTV_shunxuid.setText(one_number + "/"+dbManager.getAllQuestionA().size());
        }else {
            mTV_shunxuid.setText(0 + "/"+dbManager.getAllQuestionA().size());
        }

        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "1111";
    }


    @Override
    public void onClick(View v) {
        List<Questiona> allCuoTiA = db.findAllCuoTiA();
        List<Questiona> allShouCangA = db.findAllShouCangA();
        List<MyResultBean> chengjiList = dbResult.findAllResult();
        chengji = chengjiList.size()==0;
        cuotia = allCuoTiA.size() == 0;
        shoucanga = allShouCangA.size() == 0;
        Intent intent4 = new Intent(App.context, PaiHangBang.class);
        switch (v.getId()) {
            case R.id.bt_wincash:
                startActivity(new Intent(App.context, Answerwithcash.class));
                break;
            case R.id.tv_biguojiqiao:
                Intent intent = new Intent(App.context, CommonWebView.class);
                intent.putExtra("weburl","biguo");
                startActivity(intent);
                break;
            case R.id.tv_exam_shuoming:
                Intent intent1 = new Intent(App.context, CommonWebView.class);
                intent1.putExtra("weburl","one_talk");
                startActivity(intent1);
                break;
            case R.id.tv_eight:
                Intent intent3 = new Intent(App.context, CommonWebView.class);
                intent3.putExtra("weburl","one_important");
                startActivity(intent3);
                break;
            case R.id.tv_one:
                startActivity(new Intent(App.context, RandomTestA.class));
                break;
            case R.id.tv_two:
                startActivity(new Intent(App.context, ZhuanXiangA.class));
                break;
            case R.id.tv_three:

                if (cuotia) {
                    showToastShort("当前没有错题呦 ！");
                }else {
                    startActivity(new Intent(App.context, CuoTiA.class));
                }
                break;
            case R.id.tv_four:
                if (shoucanga) {
                    showToastShort("当前没有收藏呦 ！");
                }else {
                    startActivity(new Intent(App.context, CollectionA.class));
                }
                break;
            case R.id.ll_test_practice:
                //科目一的顺序的练习的页面
                startActivity(new Intent(App.context, SubjectAQuestion.class));
                break;
            case R.id.tv_five:
                if (chengji) {
                    showToastShort("暂无成绩！");
                }else {
                    Intent intent5 = new Intent(App.context, MyResult.class);
                    intent5.putExtra("myresult","1");
                    startActivity(intent5);
        }
                break;
            case R.id.tv_six:
                if (DialogUtils.IsLogin()) {
                    intent4.putExtra("answer_type","1");
                    startActivity(intent4);
                }else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.tv_seven:
                if (DialogUtils.IsLogin()) {
                    startActivity(new Intent(App.context, MyJiangJin.class));

                }else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.iv_seven:
                if (DialogUtils.IsLogin()) {
                    startActivity(new Intent(App.context, MyJiangJin.class));
                }else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.tv_practice_test:
                Intent intent6 = new Intent(App.context, MoNiTestPage.class);
                intent6.putExtra("moni_style","1");
                startActivity(intent6);
                break;

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        String one_number = PrefUtils.getString(App.context, "one_number", "");
        if (!TextUtils.isEmpty(one_number)) {
            mTV_shunxuid.setText(one_number + "/"+dbManager.getAllQuestionA().size());
        }else {
            mTV_shunxuid.setText(0 + "/"+dbManager.getAllQuestionA().size());
        }

    }
}
