package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.util.List;

/**
 * Created by Owner on 2016/11/3.
 */
public class LunBoShouYeBean {


    /**
     * content : [{"address":"http://p2bualq62.bkt.clouddn.com/image/jpg/volution01.jpg","sub_title":"副标题1","web_url":"","refer_id":"","carlife_type":18,"type":"image","volution_type":"0","volution_id":1,"main_title":"主标题1"},{"address":"http://p2bualq62.bkt.clouddn.com/image/jpg/volution02.jpg","sub_title":"副标题2","web_url":"","refer_id":"","carlife_type":18,"type":"image","volution_type":"0","volution_id":2,"main_title":"主标题2"},{"address":"http://p2bualq62.bkt.clouddn.com/image/jpg/volution03.jpg","sub_title":"副标题3","web_url":"","refer_id":"","carlife_type":18,"type":"image","volution_type":"0","volution_id":3,"main_title":"主标题3"}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * address : http://p2bualq62.bkt.clouddn.com/image/jpg/volution01.jpg
         * sub_title : 副标题1
         * web_url :
         * refer_id :
         * carlife_type : 18
         * type : image
         * volution_type : 0
         * volution_id : 1
         * main_title : 主标题1
         */

        private String address;
        private String sub_title;
        private String web_url;
        private String refer_id;
        private int carlife_type;
        private String type;
        private String volution_type;
        private int volution_id;
        private String main_title;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public String getRefer_id() {
            return refer_id;
        }

        public void setRefer_id(String refer_id) {
            this.refer_id = refer_id;
        }

        public int getCarlife_type() {
            return carlife_type;
        }

        public void setCarlife_type(int carlife_type) {
            this.carlife_type = carlife_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVolution_type() {
            return volution_type;
        }

        public void setVolution_type(String volution_type) {
            this.volution_type = volution_type;
        }

        public int getVolution_id() {
            return volution_id;
        }

        public void setVolution_id(int volution_id) {
            this.volution_id = volution_id;
        }

        public String getMain_title() {
            return main_title;
        }

        public void setMain_title(String main_title) {
            this.main_title = main_title;
        }
    }
}
