package com.xueyiche.zjyk.jiakao.examtext.examfragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.base.module.DBManager;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.examtext.MoNiTestPage;
import com.xueyiche.zjyk.jiakao.examtext.MyResult;
import com.xueyiche.zjyk.jiakao.examtext.kemud.CollectionD;
import com.xueyiche.zjyk.jiakao.examtext.kemud.CuoTiD;
import com.xueyiche.zjyk.jiakao.examtext.kemud.RandomTestD;
import com.xueyiche.zjyk.jiakao.examtext.kemud.SubjectDQuestion;
import com.xueyiche.zjyk.jiakao.examtext.kemud.ZhuanXiangD;
import com.xueyiche.zjyk.jiakao.homepage.bean.MyResultBean;
import com.xueyiche.zjyk.jiakao.homepage.bean.Questiona;
import com.xueyiche.zjyk.jiakao.homepage.db.KaoJiaZhaoDao;
import com.xueyiche.zjyk.jiakao.homepage.db.MyResultDB;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;

import java.util.List;


public class SubjectDFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_test_practice;
    private TextView mTV_shunxuid,tv_practice_test;
    private TextView  mTV_four, mTV_three, mTV_two, mTV_five;
    private KaoJiaZhaoDao db;
    private boolean cuotid;
    private boolean shoucangd;
    private boolean chengji;
    private MyResultDB dbResult;
    private DBManager dbManager;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        db = new KaoJiaZhaoDao(App.context);
        dbResult = new MyResultDB(App.context);
        View view = View.inflate(App.context, R.layout.home_exam_subjectd, null);

        ll_test_practice = (LinearLayout) view.findViewById(R.id.ll_test_practice);

        //顺序练习
        mTV_shunxuid = (TextView) view.findViewById(R.id.tv_shunxuid);

        //专项
        mTV_two = (TextView) view.findViewById(R.id.tv_twod);
        //错题
        mTV_three = (TextView) view.findViewById(R.id.tv_threed);

        //收藏
        mTV_four = (TextView) view.findViewById(R.id.tv_fourd);
        //我的成绩
        mTV_five = (TextView) view.findViewById(R.id.tv_fived);

        //模拟考试
        tv_practice_test = (TextView) view.findViewById(R.id.tv_practice_test);
        ll_test_practice.setOnClickListener(this);

        mTV_two.setOnClickListener(this);
        mTV_three.setOnClickListener(this);
        mTV_four.setOnClickListener(this);
        tv_practice_test.setOnClickListener(this);
        mTV_five.setOnClickListener(this);

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
        switch (v.getId()) {

            case R.id.ll_test_practice:
                startActivity(new Intent(App.context, SubjectDQuestion.class));
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
