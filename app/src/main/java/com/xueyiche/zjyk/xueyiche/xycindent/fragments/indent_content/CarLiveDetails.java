package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.ObservableScrollView;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.NewPlayActivity;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.CarDetailsBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhanglei on 2016/11/8.
 */
public class CarLiveDetails extends BaseActivity implements NetBroadcastReceiver.netEventHandler, View.OnClickListener {

    private ImageView iv_caidan, iv_indent_content_phone, iv_indent_content_daohang, iv_school_head, iv_ew_caode;
    private TextView tv_login_back, tv_caidan_background, tv_fanxian_states, tv_shop_name, tv_shop_place;
    private TextView tv_indent_number,tv_indent_content_style_fuwu, tv_indent_content_money, tv_indent_content_money_shifu;
    private TextView tv_indent_time,tv_erweima;
    private PopupWindow pop = new PopupWindow();
    private int height;
    private boolean isClosePup = true;
    private String order_number;
    private String driver_school_phone;
    private String driver_school_place;
    private String latitude;
    private String longitude;
    private LinearLayout ll_exam_back;
    private AdListView lv_shangpin;
    private String user_id;
    private String shop_name;
    private View xian_list_up,xian_list_bot;
    private ObservableScrollView sv_indent_dentails;


    @Override
    protected int initContentView() {
        return R.layout.indent_driver_school_details;
    }

