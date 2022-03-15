package com.xueyiche.zjyk.xueyiche.practicecar.bean;

import java.util.List;

/**
 * Created by ZL on 2019/3/20.
 */
public class OrderRecordBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"xlxm":"坡起，倒库","km":"2","is_del":"\u201c0\u201d","yuyue_date":"2019-03-15","ew_code_used":"\u201c\u201d","driver_id":"\u201c\u201d","driver_name":"\u201c\u201d","driver_school_id":"\u201c\u201d","driver_school_name":"\u201c\u201d","xunlian_status ":"\u201c已训练\u201d"}]
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
         * xlxm : 坡起，倒库
         * km : 2
         * is_del : “0”
         * yuyue_date : 2019-03-15
         * ew_code_used : “”
         * driver_id : “”
         * driver_name : “”
         * driver_school_id : “”
         * driver_school_name : “”
         * xunlian_status  : “已训练”
         */

        private String xlxm;
        private String km;
        private String is_del;
        private String yuyue_date;
        private String ew_code_used;
        private String driver_id;
        private String driver_name;
        private String driver_school_id;
        private String driver_school_name;
        private String xunlian_status;
        private String order_number;
        private String head_img;
        private String pingjia;

        public String getPingjia() {
            return pingjia;
        }

        public void setPingjia(String pingjia) {
            this.pingjia = pingjia;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getXlxm() {
            return xlxm;
        }

        public void setXlxm(String xlxm) {
            this.xlxm = xlxm;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getYuyue_date() {
            return yuyue_date;
        }

        public void setYuyue_date(String yuyue_date) {
            this.yuyue_date = yuyue_date;
        }

        public String getEw_code_used() {
            return ew_code_used;
        }

        public void setEw_code_used(String ew_code_used) {
            this.ew_code_used = ew_code_used;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

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

        public String getXunlian_status() {
            return xunlian_status;
        }

        public void setXunlian_status(String xunlian_status) {
            this.xunlian_status = xunlian_status;
        }
    }
}
