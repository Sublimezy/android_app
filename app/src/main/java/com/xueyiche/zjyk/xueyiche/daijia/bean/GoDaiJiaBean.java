package com.xueyiche.zjyk.xueyiche.daijia.bean;

/**
 * Created by Administrator on 2019/10/22.
 */
public class GoDaiJiaBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"order_number":"D1571713529529","order_type":"0","status":"0"}
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
         * order_number : D1571713529529
         * order_type : 0
         * status : 0
         */

        private String order_number;
        private String order_type;
        private String status;

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
