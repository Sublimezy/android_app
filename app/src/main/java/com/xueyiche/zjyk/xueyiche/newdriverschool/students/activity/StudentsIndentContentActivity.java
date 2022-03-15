package com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.WXZhiFuBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.StudentsIndentContentBean;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.pay.bean.ZhiFuBaoBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PayResult;
import com.xueyiche.zjyk.xueyiche.utils.PayUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 张磊 on 2021/1/18.                                       #
 */
//订单详情
public class StudentsIndentContentActivity extends NewBaseActivity implements View.OnClickListener {
    private LinearLayout llBack, llYaoQingPhone;
    private TextView tvTilte, tvContent, tvName, tvIdCard, tvPhone, tvYaoQingPhone, tvYuanJia, tvYouHui, tvEndMoney, tvPayMoney;
    private RadioButton rb_wechat, rb_alipay;
    private Button btPay;
    private String payType = "2";
    private String driving_order_number;

    @Override
    protected int initContentView() {
        return R.layout.students_indent_content_activity;
    }

    @Override
    protected void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        llBack = view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tvTilte = view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        llYaoQingPhone = view.findViewById(R.id.llYaoQingPhone);
        tvContent = view.findViewById(R.id.tvContent);
        tvName = view.findViewById(R.id.tvName);
        tvIdCard = view.findViewById(R.id.tvIdCard);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvYaoQingPhone = view.findViewById(R.id.tvYaoQingPhone);
        tvYuanJia = view.findViewById(R.id.tvYuanJia);
        tvYouHui = view.findViewById(R.id.tvYouHui);
        tvEndMoney = view.findViewById(R.id.tvEndMoney);
        tvPayMoney = view.findViewById(R.id.tvPayMoney);
        btPay = view.findViewById(R.id.btPay);
        rb_wechat = view.findViewById(R.id.rb_wechat);
        rb_alipay = view.findViewById(R.id.rb_alipay);

    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("支付成功", msg)) {
            Intent intent = new Intent(this, StudentsIndentContentBaoMingSuccessActivity.class);
            StudentsBaoMingActivity.stance.finish();
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        rb_wechat.setOnClickListener(this);
        rb_alipay.setOnClickListener(this);
        btPay.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTilte.setText("订单详情");

        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.orderinfo)
                    .addParams("driving_order_number",""+getIntent().getStringExtra("order_number"))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        Log.e("orderinfo",string);
                        StudentsIndentContentBean studentIndentContentBean = JsonUtil.parseJsonToBean(string, StudentsIndentContentBean.class);
                        if (studentIndentContentBean!=null) {
                            int code = studentIndentContentBean.getCode();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (200==code) {
                                        StudentsIndentContentBean.ContentBean content = studentIndentContentBean.getContent();
                                        if (content!=null) {
                                            tvContent.setText(""+content.getEntry_project());
                                            tvName.setText(""+content.getTrainee_name());
                                            tvIdCard.setText(""+content.getId_number());
                                            tvPhone.setText(""+content.getTrainee_phone());
                                            tvYaoQingPhone.setText(TextUtils.isEmpty(content.getInvent_people())?"无":content.getInvent_people());
                                            tvYuanJia.setText("￥"+content.getCost_money());
                                            tvYouHui.setText("￥"+content.getDiscounted_price());
                                            driving_order_number = content.getDriving_order_number();
                                            BigDecimal oldMoney = new BigDecimal(TextUtils.isEmpty(content.getCost_money())?"0":content.getCost_money());
                                            BigDecimal oyouhuiMoney = new BigDecimal(TextUtils.isEmpty(content.getDiscounted_price())?"0":content.getDiscounted_price());
                                            BigDecimal bignum3 = oldMoney.subtract(oyouhuiMoney);
                                            tvEndMoney.setText("合计：￥"+bignum3);
                                            tvPayMoney.setText("￥"+bignum3);

                                        }
                                    }
                                }
                            });
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
        } else {
            showToastShort("请检查网络连接");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rb_wechat:
                payType = "2";
                break;
            case R.id.rb_alipay:
                payType = "1";
                break;
            case R.id.btPay:
                if ("2".equals(payType)) {
                    wechat();
                } else {
                    zfb();
                }
//                testPay();
                break;
        }
    }

    private void testPay() {
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("trainee_id",PrefUtils.getParameter("user_id"));
        stringMap.put("driving_order_number",driving_order_number);
        stringMap.put("pay_type",payType);
        OkHttpUtils.post().url(AppUrl.paytypedrivingorder).params(stringMap)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            Log.e("paytypedrivingorder",string);
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean!=null) {
                                int code = successDisCoverBackBean.getCode();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (200==code) {
                                            Intent intent = new Intent(StudentsIndentContentActivity.this, StudentsIndentContentBaoMingSuccessActivity.class);
                                            StudentsBaoMingActivity.stance.finish();
                                            startActivity(intent);
                                            finish();
                                        }
                                        showToastShort(successDisCoverBackBean.getMsg());
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
    }

    private void zfb() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.paytypedrivingorder)
                    .addParams("driving_order_number", driving_order_number)
                    .addParams("device_id", LoginUtils.getId(StudentsIndentContentActivity.this))
                    .addParams("user_id", ""+PrefUtils.getParameter("user_id"))
                    .addParams("pay_type", "1")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    Log.e("paytypedrivingorder",string);
                    if (!TextUtils.isEmpty(string)) {
                        ZhiFuBaoBean zhiFuBaoBean = JsonUtil.parseJsonToBean(string, ZhiFuBaoBean.class);
                        if (zhiFuBaoBean != null) {
                            int code = zhiFuBaoBean.getCode();
                            if (200 == code) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        final int SDK_PAY_FLAG = 1;
                                        @SuppressLint("HandlerLeak") final Handler mHandler = new Handler() {
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
                                                            Intent intent = new Intent(App.context, StudentsIndentContentBaoMingSuccessActivity.class);
                                                            StudentsBaoMingActivity.stance.finish();
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                                                            Toast.makeText(StudentsIndentContentActivity.this, "支付失败,请重新下单", Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                    }
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
                                                            PayTask alipay = new PayTask(StudentsIndentContentActivity.this);
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
                                });
                            }

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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    private void wechat() {
        if (XueYiCheUtils.isWeixinAvilible(this)) {
            wx(StudentsIndentContentActivity.this, AppUrl.paytypedrivingorder, driving_order_number, ""+PrefUtils.getParameter("user_id"));
        } else {
            showToastShort("目前您的微信版本过低或未安装微信，需要安装微信才能使用");
        }
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private   void wx(Activity activity, String url, String order_number, String user_id) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String id = LoginUtils.getId(activity);
            final WXZhiFuBean.ContentBean[] content = new WXZhiFuBean.ContentBean[1];
            OkHttpUtils.post().url(url)
                    .addParams("driving_order_number", order_number)
                    .addParams("pay_type", "2")
                    .addParams("user_id", user_id)
                    .addParams("device_id", TextUtils.isEmpty(id)? "" :id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    Log.e("121212",""+string);
                    Log.e("121212",""+order_number);
                    Log.e("121212",""+url);
                    Log.e("121212",""+user_id);
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
        } else {
            Toast.makeText(App.context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }
}
