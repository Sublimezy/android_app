package com.xueyiche.zjyk.xueyiche.homepage.bean;

/**
 * Created by ZL on 2019/6/17.
 */
public class WeiZhangPostBean {

    /**
     * success : 1
     * message : {"orderid":"49578147F9B840C197412BB8495CBBD2","state":1,"msg":"订单提交成功"}
     */

    private int success;
    private MessageBean message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * orderid : 49578147F9B840C197412BB8495CBBD2
         * state : 1
         * msg : 订单提交成功
         */

        private String orderid;
        private int state;
        private String msg;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
