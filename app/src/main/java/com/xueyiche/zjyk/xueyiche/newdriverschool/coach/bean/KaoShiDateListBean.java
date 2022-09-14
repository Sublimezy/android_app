package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean
 * @ClassName: KaoShiDateListBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/2/8 14:54
 */
public class KaoShiDateListBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"reservation_date":"2021-02-11","res_subject":"1","id":1},{"reservation_date":"2021-02-12","res_subject":"1","id":2},{"reservation_date":"2021-02-13","res_subject":"2","id":3}]
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
         * reservation_date : 2021-02-11
         * res_subject : 1
         * id : 1
         */

        private String reservation_date;
        private String res_subject;
        private String id;

        public String getReservation_date() {
            return reservation_date;
        }

        public void setReservation_date(String reservation_date) {
            this.reservation_date = reservation_date;
        }

        public String getRes_subject() {
            return res_subject;
        }

        public void setRes_subject(String res_subject) {
            this.res_subject = res_subject;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
