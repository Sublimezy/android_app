package com.xueyiche.zjyk.xueyiche.daijia.bean;

/**
 * Created by Administrator on 2019/9/26.
 */
public class YuGuBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"amount":"64.0","over_distance":"5.0","user_amount3":"25.0","order_distance":"45","user_amount":"39.0"}
     */

    private String msg;
    private int code;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * amount : 64.0
         * over_distance : 5.0
         * user_amount3 : 25.0
         * order_distance : 45
         * user_amount : 39.0
         */

        private String amount;
        private String over_distance;
        private String user_amount3;
        private String order_distance;
        private String user_amount;
        private String coupon_offset_amount;
        private String coupon_num;

        public String getCoupon_offset_amount() {
            return coupon_offset_amount;
        }

        public void setCoupon_offset_amount(String coupon_offset_amount) {
            this.coupon_offset_amount = coupon_offset_amount;
        }

        public String getCoupon_num() {
            return coupon_num;
        }

        public void setCoupon_num(String coupon_num) {
            this.coupon_num = coupon_num;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getOver_distance() {
            return over_distance;
        }

        public void setOver_distance(String over_distance) {
            this.over_distance = over_distance;
        }

        public String getUser_amount3() {
            return user_amount3;
        }

        public void setUser_amount3(String user_amount3) {
            this.user_amount3 = user_amount3;
        }

        public String getOrder_distance() {
            return order_distance;
        }

        public void setOrder_distance(String order_distance) {
            this.order_distance = order_distance;
        }

        public String getUser_amount() {
            return user_amount;
        }

        public void setUser_amount(String user_amount) {
            this.user_amount = user_amount;
        }
    }
}
