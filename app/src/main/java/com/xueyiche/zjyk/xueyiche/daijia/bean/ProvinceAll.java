package com.xueyiche.zjyk.xueyiche.daijia.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.daijia.bean
 * @ClassName: ProvinceAll
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/3/18 15:04
 */
public class ProvinceAll {

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String AREA_ID;
        private String AREA_NAME;

        public String getAREA_ID() {
            return AREA_ID;
        }

        public void setAREA_ID(String AREA_ID) {
            this.AREA_ID = AREA_ID;
        }

        public String getAREA_NAME() {
            return AREA_NAME;
        }

        public void setAREA_NAME(String AREA_NAME) {
            this.AREA_NAME = AREA_NAME;
        }
    }
}
