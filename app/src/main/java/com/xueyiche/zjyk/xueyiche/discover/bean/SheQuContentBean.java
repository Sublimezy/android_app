package com.xueyiche.zjyk.xueyiche.discover.bean;

import java.util.List;

/**
 * Created by Owner on 2018/3/27.
 */
public class SheQuContentBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"correlationComment":[{"comment_time":"2018-10-11","if_praise":"0","comment_praise_count":3,"head_img":"http://xychead.xueyiche.vip/xyc_1544059025652jpg","nickname":"疯狂的石头","comment":"很受用","article_comment_id":2}],"article":{"comment_count":1,"share_count":0,"praise_count":6,"main_praise":"0","article_url":"http://images.xueyiche.vip/Fp4fWrkwAYwYWOk_tabtEkaGM3W2","main_collect":"0","collect_count":0}}
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
         * correlationComment : [{"comment_time":"2018-10-11","if_praise":"0","comment_praise_count":3,"head_img":"http://xychead.xueyiche.vip/xyc_1544059025652jpg","nickname":"疯狂的石头","comment":"很受用","article_comment_id":2}]
         * article : {"comment_count":1,"share_count":0,"praise_count":6,"main_praise":"0","article_url":"http://images.xueyiche.vip/Fp4fWrkwAYwYWOk_tabtEkaGM3W2","main_collect":"0","collect_count":0}
         */

        private ArticleBean article;
        private List<CorrelationCommentBean> correlationComment;

        public ArticleBean getArticle() {
            return article;
        }

        public void setArticle(ArticleBean article) {
            this.article = article;
        }

        public List<CorrelationCommentBean> getCorrelationComment() {
            return correlationComment;
        }

        public void setCorrelationComment(List<CorrelationCommentBean> correlationComment) {
            this.correlationComment = correlationComment;
        }

        public static class ArticleBean {
            /**
             * comment_count : 1
             * share_count : 0
             * praise_count : 6
             * main_praise : 0
             * article_url : http://images.xueyiche.vip/Fp4fWrkwAYwYWOk_tabtEkaGM3W2
             * main_collect : 0
             * collect_count : 0
             */

            private String comment_count;
            private String share_count;
            private String praise_count;
            private String main_praise;
            private String article_url;
            private String main_collect;
            private String collect_count;

            public String getComment_count() {
                return comment_count;
            }

            public void setComment_count(String comment_count) {
                this.comment_count = comment_count;
            }

            public String getShare_count() {
                return share_count;
            }

            public void setShare_count(String share_count) {
                this.share_count = share_count;
            }

            public String getPraise_count() {
                return praise_count;
            }

            public void setPraise_count(String praise_count) {
                this.praise_count = praise_count;
            }

            public String getMain_praise() {
                return main_praise;
            }

            public void setMain_praise(String main_praise) {
                this.main_praise = main_praise;
            }

            public String getArticle_url() {
                return article_url;
            }

            public void setArticle_url(String article_url) {
                this.article_url = article_url;
            }

            public String getMain_collect() {
                return main_collect;
            }

            public void setMain_collect(String main_collect) {
                this.main_collect = main_collect;
            }

            public String getCollect_count() {
                return collect_count;
            }

            public void setCollect_count(String collect_count) {
                this.collect_count = collect_count;
            }
        }

        public static class CorrelationCommentBean {
            /**
             * comment_time : 2018-10-11
             * if_praise : 0
             * comment_praise_count : 3
             * head_img : http://xychead.xueyiche.vip/xyc_1544059025652jpg
             * nickname : 疯狂的石头
             * comment : 很受用
             * article_comment_id : 2
             */

            private String comment_time;
            private String if_praise;
            private String comment_praise_count;
            private String head_img;
            private String nickname;
            private String comment;
            private String article_comment_id;

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

            public String getArticle_comment_id() {
                return article_comment_id;
            }

            public void setArticle_comment_id(String article_comment_id) {
                this.article_comment_id = article_comment_id;
            }
        }
    }
}
