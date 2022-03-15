package com.xueyiche.zjyk.xueyiche.carlife;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.homepage.bean.CommonSearchBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ResultBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.MyGridView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by YJF on 2020/2/25.
 */
public class CarLifeSearchActivity extends BaseActivity implements View.OnClickListener {
    private TextView iv_title_search;
    private RadioButton ll_choice_chanpin;
    private RadioButton ll_choice_shop;
    private EditText tv_search_title;
    private ImageView iv_search_yuyin;
    private AdListView myListView;
    private ImageView iv_clean_history;
    private MyGridView gv_history_content;
    private HistoryAdapter historyAdapter;
    private List<String> huancun;
//    private RecognizerDialog mDialog;
    private Gson mGson;
    private boolean isSearch = false;
    private List<CommonSearchBean.ContentBean> content;
    private ScrollView scroll_view;
    private String search_type = "1";

    @Override
    protected int initContentView() {
        return R.layout.common_search_activity;
    }

    @Override
    protected void initView() {
        ll_choice_chanpin = (RadioButton) view.findViewById(R.id.ll_choice_chanpin);
        ll_choice_shop = (RadioButton) view.findViewById(R.id.ll_choice_shop);
        iv_title_search = (TextView) view.findViewById(R.id.iv_title_search);
        iv_search_yuyin = (ImageView) view.findViewById(R.id.iv_search_yuyin);
        tv_search_title = (EditText) view.findViewById(R.id.tv_search_title);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        scroll_view.smoothScrollBy(0, 0);
        scroll_view.smoothScrollTo(0, 0);
        myListView = (AdListView) view.findViewById(R.id.lv_search);
        iv_clean_history = (ImageView) view.findViewById(R.id.iv_clean_history);
        gv_history_content = (MyGridView) view.findViewById(R.id.gv_history_content);
        iv_title_search.setOnClickListener(this);
        ll_choice_chanpin.setOnClickListener(this);
        ll_choice_shop.setOnClickListener(this);
        iv_search_yuyin.setOnClickListener(this);
        iv_clean_history.setOnClickListener(this);
        ll_choice_chanpin.setChecked(true);
        gv_history_content.setFocusable(false);
        myListView.setFocusable(false);
        huancun = new ArrayList<String>();
        XueYiCheUtils.getRecord(CarLifeSearchActivity.this);
//        SpeechUtility.createUtility(CarLifeSearchActivity.this, SpeechConstant.APPID + "=5a66e5e6");
        mGson = new Gson();
        getDataFromNet();
    }

    @Override
    protected void initListener() {
        //EditText内容的监听
        tv_search_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = tv_search_title.getText().length();
                if (length > 0) {
                    iv_search_yuyin.setImageResource(R.mipmap.shanchu);
                    iv_title_search.setText("搜索");
                    isSearch = true;
                } else {
                    iv_search_yuyin.setImageResource(R.mipmap.sousuo_yuyin);
                    iv_title_search.setText("取消");
                    isSearch = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    @Override
    protected void initData() {
        huancun = PrefUtils.getStrListValue(App.context, "list_huancun");
        Collections.reverse(huancun);
        historyAdapter = new HistoryAdapter(huancun, App.context, R.layout.gv_search_item);
        if (huancun != null && huancun.size() != 0) {
            gv_history_content.setAdapter(historyAdapter);
        }
        tv_search_title.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                searchJump();
                if (actionId == KeyEvent.KEYCODE_ENTER) {

                    return true;
                }
                return false;
            }
        });


