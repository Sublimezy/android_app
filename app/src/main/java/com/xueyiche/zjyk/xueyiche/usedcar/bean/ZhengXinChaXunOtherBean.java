package com.xueyiche.zjyk.xueyiche.usedcar.bean;

/**
 * Created by ZL on 2018/11/9.
 */
public class ZhengXinChaXunOtherBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"account_balance":4996,"charging":2}
     */

    private String msg;
    private int code;
    private ContentBean content;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * account_balance : 4996
         * charging : 2
         */

        private int account_balance;
        private String charging;
        private String order_number;

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public int getAccount_balance() {
            return account_balance;
        }

        public void setAccount_balance(int account_balance) {
            this.account_balance = account_balance;
        }

        public String getCharging() {
            return charging;
        }

        public void setCharging(String charging) {
            this.charging = charging;
        }
    }
}
