package com.xueyiche.zjyk.xueyiche.usedcar.bean;

import java.util.List;

/**
 * Created by Owner on 2018/8/3.
 */
public class UsedCarInfoBean {

    /**
     * code : 200
     * message : 信息请求成功
     * data : {"twocar_img":["http://car.xueyiche.vip/34ea1201807311630005049.jpg","http://car.xueyiche.vip/9ae71201807311630008688.jpg","http://car.xueyiche.vip/2216d201807311630016020.jpg"],"canshu":[{"canshu_value":"111","canshu_name":"行驶里程"},{"canshu_value":"2018年07月","canshu_name":"上牌时间"},{"canshu_value":"哈尔滨","canshu_name":"车牌所在地"},{"canshu_value":"哈尔滨","canshu_name":"看车地点"}],"bright":[{"bright_information":"天窗"},{"bright_information":"54"}],"content_img":[{"title":"正侧","content":"描述","img":"http://car.xueyiche.vip/34230201807311632565785.jpg"},{"title":"正前","content":"描述","img":"http://car.xueyiche.vip/9bca4201807311632563574.jpg"},{"title":"车门","content":"描述","img":"http://car.xueyiche.vip/a0ec2201807311632565611.jpg"},{"title":"前排","content":"描述","img":"http://car.xueyiche.vip/5554b201807311632571176.jpg"},{"title":"后排","content":"描述","img":"http://car.xueyiche.vip/031eb201807311632579533.jpg"},{"title":"中控","content":"描述","img":"http://car.xueyiche.vip/9297b201807311632587816.jpg"},{"title":"发动机舱","content":"描述","img":"http://car.xueyiche.vip/95134201807311632581108.jpg"}],"carsource":{"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","car_price":"111","new_car_price":"11","rent_price":"11","new_rent_price":"11"},"similar":[{"id":1,"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","last_time":"2018年07月","mileage":"1","car_price":"1","new_car_price":"1","rent_price":"100","new_rent_price":"1","just_side_img":"http://car.xueyiche.vip\\ee0bf201807101622499248.jpg","loan_first":"1","loan_month":"13","status":0,"rent_status":1},{"id":9,"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","last_time":"1970年01月","mileage":"2.8","car_price":"8","new_car_price":"","rent_price":"6","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":10,"car_allname":"奥迪A6L ","last_time":"1970年01月","mileage":"0","car_price":"4","new_car_price":"","rent_price":"78","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":12,"car_allname":"奥迪","last_time":"1970年01月","mileage":"2.2","car_price":"6","new_car_price":"","rent_price":"9","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":13,"car_allname":"奥迪 A6L  ","last_time":"1970年01月","mileage":"0","car_price":"1","new_car_price":"","rent_price":"4","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":15,"car_allname":"奥迪 A6L  2018款40周年年型  40 FSI 风尚型 ","last_time":"2018年07月","mileage":"1","car_price":"1","new_car_price":"1","rent_price":"2","new_rent_price":"","just_side_img":"","loan_first":"1","loan_month":"1","status":0,"rent_status":1},{"id":21,"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","last_time":"2018年07月","mileage":"111","car_price":"111","new_car_price":"11","rent_price":"11","new_rent_price":"11","just_side_img":"http://car.xueyiche.vip/34230201807311632565785.jpg","loan_first":"11","loan_month":"111","status":0,"rent_status":1}]}
     */

