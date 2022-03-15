package com.xueyiche.zjyk.xueyiche.constants.bean;

/**
 * Created by Owner on 2017/1/14.
 */
public class MyWalletBean {


    /**
     * current_balance : 0.5
     */

    private ContentBean content;
    /**
     * content : {"current_balance":"0.5"}
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
        private String current_balance;

        public String getCurrent_balance() {
            return current_balance;
        }

        public void setCurrent_balance(String current_balance) {
            this.current_balance = current_balance;
        }
    }
}
