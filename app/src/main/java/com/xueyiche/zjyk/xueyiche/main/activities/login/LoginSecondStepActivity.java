package com.xueyiche.zjyk.xueyiche.main.activities.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.CommonWebView;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.UserInfo;
import com.xueyiche.zjyk.xueyiche.constants.bean.YanZhengMa;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.homepage.view.VerificationCodeInput;
import com.xueyiche.zjyk.xueyiche.mine.view.CountDownTimerUtils;
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
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2018/2/6.
 */
public class LoginSecondStepActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvGetPassWord;
    private TextView tvPhone;
    private TextView tv_user_xieyi;
    private CountDownTimerUtils countDownTimer;
    private AES mAes;
    private String phone;
    private Button btLogin;
    private VerificationCodeInput input;
    private String yanzhengma;
    private LinearLayout ll_exam_back;

    @Override
    protected int initContentView() {
        return R.layout.login_second_step;
    }

    @Override
    protected void initView() {
        tvGetPassWord = (TextView) view.findViewById(R.id.tvGetPassWord);
        tv_user_xieyi = (TextView) view.findViewById(R.id.tv_user_xieyi);
        tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.shop_top_include).findViewById(R.id.ll_exam_back);
        input = (VerificationCodeInput) findViewById(R.id.verificationCodeInput);
        btLogin = (Button) view.findViewById(R.id.btLogin);


    }

    @Override
    protected void initListener() {
        countDownTimer = new CountDownTimerUtils(tvGetPassWord, 60000, 1000);
        tvGetPassWord.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        tv_user_xieyi.setOnClickListener(this);

        input.setOnCompleteListener(new VerificationCodeInput.Listener() {
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
        mAes = new AES();
        getPassWord();

    }

    //获取验证码
    private void getPassWord() {
        countDownTimer.start();
        String enString = mAes.encrypt(phone);
        if (!TextUtils.isEmpty(enString)) {
            OkHttpUtils.post().url(AppUrl.YANZHENGMA)
                    .addParams("user_phone", enString)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        YanZhengMa yanZhengMa = JsonUtil.parseJsonToBean(string, YanZhengMa.class);
                        if (yanZhengMa != null) {
                            final String content = yanZhengMa.getContent();
                            final int code = yanZhengMa.getCode();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (200 == code) {
                                        Toast.makeText(App.context, content, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }


                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {

                }


                @Override
                public void onResponse(Object response) {
                    tvGetPassWord.setClickable(false);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvGetPassWord:
                getPassWord();
                break;
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_user_xieyi:
                Intent intent = new Intent(App.context, CommonWebView.class);
                intent.putExtra("weburl", "xieyi");
                startActivity(intent);
                break;
            case R.id.btLogin:
                login();
                break;
        }
    }

    private void login() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String enString = mAes.encrypt(phone);
            String id = LoginUtils.getId(LoginSecondStepActivity.this);
            if (!TextUtils.isEmpty(yanzhengma)) {
                String phoneName = android.os.Build.MODEL;
                OkHttpUtils.post().url(AppUrl.LOGIN)
                        .addParams("user_phone", enString)
                        .addParams("device_id", id)
                        .addParams("code_message", yanzhengma)
                        .addParams("device_name", phoneName)
                        .addParams("user_type", "1")
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            final UserInfo userInfo = JsonUtil.parseJsonToBean(string, UserInfo.class);
                            final int code = userInfo.getCode();
                            final String msg = userInfo.getMsg();
                            Map<String, String> map_value = new HashMap<String, String>();
                            map_value.put(msg, msg);
                            MobclickAgent.onEventValue(App.context, "loginmsg", map_value, 12000);
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (200 == code) {
                                        MobclickAgent.onEvent(App.context, "login");
                                        String nickname = userInfo.getContent().getNickname();
                                        String head_img = userInfo.getContent().getHead_img();
                                        String user_phone = userInfo.getContent().getUser_phone();
                                        String user_id = userInfo.getContent().getUser_id();
                                        String first_type = userInfo.getContent().getFirst_type();
                                        String user_name = userInfo.getContent().getUser_name();
                                        String sex = userInfo.getContent().getSex();
                                        String driver_cards = userInfo.getContent().getDriver_cards();
                                        String user_cards = userInfo.getContent().getUser_cards();
                                        PrefUtils.putString(App.context, "user_id", user_id);
                                        PrefUtils.putString(App.context, "user_phone", user_phone);
                                        PrefUtils.putString(App.context, "user_name", user_name);
                                        PrefUtils.putString(App.context, "user_cards", user_cards);
                                        PrefUtils.putString(App.context, "driver_cards", driver_cards);
                                        PrefUtils.putString(App.context, "nickname", nickname);
                                        PrefUtils.putString(App.context, "sex", sex);
                                        PrefUtils.putString(App.context, "head_img", head_img);
                                        //获取系统时间
                                        long time = new Date().getTime();
                                        PrefUtils.putBoolean(App.context, "ISLOGIN", true);
                                        PrefUtils.putString(App.context, "LOGINTIME", time + "");
                                        MobclickAgent.onProfileSignIn(phone);
                                        if (!TextUtils.isEmpty(first_type)) {
                                            //0首次登录
                                            if ("0".equals(first_type)) {
                                                String integral_num = userInfo.getContent().getIntegral_num();
                                                DialogUtils.isFirstLogin(LoginSecondStepActivity.this,integral_num);
                                            }else {
                                                LoginFirstStepActivity.instance.finish();
                                                finish();
                                            }
                                        }

                                    }
                                    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        return string;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        EventBus.getDefault().post(new MyEvent("刷新FragmentLogin"));
                        EventBus.getDefault().post(new MyEvent("刷新进行中订单"));
                        EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                        EventBus.getDefault().post(new MyEvent("刷新Fragment"));


                    }
                });
            } else {
                Toast.makeText(App.context, "验证码不完整", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(App.context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

}
