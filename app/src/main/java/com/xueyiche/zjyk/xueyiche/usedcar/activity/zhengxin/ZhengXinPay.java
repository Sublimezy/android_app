package com.xueyiche.zjyk.xueyiche.usedcar.activity.zhengxin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.WXZhiFuBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.examtext.CommonWebView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.pay.bean.ZhiFuBaoBean;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.ZhengXinChaXunBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PayResult;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2018/10/10.
 */
public class ZhengXinPay extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle, tv_money;
    private Button bt_pay;
    private ImageView ivWechat, ivZhifubao, iv_post, iv_yinlian;
    private RelativeLayout rl_pay_zhifubao, rl_pay_wechat, rl_pay_pos, rl_indent_yinlian;
    private String isClick = "3";
    private String user_id;
    private String string;
    private String charging;
    private String credit_sfz, order_number;


    @Override
    protected int initContentView() {
        return R.layout.zhengxin_pay;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        rl_pay_zhifubao = (RelativeLayout) view.findViewById(R.id.rl_zhifubao);
        rl_pay_pos = (RelativeLayout) view.findViewById(R.id.rl_pos);
        ivZhifubao = (ImageView) view.findViewById(R.id.iv_zhifubao);
        //微信
        rl_pay_wechat = (RelativeLayout) view.findViewById(R.id.rl_wechat);
        ivWechat = (ImageView) view.findViewById(R.id.iv_wechat);
        iv_yinlian = (ImageView) view.findViewById(R.id.iv_yinlian);
        iv_post = (ImageView) view.findViewById(R.id.iv_post);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        rl_indent_yinlian = (RelativeLayout) view.findViewById(R.id.rl_yinlian);
        bt_pay = (Button) view.findViewById(R.id.bt_pay);
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        bt_pay.setOnClickListener(this);
        rl_pay_zhifubao.setOnClickListener(this);
        rl_pay_wechat.setOnClickListener(this);
        rl_pay_pos.setOnClickListener(this);
        rl_indent_yinlian.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("支付");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        Intent intent = getIntent();
        charging = intent.getStringExtra("charging");
        credit_sfz = intent.getStringExtra("credit_sfz");
        order_number = intent.getStringExtra("order_number");
        if (!TextUtils.isEmpty(charging)) {
            tv_money.setText(charging + "元");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("支付成功", msg)) {
            App.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PaySuccess();
                }
            }, 1000);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rl_zhifubao:
                ivWechat.setVisibility(View.GONE);
                ivZhifubao.setVisibility(View.VISIBLE);
                iv_post.setVisibility(View.GONE);
                iv_yinlian.setVisibility(View.GONE);
                isClick = "2";
                break;
            case R.id.rl_wechat:
                ivZhifubao.setVisibility(View.GONE);
                ivWechat.setVisibility(View.VISIBLE);
                iv_post.setVisibility(View.GONE);
                iv_yinlian.setVisibility(View.GONE);
                isClick = "3";
                break;
            case R.id.rl_pos:
//                ivZhifubao.setImageResource(R.drawable.indent_content_no);
//                ivWechat.setImageResource(R.drawable.indent_content_no);
//                iv_post.setImageResource(R.drawable.indent_content_ok);
                //           isClick = "1";
                showToastShort("暂未开通");
                break;
            case R.id.rl_yinlian:
