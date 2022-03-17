package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.PracticeGoingBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.Timer;

/**
 * Created by ZL on 2018/2/2.
 */
public class PracticeIndentContent extends BaseActivity implements View.OnClickListener {
    private SeekBar ribbon_speed_seekbar;
    private TextView seek_bar_time, tv_driver_name, tv_start_time, tv_end_time,
            tv_total_time, tv_kaishi_time, tv_driver_miaoshu;
    Timer timer = new Timer();
    private int recLen = 0;
    private int total = 0;
    private String user_id;
    private String order_number;
    private ImageView iv_driver_head, iv_indent_phone;
    private String driver_phone;
    private String now_time;
    private String practice_hours;
    private Button bt_queren;
    private String act_start_time;
    private String user_confirm;
    private LinearLayout ll_exam_back;


    @Override
    protected int initContentView() {
        return R.layout.practice_indent_content;
    }

    @Override
    protected void initView() {
        ribbon_speed_seekbar = (SeekBar) view.findViewById(R.id.ribbon_speed_seekbar);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.indent_details_include).findViewById(R.id.ll_exam_back);
        seek_bar_time = (TextView) view.findViewById(R.id.seek_bar_time);
        tv_driver_name = (TextView) view.findViewById(R.id.tv_driver_name);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        tv_kaishi_time = (TextView) view.findViewById(R.id.tv_kaishi_time);
        tv_total_time = (TextView) view.findViewById(R.id.tv_total_time);
        tv_driver_miaoshu = (TextView) view.findViewById(R.id.tv_driver_miaoshu);
        iv_driver_head = (ImageView) view.findViewById(R.id.iv_driver_head);
        iv_indent_phone = (ImageView) view.findViewById(R.id.iv_indent_phone);
        bt_queren = (Button) view.findViewById(R.id.bt_queren);
        ribbon_speed_seekbar.setEnabled(false);
        ll_exam_back.setOnClickListener(this);
        iv_indent_phone.setOnClickListener(this);
        bt_queren.setOnClickListener(this);
    }
    @Override
    protected void initListener() {

    }

    private void userEndPracticing() {
        String x = PrefUtils.getString(App.context, "x", "");
        String y = PrefUtils.getString(App.context, "y", "");
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.User_End_Practicing)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("last_user_longitude",x )
                    .addParams("last_user_latitude",y )
                    .addParams("order_number", TextUtils.isEmpty(order_number) ? "" : order_number)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                if (successDisCoverBackBean != null) {
                                    final int code = successDisCoverBackBean.getCode();
                                    if (!TextUtils.isEmpty("" + code)) {

                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (200 == code) {
                                                        finish();
                                                    }else {
                                                        showToastShort("操作失败");
                                                    }
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


    @Override
    protected void initData() {
        order_number = getIntent().getStringExtra("order_number");
        getDataFromNet();
    }


    private void getDataFromNet() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Practice_Going_Indent_Content)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", TextUtils.isEmpty(user_id) ? "" : user_id)
                    .addParams("order_number", TextUtils.isEmpty(order_number) ? "" : order_number)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final PracticeGoingBean practiceGoingBean = JsonUtil.parseJsonToBean(string, PracticeGoingBean.class);
                                if (practiceGoingBean != null) {
                                    int code = practiceGoingBean.getCode();
                                    if (!TextUtils.isEmpty("" + code)) {
                                        if (200 == code) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    PracticeGoingBean.ContentBean content = practiceGoingBean.getContent();
                                                    if (content != null) {
                                                        String head_img = content.getHead_img();
                                                        String driver_name = content.getDriver_name();
                                                        driver_phone = content.getDriver_phone();
                                                        String start_time = content.getStart_time();
                                                        String end_time = content.getEnd_time();
                                                        practice_hours = content.getPractice_hours();
                                                        String driver_infomation = content.getDriver_infomation();
                                                        act_start_time = content.getAct_start_time();
                                                        now_time = content.getNow_time();
                                                        user_confirm = content.getUser_confirm();
                                                        String about_begin_time = content.getAbout_begin_time();
                                                        if (!TextUtils.isEmpty(driver_name)) {
                                                            tv_driver_name.setText(driver_name);
                                                        }
                                                        if (!TextUtils.isEmpty(about_begin_time)) {
                                                            tv_kaishi_time.setText("距离训练："+about_begin_time);
                                                        }
                                                        if (!TextUtils.isEmpty(driver_infomation)) {
                                                            tv_driver_miaoshu.setText(driver_infomation);
                                                        }
                                                        if (!TextUtils.isEmpty(start_time)) {
                                                            tv_start_time.setText(start_time);
                                                        }
                                                        if (!TextUtils.isEmpty(end_time)) {
                                                            tv_end_time.setText(end_time);
                                                        }
                                                        if (!TextUtils.isEmpty(practice_hours)) {
                                                            tv_total_time.setText("共"+practice_hours+"小时");
                                                        }
                                                        if (!TextUtils.isEmpty(head_img)) {
                                                            Picasso.with(App.context).load(head_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_driver_head);
                                                        }
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
                            stopProgressDialog();
                        }

                        @Override
                        public void onResponse(Object response) {
                            stopProgressDialog();
                        }

                    });
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_indent_phone:
                if (TextUtils.isEmpty(driver_phone)) {
                    XueYiCheUtils.CallPhone(PracticeIndentContent.this, "是否拨打教练电话", driver_phone);
                }
                break;
            case R.id.bt_queren:
                //确认完成订单结束
                if ("1".equals(user_confirm)) {
                    userEndPracticing();
                }else {
                    showToastShort("等待教练结束训练");
                }
                break;
        }
    }
}
