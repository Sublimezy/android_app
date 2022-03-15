package com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.Json;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.constants.bean.TokenBean;
import com.xueyiche.zjyk.xueyiche.discover.activity.FaBuQuestionActivity;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.DriverSchoolTaocanListBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.OptionPicker;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.BaoMingOkBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.XiangMuListBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.PhotoUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.ToastUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * #            Created by 张磊 on 2021/1/18.                                       #
 */
//报名
public class StudentsBaoMingActivity extends NewBaseActivity implements View.OnClickListener {
    private LinearLayout llBack, llYaoQing;
    private TextView tvTitle, tv_wenxintishi, tvChoose, tvMoney;
    private ArrayList<String> xiangmuList = new ArrayList<>();
    private ArrayList<String> xiangmuListId = new ArrayList<>();
    private ArrayList<String> xiangmuListMoney = new ArrayList<>();
    private MClearEditText tvName, tvIdCard, tvPhone, tvYaoQingPhone, tvAddress;
    private RadioButton rb_ok, rb_no;
    private ImageView ivUpIdCard, ivDownIdCard;
    private Button btBaoMing;
    private String xiangmu = "";
    private String isYaoQing = "0";
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
    private String key, tupian;
    private String url_image1 = "", url_image2 = "";
    private String money = "0";
    private String entry_project = "";
    public static StudentsBaoMingActivity stance;

    @Override
    protected int initContentView() {
        return R.layout.students_baoming_activity;
    }

