package com.xueyiche.zjyk.xueyiche.daijia.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/9/26.
 */
public class ChangYongListBean {


    /**
     * msg : 操作成功！
     * code : 200
     * content : {"mapHome":{"address":"呼兰区永达路","user_id":"271","latitude":"45.888208","name":"地恒托斯卡纳","id":18,"sort":0,"longitude":"126.578762"},"listData":[{"address":"800号","user_id":"271","latitude":"45.888208","name":"中央大街","id":20,"longitude":"126.578762"}],"mapCompany":{"address":"松北区创新三路600号","user_id":"271","latitude":"45.888208","name":"科技大哈撒","id":19,"sort":1,"longitude":"126.578762"}}
     */

    private String msg;
    private int code;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * mapHome : {"address":"呼兰区永达路","user_id":"271","latitude":"45.888208","name":"地恒托斯卡纳","id":18,"sort":0,"longitude":"126.578762"}
         * listData : [{"address":"800号","user_id":"271","latitude":"45.888208","name":"中央大街","id":20,"longitude":"126.578762"}]
         * mapCompany : {"address":"松北区创新三路600号","user_id":"271","latitude":"45.888208","name":"科技大哈撒","id":19,"sort":1,"longitude":"126.578762"}
         */

        private MapHomeBean mapHome;
        private MapCompanyBean mapCompany;
        private List<ListDataBean> listData;

        public MapHomeBean getMapHome() {
            return mapHome;
        }

        public void setMapHome(MapHomeBean mapHome) {
            this.mapHome = mapHome;
        }

        public MapCompanyBean getMapCompany() {
            return mapCompany;
        }

        public void setMapCompany(MapCompanyBean mapCompany) {
            this.mapCompany = mapCompany;
        }

        public List<ListDataBean> getListData() {
            return listData;
        }

        public void setListData(List<ListDataBean> listData) {
            this.listData = listData;
        }

        public static class MapHomeBean {
            /**
             * address : 呼兰区永达路
             * user_id : 271
             * latitude : 45.888208
             * name : 地恒托斯卡纳
             * id : 18
             * sort : 0
             * longitude : 126.578762
             */

            private String address;
            private String user_id;
            private String latitude;
            private String name;
            private int id;
            private int sort;
            private String longitude;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }

        public static class MapCompanyBean {
            /**
             * address : 松北区创新三路600号
             * user_id : 271
             * latitude : 45.888208
             * name : 科技大哈撒
             * id : 19
             * sort : 1
             * longitude : 126.578762
             */

            private String address;
            private String user_id;
            private String latitude;
            private String name;
            private int id;
            private int sort;
            private String longitude;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }

        public static class ListDataBean {
            /**
             * address : 800号
             * user_id : 271
             * latitude : 45.888208
             * name : 中央大街
             * id : 20
             * longitude : 126.578762
             */

            private String address;
            private String user_id;
            private String latitude;
            private String name;
            private int id;
            private String longitude;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }
    }
}
