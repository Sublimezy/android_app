package com.xueyiche.zjyk.xueyiche.usedcar.bean;

/**
 * Created by ZL on 2018/11/1.
 */
public class ZhengXinChaXunBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"info_url":"https://36kr.com/"}
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
         * info_url : https://36kr.com/
         */

        private String info_url;

        public String getInfo_url() {
            return info_url;
        }

        public void setInfo_url(String info_url) {
            this.info_url = info_url;
        }
    }
}
