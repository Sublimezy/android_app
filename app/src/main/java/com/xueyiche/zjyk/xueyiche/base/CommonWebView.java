package com.xueyiche.zjyk.xueyiche.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZL on 2017/1/10.
 */
public class CommonWebView extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.pass_web_view)
    WebView pass_web_view;
    @Override
    protected int initContentView() {
        return R.layout.must_pass_skill;
    }
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).init();
        showProgressDialog(false);
    }
    public static void forward(Context context,String weburl) {
        Intent intent = new Intent(context, CommonWebView.class);
        intent.putExtra("weburl",weburl);
        context.startActivity(intent);
    }
    @Override
    protected void initListener() {
    }
    @Override
    protected void initData() {
        Intent intent = getIntent();
        String weburl = intent.getStringExtra("weburl");
        String httpUrl = intent.getStringExtra("httpUrl");
        String baoxianurl = intent.getStringExtra("baoxianurl");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            if ("biguo".equals(weburl)) {
                tvTitle.setText("必过技巧");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/monilianxi/biguojiqiao.html");
            } else if ("one_talk".equals(weburl)) {
                tvTitle.setText("考试说明");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/monilianxi/keyishuoming.html");
            } else if ("two_xuzhi".equals(weburl)) {
                tvTitle.setText("科二须知");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/monilianxi/keerxuzhi.html");
                pass_web_view.setWebViewClient(new WebViewClient());
            } else if ("three_xuzhi".equals(weburl)) {
                tvTitle.setText("科三须知");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/monilianxi/kesanxuzhi.html");
            } else if ("four_talk".equals(weburl)) {
                tvTitle.setText("考试说明");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/monilianxi/kesishuoming.html");
            } else if ("one_important".equals(weburl)) {
                tvTitle.setText("考试重点");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/zhongdian/keyikaoshizhongd.html");

            } else if ("four_important".equals(weburl)) {
                tvTitle.setText("考试重点");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/zhongdian/kesikaoshizhongd.html");
            } else if ("fuwuxuzhi".equals(weburl)) {
                tvTitle.setText("服务承诺");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/kaojiazhao/fuwuchengnuo.html");
                pass_web_view.setWebViewClient(new WebViewClient());
            } else if ("xucheliucheng".equals(weburl)) {
                tvTitle.setText("学车流程");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/kaojiazhao/xuecheliucheng.html");
            } else if ("baomingxuzhi".equals(weburl)) {
                tvTitle.setText("报名须知");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/kaojiazhao/baomingxuzhi.html");
            } else if ("jiaxiaotishi".equals(weburl)) {
                tvTitle.setText("返现提示");
                pass_web_view.loadUrl("http://xueyiche.cn/activity/activity-flow.html");
            } else if ("baoxian".equals(weburl)) {
                tvTitle.setText("大地保险");
                pass_web_view.loadUrl(baoxianurl);
            } else if ("fanxian".equals(weburl)) {
                tvTitle.setText("返现流程");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/reverse-flow/reverse-flow.html");

            } else if ("xieyi".equals(weburl)) {
                tvTitle.setText("服务协议");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/agreement/userAgreement.html");
            } else if ("zhengxinbaogao".equals(weburl)) {
                tvTitle.setText("征信报告");
                pass_web_view.loadUrl(baoxianurl);
            } else if ("paihangbang".equals(weburl)) {
                tvTitle.setText("排行榜");
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/agreement/userAgreement.html");
            } else if ("zhengxinxieyi".equals(weburl)) {
                tvTitle.setText("个人信息查询授权书");
                pass_web_view.loadUrl(baoxianurl);
            } else if ("sy_gg".equals(weburl)) {
                //首页广告
                WebChromeClient wvcc = new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        //title就是网页的title
                        tvTitle.setText(title);
                    }
                };
                pass_web_view.setWebChromeClient(wvcc);
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/moudledriver/moudledriver.html");
            } else if ("zhaomu".equals(weburl)) {
                WebChromeClient wvcc = new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        //title就是网页的title
                        tvTitle.setText(title);
                    }
                };
                pass_web_view.setWebChromeClient(wvcc);
                pass_web_view.loadUrl("http://xueyiche.cn/xyc/moudledriver/moudledriver.html");
            }else if("wangye".equals(weburl)){

                WebChromeClient wvcc = new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        //title就是网页的title
                        tvTitle.setText(title);
                    }
                };
                pass_web_view.setWebChromeClient(wvcc);
                pass_web_view.loadUrl(httpUrl);
            }
            pass_web_view.setWebViewClient(new WebViewClient());
            WebSettings webSettings = pass_web_view.getSettings();
            webSettings.setJavaScriptEnabled(true);
            pass_web_view.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    stopProgressDialog();
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    stopProgressDialog();
                }
            });
        } else {
            stopProgressDialog();
            showToastShort("请检查网络");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        pass_web_view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pass_web_view.onPause();
    }


    @OnClick(R.id.ll_common_back)
    public void onClick() {
        finish();
    }
}
