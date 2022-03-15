package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

import java.util.List;

/**
 * Created by ZL on 2018/4/23.
 */
public class KeFuListBean {

    /**
     * content : [{"id":1,"quest_name":"学易车和其他学车软件有什么区别？"},{"id":2,"quest_name":"续费报名后，还会收取其他费用吗？"},{"id":3,"quest_name":"学易车的驾考流程是什么？"},{"id":4,"quest_name":"在线报名后，会有人主动联系我练车吗？"}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
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
        /**
         * id : 1
         * quest_name : 学易车和其他学车软件有什么区别？
         */

        private int id;
        private String quest_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuest_name() {
            return quest_name;
        }

        public void setQuest_name(String quest_name) {
            this.quest_name = quest_name;
        }
    }
}
