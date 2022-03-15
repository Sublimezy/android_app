package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Owner on 2016/11/8.
 */
public class DriverDetails implements Serializable{


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"hour_price":1,"comment_count":0,"jdsj":"周一至周日 08:00-20:00","fengcai":[{"car_url":"http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg","id":1},{"car_url":"http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg","id":2},{"car_url":"http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg","id":3},{"car_url":"http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg","id":4}],"driver_id":"18d5361fd34a11e8b10e5254f2dc841f","type_name":"舒适型","order_count":14,"hand_auto":"手动挡","head_img":"http://xychead.xueyiche.vip/20181206173612","brand_name":"本田","on_off":"等待接单","qysc":"1小时","driver_name":"张教练 先生","seriesname":"里程","driving_year":"4年驾龄","pinglun":[{"head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","content_time":"2019-12-19","all_evaluate":"5","nickname":"嘿嘿嘿","content":""},{"head_img":"http://xychead.xueyiche.vip/xyc_1544059025652jpg","content_time":"2019-10-11","all_evaluate":"5","nickname":"疯狂的石头","content":""}],"self_estimate":"爱聊天","collect_status":"1"}
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
         * hour_price : 1
         * comment_count : 0
         * jdsj : 周一至周日 08:00-20:00
         * fengcai : [{"car_url":"http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg","id":1},{"car_url":"http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg","id":2},{"car_url":"http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg","id":3},{"car_url":"http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg","id":4}]
         * driver_id : 18d5361fd34a11e8b10e5254f2dc841f
         * type_name : 舒适型
         * order_count : 14
         * hand_auto : 手动挡
         * head_img : http://xychead.xueyiche.vip/20181206173612
         * brand_name : 本田
         * on_off : 等待接单
         * qysc : 1小时
         * driver_name : 张教练 先生
         * seriesname : 里程
         * driving_year : 4年驾龄
         * pinglun : [{"head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","content_time":"2019-12-19","all_evaluate":"5","nickname":"嘿嘿嘿","content":""},{"head_img":"http://xychead.xueyiche.vip/xyc_1544059025652jpg","content_time":"2019-10-11","all_evaluate":"5","nickname":"疯狂的石头","content":""}]
         * self_estimate : 爱聊天
         * collect_status : 1
         */

        private String hour_price;
        private String comment_count;
        private String jdsj;
        private String driver_id;
        private String type_name;
        private String order_count;
        private String hand_auto;
        private String head_img;
        private String brand_name;
        private String on_off;
        private String qysc;
        private String driver_name;
        private String seriesname;
        private String driving_year;
        private String self_estimate;
        private String collect_status;
        private List<FengcaiBean> fengcai;
        private List<PinglunBean> pinglun;

        public String getHour_price() {
            return hour_price;
        }

        public void setHour_price(String hour_price) {
            this.hour_price = hour_price;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getOrder_count() {
            return order_count;
        }

        public void setOrder_count(String order_count) {
            this.order_count = order_count;
        }

        public String getJdsj() {
            return jdsj;
        }

        public void setJdsj(String jdsj) {
            this.jdsj = jdsj;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }


        public String getHand_auto() {
            return hand_auto;
        }

        public void setHand_auto(String hand_auto) {
            this.hand_auto = hand_auto;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getOn_off() {
            return on_off;
        }

        public void setOn_off(String on_off) {
            this.on_off = on_off;
        }

        public String getQysc() {
            return qysc;
        }

        public void setQysc(String qysc) {
            this.qysc = qysc;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getSeriesname() {
            return seriesname;
        }

        public void setSeriesname(String seriesname) {
            this.seriesname = seriesname;
        }

        public String getDriving_year() {
            return driving_year;
        }

        public void setDriving_year(String driving_year) {
            this.driving_year = driving_year;
        }

        public String getSelf_estimate() {
            return self_estimate;
        }

        public void setSelf_estimate(String self_estimate) {
            this.self_estimate = self_estimate;
        }

        public String getCollect_status() {
            return collect_status;
        }

        public void setCollect_status(String collect_status) {
            this.collect_status = collect_status;
        }

        public List<FengcaiBean> getFengcai() {
            return fengcai;
        }

        public void setFengcai(List<FengcaiBean> fengcai) {
            this.fengcai = fengcai;
        }

        public List<PinglunBean> getPinglun() {
            return pinglun;
        }

        public void setPinglun(List<PinglunBean> pinglun) {
            this.pinglun = pinglun;
        }

        public static class FengcaiBean {
            /**
             * car_url : http://car0.autoimg.cn/upload/2014/10/20/S_20141020231329067497110.jpg
             * id : 1
             */

            private String car_url;
            private int id;

            public String getCar_url() {
                return car_url;
            }

            public void setCar_url(String car_url) {
                this.car_url = car_url;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class PinglunBean {
            /**
             * head_img : http://xychead.xueyiche.vip/xyc_1546850849473jpg
             * content_time : 2019-12-19
             * all_evaluate : 5
             * nickname : 嘿嘿嘿
             * content :
             */

            private String head_img;
            private String content_time;
            private String all_evaluate;
            private String nickname;
            private String content;

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

            public String getAll_evaluate() {
                return all_evaluate;
            }

            public void setAll_evaluate(String all_evaluate) {
                this.all_evaluate = all_evaluate;
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
