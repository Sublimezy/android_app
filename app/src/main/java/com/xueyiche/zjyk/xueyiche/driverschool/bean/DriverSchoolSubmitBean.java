package com.xueyiche.zjyk.xueyiche.driverschool.bean;

/**
 * Created by Owner on 2018/1/30.
 */
public class DriverSchoolSubmitBean {


    /**
     * driver_school_id : 8b664865a14641e7a537efeffa81975a
     * driver_school_name : 金朋驾校
     * distance : 0
     * user_name : 小马哥
     * user_phone : 18811149874
     * driver_school_url : http://images.xueyiche.vip/FhWCnQyQS_CAazKR7JFbQbbA2MGS
     * subscription : 500
     * package_detail : 测试--套餐详情1
     * package_price : 2580
     */

    private ContentBean content;
    /**
     * content : {"driver_school_id":"8b664865a14641e7a537efeffa81975a","driver_school_name":"金朋驾校","distance":"0","user_name":"小马哥","user_phone":"18811149874","driver_school_url":"http://images.xueyiche.vip/FhWCnQyQS_CAazKR7JFbQbbA2MGS","subscription":"500","package_detail":"测试--套餐详情1","package_price":2580}
     * code : 200
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
        private String driver_school_id;
        private String driver_school_name;
        private String distance;
        private String user_name;
        private String user_phone;
        private String driver_school_url;
        private String subscription;
        private String package_detail;
        private int package_price;

        public String getDriver_school_id() {
            return driver_school_id;
        }

        public void setDriver_school_id(String driver_school_id) {
            this.driver_school_id = driver_school_id;
        }

        public String getDriver_school_name() {
            return driver_school_name;
        }

        public void setDriver_school_name(String driver_school_name) {
            this.driver_school_name = driver_school_name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getDriver_school_url() {
            return driver_school_url;
        }

        public void setDriver_school_url(String driver_school_url) {
            this.driver_school_url = driver_school_url;
        }

        public String getSubscription() {
            return subscription;
        }

        public void setSubscription(String subscription) {
            this.subscription = subscription;
        }

        public String getPackage_detail() {
            return package_detail;
        }

        public void setPackage_detail(String package_detail) {
            this.package_detail = package_detail;
        }

        public int getPackage_price() {
            return package_price;
        }

        public void setPackage_price(int package_price) {
            this.package_price = package_price;
        }
    }
}
