package com.xueyiche.zjyk.xueyiche.carlife.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.carlife.bean.CarLiveXiaDanBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShangPinBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShopCart;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShopCartImp;
import com.xueyiche.zjyk.xueyiche.carlife.adapter.CarLiveContentShopingCarAdapter;
import com.xueyiche.zjyk.xueyiche.homepage.bean.CarLifeContentBean;
import com.xueyiche.zjyk.xueyiche.submit.CarLiveSubmitIndent;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by cheng on 16-12-22.
 */
public class ShopCartDialog extends Dialog implements View.OnClickListener, ShopCartImp {

    private LinearLayout linearLayout, bottomLayout;
    private ShopCart shopCart;
    private TextView totalPriceTextView,tv_clear;
    private RecyclerView recyclerView;
    private ShopCartDialogImp shopCartDialogImp;
    private ShopCartClear shopCartClear;
    private CarLiveContentShopingCarAdapter carLiveContentShopingCarAdapter;
    private TextView bt_shoping_car_buy;
    private Activity activity;
    private String shop_id;
    private TextView tv_shoping_car_money_menshi;
    private ImageView iv_close;

    public ShopCartDialog(Context context, ShopCart shopCart, int themeResId,String shop_id) {
        super(context, themeResId);
        Activity activityByContext = XueYiCheUtils.getActivityByContext(context);
        this.activity = activityByContext;
        this.shop_id = shop_id;
        this.shopCart = shopCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_popupview);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        bottomLayout = (LinearLayout) findViewById(R.id.shopping_cart_bottom);
        totalPriceTextView = (TextView) findViewById(R.id.tv_shoping_car_money);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        tv_shoping_car_money_menshi = (TextView) findViewById(R.id.tv_shoping_car_money_menshi);
        bt_shoping_car_buy = (TextView) findViewById(R.id.bt_shoping_car_buy);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        bottomLayout.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        bt_shoping_car_buy.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        carLiveContentShopingCarAdapter = new CarLiveContentShopingCarAdapter(getContext(), shopCart);
        recyclerView.setAdapter(carLiveContentShopingCarAdapter);
        carLiveContentShopingCarAdapter.setShopCartImp(this);
        showTotalPrice();
    }

    @Override
    public void show() {
        super.show();
        animationShow(500);
    }

    @Override
    public void dismiss() {
        animationHide(500);
    }

    private void showTotalPrice() {
        if (shopCart != null && shopCart.getShoppingTotalPrice() > 0) {
            totalPriceTextView.setVisibility(View.VISIBLE);
            tv_shoping_car_money_menshi.setVisibility(View.VISIBLE);
            double shoppingTotalPrice = shopCart.getShoppingTotalPrice();
            double shoppingTotalPriceMenShi = shopCart.getMsShoppingTotalPrice();
            BigDecimal a = new BigDecimal(shoppingTotalPrice).setScale(2, RoundingMode.HALF_UP);
            BigDecimal b = new BigDecimal(shoppingTotalPriceMenShi).setScale(2, RoundingMode.HALF_UP);
            totalPriceTextView.setText("￥" + a);
            tv_shoping_car_money_menshi.setText("门市价￥" + b);
        } else {
            totalPriceTextView.setText("￥0");
            tv_shoping_car_money_menshi.setText("门市价￥0");
        }
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY", 1000, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY", 0, 1000).setDuration(mDuration)
        );
        animatorSet.start();

        if (shopCartDialogImp != null) {
            shopCartDialogImp.dialogDismiss();
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ShopCartDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopping_cart_bottom:

                break;
            case R.id.iv_close:
                this.dismiss();
                break;
            case R.id.tv_clear:
                //清除购物车
                shopCart.clear();
                showTotalPrice();
                carLiveContentShopingCarAdapter.notifyDataSetChanged();
                if (shopCartClear != null) {
                    shopCartClear.clearShoppingCar();
                }
                this.dismiss();
                break;
            case R.id.bt_shoping_car_buy:
                if (DialogUtils.IsLogin()) {
                    String user_id = PrefUtils.getString(App.context, "user_id", "0");
                    Map<CarLifeContentBean.ContentBean.GoodListBean, Integer> shoppingSingleMap = shopCart.getShoppingSingleMap();
                    Set<Map.Entry<CarLifeContentBean.ContentBean.GoodListBean, Integer>> set = shoppingSingleMap.entrySet();
                    List<ShangPinBean> shangPinBeanList = new ArrayList<>();
                    for (Iterator<Map.Entry<CarLifeContentBean.ContentBean.GoodListBean, Integer>> it = set.iterator(); it.hasNext(); ) {
                        Map.Entry<CarLifeContentBean.ContentBean.GoodListBean, Integer> next = it.next();
                        CarLifeContentBean.ContentBean.GoodListBean key = next.getKey();
                        //商品
                        int goods_id = key.getGoods_id();
                        double price = key.getPrice();
                        //商品数量
                        Integer value = next.getValue();
                        ShangPinBean shangPinBean = new ShangPinBean();
                        shangPinBean.setNum(value);
                        shangPinBean.setGoods_id(goods_id);
                        shangPinBean.setPrice(price);
                        shangPinBeanList.add(shangPinBean);
                    }
                    String shop_car_content = shangPinBeanList.toString();
                    OkHttpUtils.post()
                            .url(AppUrl.Car_Life_XiaDan)
                            .addParams("device_id", LoginUtils.getId(activity))
                            .addParams("shop_id", shop_id)
                            .addParams("user_id", user_id)
                            .addParams("shop_car", shop_car_content)
                            .build().execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                CarLiveXiaDanBean carLiveXiaDanBean = JsonUtil.parseJsonToBean(string, CarLiveXiaDanBean.class);
                                if (carLiveXiaDanBean!=null) {
                                    CarLiveXiaDanBean.ContentBean content = carLiveXiaDanBean.getContent();
                                    if (content!=null) {
                                        String order_number = content.getOrder_number();
                                        if (order_number!=null&&!TextUtils.isEmpty(order_number)) {
                                            Intent intent = new Intent(App.context, CarLiveSubmitIndent.class);
                                            intent.putExtra("order_number",order_number);
                                            activity.startActivity(intent);
                                        }
                                    }
                                }
                            }
                            return string;
                        }

                        @Override
                        public void onError(Request request, Exception e) {

                        }

                        @Override
                        public void onResponse(Object response) {

                        }
                    });
                }

                break;
        }
    }

    @Override
    public void add(View view, int postion) {
        showTotalPrice();
    }

    @Override
    public void remove(View view, int postion) {
        showTotalPrice();
        if (shopCart.getShoppingAccount() == 0) {
            this.dismiss();
        }
    }

    public ShopCartDialogImp getShopCartDialogImp() {
        return shopCartDialogImp;
    }

    public void setShopCartDialogImp(ShopCartDialogImp shopCartDialogImp) {
        this.shopCartDialogImp = shopCartDialogImp;
    }

    public interface ShopCartDialogImp {
        public void dialogDismiss();
    }

    public ShopCartClear getShopCartClear() {
        return shopCartClear;
    }

    public void setShopCartClear(ShopCartClear shopCartClear) {
        this.shopCartClear = shopCartClear;
    }

    public interface ShopCartClear {
        public void clearShoppingCar();
    }

    public void clear() {
        shopCart.clear();
        showTotalPrice();
        if (shopCart.getShoppingAccount() == 0) {
            this.dismiss();
        }
    }
}
