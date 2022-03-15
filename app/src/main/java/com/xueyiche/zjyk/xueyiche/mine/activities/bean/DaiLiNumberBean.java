package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

/**
 * Created by Owner on 2017/9/18.
 */
public class DaiLiNumberBean {

    /**
     * agent_shop_number : 045100101
     * agent_shop_count : 0
     */

    private ContentBean content;
    /**
     * content : {"agent_shop_number":"045100101","agent_shop_count":"0"}
     * code : 0
     * msg : 操作成功
     */

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
        private String agent_shop_number;
        private String agent_shop_count;

        public String getAgent_shop_number() {
            return agent_shop_number;
        }

        public void setAgent_shop_number(String agent_shop_number) {
            this.agent_shop_number = agent_shop_number;
        }

        public String getAgent_shop_count() {
            return agent_shop_count;
        }

        public void setAgent_shop_count(String agent_shop_count) {
            this.agent_shop_count = agent_shop_count;
        }
    }
}
