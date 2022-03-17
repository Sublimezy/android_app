package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
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

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2018/6/1.
 */
public class PutStudentCardActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_bianji_back;
    private TextView mine_tv_title;
    private ImageView iv_putstudentcard;
    private Button bt_tijiao;
    PopupWindow pop;
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    LinearLayout ll_popup;
    private UploadManager uploadManager;  //七牛SDK的上传管理者
    private String key;
    private boolean image_huixian = false;
    private Bitmap bitmap;
    private String user_id;
    private String url_idcardtop = "";
    private String order_number;

    @Override
    protected int initContentView() {
        return R.layout.putstudentcard_activity;
    }

    @Override
    protected void initView() {
        ll_bianji_back = (LinearLayout) view.findViewById(R.id.tijiao_include).findViewById(R.id.ll_exam_back);
        mine_tv_title = (TextView) view.findViewById(R.id.tijiao_include).findViewById(R.id.tv_login_back);
        iv_putstudentcard = (ImageView) view.findViewById(R.id.iv_putstudentcard);
        bt_tijiao = (Button) view.findViewById(R.id.bt_tijiao);
        uploadManager = new UploadManager();

    }

    @Override
    protected void initListener() {
        ll_bianji_back.setOnClickListener(this);
        iv_putstudentcard.setOnClickListener(this);
        bt_tijiao.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mine_tv_title.setText("上传照片");
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        user_id =  PrefUtils.getString(App.context,"user_id","");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_putstudentcard:
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        PutStudentCardActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.bt_tijiao:
                tijiao();
                break;
        }
    }

    private void tijiao() {
        OkHttpUtils.post()
                .url(AppUrl.DriverSchool_FanXian)
                .addParams("device_id", LoginUtils.getId(PutStudentCardActivity.this))
                .addParams("user_id", user_id)
                .addParams("order_number", order_number)
                .addParams("photo_url", "http://xychead.xueyiche.vip/"+ key)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean != null) {
                                final int code = successDisCoverBackBean.getCode();
                                final String msg = successDisCoverBackBean.getMsg();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!TextUtils.isEmpty("" + code)) {
                                            if (200 == code) {
                                                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                                                finish();
                                                Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                        return string;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {

                    }
                });

    }

    private int output_X = 480;
    private int output_Y = 960;

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //  if (resultCode == RESULT_OK) {
        switch (requestCode) {
            case CODE_CAMERA_REQUEST://拍照完成回调
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 3, 2, output_X, output_Y, CODE_RESULT_REQUEST);
                break;
            case CODE_GALLERY_REQUEST://访问相册完成回调
                if (XueYiCheUtils.hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(this, intent.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
                    PhotoUtils.cropImageUri(this, newUri, cropImageUri, 3, 2, output_X, output_Y, CODE_RESULT_REQUEST);
                } else {
                }
                break;
            case CODE_RESULT_REQUEST:
                bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                Calendar calendar = Calendar.getInstance();
                long timeInMillis = calendar.getTimeInMillis();

                if (bitmap != null) {
                    key = "xyc_student_" + String.valueOf(timeInMillis) + "_card.jpg";
                    iv_putstudentcard.setImageBitmap(bitmap);
                    getTokenFromService(key);


                }
                break;


        }

    }

    private void getTokenFromService(final String key) {
        OkHttpUtils.post().url(AppUrl.TOUXIANG).addParams("key", key).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    TokenBean token_bean = JsonUtil.parseJsonToBean(string, TokenBean.class);
                    String token = token_bean.getContent().getToken();
                    if (!TextUtils.isEmpty(token)) {
                        shangchuanHead(key, token);
                    }


                }
                return null;
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    private void shangchuanHead(String key, String token) {
        uploadManager.put(fileCropUri, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    Log.e("qiniu", "Upload Success");
                } else {
                    Log.e("qiniu", "Upload Fail");
                    String error = info.error;
                    Log.e("error", error);
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }

        }, null);

    }

    public void showPopupWindow() {
        pop = new PopupWindow(PutStudentCardActivity.this);
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
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                autoObtainStoragePermission();
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
                    imageUri = FileProvider.getUriForFile(PutStudentCardActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                pop.dismiss();
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
            pop.dismiss();
        }

    }

}
