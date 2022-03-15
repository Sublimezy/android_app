package com.xueyiche.zjyk.xueyiche.splash;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

/**
 * Created by Owner on 2017/4/26.
 */
public class GuangGaoActivity extends BaseActivity {
    private LinearLayout llBack;
    private LinearLayout ll_must;
    private TextView tvTitle;
    private WebView pass_web_view;

    @Override
    protected int initContentView() {
        return R.layout.must_pass_skill;
    }

    @Override
    protected void initView() {
        showProgressDialog(false);
        llBack = (LinearLayout) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.tv_login_back);
        pass_web_view = (WebView) view.findViewById(R.id.pass_web_view);
        ll_must = (LinearLayout) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.ll_must);


    }

    @Override
    protected void initListener() {

    }
    @Override
    protected void initData() {
        Intent intent = getIntent();
        String guanggao = intent.getStringExtra("guanggao");

        if (XueYiCheUtils.IsHaveInternet(this)) {
            if (!TextUtils.isEmpty(guanggao)) {
                WebChromeClient wvcc = new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        //title就是网页的title
                        tvTitle.setText(title);
                    }
                };
                pass_web_view.setWebChromeClient(wvcc);
                pass_web_view.loadUrl(guanggao);
                pass_web_view.setWebViewClient(new WebViewClient());
                WebSettings webSettings = pass_web_view.getSettings();
                webSettings.setJavaScriptEnabled(true);
            }
            pass_web_view.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    stopProgressDialog();
                }
            });
        }else {
            stopProgressDialog();
            showToastShort("请检查网络");
        }


        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(App.context, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            startActivity(new Intent(App.context, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
}
