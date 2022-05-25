package com.xueyiche.zjyk.xueyiche.practicecar.bean;

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
 * #            Created by 張某人 on 2022/5/25/10:43 下午 .
 * #            com.xueyiche.zjyk.xueyiche.practicecar.bean
 * #            xueyiche5.0
 */
public class TrainWithInfoBean {

    /**
     * code : 1
     * msg : 请求成功
     * time : 1653489773
     * data : {"id":53,"title":"李教练陪练","image":"http://xychead.xueyiche.vip/uploads/20220525/fdfa7adc815a1508551ad55eac386904.jpg","images":["http://xychead.xueyiche.vip/uploads/20220525/d4cd1dd49ce6a22935c85ffb5b579995.jpg","http://xychead.xueyiche.vip/uploads/20220525/7ed3e50fa43512d3d02566a05f333005.jpg"],"driving_age":"5","cat_brand":"丰田 汉兰达","h_money":"80","money":"160"}
     */

    private int code;
    private String msg;
    private String time;
    /**
     * id : 53
     * title : 李教练陪练
     * image : http://xychead.xueyiche.vip/uploads/20220525/fdfa7adc815a1508551ad55eac386904.jpg
     * images : ["http://xychead.xueyiche.vip/uploads/20220525/d4cd1dd49ce6a22935c85ffb5b579995.jpg","http://xychead.xueyiche.vip/uploads/20220525/7ed3e50fa43512d3d02566a05f333005.jpg"]
     * driving_age : 5
     * cat_brand : 丰田 汉兰达
     * h_money : 80
     * money : 160
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
        private String driving_age;
        private String cat_brand;
        private String h_money;
        private String money;
        private List<String> images;

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

        public String getDriving_age() {
            return driving_age;
        }

        public void setDriving_age(String driving_age) {
            this.driving_age = driving_age;
        }

        public String getCat_brand() {
            return cat_brand;
        }

        public void setCat_brand(String cat_brand) {
            this.cat_brand = cat_brand;
        }

        public String getH_money() {
            return h_money;
        }

        public void setH_money(String h_money) {
            this.h_money = h_money;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
