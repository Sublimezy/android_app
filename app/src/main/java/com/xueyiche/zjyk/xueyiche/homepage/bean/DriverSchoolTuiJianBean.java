package com.xueyiche.zjyk.xueyiche.homepage.bean;

/**
 * Created by Owner on 2017/8/18.
 */
public class DriverSchoolTuiJianBean {


    /**
     * recommend_id : 0a39c96b83e811e7853c5254036244cf
     * recommender_name : 唐云峰
     */

    private ContentBean content;
    /**
     * content : {"recommend_id":"0a39c96b83e811e7853c5254036244cf","recommender_name":"唐云峰"}
     * code : 0
     * msg : 操作成功
     */

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
        private String recommend_id;
        private String recommender_name;

        public String getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(String recommend_id) {
            this.recommend_id = recommend_id;
        }

        public String getRecommender_name() {
            return recommender_name;
        }

        public void setRecommender_name(String recommender_name) {
            this.recommender_name = recommender_name;
        }
    }
}
