package com.xueyiche.zjyk.xueyiche.practicecar.bean;

import java.util.List;

/**
 * Created by ZL on 2018/3/13.
 */
public class PracticeContentBean {

    /**
     * content : [{"purpose_item_id":1,"purpose_item_name":"直角倒车"},{"purpose_item_id":2,"purpose_item_name":"路口掉头"},{"purpose_item_id":3,"purpose_item_name":"侧位停车"},{"purpose_item_id":4,"purpose_item_name":"坡点起步"},{"purpose_item_id":5,"purpose_item_name":"直角转弯"},{"purpose_item_id":6,"purpose_item_name":"出入转盘"},{"purpose_item_id":7,"purpose_item_name":"闹市区行驶"},{"purpose_item_id":8,"purpose_item_name":"变道行驶"},{"purpose_item_id":9,"purpose_item_name":"匀速跟车"},{"purpose_item_id":10,"purpose_item_name":"变道超车"},{"purpose_item_id":11,"purpose_item_name":"模拟高速"}]
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
         * purpose_item_id : 1
         * purpose_item_name : 直角倒车
         */

        private int purpose_item_id;
        private String purpose_item_name;

        public int getPurpose_item_id() {
            return purpose_item_id;
        }

        public void setPurpose_item_id(int purpose_item_id) {
            this.purpose_item_id = purpose_item_id;
        }

        public String getPurpose_item_name() {
            return purpose_item_name;
        }

        public void setPurpose_item_name(String purpose_item_name) {
            this.purpose_item_name = purpose_item_name;
        }
    }
}
