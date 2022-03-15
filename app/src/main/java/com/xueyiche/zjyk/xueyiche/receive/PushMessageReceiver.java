package com.xueyiche.zjyk.xueyiche.receive;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitYuYueActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.GoDaiJiaBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import de.greenrobot.event.EventBus;

public class PushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG, "[onMessage] " + customMessage);
        String message = customMessage.title;
        Log.e("99999999999999",message);
        DaiJiaBean daiJiaMsgBean = JsonUtil.parseJsonToBean(message, DaiJiaBean.class);
        String msg_type = daiJiaMsgBean.getMsg_type();
        String order_number = daiJiaMsgBean.getOrder_number();
        String substring = order_number.substring(0, 1);
        if ("4".equals(msg_type)) {
            if ("D".equals(substring)) {
                EventBus.getDefault().post(new MyEvent("开始行程"));
                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                return;
            }else if ("Y".equals(substring)){
                EventBus.getDefault().post(new MyEvent("刷新张磊"));
                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                return;
            }
        } else if ("6".equals(msg_type)){
            if ("D".equals(substring)) {
                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                EventBus.getDefault().post(new MyEvent("代驾结束"));
                return;
            }else if ("Y".equals(substring)){
                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                EventBus.getDefault().post(new MyEvent("刷新张磊"));
                return;
            }
        }else if ("1".equals(msg_type)){
            if ("D".equals(substring)) {
                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                EventBus.getDefault().post(new MyEvent("代驾结束"));
                return;
            }else if ("Y".equals(substring)){
                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                EventBus.getDefault().post(new MyEvent("刷新待接单"));
                return;
            }
        }else {
            EventBus.getDefault().post(new MyEvent("刷新代驾"));

        }

    }

    @Override
    public void onNotifyMessageOpened(final Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageOpened] " + message);
        try {
            String user_id = PrefUtils.getString(context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.Get_Number_UserId)
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
                                                    context.startActivity(intent);
                                                }else {
                                                    Intent intent = new Intent(App.context, WaitYuYueActivity.class);
                                                    intent.putExtra("order_number",order_number);
                                                    context.startActivity(intent);
                                                }
                                            } else  if ("1".equals(status)||"2".equals(status)) {
                                                Intent intent = new Intent(App.context, JieDanActivity.class);
                                                intent.putExtra("order_number",order_number);
                                                context.startActivity(intent);
                                            }else if ("3".equals(status)) {
                                                Intent intent = new Intent(App.context, JinXingActivity.class);
                                                intent.putExtra("order_number",order_number);
                                                context.startActivity(intent);
                                            }else if ("4".equals(status)) {
                                                Intent intent = new Intent(App.context, JieDanActivity.class);
                                                intent.putExtra("order_number",order_number);
                                                context.startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(App.context, DaiJiaActivity.class);
                                                context.startActivity(intent);
                                            }
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
        } catch (Throwable throwable) {

        }
    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if (nActionExtra == null) {
            Log.d(TAG, "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        if (nActionExtra.equals("my_extra1")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一");
        } else if (nActionExtra.equals("my_extra2")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二");
        } else if (nActionExtra.equals("my_extra3")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三");
        } else {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义");
        }
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived] " + message);

    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG, "[onRegister] " + registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    //send msg to MainActivity
//    private void processCustomMessage(Context context, CustomMessage customMessage) {
//        if (MainActivity.isForeground) {
//            String message = customMessage.message;
//            String extras = customMessage.extra;
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
//    }
}
