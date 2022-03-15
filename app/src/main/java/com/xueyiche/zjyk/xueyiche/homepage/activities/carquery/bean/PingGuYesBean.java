package com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.bean;

import java.util.List;

/**
 * Created by Owner on 2019/6/6.
 */
public class PingGuYesBean {

    /**
     * success : 1
     * message : [{"id":200002171,"gbno":"粤DFB696","stockarea":"哈尔滨","insurer":"","insurance":"","tradeprice":0,"apply_time":"2019-06-04 10:22:31","loantype":"常规产品","car_Vin":"WDDGH4JBXCF794267","cartype":"奔驰 奔驰C级(进口) 2011款 C 200 时尚旅行版","accidenttype":"正常","reportdate":"2019-06-04 13:51:23","overdate":"2019-07-04","isover":0,"shotname":"拍摄方案","user_tel":"18945057021","user_phonecode":null,"car_km":120000,"show_app_accident":1,"showAccdientCotent":false,"restmonth":null,"restprice":null,"itype":1,"pre_endtime":"1900-01-01","pre_showbtn":0,"all_price":"<font style='color:red;font-size:14px'>评估价：￥42500<\/font>","showappprice":1,"accessprice":42500,"IsCallBackPrice":false,"is_czprice":false,"modifyloantype":0,"modifyloantypeName":null,"accdient_content":"","file_info":[{"seq":2,"url":"http://gznfs.oss-cn-hangzhou.aliyuncs.com/200000317/5a0365d8-31d0-455a-bf40-809f18900454_7.jpg","minurl":"http://gznfs.oss-cn-hangzhou.aliyuncs.com/200000317/5a0365d8-31d0-455a-bf40-809f18900454_7.jpg?x-oss-process=image/resize,w_150","tag":"右前45","itype":1}]}]
     */

    private int success;
    /**
     * id : 200002171
     * gbno : 粤DFB696
     * stockarea : 哈尔滨
     * insurer :
     * insurance :
     * tradeprice : 0
     * apply_time : 2019-06-04 10:22:31
     * loantype : 常规产品
     * car_Vin : WDDGH4JBXCF794267
     * cartype : 奔驰 奔驰C级(进口) 2011款 C 200 时尚旅行版
     * accidenttype : 正常
     * reportdate : 2019-06-04 13:51:23
     * overdate : 2019-07-04
     * isover : 0
     * shotname : 拍摄方案
     * user_tel : 18945057021
     * user_phonecode : null
     * car_km : 120000
     * show_app_accident : 1
     * showAccdientCotent : false
     * restmonth : null
     * restprice : null
     * itype : 1
     * pre_endtime : 1900-01-01
     * pre_showbtn : 0
     * all_price : <font style='color:red;font-size:14px'>评估价：￥42500</font>
     * showappprice : 1
     * accessprice : 42500
     * IsCallBackPrice : false
     * is_czprice : false
     * modifyloantype : 0
     * modifyloantypeName : null
     * accdient_content :
     * file_info : [{"seq":2,"url":"http://gznfs.oss-cn-hangzhou.aliyuncs.com/200000317/5a0365d8-31d0-455a-bf40-809f18900454_7.jpg","minurl":"http://gznfs.oss-cn-hangzhou.aliyuncs.com/200000317/5a0365d8-31d0-455a-bf40-809f18900454_7.jpg?x-oss-process=image/resize,w_150","tag":"右前45","itype":1}]
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
        private int id;
        private String gbno;
        private String stockarea;
        private String insurer;
        private String insurance;
        private int tradeprice;
        private String apply_time;
        private String loantype;
        private String car_Vin;
        private String cartype;
        private String accidenttype;
        private String reportdate;
        private String overdate;
        private int isover;
        private String shotname;
        private String user_tel;
        private Object user_phonecode;
        private int car_km;
        private int show_app_accident;
        private boolean showAccdientCotent;
        private Object restmonth;
        private Object restprice;
        private int itype;
        private String pre_endtime;
        private int pre_showbtn;
        private String all_price;
        private int showappprice;
        private int accessprice;
        private boolean IsCallBackPrice;
        private boolean is_czprice;
        private int modifyloantype;
        private Object modifyloantypeName;
        private String accdient_content;
        /**
         * seq : 2
         * url : http://gznfs.oss-cn-hangzhou.aliyuncs.com/200000317/5a0365d8-31d0-455a-bf40-809f18900454_7.jpg
         * minurl : http://gznfs.oss-cn-hangzhou.aliyuncs.com/200000317/5a0365d8-31d0-455a-bf40-809f18900454_7.jpg?x-oss-process=image/resize,w_150
         * tag : 右前45
         * itype : 1
         */

