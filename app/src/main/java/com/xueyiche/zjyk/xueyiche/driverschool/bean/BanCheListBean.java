package com.xueyiche.zjyk.xueyiche.driverschool.bean;

import java.util.List;

/**
 * Created by ZL on 2019/4/29.
 */
public class BanCheListBean
{

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"driver_name":"张0灵","shifa_time":"08:00","zhongdian":"","shifa":"哈尔滨银行","id":2,"bus_number":"黑A-23456","driver_phone":"18756412781","bus_name":"一号班车","zhongdian_time":""},{"driver_name":"张三","shifa_time":"08:00","zhongdian":"","shifa":"哈尔滨银行","id":3,"bus_number":"黑A-22222","driver_phone":"18777777777","bus_name":"二号班车","zhongdian_time":""},{"driver_name":"李四","shifa_time":"08:00","zhongdian":"科技大厦","shifa":"哈尔滨银行","id":4,"bus_number":"黑A-44444","driver_phone":"18811144444","bus_name":"四号班车","zhongdian_time":"15:00"}]
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
         * driver_name : 张0灵
         * shifa_time : 08:00
         * zhongdian :
         * shifa : 哈尔滨银行
         * id : 2
         * bus_number : 黑A-23456
         * driver_phone : 18756412781
         * bus_name : 一号班车
         * zhongdian_time :
         */

        private String driver_name;
        private String shifa_time;
        private String zhongdian;
        private String shifa;
        private int id;
        private String bus_number;
        private String driver_phone;
        private String bus_name;
        private String zhongdian_time;

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getShifa_time() {
            return shifa_time;
        }

        public void setShifa_time(String shifa_time) {
            this.shifa_time = shifa_time;
        }

        public String getZhongdian() {
            return zhongdian;
        }

        public void setZhongdian(String zhongdian) {
            this.zhongdian = zhongdian;
        }

        public String getShifa() {
            return shifa;
        }

        public void setShifa(String shifa) {
            this.shifa = shifa;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBus_number() {
            return bus_number;
        }

        public void setBus_number(String bus_number) {
            this.bus_number = bus_number;
        }

        public String getDriver_phone() {
            return driver_phone;
        }

        public void setDriver_phone(String driver_phone) {
            this.driver_phone = driver_phone;
        }

        public String getBus_name() {
            return bus_name;
        }

        public void setBus_name(String bus_name) {
            this.bus_name = bus_name;
        }

        public String getZhongdian_time() {
            return zhongdian_time;
        }

        public void setZhongdian_time(String zhongdian_time) {
            this.zhongdian_time = zhongdian_time;
        }
    }
}
