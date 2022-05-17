package com.xueyiche.zjyk.xueyiche.homepage.adapters;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.custom.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ShouYeHotBean;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;

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
 * #            Created by 張某人 on 2022/4/6/3:37 下午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage.adapters
 * #            xueyiche5.0
 */
public class HomeListAdapter extends BaseQuickAdapter<ShouYeHotBean.DataBean.DataBeanX, BaseViewHolder> {
    public HomeListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShouYeHotBean.DataBean.DataBeanX item) {
        TextView tvTitle = helper.getView(R.id.tvTitle);
        TextView tvName = helper.getView(R.id.tvName);
        TextView tvDate = helper.getView(R.id.tvDate);
        RoundImageView ivCover = helper.getView(R.id.ivCover);
        tvTitle.setText(item.getTitle());
        tvName.setText("学易车");
        tvDate.setText(item.getCreatetime());
        Glide.with(mContext).load(item.getImage()).into(ivCover);

    }
}
