package com.xueyiche.zjyk.xueyiche.receive;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZL on 2019/10/23.
 */
public class LocationService extends Service {
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    insertDeviceId();
                    break;

                default:
                    break;
            }
        }


    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

            }
        }, 1000, 5000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

            }
        }, 1000, 5000);
        return super.onStartCommand(intent, flags, startId);

    }

    private void insertDeviceId(){
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            final String szImei = App.szImei;
            LogUtil.e("device_id","***"+szImei);
            if (!TextUtils.isEmpty(szImei)) {
                OkHttpUtils.post().url(AppUrl.Insert_DeviceId)
                        .addParams("user_id", PrefUtils.getParameter("user_id"))
                        .addParams("device_id", szImei)
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        final String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessBackBean sjLoactionBean = JsonUtil.parseJsonToBean(string, SuccessBackBean.class);
                            if (sjLoactionBean != null) {
                                final int code = sjLoactionBean.getCode();
                                final String msg = sjLoactionBean.getMsg();
                                if (200 == code) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("qqqqqqID",szImei);
                                            Log.e("wwwwwwwwwwwIDBack",msg);

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

}
