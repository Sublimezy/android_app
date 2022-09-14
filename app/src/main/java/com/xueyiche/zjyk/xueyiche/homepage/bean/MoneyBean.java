package com.xueyiche.zjyk.xueyiche.homepage.bean;

/**
 * Created by ZL on 2019/6/20.
 */
public class MoneyBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"pay_money":"0"}
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
         * pay_money : 0
         */

        private String pay_money;

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }
    }
}
