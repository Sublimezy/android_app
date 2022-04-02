package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.bean.ChangYongListBean;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by ZL on 2019/10/12.
 */
public class ConstantlyAddressActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack, ll_home, ll_company;
    private TextView tvTitle, tvBaocun, tv_jia_address, tv_gongsi_address, tv_jia, tv_gongsi;
    private String user_id;
    private String address_jia;
    private String address_gs;
    private String name_gs;
    private String latitude_gs;
    private String longitude_gs;
    private String name_jia;
    private String latitude_jia;
    private String longitude_jia;
    private String homeType = "";
    private String gongsiType = "";
    private String url = "";
    private int idHome;
    private int idGongsi;

    @Override
    protected int initContentView() {
        return R.layout.constantly_address_activity;
    }

    @Override
    protected void initView() {
        llBack = view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tvTitle = view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        tvBaocun = view.findViewById(R.id.title_include).findViewById(R.id.tv_wenxintishi);
        ll_home = view.findViewById(R.id.ll_home);
        ll_company = view.findViewById(R.id.ll_company);
        tv_jia = view.findViewById(R.id.tv_jia);
        tv_jia_address = view.findViewById(R.id.tv_jia_address);
        tv_gongsi = view.findViewById(R.id.tv_gongsi);
        tv_gongsi_address = view.findViewById(R.id.tv_gongsi_address);
        user_id = PrefUtils.getString(this, "user_id", "");
        getAddress();
    }

    public void getAddress() {
        String id = LoginUtils.getId(this);
        OkHttpUtils.post().url(AppUrl.ChangYong_Address)

                .addParams("device_id",id)
                .addParams("user_id", user_id)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            ChangYongListBean changyong = JsonUtil.parseJsonToBean(string, ChangYongListBean.class);
                            if (changyong != null) {
                                int code = changyong.getCode();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        final ChangYongListBean.ContentBean content = changyong.getContent();
                                        if (content != null) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ChangYongListBean.ContentBean.MapHomeBean mapHome = content.getMapHome();
                                                    ChangYongListBean.ContentBean.MapCompanyBean mapCompany = content.getMapCompany();
                                                    if (mapHome != null) {
                                                        String name_jia = mapHome.getName();
                                                        String address_jia = mapHome.getAddress();
                                                        String latitude_jia = mapHome.getLatitude();
                                                        String longitude_jia = mapHome.getLongitude();
                                                        idHome = mapHome.getId();
                                                        if (TextUtils.isEmpty(name_jia) || TextUtils.isEmpty(address_jia) || TextUtils.isEmpty(latitude_jia) || TextUtils.isEmpty(longitude_jia)) {
                                                            tv_jia.setText("添加");
                                                            tv_jia_address.setText("设置常用地址");
                                                            homeType = "0";
                                                            url = AppUrl.Add_ChangYong_Address;

                                                        } else {
                                                            tv_jia.setText("家");
                                                            tv_jia_address.setText(address_jia);
                                                            homeType = "1";
                                                            url = AppUrl.Update_ChangYong_Address;
                                                        }
                                                    }
                                                    if (mapCompany != null) {
                                                        String name_gs = mapCompany.getName();
                                                        String address_gs = mapCompany.getAddress();
                                                        String latitude_gs = mapCompany.getLatitude();
                                                        String longitude_gs = mapCompany.getLongitude();
                                                        idGongsi = mapCompany.getId();
                                                        if (TextUtils.isEmpty(name_gs) || TextUtils.isEmpty(address_gs) || TextUtils.isEmpty(latitude_gs) || TextUtils.isEmpty(longitude_gs)) {
                                                            tv_gongsi.setText("添加");
                                                            tv_gongsi_address.setText("设置常用地址");
                                                            gongsiType = "0";
                                                            url = AppUrl.Add_ChangYong_Address;
                                                        } else {
                                                            tv_gongsi.setText("公司");
                                                            tv_gongsi_address.setText(address_gs);
                                                            gongsiType = "1";
                                                            url = AppUrl.Update_ChangYong_Address;
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
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
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tvBaocun.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_company.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("常用地址");
        tvBaocun.setText("保存");
        tvBaocun.setVisibility(View.VISIBLE);
        tvBaocun.setTextColor(getResources().getColor(R.color.test_color));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.ll_home:
//                Intent intent3 = new Intent(this, ChangYongMuDiActivity.class);
//                intent3.putExtra("type", "jia");
//                startActivityForResult(intent3, 333);
                break;
            case R.id.ll_company:
                //添加常用地址2
//                Intent intent4 = new Intent(this, ChangYongMuDiActivity.class);
//                intent4.putExtra("type", "gs");
//                startActivityForResult(intent4, 444);
                break;
            case R.id.tv_wenxintishi:
                if (!TextUtils.isEmpty(name_jia) &&!TextUtils.isEmpty(address_jia) && !TextUtils.isEmpty(latitude_jia)
                        && !TextUtils.isEmpty(longitude_jia)) {
                    if ("0".equals(homeType)) {
                        sedData(AppUrl.Add_ChangYong_Address,name_jia, address_jia, longitude_jia,latitude_jia, "1");
                    }else {
                        upDate(AppUrl.Update_ChangYong_Address,""+idHome,name_jia, address_jia, longitude_jia,latitude_jia, "1");
                    }
                }
                if (!TextUtils.isEmpty(name_gs) && !TextUtils.isEmpty(address_gs) &&
                        !TextUtils.isEmpty(latitude_gs) && !TextUtils.isEmpty(longitude_gs)) {
                    if ("0".equals(gongsiType)) {
                        sedData(AppUrl.Add_ChangYong_Address,name_gs, address_gs, longitude_gs,latitude_gs , "2");
                    }else {
                        upDate(AppUrl.Update_ChangYong_Address,""+idGongsi,name_gs, address_gs, longitude_gs,latitude_gs , "2");
                    }

                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 333:
                name_jia = extras.getString("name");
                address_jia = extras.getString("address");
                latitude_jia = extras.getString("latitude");
                longitude_jia = extras.getString("longitude");
                if (!TextUtils.isEmpty(name_jia) &&!TextUtils.isEmpty(address_jia) && !TextUtils.isEmpty(latitude_jia)
                        && !TextUtils.isEmpty(longitude_jia)) {
                    tv_jia.setText("家");
                    tv_jia_address.setText(address_jia);
                }
                break;
            case 444:
                name_gs = extras.getString("name");
                address_gs = extras.getString("address");
                latitude_gs = extras.getString("latitude");
                longitude_gs = extras.getString("longitude");
                if (!TextUtils.isEmpty(name_gs) && !TextUtils.isEmpty(address_gs) &&
                        !TextUtils.isEmpty(latitude_gs) && !TextUtils.isEmpty(longitude_gs)) {
                    tv_gongsi.setText("公司");
                    tv_gongsi_address.setText(address_gs);
                }
                break;

        }
    }

    private void sedData(String postUrl,String name, String address, String longitude, String latitude, final String type) {
        showProgressDialog(false);
        OkHttpUtils.post()
                .url(postUrl)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .addParams("name", name)
                .addParams("address", address)
                .addParams("longitude", longitude)
                .addParams("latitude", latitude)
                .addParams("type", type)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean != null) {
                                int code = successDisCoverBackBean.getCode();
                                final String msg = successDisCoverBackBean.getMsg();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                                Toast.makeText(ConstantlyAddressActivity.this,msg,Toast.LENGTH_SHORT).show();
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
                        stopProgressDialog();
                    }

                    @Override
                    public void onResponse(Object response) {
                        stopProgressDialog();
                    }
                });
    }

    private void upDate(String postUrl,String id,String name, String address, String longitude, String latitude, final String type) {
        showProgressDialog(false);
        OkHttpUtils.post()
                .url(postUrl)
                .addParams("id",id )
                .addParams("device_id",LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .addParams("name", name)
                .addParams("address", address)
                .addParams("longitude", longitude)
                .addParams("latitude", latitude)
                .addParams("type", type)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean != null) {
                                int code = successDisCoverBackBean.getCode();
                                final String msg = successDisCoverBackBean.getMsg();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                                Toast.makeText(ConstantlyAddressActivity.this,msg,Toast.LENGTH_SHORT).show();
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
                        stopProgressDialog();
                    }

                    @Override
                    public void onResponse(Object response) {
                        stopProgressDialog();
                    }
                });
    }
}
