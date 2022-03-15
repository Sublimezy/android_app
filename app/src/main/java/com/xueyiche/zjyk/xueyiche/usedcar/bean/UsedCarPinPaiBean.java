package com.xueyiche.zjyk.xueyiche.usedcar.bean;

import java.util.List;

/**
 * Created by Owner on 2018/7/17.
 */
public class UsedCarPinPaiBean {

    /**
     * code : 200
     * message : 信息请求成功
     * data : {"brandList":[{"id":1,"brand_name":"奥迪","brand_img":"http://car.xueyiche.vip\\0f7cc201807101545426285.jpg","mark":"A"},{"id":3,"brand_name":"h","brand_img":"car.xueyiche.vip\\c35ca20180710150103836.jpg","mark":"H"},{"id":6,"brand_name":"dd","brand_img":"http://car.xueyiche.vip\\3efe3201807101532196212.jpg","mark":"D"},{"id":7,"brand_name":"1111","brand_img":"http://car.xueyiche.vip\\d7212201807110851454196.jpg","mark":""}],"brandhot":[{"id":1,"brand_name":"奥迪","brand_img":"http://car.xueyiche.vip\\0f7cc201807101545426285.jpg","mark":"A"},{"id":7,"brand_name":"1111","brand_img":"http://car.xueyiche.vip\\d7212201807110851454196.jpg","mark":""}]}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * brand_name : 奥迪
         * brand_img : http://car.xueyiche.vip\0f7cc201807101545426285.jpg
         * mark : A
         */

        private List<BrandListBean> brandList;
        /**
         * id : 1
         * brand_name : 奥迪
         * brand_img : http://car.xueyiche.vip\0f7cc201807101545426285.jpg
         * mark : A
         */

        private List<BrandhotBean> brandhot;

        public List<BrandListBean> getBrandList() {
            return brandList;
        }

        public void setBrandList(List<BrandListBean> brandList) {
            this.brandList = brandList;
        }

        public List<BrandhotBean> getBrandhot() {
            return brandhot;
        }

        public void setBrandhot(List<BrandhotBean> brandhot) {
            this.brandhot = brandhot;
        }

        public static class BrandListBean {
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

        public static class BrandhotBean {
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
}
