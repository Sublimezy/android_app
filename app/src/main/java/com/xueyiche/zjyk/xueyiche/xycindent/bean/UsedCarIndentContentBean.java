package com.xueyiche.zjyk.xueyiche.xycindent.bean;

/**
 * Created by ZL on 2018/8/22.
 */
public class UsedCarIndentContentBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"take_car_address":"","rent_total":3,"still_latitude":"","order_number":"E1534994656","take_longitude":"","rent_start_time":"2018-08-23 11:30:00","take_latitude":"","rent_end_time":"2018-08-23 12:00:00","order_status":"1","pay_status":"1","still_longitude":"","twocar_img":"http://car.xueyiche.vip/a33a8201808031629093236.jpg,http://car.xueyiche.vip/39c2a201808031629098057.jpg","car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","order_system_time":"2018-08-23 11:24:17","shop_phone":"0451-88888888","least_rent_time":1,"still_car_address":""}
     */

    private String msg;
    private int code;
    /**
     * take_car_address :
     * rent_total : 3
     * still_latitude :
     * order_number : E1534994656
     * take_longitude :
     * rent_start_time : 2018-08-23 11:30:00
     * take_latitude :
     * rent_end_time : 2018-08-23 12:00:00
     * order_status : 1
     * pay_status : 1
     * still_longitude :
     * twocar_img : http://car.xueyiche.vip/a33a8201808031629093236.jpg,http://car.xueyiche.vip/39c2a201808031629098057.jpg
     * car_allname : 奥迪 A6L 2018款40周年年型 40 FSI 风尚型
     * order_system_time : 2018-08-23 11:24:17
     * shop_phone : 0451-88888888
     * least_rent_time : 1
     * still_car_address :
     */

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
        private String take_car_address;
        private int rent_total;
        private String still_latitude;
        private String order_number;
        private String take_longitude;
        private String rent_start_time;
        private String take_latitude;
        private String rent_end_time;
        private String order_status;
        private String pay_status;
        private String still_longitude;
        private String header_img;
        private String car_allname;
        private String order_system_time;
        private String shop_phone;
        private int least_rent_time;
        private String still_car_address;

        public String getTake_car_address() {
            return take_car_address;
        }

        public void setTake_car_address(String take_car_address) {
            this.take_car_address = take_car_address;
        }

        public int getRent_total() {
            return rent_total;
        }

        public void setRent_total(int rent_total) {
            this.rent_total = rent_total;
        }

        public String getStill_latitude() {
            return still_latitude;
        }

        public void setStill_latitude(String still_latitude) {
            this.still_latitude = still_latitude;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getTake_longitude() {
            return take_longitude;
        }

        public void setTake_longitude(String take_longitude) {
            this.take_longitude = take_longitude;
        }

        public String getRent_start_time() {
            return rent_start_time;
        }

        public void setRent_start_time(String rent_start_time) {
            this.rent_start_time = rent_start_time;
        }

        public String getTake_latitude() {
            return take_latitude;
        }

        public void setTake_latitude(String take_latitude) {
            this.take_latitude = take_latitude;
        }

        public String getRent_end_time() {
            return rent_end_time;
        }

        public void setRent_end_time(String rent_end_time) {
            this.rent_end_time = rent_end_time;
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

        public String getStill_longitude() {
            return still_longitude;
        }

        public void setStill_longitude(String still_longitude) {
            this.still_longitude = still_longitude;
        }

        public String getHeader_img() {
            return header_img;
        }

        public void setHeader_img(String header_img) {
            this.header_img = header_img;
        }

        public String getCar_allname() {
            return car_allname;
        }

        public void setCar_allname(String car_allname) {
            this.car_allname = car_allname;
        }

        public String getOrder_system_time() {
            return order_system_time;
        }

        public void setOrder_system_time(String order_system_time) {
            this.order_system_time = order_system_time;
        }

        public String getShop_phone() {
            return shop_phone;
        }

        public void setShop_phone(String shop_phone) {
            this.shop_phone = shop_phone;
        }

        public int getLeast_rent_time() {
            return least_rent_time;
        }

        public void setLeast_rent_time(int least_rent_time) {
            this.least_rent_time = least_rent_time;
        }

        public String getStill_car_address() {
            return still_car_address;
        }

        public void setStill_car_address(String still_car_address) {
            this.still_car_address = still_car_address;
        }
    }
}
