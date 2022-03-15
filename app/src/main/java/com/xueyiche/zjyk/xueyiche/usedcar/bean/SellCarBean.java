package com.xueyiche.zjyk.xueyiche.usedcar.bean;

import java.util.List;

/**
 * Created by Owner on 2018/7/18.
 */
public class SellCarBean {

    /**
     * code : 200
     * message : 信息请求成功
     * data : {"broadcastList":[{"id":1,"title":"轮播图片1","broadcast_pic":"http://car.xueyiche.vip/b1a29201807101644392196.jpg","url":"http://www.baidu.com","content":"轮播图片1","create_time":"2018-07-09"},{"id":2,"title":"轮播图片2","broadcast_pic":"http://car.xueyiche.vip/b1a29201807101644392196.jpg","url":"http://www.baidu.com","content":"轮播图片2","create_time":"2018-07-09"}],"sellCount":1}
     */

    private int code;
    private String message;
    /**
     * broadcastList : [{"id":1,"title":"轮播图片1","broadcast_pic":"http://car.xueyiche.vip/b1a29201807101644392196.jpg","url":"http://www.baidu.com","content":"轮播图片1","create_time":"2018-07-09"},{"id":2,"title":"轮播图片2","broadcast_pic":"http://car.xueyiche.vip/b1a29201807101644392196.jpg","url":"http://www.baidu.com","content":"轮播图片2","create_time":"2018-07-09"}]
     * sellCount : 1
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int sellCount;
        /**
         * id : 1
         * title : 轮播图片1
         * broadcast_pic : http://car.xueyiche.vip/b1a29201807101644392196.jpg
         * url : http://www.baidu.com
         * content : 轮播图片1
         * create_time : 2018-07-09
         */

        private List<BroadcastListBean> broadcastList;

        public int getSellCount() {
            return sellCount;
        }

        public void setSellCount(int sellCount) {
            this.sellCount = sellCount;
        }

        public List<BroadcastListBean> getBroadcastList() {
            return broadcastList;
        }

        public void setBroadcastList(List<BroadcastListBean> broadcastList) {
            this.broadcastList = broadcastList;
        }

        public static class BroadcastListBean {
            private int id;
            private String title;
            private String broadcast_pic;
            private String url;
            private String content;
            private String create_time;

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

            public String getBroadcast_pic() {
                return broadcast_pic;
            }

            public void setBroadcast_pic(String broadcast_pic) {
                this.broadcast_pic = broadcast_pic;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
