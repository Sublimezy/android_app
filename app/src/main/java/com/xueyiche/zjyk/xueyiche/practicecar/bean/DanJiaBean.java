package com.xueyiche.zjyk.xueyiche.practicecar.bean;

public class DanJiaBean {

    private int code;
    private String msg;
    private String time;
    private DataDTO data;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private int unit_price;

        public int getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(int unit_price) {
            this.unit_price = unit_price;
        }
    }
}
