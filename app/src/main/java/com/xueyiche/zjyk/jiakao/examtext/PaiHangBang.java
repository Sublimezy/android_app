package com.xueyiche.zjyk.jiakao.examtext;

import android.view.View;
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
import com.xueyiche.zjyk.jiakao.constants.StringConstants;
import com.xueyiche.zjyk.jiakao.constants.bean.PaiHangBean;
import com.xueyiche.zjyk.jiakao.homepage.adapters.MyPaiHangBangAdapter;
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
public class PaiHangBang extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private ListView listView;
    private TextView tvTitle;
    private String user_phone;
    private String answer_type;

    @Override
    protected int initContentView() {
        return R.layout.activity_exam_kemua_paihangbang;
    }

    @Override
    protected void initView() {
        answer_type = getIntent().getStringExtra("answer_type");
        user_phone = PrefUtils.getString(App.context, "user_phone", "0");
        llBack = (LinearLayout) view.findViewById(R.id.my_result_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.my_result_include).findViewById(R.id.tv_login_back);
        listView = (ListView) view.findViewById(R.id.my_paihang_list);
        llBack.setOnClickListener(this);

        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
        tvTitle.setText("排行榜");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.MNKSPH).addParams("user_phone", user_phone)
                    .addParams("answer_type", answer_type)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    PaiHangBean paiHangBean = JsonUtil.parseJsonToBean(string, PaiHangBean.class);
                    final List<PaiHangBean.ContentBean> content = paiHangBean.getContent();
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MyPaiHangBangAdapter myPaiHangBangAdapter = new MyPaiHangBangAdapter(content);
                            listView.setAdapter(myPaiHangBangAdapter);
                        }
                    });
                    return null;
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
        }else {
            stopProgressDialog();
            showToastShort(StringConstants.CHECK_NET);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_exam_back:
                finish();
                break;

        }
    }

}
