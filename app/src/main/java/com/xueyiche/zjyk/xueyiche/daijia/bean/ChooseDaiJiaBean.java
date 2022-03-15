package com.xueyiche.zjyk.xueyiche.daijia.bean;

/**
 * Created by ZL on 2020/3/15.
 */
public class ChooseDaiJiaBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"driver_id":"1124bdfe0c3011ea8115525421981681","device_id":"101d85590923a57defe","user_name":"鹿永超","job_number":"HEBS00001","on_off":"1","driver_status":"0"}
     */

    private String msg;
    private int code;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * driver_id : 1124bdfe0c3011ea8115525421981681
         * device_id : 101d85590923a57defe
         * user_name : 鹿永超
         * job_number : HEBS00001
         * on_off : 1
         * driver_status : 0
         */

        private String driver_id;
        private String device_id;
        private String user_name;
        private String job_number;
        private String on_off;
        private String driver_status;

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getJob_number() {
            return job_number;
        }

        public void setJob_number(String job_number) {
            this.job_number = job_number;
        }

        public String getOn_off() {
            return on_off;
        }

        public void setOn_off(String on_off) {
            this.on_off = on_off;
        }

        public String getDriver_status() {
            return driver_status;
        }

        public void setDriver_status(String driver_status) {
            this.driver_status = driver_status;
        }
    }
}
