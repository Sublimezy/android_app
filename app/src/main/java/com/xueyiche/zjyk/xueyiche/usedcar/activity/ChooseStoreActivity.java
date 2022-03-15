package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.StoreBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZL on 2018/6/28.
 */
public class ChooseStoreActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_store;
    private LinearLayout llBack;
    private TextView tvTitle;
    private List<StoreBean.DataBean> data;

    @Override
    protected int initContentView() {
        return R.layout.peripheral_store_activity;
    }

    @Override
    protected void initView() {
        lv_store = (ListView) findViewById(R.id.lv_store);
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String city = getIntent().getStringExtra("city");
        getData(city);
        tvTitle.setText("门店");
        lv_store.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = data.get(i).getId();
                String shop_name = data.get(i).getShop_name();
                Intent intent = new Intent();
                intent.putExtra("store_id", id+ "");
                intent.putExtra("store_name", shop_name);
                setResult(222, intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                Intent intent = new Intent();
                intent.putExtra("store_id", "");
                intent.putExtra("store_name", "");
                setResult(222, intent);
                finish();
                break;
        }
    }

    public void getData(String city) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String id = LoginUtils.getId(this);
            OkHttpUtils.post().url(AppUrl.Used_Car_Merchant)
                    .addParams("device_id", id)
                    .addParams("area",city)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final StoreBean storeBean = JsonUtil.parseJsonToBean(string, StoreBean.class);
                        if (storeBean != null) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    data = storeBean.getData();
                                    lv_store.setAdapter(new ChooseStoreAdapter(data,App.context,R.layout.choose_store_item));
                                }
                            });

                        }
                        return string;
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

    }
    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("store_id", "");
            intent.putExtra("store_name", "");
            setResult(222, intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private class ChooseStoreAdapter extends BaseCommonAdapter {

        public ChooseStoreAdapter(List<StoreBean.DataBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            StoreBean.DataBean dataBean = (StoreBean.DataBean) item;
            final String shop_name = dataBean.getShop_name();
            String address = dataBean.getAddress();
            String shop_img = dataBean.getShop_img();
            viewHolder.setPic(R.id.iv_store_imag,shop_img);
            viewHolder.setText(R.id.tv_store_name,shop_name);
            viewHolder.setText(R.id.tv_store_address,address);
        }
    }
}
