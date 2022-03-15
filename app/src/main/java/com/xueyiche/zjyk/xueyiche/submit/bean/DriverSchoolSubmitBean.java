package com.xueyiche.zjyk.xueyiche.submit.bean;

/**
 * Created by ZL on 2018/2/2.
 */
public class DriverSchoolSubmitBean {

    /**
     * content : {"driver_school_name":"环球驾校","user_name":"刘德华","order_number":"E1517539801197","subscription":"0","order_status":"2","shop_id":"cdb83022a3a04ffbb0d60686bc07b922","pay_status":"2","tail_money":"1","ew_code":"108494470854","user_phone":"66697468687267616b7570","order_system_time":"2018-02-02 10:50:01","order_receiving":"0","order_type":"2"}
     * code : 5
     * msg : 操作成功
     */

    private ContentBean content;
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
        /**
         * driver_school_name : 环球驾校
         * user_name : 刘德华
         * order_number : E1517539801197
         * subscription : 0
         * order_status : 2
         * shop_id : cdb83022a3a04ffbb0d60686bc07b922
         * pay_status : 2
         * tail_money : 1
         * ew_code : 108494470854
         * user_phone : 66697468687267616b7570
         * order_system_time : 2018-02-02 10:50:01
         * order_receiving : 0
         * order_type : 2
         */

        private String driver_school_name;
        private String user_name;
        private String order_number;
        private String subscription;
        private String order_status;
        private String shop_id;
        private String pay_status;
        private String tail_money;
        private String ew_code;
        private String user_phone;
        private String order_system_time;
        private String order_receiving;
        private String order_type;

        public String getDriver_school_name() {
            return driver_school_name;
        }

        public void setDriver_school_name(String driver_school_name) {
            this.driver_school_name = driver_school_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getSubscription() {
            return subscription;
        }

        public void setSubscription(String subscription) {
            this.subscription = subscription;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getTail_money() {
            return tail_money;
        }

        public void setTail_money(String tail_money) {
            this.tail_money = tail_money;
        }

        public String getEw_code() {
            return ew_code;
        }

        public void setEw_code(String ew_code) {
            this.ew_code = ew_code;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getOrder_system_time() {
            return order_system_time;
        }

        public void setOrder_system_time(String order_system_time) {
            this.order_system_time = order_system_time;
        }

        public String getOrder_receiving() {
            return order_receiving;
        }

        public void setOrder_receiving(String order_receiving) {
            this.order_receiving = order_receiving;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }
    }
}
