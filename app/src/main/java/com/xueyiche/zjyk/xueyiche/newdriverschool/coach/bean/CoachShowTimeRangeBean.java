package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean
 * @ClassName: CoachShowTimeRangeBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/1/29 19:07
 */
public class CoachShowTimeRangeBean {
    /**
     * msg : 操作成功！
     * code : 200
     * content : {"section_price":"30","list":[{"selected_date_id":"49","driving_type":"0","coach_peirod":"6","id":101,"show_person_num":"0","time_period":"06:00-07:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"7","id":102,"show_person_num":"0","time_period":"07:00-08:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"8","id":103,"show_person_num":"0","time_period":"08:00-09:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"9","id":104,"show_person_num":"0","time_period":"09:00-10:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"10","id":105,"show_person_num":"0","time_period":"10:00-11:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"11","id":106,"show_person_num":"0","time_period":"11:00-12:00"}]}
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
         * section_price : 30
         * list : [{"selected_date_id":"49","driving_type":"0","coach_peirod":"6","id":101,"show_person_num":"0","time_period":"06:00-07:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"7","id":102,"show_person_num":"0","time_period":"07:00-08:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"8","id":103,"show_person_num":"0","time_period":"08:00-09:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"9","id":104,"show_person_num":"0","time_period":"09:00-10:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"10","id":105,"show_person_num":"0","time_period":"10:00-11:00"},{"selected_date_id":"49","driving_type":"0","coach_peirod":"11","id":106,"show_person_num":"0","time_period":"11:00-12:00"}]
         */

        private String section_price;
        private List<ListBean> list;

        public String getSection_price() {
            return section_price;
        }

        public void setSection_price(String section_price) {
            this.section_price = section_price;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * selected_date_id : 49
             * driving_type : 0
             * coach_peirod : 6
             * id : 101
             * show_person_num : 0
             * time_period : 06:00-07:00
             */

            private String selected_date_id;
            private String driving_type;
            private String coach_peirod;
            private String id;
            private String show_person_num;
            private String time_period;

            public String getSelected_date_id() {
                return selected_date_id;
            }

            public void setSelected_date_id(String selected_date_id) {
                this.selected_date_id = selected_date_id;
            }

            public String getDriving_type() {
                return driving_type;
            }

            public void setDriving_type(String driving_type) {
                this.driving_type = driving_type;
            }

            public String getCoach_peirod() {
                return coach_peirod;
            }

            public void setCoach_peirod(String coach_peirod) {
                this.coach_peirod = coach_peirod;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShow_person_num() {
                return show_person_num;
            }

            public void setShow_person_num(String show_person_num) {
                this.show_person_num = show_person_num;
            }

            public String getTime_period() {
                return time_period;
            }

            public void setTime_period(String time_period) {
                this.time_period = time_period;
            }
        }
    }

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"selected_date_id":"1","coach_peirod":"6","id":6,"time_period":"00:60-07:00"},{"selected_date_id":"1","coach_peirod":"7","id":7,"time_period":"00:70-08:00"},{"selected_date_id":"1","coach_peirod":"8","id":8,"time_period":"00:80-09:00"},{"selected_date_id":"1","coach_peirod":"9","id":9,"time_period":"00:90-10:00"}]
     */

 /*   private String msg;
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
        *//**
         * selected_date_id : 1
         * coach_peirod : 6
         * id : 6
         * time_period : 00:60-07:00
         *//*

        private String selected_date_id;
        private String driving_type;
        private String show_person_num;
        private String coach_peirod;
        private String id;
        private String time_period;

        public String getDriving_type() {
            return driving_type;
        }

        public void setDriving_type(String driving_type) {
            this.driving_type = driving_type;
        }

        public String getShow_person_num() {
            return show_person_num;
        }

        public void setShow_person_num(String show_person_num) {
            this.show_person_num = show_person_num;
        }

        public String getSelected_date_id() {
            return selected_date_id;
        }

        public void setSelected_date_id(String selected_date_id) {
            this.selected_date_id = selected_date_id;
        }

        public String getCoach_peirod() {
            return coach_peirod;
        }

        public void setCoach_peirod(String coach_peirod) {
            this.coach_peirod = coach_peirod;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime_period() {
            return time_period;
        }

        public void setTime_period(String time_period) {
            this.time_period = time_period;
        }
    }*/

}
