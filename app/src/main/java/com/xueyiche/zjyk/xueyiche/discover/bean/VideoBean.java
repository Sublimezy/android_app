package com.xueyiche.zjyk.xueyiche.discover.bean;

import java.util.List;

/**
 * Created by ZL on 2018/4/2.
 */
public class VideoBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"comment_count":2,"screenshot_video_url":"http://images.xueyiche.vip/49ff927da8764fbcbf2aa03.mp4","W":"300","H":"500","praise_count":1,"screenshot_image_url":"http://images.xueyiche.vip/262b3df42b124430b9ad.png","is_praise":"0","title":"倒库，还是倒库，感觉倒库目之一","video_id":16},{"comment_count":1,"screenshot_video_url":"http://images.xueyiche.vip/d0f84913a3564851a85b613226da8d00.mp4","W":"300","H":"500","praise_count":5,"screenshot_image_url":"http://images.xueyiche.vip/49d119d6e0c049a7bbd00cb32de66314.png","is_praise":"0","title":"驾考紧张怎么办？","video_id":18},{"comment_count":1,"screenshot_video_url":"http://images.xueyiche.vip/0147ea91b979419b9679df66e9659776.mp4","W":"300","H":"500","praise_count":5,"screenshot_image_url":"http://images.xueyiche.vip/e8657995137f4b6d997d283338ca3f97.png","is_praise":"0","title":"虽通过了\u201c驾考\u201d，可你敢上路吗？老梁为你揭秘新手上路的问题！","video_id":14},{"comment_count":0,"screenshot_video_url":"http://images.xueyiche.vip/46b326d837bd44c9bd09ee06ac932904.mp4","W":"300","H":"500","praise_count":1,"screenshot_image_url":"http://images.xueyiche.vip/1d613fb1a4574d13b2e21971c788b37a.jpg","is_praise":"0","title":"驾考科目三：直线行驶真的这么难？老教练教您一次性过关","video_id":22},{"comment_count":0,"screenshot_video_url":"http://images.xueyiche.vip/c537da0a3c6e41afa19f7a243a88ca9c.mp4","W":"300","H":"500","praise_count":2,"screenshot_image_url":"http://images.xueyiche.vip/8e846f39b4fa4e16b98ebfc9bf3b56f9.jpg","is_praise":"0","title":"买车的朋友注意了：这三种费用千万别交","video_id":20},{"comment_count":0,"screenshot_video_url":"http://images.xueyiche.vip/29be98831ae640b19178b6e8402c5f61.mp4","W":"300","H":"500","praise_count":0,"screenshot_image_url":"http://images.xueyiche.vip/fcf152e3896e4f41a2a9d6187b78cdfe.jpg","is_praise":"0","title":"还完车贷一定要记得做这三件事，否则车还不属于你","video_id":21},{"comment_count":0,"screenshot_video_url":"http://images.xueyiche.vip/7dcbfab9414e48c197c710cc68c700ac.mp4","W":"300","H":"500","praise_count":0,"screenshot_image_url":"http://images.xueyiche.vip/27b7cb37396f49449e5ffa3f2c5b745c.jpg","is_praise":"0","title":"还完车贷一定要记得做这三件事，否则车还不属于你","video_id":19},{"comment_count":0,"screenshot_video_url":"http://images.xueyiche.vip/77d5c7153da7469e81727131718bc120.mp4","W":"300","H":"500","praise_count":2,"screenshot_image_url":"http://images.xueyiche.vip/7fdea08ad14447e6b6caafae54ac8441.png","is_praise":"0","title":"试驾东风日产LANNIA_蓝鸟","video_id":12},{"comment_count":0,"screenshot_video_url":"http://images.xueyiche.vip/361b8952b54d4dc3aa9b6f3bb7fe04aa.mp4","W":"300","H":"500","praise_count":1,"screenshot_image_url":"http://images.xueyiche.vip/1c40fe9f94474b4989dcddcd3b26bed7.png","is_praise":"0","title":"遇到车祸，竟然撞上了自己梦中情人","video_id":11},{"comment_count":0,"screenshot_video_url":"http://images.xueyiche.vip/d37ff90ebd6f49f388954e2ff4b04d93.mp4","W":"300","H":"500","praise_count":1,"screenshot_image_url":"http://images.xueyiche.vip/6803d93f194c4343b70170b64797b9ca.png","is_praise":"0","title":"行车记录仪车祸画面 车祸视频集锦 交通事故视频合集","video_id":10}]
     */

    private String msg;
    private int code;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * comment_count : 2
         * screenshot_video_url : http://images.xueyiche.vip/49ff927da8764fbcbf2aa03.mp4
         * W : 300
         * H : 500
         * praise_count : 1
         * screenshot_image_url : http://images.xueyiche.vip/262b3df42b124430b9ad.png
         * is_praise : 0
         * title : 倒库，还是倒库，感觉倒库目之一
         * video_id : 16
         */

        private int comment_count;
        private String screenshot_video_url;
        private String W;
        private String H;
        private int praise_count;
        private String screenshot_image_url;
        private String is_praise;
        private String title;
        private int video_id;

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getScreenshot_video_url() {
            return screenshot_video_url;
        }

        public void setScreenshot_video_url(String screenshot_video_url) {
            this.screenshot_video_url = screenshot_video_url;
        }

        public String getW() {
            return W;
        }

        public void setW(String W) {
            this.W = W;
        }

        public String getH() {
            return H;
        }

        public void setH(String H) {
            this.H = H;
        }

        public int getPraise_count() {
            return praise_count;
        }

        public void setPraise_count(int praise_count) {
            this.praise_count = praise_count;
        }

        public String getScreenshot_image_url() {
            return screenshot_image_url;
        }

        public void setScreenshot_image_url(String screenshot_image_url) {
            this.screenshot_image_url = screenshot_image_url;
        }

        public String getIs_praise() {
            return is_praise;
        }

        public void setIs_praise(String is_praise) {
            this.is_praise = is_praise;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }
    }
}
