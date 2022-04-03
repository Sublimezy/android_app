package com.xueyiche.zjyk.xueyiche.daijia.bean;

import java.util.List;

public
/**
 *
 * @Package: com.example.administrator.xuyiche_daijia.bean
 * @ClassName: NearbyBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/4/1 14:42
 */
class NearbyBean {

    private int code;
    private String msg;
    private String time;
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
        private List<UserListBean> user_list;
        private String total;

        public List<UserListBean> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<UserListBean> user_list) {
            this.user_list = user_list;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public static class UserListBean {
            private String user_id;
            private String driving_status;
            private String name;
            private String head_img;
            private String mobile;
            private String user_lng;
            private String user_lat;
            private String juli;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getDriving_status() {
                return driving_status;
            }

            public void setDriving_status(String driving_status) {
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

            public String getJuli() {
                return juli;
            }

            public void setJuli(String juli) {
                this.juli = juli;
            }
        }
    }
}
