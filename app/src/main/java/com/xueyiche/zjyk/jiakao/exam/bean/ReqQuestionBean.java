package com.xueyiche.zjyk.jiakao.exam.bean;

import com.xueyiche.zjyk.jiakao.homepage.bean.QuestionBean;

import java.util.List;

public class ReqQuestionBean {

    private long total;


    private List<QuestionBean> rows;

    private int code;

    private String msg;

    public ReqQuestionBean() {
    }

    @Override
    public String toString() {
        return "ReqQuestionBean{" +
                "total=" + total +
                ", rows=" + rows +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public ReqQuestionBean(long total, List<QuestionBean> rows, int code, String msg) {
        this.total = total;
        this.rows = rows;
        this.code = code;
        this.msg = msg;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<QuestionBean> getRows() {
        return rows;
    }

    public void setRows(List<QuestionBean> rows) {
        this.rows = rows;
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
