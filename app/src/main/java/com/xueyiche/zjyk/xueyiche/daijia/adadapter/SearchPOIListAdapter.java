package com.xueyiche.zjyk.xueyiche.daijia.adadapter;
import android.widget.TextView;
import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.R;
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
 * #            Created by 張某人 on 2022/3/25/2:55 下午 .
 * #            com.example.administrator.xuyiche_daijia.adapter
 * #            xueyiche_daijia
 */
public class SearchPOIListAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    public SearchPOIListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
       TextView tvTitle =  helper.getView(R.id.tvTitle);
       TextView tvContent =  helper.getView(R.id.tvContent);
       tvTitle.setText(item.getProvinceName()+item.getCityName()+item.getAdName()+item.getTitle());
        tvContent.setText(item.getSnippet());
    }
}
