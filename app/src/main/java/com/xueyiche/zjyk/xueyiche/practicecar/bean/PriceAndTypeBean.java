package com.xueyiche.zjyk.xueyiche.practicecar.bean;

import java.util.List;

/**
 * Created by ZL on 2019/10/10.
 */
public class PriceAndTypeBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"type_name":"舒适型","carSortData":[{"car_sort":"1","sort_name":"SUV","hourPriceData":[{"hour_price":2}]}],"car_type":1},{"type_name":"经济型","carSortData":[{"car_sort":"0","sort_name":"轿车","hourPriceData":[{"hour_price":1}]}],"car_type":0}]
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
         * type_name : 舒适型
         * carSortData : [{"car_sort":"1","sort_name":"SUV","hourPriceData":[{"hour_price":2}]}]
         * car_type : 1
         */

        private String type_name;
        private int car_type;
        private List<CarSortDataBean> carSortData;

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public int getCar_type() {
            return car_type;
        }

        public void setCar_type(int car_type) {
            this.car_type = car_type;
        }

        public List<CarSortDataBean> getCarSortData() {
            return carSortData;
        }

        public void setCarSortData(List<CarSortDataBean> carSortData) {
            this.carSortData = carSortData;
        }

        public static class CarSortDataBean {
            /**
             * car_sort : 1
             * sort_name : SUV
             * hourPriceData : [{"hour_price":2}]
             */

            private String car_sort;
            private String sort_name;
            private List<HourPriceDataBean> hourPriceData;

            public String getCar_sort() {
                return car_sort;
            }

            public void setCar_sort(String car_sort) {
                this.car_sort = car_sort;
            }

            public String getSort_name() {
                return sort_name;
            }

            public void setSort_name(String sort_name) {
                this.sort_name = sort_name;
            }

            public List<HourPriceDataBean> getHourPriceData() {
                return hourPriceData;
            }

            public void setHourPriceData(List<HourPriceDataBean> hourPriceData) {
                this.hourPriceData = hourPriceData;
            }

            public static class HourPriceDataBean {
                /**
                 * hour_price : 2
                 */

                private int hour_price;

                public int getHour_price() {
                    return hour_price;
                }

                public void setHour_price(int hour_price) {
                    this.hour_price = hour_price;
                }
            }
        }
    }
}
