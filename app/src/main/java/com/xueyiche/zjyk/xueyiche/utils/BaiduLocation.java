package com.xueyiche.zjyk.xueyiche.utils;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xueyiche.zjyk.xueyiche.constants.App;

import java.util.List;

/**
 * Created by Owner on 2016/11/29.
 */
public class BaiduLocation {
    private LocationClient mLocationClient;
    /**
     * 百度地图定位
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); //打开gps android:exported="true"
        option.setAddrType("all");
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setPriority(LocationClientOption.GpsFirst);       //gps
        option.disableCache(false);
        mLocationClient.setLocOption(option);
    }

    public void baiduLocation() {
        mLocationClient = new LocationClient(App.context);
        MyLocationListenner myListener = new MyLocationListenner();
        mLocationClient.registerLocationListener(myListener);
        setLocationOption();
        //开启定位
        mLocationClient.start();
    }

    /**
     * * 百度地图定位
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
            }
            double latitude = location.getLatitude();//纬度
            double longitude = location.getLongitude(); //经度
            PrefUtils.putString(App.context,"y",latitude + "");
            PrefUtils.putString(App.context,"address",location.getAddrStr());
            PrefUtils.putString(App.context,"x",longitude + "");
            String city = location.getCity();
            String province = location.getProvince();
            String district = location.getDistrict();
            PrefUtils.putString(App.context,"city",city);
            PrefUtils.putString(App.context,"district",district);
            PrefUtils.putString(App.context,"province",province);
            LatLng latLng = new LatLng(latitude, longitude);
            initGeoCoder(latLng);
            mLocationClient.stop();
        }
    }
    private void initGeoCoder(final LatLng latLng) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                int adcode = reverseGeoCodeResult.getAdcode();
                List<PoiInfo> poiList = reverseGeoCodeResult.getPoiList();
                if (poiList != null && poiList.size() > 0) {
                    PoiInfo poiInfo = poiList.get(0);
                    if (poiInfo != null) {
                        if (!"天安门".equals(poiInfo.name)) {
                            String xiangxi_address = poiInfo.name;
                            String streetId = poiInfo.getStreetId();
                            int detail = poiInfo.getDetail();
                            String uid = poiInfo.getUid();
                            PrefUtils.putString(App.context, "xiangxi_address", xiangxi_address);
                        }
                    }
                }
            }
        });
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng).pageNum(0).pageSize(100));
    }
}
