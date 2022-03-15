package com.xueyiche.zjyk.xueyiche.daijia.bean;

/**
 * Created by Administrator on 2019/9/29.
 */
public class OrderInfoBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"get_down_address":"800号","driver_id":"","get_on_name":"科技大厦","on_latitude":"45.804077","cancle_reason":"","talk_to_driver":"","true_phone":"66627c6a69736b6b637271","area_id":"1001191000","order_status":"0","driver_amount3":0,"gatewayType":"","driver_amount2":0,"driver_need2":"0","user_amount3":0,"user_amount2":0,"job_number":null,"driver_need3":"0","act_distance":"","over_time":"0","order_number2":"J1570614984229","longitude":null,"order_number3":"M1570614984231","is_cancle":"0","order_distance":"17227","get_on_address":"中国黑龙江省哈尔滨市松北区松北街道创新二路","driver_phone":null,"driver_amount":1.78,"gatewayNumber3":null,"score_num":0,"gatewayNumber2":null,"appointed_time":null,"user_id":"271","over_distance":"0","user_amount":2,"user_name":"尹杰飞","order_number":"D1570614984207","latitude":null,"down_latitude":"45.888208","pay_status":"0","driving_year":null,"nickname":null,"user_phone":"66627c6a69736b6b637271","pay_status3":"0","pay_status2":"0","user_head":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","head_img":null,"get_down_name":"中央大街","pay_time2":"","pay_time3":"","down_longitude":"126.578762","order_time4":"","driver_name":null,"order_time2":"","order_time3":"","on_longitude":"126.51352","order_time1":"2019-10-09 17:56:24","gatewayType3":"","gatewayType2":"","need_desc3":"正常单，无额外费用","need_desc2":"正常单，无额外费用","gatewayNumber":null}
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
         * get_down_address : 800号
         * driver_id :
         * get_on_name : 科技大厦
         * on_latitude : 45.804077
         * cancle_reason :
         * talk_to_driver :
         * true_phone : 66627c6a69736b6b637271
         * area_id : 1001191000
         * order_status : 0
         * driver_amount3 : 0.0
         * gatewayType :
         * driver_amount2 : 0.0
         * driver_need2 : 0
         * user_amount3 : 0.0
         * user_amount2 : 0.0
         * job_number : null
         * driver_need3 : 0
         * act_distance :
         * over_time : 0
         * order_number2 : J1570614984229
         * longitude : null
         * order_number3 : M1570614984231
         * is_cancle : 0
         * order_distance : 17227
         * get_on_address : 中国黑龙江省哈尔滨市松北区松北街道创新二路
         * driver_phone : null
         * driver_amount : 1.78
         * gatewayNumber3 : null
         * score_num : 0
         * gatewayNumber2 : null
         * appointed_time : null
         * user_id : 271
         * over_distance : 0
         * user_amount : 2.0
         * user_name : 尹杰飞
         * order_number : D1570614984207
         * latitude : null
         * down_latitude : 45.888208
         * pay_status : 0
         * driving_year : null
         * nickname : null
         * user_phone : 66627c6a69736b6b637271
         * pay_status3 : 0
         * pay_status2 : 0
         * user_head : http://xychead.xueyiche.vip/xyc_1546850849473jpg
         * head_img : null
         * get_down_name : 中央大街
         * pay_time2 :
         * pay_time3 :
         * down_longitude : 126.578762
         * order_time4 :
         * driver_name : null
         * order_time2 :
         * order_time3 :
         * on_longitude : 126.51352
         * order_time1 : 2019-10-09 17:56:24
         * gatewayType3 :
         * gatewayType2 :
         * need_desc3 : 正常单，无额外费用
         * need_desc2 : 正常单，无额外费用
         * gatewayNumber : null
         */

        private String get_down_address;
        private String driver_id;
        private String get_on_name;
        private String on_latitude;
        private String cancle_reason;
        private String talk_to_driver;
        private String cancle_remark;
        private String true_phone;
        private String area_id;

        public String getCancle_remark() {
            return cancle_remark;
        }

        public void setCancle_remark(String cancle_remark) {
            this.cancle_remark = cancle_remark;
        }

        private String order_status;
        private String waitminutes;
        private double driver_amount3;
        private String gatewayType;
        private double driver_amount2;
        private String driver_need2;
        private double user_amount3;
        private double user_amount2;
        private String job_number;
        private String driver_need3;
        private String act_distance;
        private String over_time;
        private String order_number2;
        private String longitude;
        private String order_number3;
        private String is_cancle;
        private String order_distance;
        private String get_on_address;
        private String driver_phone;
        private double driver_amount;
        private String gatewayNumber3;
        private int score_num;
        private String gatewayNumber2;
        private String appointed_time;
        private String user_id;
        private String over_distance;
        private double user_amount;
        private String user_name;
        private String order_number;
        private String latitude;
        private String down_latitude;
        private String pay_status;
        private String driving_year;
        private String nickname;
        private String user_phone;
        private String pay_status3;
        private String pay_status2;
        private String user_head;
        private String head_img;
        private String get_down_name;
        private String pay_time2;
        private String pay_time3;
        private String down_longitude;
        private String order_time4;
        private String driver_name;
        private String order_time2;
        private String order_time3;
        private String on_longitude;
        private String order_time1;
        private String gatewayType3;
        private String gatewayType2;
        private String need_desc3;
        private String need_desc2;
        private String gatewayNumber;
        public String getWaitminutes() {
            return waitminutes;
        }

        public void setWaitminutes(String waitminutes) {
            this.waitminutes = waitminutes;
        }
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

        public String getGet_on_name() {
            return get_on_name;
        }

        public void setGet_on_name(String get_on_name) {
            this.get_on_name = get_on_name;
        }

        public String getOn_latitude() {
            return on_latitude;
        }

        public void setOn_latitude(String on_latitude) {
            this.on_latitude = on_latitude;
        }

        public String getCancle_reason() {
            return cancle_reason;
        }

        public void setCancle_reason(String cancle_reason) {
            this.cancle_reason = cancle_reason;
        }

        public String getTalk_to_driver() {
            return talk_to_driver;
        }

        public void setTalk_to_driver(String talk_to_driver) {
            this.talk_to_driver = talk_to_driver;
        }

        public String getTrue_phone() {
            return true_phone;
        }

        public void setTrue_phone(String true_phone) {
            this.true_phone = true_phone;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public double getDriver_amount3() {
            return driver_amount3;
        }

        public void setDriver_amount3(double driver_amount3) {
            this.driver_amount3 = driver_amount3;
        }

        public String getGatewayType() {
            return gatewayType;
        }

        public void setGatewayType(String gatewayType) {
            this.gatewayType = gatewayType;
        }

        public double getDriver_amount2() {
            return driver_amount2;
        }

        public void setDriver_amount2(double driver_amount2) {
            this.driver_amount2 = driver_amount2;
        }

        public String getDriver_need2() {
            return driver_need2;
        }

        public void setDriver_need2(String driver_need2) {
            this.driver_need2 = driver_need2;
        }

        public double getUser_amount3() {
            return user_amount3;
        }

        public void setUser_amount3(double user_amount3) {
            this.user_amount3 = user_amount3;
        }

        public double getUser_amount2() {
            return user_amount2;
        }

        public void setUser_amount2(double user_amount2) {
            this.user_amount2 = user_amount2;
        }

        public String getJob_number() {
            return job_number;
        }

        public void setJob_number(String job_number) {
            this.job_number = job_number;
        }

        public String getDriver_need3() {
            return driver_need3;
        }

        public void setDriver_need3(String driver_need3) {
            this.driver_need3 = driver_need3;
        }

        public String getAct_distance() {
            return act_distance;
        }

        public void setAct_distance(String act_distance) {
            this.act_distance = act_distance;
        }

        public String getOver_time() {
            return over_time;
        }

        public void setOver_time(String over_time) {
            this.over_time = over_time;
        }

        public String getOrder_number2() {
            return order_number2;
        }

        public void setOrder_number2(String order_number2) {
            this.order_number2 = order_number2;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getOrder_number3() {
            return order_number3;
        }

        public void setOrder_number3(String order_number3) {
            this.order_number3 = order_number3;
        }

        public String getIs_cancle() {
            return is_cancle;
        }

        public void setIs_cancle(String is_cancle) {
            this.is_cancle = is_cancle;
        }

        public String getOrder_distance() {
            return order_distance;
        }

        public void setOrder_distance(String order_distance) {
            this.order_distance = order_distance;
        }

        public String getGet_on_address() {
            return get_on_address;
        }

        public void setGet_on_address(String get_on_address) {
            this.get_on_address = get_on_address;
        }

        public String getDriver_phone() {
            return driver_phone;
        }

        public void setDriver_phone(String driver_phone) {
            this.driver_phone = driver_phone;
        }

        public double getDriver_amount() {
            return driver_amount;
        }

        public void setDriver_amount(double driver_amount) {
            this.driver_amount = driver_amount;
        }

        public String getGatewayNumber3() {
            return gatewayNumber3;
        }

        public void setGatewayNumber3(String gatewayNumber3) {
            this.gatewayNumber3 = gatewayNumber3;
        }

        public int getScore_num() {
            return score_num;
        }

        public void setScore_num(int score_num) {
            this.score_num = score_num;
        }

        public String getGatewayNumber2() {
            return gatewayNumber2;
        }

        public void setGatewayNumber2(String gatewayNumber2) {
            this.gatewayNumber2 = gatewayNumber2;
        }

        public String getAppointed_time() {
            return appointed_time;
        }

        public void setAppointed_time(String appointed_time) {
            this.appointed_time = appointed_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOver_distance() {
            return over_distance;
        }

        public void setOver_distance(String over_distance) {
            this.over_distance = over_distance;
        }

        public double getUser_amount() {
            return user_amount;
        }

        public void setUser_amount(double user_amount) {
            this.user_amount = user_amount;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getDown_latitude() {
            return down_latitude;
        }

        public void setDown_latitude(String down_latitude) {
            this.down_latitude = down_latitude;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getDriving_year() {
            return driving_year;
        }

        public void setDriving_year(String driving_year) {
            this.driving_year = driving_year;
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

        public String getPay_status3() {
            return pay_status3;
        }

        public void setPay_status3(String pay_status3) {
            this.pay_status3 = pay_status3;
        }

        public String getPay_status2() {
            return pay_status2;
        }

        public void setPay_status2(String pay_status2) {
            this.pay_status2 = pay_status2;
        }

        public String getUser_head() {
            return user_head;
        }

        public void setUser_head(String user_head) {
            this.user_head = user_head;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getGet_down_name() {
            return get_down_name;
        }

        public void setGet_down_name(String get_down_name) {
            this.get_down_name = get_down_name;
        }

        public String getPay_time2() {
            return pay_time2;
        }

        public void setPay_time2(String pay_time2) {
            this.pay_time2 = pay_time2;
        }

        public String getPay_time3() {
            return pay_time3;
        }

        public void setPay_time3(String pay_time3) {
            this.pay_time3 = pay_time3;
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

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
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

        public String getOn_longitude() {
            return on_longitude;
        }

        public void setOn_longitude(String on_longitude) {
            this.on_longitude = on_longitude;
        }

        public String getOrder_time1() {
            return order_time1;
        }

        public void setOrder_time1(String order_time1) {
            this.order_time1 = order_time1;
        }

        public String getGatewayType3() {
            return gatewayType3;
        }

        public void setGatewayType3(String gatewayType3) {
            this.gatewayType3 = gatewayType3;
        }

        public String getGatewayType2() {
            return gatewayType2;
        }

        public void setGatewayType2(String gatewayType2) {
            this.gatewayType2 = gatewayType2;
        }

        public String getNeed_desc3() {
            return need_desc3;
        }

        public void setNeed_desc3(String need_desc3) {
            this.need_desc3 = need_desc3;
        }

        public String getNeed_desc2() {
            return need_desc2;
        }

        public void setNeed_desc2(String need_desc2) {
            this.need_desc2 = need_desc2;
        }

        public String getGatewayNumber() {
            return gatewayNumber;
        }

        public void setGatewayNumber(String gatewayNumber) {
            this.gatewayNumber = gatewayNumber;
        }
    }
}
