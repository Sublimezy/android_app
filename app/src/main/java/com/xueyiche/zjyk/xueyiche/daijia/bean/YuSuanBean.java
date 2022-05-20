package com.xueyiche.zjyk.xueyiche.daijia.bean;

import java.io.Serializable;

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
 * #            Created by 張某人 on 2022/4/11/10:35 上午 .
 * #            com.xueyiche.zjyk.xueyiche.daijia.bean
 * #            xueyiche5.0
 */
public class YuSuanBean implements Serializable {

    /**
     * code : 1
     * msg : 请求成功
     * time : 1649408286
     * data : {"prices":"51.79","shichang_time":"75","shichang_price":"15.00","licheng_km":"11.5","licheng_price":"15.00","neiquyu_km":"11","neiquyu_km_price":"0.00","waiquyu_km":"0.5","waiquyu_km_price":"5.00","youhui_price":"0.00","total_price":"70.00"}
     */

    private int code;
    private String msg;
    private String time;
    /**
     * prices : 51.79
     * shichang_time : 75
     * shichang_price : 15.00
     * licheng_km : 11.5
     * licheng_price : 15.00
     * neiquyu_km : 11
     * neiquyu_km_price : 0.00
     * waiquyu_km : 0.5
     * waiquyu_km_price : 5.00
     * youhui_price : 0.00
     * total_price : 70.00
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

    public static class DataBean implements Serializable {
        private String qibu_price;//: "35",
        private String shichang_time;//: "19.00", //时长
        private String shichang_price;//: 0, //时长价格
        private String licheng_km;//: 11.359, //里程
        private String licheng_price;//: 51.795, //里程价格
        private String neiquyu_km;//: 0, //区域内公里
        private String neiquyu_km_price;//: 0, //区域内部公里数
        private String waiquyu_km;//: 11.359, //区域外部公里
        private String waiquyu_km_price;//: 51.795, //区域外部公里数
        private String youhui_price;//: 0, //优惠价格
        private String total_price;//: 51.795, //总价格
        private String price;//: 51.795 //预估价格

        public String getQibu_price() {
            return qibu_price;
        }

        public void setQibu_price(String qibu_price) {
            this.qibu_price = qibu_price;
        }

        public String getShichang_time() {
            return shichang_time;
        }

        public void setShichang_time(String shichang_time) {
            this.shichang_time = shichang_time;
        }

        public String getShichang_price() {
            return shichang_price;
        }

        public void setShichang_price(String shichang_price) {
            this.shichang_price = shichang_price;
        }

        public String getLicheng_km() {
            return licheng_km;
        }

        public void setLicheng_km(String licheng_km) {
            this.licheng_km = licheng_km;
        }

        public String getLicheng_price() {
            return licheng_price;
        }

        public void setLicheng_price(String licheng_price) {
            this.licheng_price = licheng_price;
        }

        public String getNeiquyu_km() {
            return neiquyu_km;
        }

        public void setNeiquyu_km(String neiquyu_km) {
            this.neiquyu_km = neiquyu_km;
        }

        public String getNeiquyu_km_price() {
            return neiquyu_km_price;
        }

        public void setNeiquyu_km_price(String neiquyu_km_price) {
            this.neiquyu_km_price = neiquyu_km_price;
        }

        public String getWaiquyu_km() {
            return waiquyu_km;
        }

        public void setWaiquyu_km(String waiquyu_km) {
            this.waiquyu_km = waiquyu_km;
        }

        public String getWaiquyu_km_price() {
            return waiquyu_km_price;
        }

        public void setWaiquyu_km_price(String waiquyu_km_price) {
            this.waiquyu_km_price = waiquyu_km_price;
        }

        public String getYouhui_price() {
            return youhui_price;
        }

        public void setYouhui_price(String youhui_price) {
            this.youhui_price = youhui_price;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
