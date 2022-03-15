package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * Created by ZL on 2017/10/24.
 */
public class YiBiBean {

    /**
     * content : {"imagelist":[{"em_goods_money":"1","em_pic_url":"1","em_price":"1","em_number":"1","em_id":"1","em_det_content":"1","em_roa_identifation":"1","em_name":"1"},{"em_goods_money":"1","em_pic_url":"1","em_price":"11","em_number":"1","em_id":"3","em_det_content":"1","em_roa_identifation":"1","em_name":"1"}],"goodslist":[{"em_goods_money":"2","em_pic_url":"2","em_price":"2","em_number":"2","em_id":"2","em_det_content":"2","em_roa_identifation":"0","em_name":"2"},{"em_goods_money":"1","em_pic_url":"1","em_price":"1","em_number":"1","em_id":"4","em_det_content":"1","em_roa_identifation":"0","em_name":"1"}]}
     * code : 0
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
        private List<ImagelistBean> imagelist;
        private List<GoodslistBean> goodslist;

        public List<ImagelistBean> getImagelist() {
            return imagelist;
        }

        public void setImagelist(List<ImagelistBean> imagelist) {
            this.imagelist = imagelist;
        }

        public List<GoodslistBean> getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(List<GoodslistBean> goodslist) {
            this.goodslist = goodslist;
        }

        public static class ImagelistBean {
            /**
             * em_goods_money : 1
             * em_pic_url : 1
             * em_price : 1
             * em_number : 1
             * em_id : 1
             * em_det_content : 1
             * em_roa_identifation : 1
             * em_name : 1
             */

            private String em_goods_money;
            private String em_pic_url;
            private String em_price;
            private String em_number;
            private String em_id;
            private String em_det_content;
            private String em_roa_identifation;
            private String em_name;

            public String getEm_goods_money() {
                return em_goods_money;
            }

            public void setEm_goods_money(String em_goods_money) {
                this.em_goods_money = em_goods_money;
            }

            public String getEm_pic_url() {
                return em_pic_url;
            }

            public void setEm_pic_url(String em_pic_url) {
                this.em_pic_url = em_pic_url;
            }

            public String getEm_price() {
                return em_price;
            }

            public void setEm_price(String em_price) {
                this.em_price = em_price;
            }

            public String getEm_number() {
                return em_number;
            }

            public void setEm_number(String em_number) {
                this.em_number = em_number;
            }

            public String getEm_id() {
                return em_id;
            }

            public void setEm_id(String em_id) {
                this.em_id = em_id;
            }

            public String getEm_det_content() {
                return em_det_content;
            }

            public void setEm_det_content(String em_det_content) {
                this.em_det_content = em_det_content;
            }

            public String getEm_roa_identifation() {
                return em_roa_identifation;
            }

            public void setEm_roa_identifation(String em_roa_identifation) {
                this.em_roa_identifation = em_roa_identifation;
            }

            public String getEm_name() {
                return em_name;
            }

            public void setEm_name(String em_name) {
                this.em_name = em_name;
            }
        }

        public static class GoodslistBean {
            /**
             * em_goods_money : 2
             * em_pic_url : 2
             * em_price : 2
             * em_number : 2
             * em_id : 2
             * em_det_content : 2
             * em_roa_identifation : 0
             * em_name : 2
             */

            private String em_goods_money;
            private String em_pic_url;
            private String em_price;
            private String em_number;
            private String em_id;
            private String em_det_content;
            private String em_roa_identifation;
            private String em_name;

            public String getEm_goods_money() {
                return em_goods_money;
            }

            public void setEm_goods_money(String em_goods_money) {
                this.em_goods_money = em_goods_money;
            }

            public String getEm_pic_url() {
                return em_pic_url;
            }

            public void setEm_pic_url(String em_pic_url) {
                this.em_pic_url = em_pic_url;
            }

            public String getEm_price() {
                return em_price;
            }

            public void setEm_price(String em_price) {
                this.em_price = em_price;
            }

            public String getEm_number() {
                return em_number;
            }

            public void setEm_number(String em_number) {
                this.em_number = em_number;
            }

            public String getEm_id() {
                return em_id;
            }

            public void setEm_id(String em_id) {
                this.em_id = em_id;
            }

            public String getEm_det_content() {
                return em_det_content;
            }

            public void setEm_det_content(String em_det_content) {
                this.em_det_content = em_det_content;
            }

            public String getEm_roa_identifation() {
                return em_roa_identifation;
            }

            public void setEm_roa_identifation(String em_roa_identifation) {
                this.em_roa_identifation = em_roa_identifation;
            }

            public String getEm_name() {
                return em_name;
            }

            public void setEm_name(String em_name) {
                this.em_name = em_name;
            }
        }
    }
}
