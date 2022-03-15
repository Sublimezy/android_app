package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.submit.UsedCarSubmitIndent;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UesdCarTokenBean;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarSuccessBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
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

/**
 * Created by Zhanglei on 2017/8/6.
 */
public class ShenFenQueRen extends BaseActivity implements View.OnClickListener {
    //iv_shop_back_all
    private ImageView iv_idcard_zheng, iv_idcard_fan;
    private TextView tv_next, tv_login_back, tv_shoudongshuru;
    public static ShenFenQueRen instance;
    private FrameLayout ll_idcard_zheng_bg;
    private FrameLayout ll_idcard_fan_bg;
    private LinearLayout ll_exam_back;
    PopupWindow pop;
    LinearLayout ll_popup;
    private UploadManager uploadManager;  //七牛SDK的上传管理者
    private String key;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int PINPAI_RESULT_REQUEST = 001;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private Bitmap bitmap;
    private String tupian;
    private AES mAes;
    private String url_idcardtop = "", url_idcarddown = "";
    private String qu_time_date, huan_city, huan_time_date, qu_city, store_id, shangMenQu, shangMenHuan, duration;
    private String basic_service_charge, shop_service_charge, violated_deposit, car_deposit, shop_name, qu_latitude_usedcar, qu_longitude_usedcar, qu_name_usedcar, huan_latitude_usedcar,
            huan_longitude_usedcar, huan_name_usedcar, rent_price;
    private String img;
    private String car_allname;
    private String carsource_id;


