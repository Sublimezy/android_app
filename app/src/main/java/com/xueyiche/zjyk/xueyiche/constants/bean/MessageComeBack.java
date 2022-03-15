package com.xueyiche.zjyk.xueyiche.constants.bean;

/**
 * Created by Owner on 2016/12/7.
 */
public class MessageComeBack {

    /**
     * code : 0
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * collectionKeep : success
     */

    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private String collectionKeep;

        public String getCollectionKeep() {
            return collectionKeep;
        }

        public void setCollectionKeep(String collectionKeep) {
            this.collectionKeep = collectionKeep;
        }
    }
}
