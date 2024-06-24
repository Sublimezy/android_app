package com.xueyiche.zjyk.jiakao.main.activities.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.CommonWebView;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.constants.AppUrl;
import com.xueyiche.zjyk.jiakao.constants.bean.YanZhengMa;
import com.xueyiche.zjyk.jiakao.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.jiakao.homepage.view.VerificationCodeInput;
import com.xueyiche.zjyk.jiakao.main.bean.LoginBean;
import com.xueyiche.zjyk.jiakao.mine.view.CountDownTimerUtils;
import com.xueyiche.zjyk.jiakao.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.jiakao.myhttp.RequestCallBack;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;
import com.xueyiche.zjyk.jiakao.utils.XueYiCheUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZL on 2018/2/6.
 */
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
    public static void forward(Context context,String phone) {
        Intent intent = new Intent(context, LoginSecondStepActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);




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
        params.put("mobile", phone);
        params.put("event", "mobilelogin");
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
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            if (!TextUtils.isEmpty(yanzhengma)) {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", phone);
                params.put("captcha", yanzhengma);
                params.put("type", "1");
                MyHttpUtils.postHttpMessage(AppUrl.LOGIN, params, LoginBean.class, new RequestCallBack<LoginBean>() {
                    @Override
                    public void requestSuccess(LoginBean json) {
                        if (json.getCode() == 1) {
                            LoginBean.DataBean.UserinfoBean userinfo = json.getData().getUserinfo();
                            PrefUtils.putBoolean(App.context,"ISLOGIN",true);
                            PrefUtils.putParameter("token",userinfo.getToken());
                            PrefUtils.putParameter("phone",userinfo.getMobile());
                            LoginFirstStepActivity.instance.finish();
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
            ToastUtils.showToast(LoginSecondStepActivity.this,"请检查网络连接");
        }
    }
    @OnClick({R.id.ll_exam_back, R.id.tvGetPassWord, R.id.btLogin, R.id.tv_user_xieyi})
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
                if(TextUtils.isEmpty(yanzhengma)){
                    showToastShort("请输入验证码");
                    return;
                }
                login();
                if ("0".equals(isCheck)) {
                    ToastUtils.showToast(LoginSecondStepActivity.this,"请阅读并同意遵守驾考APP法律条款与平台规则");
                    break;
                } else {
                    login();
                }
                break;
            case R.id.tv_user_xieyi:
                CommonWebView.forward(LoginSecondStepActivity.this,"xieyi");
                break;
        }
    }
}
