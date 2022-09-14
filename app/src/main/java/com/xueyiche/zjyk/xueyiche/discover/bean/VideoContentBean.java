package com.xueyiche.zjyk.xueyiche.discover.bean;

import java.util.List;

/**
 * Created by ZL on 2018/4/3.
 */
public class VideoContentBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"comment_count":2,"device_id":"13065ffa4e388ea2f87","head_img":"http://xychead.xue1546850849473jpg","screenshot_image_url":"http://images.xueyiche.vip/262b3df42b124889ad.png","main_praise":"0","title":"倒库，还是倒库，感觉倒库是驾考科目之一","collect_count":0,"share_count":0,"screenshot_video_url":"http://images.xueyiche.vip/49ff927da8764fbcbf89a03.mp4","user_id":"271","pinglun":[{"comment_time":"2020-02-10","if_praise":"1","comment_praise_count":1,"head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","nickname":"嘿嘿嘿","comment":"滚滚滚","id":12},{"comment_time":"2020-02-10","if_praise":"1","comment_praise_count":1,"head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","nickname":"嘿嘿嘿","comment":"哈哈哈","id":11}],"nickname":"嘿嘿嘿","praise_count":2,"main_collect":"0"}
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
         * comment_count : 2
         * device_id : 13065ffa4e388ea2f87
         * head_img : http://xychead.xue1546850849473jpg
         * screenshot_image_url : http://images.xueyiche.vip/262b3df42b124889ad.png
         * main_praise : 0
         * title : 倒库，还是倒库，感觉倒库是驾考科目之一
         * collect_count : 0
         * share_count : 0
         * screenshot_video_url : http://images.xueyiche.vip/49ff927da8764fbcbf89a03.mp4
         * user_id : 271
         * pinglun : [{"comment_time":"2020-02-10","if_praise":"1","comment_praise_count":1,"head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","nickname":"嘿嘿嘿","comment":"滚滚滚","id":12},{"comment_time":"2020-02-10","if_praise":"1","comment_praise_count":1,"head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","nickname":"嘿嘿嘿","comment":"哈哈哈","id":11}]
         * nickname : 嘿嘿嘿
         * praise_count : 2
         * main_collect : 0
         */

        private String comment_count;
        private String device_id;
        private String head_img;
        private String screenshot_image_url;
        private String main_praise;
        private String title;
        private String collect_count;
        private String share_count;
        private String screenshot_video_url;
        private String user_id;
        private String nickname;
        private String praise_count;
        private String main_collect;
        private List<PinglunBean> pinglun;

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getScreenshot_image_url() {
            return screenshot_image_url;
        }

        public void setScreenshot_image_url(String screenshot_image_url) {
            this.screenshot_image_url = screenshot_image_url;
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

        public String getScreenshot_video_url() {
            return screenshot_video_url;
        }

        public void setScreenshot_video_url(String screenshot_video_url) {
            this.screenshot_video_url = screenshot_video_url;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public List<PinglunBean> getPinglun() {
            return pinglun;
        }

        public void setPinglun(List<PinglunBean> pinglun) {
            this.pinglun = pinglun;
        }

        public static class PinglunBean {
            /**
             * comment_time : 2020-02-10
             * if_praise : 1
             * comment_praise_count : 1
             * head_img : http://xychead.xueyiche.vip/xyc_1546850849473jpg
             * nickname : 嘿嘿嘿
             * comment : 滚滚滚
             * id : 12
             */

            private String comment_time;
            private String if_praise;
            private String comment_praise_count;
            private String head_img;
            private String nickname;
            private String comment;
            private int id;

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

            public String getComment_praise_count() {
                return comment_praise_count;
            }

            public void setComment_praise_count(String comment_praise_count) {
                this.comment_praise_count = comment_praise_count;
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

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
