package com.xueyiche.zjyk.xueyiche.carlife.bean;

import java.util.List;

/**
 * Created by YJF on 2020/2/27.
 */
public class CarLifeSearchResultBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"distance":"0km","service_name":"标准洗车","goods_id":324,"shop_img":"http://images.xueyiche.vip/FvJl5QC8BGoczCE4c7UfbagfiqmO","shop_id":"1feeaebe2b22400a92f45d8fb46f1c4d","latitude_shop":"45.751677","price":"¥21.00","service_id":1,"shop_place_name":"道里区松林街副25号","longitude_shop":"126.602414","name":"普洗（外观洗车）"},{"distance":"22km","service_name":"标准洗车","goods_id":1066,"shop_img":"http://images.xueyiche.vip/FjbqVEndUfYJGa1UsSL_RMAPmmqh.jpg.jpg.jpg.jpg","shop_id":"f1bfdd3c77c646e6881bdc76b3b91c18","latitude_shop":"45.875343","price":"¥0.00","service_id":1,"shop_place_name":"香坊区三合路597号","longitude_shop":"126.827287","name":"慕尚洗车工厂"}]
     */

    private String msg;
    private int code;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * distance : 0km
         * service_name : 标准洗车
         * goods_id : 324
         * shop_img : http://images.xueyiche.vip/FvJl5QC8BGoczCE4c7UfbagfiqmO
         * shop_id : 1feeaebe2b22400a92f45d8fb46f1c4d
         * latitude_shop : 45.751677
         * price : ¥21.00
         * service_id : 1
         * shop_place_name : 道里区松林街副25号
         * longitude_shop : 126.602414
         * name : 普洗（外观洗车）
         */

        private String distance;
        private String service_name;
        private int goods_id;
        private String shop_img;
        private String shop_id;
        private String latitude_shop;
        private String price;
        private int service_id;
        private String shop_place_name;
        private String longitude_shop;
        private String name;

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
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

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getLatitude_shop() {
            return latitude_shop;
        }

        public void setLatitude_shop(String latitude_shop) {
            this.latitude_shop = latitude_shop;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getLongitude_shop() {
            return longitude_shop;
        }

        public void setLongitude_shop(String longitude_shop) {
            this.longitude_shop = longitude_shop;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
