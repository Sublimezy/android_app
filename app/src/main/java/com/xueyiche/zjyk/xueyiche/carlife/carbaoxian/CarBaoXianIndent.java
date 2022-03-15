package com.xueyiche.zjyk.xueyiche.carlife.carbaoxian;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
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
import com.xueyiche.zjyk.xueyiche.constants.bean.TokenBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.SuccessBackBean;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PhotoUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Owner on 2017/10/26.
 */
public class CarBaoXianIndent extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_bianji_back;
    private TextView mine_tv_title,tv_toubao;
    private TextView ed_car_style;
    private EditText ed_name, ed_old, ed_idcard, ed_phone, ed_carnumber;
    private RadioButton rb_man, rb_woman;
    private FrameLayout fl_idcard_zheng, fl_idcard_fan, ll_xingshizheng_bg;
    private ImageView iv_idcard_zheng, iv_idcard_fan, iv_xingshizheng;
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
    private String url1 = "", url2 = "", url3 = "";
    private String sex ;
    private int brandid;
    private String user_id;

    @Override
    protected int initContentView() {
        return R.layout.car_baoxian_indent;
    }

    @Override
    protected void initView() {
        ll_bianji_back = (LinearLayout) view.findViewById(R.id.baoxian_indent_include).findViewById(R.id.ll_exam_back);
        mine_tv_title = (TextView) view.findViewById(R.id.baoxian_indent_include).findViewById(R.id.tv_login_back);
        //品牌
        ed_car_style = (TextView) view.findViewById(R.id.ed_car_style);
        //身份证
        ed_idcard = (EditText) view.findViewById(R.id.ed_idcard);
        //姓名
        ed_name = (EditText) view.findViewById(R.id.ed_name);
        //年龄
        ed_old = (EditText) view.findViewById(R.id.ed_old);
        //电话号
        ed_phone = (EditText) view.findViewById(R.id.ed_phone);
        //车牌号
        ed_carnumber = (EditText) view.findViewById(R.id.ed_carnumber);
        //男
        rb_man = (RadioButton) view.findViewById(R.id.rb_man);
        //女
        rb_woman = (RadioButton) view.findViewById(R.id.rb_woman);
        //正面照
        fl_idcard_zheng = (FrameLayout) view.findViewById(R.id.fl_idcard_zheng);
        iv_idcard_zheng = (ImageView) view.findViewById(R.id.iv_idcard_zheng);
        //反面照
        fl_idcard_fan = (FrameLayout) view.findViewById(R.id.fl_idcard_fan);
        iv_idcard_fan = (ImageView) view.findViewById(R.id.iv_idcard_fan);
        //行驶证
        ll_xingshizheng_bg = (FrameLayout) view.findViewById(R.id.ll_xingshizheng_bg);
        iv_xingshizheng = (ImageView) view.findViewById(R.id.iv_xingshizheng);
        //投保
        tv_toubao = (TextView) view.findViewById(R.id.tv_toubao);
        mine_tv_title.setText("大地保险");
        ll_bianji_back.setOnClickListener(this);
        rb_man.setOnClickListener(this);
        rb_woman.setOnClickListener(this);
        fl_idcard_zheng.setOnClickListener(this);
        fl_idcard_fan.setOnClickListener(this);
        ll_xingshizheng_bg.setOnClickListener(this);
        tv_toubao.setOnClickListener(this);
        ed_car_style.setOnClickListener(this);
        uploadManager = new UploadManager();
        sex = "1";
        user_id = PrefUtils.getParameter("user_id");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private int output_X = 960;
    private int output_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_CAMERA_REQUEST://拍照完成回调
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 2, 3, output_X, output_Y, CODE_RESULT_REQUEST);
                break;
            case CODE_GALLERY_REQUEST://访问相册完成回调
                if (XueYiCheUtils.hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
                    PhotoUtils.cropImageUri(this, newUri, cropImageUri, 2, 3, output_X, output_Y, CODE_RESULT_REQUEST);
                } else {

                }
                break;
            case CODE_RESULT_REQUEST:
                bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                Calendar calendar = Calendar.getInstance();
                long timeInMillis = calendar.getTimeInMillis();

                if (bitmap != null) {
                    if (tupian.equals("1")) {
                        key = "xyc_" + String.valueOf(timeInMillis) + "_idcardzheng.jpg";
                        url1 = key;
                        iv_idcard_zheng.setImageBitmap(bitmap);
                        getTokenFromService(key);
                    } else if (tupian.equals("2")) {
                        key = "xyc_" + String.valueOf(timeInMillis) + "_idcardfan.jpg";
                        url2 = key;
                        iv_idcard_fan.setImageBitmap(bitmap);
                        getTokenFromService(key);
                    } else if (tupian.equals("3")) {
                        key = "xyc_" + String.valueOf(timeInMillis) + "_xingshizheng.jpg";
                        url3 = key;
                        iv_xingshizheng.setImageBitmap(bitmap);
                        getTokenFromService(key);

                    }


                }
                break;
            case PINPAI_RESULT_REQUEST:
                String seriesname = data.getStringExtra("seriesname");
                String name = data.getStringExtra("name");