    private int code;
    private String message;
    /**
     * twocar_img : ["http://car.xueyiche.vip/34ea1201807311630005049.jpg","http://car.xueyiche.vip/9ae71201807311630008688.jpg","http://car.xueyiche.vip/2216d201807311630016020.jpg"]
     * canshu : [{"canshu_value":"111","canshu_name":"行驶里程"},{"canshu_value":"2018年07月","canshu_name":"上牌时间"},{"canshu_value":"哈尔滨","canshu_name":"车牌所在地"},{"canshu_value":"哈尔滨","canshu_name":"看车地点"}]
     * bright : [{"bright_information":"天窗"},{"bright_information":"54"}]
     * content_img : [{"title":"正侧","content":"描述","img":"http://car.xueyiche.vip/34230201807311632565785.jpg"},{"title":"正前","content":"描述","img":"http://car.xueyiche.vip/9bca4201807311632563574.jpg"},{"title":"车门","content":"描述","img":"http://car.xueyiche.vip/a0ec2201807311632565611.jpg"},{"title":"前排","content":"描述","img":"http://car.xueyiche.vip/5554b201807311632571176.jpg"},{"title":"后排","content":"描述","img":"http://car.xueyiche.vip/031eb201807311632579533.jpg"},{"title":"中控","content":"描述","img":"http://car.xueyiche.vip/9297b201807311632587816.jpg"},{"title":"发动机舱","content":"描述","img":"http://car.xueyiche.vip/95134201807311632581108.jpg"}]
     * carsource : {"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","car_price":"111","new_car_price":"11","rent_price":"11","new_rent_price":"11"}
     * similar : [{"id":1,"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","last_time":"2018年07月","mileage":"1","car_price":"1","new_car_price":"1","rent_price":"100","new_rent_price":"1","just_side_img":"http://car.xueyiche.vip\\ee0bf201807101622499248.jpg","loan_first":"1","loan_month":"13","status":0,"rent_status":1},{"id":9,"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","last_time":"1970年01月","mileage":"2.8","car_price":"8","new_car_price":"","rent_price":"6","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":10,"car_allname":"奥迪A6L ","last_time":"1970年01月","mileage":"0","car_price":"4","new_car_price":"","rent_price":"78","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":12,"car_allname":"奥迪","last_time":"1970年01月","mileage":"2.2","car_price":"6","new_car_price":"","rent_price":"9","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":13,"car_allname":"奥迪 A6L  ","last_time":"1970年01月","mileage":"0","car_price":"1","new_car_price":"","rent_price":"4","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":15,"car_allname":"奥迪 A6L  2018款40周年年型  40 FSI 风尚型 ","last_time":"2018年07月","mileage":"1","car_price":"1","new_car_price":"1","rent_price":"2","new_rent_price":"","just_side_img":"","loan_first":"1","loan_month":"1","status":0,"rent_status":1},{"id":21,"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","last_time":"2018年07月","mileage":"111","car_price":"111","new_car_price":"11","rent_price":"11","new_rent_price":"11","just_side_img":"http://car.xueyiche.vip/34230201807311632565785.jpg","loan_first":"11","loan_month":"111","status":0,"rent_status":1}]
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * car_allname : 奥迪 A6L 2018款40周年年型 40 FSI 风尚型
         * car_price : 111
         * new_car_price : 11
         * rent_price : 11
         * new_rent_price : 11
         */

        private CarsourceBean carsource;
        private List<String> twocar_img;
        /**
         * canshu_value : 111
         * canshu_name : 行驶里程
         */

        private List<CanshuBean> canshu;
        /**
         * bright_information : 天窗
         */

        private List<BrightBean> bright;
        /**
         * title : 正侧
         * content : 描述
         * img : http://car.xueyiche.vip/34230201807311632565785.jpg
         */

        private List<ContentImgBean> content_img;
        /**
         * id : 1
         * car_allname : 奥迪 A6L 2018款40周年年型 40 FSI 风尚型
         * last_time : 2018年07月
         * mileage : 1
         * car_price : 1
         * new_car_price : 1
         * rent_price : 100
         * new_rent_price : 1
         * just_side_img : http://car.xueyiche.vip\ee0bf201807101622499248.jpg
         * loan_first : 1
         * loan_month : 13
         * status : 0
         * rent_status : 1
         */

        private List<SimilarBean> similar;

        public CarsourceBean getCarsource() {
            return carsource;
        }

        public void setCarsource(CarsourceBean carsource) {
            this.carsource = carsource;
        }

        public List<String> getTwocar_img() {
            return twocar_img;
        }

        public void setTwocar_img(List<String> twocar_img) {
            this.twocar_img = twocar_img;
        }

        public List<CanshuBean> getCanshu() {
            return canshu;
        }

        public void setCanshu(List<CanshuBean> canshu) {
            this.canshu = canshu;
        }

        public List<BrightBean> getBright() {
            return bright;
        }

        public void setBright(List<BrightBean> bright) {
            this.bright = bright;
        }

        public List<ContentImgBean> getContent_img() {
            return content_img;
        }

        public void setContent_img(List<ContentImgBean> content_img) {
            this.content_img = content_img;
        }

        public List<SimilarBean> getSimilar() {
            return similar;
        }

        public void setSimilar(List<SimilarBean> similar) {
            this.similar = similar;
        }

        public static class CarsourceBean {
            private String car_allname;
            private String car_price;
            private String new_car_price;
            private String rent_price;
            private String new_rent_price;
            private String collect_status;
            private String id_status;
            private String detection_address;
            private String violated_deposit;
            private String car_deposit;
            private String shop_name;
            private String about_status;
            private String rent_whether;
            private String header_img;
            private int rent_status;

            public String getHeader_img() {
                return header_img;
            }

            public void setHeader_img(String header_img) {
                this.header_img = header_img;
            }

            public String getRent_whether() {
                return rent_whether;
            }

            public void setRent_whether(String rent_whether) {
                this.rent_whether = rent_whether;
            }

            public String getAbout_status() {
                return about_status;
            }

