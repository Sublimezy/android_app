package com.xueyiche.zjyk.xueyiche.receive;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Owner on 2016/10/29.
 */
public class MyReceive extends BroadcastReceiver {

    private NotificationManager mNotificationManager;
    private Context context;

    //用于接受消息
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == mNotificationManager) {
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        this.context = context;

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {//注册
            // Util.soutLong(TAG, "JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // Util.soutLong(TAG, "接受到推送下来的自定义消息");
            // 在这里显示自定义通知
//            String alert = bundle.getString(JPushInterface.EXTRA_MESSAGE);

            String string = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogUtil.e("111111111111111",string);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            // Util.soutLong(TAG, "接受到推送下来的通知");
            // 这里会显示极光推送默认的通知，自定义能力有限
            String alert = bundle.getString(JPushInterface.EXTRA_ALERT);

            String string1 = bundle.getString(JPushInterface.EXTRA_EXTRA);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //打开Activity
            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
            String string = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String string1 = bundle.getString(JPushInterface.EXTRA_EXTRA);

            LogUtil.e("222220",alert);
            context.startActivity(i);



//
//            if ("请点击确认开始练车".equals(alert)) {
//                // 自定义打开的界面
//              //  i.putExtra("queren", "queren");
//                i.putExtra("position", 2);
//            } else if ("请点击确认结束练车".equals(alert)) {
//               // i.putExtra("queren", "end");
//                i.putExtra("position", 2);
//            }else if ("您的订单已被抢，请付款".equals(alert)){
//                i.putExtra("position", 2);
//            }


//            i.putExtra("position", 2);
//            context.startActivity(i);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Log.e("extras", extras);
//            try {
//                JSONObject jsonObject = new JSONObject(extras);
//                String order_number = jsonObject.getString("order_number");
//                Log.e("order_number", order_number);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }


        }

    }

}
