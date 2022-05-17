package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * Created by Owner on 2018/1/15.
 */
public class ShouYeBannerBean {


    private int code;
    private String msg;
    private String time;
    private List<DataBean> data;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String title;
        private String mini_image;
        private String url_type;
        private String url;
        private String createtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMini_image() {
            return mini_image;
        }

        public void setMini_image(String mini_image) {
            this.mini_image = mini_image;
        }

        public String getUrl_type() {
            return url_type;
        }

        public void setUrl_type(String url_type) {
            this.url_type = url_type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
