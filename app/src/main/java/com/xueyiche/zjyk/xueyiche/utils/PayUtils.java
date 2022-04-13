package com.xueyiche.zjyk.xueyiche.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.luck.picture.lib.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.SuccessBean;
import com.xueyiche.zjyk.xueyiche.constants.bean.WXZhiFuBean;
import com.xueyiche.zjyk.xueyiche.constants.bean.YLZhiFuBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaFragment;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitYuYueActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.XingChengActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.GoDaiJiaBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.pay.PaySucceedActivity;
import com.xueyiche.zjyk.xueyiche.pay.bean.ZhiFuBaoBean;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.submit.PracticeCarSubmitIndent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2017/7/18.
 */
public class PayUtils {
    /**
     * 支付弹窗
     *
     * @param activity
     * @param order_number
     * @param pay_style
     */
    public static void showPopupWindow(Activity activity, final String order_number, final String pay_style) {
        PopupWindow pop = new PopupWindow(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.pop_pay_void_layout, null);
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(false);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        RelativeLayout rl_pay_zhifubao = (RelativeLayout) view.findViewById(R.id.rl_pay_zhifubao);
        RelativeLayout rl_pay_wechat = (RelativeLayout) view.findViewById(R.id.rl_pay_wechat);
        ImageView iv_pay_exit = view.findViewById(R.id.iv_pay_exit);
        final ImageView iv_wecaht_select = view.findViewById(R.id.iv_wecaht_select);
        final ImageView iv_zhifubao_select = view.findViewById(R.id.iv_zhifubao_select);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        iv_pay_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        rl_pay_zhifubao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (AppUtils.isFastClick()) {
                    iv_zhifubao_select.setImageResource(R.mipmap.daijia_pay_ok);
                    iv_wecaht_select.setImageResource(R.mipmap.daijia_pay_no);
                    PayUtils.zfbPay("", activity, activity, order_number, pay_style);
                    pop.dismiss();
                    ll_popup.clearAnimation();
                }

            }
        });
        rl_pay_wechat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //微信支付
                if (AppUtils.isFastClick()) {
                    iv_zhifubao_select.setImageResource(R.mipmap.daijia_pay_no);
                    iv_wecaht_select.setImageResource(R.mipmap.daijia_pay_ok);
                    if (XueYiCheUtils.isWeixinAvilible(activity)) {
                        PayUtils.wx(activity, AppUrl.Pay_Order_One, order_number);
                    } else {
                        ToastUtils.showToast(activity, "目前您的微信版本过低或未安装微信，需要安装微信才能使用");
                    }
                    pop.dismiss();
                    ll_popup.clearAnimation();
                }

            }
        });
        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                activity, R.anim.activity_translate_in));
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    /**
     * 微信支付
     */
    public static void wx(Activity activity, String url, String order_number) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String id = LoginUtils.getId(activity);
            final WXZhiFuBean.ContentBean[] content = new WXZhiFuBean.ContentBean[1];
            Map<String, String> map = new HashMap<>();
            map.put("", "");
            map.put("", order_number);
            MyHttpUtils.postHttpMessage(url, map, WXZhiFuBean.class, new RequestCallBack<WXZhiFuBean>() {
                @Override
                public void requestSuccess(WXZhiFuBean json) {
                    content[0] = json.getContent();
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

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
        } else {
            Toast.makeText(App.context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 假支付，修改订单状态
     */
    public static void JiaPay(String order_sn, String order_status) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            Map<String, String> map = new HashMap<>();
            map.put("order_sn", order_sn);
            map.put("order_status", order_status);
            MyHttpUtils.postHttpMessage(AppUrl.updateOrderStatus, map, SuccessBean.class, new RequestCallBack<SuccessBean>() {
                @Override
                public void requestSuccess(SuccessBean json) {
                    EventBus.getDefault().post(new MyEvent("支付成功"));
                    Log.e("假支付","requestSuccess");
                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
        } else {
            Toast.makeText(App.context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 支付宝
     */
    public static void zfbPay(String url, Context context, final Activity activity, String order_number, final String pay_style) {
        if (XueYiCheUtils.IsHaveInternet(activity)) {
            Map<String, String> map = new HashMap<>();
            map.put("", "");
            MyHttpUtils.postHttpMessage(url, map, ZhiFuBaoBean.class, new RequestCallBack<ZhiFuBaoBean>() {
                @Override
                public void requestSuccess(ZhiFuBaoBean json) {
                    PayUtils.zfb(json.getContent(), context, activity, order_number, pay_style);
                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
        }
    }

    public static void zfb(final String string, final Context context, Activity activity, final String order_number, final String pay_style) {
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
                            if ("chaxun".equals(pay_style)) {
                                EventBus.getDefault().post(new MyEvent("查询支付成功"));
                                activity.finish();
                            } else if ("daijia".equals(pay_style)) {
                                EventBus.getDefault().post(new MyEvent("支付成功"));
                            } else if ("daijia_yuyue".equals(pay_style)) {
                                PrefUtils.putString(App.context, "hongbao_show", "1");
                                Intent intent = new Intent(activity, WaitYuYueActivity.class);
                                intent.putExtra("order_number", order_number);
                                activity.startActivity(intent);
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
                                                intent.putExtra("order_number", order_number);
                                                activity.startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(App.context, WaitYuYueActivity.class);
                                                intent.putExtra("order_number", order_number);
                                                activity.startActivity(intent);
                                            }
                                        } else if ("1".equals(status) || "2".equals(status)) {
                                            Intent intent = new Intent(App.context, JieDanActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            activity.startActivity(intent);
                                        } else if ("3".equals(status)) {
                                            Intent intent = new Intent(App.context, JinXingActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            activity.startActivity(intent);
                                        } else if ("4".equals(status)) {
                                            Intent intent = new Intent(App.context, JieDanActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            activity.startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(App.context, DaiJiaFragment.class);
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
}
