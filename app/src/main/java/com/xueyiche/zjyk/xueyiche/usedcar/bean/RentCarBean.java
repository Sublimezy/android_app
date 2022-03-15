package com.xueyiche.zjyk.xueyiche.usedcar.bean;

import java.util.List;

/**
 * Created by Owner on 2018/7/18.
 */
public class RentCarBean {

    /**
     * code : 200
     * message : 信息请求成功
     * data : {"broadcastList":[{"id":1,"title":"轮播图片1","broadcast_pic":"http://car.xueyiche.vip/b1a29201807101644392196.jpg","url":"http://www.baidu.com","content":"轮播图片1","create_time":"2018-07-09"},{"id":2,"title":"轮播图片2","broadcast_pic":"http://car.xueyiche.vip/b1a29201807101644392196.jpg","url":"http://www.baidu.com","content":"轮播图片2","create_time":"2018-07-09"}],"carSourceHot":[{"id":1,"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","last_time":"2018年07月","mileage":"1","car_price":"1","new_car_price":"1","rent_price":"100","new_rent_price":"1","just_side_img":"http://192.168.1.121:338/uploads/picture/20180709\\66130ad7bced23d44404d34644c81739.jpg","loan_first":"1","loan_month":"1","status":0,"rent_status":1},{"id":6,"car_allname":"","last_time":"1970年01月","mileage":"0","car_price":"3","new_car_price":"","rent_price":"3","new_rent_price":"","just_side_img":"http://car.xueyiche.vip\\d0990201807101609375524.jpg","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":7,"car_allname":"","last_time":"1970年01月","mileage":"0","car_price":"4","new_car_price":"","rent_price":"4","new_rent_price":"","just_side_img":"http://car.xueyiche.vip\\e304a201807101610596694.jpg","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":8,"car_allname":"","last_time":"1970年01月","mileage":"0","car_price":"7","new_car_price":"","rent_price":"5","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":9,"car_allname":"奥迪 A6L 2018款40周年年型 40 FSI 风尚型","last_time":"1970年01月","mileage":"2.8","car_price":"8","new_car_price":"","rent_price":"6","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":10,"car_allname":"奥迪A6L ","last_time":"1970年01月","mileage":"0","car_price":"4","new_car_price":"","rent_price":"78","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":11,"car_allname":"h","last_time":"1970年01月","mileage":"0","car_price":"5","new_car_price":"","rent_price":"8","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":12,"car_allname":"奥迪","last_time":"1970年01月","mileage":"2.2","car_price":"6","new_car_price":"","rent_price":"9","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":13,"car_allname":"奥迪 A6L  ","last_time":"1970年01月","mileage":"0","car_price":"1","new_car_price":"","rent_price":"4","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":14,"car_allname":"二恶额   ","last_time":"1970年01月","mileage":"0","car_price":"7","new_car_price":"","rent_price":"3","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1},{"id":15,"car_allname":"奥迪 A6L  2018款40周年年型  40 FSI 风尚型 ","last_time":"2018年07月","mileage":"1","car_price":"1","new_car_price":"1","rent_price":"2","new_rent_price":"","just_side_img":"","loan_first":"1","loan_month":"1","status":0,"rent_status":1},{"id":20,"car_allname":"   ","last_time":"1970年01月","mileage":"","car_price":"","new_car_price":"","rent_price":"200","new_rent_price":"","just_side_img":"","loan_first":"","loan_month":"","status":0,"rent_status":1}]}
     */

    private int code;
    private String message;
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
         * id : 1
         * title : 轮播图片1
         * broadcast_pic : http://car.xueyiche.vip/b1a29201807101644392196.jpg
         * url : http://www.baidu.com
         * content : 轮播图片1
         * create_time : 2018-07-09
         */

        private List<BroadcastListBean> broadcastList;
        /**
         * id : 1
         * car_allname : 奥迪 A6L 2018款40周年年型 40 FSI 风尚型
         * last_time : 2018年07月
         * mileage : 1
         * car_price : 1
         * new_car_price : 1
         * rent_price : 100
         * new_rent_price : 1
         * just_side_img : http://192.168.1.121:338/uploads/picture/20180709\66130ad7bced23d44404d34644c81739.jpg
         * loan_first : 1
         * loan_month : 1
         * status : 0
         * rent_status : 1
         */

        private List<CarSourceHotBean> carSourceHot;

        public List<BroadcastListBean> getBroadcastList() {
            return broadcastList;
        }

        public void setBroadcastList(List<BroadcastListBean> broadcastList) {
            this.broadcastList = broadcastList;
        }

        public List<CarSourceHotBean> getCarSourceHot() {
            return carSourceHot;
        }

        public void setCarSourceHot(List<CarSourceHotBean> carSourceHot) {
            this.carSourceHot = carSourceHot;
        }

        public static class BroadcastListBean {
            private int id;
            private String title;
            private String broadcast_pic;
            private String url;
            private String content;
            private String create_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBroadcast_pic() {
                return broadcast_pic;
            }

            public void setBroadcast_pic(String broadcast_pic) {
                this.broadcast_pic = broadcast_pic;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }

        public static class CarSourceHotBean {
            private int id;
            private String car_allname;
            private String car_price;
            private String last_mileage;
            private String new_car_price;
            private String rent_price;
            private String new_rent_price;
            private String just_side_img;
            private String loan_first;
            private String loan_month;
            private String htmlUrl;

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

            private int status;
            private int rent_status;

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
