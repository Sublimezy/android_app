package com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.bean;

import java.util.List;

/**
 * Created by Owner on 2019/6/6.
 */
public class PingGuNoBean {

    /**
     * success : 1
     * message : {"id":200002385,"gid":"7d31c266396c4ee0ac1950c9c0bb5a94    ","tradeprice":0,"stockplace":"哈尔滨","loantype":"常规产品","reject_time":"2019-06-11 15:46:24","reject_msg":"驳回 [行驶证正面]，原因：行驶证照片非实体拍摄;\n","shotplanid":387,"data":[{"fileid":24331104,"url":"http://gznfs.oss-cn-hangzhou.aliyuncs.com/200002385/7d31c266396c4ee0ac1950c9c0bb5a94_3.vip/20190611145051","tag":"行驶证正面","rejectreason":"行驶证照片非实体拍摄;","mdate":"2019-06-11 15:00:20","sn":3,"itype":1,"islocal":0,"ineed":1,"ibackground":"http://gzss.oss-cn-qingdao.aliyuncs.com/shapes/7ea7bbc2-a136-4b83-bb4e-ca48ee7679e7.png","iexample":"http://gzss.oss-cn-qingdao.aliyuncs.com/shapes/a00efe05-6345-4bda-9a1d-5d0b0cdaa8be.png"}]}
     */

    private int success;
    private MessageBean message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * id : 200002385
         * gid : 7d31c266396c4ee0ac1950c9c0bb5a94
         * tradeprice : 0
         * stockplace : 哈尔滨
         * loantype : 常规产品
         * reject_time : 2019-06-11 15:46:24
         * reject_msg : 驳回 [行驶证正面]，原因：行驶证照片非实体拍摄;

         * shotplanid : 387
         * data : [{"fileid":24331104,"url":"http://gznfs.oss-cn-hangzhou.aliyuncs.com/200002385/7d31c266396c4ee0ac1950c9c0bb5a94_3.vip/20190611145051","tag":"行驶证正面","rejectreason":"行驶证照片非实体拍摄;","mdate":"2019-06-11 15:00:20","sn":3,"itype":1,"islocal":0,"ineed":1,"ibackground":"http://gzss.oss-cn-qingdao.aliyuncs.com/shapes/7ea7bbc2-a136-4b83-bb4e-ca48ee7679e7.png","iexample":"http://gzss.oss-cn-qingdao.aliyuncs.com/shapes/a00efe05-6345-4bda-9a1d-5d0b0cdaa8be.png"}]
         */

        private int id;
        private String gid;
        private int tradeprice;
        private String stockplace;
        private String loantype;
        private String reject_time;
        private String reject_msg;
        private int shotplanid;
        private List<DataBean> data;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public int getTradeprice() {
            return tradeprice;
        }

        public void setTradeprice(int tradeprice) {
            this.tradeprice = tradeprice;
        }

        public String getStockplace() {
            return stockplace;
        }

        public void setStockplace(String stockplace) {
            this.stockplace = stockplace;
        }

        public String getLoantype() {
            return loantype;
        }

        public void setLoantype(String loantype) {
            this.loantype = loantype;
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

        public int getShotplanid() {
            return shotplanid;
        }

        public void setShotplanid(int shotplanid) {
            this.shotplanid = shotplanid;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * fileid : 24331104
             * url : http://gznfs.oss-cn-hangzhou.aliyuncs.com/200002385/7d31c266396c4ee0ac1950c9c0bb5a94_3.vip/20190611145051
             * tag : 行驶证正面
             * rejectreason : 行驶证照片非实体拍摄;
             * mdate : 2019-06-11 15:00:20
             * sn : 3
             * itype : 1
             * islocal : 0
             * ineed : 1
             * ibackground : http://gzss.oss-cn-qingdao.aliyuncs.com/shapes/7ea7bbc2-a136-4b83-bb4e-ca48ee7679e7.png
             * iexample : http://gzss.oss-cn-qingdao.aliyuncs.com/shapes/a00efe05-6345-4bda-9a1d-5d0b0cdaa8be.png
             */

            private int fileid;
            private String url;
            private String tag;
            private String rejectreason;
            private String mdate;
            private int sn;
            private int itype;
            private int islocal;
            private int ineed;
            private String ibackground;
            private String iexample;

            public int getFileid() {
                return fileid;
            }

            public void setFileid(int fileid) {
                this.fileid = fileid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getRejectreason() {
                return rejectreason;
            }

            public void setRejectreason(String rejectreason) {
                this.rejectreason = rejectreason;
            }

            public String getMdate() {
                return mdate;
            }

            public void setMdate(String mdate) {
                this.mdate = mdate;
            }

            public int getSn() {
                return sn;
            }

            public void setSn(int sn) {
                this.sn = sn;
            }

            public int getItype() {
                return itype;
            }

            public void setItype(int itype) {
                this.itype = itype;
            }

            public int getIslocal() {
                return islocal;
            }

            public void setIslocal(int islocal) {
                this.islocal = islocal;
            }

            public int getIneed() {
                return ineed;
            }

            public void setIneed(int ineed) {
                this.ineed = ineed;
            }

            public String getIbackground() {
                return ibackground;
            }

            public void setIbackground(String ibackground) {
                this.ibackground = ibackground;
            }

            public String getIexample() {
                return iexample;
            }

            public void setIexample(String iexample) {
                this.iexample = iexample;
            }
        }
    }
}
