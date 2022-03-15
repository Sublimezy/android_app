package com.xueyiche.zjyk.xueyiche.submit.bean;

import java.util.List;

/**
 * Created by ZL on 2018/3/1.
 */
public class CarLifeSubmitBean {

    /**
     * content : {"recharge_mark":"","CouponNumber":"可用1个","shop_car_info":[{"goods_name":"维修保养x1","old_price":"¥100.00","goods_id":3},{"goods_name":"普通洗车-五座x2","old_price":"¥80.00","goods_id":6}],"order_info":{"shop_id":"04489ea40c5c4c85a45d1199d0b1fa7f","pay_price":"¥80.00","integral_enough":"1","order_number":"E1519804041238","integral_use_num":"现用10000积分抵扣¥100.00","shop_img":"http://images.xueyiche.vip/20171207151826748.jpg","all_old_price":"¥180.00","shop_name":"旺盛洗车行"}}
     * code : 200
     * msg : 操作成功
     */

    private ContentBean content;
    private int code;
    private String msg;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

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

    public static class ContentBean {
        /**
         * recharge_mark :
         * CouponNumber : 可用1个
         * shop_car_info : [{"goods_name":"维修保养x1","old_price":"¥100.00","goods_id":3},{"goods_name":"普通洗车-五座x2","old_price":"¥80.00","goods_id":6}]
         * order_info : {"shop_id":"04489ea40c5c4c85a45d1199d0b1fa7f","pay_price":"¥80.00","integral_enough":"1","order_number":"E1519804041238","integral_use_num":"现用10000积分抵扣¥100.00","shop_img":"http://images.xueyiche.vip/20171207151826748.jpg","all_old_price":"¥180.00","shop_name":"旺盛洗车行"}
         */

        private String recharge_mark;
        private String CouponNumber;
        private OrderInfoBean order_info;
        private List<ShopCarInfoBean> shop_car_info;

        public String getRecharge_mark() {
            return recharge_mark;
        }

        public void setRecharge_mark(String recharge_mark) {
            this.recharge_mark = recharge_mark;
        }

        public String getCouponNumber() {
            return CouponNumber;
        }

        public void setCouponNumber(String CouponNumber) {
            this.CouponNumber = CouponNumber;
        }

        public OrderInfoBean getOrder_info() {
            return order_info;
        }

        public void setOrder_info(OrderInfoBean order_info) {
            this.order_info = order_info;
        }

        public List<ShopCarInfoBean> getShop_car_info() {
            return shop_car_info;
        }

        public void setShop_car_info(List<ShopCarInfoBean> shop_car_info) {
            this.shop_car_info = shop_car_info;
        }

        public static class OrderInfoBean {
            /**
             * shop_id : 04489ea40c5c4c85a45d1199d0b1fa7f
             * pay_price : ¥80.00
             * integral_enough : 1
             * order_number : E1519804041238
             * integral_use_num : 现用10000积分抵扣¥100.00
             * shop_img : http://images.xueyiche.vip/20171207151826748.jpg
             * all_old_price : ¥180.00
             * shop_name : 旺盛洗车行
             */

            private String shop_id;
            private String pay_price;
            private String integral_enough;
            private String order_number;
            private String integral_use_num;
            private String shop_img;
            private String all_old_price;
            private String shop_name;

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getPay_price() {
                return pay_price;
            }

            public void setPay_price(String pay_price) {
                this.pay_price = pay_price;
            }

            public String getIntegral_enough() {
                return integral_enough;
            }

            public void setIntegral_enough(String integral_enough) {
                this.integral_enough = integral_enough;
            }

            public String getOrder_number() {
                return order_number;
            }

            public void setOrder_number(String order_number) {
                this.order_number = order_number;
            }

            public String getIntegral_use_num() {
                return integral_use_num;
            }

            public void setIntegral_use_num(String integral_use_num) {
                this.integral_use_num = integral_use_num;
            }

            public String getShop_img() {
                return shop_img;
            }

            public void setShop_img(String shop_img) {
                this.shop_img = shop_img;
            }

            public String getAll_old_price() {
                return all_old_price;
            }

            public void setAll_old_price(String all_old_price) {
                this.all_old_price = all_old_price;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }
        }

        public static class ShopCarInfoBean {
            /**
             * goods_name : 维修保养x1
             * old_price : ¥100.00
             * goods_id : 3
             */

            private String goods_name;
            private String old_price;
            private int goods_id;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getOld_price() {
                return old_price;
            }

            public void setOld_price(String old_price) {
                this.old_price = old_price;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }
        }
    }
}
