package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.util.List;

/**
 * Created by ZL on 2018/3/13.
 */
public class OpenCityBean {

    /**
     * content : [{"area_id":"1001191000","area_code":"1","area_name":"哈尔滨市"},{"area_id":"1001290000","area_code":"0","area_name":"北京"},{"area_id":"1001320000","area_code":"0","area_name":"上海"},{"area_id":"1001111000","area_code":"0","area_name":"广州"},{"area_id":"1001111001","area_code":"0","area_name":"深圳市"}]
     * code : 0
     * msg : 操作成功
     */

    private int code;
    private String msg;
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
        /**
         * area_id : 1001191000
         * area_code : 1
         * area_name : 哈尔滨市
         */

        private String area_id;
        private String area_code;
        private String area_name;

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }
    }
}
