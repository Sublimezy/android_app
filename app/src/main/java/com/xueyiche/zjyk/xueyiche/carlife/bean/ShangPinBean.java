package com.xueyiche.zjyk.xueyiche.carlife.bean;

/**
 * Created by Owner on 2017/11/2.
 */
public class ShangPinBean {
    private int goods_id;
    private int num;
    private double price;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    @Override
    public String toString() {
        return "{" +
                "goods_id:'" + goods_id +"\'"+
                ", price:'" + price+"\'" +
                ", num:'" + num+"\'"+
                '}';
    }
}
