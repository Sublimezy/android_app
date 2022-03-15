package com.xueyiche.zjyk.xueyiche.homepage.activities.carquery;

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
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.homepage.bean.WeiZhangHistoryBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2019/6/3.
 */
public class CarHistoryActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private ListView lv_weizhang_history;
    private String type;
    private String url;
    private WeiZhangAdapter weiZhangAdapter;
    private List<WeiZhangHistoryBean.ContentBean> content;
    private String violation_type;

    @Override
    protected int initContentView() {
        return R.layout.car_history_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        lv_weizhang_history = (ListView) view.findViewById(R.id.lv_weizhang_history);
        tv_login_back.setText("历史记录");
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        weiZhangAdapter = new WeiZhangAdapter();

        //1 维修 2 违章 3 保险
        if ("1".equals(type)) {
            violation_type = "3";
        } else if ("2".equals(type)) {
            violation_type = "1";
        } else if ("3".equals(type)) {
            violation_type = "2";
        }

        getDataFromNet();
        lv_weizhang_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WeiZhangHistoryBean.ContentBean contentBean = content.get(i);
                if (contentBean != null) {
                    String tid = contentBean.getTid();
                    Intent intent1 = new Intent(App.context, UrlActivity.class);
                    if ("2".equals(type)) {
                        intent1.putExtra("url", "http://www.xueyiche.vip:88/wap/share/weizhang?id=" + tid);
                        intent1.putExtra("type", "100");
                    } else if ("3".equals(type)) {
                        intent1.putExtra("url", "http://www.xueyiche.vip:88/wap/share/baoxian?id=" + tid);
                        intent1.putExtra("type", "101");
                    } else if ("1".equals(type)) {
                        intent1.putExtra("url", "http://www.xueyiche.vip:88/wap/share/weibao?id=" + tid);
                        intent1.putExtra("type", "102");
                    }
                    startActivity(intent1);
                }
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

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String user_phone = PrefUtils.getString(App.context, "user_phone", "");
            showProgressDialog(false);
            OkHttpUtils.post()
                    .url(AppUrl.ChaXun_List)
//                    .url("http://172.16.51.61:8082/secondhandcarevaluation/baoxianlist.do")
                    .addParams("user_tel", AES.decrypt(user_phone))
                    .addParams("violation_type", violation_type)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                            final WeiZhangHistoryBean weiZhangHistoryBean = JsonUtil.parseJsonToBean(string, WeiZhangHistoryBean.class);
                            if (weiZhangHistoryBean != null) {
                                int code = weiZhangHistoryBean.getCode();
                                if (200 == code) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            content = weiZhangHistoryBean.getContent();
                                            lv_weizhang_history.setAdapter(weiZhangAdapter);
                                        }
                                    });
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
        }
    }

    public class WeiZhangAdapter extends BaseAdapter {

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
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolerNo viewHolerSheQu = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.car_history_list_item, null);
                viewHolerSheQu = new ViewHolerNo(view);
                view.setTag(viewHolerSheQu);
            } else {
                viewHolerSheQu = (ViewHolerNo) view.getTag();
            }
            WeiZhangHistoryBean.ContentBean contentBean = content.get(i);
            String user_tel = contentBean.getUser_tel();
            String ins_system = contentBean.getIns_system();
            String tid = contentBean.getTid();
            if (!TextUtils.isEmpty(ins_system)) {
                viewHolerSheQu.tv_date.setText("日期：" + ins_system);
            }
            if (!TextUtils.isEmpty(tid)) {
                viewHolerSheQu.tv_number.setText("编号：" + tid);
            }
            return view;
        }


        class ViewHolerNo {

            private final TextView tv_check;
            private final TextView tv_date;
            private final TextView tv_number;

            public ViewHolerNo(View view) {
                tv_check = (TextView) view.findViewById(R.id.tv_check);
                tv_date = (TextView) view.findViewById(R.id.tv_date);
                tv_number = (TextView) view.findViewById(R.id.tv_number);
            }
        }
    }
}
