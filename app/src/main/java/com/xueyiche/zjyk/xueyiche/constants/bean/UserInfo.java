package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.io.Serializable;

/**
 * Created by Owner on 2016/10/31.
 */
public class UserInfo implements Serializable{


    /**
     * content : {"user_id":"199","user_name":"小石头","head_img":"http://otqopw3sl.bkt.clouddn.com/xyc_1508465710333jpg","sex":"1","user_cards":"65627c6b6a7262616a707464636a7b7e4859","nickname":"哈哈","user_phone":"66697f6d6f73626a627373","driver_cards":"65627d69617762616a71756163697b774f5f"}
     * code : 200
     * msg : 操作成功
     */

    private ContentBean content;
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
        /**
         * user_id : 199
         * user_name : 小石头
         * head_img : http://otqopw3sl.bkt.clouddn.com/xyc_1508465710333jpg
         * sex : 1
         * user_cards : 65627c6b6a7262616a707464636a7b7e4859
         * nickname : 哈哈
         * user_phone : 66697f6d6f73626a627373
         * driver_cards : 65627d69617762616a71756163697b774f5f
         */

        private String user_id;
        private String user_name;
        private String head_img;
        private String sex;
        private String user_cards;
        private String nickname;
        private String user_phone;
        private String driver_cards;
        private String first_type;
        private String integral_num;

        public String getIntegral_num() {
            return integral_num;
        }

        public void setIntegral_num(String integral_num) {
            this.integral_num = integral_num;
        }

        public String getFirst_type() {
            return first_type;
        }

        public void setFirst_type(String first_type) {
            this.first_type = first_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUser_cards() {
            return user_cards;
        }

        public void setUser_cards(String user_cards) {
            this.user_cards = user_cards;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getDriver_cards() {
            return driver_cards;
        }

        public void setDriver_cards(String driver_cards) {
            this.driver_cards = driver_cards;
        }
    }
}
