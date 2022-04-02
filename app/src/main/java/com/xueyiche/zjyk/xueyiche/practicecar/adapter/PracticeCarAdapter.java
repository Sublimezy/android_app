package com.xueyiche.zjyk.xueyiche.practicecar.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;

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
 * #            Created by 張某人 on 2022/3/17/6:00 下午 .
 * #            com.xueyiche.zjyk.xueyiche.practicecar.adapter
 * #            xueyiche5.0
 */
public class PracticeCarAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PracticeCarAdapter(int layoutResId) {
        super(layoutResId);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        CustomShapeImageView iv_head = helper.getView(R.id.iv_head);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tvJiaLing = helper.getView(R.id.tvJiaLing);
        TextView tv_have_money = helper.getView(R.id.tv_have_money);
        TextView tv_no_money = helper.getView(R.id.tv_no_money);
        TextView tvType = helper.getView(R.id.tvType);
        TextView tvOrder = helper.getView(R.id.tvOrder);
        helper.addOnClickListener(R.id.tvOrder);
        int adapterPosition = helper.getAdapterPosition();
        if (0==adapterPosition) {
            tv_have_money.setVisibility(View.VISIBLE);
            tv_no_money.setVisibility(View.GONE);
            tvType.setVisibility(View.VISIBLE);
        }else {
            tv_have_money.setVisibility(View.GONE);
            tv_no_money.setVisibility(View.VISIBLE);
            tvType.setVisibility(View.GONE);
        }

    }
}
