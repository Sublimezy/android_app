package com.xueyiche.zjyk.xueyiche.pay;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaFragment;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitYuYueActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.GoDaiJiaBean;
import com.xueyiche.zjyk.xueyiche.pay.bean.ZhiFuBaoBean;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.OrderFaBuOkActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.submit.PracticeCarSubmitIndent;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by Owner on 2018/1/4.
 */
public class AppPay extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlZhifubao, rlWechat, rl_car_live_pos, rl_indent_yinlian;
    private ImageView iv_login_back;
    private TextView tv_login_back;
    private String order_number;
    private String subscription, jifen;
    private String url;
    private String user_id, driver_id;
    private LinearLayout ll_exam_back;
    private String pay_style;


    @Override
    protected int initContentView() {
        return R.layout.activity_app_pay;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        iv_login_back = (ImageView) view.findViewById(R.id.driver_pay_include).findViewById(R.id.iv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.driver_pay_include).findViewById(R.id.ll_exam_back);
        iv_login_back.setOnClickListener(this);
        tv_login_back = (TextView) view.findViewById(R.id.driver_pay_include).findViewById(R.id.tv_login_back);
        tv_login_back.setText("支付方式");
        rlZhifubao = (RelativeLayout) view.findViewById(R.id.rl_zhifubao);
        rlWechat = (RelativeLayout) view.findViewById(R.id.rl_wechat);
        rl_car_live_pos = (RelativeLayout) view.findViewById(R.id.rl_pos);
        rl_indent_yinlian = (RelativeLayout) view.findViewById(R.id.rl_yinlian);
        rlZhifubao.setOnClickListener(this);
        rl_indent_yinlian.setOnClickListener(this);
        rlWechat.setOnClickListener(this);
        rl_car_live_pos.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        user_id = PrefUtils.getString(App.context, "user_id", "");


    }

    @Override
    protected void initListener() {

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
            if ("chaxun".equals(pay_style)) {
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
                        dialog01.dismiss();
                        EventBus.getDefault().post(new MyEvent("查询支付成功"));
                        finish();
                    }
                });
                dialog01.setCancelable(false);
                dialog01.show();
                return;
            }  else if ("daijia_liji".equals(pay_style)) {
                PrefUtils.putString(App.context,"hongbao_show","1");
//                XingChengActivity.instance.finish();
                goDaiJia();
                return;
            } else if ("daijia_yuyue".equals(pay_style)) {
                PrefUtils.putString(App.context,"hongbao_show","1");
//                XingChengActivity.instance.finish();
                Intent intent = new Intent(this, WaitYuYueActivity.class);
                intent.putExtra("order_number", order_number);
                startActivity(intent);
                finish();
                return;
            } else if ("daijia2".equals(pay_style)) {
//                JieDanActivity.instance.finish();
//                Intent intent = new Intent(this, JinXingActivity.class);
//                intent.putExtra("order_number", order_number);
//                startActivity(intent);
//                finish();
                return;
            } else if ("daijia3".equals(pay_style)) {
                JinXingActivity.instance.finish();
                Intent intent = new Intent(this, EndActivity.class);
                intent.putExtra("order_number", order_number);
                startActivity(intent);
                finish();
                return;
            } else if ("daijia_daifu".equals(pay_style)) {
//                JieDanActivity.instance.finish();
                goDaiJia();
                return;
            } else if ("driver_school".equals(pay_style) || "kaituan".equals(pay_style)) {
            } else if ("car_life".equals(pay_style)) {
            }else if ("usedcar".equals(pay_style)) {
            } else if ("practice_wz".equals(pay_style)) {

            }else if ("zhitongche".equals(pay_style)) {
            }else if ("practice".equals(pay_style)) {
                PracticeCarSubmitIndent.instance.finish();
                queRenFaBu();
                finish();
                return;
            }
            Intent intent = new Intent(this, PaySucceedActivity.class);
            intent.putExtra("subscription", subscription);
            intent.putExtra("order_number", order_number);
            intent.putExtra("huodejifen", jifen);
            intent.putExtra("pay_style", pay_style);
            startActivity(intent);
            finish();
        }
    }
    public void goDaiJia() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        OkHttpUtils.post().url(AppUrl.Get_Number_UserId)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    GoDaiJiaBean goDaiJiaBean = JsonUtil.parseJsonToBean(string, GoDaiJiaBean.class);
                    if (goDaiJiaBean != null) {
                        final int code = goDaiJiaBean.getCode();
                        if (200 == code) {
                            final GoDaiJiaBean.ContentBean content = goDaiJiaBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
                                        String order_number = content.getOrder_number();
                                        String status = content.getStatus();
                                        String order_type = content.getOrder_type();
                                        if ("0".equals(status)) {
                                            if ("0".equals(order_type)) {
                                                Intent intent = new Intent(App.context, WaitActivity.class);
                                                intent.putExtra("order_number", order_number);
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(App.context, WaitYuYueActivity.class);
                                                intent.putExtra("order_number", order_number);
                                                startActivity(intent);
                                            }
                                        } else if ("1".equals(status) || "2".equals(status)) {
                                            Intent intent = new Intent(App.context, JieDanActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            startActivity(intent);
                                        } else if ("3".equals(status)) {
                                            Intent intent = new Intent(App.context, JinXingActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            startActivity(intent);
                                        } else if ("4".equals(status)) {
                                            Intent intent = new Intent(App.context, JieDanActivity.class);
                                            intent.putExtra("order_number", order_number);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(App.context, DaiJiaFragment.class);
                                            startActivity(intent);
                                        }
                                        finish();
                                    }
                                });
                            }
                        }
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

    private void queRenFaBu() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            String id = LoginUtils.getId(AppPay.this);
            OkHttpUtils.post().url(AppUrl.FaBu_QueRen)
                    .addParams("device_id", id)
                    .addParams("driver_id", driver_id)
                    .addParams("user_id", user_id)
                    .addParams("order_number", order_number)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                SuccessDisCoverBackBean su = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                final int code = su.getCode();
                                final String msg = su.getMsg();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (200 == code) {
                                            //发布成功
                                            Intent intent = new Intent(AppPay.this, OrderFaBuOkActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(AppPay.this, msg, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
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

    @Override
    protected void initData() {
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        driver_id = intent.getStringExtra("driver_id");
        //订单支付金额
        subscription = intent.getStringExtra("subscription");
        jifen = intent.getStringExtra("jifen");
        pay_style = intent.getStringExtra("pay_style");
        String chejia = intent.getStringExtra("chejia");
        String fadongji = intent.getStringExtra("fadongji");
        String chepai = intent.getStringExtra("chepai");
        if (!TextUtils.isEmpty(pay_style)) {
            if ("driver_school".equals(pay_style) || "kaituan".equals(pay_style)||"driver_school_er".equals(pay_style)) {
                url = AppUrl.Driver_Shcool_Indent_Pay;
            } else if ("car_life".equals(pay_style)) {
                url = AppUrl.Car_Life_Indent_Pay;
            } else if ("practice".equals(pay_style)) {
                url = AppUrl.Practice_Pay;
            } else if ("practice_wz".equals(pay_style)) {
                url = AppUrl.WZ_Practice_Pay;
            } else if ("usedcar".equals(pay_style)) {
                url = AppUrl.UsedCar_Pay;
            } else if ("train".equals(pay_style)) {
                url = AppUrl.Train_Pay;
            } else if ("zhitongche".equals(pay_style)) {
                url = AppUrl.ZTC_Pay;
            } else if ("chaxun".equals(pay_style)) {
                url = AppUrl.ZTC_Pay;
            } else if ("daijia_liji".equals(pay_style)) {
                url = AppUrl.Pay_Order_One;
            } else if ("daijia_yuyue".equals(pay_style)) {
                url = AppUrl.Pay_Order_One;
            } else if ("daijia_daifu".equals(pay_style)) {
                url = AppUrl.Pay_Order_One;
            } else if ("daijia2".equals(pay_style)) {
                url = AppUrl.Pay_Order_One;
            } else if ("daijia3".equals(pay_style)) {
                url = AppUrl.Pay_Order_One;
            }
        }
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!TextUtils.isEmpty(pay_style)) {
                if ("driver_school".equals(pay_style) || "kaituan".equals(pay_style)) {
                    AppUtils.deleteIndent(this, AppUrl.Delete_Indent_DriverSchool, user_id, order_number);
                } else if ("car_life".equals(pay_style)) {
                    AppUtils.deleteIndent(this, AppUrl.Delete_Indent_CarLive, user_id, order_number);
                } else if ("practice".equals(pay_style)) {
                    AppUtils.deleteIndent(this, AppUrl.Delete_Indent_Practice, user_id, order_number);
                } else if ("practice_wz".equals(pay_style)) {
                    AppUtils.deleteIndent(this, AppUrl.Delete_Indent_Practice, user_id, order_number);
                } else if ("usedcar".equals(pay_style)) {
                    AppUtils.deleteIndent(this, AppUrl.Delete_Indent_UsedCar, user_id, order_number);
                } else if ("train".equals(pay_style)) {
                    // AppUtils.deleteIndent(this, AppUrl.Delete_Indent_UsedCar, user_id, order_number);
                } else if ("zhitongche".equals(pay_style)) {
                    AppUtils.deleteIndent(this, AppUrl.Delete_Indent_ZTC, user_id, order_number);
                } else if ("daijia_liji".equals(pay_style)) {
                    goDaiJia1();
                } else if ("daijia_yuyue".equals(pay_style)) {
                    goDaiJia1();
                }else {
                    finish();
                }
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void goDaiJia1() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        OkHttpUtils.post().url(AppUrl.Get_Number_UserId)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                LogUtil.e("1111111111",string);
                if (!TextUtils.isEmpty(string)) {
                    GoDaiJiaBean goDaiJiaBean = JsonUtil.parseJsonToBean(string, GoDaiJiaBean.class);
                    if (goDaiJiaBean != null) {
                        final int code = goDaiJiaBean.getCode();
                        if (200 == code) {
                            final GoDaiJiaBean.ContentBean content = goDaiJiaBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                        //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
//                                        String order_number = content.getOrder_number();
//                                        String status = content.getStatus();
//                                        String order_type = content.getOrder_type();
//                                        if ("0".equals(status)) {
//                                            if ("0".equals(order_type)) {
//                                                Intent intent = new Intent(App.context, WaitActivity.class);
//                                                intent.putExtra("order_number",order_number);
//                                                startActivity(intent);
//                                            }else {
//                                                Intent intent = new Intent(App.context, WaitYuYueActivity.class);
//                                                intent.putExtra("order_number",order_number);
//                                                startActivity(intent);
//                                            }
//                                        } else  if ("1".equals(status)||"2".equals(status)) {
//                                            Intent intent = new Intent(App.context, JieDanActivity.class);
//                                            intent.putExtra("order_number",order_number);
//                                            startActivity(intent);
//                                        }else if ("3".equals(status)) {
//                                            Intent intent = new Intent(App.context, JinXingActivity.class);
//                                            intent.putExtra("order_number",order_number);
//                                            startActivity(intent);
//                                        }else if ("4".equals(status)) {
//                                            Intent intent = new Intent(App.context, DaiFukuanActivity.class);
//                                            intent.putExtra("order_number",order_number);
//                                            startActivity(intent);
//                                        } else {
//                                            Intent intent = new Intent(App.context, DaiJiaActivity.class);
//                                            startActivity(intent);
//                                        }
//                                    }
                                });
                            }
                        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                if (!TextUtils.isEmpty(pay_style)) {
                    if ("driver_school".equals(pay_style) || "kaituan".equals(pay_style)) {
                        AppUtils.deleteIndent(this, AppUrl.Delete_Indent_DriverSchool, user_id, order_number);
                    } else if ("car_life".equals(pay_style)) {
                        AppUtils.deleteIndent(this, AppUrl.Delete_Indent_CarLive, user_id, order_number);
                    } else if ("practice".equals(pay_style)) {
                        AppUtils.deleteIndent(this, AppUrl.Delete_Indent_Practice, user_id, order_number);
                    } else if ("practice_wz".equals(pay_style)) {
                        AppUtils.deleteIndent(this, AppUrl.Delete_Indent_Practice, user_id, order_number);
                    } else if ("usedcar".equals(pay_style)) {
                        AppUtils.deleteIndent(this, AppUrl.Delete_Indent_UsedCar, user_id, order_number);
                    } else if ("train".equals(pay_style)) {
                        //AppUtils.deleteIndent(this, AppUrl.Delete_Indent_UsedCar, user_id, order_number);
                    } else if ("zhitongche".equals(pay_style)) {
                        AppUtils.deleteIndent(this, AppUrl.Delete_Indent_ZTC, user_id, order_number);
                    }else if ("daijia_liji".equals(pay_style)) {
                        goDaiJia1();
                    } else if ("daijia_yuyue".equals(pay_style)) {
                        goDaiJia1();
                    } else {
                        finish();
                    }
                }
                break;
            case R.id.rl_zhifubao:
                zfb();
                break;
            case R.id.rl_wechat:
                if (XueYiCheUtils.isWeixinAvilible(this)) {
//                    PayUtils.wx(AppPay.this, url, order_number, user_id);
                } else {
                    showToastShort("目前您的微信版本过低或未安装微信，需要安装微信才能使用");
                }
                break;
            case R.id.rl_pos:
                showToastShort("暂未开通");
                break;
            case R.id.rl_yinlian:
                showToastShort("暂未开通");
                break;
        }
    }

    private void zfb() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(url)
                    .addParams("order_number", order_number)
                    .addParams("device_id", LoginUtils.getId(AppPay.this))
                    .addParams("user_id", user_id)
                    .addParams("pay_type_id", "1")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        ZhiFuBaoBean zhiFuBaoBean = JsonUtil.parseJsonToBean(string, ZhiFuBaoBean.class);
                        if (zhiFuBaoBean != null) {
                            int code = zhiFuBaoBean.getCode();
                            if (200 == code) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if ("practice".equals(pay_style)) {
                                            queRenFaBu();
                                        }
//                                        PayUtils.zfb(string, AppPay.this, AppPay.this, jifen, subscription, order_number, pay_style);
                                    }
                                });
                            }

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
    }
}
