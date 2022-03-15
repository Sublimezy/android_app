package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

import java.util.List;

/**
 * Created by Owner on 2018/8/22.
 */
public class UsedCarCollectBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"new_car_price":2,"loan_month":111,"rent_price":1,"rent_status":true,"uuid":"806d604e96f711e89c72484d7eb4a6f7","loan_first":11,"car_price":2,"last_time":1533284940,"new_rent_price":1,"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","id":1,"just_side_img":"http://car.xueyiche.vip/30501201808031630357196.jpg","mileage":"111","status":false}]
     */

    private String msg;
    private int code;
    /**
     * new_car_price : 2
     * loan_month : 111
     * rent_price : 1
     * rent_status : true
     * uuid : 806d604e96f711e89c72484d7eb4a6f7
     * loan_first : 11
     * car_price : 2
     * last_time : 1533284940
     * new_rent_price : 1
     * car_allname : 奥迪 A6L 2018款40周年年型 40 FSI 风尚型
     * id : 1
     * just_side_img : http://car.xueyiche.vip/30501201808031630357196.jpg
     * mileage : 111
     * status : false
     */

    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String new_car_price;
        private String loan_month;
        private String rent_price;
        private String uuid;
        private String loan_first;
        private String car_price;
        private String last_time;
        private String new_rent_price;
        private String car_allname;
        private String id;
        private String just_side_img;
        private String mileage;
        private String status;
        private String rent_status;
        private String htmlUrl;

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }

        public String getNew_car_price() {
            return new_car_price;
        }

        public void setNew_car_price(String new_car_price) {
            this.new_car_price = new_car_price;
        }

        public String getLoan_month() {
            return loan_month;
        }

        public void setLoan_month(String loan_month) {
            this.loan_month = loan_month;
        }

        public String getRent_price() {
            return rent_price;
        }

        public void setRent_price(String rent_price) {
            this.rent_price = rent_price;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getLoan_first() {
            return loan_first;
        }

        public void setLoan_first(String loan_first) {
            this.loan_first = loan_first;
        }

        public String getCar_price() {
            return car_price;
        }

        public void setCar_price(String car_price) {
            this.car_price = car_price;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public String getNew_rent_price() {
            return new_rent_price;
        }

        public void setNew_rent_price(String new_rent_price) {
            this.new_rent_price = new_rent_price;
        }

        public String getCar_allname() {
            return car_allname;
        }

        public void setCar_allname(String car_allname) {
            this.car_allname = car_allname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJust_side_img() {
            return just_side_img;
        }

        public void setJust_side_img(String just_side_img) {
            this.just_side_img = just_side_img;
        }

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRent_status() {
            return rent_status;
        }

        public void setRent_status(String rent_status) {
            this.rent_status = rent_status;
        }
    }
}
