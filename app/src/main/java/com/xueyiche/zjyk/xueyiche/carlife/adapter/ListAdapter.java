package com.xueyiche.zjyk.xueyiche.carlife.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.tencent.bugly.proguard.T;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.carlife.CarLifeContentActivity;
import com.xueyiche.zjyk.xueyiche.carlife.bean.CarLifeListBean;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;

import java.util.List;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 7/29/21/3:08 PM .
 * #            com.xueyiche.zjyk.xueyiche.carlife.adapter
 * #            xueyiche4.0
 */
public class ListAdapter extends BaseQuickAdapter<CarLifeListBean.ContentBean, BaseViewHolder> {

    public ListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarLifeListBean.ContentBean item) {
        RoundImageView iv_goods =  helper.getView(R.id.iv_goods);
        TextView tv_goods_name =  helper.getView(R.id.tv_goods_name);
        TextView tv_shop_place_name =  helper.getView(R.id.tv_shop_place_name);
        TextView tv_goods_distance =  helper.getView(R.id.tv_goods_distance);
        TextView tv_name_one =  helper.getView(R.id.tv_name_one);
        TextView tv_money_one =  helper.getView(R.id.tv_money_one);
        TextView tv_money_menshi_one =  helper.getView(R.id.tv_money_menshi_one);
        TextView tv_name_two =  helper.getView(R.id.tv_name_two);
        TextView tv_money_two =  helper.getView(R.id.tv_money_two);
        TextView tv_money_menshi_two =  helper.getView(R.id.tv_money_menshi_two);
        LinearLayout ll_all_content =  helper.getView(R.id.ll_all_content);
        RelativeLayout rl_one =  helper.getView(R.id.rl_one);
        RelativeLayout rl_two =  helper.getView(R.id.rl_two);
            String shop_img = item.getShop_img();
            String shop_name = item.getShop_name();
            String distance = item.getDistance();
            final String shop_id = item.getShop_id();
            String shop_place_name = item.getShop_place_name();
            if (!TextUtils.isEmpty(shop_place_name)) {
                tv_shop_place_name.setText(shop_place_name);
            }
            if (!TextUtils.isEmpty(shop_img)) {
                Picasso.with(App.context).load(shop_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_goods);
            }
            if (!TextUtils.isEmpty(distance)) {
               tv_goods_distance.setText(distance);
            }
            if (!TextUtils.isEmpty(shop_name)) {
               tv_goods_name.setText(shop_name);
            }

            List<CarLifeListBean.ContentBean.GoodListBean> good_list = item.getGood_list();
            if (good_list != null && good_list.size() > 0) {
                CarLifeListBean.ContentBean.GoodListBean goodListBean1 = good_list.get(0);
                if (goodListBean1 != null) {
                    final int goods_id = goodListBean1.getGoods_id();
                    String goods_name = goodListBean1.getGoods_name();
                    int old_price = goodListBean1.getOld_price();
                    double price = goodListBean1.getPrice();
                   tv_name_one.setText(goods_name);
                   tv_money_one.setText("¥" + price);
                   tv_money_menshi_one.setText("¥" + old_price);
                   tv_money_menshi_one.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
                }

                if (good_list.size() > 1) {
                   rl_two.setVisibility(View.VISIBLE);
                    CarLifeListBean.ContentBean.GoodListBean goodListBean2 = good_list.get(1);
                    if (goodListBean2 != null) {
                        String goods_name = goodListBean2.getGoods_name();
                        int old_price = goodListBean2.getOld_price();
                        double price = goodListBean2.getPrice();
                       tv_name_two.setText(goods_name);
                        tv_money_two.setText("¥" + price);
                       tv_money_menshi_two.setText("¥" + old_price);
                        tv_money_menshi_two.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
                    }
                } else {
                  rl_two.setVisibility(View.GONE);
                }

            } else {
           rl_one.setVisibility(View.GONE);
               rl_two.setVisibility(View.GONE);
            }
    }
}
