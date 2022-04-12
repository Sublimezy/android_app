package com.xueyiche.zjyk.xueyiche.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.appcompat.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.mine.view.MClearEditText;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by ZL on 2017/7/24.
 */
public class DialogUtils {
    /**
     *
     * @param activity
     * @param jiesheng 节省金额
     * @param shoufei   平台手续费
     * @param order_number  订单号
     * @param shiduprice   实付金额
     */
    public static void showZhiFuTiShi(final Activity activity, String jiesheng, String shoufei, final String order_number , final String shiduprice) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.get_jifen_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText("本次为您节省"+jiesheng+"元，需向您收取"+shoufei+"元支付手续费呦。");
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
                BigDecimal a = new BigDecimal(shiduprice);
                BigDecimal b = new BigDecimal("100");
                BigDecimal multiply = a.multiply(b);
                Intent intentPay = new Intent(App.context, AppPay.class);
                intentPay.putExtra("order_number", order_number);
                intentPay.putExtra("subscription", shiduprice);
                intentPay.putExtra("jifen", multiply+"");
                intentPay.putExtra("pay_style", "car_life");
                activity.startActivity(intentPay);
                dialog01.dismiss();
                activity.finish();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    //推荐人
    public static void showNoTuijian(final Activity activity, String content, final MClearEditText text) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.wenxintishidialog, null);
        Button bt_know = (Button) view.findViewById(R.id.bt_know);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ImageView iv_dis = (ImageView) view.findViewById(R.id.iv_dis);
        iv_dis.setVisibility(View.GONE);
        tv_title.setText("温馨提示");
        tv_content.setText(content);
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

        params.width = screenWidth*2/3;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        //点击空白处弹框不消失
        bt_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
                if (text.getText().length()>0) {
                    text.getText().clear();
                }
            }
        });
        dialog01.setCancelable(false);
        dialog01.show();
    }
    public static void showDaDiBaoXian(final Activity activity, String content) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.wenxintishidialog, null);
        Button bt_know = (Button) view.findViewById(R.id.bt_know);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ImageView iv_dis = (ImageView) view.findViewById(R.id.iv_dis);
        iv_dis.setVisibility(View.GONE);
        tv_title.setText("温馨提示");
        tv_content.setText(content);
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

        params.width = screenWidth - 260;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        //点击空白处弹框不消失
        bt_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
                activity.finish();
            }
        });
        dialog01.setCancelable(false);
        dialog01.show();
    }


    //我的二维码
    public static void showQuanma(final Activity activity, String number,String order_number) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.myerweima, null);
        ImageView iv_my_erweima = (ImageView) view.findViewById(R.id.iv_my_erweima);
        ImageView iv_exit = (ImageView) view.findViewById(R.id.iv_exit);
        TextView tv_order_number = (TextView) view.findViewById(R.id.tv_order_number);
        Bitmap qrCode = XueYiCheUtils.createQRCode(number, 1500, 1500,
                null);
        iv_my_erweima.setImageBitmap(qrCode);
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
//        params.width = screenWidth - 220;
//        params.height = screenHeigh / 2;
        tv_order_number.setText(order_number);
        iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        dialog01.setCancelable(false);
//        dialog01.getWindow().setAttributes(params);//设置大小
        dialog01.show();
    }

    /**
     * 代付款支付成功
     *
     * @param activity
     * @param content
     */
    public static void showPractice(final Activity activity, String content) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.wenxintishidialog, null);
        Button bt_know = (Button) view.findViewById(R.id.bt_know);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ImageView iv_dis = (ImageView) view.findViewById(R.id.iv_dis);
        bt_know.setText("查看订单");
        tv_title.setText("支付成功");
        tv_content.setText("练车时请携带本人驾驶证哦！");
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

        params.width = screenWidth - 330;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        //点击空白处弹框不消失
        bt_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.context, MainActivity.class);
                intent.putExtra("position", 1);
                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                EventBus.getDefault().post(new MyEvent("刷新进行中订单"));
                EventBus.getDefault().post(new MyEvent("刷新代付款订单"));
                EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                EventBus.getDefault().post(new MyEvent("全部订单"));
                activity.startActivity(intent);
                dialog01.dismiss();
                activity.finish();
            }
        });
        iv_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
                activity.finish();
            }
        });
        dialog01.setCancelable(false);
        dialog01.show();
    }



    //提示更新
    public static void showGengXin(final Activity activity) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.tishigengxin, null);
        ImageView iv_gengxin = (ImageView) view.findViewById(R.id.iv_gengxin);
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
        params.width = screenWidth-200;
        params.height = screenHeigh -400;
        dialog01.getWindow().setAttributes(params);//设置大小

        iv_gengxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.gengXin(activity,activity);
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }
    //取消订单
    public static void showQuXiaoIndent(Activity activity) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.quxiao_indent_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
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
                dialog01.dismiss();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    //获取积分
    public static void showGetJiFen(final Activity activity, String string) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.get_jifen_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        BigDecimal jifen = new BigDecimal(string).setScale(0, RoundingMode.CEILING);
        tv_content.setText("恭喜您获得" + jifen + "积分");

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
                dialog01.dismiss();
                activity.finish();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                EventBus.getDefault().post(new MyEvent("刷新进行中订单"));
                EventBus.getDefault().post(new MyEvent("刷新代付款订单"));
                EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                EventBus.getDefault().post(new MyEvent("全部订单"));
                Intent intent = new Intent(App.context, MainActivity.class);
                intent.putExtra("position", 1);
                activity.startActivity(intent);
                dialog01.dismiss();
                activity.finish();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }
    //首次登录
    public static void isFirstLogin(Activity activity,String jifen) {
        View view = LayoutInflater.from(App.context).inflate(R.layout.wenxintishidialog, null);
        Button bt_know = (Button) view.findViewById(R.id.bt_know);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content.setText("恭喜您获得" + jifen + "积分。");
        tv_title.setText("欢迎来到学易车");
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
        //点击空白处弹框不消失
        bt_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
                LoginFirstStepActivity.instance.finish();
                activity.finish();
            }
        });
        dialog01.setCancelable(false);
        dialog01.show();
    }

    public static boolean IsLogin() {
        boolean islogin = PrefUtils.getBoolean(App.context, "ISLOGIN", false);
//        String logintime = PrefUtils.getString(App.context, "LOGINTIME", "0");
//        long time = new Date().getTime();
//        BigDecimal b1 = new BigDecimal(logintime);
//        BigDecimal b2 = new BigDecimal(time);
//        BigDecimal b3 = new BigDecimal(604800000);
//        BigDecimal b4 = b2.subtract(b1);
        if (/*b4.compareTo(b3) <= 0 && */islogin) {
            return true;
        } else {
            return false;
        }

    }


    //列表的弹窗
    public static void showPopListNews(Activity activity) {
        final PopupWindow pop = new PopupWindow(activity);
        if (!pop.isShowing()) {
            View view = activity.getLayoutInflater().inflate(R.layout.pop_bottom_layout,
                    null);
            final LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            pop.setBackgroundDrawable(new BitmapDrawable());
            //添加弹出、弹入的动画
            pop.setAnimationStyle(R.style.Popupwindow);
            pop.setOutsideTouchable(false);
            pop.setContentView(view);
//                ll_popup.clearAnimation();
//        ll_popup.startAnimation(AnimationUtils.loadAnimation(
//                activity, R.anim.activity_translate_in));
            pop.showAtLocation(view, Gravity.BOTTOM, 0, 155);
        }


    }


}