//                ivZhifubao.setVisibility(View.GONE);
//                ivWechat.setVisibility(View.GONE);
//                iv_post.setVisibility(View.GONE);
//                iv_yinlian.setVisibility(View.VISIBLE);
//                isClick = "4";
                showToastShort("暂未开通");
                break;
            case R.id.bt_pay:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    fukuan();
                }
                break;
            default:
                break;
        }
    }

    private void fukuan() {
        if ("1".equals(isClick)) {
            pos();
        } else if ("2".equals(isClick)) {
            zfb();
        } else if ("3".equals(isClick)) {
            if (XueYiCheUtils.isWeixinAvilible(this)) {
                wx_jifen();
            } else {
                showToastShort("目前您的微信版本过低或未安装微信，需要安装微信才能使用");
            }
        } else if ("4".equals(isClick)) {
            showToastShort("暂未开通");
            //PayUtils.UnionPay(JiFenChongZhi.this, "", money);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         *
         * 处理银联手机支付控件返回的支付结果
         *
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
   /*
    * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
    */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            App.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PaySuccess();
                }
            }, 1000);
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }
        showToastShort(msg);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void pos() {
        showToastShort("暂未开通");
    }

    private void zfb() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.ZhengXin_ChaXun_Other_Pay)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("credit_sfz",  AES.decrypt(credit_sfz))
                    .addParams("credit_money", charging)
                    .addParams("user_id", user_id)
                    .addParams("pay_type_id", "1")
                    .addParams("order_number", order_number)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    string = response.body().string();
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(Object response) {
                    zfbeb(string, ZhengXinPay.this, ZhengXinPay.this, "");

                }
            });

        } else {
            showToastShort("请检查网络");
        }


    }

    private void PaySuccess() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().
                    url(AppUrl.ZhengXin_ChaXun_Other_Pay_Success)
                    .addParams("order_number", order_number)
                    .build().
                    execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final ZhengXinChaXunBean zhengXinChaXunBean = JsonUtil.parseJsonToBean(string, ZhengXinChaXunBean.class);
                        if (zhengXinChaXunBean != null) {
                            final int code = zhengXinChaXunBean.getCode();
                            final String msg = zhengXinChaXunBean.getMsg();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (200 == code) {
                                        ZhengXinChaXunBean.ContentBean content = zhengXinChaXunBean.getContent();
                                        if (content != null) {
                                            String info_url = content.getInfo_url();
                                            Intent intent = new Intent(App.context, CommonWebView.class);
                                            intent.putExtra("baoxianurl", info_url);
                                            intent.putExtra("weburl", "zhengxinbaogao");
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else if (400 == code) {
                                        showDefault(ZhengXinPay.this);
                                    }else if (666==code){
                                        XueYiCheUtils.CallPhone(ZhengXinPay.this,"请联系客服！","0451-51068980");
                                    }
                                    Toast.makeText(ZhengXinPay.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(Object response) {


                }
            });

        } else {
            showToastShort("请检查网络");
        }


    }

    private void showDefault(final Activity activity) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.zhengxin_deflaut, null);
        ImageView iv_dis = (ImageView) view.findViewById(R.id.iv_dis);
        TextView chongshi = (TextView) view.findViewById(R.id.bt_chongshi);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 200;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        iv_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        chongshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaySuccess();
                dialog01.dismiss();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    public void wx_jifen() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            final WXZhiFuBean.ContentBean[] content = new WXZhiFuBean.ContentBean[1];
            OkHttpUtils.post().url(AppUrl.ZhengXin_ChaXun_Other_Pay)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("credit_sfz", AES.decrypt(credit_sfz))
                    .addParams("credit_money", charging)
                    .addParams("pay_type_id", "2")
                    .addParams("user_id",user_id)
                    .addParams("order_number", order_number)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        WXZhiFuBean wxZhiFuBean = JsonUtil.parseJsonToBean(string, WXZhiFuBean.class);
                        if (wxZhiFuBean != null) {
                            content[0] = wxZhiFuBean.getContent();
                        }
                    }
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(Object response) {
                    if (content[0] != null) {
                        String appid = content[0].getAppid();
                        String partnerid = content[0].getPartnerid();
                        String prepayid = content[0].getPrepayid();
                        String noncestr = content[0].getNoncestr();
                        String timestamp = content[0].getTimestamp();
                        String packageValue = content[0].getPackageValue();
                        String sign = content[0].getSign();
                        PayReq req = new PayReq();
                        req.appId = appid;
                        req.partnerId = partnerid;
                        req.prepayId = prepayid;
                        req.nonceStr = noncestr;
                        req.timeStamp = timestamp;
                        req.packageValue = packageValue;
                        req.sign = sign;
                        App.wxapi.sendReq(req);
                    }
                }
            });
        } else {
            Toast.makeText(App.context, "请检查网络", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * E币支付
     *
     * @param string
     * @param context
     * @param activity
     * @param subscription
     */
    public void zfbeb(final String string, final Context context, final Activity activity, final String subscription) {
        final int SDK_PAY_FLAG = 1;
        @SuppressLint("HandlerLeak")
        final Handler mHandler = new Handler() {
            @SuppressWarnings("unused")
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            App.handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    PaySuccess();
                                }
                            }, 1000);
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(context, "支付失败,请重新下单", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    default:
                        break;
                }
            }

        };

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(string)) {
                    ZhiFuBaoBean zhiFuBaoBean = JsonUtil.parseJsonToBean(string, ZhiFuBaoBean.class);
                    if (zhiFuBaoBean != null) {
                        String content = zhiFuBaoBean.getContent();
                        if (!TextUtils.isEmpty(content)) {
                            PayTask alipay = new PayTask(activity);
                            Map<String, String> result = alipay.payV2(content, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    }
                }
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
