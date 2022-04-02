package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.bean.OrderInfoBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.DateUtils;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/19.
 */
public class WaitActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_quxia;
    private String user_id;
    private String order_number;
    private long tiam_hm_c;
    private Timer mTimer2;
    private TimerTask mTask2;
    public static WaitActivity instance;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    BitmapDescriptor mCurrentMarker;
    private ImageView iv_back;
    // UI相关
    private static final String CUSTOM_FILE_NAME_WHITE = "custom_map_config_white.json";
    private String order_time1;

    @Override
    protected int initContentView() {
        return R.layout.wait_activity;
    }

    @Override
    protected void initView() {
        instance = this;
        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_WHITE);
        tv_quxia = (TextView) view.findViewById(R.id.tv_quxia);
        iv_back = view.findViewById(R.id.iv_back);
    }

    @Override
    protected void initListener() {
        tv_quxia.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    private String getCustomStyleFilePath(Context context, String customStyleFileName) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String parentPath = null;

        try {
            inputStream = context.getAssets().open("customConfigdir/" + customStyleFileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            parentPath = context.getFilesDir().getAbsolutePath();
            File customStyleFile = new File(parentPath + "/" + customStyleFileName);
            if (customStyleFile.exists()) {
                customStyleFile.delete();
            }
            customStyleFile.createNewFile();

            outputStream = new FileOutputStream(customStyleFile);
            outputStream.write(buffer);
        } catch (IOException e) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                return null;
            }
        }

        return parentPath + "/" + customStyleFileName;
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新代驾", msg)) {
            JieDanActivity.instance.finish();
            Intent intent = new Intent(this, JinXingActivity.class);
            intent.putExtra("order_number", order_number);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }


    @Override
    protected void initData() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        order_number = getIntent().getStringExtra("order_number");


        getOrder();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quxia:
                //取消订单  弹窗确认
                showQuXiaoIndent();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void getOrder() {
        OkHttpUtils.post().url(AppUrl.Get_Order)
                .addParams("order_number", order_number)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    OrderInfoBean orderInfoBean = JsonUtil.parseJsonToBean(string, OrderInfoBean.class);
                    if (orderInfoBean != null) {
                        int code = orderInfoBean.getCode();
                        if (200 == code) {
                            final OrderInfoBean.ContentBean content = orderInfoBean.getContent();
                            if (content != null) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String down_longitude = content.getDown_longitude();
                                        String down_latitude = content.getDown_latitude();
                                        String on_latitude = content.getOn_latitude();
                                        String on_longitude = content.getOn_longitude();
                                        order_time1 = content.getOrder_time1();
                                        String x = PrefUtils.getString(App.context, "x", "");
                                        String y = PrefUtils.getString(App.context, "y", "");
                                        double la = Double.parseDouble(y);
                                        double lo = Double.parseDouble(x);
                                        View inflate = LayoutInflater.from(App.context).inflate(R.layout.mark_view_layout, null);
                                        Bitmap bitmapFromView = getBitmapFromView(inflate);
                                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmapFromView);
                                        View viewTop = LayoutInflater.from(App.context).inflate(R.layout.map_top_bg, null);
                                        // 显示 InfoWindow, 该接口会先隐藏其他已添加的InfoWindow, 再添加新的InfoWindow

                                        final TextView tv_time = (TextView) viewTop.findViewById(R.id.tv_time);
                                        if (mTimer2 == null && mTask2 == null) {
                                            mTimer2 = new Timer();
                                            mTask2 = new TimerTask() {
                                                @Override
                                                public void run() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (!TextUtils.isEmpty(order_time1)) {
                                                                //下单时间
                                                                long stringToHm = DateUtils.getStringToHm(order_time1);
                                                                //当前时间
                                                                long time = System.currentTimeMillis();
                                                                tiam_hm_c = time - stringToHm;
                                                                long hours = tiam_hm_c / (1000 * 60 * 60);
                                                                long minutes = (tiam_hm_c - hours * (1000 * 60 * 60)) / (1000 * 60);
                                                                long miao = (tiam_hm_c - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
                                                                String diffTime = "";
                                                                if (miao < 10 && minutes < 10) {
                                                                    diffTime = "0" + minutes + ":0" + miao;
                                                                } else if (miao > 10 && minutes < 10) {
                                                                    diffTime = "0" + minutes + ":" + miao;
                                                                } else if (miao < 10 && minutes > 10) {
                                                                    diffTime = minutes + ":0" + miao;
                                                                } else if (miao > 10 && minutes > 10) {
                                                                    diffTime = minutes + ":" + miao;
                                                                }
                                                                tv_time.setText(diffTime);
                                                            }
                                                        }
                                                    });
                                                }
                                            };
                                            mTimer2.schedule(mTask2, 0, 1000);
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

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    private Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    //取消订单
    public void showQuXiaoIndent() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.quxiao_indent_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 400;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消接口
                quxiaoOrder(dialog01);
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    public void quxiaoOrder(final AlertDialog dialog01) {
        OkHttpUtils.post().url(AppUrl.Quxiao_Indent)
                .addParams("device_id", LoginUtils.getId(WaitActivity.this))
                .addParams("user_id", user_id)
                .addParams("order_number", order_number)
                .addParams("cancle_reason", "")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                LogUtil.e("22222222222222", string);
                if (!TextUtils.isEmpty(string)) {
                    SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                    int code = successDisCoverBackBean.getCode();
                    final String msg = successDisCoverBackBean.getMsg();
                    if (code == 200) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                dialog01.dismiss();
                                Toast.makeText(WaitActivity.this, msg, Toast.LENGTH_SHORT).show();
                                finish();
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
    }
}
