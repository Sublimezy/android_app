package com.xueyiche.zjyk.xueyiche.daijia.bean;

/**
 * Created by Administrator on 2019/10/14.
 */
public class SjLoactionBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"driver_id":"932d51c8c98c11e99ca1525421981681","latitude":"45.804439","longitude":"126.51343"}
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
         * driver_id : 932d51c8c98c11e99ca1525421981681
         * latitude : 45.804439
         * longitude : 126.51343
         */

        private String driver_id;
        private String latitude;
        private String longitude;

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
