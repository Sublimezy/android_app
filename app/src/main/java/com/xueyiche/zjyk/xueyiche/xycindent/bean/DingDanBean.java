package com.xueyiche.zjyk.xueyiche.xycindent.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Owner on 2016/11/15.
 */
public class DingDanBean implements Serializable{


    /**
     * content : [{"order_status":"0","refer_name":"旺盛洗车行","pay_price":"￥80.00","ew_code":"935554640768","service_name":"普通洗车-五座x2,维修保养x1","order_number":"E1524022273719","order_system_time":"2018-04-18 11:31:15","order_type":"1"},{"order_status":"0","refer_name":"环球驾校","pay_price":"￥500","ew_code":"417046450771","service_name":"一站式拿证服务C1 定金","order_number":"E1520559708381","order_system_time":"2018-04-18 10:01:51","order_type":"2"},{"order_status":"0","refer_name":"旺盛洗车行","pay_price":"￥260.00","ew_code":"935554640888","service_name":"普通洗车-五座x1,内饰清洗x1,维修保养x1,全车打蜡x1","order_number":"E1520212261134","order_system_time":"2018-04-18 09:01:51","order_type":"1"}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * order_status : 0
     * refer_name : 旺盛洗车行
     * pay_price : ￥80.00
     * ew_code : 935554640768
     * service_name : 普通洗车-五座x2,维修保养x1
     * order_number : E1524022273719
     * order_system_time : 2018-04-18 11:31:15
     * order_type : 1
     */

    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String order_status;
        private String refer_name;
        private String pay_price;
        private String ew_code;
        //0：未交尾款，1：已交尾款
        private String tail_pay;
        private String tail_money;
        private String order_number2;

        public String getOrder_number2() {
            return order_number2;
        }

        public void setOrder_number2(String order_number2) {
            this.order_number2 = order_number2;
        }

        public String getTail_pay() {
            return tail_pay;
        }

        public void setTail_pay(String tail_pay) {
            this.tail_pay = tail_pay;
        }

        public String getTail_money() {
            return tail_money;
        }

        public void setTail_money(String tail_money) {
            this.tail_money = tail_money;
        }

        private String service_name;
        private String order_number;
        private String order_system_time;
        private String order_type;
        private String order_receiving;
        private String need_photo;
        private String latitude;
        private String longitude;
        private String shop_place_name;
        private String shop_phone;
        private String start_type;
        private String service;
        private String service_price;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getService_price() {
            return service_price;
        }

        public void setService_price(String service_price) {
            this.service_price = service_price;
        }

        public String getPin_url() {
            return pin_url;
        }

        public void setPin_url(String pin_url) {
            this.pin_url = pin_url;
        }

        private String pin_url;
        private String order_category;

        public String getOrder_category() {
            return order_category;
        }

        public void setOrder_category(String order_category) {
            this.order_category = order_category;
        }

        public String getStart_type() {
            return start_type;
        }

        public void setStart_type(String start_type) {
            this.start_type = start_type;
        }

        public String getShop_phone() {
            return shop_phone;
        }

        public void setShop_phone(String shop_phone) {
            this.shop_phone = shop_phone;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getShop_place_name() {
            return shop_place_name;
        }

        public void setShop_place_name(String shop_place_name) {
            this.shop_place_name = shop_place_name;
        }

        public String getNeed_photo() {
            return need_photo;
        }

        public void setNeed_photo(String need_photo) {
            this.need_photo = need_photo;
        }

        public String getOrder_receiving() {
            return order_receiving;
        }

        public void setOrder_receiving(String order_receiving) {
            this.order_receiving = order_receiving;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getRefer_name() {
            return refer_name;
        }

        public void setRefer_name(String refer_name) {
            this.refer_name = refer_name;
        }

        public String getPay_price() {
            return pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public String getEw_code() {
            return ew_code;
        }

        public void setEw_code(String ew_code) {
            this.ew_code = ew_code;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getOrder_system_time() {
            return order_system_time;
        }

        public void setOrder_system_time(String order_system_time) {
            this.order_system_time = order_system_time;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }
    }
}
