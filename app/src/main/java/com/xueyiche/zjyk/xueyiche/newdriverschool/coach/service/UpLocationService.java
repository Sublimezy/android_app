package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.LogUtils;

import cn.jpush.android.service.AlarmReceiver;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.coach.service
 * @ClassName: UpLocationService
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/2/2 12:46
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
        LogUtils.i("张斯佳上传位置", "       oncreate");

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent i = new Intent(this, AlarmssReceiver.class);//开启广播
        mPi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000, mPi);

        LogUtils.i("张斯佳上传位置", "我上传了一下位置,11111");


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtils.i("张斯佳上传位置", "           onDestroy");
        if (alarmManager != null) {
            alarmManager.cancel(mPi);//关闭的服务的时候同时关闭广播注册者

        }
        super.onDestroy();
    }


}
