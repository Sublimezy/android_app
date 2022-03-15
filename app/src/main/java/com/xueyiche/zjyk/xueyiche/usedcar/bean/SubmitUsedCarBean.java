package com.xueyiche.zjyk.xueyiche.usedcar.bean;

/**
 * Created by Owner on 2018/8/15.
 */
public class SubmitUsedCarBean {


    /**
     * code : 201
     * message : 订单提交成功
     * order_number : E1533629348
     */

    private int code;
    private String message;
    private String order_number;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }
}
