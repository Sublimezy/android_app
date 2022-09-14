package com.xueyiche.zjyk.xueyiche.driverschool.bean;

import java.util.List;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 6/22/21/4:35 PM .
 * #            com.xueyiche.zjyk.xueyiche.driverschool.bean
 * #            xueyiche3.0
 */
public class MiJIListBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"secret_img_url":"111","secret_video_url":"11","id":1,"title":"11"}]
     */

    private String msg;
    private int code;
    /**
     * secret_img_url : 111
     * secret_video_url : 11
     * id : 1
     * title : 11
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
        private String secret_img_url;
        private String secret_video_url;
        private int id;
        private String title;

        public String getSecret_img_url() {
            return secret_img_url;
        }

        public void setSecret_img_url(String secret_img_url) {
            this.secret_img_url = secret_img_url;
        }

        public String getSecret_video_url() {
            return secret_video_url;
        }

        public void setSecret_video_url(String secret_video_url) {
            this.secret_video_url = secret_video_url;
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
