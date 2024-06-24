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
import com.xueyiche.zjyk.jiakao.examtext.kemud.CollectionD;
import com.xueyiche.zjyk.jiakao.examtext.kemud.CuoTiD;
import com.xueyiche.zjyk.jiakao.examtext.kemud.RandomTestD;
import com.xueyiche.zjyk.jiakao.examtext.kemud.SubjectDQuestion;
import com.xueyiche.zjyk.jiakao.examtext.kemud.ZhuanXiangD;
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
public class SubjectDFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_test_practice;
    private TextView mTV_shunxuid,tv_exam_shuoming,tv_biguojiqiao,tv_practice_test;
    private TextView mTV_one, mTV_four, mTV_three, mTV_two, mTV_five,tv_eight,tv_six,tv_seven;
    private KaoJiaZhaoDao db;
    private boolean cuotid;
    private boolean shoucangd;
    private boolean chengji;
    private MyResultDB dbResult;
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
        View view = View.inflate(App.context, R.layout.home_exam_subjectd, null);

        bt_wincash = (Button) view.findViewById(R.id.bt_wincash);
        ll_test_practice = (LinearLayout) view.findViewById(R.id.ll_test_practice);
        //考试重点
        tv_eight = (TextView) view.findViewById(R.id.tv_eightd);
        //顺序练习
        mTV_shunxuid = (TextView) view.findViewById(R.id.tv_shunxuid);
        //随机练习
        mTV_one = (TextView) view.findViewById(R.id.tv_oned);
        //专项
        mTV_two = (TextView) view.findViewById(R.id.tv_twod);
        //错题
        mTV_three = (TextView) view.findViewById(R.id.tv_threed);
        //必过技巧
        tv_biguojiqiao = (TextView) view.findViewById(R.id.tv_biguojiqiao);
        tv_exam_shuoming = (TextView) view.findViewById(R.id.tv_exam_shuoming);
        //收藏
        mTV_four = (TextView) view.findViewById(R.id.tv_fourd);
        //我的成绩
        mTV_five = (TextView) view.findViewById(R.id.tv_fived);
        //排行榜
        tv_six = (TextView) view.findViewById(R.id.tv_sixd);
        tv_six.setOnClickListener(this);
        //我的奖金
        tv_seven = (TextView) view.findViewById(R.id.tv_sevend);
        tv_seven.setOnClickListener(this);
        //模拟考试
        tv_practice_test = (TextView) view.findViewById(R.id.tv_practice_test);
        ll_test_practice.setOnClickListener(this);
        bt_wincash.setOnClickListener(this);
        mTV_one.setOnClickListener(this);
        mTV_two.setOnClickListener(this);
        mTV_three.setOnClickListener(this);
        mTV_four.setOnClickListener(this);
        tv_practice_test.setOnClickListener(this);
        mTV_five.setOnClickListener(this);
        tv_biguojiqiao.setOnClickListener(this);
        tv_exam_shuoming.setOnClickListener(this);
        tv_eight.setOnClickListener(this);
        dbManager = new DBManager(getContext());
        dbManager.copyDBFile();
        String four_number = PrefUtils.getString(App.context, "four_number", "");
        if (!TextUtils.isEmpty(four_number)) {
            mTV_shunxuid.setText(four_number + "/"+dbManager.getAllQuestionD().size());
        }else {
            mTV_shunxuid.setText(0 + "/" + dbManager.getAllQuestionD().size());
        }


        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "1111";
    }

    @Override
    public void onClick(View v) {
        List<Questiona> allCuoTiD = db.findAllCuoTiD();
        List<Questiona> allShouCangD = db.findAllShouCangD();
        List<MyResultBean> resultBeen = dbResult.findAllResultD();
        chengji = resultBeen.size()==0;
        cuotid = allCuoTiD.size() == 0;
        shoucangd = allShouCangD.size() == 0;
        Intent intent5 = new Intent(App.context, PaiHangBang.class);
        switch (v.getId()) {

            case R.id.bt_wincash:
                startActivity(new Intent(App.context, Answerwithcash.class));
                break;
            case R.id.ll_test_practice:
                startActivity(new Intent(App.context, SubjectDQuestion.class));
                break;
            case R.id.tv_biguojiqiao:
                Intent intent1 = new Intent(App.context, CommonWebView.class);
                intent1.putExtra("weburl","biguo");
                startActivity(intent1);
                break;
            case R.id.tv_oned:
                startActivity(new Intent(App.context, RandomTestD.class));
                break;
            case R.id.tv_exam_shuoming:
                Intent intent2 = new Intent(App.context, CommonWebView.class);
                intent2.putExtra("weburl","four_talk");
                startActivity(intent2);
                break;
            case R.id.tv_eight:
                Intent intent4 = new Intent(App.context, CommonWebView.class);
                intent4.putExtra("weburl","four_important");
                startActivity(intent4);
                break;
            case R.id.tv_twod:
                startActivity(new Intent(App.context, ZhuanXiangD.class));
                break;
            case R.id.tv_threed:
                if (cuotid) {
                    showToastShort("当前没有错题呦 ！");
                } else {
                    startActivity(new Intent(App.context, CuoTiD.class));
                }
                break;
            case R.id.tv_fourd:
                if (shoucangd) {
                    showToastShort("当前没有收藏呦 ！");
                } else {
                    startActivity(new Intent(App.context, CollectionD.class));
                }
                break;
            case R.id.tv_practice_test:
                Intent intent6 = new Intent(App.context, MoNiTestPage.class);
                intent6.putExtra("moni_style","2");
                startActivity(intent6);
                break;
            case R.id.tv_fived:
                if (chengji) {
                    showToastShort("暂无成绩！");
                }else {
                    Intent intent = new Intent(App.context, MyResult.class);
                    intent.putExtra("myresult","2");
                    startActivity(intent);
                }
                break;
            case R.id.tv_sixd:
                if (DialogUtils.IsLogin()) {
                    intent5.putExtra("answer_type","4");
                    startActivity(intent5);
                }else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.tv_sevend:
                if (DialogUtils.IsLogin()) {
                    startActivity(new Intent(App.context, MyJiangJin.class));
                }else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String four_number = PrefUtils.getString(App.context, "four_number", "");
        if (!TextUtils.isEmpty(four_number)) {
            mTV_shunxuid.setText(four_number + "/"+dbManager.getAllQuestionD().size());
        }else {
            mTV_shunxuid.setText(0 + "/" + dbManager.getAllQuestionD().size());
        }

    }
}
