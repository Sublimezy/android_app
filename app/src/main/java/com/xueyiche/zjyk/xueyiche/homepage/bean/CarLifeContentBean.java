package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * Created by ZL on 2018/2/8.
 */
public class CarLifeContentBean {

    /**
     * content : {"good_list":[{"goods_name":"普通洗车-五座","price":12,"old_price":15,"goods_id":1,"discount":"8折"},{"goods_name":"维修保养","goods_id":3,"discount":"7折"}],"shop_info":{"shop_id":"04489ea40c5c4c85a45d1199d0b1fa7f","latitude_shop":"45.830007","purchase_number":"236","shop_place_name":"松北区恒大绿洲14栋120号","longitude_shop":"126.596221","shop_img":"http://images.xueyiche.vip/20171207151826748.jpg","shop_name":"旺盛洗车行","phone_shop":"18245188422"},"label_list":[{"label_id":1,"label_name":"洗车美容"},{"label_id":2,"label_name":"维修保养"},{"label_id":3,"label_name":"安装改装"}],"evaluate_num":{"comment_num":2},"volution_list":[{"volution_id":6,"type":"video","address":"http://p2bualq62.bkt.clouddn.com/image/jpg/volution01.jpg","web_url":"http://p2fpztz9o.bkt.clouddn.com/easy_video/video/video_01.mp4","main_title":"标准洗车视频"},{"volution_id":7,"type":"image","address":"http://p2bualq62.bkt.clouddn.com/image/jpg/volution02.jpg","main_title":"标准洗车图1"},{"volution_id":8,"type":"image","address":"http://p2bualq62.bkt.clouddn.com/image/jpg/volution03.jpg","main_title":"标准洗车图2"}],"evaluate_list":[{"user_id":"3502","head_img":"http://otqopw3sl.bkt.clouddn.com/20170921142315","content_time":"2018-02-07","nickname":"王庆磊","content":"这个商品物有所值"},{"user_id":"29168db2b3b211e7aeb85254f2dc841f","head_img":"http://otqopw3sl.bkt.clouddn.com/20171018111358","content_time":"2018-02-07","nickname":"kam","content":"很不错的商品"}],"append_info":{"wifi":"1","close_time_shop":"21:00","free_park":"1","open_time_shop":"09:00"}}
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
         * good_list : [{"goods_name":"普通洗车-五座","price":12,"old_price":15,"goods_id":1,"discount":"8折"},{"goods_name":"维修保养","goods_id":3,"discount":"7折"}]
         * shop_info : {"shop_id":"04489ea40c5c4c85a45d1199d0b1fa7f","latitude_shop":"45.830007","purchase_number":"236","shop_place_name":"松北区恒大绿洲14栋120号","longitude_shop":"126.596221","shop_img":"http://images.xueyiche.vip/20171207151826748.jpg","shop_name":"旺盛洗车行","phone_shop":"18245188422"}
         * label_list : [{"label_id":1,"label_name":"洗车美容"},{"label_id":2,"label_name":"维修保养"},{"label_id":3,"label_name":"安装改装"}]
         * evaluate_num : {"comment_num":2}
         * volution_list : [{"volution_id":6,"type":"video","address":"http://p2bualq62.bkt.clouddn.com/image/jpg/volution01.jpg","web_url":"http://p2fpztz9o.bkt.clouddn.com/easy_video/video/video_01.mp4","main_title":"标准洗车视频"},{"volution_id":7,"type":"image","address":"http://p2bualq62.bkt.clouddn.com/image/jpg/volution02.jpg","main_title":"标准洗车图1"},{"volution_id":8,"type":"image","address":"http://p2bualq62.bkt.clouddn.com/image/jpg/volution03.jpg","main_title":"标准洗车图2"}]
         * evaluate_list : [{"user_id":"3502","head_img":"http://otqopw3sl.bkt.clouddn.com/20170921142315","content_time":"2018-02-07","nickname":"王庆磊","content":"这个商品物有所值"},{"user_id":"29168db2b3b211e7aeb85254f2dc841f","head_img":"http://otqopw3sl.bkt.clouddn.com/20171018111358","content_time":"2018-02-07","nickname":"kam","content":"很不错的商品"}]
         * append_info : {"wifi":"1","close_time_shop":"21:00","free_park":"1","open_time_shop":"09:00"}
         */

        private ShopInfoBean shop_info;
        private EvaluateNumBean evaluate_num;
        private AppendInfoBean append_info;
        private String collect_status;
        private List<GoodListBean> good_list;
        private List<LabelListBean> label_list;
        private List<VolutionListBean> volution_list;
        private List<EvaluateListBean> evaluate_list;

        public String getCollect_status() {
            return collect_status;
        }

        public void setCollect_status(String collect_status) {
            this.collect_status = collect_status;
        }

        public ShopInfoBean getShop_info() {
            return shop_info;
        }

        public void setShop_info(ShopInfoBean shop_info) {
            this.shop_info = shop_info;
        }

        public EvaluateNumBean getEvaluate_num() {
            return evaluate_num;
        }

        public void setEvaluate_num(EvaluateNumBean evaluate_num) {
            this.evaluate_num = evaluate_num;
        }

        public AppendInfoBean getAppend_info() {
            return append_info;
        }

        public void setAppend_info(AppendInfoBean append_info) {
            this.append_info = append_info;
        }

        public List<GoodListBean> getGood_list() {
            return good_list;
        }

        public void setGood_list(List<GoodListBean> good_list) {
            this.good_list = good_list;
        }

