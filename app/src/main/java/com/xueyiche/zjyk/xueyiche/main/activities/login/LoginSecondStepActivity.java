package com.xueyiche.zjyk.xueyiche.main.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.CommonWebView;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.UserInfo;
import com.xueyiche.zjyk.xueyiche.constants.bean.YanZhengMa;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.VerificationCodeInput;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.main.bean.LoginBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CountDownTimerUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

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
                            postequipment();
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
                    ToastUtils.showToast(LoginSecondStepActivity.this,"请阅读并同意遵守学易车法律条款与平台规则");
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
    private void postequipment() {
        String equipment = PrefUtils.getParameter("equipment");
        if (TextUtils.isEmpty(equipment)) {
            Map<String, String> map = new HashMap<>();
            map.put("equipment_id", "" + JPushInterface.getRegistrationID(getApplicationContext()));
            map.put("platform", "1");
            MyHttpUtils.postHttpMessage(AppUrl.equipment, map, SuccessBackBean.class, new RequestCallBack<SuccessBackBean>() {
                @Override
                public void requestSuccess(SuccessBackBean json) {
                    if (1 == json.getCode()) {
                        PrefUtils.putParameter("", JPushInterface.getRegistrationID(getApplicationContext()));
                    }
                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
        }
    }
}
