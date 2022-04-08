package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/9/18.
 */
public class DaiJiaoActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_Name)
    EditText etName;

    public static void forward(Activity activity, int code) {
        Intent intent = new Intent(activity, DaiJiaoActivity.class);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("乘车人信息");
    }

    @Override
    protected int initContentView() {
        return R.layout.daijiao_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
    }

    private void res() {
        AndPermission.with(DaiJiaoActivity.this)
                .permission(
                        Manifest.permission.READ_CONTACTS)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Intent intent1 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent1, 1);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).start();
    }

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    //申请授权，第一个参数为要申请用户授权的权限；第二个参数为requestCode 必须大于等于0，主要用于回调的时候检测，匹配特定的onRequestPermissionsResult。
                    //可以从方法名requestPermissions以及第二个参数看出，是支持一次性申请多个权限的，系统会通过对话框逐一询问用户是否授权。
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);

                } else {
                    //如果该版本低于6.0，或者该权限已被授予，它则可以继续读取联系人。
                    getContacts(data);
                }
                break;
        }
    }

    @SuppressLint("Range")
    private void getContacts(Intent data) {
        if (data == null) {
            return;
        }

        Uri contactData = data.getData();
        if (contactData == null) {
            return;
        }
        String name = "";
        String phoneNumber = "";
        Uri contactUri = data.getData();
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String hasPhone = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String id = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            if (hasPhone.equalsIgnoreCase("1")) {
                hasPhone = "true";
            } else {
                hasPhone = "false";
            }
            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + id, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }
            cursor.close();
            etName.setText(name);
            if (!TextUtils.isEmpty(phoneNumber)) {
                String replace = phoneNumber.replace(" ", "");
                etPhone.setText("" + replace);
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("daijiao_phone", "");
            intent.putExtra("daijiao_name", "");
            setResult(333, intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }

    @OnClick({R.id.ll_common_back, R.id.tv_phone_book, R.id.iv_daijiao})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_common_back:
                intent.putExtra("daijiao_phone", "");
                intent.putExtra("daijiao_name", "");
                setResult(333, intent);
                finish();
                break;
            case R.id.tv_phone_book:
                res();
                break;
            case R.id.iv_daijiao:
                //保存电话号码
                String phone = etPhone.getText().toString().trim();
                String name = etName.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    com.luck.picture.lib.utils.ToastUtils.showToast(DaiJiaoActivity.this,"请输入手机号");
                    return;
                }
                if (phone.length() != 11) {
                    com.luck.picture.lib.utils.ToastUtils.showToast(DaiJiaoActivity.this,"请输入正确的手机号");
                    return;
                }
                intent.putExtra("daijiao_phone", phone);
                intent.putExtra("daijiao_name", !TextUtils.isEmpty(name) ? name : "");
                setResult(333, intent);
                finish();
                break;
        }
    }
}

