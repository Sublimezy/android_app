package com.gxuwz.zy.constants.bean;
public class TokenBean {

    /**
     * content : {"token":"uGpySdC57g0W9gL_s_MQDcdCkm4x1TgASFT2mvLL:IHmSRqsJACV41W_KGW91mHK8qk4=:eyJzY29wZSI6Im51bGw6YWEucG5nIiwiZGVhZGxpbmUiOjE1MDEyMTYxNTV9"}
     * code : 0
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
         * token : uGpySdC57g0W9gL_s_MQDcdCkm4x1TgASFT2mvLL:IHmSRqsJACV41W_KGW91mHK8qk4=:eyJzY29wZSI6Im51bGw6YWEucG5nIiwiZGVhZGxpbmUiOjE1MDEyMTYxNTV9
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}