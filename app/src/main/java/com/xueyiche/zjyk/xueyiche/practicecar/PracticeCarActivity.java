package com.xueyiche.zjyk.xueyiche.practicecar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
//import com.xueyiche.zjyk.xueyiche.daijia.RegistSiJiActivity;
import com.xueyiche.zjyk.xueyiche.daijia.RegistSiJiActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.PracticeCarContentActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.adapter.PracticeCarAdapter;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
 * #            Created by 張某人 on 2022/3/17/4:54 下午 .
 * #            com.xueyiche.zjyk.xueyiche.practicecar
 * #            xueyiche5.0
 */
public class PracticeCarActivity extends BaseActivity {
    @BindView(R.id.magic_indicator3)
    MagicIndicator magicIndicator;
    @BindView(R.id.tv_top_right_button)
    TextView tvTopRightButton;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private static final String[] CHANNELS = new String[]{"有车", "无车"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private List<String> list = new ArrayList<>();
    private PracticeCarAdapter practiceCarAdapter;


    public static void forward(Context context) {
        Intent intent = new Intent(context, PracticeCarActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int initContentView() {
        return R.layout.fragment_practice_car;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, view);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).keyboardEnable(true).init();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        practiceCarAdapter = new PracticeCarAdapter(R.layout.item_practice_car_layout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        magicIndicator.setBackgroundResource(R.drawable.round_indicator_bg);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#e94220"));
                clipPagerTitleView.setTextSize(30);
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float navigatorHeight = context.getResources().getDimension(R.dimen.ershiwu);
                float borderWidth = UIUtil.dip2px(context, 1);
                float lineHeight = navigatorHeight - 2 * borderWidth;
                indicator.setLineHeight(lineHeight);
                indicator.setRoundRadius(lineHeight / 2);
                indicator.setYOffset(borderWidth);
                indicator.setColors(Color.parseColor("#ff5000"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
        mFragmentContainerHelper.handlePageSelected(0);
        recyclerView.setAdapter(practiceCarAdapter);
        practiceCarAdapter.setNewData(list);
        practiceCarAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tvOrder:

                        break;
                }
            }
        });
        practiceCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(App.context, PracticeCarContentActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_top_right_button, R.id.ll_common_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_top_right_button:
                Intent intent = new Intent(App.context, RegistSiJiActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_common_back:
                finish();
                break;
        }
    }


}
