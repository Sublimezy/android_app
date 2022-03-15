package com.xueyiche.zjyk.xueyiche.practicecar.bean;

import java.util.List;

/**
 * Created by Owner on 2018/4/20.
 */
public class DriverYiXiangBean {

    /**
     * content : [{"area_name":"松北区","area_id":"100119100010000"},{"area_name":"南岗区","area_id":"100119100010002"}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * area_name : 松北区
     * area_id : 100119100010000
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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String area_name;
        private String area_id;

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }
    }
}
