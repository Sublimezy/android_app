package com.xueyiche.zjyk.xueyiche.pay.bean;

/**
 * Created by Owner on 2018/8/16.
 */
public class PinNumberBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"pin":"12345"}
     */

    private String msg;
    private int code;
    /**
     * pin : 12345
     */

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
        private String pin;

        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }
    }
}
