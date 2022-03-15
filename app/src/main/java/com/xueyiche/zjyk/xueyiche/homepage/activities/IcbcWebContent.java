package com.xueyiche.zjyk.xueyiche.homepage.activities;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

/**
 * Created by ZL on 2017/2/18.
 */
public class IcbcWebContent extends BaseActivity implements View.OnClickListener {
    private WebView news_content;
    private String consult_url;
    private LinearLayout ll_bianji_back;
    private TextView mine_tv_title;

    @Override
    protected int initContentView() {
        return R.layout.must_pass_skill;
    }

    @Override
    protected void initView() {

        news_content = (WebView) view.findViewById(R.id.pass_web_view);
        ll_bianji_back = (LinearLayout) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.ll_exam_back);
        mine_tv_title = (TextView) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.tv_login_back);
        ll_bianji_back.setOnClickListener(this);

    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        consult_url = intent.getStringExtra("consult_url");
        String new_gongyin = intent.getStringExtra("new_gongyin");
        if ("gongyin".equals(new_gongyin)) {
            mine_tv_title.setText("学易车联名卡申请");
        }
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            news_content.loadUrl(consult_url);
            news_content.setWebViewClient(new WebViewClient());
            WebSettings webSettings = news_content.getSettings();
            webSettings.setJavaScriptEnabled(true);
            news_content.setWebViewClient(new WebViewClient() {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                if (news_content.canGoBack()) {
                    news_content.goBack();
                } else {
                    finish();
                }
                break;
        }
    }

}
