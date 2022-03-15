package com.xueyiche.zjyk.xueyiche.mine.activities.my_wallet;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.xueyiche.zjyk.xueyiche.constants.bean.MyWalletBean;
import com.xueyiche.zjyk.xueyiche.constants.bean.YinHangKaBean;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.InsertwalletBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by zhanglei on 2016/11/15.
 */
public class CashActivity extends BaseActivity implements View.OnClickListener {

    //提现
    private LinearLayout llBack;
    private TextView tvTitle, tvTiXian, tv_tixian_yue, tv_show_info_msg;
    private Button btCash;
    private String user_id, user_phone;
    private EditText ed_cash_money, ed_bank_card, ed_bank_card_name, ed_bank_card_kaihu;
    private String my_money;

    @Override
    protected int initContentView() {
        return R.layout.cash_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.cash_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.cash_include).findViewById(R.id.tv_login_back);
        tvTiXian = (TextView) view.findViewById(R.id.tv_all_cash);
        tv_tixian_yue = (TextView) view.findViewById(R.id.tv_tixian_yue);
        tv_show_info_msg = (TextView) view.findViewById(R.id.tv_show_info_msg);
        ed_bank_card = (EditText) view.findViewById(R.id.ed_bank_card);
        ed_cash_money = (EditText) view.findViewById(R.id.ed_cash_money);
        ed_bank_card_kaihu = (EditText) view.findViewById(R.id.ed_bank_card_kaihu);
        ed_bank_card_name = (EditText) view.findViewById(R.id.ed_bank_card_name);
        btCash = (Button) view.findViewById(R.id.bt_cash);
        tvTiXian.setOnClickListener(this);
        llBack.setOnClickListener(this);
        btCash.setOnClickListener(this);

    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initData() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        user_phone = PrefUtils.getString(App.context, "user_phone", "");
        tvTitle.setText("余额提现");
        ed_cash_money.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        ed_cash_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        ed_cash_money.setText(s);
                        ed_cash_money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    ed_cash_money.setText(s);
                    ed_cash_money.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        ed_cash_money.setText(s.subSequence(0, 1));
                        ed_cash_money.setSelection(1);
                        return;
                    }
                }
                String trim = ed_cash_money.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    BigDecimal b1 = new BigDecimal(trim);
                    BigDecimal b2 = new BigDecimal(my_money);
                    int one = b1.compareTo(b2);

                    if (one == 1) {
                        tv_tixian_yue.setText("余额不足，无法提现");
                    } else if (one == 0 || one == -1) {

                        tv_tixian_yue.setText("我的金额 ¥" + my_money);


                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(user_id)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Get_Wallet_bank)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final YinHangKaBean yinHangKaBean = JsonUtil.parseJsonToBean(string, YinHangKaBean.class);
                        if (yinHangKaBean != null) {
                            YinHangKaBean.ContentBean content = yinHangKaBean.getContent();
                            if (content != null) {

                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        final String user_card_no = yinHangKaBean.getContent().getUser_card_no();
                                        final String user_card_per = yinHangKaBean.getContent().getUser_card_per();
                                        final String user_card_bank = yinHangKaBean.getContent().getUser_card_bank();
                                        if (!TextUtils.isEmpty(user_card_no)) {
                                            ed_bank_card.setText(user_card_no);
                                        }
                                        if (!TextUtils.isEmpty(user_card_bank)) {
                                            ed_bank_card_kaihu.setText(user_card_bank);
                                        }
                                        if (!TextUtils.isEmpty(user_card_per)) {
                                            ed_bank_card_name.setText(user_card_per);
                                        }
                                    }
                                });
                            }
                        }
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
        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(user_id)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.WDQB)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {

                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        MyWalletBean myWalletBean = JsonUtil.parseJsonToBean(string, MyWalletBean.class);
                        if (myWalletBean != null) {
                            MyWalletBean.ContentBean content = myWalletBean.getContent();
                            my_money = content.getCurrent_balance();
                        }
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_tixian_yue.setText("我的金额 ¥" + my_money);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_all_cash:
                ed_cash_money.setText(my_money);
                break;
            case R.id.bt_cash:
                String cash_money = ed_cash_money.getText().toString().trim();
                String bank_card = ed_bank_card.getText().toString().trim();
                String bank_card_name = ed_bank_card_name.getText().toString().trim();
                String bank_card_kaihu = ed_bank_card_kaihu.getText().toString().trim();
                if (TextUtils.isEmpty(bank_card_kaihu)) {
                    showToastShort("请输入开户行");
                    return;
                }
                if (TextUtils.isEmpty(bank_card)) {
                    showToastShort("请输入银行卡号");
                    return;
                }
                if (TextUtils.isEmpty(bank_card_name)) {
                    showToastShort("请输入持卡人姓名");
                    return;
                }
                if (TextUtils.isEmpty(cash_money)) {
                    showToastShort("请输入提现金额");
                    return;
                }
                BigDecimal b1 = new BigDecimal(cash_money);
                BigDecimal b2 = new BigDecimal(my_money);
                int one = b1.compareTo(b2);
                if (one == 1) {
                    tv_tixian_yue.setText("余额不足，无法提现");
                    showToastShort("余额不足，无法提现");
                } else if (one == 0 || one == -1) {
                    tv_tixian_yue.setText("我的金额 ¥" + my_money);
                    if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(user_id)) {
                        showProgressDialog(false);
                        OkHttpUtils.post().url(AppUrl.Insert_wallet)
                                .addParams("user_id", user_id)
                                .addParams("device_id", LoginUtils.getId(this))
                                .addParams("user_card_no", bank_card)
                                .addParams("user_card_per", bank_card_name)
                                .addParams("money_in", cash_money)
                                .addParams("user_card_bank", bank_card_kaihu)
                                .addParams("user_phone", user_phone)
                                .build().execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws IOException {

                                String string = response.body().string();
                                if (!TextUtils.isEmpty(string)) {
                                    InsertwalletBean insertwalletBean = JsonUtil.parseJsonToBean(string, InsertwalletBean.class);
                                    final int code = insertwalletBean.getCode();
                                    final String content = insertwalletBean.getContent();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200 == code) {
                                                openActivityThenKill(CashContent.class);
                                            } else {
                                                if (TextUtils.isEmpty(content)) {
                                                    showToastShort(content);
                                                }
                                            }
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
                }

                break;
        }
    }
}
