package com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.pinggu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.TokenBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.bean.PingGuBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.MoneyBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.PingGuPostBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.PingGuSuccedBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.video.VideoInputActivity;
import com.xueyiche.zjyk.xueyiche.pay.bean.AppPayChaXun;
import com.xueyiche.zjyk.xueyiche.pay.bean.ChaXunPayBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.PhotoUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2019/6/3.
 */
public class XYCPingGuActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle, tv_wenxintishi;
    private RecyclerView lv_list;
    private CarLifrRecyclerViewAdapter carLifrRecyclerViewAdapter;
    private List<PingGuBean.MessageBean.DataBean> content;
    PopupWindow pop;
    LinearLayout ll_popup;
    private UploadManager uploadManager;  //七牛SDK的上传管理者
    private String key;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private Bitmap bitmap;
    private String tupian;
    private List<String> content_s = new ArrayList<>();
    private List<String> content_size = new ArrayList<>();
    private List<PingGuPostBean> post_list = new ArrayList<>();
    private Button iv_xiayibu;


    private static final int REQUEST_CODE_FOR_RECORD_VIDEO = 5230;//录制视频请求码
    String path;//视频录制输出地址
    Double videoLength = 0.0;//视频时长
    private String keyvideo;
    private String jsonString_qu;
    private String pay_money;
    private String content_number;


    @Override
    protected int initContentView() {
        return R.layout.pinggu_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_wenxintishi);
        lv_list = (RecyclerView) view.findViewById(R.id.rv_carlife);
        iv_xiayibu = (Button) view.findViewById(R.id.iv_xiayibu);
        tv_wenxintishi.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(XYCPingGuActivity.this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv_list.setLayoutManager(gridLayoutManager);
        uploadManager = new UploadManager();
        EventBus.getDefault().register(this);

    }


    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        iv_xiayibu.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("查询支付成功", msg)) {
            postordernumber();
        }

    }

    @Override
    protected void initData() {
        getDataFromNet();
        getMoney();
        tvTitle.setText("新建评估");
        tv_wenxintishi.setText("历史记录");
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
//            getDataFromNet();
        }


    }

    private void getMoney() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post()
                    .url(AppUrl.Get_Money)
