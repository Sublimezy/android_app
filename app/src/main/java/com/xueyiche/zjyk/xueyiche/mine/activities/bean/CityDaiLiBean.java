package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

import java.util.List;

/**
 * Created by ZL on 2017/9/15.
 */
public class CityDaiLiBean {

    /**
     * content : [{"area_no":"0412","area_name":"鞍山市","first_py":"A"},{"area_no":"0596","area_name":"漳州市","first_py":"Z"}]
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
         * area_no : 0412
         * area_name : 鞍山市
         * first_py : A
         */

        private String area_no;
        private String area_name;
        private String first_py;

        public String getArea_no() {
            return area_no;
        }

        public void setArea_no(String area_no) {
            this.area_no = area_no;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getFirst_py() {
            return first_py;
        }

        public void setFirst_py(String first_py) {
            this.first_py = first_py;
        }
    }
}
