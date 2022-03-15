package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean
 * @ClassName: StartTrainBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/2/4 7:38
 */
public class StartTrainBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"real_people":1,"today_people":1}
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
         * real_people : 1
         * today_people : 1
         */

        private int real_people;
        private int today_people;

        public int getReal_people() {
            return real_people;
        }

        public void setReal_people(int real_people) {
            this.real_people = real_people;
        }

        public int getToday_people() {
            return today_people;
        }

        public void setToday_people(int today_people) {
            this.today_people = today_people;
        }
    }
}
