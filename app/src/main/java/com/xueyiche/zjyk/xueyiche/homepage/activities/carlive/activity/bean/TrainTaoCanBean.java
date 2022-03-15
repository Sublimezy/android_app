package com.xueyiche.zjyk.xueyiche.homepage.activities.carlive.activity.bean;

import java.util.List;

/**
 * Created by Owner on 2018/11/28.
 */
public class TrainTaoCanBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"pay_price":0.1,"package_name":"公务员套餐一","package_mark":"这是套餐一","id":1},{"pay_price":0.2,"package_name":"公务员套餐二","package_mark":"这是套餐二","id":2},{"pay_price":0.3,"package_name":"公务员套餐三","package_mark":"这是套餐三","id":3}]
     */

    private String msg;
    private int code;
    /**
     * pay_price : 0.1
     * package_name : 公务员套餐一
     * package_mark : 这是套餐一
     * id : 1
     */

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
        private double pay_price;
        private String package_name;
        private String package_mark;
        private int id;

        public double getPay_price() {
            return pay_price;
        }

        public void setPay_price(double pay_price) {
            this.pay_price = pay_price;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getPackage_mark() {
            return package_mark;
        }

        public void setPackage_mark(String package_mark) {
            this.package_mark = package_mark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
