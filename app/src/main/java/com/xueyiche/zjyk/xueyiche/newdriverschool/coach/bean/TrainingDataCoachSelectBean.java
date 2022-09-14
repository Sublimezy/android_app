package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean
 * @ClassName: TrainingTimeCoachBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/1/26 8:50
 */
public class TrainingDataCoachSelectBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"today":"2021-01-25","days":"10","list":[{"coach_data":"2021-01-25","coach_set":"1","coach_user_id":"183","driving_type":"0","id":1}]}
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
         * today : 2021-01-25
         * days : 10
         * list : [{"coach_data":"2021-01-25","coach_set":"1","coach_user_id":"183","driving_type":"0","id":1}]
         */

        private String today;
        private String days;
        private List<ListBean> list;

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * coach_data : 2021-01-25
             * coach_set : 1
             * coach_user_id : 183
             * driving_type : 0
             * id : 1
             */

            private String coach_data;
            private String coach_set;
            private String coach_user_id;
            private String driving_type;
            private String id;

            public String getCoach_data() {
                return coach_data;
            }

            public void setCoach_data(String coach_data) {
                this.coach_data = coach_data;
            }

            public String getCoach_set() {
                return coach_set;
            }

            public void setCoach_set(String coach_set) {
                this.coach_set = coach_set;
            }

            public String getCoach_user_id() {
                return coach_user_id;
            }

            public void setCoach_user_id(String coach_user_id) {
                this.coach_user_id = coach_user_id;
            }

            public String getDriving_type() {
                return driving_type;
            }

            public void setDriving_type(String driving_type) {
                this.driving_type = driving_type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
