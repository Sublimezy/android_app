package com.xueyiche.zjyk.xueyiche.driverschool.bean;

import java.util.List;

/**
 * Created by ZL on 2018/10/24.
 */
public class JiaoLianPJListBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"reply_list":[{"reply_time":"1分钟前","head_img":"http://otqopw3sl.bkt.clouddn.com/xyc_1504143406696jpg","nickname":"嘿嘿嘿","reply":"测试回复一i套"}],"service_attitude":"5.0","order_number":"E1527131304466","head_img":"http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png","all_evaluate":"5.0","technological_level":"5.0","content_time":"2018-09-04 14:44:38","nickname":"王美婷","reply_count":1,"content":"这是一段良好的体验"}
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
         * reply_list : [{"reply_time":"1分钟前","head_img":"http://otqopw3sl.bkt.clouddn.com/xyc_1504143406696jpg","nickname":"嘿嘿嘿","reply":"测试回复一i套"}]
         * service_attitude : 5.0
         * order_number : E1527131304466
         * head_img : http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png
         * all_evaluate : 5.0
         * technological_level : 5.0
         * content_time : 2018-09-04 14:44:38
         * nickname : 王美婷
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
        private String content;
        private String praise_count;
        private String reply_count;
        private String if_praise;
        private List<ReplyListBean> reply_list;

        public String getPraise_count() {
            return praise_count;
        }

        public String getReply_count() {
            return reply_count;
        }

        public void setPraise_count(String praise_count) {
            this.praise_count = praise_count;
        }

        public void setReply_count(String reply_count) {
            this.reply_count = reply_count;
        }

        public String getIf_praise() {
            return if_praise;
        }

        public void setIf_praise(String if_praise) {
            this.if_praise = if_praise;
        }

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


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ReplyListBean> getReply_list() {
            return reply_list;
        }

        public void setReply_list(List<ReplyListBean> reply_list) {
            this.reply_list = reply_list;
        }

        public static class ReplyListBean {
            /**
             * reply_time : 1分钟前
             * head_img : http://otqopw3sl.bkt.clouddn.com/xyc_1504143406696jpg
             * nickname : 嘿嘿嘿
             * reply : 测试回复一i套
             */

            private String reply_time;
            private String head_img;
            private String nickname;
            private String reply;
            private String reply_praise_count;
            private String if_praise;
            private String eva_detail_id;

            public String getReply_praise_count() {
                return reply_praise_count;
            }

            public void setReply_praise_count(String reply_praise_count) {
                this.reply_praise_count = reply_praise_count;
            }

            public String getIf_praise() {
                return if_praise;
            }

            public void setIf_praise(String if_praise) {
                this.if_praise = if_praise;
            }

            public String getEva_detail_id() {
                return eva_detail_id;
            }

            public void setEva_detail_id(String eva_detail_id) {
                this.eva_detail_id = eva_detail_id;
            }

            public String getReply_time() {
                return reply_time;
            }

            public void setReply_time(String reply_time) {
                this.reply_time = reply_time;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }
        }
    }
}
