package com.xueyiche.zjyk.xueyiche.examtext;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.CommonWebView;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;

/**
 * Created by Owner on 2019/2/26.
 */
public class Answerwithcash extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back, about, start_test, iv_paihangbang, iv_kaojiazhao;
    private CircleImageView test_head;
    private TextView tv_name, test_grade, test_time;

    @Override
    protected int initContentView() {
        return R.layout.answer_cash_activity;
    }

    @Override
    protected void initView() {
        iv_back = (ImageView) view.findViewById(R.id.test_moni_activity_include).findViewById(R.id.ll_test_license_back);
        about = (ImageView) view.findViewById(R.id.test_moni_activity_include).findViewById(R.id.ll_test_license_about);
        test_head = (CircleImageView) view.findViewById(R.id.test_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        test_grade = (TextView) view.findViewById(R.id.test_grade);
        test_time = (TextView) view.findViewById(R.id.test_time);
        start_test = (ImageView) view.findViewById(R.id.start_test);
        iv_paihangbang = (ImageView) view.findViewById(R.id.iv_paihangbang);
        iv_kaojiazhao = (ImageView) view.findViewById(R.id.iv_kaojiazhao);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        about.setOnClickListener(this);
        start_test.setOnClickListener(this);
        iv_paihangbang.setOnClickListener(this);
        iv_kaojiazhao.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_test_license_back:
                finish();
                break;
            case R.id.ll_test_license_about:
                //ToDO:about
                break;
            case R.id.start_test:
                if (DialogUtils.IsLogin()) {
                    openActivity(AnswerWithCashQuestion.class);
                }else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.iv_paihangbang:
                Intent intent = new Intent(App.context, CommonWebView.class);
                intent.putExtra("weburl","paihangbang");
                startActivity(intent);
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
