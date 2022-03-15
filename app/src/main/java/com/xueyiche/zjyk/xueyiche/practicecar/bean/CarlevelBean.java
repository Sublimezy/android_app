package com.xueyiche.zjyk.xueyiche.practicecar.bean;

import java.util.List;

/**
 * Created by YJF on 2019/12/18.
 */
public class CarlevelBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"hour_price":"A级车 100元","car_level":"1"},{"hour_price":"B级车 120元","car_level":"2"}]
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
         * hour_price : A级车 100元
         * car_level : 1
         */

        private String hour_price;
        private String car_level;

        public String getHour_price() {
            return hour_price;
        }

        public void setHour_price(String hour_price) {
            this.hour_price = hour_price;
        }

        public String getCar_level() {
            return car_level;
        }

        public void setCar_level(String car_level) {
            this.car_level = car_level;
        }
    }
}
