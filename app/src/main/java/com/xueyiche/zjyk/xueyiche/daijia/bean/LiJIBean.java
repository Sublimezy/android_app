package com.xueyiche.zjyk.xueyiche.daijia.bean;

/**
 * Created by Administrator on 2019/9/29.
 */
public class LiJIBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"get_down_address":"利民大道永达路88号","driver_id":"","on_latitude":"45.804438","order_number":"D1567477948924","down_latitude":"45.888208","order_status":"0","pay_status":"0","gatewayType":"","driving_year":null,"nickname":null,"job_number":null,"act_distance":"","is_cancle":"0","head_img":null,"order_distance":"14.1","get_on_name":"科技大厦","get_on_address":"科技大厦","get_down_name":"地恒托斯卡纳","driver_phone":null,"down_longitude":"126.578762","order_time4":"","driver_name":null,"order_time2":"","order_time3":"","score_num":0,"on_longitude":"126.51342","user_id":"271","true_phone":"","order_time1":"2019-09-03 10:32:29","user_amount":39}
     */

    private String msg;
    private int code;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * get_down_address : 利民大道永达路88号
         * driver_id :
         * on_latitude : 45.804438
         * order_number : D1567477948924
         * down_latitude : 45.888208
         * order_status : 0
         * pay_status : 0
         * gatewayType :
         * driving_year : null
         * nickname : null
         * job_number : null
         * act_distance :
         * is_cancle : 0
         * head_img : null
         * order_distance : 14.1
         * get_on_name : 科技大厦
         * get_on_address : 科技大厦
         * get_down_name : 地恒托斯卡纳
         * driver_phone : null
         * down_longitude : 126.578762
         * order_time4 :
         * driver_name : null
         * order_time2 :
         * order_time3 :
         * score_num : 0
         * on_longitude : 126.51342
         * user_id : 271
         * true_phone :
         * order_time1 : 2019-09-03 10:32:29
         * user_amount : 39
         */

        private String get_down_address;
        private String driver_id;
        private String on_latitude;
        private String order_number;
        private String down_latitude;
        private String order_status;
        private String pay_status;
        private String gatewayType;
        private Object driving_year;
        private Object nickname;
        private Object job_number;
        private String act_distance;
        private String is_cancle;
        private Object head_img;
        private String order_distance;
        private String get_on_name;
        private String get_on_address;
        private String get_down_name;
        private Object driver_phone;
        private String down_longitude;
        private String order_time4;
        private Object driver_name;
        private String order_time2;
        private String order_time3;
        private int score_num;
        private String on_longitude;
        private String user_id;
        private String true_phone;
        private String order_time1;
        private String user_amount;

        public String getGet_down_address() {
            return get_down_address;
        }

        public void setGet_down_address(String get_down_address) {
            this.get_down_address = get_down_address;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getOn_latitude() {
            return on_latitude;
        }

        public void setOn_latitude(String on_latitude) {
            this.on_latitude = on_latitude;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getDown_latitude() {
            return down_latitude;
        }

        public void setDown_latitude(String down_latitude) {
            this.down_latitude = down_latitude;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getGatewayType() {
            return gatewayType;
        }

        public void setGatewayType(String gatewayType) {
            this.gatewayType = gatewayType;
        }

        public Object getDriving_year() {
            return driving_year;
        }

        public void setDriving_year(Object driving_year) {
            this.driving_year = driving_year;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public Object getJob_number() {
            return job_number;
        }

        public void setJob_number(Object job_number) {
            this.job_number = job_number;
        }

        public String getAct_distance() {
            return act_distance;
        }

        public void setAct_distance(String act_distance) {
            this.act_distance = act_distance;
        }

        public String getIs_cancle() {
            return is_cancle;
        }

        public void setIs_cancle(String is_cancle) {
            this.is_cancle = is_cancle;
        }

        public Object getHead_img() {
            return head_img;
        }

        public void setHead_img(Object head_img) {
            this.head_img = head_img;
        }

        public String getOrder_distance() {
            return order_distance;
        }

        public void setOrder_distance(String order_distance) {
            this.order_distance = order_distance;
        }

        public String getGet_on_name() {
            return get_on_name;
        }

        public void setGet_on_name(String get_on_name) {
            this.get_on_name = get_on_name;
        }

        public String getGet_on_address() {
            return get_on_address;
        }

        public void setGet_on_address(String get_on_address) {
            this.get_on_address = get_on_address;
        }

        public String getGet_down_name() {
            return get_down_name;
        }

        public void setGet_down_name(String get_down_name) {
            this.get_down_name = get_down_name;
        }

        public Object getDriver_phone() {
            return driver_phone;
        }

        public void setDriver_phone(Object driver_phone) {
            this.driver_phone = driver_phone;
        }

        public String getDown_longitude() {
            return down_longitude;
        }

        public void setDown_longitude(String down_longitude) {
            this.down_longitude = down_longitude;
        }

        public String getOrder_time4() {
            return order_time4;
        }

        public void setOrder_time4(String order_time4) {
            this.order_time4 = order_time4;
        }

        public Object getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(Object driver_name) {
            this.driver_name = driver_name;
        }

        public String getOrder_time2() {
            return order_time2;
        }

        public void setOrder_time2(String order_time2) {
            this.order_time2 = order_time2;
        }

        public String getOrder_time3() {
            return order_time3;
        }

        public void setOrder_time3(String order_time3) {
            this.order_time3 = order_time3;
        }

        public int getScore_num() {
            return score_num;
        }

        public void setScore_num(int score_num) {
            this.score_num = score_num;
        }

        public String getOn_longitude() {
            return on_longitude;
        }

        public void setOn_longitude(String on_longitude) {
            this.on_longitude = on_longitude;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTrue_phone() {
            return true_phone;
        }

        public void setTrue_phone(String true_phone) {
            this.true_phone = true_phone;
        }

        public String getOrder_time1() {
            return order_time1;
        }

        public void setOrder_time1(String order_time1) {
            this.order_time1 = order_time1;
        }

        public String getUser_amount() {
            return user_amount;
        }

        public void setUser_amount(String user_amount) {
            this.user_amount = user_amount;
        }
    }
}
