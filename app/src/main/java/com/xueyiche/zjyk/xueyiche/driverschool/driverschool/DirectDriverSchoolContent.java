package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.submit.DirectDriverSchoolSubmitIndent;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

/**
 * Created by ZL on 2019/3/5.
 */
public class DirectDriverSchoolContent extends BaseActivity implements View.OnClickListener {
    private WebView news_content;
    private String consult_url;
    private LinearLayout ll_bianji_back;
    private TextView mine_tv_title, tv_baoming;

    @Override
    protected int initContentView() {
        return R.layout.must_pass_skill;
    }

    @Override
    protected void initView() {
        news_content = (WebView) view.findViewById(R.id.pass_web_view);
        tv_baoming = (TextView) view.findViewById(R.id.tv_baoming);
        ll_bianji_back = (LinearLayout) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.ll_exam_back);
        mine_tv_title = (TextView) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.tv_login_back);
        ll_bianji_back.setOnClickListener(this);
        tv_baoming.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mine_tv_title.setText("详情");

        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            news_content.loadUrl("http://xueyiche.cn/xyc/gongyi/zhitongche.html");
            news_content.setWebViewClient(new WebViewClient());
            WebSettings webSettings = news_content.getSettings();
            webSettings.setJavaScriptEnabled(true);
            news_content.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    stopProgressDialog();
                    tv_baoming.setVisibility(View.VISIBLE);
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
                if (news_content.canGoBack()) {
                    news_content.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.tv_baoming:
                if (DialogUtils.IsLogin()) {
                    openActivity(DirectDriverSchoolSubmitIndent.class);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
        }
    }
}
