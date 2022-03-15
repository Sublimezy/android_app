package com.xueyiche.zjyk.xueyiche.usedcar.activity.zhengxin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.examtext.CommonWebView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.ZhengXinChaXunBean;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.ZhengXinChaXunOtherBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ZL on 2018/10/9.
 */
public class WriteInfomationActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private TextView tvTitle, tv_wenxintishi, tv_xieyi;
    private LinearLayout llBack;
    private Button bt_chaxun;
    private EditText etWriteName, etWriteIdCard, etWriteYZM,etWritePhone, etWriteBankCard;
    private CheckBox cb_xieyi;
    private String isCheck = "1";
    private String style = "1";
    private RadioButton rb_danbao, rb_other;
    private ImageView zhengxin_saomiao;
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private String result;
    private RelativeLayout ll_saomiao;


    @Override
    protected int initContentView() {
        return R.layout.write_infomation_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_wenxintishi);
        bt_chaxun = (Button) view.findViewById(R.id.bt_chaxun);
        tv_xieyi = (TextView) view.findViewById(R.id.tv_xieyi);
        etWriteName = (EditText) view.findViewById(R.id.etWriteName);
        etWriteYZM = (EditText) view.findViewById(R.id.etWriteYZM);
        ll_saomiao = (RelativeLayout) view.findViewById(R.id.ll_saomiao);
        etWriteIdCard = (EditText) view.findViewById(R.id.etWriteIdCard);
        etWritePhone = (EditText) view.findViewById(R.id.etWritePhone);
        etWriteBankCard = (EditText) view.findViewById(R.id.etWriteBankCard);
        cb_xieyi = (CheckBox) view.findViewById(R.id.cb_xieyi);
        rb_danbao = (RadioButton) view.findViewById(R.id.rb_danbao);
        rb_other = (RadioButton) view.findViewById(R.id.rb_other);
        zhengxin_saomiao = (ImageView) view.findViewById(R.id.zhengxin_saomiao);
        rb_danbao.setChecked(true);

    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        bt_chaxun.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
        tv_xieyi.setOnClickListener(this);
        rb_danbao.setOnClickListener(this);
        rb_other.setOnClickListener(this);
        zhengxin_saomiao.setOnClickListener(this);
        cb_xieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isCheck = "1";
                } else {
                    isCheck = "0";
                }

            }
        });

    }


    @Override
    protected void initData() {
        tvTitle.setText("填写信息");
        tv_wenxintishi.setVisibility(View.VISIBLE);
        tv_wenxintishi.setTextColor(getResources().getColor(R.color.title_one));
        tv_wenxintishi.setText("历史记录");
//        String user_phone = PrefUtils.getString(App.context, "user_phone", "");
//        if (!TextUtils.isEmpty(user_phone)) {
//            String decrypt = AES.decrypt(user_phone);
//            etWritePhone.setText(decrypt);
//        }

    }
    //拼团活动
    private   void showDefault(final Activity activity) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.zhengxin_deflaut, null);
        ImageView iv_dis = (ImageView) view.findViewById(R.id.iv_dis);
        TextView chongshi = (TextView) view.findViewById(R.id.bt_chongshi);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth-200;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        iv_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        chongshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chaxun();
                dialog01.dismiss();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.bt_chaxun:
                if ("1".equals(style)) {
                    chaxun();
                } else if ("0".equals(style)) {
                    chaxunOther();
                }
                break;
            case R.id.tv_wenxintishi:
                if (DialogUtils.IsLogin()) {
                    Intent intent = new Intent(App.context, ZhengXinHistoryActivity.class);
                    startActivity(intent);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.tv_xieyi:
                Intent intent2 = new Intent(App.context, CommonWebView.class);
                intent2.putExtra("baoxianurl", "http://xueyiche.cn/shouquan/shouquan.html");
                intent2.putExtra("weburl", "zhengxinxieyi");
                startActivity(intent2);
                break;
            case R.id.rb_danbao:
                style = "1";
                ll_saomiao.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_other:
                style = "0";
                ll_saomiao.setVisibility(View.GONE);
                break;
            case R.id.zhengxin_saomiao:
                Intent intent1 = new Intent(WriteInfomationActivity.this, TestScanActivity.class);
                intent1.putExtra("style","1");
                startActivityForResult(intent1, 100);
                break;
            default:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                result = data.getStringExtra("result");
                if (!TextUtils.isEmpty(result)) {
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            etWriteYZM.setText(result);
                            etWriteYZM.setSelection(result.length());
                        }
                    });
                }

                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    private void chaxun() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        String WriteName = etWriteName.getText().toString().trim();
        String WriteIdCard = etWriteIdCard.getText().toString().trim();
        String WritePhone = etWritePhone.getText().toString().trim();
        String WriteYZM = etWriteYZM.getText().toString().trim();
