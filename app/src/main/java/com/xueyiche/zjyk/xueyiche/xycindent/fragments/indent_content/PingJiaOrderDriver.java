package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by Owner on 2016/12/20.
 */
public class PingJiaOrderDriver extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle, tv_name;
    private Button bt_submit;
    private RatingBar rb_one, rb_jiaoxue, rb_service;
    private FrameLayout fl_jiashizheng;
    private float i2, i3, total;
    private String order_number, head_img, driver_name;
    private ImageView iv_head;
    PopupWindow pop;
    private EditText ed_think_content;
    private TextView text_number, tv_gone;
    private final int charMaxNum = 50; // 允许输入的字数
    private String thinkContent, driver_id;

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
        fl_jiashizheng = (FrameLayout) view.findViewById(R.id.fl_jiashizheng);
        fl_jiashizheng.setVisibility(View.GONE);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        text_number = (TextView) view.findViewById(R.id.text_number);
        tv_gone = (TextView) view.findViewById(R.id.tv_gone);
        tv_gone.setVisibility(View.GONE);
        ed_think_content = (EditText) view.findViewById(R.id.ed_think_content);
        tvTitle.setText("评价");


    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        ed_think_content.addTextChangedListener(new EditChangedListener());
        text_number.setText(0 + "/" + charMaxNum);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        driver_name = intent.getStringExtra("driver_name");
        head_img = intent.getStringExtra("head_img");
        driver_id = intent.getStringExtra("driver_id");
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
                thinkContent = ed_think_content.getText().toString();
                if (TextUtils.isEmpty(thinkContent)) {
                    Toast.makeText(PingJiaOrderDriver.this, "请填写评价内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                int length = thinkContent.length();
                if (length < 5) {
                    Toast.makeText(PingJiaOrderDriver.this, "评论内容最少五个字", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitPingJia();

                break;

        }
    }


    private void submitPingJia() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        float v = total / 2;
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String id = LoginUtils.getId(PingJiaOrderDriver.this);
            OkHttpUtils.post().url(AppUrl.Order_PingJia)
                    .addParams("order_number", order_number)
                    .addParams("device_id",id)
                    .addParams("all_evaluate", "" + v)
                    .addParams("user_id", user_id)
                    .addParams("driver_id", driver_id)
                    .addParams("service_attitude", "" + i3)
                    .addParams("technological_level", "" + i2)
                    .addParams("content", thinkContent)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        SuccessDisCoverBackBean messageComeBack = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
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
        } else {
            showToastShort("请给个星评呗");
        }
    }
}
