package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * Created by ZL on 2017/1/3.
 */
public class DaTiWinBean {

    /**
     * content : [{"system_time":"2017-01-03 10:43:14","time_used":"212","user_phone":"18945057021","score":"21","bonus":"100","ranking":"31"},{"system_time":"2017-01-03 14:20:46","time_used":"2696388","user_phone":"18346012117","score":"10","bonus":"50"},{"system_time":"2017-01-03 14:21:54","time_used":"2698146","user_phone":"18346012117","score":"19","bonus":"50"}]
     * code : 0
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
         * system_time : 2017-01-03 10:43:14
         * time_used : 212
         * user_phone : 18945057021
         * score : 21
         * bonus : 100
         * ranking : 31
         */

        private String system_time;
        private String time_used;
        private String user_phone;
        private String score;
        private String bonus;
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

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }
    }
}
