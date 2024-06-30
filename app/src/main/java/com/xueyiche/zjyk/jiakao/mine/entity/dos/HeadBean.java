package com.xueyiche.zjyk.jiakao.mine.entity.dos;

public class HeadBean {

    /**
     * content : {"user_id":"29168db2b3b211e7aeb85254f2dc841f","head_img":"http://otqopw3sl.bkt.clouddn.com/20171018111358","nickname":"kam"}
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
         * user_id : 29168db2b3b211e7aeb85254f2dc841f
         * head_img : http://otqopw3sl.bkt.clouddn.com/20171018111358
         * nickname : kam
         */

        private String user_id;
        private String head_img;
        private String nickname;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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
    }
}
