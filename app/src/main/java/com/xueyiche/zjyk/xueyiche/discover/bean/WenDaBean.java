package com.xueyiche.zjyk.xueyiche.discover.bean;

import java.util.List;

/**
 * Created by Owner on 2018/3/28.
 */
public class WenDaBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"comment_count":0,"image3":"","head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","solve":"0","nickname":"嘿嘿嘿","praise_count":1,"is_praise":"0","id":4,"title":"选择两厢车与三厢车？","image1":"","image2":"","release_time":"2天前"},{"comment_count":0,"image3":"","head_img":"http://xychead.xueyiche.vip/xyc_1544059025652jpg","solve":"0","nickname":"疯狂的石头","praise_count":0,"is_praise":"0","id":8,"title":"冬季开车","image1":"","image2":"","release_time":"2018-10-11"},{"comment_count":1,"image3":"","head_img":"http://xychead.xueyiche.vip/xyc_1544059025652jpg","solve":"0","nickname":"疯狂的石头","praise_count":0,"is_praise":"0","id":7,"title":"学易车考驾照靠谱么？","image1":"","image2":"","release_time":"2018-10-11"},{"comment_count":0,"image3":"","head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","solve":"0","nickname":"嘿嘿嘿","praise_count":1,"is_praise":"1","id":6,"title":"遇到交通事故时应该怎么拍照片取证?","image1":"","image2":"","release_time":"2018-09-30"},{"comment_count":1,"image3":"","head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","solve":"0","nickname":"嘿嘿嘿","praise_count":0,"is_praise":"0","id":5,"title":"觉得原厂的轮胎过窄，抓地力不好，想要轮一款宽的轮胎，可以吗？","image1":"","image2":"","release_time":"2018-09-30"},{"comment_count":4,"image3":"","head_img":"http://xychead.xueyiche.vip/xyc_1546850849473jpg","solve":"0","nickname":"嘿嘿嘿","praise_count":0,"is_praise":"0","id":3,"title":"汽车排量大好还是小好","image1":"","image2":"","release_time":"2018-09-30"},{"comment_count":4,"image3":"","head_img":null,"solve":"0","nickname":null,"praise_count":5,"is_praise":"0","id":2,"title":"现在报名多少钱？","image1":"","image2":"","release_time":"2018-09-25"},{"comment_count":0,"image3":"","head_img":null,"solve":"0","nickname":null,"praise_count":18,"is_praise":"0","id":1,"title":"学易车的一站式拿证服务是什么意思？","image1":"","image2":"","release_time":"2018-09-25"}]
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
         * comment_count : 0
         * image3 :
         * head_img : http://xychead.xueyiche.vip/xyc_1546850849473jpg
         * solve : 0
         * nickname : 嘿嘿嘿
         * praise_count : 1
         * is_praise : 0
         * id : 4
         * title : 选择两厢车与三厢车？
         * image1 :
         * image2 :
         * release_time : 2天前
         */

        private int comment_count;
        private String image3;
        private String head_img;
        private String solve;
        private String nickname;
        private int praise_count;
        private String is_praise;
        private int id;
        private String title;
        private String image1;
        private String image2;
        private String release_time;

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
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

        public String getSolve() {
            return solve;
        }

        public void setSolve(String solve) {
            this.solve = solve;
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

        public String getIs_praise() {
            return is_praise;
        }

        public void setIs_praise(String is_praise) {
            this.is_praise = is_praise;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }
    }
}
