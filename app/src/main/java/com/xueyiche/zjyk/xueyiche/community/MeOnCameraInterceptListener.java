package com.xueyiche.zjyk.xueyiche.community;

import android.content.Context;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.luck.lib.camerax.CameraImageEngine;
import com.luck.lib.camerax.SimpleCameraX;
import com.luck.picture.lib.interfaces.OnCameraInterceptListener;

/**
 * @Package: com.example.yjf.tata.faxian
 * @ClassName: MeOnCameraInterceptListener
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/3/31 14:07
 */
public class MeOnCameraInterceptListener implements OnCameraInterceptListener {

    @Override
    public void openCamera(Fragment fragment, int cameraMode, int requestCode) {
        SimpleCameraX camera = SimpleCameraX.of();
        camera.setCameraMode(cameraMode);
        camera.setVideoFrameRate(25);
        camera.setVideoBitRate(3 * 1024 * 1024);
        camera.isDisplayRecordChangeTime(true);
        camera.isManualFocusCameraPreview(true);//支持手指点击对焦
        camera.isZoomCameraPreview(true);//支持手指缩放相机
//        camera.setOutputPathDir(getSandboxCameraOutputPath());
        camera.setPermissionDeniedListener(new MeOnSimpleXPermissionDeniedListener());
//        camera.setPermissionDescriptionListener(new MeOnSimpleXPermissionDescriptionListener());
        camera.setImageEngine(new CameraImageEngine() {
            @Override
            public void loadImage(Context context, String url, ImageView imageView) {
                Glide.with(context).load(url).into(imageView);
            }
        });
        camera.start(fragment.getActivity(), fragment, requestCode);
    }
}
