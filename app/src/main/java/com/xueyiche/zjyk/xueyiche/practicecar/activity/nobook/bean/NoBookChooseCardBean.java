package com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.bean;

import java.util.List;

/**
 * Created by ZL on 2018/11/19.
 */
public class NoBookChooseCardBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"driver_name":"郭瑞成","driver_id":"345bc872c2ca44c09f6f427818361f1d","driver_school_name":"环球驾校","order_count":"已售 0","head_img":"http://images.xueyiche.vip/20180726162521372.jpg"},{"driver_name":"刘士杰","driver_id":"76ed0c3160064e56aafc722dd06c172c","driver_school_name":"警苑驾校（江南校区）","order_count":"已售 0","head_img":"http://images.xueyiche.vip/20180725141951855.jpg"},{"driver_name":"杨波","driver_id":"e19aaabba83b4c8bb83282392223795b","driver_school_name":"环球驾校","order_count":"已售 0","head_img":"http://images.xueyiche.vip/20180726163601261.jpg"},{"driver_name":"张洪岩","driver_id":"6be85d2825e046719afc103ae2edc791","driver_school_name":"太阳岛驾校","order_count":"已售 0","head_img":"http://images.xueyiche.vip/20180727134136563.jpg"},{"driver_name":"张洪亮","driver_id":"b45505c03e86462aa14d2ae8188d25ad","driver_school_name":"警苑驾校（江北校区）","order_count":"已售 0","head_img":"http://images.xueyiche.vip/20180726105718879.jpg"},{"driver_name":"唐宪伟","driver_id":"0b9ac7408df343eeafc8ccade3e0a4b5","driver_school_name":"警苑驾校（江北校区）","order_count":"已售 0","head_img":"http://images.xueyiche.vip/20180726130439480.jpg"},{"driver_name":"唐宪伟","driver_id":"480674be611740999e0f14f16f8245fb","driver_school_name":"警苑驾校（江南校区）","order_count":"已售 0","head_img":"http://images.xueyiche.vip/20180726125948601.jpg"},{"driver_name":"国春明","driver_id":"8dab950fdc1746809d87e9988486547f","driver_school_name":"金朋驾校","order_count":"已售 0","head_img":"http://images.xueyiche.vip/20180724154320027.jpg"}]
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
         * driver_name : 郭瑞成
         * driver_id : 345bc872c2ca44c09f6f427818361f1d
         * driver_school_name : 环球驾校
         * order_count : 已售 0
         * head_img : http://images.xueyiche.vip/20180726162521372.jpg
         */

        private String driver_name;
        private String driver_id;
        private String driver_school_name;
        private String order_count;
        private String head_img;

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

        public String getDriver_school_name() {
            return driver_school_name;
        }

        public void setDriver_school_name(String driver_school_name) {
            this.driver_school_name = driver_school_name;
        }

        public String getOrder_count() {
            return order_count;
        }

        public void setOrder_count(String order_count) {
            this.order_count = order_count;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }
    }
}
