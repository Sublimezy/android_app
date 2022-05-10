package com.xueyiche.zjyk.xueyiche.daijia.bean;

/**
 * Created by YJF on 2019/10/29.
 */
public class DaiJiaBean {


    /**
     * order_sn : A2022050717101304581
     * createtime : 1651914613
     * start_address : 黑龙江省哈尔滨市松北区智谷二街1073号靠近松北科技创新产业园综合服务大厅
     * mobile : 2117
     */

    private String order_sn;
    private String createtime;
    private String start_address;
    private String mobile;
    private String order_status;

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
