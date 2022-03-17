package com.xueyiche.zjyk.xueyiche.receive;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;


/**
 */
public class UpLocationService extends Service {

    private AlarmManager alarmManager;
    private PendingIntent mPi;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent i = new Intent(this, AlarmssReceiver.class);//开启广播
        mPi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000, mPi);



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (alarmManager != null) {
            alarmManager.cancel(mPi);//关闭的服务的时候同时关闭广播注册者

        }
        super.onDestroy();
    }


}
