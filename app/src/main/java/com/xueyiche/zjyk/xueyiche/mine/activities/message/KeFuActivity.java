package com.xueyiche.zjyk.xueyiche.mine.activities.message;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.analytics.MobclickAgent;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.KeFuListBean;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2017/4/20.
 */
public class KeFuActivity extends BaseActivity implements View.OnClickListener {
    private AdListView lv_faq;
    private LinearLayout ll_exam_back;
    private TextView tv_login_back, tv_customservice_call, tv_customservice_zaixian;
    private ScrollView sv_kefu;
    private FAqAdapter fAqAdapter;
    private List<KeFuListBean.ContentBean> content;

    @Override
    protected int initContentView() {

        return R.layout.custom_service_fragment;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.message_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.message_include).findViewById(R.id.tv_login_back);
        tv_customservice_call = (TextView) view.findViewById(R.id.tv_customservice_call);
        tv_customservice_zaixian = (TextView) view.findViewById(R.id.tv_customservice_zaixian);
        sv_kefu = (ScrollView) view.findViewById(R.id.sv_kefu);
        sv_kefu.smoothScrollBy(0, 0);
        lv_faq = (AdListView) view.findViewById(R.id.lv_faq);
        lv_faq.setItemsCanFocus(true);
        tv_login_back.setText("客服");
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_customservice_zaixian.setOnClickListener(this);
        tv_customservice_call.setOnClickListener(this);
        lv_faq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int id = content.get(i).getId();
                Intent intent = new Intent(App.context, KeFuQuesContentActivity.class);
                intent.putExtra("id", "" + id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        fAqAdapter = new FAqAdapter();
        getDataFromNet();

    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils
                    .post()
                    .url(AppUrl.KeFu_List)
                    .addParams("device_id", LoginUtils.getId(KeFuActivity.this))
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                KeFuListBean keFuListBean = JsonUtil.parseJsonToBean(string, KeFuListBean.class);
                                if (keFuListBean != null) {
                                    int code = keFuListBean.getCode();
                                    if (!TextUtils.isEmpty("" + code)) {
                                        if (200 == code) {
                                            content = keFuListBean.getContent();
                                            if (content != null) {
                                                App.handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        lv_faq.setAdapter(fAqAdapter);
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
        } else {
            Toast.makeText(KeFuActivity.this, StringConstants.CHECK_NET, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击电话客服
            case R.id.tv_customservice_call:
                XueYiCheUtils.CallPhone(KeFuActivity.this, "拨打客服电话", "0451-58627471");
                MobclickAgent.onEvent(this, "kefu_phone");
                break;
            case R.id.tv_customservice_zaixian:
                if (DialogUtils.IsLogin()) {
                    openActivity(ZaiXianKeFuActivity.class);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }

    public class FAqAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (content != null) {

                return content.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return content.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            KeFuViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(KeFuActivity.this).inflate(
                        R.layout.kefu_faq_list_item, parent, false);
                holder = new KeFuViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (KeFuViewHolder) convertView.getTag();
            }
            String quest_name = content.get(position).getQuest_name();
            int id = content.get(position).getId();
            if (!TextUtils.isEmpty(quest_name)) {
                holder.tv_kefu_question.setText(id + "." + quest_name);
            }
            return convertView;
        }

        private class KeFuViewHolder {
            private TextView tv_kefu_question;

            public KeFuViewHolder(View view) {
                tv_kefu_question = (TextView) view.findViewById(R.id.tv_kefu_question);
            }
        }
    }


}
