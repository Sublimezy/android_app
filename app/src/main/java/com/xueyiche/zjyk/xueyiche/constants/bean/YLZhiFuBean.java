package com.xueyiche.zjyk.xueyiche.constants.bean;

/**
 * Created by ZL on 2017/7/11.
 */
public class YLZhiFuBean {


    /**
     * tn : 692489557331369711901
     */

    private ContentBean content;
    /**
     * content : {"tn":"692489557331369711901"}
     * code : 0
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
        private String tn;

        public String getTn() {
            return tn;
        }

        public void setTn(String tn) {
            this.tn = tn;
        }
    }
}
