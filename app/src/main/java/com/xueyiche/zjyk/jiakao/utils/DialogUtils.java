package com.xueyiche.zjyk.jiakao.utils;
import com.xueyiche.zjyk.jiakao.constants.App;

/**
 * Created by ZL on 2017/7/24.
 */
public class DialogUtils {
    public static boolean IsLogin() {
        boolean islogin = PrefUtils.getBoolean(App.context, "ISLOGIN", false);
        if (islogin) {
            return true;
        } else {
            return false;
        }

    }
}