//                seriesid = data.getStringExtra("seriesid");
                brandid = data.getIntExtra("brandid",0);
                if (!TextUtils.isEmpty(seriesname)&&!TextUtils.isEmpty(name)) {
                    ed_car_style.setText(name+"-"+seriesname);
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
                            imageUri = FileProvider.getUriForFile(CarBaoXianIndent.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
        OkHttpUtils.post().url(AppUrl.CARBAOXIANTOKEN).addParams("key", key)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    TokenBean token_bean = JsonUtil.parseJsonToBean(string, TokenBean.class);
                    String token = token_bean.getContent().getToken();
                    if (!TextUtils.isEmpty(token)) {
                        shangchuanHead( key, token);
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
                    imageUri = FileProvider.getUriForFile(CarBaoXianIndent.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
    /****
     * 头像提示框
     */
    public void showPopupWindow() {
        pop = new PopupWindow(CarBaoXianIndent.this);
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



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rb_man:
                rb_woman.setChecked(false);
                sex = "1";
                break;
            case R.id.rb_woman:
                rb_man.setChecked(false);
                sex = "0";
                break;
            case R.id.ed_car_style:
                Intent intent1 = new Intent(App.context, CarStyle.class);
                startActivityForResult(intent1,PINPAI_RESULT_REQUEST);
                break;
            case R.id.fl_idcard_zheng:
                tupian = "1";
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        CarBaoXianIndent.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.fl_idcard_fan:
                tupian = "2";
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        CarBaoXianIndent.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_xingshizheng_bg:
                tupian = "3";
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        CarBaoXianIndent.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_toubao:
                String user_name = ed_name.getText().toString();
                String age = ed_old.getText().toString();
                String id_card = ed_idcard.getText().toString();
                String phone = ed_phone.getText().toString();
                String plate_number = ed_carnumber.getText().toString();
                if (TextUtils.isEmpty(user_name)) {
                    showToastShort("请填写姓名");
                    return;
                }
                if (TextUtils.isEmpty(age)) {
                    showToastShort("请填写年龄");
                    return;
                }
                if (TextUtils.isEmpty(id_card)) {
                    showToastShort("请填写身份证号");
                    return;
                }
                if (!StringUtils.isIdCard(id_card)) {
                    showToastShort("请填写正确的身份证号");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showToastShort("请填写手机号");
                    return;
                }
                String s = ed_car_style.getText().toString();
                if (s.equals("请输入")) {
                    showToastShort("请选择品牌车系");
                    return;
                }
                if (TextUtils.isEmpty(plate_number)) {
                    showToastShort("请输入车牌号");
                    return;
                }
                if (TextUtils.isEmpty(url1)) {
                    showToastShort("请上传身份证正面照");
                    return;
                }
                if (TextUtils.isEmpty(url2)) {
                    showToastShort("请上传身份证反面照");
                    return;
                }
                if (TextUtils.isEmpty(url3)) {
                    showToastShort("请上传行驶证照片");
                    return;
                }
                tijiaoxinxi(user_name,age,id_card,phone,brandid+"",plate_number);
                break;
        }
    }
    private void tijiaoxinxi(String user_name,String age,String id_card,String phone,String brand,String plate_number) {
        OkHttpUtils.post().url(AppUrl.DADIDATAPOST)
                .addParams("user_name",user_name)
                .addParams("age",age)
                .addParams("sex",sex)
                .addParams("user_id",user_id)
                .addParams("id_card",id_card)
                .addParams("phone",phone)
                .addParams("brand",brand)
                .addParams("plate_number",plate_number)
                .addParams("front_card",url1)
                .addParams("back_card ",url2)
                .addParams("drive_license",url3)
                .addParams("area_id", PrefUtils.getParameter("area_id"))
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {

                    final SuccessBackBean duiHuanBean = JsonUtil.parseJsonToBean(string, SuccessBackBean.class);
                    if (duiHuanBean!=null) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                int code = duiHuanBean.getCode();
                                String msg = duiHuanBean.getMsg();
                                if (200==code) {
                                    DialogUtils.showDaDiBaoXian(CarBaoXianIndent.this,"您的投保申请已提交成功，请您耐心等待保险客服人员会尽快与您取得联系！");
                                }else {
                                    showToastShort(msg);
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
}
