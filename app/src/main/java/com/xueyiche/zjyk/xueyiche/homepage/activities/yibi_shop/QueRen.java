package com.xueyiche.zjyk.xueyiche.homepage.activities.yibi_shop;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.YiBiSelectLocationBean;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by ZL on 2017/10/18.
 */
public class QueRen extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private LinearLayout ll_pay;
    private TextView tvTitle;
    private TextView  tv_indent_name, tv_indent_yibi, tv_pay_yibi;
    private MClearEditText ed_name, ed_phone, ed_location;
    private ImageView iv_indent_head;
    private Button bt_xiadan;
    private String user_id;
    private String em_id;
    private String em_det_content;
    private String em_name;
    private String em_pic_url;
    private String em_goods_money;

    @Override
    protected int initContentView() {
        return R.layout.queren;
    }

    @Override
    protected void initView() {

        llBack = (LinearLayout) view.findViewById(R.id.yibi_queren_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.yibi_queren_include).findViewById(R.id.tv_login_back);

        //收货人姓名
        ed_name = (MClearEditText) view.findViewById(R.id.ed_name);
        //收货人dianhua
        ed_phone = (MClearEditText) view.findViewById(R.id.ed_phone);
        //收货人地址
        ed_location = (MClearEditText) view.findViewById(R.id.ed_location);
        //头像
        iv_indent_head = (ImageView) view.findViewById(R.id.iv_indent_head);
        //名字
        tv_indent_name = (TextView) view.findViewById(R.id.tv_indent_name);
        //易币
        tv_indent_yibi = (TextView) view.findViewById(R.id.tv_indent_yibi);
        //下面支付的
        tv_pay_yibi = (TextView) view.findViewById(R.id.tv_pay_yibi);
        //下单
        bt_xiadan = (Button) view.findViewById(R.id.bt_xiadan);
        llBack.setOnClickListener(this);
        bt_xiadan.setOnClickListener(this);
        user_id = PrefUtils.getString(App.context, "user_id", "");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("确认下单");
        Intent intent = getIntent();
        em_id = intent.getStringExtra("em_id");
        em_det_content = intent.getStringExtra("em_det_content");
        em_name = intent.getStringExtra("em_name");
        em_pic_url = intent.getStringExtra("em_pic_url");
        em_goods_money = intent.getStringExtra("em_goods_money");
        if (!TextUtils.isEmpty(em_name)) {
            tv_indent_name.setText(em_name);
        }
        if (!TextUtils.isEmpty(em_goods_money)) {
            tv_indent_yibi.setText(em_goods_money+"易币");
            tv_pay_yibi.setText(em_goods_money+"易币");
        }
        if (!TextUtils.isEmpty(em_pic_url)) {
            Picasso.with(App.context).load(em_pic_url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_indent_head);
        }
        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.YIBISHOPINDENTLOCATION).addParams("user_id",user_id).build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final YiBiSelectLocationBean yiBiSelectLocationBean = JsonUtil.parseJsonToBean(string, YiBiSelectLocationBean.class);
                        if (yiBiSelectLocationBean!=null) {
                            final int code = yiBiSelectLocationBean.getCode();
                            final String msg = yiBiSelectLocationBean.getMsg();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (0==code) {
                                        YiBiSelectLocationBean.ContentBean content = yiBiSelectLocationBean.getContent();
                                        if (content!=null) {
                                            String em_user_name = content.getEm_user_name();
                                            String em_phone = content.getEm_phone();
                                            String em_address = content.getEm_address();
                                            if (!TextUtils.isEmpty(em_user_name)) {
                                                ed_name.setText(em_user_name);
                                            }
                                            if (!TextUtils.isEmpty(em_user_name)) {
                                                ed_phone.setText(em_phone);
                                            }
                                            if (!TextUtils.isEmpty(em_user_name)) {
                                                ed_location.setText(em_address);
                                            }
                                        }
                                    }else {
                                        showToastShort(msg);
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

                }
            });
        }else {
            showToastShort(StringConstants.CHECK_NET);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.bt_xiadan:
                String name = ed_name.getText().toString().trim();
                String phone = ed_phone.getText().toString().trim();
                String location = ed_location.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showToastShort("请填写收货姓名");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showToastShort("请填写收货电话");
                    return;
                }
                if (TextUtils.isEmpty(location)) {
                    showToastShort("请填写收货地址");
                    return;
                }
                xidan(name,phone,location);
                break;
        }
    }

    private void xidan(String name, String phone, String location) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.QianDao)
                    .addParams("user_id", user_id)
                    .addParams("em_detail_type", "2")
                    .addParams("em_id", em_id)
                    .addParams("em_number", "1")
                    .addParams("em_phone", phone)
                    .addParams("em_user_name", name)
                    .addParams("em_address", location)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final SuccessBackBean duiHuanBean = JsonUtil.parseJsonToBean(string, SuccessBackBean.class);
                        if (duiHuanBean != null) {
                            final int code = duiHuanBean.getCode();
                            final String content = duiHuanBean.getContent();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    showToastShort(content);
                                    if (0 == code) {
                                        openActivity(YiBiIndent.class);
                                        finish();
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

                }
            });
        } else {
            showToastShort(StringConstants.CHECK_NET);
        }
    }
}
