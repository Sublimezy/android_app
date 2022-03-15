package com.xueyiche.zjyk.xueyiche.practicecar.bean;

import java.util.List;

/**
 * Created by ZL on 2019/3/20.
 */
public class OrderPracticeBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"driver_name":"尹杰飞","driver_id":"1af476a3506e11e98bbd5254036244cf","user_id":"6f44f0e3bae711e88bbd5254036244cf","yuyueList":[{"xlxm":"直角转弯, 倒车入库","km":"2","release_num":5,"jihua_date":"2019-03-29","id":20,"order_num":0,"status":"待预约"},{"xlxm":"直角转弯, 侧方位停车","km":"2","release_num":8,"jihua_date":"2019-03-30","id":21,"order_num":0,"status":"待预约"}],"head_img":"http://xychead.xueyiche.vip/xyc_driverschool_1553676929945_head.jpg"}
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
         * driver_name : 尹杰飞
         * driver_id : 1af476a3506e11e98bbd5254036244cf
         * user_id : 6f44f0e3bae711e88bbd5254036244cf
         * yuyueList : [{"xlxm":"直角转弯, 倒车入库","km":"2","release_num":5,"jihua_date":"2019-03-29","id":20,"order_num":0,"status":"待预约"},{"xlxm":"直角转弯, 侧方位停车","km":"2","release_num":8,"jihua_date":"2019-03-30","id":21,"order_num":0,"status":"待预约"}]
         * head_img : http://xychead.xueyiche.vip/xyc_driverschool_1553676929945_head.jpg
         */

        private String driver_name;
        private String driver_id;
        private String user_id;
        private String head_img;
        private List<YuyueListBean> yuyueList;

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public List<YuyueListBean> getYuyueList() {
            return yuyueList;
        }

        public void setYuyueList(List<YuyueListBean> yuyueList) {
            this.yuyueList = yuyueList;
        }

        public static class YuyueListBean {
            /**
             * xlxm : 直角转弯, 倒车入库
             * km : 2
             * release_num : 5
             * jihua_date : 2019-03-29
             * id : 20
             * order_num : 0
             * status : 待预约
             */

            private String xlxm;
            private String km;
            private int release_num;
            private String jihua_date;
            private int id;
            private int order_num;
            private String status;

            public String getXlxm() {
                return xlxm;
            }

            public void setXlxm(String xlxm) {
                this.xlxm = xlxm;
            }

            public String getKm() {
                return km;
            }

            public void setKm(String km) {
                this.km = km;
            }

            public int getRelease_num() {
                return release_num;
            }

            public void setRelease_num(int release_num) {
                this.release_num = release_num;
            }

            public String getJihua_date() {
                return jihua_date;
            }

            public void setJihua_date(String jihua_date) {
                this.jihua_date = jihua_date;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
