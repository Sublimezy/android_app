package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

import java.util.List;

/**
 * Created by Owner on 2017/9/18.
 */
public class DaiLiListBean {

    /**
     * content : [{"agent_time":"2017-09-15 19:22:00","agent_user_phone":"66627d606d76626e607276"},{"agent_time":"2017-09-15 19:22:01","agent_user_phone":"66627e6f6a76636e6a7574"}]
     * code : 0
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * agent_time : 2017-09-15 19:22:00
     * agent_user_phone : 66627d606d76626e607276
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
        private String agent_time;
        private String agent_user_phone;

        public String getAgent_time() {
            return agent_time;
        }

        public void setAgent_time(String agent_time) {
            this.agent_time = agent_time;
        }

        public String getAgent_user_phone() {
            return agent_user_phone;
        }

        public void setAgent_user_phone(String agent_user_phone) {
            this.agent_user_phone = agent_user_phone;
        }
    }
}
