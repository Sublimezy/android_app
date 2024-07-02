package com.gxuwz.zy.mine.activities.bianji;

import android.content.Intent;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseActivity;
import com.gxuwz.zy.constants.App;
import com.gxuwz.zy.constants.AppUrl;
import com.gxuwz.zy.mine.entity.dos.BaseResponseBean;
import com.gxuwz.zy.mine.view.MClearEditText;
import com.gxuwz.zy.myhttp.MyHttpUtils;
import com.gxuwz.zy.myhttp.RequestCallBack;
import com.gxuwz.zy.utils.AES;
import com.gxuwz.zy.utils.PrefUtils;
import com.gxuwz.zy.utils.StringUtils;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;
import java.util.Map;


public class InformationChangeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private MClearEditText ed_information;
    private TextView tv_ok;
    private TextView tvTitle;
    private AES mAes;
    private LinearLayout ll_title;
    private String change_information, user_name, user_phone, user_cards, id_card;


    @Override
    protected int initContentView() {
        return R.layout.name_activity;
    }

    @Override
    protected void initView() {
        mAes = new AES();
        llBack = view.findViewById(R.id.bianji_title_include).findViewById(R.id.ll_exam_back);
        tv_ok = view.findViewById(R.id.bianji_title_include).findViewById(R.id.tv_wenxintishi);
        tvTitle = view.findViewById(R.id.bianji_title_include).findViewById(R.id.tv_login_back);
        ed_information = view.findViewById(R.id.ed_name);
        ll_title = view.findViewById(R.id.ll_title);
        llBack.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        tv_ok.setVisibility(View.VISIBLE);
        tv_ok.setTextColor(getResources().getColor(R.color.test_color));
        ImmersionBar.with(this).titleBar(ll_title).statusBarDarkFont(true).init();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //name
        //card_num
        Intent intent = getIntent();
        change_information = intent.getStringExtra("change_information");
        if (!TextUtils.isEmpty(change_information)) {
            if ("name".equals(change_information)) {
                String nickname = PrefUtils.getString(App.context, "name", "");
                if (!TextUtils.isEmpty(nickname)) {
                    ed_information.setText(nickname);
                    ed_information.setSelection(nickname.length());
                } else {
                    ed_information.setText("");
                }
                SpannableString name = new SpannableString("请输入姓名");//定义hint的值
                ed_information.setHint(name);
                tvTitle.setText("姓名");
            } else if ("id_card".equals(change_information)) {
                tvTitle.setText("身份证");
                String user_cards = PrefUtils.getString(App.context, "card_num", "");
                String decrypt_user_cards = AES.decrypt(user_cards);
                if (!TextUtils.isEmpty(user_cards)) {
                    ed_information.setText(decrypt_user_cards);
                    ed_information.setSelection(decrypt_user_cards.length());

                } else {
                    ed_information.setText("");
                }
                SpannableString name = new SpannableString("请输入身份证号");//定义hint的值
                ed_information.setHint(name);
            } else if ("phone".equals(change_information)) {
                tvTitle.setText("手机号");
                String user_phone = PrefUtils.getString(App.context, "user_phone", "");
                String decrypt_user_cards = AES.decrypt(user_phone);
                if (!TextUtils.isEmpty(user_phone)) {
                    ed_information.setText(decrypt_user_cards);
                    ed_information.setSelection(decrypt_user_cards.length());
                } else {
                    ed_information.setText("");
                }
                SpannableString name = new SpannableString("请输入手机号");//定义hint的值
                ed_information.setHint(name);
            }
        }

        tv_ok.setText("保存");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:

                String content = ed_information.getText().toString().trim();
                if (!TextUtils.isEmpty(change_information)) {
                    if ("name".equals(change_information)) {
                        user_name = content;
                        user_phone = "";
                        user_cards = "";
                    } else if ("id_card".equals(change_information)) {
                        user_name = "";
                        user_phone = "";
                        String encrypt = AES.encrypt(content);
                        user_cards = encrypt;
                        id_card = content;

                    } else if ("phone".equals(change_information)) {
                        user_name = "";
                        String encrypt = AES.encrypt(content);
                        user_phone = encrypt;
                        user_cards = "";
                    }
                }

                if ("id_card".equals(change_information)) {
                    if (StringUtils.isIdCard(id_card)) {
                        sendMess();
                    } else {
                        Toast.makeText(InformationChangeActivity.this, "身份证格式不正确", Toast.LENGTH_SHORT).show();
                    }
                } else if ("name".equals(change_information)) {
                    int length = user_name.length();
                    if (length > 5) {
                        Toast.makeText(InformationChangeActivity.this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
                    } else {
                        sendMess();
                    }
                } else {
                    sendMess();
                }

                break;
        }
    }

    private void sendMess() {
        Map<String, String> params = new HashMap<>();
        params.put("name", user_name);
        params.put("card_num", user_cards);
        MyHttpUtils.postHttpMessage(AppUrl.userInfoEditIdentity, params, BaseResponseBean.class, new RequestCallBack<BaseResponseBean>() {
            @Override
            public void requestSuccess(BaseResponseBean json) {
                if (json.getCode() == 1) {
                    if ("id_card".equals(change_information)) {
                        PrefUtils.putParameter("card_num", user_cards);

                    } else if ("name".equals(change_information)) {
                        PrefUtils.putParameter("name", user_name);

                    }
                    finish();
                } else {

                }
                showToastShort(json.getMsg());
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });

    }
}
