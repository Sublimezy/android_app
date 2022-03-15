package com.xueyiche.zjyk.xueyiche.mine.activities.youhuiquan;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaActivity;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.CouponBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZL on 2017/7/21.
 */
public class YouHuiQuan extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private LinearLayout ll_not_indent;
    private TextView tvTitle;
    private ListView list_view_youhuiquan;
    private TextView tv_collection;
    private List<CouponBean.ContentBean> content;
    private CouponAdapter couponAdapter;

    @Override
    protected int initContentView() {
        return R.layout.youhuiquan;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.youhuiquan_include).findViewById(R.id.ll_exam_back);
        ll_not_indent = (LinearLayout) view.findViewById(R.id.ll_not_indent);
        tvTitle = (TextView) view.findViewById(R.id.youhuiquan_include).findViewById(R.id.tv_login_back);
        list_view_youhuiquan = (ListView) view.findViewById(R.id.list_view_youhuiquan);
        tv_collection = (TextView) view.findViewById(R.id.tv_collection);
        llBack.setOnClickListener(this);
        tv_collection.setText("暂无优惠券哦~");
        Intent intent = getIntent();
        getDataFromNet();

    }

    private void getDataFromNet() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Coupon_List)
                    .addParams("device_id", TextUtils.isEmpty(LoginUtils.getId(YouHuiQuan.this)) ? "" : LoginUtils.getId(YouHuiQuan.this))
                    .addParams("user_id", user_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        processData(string);
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

    @Override
    protected void onResume() {
        getDataFromNet();
        super.onResume();
    }
    private void processData(String string) {
        CouponBean couponBean = JsonUtil.parseJsonToBean(string, CouponBean.class);
        if (couponBean != null) {
            int code = couponBean.getCode();
            content = couponBean.getContent();
            if (content != null) {
                if (!TextUtils.isEmpty("" + code)) {
                    if (code == 200) {
                        couponAdapter = new CouponAdapter();
                        if (content != null && content.size() > 0) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    list_view_youhuiquan.setAdapter(couponAdapter);
                                    ll_not_indent.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            ll_not_indent.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

        }
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("优惠券");
        list_view_youhuiquan.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (content != null && content.size() != 0) {
                    CouponBean.ContentBean contentBean = content.get(i);
                    if (contentBean != null) {
                        String web_url = contentBean.getWeb_url();
                            Intent intent = new Intent(App.context, UrlActivity.class);
                            intent.putExtra("url", web_url);
                            intent.putExtra("type", "4");
                            startActivity(intent);
                    }
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }

    public class CouponAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (content != null && content.size() > 0) {
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
            ViewHolderYouHui viewHolderYouHui = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.youhuijuan_list_item, null);
                viewHolderYouHui = new ViewHolderYouHui(view);
                view.setTag(viewHolderYouHui);
            } else {
                viewHolderYouHui = (ViewHolderYouHui) view.getTag();
            }
            CouponBean.ContentBean contentBean = content.get(i);
            if (contentBean != null) {
                String coupon_name = contentBean.getCoupon_name();
                String end_time = contentBean.getEnd_time();
                String offset_money = contentBean.getOffset_money();
                String shiyongyu = contentBean.getShiyongyu();
                String coupon_type = contentBean.getCoupon_type();
                if (!TextUtils.isEmpty(coupon_type)) {
                    viewHolderYouHui.tv_coupon_type.setText(coupon_type);
                }
                if (!TextUtils.isEmpty(end_time)) {
                    viewHolderYouHui.tv_end_time.setText(end_time);
                }
                if (!TextUtils.isEmpty(shiyongyu)) {
                    viewHolderYouHui.tv_shiyongyu.setText(shiyongyu);
                }
                if (!TextUtils.isEmpty(offset_money)) {
                    viewHolderYouHui.tv_offset_money.setText(offset_money);
                }
                if (!TextUtils.isEmpty(coupon_name)) {
                    viewHolderYouHui.tv_coupon_name.setText(coupon_name);
                }
                viewHolderYouHui.tv_use.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(App.context, DaiJiaActivity.class);
                        App.context.startActivity(intent);
                    }
                });
            }
            return view;
        }

        class ViewHolderYouHui {
            private TextView tv_shiyongyu, tv_coupon_type, tv_offset_money, tv_use, tv_end_time,tv_coupon_name;

            public ViewHolderYouHui(View view) {
                tv_offset_money = (TextView) view.findViewById(R.id.tv_offset_money);
                tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
                tv_use = (TextView) view.findViewById(R.id.tv_use);
                tv_coupon_name = (TextView) view.findViewById(R.id.tv_coupon_name);
                tv_shiyongyu = (TextView) view.findViewById(R.id.tv_shiyongyu);
                tv_coupon_type = (TextView) view.findViewById(R.id.tv_coupon_type);
            }
        }
    }
}
