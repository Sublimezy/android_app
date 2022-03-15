package com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean
 * @ClassName: CoachListBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/1/20 10:12
 */
public class CoachListBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"driving_coach_name":"张雨生 教练","coach_age":"32","user_id":"183","driving_coach_id":1,"coach_years":"5","coach_sex":"2","coach_head_img":"https://img.zcool.cn/community/038cf0a591c12bfa801216a3e9b69ab.jpg"},{"driving_coach_name":"马天宇","coach_age":"37","user_id":"250","driving_coach_id":2,"coach_years":"4","coach_sex":"2","coach_head_img":"https://img.zcool.cn/community/038cf0a591c12bfa801216a3e9b69ab.jpg"}]
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
         * driving_coach_name : 张雨生 教练
         * coach_age : 32
         * user_id : 183
         * driving_coach_id : 1
         * coach_years : 5
         * coach_sex : 2
         * coach_head_img : https://img.zcool.cn/community/038cf0a591c12bfa801216a3e9b69ab.jpg
         */

        private String driving_coach_name;
        private String coach_age;
        private String user_id;
        private String driving_coach_id;
        private String coach_years;
        private String coach_sex;
        private String coach_head_img;

        public String getDriving_coach_name() {
            return driving_coach_name;
        }

        public void setDriving_coach_name(String driving_coach_name) {
            this.driving_coach_name = driving_coach_name;
        }

        public String getCoach_age() {
            return coach_age;
        }

        public void setCoach_age(String coach_age) {
            this.coach_age = coach_age;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDriving_coach_id() {
            return driving_coach_id;
        }

        public void setDriving_coach_id(String driving_coach_id) {
            this.driving_coach_id = driving_coach_id;
        }

        public String getCoach_years() {
            return coach_years;
        }

        public void setCoach_years(String coach_years) {
            this.coach_years = coach_years;
        }

        public String getCoach_sex() {
            return coach_sex;
        }

        public void setCoach_sex(String coach_sex) {
            this.coach_sex = coach_sex;
        }

        public String getCoach_head_img() {
            return coach_head_img;
        }

        public void setCoach_head_img(String coach_head_img) {
            this.coach_head_img = coach_head_img;
        }
    }
}
