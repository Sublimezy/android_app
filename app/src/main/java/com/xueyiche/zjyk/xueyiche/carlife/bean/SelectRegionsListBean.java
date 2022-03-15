package com.xueyiche.zjyk.xueyiche.carlife.bean;

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
 * #            Created by 張某人 on 8/16/21/10:15 AM .
 * #            com.xueyiche.zjyk.xueyiche.carlife.bean
 * #            xueyiche4.0
 */
public class SelectRegionsListBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"area_name":"南岗区","open_area_id":3},{"area_name":"道里区","open_area_id":4},{"area_name":"香坊区","open_area_id":5},{"area_name":"道外区","open_area_id":7},{"area_name":"松北区","open_area_id":8},{"area_name":"平房区","open_area_id":9},{"area_name":"阿城区","open_area_id":10},{"area_name":"双城区","open_area_id":11},{"area_name":"呼兰区","open_area_id":12},{"area_name":"宾县","open_area_id":13},{"area_name":"五常市","open_area_id":14}]
     */

    private String msg;
    private int code;
    /**
     * area_name : 南岗区
     * open_area_id : 3
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
        private String area_name;
        private int open_area_id;

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public int getOpen_area_id() {
            return open_area_id;
        }

        public void setOpen_area_id(int open_area_id) {
            this.open_area_id = open_area_id;
        }
    }
}
