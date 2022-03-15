package com.xueyiche.zjyk.xueyiche.driverschool.bean;

import java.util.List;

/**
 * Created by ZL on 2018/7/20.
 */
public class RemarkTrainerListBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"driver_name":"刘士杰","driver_id":"0006a2fd369948d6b98911c93e9537ca","head_img":"http://images.xueyiche.vip/20180725155918329.jpg","sex":"女","evaluate":[{"service_attitude":"5","head_img":"http://otqopw3sl.bkt.clouddn.com/20170921142315","all_evaluate":"5","technological_level":"5","nickname":"王庆磊"}],"age":"52岁"}
     */

    private String msg;
    private int code;
    /**
     * driver_name : 刘士杰
     * driver_id : 0006a2fd369948d6b98911c93e9537ca
     * head_img : http://images.xueyiche.vip/20180725155918329.jpg
     * sex : 女
     * evaluate : [{"service_attitude":"5","head_img":"http://otqopw3sl.bkt.clouddn.com/20170921142315","all_evaluate":"5","technological_level":"5","nickname":"王庆磊"}]
     * age : 52岁
     */

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
        private String driver_name;
        private String driver_id;
        private String head_img;
        private String sex;
        private String driver_school_name;
        private String car_type;
        private String age;

        public String getDriver_school_name() {
            return driver_school_name;
        }

        public void setDriver_school_name(String driver_school_name) {
            this.driver_school_name = driver_school_name;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        /**
         * service_attitude : 5
         * head_img : http://otqopw3sl.bkt.clouddn.com/20170921142315
         * all_evaluate : 5
         * technological_level : 5
         * nickname : 王庆磊
         */

        private List<EvaluateBean> evaluate;

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public List<EvaluateBean> getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(List<EvaluateBean> evaluate) {
            this.evaluate = evaluate;
        }

        public static class EvaluateBean {
            private String service_attitude;
            private String head_img;
            private String all_evaluate;
            private String technological_level;
            private String nickname;
            private String reply;
            private String content;
            private String order_number;
            private String reply_time;
            private String reply_count;
            private String praise_count;

            public String getReply_count() {
                return reply_count;
            }

            public void setReply_count(String reply_count) {
                this.reply_count = reply_count;
            }

            public String getPraise_count() {
                return praise_count;
            }

            public void setPraise_count(String praise_count) {
                this.praise_count = praise_count;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getOrder_number() {
                return order_number;
            }

            public void setOrder_number(String order_number) {
                this.order_number = order_number;
            }

            public String getReply_time() {
                return reply_time;
            }

            public void setReply_time(String reply_time) {
                this.reply_time = reply_time;
            }

            public String getContent_time() {
                return content_time;
            }

            public void setContent_time(String content_time) {
                this.content_time = content_time;
            }

            private String content_time;

            public String getService_attitude() {
                return service_attitude;
            }

            public void setService_attitude(String service_attitude) {
                this.service_attitude = service_attitude;
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

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
