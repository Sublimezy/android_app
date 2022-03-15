package com.xueyiche.zjyk.xueyiche.xycindent.bean;

/**
 * Created by Owner on 2018/5/22.
 */
public class PracticeGoingBean {

    /**
     * driver_name : 刘艳东 先生
     * now_time : 2018-05-22 13:43:02
     * start_time : 2018-05-17 16:00:00
     * practice_hours : 2
     * head_img : http://images.xueyiche.vip/Fj0MKdnP0uYHwqlX1javtIR8X5tF
     * end_time : 2018-05-17 18:00:00
     * driver_infomation : 标致408 黑A：77777
     * driver_phone : 66647a6d6c736a6963737d
     */

    private ContentBean content;
    /**
     * content : {"driver_name":"刘艳东 先生","now_time":"2018-05-22 13:43:02","start_time":"2018-05-17 16:00:00","practice_hours":"2","head_img":"http://images.xueyiche.vip/Fj0MKdnP0uYHwqlX1javtIR8X5tF","end_time":"2018-05-17 18:00:00","driver_infomation":"标致408 黑A：77777","driver_phone":"66647a6d6c736a6963737d"}
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

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

    public static class ContentBean {
        private String driver_name;
        private String now_time;
        private String start_time;
        private String practice_hours;
        private String head_img;
        private String end_time;
        private String driver_infomation;
        private String driver_phone;
        private String about_begin_time;
        private String user_confirm;
        private String act_start_time;

        public String getUser_confirm() {
            return user_confirm;
        }

        public void setUser_confirm(String user_confirm) {
            this.user_confirm = user_confirm;
        }

        public String getAct_start_time() {
            return act_start_time;
        }

        public void setAct_start_time(String act_start_time) {
            this.act_start_time = act_start_time;
        }

        public String getAbout_begin_time() {
            return about_begin_time;
        }

        public void setAbout_begin_time(String about_begin_time) {
            about_begin_time = about_begin_time;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getNow_time() {
            return now_time;
        }

        public void setNow_time(String now_time) {
            this.now_time = now_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getPractice_hours() {
            return practice_hours;
        }

        public void setPractice_hours(String practice_hours) {
            this.practice_hours = practice_hours;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getDriver_infomation() {
            return driver_infomation;
        }

        public void setDriver_infomation(String driver_infomation) {
            this.driver_infomation = driver_infomation;
        }

        public String getDriver_phone() {
            return driver_phone;
        }

        public void setDriver_phone(String driver_phone) {
            this.driver_phone = driver_phone;
        }
    }
}
