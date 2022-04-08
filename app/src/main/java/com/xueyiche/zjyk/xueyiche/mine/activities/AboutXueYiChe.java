package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;

/**
 * Created by zhanglei on 2016/10/29.
 */
public class AboutXueYiChe extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle;
    private TextView tv_version;
    private TextView tv_fuwu;
    private TextView tv_yinsi;

    @Override
    protected int initContentView() {
        return R.layout.about_xue_yi_che;
    }

    @Override
    protected void initView() {

        llBack = (LinearLayout) view.findViewById(R.id.about_xue_include).findViewById(R.id.ll_exam_back);
        llBack.setOnClickListener(this);
        tvTitle = (TextView) view.findViewById(R.id.about_xue_include).findViewById(R.id.tv_login_back);
        tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_fuwu = (TextView) view.findViewById(R.id.tv_fuwu);
        tv_yinsi = (TextView) view.findViewById(R.id.tv_yinsi);

        ImmersionBar.with(this).titleBar(R.id.rl_title).init();
    }

    @Override
    protected void initListener() {
        tv_fuwu.setOnClickListener(this);
        tv_yinsi.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("关于学易车");
        try {
            version();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_fuwu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_yinsi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_yinsi.getPaint().setAntiAlias(true);//抗锯齿
        tv_fuwu.getPaint().setAntiAlias(true);//抗锯齿
    }

    private void version() throws Exception {
        String versionName = getVersionName();
        tv_version.setText("v" + versionName);
    }

    //获取App版本号
    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AboutXueYiChe.this,UrlActivity.class);
        intent.putExtra("type", "2");
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_yinsi:
                intent.putExtra("url", "http://www.xueyiche.cn/xyc/Agreement/yszc.html");
                startActivity(intent);
                break;
            case R.id.tv_fuwu:
                intent.putExtra("url", "http://xueyiche.cn/xyc/Agreement/fwxy.html");
                startActivity(intent);
                break;
        }
    }
}
