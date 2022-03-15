package com.xueyiche.zjyk.xueyiche.carlife.bean;

import java.util.List;

/**
 * Created by Owner on 2017/10/31.
 */
public class DuiHuanGoodsBean {

    /**
     * content : [{"gift_url":"http://oyf45oo96.bkt.clouddn.com/充值10元.png","price":10,"name":"充值10元","id":1},{"gift_url":"http://oyf45oo96.bkt.clouddn.com/充值50元.png","price":50,"name":"充值50元","id":2},{"gift_url":"http://oyf45oo96.bkt.clouddn.com/购物100元.png","price":100,"name":"购物100元","id":3},{"gift_url":"http://oyf45oo96.bkt.clouddn.com/购物200元.png","price":200,"name":"购物200元","id":4},{"gift_url":"http://oyf45oo96.bkt.clouddn.com/购物500元.png","price":500,"name":"购物500元","id":5},{"gift_url":"http://oyf45oo96.bkt.clouddn.com/购物1000元.png","price":1000,"name":"购物1000元","id":6},{"gift_url":"http://oyf45oo96.bkt.clouddn.com/油卡100元.png","price":100,"name":"油卡100元","id":7},{"gift_url":"http://oyf45oo96.bkt.clouddn.com/油卡200元.png","price":200,"name":"油卡200元","id":8},{"gift_url":"http://oyf45oo96.bkt.clouddn.com/油卡500元.png","price":500,"name":"油卡500元","id":9},{"gift_url":"http://oyf45oo96.bkt.clouddn.com/油卡1000元.png","price":1000,"name":"油卡1000元","id":10}]
     * code : 0
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * gift_url : http://oyf45oo96.bkt.clouddn.com/充值10元.png
     * price : 10
     * name : 充值10元
     * id : 1
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
        private String gift_url;
        private int price;
        private String name;
        private int id;

        public String getGift_url() {
            return gift_url;
        }

        public void setGift_url(String gift_url) {
            this.gift_url = gift_url;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
