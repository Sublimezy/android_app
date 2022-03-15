package com.xueyiche.zjyk.xueyiche.driverschool.bean;

/**
 * Created by Owner on 2018/1/4.
 */
public class DriverSchoolGeRenBean {

    /**
     * driver_school_money : 2580
     * subscription : 500
     * payamount : 500
     */

    private ContentBean content;
    /**
     * content : {"driver_school_money":"2580","subscription":"500","payamount":"500"}
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

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

    public static class ContentBean {
        private String driver_school_money;
        private String subscription;
        private String payamount;

        public String getDriver_school_money() {
            return driver_school_money;
        }

        public void setDriver_school_money(String driver_school_money) {
            this.driver_school_money = driver_school_money;
        }

        public String getSubscription() {
            return subscription;
        }

        public void setSubscription(String subscription) {
            this.subscription = subscription;
        }

        public String getPayamount() {
            return payamount;
        }

        public void setPayamount(String payamount) {
            this.payamount = payamount;
        }
    }
}