//        String WriteBankCard = etWriteBankCard.getText().toString().trim();
        int length = WritePhone.length();

        if (TextUtils.isEmpty(WriteYZM)) {
            showToastShort("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(WriteName)) {
            showToastShort("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(WriteIdCard)) {
            showToastShort("请填写身份证号");
            return;
        }
        if (TextUtils.isEmpty(WritePhone)) {
            showToastShort("请填写手机号");
            return;
        }
//        if (TextUtils.isEmpty(WriteBankCard)) {
//            showToastShort("请填写银行卡号");
//            return;
//        }
        if (length != 11) {
            showToastShort("请填写正确手机号长度");
            return;
        }
        if (!StringUtils.isIdCard(WriteIdCard)) {
            showToastShort("请填写正确的身份证号");
            return;
        }
        if (TextUtils.equals("0", isCheck)) {
            showToastShort("请阅读并同意协议");
            return;
        }

        String encrypt = AES.encrypt(WritePhone);
        String encrypt_idcard = AES.encrypt(WriteIdCard);
        OkHttpUtils.post().url(AppUrl.ZhengXin_ChaXun)
                .addParams("other_type", style)
                .addParams("salesman_number", WriteYZM)
                .addParams("basic_name", WriteName)
                .addParams("credit_sfz", encrypt_idcard)
                .addParams("credit_phone", encrypt)
//                .addParams("bank_card", WriteBankCard)
                .addParams("user_id", user_id)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        final String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            ZhengXinChaXunBean zhengXinChaXunBean = JsonUtil.parseJsonToBean(string, ZhengXinChaXunBean.class);
                            if (zhengXinChaXunBean != null) {
                                final int code = zhengXinChaXunBean.getCode();
                                final String msg = zhengXinChaXunBean.getMsg();
                                ZhengXinChaXunBean.ContentBean content = zhengXinChaXunBean.getContent();
                                if (content != null) {
                                    final String info_url = content.getInfo_url();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200 == code) {
                                                Intent intent = new Intent(App.context, CommonWebView.class);
                                                intent.putExtra("baoxianurl", info_url);
                                                intent.putExtra("weburl", "zhengxinbaogao");
                                                startActivity(intent);
                                            }else if (400==code){
                                                showDefault(WriteInfomationActivity.this);
                                            }
                                            LogUtil.e("danbao_log", msg);
                                            Toast.makeText(WriteInfomationActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    });
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

    private void chaxunOther() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        String WriteName = etWriteName.getText().toString().trim();
        final String WriteIdCard = etWriteIdCard.getText().toString().trim();
        String WritePhone = etWritePhone.getText().toString().trim();
//        String WriteBankCard = etWriteBankCard.getText().toString().trim();
        int length = WritePhone.length();

        if (TextUtils.isEmpty(WriteName)) {
            showToastShort("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(WriteIdCard)) {
            showToastShort("请填写身份证号");
            return;
        }
//        if (TextUtils.isEmpty(WriteBankCard)) {
//            showToastShort("请填写银行卡号");
//            return;
//        }
        if (TextUtils.isEmpty(WritePhone)) {
            showToastShort("请填写手机号");
            return;
        }
        if (length != 11) {
            showToastShort("请填写正确手机号长度");
            return;
        }
        if (!StringUtils.isIdCard(WriteIdCard)) {
            showToastShort("请填写正确的身份证号");
            return;
        }
        if (TextUtils.equals("0", isCheck)) {
            showToastShort("请阅读并同意协议");
            return;
        }

        String encrypt = AES.encrypt(WritePhone);
        final String encrypt_idcard = AES.encrypt(WriteIdCard);
        OkHttpUtils.post().url(AppUrl.ZhengXin_ChaXun_Other)
                .addParams("basic_name", WriteName)
                .addParams("credit_sfz", WriteIdCard)
                .addParams("credit_phone", encrypt)
//                .addParams("bank_card", WriteBankCard)
                .addParams("user_id", user_id)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        final String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            ZhengXinChaXunOtherBean zhengXinChaXunBean = JsonUtil.parseJsonToBean(string, ZhengXinChaXunOtherBean.class);
                            if (zhengXinChaXunBean != null) {
                                final int code = zhengXinChaXunBean.getCode();
                                final String msg = zhengXinChaXunBean.getMsg();
                                ZhengXinChaXunOtherBean.ContentBean content = zhengXinChaXunBean.getContent();
                                if (content != null) {
                                    final String charging = content.getCharging();
                                    final String order_number = content.getOrder_number();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200 == code) {
                                                Intent intent = new Intent(App.context, ZhengXinPay.class);
                                                intent.putExtra("charging",charging);
                                                intent.putExtra("credit_sfz",encrypt_idcard);
                                                intent.putExtra("order_number",order_number);
                                                startActivity(intent);
                                            }
                                            Toast.makeText(WriteInfomationActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    });
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
}
