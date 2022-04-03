package com.xueyiche.zjyk.xueyiche.constants;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.homepage.activities.ReWebViewClient;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Owner on 2017/3/1.
 */
public class UrlActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private TextView tvTitle, tv_wenxintishi;
    private WebView pass_web_view;
    private JavaScriptInterface JSInterface;
    private String type;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    /* 请求识别码 */
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private Uri imageUri;
    private String url;
    private String address;
    private String main_title,sub_title;


    @Override
    protected int initContentView() {
        return R.layout.must_pass_skill;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.must_pass_skill_include).findViewById(R.id.tv_wenxintishi);
        pass_web_view = (WebView) view.findViewById(R.id.pass_web_view);
    }
    public static void forward(Context context,String url,String type) {
        Intent intent = new Intent(context, UrlActivity.class);
        intent.putExtra("url", ""+url);
        intent.putExtra("type", ""+type);
        context.startActivity(intent);
    }
    @Override
    protected void initListener() {
        tv_wenxintishi.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL)
                return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                Log.e("Mr.Kang", "onActivityResult: " + result);
                if (result == null) {
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;

                    Log.e("Mr.Kang", "onActivityResult: " + imageUri);
                } else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }


            }
        }


    }

    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }

        return;
    }


    private void takePhoto() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "XYC");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                showToastShort("您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "图片选择");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        UrlActivity.this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        type = intent.getStringExtra("type");
        if (!TextUtils.isEmpty(type)) {
            if ("0".equals(type)) {
                tvTitle.setText("关于驾校");
            }
            if ("1".equals(type)) {
                tvTitle.setText("报名须知");
            }
            if ("2".equals(type)) {
                //轮播图url
                WebChromeClient wvcc = new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        //title就是网页的title
                        tvTitle.setText(title);
                    }
                };
                pass_web_view.setWebChromeClient(wvcc);
                open(url);
            }
            if ("3".equals(type)) {
                tvTitle.setText("中国工商银行学易车联名卡");
            }
            if ("4".equals(type)) {
                tvTitle.setText("优惠劵详情");
            }
            if ("5".equals(type)) {
                tvTitle.setText("新车报价大全");
            }
            if ("6".equals(type)) {
                tvTitle.setText("检测报告");
            }
            if ("7".equals(type)) {
                tvTitle.setText("兼职");
                open(url);
            }
            if ("8".equals(type)) {
                tvTitle.setText("学车流程");
            }
            if ("9".equals(type)) {
                tvTitle.setText("安全须知");
            }
            if ("10".equals(type)) {
                tvTitle.setText("代驾司机注册");
                open(url);
            }
            if ("11".equals(type)) {
                tvTitle.setText("网约练车司机注册");
                open(url);
            }
            if ("12".equals(type)) {
                WebChromeClient wvcc = new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        //title就是网页的title
                        tvTitle.setText(title);
                    }
                };
                pass_web_view.setWebChromeClient(wvcc);
                open(url);
            }
            if ("99".equals(type)) {
                main_title = intent.getStringExtra("main_title");
                sub_title = intent.getStringExtra("sub_title");
                WebChromeClient wvcc = new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        //title就是网页的title
                        tvTitle.setText(title);
                        tv_wenxintishi.setText("分享");
                        tv_wenxintishi.setVisibility(View.VISIBLE);
                    }
                };
                pass_web_view.setWebChromeClient(wvcc);
                address = intent.getStringExtra("address");
                open(url);
            }
            if ("100".equals(type)) {
                tvTitle.setText("违章查询");
            }
            if ("101".equals(type)) {
                tvTitle.setText("保险查询");
            }
            if ("102".equals(type)) {
                tvTitle.setText("维保查询");
            }
        }
        if (!TextUtils.isEmpty(url)) {
            pass_web_view.loadUrl(url);
            pass_web_view.setWebViewClient(new ReWebViewClient());
            WebSettings webSettings = pass_web_view.getSettings();
            webSettings.setJavaScriptEnabled(true);
            JSInterface = new JavaScriptInterface(UrlActivity.this);
            pass_web_view.addJavascriptInterface(JSInterface, "JSInterface");
            if (XueYiCheUtils.IsHaveInternet(App.context)) {
                showProgressDialog(false);
                pass_web_view.setWebViewClient(new WebViewClient() {
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

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("5".equals(type) || "7".equals(type)) {
                    if (pass_web_view.canGoBack()) {
                        pass_web_view.goBack();
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
            }
        });
    }

    private void open(String path) {
        pass_web_view.loadUrl(path);
        pass_web_view.getSettings().setJavaScriptEnabled(true);
        pass_web_view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        WebSettings settings = pass_web_view.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        settings.setBlockNetworkImage(false);//解决图片不显示
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        pass_web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }
        });
        pass_web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                takePhoto();
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                takePhoto();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                takePhoto();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                takePhoto();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ("5".equals(type) || "7".equals(type)) {
                if (pass_web_view.canGoBack()) {
                    pass_web_view.goBack();
                } else {
                    finish();
                }
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wenxintishi:
                common_share();
//                shared();
                break;
            default:

                break;
        }
    }

    private void common_share() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(!TextUtils.isEmpty(main_title)?main_title:"学易车");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(sub_title);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(address);

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // 启动分享GUI
        oks.show(this);
    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void openGongYin(String url) {

            Intent intent = new Intent(UrlActivity.this, UrlActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("type", "2");
            startActivity(intent);
        }

        @JavascriptInterface
        public void openJiaXiao() {
        }

        @JavascriptInterface
        public void ShareYouHuiQuan() {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    shared();

                }
            });

        }

        @JavascriptInterface
        public void openPhoto() {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    shared();

                }
            });

        }

        @JavascriptInterface
        public void CheJieShi(final String chejieshi) {

        }


    }

    private void shared() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("学易车0元洗车 点击领取免费洗车券");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://www.xueyiche.cn/xyc/youhuijuan/share.html");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("学易车携手车捷仕打造0元洗车活动，优惠冰城全市免费");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://images.xueyiche.vip/20180111135557.png");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.xueyiche.cn/xyc/youhuijuan/share.html");
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String user_id = PrefUtils.getString(UrlActivity.this, "user_id", "");
                        OkHttpUtils.post().url(AppUrl.GETYOUHUIQUAN)
                                .addParams("user_id", user_id)
                                .build().execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws IOException {
                                String string = response.body().string();
                                if (!TextUtils.isEmpty(string)) {
                                    SuccessBackBean successBackBean = JsonUtil.parseJsonToBean(string, SuccessBackBean.class);
                                    if (successBackBean != null) {
                                        int code = successBackBean.getCode();
                                        final String content = successBackBean.getContent();
                                        if (0 == code) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (content.equals("0")) {
                                                        Toast.makeText(App.context, "分享成功", Toast.LENGTH_SHORT).show();
                                                    } else if (content.equals("1")) {
                                                        DialogUtils.showDaDiBaoXian(UrlActivity.this, "分享成功，获得免费洗车券");
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                                return null;
                            }

                            @Override
                            public void onError(Request request, Exception e) {

                            }

                            @Override
                            public void onResponse(Object response) {

                            }
                        });
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