        gv_history_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String content = huancun.get(i);
                searchService(content, "");
                historyAdapter.notifyDataSetChanged();
            }
        });
    }


    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            final String area_id = PrefUtils.getString(App.context, "area_id", "");
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Carlife_Search)
                    .addParams("device_id", LoginUtils.getId(CarLifeSearchActivity.this))
                    .addParams("area_id", area_id)
                    .addParams("user_id", user_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        CommonSearchBean commonSearchBean = JsonUtil.parseJsonToBean(string, CommonSearchBean.class);
                        if (commonSearchBean != null) {
                            int code = commonSearchBean.getCode();
                            if (200 == code) {
                                content = commonSearchBean.getContent();
                                if (content != null) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            myListView.setAdapter(new SearchAdapter());
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
        } else {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(App.context, StringConstants.CHECK_NET, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public static List<String> getNewList(List<String> list) {
        List<String> myList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            if (!myList.contains(list.get(i))) {
                myList.add(list.get(i));
            }
        }
        return myList;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_search:
                String s = iv_title_search.getText().toString();
                if ("取消".equals(s)) {
                    finish();
                }else {
                    searchJump();
                }

                break;
            case R.id.ll_choice_chanpin:
                search_type = "1";
                myListView.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_choice_shop:
                search_type = "2";
                myListView.setVisibility(View.GONE);
                break;
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.iv_search_yuyin:
                if (isSearch) {
                    tv_search_title.getText().clear();
                } else {
//                    onRecognise();
                }

                break;
            case R.id.iv_clean_history:
                //TODO:清除历史记录
                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        PrefUtils.remove(App.context, "list_huancun");
                        huancun.clear();
                        gv_history_content.setAdapter(historyAdapter);
                        historyAdapter.notifyDataSetChanged();
                    }
                });
                break;

        }
    }


    private void searchJump() {
        String trim = tv_search_title.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)) {
            searchService(trim, "");
        } else {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(App.context, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void searchService(String trim, String service_id) {
        if (CarLifeSearchResultActivity.instance!=null) {
            CarLifeSearchResultActivity.instance.finish();
        }
        Intent intent = new Intent(App.context, CarLifeSearchResultActivity.class);
        intent.putExtra("search_name", trim);
        intent.putExtra("search_type", search_type);
        intent.putExtra("service_id", service_id);
        intent.putExtra("service_type", "");
        intent.putExtra("coupon_id", "");
        huancun.add(trim);
        List<String> distinctList = getNewList(huancun);
        PrefUtils.putStrListValue(App.context, "list_huancun", distinctList);
        startActivity(intent);
        finish();

    }


    private class SearchAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (content != null) {
                return content.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int i) {
            return content.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SearchViewHolder searchViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.lv_search_item, null);
                searchViewHolder = new SearchViewHolder(view);
                view.setTag(searchViewHolder);
            } else {
                searchViewHolder = (SearchViewHolder) view.getTag();
            }
            if (content != null) {
                String service_type_name = content.get(i).getService_type_name();
                List<CommonSearchBean.ContentBean.CarLifeServiceTypeForSearchBean> carLifeServiceTypes = content.get(i).getCarLifeServiceTypeForSearch();
                if (!TextUtils.isEmpty(service_type_name)) {
                    searchViewHolder.tv_search_title.setText(service_type_name);
                }
                if (carLifeServiceTypes != null) {
                    searchViewHolder.gv_search_content.setAdapter(new GridAdapter(carLifeServiceTypes));
                }
            }


            return view;
        }
    }

    private class GridAdapter extends BaseAdapter {
        private List<CommonSearchBean.ContentBean.CarLifeServiceTypeForSearchBean> carLifeServiceTypes;

        public GridAdapter(List<CommonSearchBean.ContentBean.CarLifeServiceTypeForSearchBean> carLifeServiceTypes) {
            this.carLifeServiceTypes = carLifeServiceTypes;
        }

        @Override
        public int getCount() {
            if (carLifeServiceTypes != null) {

                return carLifeServiceTypes.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int i) {
            return carLifeServiceTypes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SearchViewHolder gridViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.gv_search_item, null);
                gridViewHolder = new SearchViewHolder(view);
                view.setTag(gridViewHolder);
            } else {
                gridViewHolder = (SearchViewHolder) view.getTag();
            }
            if (carLifeServiceTypes != null) {
                CommonSearchBean.ContentBean.CarLifeServiceTypeForSearchBean carLifeServiceTypesBean = carLifeServiceTypes.get(i);
                if (carLifeServiceTypesBean != null) {
                    final String service_name = carLifeServiceTypesBean.getService_name();
                    final int service_id = carLifeServiceTypesBean.getService_id();
                    if (!TextUtils.isEmpty(service_name)) {
                        gridViewHolder.tv_gv_content.setText(service_name);
                        gridViewHolder.tv_gv_content.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                searchService(service_name, service_id + "");
                            }
                        });
                    }
                }

            }


            return view;
        }
    }

    private class HistoryAdapter extends BaseCommonAdapter {

        public HistoryAdapter(List<String> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            String content = (String) item;
            if (!TextUtils.isEmpty(content)) {
                viewHolder.setText(R.id.tv_gv_content, content);
            }
        }
    }

    class SearchViewHolder {
        private TextView tv_gv_content;
        private TextView tv_search_title;
        private MyGridView gv_search_content;

        public SearchViewHolder(View view) {
            tv_gv_content = (TextView) view.findViewById(R.id.tv_gv_content);
            tv_search_title = (TextView) view.findViewById(R.id.tv_search_title);
            gv_search_content = (MyGridView) view.findViewById(R.id.gv_search_content);
        }
    }
}