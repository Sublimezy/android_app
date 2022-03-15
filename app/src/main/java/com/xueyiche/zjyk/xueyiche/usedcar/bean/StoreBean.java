package com.xueyiche.zjyk.xueyiche.usedcar.bean;

import java.util.List;

/**
 * Created by Owner on 2018/7/19.
 */
public class StoreBean {

    /**
     * code : 200
     * message : 信息请求成功
     * data : [{"id":1,"shop_name":"ceshi","shop_img":"http://car.xueyiche.vip/de080201807181043134953.jpg","address":"哈尔滨市松北区黑龙江省ETC运营管理中心"}]
     */

    private int code;
    private String message;
    /**
     * id : 1
     * shop_name : ceshi
     * shop_img : http://car.xueyiche.vip/de080201807181043134953.jpg
     * address : 哈尔滨市松北区黑龙江省ETC运营管理中心
     */

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
        private int id;
        private String shop_name;
        private String shop_img;
        private String address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_img() {
            return shop_img;
        }

        public void setShop_img(String shop_img) {
            this.shop_img = shop_img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
