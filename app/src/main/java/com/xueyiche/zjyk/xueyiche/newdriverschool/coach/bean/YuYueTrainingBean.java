package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean
 * @ClassName: YuYueTrainingBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/2/2 7:06
 */
public class YuYueTrainingBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"num_class":"共2学时","training_id":1,"entry_project":"科目二","sys_time":"2021-01-31 18:05:43","stu_name":"方文山","evaluation_type":"0","training_type":"0","driving_practice":"驾驶练习","trainee_phone":"18978888766","selected_period":"06:00,07:00","practice_time":"2021-02-01 星期二"},{"num_class":"","training_id":2,"entry_project":"科目一","sys_time":"2021-02-01 10:07:27","stu_name":"方文山","evaluation_type":"0","training_type":"0","driving_practice":"驾驶练习","trainee_phone":"18978888766","selected_period":"","practice_time":""},{"num_class":"共2学时","training_id":3,"entry_project":"科目二","sys_time":"2021-02-01 19:18:00","stu_name":"黄俊郎","evaluation_type":"0","training_type":"0","driving_practice":"驾驶练习","trainee_phone":"18978888766","selected_period":"08:00,09:00","practice_time":"2021-02-01 星期二"}]
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
         * num_class : 共2学时
         * training_id : 1
         * entry_project : 科目二
         * sys_time : 2021-01-31 18:05:43
         * stu_name : 方文山
         * evaluation_type : 0
         * training_type : 0
         * driving_practice : 驾驶练习
         * trainee_phone : 18978888766
         * selected_period : 06:00,07:00
         * practice_time : 2021-02-01 星期二
         */

        private String num_class;
        private String training_id;
        private String entry_project;
        private String sys_time;
        private String stu_name;
        private String evaluation_type;
        private String training_type;
        private String driving_practice;
        private String trainee_phone;
        private String selected_period;
        private String practice_time;

        public String getNum_class() {
            return num_class;
        }

        public void setNum_class(String num_class) {
            this.num_class = num_class;
        }

        public String getTraining_id() {
            return training_id;
        }

        public void setTraining_id(String training_id) {
            this.training_id = training_id;
        }

        public String getEntry_project() {
            return entry_project;
        }

        public void setEntry_project(String entry_project) {
            this.entry_project = entry_project;
        }

        public String getSys_time() {
            return sys_time;
        }

        public void setSys_time(String sys_time) {
            this.sys_time = sys_time;
        }

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getEvaluation_type() {
            return evaluation_type;
        }

        public void setEvaluation_type(String evaluation_type) {
            this.evaluation_type = evaluation_type;
        }

        public String getTraining_type() {
            return training_type;
        }

        public void setTraining_type(String training_type) {
            this.training_type = training_type;
        }

        public String getDriving_practice() {
            return driving_practice;
        }

        public void setDriving_practice(String driving_practice) {
            this.driving_practice = driving_practice;
        }

        public String getTrainee_phone() {
            return trainee_phone;
        }

        public void setTrainee_phone(String trainee_phone) {
            this.trainee_phone = trainee_phone;
        }

        public String getSelected_period() {
            return selected_period;
        }

        public void setSelected_period(String selected_period) {
            this.selected_period = selected_period;
        }

        public String getPractice_time() {
            return practice_time;
        }

        public void setPractice_time(String practice_time) {
            this.practice_time = practice_time;
        }
    }
}
