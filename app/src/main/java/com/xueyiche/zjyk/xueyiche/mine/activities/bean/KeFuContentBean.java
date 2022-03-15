package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

/**
 * Created by ZL on 2018/4/23.
 */
public class KeFuContentBean {

    /**
     * content : {"quest_detail":"有区别","quest_name":"学易车和其他学车软件有什么区别？"}
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
         * quest_detail : 有区别
         * quest_name : 学易车和其他学车软件有什么区别？
         */

        private String quest_detail;
        private String quest_name;

        public String getQuest_detail() {
            return quest_detail;
        }

        public void setQuest_detail(String quest_detail) {
            this.quest_detail = quest_detail;
        }

        public String getQuest_name() {
            return quest_name;
        }

        public void setQuest_name(String quest_name) {
            this.quest_name = quest_name;
        }
    }
}
