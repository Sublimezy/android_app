package com.xueyiche.zjyk.xueyiche.splash;

import android.content.res.Resources;

import com.xueyiche.zjyk.xueyiche.constants.App;


/**
 * 获取string.xml中的字
 */
public class WordUtil {

    private static Resources sResources;

    static {
        sResources = App.context.getResources();
    }

    public static String getString(int res) {
        return sResources.getString(res);
    }


    public static String getString(int res, Object...formatArgs) {
        return sResources.getString(res,formatArgs);
    }
}
