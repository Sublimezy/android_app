package com.xueyiche.zjyk.xueyiche.xycindent.bean;

import java.util.List;

/**
 * Created by Owner on 2018/5/21.
 */
public class CarDetailsBean {

    /**
     * pay_price : ￥10.00
     * coupon_offset_amount : 0
     * order_number : E1526543857209
     * shop_img : http://images.xueyiche.vip/Ftq5_kamX0z_xPa6HTHs79Zgjh9Y
     * shop_name : 腾达汽车美容装饰
     * phone_shop : 15045099302
     * order_status : 0
     * pay_status : 0
     * latitude_shop : 45.875343
     * shopList : [{"service_name":"标准洗车x1","old_price":20}]
     * ew_code : 009650650924
     * shop_place_name : 香坊区三合路583号
     * longitude_shop : 126.827287
     * order_system_time : 2018-05-17 15:57:38
     * all_old_price : ￥20.00
     */

    private ContentBean content;
    /**
     * content : {"pay_price":"￥10.00","coupon_offset_amount":0,"order_number":"E1526543857209","shop_img":"http://images.xueyiche.vip/Ftq5_kamX0z_xPa6HTHs79Zgjh9Y","shop_name":"腾达汽车美容装饰","phone_shop":"15045099302","order_status":"0","pay_status":"0","latitude_shop":"45.875343","shopList":[{"service_name":"标准洗车x1","old_price":20}],"ew_code":"009650650924","shop_place_name":"香坊区三合路583号","longitude_shop":"126.827287","order_system_time":"2018-05-17 15:57:38","all_old_price":"￥20.00"}
     * code : 200
     * msg : 操作成功
     */

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
        private String pay_price;
        private int coupon_offset_amount;
        private String order_number;
        private String shop_img;
        private String shop_name;
        private String phone_shop;
        private String order_status;
        private String pay_status;
        private String latitude_shop;
        private String ew_code;
        private String shop_place_name;
        private String longitude_shop;
        private String order_system_time;
        private String all_old_price;
        /**
         * service_name : 标准洗车x1
         * old_price : 20
         */

        private List<ShopListBean> shopList;

        public String getPay_price() {
            return pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public int getCoupon_offset_amount() {
            return coupon_offset_amount;
        }

        public void setCoupon_offset_amount(int coupon_offset_amount) {
            this.coupon_offset_amount = coupon_offset_amount;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
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

        public String getPhone_shop() {
            return phone_shop;
        }

        public void setPhone_shop(String phone_shop) {
            this.phone_shop = phone_shop;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getLatitude_shop() {
            return latitude_shop;
        }

        public void setLatitude_shop(String latitude_shop) {
            this.latitude_shop = latitude_shop;
        }

        public String getEw_code() {
            return ew_code;
        }

        public void setEw_code(String ew_code) {
            this.ew_code = ew_code;
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

        public String getOrder_system_time() {
            return order_system_time;
        }

        public void setOrder_system_time(String order_system_time) {
            this.order_system_time = order_system_time;
        }

        public String getAll_old_price() {
            return all_old_price;
        }

        public void setAll_old_price(String all_old_price) {
            this.all_old_price = all_old_price;
        }

        public List<ShopListBean> getShopList() {
            return shopList;
        }

        public void setShopList(List<ShopListBean> shopList) {
            this.shopList = shopList;
        }

        public static class ShopListBean {
            private String service_name;
            private String old_price;

            public String getService_name() {
                return service_name;
            }

            public void setService_name(String service_name) {
                this.service_name = service_name;
            }

            public String getOld_price() {
                return old_price;
            }

            public void setOld_price(String old_price) {
                this.old_price = old_price;
            }
        }
    }
}
