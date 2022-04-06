package com.xueyiche.zjyk.xueyiche.main.activities.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.community.CommunityFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.HomeFragment;
import com.xueyiche.zjyk.xueyiche.mine.fragments.MineFragment;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.receive.LocationService;
import com.xueyiche.zjyk.xueyiche.receive.UpLocationService;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.welfare.WelfareFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Owner on 2016/9/13.
 */
public class MainActivity extends BaseActivity {
    private long exitTime;
    private FragmentTransaction mTransaction;
    private HomeFragment homeFragment;
    private CommunityFragment communityFragment;
    private WelfareFragment welfareFragment;
    private MineFragment meFragment;

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initData();
        String szImei = App.szImei;
        if (TextUtils.isEmpty(szImei)) {
            Intent bindintent = new Intent(this, LocationService.class);
            startService(bindintent);
        }
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        changeFragment(0);
    }

    //切换页面
    private void changeFragment(int index) {
        FragmentManager mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        hideFragments();//隐藏所有fragment
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance("home");
                    mTransaction.add(R.id.navigation_content, homeFragment);
                } else {
                    mTransaction.show(homeFragment);
                }

                break;
            case 1:
                if (communityFragment == null) {
                    communityFragment = CommunityFragment.newInstance("community");
                    mTransaction.add(R.id.navigation_content, communityFragment);
                } else {
                    mTransaction.show(communityFragment);
                }
                break;
            case 2:
                if (welfareFragment == null) {
                    welfareFragment = WelfareFragment.newInstance("welfare");
                    mTransaction.add(R.id.navigation_content, welfareFragment);
                } else {
                    mTransaction.show(welfareFragment);
                }
                break;
            case 3:
                if (meFragment == null) {
                    meFragment = MineFragment.newInstance("mine");
                    mTransaction.add(R.id.navigation_content, meFragment);
                } else {
                    mTransaction.show(meFragment);
                }
                break;
        }
        mTransaction.commit();
    }

    //隐藏所有fragment
    private void hideFragments() {
        hideFragments(meFragment);
        hideFragments(homeFragment);
        hideFragments(communityFragment);
        hideFragments(welfareFragment);
    }

    //隐藏
    private void hideFragments(Fragment fragment) {
        if (fragment != null) {
            mTransaction.hide(fragment);
        }
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再点击一次退出学易车", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            Intent ser = new Intent(this, UpLocationService.class);
            stopService(ser);
            AppUtils.AppExit(MainActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLonLatUser();
    }

    private void updateLonLatUser() {
        String latitude = PrefUtils.getParameter("y");
        String longitude = PrefUtils.getParameter("x");
        Map<String, String> params = new HashMap<>();
        params.put("user_id", PrefUtils.getParameter("user_id"));
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        MyHttpUtils.postHttpMessage(AppUrl.updatelonlatuser, params, BaseBean.class, new RequestCallBack<BaseBean>() {
            @Override
            public void requestSuccess(BaseBean json) {

            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        Intent bindintent = new Intent(this, LocationService.class);
        stopService(bindintent);
        super.onDestroy();
    }

    @OnClick({R.id.rb_home, R.id.rb_community, R.id.rb_welfare, R.id.rb_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                changeFragment(0);
                break;
            case R.id.rb_community:
                changeFragment(1);
                break;
            case R.id.rb_welfare:
                changeFragment(2);
                break;
            case R.id.rb_my:
                changeFragment(3);
                break;
        }
    }
}
