package com.xueyiche.zjyk.xueyiche.carlife.bean;

import java.util.HashMap;
import java.util.Map;
public class ShopCartGoods {
    private int shoppingAccount;//商品总数
    private double shoppingTotalPrice;//商品总价钱
    private Map<DuiHuanGoodsBean.ContentBean,Integer> shoppingSingle;//单个物品的总价价钱

    public ShopCartGoods(){
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingSingle = new HashMap<>();
    }

    public int getShoppingAccount() {
        return shoppingAccount;
    }

    /**
     * 获取商品总价
     * @return
     */
    public double getShoppingTotalPrice() {
        return shoppingTotalPrice;
    }

    public Map<DuiHuanGoodsBean.ContentBean, Integer> getShoppingSingleMap() {
        return shoppingSingle;
    }

    public boolean addShoppingSingle(DuiHuanGoodsBean.ContentBean dish){
        int num = 0;
        if(shoppingSingle.containsKey(dish)){
            num = shoppingSingle.get(dish);
        }
        num+=1;
        shoppingSingle.put(dish,num);
        shoppingTotalPrice += dish.getPrice();
        shoppingAccount++;
        return true;
    }

    public boolean subShoppingSingle(DuiHuanGoodsBean.ContentBean dish){
        int num = 0;
        if(shoppingSingle.containsKey(dish)){
            num = shoppingSingle.get(dish);
        }
        if(num<=0) return false;
        num--;
        shoppingSingle.put(dish,num);
        if (num ==0) shoppingSingle.remove(dish);
        shoppingTotalPrice -= dish.getPrice();
        shoppingAccount--;
        return true;
    }

    public int getDishAccount() {
        return shoppingSingle.size();
    }

    public void clear(){
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingSingle.clear();
    }
}
