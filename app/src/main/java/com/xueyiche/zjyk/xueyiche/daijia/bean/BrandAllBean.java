package com.xueyiche.zjyk.xueyiche.daijia.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.daijia.bean
 * @ClassName: BrandAllBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/3/18 15:57
 */
public class BrandAllBean {

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
        private String id;
        private String logo;
        private String name;
        private String orders;
        private String speccount;
        private String pinyin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
        }

        public String getSpeccount() {
            return speccount;
        }

        public void setSpeccount(String speccount) {
            this.speccount = speccount;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }
    }
}
