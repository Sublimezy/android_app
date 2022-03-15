package com.xueyiche.zjyk.xueyiche.practicecar.bean;

/**
 * Created by Administrator on 2019/9/10.
 */
public class JinJiPhoneBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"true_phone":"66627c6a69736b6b637271"}
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
         * true_phone : 66627c6a69736b6b637271
         */

        private String true_phone;

        public String getTrue_phone() {
            return true_phone;
        }

        public void setTrue_phone(String true_phone) {
            this.true_phone = true_phone;
        }
    }
}
