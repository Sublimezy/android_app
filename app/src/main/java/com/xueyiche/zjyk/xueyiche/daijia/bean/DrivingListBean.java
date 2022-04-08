package com.xueyiche.zjyk.xueyiche.daijia.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.daijia.bean
 * @ClassName: DrivingListBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/4/8 15:45
 */
public class DrivingListBean {

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
        private int total;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            private String name;
            private String head_img;
            private String user_number;
            private String driving_id;
            private String juli;

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

            public String getUser_number() {
                return user_number;
            }

            public void setUser_number(String user_number) {
                this.user_number = user_number;
            }

            public String getDriving_id() {
                return driving_id;
            }

            public void setDriving_id(String driving_id) {
                this.driving_id = driving_id;
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
