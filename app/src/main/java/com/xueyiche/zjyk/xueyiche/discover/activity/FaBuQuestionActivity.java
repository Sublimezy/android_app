package com.xueyiche.zjyk.xueyiche.discover.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.TokenBean;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PhotoUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by ZL on 2018/8/13.
 */
public class FaBuQuestionActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle, tv_fabu;
    private EditText et_question, et_question_miaoshu;
    private ImageView iv_pic_one,iv_pic_two,iv_pic_three;
    private ImageView iv_pic_one_qx,iv_pic_two_qx,iv_pic_three_qx;
    LinearLayout ll_popup;
    private PopupWindow pop;
    private Uri imageUri;
    private Uri cropImageUri;
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private UploadManager uploadManager;
    private Bitmap bitmap;
    private String key,tupian;
    private String url_image1 = "",url_image2 = "",url_image3 = "";

    @Override
    protected int initContentView() {
        return R.layout.fabu_question_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.question_fabu_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.question_fabu_include).findViewById(R.id.tv_login_back);
        et_question = (EditText) view.findViewById(R.id.et_question);
        et_question_miaoshu = (EditText) view.findViewById(R.id.et_question_miaoshu);
        tv_fabu = (TextView) view.findViewById(R.id.tv_fabu);
        iv_pic_one = (ImageView) view.findViewById(R.id.iv_pic_one);
        iv_pic_two = (ImageView) view.findViewById(R.id.iv_pic_two);
        iv_pic_three = (ImageView) view.findViewById(R.id.iv_pic_three);
        iv_pic_one_qx = (ImageView) view.findViewById(R.id.iv_pic_one_qx);
        iv_pic_two_qx = (ImageView) view.findViewById(R.id.iv_pic_two_qx);
        iv_pic_three_qx = (ImageView) view.findViewById(R.id.iv_pic_three_qx);
        uploadManager = new UploadManager();
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        iv_pic_one.setOnClickListener(this);
        iv_pic_two.setOnClickListener(this);
        iv_pic_three.setOnClickListener(this);
        iv_pic_one_qx.setOnClickListener(this);
        iv_pic_two_qx.setOnClickListener(this);
        iv_pic_three_qx.setOnClickListener(this);
        tv_fabu.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("发布");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_pic_one_qx:
                iv_pic_one.setImageResource(R.mipmap.wd_fabu_pic);
                iv_pic_one_qx.setVisibility(View.GONE);
                url_image1 = "";
                break;
            case R.id.iv_pic_two_qx:
                iv_pic_two.setImageResource(R.mipmap.wd_fabu_pic);
                iv_pic_two_qx.setVisibility(View.GONE);
                url_image2 = "";
                break;
            case R.id.iv_pic_three_qx:
                iv_pic_three.setImageResource(R.mipmap.wd_fabu_pic);
                iv_pic_three_qx.setVisibility(View.GONE);
                url_image3 = "";
                break;
            case R.id.iv_pic_one:
                tupian = "1";
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        FaBuQuestionActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_pic_two:
                tupian = "2";
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        FaBuQuestionActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_pic_three:
                tupian = "3";
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        FaBuQuestionActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_fabu:
                String question = et_question.getText().toString();
                String content = et_question_miaoshu.getText().toString();
                if (TextUtils.isEmpty(question)) {
                    Toast.makeText(FaBuQuestionActivity.this, "请输入问题~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(FaBuQuestionActivity.this, "请输入问题描述~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (DialogUtils.IsLogin()) {
                    fabu(question,content);
                }else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;

        }
    }
    public void showPopupWindow() {
        pop = new PopupWindow(FaBuQuestionActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
                null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        TextView bt1 = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView bt2 = (TextView) view.findViewById(R.id.tv_photo_book);
        TextView bt3 = (TextView) view.findViewById(R.id.tv_exit);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                autoObtainCameraPermission();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                autoObtainStoragePermission();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }
    private void fabu(String question,String content) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(AppUrl.Question_Post)
                    .addParams("quest_user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(FaBuQuestionActivity.this))
                    .addParams("title", question)
                    .addParams("represent", content)
                    .addParams("image1", TextUtils.isEmpty(url_image1)?"":"http://xychead.xueyiche.vip/" + url_image1)
                    .addParams("image2", TextUtils.isEmpty(url_image2)?"":"http://xychead.xueyiche.vip/" + url_image2)
                    .addParams("image3", TextUtils.isEmpty(url_image3)?"":"http://xychead.xueyiche.vip/" + url_image3)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string,SuccessDisCoverBackBean.class);
                                if (successDisCoverBackBean!=null) {
                                    final int code = successDisCoverBackBean.getCode();
                                    final String msg = successDisCoverBackBean.getMsg();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200==code) {
                                                Toast.makeText(FaBuQuestionActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    });
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

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                showToastShort("您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (XueYiCheUtils.hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(FaBuQuestionActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                showToastShort("设备没有SD卡！");
            }
        }
    }

    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (XueYiCheUtils.hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(FaBuQuestionActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        showToastShort("设备没有SD卡！");
                    }
                } else {
                    showToastShort("请允许打开相机！！");
                }
                break;


            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    showToastShort("请允许打操作SDCard！！");
                }
                break;
        }
    }

    private void getTokenFromService(final String key) {
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.TOUXIANG).addParams("key", key).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    TokenBean token_bean = JsonUtil.parseJsonToBean(string, TokenBean.class);
                    String token = token_bean.getContent().getToken();
                    if (!TextUtils.isEmpty(token)) {
                        shangchuanImage(key, token);
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

    private void shangchuanImage(String key, String token) {

        uploadManager.put(fileCropUri, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    Log.e("qiniu", "Upload Success");
                } else {
                    String error = info.error;
                    LogUtil.e("error", error);

                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }

        }, null);

    }
    private int output_X = 680;
    private int output_Y = 680;

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (XueYiCheUtils.hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, intent.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        showToastShort("设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    Calendar calendar = Calendar.getInstance();
                    long timeInMillis = calendar.getTimeInMillis();

                    if (bitmap != null) {
                        if (tupian.equals("1")) {
                            key = "xyc_wenda_" + String.valueOf(timeInMillis) + "_fabu1.jpg";
                            url_image1 = key;
                            iv_pic_one.setImageBitmap(bitmap);
                            iv_pic_one_qx.setVisibility(View.VISIBLE);
                            getTokenFromService(key);
                        } else if (tupian.equals("2")) {
                            key = "xyc_wenda_" + String.valueOf(timeInMillis) + "_fabu2.jpg";
                            url_image2 = key;
                            iv_pic_two.setImageBitmap(bitmap);
                            iv_pic_two_qx.setVisibility(View.VISIBLE);
                            getTokenFromService(key);
                        } else if (tupian.equals("3")) {
                            key = "xyc_wenda_" + String.valueOf(timeInMillis) + "_fabu3.jpg";
                            url_image3 = key;
                            iv_pic_three.setImageBitmap(bitmap);
                            iv_pic_three_qx.setVisibility(View.VISIBLE);
                            getTokenFromService(key);
                        }
                    }
                    break;
            }
        }


    }
}
