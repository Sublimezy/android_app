package com.xueyiche.zjyk.xueyiche.examtext.bean;

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
 * #            Created by 張某人 on 2021/2/4/14:08 .
 * #            com.xueyiche.zjyk.xueyiche.examtext.bean
 * #            xueyiche
 */
public class UserTypeBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"stu_sign_up":"1","stu_coach":"1"}
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
         * stu_sign_up : 1
         * stu_coach : 1
         */

        private String stu_sign_up;
        private String stu_coach;
        private String coach_id;

        public String getCoach_id() {
            return coach_id;
        }

        public void setCoach_id(String coach_id) {
            this.coach_id = coach_id;
        }

        public String getStu_sign_up() {
            return stu_sign_up;
        }

        public void setStu_sign_up(String stu_sign_up) {
            this.stu_sign_up = stu_sign_up;
        }

        public String getStu_coach() {
            return stu_coach;
        }

        public void setStu_coach(String stu_coach) {
            this.stu_coach = stu_coach;
        }
    }
}
