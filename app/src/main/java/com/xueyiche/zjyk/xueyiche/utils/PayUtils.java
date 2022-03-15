package com.xueyiche.zjyk.xueyiche.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.unionpay.UPPayAssistEx;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.WXZhiFuBean;
import com.xueyiche.zjyk.xueyiche.constants.bean.YLZhiFuBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitYuYueActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.XingChengActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.GoDaiJiaBean;
import com.xueyiche.zjyk.xueyiche.pay.PaySucceedActivity;
import com.xueyiche.zjyk.xueyiche.pay.bean.ZhiFuBaoBean;
import com.xueyiche.zjyk.xueyiche.submit.CarLiveSubmitIndent;
import com.xueyiche.zjyk.xueyiche.submit.DriverSchoolSubmitIndent;
import com.xueyiche.zjyk.xueyiche.submit.PracticeCarSubmitIndent;
import com.xueyiche.zjyk.xueyiche.submit.UsedCarSubmitIndent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2017/7/18.
 */
public class PayUtils {
    /**
     * 微信支付(洗车美容，驾校,有证练车，即时学车)
     *
     * @param order_number ：订单号
     * @param user_id      ： user_id
     */
    public static void wx(Activity activity, String url, String order_number, String user_id) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String id = LoginUtils.getId(activity);
            final WXZhiFuBean.ContentBean[] content = new WXZhiFuBean.ContentBean[1];
            OkHttpUtils.post().url(url)
                    .addParams("order_number", order_number)
                    .addParams("pay_type_id", "2")
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

    /**
     * 支付宝
     *
     * @param string
     * @param context
     * @param activity
     */

    public static void zfb(final String string, final Context context, final Activity activity, final String huodejifen, final String subscription, final String order_number, final String pay_style) {
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
                            if (TextUtils.isEmpty(huodejifen)) {
                                Toast.makeText(context, "充值成功", Toast.LENGTH_SHORT).show();
                            } else {
                                if ("chaxun".equals(pay_style)) {
                                    EventBus.getDefault().post(new MyEvent("查询支付成功"));
                                    activity.finish();
                                } else if ("practice".equals(pay_style)) {

                                } else if ("daijia_yuyue".equals(pay_style)) {
                                    PrefUtils.putString(App.context,"hongbao_show","1");
                                    XingChengActivity.instance.finish();
                                    Intent intent = new Intent(activity, WaitYuYueActivity.class);
                                    intent.putExtra("order_number", order_number);
                                    activity.startActivity(intent);
                                }else if ("daijia_liji".equals(pay_style)) {
                                    PrefUtils.putString(App.context,"hongbao_show","1");
                                    XingChengActivity.instance.finish();
                                    Intent intent = new Intent(activity, WaitActivity.class);
                                    intent.putExtra("order_number", order_number);
                                    activity.startActivity(intent);
                                    return;
                                }else if ("daijia2".equals(pay_style)) {
                                    JieDanActivity.instance.finish();
                                    Intent intent = new Intent(activity, JinXingActivity.class);
                                    intent.putExtra("order_number", order_number);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    return;
                                }else if ("daijia3".equals(pay_style)) {
                                    JinXingActivity.instance.finish();
                                    Intent intent = new Intent(activity, EndActivity.class);
                                    intent.putExtra("order_number", order_number);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    return;
                                }else if ("daijia_daifu".equals(pay_style)) {
                                    JieDanActivity.instance.finish();
                                    goDaiJia(activity);
                                    return;
                                }else{
                                    Intent intent = new Intent(activity, PaySucceedActivity.class);
                                    intent.putExtra("subscription", subscription);
                                    intent.putExtra("huodejifen", huodejifen);
                                    intent.putExtra("order_number", order_number);
                                    intent.putExtra("pay_style", pay_style);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    if (!TextUtils.isEmpty(pay_style)) {
                                        if ("driver_school".equals(pay_style)) {
                                            DriverSchoolSubmitIndent.instance.finish();
                                        } else if ("car_life".equals(pay_style)) {
                                            CarLiveSubmitIndent.instance.finish();
                                        } else if ("practice".equals(pay_style)) {
                                            PracticeCarSubmitIndent.instance.finish();
                                        } else if ("usedcar".equals(pay_style)) {
                                            UsedCarSubmitIndent.instance.finish();
                                        }
                                    }
                                }
                            }
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(context, "支付失败,请重新下单", Toast.LENGTH_SHORT).show();
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
    public static void goDaiJia(final Activity activity) {
        String user_id = PrefUtils.getString(activity, "user_id", "");
        OkHttpUtils.post().url(AppUrl.Get_Number_UserId)
                .addParams("device_id", LoginUtils.getId(activity))
                .addParams("user_id", user_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    GoDaiJiaBean goDaiJiaBean = JsonUtil.parseJsonToBean(string, GoDaiJiaBean.class);
                    if (goDaiJiaBean != null) {
                        final int code = goDaiJiaBean.getCode();
                        if (200 == code) {
                            final GoDaiJiaBean.ContentBean content = goDaiJiaBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
                                        String order_number = content.getOrder_number();
                                        String status = content.getStatus();
                                        String order_type = content.getOrder_type();
                                        if ("0".equals(status)) {
                                            if ("0".equals(order_type)) {
                                                Intent intent = new Intent(App.context, WaitActivity.class);
                                                intent.putExtra("order_number",order_number);
                                                activity.startActivity(intent);
                                            }else {
                                                Intent intent = new Intent(App.context, WaitYuYueActivity.class);
                                                intent.putExtra("order_number",order_number);
                                                activity.startActivity(intent);
                                            }
                                        } else  if ("1".equals(status)||"2".equals(status)) {
                                            Intent intent = new Intent(App.context, JieDanActivity.class);
                                            intent.putExtra("order_number",order_number);
                                            activity.startActivity(intent);
                                        }else if ("3".equals(status)) {
                                            Intent intent = new Intent(App.context, JinXingActivity.class);
                                            intent.putExtra("order_number",order_number);
                                            activity.startActivity(intent);
                                        }else if ("4".equals(status)) {
                                            Intent intent = new Intent(App.context, JieDanActivity.class);
                                            intent.putExtra("order_number",order_number);
                                            activity.startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(App.context, DaiJiaActivity.class);
                                            activity.startActivity(intent);
                                        }
                                        activity.finish();
                                    }
                                });
                            }
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
    /**
     * 银联支付
     *
     * @param context      ：上下文
     * @param order_number ：订单号
     * @param money        ：支付的金额
     */
    public static void UnionPay(final Context context, String order_number, String money) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            OkHttpUtils.post().url(AppUrl.ZFYL)
                    .addParams("order_number", order_number)
                    .addParams("paytype", "yl")
                    .addParams("pay_money", money)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        YLZhiFuBean wxZhiFuBean = JsonUtil.parseJsonToBean(string, YLZhiFuBean.class);
                        if (wxZhiFuBean != null) {
                            YLZhiFuBean.ContentBean contentYL = wxZhiFuBean.getContent();
                            if (contentYL != null) {
                                final String tn = contentYL.getTn();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!TextUtils.isEmpty(tn)) {
                                            UPPayAssistEx.startPay(context, null, null, tn, "00");
                                        } else {
                                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                                            builder.setTitle("错误提示");
                                            builder.setMessage("网络连接失败,请重试!");
                                            builder.setNegativeButton("确定",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            builder.create().show();
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

                }

                @Override
                public void onResponse(Object response) {

                }
            });
        }
    }
}
