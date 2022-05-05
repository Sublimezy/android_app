package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.SuccessBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.IndentContentBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.pay.bean.ZhiFuBaoBean;
import com.xueyiche.zjyk.xueyiche.route.DrivingRouteOverLay;
import com.xueyiche.zjyk.xueyiche.utils.AMapUtil;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PayUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/23.
 */
public class EndActivity extends BaseMapActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.ci_head)
    CircleImageView ciHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_gonghao)
    TextView tvGonghao;
    @BindView(R.id.tv_qibu)
    TextView tvQibu;
    @BindView(R.id.tv_shichangfei_title)
    TextView tvShichangfeiTitle;
    @BindView(R.id.tv_shichangfei)
    TextView tvShichangfei;
    @BindView(R.id.tv_lichengfei_title)
    TextView tvLichengfeiTitle;
    @BindView(R.id.tv_lichengfei)
    TextView tvLichengfei;
    @BindView(R.id.tv_lichengfei_title_nei)
    TextView tvLichengfeiTitleNei;
    @BindView(R.id.tv_lichengfei_nei)
    TextView tvLichengfeiNei;
    @BindView(R.id.tv_lichengfei_title_wai)
    TextView tvLichengfeiTitleWai;
    @BindView(R.id.tv_lichengfei_wai)
    TextView tvLichengfeiWai;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.tvEndPayMoney)
    TextView tvEndPayMoney;
    @BindView(R.id.rlNoPay)
    RelativeLayout rlNoPay;
    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;
    @BindView(R.id.tvDaShang)
    TextView tvDaShang;
    @BindView(R.id.tvPingJia)
    TextView tvPingJia;
    @BindView(R.id.rlPay)
    LinearLayout rlPay;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.ll_richang)
    LinearLayout llRichang;
    private String order_sn;
    private String user_mobile;

    public static void forward(Context context, String order_sn) {
        Intent intent = new Intent(context, EndActivity.class);
        intent.putExtra("order_sn", order_sn);
        context.startActivity(intent);
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("支付成功", msg)) {
            getDataFromNet();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected int initContentView() {
        return R.layout.end_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        ImmersionBar.with(this).titleBar(rlTitle).init();
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("服务结束");
        order_sn = getIntent().getStringExtra("order_sn");
        getDataFromNet();
    }

    private void getDataFromNet() {
        showProgressDialog(false);
        Map<String, String> params = new HashMap<>();
        params.put("order_sn", "" + order_sn);
        MyHttpUtils.postHttpMessage(AppUrl.orderDetails, params, IndentContentBean.class, new RequestCallBack<IndentContentBean>() {
            @Override
            public void requestSuccess(IndentContentBean json) {
                if (1 == json.getCode()) {
                    IndentContentBean.DataBean data = json.getData();
                    Glide.with(App.context).load(data.getHead_img()).error(R.mipmap.mine_head).placeholder(R.mipmap.mine_head).into(ciHead);
                    tvName.setText("" + data.getUser_name());
                    user_mobile = data.getUser_mobile();
                    tvGonghao.setText("工号：" + data.getUser_number());
                    tvQibu.setText("" + data.getQibu_price() + "元");
                    tvShichangfeiTitle.setText("时长费（共" + data.getShichang_time() + "）");
                    tvShichangfei.setText("" + data.getShichang_price() + "元");
                    tvLichengfeiTitle.setText("里程费（共" + data.getLicheng_km() + "公里）");
                    tvLichengfei.setText("" + data.getLicheng_price() + "元");
                    tvLichengfeiTitleNei.setText("区域内里程（共" + data.getNeiquyu_km() + "公里）");
                    tvLichengfeiNei.setText("" + data.getLicheng_price() + "元");
                    tvLichengfeiTitleWai.setText("区域外里程（共" + data.getWaiquyu_km() + "公里）");
                    tvLichengfeiWai.setText("" + data.getWaiquyu_km_price() + "元");
                    tvYouhui.setText("" + data.getYouhui_price() + "元");
                    tvEndPayMoney.setText("实付金额：" + data.getTotal_price() + "元 立即支付");
                    tvAllMoney.setText("实付：" + data.getTotal_price() + "元");
                    new GDLocation().startLocation();
                    String lat = PrefUtils.getParameter("lat");
                    String lon = PrefUtils.getParameter("lon");
                    String end_address_lat = data.getEnd_address_lat();
                    String end_address_lng = data.getEnd_address_lng();
                    LatLonPoint mStartPoint = new LatLonPoint(Double.parseDouble(lat), Double.parseDouble(lon));
                    LatLonPoint mEndPoint = new LatLonPoint(Double.parseDouble(end_address_lat), Double.parseDouble(end_address_lng));
                    setfromandtoMarker(aMap, mStartPoint, mEndPoint);
                    searchRouteResult(2, RouteSearch.DrivingDefault, mStartPoint, mEndPoint);
                    int order_status = data.getOrder_status();
                    switch (order_status) {
                        case 4:
                            //未支付
                            rlNoPay.setVisibility(View.VISIBLE);
                            rlPay.setVisibility(View.GONE);
                            break;
                        case 5:
                            //已支付
                            rlPay.setVisibility(View.VISIBLE);
                            rlNoPay.setVisibility(View.GONE);
                            tvPingJia.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            //已评价
                            rlPay.setVisibility(View.VISIBLE);
                            rlNoPay.setVisibility(View.GONE);
                            tvPingJia.setVisibility(View.GONE);
                            break;
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                stopProgressDialog();
            }
        });


    }

    private void showPingJia() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.pingjia_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        final RatingBar rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        rb_star.setRating(5f);
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
        params.width = screenWidth - 400;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rating = (int) rb_star.getRating();
                Map<String, String> map = new HashMap<>();
                map.put("order_sn", ""+order_sn);
                map.put("pingjia_rank", ""+rating);
                MyHttpUtils.postHttpMessage(AppUrl.orderAssess, map, SuccessBean.class, new RequestCallBack<SuccessBean>() {
                    @Override
                    public void requestSuccess(SuccessBean json) {
                        if (1==json.getCode()) {
                            dialog01.dismiss();
                            getDataFromNet();
                        }
                        ToastUtils.showToast(EndActivity.this,""+json.getMsg());
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {

                    }
                });

            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();


//


            }
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        aMap.clear();// 清理地图上的所有覆盖物
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
                            App.context, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                }
            }
        }
    }

    @OnClick({R.id.iv_anquan, R.id.ll_common_back, R.id.now_location, R.id.tvCallPhone, R.id.tvEndPayMoney, R.id.rlNoPay, R.id.tvDaShang, R.id.tvPingJia})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.iv_anquan:
                AppUtils.showAnQuan(EndActivity.this);
                break;
            case R.id.now_location:
                userLocation();
                break;
            case R.id.tvCallPhone:
                XueYiCheUtils.CallPhone(EndActivity.this, "拨打电话？", "" + user_mobile);
                break;
            case R.id.tvEndPayMoney:
//                PayUtils.JiaPay(order_sn, "5");
//                PrefUtils.putInt(App.context, "start_time", 0);
               PayUtils.showPopupWindow(EndActivity.this,order_sn,"daijia");
                break;
            case R.id.tvDaShang:
                DaShangActivity.forward(EndActivity.this, order_sn);
                break;
            case R.id.tvPingJia:
                showPingJia();
                break;
        }
    }


}
