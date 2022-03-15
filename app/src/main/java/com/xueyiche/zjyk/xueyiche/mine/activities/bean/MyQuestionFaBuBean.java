package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

import java.util.List;

/**
 * Created by ZL on 2019/2/22.
 */
public class MyQuestionFaBuBean
{

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"id":7,"title":"学易车考驾照靠谱么？","represent":"驾校的服务怎么样？有没有其他的收费行为？","release_time":"2018-10-11"},{"id":8,"title":"冬季开车","represent":"冬天快来了，自己的爱车需要做些什么准备安稳度过严冬。。","release_time":"2018-10-11"}]
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
         * id : 7
         * title : 学易车考驾照靠谱么？
         * represent : 驾校的服务怎么样？有没有其他的收费行为？
         * release_time : 2018-10-11
         */

        private int id;
        private int comment_id;
        private String title;
        private String represent;
        private String release_time;
        private String comment_time;
        private String comment;
        private String image1;
        private String image2;
        private String image3;

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

        public String getImage3() {
            return image3;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
    }
}
