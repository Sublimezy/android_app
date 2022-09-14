package com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean;

import java.util.List;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 2021/2/2/16:13 .
 * #            com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean
 * #            xueyiche
 */
public class StudentOrderRecordBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"coach_phone":"18600099988","coach_name":"王教练","num_class":"共2学时","training_id":1,"entry_project":"科目二","sys_time":"2021-01-31 18:05:43","driving_practice":"驾驶练习","selected_period":"06:00,07:00","practice_time":"2021-02-01 星期二"},{"coach_phone":"18600099988","coach_name":"王教练","num_class":"","training_id":2,"entry_project":"科目一","sys_time":"2021-02-01 10:07:27","driving_practice":"驾驶练习","selected_period":"","practice_time":""}]
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
         * coach_phone : 18600099988
         * coach_name : 王教练
         * num_class : 共2学时
         * training_id : 1
         * entry_project : 科目二
         * sys_time : 2021-01-31 18:05:43
         * driving_practice : 驾驶练习
         * selected_period : 06:00,07:00
         * practice_time : 2021-02-01 星期二
         */

        private String coach_phone;
        private String coach_name;
        private String num_class;
        private int training_id;
        private String entry_project;
        private String sys_time;
        private String driving_practice;
        private String selected_period;
        private String practice_time;

        public String getCoach_phone() {
            return coach_phone;
        }

        public void setCoach_phone(String coach_phone) {
            this.coach_phone = coach_phone;
        }

        public String getCoach_name() {
            return coach_name;
        }

        public void setCoach_name(String coach_name) {
            this.coach_name = coach_name;
        }

        public String getNum_class() {
            return num_class;
        }

        public void setNum_class(String num_class) {
            this.num_class = num_class;
        }

        public int getTraining_id() {
            return training_id;
        }

        public void setTraining_id(int training_id) {
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

        public String getDriving_practice() {
            return driving_practice;
        }

        public void setDriving_practice(String driving_practice) {
            this.driving_practice = driving_practice;
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
