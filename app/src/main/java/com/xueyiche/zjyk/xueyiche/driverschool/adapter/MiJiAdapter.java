package com.xueyiche.zjyk.xueyiche.driverschool.adapter;

import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.discover.bean.VideoBean;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.MiJIListBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;

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
 * #            Created by 張某人 on 6/21/21/2:34 PM .
 * #            com.xueyiche.zjyk.xueyiche.driverschool.adapter
 * #            xueyiche3.0
 */
public class MiJiAdapter extends BaseQuickAdapter<MiJIListBean.ContentBean, BaseViewHolder> {
    public MiJiAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MiJIListBean.ContentBean item) {
        RoundImageView video_bg = helper.getView(R.id.video_bg);
        TextView tvTitle = helper.getView(R.id.tvTitle);
        tvTitle.setText("" + item.getTitle());
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(App.context,250));
        layoutParams1.setMargins(DensityUtil.dip2px( App.context,5), 0, DensityUtil.dip2px( App.context,5), 0);
        video_bg.setLayoutParams(layoutParams1);
        Picasso.with(App.context).load("" + item.getSecret_img_url()).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(video_bg);
    }
}
