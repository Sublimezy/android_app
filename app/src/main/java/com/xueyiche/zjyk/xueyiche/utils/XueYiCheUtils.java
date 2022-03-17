package com.xueyiche.zjyk.xueyiche.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.custom.MapSelectDialogDP;
import com.xueyiche.zjyk.xueyiche.homepage.view.XYCSwipeRefreshLayout;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Owner on 2016/10/26.
 */
public class XueYiCheUtils {
    public static String[] PERMISSION = {Manifest.permission.READ_PHONE_STATE};

    public static boolean isLacksOfPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(
                    App.context, permission) == PackageManager.PERMISSION_DENIED;
        }
        return false;
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
    /**
     * 温馨提示活动积分
     *
     * @param user_id
     * @param context
     */
    public static void getDataNet(String user_id, final Context context, final Activity activity) {
        if (XueYiCheUtils.IsHaveInternet(context)) {
            OkHttpUtils.post().url(AppUrl.Shared_JiFen)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(activity))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                        if (successDisCoverBackBean != null) {
                            int code = successDisCoverBackBean.getCode();
                            final String msg = successDisCoverBackBean.getMsg();
                            if (!TextUtils.isEmpty("" + code)) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
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
    }

    /**
     * 分享app获得积分
     *
     * @param context
     * @param activity
     */
    public static void showShareAppCommon(final Context context, final Activity activity, String title, String titleUrl, String Text, String imageUtl, String url) {
        final String user_id = PrefUtils.getString(App.context, "user_id", "");
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(Text);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imageUtl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getDataNet(user_id, context, activity);
                    }
                });

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
// 启动分享GUI
        oks.show(context);
    }
    /**
     * 分享app获得积分
     *
     * @param context
     * @param activity
     */
    public static void czhFenXiang(final Context context, final Activity activity, String title, String titleUrl,
                                   String Text, String imageUtl, String url, final String like_type, final String refer_id) {
        final String user_id = PrefUtils.getString(App.context, "user_id", "");
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(Text);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imageUtl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getDataNet(user_id, context, activity);
                        shareRecord(user_id, context, activity,like_type,refer_id);
                    }


                });

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
// 启动分享GUI
        oks.show(context);
    }

    private static void shareRecord(String user_id, Context context, Activity activity,String like_type,String refer_id) {
        if (XueYiCheUtils.IsHaveInternet(context)) {
            OkHttpUtils.post().url(AppUrl.Czh_Share_number)
                    .addParams("device_id", LoginUtils.getId(activity))
                    .addParams("like_type",like_type)
                    .addParams("refer_id", refer_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                        if (successDisCoverBackBean != null) {
                            int code = successDisCoverBackBean.getCode();
                            final String msg = successDisCoverBackBean.getMsg();
                            if (!TextUtils.isEmpty("" + code)) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
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

    }

    /**
     * 判断是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Activity getActivityByContext(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        if (context instanceof Activity) {
            return (Activity) context;
        }
        return (Activity) context;
    }
    /**
     * 四舍五入
     *
     * @param money
     * @return
     */
    public static String totalMoney(double money) {
        java.math.BigDecimal bigDec = new java.math.BigDecimal(money);
        double total = bigDec.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(total);
    }

    /**
     * 判断手机上是否安装某程序
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
        //从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    /**
     * 坐标转换，百度地图坐标转换成腾讯地图坐标
     *
     * @param lat 百度坐标纬度
     * @param lon 百度坐标经度
     * @return 返回结果：纬度,经度
     */
    public static double[] map_bd2hx(double lat, double lon) {
        double tx_lat;
        double tx_lon;
        double x_pi = 3.14159265358979324;
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        tx_lon = z * Math.cos(theta);
        tx_lat = z * Math.sin(theta);

        double[] doubles = new double[]{tx_lat, tx_lon};
        return doubles;
    }


    /**
     * 设置刷新属性。 距顶位置 颜色。
     */
    public static void setSwipeLayout(Context context, View view, XYCSwipeRefreshLayout swipeRefreshLayout) {

        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setViewGroup(view);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorOrange, R.color.colorPrimary,
                R.color.colorPink);
        swipeRefreshLayout.setProgressViewEndTarget(true, getImageSize(120, XueYiCheUtils.getWidth(context)));
    }

    /**
     * @param oldSize 图片原本大小
     * @param width   手机宽度大小
     * @return
     */
    public static int getImageSize(int oldSize, int width) {

        return oldSize * width / 720;
    }


    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);

        return metric.widthPixels;
    }


    /**
     * 判断是否有网
     *
     * @param context
     * @return
     */
    public static boolean IsHaveInternet(final Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }




    /**
     * 获取SD存储路径
     *
     * @return
     */
    public static String getSDPATH() {
        String sdpath = Environment.getExternalStorageDirectory() + "/xueyiche/";
        File file = new File(sdpath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return sdpath;
    }

    /**
     * 剪裁的文件保存本地
     *
     * @param bitName
     */
    public static File saveFile(String bitName, Bitmap bitmap) {

        File bitNames = new File(getSDPATH() + bitName);
        try {
            bitNames.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(bitNames);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitNames;

    }

    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public static Bitmap getImageBit(String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bitmap;
    }


    public static String bitmaptoString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            if (bStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 40, bStream);
                byte[] bytes = bStream.toByteArray();
                string = Base64.encodeToString(bytes, Base64.DEFAULT);
            }
        }
        return string;
    }
    /**
     * 创建二维码
     *
     * @param content   content
     * @param widthPix  widthPix
     * @param heightPix heightPix
     * @param logoBm    logoBm
     * @return 二维码
     */
    public static Bitmap createQRCode(String content, int widthPix, int heightPix, Bitmap logoBm) {
        try {
            if (content == null || "".equals(content)) {
                return null;
            }
            // 配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix,
                    heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }
            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap createQRCodeDriver(String content, int widthPix, int heightPix, Bitmap logoBm) {
        try {
            if (content == null || "".equals(content)) {
                return null;
            }
            // 配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix,
                    heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xffffb10c;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }
            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
//            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }

    //获取地点
    public static void getNowLocation(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.READ_PHONE_STATE}, 13);
                    } else {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.READ_PHONE_STATE}, 13);
                    }
                } else {
                    getLocation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getLocation();
        }
    }

    public static void getRecord(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, 13);
                    } else {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, 13);
                    }
                } else {
                    getLocation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getLocation();
        }
    }

    private static void getLocation() {
        BaiduLocation baidu = new BaiduLocation();
        baidu.baiduLocation();
        String city = PrefUtils.getString(App.context, "city", "");
        PrefUtils.putString(App.context, "city", city);
    }

    //显示地图的dialog
    public static void getDiaLocation(Activity activity, String latitude, String longitude, String shop_name, String shop_place) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 13);
                    } else {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 13);
                    }
                } else {
                    openMap(activity, latitude, longitude, shop_name, shop_place);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            openMap(activity, latitude, longitude, shop_name, shop_place);
        }
    }

    /**
     * 打开地图
     *
     * @param activity
     * @param latitude
     * @param longitude
     * @param shop_name
     * @param shop_place
     */
    private static void openMap(Activity activity, String latitude, String longitude, String shop_name, String shop_place) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            PackageManager pm = App.context.getPackageManager();
            //有这个权限，做相应处理
            BaiduLocation baidu = new BaiduLocation();
            baidu.baiduLocation();
            String x = PrefUtils.getString(App.context, "x", "");
            String y = PrefUtils.getString(App.context, "y", "");
            showDialog(activity, latitude, longitude, shop_name, x, y, shop_place);
        } else {
            Toast.makeText(App.context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    private static void showDialog(Activity activity, String latitude, String longitude, String address, String x, String y, String shop_place) {
        if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(x) && !TextUtils.isEmpty(y)) {
            MapSelectDialogDP dialog = new MapSelectDialogDP(activity, R.style.testDialog, latitude, longitude, address, x, y, shop_place);
            dialog.show();
        } else {
            Toast.makeText(App.context, "目的地位置异常", Toast.LENGTH_SHORT).show();
        }
    }

    public static void CallPhone(final Activity activity, String title, final String phone) {
        //显示拨打电话的dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(R.mipmap.logo);
        builder.setTitle(title);
        builder.setMessage(phone);
        //点击空白处弹框不消失
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri telUri = Uri.parse("tel:" + phone);
                Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
                activity.startActivity(intent);
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

    /**
     * 解决 item 显示不全
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
