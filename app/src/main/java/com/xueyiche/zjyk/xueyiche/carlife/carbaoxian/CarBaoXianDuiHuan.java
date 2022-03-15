package com.xueyiche.zjyk.xueyiche.carlife.carbaoxian;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.PointF;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.carlife.bean.BaoDanBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.DuiHuanGoodsBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.DuiHuanShangPinBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShopCartGoods;
import com.xueyiche.zjyk.xueyiche.carlife.bean.ShopCartImp;
import com.xueyiche.zjyk.xueyiche.carlife.dialog.DuihuanShopCartDialog;
import com.xueyiche.zjyk.xueyiche.carlife.dialog.FakeAddImageView;
import com.xueyiche.zjyk.xueyiche.carlife.dialog.PointFTypeEvaluator;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.YiBiSelectLocationBean;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Owner on 2017/10/26.
 */
public class CarBaoXianDuiHuan extends BaseActivity implements View.OnClickListener, ShopCartImp, DuihuanShopCartDialog.ShopCartDialogImp {
    private LinearLayout ll_exam_back, ll_zongjia;
    private TextView tv_title;
    private FrameLayout rl_shuliang;
    private RecyclerView recycler_view;
    private DuiHuanAdapter myAdapter;
    private List<DuiHuanGoodsBean.ContentBean> dishMenuList;//数据源
    private ShopCartGoods shopCart;
    private ImageView shoppingCartView, iv_duihuan_search;
    private TextView totalPriceTextView;
    private TextView totalPriceNumTextView;
    private TextView tv_total_duihuan;
    private RelativeLayout mainLayout;
    private RelativeLayout rl_search_baodan;
    private GridLayoutManager mLayoutManage;
    private TextView tv_duihuan;
    private EditText ed_duihuan_search;
    private boolean isChick = false;
    private String money_baodan;
    private String baodanhao;
    public static CarBaoXianDuiHuan instance;


    @Override
    protected int initContentView() {
        return R.layout.baoxian_duihuan;
    }

