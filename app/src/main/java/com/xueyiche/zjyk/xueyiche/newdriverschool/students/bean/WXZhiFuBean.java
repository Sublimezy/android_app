package com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean;

/**
 * Created by Owner on 2017/1/24.
 */
public class WXZhiFuBean {

    /**
     * sign : C64FDA2DA93EADEC532D393B0134648A
     * timestamp : 1485249338
     * noncestr : qper1hgz3apr2gngtv3klzgdgm7gcul3
     * partnerid : 1413045602
     * prepayid : wx201701241715362a7d54817d0663195977
     * package : Sign=WXPay
     * appid : wx2c016c0ed633dbd2
     */

    private ContentBean content;
    /**
     * content : {"sign":"C64FDA2DA93EADEC532D393B0134648A","timestamp":"1485249338","noncestr":"qper1hgz3apr2gngtv3klzgdgm7gcul3","partnerid":"1413045602","prepayid":"wx201701241715362a7d54817d0663195977","package":"Sign=WXPay","appid":"wx2c016c0ed633dbd2"}
     * code : 0
     * msg : 操作成功
     */

    private int code;
    private String msg;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
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

    public static class ContentBean {
        private String sign;
        private String timestamp;
        private String noncestr;
        private String partnerid;
        private String prepayid;
        private String packageValue;
        private String appid;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }
    }
}
