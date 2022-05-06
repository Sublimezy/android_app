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


    /**
     * code : 1
     * msg : 请求成功
     * time : 1651654273
     * data : {"id":174,"user_id":4,"order_sn":"A2022050414475804653","start_address":"黑龙江省哈尔滨市松北区智谷二街1073号靠近松北科技创新产业园综合服务大厅","start_address_lng":"126.47706","start_address_lat":"45.808566","end_address":"松北上品(西南1门)","end_address_lng":"126.555086","end_address_lat":"45.812225","order_type":0,"call_name":"","call_mobile":"","pay_status":0,"pay_money":null,"pay_type":null,"driving_user_id":4,"order_status":4,"jiedan_time":1651646878,"daoda_time":1651646893,"start_time":"2022-05-04 14:48:19","end_time":"2022-05-04 16:00:09","quxiao_time":null,"quxiao_reason":null,"quxiao_remarks":null,"createtime":1651646878,"updatetime":1651646878,"qibu_price":"35.00","shichang_time":"-74","shichang_price":"0.00","licheng_km":"0","licheng_price":"0.00","neiquyu_km":"1.26","waiquyu_km":"0.088","voice_url":"http://xychead.xueyiche.vip/record_A2022050414475804653_1651648572003.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653_1651650703047.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653_1651649606911.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653_1651647141755.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653_1651648663491.mp3","pingjia_rank":null,"total_price":"35.00","youhui_price":"0.00","waiquyu_km_price":"0.00","neiquyu_km_price":"35.00","service_id":"679498","terminal_name":"daijiaA2022050414475804653","bd_type":null,"t_id":"521420640","tr_id":"240","create_time":"2022-05-04 14:47:58","user_lng":"126.477055","user_lat":"45.808574","user_mobile":"18346012117","user_name":"蒙奇.D.路飞","head_img":"http://xychead.xueyiche.vip/fx_tuwen1648868958349.jpg","user_number":"HRB00014(蒙奇.D.路飞)","quxiao_content":"司机正在火速赶来 14:57:58分后取消需支付违约费用"}
     */

    private int code;
    private String msg;
    private String time;
    /**
     * id : 174
     * user_id : 4
     * order_sn : A2022050414475804653
     * start_address : 黑龙江省哈尔滨市松北区智谷二街1073号靠近松北科技创新产业园综合服务大厅
     * start_address_lng : 126.47706
     * start_address_lat : 45.808566
     * end_address : 松北上品(西南1门)
     * end_address_lng : 126.555086
     * end_address_lat : 45.812225
     * order_type : 0
     * call_name :
     * call_mobile :
     * pay_status : 0
     * pay_money : null
     * pay_type : null
     * driving_user_id : 4
     * order_status : 4
     * jiedan_time : 1651646878
     * daoda_time : 1651646893
     * start_time : 2022-05-04 14:48:19
     * end_time : 2022-05-04 16:00:09
     * quxiao_time : null
     * quxiao_reason : null
     * quxiao_remarks : null
     * createtime : 1651646878
     * updatetime : 1651646878
     * qibu_price : 35.00
     * shichang_time : -74
     * shichang_price : 0.00
     * licheng_km : 0
     * licheng_price : 0.00
     * neiquyu_km : 1.26
     * waiquyu_km : 0.088
     * voice_url : http://xychead.xueyiche.vip/record_A2022050414475804653_1651648572003.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653_1651650703047.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653_1651649606911.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653_1651647141755.mp3,http://xychead.xueyiche.vip/record_A2022050414475804653_1651648663491.mp3
     * pingjia_rank : null
     * total_price : 35.00
     * youhui_price : 0.00
     * waiquyu_km_price : 0.00
     * neiquyu_km_price : 35.00
     * service_id : 679498
     * terminal_name : daijiaA2022050414475804653
     * bd_type : null
     * t_id : 521420640
     * tr_id : 240
     * create_time : 2022-05-04 14:47:58
     * user_lng : 126.477055
     * user_lat : 45.808574
     * user_mobile : 18346012117
     * user_name : 蒙奇.D.路飞
     * head_img : http://xychead.xueyiche.vip/fx_tuwen1648868958349.jpg
     * user_number : HRB00014(蒙奇.D.路飞)
     * quxiao_content : 司机正在火速赶来 14:57:58分后取消需支付违约费用
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
        private int id;
        private int user_id;
        private String order_sn;
        private String start_address;
        private String start_address_lng;
        private String start_address_lat;
        private String end_address;
        private String end_address_lng;
        private String end_address_lat;
        private int order_type;
        private String call_name;
        private String call_mobile;
        private int pay_status;
        private Object pay_money;
        private Object pay_type;
        private int driving_user_id;
        private int order_status;
        private int jiedan_time;
        private int daoda_time;
        private String start_time;
        private String end_time;
        private Object quxiao_time;
        private Object quxiao_reason;
        private Object quxiao_remarks;
        private int createtime;
        private int updatetime;
        private String qibu_price;
        private String shichang_time;
        private String shichang_price;
        private String licheng_km;
        private String licheng_price;
        private String neiquyu_km;
        private String waiquyu_km;
        private String voice_url;
        private String pingjia_rank;
        private String total_price;
        private String youhui_price;
        private String waiquyu_km_price;
        private String neiquyu_km_price;

        private String terminal_name;
        private Object bd_type;
        private String service_id;
        private String t_id;
        private String tr_id;
        private String create_time;
        private String user_lng;
        private String user_lat;
        private String user_mobile;
        private String user_name;
        private String head_img;
        private String user_number;
        private String quxiao_content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public String getCall_name() {
            return call_name;
        }

        public void setCall_name(String call_name) {
            this.call_name = call_name;
        }

        public String getCall_mobile() {
            return call_mobile;
        }

        public void setCall_mobile(String call_mobile) {
            this.call_mobile = call_mobile;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public Object getPay_money() {
            return pay_money;
        }

        public void setPay_money(Object pay_money) {
            this.pay_money = pay_money;
        }

        public Object getPay_type() {
            return pay_type;
        }

        public void setPay_type(Object pay_type) {
            this.pay_type = pay_type;
        }

        public int getDriving_user_id() {
            return driving_user_id;
        }

        public void setDriving_user_id(int driving_user_id) {
            this.driving_user_id = driving_user_id;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getJiedan_time() {
            return jiedan_time;
        }

        public void setJiedan_time(int jiedan_time) {
            this.jiedan_time = jiedan_time;
        }

        public int getDaoda_time() {
            return daoda_time;
        }

        public void setDaoda_time(int daoda_time) {
            this.daoda_time = daoda_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public Object getQuxiao_time() {
            return quxiao_time;
        }

        public void setQuxiao_time(Object quxiao_time) {
            this.quxiao_time = quxiao_time;
        }

        public Object getQuxiao_reason() {
            return quxiao_reason;
        }

        public void setQuxiao_reason(Object quxiao_reason) {
            this.quxiao_reason = quxiao_reason;
        }

        public Object getQuxiao_remarks() {
            return quxiao_remarks;
        }

        public void setQuxiao_remarks(Object quxiao_remarks) {
            this.quxiao_remarks = quxiao_remarks;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
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

        public String getWaiquyu_km() {
            return waiquyu_km;
        }

        public void setWaiquyu_km(String waiquyu_km) {
            this.waiquyu_km = waiquyu_km;
        }

        public String getVoice_url() {
            return voice_url;
        }

        public void setVoice_url(String voice_url) {
            this.voice_url = voice_url;
        }

        public String getPingjia_rank() {
            return pingjia_rank;
        }

        public void setPingjia_rank(String pingjia_rank) {
            this.pingjia_rank = pingjia_rank;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getYouhui_price() {
            return youhui_price;
        }

        public void setYouhui_price(String youhui_price) {
            this.youhui_price = youhui_price;
        }

        public String getWaiquyu_km_price() {
            return waiquyu_km_price;
        }

        public void setWaiquyu_km_price(String waiquyu_km_price) {
            this.waiquyu_km_price = waiquyu_km_price;
        }

        public String getNeiquyu_km_price() {
            return neiquyu_km_price;
        }

        public void setNeiquyu_km_price(String neiquyu_km_price) {
            this.neiquyu_km_price = neiquyu_km_price;
        }

        public String getService_id() {
            return service_id;
        }

        public void setService_id(String service_id) {
            this.service_id = service_id;
        }

        public String getTerminal_name() {
            return terminal_name;
        }

        public void setTerminal_name(String terminal_name) {
            this.terminal_name = terminal_name;
        }

        public Object getBd_type() {
            return bd_type;
        }

        public void setBd_type(Object bd_type) {
            this.bd_type = bd_type;
        }

        public String getT_id() {
            return t_id;
        }

        public void setT_id(String t_id) {
            this.t_id = t_id;
        }

        public String getTr_id() {
            return tr_id;
        }

        public void setTr_id(String tr_id) {
            this.tr_id = tr_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
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

        public String getQuxiao_content() {
            return quxiao_content;
        }

        public void setQuxiao_content(String quxiao_content) {
            this.quxiao_content = quxiao_content;
        }
    }
}
