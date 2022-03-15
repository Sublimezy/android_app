package com.xueyiche.zjyk.xueyiche.carlife.bean;

import java.util.List;

/**
 * Created by YJF on 2020/2/26.
 */
public class CarLifeListBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"good_list":[{"goods_name":"普洗","discount_0":"0.89","price":35.6,"old_price":40,"goods_description":"大型SUV（7座及以下车型）","goods_id":32,"discount":"89折"},{"goods_name":"普洗","discount_0":"0.81","price":20.25,"old_price":25,"goods_description":"5座及以下车型","goods_id":317,"discount":"81折"}],"shop_id":"c8756df98bb649f2b82309495cfbf61a","latitude_shop":"45.735519","distance":"8km","service_name":"标准洗车","service_id":1,"shop_place_name":"香坊区安埠街137号","longitude_shop":"126.708627","shop_img":"http://images.xueyiche.vip/FmK1a0UE8sHp5CrC8_HXstk0b_xt.jpg.jpg.jpg","shop_name":"博斯卡汽车美容欧冠养护"},{"good_list":[{"goods_name":"普洗","discount_0":"0.89","price":35.6,"old_price":40,"goods_description":"中型SUV（5座及以下车型）","goods_id":70,"discount":"89折"},{"goods_name":"普洗","discount_0":"0.91","price":45.5,"old_price":50,"goods_description":"大型SUV（7座及以下车型）","goods_id":232,"discount":"91折"}],"shop_id":"253e3032694949ee8403ac09ee2e082c","latitude_shop":"45.752536","distance":"8km","service_name":"标准洗车","service_id":1,"shop_place_name":"南岗区千山路13-29号","longitude_shop":"126.69904","shop_img":"http://images.xueyiche.vip/Fj5zbee2jDGu_WzWWtSRFIpKHP8S","shop_name":"运通骏业汽车美容\t"},{"good_list":[{"goods_name":"普洗","discount_0":"0.7","price":21,"old_price":30,"goods_description":"5座及以下车型","goods_id":18,"discount":"7折"},{"goods_name":"普洗","discount_0":"0.75","price":30,"old_price":40,"goods_description":"中型SUV（5座及以下车型）","goods_id":215,"discount":"75折"}],"shop_id":"25d9019ab8fd4d4ba4658667b7fa020d","latitude_shop":"45.792431","distance":"9km","service_name":"标准洗车","service_id":1,"shop_place_name":"道外南直路522-6与宏北街交口","longitude_shop":"126.707856","shop_img":"http://images.xueyiche.vip/Fl-FhYc-6P7MXUQgxUQisYIC-0fp","shop_name":"畅驰汽车美容会馆"}]
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
         * good_list : [{"goods_name":"普洗","discount_0":"0.89","price":35.6,"old_price":40,"goods_description":"大型SUV（7座及以下车型）","goods_id":32,"discount":"89折"},{"goods_name":"普洗","discount_0":"0.81","price":20.25,"old_price":25,"goods_description":"5座及以下车型","goods_id":317,"discount":"81折"}]
         * shop_id : c8756df98bb649f2b82309495cfbf61a
         * latitude_shop : 45.735519
         * distance : 8km
         * service_name : 标准洗车
         * service_id : 1
         * shop_place_name : 香坊区安埠街137号
         * longitude_shop : 126.708627
         * shop_img : http://images.xueyiche.vip/FmK1a0UE8sHp5CrC8_HXstk0b_xt.jpg.jpg.jpg
         * shop_name : 博斯卡汽车美容欧冠养护
         */

        private String shop_id;
        private String latitude_shop;
        private String distance;
        private String service_name;
        private int service_id;
        private String shop_place_name;
        private String longitude_shop;
        private String shop_img;
        private String shop_name;
        private List<GoodListBean> good_list;

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

        public List<GoodListBean> getGood_list() {
            return good_list;
        }

        public void setGood_list(List<GoodListBean> good_list) {
            this.good_list = good_list;
        }

        public static class GoodListBean {
            /**
             * goods_name : 普洗
             * discount_0 : 0.89
             * price : 35.6
             * old_price : 40
             * goods_description : 大型SUV（7座及以下车型）
             * goods_id : 32
             * discount : 89折
             */

            private String goods_name;
            private String discount_0;
            private double price;
            private int old_price;
            private String goods_description;
            private int goods_id;
            private String discount;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getDiscount_0() {
                return discount_0;
            }

            public void setDiscount_0(String discount_0) {
                this.discount_0 = discount_0;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getOld_price() {
                return old_price;
            }

            public void setOld_price(int old_price) {
                this.old_price = old_price;
            }

            public String getGoods_description() {
                return goods_description;
            }

            public void setGoods_description(String goods_description) {
                this.goods_description = goods_description;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }
        }
    }
}
