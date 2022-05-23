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
 * #            Created by 張某人 on 2022/5/23/3:24 下午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage.bean
 * #            xueyiche5.0
 */
public class DaiJianCheBean {

    /**
     * code : 1
     * msg : 请求成功
     * time : 1653285733
     * data : {"total":1,"per_page":"10","current_page":1,"last_page":1,"data":[{"id":43,"title":"学易车检车站","image":"http://xychead.xueyiche.vip/uploads/20220523/e8d6cb94bfb41921c5993fad240cdf37.png","address":"黑龙江省哈尔滨市松北区智谷大街靠近松北(龙岗)科技创新产业园","distance":77.51,"coordinate":"126.476907,45.808800"}]}
     */

    private int code;
    private String msg;
    private String time;
    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":43,"title":"学易车检车站","image":"http://xychead.xueyiche.vip/uploads/20220523/e8d6cb94bfb41921c5993fad240cdf37.png","address":"黑龙江省哈尔滨市松北区智谷大街靠近松北(龙岗)科技创新产业园","distance":77.51,"coordinate":"126.476907,45.808800"}]
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
        private int total;
        private String per_page;
        private int current_page;
        private int last_page;
        /**
         * id : 43
         * title : 学易车检车站
         * image : http://xychead.xueyiche.vip/uploads/20220523/e8d6cb94bfb41921c5993fad240cdf37.png
         * address : 黑龙江省哈尔滨市松北区智谷大街靠近松北(龙岗)科技创新产业园
         * distance : 77.51
         * coordinate : 126.476907,45.808800
         */

        private List<DataBean1> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean1> getData() {
            return data;
        }

        public void setData(List<DataBean1> data) {
            this.data = data;
        }

        public static class DataBean1 {
            private int id;
            private String title;
            private String image;
            private String address;
            private double distance;
            private String coordinate;

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

            public String getCoordinate() {
                return coordinate;
            }

            public void setCoordinate(String coordinate) {
                this.coordinate = coordinate;
            }
        }
    }
}