    @Override
    protected void initView() {
        stance = this;
        llBack = view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tvTitle = view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        tv_wenxintishi = view.findViewById(R.id.title_include).findViewById(R.id.tv_wenxintishi);
        tvChoose = view.findViewById(R.id.tvChoose);
        tvMoney = view.findViewById(R.id.tvMoney);
        tvName = view.findViewById(R.id.tvName);
        tvIdCard = view.findViewById(R.id.tvIdCard);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvYaoQingPhone = view.findViewById(R.id.tvYaoQingPhone);
        rb_ok = view.findViewById(R.id.rb_ok);
        rb_no = view.findViewById(R.id.rb_no);
        tvAddress = view.findViewById(R.id.tvAddress);
        ivUpIdCard = view.findViewById(R.id.ivUpIdCard);
        ivDownIdCard = view.findViewById(R.id.ivDownIdCard);
        btBaoMing = view.findViewById(R.id.btBaoMing);
        llYaoQing = view.findViewById(R.id.llYaoQing);
        uploadManager = new UploadManager();


    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
        tvChoose.setOnClickListener(this);
        rb_ok.setOnClickListener(this);
        rb_no.setOnClickListener(this);
        ivUpIdCard.setOnClickListener(this);
        ivDownIdCard.setOnClickListener(this);
        btBaoMing.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_wenxintishi.setVisibility(View.VISIBLE);
        tvTitle.setText("报名");
        tv_wenxintishi.setText("报名须知");
        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.get().url(AppUrl.drivingentryproject).build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        XiangMuListBean xiangMuListBean = JsonUtil.parseJsonToBean(string, XiangMuListBean.class);
                        if (xiangMuListBean!=null) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int code = xiangMuListBean.getCode();
                                    if (200==code) {
                                        List<XiangMuListBean.ContentBean> content = xiangMuListBean.getContent();
                                        if (content!=null) {
                                            for (int i = 0; i < content.size(); i++) {
                                                xiangmuList.add(content.get(i).getEntry_project());
                                                xiangmuListId.add(""+content.get(i).getId());
                                                xiangmuListMoney.add(content.get(i).getMoney());
                                            }
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
                    stopProgressDialog();
                }

                @Override
                public void onResponse(Object response) {
                    stopProgressDialog();
                }
            });
        } else {
            showToastShort("请检查网络连接");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tvChoose:
                if (xiangmuList!=null&&xiangmuList.size()>0) {
                    chooseXiangMu();
                }
                break;
            case R.id.ivUpIdCard:
                tupian = "1";
                showPopupWindow();
                break;
            case R.id.ivDownIdCard:
                tupian = "2";
                showPopupWindow();
                break;
            case R.id.rb_ok:
                isYaoQing = "1";
                llYaoQing.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_no:
                isYaoQing = "0";
                llYaoQing.setVisibility(View.GONE);
                break;
            case R.id.btBaoMing:
                postInformation();
                break;
            case R.id.tv_wenxintishi:
                Intent intent3 = new Intent(App.context, UrlActivity.class);
                intent3.putExtra("url", "http://xueyiche.cn/xyc/kaojiazhao/baomingxuzhi.html");
                intent3.putExtra("type", "1");
                startActivity(intent3);
                break;
        }
    }

    private void postInformation() {
        String name = tvName.getText().toString().trim();
        String idCard = tvIdCard.getText().toString().trim();
        String phone = tvPhone.getText().toString().trim();
        String yaoqingPhone = tvYaoQingPhone.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();
        if (TextUtils.isEmpty(entry_project)) {
            Toast.makeText(this, "请选择报名项目", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(idCard)) {
            Toast.makeText(this, "请输入真实身份证号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入报名者手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请输入居住地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("1".equals(isYaoQing)) {
            if (TextUtils.isEmpty(yaoqingPhone)) {
                Toast.makeText(this, "请输入邀请人手机号手机号", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (TextUtils.isEmpty(url_image1)) {
            Toast.makeText(this, "完善身份证信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(url_image2)) {
            Toast.makeText(this, "完善身份证信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("trainee_name", name);
            stringMap.put("id_number", idCard);
            stringMap.put("trainee_phone", phone);
            stringMap.put("invent_people", "" + yaoqingPhone);
            stringMap.put("id_card_pic1", url_image1);
            stringMap.put("id_card_pic2", url_image2);
            stringMap.put("user_id", PrefUtils.getParameter("user_id"));
            stringMap.put("address", address);
            stringMap.put("entry_project", entry_project);
            Log.e("inserttrainee_post",""+new Gson().toJson(stringMap));
            OkHttpUtils.post().url(AppUrl.inserttrainee).params(stringMap)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        BaoMingOkBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, BaoMingOkBean.class);
                        if (successDisCoverBackBean != null) {
                            int code = successDisCoverBackBean.getCode();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (200 == code) {
                                        Intent intent = new Intent(App.context, StudentsIndentContentActivity.class);
                                        intent.putExtra("order_number",successDisCoverBackBean.getContent());
                                        startActivity(intent);
                                    }
                                    showToastShort("" + successDisCoverBackBean.getMsg());
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
        } else {
            showToastShort("请检查网络连接");
        }

    }

    public void showPopupWindow() {
        pop = new PopupWindow(StudentsBaoMingActivity.this);
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
        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                StudentsBaoMingActivity.this, R.anim.activity_translate_in));
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


    private void chooseXiangMu() {
        final OptionPicker picker = new OptionPicker(this, xiangmuList);
        picker.setOffset(1);
        picker.setSelectedIndex(0);
        picker.setTextSize(15);
        picker.setTitleTextColor(getResources().getColor(R.color._3333));
        picker.setTitleText("请选择");
        picker.setTitleTextSize(20);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                picker.setSelectedIndex(position);
                xiangmu = option;
                entry_project = xiangmuListId.get(position);
                money = xiangmuListMoney.get(position);
                tvChoose.setText(option);
                tvMoney.setText("￥"+money);
            }
        });
        picker.show();
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
                    imageUri = FileProvider.getUriForFile(StudentsBaoMingActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
                            imageUri = FileProvider.getUriForFile(StudentsBaoMingActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
                            key = "xyc_baoming_" + String.valueOf(timeInMillis) + "_idcard_up.jpg";
                            url_image1 = key;
                            ivUpIdCard.setImageBitmap(bitmap);
                            getTokenFromService(key);
                        } else if (tupian.equals("2")) {
                            key = "xyc_baoming_" + String.valueOf(timeInMillis) + "_idcard_down.jpg";
                            url_image2 = key;
                            ivDownIdCard.setImageBitmap(bitmap);
                            getTokenFromService(key);
                        }
                    }
                    break;
            }
        }


    }
}
