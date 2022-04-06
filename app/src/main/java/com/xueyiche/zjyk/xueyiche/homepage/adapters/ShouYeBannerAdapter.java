package com.xueyiche.zjyk.xueyiche.homepage.adapters;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

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
 * #            Created by 張某人 on 2022/4/6/3:01 下午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage.adapters
 * #            xueyiche5.0
 */
public class ShouYeBannerAdapter extends BaseBannerAdapter<String> {


    @Override
    protected void bindData(BaseViewHolder<String> holder, String data, int position, int pageSize) {
        ImageView imageView = holder.findViewById(R.id.image);

//            new RoundImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Picasso.with(App.context)
                .load(data)
                .into(imageView);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.shouye_banner2;
    }
}

