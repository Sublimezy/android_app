package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean
 * @ClassName: TrainingTimeCoachBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/1/28 17:49
 */
public class TrainingTimeCoachBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"time_interval":"0","time_period":"00:00-01:00"},{"time_interval":"1","time_period":"00:10-02:00"},{"time_interval":"23","time_period":"00:23-00:00"}]
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
         * time_interval : 0
         * time_period : 00:00-01:00
         */

        private String time_interval;
        private String time_period;

        public String getTime_interval() {
            return time_interval;
        }

        public void setTime_interval(String time_interval) {
            this.time_interval = time_interval;
        }

        public String getTime_period() {
            return time_period;
        }

        public void setTime_period(String time_period) {
            this.time_period = time_period;
        }
    }
}
