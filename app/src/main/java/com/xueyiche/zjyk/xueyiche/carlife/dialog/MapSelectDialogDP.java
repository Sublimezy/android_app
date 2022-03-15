package com.xueyiche.zjyk.xueyiche.carlife.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.net.URISyntaxException;

/**
 * Created by Owner on 2016/12/27.
 */

public class MapSelectDialogDP extends Dialog implements View.OnClickListener {

    private Activity mActivity;

    // 起点坐标 113.40694,23.049772 百度坐标
    private String nowLat;
    private String nowLng;

    // 目的坐标  113.32983,23.112109

    private String desLat;
    private String desLng;
    private String desAddress;
    private String title;
    private LinearLayout ll_baidu;
    private LinearLayout ll_gaode;
    private TextView tv_title;

    public MapSelectDialogDP(Activity activity, int testDialog, String latitude, String longitude,
                             String address, String x, String y, String title) {
        super(activity, testDialog);
        mActivity = activity;
        this.desAddress = address;
        this.desLat = latitude;
        this.nowLat = y;
        this.desLng = longitude;
        this.nowLng = x;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_dialog, null);
        setContentView(view);
        ll_baidu = (LinearLayout) view.findViewById(R.id.ll_baidu);
        ll_gaode = (LinearLayout) view.findViewById(R.id.ll_gaode);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        ll_gaode.setOnClickListener(this);
        ll_baidu.setOnClickListener(this);
    }

    private void selectBaidu() {
        this.dismiss();
        try {
            //调起App
            if (XueYiCheUtils.isAvilible(App.context, "com.baidu.BaiduMap")) {
                Intent intent = Intent.getIntent(
                        "intent://map/marker?location="
                                + desLat
                                + ","
                                + desLng
                                + "&title="
                                + desAddress
                                + "&content="
                                + title
                                + "&src＝southwest#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end"
                );
                mActivity.startActivity(intent);
            } else {
                Toast.makeText(mActivity, "如果您没有安装百度地图APP，" +
                        "可能无法正常使用导航，建议选择其他地图", Toast.LENGTH_SHORT).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void selectGaode() {
        this.dismiss();
        double desLataa = Double.parseDouble(desLat);
        double desLngaa = Double.parseDouble(desLng);
        double[] doubles = XueYiCheUtils.map_bd2hx(desLataa, desLngaa);

        if (XueYiCheUtils.isAvilible(App.context, "com.autonavi.minimap")) {
            try {

                Intent intentOther = new Intent("android.intent.action.VIEW",
                        Uri.parse("androidamap://navi?sourceApplication=amap&lat="
                                + doubles[0] + "&lon=" + doubles[1] + "&dev=1&stype=0"));
                intentOther.setPackage("com.autonavi.minimap");
                intentOther.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mActivity.startActivity(intentOther);
            } catch (Exception e) {

            }

        } else {
            Toast.makeText(App.context,"没有安装高德地图客户端",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_baidu:
                selectBaidu();
                break;
            case R.id.ll_gaode:
                selectGaode();
                break;
        }
    }
}