//                    .url("http://172.16.51.61:8082/secondhandcarevaluation/pay_type.do")
                    .addParams("paytype", "4")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final MoneyBean moneyBean = JsonUtil.parseJsonToBean(string, MoneyBean.class);
                                if (moneyBean != null) {
                                    int code = moneyBean.getCode();
                                    if (200 == code) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                MoneyBean.ContentBean content = moneyBean.getContent();
                                                if (content != null) {
                                                    pay_money = content.getPay_money();
                                                    iv_xiayibu.setText("下一步（" + pay_money + "元/次）");
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
        } else {
            Toast.makeText(XYCPingGuActivity.this, "检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.get()
//                    .url("http://172.16.51.61:8082/secondhandcarevaluation/getshotplan.do").
                    .url(AppUrl.PingGu_Image).
                    build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        PingGuBean pingGuBean = JsonUtil.parseJsonToBean(string, PingGuBean.class);

                        if (pingGuBean != null) {
                            int success = pingGuBean.getSuccess();
                            if (1 == success) {
                                final List<PingGuBean.MessageBean> message = pingGuBean.getMessage();
                                if (message != null && message.size() > 0) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            content = message.get(0).getData();
                                            carLifrRecyclerViewAdapter = new CarLifrRecyclerViewAdapter(content, content_s);
                                            for (int i = 0; i < content.size(); i++) {
                                                String ibackground1 = content.get(i).getIbackground();
                                                content_s.add(ibackground1);
                                            }
                                            lv_list.setAdapter(carLifrRecyclerViewAdapter);
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
        } else {
            Toast.makeText(XYCPingGuActivity.this, "请检查网络连接！！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:
                Intent intent = new Intent(App.context, PingGuHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_xiayibu:
                if ("0".equals(pay_money)) {
                    PostShuJu();
                } else {
                    getOrderNumber();
                }
                break;
            default:

                break;
        }
    }

    private void getOrderNumber() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            for (int i = 0; i < content.size(); i++) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
                int itype = content.get(i).getItype();
                int ineed = content.get(i).getIneed();
                String service_url = content_s.get(i);
                String name = content.get(i).getName();
                int sn = content.get(i).getSn();
                Date date = new Date(System.currentTimeMillis());
                PingGuPostBean pingGuPostBean = new PingGuPostBean();
                if (!service_url.contains("xyc_car")) {
                    if (1 == ineed && 1 == itype) {
                        Toast.makeText(XYCPingGuActivity.this, "请选择" + name + "照片！", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (1 == ineed && 2 == itype) {
                        Toast.makeText(XYCPingGuActivity.this, "请选择" + name + "视频！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (i == content.size() - 1) {
                    pingGuPostBean.setUrl("http://xychead.xueyiche.vip/" + keyvideo);
                } else {
                    pingGuPostBean.setUrl(service_url);
                }
                pingGuPostBean.setSn("" + sn);
                pingGuPostBean.setItype("" + itype);
                pingGuPostBean.setTag(name);
                pingGuPostBean.setShottime(simpleDateFormat.format(date));
                for (int i1 = 0; i1 < content_size.size(); i1++) {
                    pingGuPostBean.setSize(content_size.get(i1));
                }
                post_list.add(pingGuPostBean);
            }
            List list = removeDuplicate(post_list);
            Gson g = new Gson();
            jsonString_qu = g.toJson(list);
            String jsonString = g.toJson(content_s);
            LogUtil.e("9999999999999", jsonString_qu);
            showProgressDialog(false);
            String user_phone = PrefUtils.getString(App.context, "user_phone", "");
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            OkHttpUtils.post()
                    .url(AppUrl.Get_ChaXun_Ordernumber)
//                    .url("http://172.16.51.61:8082/secondhandcarevaluation/subordernumber.do")
                    .addParams("user_tel", AES.decrypt(user_phone))
                    .addParams("violation_type", "4")
                    .addParams("user_id", user_id)
                    .addParams("pay_money", pay_money)
                    .addParams("jsonk", jsonString_qu)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final ChaXunPayBean weiZhangPostBean = JsonUtil.parseJsonToBean(string, ChaXunPayBean.class);
                                if (weiZhangPostBean != null) {
                                    int success = weiZhangPostBean.getCode();
                                    if (200 == success) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                content_number = weiZhangPostBean.getContent();
                                                Intent intent_pay = new Intent(App.context, AppPayChaXun.class);
                                                intent_pay.putExtra("pay_style", "chaxun");
                                                intent_pay.putExtra("type", "4");
                                                intent_pay.putExtra("order_number", content_number);
                                                intent_pay.putExtra("subscription", pay_money);
                                                intent_pay.putExtra("jifen", "0");
                                                startActivity(intent_pay);
                                            }
                                        });

                                    } else {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                stopProgressDialog();
                                                Toast.makeText(XYCPingGuActivity.this, "失败", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(XYCPingGuActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }

    }

    public static List removeDuplicate(List list) {
        List listTemp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

    private void PostShuJu() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            for (int i = 0; i < content.size(); i++) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
                int itype = content.get(i).getItype();
                int ineed = content.get(i).getIneed();
                String service_url = content_s.get(i);
                String name = content.get(i).getName();
                int sn = content.get(i).getSn();
                Date date = new Date(System.currentTimeMillis());
                PingGuPostBean pingGuPostBean = new PingGuPostBean();
                if (!service_url.contains("xyc_car")) {
                    if (1 == ineed && 1 == itype) {
                        Toast.makeText(XYCPingGuActivity.this, "请选择" + name + "照片！", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (1 == ineed && 2 == itype) {
                        Toast.makeText(XYCPingGuActivity.this, "请选择" + name + "视频！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (i == content.size() - 1) {
                    pingGuPostBean.setUrl("http://xychead.xueyiche.vip/" + keyvideo);
                } else {
                    pingGuPostBean.setUrl(service_url);
                }
                pingGuPostBean.setSn("" + sn);
                pingGuPostBean.setItype("" + itype);
                pingGuPostBean.setTag(name);
                pingGuPostBean.setShottime(simpleDateFormat.format(date));
                for (int i1 = 0; i1 < content_size.size(); i1++) {
                    pingGuPostBean.setSize(content_size.get(i1));
                }
                post_list.add(pingGuPostBean);
            }
            List list = removeDuplicate(post_list);
            Gson g = new Gson();
            jsonString_qu = g.toJson(list);
            String jsonString = g.toJson(content_s);
            LogUtil.e("qqqqqqqqqqqqqq", jsonString_qu);
            postvoid(jsonString_qu);
        } else {
            Toast.makeText(XYCPingGuActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
        }
    }

    private void postvoid(String jsonString_qu) {
        showProgressDialog(false);
        String user_phone = PrefUtils.getParameter("user_phone");
        OkHttpUtils.post().url(AppUrl.PingGu_Post)
                .addParams("user_tel", AES.decrypt(user_phone))
//                .addParams("user_tel", "18945057021")
                .addParams("jsonk", jsonString_qu)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        LogUtil.e("aaaaaaaaaaa", string);
                        if (!TextUtils.isEmpty(string)) {
                            PingGuSuccedBean pingGuSuccedBean = JsonUtil.parseJsonToBean(string, PingGuSuccedBean.class);
                            if (pingGuSuccedBean != null) {
                                int success = pingGuSuccedBean.getSuccess();
                                if (1 == success) {
                                    PingGuSuccedBean.MessageBean message = pingGuSuccedBean.getMessage();
                                    if (message != null) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(XYCPingGuActivity.this, "成功！！", Toast.LENGTH_SHORT).show();
                                                finish();
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
    private void postordernumber() {
        showProgressDialog(false);
        OkHttpUtils.post()
                .url(AppUrl.Post_Ordernumber)
//                .url("http://172.16.51.61:8082/secondhandcarevaluation/callback_iosinfo.do")
                .addParams("order_number",content_number)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        LogUtil.e("aaaaaaaaaaa", string);
                        if (!TextUtils.isEmpty(string)) {
                            PingGuSuccedBean pingGuSuccedBean = JsonUtil.parseJsonToBean(string, PingGuSuccedBean.class);
                            if (pingGuSuccedBean != null) {
                                int success = pingGuSuccedBean.getSuccess();
                                if (1 == success) {
                                    PingGuSuccedBean.MessageBean message = pingGuSuccedBean.getMessage();
                                    if (message != null) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(XYCPingGuActivity.this, "成功！！", Toast.LENGTH_SHORT).show();
                                                finish();
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

    public class CarLifrRecyclerViewAdapter extends RecyclerView.Adapter<CarLifrRecyclerViewAdapter.ViewHolder> {
        private List<PingGuBean.MessageBean.DataBean> carLifeServiceTypes;
        private List<String> content;

        public CarLifrRecyclerViewAdapter(List<PingGuBean.MessageBean.DataBean> carLifeServiceTypes, List<String> content) {
            this.carLifeServiceTypes = carLifeServiceTypes;
            this.content = content;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_title;
            RoundImageView iv_bg, iv_photo_type;

            public ViewHolder(View v) {
                super(v);
            }

        }

        @Override
        public int getItemCount() {
            if (carLifeServiceTypes != null) {
                return carLifeServiceTypes.size();
            } else {
                return 0;
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinggu_image_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.iv_bg = (RoundImageView) v.findViewById(R.id.iv_bg);
            holder.iv_photo_type = (RoundImageView) v.findViewById(R.id.iv_photo_type);
            holder.tv_title = (TextView) v.findViewById(R.id.tv_title);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final String service_url = content.get(position);
            if (!TextUtils.isEmpty(service_url)) {
                Picasso.with(App.context).load(service_url).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(holder.iv_bg);
            }
            int itype = carLifeServiceTypes.get(position).getItype();
            int sn = carLifeServiceTypes.get(position).getSn();
            String name = carLifeServiceTypes.get(position).getName();


            if (service_url.contains("xyc_car")) {
                holder.iv_photo_type.setVisibility(View.GONE);
            } else {
                holder.iv_photo_type.setVisibility(View.VISIBLE);
                if (itype == 1) {
                    holder.iv_photo_type.setImageResource(R.mipmap.pinggu_photo);
                } else if (itype == 2) {
                    holder.iv_photo_type.setImageResource(R.mipmap.pinggu_video);
                }
            }

            if (carLifeServiceTypes != null) {
                PingGuBean.MessageBean.DataBean dataBean = carLifeServiceTypes.get(position);
                if (dataBean != null) {
                    final String service_name = dataBean.getName();

                    final String iexample = dataBean.getIexample();
                    if (!TextUtils.isEmpty(service_name)) {
                        holder.tv_title.setText(service_name);
                    }
                    holder.iv_photo_type.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDia(iexample, position, content);

                        }
                    });
                    holder.iv_bg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDia(iexample, position, content);

                        }
                    });


                }
            }
        }
    }

    private void showDia(String url, final int position, final List<String> c) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.pinggu_dia_layout, null);
        Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
        ImageView iv_content = (ImageView) view.findViewById(R.id.iv_content);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);

        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 200;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog01.getWindow().setAttributes(params);//设置大小
        Picasso.with(App.context).load(url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(iv_content);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
                int i = position + 1;
                int size = content_s.size();
                LogUtil.e("12222222222", i + "------" + size);
                if (i == content_s.size()) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        int REQUEST_CODE_CONTACT = 101;
                        String[] permissions = {Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                        //验证是否许可权限
                        for (String str : permissions) {
                            if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                                //申请权限
                                requestPermissions(permissions, REQUEST_CODE_CONTACT);
                                return;
                            }

                        }
                    }
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            VideoInputActivity.startActivityForResult(XYCPingGuActivity.this, REQUEST_CODE_FOR_RECORD_VIDEO, VideoInputActivity.Q720);
                        }
                    });
                    LogUtil.e("dadadadad", "视频");
                } else {
                    tupian = "" + i;
                    showPopupWindow();
                    LogUtil.e("qeqeqeqe", "照片");
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(
                            XYCPingGuActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                }
            }
        });
        dialog01.show();
    }

    public void showPopupWindow() {
        pop = new PopupWindow(XYCPingGuActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
                null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        TextView bt1 = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView bt2 = (TextView) view.findViewById(R.id.tv_photo_book);
        TextView bt3 = (TextView) view.findViewById(R.id.tv_exit);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                autoObtainCameraPermission();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                autoObtainStoragePermission();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (XueYiCheUtils.hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(XYCPingGuActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {

            }
        }
    }

    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    private int output_X = 480;
    private int output_Y = 960;

    //获取视频大小
    private String getFileSizeVideo(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return "0";
        } else {
            long size = f.length();
            return (size / 1024f) / 1024f + "";
        }
    }

    int progress = 0;

    private int getProgress(String source) {
        // Duration: 00:00:22.50, start: 0.000000, bitrate: 13995 kb/s

        //progress frame=   28 fps=0.0 q=24.0 size= 107kB time=00:00:00.91 bitrate= 956.4kbits/s
        if (source.contains("start: 0.000000")) {
            return progress;
        }
        Pattern p = Pattern.compile("00:\\d{2}:\\d{2}");
        Matcher m = p.matcher(source);
        if (m.find()) {
            //00:00:00
            String result = m.group(0);
            String temp[] = result.split(":");
            Double seconds = Double.parseDouble(temp[1]) * 60 + Double.parseDouble(temp[2]);

            if (0 != videoLength) {
                Log.v("进度长度", "current second = " + seconds + "/videoLength=" + videoLength);
                progress = (int) (seconds * 100 / videoLength);

                return progress;
            }
            return progress;
        }
        return progress;
    }

    public File saveFile(Bitmap bm, String fileName) throws IOException {//将Bitmap类型的图片转化成file类型，便于上传到服务器
        String path = Environment.getExternalStorageDirectory() + "/Ask";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_RECORD_VIDEO && resultCode == RESULT_CANCELED) {

        }
        if (requestCode == REQUEST_CODE_FOR_RECORD_VIDEO && resultCode == RESULT_OK) {
            String path = data.getStringExtra(VideoInputActivity.INTENT_EXTRA_VIDEO_PATH);
            Log.e("地址:", path);
            //根据视频地址获取缩略图
            this.path = path;
            Calendar calendar = Calendar.getInstance();
            long timeInMillis = calendar.getTimeInMillis();
            key = "xyc_car_" + String.valueOf(timeInMillis) + "_video.jpg";
            keyvideo = "xyc_car_" + String.valueOf(timeInMillis) + "_video.mp4";
            Bitmap bitmap_Video = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
            try {
                File file = saveFile(bitmap_Video, key);
                getTokenFromServiceVideo(path, keyvideo);
                getTokenFromService(file, key, content_s.size() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileSizeVideo = getFileSizeVideo(path);
            content_size.add(fileSizeVideo);
            LogUtil.e("视频大小", fileSizeVideo);
            LogUtil.e("视频长度", getProgress(path) + "");
        }
        switch (requestCode) {
            case CODE_CAMERA_REQUEST://拍照完成回调
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 3, 2, output_X, output_Y, CODE_RESULT_REQUEST);
                break;
            case CODE_GALLERY_REQUEST://访问相册完成回调
                if (XueYiCheUtils.hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
                    PhotoUtils.cropImageUri(this, newUri, cropImageUri, 3, 2, output_X, output_Y, CODE_RESULT_REQUEST);
                } else {
                }
                break;
            case CODE_RESULT_REQUEST:
                bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                Calendar calendar = Calendar.getInstance();
                long timeInMillis = calendar.getTimeInMillis();
                if (bitmap != null) {
                    if (tupian.equals("1")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_login_one.jpg";
                    } else if (tupian.equals("2")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_login_thiree.jpg";
                    } else if (tupian.equals("3")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_drivercard_up.jpg";
                    } else if (tupian.equals("4")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_left_front_45.jpg";
                    } else if (tupian.equals("5")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_fadongjicang.jpg";
                    } else if (tupian.equals("6")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_fadongjigai.jpg";
                    } else if (tupian.equals("7")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_left_front_jianzhengqi.jpg";
                    } else if (tupian.equals("8")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_right_front_jianzhengqi.jpg";
                    } else if (tupian.equals("9")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_qiandangfeng_logo.jpg";
                    } else if (tupian.equals("10")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_right_ABzhu.jpg";
                    } else if (tupian.equals("11")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_right_BCzhu.jpg";
                    } else if (tupian.equals("12")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_right_behind_45.jpg";
                    } else if (tupian.equals("13")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_houbeixiang.jpg";
                    } else if (tupian.equals("15")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_houdangfeng_logo.jpg";
                    } else if (tupian.equals("14")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_houbeixianggai.jpg";
                    } else if (tupian.equals("16")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_left_BCzhu.jpg";
                    } else if (tupian.equals("17")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_left_ABzhu.jpg";
                    } else if (tupian.equals("18")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_carmingpai.jpg";
                    } else if (tupian.equals("19")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_zhongkongall.jpg";
                    } else if (tupian.equals("20")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_login_five.jpg";
                    } else if (tupian.equals("21")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_futu2.jpg";
                    } else if (tupian.equals("22")) {
                        key = "xyc_car_" + String.valueOf(timeInMillis) + "_futu3.jpg";
                    }
                    try {
                        long fileSize = getFileSize(fileCropUri);
                        content_size.add(Integer.parseInt(tupian) - 1, "" + fileSize);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getTokenFromService(fileCropUri, key, Integer.parseInt(tupian) - 1);
                    carLifrRecyclerViewAdapter.notifyDataSetChanged();

                }
                break;
        }
    }


    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    private void shangchuanHead(File file, String key, String token, final int position) {
        showProgressDialog(false);
        uploadManager.put(file, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    content_s.remove(position);
                    content_s.add(position, "http://xychead.xueyiche.vip/" + key);
                    carLifrRecyclerViewAdapter.notifyDataSetChanged();
                    Log.e("qiniu", "Upload Success");
                    stopProgressDialog();
                } else {
                    Log.e("qiniu", "Upload Fail");
                    String error = info.error;
                    Log.e("error", error);
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }

        }, null);

    }

    private void shangchuanHeadVideo(String file, String key, String token) {
        showProgressDialog(false);
        uploadManager.put(file, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    Log.e("qiniu", "Upload Success");
                    stopProgressDialog();
                } else {
                    Log.e("qiniu", "Upload Fail");
                    String error = info.error;
                    Log.e("error", error);
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }

        }, null);

    }

    private void getTokenFromService(final File upFile, final String key, final int position) {
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.TOUXIANG)
                .addParams("key", key)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    if (!TextUtils.isEmpty(string)) {
                        TokenBean token_bean = JsonUtil.parseJsonToBean(string, TokenBean.class);
                        final String token = token_bean.getContent().getToken();
                        if (!TextUtils.isEmpty(token)) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    stopProgressDialog();
                                    shangchuanHead(upFile, key, token, position);
                                }
                            });

                        }
                    }
                }
                return null;
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    private void getTokenFromServiceVideo(final String upFile, final String key) {
        showProgressDialog(false);
        OkHttpUtils.post().url(AppUrl.TOUXIANG)
                .addParams("key", key)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    if (!TextUtils.isEmpty(string)) {
                        TokenBean token_bean = JsonUtil.parseJsonToBean(string, TokenBean.class);
                        final String token = token_bean.getContent().getToken();
                        if (!TextUtils.isEmpty(token)) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    stopProgressDialog();
                                    shangchuanHeadVideo(upFile, key, token);
                                }
                            });

                        }
                    }
                }
                return null;
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
