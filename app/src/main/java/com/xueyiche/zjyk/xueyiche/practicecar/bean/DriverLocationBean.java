package com.xueyiche.zjyk.xueyiche.practicecar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/9/5.
 */
public class DriverLocationBean implements Serializable {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"driver_name":"王晓春","driver_id":"4520e50fd67211e7aeb85254f2dc841f","longitude_driver":"","head_img":"http://images.xueyiche.vip/20180315162449333.jpg","sex":"1","on_off":0,"driver_phone":"66627a686a75636e617577","latitude_driver":"","age":"46"},{"driver_name":"张师傅","driver_id":"18d5361fd34a11e8b10e5254f2dc841f","longitude_driver":"126.578762","head_img":"http://xychead.xueyiche.vip/20181206173612","sex":"1","on_off":0,"driver_phone":"66647a6a6f70636e637a77","latitude_driver":"45.888208","age":"38"}]
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

    public static class ContentBean implements Serializable {
        /**
         * driver_name : 王晓春
         * driver_id : 4520e50fd67211e7aeb85254f2dc841f
         * longitude_driver :
         * head_img : http://images.xueyiche.vip/20180315162449333.jpg
         * sex : 1
         * on_off : 0
         * driver_phone : 66627a686a75636e617577
         * latitude_driver :
         * age : 46
         */

        private String driver_name;
        private String driver_id;
        private String longitude_driver;
        private String head_img;
        private String sex;
        private int on_off;
        private String driver_phone;
        private String latitude_driver;
        private String age;

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getLongitude_driver() {
            return longitude_driver;
        }

        public void setLongitude_driver(String longitude_driver) {
            this.longitude_driver = longitude_driver;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getOn_off() {
            return on_off;
        }

        public void setOn_off(int on_off) {
            this.on_off = on_off;
        }

        public String getDriver_phone() {
            return driver_phone;
        }

        public void setDriver_phone(String driver_phone) {
            this.driver_phone = driver_phone;
        }

        public String getLatitude_driver() {
            return latitude_driver;
        }

        public void setLatitude_driver(String latitude_driver) {
            this.latitude_driver = latitude_driver;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
