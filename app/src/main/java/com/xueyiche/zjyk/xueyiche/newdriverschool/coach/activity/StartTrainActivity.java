package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.StartTrainBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.LogUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.service.UpLocationService;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开始练车
 */
public class StartTrainActivity extends NewBaseActivity {


    @BindView(R.id.title_bar_iv_back)
    ImageView titleBarIvBack;
    @BindView(R.id.title_bar_back)
    RelativeLayout titleBarBack;
    @BindView(R.id.title_bar_title_text)
    TextView titleBarTitleText;
    @BindView(R.id.title_bar_rl)
    RelativeLayout titleBarRl;
    @BindView(R.id.title_bar_right_text)
    TextView titleBarRightText;
    @BindView(R.id.tv_total_today)
    TextView tvTotalToday;
    @BindView(R.id.tv_real_come_num)
    TextView tvRealComeNum;
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    private String coach_id;

    @Override
    protected int initContentView() {
        return R.layout.activity_start_train;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        titleBarRl.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarIvBack.setImageDrawable(getResources().getDrawable(R.mipmap.white_iv_back));
        titleBarTitleText.setText("开始练车");
        titleBarTitleText.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {


        showProgressDialog(false, "加载中...");
        coach_id = getIntent().getStringExtra("coach_id");

//        1:开始 2:结束
        lianChe("1");



    }

    @Override
    protected void onResume() {
        super.onResume();
        GDLocation gdLocation = new GDLocation();
        gdLocation.startLocation();

    }

    private void lianChe(String training_type) {
        String lon = PrefUtils.getParameter("x");// 经度
        String lat = PrefUtils.getParameter("y");//纬度

        PrefUtils.getParameter("address"); //地址


        Map<String, String> params = new HashMap<>();
//        PrefUtils.putParameter("x");
        params.put("coach_user_id", coach_id);
//        1:开始 2:结束
        if("1".equals(training_type)){
            params.put("coach_boarding_lon", lon);
            params.put("coach_boarding_lat", lat);
        }else{
            params.put("coach_getoff_lon", lon);
            params.put("coach_getoff_lat", lat);
        }
        params.put("training_type", training_type);
        MyHttpUtils.postHttpMessage(AppUrl.startendpracticecar, params, StartTrainBean.class, new RequestCallBack<StartTrainBean>() {
            @Override
            public void requestSuccess(StartTrainBean json) {
                stopProgressDialog();
                if (json.getCode() == 200) {

                    if ("1".equals(training_type)) {

                        tvTotalToday.setText(json.getContent().getToday_people() + "");
                        tvRealComeNum.setText(json.getContent().getReal_people() + "");
                        Intent ser = new Intent(StartTrainActivity.this, UpLocationService.class);
                        startService(ser);
                    } else {
                        setResult(1234);
                        finish();
                        Intent ser = new Intent(StartTrainActivity.this, UpLocationService.class);
                        stopService(ser);
                    }
                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

                stopProgressDialog();
                showToastShort("服务器连接失败,稍后再试!");
            }
        });
    }


    @OnClick({R.id.title_bar_back, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.tv_btn:
                lianChe("2");
                break;
        }
    }
}
