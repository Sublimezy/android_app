package com.xueyiche.zjyk.xueyiche.practicecar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.RegistSiJiActivity;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.PracticeCarContentActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.adapter.PracticeCarAdapter;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.TrainWithBean;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.xueyiche.zjyk.xueyiche.daijia.RegistSiJiActivity;

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
public class PracticeCarActivity extends BaseActivity implements OnRefreshListener {
    //    @BindView(R.id.magic_indicator3)
//    MagicIndicator magicIndicator;
    @BindView(R.id.tv_top_right_button)
    TextView tvTopRightButton;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private static final String[] CHANNELS = new String[]{"有车", "无车"};
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
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
        tvTopRightButton.setVisibility(View.GONE);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).keyboardEnable(true).init();
        practiceCarAdapter = new PracticeCarAdapter(R.layout.item_practice_car_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        magicIndicator.setBackgroundResource(R.drawable.round_indicator_bg);
//        CommonNavigator commonNavigator = new CommonNavigator(this);/
//        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
//            @Override
//            public int getCount() {
//                return mDataList == null ? 0 : mDataList.size();
//            }
//
//            @Override
//            public IPagerTitleView getTitleView(Context context, final int index) {
//                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
//                clipPagerTitleView.setText(mDataList.get(index));
//                clipPagerTitleView.setTextColor(Color.parseColor("#e94220"));
//                clipPagerTitleView.setTextSize(30);
//                clipPagerTitleView.setClipColor(Color.WHITE);
//                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mFragmentContainerHelper.handlePageSelected(index);
//                    }
//                });
//                return clipPagerTitleView;
//            }
//
//            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                float navigatorHeight = context.getResources().getDimension(R.dimen.ershiwu);
//                float borderWidth = UIUtil.dip2px(context, 1);
//                float lineHeight = navigatorHeight - 2 * borderWidth;
//                indicator.setLineHeight(lineHeight);
//                indicator.setRoundRadius(lineHeight / 2);
//                indicator.setYOffset(borderWidth);
//                indicator.setColors(Color.parseColor("#ff5000"));
//                return indicator;
//            }
//        });
//        magicIndicator.setNavigator(commonNavigator);
//        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
//        mFragmentContainerHelper.handlePageSelected(0);
        recyclerView.setAdapter(practiceCarAdapter);

        practiceCarAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tvOrder:
                        TrainWithBean.DataBean.DataBean1 dataBean1 = (TrainWithBean.DataBean.DataBean1) adapter.getItem(position);
                        if (dataBean1!=null) {
                            ContentTrainNewActivity.forward(PracticeCarActivity.this,""+dataBean1.getId());
                        }
                        break;
                }
            }
        });
        practiceCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


            }
        });
    }


    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        geDataFromNet();
    }

    private void geDataFromNet() {
        showProgressDialog(false);
        Map<String, String> map = new HashMap<>();
        MyHttpUtils.postHttpMessage(AppUrl.trainwith, map, TrainWithBean.class, new RequestCallBack<TrainWithBean>() {
            @Override
            public void requestSuccess(TrainWithBean json) {
                if (1 == json.getCode()) {
                    TrainWithBean.DataBean data = json.getData();
                    if (data != null) {
                        List<TrainWithBean.DataBean.DataBean1> data1 = data.getData();
                        if (data1 != null && data1.size() > 0) {
                            practiceCarAdapter.setNewData(data1);
                        } else {
                            practiceCarAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
                        }
                    }
                    refreshLayout.finishRefresh();
                    stopProgressDialog();
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                stopProgressDialog();
            }
        });
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


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        geDataFromNet();
    }
}
