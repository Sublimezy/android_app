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
import com.xueyiche.zjyk.jiakao.message.MessageFragment;
import com.xueyiche.zjyk.jiakao.mine.fragments.MineFragment;
import com.xueyiche.zjyk.jiakao.utils.AppUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private long exitTime;
    //    FragmentTransaction对象是用来管理Fragment的变化，例如添加、替换、移除Fragment以及设置动画等。主要的操作包括：
//
//添加（Add）：将一个新的Fragment添加到Activity中。
//替换（Replace）：替换当前Activity中的某个Fragment。
//移除（Remove）：移除当前Activity中的某个Fragment。
//显示（Show）和隐藏（Hide）：显示或隐藏Fragment，而不是直接移除。
//附加（Attach）和分离（Detach）：将Fragment附加到Activity或分离出来，但并不移除。
//设置动画（Set custom animations）：为Fragment事务设置进入和退出时的动画效果。
    private FragmentTransaction mTransaction;
    private ExamFragment homeFragment;
    private MineFragment meFragment;
    private MessageFragment messageFragment;

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

            case 2:
                if (messageFragment == null) {
                    messageFragment = MessageFragment.newInstance("message");
                    mTransaction.add(R.id.navigation_content, messageFragment);
                } else {
                    mTransaction.show(messageFragment);
                }
                break;


        }
        mTransaction.commit();
    }

    //隐藏所有fragment
    private void hideFragments() {
        hideFragments(meFragment);
        hideFragments(homeFragment);
        hideFragments(messageFragment);
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


    @OnClick({R.id.rb_home, R.id.rb_message, R.id.rb_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                changeFragment(0);
                break;
            case R.id.rb_my:
                changeFragment(1);
                break;
            case R.id.rb_message:
                changeFragment(2);
                break;
        }
    }
}
