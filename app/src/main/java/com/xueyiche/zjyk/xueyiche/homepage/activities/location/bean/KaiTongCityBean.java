package com.xueyiche.zjyk.xueyiche.homepage.activities.location.bean;

import java.util.List;

/**
 * Created by ZL on 2017/1/13.
 */
public class KaiTongCityBean {
    /**
     * content : [{"area_name":"南岗区","area_id":"100119100010002"},{"area_name":"道里区","area_id":"100119100010001"},{"area_name":"香坊区","area_id":"100119100010005"},{"area_name":"道外区","area_id":"100119100010003"},{"area_name":"松北区","area_id":"100119100010000"},{"area_name":"平房区","area_id":"100119100010004"},{"area_name":"阿城区","area_id":"100119100010007"},{"area_name":"双城区","area_id":"100119100010008"},{"area_name":"呼兰区","area_id":"100119100010006"},{"area_name":"宾县","area_id":"100119100010013"},{"area_name":"五常市","area_id":"100119100010010"}]
     * code : 200
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
         * area_name : 南岗区
         * area_id : 100119100010002
         */

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

    /**
     * content : [{"area_id":"1001191000","area_code":"1","area_name":"哈尔滨市"},{"area_id":"1001290000","area_code":"0","area_name":"北京"},{"area_id":"1001320000","area_code":"0","area_name":"上海"},{"area_id":"1001111000","area_code":"0","area_name":"广州"},{"area_id":"1001111001","area_code":"0","area_name":"深圳市"}]
     * code : 0
     * msg : 操作成功
     */


}
