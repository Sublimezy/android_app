package com.xueyiche.zjyk.xueyiche.carlife.carbaoxian;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

/**
 * Created by Owner on 2017/10/26.
 */
public class CarBaoXianNew extends BaseActivity implements View.OnClickListener{

    private LinearLayout ll_bianji_back;
    private TextView mine_tv_title,tv_baoxian_xiadan,tv_baoxian_duihuan;
    private WebView wv_baoxian_huodong;

    @Override
    protected int initContentView() {
        return R.layout.car_baoxian_activity;
    }

    @Override
    protected void initView() {
        ll_bianji_back = (LinearLayout) view.findViewById(R.id.baoxian_new_include).findViewById(R.id.ll_exam_back);
        mine_tv_title = (TextView) view.findViewById(R.id.baoxian_new_include).findViewById(R.id.tv_login_back);
        tv_baoxian_duihuan = (TextView) view.findViewById(R.id.tv_baoxian_duihuan);
        tv_baoxian_xiadan = (TextView) view.findViewById(R.id.tv_baoxian_xiadan);
        wv_baoxian_huodong = (WebView) view.findViewById(R.id.wv_baoxian_huodong);
        mine_tv_title.setText("大地保险");
        ll_bianji_back.setOnClickListener(this);
        tv_baoxian_xiadan.setOnClickListener(this);
        tv_baoxian_duihuan.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            wv_baoxian_huodong.loadUrl("http://xueyiche.cn/xyc/insurance/insurance.html");
            wv_baoxian_huodong.setWebViewClient(new WebViewClient());
            WebSettings webSettings = wv_baoxian_huodong.getSettings();
            webSettings.setJavaScriptEnabled(true);
            wv_baoxian_huodong.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    stopProgressDialog();
                }
            });
        } else {
            stopProgressDialog();
            showToastShort("请检查网络");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_baoxian_xiadan:
                if (DialogUtils.IsLogin()) {
                    Intent intent1 = new Intent(this,CarBaoXianIndent.class);
                    startActivity(intent1);
                }else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.tv_baoxian_duihuan:
                if (DialogUtils.IsLogin()) {
                    Intent intent2 = new Intent(this,CarBaoXianDuiHuan.class);
                    startActivity(intent2);
                }else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
        }
    }
}
