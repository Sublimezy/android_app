package com.xueyiche.zjyk.xueyiche.practicecar.bean;

/**
 * Created by ZL on 2018/3/14.
 */
public class OrderPracticeOkBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"hour_price":100,"get_down_address":"就酱肘子","driver_id":"18d5361fd34a11e8b10e5254f2dc841f","on_latitude":"45.729592","end_school_level":"","order_number":"Y1576735270776","down_latitude":"45.607292","true_phone":"","down_card_time":"","order_status":"2","pay_status":"1","traincar_experience":"","gatewayType":"WXPAY","driving_year":"4","car_level":"0","bz":"","series_name":"里程","track_status":"2","pay_price":200,"purpose_item":"","practice_hours":"2","longitude_driver":"126.578762","hand_auto":"0","head_img":"http://xychead.xueyiche.vip/20181206173612","end_time":"2019-12-19 16:01:11","brand_name":"本田","get_on_address":"好房","driver_phone":"66647a6a6f70636e637a77","down_longitude":"126.638199","driver_name":"张教练","start_time":"2019-12-19 14:01:11","on_longitude":"126.542885","car_level_detail":"A级车 100元","order_system_time":"2019-12-19 14:01:10","order_sort":"1","latitude_driver":"45.888208","gatewayNumber":"4200000464201912193407242593"}
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
         * hour_price : 100
         * get_down_address : 就酱肘子
         * driver_id : 18d5361fd34a11e8b10e5254f2dc841f
         * on_latitude : 45.729592
         * end_school_level :
         * order_number : Y1576735270776
         * down_latitude : 45.607292
         * true_phone :
         * down_card_time :
         * order_status : 2
         * pay_status : 1
         * traincar_experience :
         * gatewayType : WXPAY
         * driving_year : 4
         * car_level : 0
         * bz :
         * series_name : 里程
         * track_status : 2
         * pay_price : 200
         * purpose_item :
         * practice_hours : 2
         * longitude_driver : 126.578762
         * hand_auto : 0
         * head_img : http://xychead.xueyiche.vip/20181206173612
         * end_time : 2019-12-19 16:01:11
         * brand_name : 本田
         * get_on_address : 好房
         * driver_phone : 66647a6a6f70636e637a77
         * down_longitude : 126.638199
         * driver_name : 张教练
         * start_time : 2019-12-19 14:01:11
         * on_longitude : 126.542885
         * car_level_detail : A级车 100元
         * order_system_time : 2019-12-19 14:01:10
         * order_sort : 1
         * latitude_driver : 45.888208
         * gatewayNumber : 4200000464201912193407242593
         */

        private int hour_price;
        private String get_down_address;
        private String driver_id;
        private String on_latitude;
        private String end_school_level;
        private String order_number;
        private String down_latitude;
        private String true_phone;
        private String down_card_time;
        private String order_status;
        private String pay_status;
        private String traincar_experience;
        private String gatewayType;
        private String driving_year;
        private String car_level;
        private String bz;
        private String series_name;
        private String track_status;
        private int pay_price;
        private String purpose_item;
        private String practice_hours;
        private String longitude_driver;
        private String hand_auto;
        private String head_img;
        private String end_time;
        private String brand_name;
        private String get_on_address;
        private String driver_phone;
        private String down_longitude;
        private String driver_name;
        private String start_time;
        private String on_longitude;
        private String car_level_detail;
        private String order_system_time;
        private String order_sort;
        private String latitude_driver;
        private String gatewayNumber;

        public int getHour_price() {
            return hour_price;
        }

        public void setHour_price(int hour_price) {
            this.hour_price = hour_price;
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

        public String getOn_latitude() {
            return on_latitude;
        }

        public void setOn_latitude(String on_latitude) {
            this.on_latitude = on_latitude;
        }

        public String getEnd_school_level() {
            return end_school_level;
        }

        public void setEnd_school_level(String end_school_level) {
            this.end_school_level = end_school_level;
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

        public String getTrue_phone() {
            return true_phone;
        }

        public void setTrue_phone(String true_phone) {
            this.true_phone = true_phone;
        }

        public String getDown_card_time() {
            return down_card_time;
        }

        public void setDown_card_time(String down_card_time) {
            this.down_card_time = down_card_time;
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

        public String getTraincar_experience() {
            return traincar_experience;
        }

        public void setTraincar_experience(String traincar_experience) {
            this.traincar_experience = traincar_experience;
        }

        public String getGatewayType() {
            return gatewayType;
        }

        public void setGatewayType(String gatewayType) {
            this.gatewayType = gatewayType;
        }

        public String getDriving_year() {
            return driving_year;
        }

        public void setDriving_year(String driving_year) {
            this.driving_year = driving_year;
        }

        public String getCar_level() {
            return car_level;
        }

        public void setCar_level(String car_level) {
            this.car_level = car_level;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getSeries_name() {
            return series_name;
        }

        public void setSeries_name(String series_name) {
            this.series_name = series_name;
        }

        public String getTrack_status() {
            return track_status;
        }

        public void setTrack_status(String track_status) {
            this.track_status = track_status;
        }

        public int getPay_price() {
            return pay_price;
        }

        public void setPay_price(int pay_price) {
            this.pay_price = pay_price;
        }

        public String getPurpose_item() {
            return purpose_item;
        }

        public void setPurpose_item(String purpose_item) {
            this.purpose_item = purpose_item;
        }

        public String getPractice_hours() {
            return practice_hours;
        }

        public void setPractice_hours(String practice_hours) {
            this.practice_hours = practice_hours;
        }

        public String getLongitude_driver() {
            return longitude_driver;
        }

        public void setLongitude_driver(String longitude_driver) {
            this.longitude_driver = longitude_driver;
        }

        public String getHand_auto() {
            return hand_auto;
        }

        public void setHand_auto(String hand_auto) {
            this.hand_auto = hand_auto;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
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

        public String getDown_longitude() {
            return down_longitude;
        }

        public void setDown_longitude(String down_longitude) {
            this.down_longitude = down_longitude;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getOn_longitude() {
            return on_longitude;
        }

        public void setOn_longitude(String on_longitude) {
            this.on_longitude = on_longitude;
        }

        public String getCar_level_detail() {
            return car_level_detail;
        }

        public void setCar_level_detail(String car_level_detail) {
            this.car_level_detail = car_level_detail;
        }

        public String getOrder_system_time() {
            return order_system_time;
        }

        public void setOrder_system_time(String order_system_time) {
            this.order_system_time = order_system_time;
        }

        public String getOrder_sort() {
            return order_sort;
        }

        public void setOrder_sort(String order_sort) {
            this.order_sort = order_sort;
        }

        public String getLatitude_driver() {
            return latitude_driver;
        }

        public void setLatitude_driver(String latitude_driver) {
            this.latitude_driver = latitude_driver;
        }

        public String getGatewayNumber() {
            return gatewayNumber;
        }

        public void setGatewayNumber(String gatewayNumber) {
            this.gatewayNumber = gatewayNumber;
        }
    }
}
