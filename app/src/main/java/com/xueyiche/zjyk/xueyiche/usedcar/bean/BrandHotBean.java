package com.xueyiche.zjyk.xueyiche.usedcar.bean;

import java.util.List;

/**
 * Created by ZL on 2018/9/25.
 */
public class BrandHotBean {

    /**
     * code : 200
     * message : 信息请求成功
     * data : [{"id":1,"brand_name":"阿斯顿马丁","brand_img":"/tmsweb/style/images/show/asdmd3.jpg","mark":"A"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * brand_name : 阿斯顿马丁
         * brand_img : /tmsweb/style/images/show/asdmd3.jpg
         * mark : A
         */

        private int id;
        private String brand_name;
        private String brand_img;
        private String mark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getBrand_img() {
            return brand_img;
        }

        public void setBrand_img(String brand_img) {
            this.brand_img = brand_img;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }
    }
}
