package com.xueyiche.zjyk.xueyiche.main.activities.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

/**
 * Created by ZL on 2018/2/6.
 */
public class LoginFirstStepActivity extends BaseActivity implements View.OnClickListener {
    private EditText etLoginPhone;
    private Button btNextStep;
    public static LoginFirstStepActivity instance;
    private LinearLayout ll_exam_back;

    @Override
    protected int initContentView() {
        return R.layout.login_first_step;
    }

    @Override
    protected void initView() {
        etLoginPhone = (EditText) view.findViewById(R.id.etLoginPhone);
        btNextStep = (Button) view.findViewById(R.id.btNextStep);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.shop_top_include).findViewById(R.id.ll_exam_back);
        instance=this;
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        btNextStep.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.btNextStep:
                String phone = etLoginPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    if (!StringUtils.isMobileNumber(phone)) {
                        Toast.makeText(App.context, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                    }else {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            Intent intent = new Intent(App.context,LoginSecondStepActivity.class);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                        }else {
                            Toast.makeText(App.context, "请检查网络", Toast.LENGTH_SHORT).show();
                        }

                    }
                }else {
                    Toast.makeText(App.context,"请填写手机号",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
