package com.icbc.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.icbc.paysdk.ICBCAPI;
import com.icbc.paysdk.IPayEventHandler;
import com.icbc.paysdk.model.PayResp;
import com.icbc.paysdk.model.ReqErr;
import com.xueyiche.zjyk.xueyiche.R;

/**
 * Created by Owner on 2017/6/12.
 */
public class PayResultHandler extends Activity implements IPayEventHandler {
    ICBCAPI api;
    TextView result_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pay_result_handler_layout);
        result_text = (TextView) findViewById(R.id.pay_result);

        ICBCAPI.getInstance().handleIntent(getIntent(), this);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        ICBCAPI.getInstance().handleIntent(intent, this);
    }



    @Override
    public void onErr(ReqErr err) {

        result_text.setText("支付错误："+ err.getErrorType());

    }


    @Override
    public void onResp(PayResp resp) {
        String tranCode = resp.getTranCode();
        String tranMsg = resp.getTranMsg();
        String orderNo = resp.getOrderNo();
        result_text.setText("交易码：" + tranCode + "\n交易信息：" + tranMsg + "\n订单号："+ orderNo);
        if(tranCode.equals("1")){
            result_text.setText("支付成功");
        }


    }
}
