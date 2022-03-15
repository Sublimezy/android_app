package com.xueyiche.zjyk.xueyiche.submit;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.DriverSchoolGeRenBean;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.DriverSchoolTaocanListBean;
import com.xueyiche.zjyk.xueyiche.driverschool.driverschool.fenqi.FenQiTestDriverBookActivity;
import com.xueyiche.zjyk.xueyiche.homepage.bean.DriverSchoolTuiJianBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.OptionPicker;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.pay.PaySucceedActivity;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.submit.bean.DriverSchoolSubmitBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhanglei on 2016/11/28.
 */
public class DriverSchoolSubmitIndent extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler {
    //驾校支付页
    private TextView tv_pay_money, bt_indent_content_ok;
    private TextView tv_driver_content_phone;
    private MClearEditText tv_driver_content_name, tv_driver_content_card;
    private ImageView iv_school_head;
    private TextView tv_shop_name, tv_service_money, tv_driver_school_dingjin, tv_service_content;
    private TextView tv_login_back;
    private String driver_school_id, user_id, payamount, user_phone, subscription, driver_user_name, driver_user_card;
    private RadioButton rb_ok, rb_no;
    private MClearEditText tv_tuijian_phone;
    private boolean isTuiJian = false;
    private TextView tv_tuijian_name;
    private String recommend_id = "";
    private LinearLayout ll_tuijianren_phone;
    private LinearLayout ll_exam_back;
    private LinearLayout ll_caotan_chose;
    private TextView tv_taocan;
    private ArrayList<String> taoCanList;
    private List<DriverSchoolTaocanListBean.ContentBean> content_taocan;
    private int package_id;
    public static DriverSchoolSubmitIndent instance;
    private String tuan, order_category, driver_school_money;
    private TextView tv_dingjin;
    private String stkm;


    @Override
    protected int initContentView() {
        return R.layout.driverschool_submit;
    }

