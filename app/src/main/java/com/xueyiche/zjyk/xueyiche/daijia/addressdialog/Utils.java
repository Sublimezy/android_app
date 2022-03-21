package com.xueyiche.zjyk.xueyiche.daijia.addressdialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 基础工具类，包含了单位转换等
 */
public class Utils {
    //Glide加载图片为圆形
    public static void loadCircleImageView(Context context, String url, ImageView iv, boolean isShowFrame, int color) {
        if (context != null) {
            if (context instanceof Activity) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!((Activity) context).isDestroyed()) {
                        if (isShowFrame) {
                            loadCircleBorder(context, url, iv, color);
                        } else {
                            loadCircle(context, url, iv);
                        }
                    }
                } else {
                    if (isShowFrame) {
                        loadCircleBorder(context, url, iv, color);
                    } else {
                        loadCircle(context, url, iv);
                    }
                }
            } else {
                if (isShowFrame) {
                    loadCircleBorder(context, url, iv, color);
                } else {
                    loadCircle(context, url, iv);
                }
            }
        }
    }

    private static void loadCircle(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).
                diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(iv);
    }

    private static void loadCircleBorder(Context context, String url, ImageView iv, int color) {
        Glide.with(context).load(url).
                diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                transform(new GlideCircleTransformWithBorder(context, 2, color)).
                into(iv);
    }
    /**
     * 弧度换算成角度
     */
    public static double radianToDegree(double radian) {
        return radian * 180 / Math.PI;
    }

    /**
     * 角度换算成弧度
     */
    public static double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }

    /**
     * 获取变长参数最大的值
     */
    public static int getMaxValue(Integer... array) {
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(list.size() - 1);
    }

    /**
     * 获取变长参数最小的值
     */
    public static int getMinValue(Integer... array) {
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(0);
    }

    /**
     * 两个点之间的距离
     */
    public static float getTwoPointsDistance(PointF pf1, PointF pf2) {
        float disX = pf2.x - pf1.x;
        float disY = pf2.y - pf1.y;
        return (float) Math.sqrt(disX * disX + disY * disY);
    }


    public static int dp2px (Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue*scale + 0.5f);
    }
}