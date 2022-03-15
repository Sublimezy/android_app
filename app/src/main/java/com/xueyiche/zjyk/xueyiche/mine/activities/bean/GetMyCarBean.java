package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

/**
 * Created by ZL on 2019/7/18.
 */
public class GetMyCarBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"licenseno":"123","enginenumber":"123","vin":"123"}
     */

    private String msg;
    private int code;
    private ContentBean content;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * licenseno : 123
         * enginenumber : 123
         * vin : 123
         */

        private String licenseno;
        private String enginenumber;
        private String vin;

        public String getLicenseno() {
            return licenseno;
        }

        public void setLicenseno(String licenseno) {
            this.licenseno = licenseno;
        }

        public String getEnginenumber() {
            return enginenumber;
        }

        public void setEnginenumber(String enginenumber) {
            this.enginenumber = enginenumber;
        }

        public String getVin() {
            return vin;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }
    }
}
