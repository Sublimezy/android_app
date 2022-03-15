package com.xueyiche.zjyk.xueyiche.practicecar.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.POIAdapter;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.List;

/**
 * Created by Owner on 2016/11/26.
 */
public class POIDressActivity extends BaseActivity implements View.OnClickListener {
    private Button aaa;
    private PoiCitySearchOption poiCitySearchOption;
    private EditText city, keyword;
    private ListView lv_poi;
    private PoiSearch poiSearch;
    private LinearLayout ll_exam_back;
    private List<PoiInfo> allPoi;
    private String cartype;

    @Override
    protected int initContentView() {
        return R.layout.poi_activity;
    }

    @Override
    protected void initView() {
        poiSearch = PoiSearch.newInstance();
        ll_exam_back = (LinearLayout) view.findViewById(R.id.ll_exam_back);
        keyword = (EditText) view.findViewById(R.id.keyword);
        lv_poi = (ListView) view.findViewById(R.id.lv_poi);
        ll_exam_back.setOnClickListener(this);
        cartype = getIntent().getStringExtra("cartype");
    }

    @Override
    protected void initListener() {
        keyword.addTextChangedListener(new EditChangedListener());
        lv_poi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (allPoi!=null) {
                    PoiInfo poiInfo = allPoi.get(position);
                    String name = poiInfo.name;
                    LatLng location = poiInfo.  location;
                    if (location!=null) {
                        double latitude = location.latitude;//纬度
                        double longitude = location.longitude;//经度
                        Intent intent = new Intent();
                        intent.putExtra("name", name);
                        intent.putExtra("latitude", latitude + "");
                        intent.putExtra("longitude", longitude + "");
                        String jiesong_type = getIntent().getStringExtra("jiesong_type");
                        if ("1".equals(jiesong_type)) {
                            setResult(111, intent);
                        }else if ("2".equals(jiesong_type)){
                            setResult(333, intent);
                        }
                        finish();
                    }
                }else {
                    showToastShort("请重新选择");
                }
            }
        });
    }
    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("name", "");
            intent.putExtra("latitude", "");
            intent.putExtra("longitude", "");
            String jiesong_type = getIntent().getStringExtra("jiesong_type");
            if ("1".equals(jiesong_type)) {
                setResult(111, intent);
            }else if ("2".equals(jiesong_type)){
                setResult(333, intent);
            }
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                Intent intent = new Intent();
                intent.putExtra("name", "");
                intent.putExtra("latitude", "");
                intent.putExtra("longitude", "");
                String jiesong_type = getIntent().getStringExtra("jiesong_type");
                if ("1".equals(jiesong_type)) {
                    setResult(111, intent);
                }else if ("2".equals(jiesong_type)){
                    setResult(333, intent);
                }
                finish();
                break;
        }
    }


    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String key = s.toString();
            String area_name = "哈尔滨";
            if ("1".equals(cartype)) {
                 area_name = PrefUtils.getParameter("area_name");
            }else {
                 area_name = PrefUtils.getString(POIDressActivity.this,"usedcarcity","哈尔滨市");
            }

            poiCitySearchOption = new PoiCitySearchOption()
                    .city(area_name)
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
            String area_name = "哈尔滨";
            if ("1".equals(cartype)) {
                area_name = PrefUtils.getParameter("area_name");
            }else {
                area_name = PrefUtils.getString(POIDressActivity.this,"usedcarcity","");
            }
            poiCitySearchOption = new PoiCitySearchOption()
                    .city(area_name)
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
}
