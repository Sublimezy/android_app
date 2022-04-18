package com.xueyiche.zjyk.xueyiche.constants.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Owner on 2017/1/24.
 */
public class WXZhiFuBean {

    /**
     * code : 1
     * msg : 请求成功
     * time : 1650001174
     * data : {"appid":"wx3c3dcf4648234b46","partnerid":"1457762202","prepayid":"wx15133935269283feba2c0e8e2349af0000","timestamp":"1650001175","noncestr":"hr4DHZeZvMHJIfIX","package":"Sign=WXPay","sign":"498C1E218463038D4F88260EA1921657"}
     */

    private int code;
    private String msg;
    private String time;
    /**
     * appid : wx3c3dcf4648234b46
     * partnerid : 1457762202
     * prepayid : wx15133935269283feba2c0e8e2349af0000
     * timestamp : 1650001175
     * noncestr : hr4DHZeZvMHJIfIX
     * package : Sign=WXPay
     * sign : 498C1E218463038D4F88260EA1921657
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String appid;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
