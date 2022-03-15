package com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean;

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
 * #            Created by 張某人 on 2021/2/4/21:08 .
 * #            com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean
 * #            xueyiche
 */
public class StudentsOrderConentBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"practice_content":"","coach_phone":"18600099988","coach_name":"王教练","evaluation_type":"0","selected_period":"06:00-07:00,07:00-08:00","coach_to_student_detail":"","selected_period_id":"6,7","stu_user_id":"183","training_id":1,"entry_project":"科目二","sys_time":"2021-01-31 18:05:43","training_type":"0","trainee_phone":"18978888766","pick_up_location":"科技大厦","get_off_time":"2021-02-01 16:41:23","num_class":"共2学时","student_to_coach_detail":"","driving_practice":"驾驶练习","drop_off_location":"双创产业园","coach_user_id":"199","boarding_time":"2021-02-01 16:40:50","coach_to_student":"","stu_name":"方文山","practice_time":"2021-02-05 星期二","student_to_coach":""}
     */

    private String msg;
    private int code;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * practice_content :
         * coach_phone : 18600099988
         * coach_name : 王教练
         * evaluation_type : 0
         * selected_period : 06:00-07:00,07:00-08:00
         * coach_to_student_detail :
         * selected_period_id : 6,7
         * stu_user_id : 183
         * training_id : 1
         * entry_project : 科目二
         * sys_time : 2021-01-31 18:05:43
         * training_type : 0
         * trainee_phone : 18978888766
         * pick_up_location : 科技大厦
         * get_off_time : 2021-02-01 16:41:23
         * num_class : 共2学时
         * student_to_coach_detail :
         * driving_practice : 驾驶练习
         * drop_off_location : 双创产业园
         * coach_user_id : 199
         * boarding_time : 2021-02-01 16:40:50
         * coach_to_student :
         * stu_name : 方文山
         * practice_time : 2021-02-05 星期二
         * student_to_coach :
         */

        private String practice_content;
        private String coach_phone;
        private String coach_name;
        private String evaluation_type;
        private String selected_period;
        private String coach_to_student_detail;
        private String selected_period_id;
        private String stu_user_id;
        private int training_id;
        private String entry_project;
        private String sys_time;
        private String training_type;
        private String trainee_phone;
        private String pick_up_location;
        private String get_off_time;
        private String num_class;
        private String student_to_coach_detail;
        private String driving_practice;
        private String drop_off_location;
        private String coach_user_id;
        private String boarding_time;
        private String coach_to_student;
        private String stu_name;
        private String practice_time;
        private String student_to_coach;

        public String getPractice_content() {
            return practice_content;
        }

        public void setPractice_content(String practice_content) {
            this.practice_content = practice_content;
        }

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

        public String getEvaluation_type() {
            return evaluation_type;
        }

        public void setEvaluation_type(String evaluation_type) {
            this.evaluation_type = evaluation_type;
        }

        public String getSelected_period() {
            return selected_period;
        }

        public void setSelected_period(String selected_period) {
            this.selected_period = selected_period;
        }

        public String getCoach_to_student_detail() {
            return coach_to_student_detail;
        }

        public void setCoach_to_student_detail(String coach_to_student_detail) {
            this.coach_to_student_detail = coach_to_student_detail;
        }

        public String getSelected_period_id() {
            return selected_period_id;
        }

        public void setSelected_period_id(String selected_period_id) {
            this.selected_period_id = selected_period_id;
        }

        public String getStu_user_id() {
            return stu_user_id;
        }

        public void setStu_user_id(String stu_user_id) {
            this.stu_user_id = stu_user_id;
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

        public String getTraining_type() {
            return training_type;
        }

        public void setTraining_type(String training_type) {
            this.training_type = training_type;
        }

        public String getTrainee_phone() {
            return trainee_phone;
        }

        public void setTrainee_phone(String trainee_phone) {
            this.trainee_phone = trainee_phone;
        }

        public String getPick_up_location() {
            return pick_up_location;
        }

        public void setPick_up_location(String pick_up_location) {
            this.pick_up_location = pick_up_location;
        }

        public String getGet_off_time() {
            return get_off_time;
        }

        public void setGet_off_time(String get_off_time) {
            this.get_off_time = get_off_time;
        }

        public String getNum_class() {
            return num_class;
        }

        public void setNum_class(String num_class) {
            this.num_class = num_class;
        }

        public String getStudent_to_coach_detail() {
            return student_to_coach_detail;
        }

        public void setStudent_to_coach_detail(String student_to_coach_detail) {
            this.student_to_coach_detail = student_to_coach_detail;
        }

        public String getDriving_practice() {
            return driving_practice;
        }

        public void setDriving_practice(String driving_practice) {
            this.driving_practice = driving_practice;
        }

        public String getDrop_off_location() {
            return drop_off_location;
        }

        public void setDrop_off_location(String drop_off_location) {
            this.drop_off_location = drop_off_location;
        }

        public String getCoach_user_id() {
            return coach_user_id;
        }

        public void setCoach_user_id(String coach_user_id) {
            this.coach_user_id = coach_user_id;
        }

        public String getBoarding_time() {
            return boarding_time;
        }

        public void setBoarding_time(String boarding_time) {
            this.boarding_time = boarding_time;
        }

        public String getCoach_to_student() {
            return coach_to_student;
        }

        public void setCoach_to_student(String coach_to_student) {
            this.coach_to_student = coach_to_student;
        }

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getPractice_time() {
            return practice_time;
        }

        public void setPractice_time(String practice_time) {
            this.practice_time = practice_time;
        }

        public String getStudent_to_coach() {
            return student_to_coach;
        }

        public void setStudent_to_coach(String student_to_coach) {
            this.student_to_coach = student_to_coach;
        }
    }
}
