package com.xueyiche.zjyk.xueyiche.carlife.bean;

import com.xueyiche.zjyk.xueyiche.homepage.bean.CarLifeContentBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheng on 16-11-12.
 */
public class ShopCart {
    private int shoppingAccount;//商品总数
    private double shoppingTotalPrice;//商品总价钱
    private double shoppingTotalPriceMenShi;//门市商品总价钱
    private Map<CarLifeContentBean.ContentBean.GoodListBean,Integer> shoppingSingle;//单个物品的总价价钱
    public ShopCart(){
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingTotalPriceMenShi = 0;
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
    /**
     * 获取商品总价
     * @return
     */
    public double getMsShoppingTotalPrice() {
        return shoppingTotalPriceMenShi;
    }

    public Map<CarLifeContentBean.ContentBean.GoodListBean, Integer> getShoppingSingleMap() {
        return shoppingSingle;
    }

    public boolean addShoppingSingle(CarLifeContentBean.ContentBean.GoodListBean dish){
        int num = 0;
        int number = 0;
        if(shoppingSingle.containsKey(dish)){
            number = dish.getNumber();
            num = shoppingSingle.get(dish);
        }
        num+=1;
        number+=1;
        dish.setNumber(number);
        shoppingSingle.put(dish,num);
        shoppingTotalPrice += dish.getPrice();
        shoppingTotalPriceMenShi += dish.getOld_price();
        shoppingAccount++;
        return true;
    }

    public boolean subShoppingSingle(CarLifeContentBean.ContentBean.GoodListBean dish){
        int num = 0;
        int number = 0;
        if(shoppingSingle.containsKey(dish)){
            number = dish.getNumber();
            num = shoppingSingle.get(dish);
        }

        if(num<=0) return false;
        num--;
        number--;
        dish.setNumber(number);
        if (number==0) {
            dish.setNumber(0);
        }
        shoppingSingle.put(dish,num);
        if (num ==0){
            shoppingSingle.remove(dish);
        }
        shoppingTotalPrice -= dish.getPrice();
        shoppingTotalPriceMenShi -= dish.getOld_price();
        shoppingAccount--;
        return true;
    }
    public boolean subShoppingFuWu(CarLifeContentBean.ContentBean.GoodListBean dish){
        int num = 0;
        if(shoppingSingle.containsKey(dish)){
            dish.setNumber(0);
            num = shoppingSingle.get(dish);
            double i = num * dish.getPrice();
            shoppingTotalPrice -=i;
            shoppingTotalPriceMenShi -=i;
            shoppingSingle.remove(dish);
            shoppingAccount-=num;
            return true;
        }else {
            return false;
        }

    }

    public boolean addShoppingFuWu(CarLifeContentBean.ContentBean.GoodListBean dish){
        if(shoppingSingle.containsKey(dish)){
            return false;
        }else {
            dish.setNumber(1);
            shoppingSingle.put(dish,1);
            shoppingTotalPrice += dish.getPrice();
            shoppingTotalPriceMenShi += dish.getOld_price();
            shoppingAccount++;
            return true;
        }


    }
    public int getDishAccount() {
        return shoppingSingle.size();
    }

    public void clear(){
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingTotalPriceMenShi = 0;
        this.shoppingSingle.clear();
    }
}
