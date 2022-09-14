package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * Created by ZL on 2019/6/18.
 */
public class WeiZhangHistoryBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"user_tel":"18945057021","ins_system":"2019-06-18 16:16:37","vin":"LVVDC11BX6D209011","tid":"E5F98D0549C84B97B593AF821E551F81"},{"user_tel":"18945057021","ins_system":"2019-06-18 16:20:13","vin":"LVVDC11BX6D209011","tid":"B3A1FE88591F4A9A8F5570A866A5472C"}]
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
         * user_tel : 18945057021
         * ins_system : 2019-06-18 16:16:37
         * vin : LVVDC11BX6D209011
         * tid : E5F98D0549C84B97B593AF821E551F81
         */

        private String user_tel;
        private String ins_system;
        private String vin;
        private String tid;

        public String getUser_tel() {
            return user_tel;
        }

        public void setUser_tel(String user_tel) {
            this.user_tel = user_tel;
        }

        public String getIns_system() {
            return ins_system;
        }

        public void setIns_system(String ins_system) {
            this.ins_system = ins_system;
        }

        public String getVin() {
            return vin;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}
