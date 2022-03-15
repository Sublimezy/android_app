package com.xueyiche.zjyk.xueyiche.submit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.mine.activities.youhuiquan.YouHuiQuan;
import com.xueyiche.zjyk.xueyiche.pay.JiFenChongZhi;
import com.xueyiche.zjyk.xueyiche.submit.bean.CarLifeSubmitBean;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ZL on 2018/1/24.
 */
public class CarLiveSubmitIndent extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private LinearLayout llTiShi;
    private Button bt_indent_content_ok;
    //支付的金额
    private TextView tv_pay_money;
    //总金额
    private TextView tv_total_money;
    //折扣
    private TextView tv_zhekou;
    //优惠券数
    private TextView tv_youhuiquan_number;
    //商家名字
    private TextView tv_shop_name;
    //实付金额
    private TextView tv_shifu_money;
    //去充值
    private TextView tvGoChongZhi;
    //提示
    private TextView tvTiShi;
    //商品头像
    private ImageView iv_school_head;
    //购买的商品的列表
    private AdListView lv_service_content;
    private String user_id;
    private String order_number;
    private String coupon_id;
    private String couponNumber;
    private String shiduprice, chanpinprice;
    public static CarLiveSubmitIndent instance;

    @Override
    protected int initContentView() {
        return R.layout.carlife_submit;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.carlife_submit_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.carlife_submit_include).findViewById(R.id.tv_login_back);
        bt_indent_content_ok = (Button) view.findViewById(R.id.bt_indent_content_ok);
        llTiShi = (LinearLayout) view.findViewById(R.id.llTiShi);
        tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        tv_total_money = (TextView) view.findViewById(R.id.tv_total_money);
        tv_shifu_money = (TextView) view.findViewById(R.id.tv_shifu_money);
        tv_zhekou = (TextView) view.findViewById(R.id.tv_zhekou);
        tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
        tvTiShi = (TextView) view.findViewById(R.id.tvTiShi);
        tv_youhuiquan_number = (TextView) view.findViewById(R.id.tv_youhuiquan_number);
        tvGoChongZhi = (TextView) view.findViewById(R.id.tvGoChongZhi);
        iv_school_head = (ImageView) view.findViewById(R.id.iv_school_head);
        lv_service_content = (AdListView) view.findViewById(R.id.lv_service_content);
        ll_exam_back.setOnClickListener(this);
        bt_indent_content_ok.setOnClickListener(this);
        tv_youhuiquan_number.setOnClickListener(this);
        tvGoChongZhi.setOnClickListener(this);
        instance = this;

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tv_login_back.setText("提交订单");
        order_number = getIntent().getStringExtra("order_number");
        user_id = PrefUtils.getString(App.context, "user_id", "");


        getDataFromNet();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromNet();
    }

    private void getDataFromNet() {

        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post()
                    .url(AppUrl.Car_Life_Indent_Submit)
                    .addParams("device_id", LoginUtils.getId(CarLiveSubmitIndent.this))
                    .addParams("user_id", user_id)
                    .addParams("order_number", order_number)
                    .addParams("coupon_id", TextUtils.isEmpty(coupon_id) ? "" : coupon_id)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                process(string);
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

    private void process(String string) {
        CarLifeSubmitBean carLifeSubmitBean = JsonUtil.parseJsonToBean(string, CarLifeSubmitBean.class);
        if (carLifeSubmitBean != null) {
            int code = carLifeSubmitBean.getCode();
            String msg = carLifeSubmitBean.getMsg();
            if (!TextUtils.isEmpty("" + code)) {
                if (200 == code) {
                    final CarLifeSubmitBean.ContentBean content = carLifeSubmitBean.getContent();
                    if (content != null) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                couponNumber = content.getCouponNumber();
                                if (!TextUtils.isEmpty(couponNumber)) {
                                    tv_youhuiquan_number.setText(couponNumber);
                                }
                                final String recharge_mark = content.getRecharge_mark();
                                final List<CarLifeSubmitBean.ContentBean.ShopCarInfoBean> shop_car_info = content.getShop_car_info();
                                if (shop_car_info.size() != 0) {
                                    GoodListAdapter goodListAdapter = new GoodListAdapter(shop_car_info, App.context, R.layout.carlife_submit_goods_item);
                                    lv_service_content.setAdapter(goodListAdapter);
                                }
                                CarLifeSubmitBean.ContentBean.OrderInfoBean order_info = content.getOrder_info();
                                if (order_info != null) {
                                    //总额
                                    final String all_old_price = order_info.getAll_old_price();
                                    //折扣说明
                                    final String integral_use_num = order_info.getIntegral_use_num();
                                    //实际付款
                                    final String pay_price = order_info.getPay_price();
                                    //头像
                                    final String shop_img = order_info.getShop_img();
                                    //名字
                                    final String shop_name = order_info.getShop_name();
                                    //积分充足的判断
                                    final String integral_enough = order_info.getIntegral_enough();
                                    if (!TextUtils.isEmpty(integral_enough)) {
                                        if ("1".equals(integral_enough)) {
                                            tvTiShi.setText(recharge_mark);
                                            llTiShi.setVisibility(View.GONE);
                                        } else if ("2".equals(integral_enough)) {
                                            tvTiShi.setText(recharge_mark);
                                        }
                                    }
                                    if (!TextUtils.isEmpty(all_old_price)) {
                                        chanpinprice = all_old_price.substring(1, all_old_price.length());
                                        tv_total_money.setText(all_old_price);
                                    }
                                    if (!TextUtils.isEmpty(integral_use_num)) {
                                        tv_zhekou.setText(integral_use_num);
                                    }
                                    if (!TextUtils.isEmpty(pay_price)) {
                                        tv_shifu_money.setText(pay_price);
                                        shiduprice = pay_price.substring(1, pay_price.length());
                                        tv_pay_money.setText(shiduprice);
                                    }
                                    if (!TextUtils.isEmpty(shop_img)) {
                                        Picasso.with(App.context).load(shop_img).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(iv_school_head);
                                    }
                                    if (!TextUtils.isEmpty(shop_name)) {
                                        tv_shop_name.setText(shop_name);
                                    }
                                }
                            }
                        });
                    }


                } else {
                    LogUtil.e("msg", msg);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                //删除订单
                if (!TextUtils.isEmpty(order_number) && !TextUtils.isEmpty(user_id)) {
                    AppUtils.deleteIndent(this, AppUrl.Delete_Indent_CarLive, user_id, order_number);
                }
                break;
            case R.id.tvGoChongZhi:
                //去充值
                Intent intentChongZhi = new Intent(App.context, JiFenChongZhi.class);
                startActivity(intentChongZhi);
                break;
            case R.id.bt_indent_content_ok:
                if (!TextUtils.isEmpty(chanpinprice) && !TextUtils.isEmpty(shiduprice)) {
                    BigDecimal a = new BigDecimal(chanpinprice);
                    BigDecimal b = new BigDecimal(shiduprice);
                    BigDecimal c = new BigDecimal("0.01");
                    BigDecimal subtract = a.subtract(b);
                    BigDecimal multiply = b.multiply(c).setScale(2, BigDecimal.ROUND_UP);
                    DialogUtils.showZhiFuTiShi(this, subtract + "", multiply + "", order_number, shiduprice);
                } else {
                    Toast.makeText(CarLiveSubmitIndent.this, "数据异常", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_youhuiquan_number:
                if (!TextUtils.isEmpty(couponNumber)) {
                    if (TextUtils.equals(couponNumber, "可用0个")) {
                        Toast.makeText(App.context, "无可用优惠券", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(App.context, YouHuiQuan.class);
                        intent.putExtra("order_number", order_number);
                        startActivityForResult(intent, 2);
                    }
                }
                break;
        }
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!TextUtils.isEmpty(order_number) && !TextUtils.isEmpty(user_id)) {
                AppUtils.deleteIndent(this, AppUrl.Delete_Indent_CarLive, user_id, order_number);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 222:
                Bundle extras1 = data.getExtras();
                //优惠券id
                coupon_id = extras1.getString("coupon_id");
                break;
            default:
                break;
        }

    }

    private class GoodListAdapter extends BaseCommonAdapter {
        public GoodListAdapter(List<CarLifeSubmitBean.ContentBean.ShopCarInfoBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            CarLifeSubmitBean.ContentBean.ShopCarInfoBean contentBean = (CarLifeSubmitBean.ContentBean.ShopCarInfoBean) item;
            String goods_name = contentBean.getGoods_name();
            String old_price = contentBean.getOld_price();
            if (!TextUtils.isEmpty(goods_name)) {
                viewHolder.setText(R.id.tv_goods_name, goods_name);
            }
            if (!TextUtils.isEmpty(old_price)) {
                viewHolder.setText(R.id.tv_goods_money, old_price);
            }
        }
    }

}
