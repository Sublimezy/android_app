package com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.bean;

import java.util.List;

/**
 * Created by ZL on 2018/11/19.
 */
public class WZDriverDetailsBean {
    /**
     * content : {"comment_count":99,"driver_id":"xxx","order_count":"99人","head_img":"http://oub7evnni.bkt.clouddn.com/20171020103436.jpg","on_off":"等待接单","driver_name":"王蕾 女士","driving_year":"10年驾龄","pinglun":[{"service_attitude":"5.0","order_number":"E1527130664710","head_img":"http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png","all_evaluate":"5.0","technological_level":"5.0","content_time":"2018-09-04 07:46:51","nickname":"李建周","praise_count":2,"reply_count":1,"content":"这是一段良好的体验"},{"service_attitude":"5.0","order_number":"E1527131304466","head_img":"http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png","all_evaluate":"5.0","technological_level":"5.0","content_time":"2018-09-04 14:44:38","nickname":"王美婷","praise_count":2,"reply_count":0,"content":"这是一段良好的体验"}],"collect_status":"1"}
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
         * comment_count : 99
         * driver_id : xxx
         * order_count : 99人
         * head_img : http://oub7evnni.bkt.clouddn.com/20171020103436.jpg
         * on_off : 等待接单
         * driver_name : 王蕾 女士
         * driving_year : 10年驾龄
         * pinglun : [{"service_attitude":"5.0","order_number":"E1527130664710","head_img":"http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png","all_evaluate":"5.0","technological_level":"5.0","content_time":"2018-09-04 07:46:51","nickname":"李建周","praise_count":2,"reply_count":1,"content":"这是一段良好的体验"},{"service_attitude":"5.0","order_number":"E1527131304466","head_img":"http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png","all_evaluate":"5.0","technological_level":"5.0","content_time":"2018-09-04 14:44:38","nickname":"王美婷","praise_count":2,"reply_count":0,"content":"这是一段良好的体验"}]
         * collect_status : 1
         */

        private int comment_count;
        private String driver_id;
        private String order_count;
        private String head_img;
        private String on_off;
        private String driver_name;
        private String driving_year;
        private String collect_status;
        private List<PinglunBean> pinglun;

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getOrder_count() {
            return order_count;
        }

        public void setOrder_count(String order_count) {
            this.order_count = order_count;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getOn_off() {
            return on_off;
        }

        public void setOn_off(String on_off) {
            this.on_off = on_off;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getDriving_year() {
            return driving_year;
        }

        public void setDriving_year(String driving_year) {
            this.driving_year = driving_year;
        }

        public String getCollect_status() {
            return collect_status;
        }

        public void setCollect_status(String collect_status) {
            this.collect_status = collect_status;
        }

        public List<PinglunBean> getPinglun() {
            return pinglun;
        }

        public void setPinglun(List<PinglunBean> pinglun) {
            this.pinglun = pinglun;
        }

        public static class PinglunBean {
            /**
             * service_attitude : 5.0
             * order_number : E1527130664710
             * head_img : http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png
             * all_evaluate : 5.0
             * technological_level : 5.0
             * content_time : 2018-09-04 07:46:51
             * nickname : 李建周
             * praise_count : 2
             * reply_count : 1
             * content : 这是一段良好的体验
             */

            private String service_attitude;
            private String order_number;
            private String head_img;
            private String all_evaluate;
            private String technological_level;
            private String content_time;
            private String nickname;
            private int praise_count;
            private int reply_count;
            private String content;

            public String getService_attitude() {
                return service_attitude;
            }

            public void setService_attitude(String service_attitude) {
                this.service_attitude = service_attitude;
            }

            public String getOrder_number() {
                return order_number;
            }

            public void setOrder_number(String order_number) {
                this.order_number = order_number;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getAll_evaluate() {
                return all_evaluate;
            }

            public void setAll_evaluate(String all_evaluate) {
                this.all_evaluate = all_evaluate;
            }

            public String getTechnological_level() {
                return technological_level;
            }

            public void setTechnological_level(String technological_level) {
                this.technological_level = technological_level;
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

            public int getPraise_count() {
                return praise_count;
            }

            public void setPraise_count(int praise_count) {
                this.praise_count = praise_count;
            }

            public int getReply_count() {
                return reply_count;
            }

            public void setReply_count(int reply_count) {
                this.reply_count = reply_count;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }

    /**
     * content : {"comment_count":99,"driver_id":"xxx","order_count":"99人","head_img":"http://oub7evnni.bkt.clouddn.com/20171020103436.jpg","on_off":"等待接单","driver_name":"王蕾 女士","driving_year":"10年驾龄","pinglun":[{"head_img":"http://otqopw3sl.bkt.clouddn.com/20171020105245","content_time":"1天前","nickname":"ph","content":"dd"},{"head_img":"http://otqopw3sl.bkt.clouddn.com/20171020105245","content_time":"1天前","nickname":"ph","content":"tes"}],"collect_status":"1"}
     * code : 200
     * msg : 操作成功
     */

  
}
