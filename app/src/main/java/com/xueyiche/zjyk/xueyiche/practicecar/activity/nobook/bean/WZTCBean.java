package com.xueyiche.zjyk.xueyiche.practicecar.activity.nobook.bean;

import java.util.List;

/**
 * Created by ZL on 2018/11/26.
 */
public class WZTCBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"hour_price":150,"remark":"科目二"},{"hour_price":200,"remark":"科目三"},{"hour_price":450,"remark":"套餐（3小时价：包过）"}]
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
         * hour_price : 150
         * remark : 科目二
         */

        private String hour_price;
        private String remark;

        public void setHour_price(String hour_price) {
            this.hour_price = hour_price;
        }

        public String getHour_price() {
            return hour_price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
