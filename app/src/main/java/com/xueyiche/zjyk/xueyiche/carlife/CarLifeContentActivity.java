package com.xueyiche.zjyk.xueyiche.carlife;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.carlife.bean.CarLiveXiaDanBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShangPinBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShopCart;
import com.xueyiche.zjyk.xueyiche.carlife.dialog.ShopCartDialog;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.CarLifeContentBean;
import com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview.LoopViewPager;
import com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview.ProgressPageIndicator;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.ObservableScrollView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.submit.CarLiveSubmitIndent;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by YJF on 2020/2/22.
 */
public class CarLifeContentActivity extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler, ShopCartDialog.ShopCartDialogImp, ShopCartDialog.ShopCartClear, ObservableScrollView.ScrollViewListener {

    private ObservableScrollView os_shop;
    private String shop_id, user_id;
    private String collect_status;
    private int width;
    private Handler mHandler;
    private TimerTask mTimerTask;
    private Timer mTimer;
    private static final int MSG_NEXT = 1;
    private static final int MSG_NEXT_ONE = 2;
    private boolean isCheck;
    private AdListView lv_car_fuwu;
    private ProgressPageIndicator progressPageIndicator;
    private String goods_id;
    private ShopCart shopCart;
    private Button bt_carlive_shop_buy;
    //顶部轮播图
    private ImageView iv_shop_phone, iv_shop_daohang, shoppingCartView, iv_caidan;
    private TextView tv_shop_name, tv_shop_address, tv_open_time, tv_shop_content_money, shopping_cart_total_num, tv_menshi_money;
    private LoopViewPager vp_shop_lunbo;
    private String phone_shop;
    private String shop_name;
    private CarLifeContentBean.ContentBean content;
    private String latitude_shop;
    private String longitude_shop;
    private String shop_place_name;
    private CarLiveShopFuWuAdapter carLiveShopFuWuAdapter;
    private String service_id;
    private String address;
    private LinearLayout ll_exam_back;
    private ImageView iv_shoucang, iv_fenxiang;
    private TextView tv_juli;
    private TextView tv_lunbo_number;
    private RelativeLayout rl_title;