    @Override
    protected void initView() {
        iv_caidan = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_caidan);
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        iv_indent_content_daohang = (ImageView) view.findViewById(R.id.iv_indent_content_daohang);
        tv_caidan_background = (TextView) view.findViewById(R.id.tv_caidan_background);
        tv_fanxian_states = (TextView) view.findViewById(R.id.tv_fanxian_states);
        tv_erweima = (TextView) view.findViewById(R.id.tv_erweima);
        sv_indent_dentails = (ObservableScrollView) view.findViewById(R.id.sv_indent_dentails);
        sv_indent_dentails.scrollTo(0,0);
        lv_shangpin = (AdListView) view.findViewById(R.id.lv_shangpin);
        lv_shangpin.setFocusable(false);
        tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
        tv_indent_content_style_fuwu = (TextView) view.findViewById(R.id.tv_indent_content_style_fuwu);
        tv_indent_content_style_fuwu.setVisibility(View.GONE);
        tv_shop_place = (TextView) view.findViewById(R.id.tv_shop_place);
        tv_indent_number = (TextView) view.findViewById(R.id.tv_indent_number);
        tv_indent_content_money_shifu = (TextView) view.findViewById(R.id.tv_indent_content_money_shifu);
        tv_indent_content_money = (TextView) view.findViewById(R.id.tv_indent_content_money);
        tv_indent_time = (TextView) view.findViewById(R.id.tv_indent_time);
        iv_school_head = (ImageView) view.findViewById(R.id.iv_school_head);
        iv_ew_caode = (ImageView) view.findViewById(R.id.iv_ew_caode);
        xian_list_up = (View) view.findViewById(R.id.xian_list_up);
        xian_list_bot = (View) view.findViewById(R.id.xian_list_bot);
        xian_list_bot.setVisibility(View.GONE);
        tv_caidan_background.setFocusable(true);
        iv_indent_content_phone = (ImageView) view.findViewById(R.id.iv_indent_content_phone);
        iv_caidan.setVisibility(View.GONE);
        iv_caidan.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        tv_login_back.setText("订单详情");
        iv_indent_content_daohang.setOnClickListener(this);
        iv_indent_content_phone.setOnClickListener(this);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        height = dm.heightPixels;
    }

    @Override
    protected void initListener() {
        final View inflate = LayoutInflater.from(CarLiveDetails.this).inflate(R.layout.pop_indent_content_caidan, null);
        final TextView tv_caidan_message = (TextView) inflate.findViewById(R.id.tv_caidan_message);
        final TextView tv_caidan_shouye = (TextView) inflate.findViewById(R.id.tv_caidan_shouye);
        final TextView tv_caidan_kefu = (TextView) inflate.findViewById(R.id.tv_caidan_kefu);
        iv_caidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (isClosePup) {
                    tv_caidan_background.setVisibility(View.VISIBLE);
                    tv_caidan_message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (pop != null && pop.isShowing()) {
                                Intent intent = new Intent(App.context, NewPlayActivity.class);
                                startActivity(intent);
                                finish();
                                pop.dismiss();
                                isClosePup = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_caidan_background.setVisibility(View.GONE);
                                    }
                                }, 200);
                            }
                        }
                    });
                    tv_caidan_shouye.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (pop != null && pop.isShowing()) {
                                pop.dismiss();
                                Intent intent = new Intent(App.context, MainActivity.class);
                                intent.putExtra("position", 0);
                                startActivity(intent);
                                finish();
                                isClosePup = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_caidan_background.setVisibility(View.GONE);
                                    }
                                }, 300);
                            }
                        }
                    });
                    tv_caidan_kefu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (pop != null && pop.isShowing()) {
                                pop.dismiss();
                                startActivity(new Intent(App.context, KeFuActivity.class));
                                isClosePup = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_caidan_background.setVisibility(View.GONE);
                                    }
                                }, 300);
                            }
                        }
                    });
                    pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    pop.setHeight(height - 240);
                    pop.setBackgroundDrawable(new BitmapDrawable());
                    //添加弹出、弹入的动画
                    pop.setAnimationStyle(R.style.Popupwindow_indent_caidan);
                    pop.setOutsideTouchable(false);
                    pop.setContentView(inflate);
                    pop.showAsDropDown(view, 0, 0);
                    isClosePup = false;
                } else {
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                        isClosePup = true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tv_caidan_background.setVisibility(View.GONE);
                            }
                        }, 300);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        getDataFromNet();

    }

    private void getDataFromNet() {
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.Car_Live_Indent_Details)
                .addParams("device_id", LoginUtils.getId(CarLiveDetails.this))
                .addParams("order_number", order_number)
                .addParams("user_id", user_id)
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
    private void process(String string) {
        CarDetailsBean carDetailsBean = JsonUtil.parseJsonToBean(string, CarDetailsBean.class);
        if (carDetailsBean != null) {
            int code = carDetailsBean.getCode();
            if (200 == code) {
                CarDetailsBean.ContentBean content = carDetailsBean.getContent();
                if (content != null) {
                    final List<CarDetailsBean.ContentBean.ShopListBean>  shopList = content.getShopList();
                    shop_name = content.getShop_name();
                    latitude = content.getLatitude_shop();
                    longitude = content.getLongitude_shop();
                    final String pay_price = content.getPay_price();
                    driver_school_phone = content.getPhone_shop();
                    driver_school_place = content.getShop_place_name();
                    final String order_number = content.getOrder_number();
                    final String shop_img = content.getShop_img();
                    final String all_old_price = content.getAll_old_price();
                    final String order_system_time = content.getOrder_system_time();
                    final String ew_code = content.getEw_code();
                    final String order_status = content.getOrder_status();
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_erweima.setText("券码："+ew_code);
                            if (shopList !=null&& shopList.size()!=0) {
                                lv_shangpin.setAdapter(new ShangPinAdapter(shopList,App.context,R.layout.carlive_details_shangpin_item));
                            }
                            if (!TextUtils.isEmpty(order_status)) {
                                if ("1".equals(order_status)) {
                                    tv_fanxian_states.setText("进行中");
                                } else if ("2".equals(order_status)) {

                                    tv_fanxian_states.setText("已完成");
                                } else if ("5".equals(order_status)) {
                                    tv_fanxian_states.setText("待返现");
                                } else if ("8".equals(order_status)) {
                                    tv_fanxian_states.setText("返现中");
                                }
                            }
                            if (!TextUtils.isEmpty(shop_img)) {
                                Picasso.with(App.context).load(shop_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_school_head);
                            }
                            if (!TextUtils.isEmpty(shop_name)) {
                                tv_shop_name.setText(shop_name);
                            }
                            if (!TextUtils.isEmpty(driver_school_place)) {
                                tv_shop_place.setText(driver_school_place);
                            }
                            if (!TextUtils.isEmpty(order_number)) {
                                tv_indent_number.setText(order_number);
                            }
                            if (!TextUtils.isEmpty(all_old_price)) {
                                tv_indent_content_money.setText(all_old_price);
                            }
                            if (!TextUtils.isEmpty(pay_price)) {
                                tv_indent_content_money_shifu.setText(pay_price);
                            }
                            if (!TextUtils.isEmpty(order_system_time)) {
                                tv_indent_time.setText(order_system_time);
                            }
                            Bitmap qrCode = XueYiCheUtils.createQRCodeDriver(ew_code, 260, 260, null);
                            iv_ew_caode.setImageBitmap(qrCode);
                        }
                    });
                }
            }
        }

    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_indent_content_daohang:
                //导航
                if (TextUtils.isEmpty(longitude)) {
                    showToastShort("位置异常");
                    return;
                }
                if (TextUtils.isEmpty(latitude)) {
                    showToastShort("位置异常");
                    return;
                }
                if (TextUtils.isEmpty(shop_name)) {
                    showToastShort("店铺名称为空");
                    return;
                }
                if (TextUtils.isEmpty(driver_school_place)) {
                    showToastShort("店铺地址为空");
                    return;
                }
                XueYiCheUtils.getDiaLocation(CarLiveDetails.this, latitude, longitude, shop_name, driver_school_place);
                break;
            case R.id.iv_indent_content_phone:
                if (TextUtils.isEmpty(driver_school_phone)) {
                    showToastShort("店铺电话为空");
                    return;
                }
                if (TextUtils.isEmpty(shop_name)) {
                    showToastShort("店铺名称为空");
                    return;
                }
                XueYiCheUtils.CallPhone(CarLiveDetails.this, shop_name, driver_school_phone);
                break;
        }
    }
    public class ShangPinAdapter extends BaseCommonAdapter {
        public ShangPinAdapter(List<CarDetailsBean.ContentBean.ShopListBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }
        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            CarDetailsBean.ContentBean.ShopListBean shopListBean = (CarDetailsBean.ContentBean.ShopListBean) item;
            String old_price = shopListBean.getOld_price();
            String service_name = shopListBean.getService_name();
            viewHolder.setText(R.id.tv_money,"¥"+old_price);
            viewHolder.setText(R.id.tv_shangpin,service_name);
        }
    }
}
