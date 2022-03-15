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
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
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
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.bean.PingGuBean;
import com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.bean.PingGuNoBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.OncePostSuccessBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.PingGuOncePostBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.video.VideoInputActivity;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Owner on 2019/5/31.
 */
public class OrderContentNoActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private MaxRecyclerView lv_list;
    private AdListView lv_no;
    private NoPhotoAdapter noPhotoAdapter;
    private CarLifrRecyclerViewAdapter carLifrRecyclerViewAdapter;
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
    private static final int REQUEST_CODE_FOR_RECORD_VIDEO = 5230;//录制视频请求码
    String path;//视频录制输出地址
    Double videoLength = 0.0;//视频时长
    private String keyvideo;
    private String jsonString_qu;
    private TextView tv_bohui_time, tv_tradeprice, tv_number;
    private ImageView iv_tijiao;
    private List<PingGuOncePostBean> post_list = new ArrayList<>();
    private List<PingGuNoBean.MessageBean.DataBean> data;
    private int position_need = 0;
    private String tid;
    private String gid;

    @Override
    protected int initContentView() {
        return R.layout.pinggu_order_content_no_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        lv_list = (MaxRecyclerView) view.findViewById(R.id.lv_list);
        lv_no = (AdListView) view.findViewById(R.id.lv_no);
        tv_bohui_time = (TextView) view.findViewById(R.id.tv_bohui_time);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        tv_tradeprice = (TextView) view.findViewById(R.id.tv_tradeprice);
        iv_tijiao = (ImageView) view.findViewById(R.id.iv_tijiao);
        iv_tijiao.setOnClickListener(this);
        tv_login_back.setText("订单详情");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(OrderContentNoActivity.this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv_list.setLayoutManager(gridLayoutManager);
//        carLifrRecyclerViewAdapter = new CarLifrRecyclerViewAdapter(data);
        uploadManager = new UploadManager();

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
                    key = "xyc_car_" + String.valueOf(timeInMillis) + "_change.jpg";
                    getTokenFromService(fileCropUri, key, position_need);
                    noPhotoAdapter.notifyDataSetChanged();

                }
                break;
        }
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

    private void shangchuanHead(File file, String key, String token, final int position) {
        showProgressDialog(false);
        uploadManager.put(file, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    content_s.remove(position);
                    content_s.add(position, "http://xychead.xueyiche.vip/" + key);
                    noPhotoAdapter.notifyDataSetChanged();
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

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tid = getIntent().getStringExtra("tid");
        LogUtil.e("123456",tid);
        getDataFromNet(tid);
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

    public void getDataFromNet(String tid) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.PingGu_Out)
                    .addParams("tid", tid)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    LogUtil.e("aaaaaaaaaaaaaa",string);
                    if (!TextUtils.isEmpty(string)) {
                        processData(string);
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

    private void processData(String string) {
        final PingGuNoBean pingGuNoBean = JsonUtil.parseJsonToBean(string, PingGuNoBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (pingGuNoBean != null) {
                    int success = pingGuNoBean.getSuccess();
                    if (1 == success) {
                        PingGuNoBean.MessageBean message = pingGuNoBean.getMessage();
                        if (message != null) {
                            data = message.getData();
                            for (int i = 0; i < data.size(); i++) {
                                String ibackground1 = data.get(i).getIbackground();
                                content_s.add(ibackground1);
                            }
                            noPhotoAdapter = new NoPhotoAdapter(data);
                            lv_no.setAdapter(noPhotoAdapter);
                            int id = message.getId();
                            gid = message.getGid();
                            tv_number.setText(id + "");
                            int tradeprice = message.getTradeprice();
                            tv_tradeprice.setText(tradeprice + "");
                            String reject_time = message.getReject_time();
                            tv_bohui_time.setText(reject_time + "");
//                            List<PingGuYesBean.MessageBean.FileInfoBean> content = messageBean.getFile_info();
//                            carLifrRecyclerViewAdapter = new CarLifrRecyclerViewAdapter(content);
//                            lv_list.setAdapter(carLifrRecyclerViewAdapter);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_tijiao:
                //提交
                PostShuJu();
                break;
        }
    }

    private void PostShuJu() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            for (int i = 0; i < data.size(); i++) {
                int itype = data.get(i).getItype();
                String service_url = content_s.get(i);
                String name = data.get(i).getTag();
                int sn = data.get(i).getSn();
                PingGuOncePostBean pingGuPostBean = new PingGuOncePostBean();
                if (2 == itype) {
                    pingGuPostBean.setUrl("http://xychead.xueyiche.vip/" + keyvideo);
                } else if(1==itype){
                    pingGuPostBean.setUrl(service_url);
                }
                pingGuPostBean.setSn("" + sn);
                pingGuPostBean.setItype("" + itype);
                pingGuPostBean.setTag(name);
                post_list.add(pingGuPostBean);

            }
            List list = removeDuplicate(post_list);
            Gson g = new Gson();
            jsonString_qu = g.toJson(list);
            String jsonString = g.toJson(content_s);
            LogUtil.e("qqqqqqqqqqqqqq", jsonString_qu);
            postvoid(jsonString_qu);


        } else {
            Toast.makeText(OrderContentNoActivity.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
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

    private void postvoid(String jsonString_qu) {
        showProgressDialog(false);
        String user_phone = PrefUtils.getParameter("user_phone");
        OkHttpUtils.post().url(AppUrl.PingGu_Once_post)
                .addParams("user_tel", AES.decrypt(user_phone))
                .addParams("tid", tid)
                .addParams("gid", gid)
                .addParams("jsonk", jsonString_qu)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        LogUtil.e("aaaaaaaaaaa", string);
                        if (!TextUtils.isEmpty(string)) {
                            OncePostSuccessBean pingGuSuccedBean = JsonUtil.parseJsonToBean(string, OncePostSuccessBean.class);
                            if (pingGuSuccedBean != null) {
                                int success = pingGuSuccedBean.getSuccess();
                                if (1 == success) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(OrderContentNoActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
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
    }

    public class CarLifrRecyclerViewAdapter extends RecyclerView.Adapter<CarLifrRecyclerViewAdapter.ViewHolder> {
        private List<PingGuBean.MessageBean.DataBean> carLifeServiceTypes;

        public CarLifrRecyclerViewAdapter(List<PingGuBean.MessageBean.DataBean> carLifeServiceTypes) {
            this.carLifeServiceTypes = carLifeServiceTypes;
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
            holder.iv_photo_type.setVisibility(View.VISIBLE);
            if (carLifeServiceTypes != null) {
                PingGuBean.MessageBean.DataBean dataBean = carLifeServiceTypes.get(position);
                if (dataBean != null) {
                    final String service_name = dataBean.getName();
                    String ibackground = dataBean.getIbackground();
                    if (!TextUtils.isEmpty(service_name)) {
                        holder.tv_title.setText(service_name);
                    }
                    if (!TextUtils.isEmpty(service_name)) {
                        Picasso.with(App.context).load(ibackground).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(holder.iv_bg);
                    }
                }
            }
        }
    }

    public class NoPhotoAdapter extends BaseAdapter {
        private List<PingGuNoBean.MessageBean.DataBean> data;

        public NoPhotoAdapter(List<PingGuNoBean.MessageBean.DataBean> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            if (data != null) {
                return data.size();
            } else {
                return 0;
            }

        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            ViewHolerNo viewHolerSheQu = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.pinggu_content_no_list_item, null);
                viewHolerSheQu = new ViewHolerNo(view);
                view.setTag(viewHolerSheQu);
            } else {
                viewHolerSheQu = (ViewHolerNo) view.getTag();
            }
            if (data != null) {
                PingGuNoBean.MessageBean.DataBean dataBean = data.get(i);
                String rejectreason = dataBean.getRejectreason();
                String tag = dataBean.getTag();
                final String iexample = dataBean.getIexample();
                final int itype = dataBean.getItype();
                String aa = "驳回原因 :  " + "<font color='#dc0d0d'>" + rejectreason + "</font>";
                Spanned spanned = Html.fromHtml(aa);
                if (!TextUtils.isEmpty(spanned)) {
                    viewHolerSheQu.tv_no_content.setText(spanned);
                }
                if (!TextUtils.isEmpty(tag)) {
                    viewHolerSheQu.tv_title.setText(tag);
                }
                final String service_url = content_s.get(i);

                if (service_url.contains("xyc_car")) {
                    viewHolerSheQu.iv_photo_type.setVisibility(View.GONE);
                } else {
                    viewHolerSheQu.iv_photo_type.setVisibility(View.VISIBLE);
                    if (itype == 1) {
                        viewHolerSheQu.iv_photo_type.setImageResource(R.mipmap.pinggu_photo);
                    } else if (itype == 2) {
                        viewHolerSheQu.iv_photo_type.setImageResource(R.mipmap.pinggu_video);
                    }
                }
                if (!TextUtils.isEmpty(service_url)) {
                    Picasso.with(App.context).load(service_url).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(viewHolerSheQu.iv_bg);
                }
                viewHolerSheQu.iv_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDia(iexample, i, itype);
                        position_need = i;

                    }
                });
                viewHolerSheQu.iv_photo_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDia(iexample, i, itype);
                        position_need = i;

                    }
                });

            }


            return view;
        }


        class ViewHolerNo {

            private final ImageView iv_bg;
            private final ImageView iv_photo_type;
            private final TextView tv_no_content;
            private final TextView tv_title;

            public ViewHolerNo(View view) {
                iv_bg = (ImageView) view.findViewById(R.id.iv_bg);
                iv_photo_type = (ImageView) view.findViewById(R.id.iv_photo_type);
                tv_no_content = (TextView) view.findViewById(R.id.tv_no_content);
                tv_title = (TextView) view.findViewById(R.id.tv_title);
            }
        }
    }

    private void showDia(String url, final int position, final int itype) {
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
                if (itype == 2) {
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
                            VideoInputActivity.startActivityForResult(OrderContentNoActivity.this, REQUEST_CODE_FOR_RECORD_VIDEO, VideoInputActivity.Q720);
                        }
                    });
                    LogUtil.e("dadadadad", "视频");
                } else {
                    showPopupWindow();
                    LogUtil.e("qeqeqeqe", "照片");
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(
                            OrderContentNoActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                }
            }
        });
        dialog01.show();
    }

    public void showPopupWindow() {
        pop = new PopupWindow(OrderContentNoActivity.this);
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
                    imageUri = FileProvider.getUriForFile(OrderContentNoActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
}
