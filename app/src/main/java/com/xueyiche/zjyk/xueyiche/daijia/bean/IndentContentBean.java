package com.xueyiche.zjyk.xueyiche.daijia.bean;

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
 * #            Created by 張某人 on 2022/4/12/9:47 上午 .
 * #            com.xueyiche.zjyk.xueyiche.daijia.bean
 * #            xueyiche5.0
 */
public class IndentContentBean {

    /**
     * code : 1
     * msg : 请求成功
     * time : 1649669317
     * data : {"user_id":2,"order_sn":"2_20220326160856745","start_address":"哈尔滨普宁医院","start_address_lng":"126.61161564819334","start_address_lat":"45.84652247593694","end_address":"道里","end_address_lng":"1","end_address_lat":"2","order_status":5,"order_type":2,"driving_user_id":13,"user_mobile":"13333333333","user_number":"HRB00013(斯先生)","qibu_price":"35.00","shichang_time":"75","shichang_price":"15.00","licheng_km":"11.5","licheng_price":"15.00","neiquyu_km":"11","neiquyu_km_price":"0.00","waiquyu_km":"0.5","waiquyu_km_price":"5.00","youhui_price":"0.00","total_price":"70.00"}
     */

    private int code;
    private String msg;
    private String time;
    /**
     * user_id : 2
     * order_sn : 2_20220326160856745
     * start_address : 哈尔滨普宁医院
     * start_address_lng : 126.61161564819334
     * start_address_lat : 45.84652247593694
     * end_address : 道里
     * end_address_lng : 1
     * end_address_lat : 2
     * order_status : 5
     * order_type : 2
     * driving_user_id : 13
     * user_mobile : 13333333333
     * user_number : HRB00013(斯先生)
     * qibu_price : 35.00
     * shichang_time : 75
     * shichang_price : 15.00
     * licheng_km : 11.5
     * licheng_price : 15.00
     * neiquyu_km : 11
     * neiquyu_km_price : 0.00
     * waiquyu_km : 0.5
     * waiquyu_km_price : 5.00
     * youhui_price : 0.00
     * total_price : 70.00
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
        private int user_id;
        private String order_sn;
        private String start_address;
        private String start_address_lng;
        private String start_address_lat;
        private String end_address;
        private String end_address_lng;
        private String end_address_lat;
        private int order_status;
        private int order_type;
        private int driving_user_id;
        private String user_mobile;
        private String user_number;
        private String qibu_price;
        private String shichang_time;
        private String shichang_price;
        private String licheng_km;
        private String licheng_price;
        private String neiquyu_km;
        private String neiquyu_km_price;
        private String waiquyu_km;
        private String waiquyu_km_price;
        private String youhui_price;
        private String total_price;
        private String quxiao_content;
        private String user_name;
        private String user_lng;
        private String user_lat;
        private String head_img;
        private String createtime;
        private String chaoshi_time;
        private String wait_time;
        private String wait_price;

        public String getChaoshi_time() {
            return chaoshi_time;
        }

        public void setChaoshi_time(String chaoshi_time) {
            this.chaoshi_time = chaoshi_time;
        }

        public String getWait_time() {
            return wait_time;
        }

        public void setWait_time(String wait_time) {
            this.wait_time = wait_time;
        }

        public String getWait_price() {
            return wait_price;
        }

        public void setWait_price(String wait_price) {
            this.wait_price = wait_price;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getQuxiao_content() {
            return quxiao_content;
        }

        public void setQuxiao_content(String quxiao_content) {
            this.quxiao_content = quxiao_content;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getStart_address() {
            return start_address;
        }

        public void setStart_address(String start_address) {
            this.start_address = start_address;
        }

        public String getStart_address_lng() {
            return start_address_lng;
        }

        public void setStart_address_lng(String start_address_lng) {
            this.start_address_lng = start_address_lng;
        }

        public String getStart_address_lat() {
            return start_address_lat;
        }

        public void setStart_address_lat(String start_address_lat) {
            this.start_address_lat = start_address_lat;
        }

        public String getEnd_address() {
            return end_address;
        }

        public void setEnd_address(String end_address) {
            this.end_address = end_address;
        }

        public String getEnd_address_lng() {
            return end_address_lng;
        }

        public void setEnd_address_lng(String end_address_lng) {
            this.end_address_lng = end_address_lng;
        }

        public String getEnd_address_lat() {
            return end_address_lat;
        }

        public void setEnd_address_lat(String end_address_lat) {
            this.end_address_lat = end_address_lat;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public int getDriving_user_id() {
            return driving_user_id;
        }

        public void setDriving_user_id(int driving_user_id) {
            this.driving_user_id = driving_user_id;
        }

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

        public String getQibu_price() {
            return qibu_price;
        }

        public void setQibu_price(String qibu_price) {
            this.qibu_price = qibu_price;
        }

        public String getShichang_time() {
            return shichang_time;
        }

        public void setShichang_time(String shichang_time) {
            this.shichang_time = shichang_time;
        }

        public String getShichang_price() {
            return shichang_price;
        }

        public void setShichang_price(String shichang_price) {
            this.shichang_price = shichang_price;
        }

        public String getLicheng_km() {
            return licheng_km;
        }

        public void setLicheng_km(String licheng_km) {
            this.licheng_km = licheng_km;
        }

        public String getLicheng_price() {
            return licheng_price;
        }

        public void setLicheng_price(String licheng_price) {
            this.licheng_price = licheng_price;
        }

        public String getNeiquyu_km() {
            return neiquyu_km;
        }

        public void setNeiquyu_km(String neiquyu_km) {
            this.neiquyu_km = neiquyu_km;
        }

        public String getNeiquyu_km_price() {
            return neiquyu_km_price;
        }

        public void setNeiquyu_km_price(String neiquyu_km_price) {
            this.neiquyu_km_price = neiquyu_km_price;
        }

        public String getWaiquyu_km() {
            return waiquyu_km;
        }

        public void setWaiquyu_km(String waiquyu_km) {
            this.waiquyu_km = waiquyu_km;
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
