package com.xueyiche.zjyk.xueyiche.homepage.activities.yibi_shop;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by ZL on 2017/10/18.
 */
public class YiBiShopContent extends BaseActivity implements View.OnClickListener {
    //易币商品详情
    private LinearLayout llBack;
    private LinearLayout ll_pay;
    private TextView tvTitle;
    private TextView tv_goods_money;
    private Button bt_share;
    private WebView shop_web;
    private String em_id;
    private String em_name;
    private String em_pic_url;
    private String em_goods_money;

    @Override
    protected int initContentView() {
        return R.layout.yibi_shop_content;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.yibi_content_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.yibi_content_include).findViewById(R.id.tv_login_back);
        //显示消耗的易币
        tv_goods_money = (TextView) view.findViewById(R.id.tv_goods_money);
        //分享
        bt_share = (Button) view.findViewById(R.id.bt_share);
        //zhifu
        ll_pay = (LinearLayout) view.findViewById(R.id.ll_pay);
        //xiangqing
        shop_web = (WebView) view.findViewById(R.id.shop_web);

        llBack.setOnClickListener(this);
        bt_share.setOnClickListener(this);
        ll_pay.setOnClickListener(this);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("详情");
        Intent intent = getIntent();
        em_id = intent.getStringExtra("em_id");
        em_name = intent.getStringExtra("em_name");
        em_pic_url = intent.getStringExtra("em_pic_url");
        em_goods_money = intent.getStringExtra("em_goods_money");
        tv_goods_money.setText(em_goods_money+"易币");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            shop_web.loadUrl("http://222.171.205.11:8080/xyc/detail/detail.html?em_id="+em_id+"&Identification=2");
            shop_web.setWebViewClient(new WebViewClient());
            WebSettings webSettings = shop_web.getSettings();
            webSettings.setJavaScriptEnabled(true);
            shop_web.setWebViewClient(new WebViewClient() {
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
            case R.id.bt_share:
                //判定有没有登录和过期
                if (DialogUtils.IsLogin()) {
                    sharedGood();
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.ll_pay:
                //判定有没有登录和过期
                if (DialogUtils.IsLogin()) {
                    Intent intent = new Intent(App.context, QueRen.class);
                    intent.putExtra("em_id", em_id);
                    intent.putExtra("em_name", em_name);
                    intent.putExtra("em_pic_url", em_pic_url);
                    intent.putExtra("em_goods_money", em_goods_money);
                    startActivity(intent);
                    finish();
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
        }
    }

    private void sharedGood() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("学易车-易动华夏");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://222.171.205.11:8080/xyc/detail/detail.html?em_id="+em_id+"&Identification=1");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(em_name);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(em_pic_url);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://222.171.205.11:8080/xyc/detail/detail.html?em_id="+em_id+"&Identification=1");
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getDataNet();
                    }
                });

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
// 启动分享GUI
        oks.show(this);
    }

    private void getDataNet() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.QianDao)
                    .addParams("user_id", user_id)
                    .addParams("em_detail_type ", "0")
                    .addParams("em_id", em_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        SuccessBackBean yiBiSharedBean = JsonUtil.parseJsonToBean(string, SuccessBackBean.class);
                        if (yiBiSharedBean != null) {
                            final String content = yiBiSharedBean.getContent();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showToastShort(content);
                                    }
                                });
                        }
                    }
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {
                    stopProgressDialog();
                }

                @Override
                public void onResponse(Object response) {
                    stopProgressDialog();
                }
            });
        }

    }

}
