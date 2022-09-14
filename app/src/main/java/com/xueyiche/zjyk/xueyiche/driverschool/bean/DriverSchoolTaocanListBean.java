package com.xueyiche.zjyk.xueyiche.driverschool.bean;

import java.util.List;

/**
 * Created by Owner on 2018/5/29.
 */
public class DriverSchoolTaocanListBean {


    /**
     * content : [{"driver_school_id":"0fbb2025a39b4ffba32bf7859f2faf67","driver_school_service":"一站式拿证C1","cost_description":"费用明细br一、返现200元br打的费对方答复地方","driver_school_money":2580,"return_money":"200","package_id":1,"subscription":"680"},{"driver_school_id":"0fbb2025a39b4ffba32bf7859f2faf67","driver_school_service":"裸班式拿证C1","cost_description":"费用明细br一、返现380元br打的费对方答复地方","driver_school_money":2280,"return_money":"380","package_id":2,"subscription":"680"}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * driver_school_id : 0fbb2025a39b4ffba32bf7859f2faf67
     * driver_school_service : 一站式拿证C1
     * cost_description : 费用明细br一、返现200元br打的费对方答复地方
     * driver_school_money : 2580
     * return_money : 200
     * package_id : 1
     * subscription : 680
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
        private String driver_school_id;
        private String driver_school_service;
        private String cost_description;
        private String driver_school_money;
        private String return_money;
        private int package_id;
        private String subscription;
        private String subscription_v;

        public String getSubscription_v() {
            return subscription_v;
        }

        public void setSubscription_v(String subscription_v) {
            this.subscription_v = subscription_v;
        }

        public String getDriver_school_id() {
            return driver_school_id;
        }

        public void setDriver_school_id(String driver_school_id) {
            this.driver_school_id = driver_school_id;
        }

        public String getDriver_school_service() {
            return driver_school_service;
        }

        public void setDriver_school_service(String driver_school_service) {
            this.driver_school_service = driver_school_service;
        }

        public String getCost_description() {
            return cost_description;
        }

        public void setCost_description(String cost_description) {
            this.cost_description = cost_description;
        }

        public String getDriver_school_money() {
            return driver_school_money;
        }

        public void setDriver_school_money(String driver_school_money) {
            this.driver_school_money = driver_school_money;
        }

        public String getReturn_money() {
            return return_money;
        }

        public void setReturn_money(String return_money) {
            this.return_money = return_money;
        }

        public int getPackage_id() {
            return package_id;
        }

        public void setPackage_id(int package_id) {
            this.package_id = package_id;
        }

        public String getSubscription() {
            return subscription;
        }

        public void setSubscription(String subscription) {
            this.subscription = subscription;
        }
    }
}
