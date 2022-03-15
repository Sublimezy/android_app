package com.xueyiche.zjyk.xueyiche.homepage.bean;

/**
 * Created by ZL on 2017/10/25.
 */
public class YiBiSelectLocationBean {


    /**
     * em_phone : 18341716546
     * easy_address_id : c8a1b694e22b401dac7248355f0a1edf
     * em_user_id : 183
     * em_user_name : 王庆磊
     * em_address : 哈尔滨市松北区商业大学附近小区
     */

    private ContentBean content;
    /**
     * content : {"em_phone":"18341716546","easy_address_id":"c8a1b694e22b401dac7248355f0a1edf","em_user_id":"183","em_user_name":"王庆磊","em_address":"哈尔滨市松北区商业大学附近小区"}
     * code : 0
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
        private String em_phone;
        private String easy_address_id;
        private String em_user_id;
        private String em_user_name;
        private String em_address;

        public String getEm_phone() {
            return em_phone;
        }

        public void setEm_phone(String em_phone) {
            this.em_phone = em_phone;
        }

        public String getEasy_address_id() {
            return easy_address_id;
        }

        public void setEasy_address_id(String easy_address_id) {
            this.easy_address_id = easy_address_id;
        }

        public String getEm_user_id() {
            return em_user_id;
        }

        public void setEm_user_id(String em_user_id) {
            this.em_user_id = em_user_id;
        }

        public String getEm_user_name() {
            return em_user_name;
        }

        public void setEm_user_name(String em_user_name) {
            this.em_user_name = em_user_name;
        }

        public String getEm_address() {
            return em_address;
        }

        public void setEm_address(String em_address) {
            this.em_address = em_address;
        }
    }
}
