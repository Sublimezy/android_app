package com.xueyiche.zjyk.xueyiche.usedcar.bean;

/**
 * Created by ZL on 2018/8/9.
 */
public class UesdCarTokenBean {

    /**
     * code : 200
     * message : 请求成功
     * token : uGpySdC57g0W9gL_s_MQDcdCkm4x1TgASFT2mvLL:ZqUbRfDh85c91T3qIlZ9E5YtLvg=:eyJzY29wZSI6InR3b2NhciIsImRlYWRsaW5lIjoxNTMzODAxOTk4fQ==
     */

    private int code;
    private String message;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
