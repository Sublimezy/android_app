package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by zhanglei on 2016/10/29.
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle;
    private EditText ed_think_content, ed_think_number;
    private Button bt_think_submit;
    private TextView text_number;
    private final int charMaxNum = 200; // 允许输入的字数
    private String talk;

    @Override
    protected int initContentView() {

        return R.layout.feed_back_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.feed_back_include).findViewById(R.id.ll_bianji_back);
        llBack.setOnClickListener(this);

        tvTitle = (TextView) view.findViewById(R.id.feed_back_include).findViewById(R.id.mine_tv_title);
        ed_think_content = (EditText) view.findViewById(R.id.ed_think_content);
        ed_think_number = (EditText) view.findViewById(R.id.ed_think_number);
        bt_think_submit = (Button) view.findViewById(R.id.bt_think_submit);
        text_number = (TextView) view.findViewById(R.id.text_number);
        bt_think_submit.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("意见反馈");
        ed_think_content.addTextChangedListener(new EditChangedListener());
        text_number.setText(0 + "/" + charMaxNum);
        Intent intent = getIntent();
        talk = intent.getStringExtra("talk");
        if ("liuyankefu".equals(talk)) {
            tvTitle.setText("留言客服");
            SpannableString ss = new SpannableString("联系电话 (必填，便于我们联系您~)");//定义hint的值
            ed_think_number.setHint(new SpannableString(ss));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bianji_back:
                finish();
                break;
            case R.id.bt_think_submit:
                String content = ed_think_content.getText().toString();
                String phone = ed_think_number.getText().toString().trim();

                if (XueYiCheUtils.IsHaveInternet(App.context)) {
                    if ("yijianfankui".equals(talk)) {
                        if (!TextUtils.isEmpty(content)) {
                            if (!TextUtils.isEmpty(phone)) {
                                if (!StringUtils.isMobileNumber(phone)) {
                                    showToastShort("请填写正确的手机号");
                                } else {
                                    //提交服务器
                                    sendText(content, phone);
                                }
                            }else {
                                sendText(content, phone);
                            }
                        } else {
                            showToastShort("请填写反馈内容!!");
                        }
                    } else if ("liuyankefu".equals(talk)) {
                        if (!TextUtils.isEmpty(content)) {
                            if (!TextUtils.isEmpty(phone)) {
                                if (!StringUtils.isMobileNumber(phone)) {
                                    showToastShort("请填写正确的手机号");
                                } else {
                                    OkHttpUtils.post().url(AppUrl.YIJIANFANKUI)
                                            .addParams("feed_message_type", "1")
                                            .addParams("feedback", content)
                                            .addParams("user_phone", phone)
                                            .build().execute(new Callback() {
                                        @Override
                                        public Object parseNetworkResponse(Response response) throws IOException {
                                            return null;
                                        }

                                        @Override
                                        public void onError(Request request, Exception e) {

                                        }

                                        @Override
                                        public void onResponse(Object response) {
                                            dialog();

                                        }


                                    });
                                }

                            } else {
                                showToastShort("请填写手机号");
                            }
                        } else {
                            showToastShort("请填写反馈内容!!");
                        }


                    }
                } else {
                    showToastShort("请检查网络状态");
                }


                break;

        }
    }

    private void sendText(String content, String phone) {
        OkHttpUtils.post().url(AppUrl.YIJIANFANKUI)
                .addParams("feed_message_type", "0")
                .addParams("feedback", content)
                .addParams("user_phone", phone)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                return null;
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) {
                showToastShort("反馈成功");
                finish();
            }
        });
    }



    //提示的弹窗
    private void dialog() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.liuyan_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(FeedBackActivity.this, R.style.Dialog).setView(view);
        ImageView liuyan_end = (ImageView) view.findViewById(R.id.liuyan_end);
        liuyan_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //点击空白处弹框不消失
        dialog.setCancelable(false);
        dialog.create().show();
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
                showToastShort("最多可输入200个字");

            }

        }
    }
}
