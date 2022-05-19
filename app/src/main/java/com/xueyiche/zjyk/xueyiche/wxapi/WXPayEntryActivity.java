package com.xueyiche.zjyk.xueyiche.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Owner on 2017/1/20.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, "wx3c3dcf4648234b46");
        api.handleIntent(getIntent(), this);
        Log.e("pay_ccccc", "aaaaaaaaa");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.e("pay_dddddddddd", "lllllllllllll");
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.e("pay_bbb", "" + new Gson().toJson(req));

    }

    /**
     * 四、接收支付返回结果：
     */
    @Override
    public void onResp(BaseResp resp) {
        String errStr = resp.errStr;
        Log.e("pay_aaa", "" + new Gson().toJson(resp));
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String s = String.valueOf(resp.errCode);
            Log.e("pay_code", "" + s);
            if ("0".equals(s)) {
                EventBus.getDefault().post(new MyEvent("支付成功"));

                EventBus.getDefault().post(new MyEvent("打赏支付成功"));


            }
            finish();
        }
        //回调中errCode值
// 0    成功  展示成功页面
//-1    错误  可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//-2    用户取消    无需处理。发生场景：用户不支付了，点击取消，返回APP。
    }
}
