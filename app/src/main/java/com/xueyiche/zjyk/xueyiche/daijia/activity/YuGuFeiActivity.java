package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.YuSuanBean;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/9/26.
 */
public class YuGuFeiActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right_btn)
    TextView tv_wenxintishi;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_qibu)
    TextView tvQibu;
    @BindView(R.id.tv_shichangfei_title)
    TextView tvShichangfeiTitle;
    @BindView(R.id.tv_shichangfei)
    TextView tvShichangfei;
    @BindView(R.id.tv_lichengfei_title)
    TextView tvLichengfeiTitle;
    @BindView(R.id.tv_lichengfei)
    TextView tvLichengfei;
    @BindView(R.id.tv_lichengfei_title_nei)
    TextView tvLichengfeiTitleNei;
    @BindView(R.id.tv_lichengfei_nei)
    TextView tvLichengfeiNei;
    @BindView(R.id.tv_lichengfei_title_wai)
    TextView tvLichengfeiTitleWai;
    @BindView(R.id.tv_lichengfei_wai)
    TextView tvLichengfeiWai;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;


    public static void forward(Context context, String start_lng, String start_lat, String end_lng, String end_lat) {
        Intent intent = new Intent(context, YuGuFeiActivity.class);
        intent.putExtra("start_lng", start_lng);
        intent.putExtra("start_lat", start_lat);
        intent.putExtra("end_lng", end_lng);
        intent.putExtra("end_lat", end_lat);
        context.startActivity(intent);
    }

    @Override
    protected int initContentView() {
        return R.layout.yugu_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, view);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        tv_title.setText("预估费");
//        tv_wenxintishi.setText("计费规则");
        getYuSuan();
    }

    private void getYuSuan() {
        Intent intent = getIntent();
        String start_lng = intent.getStringExtra("start_lng");
        String start_lat = intent.getStringExtra("start_lat");
        String end_lng = intent.getStringExtra("end_lng");
        String end_lat = intent.getStringExtra("end_lat");
        Map<String, String> map = new HashMap<>();
        map.put("start_lng", "" + start_lng);
        map.put("start_lat", "" + start_lat);
        map.put("end_lng", "" + end_lng);
        map.put("end_lat", "" + end_lat);
        MyHttpUtils.postHttpMessage(AppUrl.orderBudgetPrice, map, YuSuanBean.class, new RequestCallBack<YuSuanBean>() {
            @Override
            public void requestSuccess(YuSuanBean json) {
                if (1 == json.getCode()) {
                    YuSuanBean.DataBean data = json.getData();
                    tvMoney.setText("约"+data.getPrice()+"元");
                    tvQibu.setText(""+data.getPrice()+"元");
                    tvShichangfeiTitle.setText("时长费（共"+data.getShichang_time()+"分钟）");
                    tvShichangfei.setText(""+data.getShichang_price()+"元");
                    tvLichengfeiTitle.setText("里程费（共"+data.getLicheng_km()+"公里）");
                    tvLichengfei.setText(""+data.getLicheng_price()+"元");
                    tvLichengfeiTitleNei.setText("区域内里程（共"+data.getNeiquyu_km()+"公里）");
                    tvLichengfeiNei.setText(""+data.getLicheng_price()+"元");
                    tvLichengfeiTitleWai.setText("区域外里程（共"+data.getWaiquyu_km()+"公里）");
                    tvLichengfeiWai.setText(""+data.getWaiquyu_km_price()+"元");
                    tvYouhui.setText(""+data.getYouhui_price()+"元");

                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    @OnClick({R.id.ll_common_back, R.id.jifei_rule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.jifei_rule:
                UrlActivity.forward(YuGuFeiActivity.this, "http://tabankeji.com/djh5/jifeiguize.html", "103");
                break;
        }
    }
}
