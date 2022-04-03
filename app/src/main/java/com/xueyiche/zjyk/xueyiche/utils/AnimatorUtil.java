package com.xueyiche.zjyk.xueyiche.utils;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

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
 * #            Created by 張某人 on 2022/4/3/10:15 上午 .
 * #            com.xueyiche.zjyk.xueyiche.utils
 * #            xueyiche5.0
 */
public class AnimatorUtil {
    private static AnimatorUtil animatorUtil;

    public static AnimatorUtil getInstance() {
        if (animatorUtil == null) {
            animatorUtil = new AnimatorUtil();
        }
        return animatorUtil;
    }

    /**
     * View缩放动画
     *
     * @param view
     * @param time
     * @param count
     */
    public void scaleViewAnimator(View view, int time, int count) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(time);
        scaleAnimation.setRepeatCount(count);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }

    public static ValueAnimator getValueAnimator(int min, int max, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(min, max);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animatorUpdateListener);
        //无限循环
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //从头开始动画
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.start();
        return valueAnimator;
    }
}
