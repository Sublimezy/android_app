package com.xueyiche.zjyk.xueyiche.homepage.activities;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.xueyiche.zjyk.xueyiche.homepage.activities.carlive.activity.bean.TrainBaoMingSuccessBean;
import com.xueyiche.zjyk.xueyiche.homepage.activities.carlive.activity.bean.TrainTaoCanBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
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
import java.util.List;

/**
 * Created by Owner on 2018/11/28.
 */
public class TrainBaoMingActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private TextView tvTitle;
    private TextView tv_wenxintishi;
    private AdListView lv_train_fuwu;
    private ScrollView scroll_view;
    private String type = "1";
    private String pay_price = "", driver_card_url = "";
    private RadioButton rb_gonwu, rb_sifa, rb_wu, rb_you;
    private int selectPosition = -1;//用于记录用户选择的变量
    private String selectId;
    private Button bt_pay;
    private EditText etWriteName, etWriteIdCard, etWritePhone;
    private ImageView iv_uploading_drivecard;
    private PopupWindow pop;
    private LinearLayout ll_popup;
    private Uri imageUri;
    private Uri cropImageUri;
    private Bitmap bitmap;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private String key;
    private UploadManager uploadManager;
    public static TrainBaoMingActivity instance;

    @Override
    protected int initContentView() {
        return R.layout.train_baoming_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_wenxintishi);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        bt_pay = (Button) view.findViewById(R.id.bt_pay);
        iv_uploading_drivecard = (ImageView) view.findViewById(R.id.iv_uploading_drivecard);
        etWriteName = (EditText) view.findViewById(R.id.etWriteName);
        etWriteIdCard = (EditText) view.findViewById(R.id.etWriteIdCard);
        etWritePhone = (EditText) view.findViewById(R.id.etWritePhone);
        rb_gonwu = (RadioButton) view.findViewById(R.id.rb_gonwu);
        rb_sifa = (RadioButton) view.findViewById(R.id.rb_sifa);
        rb_wu = (RadioButton) view.findViewById(R.id.rb_wu);
        rb_you = (RadioButton) view.findViewById(R.id.rb_you);
        uploadManager = new UploadManager();
        //服务列表
        lv_train_fuwu = (AdListView) view.findViewById(R.id.lv_train_fuwu);
        lv_train_fuwu.setItemsCanFocus(true);
        tv_wenxintishi.setText("说明");
        tvTitle.setText("报名");
        scroll_view.smoothScrollBy(0, 0);
        scroll_view.smoothScrollTo(0, 0);
        instance = this;
    }

    @Override
    protected void initListener() {
        iv_uploading_drivecard.setOnClickListener(this);
        bt_pay.setOnClickListener(this);
        llBack.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
        rb_you.setOnClickListener(this);
        rb_wu.setOnClickListener(this);
        rb_sifa.setOnClickListener(this);
        rb_gonwu.setOnClickListener(this);
        lv_train_fuwu.setFocusable(false);
        lv_train_fuwu.setVerticalScrollBarEnabled(false);
        lv_train_fuwu.setFastScrollEnabled(false);
    }

    @Override
    protected void initData() {
        getDataFromNet();

    }

    private void getDataFromNet() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post()
                    .url(AppUrl.Train_TaoCan)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("type", type)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        TrainTaoCanBean trainTaoCanBean = JsonUtil.parseJsonToBean(string, TrainTaoCanBean.class);
                        if (trainTaoCanBean != null) {
                            final List<TrainTaoCanBean.ContentBean> content = trainTaoCanBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        TrainFuWuAdapter trainFuWuAdapter = new TrainFuWuAdapter(content);
                                        lv_train_fuwu.setAdapter(trainFuWuAdapter);
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
                }
            });
        }
    }
    private int output_X = 480;
    private int output_Y = 480;
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
                    key = "xyc_" + String.valueOf(timeInMillis) + "jpg";
                    if (bitmap != null) {
                        iv_uploading_drivecard.setImageBitmap(bitmap);
                    }
                    break;
            }
        }


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:
                //说明
                break;
            case R.id.rb_sifa:
                type = "2";
                selectPosition = -1;
                selectId = "";
                getDataFromNet();
                break;
            case R.id.rb_gonwu:
                type = "1";
                selectPosition = -1;
                selectId = "";
                getDataFromNet();
                break;
            case R.id.bt_pay:
                //支付
                if (DialogUtils.IsLogin()) {
                    goPay();
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.rb_you:
                iv_uploading_drivecard.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_wu:
                iv_uploading_drivecard.setVisibility(View.GONE);
                break;
            case R.id.iv_uploading_drivecard:
                showPopupWindow();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        TrainBaoMingActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }
    }


    public void showPopupWindow() {
        pop = new PopupWindow(this);
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
                showToastShort("您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (XueYiCheUtils.hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
    private void goPay() {
        String name = etWriteName.getText().toString().trim();
        String card = etWriteIdCard.getText().toString().trim();
        String phone = etWritePhone.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToastShort("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(card)) {
            showToastShort("请输入身份证号码");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToastShort("请输入手机号");
            return;
        }
        if (!StringUtils.isIdCard(card)) {
            showToastShort("请输入正确的身份证号码");
            return;
        }
        if (TextUtils.isEmpty(selectId)) {
            showToastShort("请选择套餐");
            return;
        }
        if (rb_you.isChecked()) {
            if (TextUtils.isEmpty(key)) {
                showToastShort("请选择照片");
                return;
            }
        }

        sendData(name, card, phone);
    }

    private void sendData(String name, String card, String phone) {
        String user_id = PrefUtils.getString(this, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post()
                    .url(AppUrl.Train_BaoMing)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .addParams("package_id", selectId)
                    .addParams("pay_price", pay_price)
                    .addParams("user_name", name)
                    .addParams("user_phone", AES.encrypt(phone))
                    .addParams("user_cards", AES.encrypt(card))
                    .addParams("have_driver_card", rb_you.isChecked() ? "1" : "2")
                    .addParams("driver_card_url","http://xychead.xueyiche.vip/" + key)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        TrainBaoMingSuccessBean trainBaoMingSuccessBean = JsonUtil.parseJsonToBean(string, TrainBaoMingSuccessBean.class);
                        if (trainBaoMingSuccessBean != null) {
                            final TrainBaoMingSuccessBean.ContentBean content = trainBaoMingSuccessBean.getContent();
                            final int code = trainBaoMingSuccessBean.getCode();
                            final String msg = trainBaoMingSuccessBean.getMsg();


                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    showToastShort(msg);
                                    if (200 == code) {
                                        if (content != null) {
                                            String order_number = content.getOrder_number();
                                            if (!TextUtils.isEmpty(order_number)) {
                                                //支付
                                                Intent intent = new Intent(App.context, AppPay.class);
                                                intent.putExtra("pay_style", "train");
                                                intent.putExtra("order_number", order_number);
                                                intent.putExtra("subscription", pay_price + "");
                                                intent.putExtra("jifen","0");
                                                startActivity(intent);
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
                } else {
                    String error = info.error;
                    LogUtil.e("error", error);
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToastShort("照片提交失败");
                        }
                    });
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }

        }, null);

    }
    public class TrainFuWuAdapter extends BaseAdapter {
        private List<TrainTaoCanBean.ContentBean> content;

        public TrainFuWuAdapter(List<TrainTaoCanBean.ContentBean> content) {
            this.content = content;
        }

        @Override
        public int getCount() {
            return content.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(TrainBaoMingActivity.this).inflate(
                        R.layout.train_baoming_item, parent, false);
                holder = new ViewHolder();
                holder.rl_car_fuwu_item_top = (RelativeLayout) convertView.findViewById(R.id.rl_car_fuwu_item_top);
                holder.tv_car_fuwu_item_more = (TextView) convertView.findViewById(R.id.tv_car_fuwu_item_more);
                //价格
                holder.tv_goods_money_shop = (TextView) convertView.findViewById(R.id.tv_goods_money_shop);
                //价格单位
                holder.tv_shop_service_cartype = (TextView) convertView.findViewById(R.id.tv_shop_service_cartype);
                holder.iv_fuwu_chick = (ImageView) convertView.findViewById(R.id.iv_fuwu_chick);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (content != null && content.size() != 0) {
                TrainTaoCanBean.ContentBean contentBean = content.get(i);
                if (contentBean != null) {
                    final int id = contentBean.getId();
                    String package_name = contentBean.getPackage_name();
                    String package_mark = contentBean.getPackage_mark();
                    final double payprice = contentBean.getPay_price();
                    holder.tv_shop_service_cartype.setText(package_name);
                    holder.tv_car_fuwu_item_more.setText(package_mark);
                    holder.tv_goods_money_shop.setText(payprice + "");
                    if (selectPosition == i) {
                        holder.iv_fuwu_chick.setImageResource(R.mipmap.car_fuwu_item_true);
                    } else {
                        holder.iv_fuwu_chick.setImageResource(R.mipmap.car_fuwu_item_false);
                    }

                    holder.rl_car_fuwu_item_top.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectPosition = i;
                            selectId = id + "";
                            pay_price = payprice + "";
                            notifyDataSetChanged();
                        }
                    });
                }
            }
            return convertView;
        }

    }

    private static class ViewHolder {
        private RelativeLayout rl_car_fuwu_item_top;
        private TextView tv_car_fuwu_item_more;
        private TextView tv_shop_service_cartype;
        private TextView tv_goods_money_shop;
        private ImageView iv_fuwu_chick;
    }
}
