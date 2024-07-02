package com.gxuwz.zy.login.activity;

import static com.gxuwz.zy.constants.App.context;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseActivity;
import com.gxuwz.zy.constants.AppUrl;
import com.gxuwz.zy.constants.bean.YanZhengMa;
import com.gxuwz.zy.login.entity.dos.LoginBean;
import com.gxuwz.zy.mine.view.CountDownTimerUtils;
import com.gxuwz.zy.myhttp.MyHttpUtils;
import com.gxuwz.zy.myhttp.RequestCallBack;
import com.gxuwz.zy.utils.PrefUtils;
import com.gxuwz.zy.utils.XueYiCheUtils;
import com.gxuwz.zy.view.VerificationCodeInput;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginSecondStepActivity extends BaseActivity {
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.verificationCodeInput)
    VerificationCodeInput verificationCodeInput;
    @BindView(R.id.tvGetPassWord)
    TextView tvGetPassWord;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.check_box)
    CheckBox check_box;
    private CountDownTimerUtils countDownTimer;
    private String phone;
    private String yanzhengma;
    private String isCheck = "1";

    @Override
    protected int initContentView() {
        return R.layout.login_second_step;
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(R.id.rl_title).statusBarDarkFont(true).init();

    }

    @Override
    protected void initListener() {
        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isCheck = "1";
                } else {
                    isCheck = "0";
                }
            }
        });
        countDownTimer = new CountDownTimerUtils(tvGetPassWord, 60000, 1000);
        verificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                yanzhengma = content;
            }
        });

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        tvPhone.setText(phone);
        getPassWord();
    }

    //获取验证码
    private void getPassWord() {
        countDownTimer.start();
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("email", phone);
        params.put("event", "MOBILE_LOGIN");
        MyHttpUtils.postHttpMessage(AppUrl.sendSMS, params, YanZhengMa.class, new RequestCallBack<YanZhengMa>() {
            @Override
            public void requestSuccess(YanZhengMa json) {
                if (json.getCode() == 1) {
                    countDownTimer.start();
                    tvGetPassWord.setClickable(false);
                } else {
                }
                showToastShort(json.getMsg());
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    private void login() {
        if (XueYiCheUtils.IsHaveInternet(context)) {
            if (!TextUtils.isEmpty(yanzhengma)) {
                Map<String, String> params = new HashMap<>();
                params.put("email", phone);
                params.put("captcha", yanzhengma);
                params.put("loginType", "CAPTCHA");
                MyHttpUtils.postHttpMessage(AppUrl.LOGIN, params, LoginBean.class, new RequestCallBack<LoginBean>() {
                    @Override
                    public void requestSuccess(LoginBean json) {
                        if (json.getCode() == 200) {

                            LoginBean.DataBean.DistanceUser userinfo = json.getData().getDistanceUser();
                            String token = json.getData().getToken();

                            LoginFirstStepActivity.instance.finish();

                            PrefUtils.putBoolean(context, "ISLOGIN", true);
                            PrefUtils.putParameter("token", token);
                            PrefUtils.putParameter(context, "userinfo", userinfo);
                            LoginBean.DataBean.DistanceUser distanceUser = PrefUtils.getParameter(context, "userinfo", LoginBean.DataBean.DistanceUser.class);


                            System.out.println(distanceUser);


                            finish();
                        }
                        showToastShort(json.getMsg());
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {
                        showToastShort("连接服务器失败");
                    }
                });
            }
        } else {
            ToastUtils.showToast(LoginSecondStepActivity.this, "请检查网络连接");
        }
    }

    @OnClick({R.id.ll_exam_back, R.id.tvGetPassWord, R.id.btLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tvGetPassWord:
                getPassWord();
                break;
            case R.id.btLogin:

                yanzhengma = et_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(yanzhengma)) {
                    showToastShort("请输入验证码");
                    return;
                }

                if ("0".equals(isCheck)) {
                    ToastUtils.showToast(LoginSecondStepActivity.this, "请阅读并同意遵守驾考APP法律条款与平台规则");
                    break;
                } else {
                    login();
                }
                break;

        }
    }
}
