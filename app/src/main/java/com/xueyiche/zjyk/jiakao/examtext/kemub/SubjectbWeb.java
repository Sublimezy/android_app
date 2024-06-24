package com.xueyiche.zjyk.jiakao.examtext.kemub;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.utils.XueYiCheUtils;

/**
 * Created by Owner on 2016/12/30.
 */
public class SubjectbWeb extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private LinearLayout ll_exam_back;
    private WebView wb_subjectb;

    @Override
    protected int initContentView() {
        return R.layout.activity_subjectb_web;
    }

    @Override
    protected void initView() {
        tv_title = (TextView) view.findViewById(R.id.indent_content_include).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.indent_content_include).findViewById(R.id.ll_exam_back);
        wb_subjectb = (WebView) view.findViewById(R.id.wb_subjectb);
        ll_exam_back.setOnClickListener(this);
        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            if ("daoche_ruku".equals(title)) {
                tv_title.setText("倒车入库");
                wb_subjectb.loadUrl("http://xueyiche.cn/xyc/jiaocheng/daocheruku.html");
            } else if ("potingpoqi".equals(title)) {
                tv_title.setText("坡停坡起");
                wb_subjectb.loadUrl("http://xueyiche.cn/xyc/jiaocheng/poqi.html");
            } else if ("cefangtingche".equals(title)) {
                tv_title.setText("侧方停车");
                wb_subjectb.loadUrl("http://xueyiche.cn/xyc/jiaocheng/cefangweitingche.html");
            } else if ("xuxianxingshi".equals(title)) {
                tv_title.setText("曲线行驶");
                wb_subjectb.loadUrl("http://xueyiche.cn/xyc/jiaocheng/quxianxingshi.html");
            } else if ("zhijiaozhuanwan".equals(title)) {
                tv_title.setText("直角转弯");
                wb_subjectb.loadUrl("http://xueyiche.cn/xyc/jiaocheng/zhijiaozhuanwan.html");

            }
            wb_subjectb.setWebViewClient(new WebViewClient());
            wb_subjectb.getSettings().setJavaScriptEnabled(true);
            WebSettings webSettings = wb_subjectb.getSettings();
            webSettings.setJavaScriptEnabled(true);
            wb_subjectb.setWebViewClient(new WebViewClient() {
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
    protected void onResume() {
        super.onResume();
        wb_subjectb.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wb_subjectb.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }
}
