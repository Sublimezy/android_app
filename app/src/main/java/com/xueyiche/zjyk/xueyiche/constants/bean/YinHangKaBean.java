package com.xueyiche.zjyk.xueyiche.constants.bean;

/**
 * Created by Owner on 2017/1/14.
 */
public class YinHangKaBean {


    /**
     * user_card_bank : 工商银行
     * user_card_per : 测试
     * user_card_no : 622666666666
     */

    private ContentBean content;
    /**
     * content : {"user_card_bank":"工商银行","user_card_per":"测试","user_card_no":"622666666666"}
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

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

    public static class ContentBean {
        private String user_card_bank;
        private String user_card_per;
        private String user_card_no;

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

        public String getUser_card_no() {
            return user_card_no;
        }

        public void setUser_card_no(String user_card_no) {
            this.user_card_no = user_card_no;
        }
    }
}
