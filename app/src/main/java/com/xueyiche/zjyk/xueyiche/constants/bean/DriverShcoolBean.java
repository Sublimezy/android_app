package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Owner on 2016/12/7.
 */
public class DriverShcoolBean implements Serializable {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"driver_school_id":"b34c480e934f452a9a20a418f675fb41","driver_school_name":"松北区龙轩路中电巨鹰考场练车场","wecsf":"无二次收费","distance":"12km","latitude":"45.834087","driver_school_money":"2880","return_money":"返现￥200","driver_school_url":"http://images.xueyiche.vip/20190613102800331.jpg","driver_school_place":"哈尔滨市松北区龙轩路西侧","min_school_price":"2680","longitude":"126.500633"}]
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
         * driver_school_id : b34c480e934f452a9a20a418f675fb41
         * driver_school_name : 松北区龙轩路中电巨鹰考场练车场
         * wecsf : 无二次收费
         * distance : 12km
         * latitude : 45.834087
         * driver_school_money : 2880
         * return_money : 返现￥200
         * driver_school_url : http://images.xueyiche.vip/20190613102800331.jpg
         * driver_school_place : 哈尔滨市松北区龙轩路西侧
         * min_school_price : 2680
         * longitude : 126.500633
         */

        private String driver_school_id;
        private String driver_school_name;
        private String wecsf;
        private String distance;
        private String latitude;
        private String driver_school_money;
        private String return_money;
        private String driver_school_url;
        private String driver_school_place;
        private String min_school_price;
        private String longitude;

        public String getDriver_school_id() {
            return driver_school_id;
        }

        public void setDriver_school_id(String driver_school_id) {
            this.driver_school_id = driver_school_id;
        }

        public String getDriver_school_name() {
            return driver_school_name;
        }

        public void setDriver_school_name(String driver_school_name) {
            this.driver_school_name = driver_school_name;
        }

        public String getWecsf() {
            return wecsf;
        }

        public void setWecsf(String wecsf) {
            this.wecsf = wecsf;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getDriver_school_money() {
            return driver_school_money;
        }

        public void setDriver_school_money(String driver_school_money) {
            this.driver_school_money = driver_school_money;
        }

        public String getReturn_money() {
            return return_money;
        }

        public void setReturn_money(String return_money) {
            this.return_money = return_money;
        }

        public String getDriver_school_url() {
            return driver_school_url;
        }

        public void setDriver_school_url(String driver_school_url) {
            this.driver_school_url = driver_school_url;
        }

        public String getDriver_school_place() {
            return driver_school_place;
        }

        public void setDriver_school_place(String driver_school_place) {
            this.driver_school_place = driver_school_place;
        }

        public String getMin_school_price() {
            return min_school_price;
        }

        public void setMin_school_price(String min_school_price) {
            this.min_school_price = min_school_price;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
