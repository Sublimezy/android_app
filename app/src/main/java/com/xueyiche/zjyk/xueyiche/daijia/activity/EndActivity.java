package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.route.DriveRouteResult;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.module.BaseMapActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Administrator on 2019/9/23.
 */
public class EndActivity extends BaseMapActivity {

    public static void forward(Context context, String order_sn) {
        Intent intent = new Intent(context, EndActivity.class);
        intent.putExtra("order_sn",order_sn);
        context.startActivity(intent);
    }

    @Override
    protected int initContentView() {
        return R.layout.end_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {


    }



//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//            case R.id.now_location:
//
//                break;
//            case R.id.iv_call:
//                //打电话
//                if (!TextUtils.isEmpty(driver_phone)) {
//                    if (driver_phone.length() != 11) {
//                        String decrypt = AES.decrypt(driver_phone);
//                        XueYiCheUtils.CallPhone(this, "拨打代驾电话", decrypt);
//                    } else {
//                        XueYiCheUtils.CallPhone(this, "拨打代驾电话", driver_phone);
//                    }
//                }
//                break;
//            case R.id.iv_anquan:
//                showAnQuan();
//                break;
//            case R.id.tv_pingjia:
//                //去评价
//                if ("4".equals(order_status)) {
//                    showPingJia();
//                }
//                break;
//        }
//    }


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

                dialog01.dismiss();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }
}
