package com.xueyiche.zjyk.xueyiche.carlife.bean;

/**
 * Created by Owner on 2018/2/28.
 */
public class CarLiveXiaDanBean {


    /**
     * integral_enough : 1
     * order_number : E1519720865577
     */

    private ContentBean content;
    /**
     * content : {"integral_enough":"1","order_number":"E1519720865577"}
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
        private String integral_enough;
        private String order_number;

        public String getIntegral_enough() {
            return integral_enough;
        }

        public void setIntegral_enough(String integral_enough) {
            this.integral_enough = integral_enough;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }
    }
}