        private List<FileInfoBean> file_info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGbno() {
            return gbno;
        }

        public void setGbno(String gbno) {
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

        public String getInsurance() {
            return insurance;
        }

        public void setInsurance(String insurance) {
            this.insurance = insurance;
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

        public String getLoantype() {
            return loantype;
        }

        public void setLoantype(String loantype) {
            this.loantype = loantype;
        }

        public String getCar_Vin() {
            return car_Vin;
        }

        public void setCar_Vin(String car_Vin) {
            this.car_Vin = car_Vin;
        }

        public String getCartype() {
            return cartype;
        }

        public void setCartype(String cartype) {
            this.cartype = cartype;
        }

        public String getAccidenttype() {
            return accidenttype;
        }

        public void setAccidenttype(String accidenttype) {
            this.accidenttype = accidenttype;
        }

        public String getReportdate() {
            return reportdate;
        }

        public void setReportdate(String reportdate) {
            this.reportdate = reportdate;
        }

        public String getOverdate() {
            return overdate;
        }

        public void setOverdate(String overdate) {
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

        public String getUser_tel() {
            return user_tel;
        }

        public void setUser_tel(String user_tel) {
            this.user_tel = user_tel;
        }

        public Object getUser_phonecode() {
            return user_phonecode;
        }

        public void setUser_phonecode(Object user_phonecode) {
            this.user_phonecode = user_phonecode;
        }

        public int getCar_km() {
            return car_km;
        }

        public void setCar_km(int car_km) {
            this.car_km = car_km;
        }

        public int getShow_app_accident() {
            return show_app_accident;
        }

        public void setShow_app_accident(int show_app_accident) {
            this.show_app_accident = show_app_accident;
        }

        public boolean isShowAccdientCotent() {
            return showAccdientCotent;
        }

        public void setShowAccdientCotent(boolean showAccdientCotent) {
            this.showAccdientCotent = showAccdientCotent;
        }

        public Object getRestmonth() {
            return restmonth;
        }

        public void setRestmonth(Object restmonth) {
            this.restmonth = restmonth;
        }

        public Object getRestprice() {
            return restprice;
        }

        public void setRestprice(Object restprice) {
            this.restprice = restprice;
        }

        public int getItype() {
            return itype;
        }

        public void setItype(int itype) {
            this.itype = itype;
        }

        public String getPre_endtime() {
            return pre_endtime;
        }

        public void setPre_endtime(String pre_endtime) {
            this.pre_endtime = pre_endtime;
        }

        public int getPre_showbtn() {
            return pre_showbtn;
        }

        public void setPre_showbtn(int pre_showbtn) {
            this.pre_showbtn = pre_showbtn;
        }

        public String getAll_price() {
            return all_price;
        }

        public void setAll_price(String all_price) {
            this.all_price = all_price;
        }

        public int getShowappprice() {
            return showappprice;
        }

        public void setShowappprice(int showappprice) {
            this.showappprice = showappprice;
        }

        public int getAccessprice() {
            return accessprice;
        }

        public void setAccessprice(int accessprice) {
            this.accessprice = accessprice;
        }

        public boolean isIsCallBackPrice() {
            return IsCallBackPrice;
        }

        public void setIsCallBackPrice(boolean IsCallBackPrice) {
            this.IsCallBackPrice = IsCallBackPrice;
        }

        public boolean isIs_czprice() {
            return is_czprice;
        }

        public void setIs_czprice(boolean is_czprice) {
            this.is_czprice = is_czprice;
        }

        public int getModifyloantype() {
            return modifyloantype;
        }

        public void setModifyloantype(int modifyloantype) {
            this.modifyloantype = modifyloantype;
        }

        public Object getModifyloantypeName() {
            return modifyloantypeName;
        }

        public void setModifyloantypeName(Object modifyloantypeName) {
            this.modifyloantypeName = modifyloantypeName;
        }

        public String getAccdient_content() {
            return accdient_content;
        }

        public void setAccdient_content(String accdient_content) {
            this.accdient_content = accdient_content;
        }

        public List<FileInfoBean> getFile_info() {
            return file_info;
        }

        public void setFile_info(List<FileInfoBean> file_info) {
            this.file_info = file_info;
        }

        public static class FileInfoBean {
            private int seq;
            private String url;
            private String minurl;
            private String tag;
            private int itype;

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getMinurl() {
                return minurl;
            }

            public void setMinurl(String minurl) {
                this.minurl = minurl;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public int getItype() {
                return itype;
            }

            public void setItype(int itype) {
                this.itype = itype;
            }
        }
    }
}
