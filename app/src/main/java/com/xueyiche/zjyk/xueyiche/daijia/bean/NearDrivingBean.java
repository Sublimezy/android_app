package com.xueyiche.zjyk.xueyiche.daijia.bean;

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
 * #            Created by 張某人 on 2022/5/26/9:55 上午 .
 * #            com.xueyiche.zjyk.xueyiche.daijia.bean
 * #            xueyiche5.0
 */
public class NearDrivingBean {

    /**
     * code : 1
     * msg : 请求成功
     * time : 1653530096
     * data : {"user_list":[{"user_id":7,"driving_status":2,"name":"QC","head_img":"http://xychead.xueyiche.vip/0_1648626549558","mobile":"15546312273","user_lng":"126.476990","user_lat":"45.808465","juli":0.01},{"user_id":4,"driving_status":2,"name":"蒙奇.D.路飞","head_img":"http://xychead.xueyiche.vip/fx_tuwen1648868958349.jpg","mobile":"18346012117","user_lng":"126.47737035265094","user_lat":"45.808465902436396","juli":0.03}],"total":2}
     */

    private int code;
    private String msg;
    private String time;
    /**
     * user_list : [{"user_id":7,"driving_status":2,"name":"QC","head_img":"http://xychead.xueyiche.vip/0_1648626549558","mobile":"15546312273","user_lng":"126.476990","user_lat":"45.808465","juli":0.01},{"user_id":4,"driving_status":2,"name":"蒙奇.D.路飞","head_img":"http://xychead.xueyiche.vip/fx_tuwen1648868958349.jpg","mobile":"18346012117","user_lng":"126.47737035265094","user_lat":"45.808465902436396","juli":0.03}]
     * total : 2
     */

    private DataBean data;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int total;
        /**
         * user_id : 7
         * driving_status : 2
         * name : QC
         * head_img : http://xychead.xueyiche.vip/0_1648626549558
         * mobile : 15546312273
         * user_lng : 126.476990
         * user_lat : 45.808465
         * juli : 0.01
         */

        private List<UserListBean> user_list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<UserListBean> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<UserListBean> user_list) {
            this.user_list = user_list;
        }

        public static class UserListBean {
            private int user_id;
            private int driving_status;
            private String name;
            private String head_img;
            private String mobile;
            private String user_lng;
            private String user_lat;
            private double juli;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getDriving_status() {
                return driving_status;
            }

            public void setDriving_status(int driving_status) {
                this.driving_status = driving_status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getUser_lng() {
                return user_lng;
            }

            public void setUser_lng(String user_lng) {
                this.user_lng = user_lng;
            }

            public String getUser_lat() {
                return user_lat;
            }

            public void setUser_lat(String user_lat) {
                this.user_lat = user_lat;
            }

            public double getJuli() {
                return juli;
            }

            public void setJuli(double juli) {
                this.juli = juli;
            }
        }
    }
}
