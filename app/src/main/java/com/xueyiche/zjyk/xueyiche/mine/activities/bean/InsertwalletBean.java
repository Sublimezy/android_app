package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

/**
 * Created by Owner on 2018/6/22.
 */
public class InsertwalletBean {

    /**
     * content : 余额大于20元,方可提现
     * code : 414
     * msg : 操作成功
     */

    private String content;
    private int code;
    private String msg;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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
}