    @Override
    protected void initView() {
        //标题栏的一些内容
        tv_login_back = (TextView) view.findViewById(R.id.driver_pay_include).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.driver_pay_include).findViewById(R.id.ll_exam_back);
        tv_login_back.setText("提交订单");
        ll_exam_back.setOnClickListener(this);
        //姓名的输入框
        tv_driver_content_name = (MClearEditText) view.findViewById(R.id.tv_driver_content_name);
        //身份证的输入框
        tv_driver_content_card = (MClearEditText) view.findViewById(R.id.tv_driver_content_card);
        textChange tc1 = new textChange();
        tv_driver_content_card.addTextChangedListener(tc1);
        //手机号码
        tv_driver_content_phone = (TextView) view.findViewById(R.id.tv_driver_content_phone);
        //驾校头像
        iv_school_head = (ImageView) view.findViewById(R.id.iv_school_head);
        //驾校名字
        tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
        tv_taocan = (TextView) view.findViewById(R.id.tv_taocan);
        //服务项目
        tv_service_content = (TextView) view.findViewById(R.id.tv_service_content);
        //服务价格
        tv_service_money = (TextView) view.findViewById(R.id.tv_service_money);
        //支付定金
        tv_driver_school_dingjin = (TextView) view.findViewById(R.id.tv_driver_school_dingjin);
        //提交订单左边显示的金额
        tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        rb_ok = (RadioButton) view.findViewById(R.id.rb_ok);
        rb_no = (RadioButton) view.findViewById(R.id.rb_no);
        //推荐人电话
        tv_tuijian_phone = (MClearEditText) view.findViewById(R.id.tv_tuijian_phone);
        //推荐人姓名
        tv_tuijian_name = (TextView) view.findViewById(R.id.tv_tuijian_name);
        tv_dingjin = (TextView) view.findViewById(R.id.tv_dingjin);
        //点击选择套餐
        ll_caotan_chose = (LinearLayout) view.findViewById(R.id.ll_caotan_chose);
        ll_tuijianren_phone = (LinearLayout) view.findViewById(R.id.ll_tuijianren_phone);
        textChange2 tc2 = new textChange2();
        instance = this;
        tv_tuijian_phone.addTextChangedListener(tc2);
        rb_ok.setOnClickListener(this);
        rb_no.setOnClickListener(this);
        ll_caotan_chose.setOnClickListener(this);
        taoCanList = new ArrayList<>();
        //提交订单
        bt_indent_content_ok = (TextView) view.findViewById(R.id.bt_indent_content_ok);
        bt_indent_content_ok.setOnClickListener(this);
        bt_indent_content_ok.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                driverPay();
            }
        });
    }

    private void driverPay() {
        driver_user_name = tv_driver_content_name.getText().toString().trim();
        driver_user_card = tv_driver_content_card.getText().toString().trim();
        String taocan = tv_taocan.getText().toString().trim();
        if (TextUtils.isEmpty(driver_user_name)) {
            showToastShort("请填写真实姓名");
            return;
        }
        if ("请选择".equals(taocan) || TextUtils.isEmpty(package_id + "") || TextUtils.isEmpty(subscription)) {
            showToastShort("请选择服务项目");
            return;
        }
        if (TextUtils.isEmpty(driver_user_card)) {
            showToastShort("请填写身份证号");
            return;
        }

        if (!StringUtils.isIdCard(driver_user_card)) {
            showToastShort("请填写正确的身份证号码");
            return;
        }
        String string = tv_tuijian_phone.getText().toString();
        if (rb_ok.isChecked() && TextUtils.isEmpty(string)) {
            showToastShort("请填写推荐人");
            return;
        }
        if (rb_ok.isChecked() && !isTuiJian) {
            showToastShort("无效的推荐人");
            return;
        }
        String encrypt = AES.encrypt(driver_user_card);
        String user_phone_encrypt = AES.encrypt(user_phone);
//        String encrypt = AES.encrypt("231084198311280319");
//        String user_phone_encrypt = AES.encrypt("18811149874");
        OkHttpUtils.post().url(AppUrl.Driver_Shcool_Post)
                .addParams("user_id", user_id)
                .addParams("user_phone", user_phone_encrypt)
                .addParams("user_name", driver_user_name)
                .addParams("user_cards", encrypt)
                .addParams("package_id", package_id + "")
                .addParams("recommend_id", recommend_id)
                .addParams("device_id", LoginUtils.getId(DriverSchoolSubmitIndent.this))
                .addParams("driver_school_id", driver_school_id)
                .addParams("pin", "")
                .addParams("order_category", "0")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    DriverSchoolSubmitBean submitBean = JsonUtil.parseJsonToBean(string, DriverSchoolSubmitBean.class);
                    if (submitBean != null) {
                        final int code = submitBean.getCode();
                        final String msg = submitBean.getMsg();
                        final DriverSchoolSubmitBean.ContentBean content = submitBean.getContent();
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (content != null) {
                                    showToastShort(msg);
                                    String order_number = content.getOrder_number();
                                    if (!TextUtils.isEmpty(order_number) && !TextUtils.isEmpty(subscription)) {
                                        if (code == 5) {
                                            Intent intent = new Intent(DriverSchoolSubmitIndent.this, PaySucceedActivity.class);
                                            intent.putExtra("subscription", subscription);
                                            intent.putExtra("order_number", order_number);
                                            intent.putExtra("jifen", driver_school_money + "00");
                                            intent.putExtra("pay_style", "driver_school");
                                            startActivity(intent);
                                            finish();
                                        } else if (code == 200) {
                                            //支付页
                                            Intent intent = new Intent(App.context, AppPay.class);
                                            intent.putExtra("order_number", order_number);
                                            intent.putExtra("subscription", subscription);
                                            intent.putExtra("jifen", "1000");
                                            if ("kaituan".equals(tuan)) {
                                                intent.putExtra("pay_style", "kaituan");
                                            } else {
                                                intent.putExtra("pay_style", "driver_school");
                                            }
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
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

    }


    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        } else {
            getOrder();
        }
    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        user_id = PrefUtils.getString(this, "user_id", "");
        String user_name = PrefUtils.getString(this, "user_name", "");
        if (!TextUtils.isEmpty(user_name)) {
            tv_driver_content_name.setText(user_name);
            tv_driver_content_name.setSelection(user_name.length());
        }
        driver_school_id = intent.getStringExtra("driver_school_id");
        tuan = intent.getStringExtra("tuan");
        //stkm 0 正常  1 全款
        stkm = intent.getStringExtra("stkm");
        if ("0".equals(stkm)) {
            tv_dingjin.setText("支付定金");
        } else if ("1".equals(stkm)) {
            tv_dingjin.setText("含定金");
        }
        getOrder();
        getTaoCan();
    }

    public void getTaoCan() {
        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(driver_school_id)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Driver_Shcool_Taocan_List)
                    .addParams("driver_school_id", driver_school_id)
                    .addParams("pin_id", "zhengchang".equals(tuan) ? "0" : "1")
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                DriverSchoolTaocanListBean driverSchoolTaocanListBean = JsonUtil.parseJsonToBean(string, DriverSchoolTaocanListBean.class);
                                if (driverSchoolTaocanListBean != null) {
                                    content_taocan = driverSchoolTaocanListBean.getContent();
                                    if (content_taocan != null) {
                                        for (DriverSchoolTaocanListBean.ContentBean contentBean : content_taocan) {
                                            String driver_school_service = contentBean.getDriver_school_service();
                                            String driver_school_money = contentBean.getDriver_school_money();
                                            taoCanList.add(driver_school_service + " ¥" + driver_school_money);
                                        }
                                    }
                                }
                            }
                        });
                    }
                    return null;
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

    public void getTuiJianName(String tuijian_phone) {
        String encrypt = AES.encrypt(tuijian_phone);
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.TuiJianMoney)
                .addParams("recommender_phone", encrypt)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                final String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    final DriverSchoolTuiJianBean driverSchoolTuiJianBean = JsonUtil.parseJsonToBean(string, DriverSchoolTuiJianBean.class);
                    if (driverSchoolTuiJianBean != null) {
                        final int code = driverSchoolTuiJianBean.getCode();
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (200 == code) {
                                    DriverSchoolTuiJianBean.ContentBean content = driverSchoolTuiJianBean.getContent();
                                    if (content != null) {
                                        String recommender_name = content.getRecommender_name();
                                        recommend_id = content.getRecommend_id();
                                        tv_tuijian_name.setText(recommender_name);
                                        tv_tuijian_name.setVisibility(View.VISIBLE);
                                        isTuiJian = true;
                                    }
                                } else {
                                    isTuiJian = false;
                                    tv_tuijian_name.setVisibility(View.GONE);
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogUtils.showNoTuijian(DriverSchoolSubmitIndent.this, "无效的推荐人", tv_tuijian_phone);
                                        }
                                    });
                                }
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

    private void getOrder() {

        if (!TextUtils.isEmpty(driver_school_id) && !TextUtils.isEmpty(user_id)) {


            if (XueYiCheUtils.IsHaveInternet(this)) {
                showProgressDialog(false);
                OkHttpUtils.post().url(AppUrl.Driver_Shcool_submit)
                        .addParams("driver_school_id", driver_school_id)
                        .addParams("user_id", user_id)
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        final String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    processData(string);
                                }
                            });
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

    private void processData(String string) {
        com.xueyiche.zjyk.xueyiche.driverschool.bean.DriverSchoolSubmitBean driverSchoolSubmitBean = JsonUtil.parseJsonToBean(string, com.xueyiche.zjyk.xueyiche.driverschool.bean.DriverSchoolSubmitBean.class);
        if (driverSchoolSubmitBean != null) {
            com.xueyiche.zjyk.xueyiche.driverschool.bean.DriverSchoolSubmitBean.ContentBean content = driverSchoolSubmitBean.getContent();
            if (content != null) {
                String driver_school_name = content.getDriver_school_name();
                String package_detail = content.getPackage_detail();
                String driver_school_url = content.getDriver_school_url();
                user_phone = content.getUser_phone();
                if (!TextUtils.isEmpty(user_phone)) {
                    tv_driver_content_phone.setText(user_phone);
                }
                if (!TextUtils.isEmpty(driver_school_url)) {
                    Picasso.with(App.context).load(driver_school_url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_school_head);
                }
                if (!TextUtils.isEmpty(driver_school_name)) {
                    tv_shop_name.setText(driver_school_name);
                }
                if (!TextUtils.isEmpty(package_detail)) {
                    tv_service_content.setText(package_detail);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                //删除订单
                finish();
                break;
            case R.id.ll_caotan_chose:
                //选择服务项目
                choseTaoCan();
                break;
            case R.id.tv_fenqi:
                //TODO:分期考驾照
                showDialog();
                break;
            case R.id.rb_ok:
                rb_no.setChecked(false);
                ll_tuijianren_phone.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_no:
                rb_ok.setChecked(false);
                ll_tuijianren_phone.setVisibility(View.GONE);
                break;
        }
    }

    private void showDialog() {
        driver_user_name = tv_driver_content_name.getText().toString().trim();
        driver_user_card = tv_driver_content_card.getText().toString().trim();
        String taocan = tv_taocan.getText().toString().trim();
        if (TextUtils.isEmpty(driver_user_name)) {
            showToastShort("请填写真实姓名");
            return;
        }
        if ("请选择".equals(taocan) || TextUtils.isEmpty(package_id + "") || TextUtils.isEmpty(subscription)) {
            showToastShort("请选择服务项目");
            return;
        }
        if (TextUtils.isEmpty(driver_user_card)) {
            showToastShort("请填写身份证号");
            return;
        }

        if (!StringUtils.isIdCard(driver_user_card)) {
            showToastShort("请填写正确的身份证号码");
            return;
        }
        String string = tv_tuijian_phone.getText().toString();
        if (rb_ok.isChecked() && TextUtils.isEmpty(string)) {
            showToastShort("请填写推荐人");
            return;
        }
        if (rb_ok.isChecked() && !isTuiJian) {
            showToastShort("无效的推荐人");
            return;
        }
        View viewDia = LayoutInflater.from(App.context).inflate(R.layout.usedcar_tanchuang_layout, null);
        TextView bt_know = (TextView) viewDia.findViewById(R.id.tv_ok);
        TextView tv_title = (TextView) viewDia.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) viewDia.findViewById(R.id.tv_content);
        TextView tv_no = (TextView) viewDia.findViewById(R.id.tv_no);
        LinearLayout ll_xieyi = (LinearLayout) viewDia.findViewById(R.id.ll_xieyi);
        ll_xieyi.setVisibility(View.VISIBLE);
        tv_content.setText("分期考驾照，需查询个人征信资格，征信查询需支付查询费用10元。");
        tv_title.setText("征信查询");
        bt_know.setText("去支付");
        final AlertDialog.Builder builder = new AlertDialog.Builder(DriverSchoolSubmitIndent.this, R.style.Dialog).setView(viewDia);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 200;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        //点击空白处弹框不消失
        bt_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1 = new Intent(App.context, AppPay.class);
//                intent1.putExtra("pay_style", "usedcar");
//                intent1.putExtra("order_number", "");
//                intent1.putExtra("subscription", "10");
//                intent1.putExtra("jifen", "0");
//                startActivity(intent1);
                openActivity(FenQiTestDriverBookActivity.class);
                dialog01.dismiss();
            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        dialog01.setCancelable(false);
        dialog01.show();
    }

    private void choseTaoCan() {
        final OptionPicker picker = new OptionPicker(this, taoCanList);
        picker.setOffset(1);
        picker.setSelectedIndex(0);
        picker.setTextSize(15);
        picker.setTitleTextColor(getResources().getColor(R.color._3232));
        picker.setTitleText("请选择");
        picker.setTitleTextSize(20);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                picker.setSelectedIndex(position);
                if (content_taocan != null) {
                    DriverSchoolTaocanListBean.ContentBean contentBean = content_taocan.get(position);
                    if (contentBean != null) {
                        subscription = contentBean.getSubscription();
                        driver_school_money = contentBean.getDriver_school_money();
                        package_id = contentBean.getPackage_id();
                        String subscription_v = contentBean.getSubscription_v();
                        if ("1".equals(stkm)) {
                            tv_driver_school_dingjin.setText("¥" + subscription_v);
                        }else if ("0".equals(stkm)){
                            tv_driver_school_dingjin.setText("¥" + subscription);
                        }
                        tv_service_money.setText("¥" + driver_school_money);
                        tv_pay_money.setText(" ¥ "+subscription);
                        tv_taocan.setText(option);
                        tv_driver_content_card.getText().clear();
                    }
                }
            }
        });
        picker.show();
    }


    public abstract class NoDoubleClickListener implements View.OnClickListener {
        public static final int MIN_CLICK_DELAY_TIME = 2000;
        private long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        }

        protected abstract void onNoDoubleClick(View v);
    }

    public void getIntentMoney(String id_crad) {
        if (XueYiCheUtils.IsHaveInternet(DriverSchoolSubmitIndent.this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Driver_School_HuoDong_Money)
                    .addParams("user_id", user_id)
                    .addParams("package_id", package_id + "")
                    .addParams("device_id", LoginUtils.getId(DriverSchoolSubmitIndent.this))
                    .addParams("user_cards", AES.encrypt(id_crad))
                    .addParams("driver_school_id", driver_school_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    final DriverSchoolGeRenBean driverSchoolGeRenBean = JsonUtil.parseJsonToBean(string, DriverSchoolGeRenBean.class);
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (driverSchoolGeRenBean != null) {
                                DriverSchoolGeRenBean.ContentBean content = driverSchoolGeRenBean.getContent();
                                if (content != null) {
                                    //全额
                                    String driver_school_money1 = content.getDriver_school_money();
                                    payamount = content.getPayamount();
                                    String subscription1 = content.getSubscription();
                                    if (!"null".equals(subscription1)) {
                                        if (!TextUtils.isEmpty(driver_school_money1)) {
                                            tv_service_money.setText(StringUtils.getMoneyTitle(driver_school_money1));
                                        }
                                        //定金
                                        if (!TextUtils.isEmpty(subscription1)) {
                                            tv_driver_school_dingjin.setText(StringUtils.getMoneyTitle(subscription1));
                                        }
                                        //实付金额
                                        if (!TextUtils.isEmpty(payamount)) {
                                            tv_pay_money.setText(StringUtils.getMoneyTitle(payamount));
                                        }
                                    } else {
                                        tv_driver_school_dingjin.setText("¥" + subscription);
                                        tv_service_money.setText("¥" + driver_school_money);
                                        tv_pay_money.setText(subscription);
                                    }
                                }
                            }
                        }
                    });
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
            showToastShort("请检查网络");
        }
    }

    class textChange2 implements TextWatcher {
        @Override

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (text.length() == 11) {
                getTuiJianName(text);
            }
        }

        @Override

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count) {

        }
    }

    class textChange implements TextWatcher {
        @Override

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            String taocan = tv_taocan.getText().toString().trim();
            if ("请选择".equals(taocan) || TextUtils.isEmpty(package_id + "") || TextUtils.isEmpty(subscription)) {
                showToastShort("请选择服务项目");
                editable.clear();
            }
            int length = text.length();
            if (18 == length) {
                if (StringUtils.isIdCard(text)) {
                    getIntentMoney(text);
                } else {
                    showToastShort("请输入正确的身份证号");
                }
            }

        }

        @Override

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count) {
            String txt = tv_driver_content_card.getText().toString();
            Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(txt);
            if (m.matches()) {
                showToastShort("请输入正确的身份证号");
            }


        }
    }
}
