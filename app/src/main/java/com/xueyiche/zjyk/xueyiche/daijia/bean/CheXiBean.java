package com.xueyiche.zjyk.xueyiche.daijia.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.daijia.bean
 * @ClassName: CheXiBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/3/18 15:59
 */
public class CheXiBean {

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
        private String factoryid;
        private String name;
        private String logo;
        private String seriesid;
        private String seriesname;
        private String serieslogo;
        private String brandid;

        public String getFactoryid() {
            return factoryid;
        }

        public void setFactoryid(String factoryid) {
            this.factoryid = factoryid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getSeriesid() {
            return seriesid;
        }

        public void setSeriesid(String seriesid) {
            this.seriesid = seriesid;
        }

        public String getSeriesname() {
            return seriesname;
        }

        public void setSeriesname(String seriesname) {
            this.seriesname = seriesname;
        }

        public String getSerieslogo() {
            return serieslogo;
        }

        public void setSerieslogo(String serieslogo) {
            this.serieslogo = serieslogo;
        }

        public String getBrandid() {
            return brandid;
        }

        public void setBrandid(String brandid) {
            this.brandid = brandid;
        }
    }
}
