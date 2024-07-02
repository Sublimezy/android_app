package com.gxuwz.zy.mine.activities.bianji;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseActivity;
import com.gxuwz.zy.constants.App;
import com.gxuwz.zy.constants.AppUrl;
import com.gxuwz.zy.constants.bean.TokenBean;
import com.gxuwz.zy.constants.event.MyEvent;
import com.gxuwz.zy.mine.entity.dos.HeadBean;
import com.gxuwz.zy.utils.JsonUtil;
import com.gxuwz.zy.utils.LogUtil;
import com.gxuwz.zy.utils.PhotoUtils;
import com.gxuwz.zy.utils.PrefUtils;
import com.gxuwz.zy.utils.XueYiCheUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import de.greenrobot.event.EventBus;


public class ChangeHeadActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private ImageView iv_change_head;
    private ImageView iv_head_xuanze;
    private EditText et_nackname;
    private Button bt_baocun;
    PopupWindow pop;
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private final File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private final File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    LinearLayout ll_popup;
    private UploadManager uploadManager;
    private String key;
    private boolean image_huixian = false;
    private Bitmap bitmap;
    private String user_phone;
    private String user_id;
    private String key_panduan;

    @Override
    protected int initContentView() {
        return R.layout.change_head_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = view.findViewById(R.id.change_head_include).findViewById(R.id.ll_exam_back);
        tv_login_back = view.findViewById(R.id.change_head_include).findViewById(R.id.tv_login_back);
        iv_change_head = view.findViewById(R.id.iv_change_head);
        iv_head_xuanze = view.findViewById(R.id.iv_head_xuanze);
        et_nackname = view.findViewById(R.id.et_nackname);
        bt_baocun = view.findViewById(R.id.bt_baocun);
        user_phone = PrefUtils.getString(App.context, "user_phone", "");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        uploadManager = new UploadManager();

    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        iv_head_xuanze.setOnClickListener(this);
        bt_baocun.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_login_back.setVisibility(View.INVISIBLE);
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.My_Head)
                    .addParams("user_id", user_id)
                    .build().execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            HeadBean userInfo = JsonUtil.parseJsonToBean(string, HeadBean.class);
                            if (userInfo != null) {
                                HeadBean.ContentBean content = userInfo.getContent();
                                if (content != null) {
                                    final String nickname = content.getNickname();
                                    final String head_img = content.getHead_img();
                                    PrefUtils.putString(App.context, "user_phone", user_phone);
                                    PrefUtils.putString(App.context, "nickname", nickname);
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!TextUtils.isEmpty(nickname)) {
                                                et_nackname.setText(nickname);
                                                et_nackname.setSelection(nickname.length());
                                            }

                                            if (!image_huixian) {
                                                if (!TextUtils.isEmpty(head_img)) {
                                                    Picasso.with(ChangeHeadActivity.this).load(head_img).into(iv_change_head);
                                                } else {
                                                    iv_change_head.setImageResource(R.mipmap.mine_head);
                                                }
                                            }
                                        }
                                    });
                                }

                            }
                            return userInfo;
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            stopProgressDialog();
                        }

                        @Override
                        public void onResponse(Object response) {
                            EventBus.getDefault().post(new MyEvent("刷新FragmentLogin"));
                            EventBus.getDefault().post(new MyEvent("刷新Fragment"));
                            stopProgressDialog();
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        image_huixian = false;
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_head_xuanze:
                showPopupWindow();

                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        ChangeHeadActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.bt_baocun:
                String trim = et_nackname.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    int length = trim.length();
                    if (length > 8) {
                        Toast.makeText(ChangeHeadActivity.this, "昵称过长，请输入8位以内的昵称！", Toast.LENGTH_SHORT).show();
                    } else {
                        key_panduan = PrefUtils.getString(App.context, "key_panduan", "");
                        getTokenFromService(TextUtils.isEmpty(key) ? key_panduan : key, trim);
                    }
                } else {
                    Toast.makeText(ChangeHeadActivity.this, "请填写昵称", Toast.LENGTH_SHORT).show();
                }
                break;
            default:

                break;
        }
    }

    private final int output_X = 480;
    private final int output_Y = 480;

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
                    key = "xyc_" + timeInMillis + "jpg";
                    if (bitmap != null) {
                        image_huixian = true;
                        iv_change_head.setImageBitmap(bitmap);

                    }
                    break;
            }
        }


    }

    /****
     * 头像提示框
     */
    public void showPopupWindow() {
        pop = new PopupWindow(ChangeHeadActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
                null);
        ll_popup = view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = view.findViewById(R.id.parent);
        TextView bt1 = view.findViewById(R.id.tv_take_photo);
        TextView bt2 = view.findViewById(R.id.tv_photo_book);
        TextView bt3 = view.findViewById(R.id.tv_exit);
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
                    imageUri = FileProvider.getUriForFile(ChangeHeadActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
                            imageUri = FileProvider.getUriForFile(ChangeHeadActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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

    private void getTokenFromService(final String key, final String trim) {
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.TOUXIANG).addParams("key", key).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    TokenBean token_bean = JsonUtil.parseJsonToBean(string, TokenBean.class);
                    String token = token_bean.getContent().getToken();
                    if (!TextUtils.isEmpty(token)) {
                        shangchuanHead(key, token, trim);
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

    private void shangchuanHead(String key, String token, final String trim) {

        uploadManager.put(fileCropUri, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    sendImage(trim);
                    Log.e("qiniu", "Upload Success");
                } else {
                    String error = info.error;
                    LogUtil.e("error", error);

                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }

        }, null);

    }

    private void sendImage(final String trim) {

    }

}
