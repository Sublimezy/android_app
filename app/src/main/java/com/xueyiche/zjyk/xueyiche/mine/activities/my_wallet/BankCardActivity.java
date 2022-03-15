package com.xueyiche.zjyk.xueyiche.mine.activities.my_wallet;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.MessageComeBack;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by zhanglei on 2016/11/15.
 */
public class BankCardActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle;
    private Button bt_bangding;
    private EditText ed_bank_name, ed_bank_card,ed_bank_kaihuhang;


    @Override
    protected int initContentView() {
        return R.layout.bank_card_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.bank_card_include).findViewById(R.id.ll_bianji_back);
        tvTitle = (TextView) view.findViewById(R.id.bank_card_include).findViewById(R.id.mine_tv_title);
        bt_bangding = (Button) view.findViewById(R.id.bt_bangding);
        ed_bank_name = (EditText) view.findViewById(R.id.ed_bank_name);
        ed_bank_kaihuhang = (EditText) view.findViewById(R.id.ed_bank_kaihuhang);
        ed_bank_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        ed_bank_card = (EditText) view.findViewById(R.id.ed_bank_card);
        llBack.setOnClickListener(this);
        bt_bangding.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("添加银行卡");


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bianji_back:
                finish();
                break;
            case R.id.bt_bangding:
                final String bankName, bankCard,bankkaihuhang;
                bankName = ed_bank_name.getText().toString().trim();
                bankCard = ed_bank_card.getText().toString().trim();
                bankkaihuhang = ed_bank_kaihuhang.getText().toString().trim();
                if (!TextUtils.isEmpty(bankName) && StringUtils.checkBankCard(bankCard)) {
                    String user_phone = PrefUtils.getString(App.context, "user_phone", "");
                    if (XueYiCheUtils.IsHaveInternet(this)&&!TextUtils.isEmpty(user_phone)) {
                        showProgressDialog(false);
                        OkHttpUtils.post().url(AppUrl.SCBDYHK)
                                .addParams("user_phone", user_phone)
                                .addParams("user_card_per", bankName)
                                .addParams("user_card_no", bankCard)
                                .addParams("user_driver", "1")
                                .build().execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws IOException {

                                String string = response.body().string();
                                if (!TextUtils.isEmpty(string)) {
                                    MessageComeBack messageComeBack = JsonUtil.parseJsonToBean(string, MessageComeBack.class);
                                    final String msg = messageComeBack.getMsg();
                                    final int code = messageComeBack.getCode();

                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (0==code) {
                                                startActivity(new Intent(App.context, CashActivity.class));
                                                finish();
                                            }
                                            showToastShort(msg);
                                        }
                                    });
                                }
                                return string;
                            }

                            @Override
                            public void onError(Request request, Exception e) {
                                stopProgressDialog();
                            }

                            @Override
                            public void onResponse(Object response) {
                                stopProgressDialog();
                            }
                        });
                    }

                } else if (!TextUtils.isEmpty(bankName) && TextUtils.isEmpty(bankCard)) {
                    showToastShort("请填写银行卡号");
                } else if (TextUtils.isEmpty(bankName)&& !TextUtils.isEmpty(bankCard)) {
                    showToastShort("请填写持卡人姓名");
                }else if (TextUtils.isEmpty(bankName) && TextUtils.isEmpty(bankCard)){
                    showToastShort("请填写具体信息");
                }else if (!TextUtils.isEmpty(bankName) && !TextUtils.isEmpty(bankCard)&& !StringUtils.checkBankCard(bankCard)){
                    showToastShort("银行卡号有误，请重新填写");
                }
                break;
        }
    }


}
