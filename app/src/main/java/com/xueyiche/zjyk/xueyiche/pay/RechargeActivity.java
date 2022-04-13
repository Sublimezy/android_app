package com.xueyiche.zjyk.xueyiche.pay;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.WXZhiFuBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PayUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;

import de.greenrobot.event.EventBus;

/**
 * Created by zhanglei on 2016/11/15.
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    //钱包充值
    private LinearLayout llBack;
    private TextView tvTitle;
    private EditText ed_recharge_money;
    private Button bt_ok_recharge;
    private RelativeLayout rl_recharge_zhifubao, rl_recharge_wechat,rl_recharge_pos;
    private ImageView iv_zhifubao_no, iv_wechat_no,iv_post;
    private String isClick = "2";
    private String string;
    private String user_phone;
    private String user_name;
    private WXZhiFuBean.ContentBean content;
    private Long multiply;
    private ImageView iv_yinlian;
    private View rl_indent_yinlian;

    @Override
    protected int initContentView() {
        return R.layout.recharge_activity;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        user_phone = PrefUtils.getString(App.context, "user_phone", "");
        user_name = PrefUtils.getString(App.context, "user_name", "");
        llBack = (LinearLayout) view.findViewById(R.id.recharge_include).findViewById(R.id.ll_bianji_back);
        tvTitle = (TextView) view.findViewById(R.id.recharge_include).findViewById(R.id.mine_tv_title);
        ed_recharge_money = (EditText) view.findViewById(R.id.ed_recharge_money);
        bt_ok_recharge = (Button) view.findViewById(R.id.bt_ok_recharge);
        rl_recharge_zhifubao = (RelativeLayout) view.findViewById(R.id.rl_recharge_zhifubao);
        rl_recharge_wechat = (RelativeLayout) view.findViewById(R.id.rl_recharge_wechat);
        rl_recharge_pos = (RelativeLayout) view.findViewById(R.id.rl_recharge_pos);
        iv_zhifubao_no = (ImageView) view.findViewById(R.id.iv_zhifubao_no);
        iv_post = (ImageView) view.findViewById(R.id.iv_post);
        iv_wechat_no = (ImageView) view.findViewById(R.id.iv_wechat_no);
        rl_indent_yinlian = (RelativeLayout) view.findViewById(R.id.rl_yinlian);
        rl_indent_yinlian.setOnClickListener(this);
        iv_yinlian = (ImageView) view.findViewById(R.id.iv_yinlian);
        llBack.setOnClickListener(this);
        bt_ok_recharge.setOnClickListener(this);
        rl_recharge_zhifubao.setOnClickListener(this);
        rl_recharge_pos.setOnClickListener(this);
        rl_recharge_wechat.setOnClickListener(this);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("支付成功", msg)) {
           // XueYiCheUtils.showGetEB(this, YiBi,JiFenChongZhi.this);
            Toast.makeText(this, "充值成功", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("充值");
        ed_recharge_money.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        ed_recharge_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                //只能显示小数点后两位
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        ed_recharge_money.setText(s);
                        ed_recharge_money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    ed_recharge_money.setText(s);
                    ed_recharge_money.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        ed_recharge_money.setText(s.subSequence(0, 1));
                        ed_recharge_money.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bianji_back:
                finish();
                break;
            case R.id.rl_recharge_zhifubao:
                iv_zhifubao_no.setImageResource(R.mipmap.recharge_ok);
                iv_wechat_no.setImageResource(R.mipmap.recharge_no);
                iv_post.setImageResource(R.mipmap.recharge_no);
                isClick = "2";
                break;
            case R.id.rl_recharge_wechat:
                iv_zhifubao_no.setImageResource(R.mipmap.recharge_no);
                iv_wechat_no.setImageResource(R.mipmap.recharge_ok);
                iv_post.setImageResource(R.mipmap.recharge_no);
                isClick = "3";
                break;
            case R.id.rl_recharge_pos:
//                iv_zhifubao_no.setImageResource(R.mipmap.recharge_no);
//                iv_wechat_no.setImageResource(R.mipmap.recharge_no);
//                iv_post.setImageResource(R.mipmap.recharge_ok);
//                isClick = "1";
                showToastShort("暂未开通");
                break;
            case R.id.rl_yinlian:
//                ivZhifubao.setImageResource(R.drawable.indent_content_no);
//                ivWechat.setImageResource(R.drawable.indent_content_no);
//                iv_post.setImageResource(R.drawable.indent_content_no);
//                iv_yinlian.setImageResource(R.drawable.indent_content_ok);
//                isClick = "4";
                showToastShort("暂未开通");
                break;
            case R.id.bt_ok_recharge:

                String trim = ed_recharge_money.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    BigDecimal b1 = new BigDecimal(trim);
                    String b4 = b1 + "";
                    multiply = (long) Double.parseDouble(b4);
                }
                if (!TextUtils.isEmpty(trim)) {
                   // wzf();
                    fukuan();

                } else {
                    showToastShort("请输入付款金额");
                }

                break;


        }
    }

    private void fukuan() {
        if ("1".equals(isClick)) {
            pos();
        } else if ("2".equals(isClick)) {
            zfb();
        }else if ("3".equals(isClick)){
            if (XueYiCheUtils.isWeixinAvilible(this)) {
                wx();
            }else {
                showToastShort("目前您的微信版本过低或未安装微信，需要安装微信才能使用");
            }
        }
    }

    private void pos() {
        showToastShort("暂未开通");
    }

    private void wx() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.JYZFB)
                    .addParams("order_type", "8")
                    .addParams("user_phone", user_phone)
                    .addParams("user_name", user_name)
                    .addParams("service_money", multiply+"")
                    .addParams("paytype", "wx")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        WXZhiFuBean wxZhiFuBean = JsonUtil.parseJsonToBean(string, WXZhiFuBean.class);
                        if (wxZhiFuBean != null) {
                            content = wxZhiFuBean.getContent();
                        }
                    }
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(Object response) {
                    if (content != null) {
                        String appid = content.getAppid();
                        String partnerid = content.getPartnerid();
                        String prepayid = content.getPrepayid();
                        String noncestr = content.getNoncestr();
                        String timestamp = content.getTimestamp();
                        String packageValue = content.getPackageValue();
                        String sign = content.getSign();
                        PayReq req = new PayReq();
                        req.appId = appid;
                        req.partnerId = partnerid;
                        req.prepayId = prepayid;
                        req.nonceStr = noncestr;
                        req.timeStamp = timestamp;
                        req.packageValue = packageValue;
                        req.sign = sign;
                        App.wxapi.sendReq(req);
                    }
                }
            });
        } else {
            showToastShort("请检查网络");
        }

    }

    private void zfb() {

        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(user_phone)) {
            OkHttpUtils.post().url(AppUrl.JYZFB)
                    .addParams("order_type", "8")
                    .addParams("user_phone", user_phone)
                    .addParams("user_name", user_name)
                    .addParams("service_money", multiply+"")
                    .addParams("paytype", "zfb")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    string = response.body().string();
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {
                }

                @Override
                public void onResponse(Object response) {
//                    PayUtils.zfb(string, RechargeActivity.this, RechargeActivity.this,"" ,"","","");
                }
            });
        }else {
            showToastShort("请输入提现金额");
        }
    }

}
