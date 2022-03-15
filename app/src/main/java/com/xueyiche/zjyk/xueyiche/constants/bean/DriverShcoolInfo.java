package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Owner on 2016/12/7.
 */
public class DriverShcoolInfo implements Serializable {


    /**
     * package_taocan : [{"driver_school_id":"0fbb2025a39b4ffba32bf7859f2faf67","driver_school_service":"一站式拿证C1","driver_school_money":2580,"return_money":"200","package_id":1,"subscription":"680"},{"driver_school_id":"0fbb2025a39b4ffba32bf7859f2faf67","driver_school_service":"裸班式拿证C1","driver_school_money":2280,"return_money":"380","package_id":2,"subscription":"680"}]
     * content_num : {"content_num":1}
     * school_info : {"driver_school_id":"0fbb2025a39b4ffba32bf7859f2faf67","driver_school_name":"盛博驾校","cost_description":"费用明细br一、返现200元br打的费对方答复地方br费用明细br一、返现380元br打的费对方答复地方","label_one_name":"下证快","driver_school_phone":"0451-58805888","latitude":"45.728668","entry_num":103,"driver_school_place":"香坊区公滨路24号（曹家村老砖厂）","label_two_name":"免预约","jxjj_url":"http://images.xueyiche.vip/Fv5WRIRWcx6TkY2MyucCC4rpQXm0","driver_school_url":"http://images.xueyiche.vip/FrPYVpxr6BZI4vroM5jJFE13SuuQ","driver_school_profile":"哈尔滨市盛博机动车驾驶员培训学校是经省、市行管部门批准的二类驾校。现有理论教官20人、......","fymx_url":"http://xueyiche.cn/fymx/fymx1.html","longitude":"126.751414"}
     * volution : [{"volution_id":3,"type":"image","address":"http://images.xueyiche.vip/FrPYVpxr6BZI4vroM5jJFE13SuuQ","web_url":"","main_title":"盛博驾校"}]
     * content_pinglun : [{"content":"测试4","content_time":"2018-01-30 10:24:19","nickname":"KamLeung","head_img":"http://otqopw3sl.bkt.clouddn.com/20171018111358"}]
     * collect_status : 1
     */

    private ContentBean content;
    /**
     * content : {"package_taocan":[{"driver_school_id":"0fbb2025a39b4ffba32bf7859f2faf67","driver_school_service":"一站式拿证C1","driver_school_money":2580,"return_money":"200","package_id":1,"subscription":"680"},{"driver_school_id":"0fbb2025a39b4ffba32bf7859f2faf67","driver_school_service":"裸班式拿证C1","driver_school_money":2280,"return_money":"380","package_id":2,"subscription":"680"}],"content_num":{"content_num":1},"school_info":{"driver_school_id":"0fbb2025a39b4ffba32bf7859f2faf67","driver_school_name":"盛博驾校","cost_description":"费用明细br一、返现200元br打的费对方答复地方br费用明细br一、返现380元br打的费对方答复地方","label_one_name":"下证快","driver_school_phone":"0451-58805888","latitude":"45.728668","entry_num":103,"driver_school_place":"香坊区公滨路24号（曹家村老砖厂）","label_two_name":"免预约","jxjj_url":"http://images.xueyiche.vip/Fv5WRIRWcx6TkY2MyucCC4rpQXm0","driver_school_url":"http://images.xueyiche.vip/FrPYVpxr6BZI4vroM5jJFE13SuuQ","driver_school_profile":"哈尔滨市盛博机动车驾驶员培训学校是经省、市行管部门批准的二类驾校。现有理论教官20人、......","fymx_url":"http://xueyiche.cn/fymx/fymx1.html","longitude":"126.751414"},"volution":[{"volution_id":3,"type":"image","address":"http://images.xueyiche.vip/FrPYVpxr6BZI4vroM5jJFE13SuuQ","web_url":"","main_title":"盛博驾校"}],"content_pinglun":[{"content":"测试4","content_time":"2018-01-30 10:24:19","nickname":"KamLeung","head_img":"http://otqopw3sl.bkt.clouddn.com/20171018111358"}],"collect_status":"1"}
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
        /**
         * content_num : 1
         */

