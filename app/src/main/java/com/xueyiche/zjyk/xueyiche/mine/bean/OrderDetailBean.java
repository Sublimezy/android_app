package com.xueyiche.zjyk.xueyiche.mine.bean;

public
/**
 *
 * @Package: com.example.administrator.xuyiche_daijia.bean
 * @ClassName: OrderDetailBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/4/2 9:09
 */
class OrderDetailBean {


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
        private String order_sn;
        private String createtime;
        private String start_time;
        private String start_address;
        private String end_address;
        private String end_time;
        private String mobile;
        private String mobile_yin;
        private String qibu_price;
        private String shichang_price;
        private String licheng_price;
        private String neiquyu_km_price;
        private String waiquyu_km_price;
        private String youhui_price;
        private String total_price;
        private String shichang_time;
        private String licheng_km;
        private String neiquyu_km;
        private String waiquyu_km;
        private String user_number;
        private String user_mobile;

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_number() {
            return user_number;
        }

        public void setUser_number(String user_number) {
            this.user_number = user_number;
        }

        public String getShichang_time() {
            return shichang_time;
        }

        public void setShichang_time(String shichang_time) {
            this.shichang_time = shichang_time;
        }

        public String getLicheng_km() {
            return licheng_km;
        }

        public void setLicheng_km(String licheng_km) {
            this.licheng_km = licheng_km;
        }

        public String getNeiquyu_km() {
            return neiquyu_km;
        }

        public void setNeiquyu_km(String neiquyu_km) {
            this.neiquyu_km = neiquyu_km;
        }

        public String getWaiquyu_km() {
            return waiquyu_km;
        }

        public void setWaiquyu_km(String waiquyu_km) {
            this.waiquyu_km = waiquyu_km;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStart_address() {
            return start_address;
        }

        public void setStart_address(String start_address) {
            this.start_address = start_address;
        }

        public String getEnd_address() {
            return end_address;
        }

        public void setEnd_address(String end_address) {
            this.end_address = end_address;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile_yin() {
            return mobile_yin;
        }

        public void setMobile_yin(String mobile_yin) {
            this.mobile_yin = mobile_yin;
        }

        public String getQibu_price() {
            return qibu_price;
        }

        public void setQibu_price(String qibu_price) {
            this.qibu_price = qibu_price;
        }

        public String getShichang_price() {
            return shichang_price;
        }

        public void setShichang_price(String shichang_price) {
            this.shichang_price = shichang_price;
        }

        public String getLicheng_price() {
            return licheng_price;
        }

        public void setLicheng_price(String licheng_price) {
            this.licheng_price = licheng_price;
        }

        public String getNeiquyu_km_price() {
            return neiquyu_km_price;
        }

        public void setNeiquyu_km_price(String neiquyu_km_price) {
            this.neiquyu_km_price = neiquyu_km_price;
        }

        public String getWaiquyu_km_price() {
            return waiquyu_km_price;
        }

        public void setWaiquyu_km_price(String waiquyu_km_price) {
            this.waiquyu_km_price = waiquyu_km_price;
        }

        public String getYouhui_price() {
            return youhui_price;
        }

        public void setYouhui_price(String youhui_price) {
            this.youhui_price = youhui_price;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }
    }
}
