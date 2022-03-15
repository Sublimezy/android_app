package com.xueyiche.zjyk.xueyiche.discover.bean;

import java.util.List;

/**
 * Created by Owner on 2018/3/28.
 */
public class WenDaContentBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"comment_count":1,"image3":"","head_img":"http://xychead.xueyiche.vip/20200214185631","main_praise":"1","title":"在哪里报考比较好","image1":"http://xychead.xueyiche.vip/20200214200640","image2":"","collect_count":0,"share_count":0,"quest_nickname":"安安","pinglun":[{"comment_time":"2天前","if_praise":"1","comment_praise_count":1,"head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","comment_reply_count":0,"comment":"v过","comment_nickname":"嘿嘿嘿","comment_id":14}],"praise_count":1,"main_collect":"0","represent":"吃拿卡要严重的地方我怎么办","release_time":"2020-02-14"}
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
         * comment_count : 1
         * image3 :
         * head_img : http://xychead.xueyiche.vip/20200214185631
         * main_praise : 1
         * title : 在哪里报考比较好
         * image1 : http://xychead.xueyiche.vip/20200214200640
         * image2 :
         * collect_count : 0
         * share_count : 0
         * quest_nickname : 安安
         * pinglun : [{"comment_time":"2天前","if_praise":"1","comment_praise_count":1,"head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","comment_reply_count":0,"comment":"v过","comment_nickname":"嘿嘿嘿","comment_id":14}]
         * praise_count : 1
         * main_collect : 0
         * represent : 吃拿卡要严重的地方我怎么办
         * release_time : 2020-02-14
         */

        private String comment_count;
        private String image3;
        private String head_img;
        private String main_praise;
        private String title;
        private String image1;
        private String image2;
        private String collect_count;
        private String share_count;
        private String quest_nickname;
        private String praise_count;
        private String main_collect;
        private String represent;
        private String release_time;
        private List<PinglunBean> pinglun;

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getImage3() {
            return image3;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getMain_praise() {
            return main_praise;
        }

        public void setMain_praise(String main_praise) {
            this.main_praise = main_praise;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(String collect_count) {
            this.collect_count = collect_count;
        }

        public String getShare_count() {
            return share_count;
        }

        public void setShare_count(String share_count) {
            this.share_count = share_count;
        }

        public String getQuest_nickname() {
            return quest_nickname;
        }

        public void setQuest_nickname(String quest_nickname) {
            this.quest_nickname = quest_nickname;
        }

        public String getPraise_count() {
            return praise_count;
        }

        public void setPraise_count(String praise_count) {
            this.praise_count = praise_count;
        }

        public String getMain_collect() {
            return main_collect;
        }

        public void setMain_collect(String main_collect) {
            this.main_collect = main_collect;
        }

        public String getRepresent() {
            return represent;
        }

        public void setRepresent(String represent) {
            this.represent = represent;
        }

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public List<PinglunBean> getPinglun() {
            return pinglun;
        }

        public void setPinglun(List<PinglunBean> pinglun) {
            this.pinglun = pinglun;
        }

        public static class PinglunBean {
            /**
             * comment_time : 2天前
             * if_praise : 1
             * comment_praise_count : 1
             * head_img : http://xychead.xueyiche.vip/xyc_1546850849473jpg
             * comment_reply_count : 0
             * comment : v过
             * comment_nickname : 嘿嘿嘿
             * comment_id : 14
             */

            private String comment_time;
            private String if_praise;
            private int comment_praise_count;
            private String head_img;
            private int comment_reply_count;
            private String comment;
            private String comment_nickname;
            private int comment_id;

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
            }

            public String getIf_praise() {
                return if_praise;
            }

            public void setIf_praise(String if_praise) {
                this.if_praise = if_praise;
            }

            public int getComment_praise_count() {
                return comment_praise_count;
            }

            public void setComment_praise_count(int comment_praise_count) {
                this.comment_praise_count = comment_praise_count;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public int getComment_reply_count() {
                return comment_reply_count;
            }

            public void setComment_reply_count(int comment_reply_count) {
                this.comment_reply_count = comment_reply_count;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getComment_nickname() {
                return comment_nickname;
            }

            public void setComment_nickname(String comment_nickname) {
                this.comment_nickname = comment_nickname;
            }

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }
        }
    }
}
