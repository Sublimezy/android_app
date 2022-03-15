package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.coach.service
 * @ClassName: AlarmssReceiver
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/2/3 10:24
 */
public class AlarmssReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        UIUtils.startTokenService();//可以把开启跟关闭服务的逻辑封装一下,直接调用
        Intent i = new Intent(context, UpLocationService.class);
        //启动服务
        context.startService(i);
    }
}