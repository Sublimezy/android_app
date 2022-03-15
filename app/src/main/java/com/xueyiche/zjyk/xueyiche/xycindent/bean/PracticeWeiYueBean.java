package com.xueyiche.zjyk.xueyiche.xycindent.bean;

/**
 * Created by Owner on 2018/7/11.
 */
public class PracticeWeiYueBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"remark":"12小时内取消，收取5%br,2小时内取消，收取20%br,1小时内取消，收取50%br"}
     */

    private String msg;
    private int code;
    /**
     * remark : 12小时内取消，收取5%br,2小时内取消，收取20%br,1小时内取消，收取50%br
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
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
