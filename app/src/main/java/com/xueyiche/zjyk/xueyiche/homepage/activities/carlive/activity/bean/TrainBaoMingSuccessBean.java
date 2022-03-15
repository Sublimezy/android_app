package com.xueyiche.zjyk.xueyiche.homepage.activities.carlive.activity.bean;

/**
 * Created by Owner on 2018/11/29.
 */
public class TrainBaoMingSuccessBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"order_number":"E1543407033873"}
     */

    private String msg;
    private int code;
    /**
     * order_number : E1543407033873
     */

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
        private String order_number;

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }
    }
}
