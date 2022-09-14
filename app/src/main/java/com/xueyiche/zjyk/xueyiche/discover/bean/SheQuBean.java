package com.xueyiche.zjyk.xueyiche.discover.bean;

import java.util.List;

/**
 * Created by Owner on 2018/3/27.
 */
public class SheQuBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"comment_count":0,"image":"http://images.xueyiche.vip/8e846f39b4fa4e16b98ebfc9bf3b56f9.jpg","system_time":"2018-03-19 16:14:31","information_from":"学易车","praise_count":0,"id":35,"title":"准备学车的都看看吧，今年起，只有满足这些条件，才能参加驾考"},{"comment_count":0,"image":"http://images.xueyiche.vip/8e846f39b4fa4e16b98ebfc9bf3b56f9.jpg","system_time":"2018-03-19 16:12:25","information_from":"学易车","praise_count":1,"id":34,"title":"掌握制动技巧，驾车不再点头"},{"comment_count":0,"image":"http://images.xueyiche.vip/8e846f39b4fa4e16b98ebfc9bf3b56f9.jpg","system_time":"2018-03-19 16:10:49","information_from":"学易车","praise_count":1,"id":33,"title":"新规科目三这些地方容易挂，先别急着考试（考前收藏）"},{"comment_count":0,"image":"http://images.xueyiche.vip/8e846f39b4fa4e16b98ebfc9bf3b56f9.jpg","system_time":"2018-03-19 16:10:07","information_from":"学易车","praise_count":0,"id":32,"title":"为什么老司机也要避让绑着红布条的新车？"},{"comment_count":0,"image":"http://images.xueyiche.vip/8e846f39b4fa4e16b98ebfc9bf3b56f9.jpg","system_time":"2018-03-19 16:09:23","information_from":"学易车","praise_count":2,"id":31,"title":"如何用离合控制车速？"}]
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
         * comment_count : 0
         * image : http://images.xueyiche.vip/8e846f39b4fa4e16b98ebfc9bf3b56f9.jpg
         * system_time : 2018-03-19 16:14:31
         * information_from : 学易车
         * praise_count : 0
         * id : 35
         * title : 准备学车的都看看吧，今年起，只有满足这些条件，才能参加驾考
         */

        private int comment_count;
        private String image;
        private String system_time;
        private String information_from;
        private int praise_count;
        private int id;
        private String title;

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSystem_time() {
            return system_time;
        }

        public void setSystem_time(String system_time) {
            this.system_time = system_time;
        }

        public String getInformation_from() {
            return information_from;
        }

        public void setInformation_from(String information_from) {
            this.information_from = information_from;
        }

        public int getPraise_count() {
            return praise_count;
        }

        public void setPraise_count(int praise_count) {
            this.praise_count = praise_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
