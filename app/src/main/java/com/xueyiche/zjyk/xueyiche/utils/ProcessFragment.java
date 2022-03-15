package com.xueyiche.zjyk.xueyiche.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.splash.ToastUtil;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 6/4/21/4:27 PM .
 * #            com.xueyiche.zjyk.xueyiche.utils
 * #            xueyiche3.0
 */
public class ProcessFragment extends Fragment {

    private Context mContext;
    private Runnable mPermissionCallback;
    private ActivityResultCallback mActivityResultCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


    /**
     * 检查是否具有多个权限
     *
     * @param permissions
     * @return true 有权限 false无权限
     */
    private boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (isAllGranted(permissions, grantResults)) {
            if (mPermissionCallback != null) {
                mPermissionCallback.run();
            }
        }
        mPermissionCallback = null;
    }


    /**
     * 判断申请的权限有没有被允许
     */
    private boolean isAllGranted(String[] permissions, int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                showTip(permissions[i]);
                return false;
            }
        }
        return true;
    }

    /**
     * 拒绝某项权限时候的提示
     */
    private void showTip(String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                com.xueyiche.zjyk.xueyiche.splash.ToastUtil.show(R.string.permission_storage_refused);
                break;
            case Manifest.permission.CAMERA:
                com.xueyiche.zjyk.xueyiche.splash.ToastUtil.show(R.string.permission_camera_refused);
                break;
            case Manifest.permission.RECORD_AUDIO:
                com.xueyiche.zjyk.xueyiche.splash.ToastUtil.show(R.string.permission_record_audio_refused);
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                com.xueyiche.zjyk.xueyiche.splash.ToastUtil.show(R.string.permission_location_refused);
                break;
            case Manifest.permission.READ_PHONE_STATE:
                com.xueyiche.zjyk.xueyiche.splash.ToastUtil.show(R.string.permission_read_phone_state_refused);
                break;
            case Manifest.permission.READ_CONTACTS:
                ToastUtil.show("您拒绝了读取手机通讯录的权限，请到设置中修改");
                break;
        }
    }

    public void requestPermissions(String[] permissions, Runnable runnable) {
        if (runnable != null) {
            if (checkPermissions(permissions)) {
                runnable.run();
            } else {
                mPermissionCallback = runnable;
                requestPermissions(permissions, 0);
            }
        }
    }

    public void startActivityForResult(Intent intent, ActivityResultCallback callback) {
        mActivityResultCallback = callback;
        super.startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mActivityResultCallback != null) {
            if (resultCode == -1) {//RESULT_OK
                mActivityResultCallback.onSuccess(data);
            } else {
                mActivityResultCallback.onFailure();
            }
        }
    }

    public void release() {
        mPermissionCallback = null;
        mActivityResultCallback = null;
    }
}