    @Override
    protected int initContentView() {
        return R.layout.shenfenqueren_layout;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.shenfenqueren_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.shenfenqueren_include).findViewById(R.id.tv_login_back);
        iv_idcard_zheng = (ImageView) view.findViewById(R.id.iv_idcard_zheng);
        iv_idcard_fan = (ImageView) view.findViewById(R.id.iv_idcard_fan);
        tv_next = (TextView) view.findViewById(R.id.tv_next);
        tv_shoudongshuru = (TextView) view.findViewById(R.id.tv_shoudongshuru);
        ll_idcard_zheng_bg = (FrameLayout) view.findViewById(R.id.ll_idcard_zheng_bg);
        ll_idcard_fan_bg = (FrameLayout) view.findViewById(R.id.ll_idcard_fan_bg);
        instance = this;
        uploadManager = new UploadManager();
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        ll_idcard_zheng_bg.setOnClickListener(this);
        ll_idcard_fan_bg.setOnClickListener(this);
        tv_shoudongshuru.setOnClickListener(this);
        tv_next.setOnClickListener(this);


    }

    @Override
    protected void initData() {
        tv_login_back.setText("身份确认");
        tv_shoudongshuru.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_shoudongshuru.getPaint().setAntiAlias(true);//抗锯齿
        tv_shoudongshuru.setText(Html.fromHtml("" + "手动输入" + ""));
        Intent intent = getIntent();
        shangMenQu = intent.getStringExtra("shangMenQu");
        shangMenHuan = intent.getStringExtra("shangMenHuan");
        qu_time_date = intent.getStringExtra("qu_time_date");
        huan_time_date = intent.getStringExtra("huan_time_date");
        qu_city = intent.getStringExtra("qu_city_usedcar");
        huan_city = intent.getStringExtra("huan_city_usedcar");
        store_id = intent.getStringExtra("store_id_usedcar");
        duration = intent.getStringExtra("duration");
        //取车地址名称和经纬度上门
        qu_latitude_usedcar = intent.getStringExtra("qu_latitude_usedcar");
        qu_longitude_usedcar = intent.getStringExtra("qu_longitude_usedcar");
        qu_name_usedcar = intent.getStringExtra("qu_name_usedcar");
        //还车地址名称和经纬度上门
        huan_latitude_usedcar = intent.getStringExtra("huan_latitude_usedcar");
        huan_longitude_usedcar = intent.getStringExtra("huan_longitude_usedcar");
        huan_name_usedcar = intent.getStringExtra("huan_name_usedcar");
        //基本保障服务费
        basic_service_charge = intent.getStringExtra("basic_service_charge");
        //租车单价
        rent_price = intent.getStringExtra("rent_price");
        //门店服务费
        shop_service_charge = intent.getStringExtra("shop_service_charge");
        //违章押金
        violated_deposit = intent.getStringExtra("violated_deposit");
        //车辆押金
        car_deposit = intent.getStringExtra("car_deposit");
        //门店名称
        shop_name = intent.getStringExtra("shop_name");
        img = intent.getStringExtra("img");
        car_allname = intent.getStringExtra("car_allname");
        carsource_id = intent.getStringExtra("carsource_id");


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void commonIntent(Intent intent1) {
        intent1.putExtra("carsource_id", carsource_id);
        intent1.putExtra("qu_time_date", qu_time_date);
        intent1.putExtra("huan_time_date", huan_time_date);
        intent1.putExtra("store_id_usedcar", store_id);
        intent1.putExtra("duration", duration);
        intent1.putExtra("qu_city_usedcar", qu_city);
        intent1.putExtra("huan_city_usedcar", huan_city);
        intent1.putExtra("shangMenQu", shangMenQu);
        intent1.putExtra("shangMenHuan", shangMenHuan);
        intent1.putExtra("qu_latitude_usedcar", qu_latitude_usedcar);
        intent1.putExtra("qu_longitude_usedcar", qu_longitude_usedcar);
        intent1.putExtra("qu_name_usedcar", qu_name_usedcar);
        intent1.putExtra("huan_latitude_usedcar", huan_latitude_usedcar);
        intent1.putExtra("huan_longitude_usedcar", huan_longitude_usedcar);
        intent1.putExtra("huan_name_usedcar", huan_name_usedcar);
        intent1.putExtra("car_allname", car_allname);
        intent1.putExtra("shop_service_charge", shop_service_charge);
        intent1.putExtra("basic_service_charge", basic_service_charge);
        intent1.putExtra("violated_deposit", violated_deposit);
        intent1.putExtra("car_deposit", car_deposit);
        intent1.putExtra("rent_price", rent_price);
        intent1.putExtra("shop_name", shop_name);
        intent1.putExtra("img", img);
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
                    if (tupian.equals("1")) {
                        key = "xyc_querenshenfen_" + String.valueOf(timeInMillis) + "_idcardtop.jpg";
                        url_idcardtop = key;
                        iv_idcard_zheng.setImageBitmap(bitmap);
                        getTokenFromService(key);
                    } else if (tupian.equals("2")) {
                        key = "xyc_querenshenfen_" + String.valueOf(timeInMillis) + "_idcarddown.jpg";
                        url_idcarddown = key;
                        iv_idcard_fan.setImageBitmap(bitmap);
                        getTokenFromService(key);
                    }


                }
                break;


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
                            imageUri = FileProvider.getUriForFile(ShenFenQueRen.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                    }
                } else {
                }
                break;


            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                }
                break;
        }
    }

    private void getTokenFromService(final String key) {
        OkHttpUtils.post().url(AppUrl.Used_Car_Token)
                .addParams("device_id", LoginUtils.getId(this))
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    UesdCarTokenBean token_bean = JsonUtil.parseJsonToBean(string, UesdCarTokenBean.class);
                    String token = token_bean.getToken();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.ll_idcard_zheng_bg:
                tupian = "1";
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        ShenFenQueRen.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_idcard_fan_bg:
                tupian = "2";
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        ShenFenQueRen.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_next:
                if (TextUtils.isEmpty(url_idcardtop)) {
                    showToastShort("请扫描身份证正面照片");
                    return;
                }
                if (TextUtils.isEmpty(url_idcarddown)) {
                    showToastShort("请扫描身份证反面照片");
                    return;
                }
                submitDate();
                break;
            case R.id.tv_shoudongshuru:
                Intent intent = new Intent(App.context,CheckShenFenActivity.class);
                commonIntent(intent);
                startActivity(intent);
                break;

        }
    }

    private void submitDate() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Used_Car_IdData)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("id_front", url_idcardtop)
                    .addParams("id_back", url_idcarddown)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        UsedCarSuccessBean usedCarSuccessBean = JsonUtil.parseJsonToBean(string, UsedCarSuccessBean.class);
                        int code = usedCarSuccessBean.getCode();
                        final String message = usedCarSuccessBean.getMessage();
                        if (200 == code) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(App.context, UsedCarSubmitIndent.class);
                                    commonIntent(intent);
                                    startActivity(intent);
                                    showToastShort(message);
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


    public void showPopupWindow() {
        pop = new PopupWindow(ShenFenQueRen.this);
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

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (XueYiCheUtils.hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(ShenFenQueRen.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {

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
}
