package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

import java.util.List;

/**
 * Created by ZL on 2018/4/14.
 */
public class JiFenBean {

    /**
     * content : {"records":[{"system_time":"2018-03-26 10:22:38","detail_num":"+10000","integral_type":"注册返积分"},{"system_time":"2018-04-13 18:18:18","detail_num":"+10000","integral_type":"充值返积分"}],"integral_num":20000}
     * code : 200
     * msg : 操作成功
     */

    private ContentBean content;
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
        /**
         * records : [{"system_time":"2018-03-26 10:22:38","detail_num":"+10000","integral_type":"注册返积分"},{"system_time":"2018-04-13 18:18:18","detail_num":"+10000","integral_type":"充值返积分"}]
         * integral_num : 20000
         */

        private int integral_num;
        private List<RecordsBean> records;

        public int getIntegral_num() {
            return integral_num;
        }

        public void setIntegral_num(int integral_num) {
            this.integral_num = integral_num;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            /**
             * system_time : 2018-03-26 10:22:38
             * detail_num : +10000
             * integral_type : 注册返积分
             */

            private String system_time;
            private String detail_num;
            private String integral_type;

            public String getSystem_time() {
                return system_time;
            }

            public void setSystem_time(String system_time) {
                this.system_time = system_time;
            }

            public String getDetail_num() {
                return detail_num;
            }

            public void setDetail_num(String detail_num) {
                this.detail_num = detail_num;
            }

            public String getIntegral_type() {
                return integral_type;
            }

            public void setIntegral_type(String integral_type) {
                this.integral_type = integral_type;
            }
        }
    }
}
