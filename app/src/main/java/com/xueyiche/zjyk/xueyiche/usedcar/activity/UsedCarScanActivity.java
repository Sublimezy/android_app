package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by ZL on 2018/7/23.
 */
public class UsedCarScanActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private String agent_shop_number;
    private QRCodeView mQRCodeView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erweima_yanzheng_activity);
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpot();
        agent_shop_number = PrefUtils.getString(App.context, "agent_shop_number", "");
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(final String result) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.Driver_Map)
                    .addParams("agent_shop_number",agent_shop_number )
                    .addParams("agent_user_phone",result )
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {

                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        SuccessBackBean daiLiYanZhengBean = JsonUtil.parseJsonToBean(string, SuccessBackBean.class);
                        if (daiLiYanZhengBean!=null) {
                            final String content = daiLiYanZhengBean.getContent();
                            final int code = daiLiYanZhengBean.getCode();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mIntent = new Intent();
                                    mIntent.putExtra("code", ""+code);
                                    mIntent.putExtra("content", content);
                                    // 设置结果，并进行传送
                                    setResult(0, mIntent);
                                    finish();
                                }
                            });
                        }
                    }

                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {
                    Toast.makeText(App.context, StringConstants.CHECK_NET,Toast.LENGTH_LONG);
                }

                @Override
                public void onResponse(Object response) {
                }
            });
        }else {
            Toast.makeText(App.context,StringConstants.CHECK_NET,Toast.LENGTH_LONG);
        }


    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("aaaaaaa", "打开相机出错");
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent mIntent = new Intent();
            mIntent.putExtra("code", "");
            mIntent.putExtra("content", "");
            // 设置结果，并进行传送
            setResult(0, mIntent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
