package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.ProcessResultUtil;


/**
 * Created by Administrator on 2019/9/18.
 */
public class DaiJiaoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title, tv_phone_book;
    private ImageView ll_exam_back, iv_daijiao;
    private EditText et_phone, et_Name;
    private String phoneName;
    private ProcessResultUtil mProcessResultUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daijiao_activity);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initData();
    }

    private void initView() {
        tv_title = findViewById(R.id.title).findViewById(R.id.tv_title);
        ll_exam_back = findViewById(R.id.title).findViewById(R.id.iv_login_back);
        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_phone_book = (TextView) findViewById(R.id.tv_phone_book);
        iv_daijiao = (ImageView) findViewById(R.id.iv_daijiao);
        et_Name = (EditText) findViewById(R.id.et_Name);
        ll_exam_back.setOnClickListener(this);
        tv_phone_book.setOnClickListener(this);
        iv_daijiao.setOnClickListener(this);
        mProcessResultUtil = new ProcessResultUtil(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_login_back:
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
                String phone = et_phone.getText().toString().trim();
                String name = et_Name.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(DaiJiaoActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.length() != 11) {
                    Toast.makeText(DaiJiaoActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("daijiao_phone", phone);
                intent.putExtra("daijiao_name", !TextUtils.isEmpty(name) ? name : "");
                setResult(444, intent);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProcessResultUtil != null) {
            mProcessResultUtil.release();
        }

    }

    private void res() {
        mProcessResultUtil.requestPermissions(new String[]{
                Manifest.permission.READ_CONTACTS,
        }, mStartLiveRunnable);
    }

    private Runnable mStartLiveRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent1 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent1, 1);
        }
    };
    private Intent mIntent;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户成功授予权限
                getContacts(mIntent);
            } else {
                Toast.makeText(this, "你拒绝了此应用对读取联系人权限的申请！", Toast.LENGTH_SHORT).show();
            }
        }
    }

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

            et_Name.setText(name);
            et_phone.setText("" + phoneNumber);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("daijiao_phone", "");
            intent.putExtra("daijiao_name", "");
            setResult(444, intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }

    private void initData() {
        tv_title.setText("乘车人信息");
        String daijiao_phone = PrefUtils.getString(DaiJiaoActivity.this, "daijiao_phone", "");
        et_phone.setText(daijiao_phone);

    }
}
