package com.xueyiche.zjyk.xueyiche.mine.activities.myjifen;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.JiFenBean;
import com.xueyiche.zjyk.xueyiche.pay.JiFenChongZhi;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglei on 2016/10/29.
 */
public class MyJiFenActivity extends BaseActivity implements View.OnClickListener {
    private String user_id;
    private TextView tv_login_back;
    private ListView lv_jifen_mingxi;
    private RadioButton iv_qiandao_kaiguan;
    private LinearLayout ll_exam_back;
    private List<JiFenBean.ContentBean.RecordsBean> records;
    private TextView tv_jifen_number;
    private Button bt_chongzhi;
    private TextView tv_wenxintishi;
    @Override
    protected int initContentView() {
        return R.layout.my_jifen_activity;
    }

    @Override
    protected void initView() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_wenxintishi);
        lv_jifen_mingxi = (ListView) view.findViewById(R.id.lv_jifen_mingxi);
        iv_qiandao_kaiguan = (RadioButton) view.findViewById(R.id.iv_qiandao_kaiguan);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        bt_chongzhi = (Button) view.findViewById(R.id.bt_chongzhi);
        tv_jifen_number = (TextView) view.findViewById(R.id.tv_jifen_number);
        tv_login_back.setText("我的积分");
//        tv_wenxintishi.setVisibility(View.VISIBLE);
//        tv_wenxintishi.setText("积分明细");
        MobclickAgent.onEvent(MyJiFenActivity.this, "yibi_chongzhi");

    }

    @Override
    protected void initListener() {
        iv_qiandao_kaiguan.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        bt_chongzhi.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getDataFromNet();

    }

    private void getDataFromNet() {
        OkHttpUtils.post().url(AppUrl.My_JiFen)
                .addParams("user_id", user_id)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            JiFenBean jiFenBean = JsonUtil.parseJsonToBean(string, JiFenBean.class);
                            if (jiFenBean != null) {
                                int code = jiFenBean.getCode();
                                if (!TextUtils.isEmpty("" + code)) {
                                    if (200 == code) {
                                        JiFenBean.ContentBean content = jiFenBean.getContent();
                                        if (content != null) {
                                            final int integral_num = content.getIntegral_num();
                                            records = content.getRecords();
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (!TextUtils.isEmpty("" + integral_num)) {
                                                        tv_jifen_number.setText("" + integral_num);
                                                        lv_jifen_mingxi.setAdapter(new MineJiFenDuiHuanAdapter());
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

                    }

                    @Override
                    public void onResponse(Object response) {

                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:
                openActivity(JiFenMingXiActivity.class);
                break;
            case R.id.bt_chongzhi:
                //去充值
                Intent intentChongZhi = new Intent(App.context, JiFenChongZhi.class);
                startActivity(intentChongZhi);
                break;
        }
    }


    private class MineJiFenDuiHuanAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (records != null) {
                return records.size();
            } else {
                return 0;

            }
        }

        @Override
        public Object getItem(int i) {
            return records.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MineViewHolder mineViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.mine_jifenduihuan_list_item, null);
                mineViewHolder = new MineViewHolder(view);
                view.setTag(mineViewHolder);
            } else {
                mineViewHolder = (MineViewHolder) view.getTag();
            }
            JiFenBean.ContentBean.RecordsBean recordsBean = records.get(i);
            if (recordsBean != null) {

            }
            return view;
        }

        class MineViewHolder {
            private TextView tv_shiyongyu, tv_coupon_type, tv_offset_money, tv_use, tv_end_time,tv_coupon_name,tv_jifen_price;

            public MineViewHolder(View view) {
                tv_offset_money = (TextView) view.findViewById(R.id.tv_offset_money);
                tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
                tv_use = (TextView) view.findViewById(R.id.tv_use);
                tv_coupon_name = (TextView) view.findViewById(R.id.tv_coupon_name);
                tv_shiyongyu = (TextView) view.findViewById(R.id.tv_shiyongyu);
                tv_coupon_type = (TextView) view.findViewById(R.id.tv_coupon_type);
                tv_jifen_price = (TextView) view.findViewById(R.id.tv_jifen_price);

            }
        }

    }
}
