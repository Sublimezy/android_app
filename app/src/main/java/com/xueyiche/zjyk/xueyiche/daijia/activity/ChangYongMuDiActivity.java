package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
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
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.bean.ChangYongListBean;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.POIAdapter;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2019/9/17.
 */
public class ChangYongMuDiActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_finish, tv_city;
    private ListView lv_lishi, lv_poi;
    private String substring;
    private String name_mudi = "哈尔滨";
    private EditText et_mudi;
    private PoiCitySearchOption poiCitySearchOption;
    private PoiSearch poiSearch;
    private List<PoiInfo> allPoi;
    private String type;
    private List<ChangYongListBean.ContentBean.ListDataBean> listData;
    private String user_id;

    @Override
    protected int initContentView() {
        return R.layout.mudi_activity;
    }

    @Override
    protected void initView() {
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_finish = (TextView) view.findViewById(R.id.tv_finish);
        et_mudi = (EditText) view.findViewById(R.id.et_mudi);
        lv_lishi = (ListView) view.findViewById(R.id.lv_lishi);
        lv_poi = (ListView) view.findViewById(R.id.lv_poi);
        poiSearch = PoiSearch.newInstance();
    }

    @Override
    protected void initListener() {
        tv_finish.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        et_mudi.addTextChangedListener(new EditChangedListener());
        lv_lishi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                        if ("jia".equals(type)) {
                            setResult(333, intent);
                        } else if ("gs".equals(type)) {
                            setResult(444, intent);
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
                    LatLng location = poiInfo.location;
//                    if (area_open.contains(area)) {
                        if (location != null) {
                            double latitude = location.latitude;//纬度
                            double longitude = location.longitude;//经度
                            String address = poiInfo.address;
                            Intent intent1 = new Intent();
                            intent1.putExtra("name", name);
                            intent1.putExtra("address", address);
                            intent1.putExtra("latitude", latitude+"");
                            intent1.putExtra("longitude", longitude+"");
                            if ("jia".equals(type)) {
                                setResult(333, intent1);
                            } else if ("gs".equals(type)) {
                                setResult(444, intent1);
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
    protected void initData() {
        user_id = PrefUtils.getString(this, "user_id", "");
        getChaongYong();
        XueYiCheUtils.getNowLocation(this);
        String city = PrefUtils.getString(App.context, "city", "");
        type = getIntent().getStringExtra("type");
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
                if ("jia".equals(type)) {
                    setResult(333, intent1);
                } else if ("gs".equals(type)) {
                    setResult(444, intent1);
                }
                finish();
                break;
            case R.id.tv_city:
                //选择城市
                Intent intent2 = new Intent(this, CityActivity.class);
                startActivityForResult(intent2, 111);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent1 = new Intent();
            intent1.putExtra("name", "");
            intent1.putExtra("address", "");
            intent1.putExtra("latitude", "");
            intent1.putExtra("longitude", "");
            if ("jia".equals(type)) {
                setResult(333, intent1);
            } else if ("gs".equals(type)) {
                setResult(444, intent1);
            }
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);

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
        }
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