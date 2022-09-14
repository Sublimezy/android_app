package com.xueyiche.zjyk.xueyiche.examtext.kemua;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.examtext.MoNiTestPage;
import com.xueyiche.zjyk.xueyiche.homepage.bean.Questiona;
import com.xueyiche.zjyk.xueyiche.homepage.db.KaoJiaZhaoDao;
import com.xueyiche.zjyk.xueyiche.homepage.db.MyResultDB;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanglei on 2016/10/19.
 */
public class AchievementOneActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivBack, ivHeGe, ivLook;
    private TextView tvGrade, tvTime,tv_kemu_chengji;
    private int i;
    private MyResultDB db;
    private KaoJiaZhaoDao dbK;
    private boolean cuotia;
    private Button ivOnce;

    @Override
    protected int initContentView() {
        return R.layout.achievement_activity;
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) view.findViewById(R.id.iv_achievement_back);
        ivHeGe = (ImageView) view.findViewById(R.id.iv_good_and_bad);
        ivOnce = (Button) view.findViewById(R.id.iv_achievement_once);
        ivLook = (ImageView) view.findViewById(R.id.iv_achievement_look_wrong);
        tvGrade = (TextView) view.findViewById(R.id.tv_achievement_grade);
        tvTime = (TextView) view.findViewById(R.id.tv_achievement_time);
        tv_kemu_chengji = (TextView) view.findViewById(R.id.tv_kemu_chengji);
        ivBack.setOnClickListener(this);
        ivOnce.setOnClickListener(this);
        ivLook.setOnClickListener(this);
        tv_kemu_chengji.setText("科目一考试成绩");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        db = new MyResultDB(App.context);
        dbK = new KaoJiaZhaoDao(App.context);
        String number = intent.getStringExtra("number");
        long time = intent.getLongExtra("time", 0);
        Date date = new Date();
        date.setTime(time);
        String format = new SimpleDateFormat("mm:ss").format(date);
        //   SimpleDateFormat formatter = new SimpleDateFormat("mm;ss");
        // String dateString = formatter.format(time);
        tvGrade.setText(number);
        tvTime.setText("用时 : " + format);
        String date1 = intent.getStringExtra("presenttimed");
        db.add(date1,number,format,"1");
        i = Integer.parseInt(number);
        if (i < 90) {
            ivHeGe.setVisibility(View.VISIBLE);
        }else {
            ivHeGe.setImageResource(R.mipmap.grade_hege);
            ivHeGe.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public void onClick(View v) {
        List<Questiona> allCuoTiA = dbK.findAllCuoTiA();
        cuotia = allCuoTiA.size() == 0;
        switch (v.getId()) {
            case R.id.iv_achievement_back:
                finish();
                break;
            case R.id.iv_achievement_once:
                Intent intent = new Intent(App.context, MoNiTestPage.class);
                intent.putExtra("moni_style","1");
                startActivity(intent);
                finish();
                break;
            case R.id.iv_achievement_look_wrong:
                if (cuotia) {
                    showToastShort("当前没有错题呦 ！");
                }else {
                    startActivity(new Intent(App.context, CuoTiA.class));
                }
                break;
        }

    }
}
