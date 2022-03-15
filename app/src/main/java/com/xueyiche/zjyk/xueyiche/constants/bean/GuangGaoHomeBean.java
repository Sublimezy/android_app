package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.util.List;

/**
 * Created by Owner on 2017/4/28.
 */
public class GuangGaoHomeBean {


    /**
     * content : [{"city_id":"1001191000","end_date":"2017-05-30","picture_size":"720*1080","picture_url":"http://222.171.205.7:10082/xyc_bms/uploadFiles/uploadImgs/20170328/1f6e0f8df3df4dea80f77ce4bab3127d.jpg","end_time":"18:00:00","start_time":"10:00:00","web_content_url":"http://222.171.205.7:10082/xyc_bms/uploadFiles/ckht/20170425/20170425164357.html","start_date":"2017-01-01","picture_refer_id":"0a693634941f47188147dc173f6184eJ","audit_status":"1","version":"1.0.1"}]
     * code : 0
     * msg : 操作成功
     * version : 1.0.1
     */

    private int code;
    private String msg;
    private String version;
    /**
     * city_id : 1001191000
     * end_date : 2017-05-30
     * picture_size : 720*1080
     * picture_url : http://222.171.205.7:10082/xyc_bms/uploadFiles/uploadImgs/20170328/1f6e0f8df3df4dea80f77ce4bab3127d.jpg
     * end_time : 18:00:00
     * start_time : 10:00:00
     * web_content_url : http://222.171.205.7:10082/xyc_bms/uploadFiles/ckht/20170425/20170425164357.html
     * start_date : 2017-01-01
     * picture_refer_id : 0a693634941f47188147dc173f6184eJ
     * audit_status : 1
     * version : 1.0.1
     */

    private List<ContentBean> content;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String city_id;
        private String end_date;
        private String picture_size;
        private String picture_url;
        private String end_time;
        private String start_time;
        private String web_content_url;
        private String start_date;
        private String picture_refer_id;
        private String audit_status;
        private String version;

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getPicture_size() {
            return picture_size;
        }

        public void setPicture_size(String picture_size) {
            this.picture_size = picture_size;
        }

        public String getPicture_url() {
            return picture_url;
        }

        public void setPicture_url(String picture_url) {
            this.picture_url = picture_url;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getWeb_content_url() {
            return web_content_url;
        }

        public void setWeb_content_url(String web_content_url) {
            this.web_content_url = web_content_url;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getPicture_refer_id() {
            return picture_refer_id;
        }

        public void setPicture_refer_id(String picture_refer_id) {
            this.picture_refer_id = picture_refer_id;
        }

        public String getAudit_status() {
            return audit_status;
        }

        public void setAudit_status(String audit_status) {
            this.audit_status = audit_status;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
