package com.xueyiche.zjyk.jiakao.myhttp;

import android.util.Log;

/**
 * @Package: com.keji.zsj.virtual_country.utils
 * @ClassName: LogUtils
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2020/12/1 13:37
 */
public class LogUtils {
    private static boolean DEBUG = true;
    public static void v(String tag, String msg){
        logger("v",tag,msg);
    }

    public static void d(String tag, String msg){
        logger("d",tag,msg);
    }

    public static void i(String tag, String msg){
        logger("i",tag,msg);
    }

    public static void w(String tag, String msg){
        logger("w",tag,msg);
    }

    public static void e(String tag, String msg){
        logger("e",tag,msg);
    }

    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static void setDEBUG(boolean DEBUG) {
        LogUtils.DEBUG = DEBUG;
    }

    private static void logger(String priority, String tag, String msg){
        if (!DEBUG){
            return;
        }
        switch (priority){
            case "v":
                Log.v(tag,msg+"");
                break;
            case "d":
                Log.d(tag,msg+"");
                break;
            case "i":
                Log.i(tag,msg+"");
                break;
            case "w":
                Log.w(tag,msg+"");
                break;
            default:
                Log.e(tag,msg+"");
        }
    }
}
