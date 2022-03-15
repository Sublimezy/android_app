package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

import java.util.List;

/**
 * Created by ZL on 2018/3/2.
 */
public class CouponBean {

    /**
     * content : [{"coupon_id":1,"offset_money":"减1.00","end_time":"截止日期：2018-05-31","coupon_type_main":"1","coupon_name":"学易车无门槛优惠券","coupon_type":"无门槛优惠券","shiyongyu":"适用于：全部商家"},{"coupon_id":2,"offset_money":"减2.00","end_time":"截止日期：2018-05-31","coupon_type_main":"2","coupon_name":"学易车满减优惠券","coupon_type":"无门槛优惠券","shiyongyu":"适用于：满30.00可用"},{"coupon_id":3,"offset_money":"减3.00","end_time":"截止日期：2018-05-31","coupon_type_main":"3","coupon_name":"学易车洗车优惠券","coupon_type":"无门槛优惠券","shiyongyu":"适用于：标准洗车"},{"coupon_id":4,"offset_money":"减4.00","end_time":"截止日期：2018-05-31","coupon_type_main":"4","coupon_name":"腾达汽车美容专用","coupon_type":"限指定商家","shiyongyu":"适用于：指定商家"},{"coupon_id":5,"offset_money":"减5.00","end_time":"截止日期：2018-05-31","coupon_type_main":"4","coupon_name":"通瑞汽车维修专用","coupon_type":"限指定商家","shiyongyu":"适用于：指定商家"},{"coupon_id":6,"offset_money":"减6.00","end_time":"截止日期：2018-05-31","coupon_type_main":"4","coupon_name":"旺盛洗车行专用","coupon_type":"限指定商家","shiyongyu":"适用于：指定商家"}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * coupon_id : 1
     * offset_money : 减1.00
     * end_time : 截止日期：2018-05-31
     * coupon_type_main : 1
     * coupon_name : 学易车无门槛优惠券
     * coupon_type : 无门槛优惠券
     * shiyongyu : 适用于：全部商家
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
        private int coupon_id;
        private String offset_money;
        private String end_time;
        private String coupon_type_main;
        private String coupon_name;
        private String coupon_type;
        private String shiyongyu;
        private String coupon_selected;
        public String getCoupon_selected() {
            return coupon_selected;
        }

        public void setCoupon_selected(String coupon_selected) {
            this.coupon_selected = coupon_selected;
        }



        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        private String web_url;

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getOffset_money() {
            return offset_money;
        }

        public void setOffset_money(String offset_money) {
            this.offset_money = offset_money;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCoupon_type_main() {
            return coupon_type_main;
        }

        public void setCoupon_type_main(String coupon_type_main) {
            this.coupon_type_main = coupon_type_main;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(String coupon_type) {
            this.coupon_type = coupon_type;
        }

        public String getShiyongyu() {
            return shiyongyu;
        }

        public void setShiyongyu(String shiyongyu) {
            this.shiyongyu = shiyongyu;
        }
    }
}
