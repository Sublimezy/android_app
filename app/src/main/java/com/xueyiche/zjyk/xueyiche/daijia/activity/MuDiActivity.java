package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.bean.ChangYongListBean;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.homepage.activities.location.LocationActivity;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.POIAdapter;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2019/9/16.
 */
public class MuDiActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_finish, tv_city, tv_gongsi, tv_jia, tv_gongsi_address, tv_jia_address;
    private ListView lv_lishi, lv_poi;
    private LinearLayout ll_home, ll_company;
    private String substring;
    private String name_mudi = "哈尔滨";
    private EditText et_mudi;
    private PoiCitySearchOption poiCitySearchOption;
    private PoiSearch poiSearch;
    private List<PoiInfo> allPoi;
    private String name_jia;
    private String address_jia;
    private String latitude_jia;
    private String longitude_jia;
    private String name_gs;
    private String address_gs;
    private String latitude_gs;
    private String longitude_gs;
    private String type;
    private View head_lv_mudi;
    private String user_id;
    private List<ChangYongListBean.ContentBean.ListDataBean> listData;

    @Override
    protected int initContentView() {
        return R.layout.mudi_activity;
    }

    @Override
    protected void initView() {
        head_lv_mudi = LayoutInflater.from(App.context).inflate(R.layout.head_lv_mudi, null);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_finish = (TextView) view.findViewById(R.id.tv_finish);
        ll_home = (LinearLayout) head_lv_mudi.findViewById(R.id.ll_home);
        ll_company = (LinearLayout) head_lv_mudi.findViewById(R.id.ll_company);
        tv_gongsi = (TextView) head_lv_mudi.findViewById(R.id.tv_gongsi);
        tv_gongsi_address = (TextView) head_lv_mudi.findViewById(R.id.tv_gongsi_address);
        tv_jia = (TextView) head_lv_mudi.findViewById(R.id.tv_jia);
        tv_jia_address = (TextView) head_lv_mudi.findViewById(R.id.tv_jia_address);
        et_mudi = (EditText) view.findViewById(R.id.et_mudi);
        lv_lishi = (ListView) view.findViewById(R.id.lv_lishi);
        lv_poi = (ListView) view.findViewById(R.id.lv_poi);
        poiSearch = PoiSearch.newInstance();

    }

    @Override
    protected void initListener() {
        tv_finish.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        ll_company.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        et_mudi.addTextChangedListener(new EditChangedListener());
        lv_lishi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int a, long id) {
                int position;
                if ("qi".equals(type)) {
                    position = a;
                } else {
                    position = a - 1;
                }
                if (listData!=null&&listData.size()>0) {
                    ChangYongListBean.ContentBean.ListDataBean listDataBean = listData.get(position);
                    if (listDataBean!=null) {
                        String address = listDataBean.getAddress();
                        String latitude = listDataBean.getLatitude();
                        String name = listDataBean.getName();
                        String longitude = listDataBean.getLongitude();
                        Intent intent = new Intent();
                        intent.putExtra("name", name);
                        intent.putExtra("address", address);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        if ("qi".equals(type)) {
                            setResult(111, intent);
                        } else {
                            setResult(222, intent);
                        }
                        finish();
                    }
                }
            }
        });
        lv_poi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (allPoi != null) {
//                    String area_open = "南岗区，道理区，道外区，香坊区，松北区";
                    PoiInfo poiInfo = allPoi.get(position);
                    String name = poiInfo.name;
//                    String area = poiInfo.area;
                    String address = poiInfo.address;
                    LatLng location = poiInfo.location;
//                    if (area_open.contains(area)) {
                        if (location != null) {
                            double latitude = location.latitude;//纬度
                            double longitude = location.longitude;//经度
                            Intent intent = new Intent();
                            intent.putExtra("name", name);
                            intent.putExtra("address", address);
                            intent.putExtra("latitude", latitude+"");
                            intent.putExtra("longitude", longitude+"");
                            if ("qi".equals(type)) {
                                setResult(111, intent);
                            } else {
                                setResult(222, intent);
                            }
                            finish();
                        }
//                    }else {
//                        showToastShort("当前区域暂未开通");
//                    }
                } else {
                    showToastShort("请重新选择");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        poiSearch.destroy();
    }

    @Override
    protected void initData() {
        user_id = PrefUtils.getString(this, "user_id", "");
        getChaongYong();
        type = getIntent().getStringExtra("type");
        if ("zhong".equals(type)) {
            lv_lishi.addHeaderView(head_lv_mudi);
        }
        XueYiCheUtils.getNowLocation(this);
        String city = PrefUtils.getString(App.context, "city", "");
        if (!TextUtils.isEmpty(city)) {
            if (city.contains("市")) {
                substring = city.substring(0, city.length() - 1);
            }
            tv_city.setText(substring);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_finish:
                Intent intent1 = new Intent();
                intent1.putExtra("name", "");
                intent1.putExtra("address", "");
                intent1.putExtra("latitude", "");
                intent1.putExtra("longitude", "");
                if ("qi".equals(type)) {
                    setResult(111, intent1);
                } else {
                    setResult(222, intent1);
                }
                finish();
                break;
            case R.id.tv_city:
                //选择城市
                Intent intent2 = new Intent(this, LocationActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_home:
                //添加常用地址1
                String jia = tv_jia.getText().toString();
                if (!"家".equals(jia)) {
                    Intent intent3 = new Intent(this, ChangYongMuDiActivity.class);
                    intent3.putExtra("type", "jia");
                    startActivityForResult(intent3, 333);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("name", name_jia);
                    intent.putExtra("address", address_jia);
                    intent.putExtra("latitude", latitude_jia);
                    intent.putExtra("longitude", longitude_jia);
                    setResult(222, intent);
                    finish();
                }
                break;
            case R.id.ll_company:
                //添加常用地址2
                String gs = tv_gongsi.getText().toString();
                if (!"公司".equals(gs)) {
                    Intent intent4 = new Intent(this, ChangYongMuDiActivity.class);
                    intent4.putExtra("type", "gs");
                    startActivityForResult(intent4, 444);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("name", name_gs);
                    intent.putExtra("address", address_gs);
                    intent.putExtra("latitude", latitude_gs);
                    intent.putExtra("longitude", longitude_gs);
                    setResult(222, intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("name", "");
            intent.putExtra("address", "");
            intent.putExtra("latitude", "");
            intent.putExtra("longitude", "");
            if ("qi".equals(type)) {
                setResult(111, intent);
            } else {
                setResult(222, intent);
            }
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }

    public void getChaongYong() {
        showProgressDialog(false);
        OkHttpUtils.post()
                .url(AppUrl.ChangYong_Address)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            ChangYongListBean changyong = JsonUtil.parseJsonToBean(string, ChangYongListBean.class);
                            if (changyong != null) {
                                int code = changyong.getCode();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        final ChangYongListBean.ContentBean content = changyong.getContent();
                                        if (content != null) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    listData = content.getListData();
                                                    lv_lishi.setAdapter(new MudiAdapter());
                                                    ChangYongListBean.ContentBean.MapHomeBean mapHome = content.getMapHome();
                                                    ChangYongListBean.ContentBean.MapCompanyBean mapCompany = content.getMapCompany();
                                                    if (mapHome != null) {
                                                         name_jia = mapHome.getName();
                                                         address_jia = mapHome.getAddress();
                                                         latitude_jia = mapHome.getLatitude();
                                                         longitude_jia = mapHome.getLongitude();
                                                        if (TextUtils.isEmpty(name_jia)||TextUtils.isEmpty(address_jia)||TextUtils.isEmpty(latitude_jia)||TextUtils.isEmpty(longitude_jia)) {
                                                            tv_jia.setText("添加");
                                                            tv_jia_address.setText("设置常用地址");
                                                        }else {
                                                            tv_jia.setText("家");
                                                            tv_jia_address.setText(name_jia);
                                                        }
                                                    }
                                                    if (mapCompany != null) {
                                                        name_gs = mapCompany.getName();
                                                        address_gs = mapCompany.getAddress();
                                                        latitude_gs = mapCompany.getLatitude();
                                                        longitude_gs = mapCompany.getLongitude();
                                                        if (TextUtils.isEmpty(name_gs)||TextUtils.isEmpty(address_gs)||TextUtils.isEmpty(latitude_gs)||TextUtils.isEmpty(longitude_gs)) {
                                                            tv_gongsi.setText("添加");
                                                            tv_gongsi_address.setText("设置常用地址");
                                                        }else {
                                                            tv_gongsi.setText("公司");
                                                            tv_gongsi_address.setText(name_gs);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
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


    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String key = s.toString();
            if (key.length() > 0) {
                lv_lishi.setVisibility(View.GONE);
                lv_poi.setVisibility(View.VISIBLE);
            } else {
                lv_lishi.setVisibility(View.VISIBLE);
                lv_poi.setVisibility(View.GONE);
            }
            poiCitySearchOption = new PoiCitySearchOption()
                    .city(name_mudi)
                    .keyword(key);
            poiSearch.searchInCity(poiCitySearchOption);
            poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                @Override
                public void onGetPoiResult(PoiResult poiResult) {
                    allPoi = poiResult.getAllPoi();
                    if (allPoi != null && allPoi.size() != 0) {
                        lv_poi.setAdapter(new POIAdapter(allPoi));
                    }
                }

                @Override
                public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

                }

                @Override
                public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

                }

                @Override
                public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                }
            });

        }

        @Override
        public void afterTextChanged(Editable s) {
            String key = s.toString();
            if (key.length() > 0) {
                lv_lishi.setVisibility(View.GONE);
                lv_poi.setVisibility(View.VISIBLE);
            } else {
                lv_lishi.setVisibility(View.VISIBLE);
                lv_poi.setVisibility(View.GONE);
            }
            poiCitySearchOption = new PoiCitySearchOption()
                    .city(name_mudi)
                    .keyword(key);
            poiSearch.searchInCity(poiCitySearchOption);
            poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                @Override
                public void onGetPoiResult(PoiResult poiResult) {
                    allPoi = poiResult.getAllPoi();
                    if (allPoi != null && allPoi.size() != 0) {
                        lv_poi.setAdapter(new POIAdapter(allPoi));
                    }
                }

                @Override
                public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

                }

                @Override
                public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

                }

                @Override
                public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 111:
                name_mudi = extras.getString("name");
                tv_city.setText(name_mudi);
                break;
            case 333:
                name_jia = extras.getString("name");
                address_jia = extras.getString("address");
                latitude_jia = extras.getString("latitude");
                longitude_jia = extras.getString("longitude");
                if (TextUtils.isEmpty(name_jia) || TextUtils.isEmpty(address_jia) || TextUtils.isEmpty(latitude_jia) || TextUtils.isEmpty(longitude_jia)) {
                    tv_jia.setText("添加");
                    tv_jia_address.setText("设置常用地址");
                } else {
                    sedData(name_jia, address_jia, longitude_jia, latitude_jia, "1");
                }

                break;
            case 444:
                name_gs = extras.getString("name");
                address_gs = extras.getString("address");
                latitude_gs = extras.getString("latitude");
                longitude_gs = extras.getString("longitude");
                if (TextUtils.isEmpty(name_gs) || TextUtils.isEmpty(address_gs) || TextUtils.isEmpty(latitude_gs) || TextUtils.isEmpty(longitude_gs)) {
                    tv_gongsi.setText("添加");
                    tv_gongsi_address.setText("设置常用地址");
                } else {
                    sedData(name_gs, address_gs, latitude_gs, longitude_gs, "2");
                }
                break;
        }
    }

    private void sedData(String name, String address, String longitude, String latitude, final String type) {
        showProgressDialog(false);
        OkHttpUtils.post()
                .url(AppUrl.Add_ChangYong_Address)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .addParams("name", name)
                .addParams("address", address)
                .addParams("longitude", longitude)
                .addParams("latitude", latitude)
                .addParams("type", type)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                            if (successDisCoverBackBean != null) {
                                int code = successDisCoverBackBean.getCode();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if ("1".equals(type)) {
                                                    tv_jia.setText("家");
                                                    tv_jia_address.setText(address_jia);
                                                } else {
                                                    tv_gongsi.setText("公司");
                                                    tv_gongsi_address.setText(address_gs);
                                                }
                                            }
                                        });
                                    }
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

    private class MudiAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (listData != null) {
                return listData.size();
            } else {
                return 1;
            }

        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            XueYuanViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.mudi_lv_item, null);
                holder = new XueYuanViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (XueYuanViewHolder) convertView.getTag();
            }
            ChangYongListBean.ContentBean.ListDataBean listDataBean = listData.get(position);
            if (listDataBean!=null) {
                String address = listDataBean.getAddress();
                String name = listDataBean.getName();
                holder.tv_mudi.setText(name);
                holder.tv_location.setText(address);
            }
            return convertView;
        }

        class XueYuanViewHolder {
            private TextView tv_mudi, tv_location;

            public XueYuanViewHolder(View view) {
                tv_mudi = (TextView) view.findViewById(R.id.tv_mudi);
                tv_location = (TextView) view.findViewById(R.id.tv_location);

            }
        }
    }
}
