package com.xueyiche.zjyk.xueyiche.mine.activities.bianji;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.luck.picture.lib.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.UserInfo;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.mine.activities.DataCleanManager;
import com.xueyiche.zjyk.xueyiche.utils.ACache;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by zhanglei on 2016/10/27.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ll_exit;
    private TextView mine_tv_title;
    private String user_id;
    //新版
    private SuperTextView st_name, st_phone, st_bianji_card, st_zhuxiao, st_wechat, st_qq, st_clean, st_exit;

    @Override
    protected int initContentView() {
        return R.layout.bianji_activity;
    }

    @Override
    protected void initView() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        ll_exit = view.findViewById(R.id.title).findViewById(R.id.iv_login_back);
        mine_tv_title = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_title);
        //新版
        //真实姓名
        st_name = (SuperTextView) view.findViewById(R.id.st_name);
        //手机号
        st_phone = (SuperTextView) view.findViewById(R.id.st_phone);
        st_zhuxiao = (SuperTextView) view.findViewById(R.id.st_zhuxiao);
        //身份证号
        st_bianji_card = (SuperTextView) view.findViewById(R.id.st_bianji_card);
        //微信绑定
        st_wechat = (SuperTextView) view.findViewById(R.id.st_wechat);
        //QQ绑定
        st_qq = (SuperTextView) view.findViewById(R.id.st_qq);
        //清除缓存
        st_clean = (SuperTextView) view.findViewById(R.id.st_clean);
        //退出账号
        st_exit = (SuperTextView) view.findViewById(R.id.st_exit);
        ll_exit.setOnClickListener(this);
        mine_tv_title.setText("设置");
        //新版
        st_name.setOnClickListener(this);
        st_phone.setOnClickListener(this);
        st_wechat.setOnClickListener(this);
        st_qq.setOnClickListener(this);
        st_clean.setOnClickListener(this);
        st_exit.setOnClickListener(this);
        st_zhuxiao.setOnClickListener(this);
        st_bianji_card.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.CHAKANGERENXINXI)
                    .addParams("user_id", user_id).build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    UserInfo userInfo = JsonUtil.parseJsonToBean(string, UserInfo.class);
                    if (userInfo != null) {
                        UserInfo.ContentBean content = userInfo.getContent();
                        if (content != null) {
                            final String user_phone = content.getUser_phone();
                            final String user_cards = content.getUser_cards();
                            final String user_name = content.getUser_name();
                            PrefUtils.putString(App.context, "user_phone", user_phone);
                            PrefUtils.putString(App.context, "user_name", user_name);
                            PrefUtils.putString(App.context, "user_cards", user_cards);
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (!TextUtils.isEmpty(user_cards)) {
                                        String decrypt_driver_cards = AES.decrypt(user_cards);
                                        st_bianji_card.setRightString(decrypt_driver_cards);
                                    } else {
                                        st_bianji_card.setRightString("");
                                    }
                                    if (!TextUtils.isEmpty(user_name)) {
                                        st_name.setRightString(user_name);
                                    } else {
                                        st_name.setRightString("");
                                    }
                                    if (!TextUtils.isEmpty(user_phone)) {
                                        String phone = AES.decrypt(user_phone);
                                        st_phone.setRightString(phone);
                                    } else {
                                        st_phone.setRightString("");
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
    protected void onResume() {
        initData();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(App.context, InformationChangeActivity.class);
        switch (v.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.st_name:
                intent1.putExtra("change_information", "name");
                startActivity(intent1);
                break;
            case R.id.st_phone:
                intent1.putExtra("change_information", "phone");
                startActivity(intent1);
                break;
            case R.id.st_bianji_card:
                intent1.putExtra("change_information", "id_card");
                startActivity(intent1);
                break;
            case R.id.st_wechat:
                Toast.makeText(SettingActivity.this, "微信绑定", Toast.LENGTH_SHORT).show();
                break;
            case R.id.st_qq:
                Toast.makeText(SettingActivity.this, "QQ绑定", Toast.LENGTH_SHORT).show();
                break;
            case R.id.st_clean:
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("清除缓存")
                        .setMessage("您是否清除缓存?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataCleanManager.clearAllCache(App.context);
                                ACache.get(SettingActivity.this).clear();
                                showToastShort("您已成功清理缓存。");
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.st_exit:
            outLogin("是否退出登录？","退出成功");
                break;
            case R.id.st_zhuxiao:
                outLogin("是否注销账户？","注销成功");
                break;
        }
    }

    private void outLogin(String title,String toast) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.logo);
        builder.setTitle(title);
        //点击空白处弹框不消失
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //清空数据 退出登录
                PrefUtils.putBoolean(App.context, "ISLOGIN", false);
                PrefUtils.putString(App.context, "user_phone", "");
                PrefUtils.putString(App.context, "user_cards", "");
                PrefUtils.putString(App.context, "driver_cards", "");
                PrefUtils.putString(App.context, "nickname", "");
                PrefUtils.putString(App.context, "user_name", "");
                PrefUtils.putString(App.context, "order_status", "");
                PrefUtils.putString(App.context, "address", "");
                PrefUtils.putString(App.context, "randNum", "");
                PrefUtils.putString(App.context, "sex", "");
                PrefUtils.putString(App.context, "head_img", "");
                PrefUtils.putString(App.context, "user_id", "");
                PrefUtils.putString(App.context, "user_random", "");
                EventBus.getDefault().post(new MyEvent("刷新FragmentLogin"));
                EventBus.getDefault().post(new MyEvent("刷新Fragment"));
                ToastUtils.showToast(SettingActivity.this,""+toast);
                finish();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        builder.show();

    }
}