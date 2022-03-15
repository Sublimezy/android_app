package com.xueyiche.zjyk.xueyiche.carlife.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShopCart;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShopCartImp;
import com.xueyiche.zjyk.xueyiche.homepage.bean.CarLifeContentBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarLiveContentShopingCarAdapter extends RecyclerView.Adapter {
    private ShopCart shopCart;
    private Context context;
    private int itemCount;
    private List<CarLifeContentBean.ContentBean.GoodListBean> dishList;
    private ShopCartImp shopCartImp;

    public CarLiveContentShopingCarAdapter(Context context, ShopCart shopCart) {
        this.shopCart = shopCart;
        this.context = context;
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
        final CarLifeContentBean.ContentBean.GoodListBean dishByPosition = getDishByPosition(position);
        Map<CarLifeContentBean.ContentBean.GoodListBean, Integer> shoppingSingleMap = shopCart.getShoppingSingleMap();
        if (shoppingSingleMap != null && shoppingSingleMap.size() > 0) {
            if (dishByPosition != null) {
                dishholder.right_dish_name_tv.setText(dishByPosition.getGoods_name());
                double price = dishByPosition.getPrice();
                BigDecimal a = new BigDecimal(price).setScale(2,BigDecimal.ROUND_UP);
                boolean weiXuBaoYang = dishByPosition.isWeiXuBaoYang();
                int num = shoppingSingleMap.get(dishByPosition);
                dishholder.right_dish_account_tv.setText(num + "");
                dishholder.right_dish_price_tv.setText(a + "");
                if (weiXuBaoYang) {
                    dishholder.right_dish_add_iv.setVisibility(View.INVISIBLE);
                    dishholder.right_dish_remove_iv.setVisibility(View.INVISIBLE);
                } else {
                    dishholder.right_dish_add_iv.setVisibility(View.VISIBLE);
                    dishholder.right_dish_remove_iv.setVisibility(View.VISIBLE);
                }
                dishholder.right_dish_add_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //当前商品价格
                        if (shopCart.addShoppingSingle(dishByPosition)) {
                            notifyItemChanged(position);
                            if (shopCartImp != null) {
                                shopCartImp.add(view, position);
                            }
                        }
                    }
                });

                dishholder.right_dish_remove_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (shopCart.subShoppingSingle(dishByPosition)) {
                            dishList.clear();
                            dishList.addAll(shopCart.getShoppingSingleMap().keySet());
                            itemCount = shopCart.getDishAccount();
                            notifyDataSetChanged();
                            if (shopCartImp != null)
                                shopCartImp.remove(view, position);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    public CarLifeContentBean.ContentBean.GoodListBean getDishByPosition(int position) {
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
