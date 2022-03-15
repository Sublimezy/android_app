package com.xueyiche.zjyk.xueyiche.xycindent.bean;

import java.util.List;

/**
 * Created by Owner on 2018/8/13.
 */
public class DriverSchoolCoachPingJiaBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"driver_name":"王海涛","driver_id":"171dd23ddad94ba2a7b5136c7b052560","head_img":"http://images.xueyiche.vip/20180726162952807.jpg","sex":"女","car_type":"C1车型"},{"driver_name":"郭瑞成","driver_id":"345bc872c2ca44c09f6f427818361f1d","head_img":"http://images.xueyiche.vip/20180726162521372.jpg","sex":"男","car_type":"C1车型"},{"driver_name":"王亮","driver_id":"677d584e7e0747179415e68f9fb69bc8","head_img":"http://images.xueyiche.vip/20180726160020306.jpg","sex":"男","car_type":"C1车型"},{"driver_name":"杨波","driver_id":"e19aaabba83b4c8bb83282392223795b","head_img":"http://images.xueyiche.vip/20180726163601261.jpg","sex":"男","car_type":"C1车型"},{"driver_name":"顾方明","driver_id":"ed970ba1eecd46a2b72c60a53cf5cb93","head_img":"http://images.xueyiche.vip/20180726164055053.jpg","sex":"男","car_type":"C1车型"}]
     */

    private String msg;
    private int code;
    /**
     * driver_name : 王海涛
     * driver_id : 171dd23ddad94ba2a7b5136c7b052560
     * head_img : http://images.xueyiche.vip/20180726162952807.jpg
     * sex : 女
     * car_type : C1车型
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
        private String driver_name;
        private String driver_id;
        private String head_img;
        private String sex;
        private String car_type;
        private String all_evaluate;

        public String getAll_evaluate() {
            return all_evaluate;
        }

        public void setAll_evaluate(String all_evaluate) {
            this.all_evaluate = all_evaluate;
        }

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

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }
    }
}
