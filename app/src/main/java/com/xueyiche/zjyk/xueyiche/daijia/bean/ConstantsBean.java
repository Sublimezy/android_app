package com.xueyiche.zjyk.xueyiche.daijia.bean;

/**
 * Created by Administrator on 2019/10/10.
 */
public class ConstantsBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"over_time_amount":"5","in_time_amount":"59","default_time":"15","out_time_amount":"39","per_distance_price":"5","over_time":"10","max_distance":"40","wait_time":"40","appointedCancle_time":"-50","substitute_time":"22:00:00,06:00:00"}
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
         * over_time_amount : 5
         * in_time_amount : 59
         * default_time : 15
         * out_time_amount : 39
         * per_distance_price : 5
         * over_time : 10
         * max_distance : 40
         * wait_time : 40
         * appointedCancle_time : -50
         * substitute_time : 22:00:00,06:00:00
         */

        private String over_time_amount;
        private String in_time_amount;
        private String default_time;
        private String out_time_amount;
        private String per_distance_price;
        private String over_time;
        private String max_distance;
        private String wait_time;
        private String appointedCancle_time;
        private String substitute_time;

        public String getOver_time_amount() {
            return over_time_amount;
        }

        public void setOver_time_amount(String over_time_amount) {
            this.over_time_amount = over_time_amount;
        }

        public String getIn_time_amount() {
            return in_time_amount;
        }

        public void setIn_time_amount(String in_time_amount) {
            this.in_time_amount = in_time_amount;
        }

        public String getDefault_time() {
            return default_time;
        }

        public void setDefault_time(String default_time) {
            this.default_time = default_time;
        }

        public String getOut_time_amount() {
            return out_time_amount;
        }

        public void setOut_time_amount(String out_time_amount) {
            this.out_time_amount = out_time_amount;
        }

        public String getPer_distance_price() {
            return per_distance_price;
        }

        public void setPer_distance_price(String per_distance_price) {
            this.per_distance_price = per_distance_price;
        }

        public String getOver_time() {
            return over_time;
        }

        public void setOver_time(String over_time) {
            this.over_time = over_time;
        }

        public String getMax_distance() {
            return max_distance;
        }

        public void setMax_distance(String max_distance) {
            this.max_distance = max_distance;
        }

        public String getWait_time() {
            return wait_time;
        }

        public void setWait_time(String wait_time) {
            this.wait_time = wait_time;
        }

        public String getAppointedCancle_time() {
            return appointedCancle_time;
        }

        public void setAppointedCancle_time(String appointedCancle_time) {
            this.appointedCancle_time = appointedCancle_time;
        }

        public String getSubstitute_time() {
            return substitute_time;
        }

        public void setSubstitute_time(String substitute_time) {
            this.substitute_time = substitute_time;
        }
    }
}
