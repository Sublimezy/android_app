package com.xueyiche.zjyk.xueyiche.driverschool.bean;

import java.util.List;

/**
 * Created by ZL on 2019/4/29.
 */
public class BanCheContentBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"driver_name":"李四","driver_school_id":"cf2b684b649f4d4a83a2a7629df37890","listData":[{"station_address":"世茂大道500号","station_time":"08:00","start_end":"1","latitude":"45.81373","xuhao":"1","station":"哈尔滨银行","longitude":"126.538221"},{"station_address":"创新三路600号","station_time":"09:00","start_end":"1","latitude":"45.804437","xuhao":"2","station":"科技大厦","longitude":"126.513462"}],"id":4,"bus_number":"黑A-44444","driver_phone":"18811144444","bus_name":"四号班车"}
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
         * driver_name : 李四
         * driver_school_id : cf2b684b649f4d4a83a2a7629df37890
         * listData : [{"station_address":"世茂大道500号","station_time":"08:00","start_end":"1","latitude":"45.81373","xuhao":"1","station":"哈尔滨银行","longitude":"126.538221"},{"station_address":"创新三路600号","station_time":"09:00","start_end":"1","latitude":"45.804437","xuhao":"2","station":"科技大厦","longitude":"126.513462"}]
         * id : 4
         * bus_number : 黑A-44444
         * driver_phone : 18811144444
         * bus_name : 四号班车
         */

        private String driver_name;
        private String driver_school_id;
        private int id;
        private String bus_number;
        private String driver_phone;
        private String bus_name;
        private String zhongdian;
        private String shifa_time;
        private String shifa;
        private String zhongdian_time;
        private List<ListDataBean> listData;

        public String getZhongdian() {
            return zhongdian;
        }

        public void setZhongdian(String zhongdian) {
            this.zhongdian = zhongdian;
        }

        public String getShifa_time() {
            return shifa_time;
        }

        public void setShifa_time(String shifa_time) {
            this.shifa_time = shifa_time;
        }

        public String getShifa() {
            return shifa;
        }

        public void setShifa(String shifa) {
            this.shifa = shifa;
        }

        public String getZhongdian_time() {
            return zhongdian_time;
        }

        public void setZhongdian_time(String zhongdian_time) {
            this.zhongdian_time = zhongdian_time;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getDriver_school_id() {
            return driver_school_id;
        }

        public void setDriver_school_id(String driver_school_id) {
            this.driver_school_id = driver_school_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBus_number() {
            return bus_number;
        }

        public void setBus_number(String bus_number) {
            this.bus_number = bus_number;
        }

        public String getDriver_phone() {
            return driver_phone;
        }

        public void setDriver_phone(String driver_phone) {
            this.driver_phone = driver_phone;
        }

        public String getBus_name() {
            return bus_name;
        }

        public void setBus_name(String bus_name) {
            this.bus_name = bus_name;
        }

        public List<ListDataBean> getListData() {
            return listData;
        }

        public void setListData(List<ListDataBean> listData) {
            this.listData = listData;
        }

        public static class ListDataBean {
            /**
             * station_address : 世茂大道500号
             * station_time : 08:00
             * start_end : 1
             * latitude : 45.81373
             * xuhao : 1
             * station : 哈尔滨银行
             * longitude : 126.538221
             */

            private String station_address;
            private String station_time;
            private String start_end;
            private String latitude;
            private String xuhao;
            private String station;
            private String longitude;

            public String getStation_address() {
                return station_address;
            }

            public void setStation_address(String station_address) {
                this.station_address = station_address;
            }

            public String getStation_time() {
                return station_time;
            }

            public void setStation_time(String station_time) {
                this.station_time = station_time;
            }

            public String getStart_end() {
                return start_end;
            }

            public void setStart_end(String start_end) {
                this.start_end = start_end;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getXuhao() {
                return xuhao;
            }

            public void setXuhao(String xuhao) {
                this.xuhao = xuhao;
            }

            public String getStation() {
                return station;
            }

            public void setStation(String station) {
                this.station = station;
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
