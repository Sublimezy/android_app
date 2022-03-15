package com.xueyiche.zjyk.xueyiche.xycindent.bean;

import java.util.List;

/**
 * Created by ZL on 2018/5/10.
 */
public class PingJiaBean {

    /**
     * content : {"head_url":"http://images.xueyiche.vip/Fn-kPDibgs0QJbGojVMuPBOo8geB.jpg.jpg.jpg","refer_id":"cdb83022a3a04ffbb0d60686bc07b922","name":"环球驾校","place":"哈尔滨市道里区河鼓街副24-20号","evaluate":[{"evaluate_id":1,"evaluate_name":"驾校标签1"},{"evaluate_id":2,"evaluate_name":"驾校标签2"},{"evaluate_id":3,"evaluate_name":"驾校标签3"},{"evaluate_id":4,"evaluate_name":"驾校标签4"},{"evaluate_id":5,"evaluate_name":"驾校标签5"}]}
     * code : 200
     * msg : 操作成功
     */

    private ContentBean content;
    private int code;
    private String msg;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

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

    public static class ContentBean {
        /**
         * head_url : http://images.xueyiche.vip/Fn-kPDibgs0QJbGojVMuPBOo8geB.jpg.jpg.jpg
         * refer_id : cdb83022a3a04ffbb0d60686bc07b922
         * name : 环球驾校
         * place : 哈尔滨市道里区河鼓街副24-20号
         * evaluate : [{"evaluate_id":1,"evaluate_name":"驾校标签1"},{"evaluate_id":2,"evaluate_name":"驾校标签2"},{"evaluate_id":3,"evaluate_name":"驾校标签3"},{"evaluate_id":4,"evaluate_name":"驾校标签4"},{"evaluate_id":5,"evaluate_name":"驾校标签5"}]
         */

        private String head_url;
        private String refer_id;
        private String name;
        private String place;
        private List<EvaluateBean> evaluate;

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getRefer_id() {
            return refer_id;
        }

        public void setRefer_id(String refer_id) {
            this.refer_id = refer_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public List<EvaluateBean> getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(List<EvaluateBean> evaluate) {
            this.evaluate = evaluate;
        }

        public static class EvaluateBean {
            /**
             * evaluate_id : 1
             * evaluate_name : 驾校标签1
             */

            private int evaluate_id;
            private String evaluate_name;

            public int getEvaluate_id() {
                return evaluate_id;
            }

            public void setEvaluate_id(int evaluate_id) {
                this.evaluate_id = evaluate_id;
            }

            public String getEvaluate_name() {
                return evaluate_name;
            }

            public void setEvaluate_name(String evaluate_name) {
                this.evaluate_name = evaluate_name;
            }
        }
    }
}
