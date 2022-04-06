package com.xueyiche.zjyk.xueyiche.practicecar.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.OrderPracticeBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ZL on 2019/3/11.
 */
public class OrderTrainerActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private LinearLayout llBack;
    private TextView tvTitle, tv_wenxintishi, tv_name, tv_saomao;
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private List<OrderPracticeBean.ContentBean.YuyueListBean> yuyueList;
    private XunLianAdapter xunLianAdapter;
    private AdListView lv_order;
    private RefreshLayout refreshLayout;


    @Override
    protected int initContentView() {
        return R.layout.order_trainer_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_wenxintishi);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_saomao = (TextView) view.findViewById(R.id.tv_saomao);
        lv_order = (AdListView) view.findViewById(R.id.lv_order);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        xunLianAdapter = new XunLianAdapter();

    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
        tv_saomao.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            getDataFromNet();
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                            Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 1500);
            }
        });
    }

    @Override
    protected void initData() {
        tvTitle.setText("预约");
        tv_wenxintishi.setVisibility(View.VISIBLE);
        tv_wenxintishi.setTextColor(getResources().getColor(R.color.title_one));
        tv_wenxintishi.setText("记录");

        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            String user_id = PrefUtils.getParameter("user_id");
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Order_List)
                    .addParams("user_id", user_id)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final OrderPracticeBean orderPracticeBean = JsonUtil.parseJsonToBean(string, OrderPracticeBean.class);
                                if (orderPracticeBean != null) {
                                    final int code = orderPracticeBean.getCode();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200 == code) {
                                                OrderPracticeBean.ContentBean content = orderPracticeBean.getContent();
                                                if (content != null) {
                                                    String driver_name = content.getDriver_name();
                                                    if (!TextUtils.isEmpty(driver_name)) {
                                                        tv_name.setText(driver_name + "教练");
                                                    } else {
                                                        tv_name.setText("暂无教练");
                                                    }
                                                    yuyueList = content.getYuyueList();
                                                    lv_order.setAdapter(xunLianAdapter);

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
        } else {
            Toast.makeText(OrderTrainerActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200:
                final String result = data.getStringExtra("result");
                if (!TextUtils.isEmpty(result)) {
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //显示拨打电话的dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(OrderTrainerActivity.this);
                            builder.setIcon(R.mipmap.logo);
                            builder.setTitle("是否确定扫码上车？");
                            //点击空白处弹框不消失
                            builder.setCancelable(false);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    OkHttpUtils.post().url(AppUrl.Order_SaoMa)
                                            .addParams("user_id", PrefUtils.getParameter("user_id"))
                                            .addParams("ew_code", result)
                                            .build()
                                            .execute(new Callback() {
                                                @Override
                                                public Object parseNetworkResponse(Response response) throws IOException {
                                                    String string = response.body().string();
                                                    if (!TextUtils.isEmpty(string)) {
                                                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                                        if (successDisCoverBackBean != null) {
                                                            final int code = successDisCoverBackBean.getCode();
                                                            final String msg = successDisCoverBackBean.getMsg();
                                                            App.handler.post(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if (200 == code) {
                                                                        getDataFromNet();
                                                                    }
                                                                    Toast.makeText(OrderTrainerActivity.this, msg, Toast.LENGTH_SHORT).show();

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
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    });
                }

                break;

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:

                break;
            case R.id.tv_saomao:
//                Intent intent1 = new Intent(this, TestScanActivity.class);
//                intent1.putExtra("style", "2");
//                startActivityForResult(intent1, 200);
                break;
            default:

                break;
        }
    }

    public class XunLianAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (yuyueList != null) {
                return yuyueList.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            XunLianViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.train_item, null);
                holder = new XunLianViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (XunLianViewHolder) convertView.getTag();
            }
            if (yuyueList != null && yuyueList.size() > 0) {
                OrderPracticeBean.ContentBean.YuyueListBean yuyueListBean = yuyueList.get(position);
                if (yuyueListBean != null) {
                    String km = yuyueListBean.getKm();
                    final int id = yuyueListBean.getId();
                    String xlxm = yuyueListBean.getXlxm();
                    int order_num = yuyueListBean.getOrder_num();
                    int release_num = yuyueListBean.getRelease_num();
                    String jihua_date = yuyueListBean.getJihua_date();
                    final String status = yuyueListBean.getStatus();
                    if (!TextUtils.isEmpty(status)) {
                        if ("待预约".equals(status)) {
                            holder.iv_today_order.setImageResource(R.mipmap.liji_order);
                        } else if ("已约满".equals(status)) {
                            holder.iv_today_order.setImageResource(R.mipmap.order_full);
                        } else if ("已预约".equals(status)) {
                            holder.iv_today_order.setImageResource(R.mipmap.delete_order);
                        }
                        holder.iv_today_order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if ("待预约".equals(status)) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderTrainerActivity.this);
                                    builder.setIcon(R.mipmap.logo);
                                    builder.setTitle("是否预约？");
                                    //点击空白处弹框不消失
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            orderordelete(AppUrl.Order_List_order, "" + id);

                                        }
                                    });
                                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();

                                } else if ("已预约".equals(status)) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderTrainerActivity.this);
                                    builder.setIcon(R.mipmap.logo);
                                    builder.setTitle("是否取消预约？");
                                    //点击空白处弹框不消失
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            orderordelete(AppUrl.Order_List_del, "" + id);
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
                        });


                    }

                    if (!TextUtils.isEmpty(xlxm)) {
                        holder.tv_today_content.setText("科目：" + xlxm);
                    }

                    if (!TextUtils.isEmpty("" + order_num) && !TextUtils.isEmpty("" + release_num)) {
                        holder.tv_order_num.setText(order_num + "/" + release_num);
                    }
                    if (!TextUtils.isEmpty(jihua_date)) {
                        holder.tv_date.setText(jihua_date);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Long second = simpleDateFormat.parse(jihua_date).getTime();
                            Date date = new Date(System.currentTimeMillis());
                            String format = simpleDateFormat.format(date);
                            Long time = simpleDateFormat.parse(format).getTime();
                            int i = StringUtils.dateDiff(time, second);
                            if (i == 0) {
                                holder.tv_day.setText("今天");
                            }
                            if (i == 1) {
                                holder.tv_day.setText("明天");
                            }
                            if (i == 2) {
                                holder.tv_day.setText("后天");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }

            return convertView;
        }

        class XunLianViewHolder {
            private final TextView tv_day;
            private final TextView tv_date;
            private final TextView tv_today_content;
            private final TextView tv_order_num;
            private final ImageView iv_today_order;

            public XunLianViewHolder(View view) {
                tv_day = (TextView) view.findViewById(R.id.tv_day);
                tv_date = (TextView) view.findViewById(R.id.tv_date);
                tv_today_content = (TextView) view.findViewById(R.id.tv_today_content);
                tv_order_num = (TextView) view.findViewById(R.id.tv_order_num);
                iv_today_order = (ImageView) view.findViewById(R.id.iv_today_order);
            }
        }
    }

    private void orderordelete(String url, String id) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post().url(url)
                    .addParams("id", id)
                    .addParams("user_id", user_id)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                if (successDisCoverBackBean != null) {
                                    final int code = successDisCoverBackBean.getCode();
                                    final String msg = successDisCoverBackBean.getMsg();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200 == code) {
                                                getDataFromNet();
                                            }
                                            Toast.makeText(OrderTrainerActivity.this, msg, Toast.LENGTH_SHORT).show();
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
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OrderTrainerActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
