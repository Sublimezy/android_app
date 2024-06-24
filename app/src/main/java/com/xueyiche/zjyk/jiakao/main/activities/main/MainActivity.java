package com.xueyiche.zjyk.jiakao.main.activities.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.examtext.ExamFragment;
import com.xueyiche.zjyk.jiakao.mine.fragments.MineFragment;
import com.xueyiche.zjyk.jiakao.utils.AppUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private long exitTime;
    private FragmentTransaction mTransaction;
    private ExamFragment homeFragment;
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
                    homeFragment = ExamFragment.newInstance("home");
                    mTransaction.add(R.id.navigation_content, homeFragment);
                } else {
                    mTransaction.show(homeFragment);
                }

                break;
            case 1:
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
            Toast.makeText(this, "再点击一次退出驾考APP", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppUtils.AppExit(MainActivity.this);
        }
    }



    @OnClick({R.id.rb_home, R.id.rb_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                changeFragment(0);
                break;
            case R.id.rb_my:
                changeFragment(1);
                break;
        }
    }
}
