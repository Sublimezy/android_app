package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.MessageComeBack;
import com.xueyiche.zjyk.xueyiche.constants.bean.TokenBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PhotoUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.GetWZDriverInformationBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

/**
 * Created by Owner on 2016/12/20.
 */
public class PingJiaDriverSchool extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle, tv_name;
    private Button bt_submit;
    private RatingBar rb_one, rb_jiaoxue, rb_service;
    private int i1;
    private FrameLayout fl_jiashizheng;
    private float i2, i3, total;
    private String order_number, driver_id, head_img, driver_name;
    private ImageView iv_head, iv_jiashizheng;
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
    private UploadManager uploadManager;
    private Bitmap bitmap;
    private String key;
    private EditText ed_think_content;
    private TextView text_number, tv_gone;
    private final int charMaxNum = 50; // 允许输入的字数
    private String thinkContent, evaluate_type;

    @Override
    protected int initContentView() {
        return R.layout.activity_driverschool_pingjia;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.jiaxiao_pingjia_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.jiaxiao_pingjia_include).findViewById(R.id.tv_login_back);
        rb_one = (RatingBar) view.findViewById(R.id.rb_one);

        rb_jiaoxue = (RatingBar) view.findViewById(R.id.rb_jiaoxue);
        rb_service = (RatingBar) view.findViewById(R.id.rb_service);
        bt_submit = (Button) view.findViewById(R.id.bt_submit);
        iv_head = (ImageView) view.findViewById(R.id.iv_head);
        iv_jiashizheng = (ImageView) view.findViewById(R.id.iv_jiashizheng);
        fl_jiashizheng = (FrameLayout) view.findViewById(R.id.fl_jiashizheng);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        text_number = (TextView) view.findViewById(R.id.text_number);
        tv_gone = (TextView) view.findViewById(R.id.tv_gone);
        ed_think_content = (EditText) view.findViewById(R.id.ed_think_content);
        uploadManager = new UploadManager();
        tvTitle.setText("评价");


    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        fl_jiashizheng.setOnClickListener(this);
        ed_think_content.addTextChangedListener(new EditChangedListener());
        text_number.setText(0 + "/" + charMaxNum);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        evaluate_type = intent.getStringExtra("evaluate_type");
        if ("0".equals(evaluate_type)) {
            driver_name = intent.getStringExtra("driver_name");
            head_img = intent.getStringExtra("head_img");
            driver_id = intent.getStringExtra("driver_id");
            fl_jiashizheng.setVisibility(View.VISIBLE);
            tv_gone.setVisibility(View.VISIBLE);
        } else if ("3".equals(evaluate_type)) {
            fl_jiashizheng.setVisibility(View.GONE);
            tv_gone.setVisibility(View.GONE);
            OkHttpUtils.post().url(AppUrl.Get_WZ_Driver_Information).addParams("order_number", order_number)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        GetWZDriverInformationBean getWZDriverInformationBean = JsonUtil.parseJsonToBean(string, GetWZDriverInformationBean.class);
                        if (getWZDriverInformationBean!=null) {
                            int code = getWZDriverInformationBean.getCode();
                            if (200==code) {
                                GetWZDriverInformationBean.ContentBean content = getWZDriverInformationBean.getContent();
                                if (content!=null) {
                                     driver_id = content.getDriver_id();
                                    final String driver_name = content.getDriver_name();
                                    final String head_img = content.getHead_img();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!TextUtils.isEmpty(driver_name)) {
                                                tv_name.setText(driver_name);
                                            }
                                            if (!TextUtils.isEmpty(head_img)) {
                                                Picasso.with(App.context).load(head_img).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(iv_head);
                                            }
                                        }
                                    });
                                }
                            }
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


        if (!TextUtils.isEmpty(driver_name)) {
            tv_name.setText(driver_name);
        }
        if (!TextUtils.isEmpty(head_img)) {
            Picasso.with(App.context).load(head_img).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(iv_head);
        }
        rb_one.setStepSize(0.5f);
        rb_jiaoxue.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                i2 = rb_jiaoxue.getRating();
                total = i2 + i3;
                rb_one.setRating(total / 2);

            }
        });
        rb_service.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                i3 = rb_service.getRating();
                total = i2 + i3;
                rb_one.setRating(total / 2);
            }
        });

    }

    class EditChangedListener implements TextWatcher {
        private CharSequence temp; // 监听前的文本
        private int editStart; // 光标开始位置
        private int editEnd; // 光标结束位置

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            text_number.setText((s.length()) + "/" + charMaxNum);
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = ed_think_content.getSelectionStart();
            editEnd = ed_think_content.getSelectionEnd();
            if (temp.length() > charMaxNum) {
                s.delete(editStart - 1, editEnd);
                ed_think_content.setText(s);
                ed_think_content.setSelection(s.length());
                showToastShort("最多可输入50个字");

            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();

                break;
            case R.id.bt_submit:
                if ("0".equals(evaluate_type)) {
                    if (TextUtils.isEmpty(key)) {
                        Toast.makeText(PingJiaDriverSchool.this, "请上传驾驶证照片！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    thinkContent = ed_think_content.getText().toString();
                    if (TextUtils.isEmpty(thinkContent)) {
                        Toast.makeText(PingJiaDriverSchool.this, "请填写评价内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int length = thinkContent.length();
                    if (length < 5) {
                        Toast.makeText(PingJiaDriverSchool.this, "评论内容最少五个字", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getTokenFromService(key);
                } else if ("3".equals(evaluate_type)) {
                    thinkContent = ed_think_content.getText().toString();
                    if (TextUtils.isEmpty(thinkContent)) {
                        Toast.makeText(PingJiaDriverSchool.this, "请填写评价内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int length = thinkContent.length();
                    if (length < 5) {
                        Toast.makeText(PingJiaDriverSchool.this, "评论内容最少五个字", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    submitPingJia();
                }
                break;
            case R.id.fl_jiashizheng:
                showPopupWindow();

                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        PingJiaDriverSchool.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    /****
     * 头像提示框
     */
    public void showPopupWindow() {
        pop = new PopupWindow(PingJiaDriverSchool.this);
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


    private int output_X = 480;
    private int output_Y = 960;

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
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
                        showToastShort("设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    Calendar calendar = Calendar.getInstance();
                    long timeInMillis = calendar.getTimeInMillis();
                    key = "xyc_jiashizheng_" + String.valueOf(timeInMillis) + ".jpg";
                    if (bitmap != null) {
                        iv_jiashizheng.setImageBitmap(bitmap);
                    }
                    break;
            }
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
                    imageUri = FileProvider.getUriForFile(PingJiaDriverSchool.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
                            imageUri = FileProvider.getUriForFile(PingJiaDriverSchool.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
                    int code = token_bean.getCode();
                    if (200 == code) {
                        String token = token_bean.getContent().getToken();
                        if (!TextUtils.isEmpty(token)) {
                            shangchuanHead(key, token);
                        }
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

    private void shangchuanHead(String key, String token) {

        uploadManager.put(fileCropUri, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    Log.e("qiniu", "Upload Success");
                    //提交评价
                    submitPingJia();
                } else {
                    String error = info.error;
                    LogUtil.e("error", error);
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PingJiaDriverSchool.this, "照片提交失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }

        }, null);

    }

    private void submitPingJia() {
        i1 = (int) rb_one.getRating();
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        float v = total / 2;
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.PingJia_DriverSchool_Trainer)
                    .addParams("order_number", order_number)
                    .addParams("device_id", LoginUtils.getId(PingJiaDriverSchool.this))
                    .addParams("all_evaluate", "" + v)
                    .addParams("driver_id", driver_id)
                    .addParams("user_id", user_id)
                    .addParams("service_attitude", "" + i3)
                    .addParams("technological_level", "" + i2)
                    .addParams("content", thinkContent)
                    .addParams("driving_licence", !TextUtils.isEmpty(key) ? key : "")
                    .addParams("evaluate_type", evaluate_type)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        MessageComeBack messageComeBack = JsonUtil.parseJsonToBean(string, MessageComeBack.class);
                        if (messageComeBack != null) {
                            int code = messageComeBack.getCode();
                            final String msg = messageComeBack.getMsg();
                            if (200 == code) {
                                App.handler
                                        .post(new Runnable() {
                                            @Override
                                            public void run() {
                                                showToastShort("评价成功");
                                                finish();
                                                if ("0".equals(evaluate_type)) {
                                                    DriverSchoolTrainerListActivity.instance.finish();
                                                }
                                                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                                                EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                                            }
                                        });
                            }
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
                    EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                    EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                }
            });
            EventBus.getDefault().post(new MyEvent("刷新全部订单"));
            EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
            finish();
        } else {
            showToastShort("请给个星评呗");
        }
    }
}
