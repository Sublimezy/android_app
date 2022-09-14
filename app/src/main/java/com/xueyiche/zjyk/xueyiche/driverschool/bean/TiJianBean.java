package com.xueyiche.zjyk.xueyiche.driverschool.bean;

import java.util.List;

/**
 * Created by ZL on 2019/4/23.
 */
public class TiJianBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"tijian_date":"周一至周五","address":"香坊区香电街69号","latitude":"45.740744","id":1,"tel_phone":"045155197802","area_id":"100119100010005","hospital_name":"省农垦总医院","longitude":"126.700681","from_user":"7.7km"},{"tijian_date":"周一至周五","address":"南岗区哈双路235号","latitude":"45.688007","id":2,"tel_phone":"045155197777","area_id":"100119100010002","hospital_name":"黑龙江省农垦总局总医院","longitude":"126.56622","from_user":"7.6km"},{"tijian_date":"周一至周五","address":"道里区工厂街138号，毗邻尚志大街","latitude":"45.771331","id":3,"tel_phone":"045184679718","area_id":"100119100010001","hospital_name":"哈尔滨市公安医院","longitude":"126.630899","from_user":"3.1km"}]
     */

    private String msg;
    private int code;
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
        /**
         * tijian_date : 周一至周五
         * address : 香坊区香电街69号
         * latitude : 45.740744
         * id : 1
         * tel_phone : 045155197802
         * area_id : 100119100010005
         * hospital_name : 省农垦总医院
         * longitude : 126.700681
         * from_user : 7.7km
         */

        private String tijian_date;
        private String address;
        private String latitude;
        private int id;
        private String tel_phone;
        private String area_id;
        private String hospital_name;
        private String longitude;
        private String from_user;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTijian_date() {

            return tijian_date;
        }

        public void setTijian_date(String tijian_date) {
            this.tijian_date = tijian_date;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTel_phone() {
            return tel_phone;
        }

        public void setTel_phone(String tel_phone) {
            this.tel_phone = tel_phone;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getHospital_name() {
            return hospital_name;
        }

        public void setHospital_name(String hospital_name) {
            this.hospital_name = hospital_name;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getFrom_user() {
            return from_user;
        }

        public void setFrom_user(String from_user) {
            this.from_user = from_user;
        }
    }
}
