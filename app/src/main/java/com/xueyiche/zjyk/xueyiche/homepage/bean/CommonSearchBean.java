package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * Created by ZL on 2018/1/24.
 */
public class CommonSearchBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"id":1,"service_type_name":"周边服务","carLifeServiceTypeForSearch":[{"service_id":12,"service_name":"加油站"},{"service_id":13,"service_name":"监测站"},{"service_id":14,"service_name":"停车场"},{"service_id":15,"service_name":"交警队"},{"service_id":16,"service_name":"医院"}]},{"id":2,"service_type_name":"洗车服务","carLifeServiceTypeForSearch":[{"service_id":1,"service_name":"标准洗车"},{"service_id":3,"service_name":"内饰清洗"},{"service_id":4,"service_name":"全车精洗"},{"service_id":5,"service_name":"全车打蜡"},{"service_id":6,"service_name":"全车镀晶"},{"service_id":8,"service_name":"全车贴膜"},{"service_id":24,"service_name":"底盘装甲"}]},{"id":3,"service_type_name":"汽车交易","carLifeServiceTypeForSearch":[{"service_id":32,"service_name":"新车报价"},{"service_id":33,"service_name":"二手车"}]},{"id":4,"service_type_name":"维修保养","carLifeServiceTypeForSearch":[{"service_id":2,"service_name":"维修保养"},{"service_id":31,"service_name":"4S店"}]},{"id":5,"service_type_name":"轮胎服务","carLifeServiceTypeForSearch":[{"service_id":25,"service_name":"轮胎服务"}]},{"id":7,"service_type_name":"汇吃汇玩","carLifeServiceTypeForSearch":[{"service_id":9,"service_name":"美食"},{"service_id":10,"service_name":"KTV"},{"service_id":11,"service_name":"酒店"}]},{"id":8,"service_type_name":"精品用品","carLifeServiceTypeForSearch":[{"service_id":26,"service_name":"精品用品"}]},{"id":9,"service_type_name":"工银金融服务","carLifeServiceTypeForSearch":[{"service_id":27,"service_name":"融e购"},{"service_id":28,"service_name":"融e行"},{"service_id":29,"service_name":"融e联"},{"service_id":30,"service_name":"工银e校园"}]}]
     */

    private String msg;
    private int code;
    private List<ContentBean> content;

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
         * carLifeServiceTypeForSearch : [{"service_id":12,"service_name":"加油站"},{"service_id":13,"service_name":"监测站"},{"service_id":14,"service_name":"停车场"},{"service_id":15,"service_name":"交警队"},{"service_id":16,"service_name":"医院"}]
         */

        private int id;
        private String service_type_name;
        private List<CarLifeServiceTypeForSearchBean> carLifeServiceTypeForSearch;

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

        public List<CarLifeServiceTypeForSearchBean> getCarLifeServiceTypeForSearch() {
            return carLifeServiceTypeForSearch;
        }

        public void setCarLifeServiceTypeForSearch(List<CarLifeServiceTypeForSearchBean> carLifeServiceTypeForSearch) {
            this.carLifeServiceTypeForSearch = carLifeServiceTypeForSearch;
        }

        public static class CarLifeServiceTypeForSearchBean {
            /**
             * service_id : 12
             * service_name : 加油站
             */

            private int service_id;
            private String service_name;

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
        }
    }
}
