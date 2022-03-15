package com.xueyiche.zjyk.xueyiche.carlife.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.carlife.bean.DuiHuanGoodsBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShopCartGoods;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShopCartImp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 16-12-23.
 */
public class PopupDishAdapter extends RecyclerView.Adapter {
    private static String TAG = "PopupDishAdapter";
    private ShopCartGoods shopCart;
    private Context context;
    private int itemCount;
    private List<DuiHuanGoodsBean.ContentBean> dishList;
    private ShopCartImp shopCartImp;
    private String money_baodan;

    public PopupDishAdapter(Context context, ShopCartGoods shopCart, String money_baodan) {
        this.shopCart = shopCart;
        this.context = context;
        this.money_baodan = money_baodan;
        this.itemCount = shopCart.getDishAccount();
        this.dishList = new ArrayList<>();
        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_dish_item, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DishViewHolder dishholder = (DishViewHolder) holder;
        final DuiHuanGoodsBean.ContentBean dish = getDishByPosition(position);
        if (dish != null) {
            dishholder.right_dish_name_tv.setText(dish.getName());
            final int price = dish.getPrice();
            dishholder.right_dish_price_tv.setText(price + "");
            int num = shopCart.getShoppingSingleMap().get(dish);
            dishholder.right_dish_account_tv.setText(num + "");

            dishholder.right_dish_add_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double shoppingTotalPrice = shopCart.getShoppingTotalPrice();
                    if (!TextUtils.isEmpty(money_baodan)) {
                        BigDecimal a = new BigDecimal(money_baodan);
                        BigDecimal b = new BigDecimal(shoppingTotalPrice);
                        //当前商品价格
                        BigDecimal c = new BigDecimal(price);
                        if (a.compareTo(b.add(c)) >= 0) {
                            if (shopCart.addShoppingSingle(dish)) {
                                notifyItemChanged(position);
                                if (shopCartImp != null)
                                    shopCartImp.add(view, position);
                            }
                        } else {
                            Toast.makeText(App.context, "可用金额不足", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            dishholder.right_dish_remove_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopCart.subShoppingSingle(dish)) {
                        dishList.clear();
                        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
                        itemCount = shopCart.getDishAccount();
                        ;
                        notifyDataSetChanged();
                        if (shopCartImp != null)
                            shopCartImp.remove(view, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    public DuiHuanGoodsBean.ContentBean getDishByPosition(int position) {
        return dishList.get(position);
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    private class DishViewHolder extends RecyclerView.ViewHolder {
        private TextView right_dish_name_tv;
        private TextView right_dish_price_tv;
        private LinearLayout right_dish_layout;
        private ImageView right_dish_remove_iv;
        private ImageView right_dish_add_iv;
        private TextView right_dish_account_tv;

        public DishViewHolder(View itemView) {
            super(itemView);
            right_dish_name_tv = (TextView) itemView.findViewById(R.id.right_dish_name);
            right_dish_price_tv = (TextView) itemView.findViewById(R.id.right_dish_price);
            right_dish_layout = (LinearLayout) itemView.findViewById(R.id.right_dish_item);
            right_dish_remove_iv = (ImageView) itemView.findViewById(R.id.right_dish_remove);
            right_dish_add_iv = (ImageView) itemView.findViewById(R.id.right_dish_add);
            right_dish_account_tv = (TextView) itemView.findViewById(R.id.right_dish_account);
        }

    }
}
