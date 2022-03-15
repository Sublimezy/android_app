package com.xueyiche.zjyk.xueyiche.submit.bean;

/**
 * Created by ZL on 2019/3/6.
 */
public class DirectCarBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"effective":"1","service_price":"2980","baoming_num":0,"service":"学易车驾考直通车","user_name":"马金良","user_cards":"65627d69617762616b71756260617a754b55","user_phone":"18811149874","sys_year_month":"201903","id":1}
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
         * effective : 1
         * service_price : 2980
         * baoming_num : 0
         * service : 学易车驾考直通车
         * user_name : 马金良
         * user_cards : 65627d69617762616b71756260617a754b55
         * user_phone : 18811149874
         * sys_year_month : 201903
         * id : 1
         */

        private String effective;
        private String service_price;
        private int baoming_num;
        private String service;
        private String user_name;
        private String user_cards;
        private String user_phone;
        private String sys_year_month;
        private int id;

        public String getEffective() {
            return effective;
        }

        public void setEffective(String effective) {
            this.effective = effective;
        }

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

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_cards() {
            return user_cards;
        }

        public void setUser_cards(String user_cards) {
            this.user_cards = user_cards;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getSys_year_month() {
            return sys_year_month;
        }

        public void setSys_year_month(String sys_year_month) {
            this.sys_year_month = sys_year_month;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