        public List<LabelListBean> getLabel_list() {
            return label_list;
        }

        public void setLabel_list(List<LabelListBean> label_list) {
            this.label_list = label_list;
        }

        public List<VolutionListBean> getVolution_list() {
            return volution_list;
        }

        public void setVolution_list(List<VolutionListBean> volution_list) {
            this.volution_list = volution_list;
        }

        public List<EvaluateListBean> getEvaluate_list() {
            return evaluate_list;
        }

        public void setEvaluate_list(List<EvaluateListBean> evaluate_list) {
            this.evaluate_list = evaluate_list;
        }

        public static class ShopInfoBean {
            /**
             * shop_id : 04489ea40c5c4c85a45d1199d0b1fa7f
             * latitude_shop : 45.830007
             * purchase_number : 236
             * shop_place_name : 松北区恒大绿洲14栋120号
             * longitude_shop : 126.596221
             * shop_img : http://images.xueyiche.vip/20171207151826748.jpg
             * shop_name : 旺盛洗车行
             * phone_shop : 18245188422
             */

            private String shop_id;
            private String latitude_shop;
            private String purchase_number;
            private String shop_place_name;
            private String longitude_shop;
            private String shop_img;
            private String distance;
            private String shop_name;
            private String phone_shop;

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
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

            public String getPurchase_number() {
                return purchase_number;
            }

            public void setPurchase_number(String purchase_number) {
                this.purchase_number = purchase_number;
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

            public String getPhone_shop() {
                return phone_shop;
            }

            public void setPhone_shop(String phone_shop) {
                this.phone_shop = phone_shop;
            }
        }

        public static class EvaluateNumBean {
            /**
             * comment_num : 2
             */

            private int comment_num;

            public int getComment_num() {
                return comment_num;
            }

            public void setComment_num(int comment_num) {
                this.comment_num = comment_num;
            }
        }

        public static class AppendInfoBean {
            /**
             * wifi : 1
             * close_time_shop : 21:00
             * free_park : 1
             * open_time_shop : 09:00
             */

            private String wifi;
            private String close_time_shop;
            private String free_park;
            private String open_time_shop;

            public String getWifi() {
                return wifi;
            }

            public void setWifi(String wifi) {
                this.wifi = wifi;
            }

            public String getClose_time_shop() {
                return close_time_shop;
            }

            public void setClose_time_shop(String close_time_shop) {
                this.close_time_shop = close_time_shop;
            }

            public String getFree_park() {
                return free_park;
            }

            public void setFree_park(String free_park) {
                this.free_park = free_park;
            }

            public String getOpen_time_shop() {
                return open_time_shop;
            }

            public void setOpen_time_shop(String open_time_shop) {
                this.open_time_shop = open_time_shop;
            }
        }

        public static class GoodListBean {
            /**
             * goods_name : 普通洗车-五座
             * price : 12
             * old_price : 15
             * goods_id : 1
             * discount : 8折
             */

            private String goods_name;
            private String goods_description;
            private double price;

            public String getGoods_description() {
                return goods_description;
            }

            public void setGoods_description(String goods_description) {
                this.goods_description = goods_description;
            }

            private double old_price;
            private int goods_id;
            private String discount;
            private String discount_0;
            private boolean isWeiXuBaoYang;
            private int number = 0;

            public String getDiscount_0() {
                return discount_0;
            }

            public void setDiscount_0(String discount_0) {
                this.discount_0 = discount_0;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public boolean isWeiXuBaoYang() {
                return isWeiXuBaoYang;
            }

            public void setWeiXuBaoYang(boolean weiXuBaoYang) {
                isWeiXuBaoYang = weiXuBaoYang;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getOld_price() {
                return old_price;
            }

            public void setOld_price(double old_price) {
                this.old_price = old_price;
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

        public static class LabelListBean {
            /**
             * label_id : 1
             * label_name : 洗车美容
             */

            private int label_id;
            private String label_name;

            public int getLabel_id() {
                return label_id;
            }

            public void setLabel_id(int label_id) {
                this.label_id = label_id;
            }

            public String getLabel_name() {
                return label_name;
            }

            public void setLabel_name(String label_name) {
                this.label_name = label_name;
            }
        }

        public static class VolutionListBean {
            /**
             * volution_id : 6
             * type : video
             * address : http://p2bualq62.bkt.clouddn.com/image/jpg/volution01.jpg
             * web_url : http://p2fpztz9o.bkt.clouddn.com/easy_video/video/video_01.mp4
             * main_title : 标准洗车视频
             */

            private int volution_id;
            private String type;
            private String address;
            private String web_url;
            private String main_title;

            public int getVolution_id() {
                return volution_id;
            }

            public void setVolution_id(int volution_id) {
                this.volution_id = volution_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getWeb_url() {
                return web_url;
            }

            public void setWeb_url(String web_url) {
                this.web_url = web_url;
            }

            public String getMain_title() {
                return main_title;
            }

            public void setMain_title(String main_title) {
                this.main_title = main_title;
            }
        }

        public static class EvaluateListBean {
            /**
             * user_id : 3502
             * head_img : http://otqopw3sl.bkt.clouddn.com/20170921142315
             * content_time : 2018-02-07
             * nickname : 王庆磊
             * content : 这个商品物有所值
             */

            private String user_id;
            private String head_img;
            private String content_time;
            private String nickname;
            private String content;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getContent_time() {
                return content_time;
            }

            public void setContent_time(String content_time) {
                this.content_time = content_time;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
