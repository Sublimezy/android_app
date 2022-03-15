package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

import java.util.List;

/**
 * Created by Owner on 2018/5/24.
 */
public class ZaixianKefuListBean {

    /**
     * content : [{"head_img":"http://otqopw3sl.bkt.clouddn.com/xyc_1504143406696jpg","nickname":"嘿嘿嘿","id":4,"msg_text":"用户发消息给客服1","push_time":"2018-05-24 10:00:00"},{"head_img":"http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png","nickname":"学易车","id":5,"msg_text":"客服回复用户消息1","push_time":"2018-05-24 10:00:18"},{"head_img":"http://otqopw3sl.bkt.clouddn.com/xyc_1504143406696jpg","nickname":"嘿嘿嘿","id":6,"msg_text":"谢谢客服回复2","push_time":"2018-05-24 10:01:25"},{"head_img":"http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png","nickname":"学易车","id":7,"msg_text":"客服回复不用客气2","push_time":"2018-05-24 10:18:24"}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * head_img : http://otqopw3sl.bkt.clouddn.com/xyc_1504143406696jpg
     * nickname : 嘿嘿嘿
     * id : 4
     * msg_text : 用户发消息给客服1
     * push_time : 2018-05-24 10:00:00
     */

    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String head_img;
        private String nickname;
        private int id;
        private String msg_text;
        private String push_time;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsg_text() {
            return msg_text;
        }

        public void setMsg_text(String msg_text) {
            this.msg_text = msg_text;
        }

        public String getPush_time() {
            return push_time;
        }

        public void setPush_time(String push_time) {
            this.push_time = push_time;
        }
    }
}
