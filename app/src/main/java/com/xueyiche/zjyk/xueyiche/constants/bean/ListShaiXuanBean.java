package com.xueyiche.zjyk.xueyiche.constants.bean;

/**
 * Created by Owner on 2017/1/11.
 */
public class ListShaiXuanBean {
    //排序
//    sort_method    == 0   默认排序
//    sort_method    ==1   距离排序
//    sort_method   ==  2 人气优先
//    sort_method    == 3 好评优先
    private String sort_method = "0";
    //地区的id
    private String area_id = "1001191000";
    private String area_id_practice = "1001191000";
    //搜索文字
    private String name = "";
    private String isFree = "";
    private String handOrauto = "";
    private String series_id = "";
    private String search_name = "";
    private String service_id = "";
    private String luli = "";

    public String getLuli() {
        return luli;
    }

    public void setLuli(String luli) {
        this.luli = luli;
    }

    public String getSearch_name() {
        return search_name;
    }

    public void setSearch_name(String search_name) {
        this.search_name = search_name;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }
//车型

    public String getSeries_id() {
        return series_id;
    }

    public void setSeries_id(String series_id) {
        this.series_id = series_id;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getHandOrauto() {
        return handOrauto;
    }

    public void setHandOrauto(String handOrauto) {
        this.handOrauto = handOrauto;
    }

    public String getArea_id_practice() {
        return area_id_practice;
    }

    public void setArea_id_practice(String area_id_practice) {
        this.area_id_practice = area_id_practice;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getBiansutype() {
        return biansutype;
    }

    public void setBiansutype(String biansutype) {
        this.biansutype = biansutype;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String cartype = "";
    //变速箱
    private String biansutype = "";
    //价格
    private String price = "";

    public String getName() {
        return name;
    }

    public void setName(String driver_school_name) {
        this.name = driver_school_name;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getSort_method() {
        return sort_method;
    }

    public void setSort_method(String sort_method) {
        this.sort_method = sort_method;
    }
}
