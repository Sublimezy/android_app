package com.xueyiche.zjyk.xueyiche.receive;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
/**
 * Created by Owner on 2016/10/29.
 */
public class MyReceive extends BroadcastReceiver {

    private NotificationManager mNotificationManager;


    //用于接受消息
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == mNotificationManager) {
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();


    }

}
