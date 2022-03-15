package com.xueyiche.zjyk.xueyiche.usedcar.bean;

import java.util.List;

/**
 * Created by Owner on 2018/7/17.
 */
public class UsedCarCheXiBean {


    /**
     * code : 200
     * message : 信息请求成功
     * data : [{"id":1,"system_name":"A6L","system_img":""},{"id":2,"system_name":"dddd","system_img":""},{"id":6,"system_name":"ww1-1xi","system_img":""},{"id":7,"system_name":" ww1-2q","system_img":"http://car.xueyiche.vip/c1041201807170924163359.jpg"}]
     */

    private int code;
    private String message;
    /**
     * id : 1
     * system_name : A6L
     * system_img :
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
        private String system_name;
        private String system_img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSystem_name() {
            return system_name;
        }

        public void setSystem_name(String system_name) {
            this.system_name = system_name;
        }

        public String getSystem_img() {
            return system_img;
        }

        public void setSystem_img(String system_img) {
            this.system_img = system_img;
        }
    }
}
