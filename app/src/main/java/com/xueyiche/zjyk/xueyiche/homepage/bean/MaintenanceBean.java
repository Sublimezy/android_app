package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

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
 * #            Created by 張某人 on 2022/5/25/4:30 下午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage.bean
 * #            xueyiche5.0
 */
public class MaintenanceBean {

    /**
     * code : 1
     * msg : 请求成功
     * time : 1653469131
     * data : {"id":43,"title":"学易车检车站","image":"http://xychead.xueyiche.vip/uploads/20220523/e8d6cb94bfb41921c5993fad240cdf37.png","images":["http://xychead.xueyiche.vip/uploads/20220525/d4cd1dd49ce6a22935c85ffb5b579995.jpg","http://xychead.xueyiche.vip/uploads/20220525/7ed3e50fa43512d3d02566a05f333005.jpg"],"address":"黑龙江省哈尔滨市松北区智谷大街靠近松北(龙岗)科技创新产业园","distance":0.03,"coordinate":["126.476907","45.808800"],"money":"","tel":"","lng":"126.476907","lat":"45.808800"}
     */

    private int code;
    private String msg;
    private String time;
    /**
     * id : 43
     * title : 学易车检车站
     * image : http://xychead.xueyiche.vip/uploads/20220523/e8d6cb94bfb41921c5993fad240cdf37.png
     * images : ["http://xychead.xueyiche.vip/uploads/20220525/d4cd1dd49ce6a22935c85ffb5b579995.jpg","http://xychead.xueyiche.vip/uploads/20220525/7ed3e50fa43512d3d02566a05f333005.jpg"]
     * address : 黑龙江省哈尔滨市松北区智谷大街靠近松北(龙岗)科技创新产业园
     * distance : 0.03
     * coordinate : ["126.476907","45.808800"]
     * money :
     * tel :
     * lng : 126.476907
     * lat : 45.808800
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
        private int id;
        private String title;
        private String image;
        private String address;
        private double distance;
        private String money;
        private String tel;
        private String lng;
        private String lat;
        private List<String> images;
        private List<String> coordinate;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<String> getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(List<String> coordinate) {
            this.coordinate = coordinate;
        }
    }
}
