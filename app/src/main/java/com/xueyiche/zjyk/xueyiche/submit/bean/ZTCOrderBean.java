package com.xueyiche.zjyk.xueyiche.submit.bean;

/**
 * Created by ZL on 2019/3/6.
 */
public class ZTCOrderBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"service_price":"2980","baoming_num":0,"order_status":"0","pay_status":"0","user_id":"29168db2b3b211e7aeb85254f2dc841f","service":"学易车驾考直通车","order_number":"Z1551868653227","recommend_id":"","order_receiving":"0","order_system_time":"2019-03-06 18:37:34","user_id_card":"","order_id":1136,"order_type":"7"}
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
         * service_price : 2980
         * baoming_num : 0
         * order_status : 0
         * pay_status : 0
         * user_id : 29168db2b3b211e7aeb85254f2dc841f
         * service : 学易车驾考直通车
         * order_number : Z1551868653227
         * recommend_id :
         * order_receiving : 0
         * order_system_time : 2019-03-06 18:37:34
         * user_id_card :
         * order_id : 1136
         * order_type : 7
         */

        private String service_price;
        private int baoming_num;
        private String order_status;
        private String pay_status;
        private String user_id;
        private String service;
        private String order_number;
        private String recommend_id;
        private String order_receiving;
        private String order_system_time;
        private String user_id_card;
        private int order_id;
        private String order_type;

        public String getService_price() {
            return service_price;
        }

        public void setService_price(String service_price) {
            this.service_price = service_price;
        }

        public int getBaoming_num() {
            return baoming_num;
        }

        public void setBaoming_num(int baoming_num) {
            this.baoming_num = baoming_num;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(String recommend_id) {
            this.recommend_id = recommend_id;
        }

        public String getOrder_receiving() {
            return order_receiving;
        }

        public void setOrder_receiving(String order_receiving) {
            this.order_receiving = order_receiving;
        }

        public String getOrder_system_time() {
            return order_system_time;
        }

        public void setOrder_system_time(String order_system_time) {
            this.order_system_time = order_system_time;
        }

        public String getUser_id_card() {
            return user_id_card;
        }

        public void setUser_id_card(String user_id_card) {
            this.user_id_card = user_id_card;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }
    }
}
