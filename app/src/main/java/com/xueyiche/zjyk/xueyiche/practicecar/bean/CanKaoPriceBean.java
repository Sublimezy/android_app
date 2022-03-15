package com.xueyiche.zjyk.xueyiche.practicecar.bean;

import java.util.List;

/**
 * Created by ZL on 2018/3/13.
 */
public class CanKaoPriceBean {

    /**
     * content : [{"hour_price":80,"car_price":1},{"hour_price":100,"car_price":2},{"hour_price":120,"car_price":3},{"hour_price":180,"car_price":4},{"hour_price":220,"car_price":5}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
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
        /**
         * hour_price : 80
         * car_price : 1
         */

        private int hour_price;
        private int car_price;

        public int getHour_price() {
            return hour_price;
        }

        public void setHour_price(int hour_price) {
            this.hour_price = hour_price;
        }

        public int getCar_price() {
            return car_price;
        }

        public void setCar_price(int car_price) {
            this.car_price = car_price;
        }
    }
}