            public void setAbout_status(String about_status) {
                this.about_status = about_status;
            }
            public String getViolated_deposit() {
                return violated_deposit;
            }

            public void setViolated_deposit(String violated_deposit) {
                this.violated_deposit = violated_deposit;
            }

            public String getCar_deposit() {
                return car_deposit;
            }

            public void setCar_deposit(String car_deposit) {
                this.car_deposit = car_deposit;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getCollect_status() {
                return collect_status;
            }

            public String getDetection_address() {
                return detection_address;
            }

            public void setDetection_address(String detection_address) {
                this.detection_address = detection_address;
            }

            public void setCollect_status(String collect_status) {
                this.collect_status = collect_status;
            }

            public String getId_status() {
                return id_status;
            }

            public void setId_status(String id_status) {
                this.id_status = id_status;
            }

            public int getRent_status() {
                return rent_status;
            }

            public void setRent_status(int rent_status) {
                this.rent_status = rent_status;
            }

            public String getCar_allname() {
                return car_allname;
            }

            public void setCar_allname(String car_allname) {
                this.car_allname = car_allname;
            }

            public String getCar_price() {
                return car_price;
            }

            public void setCar_price(String car_price) {
                this.car_price = car_price;
            }

            public String getNew_car_price() {
                return new_car_price;
            }

            public void setNew_car_price(String new_car_price) {
                this.new_car_price = new_car_price;
            }

            public String getRent_price() {
                return rent_price;
            }

            public void setRent_price(String rent_price) {
                this.rent_price = rent_price;
            }

            public String getNew_rent_price() {
                return new_rent_price;
            }

            public void setNew_rent_price(String new_rent_price) {
                this.new_rent_price = new_rent_price;
            }
        }

        public static class CanshuBean {
            private String canshu_value;
            private String canshu_name;

            public String getCanshu_value() {
                return canshu_value;
            }

            public void setCanshu_value(String canshu_value) {
                this.canshu_value = canshu_value;
            }

            public String getCanshu_name() {
                return canshu_name;
            }

            public void setCanshu_name(String canshu_name) {
                this.canshu_name = canshu_name;
            }
        }

        public static class BrightBean {
            private String bright_information;

            public String getBright_information() {
                return bright_information;
            }

            public void setBright_information(String bright_information) {
                this.bright_information = bright_information;
            }
        }

        public static class ContentImgBean {
            private String title;
            private String content;
            private String img;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class SimilarBean {
            private int id;
            private String car_allname;
            private String last_time;
            private String mileage;
            private String car_price;
            private String new_car_price;
            private String rent_price;
            private String new_rent_price;
            private String just_side_img;
            private String loan_first;
            private String loan_month;
            private String last_mileage;
            private String htmlUrl;
            private int status;
            private int rent_status;

            public String getHtmlUrl() {
                return htmlUrl;
            }

            public void setHtmlUrl(String htmlUrl) {
                this.htmlUrl = htmlUrl;
            }

            public String getLast_mileage() {
                return last_mileage;
            }

            public void setLast_mileage(String last_mileage) {
                this.last_mileage = last_mileage;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCar_allname() {
                return car_allname;
            }

            public void setCar_allname(String car_allname) {
                this.car_allname = car_allname;
            }

            public String getLast_time() {
                return last_time;
            }

            public void setLast_time(String last_time) {
                this.last_time = last_time;
            }

            public String getMileage() {
                return mileage;
            }

            public void setMileage(String mileage) {
                this.mileage = mileage;
            }

            public String getCar_price() {
                return car_price;
            }

            public void setCar_price(String car_price) {
                this.car_price = car_price;
            }

            public String getNew_car_price() {
                return new_car_price;
            }

            public void setNew_car_price(String new_car_price) {
                this.new_car_price = new_car_price;
            }

            public String getRent_price() {
                return rent_price;
            }

            public void setRent_price(String rent_price) {
                this.rent_price = rent_price;
            }

            public String getNew_rent_price() {
                return new_rent_price;
            }

            public void setNew_rent_price(String new_rent_price) {
                this.new_rent_price = new_rent_price;
            }

            public String getJust_side_img() {
                return just_side_img;
            }

            public void setJust_side_img(String just_side_img) {
                this.just_side_img = just_side_img;
            }

            public String getLoan_first() {
                return loan_first;
            }

            public void setLoan_first(String loan_first) {
                this.loan_first = loan_first;
            }

            public String getLoan_month() {
                return loan_month;
            }

            public void setLoan_month(String loan_month) {
                this.loan_month = loan_month;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getRent_status() {
                return rent_status;
            }

            public void setRent_status(int rent_status) {
                this.rent_status = rent_status;
            }
        }
    }
}
