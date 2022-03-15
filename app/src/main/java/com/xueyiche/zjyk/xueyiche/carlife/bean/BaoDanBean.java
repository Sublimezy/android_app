package com.xueyiche.zjyk.xueyiche.carlife.bean;

/**
 * Created by Owner on 2017/10/31.
 */
public class BaoDanBean {


    /**
     * convert_type : 1
     * amount : 1500.05
     */

    private ContentBean content;
    /**
     * content : {"convert_type":"1","amount":1500.05}
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
        private String convert_type;
        private double old_amount;

        public String getConvert_type() {
            return convert_type;
        }

        public void setConvert_type(String convert_type) {
            this.convert_type = convert_type;
        }

        public double getOld_amount() {
            return old_amount;
        }

        public void setOld_amount(double old_amount) {
            this.old_amount = old_amount;
        }
    }
}
