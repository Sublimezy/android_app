package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * Created by ZL on 2019/3/4.
 */
public class KuaiBaoListBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"comment_count":0,"image":"http://images.xueyiche.vip/4d6c6eab456c4ef9980107fc7d256e47.jpeg","system_time":"2019-02-27 14:33:10","nickname":"学易车","praise_count":1,"id":50,"title":"2019驾考又变难了？技巧总结拿去，稳过！","article_url":"http://images.xueyiche.vip/FhlOt18AHh7WzvyY0t5Inm-Hp7yx"},{"comment_count":0,"image":"http://images.xueyiche.vip/ab2d5a58a1874ac09dcaa3a20271a0a9.jpeg","system_time":"2019-02-27 13:56:13","nickname":"学易车","praise_count":3,"id":49,"title":"科目三成了驾考难题？学会老教练的这四招，考试简单了！","article_url":"http://images.xueyiche.vip/FsFtdYWUMfs9l0p06g2MNfnRblC6"},{"comment_count":0,"image":"http://images.xueyiche.vip/b40044ceb1424bca9d6e1088832413a9.jpeg","system_time":"2019-02-27 13:53:48","nickname":"学易车","praise_count":0,"id":48,"title":"能一次性通过驾考的，都有这3个特征","article_url":"http://images.xueyiche.vip/FqaraS2H3kxvz8P5RqS6YD54-I6X"}]
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
         * image : http://images.xueyiche.vip/4d6c6eab456c4ef9980107fc7d256e47.jpeg
         * system_time : 2019-02-27 14:33:10
         * nickname : 学易车
         * praise_count : 1
         * id : 50
         * title : 2019驾考又变难了？技巧总结拿去，稳过！
         * article_url : http://images.xueyiche.vip/FhlOt18AHh7WzvyY0t5Inm-Hp7yx
         */

        private int comment_count;
        private String image;
        private String system_time;
        private String nickname;
        private int praise_count;
        private int id;
        private String title;
        private String article_url;

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSystem_time() {
            return system_time;
        }

        public void setSystem_time(String system_time) {
            this.system_time = system_time;
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

        public String getArticle_url() {
            return article_url;
        }

        public void setArticle_url(String article_url) {
            this.article_url = article_url;
        }
    }
}
