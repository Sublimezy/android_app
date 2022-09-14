package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * Created by Owner on 2018/1/18.
 */
public class CarLifeGoodsBean {


    /**
     * content : [{"shop_id":"04489ea40c5c4c85a45d1199d0b1fa7f","goods_name":"普通洗车-五座","distance":"7km","sold_number":"已售 200人","price":"¥12.00元","service_name":"标准洗车","service_id":1,"shop_place_name":"松北区恒大绿洲14栋120号","discount":"0折","goods_id":1,"shop_img":"http://images.xueyiche.vip/20171207151826748.jpg","shop_name":"旺盛洗车行"},{"shop_id":"0aa0c47c6ed74be686a270d9f63b3cc2","goods_name":"普通洗车-五座","distance":"26km","sold_number":"已售 180人","price":"¥10.00元","service_name":"标准洗车","service_id":1,"shop_place_name":"香坊区三合路583号","discount":"0折","goods_id":1,"shop_img":"http://images.xueyiche.vip/Ftq5_kamX0z_xPa6HTHs79Zgjh9Y","shop_name":"腾达汽车美容装饰\t"}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * shop_id : 04489ea40c5c4c85a45d1199d0b1fa7f
     * goods_name : 普通洗车-五座
     * distance : 7km
     * sold_number : 已售 200人
     * price : ¥12.00元
     * service_name : 标准洗车
     * service_id : 1
     * shop_place_name : 松北区恒大绿洲14栋120号
     * discount : 0折
     * goods_id : 1
     * shop_img : http://images.xueyiche.vip/20171207151826748.jpg
     * shop_name : 旺盛洗车行
     */

    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String shop_id;
        private String goods_name;
        private String distance;
        private String sold_number;
        private double price;
        private String service_name;
        private int service_id;
        private String shop_place_name;
        private String discount;
        private int goods_id;
        private String shop_img;
        private String shop_name;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getSold_number() {
            return sold_number;
        }

        public void setSold_number(String sold_number) {
            this.sold_number = sold_number;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public int getService_id() {
            return service_id;
        }

        public void setService_id(int service_id) {
            this.service_id = service_id;
        }

        public String getShop_place_name() {
            return shop_place_name;
        }

        public void setShop_place_name(String shop_place_name) {
            this.shop_place_name = shop_place_name;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getShop_img() {
            return shop_img;
        }

        public void setShop_img(String shop_img) {
            this.shop_img = shop_img;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }
    }
}
