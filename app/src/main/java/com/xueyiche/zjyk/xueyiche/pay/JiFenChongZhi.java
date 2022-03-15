package com.xueyiche.zjyk.xueyiche.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.WXZhiFuBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.pay.bean.ZhiFuBaoBean;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PayResult;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2017/1/12.
 */
public class JiFenChongZhi extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private LinearLayout llBack;
    private TextView tvTitle;
    private RadioGroup rg_one, rg_two;
    private RadioButton rb_shi, rb_ershi, rb_sanshi, rb_wushi, rb_yibai, rb_erbai;
    private String money;
    private String YiBi;
    private String isClick = "3";
    private Button bt_pay;
    private ImageView ivWechat, ivZhifubao, iv_post;
    private RelativeLayout rl_pay_zhifubao, rl_pay_wechat, rl_pay_pos;
    //RadioButton实现多行单选
    String strBtnSelected = "unInit";
    private String string;
    private ImageView iv_yinlian;
    private View rl_indent_yinlian;
    private String user_id;


    public class BtnSelected implements View.OnClickListener {
        public BtnSelected(String str) {
            bntID = str;
        }

        final public String bntID;

        @Override
        public void onClick(View arg0) {
            strBtnSelected = bntID;
            switch (arg0.getId()) {
                case R.id.rb_shi:
                    money = "10";
                    YiBi = "2000";
                    bt_pay.setBackgroundResource(R.drawable.my_wallet_back);
                    break;
                case R.id.rb_ershi:
                    money = "20";
                    YiBi = "4000";
                    bt_pay.setBackgroundResource(R.drawable.my_wallet_back);
                    break;
                case R.id.rb_sanshi:
                    money = "30";
                    YiBi = "6000";
                    bt_pay.setBackgroundResource(R.drawable.my_wallet_back);
                    break;
                case R.id.rb_wushi:
                    money = "50";
                    YiBi = "10000";
                    bt_pay.setBackgroundResource(R.drawable.my_wallet_back);
                    break;
                case R.id.rb_yibai:
                    money = "100";
                    YiBi = "20000";
                    bt_pay.setBackgroundResource(R.drawable.my_wallet_back);
                    break;
                case R.id.rb_erbai:
                    money = "200";
                    YiBi = "40000";
                    bt_pay.setBackgroundResource(R.drawable.my_wallet_back);
                    break;

            }


            if (bntID.equals("1") || bntID.equals("2") || bntID.equals("3")) {
                rg_two.clearCheck();
            } else if (bntID.equals("4") || bntID.equals("5")
                    || bntID.equals("6")) {
                rg_one.clearCheck();
            }
        }
    }

    ;
    BtnSelected btnListener1 = new BtnSelected("1");
    BtnSelected btnListener2 = new BtnSelected("2");
    BtnSelected btnListener3 = new BtnSelected("3");
    BtnSelected btnListener4 = new BtnSelected("4");
    BtnSelected btnListener5 = new BtnSelected("5");
    BtnSelected btnListener6 = new BtnSelected("6");

    //一直到这
    @Override
    protected int initContentView() {
        return R.layout.jifen_chongzhi;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        tvTitle = (TextView) view.findViewById(R.id.jifen_chongzhi_include).findViewById(R.id.tv_login_back);
        llBack = (LinearLayout) view.findViewById(R.id.jifen_chongzhi_include).findViewById(R.id.ll_exam_back);
        //支付宝
        rl_pay_zhifubao = (RelativeLayout) view.findViewById(R.id.rl_zhifubao);
        rl_pay_pos = (RelativeLayout) view.findViewById(R.id.rl_pos);
        ivZhifubao = (ImageView) view.findViewById(R.id.iv_zhifubao);
        iv_post = (ImageView) view.findViewById(R.id.iv_post);
        //微信
        rl_pay_wechat = (RelativeLayout) view.findViewById(R.id.rl_wechat);
        ivWechat = (ImageView) view.findViewById(R.id.iv_wechat);
        rb_shi = (RadioButton) view.findViewById(R.id.rb_shi);
        bt_pay = (Button) view.findViewById(R.id.bt_pay);
        bt_pay.setBackgroundResource(R.drawable.my_wallet_back_gray);
        rb_ershi = (RadioButton) view.findViewById(R.id.rb_ershi);
        rb_sanshi = (RadioButton) view.findViewById(R.id.rb_sanshi);
        rb_wushi = (RadioButton) view.findViewById(R.id.rb_wushi);
        rb_yibai = (RadioButton) view.findViewById(R.id.rb_yibai);
        rb_erbai = (RadioButton) view.findViewById(R.id.rb_erbai);
        rl_indent_yinlian = (RelativeLayout) view.findViewById(R.id.rl_yinlian);
        rl_indent_yinlian.setOnClickListener(this);
        iv_yinlian = (ImageView) view.findViewById(R.id.iv_yinlian);
        rg_one = (RadioGroup) view.findViewById(R.id.rg_one);
        rg_two = (RadioGroup) view.findViewById(R.id.rg_two);
        llBack.setOnClickListener(this);
        bt_pay.setOnClickListener(this);
        rl_pay_wechat.setOnClickListener(this);
        rl_pay_zhifubao.setOnClickListener(this);
        rl_pay_pos.setOnClickListener(this);
        tvTitle.setText("积分充值");
        ivWechat.setImageResource(R.mipmap.pay_check);
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
            DialogUtils.showGetJiFen(JiFenChongZhi.this, YiBi);
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        user_id = PrefUtils.getString(this, "user_id", "");
        rg_one.setOnCheckedChangeListener(this);
        rg_two.setOnCheckedChangeListener(this);
        //为每个RadioButton设置监听器
        rb_shi.setOnClickListener(btnListener1);
        rb_ershi.setOnClickListener(btnListener2);
        rb_sanshi.setOnClickListener(btnListener3);
        rb_wushi.setOnClickListener(btnListener4);
        rb_yibai.setOnClickListener(btnListener5);
        rb_erbai.setOnClickListener(btnListener6);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rl_zhifubao:
                ivWechat.setVisibility(View.GONE);
                ivZhifubao.setVisibility(View.VISIBLE);
                iv_post.setVisibility(View.GONE);
                iv_yinlian.setVisibility(View.GONE);
                isClick = "2";
                break;
            case R.id.rl_wechat:
                ivZhifubao.setVisibility(View.GONE);
                ivWechat.setVisibility(View.VISIBLE);
                iv_post.setVisibility(View.GONE);
                iv_yinlian.setVisibility(View.GONE);
                isClick = "3";
                break;
            case R.id.rl_pos:
//                ivZhifubao.setImageResource(R.drawable.indent_content_no);
//                ivWechat.setImageResource(R.drawable.indent_content_no);
//                iv_post.setImageResource(R.drawable.indent_content_ok);
                //           isClick = "1";
                showToastShort("暂未开通");
                break;
            case R.id.rl_yinlian:
//                ivZhifubao.setVisibility(View.GONE);
//                ivWechat.setVisibility(View.GONE);
//                iv_post.setVisibility(View.GONE);
//                iv_yinlian.setVisibility(View.VISIBLE);
//                isClick = "4";
                showToastShort("暂未开通");
                break;
            case R.id.bt_pay:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    fukuan();
                }
                break;
        }
    }

    private void fukuan() {
        if ("1".equals(isClick)) {
            pos();
        } else if ("2".equals(isClick)) {
            zfb();
        } else if ("3".equals(isClick)) {
            if (XueYiCheUtils.isWeixinAvilible(this)) {
                wx_jifen();
            } else {
                showToastShort("目前您的微信版本过低或未安装微信，需要安装微信才能使用");
            }
        } else if ("4".equals(isClick)) {
            showToastShort("暂未开通");
            //PayUtils.UnionPay(JiFenChongZhi.this, "", money);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         *
         * 处理银联手机支付控件返回的支付结果
         *
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
   /*
    * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
    */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            DialogUtils.showGetJiFen(JiFenChongZhi.this, YiBi);
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }
        showToastShort(msg);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void pos() {
        showToastShort("暂未开通");
    }

    private void zfb() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            if (!TextUtils.isEmpty(money)) {
                OkHttpUtils.post().url(AppUrl.ChongZhi_JiFen)
                        .addParams("device_id", LoginUtils.getId(this))
                        .addParams("user_id", user_id)
                        .addParams("face_value", money)
                        .addParams("pay_type_id", "1")
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
                        zfbeb(string, JiFenChongZhi.this, JiFenChongZhi.this, YiBi);

                    }
                });
            }

        } else {
            showToastShort("请检查网络");
        }


    }

    public void wx_jifen() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            final WXZhiFuBean.ContentBean[] content = new WXZhiFuBean.ContentBean[1];
            if (!TextUtils.isEmpty(money)) {
                OkHttpUtils.post().url(AppUrl.ChongZhi_JiFen)
                        .addParams("device_id", LoginUtils.getId(this))
                        .addParams("user_id", user_id)
                        .addParams("face_value", money)
                        .addParams("pay_type_id", "2")
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            WXZhiFuBean wxZhiFuBean = JsonUtil.parseJsonToBean(string, WXZhiFuBean.class);
                            if (wxZhiFuBean != null) {
                                content[0] = wxZhiFuBean.getContent();
                            }
                        }
                        return string;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        if (content[0] != null) {
                            String appid = content[0].getAppid();
                            String partnerid = content[0].getPartnerid();
                            String prepayid = content[0].getPrepayid();
                            String noncestr = content[0].getNoncestr();
                            String timestamp = content[0].getTimestamp();
                            String packageValue = content[0].getPackageValue();
                            String sign = content[0].getSign();
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
            }
        } else {
            Toast.makeText(App.context, "请检查网络", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * E币支付
     *
     * @param string
     * @param context
     * @param activity
     * @param subscription
     */
    public void zfbeb(final String string, final Context context, final Activity activity, final String subscription) {
        final int SDK_PAY_FLAG = 1;
        @SuppressLint("HandlerLeak")
        final Handler mHandler = new Handler() {
            @SuppressWarnings("unused")
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            DialogUtils.showGetJiFen(activity, subscription);
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(context, "支付失败,请重新下单", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    default:
                        break;
                }
            }

        };

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(string)) {
                    ZhiFuBaoBean zhiFuBaoBean = JsonUtil.parseJsonToBean(string, ZhiFuBaoBean.class);
                    if (zhiFuBaoBean != null) {
                        String content = zhiFuBaoBean.getContent();
                        if (!TextUtils.isEmpty(content)) {
                            PayTask alipay = new PayTask(activity);
                            Map<String, String> result = alipay.payV2(content, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    }
                }
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
