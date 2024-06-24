package com.xueyiche.zjyk.jiakao.examtext;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.constants.AppUrl;
import com.xueyiche.zjyk.jiakao.homepage.bean.DaTiWinBean;
import com.xueyiche.zjyk.jiakao.utils.JsonUtil;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;
import com.xueyiche.zjyk.jiakao.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2017/1/12.
 */
public class MyJiangJin extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private ListView listView;
    private TextView tvTitle;
    private MyJiangJinAdapter myJiangJinAdapterA;
    private List<DaTiWinBean.ContentBean> content;

    @Override
    protected int initContentView() {
        return R.layout.activity_exam_kemua_jiangjin;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.my_result_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.my_result_include).findViewById(R.id.tv_login_back);
        listView = (ListView) view.findViewById(R.id.my_jiangjin_list);
        llBack.setOnClickListener(this);
        myJiangJinAdapterA = new MyJiangJinAdapter();
        tvTitle.setText("我的奖金");

        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String user_phone = PrefUtils.getString(App.context, "user_phone", "");
        if (XueYiCheUtils.IsHaveInternet(this) && !TextUtils.isEmpty(user_phone)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.DATIYINGXIANJINLIEBIAO)
                    .addParams("user_phone", user_phone)
                    .addParams("jiangjin", "1")
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
    private void processData(String string) {
        DaTiWinBean dingDan = JsonUtil.parseJsonToBean(string, DaTiWinBean.class);
        content = dingDan.getContent();
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (content != null) {
                    if (content.size() != 0) {
                        listView.setAdapter(myJiangJinAdapterA);
                    }
                }

            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_exam_back:
                finish();
                break;

        }
    }
    public class MyJiangJinAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return content.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(App.context, R.layout.my_jiangjin_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            DaTiWinBean.ContentBean contentBean = content.get(position);
            String system_time = contentBean.getSystem_time();
            String bonus = contentBean.getBonus();
            if (!TextUtils.isEmpty(system_time)) {
                holder.tv_my_jiangjin_time.setText(system_time);
            }
            if (!TextUtils.isEmpty(bonus)) {
                holder.tv_my_jiangjin.setText(bonus);
            }
            holder.tv_my_jiangjin_mingci.setText(position+1+"");

            return convertView;
        }
        public class ViewHolder {
            TextView tv_my_jiangjin_time;
            TextView tv_my_jiangjin_mingci;
            TextView tv_my_jiangjin;

            public ViewHolder(View view) {
                tv_my_jiangjin_time = (TextView) view.findViewById(R.id.tv_my_jiangjin_time);
                tv_my_jiangjin_mingci = (TextView) view.findViewById(R.id.tv_my_jiangjin_mingci);
                tv_my_jiangjin = (TextView) view.findViewById(R.id.tv_my_jiangjin);
            }
        }

    }
}
