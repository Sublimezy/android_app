package com.xueyiche.zjyk.xueyiche.daijia.bean;

public
/**
 *
 * @Package: com.xueyiche.zjyk.xueyiche.daijia.bean
 * @ClassName: CancelOrderBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/4/8 16:24
 */
class CancelOrderBean {

    private int code;
    private String msg;
    private String time;
    private Object data;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
