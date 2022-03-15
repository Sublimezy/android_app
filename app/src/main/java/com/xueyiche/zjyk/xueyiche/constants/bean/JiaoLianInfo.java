package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.util.List;

/**
 * Created by Owner on 2016/11/3.
 */
public class JiaoLianInfo {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"hour_price":1,"driver_id":"18d5361fd34a11e8b10e5254f2dc841f","order_count":"已售 14","longitude_driver":"126.578762","hand_auto":"手动挡","car_url":null,"sex":"1","head_img":"http://xychead.xueyiche.vip/20181206173612","brand_name":"本田","from_user":"9.2km","driver_name":"张教练","seriesname":"里程","driving_year":"4年驾龄","on_off":0,"latitude_driver":"45.888208"},{"hour_price":0,"driver_id":"69022bfa170a11ea8115525421981681","order_count":"已售 0","longitude_driver":"125.94048","hand_auto":"手动挡","car_url":null,"sex":"0","head_img":"http://car.xueyiche.vip/bf4cc201912051053251868.jpg","brand_name":"大众","from_user":"","driver_name":"提货函22","seriesname":"夏朗","driving_year":"1年驾龄","order_type":0,"latitude_driver":"46.049305"},{"hour_price":2,"driver_id":"4520e50fd67211e7aeb85254f2dc841f","order_count":"已售 0","longitude_driver":"125.94048","hand_auto":"自动挡","car_url":"http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg","sex":"1","head_img":"http://images.xueyiche.vip/20180315162449333.jpg","brand_name":"凯迪拉克","from_user":"53.7km","driver_name":"王晓春","seriesname":"凯迪拉克STS","driving_year":"30年驾龄","order_type":0,"latitude_driver":"46.049305"}]
     */

    private String msg;
    private int code;
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
        /**
         * hour_price : 1
         * driver_id : 18d5361fd34a11e8b10e5254f2dc841f
         * order_count : 已售 14
         * longitude_driver : 126.578762
         * hand_auto : 手动挡
         * car_url : null
         * sex : 1
         * head_img : http://xychead.xueyiche.vip/20181206173612
         * brand_name : 本田
         * from_user : 9.2km
         * driver_name : 张教练
         * seriesname : 里程
         * driving_year : 4年驾龄
         * on_off : 0
         * latitude_driver : 45.888208
         * order_type : 0
         */

        private String hour_price;
        private String driver_id;
        private String order_count;
        private String longitude_driver;
        private String hand_auto;
        private String car_url;
        private String sex;
        private String head_img;
        private String brand_name;
        private String from_user;
        private String driver_name;
        private String seriesname;
        private String driving_year;
        private String on_off;
        private String latitude_driver;
        private String order_type;

        public void setCar_url(String car_url) {
            this.car_url = car_url;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getOrder_count() {
            return order_count;
        }

        public void setOrder_count(String order_count) {
            this.order_count = order_count;
        }

        public String getLongitude_driver() {
            return longitude_driver;
        }

        public void setLongitude_driver(String longitude_driver) {
            this.longitude_driver = longitude_driver;
        }

        public String getHand_auto() {
            return hand_auto;
        }

        public void setHand_auto(String hand_auto) {
            this.hand_auto = hand_auto;
        }

        public String getCar_url() {
            return car_url;
        }


        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getFrom_user() {
            return from_user;
        }

        public void setFrom_user(String from_user) {
            this.from_user = from_user;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getSeriesname() {
            return seriesname;
        }

        public void setSeriesname(String seriesname) {
            this.seriesname = seriesname;
        }

        public String getDriving_year() {
            return driving_year;
        }

        public void setDriving_year(String driving_year) {
            this.driving_year = driving_year;
        }


        public String getLatitude_driver() {
            return latitude_driver;
        }

        public void setLatitude_driver(String latitude_driver) {
            this.latitude_driver = latitude_driver;
        }

        public String getHour_price() {
            return hour_price;
        }

        public void setHour_price(String hour_price) {
            this.hour_price = hour_price;
        }

        public String getOn_off() {
            return on_off;
        }

        public void setOn_off(String on_off) {
            this.on_off = on_off;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }
    }
}
