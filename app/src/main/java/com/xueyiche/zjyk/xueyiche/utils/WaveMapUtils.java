package com.xueyiche.zjyk.xueyiche.utils;

import android.animation.ValueAnimator;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

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
 * #            Created by 張某人 on 2022/4/3/10:17 上午 .
 * #            com.xueyiche.zjyk.xueyiche.utils
 * #            xueyiche5.0
 */
public class WaveMapUtils {
    private List<Circle> circleList;//圆集合

    private ValueAnimator valueAnimator;//动画工具

    /**
     * 添加水波纹效果
     *
     * @param latLng 要展示扩散效果的点经纬度
     * AMap aMap：高德地图
     */
    public void addWaveAnimation(LatLng latLng, AMap aMap) {
        circleList = new ArrayList<>();
        int radius = 50;
        for (int i = 0; i < 4; i++) {
            radius = radius + 50 * i;
            circleList.add(CircleBuilder.addCircle(latLng, radius, aMap));
        }
        valueAnimator = AnimatorUtil.getValueAnimator(0, 50, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                for (int i = 0; i < circleList.size(); i++) {
                    int nowradius = 50 + 50 * i;
                    Circle circle = circleList.get(i);
                    double radius1 = value + nowradius;
                    circle.setRadius(radius1);
                    int strokePercent = 200;
                    int fillPercent = 20;
                    if (value < 25) {
                        strokePercent = value * 8;
                        fillPercent = value * 20 / 50;
                    } else {
                        strokePercent = 200 - value * 4;
                        fillPercent = 20 - value * 20 / 50;
                    }
                    if (circle.getFillColor() != CircleBuilder.getStrokeColor(strokePercent)) {
                        circle.setStrokeColor(CircleBuilder.getStrokeColor(strokePercent));
                        circle.setFillColor(CircleBuilder.getFillColor(fillPercent));
                    }
                }
            }
        });
    }

    /**
     * 移除水波纹动画
     */
    public void removeCircleWave() {
        if (null != valueAnimator) {
            valueAnimator.cancel();
        }
        if (circleList != null) {
            for (Circle circle : circleList) {
                circle.remove();
            }
            circleList.clear();
        }
    }

}
