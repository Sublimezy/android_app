package com.xueyiche.zjyk.jiakao.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.utils.NetUtil;

import java.util.ArrayList;


public class NetBroadcastReceiver extends BroadcastReceiver {
    public static ArrayList<netEventHandler> mListeners = new ArrayList<netEventHandler>();
    private static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NET_CHANGE_ACTION)) {
            App.mNetWorkState = NetUtil.getNetWorkState(context);
            if (mListeners.size() > 0)// 通知接口完成加载
                for (netEventHandler handler : mListeners) {
                    handler.onNetChange();
                }
        }
    }

    public static abstract interface netEventHandler {

        public abstract void onNetChange();
    }
}
