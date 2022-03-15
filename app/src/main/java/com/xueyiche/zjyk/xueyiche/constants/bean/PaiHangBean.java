package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.util.List;

/**
 * Created by Owner on 2017/1/12.
 */
public class PaiHangBean {


    /**
     * content : [{"system_time":"2017-01-11 12:44:46","time_used":"5323","user_phone":"18600752610","score":"55","ranking":"1"},{"system_time":"2017-01-12 12:44:22","time_used":"5687","user_phone":"18600752610","score":"22","ranking":"1"}]
     * code : 0
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * system_time : 2017-01-11 12:44:46
     * time_used : 5323
     * user_phone : 18600752610
     * score : 55
     * ranking : 1
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
        private String system_time;
        private String time_used;
        private String user_phone;
        private String score;
        private String ranking;

        public String getSystem_time() {
            return system_time;
        }

        public void setSystem_time(String system_time) {
            this.system_time = system_time;
        }

        public String getTime_used() {
            return time_used;
        }

        public void setTime_used(String time_used) {
            this.time_used = time_used;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }
    }
}
