package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.bean.CommonBean;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaActivity;
import com.xueyiche.zjyk.xueyiche.daijia.adadapter.SearchPOIListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationSearchActivity extends BaseActivity implements AMapLocationListener, PoiSearch.OnPoiSearchListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private CommonBean commonBean = new CommonBean();
    private SearchPOIListAdapter searchPOIListAdapter;
    @Override
    protected int initContentView() {
        return R.layout.activity_location_search;
    }
    public static void forward(Activity activity,int code,String type) {
        Intent intent = new Intent(activity, LocationSearchActivity.class);
        intent.putExtra("type",type);
        activity.startActivityForResult(intent,code);
    }
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvTitle.setText("位置检索");
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        try {
            mlocationClient = new AMapLocationClient(this);
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
        searchPOIListAdapter = new SearchPOIListAdapter(R.layout.item_search_poi_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchPOIListAdapter);
        searchPOIListAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
        searchPOIListAdapter.getData().clear();
        searchPOIListAdapter.notifyDataSetChanged();
        searchPOIListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PoiItem poiItem = (PoiItem) adapter.getItem(position);
                if (poiItem!=null) {
                    String cityCode = poiItem.getCityCode();
                    LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                    double latitude = latLonPoint.getLatitude();
                    double longitude = latLonPoint.getLongitude();
                    String title = poiItem.getTitle();
                    Intent intent = new Intent();
                    intent.putExtra("lat", ""+latitude);
                    intent.putExtra("lon", ""+longitude);
                    intent.putExtra("title", ""+title);
                    intent.putExtra("cityCode", ""+cityCode);
                    Log.e("cityCode",""+cityCode);
                    if ("start".equals(getIntent().getStringExtra("type"))) {
                        setResult(111,intent);
                    }else if ("end".equals((getIntent().getStringExtra("type")))){
                        setResult(222,intent);
                    }

                    finish();
                }


            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if (!TextUtils.isEmpty(s1)) {
                    search(s1);
                } else {
                    searchPOIListAdapter.getData().clear();
                    searchPOIListAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
                    searchPOIListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("lat", "");
            intent.putExtra("lon", "");
            intent.putExtra("title", "");
            intent.putExtra("cityCode", "");
            if ("start".equals(getIntent().getStringExtra("type"))) {
                setResult(111,intent);
            }else if ("end".equals(equals(getIntent().getStringExtra("type")))){
                setResult(222,intent);
            }

            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private PoiSearch.Query query;// Poi查询条件类
    private PoiResult poiResult1;
    private PoiSearch poiSearch;// POI搜索

    private void search(String search) {
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(search, "", !TextUtils.isEmpty(commonBean.getCity()) ? commonBean.getCity() : "");
        query.setPageSize(100);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        query.setCityLimit(true);
        try {
            poiSearch = new PoiSearch(this, query);
        } catch (AMapException e) {
            e.printStackTrace();
        }
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                String city = aMapLocation.getCity();
                String cityCode = aMapLocation.getCityCode();
                Log.e("onLocationChanged", "" + city);
                Log.e("onLocationChanged", "" + cityCode);
                commonBean.setCity("" + city);

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    poiResult1 = poiResult;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    Gson gson = new Gson();
                    String s = gson.toJson(poiItems);
                    if (poiItems != null && poiItems.size() > 0) {
                        searchPOIListAdapter.setNewData(poiItems);
                    } else if (suggestionCities != null && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                        searchPOIListAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
                        searchPOIListAdapter.getData().clear();
                        searchPOIListAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                searchPOIListAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
                searchPOIListAdapter.getData().clear();
                searchPOIListAdapter.notifyDataSetChanged();
            }
        } else {
            searchPOIListAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
            searchPOIListAdapter.getData().clear();
            searchPOIListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }

    }

    @OnClick(R.id.ll_common_back)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("lat","");
        intent.putExtra("lon","");
        intent.putExtra("title","");
        intent.putExtra("cityCode", "");
        if ("start".equals(getIntent().getStringExtra("type"))) {
            setResult(111,intent);
        }else if ("end".equals(equals(getIntent().getStringExtra("type")))){
            setResult(222,intent);
        }
        finish();
    }
}