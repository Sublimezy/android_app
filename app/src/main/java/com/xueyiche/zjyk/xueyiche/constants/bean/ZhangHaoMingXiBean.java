package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.util.List;

/**
 * Created by Owner on 2017/1/14.
 */
public class ZhangHaoMingXiBean {

    /**
     * content : [{"user_card_no":"5555555","system_time":"2017-1-14","user_card_bank":"招商银行","user_card_per":"克里斯","money_out":"1","user_phone":"18600752610"}]
     * code : 0
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * user_card_no : 5555555
     * system_time : 2017-1-14
     * user_card_bank : 招商银行
     * user_card_per : 克里斯
     * money_out : 1
     * user_phone : 18600752610
     */

    private List<ContentBean> content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String user_card_no;
        private String system_time;
        private String user_card_bank;
        private String user_card_per;
        private String money_out;
        private String money_in;
        private String user_phone;
        private String money_type;

        public String getMoney_type() {
            return money_type;
        }

        public void setMoney_type(String money_type) {
            this.money_type = money_type;
        }

        public String getMoney_in() {
            return money_in;
        }

        public void setMoney_in(String money_in) {
            this.money_in = money_in;
        }

        public String getUser_card_no() {
            return user_card_no;
        }

        public void setUser_card_no(String user_card_no) {
            this.user_card_no = user_card_no;
        }

        public String getSystem_time() {
            return system_time;
        }

        public void setSystem_time(String system_time) {
            this.system_time = system_time;
        }

        public String getUser_card_bank() {
            return user_card_bank;
        }

        public void setUser_card_bank(String user_card_bank) {
            this.user_card_bank = user_card_bank;
        }

        public String getUser_card_per() {
            return user_card_per;
        }

        public void setUser_card_per(String user_card_per) {
            this.user_card_per = user_card_per;
        }

        public String getMoney_out() {
            return money_out;
        }

        public void setMoney_out(String money_out) {
            this.money_out = money_out;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }
    }
}