    @Override
    protected void initView() {
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        rl_search_baodan = (RelativeLayout) findViewById(R.id.rl_search_baodan);
        shoppingCartView = (ImageView) findViewById(R.id.shopping_cart);
        iv_duihuan_search = (ImageView) findViewById(R.id.iv_duihuan_search);
        totalPriceTextView = (TextView) findViewById(R.id.shopping_cart_total_tv);
        totalPriceNumTextView = (TextView) findViewById(R.id.shopping_cart_total_num);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.duihuan_include).findViewById(R.id.ll_exam_back);
        tv_title = (TextView) view.findViewById(R.id.duihuan_include).findViewById(R.id.tv_login_back);
        rl_shuliang = (FrameLayout) view.findViewById(R.id.shopping_cart_layout);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        tv_duihuan = (TextView) view.findViewById(R.id.tv_duihuan);
        //搜索框下的兑换总额
        tv_total_duihuan = (TextView) view.findViewById(R.id.tv_total_duihuan);
        ll_zongjia = (LinearLayout) view.findViewById(R.id.ll_zongjia);
        ed_duihuan_search = (EditText) view.findViewById(R.id.ed_duihuan_search);
        ll_zongjia.setVisibility(View.GONE);
        ll_exam_back.setOnClickListener(this);
        rl_shuliang.setOnClickListener(this);
        tv_duihuan.setOnClickListener(this);
        iv_duihuan_search.setOnClickListener(this);
        ll_zongjia.setOnClickListener(this);
        mLayoutManage = new GridLayoutManager(this, 2);
        mLayoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(mLayoutManage);
        instance = this;


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        getGoods();
        tv_title.setText("兑换好礼");
        ed_duihuan_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                objSearch();
                if (actionId == KeyEvent.KEYCODE_ENTER) {

                    return true;
                }
                return false;
            }
        });
    }

    private void objSearch() {
        baodanhao = ed_duihuan_search.getText().toString().trim();
        if (!TextUtils.isEmpty(baodanhao)) {
            if (XueYiCheUtils.IsHaveInternet(this)) {
                OkHttpUtils.post().url(AppUrl.BaoDanChaXun)
                        .addParams("policy_num", baodanhao)
                        .addParams("area_id", PrefUtils.getParameter("area_id"))
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            final BaoDanBean baoDanBean = JsonUtil.parseJsonToBean(string, BaoDanBean.class);
                            if (baoDanBean != null) {
                                final String msg = baoDanBean.getMsg();
                                final int code = baoDanBean.getCode();

                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showToastShort(msg);

                                        if (200 == code) {
                                            isChick = true;
                                            myAdapter.notifyDataSetChanged();
                                            money_baodan = baoDanBean.getContent().getOld_amount() + "";
                                            BigDecimal a = new BigDecimal(money_baodan).setScale(0, BigDecimal.ROUND_HALF_UP);
                                            tv_total_duihuan.setText(a+"");
                                            ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(rl_search_baodan, "translationY", -50);
                                            ObjectAnimator valueAnimator1 = ObjectAnimator.ofFloat(ll_zongjia, "translationY", -50);
                                            valueAnimator.setDuration(500);
                                            valueAnimator1.setDuration(500);
                                            valueAnimator.start();
                                            valueAnimator1.start();
                                            ll_zongjia.setVisibility(View.VISIBLE);
                                        } else {
                                            isChick = false;
                                            ll_zongjia.setVisibility(View.GONE);
                                            money_baodan = "0";
                                        }
                                    }
                                });
                            }
                        }
                        return null;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        shopCart = new ShopCartGoods();
                        totalPriceTextView.setText("已用金额：0");
                        totalPriceNumTextView.setVisibility(View.INVISIBLE);
                        myAdapter.notifyDataSetChanged();

                    }
                });
            } else {
                showToastShort(StringConstants.CHECK_NET);
            }
        } else {
            showToastShort("请输入保单号");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.shopping_cart_layout:
                showCart();
                break;
            case R.id.iv_duihuan_search:
                objSearch();
                break;
            case R.id.tv_duihuan:
                if (isChick) {
                    //弹窗的信息
                    duihuanAll();

                } else {
                    showToastShort("请填写保单号");
                }
                break;
        }
    }

    public void duihuanAll() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.duihuan_address, (ViewGroup) findViewById(R.id.order_practice_dialog));
        final EditText edName = (EditText) layout.findViewById(R.id.ed_duihuan_name);
        final EditText edPhone = (EditText) layout.findViewById(R.id.ed_duihuan_phone);
        final EditText edAddress = (EditText) layout.findViewById(R.id.ed_duihuan_address);
        if (XueYiCheUtils.IsHaveInternet(this)) {
            String user_id = PrefUtils.getParameter("user_id");
            OkHttpUtils.post().url(AppUrl.YIBISHOPINDENTLOCATION)
                    .addParams("user_id", user_id)
                    .addParams("area_id", PrefUtils.getParameter("area_id"))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final YiBiSelectLocationBean yiBiSelectLocationBean = JsonUtil.parseJsonToBean(string, YiBiSelectLocationBean.class);
                        if (yiBiSelectLocationBean != null) {
                            final int code = yiBiSelectLocationBean.getCode();
                            final String msg = yiBiSelectLocationBean.getMsg();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (200 == code) {
                                        YiBiSelectLocationBean.ContentBean content = yiBiSelectLocationBean.getContent();
                                        if (content != null) {
                                            String em_user_name = content.getEm_user_name();
                                            String em_phone = content.getEm_phone();
                                            String em_address = content.getEm_address();
                                            if (!TextUtils.isEmpty(em_user_name)) {
                                                edName.setText(em_user_name);
                                            }
                                            if (!TextUtils.isEmpty(em_user_name)) {
                                                edPhone.setText(em_phone);
                                            }
                                            if (!TextUtils.isEmpty(em_user_name)) {
                                                edAddress.setText(em_address);
                                            }
                                        }
                                    } else {
                                        showToastShort(msg);
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
            new AlertDialog.Builder(this)
                    .setTitle("完善收货信息")
                    .setView(layout)
                    .setIcon(R.mipmap.logo)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edPhone.getWindowToken(), 0);
                            imm.hideSoftInputFromWindow(edName.getWindowToken(), 0);
                            imm.hideSoftInputFromWindow(edAddress.getWindowToken(), 0);
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确认兑换", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = edName.getText().toString().trim();
                            String phone = edPhone.getText().toString().trim();
                            String addreess = edAddress.getText().toString().trim();

                            if (TextUtils.isEmpty(name)) {
                                showToastShort("请填写收货人姓名");
                                return;
                            }
                            if (TextUtils.isEmpty(phone)) {
                                showToastShort("请填写收货人电话");
                                return;
                            }
                            if (TextUtils.isEmpty(addreess)) {
                                showToastShort("请填写收货人地址");
                                return;
                            }
                            double shoppingTotalPrice = shopCart.getShoppingTotalPrice();
                            if (!TextUtils.isEmpty(money_baodan)) {
                                BigDecimal a = new BigDecimal(money_baodan);
                                BigDecimal b = new BigDecimal(shoppingTotalPrice);
                                BigDecimal c = new BigDecimal(0);
                                if (b.compareTo(c) > 0) {
                                    if (a.compareTo(b) >= 0) {
                                        //兑换
                                        Map<DuiHuanGoodsBean.ContentBean, Integer> shoppingSingleMap = shopCart.getShoppingSingleMap();
                                        Set<Map.Entry<DuiHuanGoodsBean.ContentBean, Integer>> set = shoppingSingleMap.entrySet();
                                        List<DuiHuanShangPinBean> shangPinBeanList = new ArrayList<>();
                                        for (Iterator<Map.Entry<DuiHuanGoodsBean.ContentBean, Integer>> it = set.iterator(); it.hasNext(); ) {
                                            Map.Entry<DuiHuanGoodsBean.ContentBean, Integer> entry = it.next();
                                            DuiHuanGoodsBean.ContentBean key = entry.getKey();
                                            //商品
                                            int dishid = key.getId();
                                            //商品数量
                                            Integer value = entry.getValue();
                                            DuiHuanShangPinBean shangPinBean = new DuiHuanShangPinBean();
                                            shangPinBean.setCvt_num(value);
                                            shangPinBean.setMain_id(dishid);
                                            shangPinBean.setPolicy_num(baodanhao);
                                            shangPinBeanList.add(shangPinBean);
                                        }
                                        String string = shangPinBeanList.toString();
                                        //兑换
                                        duiHuanGoods(string, shoppingTotalPrice + "", name, phone, addreess);

                                    } else {
                                        showToastShort("可用金额不足");
                                    }
                                } else {
                                    showToastShort("请选择商品");
                                }


                            }
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edAddress.getWindowToken(), 0);
                            imm.hideSoftInputFromWindow(edName.getWindowToken(), 0);
                            imm.hideSoftInputFromWindow(edPhone.getWindowToken(), 0);
                        }
                    })
                    .show();

        }

    }

    private void duiHuanGoods(String goods, String shoppingTotalPrice, String name, String phone, String address) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.LiPinDuiHuan)
                    .addParams("user_name", name)
                    .addParams("phone", phone)
                    .addParams("address", address)
                    .addParams("policy_num", baodanhao)
                    .addParams("amount", shoppingTotalPrice)
                    .addParams("gift_dts", goods)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final SuccessBackBean duiHuanBean = JsonUtil.parseJsonToBean(string, SuccessBackBean.class);
                        if (duiHuanBean != null) {
                            final String msg = duiHuanBean.getMsg();
                            final int code = duiHuanBean.getCode();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    showToastShort(msg);
                                    if (200 == code) {
                                        //温馨提示
                                        DialogUtils.showDaDiBaoXian(CarBaoXianDuiHuan.this, "恭喜您，您的保单礼品兑换成功，工作人员会尽快为您邮寄，请您耐心等待！如有疑问请咨询客服电话：0451-51068980");
                                    }
                                }
                            });
                        }
                    }
                    return null;
                }

                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(Object response) {

                }
            });
        } else {
            showToastShort(StringConstants.CHECK_NET);
        }
    }

    private void showCart() {
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            DuihuanShopCartDialog dialog = new DuihuanShopCartDialog(this, shopCart, R.style.cartdialog, money_baodan);
            Window window = dialog.getWindow();
            dialog.setShopCartDialogImp(this);
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
    public void dialogDismiss() {
        showTotalPrice();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void add(View view, int postion) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        shoppingCartView.getLocationInWindow(cartLocation);
        recycler_view.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1] - recycleLocation[0];
        endP.x = cartLocation[0];
        endP.y = cartLocation[1] - recycleLocation[0];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final FakeAddImageView fakeAddImageView = new FakeAddImageView(this);
        mainLayout.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.mipmap.ic_add_baoxian);
        fakeAddImageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        fakeAddImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        fakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(fakeAddImageView, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fakeAddImageView.setVisibility(View.GONE);
                mainLayout.removeView(fakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(shoppingCartView, "scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(shoppingCartView, "scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();

        showTotalPrice();

    }

    private void showTotalPrice() {
        if (shopCart != null && shopCart.getShoppingTotalPrice() > 0) {
            double shoppingTotalPrice = shopCart.getShoppingTotalPrice();
            BigDecimal a = new BigDecimal(shoppingTotalPrice).setScale(0, BigDecimal.ROUND_HALF_UP);
            totalPriceTextView.setText("已用金额：" + a);
            totalPriceNumTextView.setVisibility(View.VISIBLE);
            totalPriceNumTextView.setText("" + shopCart.getShoppingAccount());
        } else {
            totalPriceNumTextView.setVisibility(View.INVISIBLE);
            totalPriceTextView.setText("已用金额：0");
        }
    }

    @Override
    public void remove(View view, int postion) {

    }

    public void getGoods() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.get().url(AppUrl.DuiHuanGoods)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        DuiHuanGoodsBean duiHuanGoodsBean = JsonUtil.parseJsonToBean(string, DuiHuanGoodsBean.class);
                        if (duiHuanGoodsBean != null) {
                            dishMenuList = duiHuanGoodsBean.getContent();
                        }
                    }
                    return null;
                }

                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(Object response) {
                    myAdapter = new DuiHuanAdapter(dishMenuList);
                    recycler_view.setAdapter(myAdapter);
                    myAdapter.setShopCartImp(CarBaoXianDuiHuan.this);
                }
            });
        } else {
            showToastShort(StringConstants.CHECK_NET);
        }

    }

    public class DuiHuanAdapter extends RecyclerView.Adapter {
        private List<DuiHuanGoodsBean.ContentBean> mMenuList;
        private int mItemCount;
        private ShopCartImp shopCartImp;

        public DuiHuanAdapter(List<DuiHuanGoodsBean.ContentBean> mMenuList) {
            this.mMenuList = mMenuList;
            if (mMenuList != null) {
                this.mItemCount = mMenuList.size();
            } else {
                this.mItemCount = 0;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_dish, parent, false);
            DishViewHolder viewHolder = new DishViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final DishViewHolder dishholder = (DishViewHolder) holder;
            if (dishholder != null) {
                final DuiHuanGoodsBean.ContentBean dishByPosition = getDishByPosition(position);
                if (dishMenuList != null) {
                    DuiHuanGoodsBean.ContentBean contentBean = dishMenuList.get(position);
                    if (contentBean != null) {
                        String gift_url = contentBean.getGift_url();
                        final int price = contentBean.getPrice();
                        Picasso.with(App.context).load(gift_url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(dishholder.iv_goods);
                        dishholder.iv_goods.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (isChick) {
                                    double shoppingTotalPrice = shopCart.getShoppingTotalPrice();
                                    if (!TextUtils.isEmpty(money_baodan)) {
                                        BigDecimal a = new BigDecimal(money_baodan);
                                        BigDecimal b = new BigDecimal(shoppingTotalPrice);
                                        //当前商品价格
                                        BigDecimal c = new BigDecimal(price);
                                        if (a.compareTo(b.add(c)) >= 0) {
                                            if (shopCart.addShoppingSingle(dishByPosition)) {
                                                notifyItemChanged(position);
                                                if (shopCartImp != null)
                                                    shopCartImp.add(view, position);
                                            }
                                        } else {
                                            showToastShort("可用金额不足");
                                        }
                                    }
                                } else {
                                    showToastShort("请填写保单号");
                                }
                            }
                        });
                    }
                }

            }
        }


        public DuiHuanGoodsBean.ContentBean getDishByPosition(int position) {
            return mMenuList.get(position);
        }


        @Override
        public int getItemCount() {
            return mItemCount;
        }


        public void setShopCartImp(ShopCartImp shopCartImp) {
            this.shopCartImp = shopCartImp;
        }

        private class DishViewHolder extends RecyclerView.ViewHolder {
            private ImageView iv_goods;

            public DishViewHolder(View itemView) {
                super(itemView);
                iv_goods = (ImageView) itemView.findViewById(R.id.iv_goods);
            }

        }
    }

}
