package com.xueyiche.zjyk.xueyiche.base;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

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
 * #            Created by 張某人 on 2022/3/29/5:19 下午 .
 * #            com.example.administrator.xuyiche_daijia.base
 * #            xueyiche_daijia
 */
public class GDLocation implements AMapLocationListener {
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    public void startLocation() {
        try {
            mlocationClient = new AMapLocationClient(App.context);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLocation() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                String city = aMapLocation.getCity();
                String cityCode = aMapLocation.getCityCode();
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                String address = aMapLocation.getAddress();
                PrefUtils.putParameter("city", "" + city);
                PrefUtils.putParameter("cityCode", "" + cityCode);
                Log.i("getDrivingNear张斯佳", cityCode);
                PrefUtils.putParameter("lat", "" + latitude);
                PrefUtils.putParameter("lon", "" + longitude);
                PrefUtils.putParameter("y", "" + latitude);
                PrefUtils.putParameter("x", "" + longitude);
                PrefUtils.putParameter("address", "" + address);
                PrefUtils.putParameter("xiangxi_address", "" + address);


                Log.e("GDLocation", "" + city);
                Log.e("GDLocation", "" + cityCode);
                Log.e("GDLocation", "" + latitude);
                Log.e("GDLocation", "" + longitude);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }
}
