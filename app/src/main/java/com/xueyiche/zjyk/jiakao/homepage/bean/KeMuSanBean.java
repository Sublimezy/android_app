package com.xueyiche.zjyk.jiakao.homepage.bean;

import java.util.List;


public class KeMuSanBean {


    /**
     * content : [{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/1%E4%B8%8A%E8%BD%A6%E5%87%86%E5%A4%87%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"1上车准备操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/2%E8%B5%B7%E6%AD%A5%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"2起步操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/3%E7%9B%B4%E7%BA%BF%E8%A1%8C%E9%A9%B6%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"3直线行驶操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/4%E5%B7%A6%E8%BD%AC%E5%BC%AF%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"4左转弯操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/5%E9%80%9A%E8%BF%87%E5%AD%A6%E6%A0%B7%E5%8C%BA%E5%9F%9F%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"5通过学样区域操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/6%E5%8F%B3%E8%BD%AC%E5%BC%AF%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"6右转弯操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/7%E9%80%9A%E8%BF%87%E5%85%AC%E4%BA%A4%E7%AB%99%E5%8F%B0%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"7通过公交站台操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/8%E8%B6%85%E8%BD%A6%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"8超车操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/9%E5%8A%A0%E5%87%8F%E6%A1%A3%E4%BD%8D%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"9加减档位操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/10%E7%9B%B4%E8%A1%8C%E9%80%9A%E8%BF%87%E8%B7%AF%E5%8F%A3%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"10直行通过路口操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/11%E6%8E%89%E5%A4%B4%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"11掉头操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/12%E5%8F%98%E6%9B%B4%E8%BD%A6%E9%81%93%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"12变更车道操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/13%E5%89%8D%E6%96%B9%E8%B7%AF%E5%8F%A3%E7%9B%B4%E8%A1%8C.mp4","subjects_name":"13前方路口直行"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/14%E4%BC%9A%E8%BD%A6%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"14会车操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/15%E8%B7%AF%E5%8F%A3%E5%B7%A6%E8%BD%AC%E5%BC%AF%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"15路口左转弯操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/16%E9%80%9A%E8%BF%87%E4%BA%BA%E8%A1%8C%E6%A8%AA%E9%81%93%E7%BA%BF.mp4","subjects_name":"16通过人行横道线"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/17%E5%8F%B3%E8%BD%AC%E5%BC%AF%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"17右转弯操作方法"},{"img_url":"1","video_url":"http://ojljpzjpm.bkt.clouddn.com/18%E9%9D%A0%E8%BE%B9%E5%81%9C%E8%BD%A6%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4","subjects_name":"18靠边停车操作方法"}]
     * code : 0
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
         * img_url : 1
         * video_url : http://ojljpzjpm.bkt.clouddn.com/1%E4%B8%8A%E8%BD%A6%E5%87%86%E5%A4%87%E6%93%8D%E4%BD%9C%E6%96%B9%E6%B3%95.mp4
         * subjects_name : 1上车准备操作方法
         */

        private String img_url;
        private String video_url;
        private String subjects_name;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getSubjects_name() {
            return subjects_name;
        }

        public void setSubjects_name(String subjects_name) {
            this.subjects_name = subjects_name;
        }
    }
}
