package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  16/5/24/下午4:33
 * DESC:
 */
public class CarLifeServiceBean {


    /**
     * content : [{"id":1,"service_type_name":"周边服务","carLifeServiceTypes":[{"service_id":12,"service_name":"加油站","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":13,"service_name":"监测站","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":14,"service_name":"停车场","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":15,"service_name":"车管所","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":16,"service_name":"交警队","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"}]},{"id":2,"service_type_name":"洗车服务","carLifeServiceTypes":[{"service_id":1,"service_name":"标准洗车","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":3,"service_name":"内饰清洗","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":5,"service_name":"全车打蜡","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"}]},{"id":3,"service_type_name":"维修保养","carLifeServiceTypes":[{"service_id":2,"service_name":"保养维修","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":4,"service_name":"钣金喷漆","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":6,"service_name":"美容养护","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":8,"service_name":"4S店","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"}]},{"id":4,"service_type_name":"汇吃汇玩","carLifeServiceTypes":[{"service_id":9,"service_name":"美食","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":10,"service_name":"KTV","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":11,"service_name":"酒店","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"}]}]
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
         * id : 1
         * service_type_name : 周边服务
         * carLifeServiceTypes : [{"service_id":12,"service_name":"加油站","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":13,"service_name":"监测站","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":14,"service_name":"停车场","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":15,"service_name":"车管所","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"},{"service_id":16,"service_name":"交警队","service_url":"http://images.xueyiche.vip/gonghang_720x1280.png","web_skip":"0"}]
         */

        private int id;
        private String service_type_name;
        private List<CarLifeServiceTypesBean> carLifeServiceTypes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getService_type_name() {
            return service_type_name;
        }

        public void setService_type_name(String service_type_name) {
            this.service_type_name = service_type_name;
        }

        public List<CarLifeServiceTypesBean> getCarLifeServiceTypes() {
            return carLifeServiceTypes;
        }

        public void setCarLifeServiceTypes(List<CarLifeServiceTypesBean> carLifeServiceTypes) {
            this.carLifeServiceTypes = carLifeServiceTypes;
        }

        public static class CarLifeServiceTypesBean {
            /**
             * service_id : 12
             * service_name : 加油站
             * service_url : http://images.xueyiche.vip/gonghang_720x1280.png
             * web_skip : 0
             */

            private int service_id;
            private String service_name;
            private String service_url;
            private String web_url;
            private String web_skip;

            public String getWeb_url() {
                return web_url;
            }

            public void setWeb_url(String web_url) {
                this.web_url = web_url;
            }

            public int getService_id() {
                return service_id;
            }

            public void setService_id(int service_id) {
                this.service_id = service_id;
            }

            public String getService_name() {
                return service_name;
            }

            public void setService_name(String service_name) {
                this.service_name = service_name;
            }

            public String getService_url() {
                return service_url;
            }

            public void setService_url(String service_url) {
                this.service_url = service_url;
            }

            public String getWeb_skip() {
                return web_skip;
            }

            public void setWeb_skip(String web_skip) {
                this.web_skip = web_skip;
            }
        }
    }
}
