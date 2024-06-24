package com.xueyiche.zjyk.jiakao.main.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.utils.StringUtils;
import com.xueyiche.zjyk.jiakao.utils.XueYiCheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFirstStepActivity extends BaseActivity {
    @BindView(R.id.etLoginPhone)
    EditText etLoginPhone;
    public static LoginFirstStepActivity instance;

    @Override
    protected int initContentView() {
        return R.layout.login_first_step;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(context, LoginFirstStepActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(R.id.rl_title).statusBarDarkFont(true).init();
        instance = this;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_exam_back, R.id.btNextStep})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.btNextStep:
                String phone = etLoginPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
        /*            if (!StringUtils.isMobileNumber(phone)) {
                        ToastUtils.showToast(LoginFirstStepActivity.this,"请填写正确的手机号");
                    } else {


                    }*/
                    if (XueYiCheUtils.IsHaveInternet(App.context)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("phone", phone);
                        readyGo(LoginSecondStepActivity.class, bundle);
                    } else {
                        ToastUtils.showToast(LoginFirstStepActivity.this, "请检查网络");
                    }
                } else {
                    ToastUtils.showToast(LoginFirstStepActivity.this, "请填写手机号");
                }
                break;
        }
    }
}
