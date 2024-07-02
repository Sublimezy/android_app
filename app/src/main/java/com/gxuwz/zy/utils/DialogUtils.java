package com.gxuwz.zy.utils;
import com.gxuwz.zy.constants.App;

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
