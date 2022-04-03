package com.xueyiche.zjyk.xueyiche.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.JinjiPhoneActivity;
import com.xueyiche.zjyk.xueyiche.submit.bean.DeleteIndentBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//应用程序Activity管理类：用于Activity管理和应用程序退出 
public class AppUtils {
    private static Stack<Activity> activityStack;
    private static AppUtils instance;
    private static PopupWindow pop = new PopupWindow();
    private static long lastClickTime;
    private static final int MIN_CLICK_DELAY_TIME = 1000;

    public static boolean isFastClick(){
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
    public static void hideSoftKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean isSoftShowing(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom - getSoftButtonsBarHeight(activity) != 0;
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    private AppUtils() {
    }

    /**
     * 单一实例
     */
    public static AppUtils getAppManager() {
        if (instance == null) {
            instance = new AppUtils();
        }
        return instance;
    }
    //协议弹窗
    public static void showXY(Activity activity) {
        //请您先同意学易车APP平台《法律条款》《平台规则》《用户使用协议》     同意  |  查看
        View view = LayoutInflater.from(App.context).inflate(R.layout.quxiao_indent_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_tishi_content = (TextView) view.findViewById(R.id.tv_tishi_content);
        tv_title.setVisibility(View.INVISIBLE);
        tv_queren.setTextColor(Color.parseColor("#ffb10c"));
        tv_queren.setText("查看");
        tv_quxiao.setText("同意");
        tv_tishi_content.setText("请您先同意学易车APP平台《法律条款》《平台规则》《用户使用协议》");
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
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
                PrefUtils.putString(App.context, "dj_xy", "1");
                dialog01.dismiss();

            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrlActivity.forward(activity,"http://xueyiche.cn/xyc/instructions/index.html","2");
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }
//代驾安全弹窗
    public static void showAnQuan(Activity activity) {
        final Dialog dialog = new Dialog(activity, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_anqun_layout, null);
        //初始化控件
        LinearLayout ll_jinji = (LinearLayout) inflate.findViewById(R.id.ll_jinji);
        LinearLayout ll_baojing = (LinearLayout) inflate.findViewById(R.id.ll_baojing);
        RelativeLayout rl_xuzhi = (RelativeLayout) inflate.findViewById(R.id.rl_xuzhi);
        ImageView iv_close_anquan = (ImageView) inflate.findViewById(R.id.iv_close_anquan);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
        iv_close_anquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll_jinji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JinjiPhoneActivity.forward(activity);
                dialog.dismiss();
            }
        });
        ll_baojing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XueYiCheUtils.CallPhone(activity, "拨打报警电话", "110");
            }
        });
        rl_xuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //web页
                UrlActivity.forward(activity,"http://xueyiche.cn/xyc/instructions/instructions.html","2");
            }
        });

    }
    public static void gengXin(Context context, Activity activity) {
        if (isAvilible(context, "com.tencent.android.qqdownloader")) {
            // 市场存在
            launchAppDetail(context, "com.xueyiche.zjyk.xueyiche", "com.tencent.android.qqdownloader");
        } else {
            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.xueyiche.zjyk.xueyiche");
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(it);
        }
    }
    // 判断市场是否存在的方法
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
// 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }


    /**
     *      * 启动到app详情界面
     *      *
     *      * @param appPkg
     *      *            App的包名
     *      * @param marketPkg
     *      *            应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     *      
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public static float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }


    public static boolean isServiceRunning(String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) App.context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 获取版本号
     * 也可使用 BuildConfig.VERSION_NAME 替换
     *
     * @return 版本号
     */
    public static String getVersionName() {
        PackageManager packageManager = App.context.getPackageManager();
        String packageName = App.context.getPackageName();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    /**
     * 获取版本code
     * 也可使用 BuildConfig.VERSION_CODE 替换
     *
     * @return 版本code
     */
    public static int getVersionCode() {
        PackageManager packageManager = App.context.getPackageManager();
        String packageName = App.context.getPackageName();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void showList(final Activity activity, final String name, final String content, final String head) {
        if (!pop.isShowing()) {
            View view = LayoutInflater.from(activity).inflate(R.layout.pop_bottom_layout, null);
            ImageView pop_exit = (ImageView) view.findViewById(R.id.pop_exit);
            CircleImageView pop_head = (CircleImageView) view.findViewById(R.id.pop_head);
            TextView tv_pop_name = (TextView) view.findViewById(R.id.tv_pop_name);
            TextView tv_pop_content = (TextView) view.findViewById(R.id.tv_pop_content);
            pop_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                    }
                }
            });
            tv_pop_name.setText(name);
            tv_pop_content.setText(content);
            if (!TextUtils.isEmpty(head)) {
                Picasso.with(App.context).load(head).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(pop_head);
            }

            pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            pop.setBackgroundDrawable(new BitmapDrawable());
            //添加弹出、弹入的动画
            pop.setAnimationStyle(R.style.Popupwindow);
            pop.setOutsideTouchable(false);
            pop.setContentView(view);
            pop.showAtLocation(view, Gravity.BOTTOM, 0, 250);
        }

    }

    public static void dissList(final Activity activity) {

        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    //防止多次点击
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    //删除订单
    public static void deleteIndent(final Activity activity, final String Url, final String user_id, final String order_number) {
        //删除订单
        View viewDia = LayoutInflater.from(App.context).inflate(R.layout.get_jifen_dialog, null);
        TextView tv_quxiao = (TextView) viewDia.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) viewDia.findViewById(R.id.tv_queren);
        TextView tv_content = (TextView) viewDia.findViewById(R.id.tv_content);
        tv_content.setText("确认返回将取消订单，是否返回？");
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog).setView(viewDia);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth * 2 / 3;
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
                OkHttpUtils.post().url(Url)
                        .addParams("device_id", TextUtils.isEmpty(LoginUtils.getId(activity)) ? "" : LoginUtils.getId(activity))
                        .addParams("user_id", user_id)
                        .addParams("order_number", order_number)
                        .build()
                        .execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws IOException {
                                String string = response.body().string();
                                if (!TextUtils.isEmpty(string)) {
                                    DeleteIndentBean deleteIndentBean = JsonUtil.parseJsonToBean(string, DeleteIndentBean.class);
                                    if (deleteIndentBean != null) {
                                        final int code = deleteIndentBean.getCode();
                                        final String msg = deleteIndentBean.getMsg();
                                        if (!TextUtils.isEmpty("" + code)) {

                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (200 == code) {
                                                        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                                        dialog01.dismiss();
                                                        activity.finish();
                                                    } else {
                                                        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                                        dialog01.dismiss();
                                                    }
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
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

}