    @Override
    protected int initContentView() {
        return R.layout.carlife_content_activity;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        shop_id = intent.getStringExtra("shop_id");
        service_id = intent.getStringExtra("service_id");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        ll_exam_back = (LinearLayout) view.findViewById(R.id.ll_exam_back);
        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        iv_shop_phone = (ImageView) view.findViewById(R.id.iv_shop_phone);
        iv_shop_daohang = (ImageView) view.findViewById(R.id.iv_shop_daohang);
        vp_shop_lunbo = (LoopViewPager) view.findViewById(R.id.vp_shop_lunbo);
        iv_shoucang = (ImageView) view.findViewById(R.id.iv_shoucang);
        iv_fenxiang = (ImageView) view.findViewById(R.id.iv_fenxiang);
        iv_caidan = (ImageView) view.findViewById(R.id.iv_caidan);
        tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
        tv_open_time = (TextView) view.findViewById(R.id.tv_open_time);
        tv_lunbo_number = (TextView) view.findViewById(R.id.tv_lunbo_number);
        tv_juli = (TextView) view.findViewById(R.id.tv_juli);
        tv_shop_address = (TextView) view.findViewById(R.id.tv_shop_address);
        os_shop = (ObservableScrollView) view.findViewById(R.id.os_shop);
        os_shop.setScrollViewListener(this);
        //服务列表
        lv_car_fuwu = (AdListView) view.findViewById(R.id.lv_car_fuwu);
        lv_car_fuwu.setItemsCanFocus(true);
        //购物车
        shoppingCartView = (ImageView) findViewById(R.id.shopping_cart);
        tv_shop_content_money = (TextView) view.findViewById(R.id.tv_shop_content_money);

        tv_menshi_money = (TextView) view.findViewById(R.id.tv_menshi_money);
        shopping_cart_total_num = (TextView) view.findViewById(R.id.shopping_cart_total_num);
        shoppingCartView.setOnClickListener(this);
        bt_carlive_shop_buy = (Button) view.findViewById(R.id.bt_carlive_shop_buy);
        bt_carlive_shop_buy.setOnClickListener(this);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        ll_exam_back.setOnClickListener(this);
        iv_fenxiang.setOnClickListener(this);
        iv_shoucang.setOnClickListener(this);
        iv_shop_phone.setOnClickListener(this);
        iv_caidan.setOnClickListener(this);
        iv_shop_daohang.setOnClickListener(this);
        NetBroadcastReceiver.mListeners.add(this);
        os_shop.smoothScrollBy(0, 0);
        os_shop.smoothScrollTo(0, 0);
        lv_car_fuwu.setFocusable(false);
        lv_car_fuwu.setVerticalScrollBarEnabled(false);
        lv_car_fuwu.setFastScrollEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void collction() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            if (!isCheck) {
                OkHttpUtils.post().url(AppUrl.Collection_Add)
                        .addParams("user_id", user_id)
                        .addParams("device_id", LoginUtils.getId(this))
                        .addParams("refer_id", shop_id)
                        .addParams("kind_type", "1")
                        .addParams("mark_type", "1")
                        .addParams("service_id", service_id)
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean != null) {
                                int code = successDisCoverBackBean.getCode();
                                if (200 == code) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            iv_shoucang.setImageResource(R.mipmap.jx_title_shoucang_select);
                                            showToastShort("已收藏");
                                            isCheck = true;
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
            } else {
                OkHttpUtils.post().url(AppUrl.Collection_Delete)
                        .addParams("user_id", user_id)
                        .addParams("refer_id", shop_id)
                        .addParams("kind_type", "1")
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean != null) {
                                int code = successDisCoverBackBean.getCode();
                                if (200 == code) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            iv_shoucang.setImageResource(R.mipmap.jx_title_shoucang);
                                            showToastShort("取消收藏");
                                            isCheck = false;
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

    @Override
    protected void initListener() {
        vp_shop_lunbo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (content != null) {
                    List<CarLifeContentBean.ContentBean.VolutionListBean> volution_list = content.getVolution_list();
                    if (volution_list != null && volution_list.size() > 0) {
                        tv_lunbo_number.setText(position + 1 + "/" + volution_list.size());

                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //选择下一个卡片
    public void next() {
        if (content != null) {
            List<CarLifeContentBean.ContentBean.VolutionListBean> volution_list = content.getVolution_list();
            if (volution_list != null && volution_list.size() > 0) {
                int pos = vp_shop_lunbo.getCurrentItem();
                pos += 1;
                vp_shop_lunbo.setCurrentItem(pos);
            }

        }
    }

    //启动动画
    public void startTimer() {
        stopTimer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_NEXT);
            }
        };
        mTimer = new Timer(true);
        mTimer.schedule(mTimerTask, 1000, 3000);
    }

    //停止动画
    public void stopTimer() {
        mHandler.removeMessages(MSG_NEXT);
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    private void lunbo() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_NEXT:
                        next();
                        break;
                    case MSG_NEXT_ONE:

                        break;
                }
                super.handleMessage(msg);
            }
        };
        startTimer();
        vp_shop_lunbo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        stopTimer();
                        break;
                    case MotionEvent.ACTION_UP:
                        startTimer();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        shopCart = new ShopCart();
        showTotalPrice();
        getDataFromNet();
    }


    private void getDataFromNet() {
        String longitude_user = PrefUtils.getString(App.context, "x", "");
        String latitude_user = PrefUtils.getString(App.context, "y", "");
        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(shop_id)) {
            showProgressDialog(false);
            OkHttpUtils.post()
                    .url(AppUrl.Car_Life_Content)
                    .addParams("device_id", LoginUtils.getId(CarLifeContentActivity.this))
                    .addParams("shop_id", shop_id)
                    .addParams("goods_id", goods_id)
                    .addParams("service_id", service_id)
                    .addParams("user_id", user_id)
                    .addParams("longitude_user", longitude_user)
                    .addParams("latitude_user", latitude_user)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        processData(string);
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

    /**
     * 解析数据
     *
     * @param string
     */
    private void processData(String string) {
        final CarLifeContentBean carLifeContentBean = JsonUtil.parseJsonToBean(string, CarLifeContentBean.class);
        if (carLifeContentBean != null) {
            final int code = carLifeContentBean.getCode();
            final String msg = carLifeContentBean.getMsg();
            if (!TextUtils.isEmpty("" + code)) {
                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (200 == code) {
                            content = carLifeContentBean.getContent();
                            if (content != null) {
                                //评论内容
                                collect_status = content.getCollect_status();
                                if ("1".equals(collect_status)) {
                                    //未收藏
                                    isCheck = false;
                                    iv_shoucang.setImageResource(R.mipmap.jx_title_shoucang);
                                } else if ("2".equals(collect_status)) {
                                    isCheck = true;
                                    iv_shoucang.setImageResource(R.mipmap.jx_title_shoucang_select);
                                }
                                //轮播
                                List<CarLifeContentBean.ContentBean.VolutionListBean> volution_list = content.getVolution_list();
                                if (volution_list != null && volution_list.size() > 0) {
                                    vp_shop_lunbo.setAdapter(new CarLiveShopPagAdapter(volution_list));
                                    lunbo();
                                }
                                //店铺信息
                                CarLifeContentBean.ContentBean.ShopInfoBean shop_info = content.getShop_info();
                                List<CarLifeContentBean.ContentBean.LabelListBean> label_list = content.getLabel_list();
                                dianPuXinXi(shop_info, label_list);
                                //附加信息内容
                                CarLifeContentBean.ContentBean.AppendInfoBean append_info = content.getAppend_info();
                                fuJiaXinXi(append_info);

                                List<CarLifeContentBean.ContentBean.GoodListBean> good_list = content.getGood_list();
                                //商品列表
                                shopGoodsData(good_list);
                            }
                            os_shop.smoothScrollBy(0, 0);
                        } else {
                            showToastShort(msg);
                        }
                    }
                });

            }
        }
    }

    /**
     * 商品信息 列表
     *
     * @param good_list
     */
    private void shopGoodsData(List<CarLifeContentBean.ContentBean.GoodListBean> good_list) {
        if (good_list != null && good_list.size() > 0) {
            carLiveShopFuWuAdapter = new CarLiveShopFuWuAdapter(good_list);
            lv_car_fuwu.setAdapter(carLiveShopFuWuAdapter);
        }
    }

    /**
     * 附加信息  开店时间 停车 wife
     *
     * @param append_info
     */
    private void fuJiaXinXi(CarLifeContentBean.ContentBean.AppendInfoBean append_info) {
        if (append_info != null) {
            String close_time_shop = append_info.getClose_time_shop();
            String open_time_shop = append_info.getOpen_time_shop();
            tv_open_time.setText(open_time_shop + "-" + close_time_shop);
        }
    }

    /**
     * 店铺信息 轮播图 标签 电话 地址
     *
     * @param shop_info
     * @param label_list
     */
    private void dianPuXinXi(CarLifeContentBean.ContentBean.ShopInfoBean shop_info, List<CarLifeContentBean.ContentBean.LabelListBean> label_list) {
        if (shop_info != null) {
            shop_name = shop_info.getShop_name();
            String distance = shop_info.getDistance();
            tv_juli.setText("距您:" + distance);
            final String purchase_number = shop_info.getPurchase_number();
            shop_place_name = shop_info.getShop_place_name();
            phone_shop = shop_info.getPhone_shop();
            latitude_shop = shop_info.getLatitude_shop();
            longitude_shop = shop_info.getLongitude_shop();
            if (!TextUtils.isEmpty(shop_name)) {
                tv_shop_name.setText(shop_name);
            }
            if (!TextUtils.isEmpty(shop_place_name)) {
                tv_shop_address.setText(shop_place_name);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_shop_kefu:
                openActivity(KeFuActivity.class);
                break;
            case R.id.iv_shop_phone:
                if (content != null) {
                    //商铺电话
                    XueYiCheUtils.CallPhone(this, shop_name, phone_shop);
                }

                break;
            case R.id.iv_caidan:
                openActivity(KeFuActivity.class);
                break;
            case R.id.iv_shoucang:
                if (content != null) {
                    //收藏
                    if (DialogUtils.IsLogin()) {
                        collction();
                    } else {
                        openActivity(LoginFirstStepActivity.class);
                    }
                }

                break;
            case R.id.iv_shop_daohang:
                if (content != null) {
                    //导航
                    XueYiCheUtils.getDiaLocation(this, latitude_shop, longitude_shop, shop_name, shop_place_name);
                }

                break;
            case R.id.iv_fenxiang:
                if (content != null) {
                    //                分享
                    List<CarLifeContentBean.ContentBean.VolutionListBean> volution_list = content.getVolution_list();
                    if (volution_list != null && volution_list.size() > 0) {
                        address = volution_list.get(0).getAddress();
                    }
                    XueYiCheUtils.showShareAppCommon(App.context, this,
                            StringConstants.SHARED_TITLE + shop_name,
                            "http://xueyiche.cn/share/cheshenghuo.php?shop_id=" + shop_id,
                            StringConstants.SHARED_TEXT, address,
                            "http://xueyiche.cn/share/cheshenghuo.php?shop_id=" + shop_id);
                }

                break;
            case R.id.bt_carlive_shop_buy:
                if (DialogUtils.IsLogin()) {
                    if (content != null) {
                        user_id = PrefUtils.getString(this, "user_id", "");
                        double shoppingTotalPrice = shopCart.getShoppingTotalPrice();
                        //订单总金额
                        if (shoppingTotalPrice > 0) {
                            //进入支付页
                            if (AppUtils.isFastDoubleClick()) {
                                return;
                            }
                            goSubmitIndent();
                        } else {
                            showToastShort("请选择商品");
                        }
                    }
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.shopping_cart:
                //显示购物车
                showCart();
                break;
        }
    }


    private void goSubmitIndent() {
        Map<CarLifeContentBean.ContentBean.GoodListBean, Integer> shoppingSingleMap = shopCart.getShoppingSingleMap();
        Set<Map.Entry<CarLifeContentBean.ContentBean.GoodListBean, Integer>> set = shoppingSingleMap.entrySet();
        List<ShangPinBean> shangPinBeanList = new ArrayList<>();
        for (Iterator<Map.Entry<CarLifeContentBean.ContentBean.GoodListBean, Integer>> it = set.iterator(); it.hasNext(); ) {
            Map.Entry<CarLifeContentBean.ContentBean.GoodListBean, Integer> next = it.next();
            CarLifeContentBean.ContentBean.GoodListBean key = next.getKey();
            //商品
            int goods_id = key.getGoods_id();
            double price = key.getPrice();
            //商品数量
            Integer value = next.getValue();
            ShangPinBean shangPinBean = new ShangPinBean();
            shangPinBean.setNum(value);
            shangPinBean.setGoods_id(goods_id);
            shangPinBean.setPrice(price);
            shangPinBeanList.add(shangPinBean);
        }
        String shop_car_content = shangPinBeanList.toString();
        OkHttpUtils.post()
                .url(AppUrl.Car_Life_XiaDan)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("shop_id", shop_id)
                .addParams("user_id", user_id)
                .addParams("shop_car", shop_car_content)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    CarLiveXiaDanBean carLiveXiaDanBean = JsonUtil.parseJsonToBean(string, CarLiveXiaDanBean.class);
                    if (carLiveXiaDanBean != null) {
                        CarLiveXiaDanBean.ContentBean content = carLiveXiaDanBean.getContent();
                        if (content != null) {
                            String order_number = content.getOrder_number();
                            if (order_number != null && !TextUtils.isEmpty(order_number)) {
                                Intent intent = new Intent(CarLifeContentActivity.this, CarLiveSubmitIndent.class);
                                intent.putExtra("order_number", order_number);
                                startActivity(intent);
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

    //弹出购物车
    private void showCart() {
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            ShopCartDialog dialog = new ShopCartDialog(this, shopCart, R.style.cartdialog, shop_id);
            Window window = dialog.getWindow();
            dialog.setShopCartDialogImp(this);
            dialog.setShopCartClear(this);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            params.dimAmount = 0.5f;
            window.setAttributes(params);
        } else {
            showToastShort("请选择商品");
        }
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();

        } else {
            getDataFromNet();
        }
    }

    @Override
    public void dialogDismiss() {
        showTotalPrice();
        carLiveShopFuWuAdapter.notifyDataSetChanged();
    }

    private void showTotalPrice() {
        if (shopCart != null && shopCart.getShoppingTotalPrice() > 0) {
            double shoppingTotalPrice = shopCart.getShoppingTotalPrice();
            double shoppingTotalPriceMenShi = shopCart.getMsShoppingTotalPrice();
            BigDecimal a = new BigDecimal(shoppingTotalPrice).setScale(2, RoundingMode.HALF_UP);
            BigDecimal b = new BigDecimal(shoppingTotalPriceMenShi).setScale(2, RoundingMode.HALF_UP);
            tv_shop_content_money.setText("￥" + a);
            tv_menshi_money.setText("门市价￥" + b);
            shopping_cart_total_num.setVisibility(View.VISIBLE);
            shopping_cart_total_num.setText("" + shopCart.getShoppingAccount());
        } else {
            shopping_cart_total_num.setVisibility(View.INVISIBLE);
            tv_shop_content_money.setText("￥0");
            tv_menshi_money.setText("门市价￥0");
        }
    }

    @Override
    public void clearShoppingCar() {
        List<CarLifeContentBean.ContentBean.GoodListBean> good_list = content.getGood_list();
        if (good_list != null && good_list.size() > 0) {
            for (CarLifeContentBean.ContentBean.GoodListBean goodListBean : good_list) {
                goodListBean.setNumber(0);
            }
        }
        carLiveShopFuWuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//标题栏颜色的变化
        int height = vp_shop_lunbo.getHeight();
        if (y <= 0 || y < height) {
            rl_title.setBackgroundColor(Color.argb(0, 0, 0, 0));//AGB由相关工具获得，或者美工提供
        } else if (y > height) {
            rl_title.setBackgroundColor(Color.argb(255, 255, 255, 255));
        }
    }

    public class CarLiveShopFuWuAdapter extends BaseAdapter {

        List<CarLifeContentBean.ContentBean.GoodListBean> good_list;
        private int mItemCount;

        public CarLiveShopFuWuAdapter(List<CarLifeContentBean.ContentBean.GoodListBean> good_list) {
            if (good_list != null) {
                mItemCount = good_list.size();
                this.good_list = good_list;
            } else {
                mItemCount = 0;
            }
        }

        @Override
        public int getCount() {
            return mItemCount;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(CarLifeContentActivity.this).inflate(
                        R.layout.car_fuwu_item, parent, false);
                holder = new ViewHolder();
                holder.rl_car_fuwu_item_top = (RelativeLayout) convertView.findViewById(R.id.rl_car_fuwu_item_top);
                //价格
                holder.tv_goods_money_shop = (TextView) convertView.findViewById(R.id.tv_goods_money_shop);
                holder.tv_goods_oldmoney_shop = (TextView) convertView.findViewById(R.id.tv_goods_oldmoney_shop);
                holder.ll_menshi = (LinearLayout) convertView.findViewById(R.id.ll_menshi);
                //价格单位
                holder.tv_shop_service_cartype = (TextView) convertView.findViewById(R.id.tv_shop_service_cartype);
                holder.iv_fuwu_chick = (ImageView) convertView.findViewById(R.id.iv_fuwu_chick);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (good_list != null && good_list.size() > 0) {
                CarLifeContentBean.ContentBean.GoodListBean goodListBean = good_list.get(position);
                final double old_price = goodListBean.getOld_price();
                String discount = goodListBean.getDiscount();
                String goods_description = goodListBean.getGoods_description();
                final String goods_name = goodListBean.getGoods_name();
                int service_info_id = goodListBean.getGoods_id();
                double price = goodListBean.getPrice();
                final boolean weiXuBaoYang = goodListBean.isWeiXuBaoYang();
                if (!weiXuBaoYang) {
                    if (!TextUtils.isEmpty(old_price + "") && old_price > 0) {
                        goodListBean.setWeiXuBaoYang(false);
                        holder.ll_menshi.setVisibility(View.VISIBLE);
                        holder.tv_goods_money_shop.setText("¥" + price);
                        holder.tv_goods_oldmoney_shop.setText("¥" + old_price);
                        holder.tv_goods_oldmoney_shop.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
                    } else if (!TextUtils.isEmpty(discount)) {
                        goodListBean.setWeiXuBaoYang(true);
                        holder.ll_menshi.setVisibility(View.GONE);
                        holder.tv_goods_money_shop.setText(discount);
                    }
                } else {
                    holder.ll_menshi.setVisibility(View.GONE);
                    holder.tv_goods_money_shop.setText(discount);
                }
                if (!TextUtils.isEmpty(goods_name)) {
                    holder.tv_shop_service_cartype.setText(goods_name);
                } else {
                    holder.tv_shop_service_cartype.setText("");
                }
                CarLifeContentBean.ContentBean.GoodListBean dishByPosition = getDishByPosition(position);
                if (!TextUtils.isEmpty(goods_id)) {
                    if (goods_id.equals(service_info_id + "")) {
                        goods_id = "";
                        if (!TextUtils.isEmpty(old_price + "") && old_price > 0) {
                            holder.iv_fuwu_chick.setImageResource(R.mipmap.csh_fuwu_item_ture);
                            if (shopCart.addShoppingFuWu(dishByPosition)) {
                                showTotalPrice();
                            }
                        }
                    } else {
                        holder.iv_fuwu_chick.setImageResource(R.mipmap.csh_fuwu_item_false);
                    }
                }
                if (dishByPosition.getNumber() > 0) {
                    holder.iv_fuwu_chick.setImageResource(R.mipmap.csh_fuwu_item_ture);
                } else {
                    holder.iv_fuwu_chick.setImageResource(R.mipmap.csh_fuwu_item_false);
                }
                //点击事件
                holder.rl_car_fuwu_item_top.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CarLifeContentBean.ContentBean.GoodListBean dishByPosition = getDishByPosition(position);
                        int number = dishByPosition.getNumber();
                        if (number > 0) {
                            if (weiXuBaoYang) {
                                dishByPosition.setOld_price(0);
                            }
                            shopCart.subShoppingFuWu(dishByPosition);
                        } else {
                            if (!(!TextUtils.isEmpty(old_price + "") && old_price > 0)) {
                                //弹出输入框输入金额
                                getFeiYong(shop_name, dishByPosition);
                            } else {
                                shopCart.addShoppingFuWu(dishByPosition);
                            }
                        }
                        showTotalPrice();
                        notifyDataSetChanged();
                    }
                });
            }
            return convertView;
        }

        public CarLifeContentBean.ContentBean.GoodListBean getDishByPosition(int position) {
            if (good_list != null && good_list.size() > 0) {
                CarLifeContentBean.ContentBean.GoodListBean goodListBean = good_list.get(position);
                return goodListBean;
            } else {
                CarLifeContentBean.ContentBean.GoodListBean a = new CarLifeContentBean.ContentBean.GoodListBean();
                return a;
            }
        }
    }


    public void getFeiYong(final String name, final CarLifeContentBean.ContentBean.GoodListBean dishByPosition) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.carlive_shop_getfeiyong, null);
        final TextView tv_title_fu = (TextView) view.findViewById(R.id.tv_title_fu);
        final TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        final ImageView tv_quxiao = (ImageView) view.findViewById(R.id.tv_quxiao);
        final ImageView tv_queren = (ImageView) view.findViewById(R.id.tv_queren);
        tv_title.setText(name);
        tv_title_fu.setText("请输入您的消费金额\n系统将自动计算折扣");
        final EditText et_feiyong = (EditText) view.findViewById(R.id.et_feiyong);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth * 4 / 5;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = et_feiyong.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    int i = Integer.parseInt(trim);
                    if (i > 0) {
                        String discount_0 = dishByPosition.getDiscount_0();
                        BigDecimal dis = new BigDecimal(discount_0);
                        BigDecimal money = new BigDecimal(i);
                        BigDecimal multiply = dis.multiply(money);
                        dishByPosition.setPrice(multiply.doubleValue());
                        dishByPosition.setOld_price(Double.parseDouble(trim));
                        shopCart.addShoppingFuWu(dishByPosition);
                        showTotalPrice();
                        carLiveShopFuWuAdapter.notifyDataSetChanged();
                        InputMethodManager imm = (InputMethodManager) CarLifeContentActivity.this.getSystemService(CarLifeContentActivity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(et_feiyong.getWindowToken(), 0);
                        dialog01.dismiss();
                    } else {
                        showToastShort("金额必须大于0");
                    }
                }
            }
        });
        //点击空白处弹框不消失
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) CarLifeContentActivity.this.getSystemService(CarLifeContentActivity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_feiyong.getWindowToken(), 0);
                dialog01.dismiss();
            }
        });
        dialog01.setCancelable(false);
        dialog01.show();
    }

    private static class ViewHolder {
        private RelativeLayout rl_car_fuwu_item_top;
        private TextView tv_shop_service_cartype;
        private TextView tv_goods_money_shop;
        private TextView tv_goods_oldmoney_shop;
        private LinearLayout ll_menshi;
        private ImageView iv_fuwu_chick;
    }

    public class CarLiveShopPagAdapter extends PagerAdapter {
        private List<CarLifeContentBean.ContentBean.VolutionListBean> volution_list;

        public CarLiveShopPagAdapter(List<CarLifeContentBean.ContentBean.VolutionListBean> volution_list) {
            this.volution_list = volution_list;
        }

        @Override
        public int getCount() {
            if (volution_list != null) {
                return volution_list.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(App.context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            CarLifeContentBean.ContentBean.VolutionListBean volutionListBean = volution_list.get(position);
            if (volutionListBean != null) {
                String address = volutionListBean.getAddress();
                Picasso.with(App.context).load(address).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
