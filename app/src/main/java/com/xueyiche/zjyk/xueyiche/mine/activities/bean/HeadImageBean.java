package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

/**
 * Created by Owner on 2017/9/6.
 */
public class HeadImageBean {


    /**
     * user_id : 271
     * user_phone : 66627c6a69736b6b637271
     * user_name :
     * head_img : http://otqopw3sl.bkt.clouddn.com/xyc_1504678665709jpg
     * sex : 1
     * user_cards : 65627e6a6a7362616a737467636b78724b5b
     * driver_cards : 65627e6a6a7362616a737467636b78724b5b
     * province_id :
     * area_id :
     * county_id :
     * privince_name :
     * area_name :
     * county_name :
     * nickname : 嘿嘿嘿
     * address :
     */

    private ContentBean content;
    /**
     * content : {"user_id":"271","user_phone":"66627c6a69736b6b637271","user_name":"","head_img":"http://otqopw3sl.bkt.clouddn.com/xyc_1504678665709jpg","sex":"1","user_cards":"65627e6a6a7362616a737467636b78724b5b","driver_cards":"65627e6a6a7362616a737467636b78724b5b","province_id":"","area_id":"","county_id":"","privince_name":"","area_name":"","county_name":"","nickname":"嘿嘿嘿","address":""}
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
        private String user_id;
        private String user_phone;
        private String user_name;
        private String head_img;
        private String sex;
        private String user_cards;
        private String driver_cards;
        private String province_id;
        private String area_id;
        private String county_id;
        private String privince_name;
        private String area_name;
        private String county_name;
        private String nickname;
        private String address;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
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

        public String getDriver_cards() {
            return driver_cards;
        }

        public void setDriver_cards(String driver_cards) {
            this.driver_cards = driver_cards;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getCounty_id() {
            return county_id;
        }

        public void setCounty_id(String county_id) {
            this.county_id = county_id;
        }

        public String getPrivince_name() {
            return privince_name;
        }

        public void setPrivince_name(String privince_name) {
            this.privince_name = privince_name;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getCounty_name() {
            return county_name;
        }

        public void setCounty_name(String county_name) {
            this.county_name = county_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
