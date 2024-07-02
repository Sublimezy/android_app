package com.gxuwz.zy.mine.activities.bianji;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gxuwz.zy.R;
import com.gxuwz.zy.base.BaseActivity;
import com.gxuwz.zy.constants.AppUrl;
import com.gxuwz.zy.constants.bean.TokenBean;
import com.gxuwz.zy.mine.GridImageAdapter2;
import com.gxuwz.zy.mine.ImageLoaderUtils;
import com.gxuwz.zy.mine.OnItemClickListener;
import com.gxuwz.zy.mine.activities.FullyGridLayoutManager;
import com.gxuwz.zy.mine.activities.GlideEngine;
import com.gxuwz.zy.mine.entity.dos.BaseResponseBean;
import com.gxuwz.zy.myhttp.MyHttpUtils;
import com.gxuwz.zy.myhttp.RequestCallBack;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CropEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.permissionx.guolindev.PermissionX;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;
import com.yanzhenjie.permission.Permission;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditMySelfInfoActivity extends BaseActivity {


    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.ll_common_back)
    LinearLayout llCommonBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_btn)
    TextView tvRightBtn;
    @BindView(R.id.iv_caidan)
    ImageView ivCaidan;
    @BindView(R.id.title_view_heng)
    View titleViewHeng;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recycler_mianguan)
    RecyclerView recyclerMianguan;
    @BindView(R.id.et_nackname)
    EditText etNackname;
    @BindView(R.id.bt_baocun)
    Button btBaocun;
    private PictureSelectorStyle selectorStyle;
    private final int CodeMianGuan = 101;
    private String head;
    private UploadManager uploadManager;

    @Override
    protected int initContentView() {
        return R.layout.activity_edit_my_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).init();
        tvTitle.setText("修改信息");
        selectorStyle = new PictureSelectorStyle();
        PictureWindowAnimationStyle defaultAnimationStyle = new PictureWindowAnimationStyle();
        defaultAnimationStyle.setActivityEnterAnimation(R.anim.ps_anim_enter);
        defaultAnimationStyle.setActivityExitAnimation(R.anim.ps_anim_exit);
        selectorStyle.setWindowAnimationStyle(defaultAnimationStyle);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String name = getIntent().getStringExtra("name");
        head = getIntent().getStringExtra("head");
        etNackname.setText(name);
        initRecycler();
        ArrayList<LocalMedia> list = new ArrayList<>();
        LocalMedia data = new LocalMedia();
        data.setPath(head);
        data.setCut(true);
        data.setCutPath(head);
        list.add(data);
        mAdapterMianGuan.setList(list);
    }

    private GridImageAdapter2 mAdapterMianGuan;

    private void initRecycler() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);

        recyclerMianguan.setLayoutManager(manager);
        mAdapterMianGuan = new GridImageAdapter2(EditMySelfInfoActivity.this, new GridImageAdapter2.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                PermissionX.init(EditMySelfInfoActivity.this).permissions(
                                Permission.CAMERA,
                                Permission.READ_EXTERNAL_STORAGE
                        )
                        .explainReasonBeforeRequest()
                        .onExplainRequestReason((scope, deniedList) ->
                                scope.showRequestReasonDialog(deniedList, "为了更好的使用修改头像,需要申请驾考APPAPP相机和相册权限。您可以通过系统\"设置\"进行权限管理。", "允许", "拒绝")
                        )
                        .onForwardToSettings((scope, deniedList) ->
                                scope.showForwardToSettingsDialog(deniedList, "去设置中获取使用权限", "允许", "拒绝")
                        )
                        .request((allGranted, grantedList, deniedList) ->
                                {
                                    getPictureSelectionModel().forResult(CodeMianGuan);
                                }
                        );

            }
        });
        mAdapterMianGuan.setSelectMax(1);
        recyclerMianguan.setAdapter(mAdapterMianGuan);
        mAdapterMianGuan.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterMianGuan.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, mAdapterMianGuan);
                }
            }
        });
    }


    @OnClick({R.id.ll_common_back, R.id.bt_baocun})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.bt_baocun:
                String trim = etNackname.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    showToastShort("请输入昵称");
                    return;
                }
                if (mAdapterMianGuan.getData().size() == 0) {
                    showToastShort("请选择头像");
                    return;
                }
                showProgressDialog(false);
                if (!mAdapterMianGuan.getData().get(0).getCutPath().equals(head)) {
                    //走正常上传逻辑  先上传头像
                    uploadManager = new UploadManager();
                    Calendar calendar = Calendar.getInstance();
                    long timeInMillis = calendar.getTimeInMillis();
                    UpLoadToQiNiu("xyc_head" + timeInMillis + ".jpg", mAdapterMianGuan.getData().get(0));
                } else {
                    //头像没修改过  不变 直接上传
                    Map<String, String> params = new HashMap<>();
                    params.put("nickname", trim);
                    params.put("avatar", head);
                    MyHttpUtils.postHttpMessage(AppUrl.userInfoEdit, params, BaseResponseBean.class, new RequestCallBack<BaseResponseBean>() {
                        @Override
                        public void requestSuccess(BaseResponseBean json) {
                            stopProgressDialog();
                            if (json.getCode() == 1) {
                                finish();
                            } else {

                            }
                            showToastShort(json.getMsg());
                        }

                        @Override
                        public void requestError(String errorMsg, int errorType) {

                            stopProgressDialog();
                        }
                    });
                }
                break;
        }
    }

    private void UpLoadToQiNiu(final String key, LocalMedia media) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        MyHttpUtils.postHttpMessage(AppUrl.TOUXIANG, params, TokenBean.class, new RequestCallBack<TokenBean>() {
            @Override
            public void requestSuccess(TokenBean json) {
                if (json.getCode() == 200) {
                    String token = json.getContent().getToken();
                    if (!TextUtils.isEmpty(token)) {
                        File fileCropUri;
//                        if (TextUtils.isEmpty(media.getCompressPath())) {
//                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//                                fileCropUri = new File(media.getSandboxPath());
//                            } else {
//                                fileCropUri = new File(media.getRealPath());
//                            }
//                        } else {
//                            fileCropUri = new File(media.getCompressPath());
//                        }
                        fileCropUri = new File(media.getCutPath());

                        uploadManager.put(fileCropUri, key, token, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("nickname", etNackname.getText().toString().trim());
                                    MyHttpUtils.postHttpMessage(AppUrl.userInfoEdit, params, BaseResponseBean.class, new RequestCallBack<BaseResponseBean>() {
                                        @Override
                                        public void requestSuccess(BaseResponseBean json) {
                                            stopProgressDialog();
                                            if (json.getCode() == 1) {
                                                finish();
                                            } else {

                                            }
                                            showToastShort(json.getMsg());
                                        }

                                        @Override
                                        public void requestError(String errorMsg, int errorType) {

                                            stopProgressDialog();
                                        }
                                    });

                                } else {
                                    stopProgressDialog();
                                    showToastShort("上传失败,请重新尝试!");
                                }


                            }
                        }, null);
                    }
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                stopProgressDialog();
                showToastShort("连接服务器失败");
            }
        });
    }

    private PictureSelectionModel getPictureSelectionModel() {

        return PictureSelector.create(EditMySelfInfoActivity.this)
                .openGallery(SelectMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .setImageEngine(GlideEngine.createGlideEngine())
                .setSelectorUIStyle(selectorStyle)// 动态自定义相册主题
                .setMaxSelectNum(1)// 最大图片选择数量
                .setMinSelectNum(1)// 最小选择数量
                .setImageSpanCount(4)// 每行显示个数
                .isPreviewImage(true) // 是否支持预览图片
                .isPreviewVideo(true) // 是否支持预览视频
                .isDisplayCamera(false)// 是否显示拍照按钮
                .setCropEngine(new ImageCropEngine()) // 是否裁剪 true or false
                /* .setCompressEngine(new ImageCompressEngine())*/;
    }

    /**
     * 预览图片
     *
     * @param position
     * @param mAdapter
     */
    private void previewPicture(int position, GridImageAdapter2 mAdapter) {
        PictureSelector.create(EditMySelfInfoActivity.this)
                .openPreview()
                .setImageEngine(GlideEngine.createGlideEngine())
                .setSelectorUIStyle(selectorStyle)
                .isPreviewFullScreenMode(true)
                .setExternalPreviewEventListener(new MyExternalPreviewEventListener(mAdapter))
                .startActivityPreview(position, true, mAdapter.getData());
    }

    /**
     * 外部预览监听事件
     */
    private class MyExternalPreviewEventListener implements OnExternalPreviewEventListener {
        private final GridImageAdapter2 adapter;

        public MyExternalPreviewEventListener(GridImageAdapter2 adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onPreviewDelete(int position) {
            head = "";
            adapter.remove(position);
            adapter.notifyItemRemoved(position);
        }

        @Override
        public boolean onLongPressDownload(LocalMedia media) {
            return false;
        }
    }

    private ArrayList<LocalMedia> selectListMianGuan;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CodeMianGuan) {// 图片选择结果回调
            //     selectList = mAdapter.getData();
            selectListMianGuan = PictureSelector.obtainSelectorList(data);
            mAdapterMianGuan.setList(selectListMianGuan);
            mAdapterMianGuan.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class ImageCropEngine implements CropEngine {

        @Override
        public void onStartCrop(Fragment fragment, LocalMedia currentLocalMedia,
                                ArrayList<LocalMedia> dataSource, int requestCode) {
            String currentCropPath = currentLocalMedia.getAvailablePath();
            Uri inputUri;
            if (PictureMimeType.isContent(currentCropPath) || PictureMimeType.isHasHttp(currentCropPath)) {
                inputUri = Uri.parse(currentCropPath);
            } else {
                inputUri = Uri.fromFile(new File(currentCropPath));
            }
            String fileName = DateUtils.getCreateFileName("CROP_") + ".jpg";
            Uri destinationUri = Uri.fromFile(new File(getSandboxPath(), fileName));
            UCrop.Options options = buildOptions();
            ArrayList<String> dataCropSource = new ArrayList<>();
            for (int i = 0; i < dataSource.size(); i++) {
                LocalMedia media = dataSource.get(i);
                dataCropSource.add(media.getAvailablePath());
            }
            UCrop uCrop = UCrop.of(inputUri, destinationUri, dataCropSource);
            //options.setMultipleCropAspectRatio(buildAspectRatios(dataSource.size()));
            uCrop.withOptions(options);
            uCrop.setImageEngine(new UCropImageEngine() {
                @Override
                public void loadImage(Context context, String url, ImageView imageView) {
                    if (!ImageLoaderUtils.assertValidRequest(context)) {
                        return;
                    }
                    Glide.with(context).load(url).override(180, 180).into(imageView);
                }

                @Override
                public void loadImage(Context context, Uri url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {
                    if (!ImageLoaderUtils.assertValidRequest(context)) {
                        return;
                    }
                    Glide.with(context).asBitmap().override(maxWidth, maxHeight).load(url).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @androidx.annotation.Nullable Transition<? super Bitmap> transition) {
                            if (call != null) {
                                call.onCall(resource);
                            }
                        }

                        @Override
                        public void onLoadFailed(@androidx.annotation.Nullable Drawable errorDrawable) {
                            if (call != null) {
                                call.onCall(null);
                            }
                        }

                        @Override
                        public void onLoadCleared(@androidx.annotation.Nullable Drawable placeholder) {
                        }
                    });
                }
            });
            uCrop.start(fragment.getActivity(), fragment, requestCode);
        }

    }

    /**
     * 创建自定义输出目录
     *
     * @return
     */
    private String getSandboxPath() {
        File externalFilesDir = getExternalFilesDir("");
        File customFile = new File(externalFilesDir.getAbsolutePath(), "Sandbox");
        if (!customFile.exists()) {
            customFile.mkdirs();
        }
        return customFile.getAbsolutePath() + File.separator;
    }

    private UCrop.Options buildOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);
        options.setShowCropFrame(true);
        options.setShowCropGrid(true);
        options.setCircleDimmedLayer(false);
        options.withAspectRatio(1, 1);
        options.setCropOutputPathDir(getSandboxPath());
        options.isCropDragSmoothToCenter(false);
        options.isUseCustomLoaderBitmap(false);
        options.setSkipCropMimeType(PictureMimeType.ofGIF(), PictureMimeType.ofWEBP());
        options.isForbidCropGifWebp(true);
        options.isForbidSkipMultipleCrop(false);
        options.setMaxScaleMultiplier(100);
        if (selectorStyle != null && selectorStyle.getSelectMainStyle().getStatusBarColor() != 0) {
            SelectMainStyle mainStyle = selectorStyle.getSelectMainStyle();
            boolean isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack();
            int statusBarColor = mainStyle.getStatusBarColor();
            options.isDarkStatusBarBlack(isDarkStatusBarBlack);
            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                options.setStatusBarColor(statusBarColor);
                options.setToolbarColor(statusBarColor);
            } else {
                options.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
                options.setToolbarColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
            }
            TitleBarStyle titleBarStyle = selectorStyle.getTitleBarStyle();
            if (StyleUtils.checkStyleValidity(titleBarStyle.getTitleTextColor())) {
                options.setToolbarWidgetColor(titleBarStyle.getTitleTextColor());
            } else {
                options.setToolbarWidgetColor(ContextCompat.getColor(getContext(), R.color.ps_color_white));
            }
        } else {
            options.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
            options.setToolbarColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
            options.setToolbarWidgetColor(ContextCompat.getColor(getContext(), R.color.ps_color_white));
        }
        return options;
    }

    public Context getContext() {
        return this;
    }

}