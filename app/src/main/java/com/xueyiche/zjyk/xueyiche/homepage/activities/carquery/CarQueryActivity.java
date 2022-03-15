package com.xueyiche.zjyk.xueyiche.homepage.activities.carquery;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.homepage.bean.MoneyBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.WeiZhangPostBean;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.pay.bean.AppPayChaXun;
import com.xueyiche.zjyk.xueyiche.pay.bean.ChaXunPayBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.CarKeyboardUtil;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by Owner on 2019/6/3.
 */
public class CarQueryActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private ImageView ll_exam_back;
    private TextView tv_login_back;
    private TextView tv_top_right_button;
    private TextView tv_query;
    private TextView tv_money;
    private MClearEditText ed_chejia, ed_fadongji;
    private EditText ed_chepai;
    private LinearLayout ll_sheng, ll_chepai;
    private View xian_sheng, xian_chepai;
    private String type;
    private EditText ed_sheng;
    private CarKeyboardUtil keyboardUtil;
    private String url;
    private String paytype;
    private String pay_money;
    private String chejia;
    private String fadongji;
    private String chepai;
    private String content;

    @Override
    protected int initContentView() {
        return R.layout.car_query_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back =view.findViewById(R.id.title_include).findViewById(R.id.iv_login_back);
        tv_login_back = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_title);
        tv_top_right_button = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_top_right_button);
        ll_sheng = (LinearLayout) view.findViewById(R.id.ll_sheng);
        ll_chepai = (LinearLayout) view.findViewById(R.id.ll_chepai);
        xian_sheng = view.findViewById(R.id.xian_sheng);
        xian_chepai = view.findViewById(R.id.xian_chepai);
        tv_query = (TextView) view.findViewById(R.id.tv_query);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        ed_chejia = (MClearEditText) view.findViewById(R.id.ed_chejia);
        ed_fadongji = (MClearEditText) view.findViewById(R.id.ed_fadongji);
        ed_sheng = (EditText) view.findViewById(R.id.ed_sheng);
        ed_chepai = (EditText) view.findViewById(R.id.ed_chepai);
        ed_chejia.setGravity(Gravity.RIGHT);
        keyboardUtil = new CarKeyboardUtil(this, ed_chepai);
        ed_chepai.setOnTouchListener(this);
        ed_sheng.setOnTouchListener(this);
        ed_fadongji.setGravity(Gravity.RIGHT);
        ed_sheng.setGravity(Gravity.RIGHT);
        EventBus.getDefault().register(this);
        ed_chepai.setGravity(Gravity.RIGHT);
        tv_top_right_button.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("查询支付成功", msg)) {
            pay_success_post();
        }

    }

    private void pay_success_post() {
        chejia = ed_chejia.getText().toString().trim();
        fadongji = ed_fadongji.getText().toString().trim();
        chepai = ed_chepai.getText().toString().trim();

        if (TextUtils.isEmpty(chejia)) {
            showToastShort("请输入车架号");
            return;
        }
        //1 维修 2 违章 3 保险
        if ("1".equals(type)) {
        } else if ("2".equals(type)) {
            if (TextUtils.isEmpty(chepai)) {
                showToastShort("请输入车牌号");
                return;
            }
            if (TextUtils.isEmpty(fadongji)) {
                showToastShort("请输入发动机号");
                return;
            }
        } else if ("3".equals(type)) {
            if (TextUtils.isEmpty(chepai)) {
                showToastShort("请输入车牌号");
                return;
            }
            if (TextUtils.isEmpty(fadongji)) {
                showToastShort("请输入发动机号");
                return;
            }
        }
        if ("1".equals(type)) {
            postShuJuWeiBao(chejia);
        } else if ("2".equals(type)) {
            postShuJuWeiZhang(chejia, fadongji, chepai);
        } else if ("3".equals(type)) {
            postShuJuWeiZhang(chejia, fadongji, chepai);

        }
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_top_right_button.setOnClickListener(this);
        tv_query.setOnClickListener(this);

        ed_chepai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.contains("港") || text.contains("澳") || text.contains("学")) {
                    ed_chepai.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
                } else {
                    ed_chepai.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void initData() {
        tv_top_right_button.setText("历史记录");
        tv_top_right_button.setTextColor(getResources().getColor(R.color.test_color));
        type = getIntent().getStringExtra("type");
        //1 维修 2 违章 3 保险
        if ("1".equals(type)) {
            tv_login_back.setText("维保查询");
            ll_sheng.setVisibility(View.GONE);
            ll_chepai.setVisibility(View.GONE);
            xian_chepai.setVisibility(View.GONE);
            xian_sheng.setVisibility(View.GONE);
            String weibao_chejia = PrefUtils.getString(App.context, "weibao_chejia", "");
            String weibao_fadongji = PrefUtils.getString(App.context, "weibao_fadongji", "");
            if (!TextUtils.isEmpty(weibao_chejia)) {
                ed_chejia.setText(weibao_chejia);
                ed_chejia.setSelection(weibao_chejia.length());
            }
            if (!TextUtils.isEmpty(weibao_fadongji)) {
                ed_fadongji.setText(weibao_fadongji);
                ed_fadongji.setSelection(weibao_fadongji.length());
            }
            paytype = "3";
//            url="http://172.16.51.61:8082/secondhandcarevaluation/weibao.do";
            url = AppUrl.Post_WeiBao;
        } else if ("2".equals(type)) {
            tv_login_back.setText("违章查询");
            ll_sheng.setVisibility(View.GONE);
            xian_sheng.setVisibility(View.GONE);
            url = AppUrl.Post_WeiZhang;
//            url = "http://172.16.51.61:8082/secondhandcarevaluation/violation.do";
            paytype = "1";
            String weizhang_chejia = PrefUtils.getString(App.context, "weizhang_chejia", "");
            String weizhang_fadongji = PrefUtils.getString(App.context, "weizhang_fadongji", "");
            String weizhang_chepai = PrefUtils.getString(App.context, "weizhang_chepai", "");
            if (!TextUtils.isEmpty(weizhang_chejia)) {
                ed_chejia.setText(weizhang_chejia);
                ed_chejia.setSelection(weizhang_chejia.length());
            }
            if (!TextUtils.isEmpty(weizhang_fadongji)) {
                ed_fadongji.setText(weizhang_fadongji);
                ed_fadongji.setSelection(weizhang_fadongji.length());
            }
            if (!TextUtils.isEmpty(weizhang_chepai)) {
                ed_chepai.setText(weizhang_chepai);
                ed_chepai.setSelection(weizhang_chepai.length());
            }
        } else if ("3".equals(type)) {
            tv_login_back.setText("保险查询");
            url = AppUrl.Post_BaoXian;
//            url = "http://172.16.51.61:8082/secondhandcarevaluation/insurance_inquiry.do";
            paytype = "2";
            String baoxian_chejia = PrefUtils.getString(App.context, "baoxian_chejia", "");
            String baoxian_fadongji = PrefUtils.getString(App.context, "baoxian_fadongji", "");
            String baoxian_chepai = PrefUtils.getString(App.context, "baoxian_chepai", "");
            if (!TextUtils.isEmpty(baoxian_chejia)) {
                ed_chejia.setText(baoxian_chejia);
                ed_chejia.setSelection(baoxian_chejia.length());
            }
            if (!TextUtils.isEmpty(baoxian_fadongji)) {
                ed_fadongji.setText(baoxian_fadongji);
                ed_fadongji.setSelection(baoxian_fadongji.length());
            }
            if (!TextUtils.isEmpty(baoxian_chepai)) {
                ed_chepai.setText(baoxian_chepai);
                ed_chepai.setSelection(baoxian_chepai.length());
            }
        }
        getMoney();
    }

    private void getMoney() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post()
                    .url(AppUrl.Get_Money)
                    .addParams("paytype", paytype)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final MoneyBean moneyBean = JsonUtil.parseJsonToBean(string, MoneyBean.class);
                                if (moneyBean != null) {
                                    int code = moneyBean.getCode();
                                    if (200 == code) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                MoneyBean.ContentBean content = moneyBean.getContent();
                                                if (content != null) {
                                                    pay_money = content.getPay_money();
                                                    tv_money.setText(pay_money);
                                                }
                                            }
                                        });
                                    }
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
        } else {
            Toast.makeText(CarQueryActivity.this, "检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:
                Intent intent = new Intent(App.context, CarHistoryActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
            case R.id.tv_query:
                //立即查询
                chejia = ed_chejia.getText().toString().trim();
                fadongji = ed_fadongji.getText().toString().trim();
                chepai = ed_chepai.getText().toString().trim();

                if (TextUtils.isEmpty(chejia)) {
                    showToastShort("请输入车架号");
                    return;
                }
                //1 维修 2 违章 3 保险
                if ("1".equals(type)) {
                } else if ("2".equals(type)) {
                    if (TextUtils.isEmpty(chepai)) {
                        showToastShort("请输入车牌号");
                        return;
                    }
                    if (TextUtils.isEmpty(fadongji)) {
                        showToastShort("请输入发动机号");
                        return;
                    }
                } else if ("3".equals(type)) {
                    if (TextUtils.isEmpty(chepai)) {
                        showToastShort("请输入车牌号");
                        return;
                    }
                    if (TextUtils.isEmpty(fadongji)) {
                        showToastShort("请输入发动机号");
                        return;
                    }
                }

                if ("1".equals(type)) {
                    if ("0".equals(pay_money)) {
                        postShuJuWeiBao(chejia);
                    } else {
                        getOrderNumber(chejia, fadongji, chepai);
                    }
                    PrefUtils.putString(App.context, "weibao_chejia", chejia);
                    PrefUtils.putString(App.context, "weibao_fadongji", fadongji);
                } else if ("2".equals(type)) {
                    if ("0".equals(pay_money)) {
                        postShuJuWeiZhang(chejia, fadongji, chepai);
                    } else {
                        getOrderNumber(chejia, fadongji, chepai);
                    }
                    PrefUtils.putString(App.context, "weizhang_chejia", chejia);
                    PrefUtils.putString(App.context, "weizhang_fadongji", fadongji);
                    PrefUtils.putString(App.context, "weizhang_chepai", chepai);
                } else if ("3".equals(type)) {
                    if ("0".equals(pay_money)) {
                        postShuJuWeiZhang(chejia, fadongji, chepai);
                    } else {
                        getOrderNumber(chejia, fadongji, chepai);
                    }
                    PrefUtils.putString(App.context, "baoxian_chejia", chejia);
                    PrefUtils.putString(App.context, "baoxian_fadongji", fadongji);
                    PrefUtils.putString(App.context, "baoxian_chepai", chepai);
                }

                break;
        }
    }

    private void getOrderNumber(String vin, String engineNumber, String licenseNo) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String user_phone = PrefUtils.getString(App.context, "user_phone", "");
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.Get_ChaXun_Ordernumber)
                    .addParams("vin", !TextUtils.isEmpty(vin)?vin:"")
                    .addParams("enginenumber", !TextUtils.isEmpty(engineNumber)?engineNumber:"")
                    .addParams("licenseno", !TextUtils.isEmpty(licenseNo)?licenseNo:"")
                    .addParams("user_tel", AES.decrypt(user_phone))
                    .addParams("violation_type", paytype)
                    .addParams("user_id", user_id)
                    .addParams("pay_money", pay_money)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final ChaXunPayBean weiZhangPostBean = JsonUtil.parseJsonToBean(string, ChaXunPayBean.class);
                                if (weiZhangPostBean != null) {
                                    int success = weiZhangPostBean.getCode();
                                    if (200 == success) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                content = weiZhangPostBean.getContent();
                                                Intent intent_pay = new Intent(App.context, AppPayChaXun.class);
                                                intent_pay.putExtra("pay_style", "chaxun");
                                                intent_pay.putExtra("type", paytype);
                                                intent_pay.putExtra("order_number", content);
                                                intent_pay.putExtra("subscription", pay_money);
                                                intent_pay.putExtra("jifen", "0");
                                                startActivity(intent_pay);
                                            }
                                        });

                                    } else {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                stopProgressDialog();
                                                Toast.makeText(CarQueryActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
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
        } else {
            Toast.makeText(CarQueryActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }

    private void postShuJuWeiZhang(String vin, String engineNumber, String licenseNo) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false, "查询中。。。");
            String user_phone = PrefUtils.getString(App.context, "user_phone", "");
            OkHttpUtils.post().url(url)
                    .addParams("vin", vin)
                    .addParams("engineNumber", engineNumber)
                    .addParams("order_number", TextUtils.isEmpty(content)?"":content)
                    .addParams("licenseNo", licenseNo)
                    .addParams("user_tel", AES.decrypt(user_phone))
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final WeiZhangPostBean weiZhangPostBean = JsonUtil.parseJsonToBean(string, WeiZhangPostBean.class);
                                if (weiZhangPostBean != null) {
                                    int success = weiZhangPostBean.getSuccess();
                                    if (1 == success) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                WeiZhangPostBean.MessageBean message = weiZhangPostBean.getMessage();
                                                if (message != null) {
                                                    String orderid = message.getOrderid();
                                                    if (!TextUtils.isEmpty(orderid)) {
                                                        dialog();
                                                    }
                                                }
                                            }
                                        });

                                    } else {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                stopProgressDialog();
                                                Toast.makeText(CarQueryActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
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
        } else {
            Toast.makeText(CarQueryActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }

    private void postShuJuWeiBao(String vin) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            String user_phone = PrefUtils.getString(App.context, "user_phone", "");
            showProgressDialog(false, "查询中。。。");
            OkHttpUtils.post().url(url)
                    .addParams("vin", vin)
                    .addParams("order_number", TextUtils.isEmpty(content)?"":content)
                    .addParams("user_tel", AES.decrypt(user_phone))
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final WeiZhangPostBean weiZhangPostBean = JsonUtil.parseJsonToBean(string, WeiZhangPostBean.class);
                                if (weiZhangPostBean != null) {
                                    int success = weiZhangPostBean.getSuccess();
                                    if (1 == success) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                WeiZhangPostBean.MessageBean message = weiZhangPostBean.getMessage();
                                                if (message != null) {
                                                    String orderid = message.getOrderid();
                                                    if (!TextUtils.isEmpty(orderid)) {
                                                        dialog();
                                                    }
                                                }
                                            }
                                        });

                                    } else {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                stopProgressDialog();
                                                Toast.makeText(CarQueryActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
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
        } else {
            Toast.makeText(CarQueryActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }

    private void dialog() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.wenxintishidialog, null);
        Button bt_know = (Button) view.findViewById(R.id.bt_know);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ImageView iv_dis = (ImageView) view.findViewById(R.id.iv_dis);
        iv_dis.setVisibility(View.GONE);
        tv_title.setText("温馨提示");
        tv_content.setText("报告生成中请稍后，结果请在历史记录中查看详情");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;

        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =

                dialog01.getWindow().getAttributes();//获取dialog信息

        params.width = screenWidth * 2 / 3;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        //点击空白处弹框不消失
        bt_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog01.dismiss();
            }
        });
        dialog01.setCancelable(false);
        dialog01.show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.ed_chepai:
                keyboardUtil.hideSystemKeyBroad();
                keyboardUtil.hideSoftInputMethod();
                if (!keyboardUtil.isShow())
                    keyboardUtil.showKeyboard();
                break;
            case R.id.ed_sheng:
                if (keyboardUtil.isShow())
                    keyboardUtil.hideKeyboard();
                break;
            default:
                if (keyboardUtil.isShow())
                    keyboardUtil.hideKeyboard();
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (keyboardUtil.isShow()) {
            keyboardUtil.hideKeyboard();
        }
        return super.onTouchEvent(event);
    }
}