package com.gxuwz.zy.message.entity.dto;


import com.gxuwz.zy.message.entity.bean.MessageBean;

import java.util.List;

public class ReqMessageBean {

    private long total;


    private List<MessageBean> rows;

    private int code;

    private String msg;

    public ReqMessageBean() {
    }

    @Override
    public String toString() {
        return "ReqMessageBean{" +
                "total=" + total +
                ", rows=" + rows +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public ReqMessageBean(long total, List<MessageBean> rows, int code, String msg) {
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

    public List<MessageBean> getRows() {
        return rows;
    }

    public void setRows(List<MessageBean> rows) {
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
