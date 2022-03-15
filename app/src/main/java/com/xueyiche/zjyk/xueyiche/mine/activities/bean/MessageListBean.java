package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

import java.util.List;

/**
 * Created by Owner on 2018/5/24.
 */
public class MessageListBean {

    /**
     * content : [{"web_url":"www.baidu.com","img_url":"http://otqopw3sl.bkt.clouddn.com/20171018111358","receive_time":"2018-05-23 15:54:18","read_status":"0","title":"消息标题","receive_id":1}]
     * code : 200
     * msg : 操作成功
     */

    private int code;
    private String msg;
    /**
     * web_url : www.baidu.com
     * img_url : http://otqopw3sl.bkt.clouddn.com/20171018111358
     * receive_time : 2018-05-23 15:54:18
     * read_status : 0
     * title : 消息标题
     * receive_id : 1
     */

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
        private String web_url;
        private String img_url;
        private String receive_time;
        private String read_status;
        private String title;
        private int receive_id;

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getReceive_time() {
            return receive_time;
        }

        public void setReceive_time(String receive_time) {
            this.receive_time = receive_time;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getReceive_id() {
            return receive_id;
        }

        public void setReceive_id(int receive_id) {
            this.receive_id = receive_id;
        }
    }
}
