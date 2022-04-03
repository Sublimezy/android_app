package com.xueyiche.zjyk.xueyiche.utils;

import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;

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
public class CircleBuilder {
    //180, 3, 145, 255
    //10, 0, 0, 180
    public static final int STROKE_COLOR = Color.argb(180, 3, 145, 200);
    public static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    //224,236,237
    //233,241,242


    public static Circle addCircle(LatLng latlng, double radius, AMap aMap) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        return aMap.addCircle(options);
    }

    public static int getStrokeColor(int alpha) {

        return Color.argb(alpha, 3, 145, 200);

    }


    public static int getFillColor(int alpha) {

        return Color.argb(alpha, 0, 0, 180);

    }
}
