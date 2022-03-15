package com.xueyiche.zjyk.xueyiche.mine.activities.myjifen;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.JiFenBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2018/6/27.
 */
public class JiFenMingXiActivity extends BaseActivity implements View.OnClickListener {

    private String user_id;
    private TextView tv_login_back;
    private ListView lv_jifen_mingxi;
    private LinearLayout ll_exam_back;
    private List<JiFenBean.ContentBean.RecordsBean> records;

    @Override
    protected int initContentView() {
        return R.layout.my_jifen_mingxi_activity;
    }

    @Override
    protected void initView() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        lv_jifen_mingxi = (ListView) view.findViewById(R.id.lv_jifen_mingxi);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tv_login_back.setText("积分明细");
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
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
                                            records = content.getRecords();
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    lv_jifen_mingxi.setAdapter(new MineJiFenAdapter());
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }

    private class MineJiFenAdapter extends BaseAdapter {

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
                view = LayoutInflater.from(App.context).inflate(R.layout.mine_jifen_list_item, null);
                mineViewHolder = new MineViewHolder(view);
                view.setTag(mineViewHolder);
            } else {
                mineViewHolder = (MineViewHolder) view.getTag();
            }
            JiFenBean.ContentBean.RecordsBean recordsBean = records.get(i);
            if (recordsBean != null) {
                String detail_num = recordsBean.getDetail_num();
                String integral_type = recordsBean.getIntegral_type();
                String system_time = recordsBean.getSystem_time();
                if (!TextUtils.isEmpty(system_time)) {
                    mineViewHolder.tv_jifen_time.setText(system_time);
                }
                if (!TextUtils.isEmpty(integral_type)) {
                    mineViewHolder.tv_mine_jifen_name.setText(integral_type);
                }
                if (!TextUtils.isEmpty(detail_num)) {
                    mineViewHolder.tv_jifen_number.setText(detail_num);
                }
            }
            return view;
        }

        class MineViewHolder {
            private TextView tv_jifen_time, tv_jifen_number, tv_mine_jifen_name;

            public MineViewHolder(View view) {
                tv_jifen_time = (TextView) view.findViewById(R.id.tv_jifen_time);
                tv_jifen_number = (TextView) view.findViewById(R.id.tv_jifen_number);
                tv_mine_jifen_name = (TextView) view.findViewById(R.id.tv_mine_jifen_name);

            }
        }

    }
}
