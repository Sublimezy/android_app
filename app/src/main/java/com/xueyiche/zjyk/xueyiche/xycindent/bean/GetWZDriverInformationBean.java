package com.xueyiche.zjyk.xueyiche.xycindent.bean;

/**
 * Created by ZL on 2018/12/14.
 */
public class GetWZDriverInformationBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"driver_name":"孙志远","driver_id":"8e4abf1b9ba145da83f59f4bf63ab37f","head_img":"http://images.xueyiche.vip/20180726093756689.jpg"}
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
         * driver_name : 孙志远
         * driver_id : 8e4abf1b9ba145da83f59f4bf63ab37f
         * head_img : http://images.xueyiche.vip/20180726093756689.jpg
         */

        private String driver_name;
        private String driver_id;
        private String head_img;

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }
    }
}
