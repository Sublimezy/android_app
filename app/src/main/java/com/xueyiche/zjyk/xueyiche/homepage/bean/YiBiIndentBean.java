package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * Created by ZL on 2017/10/25.
 */
public class YiBiIndentBean {


    /**
     * content : [{"em_pic_url":"https://detail.tmall.com/item.htm?spm=a230r.1.14.1.26a175ecYgq6le&id=20983079462&ns=1&abbucket=12","em_goods_money":"2","em_buy_number":"1","em_order_info_id":"E1508833083404","em_order_type":"0","em_det_content":"这是一个超级拉杆箱","em_order_time":"2017-10-24 16:18:03","em_name":"幸运拉杆箱"},{"em_pic_url":"https://detail.tmall.com/item.htm?spm=a230r.1.14.1.26a175ecYgq6le&id=20983079462&ns=1&abbucket=12","em_goods_money":"2","em_buy_number":"1","em_order_info_id":"E1508892595413","em_order_type":"0","em_det_content":"这是一个超级拉杆箱","em_order_time":"2017-10-25 08:49:55","em_name":"幸运拉杆箱"},{"em_pic_url":"https://detail.tmall.com/item.htm?spm=a230r.1.14.1.26a175ecYgq6le&id=20983079462&ns=1&abbucket=12","em_goods_money":"2000","em_buy_number":"1","em_order_info_id":"E1508917913372","em_order_type":"0","em_det_content":"这是一个超级拉杆箱","em_order_time":"2017-10-25 15:51:53","em_name":"幸运拉杆箱"}]
     * code : 0
     * msg : 操作成功
     */

    private int code;
    private String msg;
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
        /**
         * em_pic_url : https://detail.tmall.com/item.htm?spm=a230r.1.14.1.26a175ecYgq6le&id=20983079462&ns=1&abbucket=12
         * em_goods_money : 2
         * em_buy_number : 1
         * em_order_info_id : E1508833083404
         * em_order_type : 0
         * em_det_content : 这是一个超级拉杆箱
         * em_order_time : 2017-10-24 16:18:03
         * em_name : 幸运拉杆箱
         */

        private String em_pic_url;
        private String em_goods_money;
        private String em_buy_number;
        private String em_order_info_id;
        private String em_order_type;
        private String em_det_content;
        private String em_order_time;
        private String em_name;

        public String getEm_pic_url() {
            return em_pic_url;
        }

        public void setEm_pic_url(String em_pic_url) {
            this.em_pic_url = em_pic_url;
        }

        public String getEm_goods_money() {
            return em_goods_money;
        }

        public void setEm_goods_money(String em_goods_money) {
            this.em_goods_money = em_goods_money;
        }

        public String getEm_buy_number() {
            return em_buy_number;
        }

        public void setEm_buy_number(String em_buy_number) {
            this.em_buy_number = em_buy_number;
        }

        public String getEm_order_info_id() {
            return em_order_info_id;
        }

        public void setEm_order_info_id(String em_order_info_id) {
            this.em_order_info_id = em_order_info_id;
        }

        public String getEm_order_type() {
            return em_order_type;
        }

        public void setEm_order_type(String em_order_type) {
            this.em_order_type = em_order_type;
        }

        public String getEm_det_content() {
            return em_det_content;
        }

        public void setEm_det_content(String em_det_content) {
            this.em_det_content = em_det_content;
        }

        public String getEm_order_time() {
            return em_order_time;
        }

        public void setEm_order_time(String em_order_time) {
            this.em_order_time = em_order_time;
        }

        public String getEm_name() {
            return em_name;
        }

        public void setEm_name(String em_name) {
            this.em_name = em_name;
        }
    }
}
