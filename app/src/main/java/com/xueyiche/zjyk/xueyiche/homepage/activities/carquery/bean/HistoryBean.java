package com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.bean;

import java.util.List;

/**
 * Created by Owner on 2019/6/5.
 */
public class HistoryBean {


    /**
     * success : 1
     * message : [{"istate":-1,"gbno":null,"stockarea":"哈尔滨","insurer":"","car_Vin":null,"cartype":null,"accessprice":null,"reportdate":null,"overdate":null,"isover":0,"shotname":"拍摄方案","car_km":null,"id":200002202,"tradeprice":0,"apply_time":"2019-06-05 14:45","expect_time":"2019-06-05 15:45","loantype":"常规产品","user_tel":"18945057021","stockplace":"哈尔滨","isurgent":false,"reporturl":null,"url":null,"reject_time":"2019-06-05 15:15:21","reject_msg":"驳回 [右前45]，原因：登记证信息与实车信息不一致，请确认登记证是否与评估车辆匹配\n"}]
     */

    private int success;
    /**
     * istate : -1
     * gbno : null
     * stockarea : 哈尔滨
     * insurer :
     * car_Vin : null
     * cartype : null
     * accessprice : null
     * reportdate : null
     * overdate : null
     * isover : 0
     * shotname : 拍摄方案
     * car_km : null
     * id : 200002202
     * tradeprice : 0
     * apply_time : 2019-06-05 14:45
     * expect_time : 2019-06-05 15:45
     * loantype : 常规产品
     * user_tel : 18945057021
     * stockplace : 哈尔滨
     * isurgent : false
     * reporturl : null
     * url : null
     * reject_time : 2019-06-05 15:15:21
     * reject_msg : 驳回 [右前45]，原因：登记证信息与实车信息不一致，请确认登记证是否与评估车辆匹配

     */

    private List<MessageBean> message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        private int istate;
        private Object gbno;
        private String stockarea;
        private String insurer;
        private Object car_Vin;
        private Object cartype;
        private String accessprice;
        private String reportdate;
        private Object overdate;
        private int isover;
        private String shotname;
        private Object car_km;
        private int id;
        private int tradeprice;
        private String apply_time;
        private String expect_time;
        private String loantype;
        private String user_tel;
        private String stockplace;
        private boolean isurgent;
        private Object reporturl;
        private String url;
        private String reject_time;
        private String reject_msg;

        public int getIstate() {
            return istate;
        }

        public void setIstate(int istate) {
            this.istate = istate;
        }

        public Object getGbno() {
            return gbno;
        }

        public void setGbno(Object gbno) {
            this.gbno = gbno;
        }

        public String getStockarea() {
            return stockarea;
        }

        public void setStockarea(String stockarea) {
            this.stockarea = stockarea;
        }

        public String getInsurer() {
            return insurer;
        }

        public void setInsurer(String insurer) {
            this.insurer = insurer;
        }

        public Object getCar_Vin() {
            return car_Vin;
        }

        public void setCar_Vin(Object car_Vin) {
            this.car_Vin = car_Vin;
        }

        public Object getCartype() {
            return cartype;
        }

        public void setCartype(Object cartype) {
            this.cartype = cartype;
        }

        public String getAccessprice() {
            return accessprice;
        }

        public void setAccessprice(String accessprice) {
            this.accessprice = accessprice;
        }

        public String getReportdate() {
            return reportdate;
        }

        public void setReportdate(String reportdate) {
            this.reportdate = reportdate;
        }

        public Object getOverdate() {
            return overdate;
        }

        public void setOverdate(Object overdate) {
            this.overdate = overdate;
        }

        public int getIsover() {
            return isover;
        }

        public void setIsover(int isover) {
            this.isover = isover;
        }

        public String getShotname() {
            return shotname;
        }

        public void setShotname(String shotname) {
            this.shotname = shotname;
        }

        public Object getCar_km() {
            return car_km;
        }

        public void setCar_km(Object car_km) {
            this.car_km = car_km;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTradeprice() {
            return tradeprice;
        }

        public void setTradeprice(int tradeprice) {
            this.tradeprice = tradeprice;
        }

        public String getApply_time() {
            return apply_time;
        }

        public void setApply_time(String apply_time) {
            this.apply_time = apply_time;
        }

        public String getExpect_time() {
            return expect_time;
        }

        public void setExpect_time(String expect_time) {
            this.expect_time = expect_time;
        }

        public String getLoantype() {
            return loantype;
        }

        public void setLoantype(String loantype) {
            this.loantype = loantype;
        }

        public String getUser_tel() {
            return user_tel;
        }

        public void setUser_tel(String user_tel) {
            this.user_tel = user_tel;
        }

        public String getStockplace() {
            return stockplace;
        }

        public void setStockplace(String stockplace) {
            this.stockplace = stockplace;
        }

        public boolean isIsurgent() {
            return isurgent;
        }

        public void setIsurgent(boolean isurgent) {
            this.isurgent = isurgent;
        }

        public Object getReporturl() {
            return reporturl;
        }

        public void setReporturl(Object reporturl) {
            this.reporturl = reporturl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getReject_time() {
            return reject_time;
        }

        public void setReject_time(String reject_time) {
            this.reject_time = reject_time;
        }

        public String getReject_msg() {
            return reject_msg;
        }

        public void setReject_msg(String reject_msg) {
            this.reject_msg = reject_msg;
        }
    }
}
