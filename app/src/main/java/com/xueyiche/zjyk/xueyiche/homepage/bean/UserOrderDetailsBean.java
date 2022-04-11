package com.xueyiche.zjyk.xueyiche.homepage.bean;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 2022/4/11/4:31 下午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage.bean
 * #            xueyiche5.0
 */
public class UserOrderDetailsBean {

    /**
     * code : 1
     * msg : 请求成功
     * time : 1649665720
     * data : {"user_id":2,"order_sn":"2_20220330152520461","start_address":"哈尔滨","start_address_lng":"1","start_address_lat":"2","end_address":"道里","end_address_lng":"1","end_address_lat":"2","order_status":0,"order_type":0,"call_name":null,"call_mobile":null,"user_mobile":"18545560800","user_mobile_yin":"0800"}
     */

    private int code;
    private String msg;
    private String time;
    /**
     * user_id : 2
     * order_sn : 2_20220330152520461
     * start_address : 哈尔滨
     * start_address_lng : 1
     * start_address_lat : 2
     * end_address : 道里
     * end_address_lng : 1
     * end_address_lat : 2
     * order_status : 0
     * order_type : 0
     * call_name : null
     * call_mobile : null
     * user_mobile : 18545560800
     * user_mobile_yin : 0800
     */

    private DataBean data;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int user_id;
        private String order_sn;
        private String start_address;
        private String start_address_lng;
        private String start_address_lat;
        private String end_address;
        private String end_address_lng;
        private String end_address_lat;
        private int order_status;
        private int order_type;
        private Object call_name;
        private Object call_mobile;
        private String user_mobile;
        private String user_mobile_yin;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getStart_address() {
            return start_address;
        }

        public void setStart_address(String start_address) {
            this.start_address = start_address;
        }

        public String getStart_address_lng() {
            return start_address_lng;
        }

        public void setStart_address_lng(String start_address_lng) {
            this.start_address_lng = start_address_lng;
        }

        public String getStart_address_lat() {
            return start_address_lat;
        }

        public void setStart_address_lat(String start_address_lat) {
            this.start_address_lat = start_address_lat;
        }

        public String getEnd_address() {
            return end_address;
        }

        public void setEnd_address(String end_address) {
            this.end_address = end_address;
        }

        public String getEnd_address_lng() {
            return end_address_lng;
        }

        public void setEnd_address_lng(String end_address_lng) {
            this.end_address_lng = end_address_lng;
        }

        public String getEnd_address_lat() {
            return end_address_lat;
        }

        public void setEnd_address_lat(String end_address_lat) {
            this.end_address_lat = end_address_lat;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public Object getCall_name() {
            return call_name;
        }

        public void setCall_name(Object call_name) {
            this.call_name = call_name;
        }

        public Object getCall_mobile() {
            return call_mobile;
        }

        public void setCall_mobile(Object call_mobile) {
            this.call_mobile = call_mobile;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_mobile_yin() {
            return user_mobile_yin;
        }

        public void setUser_mobile_yin(String user_mobile_yin) {
            this.user_mobile_yin = user_mobile_yin;
        }
    }
}