        private ContentNumBean content_num;
        /**
         * driver_school_id : 0fbb2025a39b4ffba32bf7859f2faf67
         * driver_school_name : 盛博驾校
         * cost_description : 费用明细br一、返现200元br打的费对方答复地方br费用明细br一、返现380元br打的费对方答复地方
         * label_one_name : 下证快
         * driver_school_phone : 0451-58805888
         * latitude : 45.728668
         * entry_num : 103
         * driver_school_place : 香坊区公滨路24号（曹家村老砖厂）
         * label_two_name : 免预约
         * jxjj_url : http://images.xueyiche.vip/Fv5WRIRWcx6TkY2MyucCC4rpQXm0
         * driver_school_url : http://images.xueyiche.vip/FrPYVpxr6BZI4vroM5jJFE13SuuQ
         * driver_school_profile : 哈尔滨市盛博机动车驾驶员培训学校是经省、市行管部门批准的二类驾校。现有理论教官20人、......
         * fymx_url : http://xueyiche.cn/fymx/fymx1.html
         * longitude : 126.751414
         */

        private SchoolInfoBean school_info;
        private String collect_status;
        /**
         * driver_school_id : 0fbb2025a39b4ffba32bf7859f2faf67
         * driver_school_service : 一站式拿证C1
         * driver_school_money : 2580
         * return_money : 200
         * package_id : 1
         * subscription : 680
         */

        private List<PackageTaocanBean> package_taocan;
        /**
         * volution_id : 3
         * type : image
         * address : http://images.xueyiche.vip/FrPYVpxr6BZI4vroM5jJFE13SuuQ
         * web_url :
         * main_title : 盛博驾校
         */

        private List<VolutionBean> volution;
        /**
         * content : 测试4
         * content_time : 2018-01-30 10:24:19
         * nickname : KamLeung
         * head_img : http://otqopw3sl.bkt.clouddn.com/20171018111358
         */

        private List<ContentPinglunBean> content_pinglun;

        public ContentNumBean getContent_num() {
            return content_num;
        }

        public void setContent_num(ContentNumBean content_num) {
            this.content_num = content_num;
        }

        public SchoolInfoBean getSchool_info() {
            return school_info;
        }

        public void setSchool_info(SchoolInfoBean school_info) {
            this.school_info = school_info;
        }

        public String getCollect_status() {
            return collect_status;
        }

        public void setCollect_status(String collect_status) {
            this.collect_status = collect_status;
        }

        public List<PackageTaocanBean> getPackage_taocan() {
            return package_taocan;
        }

        public void setPackage_taocan(List<PackageTaocanBean> package_taocan) {
            this.package_taocan = package_taocan;
        }

        public List<VolutionBean> getVolution() {
            return volution;
        }

        public void setVolution(List<VolutionBean> volution) {
            this.volution = volution;
        }

        public List<ContentPinglunBean> getContent_pinglun() {
            return content_pinglun;
        }

        public void setContent_pinglun(List<ContentPinglunBean> content_pinglun) {
            this.content_pinglun = content_pinglun;
        }

        public static class ContentNumBean {
            private int content_num;

            public int getContent_num() {
                return content_num;
            }

            public void setContent_num(int content_num) {
                this.content_num = content_num;
            }
        }

        public static class SchoolInfoBean {
            private String driver_school_id;
            private String driver_school_name;
            private String cost_description;
            private String label_one_name;
            private String driver_school_phone;
            private String latitude;
            private int entry_num;
            private String driver_school_place;
            private String label_two_name;
            private String distance;
            private String jxjj_url;

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            private String driver_school_url;
            private String driver_school_profile;
            private String fymx_url;
            private String longitude;
            private String practice_latitude;
            private String practice_longitude;
            private String practice_place;
            private String stkm;
            private String gmxz;

