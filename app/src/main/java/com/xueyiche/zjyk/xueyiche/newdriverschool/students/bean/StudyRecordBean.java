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
 * #            Created by 張某人 on 2021/1/27/9:05 .
 * #            com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean
 * #            xueyiche
 */
public class StudyRecordBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"stu_user_id":"183","subject_backup":"倒车入库","sub_systime":"2020-01-14 14:49:49","driving_subject_stu_id":2,"cross_subject":"1","subjectid":"5"},{"stu_user_id":"183","subject_backup":"科目一模拟考试98分","sub_systime":"2020-01-14 14:49:44","driving_subject_stu_id":1,"cross_subject":"1","subjectid":"1"}]
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
         * stu_user_id : 183
         * subject_backup : 倒车入库
         * sub_systime : 2020-01-14 14:49:49
         * driving_subject_stu_id : 2
         * cross_subject : 1
         * subjectid : 5
         */

        private String stu_user_id;
        private String subject_backup;
        private String sub_systime;
        private int driving_subject_stu_id;
        private String cross_subject;
        private String subjectid;
        private String subject_name;
        private String training_id;

        public String getTraining_id() {
            return training_id;
        }

        public void setTraining_id(String training_id) {
            this.training_id = training_id;
        }

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public String getStu_user_id() {
            return stu_user_id;
        }

        public void setStu_user_id(String stu_user_id) {
            this.stu_user_id = stu_user_id;
        }

        public String getSubject_backup() {
            return subject_backup;
        }

        public void setSubject_backup(String subject_backup) {
            this.subject_backup = subject_backup;
        }

        public String getSub_systime() {
            return sub_systime;
        }

        public void setSub_systime(String sub_systime) {
            this.sub_systime = sub_systime;
        }

        public int getDriving_subject_stu_id() {
            return driving_subject_stu_id;
        }

        public void setDriving_subject_stu_id(int driving_subject_stu_id) {
            this.driving_subject_stu_id = driving_subject_stu_id;
        }

        public String getCross_subject() {
            return cross_subject;
        }

        public void setCross_subject(String cross_subject) {
            this.cross_subject = cross_subject;
        }

        public String getSubjectid() {
            return subjectid;
        }

        public void setSubjectid(String subjectid) {
            this.subjectid = subjectid;
        }
    }
}
