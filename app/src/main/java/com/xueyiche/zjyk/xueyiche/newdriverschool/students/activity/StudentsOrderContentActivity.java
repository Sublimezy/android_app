package com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.gyf.immersionbar.ImmersionBar;
import com.qiniu.android.utils.Json;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.StudentsOrderConentBean;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.BaiduLocation;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
//预约详情
public class StudentsOrderContentActivity extends NewBaseActivity {
    @BindView(R.id.title_bar_iv_back)
    ImageView titleBarIvBack;
    @BindView(R.id.title_bar_back)
    RelativeLayout titleBarBack;
    @BindView(R.id.title_bar_title_text)
    TextView titleBarTitleText;
    @BindView(R.id.title_bar_right_text)
    TextView titleBarRightText;
    @BindView(R.id.title_bar_rl)
    RelativeLayout titleBarRl;
    @BindView(R.id.tvOrderState)
    TextView tvOrderState;
    @BindView(R.id.tvKeMuType)
    TextView tvKeMuType;
    @BindView(R.id.tvSubmitDate)
    TextView tvSubmitDate;
    @BindView(R.id.tvTestName)
    TextView tvTestName;
    @BindView(R.id.tvTestTime)
    TextView tvTestTime;
    @BindView(R.id.tvTotalXueShi)
    TextView tvTotalXueShi;
    @BindView(R.id.tvLianCheTime)
    TextView tvLianCheTime;
    @BindView(R.id.tvCoachName)
    TextView tvCoachName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvUpTime)
    TextView tvUpTime;
    @BindView(R.id.tvUpAddress)
    TextView tvUpAddress;
    @BindView(R.id.tvDownTime)
    TextView tvDownTime;
    @BindView(R.id.tvDownAddress)
    TextView tvDownAddress;
    @BindView(R.id.tvCallCoach)
    TextView tvCallCoach;
    @BindView(R.id.tvDaKa)
    TextView tvDaKa;
    private String training_type;
    private String coach_name;
    private String coach_phone;
    private int training_id;


    @Override
    protected int initContentView() {
        return R.layout.student_order_content_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        titleBarRl.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarIvBack.setImageDrawable(getResources().getDrawable(R.mipmap.white_iv_back));
        titleBarTitleText.setText("预约详情");
        titleBarTitleText.setTextColor(getResources().getColor(R.color.white));


        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.selectdrivingtraining)
                    .addParams("training_id", getIntent().getStringExtra("training_id"))
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            Log.e("selectdrivingtraining",""+string);
                            if (!TextUtils.isEmpty(string)) {
                                StudentsOrderConentBean studentsOrderConentBean = JsonUtil.parseJsonToBean(string, StudentsOrderConentBean.class);
                                if (studentsOrderConentBean != null) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            int code = studentsOrderConentBean.getCode();
                                            if (200 == code) {
                                                StudentsOrderConentBean.ContentBean content = studentsOrderConentBean.getContent();
                                                if (content != null) {
                                                    String entry_project = content.getEntry_project();
                                                    String sys_time = content.getSys_time();
                                                    String driving_practice = content.getDriving_practice();
                                                    String practice_time = content.getPractice_time();
                                                    String num_class = content.getNum_class();
                                                    String selected_period = content.getSelected_period();
                                                    coach_name = content.getCoach_name();
                                                    training_id = content.getTraining_id();
                                                    coach_phone = content.getCoach_phone();
                                                    String boarding_time = content.getBoarding_time();
                                                    String pick_up_location = content.getPick_up_location();
                                                    String get_off_time = content.getGet_off_time();
                                                    String drop_off_location = content.getDrop_off_location();
                                                    training_type = content.getTraining_type();
                                                    if (!TextUtils.isEmpty(training_type)) {
                                                        if ("0".equals(training_type)) {
                                                            titleBarRightText.setVisibility(View.VISIBLE);
                                                            titleBarRightText.setText("取消");
                                                            tvDaKa.setText("上车打卡");
                                                            titleBarRightText.setTextColor(getResources().getColor(R.color.white));
                                                        } else if ("1".equals(training_type)){
                                                            tvDaKa.setText("下车打卡");
                                                            titleBarRightText.setVisibility(View.GONE);
                                                        }else if ("3".equals(training_type)){
                                                            tvDaKa.setText("已评价");
                                                            titleBarRightText.setVisibility(View.GONE);
                                                        }else {
                                                            tvDaKa.setText("提交评价");
                                                            titleBarRightText.setVisibility(View.GONE);
                                                        }
                                                    }
                                                    if (!TextUtils.isEmpty(entry_project)) {
                                                        tvKeMuType.setText(entry_project);
                                                    }
                                                    if (!TextUtils.isEmpty(sys_time)) {
                                                        tvSubmitDate.setText(sys_time);
                                                    }
                                                    if (!TextUtils.isEmpty(driving_practice)) {
                                                        tvTestName.setText(driving_practice);
                                                    }
                                                    if (!TextUtils.isEmpty(practice_time)) {
                                                        tvTestTime.setText("（" + practice_time + "）");
                                                    }
                                                    if (!TextUtils.isEmpty(num_class)) {
                                                        tvTotalXueShi.setText("共"+num_class+"学时");
                                                    }
                                                    if (!TextUtils.isEmpty(selected_period)) {
                                                        tvLianCheTime.setText(selected_period);
                                                    }
                                                    if (!TextUtils.isEmpty(coach_name)) {
                                                        tvCoachName.setText(coach_name);
                                                    }
                                                    if (!TextUtils.isEmpty(coach_phone)) {
                                                        tvPhone.setText(coach_phone);
                                                    }
                                                    if (!TextUtils.isEmpty(boarding_time)) {
                                                        tvUpTime.setText(boarding_time);
                                                    }else {
                                                        tvUpTime.setText("--");
                                                    }
                                                    if (!TextUtils.isEmpty(pick_up_location)) {
                                                        tvUpAddress.setText(pick_up_location);
                                                    }else {
                                                        tvUpAddress.setText("--");
                                                    }
                                                    if (!TextUtils.isEmpty(get_off_time)) {
                                                        tvDownTime.setText(get_off_time);
                                                    } else {
                                                        tvDownTime.setText("--");
                                                    }
                                                    if (!TextUtils.isEmpty(drop_off_location)) {
                                                        tvDownAddress.setText(drop_off_location);
                                                    } else {
                                                        tvDownAddress.setText("--");
                                                    }
                                                    tvOrderState.setText("预约成功");

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

    @OnClick({R.id.title_bar_back, R.id.title_bar_right_text, R.id.tvCallCoach, R.id.tvDaKa})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_bar_right_text:
                //TODO:取消预约
                break;
            case R.id.tvCallCoach:
                XueYiCheUtils.CallPhone(StudentsOrderContentActivity.this, "是否拨打" + coach_name + "电话？", coach_phone);
                break;
            case R.id.tvDaKa:
                if ("0".equals(training_type)) {
                    showDaKaUp("" + training_id);
                } else if ("1".equals(training_type)) {
                    showDaKaUp("" + training_id);
                }  else if ("3".equals(training_type)) {
                    Toast.makeText(this, "您已评价！", Toast.LENGTH_SHORT).show();
                } else {
                    showDaKaDown();
                }
                break;

        }
    }

    private void showDaKaDown() {
        final String[] level = {"0"};
        View view = LayoutInflater.from(App.context).inflate(R.layout.daka_pingjia_dia_layout, null);
        RadioButton rbJiHao = view.findViewById(R.id.rbJiHao);
        RadioButton rbLiangHao = view.findViewById(R.id.rbLiangHao);
        RadioButton rbJiaoCha = view.findViewById(R.id.rbJiaoCha);
        RadioButton rbJiaCha = view.findViewById(R.id.rbJiaCha);
        rbJiHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level[0] = "0";
            }
        });
        rbLiangHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level[0] = "1";
            }
        });
        rbJiaoCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level[0] = "2";
            }
        });
        rbJiaCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level[0] = "3";
            }
        });
        EditText etRemark = view.findViewById(R.id.etRemark);
        TextView tvQuXiao = view.findViewById(R.id.tvQuXiao);
        TextView tvSubmitIndent = view.findViewById(R.id.tvSubmitIndent);
        final AlertDialog.Builder builder = new AlertDialog.Builder(StudentsOrderContentActivity.this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 200;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tvQuXiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm!=null) {
                    imm.hideSoftInputFromWindow(etRemark.getWindowToken(), 0); // 强制隐藏键盘
                }
                hideSoftKeyboard(StudentsOrderContentActivity.this);
                dialog01.dismiss();
            }
        });
        tvSubmitIndent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (XueYiCheUtils.IsHaveInternet(App.context)) {
                    String training_id = getIntent().getStringExtra("training_id");
                    Log.e("training_id",training_id);
                    OkHttpUtils.post().url(AppUrl.evaluationboth)
                            .addParams("training_id", ""+training_id)
                            .addParams("coach_to_student", "")
                            .addParams("coach_to_student_detail", "")
                            .addParams("student_to_coach", level[0])
                            .addParams("student_to_coach_detail", "" + etRemark.getText().toString().trim())
                            .addParams("practice_content", "")

                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                        if (successDisCoverBackBean != null) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    int code = successDisCoverBackBean.getCode();
                                                    if (200 == code) {
                                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                        if (imm!=null) {
                                                            imm.hideSoftInputFromWindow(etRemark.getWindowToken(), 0); // 强制隐藏键盘
                                                        }
                                                        hideSoftKeyboard(StudentsOrderContentActivity.this);
                                                        finish();
                                                        dialog01.dismiss();
                                                    }
                                                    showToastLong("" + successDisCoverBackBean.getMsg());
                                                }
                                            });
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
                } else {
                    showToastShort("请检查网络连接");
                }
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }
    public  void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void showDaKaUp(String training_id) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.up_car_dia_layout, null);
        TextView tvContent = view.findViewById(R.id.tvContent);
        if ("0".equals(training_type)) {
            tvContent.setText("您是否进行上车打卡？");
        } else if ("1".equals(training_type)) {
            tvContent.setText("您是否进行下车打卡？");
        }
        TextView tvQuXiao = view.findViewById(R.id.tvQuXiao);
        TextView tvSubmitIndent = view.findViewById(R.id.tvSubmitIndent);
        final AlertDialog.Builder builder = new AlertDialog.Builder(StudentsOrderContentActivity.this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 200;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tvQuXiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        tvSubmitIndent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (XueYiCheUtils.IsHaveInternet(App.context)) {
                    BaiduLocation baiduLocation = new BaiduLocation();
                    baiduLocation.baiduLocation();
                    String address = PrefUtils.getParameter("xiangxi_address");
                    String lan = PrefUtils.getParameter("y");
                    String lon = PrefUtils.getParameter("x");
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("training_id", training_id);
                    if ("0".equals(training_type)) {
                        stringMap.put("pick_up_location", address);
                        stringMap.put("stu_boarding_lon", lon);
                        stringMap.put("stu_boarding_lat", lan);
                    } else if ("1".equals(training_type)) {
                        stringMap.put("drop_off_location", address);
                        stringMap.put("stu_getoff_lon", lon);
                        stringMap.put("stu_getoff_lat", lan);
                    }
                    OkHttpUtils.post().url(AppUrl.updategetonoff)
                            .params(stringMap)
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                        if (successDisCoverBackBean != null) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    int code = successDisCoverBackBean.getCode();
                                                    if (200 == code) {
                                                        getDataFromNet();
                                                        dialog01.dismiss();
                                                        if ("2".equals(training_type)) {
                                                            showDaKaDown();
                                                        }
                                                    }
                                                    showToastLong("" + successDisCoverBackBean.getMsg());
                                                }
                                            });
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
                } else {
                    showToastShort("请检查网络连接");
                }

            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    private void showQuXiao() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.up_car_dia_layout, null);
        TextView tvContent = view.findViewById(R.id.tvContent);
        tvContent.setText("您是否取消预约？");
        TextView tvQuXiao = view.findViewById(R.id.tvQuXiao);
        TextView tvSubmitIndent = view.findViewById(R.id.tvSubmitIndent);
        final AlertDialog.Builder builder = new AlertDialog.Builder(StudentsOrderContentActivity.this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 200;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tvQuXiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        tvSubmitIndent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (XueYiCheUtils.IsHaveInternet(App.context)) {
                    OkHttpUtils.post().url("")
                            .addParams("", "")
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                        if (successDisCoverBackBean != null) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    int code = successDisCoverBackBean.getCode();
                                                    if (200 == code) {
                                                        finish();
                                                        dialog01.dismiss();

                                                    }
                                                    showToastLong("" + successDisCoverBackBean.getMsg());
                                                }
                                            });
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
                } else {
                    showToastShort("请检查网络连接");
                }

            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

}