            public String getGmxz() {
                return gmxz;
            }

            public void setGmxz(String gmxz) {
                this.gmxz = gmxz;
            }

            private String service_detail_text;

            public String getService_detail_text() {
                return service_detail_text;
            }

            public void setService_detail_text(String service_detail_text) {
                this.service_detail_text = service_detail_text;
            }

            public String getStkm() {
                return stkm;
            }

            public void setStkm(String stkm) {
                this.stkm = stkm;
            }

            public String getPractice_latitude() {
                return practice_latitude;
            }

            public void setPractice_latitude(String practice_latitude) {
                this.practice_latitude = practice_latitude;
            }

            public String getPractice_longitude() {
                return practice_longitude;
            }

            public void setPractice_longitude(String practice_longitude) {
                this.practice_longitude = practice_longitude;
            }

            public String getPractice_place() {
                return practice_place;
            }

            public void setPractice_place(String practice_place) {
                this.practice_place = practice_place;
            }

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

            public String getCost_description() {
                return cost_description;
            }

            public void setCost_description(String cost_description) {
                this.cost_description = cost_description;
            }

            public String getLabel_one_name() {
                return label_one_name;
            }

            public void setLabel_one_name(String label_one_name) {
                this.label_one_name = label_one_name;
            }

            public String getDriver_school_phone() {
                return driver_school_phone;
            }

            public void setDriver_school_phone(String driver_school_phone) {
                this.driver_school_phone = driver_school_phone;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public int getEntry_num() {
                return entry_num;
            }

            public void setEntry_num(int entry_num) {
                this.entry_num = entry_num;
            }

            public String getDriver_school_place() {
                return driver_school_place;
            }

            public void setDriver_school_place(String driver_school_place) {
                this.driver_school_place = driver_school_place;
            }

            public String getLabel_two_name() {
                return label_two_name;
            }

            public void setLabel_two_name(String label_two_name) {
                this.label_two_name = label_two_name;
            }

            public String getJxjj_url() {
                return jxjj_url;
            }

            public void setJxjj_url(String jxjj_url) {
                this.jxjj_url = jxjj_url;
            }

            public String getDriver_school_url() {
                return driver_school_url;
            }

            public void setDriver_school_url(String driver_school_url) {
                this.driver_school_url = driver_school_url;
            }

            public String getDriver_school_profile() {
                return driver_school_profile;
            }

            public void setDriver_school_profile(String driver_school_profile) {
                this.driver_school_profile = driver_school_profile;
            }

            public String getFymx_url() {
                return fymx_url;
            }

            public void setFymx_url(String fymx_url) {
                this.fymx_url = fymx_url;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }

        public static class PackageTaocanBean {
            private String driver_school_id;
            private String driver_school_service;
            private String driver_school_money;
            private String return_money;
            private int package_id;
            private String subscription;
            private String min_school_price;
            private String cost_description;

            public String getMin_school_price() {
                return min_school_price;
            }

            public void setMin_school_price(String min_school_price) {
                this.min_school_price = min_school_price;
            }

            public String getCost_description() {
                return cost_description;
            }

            public void setCost_description(String cost_description) {
                this.cost_description = cost_description;
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

        public static class VolutionBean {
            private int volution_id;
            private String type;
            private String address;
            private String web_url;
            private String main_title;

            public int getVolution_id() {
                return volution_id;
            }

            public void setVolution_id(int volution_id) {
                this.volution_id = volution_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getWeb_url() {
                return web_url;
            }

            public void setWeb_url(String web_url) {
                this.web_url = web_url;
            }

            public String getMain_title() {
                return main_title;
            }

            public void setMain_title(String main_title) {
                this.main_title = main_title;
            }
        }

        public static class ContentPinglunBean {
            private String content;
            private String content_time;
            private String nickname;
            private String head_img;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getContent_time() {
                return content_time;
            }

            public void setContent_time(String content_time) {
                this.content_time = content_time;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }
        }
    }
}
