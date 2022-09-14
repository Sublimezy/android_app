package com.xueyiche.zjyk.xueyiche.examtext;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;

/**
 * Created by Owner on 2019/2/26.
 */
public class AnswerwithcashFinish extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back, about, iv_once_test,iv_kaojiazhao;
    private CircleImageView test_head;
    private TextView test_jiangli;

    @Override
    protected int initContentView() {
        return R.layout.answer_cash_finish_activity;
    }

    @Override
    protected void initView() {
        iv_back = (ImageView) view.findViewById(R.id.test_moni_activity_include).findViewById(R.id.ll_test_license_back);
        about = (ImageView) view.findViewById(R.id.test_moni_activity_include).findViewById(R.id.ll_test_license_about);
        test_head = (CircleImageView) view.findViewById(R.id.test_head);
        test_jiangli = (TextView) view.findViewById(R.id.test_jiangli);
        iv_once_test = (ImageView) view.findViewById(R.id.iv_once_test);
        iv_kaojiazhao = (ImageView) view.findViewById(R.id.iv_kaojiazhao);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        about.setOnClickListener(this);
        iv_kaojiazhao.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        int fen = getIntent().getIntExtra("fen", 0);
        int i = fen * 10;
        test_jiangli.setText("恭喜获得"+i+"元");
        about.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_test_license_back:
                finish();
                break;
            case R.id.iv_once_test:
                openActivity(AnswerWithCashQuestion.class);
                finish();
                break;
            case R.id.iv_kaojiazhao:
                Intent intent1 = new Intent(App.context,MainActivity.class);
                intent1.putExtra("baoming","baoming");
                intent1.putExtra("position",1);
                startActivity(intent1);
                break;
            default:

                break;
        }
    }
}
