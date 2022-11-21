package com.xueyiche.zjyk.xueyiche.splash;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static final String NAME = "xueyiche_app_settings";
    private static final String KEY_PRIVACY_AGREEMENT = "xueyiche_agreement_accepted";
    private static final String KEY_INIT_PERMISSION = "xueyiche_init_permission";

    private static volatile MyPreferences instance;

    private final SharedPreferences preferences;

    private MyPreferences(Context context) {
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static MyPreferences getInstance(Context context) {
        if (instance == null) {
            synchronized (MyPreferences.class) {
                if (instance == null) {
                    instance = new MyPreferences(context);
                }
            }
        }
        return instance;
    }

    /**
     * 设置隐私协议是否同意
     *
     * @param value 是否同意
     */
    public void setAgreePrivacyAgreement(boolean value) {
        preferences.edit().putBoolean(KEY_PRIVACY_AGREEMENT, value).apply();
    }

    /**
     * 是否同意了隐私协议
     *
     * @return true 已经同意；false 还没有同意
     */
    public boolean hasAgreePrivacyAgreement() {
        return preferences.getBoolean(KEY_PRIVACY_AGREEMENT, false);
    }
    /**
     * 设置隐私协议是否同意
     *
     * @param value 是否同意
     */
    public void setInitSplashPermission(boolean value) {
        preferences.edit().putBoolean(KEY_INIT_PERMISSION, value).apply();
    }

    /**
     * 是否同意了隐私协议
     *
     * @return true 已经同意；false 还没有同意
     */
    public boolean hasInitSplashPermission() {
        return preferences.getBoolean(KEY_INIT_PERMISSION, false);
    }

}
