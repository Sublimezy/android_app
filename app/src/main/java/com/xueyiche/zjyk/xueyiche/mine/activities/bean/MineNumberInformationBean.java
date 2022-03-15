package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

/**
 * Created by Owner on 2017/10/24.
 */
public class MineNumberInformationBean {


    /**
     * content : {"user_id":"b19ed950b53811e7aeb85254f2dc841f","head_img":"http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png","keep":"11","foot":"11","integral_num":"20000"}
     * code : 200
     * msg : 操作成功
     */

    private ContentBean content;
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
        /**
         * user_id : b19ed950b53811e7aeb85254f2dc841f
         * head_img : http://otqopw3sl.bkt.clouddn.com/XUEYICHETOUXIANG.png
         * keep : 11
         * foot : 11
         * integral_num : 20000
         */

        private String user_id;
        private String head_img;
        private String keep;
        private String foot;
        private String integral_num;
        private String nickname;
        private String message_status;
        private String mylovercar;
        private String yhq_num;
        private String dfk_num;
        private String jxz_num;

        public String getYhq_num() {
            return yhq_num;
        }

        public void setYhq_num(String yhq_num) {
            this.yhq_num = yhq_num;
        }

        public String getDfk_num() {
            return dfk_num;
        }

        public void setDfk_num(String dfk_num) {
            this.dfk_num = dfk_num;
        }

        public String getJxz_num() {
            return jxz_num;
        }

        public void setJxz_num(String jxz_num) {
            this.jxz_num = jxz_num;
        }

        public String getMylovercar() {
            return mylovercar;
        }

        public void setMylovercar(String mylovercar) {
            this.mylovercar = mylovercar;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getKeep() {
            return keep;
        }

        public void setKeep(String keep) {
            this.keep = keep;
        }

        public String getFoot() {
            return foot;
        }

        public void setFoot(String foot) {
            this.foot = foot;
        }

        public String getIntegral_num() {
            return integral_num;
        }

        public void setIntegral_num(String integral_num) {
            this.integral_num = integral_num;
        }
    }
}
